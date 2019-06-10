package com.zekrom_64.ze.gl.objects;

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
import com.zekrom_64.mathlib.tuple.impl.Vector3I;
import com.zekrom_64.ze.base.backend.render.ZERenderCommandBuffer;
import com.zekrom_64.ze.base.backend.render.ZERenderWorkRecorder;
import com.zekrom_64.ze.base.backend.render.obj.ZEBuffer;
import com.zekrom_64.ze.base.backend.render.obj.ZEColorClearValue;
import com.zekrom_64.ze.base.backend.render.obj.ZEFramebuffer;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderEvent;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderPass;
import com.zekrom_64.ze.base.backend.render.obj.ZESampler.ZEFilter;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture.ZETextureLayer;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture.ZETextureLayout;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture.ZETextureRange;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEFrontBack;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipeline;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBindSet;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder.ZEViewport;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineStage;
import com.zekrom_64.ze.base.util.ZEPrimitiveType;
import com.zekrom_64.ze.gl.GLException;
import com.zekrom_64.ze.gl.GLRenderBackend;

/** Compiler for converting a series of render work commands into
 * java bytecode.
 * 
 * @author Zekrom_64
 * @deprecated Incomplete and experimental, do not use yet
 */
@Deprecated
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
	
	private class GLCommandBufferCompiled extends GLCommandBuffer {

		private final Runnable commands;
		
		public GLCommandBufferCompiled(GLRenderBackend backend, Runnable commands) {
			super(backend, false);
			this.commands = commands;
		}

		@Override
		public ZERenderWorkRecorder beginRecording() {
			return null;
		}

		@Override
		public ZERenderWorkRecorder beginRecording(ZERenderCommandBuffer parent) {
			return null;
		}

		@Override
		public void endRecording() { }

		@Override
		public void executeCommands() {
			commands.run();
		}
		
	}
	
	private Method lastMethodGen = null;
	
	/** Compiles the current list of commands into a usable command buffer.
	 * 
	 * @return
	 */
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

	@Override
	public void executeBuffer(ZERenderCommandBuffer buffer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void bindPipeline(ZEPipeline pipeline) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beginPass(ZERenderPass renderPass, ZEFramebuffer framebuffer, ZEAttachmentClearValue[] clearVals) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void nextPass() {
		
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
	public void bindVertexBuffers(int baseBindPoint, ZEBuffer ... buffers) {
		
	}

	@Override
	public void bindIndexBuffer(ZEBuffer buffer, ZEPrimitiveType indexType) {
		// TODO Auto-generated method stub

	}
	
	public void bindPipelineBindSet(ZEPipelineBindSet bindSet) {
		
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
	public void setEvent(ZEPipelineStage stage, ZERenderEvent event) {
		// TODO Auto-generated method stub
	}

	@Override
	public void resetEvent(ZEPipelineStage stage, ZERenderEvent event) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void waitForEvents(ZEPipelineStage[] src, ZEPipelineStage[] dst, ZERenderEvent ... events) {
		
	}

	@Override
	public void blitBuffer(ZEBuffer src, ZEBuffer dst, long srcPos, long dstPos, long size) {
		// TODO Auto-generated method stub

	}

	@Override
	public void blitTexture(ZETexture srcTex, ZETextureLayer srcLayer, ZETextureLayout srcLayout, Vector3I srcPos,Vector3I srcSize,
			ZETexture dstTex, ZETextureLayer dstLayer, ZETextureLayout dstLayout, Vector3I dstPos, Vector3I dstSize,
			ZEFilter filter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearBuffer(ZEBuffer buf, int offset, int count, ByteBuffer value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearTexture(ZETexture tex, Vector3I start, Vector3I size, ZETextureRange range, ZEColorClearValue color) {
		// TODO Auto-generated method stub

	}

	@Override
	public void imageToBuffer(ZETexture src, Vector3I srcPos, Vector3I srcSize, ZETextureLayer range, ZEBuffer dst, int dstOffset,
			int dstRowLength, int dstHeight) {
		// TODO Auto-generated method stub

	}

	@Override
	public void bufferToImage(ZEBuffer src, int srcOffset, int srcRowLength, int srcHeight, ZETexture dst,
			Vector3I dstPos, Vector3I dstSize, ZETextureLayer range) {
		// TODO Auto-generated method stub

	}

	@Override
	public void uploadToBuffer(ZEBuffer dst, int dstoff, int len, ByteBuffer data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void transitionTextureLayout(ZETexture tex, ZETextureLayout oldLayout, ZETextureLayout newLayout) {
		// OpenGL doesn't really have texture layouts
	}

	@Override
	public void setViewport(ZEViewport[] viewports, int firstViewport) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDepthBias(double constantFactor, double clamp, double slopeFactor) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setBlendConstants(float... constants) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStencilCompareMask(ZEFrontBack face, int compareMask) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStencilWriteMask(ZEFrontBack face, int writeMask) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStencilReference(ZEFrontBack face, int reference) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void waitForEvents(ZEPipelineStage[] readyStages, ZEPipelineStage[] waitingStages, ZERenderEvent[] events,
			ZEPipelineBarrier... barriers) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pipelineBarrier(ZEPipelineStage[] readyStages, ZEPipelineStage[] waitingStages,
			ZEPipelineBarrier... barriers) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateMipmaps(ZETexture tex) {
		// TODO Auto-generated method stub
		
	}

}
