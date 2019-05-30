package com.zekrom_64.ze.glfw;

import java.nio.ByteBuffer;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWNativeGLX;
import org.lwjgl.glfw.GLFWNativeNSGL;
import org.lwjgl.glfw.GLFWNativeWGL;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.Platform;
import org.lwjgl.system.macosx.ObjCRuntime;

import com.zekrom_64.ze.base.image.ZEImage;
import com.zekrom_64.ze.base.image.ZEImageLoader;
import com.zekrom_64.ze.base.image.ZEPixelFormat;
import com.zekrom_64.ze.nat.ZEStructUtils;

public class GLFWU {
	
	/** Sets the window icon, if allowed by the platform, for a window. The icon
	 * image must be in ARGB format.
	 * 
	 * @param window GLFW window
	 * @param icon Icon image
	 * @deprecated Functions built into GLFW 3.2 and the GLFWWindow class
	 */
	@Deprecated()
	public static void glfwuSetWindowIcon(long window, GLFWImage icon) {
		GLFWImage.Buffer buf = ZEStructUtils.createStructBuffer(1, GLFWImage.class);
		buf.put(0, icon);
		GLFW.glfwSetWindowIcon(window, buf);
		/*
		int size = icon.width() * icon.height() * 4;
		switch(Platform.get()) {
		case WINDOWS:
			// Get the HWND of the GLFW window
			Pointer<HWND> hWnd = (Pointer<HWND>) Pointer.pointerToAddress(GLFWNativeWin32.glfwGetWin32Window(window));
			// Create new big and small icons for the window
			// Note: Big and Small icons don't necessarily have size limits outside of windows icon sizes,
			//	instead it determines where the icon is used, with small being displayed in the upper left
			//	corner and big being used in alt-tab
			Pointer<HICON> hIconBig = WindowsUtil.iconFromARGB(icon.pixels(size), icon.width(), icon.height());
			Pointer<HICON> hIconSmall = WindowsUtil.iconFromARGB(icon.pixels(size), icon.width(), icon.height());
			// Temporary pointers for the WParam part of SendMessage
			Pointer<?> wBigIcon = Pointer.pointerToAddress(User32.ICON_BIG);
			Pointer<?> wSmallIcon = Pointer.pointerToAddress(User32.ICON_SMALL);
			// Call SendMessage to set the new window icons
			Pointer<HICON> hIconBigPrev = (Pointer<HICON>) Pointer.pointerToAddress(User32.SendMessage(hWnd, User32.WM_SETICON, wBigIcon, hIconBig));
			Pointer<HICON> hIconSmallPrev = (Pointer<HICON>) Pointer.pointerToAddress(User32.SendMessage(hWnd, User32.ICON_SMALL, wSmallIcon, hIconSmall));
			// Release any old icons used for the window. This assumes the window didn't have icons changed natively!
			if (!Pointer.NULL.equals(hIconBigPrev)) User32.DestroyIcon(hIconBigPrev);
			if (!Pointer.NULL.equals(hIconSmallPrev)) User32.DestroyIcon(hIconSmallPrev);
			break;
		case LINUX:
			CLong xwindow = new CLong(GLFWNativeX11.glfwGetX11Window(window));
			Pointer<Display> xdisplay = (Pointer<Display>) Pointer.pointerToAddress(GLFWNativeX11.glfwGetX11Display());
			CLong net_wm_icon = X11.XInternAtom(xdisplay, "_NET_WM_ICON", X11.False);
			CLong cardinal = X11.XInternAtom(xdisplay, "CARDINAL", X11.False);
			
			ByteBuffer buffer = BufferUtils.createByteBuffer(size + 8);
			buffer.putInt(icon.width()).putInt(icon.height()).put(icon.pixels(size));
			buffer.rewind();
			Pointer<Byte> pbuffer = (Pointer<Byte>) Pointer.pointerToBuffer(buffer);
			X11.XChangeProperty(xdisplay, xwindow, net_wm_icon, cardinal, 32, X11.PropModeReplace, pbuffer, buffer.capacity());
			pbuffer.release();
			break;
		case MACOSX:
		}
		*/
	}
	
	/*
	private static GLFWImage[] sortImagesBySize(GLFWImage[] imgs) {
		ArrayList<GLFWImage> list = new ArrayList<>();
		for(GLFWImage i : imgs) if (i!=null) list.add(i);
		Collections.sort(list, new Comparator<GLFWImage>() {

			@Override
			public int compare(GLFWImage arg0, GLFWImage arg1) {
				int size0 = arg0.width() * arg0.height();
				int size1 = arg1.width() * arg1.height();
				return Integer.compare(size0, size1);
			}
			
		});
		return (GLFWImage[]) list.toArray();
	}
	*/
	
	/** Sets the possible window icons, if allowed by the platform, for a window. The icon
	 * images must be in ARGB format.
	 * 
	 * @param window GLFW window
	 * @param icons Icon images
	 * 
	 * @deprecated Functions built into GLFW 3.2 and the GLFWWindow class
	 */
	@Deprecated
	public static void glfwuSetWindowIcons(long window, GLFWImage[] icons) {
		if (icons.length==0) return;
		GLFWImage.Buffer buf = ZEStructUtils.createStructBuffer(icons.length, GLFWImage.class);
		for(int i = 0; i < icons.length; i++) buf.put(i, icons[i]);
		GLFW.glfwSetWindowIcon(window, buf);
		/*
		switch(Platform.get()) {
		case WINDOWS:
			GLFWImage[] sorted = sortImagesBySize(icons);
			GLFWImage bigImage = sorted[sorted.length-1];
			GLFWImage smallImage = sorted.length>1 ? sorted[sorted.length-2] : bigImage;
			Pointer<HWND> hWnd = (Pointer<HWND>) Pointer.pointerToAddress(GLFWNativeWin32.glfwGetWin32Window(window));
			int width = bigImage.width(), height = bigImage.height();
			Pointer<HICON> hIconBig = WindowsUtil.iconFromARGB(bigImage.pixels(width*height*4), width, height);
			width = smallImage.width(); height = smallImage.height();
			Pointer<HICON> hIconSmall = WindowsUtil.iconFromARGB(smallImage.pixels(width*height*4), width, height);
			Pointer<?> wBigIcon = Pointer.pointerToAddress(User32.ICON_BIG);
			Pointer<?> wSmallIcon = Pointer.pointerToAddress(User32.ICON_SMALL);
			Pointer<HICON> hIconBigPrev = (Pointer<HICON>) Pointer.pointerToAddress(User32.SendMessage(hWnd, User32.WM_SETICON, wBigIcon, hIconBig));
			Pointer<HICON> hIconSmallPrev = (Pointer<HICON>) Pointer.pointerToAddress(User32.SendMessage(hWnd, User32.ICON_SMALL, wSmallIcon, hIconSmall));
			if (!Pointer.NULL.equals(hIconBigPrev)) User32.DestroyIcon(hIconBigPrev);
			if (!Pointer.NULL.equals(hIconSmallPrev)) User32.DestroyIcon(hIconSmallPrev);
			break;
		case LINUX:
			CLong xwindow = new CLong(GLFWNativeX11.glfwGetX11Window(window));
			Pointer<Display> xdisplay = (Pointer<Display>) Pointer.pointerToAddress(GLFWNativeX11.glfwGetX11Display());
			CLong net_wm_icon = X11.XInternAtom(xdisplay, "_NET_WM_ICON", X11.False);
			CLong cardinal = X11.XInternAtom(xdisplay, "CARDINAL", X11.False);
			
			int size = 0;
			for (GLFWImage icon : icons) size += (icon.width() * icon.height() * 4) + 8;
			ByteBuffer buffer = BufferUtils.createByteBuffer(size);
			for (GLFWImage icon : icons) buffer.putInt(icon.width()).putInt(icon.height()).put(icon.pixels(size));
			buffer.rewind();
			Pointer<Byte> pbuffer = (Pointer<Byte>) Pointer.pointerToBuffer(buffer);
			X11.XChangeProperty(xdisplay, xwindow, net_wm_icon, cardinal, 32, X11.PropModeReplace, pbuffer, buffer.capacity());
			pbuffer.release();
			break;
		case MACOSX:
		}
		*/
	}
	
	/** Gets the platform-specific context for a GLFW window
	 * 
	 * @param glfwwindow The glfw window
	 * @return The platform specific context
	 */
	public static long glfwGetPlatformContext(long glfwwindow) {
		switch(Platform.get()) {
		case WINDOWS: return GLFWNativeWGL.glfwGetWGLContext(glfwwindow);
		case MACOSX: { // Damn you OSX and your weird OpenGL interface
			long nsOpenglContext = GLFWNativeNSGL.glfwGetNSGLContext(glfwwindow);
			try(MemoryStack sp = MemoryStack.stackPush()) {
				PointerBuffer pContext = sp.mallocPointer(1);
				ObjCRuntime.object_getInstanceVariable(nsOpenglContext, "cglContextObj", pContext);
				return pContext.get();
			}
			
		}
		case LINUX: return GLFWNativeGLX.glfwGetGLXContext(glfwwindow);
		}
		return 0;
	}
	
	/** Converts a ZEImage to a GLFWImage.
	 * 
	 * @param image ZEImage
	 * @return GLFWImage
	 */
	public static GLFWImage toGLFWImage(ZEImage image) {
		GLFWImage img = GLFWImage.create();
		img.width(image.width);
		img.height(image.height);
		ByteBuffer pixels;
		switch(image.format) {
		case A8R8G8B8_UINT: pixels = image.buffer; break;
		case R8G8B8A8_UINT:
			pixels = image.buffer;
			ZEImageLoader.argbToRgbaNoCopy(pixels);
			break;
		default: pixels = image.transcode(ZEPixelFormat.A8R8G8B8_UINT).buffer;
		}
		img.pixels(pixels);
		return img;
	}
	
}
