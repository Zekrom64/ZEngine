package com.zekrom_64.ze.nat.win;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.ann.Library;

import com.zekrom_64.ze.nat.win.WinTypes.HDC;
import com.zekrom_64.ze.nat.win.WinTypes.HICON;
import com.zekrom_64.ze.nat.win.WinTypes.HWND;
import com.zekrom_64.ze.nat.win.structs.ICONINFO;

@Library("User32")
public class User32 {

	static {
		BridJ.register();
	}
	
	/** <p>Used to define private messages for use by private window classes, usually of the form <b>WM_USER</b>+x, where x is an
	 * integer value.</p>
	 * 
	 * <p>The following are the ranges of message numbers.</p>
	 * 
	 * <style>
	 * table {
	 * border-collapse: collapse;
	 * margin: 5px;
	 * }
	 * td {
	 * padding: 5px;
	 * }
	 * </style>
	 * 
	 * <table border="1">
	 * <tr><b>
	 * <td>Range</td><td>Meaning</td>
	 * </b></tr><tr>
	 * <td>0 through <b>WM_USER</b>-1</td><td>Messages reserved for use by the system.</td>
	 * </tr><tr>
	 * <td><b>WM_USER</b> through 0x7FFF</td><td>Integer messages for use by private window classes</td>
	 * </tr><tr>
	 * <td>{@link #WM_APP} (0x8000) through 0xBFFF</td><td>Messages available for use by applications.</td>
	 * </tr><tr>
	 * <td>0xC000 through 0xFFFF</td><td>String messages for use by applications.</td>
	 * </tr><tr>
	 * <td>Greater than 0xFFFF</td><td>Reserved by the system.</td>
	 * </tr>
	 * </table>
	 * 
	 * <p>Message numbers in the first range (0 through <b>WM_USER</b> –1) are defined by the system. Values in this range that are not
	 * explicitly defined are reserved by the system.</p>
	 * 
	 * <p>Message numbers in the second range (<b>WM_USER</b> through 0x7FFF) can be defined and used by an application to send messages
	 * within a private window class. These values cannot be used to define messages that are meaningful throughout an application because
	 * some predefined window classes already define values in this range. For example, predefined control classes such as <b>BUTTON</b>,
	 * <b>EDIT</b>, <b>LISTBOX</b>, and <b>COMBOBOX</b> may use these values. Messages in this range should not be sent to other applications
	 * unless the applications have been designed to exchange messages and to attach the same meaning to the message numbers.</p>
	 * 
	 * <p>Message numbers in the third range (0x8000 through 0xBFFF) are available for applications to use as private messages. Messages in
	 * this range do not conflict with system messages.</p>
	 * 
	 * <p>Message numbers in the fourth range (0xC000 through 0xFFFF) are defined at run time when an application calls the 
	 * {@link #RegisterWindowMessage} function to retrieve a message number for a string. All applications that register the same string can
	 * use the associated message number for exchanging messages. The actual message number, however, is not a constant and cannot be assumed
	 * to be the same between different sessions.</p>
	 * 
	 * <p>Message numbers in the fifth range (greater than 0xFFFF) are reserved by the system.</p>
	 */
	public static final int WM_USER = 0x400;
	
	/** <p>Used to define private messages, usually of the form <b>WM_APP</b>+x, where x is an integer value.</p>
	 * 
	 * <p>The <b>WM_APP</b> constant is used to distinguish between message values that are reserved for use by the system and
	 * values that can be used by an application to send messages within a private window class. The following are the ranges
	 * of message numbers available</p>
	 * 
	 * <style>
	 * table {
	 * border-collapse: collapse;
	 * margin: 5px;
	 * }
	 * td {
	 * padding: 5px;
	 * }
	 * </style>
	 * 
	 * <table border="1">
	 * <tr><b>
	 * <td>Range</td><td>Meaning</td>
	 * </b></tr><tr>
	 * <td>0 through {@link #WM_USER}-1</td><td>Messages reserved for use by the system.</td>
	 * </tr><tr>
	 * <td>{@link #WM_USER} through 0x7FFF</td><td>Integer messages for use by private window classes</td>
	 * </tr><tr>
	 * <td><b>WM_APP</b> (0x8000) through 0xBFFF</td><td>Messages available for use by applications.</td>
	 * </tr><tr>
	 * <td>0xC000 through 0xFFFF</td><td>String messages for use by applications.</td>
	 * </tr><tr>
	 * <td>Greater than 0xFFFF</td><td>Reserved by the system.</td>
	 * </tr>
	 * </table>
	 * 
	 * <p>Message numbers in the first range (0 through {@link #WM_USER}-1) are defined by the system. Vaues in this range that are not
	 * explicitly defined are reserved by the system.</p>
	 * 
	 * <p>Message numbers in the second range ({@link #WM_USER} through 0x7FFF) can be defined and used by an application to send messages
	 * within a private window class. These values cannot be used to define messages that are meaningful throughout an application because
	 * some predefined window classes already define values in this range. For example, predefined control classes such as <b>BUTTON</b>,
	 * <b>EDIT</b>, <b>LISTBOX</b>, and <b>COMBOBOX</b> may use these values. Messages in this range should not be sent to other applications
	 * unless the applications have been designed to exchange messages and to attach the same meaning to the message numbers.</p>
	 * 
	 * <p>Message numbers in the third range (0x8000 through 0xBFFF) are available for applications to use as private messages. Messages in
	 * this range do not conflict with system messages.</p>
	 * 
	 * <p>Message numbers in the fourth range (0xC000 through 0xFFFF) are defined at run time when an application calls the 
	 * {@link #RegisterWindowMessage} function to retrieve a message number for a string. All applications that register the same string can
	 * use the associated message number for exchanging messages. The actual message number, however, is not a constant and cannot be assumed
	 * to be the same between different sessions.</p>
	 * 
	 * <p>Message numbers in the fifth range (greater than 0xFFFF) are reserved by the system.</p>
	 */
	public static final int WM_APP = 0x8000;
	
	/** Associates a new large or small icon with a window. The system displays the large icon in the ALT + TAB dialog box, and the small icon
	 * in the window caption.
	 * 
	 * <style>
	 * table {
	 * border-collapse: collapse;
	 * margin: 5px;
	 * }
	 * td {
	 * padding: 5px;
	 * }
	 * </style>
	 * 
	 * <h3>Parameters</h3>
	 * <i>wParam</i>
	 * <blockquote>
	 * <p>The type of icon to be set. This parameter can be one of the following values.</p>
	 * <table border="1">
	 * <tr>
	 * <td><b>Value</b></td><td><b>Meaning</b></td>
	 * </tr><tr>
	 * <td><b>ICON_BIG</b><br/>1</td><td>Set the large icon for the window.</td>
	 * </tr><tr>
	 * <td><b>ICON_SMALL</b><br/>0</td><td>Set the small icon for the window.</td>
	 * </tr>
	 * </table>
	 * </blockquote>
	 * <br/>
	 * <i>lParam</i>
	 * <blockquote>
	 * A handle to the new large or small icon. If this parameter is <b>NULL</b>, the icon indicated by <i>wParam</i> is removed.
	 * </blockquote>
	 * <br/>
	 * <h3>Return value</h3>
	 * The return value is a handle to the previous large or small icon, depending on the value of <i>wParam</i>. It is <b>NULL</b> if the
	 * window previously had no icon of the type indicated by <i>wParam</i>.
	 */
	public static final int WM_SETICON = 0x80;
	
	/** <p>Sent to a window to retrieve a handle to the large or small icon associated with a window. The system displays the large icon in the
	 * ALT + TAB dialog, and the small icon in the window caption.</p>
	 * 
	 * <p>A window receives this message through its <b>WindowProc</b> function.</p>
	 * 
	 * <style>
	 * table {
	 * border-collapse: collapse;
	 * margin: 5px;
	 * }
	 * td {
	 * padding: 5px;
	 * }
	 * </style>
	 * 
	 * <h3>Parameters</h3>
	 * <i>wParam</i>
	 * <blockquote>
	 * <p>The type of icon being retrieved. This parameter can be one of the following values.</p>
	 * <table border="1">
	 * <tr><td><b>Value</b></td><td><b>Meaning</b></td></tr>
	 * <tr>
	 * <td><b>ICON_BIG</b><br/>1</td><td>Retrieve the large icon for the window.</td>
	 * </tr><tr>
	 * <td><b>ICON_SMALL</b><br/>0</td><td>Retrieve the small icon for the window.</td>
	 * </tr><tr>
	 * <td><b>ICON_SMALL2</b><br/>2</td><td>Retrieves the small icon provided by the application. If the application does not provide one, the
	 * system uses the system-generated icon for that window.</td>
	 * </tr>
	 * </table>
	 * </blockquote>
	 * <br/>
	 * <i>lParam</i>
	 * <blockquote>
	 * The DPI of the icon being retrieved. This can be used to provide different icons depending on the icon size.
	 * </blockquote>
	 * <br/>
	 * <h3>Return value</h3>
	 * The return value is a handle to the large or small icon, depending on the value of <i>wParam</i>. When an application receives
	 * this message, it can return a handle to a large or small icon, or pass the message to the <b>DefWIndowProc</b> function.
	 */
	public static final int WM_GETICON = 0x7F;
	
	/** Icon size 
	 * @see #WM_SETICON
	 * @see #WM_GETICON
	 */
	public static final int ICON_BIG = 1, ICON_SMALL = 0;
	
	public static native boolean DeleteDC(Pointer<HDC> hdc);
	
	/** <p>Creates an icon or cursor from an {@link ICONINFO} structure.</p>
	 * 
	 * <p>The system copies the bitmaps in the {@link ICONINFO} structure before creating the icon or cursor. Because the system may
	 * temporarily select the bitmaps in a device context, the <b>hbmMask</b> and <b>hbmColor</b> members of the <b>ICONINFO</b>
	 * structure should not already be selected into a device context. The application must continue to manage the original bitmaps
	 * and delete them when they are no longer necessary.</p>
	 * 
	 * <p>When you are finished using the icon, destroy it using the {@link DestroyIcon} function.</p>
	 * 
	 * @param piconinfo A pointer to an {@link ICONINFO} structure the function uses to create the icon or cursor.
	 * @return If the function succeeds, the return value is a handle to the icon or cursor that is created.
	 * 		<p>If the function fails, the return value is <b>NULL</b>. To get gextended error information, call
	 * 		{@link Kernel32#GetLastError GetLastError}.</p>
	 */
	public static native Pointer<HICON> CreateIconIndirect(Pointer<ICONINFO> piconinfo);
	
	/** <p>Destroys an icon and frees any memory the icon occupied.</p>
	 * 
	 * <p>It is only necessary to call <b>DestroyIcon</b> for icons and cursors created with the following functions:
	 * {@link #CreateIconFromResourceEx} (if called without the <b>LR_SHARED</b> flag), {@link #CreateIconIndirect}, and
	 * {@link #CopyIcon}. Do not use this function to destroy a shared icon. A shared icon is valid as long as the module from
	 * which it was loaded remains in memory. The following functions obtain a shared icon.</p>
	 * <ul>
	 * <li>{@link #LoadIcon}</li>
	 * <li>{@link #LoadImage} (if you use the <b>LR_SHARED</b> flag)</li>
	 * <li>{@link #CopyImage} (if you use the <b>LR_COPYRETURNORG</b> flag and the <i>hImage</i> parameter is a shared icon)</li>
	 * <li>{@link CreateIconFromResource}</li>
	 * <li>{@link CreateIconFromResourceEx} (if you use the <b>LR_SHARED</b> flag)</li>
	 * </ul>
	 * 
	 * @param hIcon A handle to the icon to be destroyed, THe icon must not be in use.
	 * @return If the function succeeds, the return value is nonszero.
	 * 		<p>If the function fails, the return value is zero. To get extended information, call {@link Kernel32#GetLastError GetLastError}.
	 * 		</p>
	 */
	public static native boolean DestroyIcon(Pointer<HICON> hIcon);
	
	/** <p>Sends the specified message to a window or windows. The <b>SendMessage</b> function calls the window procedure for the
	 * specified window and does not return until the window procedure has processed the message.</p>
	 * 
	 * <p>To send a message and return immediately, use the {@link #SendMessageCallback} or {@link #SendNotifyMessage} function. To
	 * post a message to a thread's message queue and return immediately, use the {@link #PostMessage} or {@link #PostThreadMessage}
	 * function.</p>
	 * 
	 * <p>When a message is blocked by the UIPI the last error retrieved with {@link Kernel32#GetLastError GetLastError}, is set
	 * to 5 (access denied).</p>
	 * 
	 * <p>Applications that need to communicate using <b>HWND_BROADCAST</b> should use the {@link #RegisterWindowMessage} function to
	 * obtain a unique message for inter-application communication.</p>
	 * 
	 * <p>The system only does marshalling for system messages (those in the range 0 to (<b>WM_USER</b>-1)). To send other messages
	 * (those >= <b>WM_USER</b>) to another process, you must do the custom marshalling.</p>
	 * 
	 * <p>If the specified window was created by the calling thread, the window procedure is called immediately as a subroutine. If the
	 * specified window was created by a different thread, the system switches to that thread and calls the appropriate window procedure.
	 * Messages sent between threads are processed only when the receiving thread executes message retrieval code. The sending thread is
	 * blocked until the receiving thread processes the message. However, the sending thread will process incoming nonqueued messages while
	 * waiting for its message to be processed. TO prevent this, use {@link #SendMessageTimeout} with SMTO_BLOCK set. For more information on
	 * nonqueued messages, see <a href="https://msdn.microsoft.com/en-us/library/windows/desktop/ms644927(v=vs.85).aspx#nonqueued_messages">
	 * Nonqueued Messages</a>.</p>
	 * 
	 * @param hWnd A handle to the window whose window procedure will receive the message. If this parameter is <b>HWND_BROADCAST</b>
	 * 		((HWND)0xFFFF), the message is sent to all top-level windows in the system, including disabled or invisible unowned windows,
	 * 		overlapped windows, and pop-up windows; but the message is not sent to child windows.
	 * 
	 * 		<p>Message sending is subject to UIPI. The thread of a process can send messages only to message queues of threads in processes
	 * 		of lesser or equal integrity level.</p>
	 * @param Msg The message to be sent.
	 * 		<p>For lists of the system-provided messages, see
	 * 		<a href="https://msdn.microsoft.com/en-us/library/windows/desktop/ms644927(v=vs.85).aspx#system_defined">Systen-Defined Messages</a>
	 * 		.</p>
	 * @param wParam Additional message-specific information.
	 * @param lParam Additional message-specific information.
	 * @return The return value specifies the result of the message processing; it depends on the message sent.
	 */
	public static native int SendMessage(Pointer<HWND> hWnd, int Msg, Pointer<?> wParam, Pointer<?> lParam);
	
	/** <p>The <b>GetDC</b> function retrieves a handle to a device context (DC) for the client area of a specified window or for the entire
	 * screen. You can use the returned handle in subsequent GDI functions to draw in the DC. The device context is an opaque data structure,
	 * whose values are used internally by GDI.</p>
	 * 
	 * <p>The {@link #GetDCEx} function is an extension to <b>GetDC</b>, which gives an application more control over how and whether clipping
	 * occurs in the client area.</p>
	 * 
	 * <p>The <b>GetDC</b> function retrieves a common, class, or private DC depending on the class style of the specified window. For class
	 * and private DCs, <b>GetDC</b> leaves the previously assigned attributes unchanged. However, for common DCs, <b>GetDC</b> assigns
	 * default attributes to the DC each time it is retrieved. For example, the default font is System, which is a bitmap font. Because of
	 * this, the handle to a common DC returned by <b>GetDC</b> does not tell you what font, color, or brush was used when the window was
	 * drawn. To determine the font, call {@link GDI32#GetTextFace GetTextFace}.</p>
	 * 
	 * <p>Note that the handle to the DC can only be used by a single thread at any one time.</p>
	 * 
	 * <p>After painting with a common DC, the {@link #ReleaseDC} function must be called to release the DC. Class and private DCs do not have
	 * to be released. <b>ReleaseDC</b> must be called from the same thread that called <b>GetDC</b>. The number of DCs is limited only by
	 * available memory.</p>
	 * 
	 * @param hWnd A handle to the window whose DC is to be retrieved. If this value is <b>NULL</b>, <b>GetDC</b> retrieves the DC for the
	 * 		entire screen.
	 * @return If the function succeeds, the return value is a handle to the DC for the specified window's client area.
	 * 		<p>If the function fails, the return value is <b>NULL</b>.
	 */
	public static native Pointer<HDC> GetDC(Pointer<HWND> hWnd);
	
}
