package com.zekrom_64.ze.gl.shader;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.lwjgl.opengl.ARBGLSPIRV;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL40;
import org.lwjgl.opengl.GL41;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.libc.LibCStdlib;

import com.zekrom_64.ze.base.backend.render.shader.ZEShader;
import com.zekrom_64.ze.base.backend.render.shader.ZEShaderCompiler;
import com.zekrom_64.ze.base.backend.render.shader.ZEShaderProgram;
import com.zekrom_64.ze.gl.GLException;
import com.zekrom_64.ze.gl.GLNativeContext;
import com.zekrom_64.ze.gl.GLRenderBackend;
import com.zekrom_64.ze.gl.shader.GLShaderProgram.GLAttribute;
import com.zekrom_64.ze.gl.shader.GLShaderProgram.GLUniform;

public class GLShaderCompiler implements ZEShaderCompiler {
	
	private final GLRenderBackend backend;
	private final GLNativeContext context;
	
	public GLShaderCompiler(GLRenderBackend backend) {
		this.backend = backend;
		context = backend.getNativeContext();
	}
	
	private static ByteBuffer loadSPIRV(Object obj) {
		if (obj instanceof int[]) {
			int[] array = (int[])obj;
			ByteBuffer buf = LibCStdlib.malloc(array.length * 4);
			buf.asIntBuffer().put(array);
			buf.rewind();
			return buf;
		} else if (obj instanceof IntStream) {
			return loadSPIRV(((IntStream)obj).toArray());
		} else if (obj instanceof IntBuffer) {
			IntBuffer ibuf = (IntBuffer)obj;
			ByteBuffer buf = LibCStdlib.malloc(ibuf.remaining() * 4);
			int pos = ibuf.position();
			buf.asIntBuffer().put(ibuf);
			ibuf.position(pos);
			buf.rewind();
			return buf;
		} else throw new IllegalArgumentException("Invalid SPIR-V source object of type \"" + obj.getClass() + "\"");
	}
	
	@Override
	public ZEShader compileShader(Object src, String type, String shaderType) {
		int glsType = -1;
		switch(type) {
		case ZEShader.SHADER_TYPE_VERTEX: glsType = GL20.GL_VERTEX_SHADER; break;
		case ZEShader.SHADER_TYPE_FRAGMENT: glsType = GL20.GL_FRAGMENT_SHADER; break;
		case ZEShader.SHADER_TYPE_GEOMETRY: glsType = GL32.GL_GEOMETRY_SHADER; break;
		case ZEShader.SHADER_TYPE_TESSELLATION_CONTROL: glsType = GL40.GL_TESS_CONTROL_SHADER; break;
		case ZEShader.SHADER_TYPE_TESSELLATION_EVALUATION: glsType = GL40.GL_TESS_EVALUATION_SHADER; break;
		}
		if (SHADER_SOURCE_TYPE_GLSL.equals(shaderType)) {
			context.bindExclusively();
			try {
				int shaderId = GL20.glCreateShader(glsType);
				backend.checkErrorFine();
				GL20.glShaderSource(shaderId, src.toString());
				backend.checkErrorFine();
				GL20.glCompileShader(shaderId);
				backend.checkErrorFine();
				int shaderstatus = GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS);
				backend.checkErrorFine();
				if (shaderstatus != GL11.GL_TRUE) {
					String log = GL20.glGetShaderInfoLog(shaderId);
					backend.checkErrorFine();
					throw new GLException("Failed to compile GLSL shader:\n" + log);
				}
				backend.checkErrorCoarse("Failed to compile shader");
				return new GLShader(shaderId, glsType);
			} finally {
				context.unbindExclusively();
			}
		} else if (SHADER_SOURCE_TYPE_SPIRV.equals(shaderType) && backend.getCapabilities().GL_ARB_gl_spirv) {
			ByteBuffer binary = loadSPIRV(src);
			context.bindExclusively();
			try (MemoryStack sp = MemoryStack.stackPush()) {
				int shaderId = GL20.glCreateShader(glsType);
				backend.checkErrorFine();
				GL41.glShaderBinary(new int[] { shaderId }, ARBGLSPIRV.GL_SHADER_BINARY_FORMAT_SPIR_V_ARB, binary);
				backend.checkErrorFine();
				ARBGLSPIRV.glSpecializeShaderARB(shaderId, "main", sp.ints(0), sp.ints(0));
				backend.checkErrorFine();
				backend.checkErrorCoarse("Failed to load SPIR-V shader");
				return new GLShader(shaderId, glsType);
			} finally {
				context.unbindExclusively();
				LibCStdlib.free(binary);
			}
		}
		return null;
	}

	@Override
	public ZEShaderProgram compileShaderProgram(ZEShader... modules) {
		int[] shaders = new int[modules.length];
		for(int i = 0; i < shaders.length; i++) {
			GLShader gls = (GLShader)modules[i];
			shaders[i] = gls.shaderObject;
		}
		context.bindExclusively();
		try {
			int prgm = GL20.glCreateProgram();
			backend.checkErrorFine();
			for(int shader : shaders) {
				GL20.glAttachShader(prgm, shader);
				backend.checkErrorFine();
			}
			
			GL20.glLinkProgram(prgm);
			backend.checkErrorFine();
			int pgmstatus = GL20.glGetProgrami(prgm, GL20.GL_LINK_STATUS);
			backend.checkErrorFine();
			if (pgmstatus != GL11.GL_TRUE) {
				String log = GL20.glGetShaderInfoLog(prgm);
				backend.checkErrorFine();
				throw new GLException("Failed to link GLSL program:\n" + log);
			}
			
			List<GLAttribute> attribs = new ArrayList<>();
			List<GLUniform> uniforms = new ArrayList<>();
			// Use GL_ARB_program_interface_query if we need more info, non-sequential binding numbers?
			/*
			if (backend.getExtensions().programInterfaceQuery) {
				int numAttribs = GL43.glGetProgramInterfacei(prgm, GL43.GL_PROGRAM_INPUT, GL43.GL_ACTIVE_RESOURCES);
				int numUniforms = GL43.glGetProgramInterfacei(prgm, GL43.GL_UNIFORM, GL43.GL_ACTIVE_RESOURCES);
				for(int i = 0; i < numAttribs; i++) {
					GL43.glGetProgramResourceiv(prgm, GL43.GL_PROGRAM_INPUT, i, new int[] {}, new int[] {}, new int[] {});
				}
			} else */{
				int maxAttribLength = GL20.glGetProgrami(prgm, GL20.GL_ACTIVE_ATTRIBUTE_MAX_LENGTH);
				int numAttribs = GL20.glGetProgrami(prgm, GL20.GL_ACTIVE_ATTRIBUTES);
				try(MemoryStack sp = MemoryStack.stackPush()) {
					IntBuffer pSize = sp.mallocInt(1);
					IntBuffer pType = sp.mallocInt(1);
					for(int i = 0; i < numAttribs; i++) {
						String name = GL20.glGetActiveAttrib(prgm, i, maxAttribLength, pSize, pType);
						attribs.add(new GLAttribute(name, i));
					}
				}
				
				int maxUniformLength = GL20.glGetProgrami(prgm, GL20.GL_ACTIVE_UNIFORM_MAX_LENGTH);
				int numUniforms = GL20.glGetProgrami(prgm, GL20.GL_ACTIVE_UNIFORMS);
				try(MemoryStack sp = MemoryStack.stackPush()) {
					IntBuffer pSize = sp.mallocInt(1);
					IntBuffer pType = sp.mallocInt(1);
					for(int i = 0; i < numUniforms; i++) {
						String name = GL20.glGetActiveUniform(prgm, i, maxUniformLength, pSize, pType);
						uniforms.add(new GLUniform(name, i));
					}
				}
			}
			
			backend.checkErrorCoarse("Failed to compile shader program");
			return new GLShaderProgram(prgm, attribs, uniforms);
		} finally {
			context.unbindExclusively();
		}
	}

	@Override
	public void deleteShader(ZEShader shader) {
		backend.addReleasable(() -> GL20.glDeleteShader(((GLShader)shader).shaderObject));
	}

	@Override
	public void deleteShaderProgram(ZEShaderProgram program) {
		backend.addReleasable(() -> GL20.glDeleteProgram(((GLShaderProgram)program).programObject));
	}

}
