package com.zekrom_64.ze.gl.cmd;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.bcel.Const;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.FieldGen;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.Type;

import com.zekrom_64.mathlib.shape.Rectangle;
import com.zekrom_64.mathlib.tuple.impl.Vector3Int;
import com.zekrom_64.ze.base.backend.render.ZEBuffer;
import com.zekrom_64.ze.base.backend.render.ZEFramebuffer;
import com.zekrom_64.ze.base.backend.render.ZERenderCommandBuffer;
import com.zekrom_64.ze.base.backend.render.ZERenderEvent;
import com.zekrom_64.ze.base.backend.render.ZERenderWorkRecorder;
import com.zekrom_64.ze.base.backend.render.ZETexture;
import com.zekrom_64.ze.base.backend.render.ZETexture.ZETextureLayout;
import com.zekrom_64.ze.base.backend.render.input.ZEIndexBuffer;
import com.zekrom_64.ze.base.backend.render.input.ZEVertexBuffer;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipeline;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipeline.ZEVertexInput;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBindSet;
import com.zekrom_64.ze.base.image.ZEPixelFormat;
import com.zekrom_64.ze.gl.GLException;
import com.zekrom_64.ze.gl.GLRenderBackend;

public class GLCommandBufferCompiler extends ClassLoader implements ZERenderWorkRecorder {

	private static volatile int compilerNameVar = 0;
	private static Map<JavaClass,Runnable> compilationCache = new HashMap<>();
	
	public final GLRenderBackend backend;
	private ArrayList<Object> constObjects = new ArrayList<>();
	private ClassGen classGenerator = new ClassGen(
			"com/zekrom_64/ze/gl/cmd/runtime/CMDGEN",
			null,
			"",
			Const.ACC_PUBLIC | Const.ACC_FINAL,
			new String[] { "java/lang/Runnable" });
	
	private ConstantPoolGen constPoolGen;
	private InstructionList insnList = new InstructionList();
	
	public GLCommandBufferCompiler(GLRenderBackend backend) {
		this.backend = backend;
		constPoolGen = classGenerator.getConstantPool();
		
		FieldGen fConstObjs = new FieldGen(Const.ACC_PUBLIC, Type.getType(Object[].class), "constObjs", constPoolGen);
		classGenerator.addField(fConstObjs.getField());
	}
	
	@Override
	public void inlineWork(Runnable r) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeBuffer(ZERenderCommandBuffer buffer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void bindPipeline(ZEPipeline pipeline) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beginPass(ZEPipelineBindSet bindSet, ZEFramebuffer framebuffer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void endPass() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setScissor(Rectangle[] scissors, int firstScissor, int numScissors) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLineWidth(float width) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDepthBounds(double min, double max) {
		// TODO Auto-generated method stub

	}

	@Override
	public void bindVertexBuffer(ZEVertexInput bindPoint, ZEVertexBuffer buffer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void bindIndexBuffer(ZEIndexBuffer buffer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(int nVertices, int start) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawIndexed(int nIndices, int startIndex, int startVertex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawIndirect(ZEBuffer paramBuffer, int offset, int drawCount, int stride) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawIndexedIndirect(ZEBuffer paramBuffer, int offset, int drawCount, int stride) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEvent(ZERenderEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetEvent(ZERenderEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void blitBuffer(ZEBuffer src, ZEBuffer dst, int srcPos, int dstPos, int size) {
		// TODO Auto-generated method stub

	}

	@Override
	public void blitTexture(ZETexture srcTex, ZETexture dstTex, Vector3Int src, Vector3Int dst, Vector3Int size) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearBuffer(ZEBuffer buf, int offset, int count, ByteBuffer value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearTexture(ZETexture tex, Vector3Int start, Vector3Int size, ZEPixelFormat format, ByteBuffer color) {
		// TODO Auto-generated method stub

	}

	@Override
	public void imageToBuffer(ZETexture src, Vector3Int srcPos, Vector3Int srcSize, ZEBuffer dst, int dstOffset,
			int dstRowLength, int dstHeight) {
		// TODO Auto-generated method stub

	}

	@Override
	public void bufferToImage(ZEBuffer src, int srcOffset, int srcRowLength, int srcHeight, ZETexture dst,
			Vector3Int dstPos, Vector3Int dstSize) {
		// TODO Auto-generated method stub

	}

	@Override
	public void uploadToBuffer(ZEBuffer dst, int dstoff, int len, ByteBuffer data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void transitionTextureLayout(ZETexture tex, ZETextureLayout oldLayout, ZETextureLayout newLayout) {
		// TODO Auto-generated method stub

	}
	
	private Method lastMethodGen = null;
	
	public synchronized Runnable compile() {
		// Build method
		if (lastMethodGen != null) classGenerator.removeMethod(lastMethodGen);
		MethodGen mRun = new MethodGen(Const.ACC_PUBLIC, Type.VOID, new Type[0], new String[0], "run", null, insnList, constPoolGen);
		classGenerator.addMethod(lastMethodGen = mRun.getMethod());
		
		// Generate class, attempt to find class in cache
		JavaClass jclazz = classGenerator.getJavaClass();
		Runnable run = compilationCache.get(jclazz);
		if (run != null) return run;
		
		// Build the class
		jclazz = jclazz.copy();
		jclazz.setClassName("com/zekrom_64/ze/gl/cmd/runtime/CMDGEN" + compilerNameVar++);
		byte[] bytes = jclazz.getBytes();
		
		try {
			// Define and instantiate class
			Class<?> clazz = defineClass(jclazz.getClassName(), bytes, 0, bytes.length);
			run = (Runnable) clazz.newInstance();
			// Set constObjs array
			Field fieldConstObjs = clazz.getField("constObjs");
			fieldConstObjs.set(run, constObjects.toArray(new Object[0]));
		} catch (Exception e) {
			throw new GLException("Failed to dynamically compile command buffer", e);
		}
		return run;
	}

}
