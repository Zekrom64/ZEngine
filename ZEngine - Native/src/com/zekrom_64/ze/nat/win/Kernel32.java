package com.zekrom_64.ze.nat.win;

import org.bridj.CLib;
import org.bridj.Pointer;
import org.bridj.ann.Library;

@Library("kernel32")
public class Kernel32 {

	/** <p>Fills a block of memory with zeros.</p>
	 * 
	 * <p>To avoid any undesired effects of optimizing compilers, use the {@link #SecureZeroMemory} function.</p>
	 * 
	 * <p>Many programming languages include syntax for initializing complex variables to zero. There can be differences between the results
	 * of these operations and the <b>ZeroMemory</b> function. Use <b>ZeroMemory</b> to clear a block of memory in any programming language.</p>
	 * 
	 * <p>This macro is defined as the <b>RtlZeroMemory</b> macro. For more information, see WinBase.h and WinNT.h.</p>
	 * 
	 * @param Destination A pointer to the starting address of the block of memory to fill with zeros.
	 * @param Length The size of the block of memory to fill with zeros, in bytes.
	 */
	public static void ZeroMemory(Pointer<?> Destination, long Length) {
		CLib.memset(Destination, (byte)0, Length);
	}
	
}
