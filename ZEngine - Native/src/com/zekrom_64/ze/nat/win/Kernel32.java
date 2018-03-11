package com.zekrom_64.ze.nat.win;

import org.bridj.CLib;
import org.bridj.Pointer;
import org.bridj.SizeT;
import org.bridj.ann.Convention;
import org.bridj.ann.Convention.Style;
import org.bridj.ann.Library;

@Library("kernel32")
public class Kernel32 {
	
	public static final int MEM_COMMIT = 0x00001000;
	public static final int MEM_RESERVE = 0x00002000;
	public static final int MEM_RESET = 0x00080000;
	public static final int MEM_RESET_UNDO = 0x1000000;
	
	public static final int MEM_LARGE_PAGES = 0x20000000;
	public static final int MEM_PHYSICAL = 0x00400000;
	public static final int MEM_TOP_DOWN = 0x00100000;
	public static final int MEM_WRITE_WATCH = 0x00200000;
	
	public static final int MEM_DECOMMIT = 0x4000;
	public static final int MEM_RELEASE = 0x8000;
	
	public static final int PAGE_EXECUTE = 0x10;
	public static final int PAGE_EXECUTE_READ = 0x20;
	public static final int PAGE_EXECUTE_READ_WRITE = 0x40;
	public static final int PAGE_EXECUTE_READ_WRITE_COPY = 0x80;
	public static final int PAGE_NOACCESS = 0x01;
	public static final int PAGE_READ_ONLY = 0x02;
	public static final int PAGE_READ_WRITE = 0x04;
	public static final int PAGE_WRITECOPY = 0x08;
	public static final int PAGE_TARGETS_INVALID = 0x40000000;
	public static final int PAGE_TARGETS_NO_UPDATE = 0x40000000;
	
	public static final int PAGE_GUARDED = 0x100;
	public static final int PAGE_NOCACHE = 0x200;
	public static final int PAGE_WRITECOMBINE = 0x400;

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
	
	/** <p> Reserves, commits, or changes the state of a region of pages in the virtual address space of the calling process.
	 * Memory allocated by this function is automatically initialized to zero.</p>
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
	 * @param lpAddress <p> The starting address of the region to allocate. If the memory is being reserved, the specified address
	 * is rounded down to the nearest multiple of the allocation granularity. If the memory is already reserved and is being committed,
	 * the address is rounded down to the next page boundary. If this parameter is <b>NULL</b>, the system determines where to allocate the region.
	 * </p>
	 * @param dwSize <p> The size of the region, in bytes. If the lpAddress parameter is <b>NULL</b>, this value is rounded up to the next page
	 * boundary. Otherwise, the allocated pages include all pages containing one or more bytes in the range from lpAddress to lpAddress+dwSize.
	 * This means that a 2-byte range straddling a page boundary causes both pages to be included in the allocated region.
	 * </p>
	 * @param flAllocationType <p> The type of memory allocation. This parameter must contain one of the following values.</p>
	 * <table border="1">
	 * <th>Value</th><th>Meaning</th>
	 * <tr>
	 * <td><b>MEM_COMMIT</b> 0x00001000</td>
	 * <td>
	 * <p> Allocates memory charges (from the overall size of memory and the paging files on disk) for the specified reserved memory pages.
	 * The function also guarantees that when the caller later initially accesses the memory, the contents will be zero. Actual physical pages
	 * are not allocated unless/until the virtual addresses are actually accessed.</p>
	 * <p> To reserve and commit pages in one step, call <b>VirtualAlloc</b> with <tt>MEM_COMMIT | MEM_RESERVE.</tt></p>
	 * <p> Attempting to commit a specific address range by specifying <b>MEM_COMMIT</b> without <b>MEM_RESERVE</b> and a non-<b>NULL</b>
	 * <i>lpAddress</i> fails unless the entire range has already been reserved. The resulting error code is <b>ERROR_INVALID_ADDRESS</b>.</p>
	 * <p> An attempt to commit a page that is already committed does not cause the function to fail. This means that you can commit pages
	 * without first determining the current commitment state of each page.</p>
	 * <p>If <i>lpAddress</i> specifies an address within an enclave, <i>flAllocationType</i> must be <b>MEM_COMMIT</b>.</p>
	 * </td>
	 * </tr><tr>
	 * <td><b>MEM_RESERVE</b> 0x00002000</td>
	 * <td><p> Reserves a range of the process's virtual address space without allocating any actual physical storage in memory or in the
	 * paging file on disk.</p>
	 * <p> You can commit reserved pages in subsequent calls to the <b>VirtualAlloc</b> function. To reserve and commit pages in one step,
	 * call <b>VirtualAlloc</b> with <b>MEM_COMMIT</b> | <b>MEM_RESERVE</b>.</p>
	 * <p> Other memory allocation functions, such as <b>malloc</b>, cannot use a reserved range of memory until it is released. </p>
	 * </td>
	 * </tr><tr>
	 * <td><b>MEM_RESET</b> 0x00080000</td>
	 * <td><p> Indicates that data in the memory range specified by <t>lpAddress</i> and <i>dwSize</i> is no longer of interest. The pages
	 * should not be read from or written to the paging file. However, the memory block will be used again later, so it should not be
	 * decommitted. This value cannot be used with any other value.
	 * </p><p> Using this value does not guarantee that the range operated on with <b>MEM_RESET</b> will contain zeros. If you want the
	 * range to contain zeros, decommit the memory and then recommit it.
	 * </p><p> When you specify <b>MEM_RESET</b>, the <b>VirtualAlloc</b> function ignores the value of <i>flProtect</i>. However, you must
	 * still set <i>flProtect</i> to a valid protection value, such as <b>PAGE_NOACCESS</b>.
	 * </p><p> <b>VirtualAlloc</b> returns an error if you use <b>MEM_RESET</b> and the range of memory is mapped to a file. A shared view
	 * is only acceptable if it is mapped to a paging file.</p>
	 * </td>
	 * </tr><tr>
	 * <td><b>MEM_RESET_UNDO</b> 0x1000000</td>
	 * <td><p> <b>MEM_RESET_UNDO</b> should only be called on an address range to which <b>MEM_RESET</b> was successfully applied earlier.
	 * It indicates that the data in the specified memory range specified by <i>lpAddress</i> and <i>dwSize</i> is of interest to the caller
	 * and attempts to reverse the effects of <b>MEM_RESET</b>. If the function succeeds, that means all data in the specified address range
	 * is intact. If the function fails, at least some of the data in the address range has been replaced with zeroes.
	 * </p><p>
	 * This value cannot be used with any other value. If <b>MEM_RESET_UNDO</b> is called on an address range which was not <b>MEM_RESET</b>
	 * earlier, the behavior is undefined. When you specify <b>MEM_RESET</b>, the <b>VirtualAlloc</b> function ignores the value of
	 * <i>flProtect</i>. However, you must still set <i>flProtect</i> to a valid protection value, such as <b>PAGE_NOACCESS</b>.
	 * </p>
	 * </td>
	 * </tr>
	 * </table>
	 * <p> This parameter can also specify the following values as indicated. </p>
	 * <table border="1">
	 * <th>Value</th><th>Meaning</th>
	 * <tr>
	 * <td><b>MEM_LARGE_PAGES</b> 0x20000000</td>
	 * <td><p> Allocates memory using <a href="https://msdn.microsoft.com/en-us/library/windows/desktop/aa366720(v=vs.85).aspx">
	 * large page support</a>.</p>
	 * <p> The size and alignment must be a multiple of the large-page minimum. To obtain this value, use the {@link GetLargePageMinimum} function. </p>
	 * <p> If you specify this value, you must also specify <b>MEM_RESERVE</b> and <b>MEM_COMMIT</b>.</p>
	 * </td>
	 * </tr><tr>
	 * <td><b>MEM_PHYSICAL</b> 0x00400000</td>
	 * <td>
	 * <p> Reserves an address range that can be used to map <a href="https://msdn.microsoft.com/en-us/library/windows/desktop/aa366527(v=vs.85).aspx">
	 * Address Windowing Extensions</a> (AWE) pages.</p>
	 * <p> This value must be used with <b>MEM_RESERVE</b> and no other values. </p>
	 * </td>
	 * </tr><tr>
	 * <td><b>MEM_TOP_DOWN</b> 0x00100000</td>
	 * <td> Allocates memory at the highest possible address. This can be slower than regular allocations, especially when there are many allocations.</td>
	 * </tr><tr>
	 * <td><b>MEM_WRITE_WATCH</b> 0x00200000</td>
	 * <td><p> Causes the system to track pages that are written to in the allocated region. If you specify this value, you must also specify <b>MEM_RESERVE</b>.</p>
	 * <p> To retrieve the addresses of the pages that have been written to since the region was allocated or the write-tracking state was reset,
	 * call the {@link GetWriteWatch} function. To reset the write-tracking state, call <b>GetWriteWatch</b> or {@link ResetWriteWatch}. The
	 * write-tracking feature remains enabled for the memory region until the region is freed. </p>
	 * </td>
	 * </tr>
	 * </table>
	 * @param flProtect
	 * <p> The memory protection for the region of pages to be allocated. If the pages are being committed, you can specify any one of
	 * the <a href="https://msdn.microsoft.com/en-us/library/windows/desktop/aa366786(v=vs.85).aspx">memory protection constants</a>.</p>
	 * <p>If <i>lpAddress</i> specifies an address within an enclave, <i>flProtect</i> cannot be any of the following values:
	 * <ul>
	 * <li>PAGE_NOACCESS</li>
	 * <li>PAGE_GUARD</li>
	 * <li>PAGE_NOCACHE</li>
	 * <li>PAGE_WRITECOMBINE</li>
	 * </ul></p>
	 * @return <p>
	 * If the function succeeds, the return value is the base address of the allocated region of pages.
	 * </p><p>
	 * If the function fails, the return value is <b>NULL</b>.
	 * </p>
	 */
	@Convention(Style.StdCall)
	public static native Pointer<?> VirtualAlloc(Pointer<?> lpAddress, SizeT dwSize, int flAllocationType, int flProtect);
	
	/** <p> Releases, decommits, or releases and decommits a region of pages within the virtual address space of the calling process.</p>
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
	 * @param lpAddress
	 * <p> A pointer to the base address of the region of pages to be freed. </p>
	 * <p> If the <i>dwFreeType</i> parameter is <b>MEM_RELEASE</b>, this parameter must be the base address returned by the {@link VirtualAlloc}
	 *  function when the region of pages is reserved. </p>
	 * @param dwSize
	 * <p> The size of the region of memory to be freed, in bytes. </p>
	 * <p> If the <i>dwFreeType</i> parameter is <b>MEM_RELEASE</b>, this parameter must be 0 (zero). The function frees the entire region that
	 * is reserved in the initial allocation call to {@link VirtualAlloc}. </p>
	 * <p> If the <i>dwFreeType</i> parameter is <b>MEM_DECOMMIT</b>, the function decommits all memory pages that contain one or more bytes in
	 * the range from the <i>lpAddress</i> parameter to <tt>(lpAddress+dwSize)</tt>. This means, for example, that a 2-byte region of memory that
	 * straddles a page boundary causes both pages to be decommitted. If <i>lpAddress</i> is the base address returned by {@link VirtualAlloc}
	 * and <i>dwSize</i> is 0 (zero), the function decommits the entire region that is allocated by <b>VirtualAlloc</b>. After that, the entire
	 * region is in the reserved state. </p>
	 * @param dwFreeType
	 * <p> The type of free operation. This parameter can be one of the following values. </p>
	 * <table border="1">
	 * <th>Value</th><th>Meaning</th>
	 * <tr>
	 * <td><b>MEM_DECOMMIT</b> 0x4000</td>
	 * <td>
	 * <p> Decommits the specified region of committed pages. After the operation, the pages are in the reserved state. </p>
	 * <p> The function does not fail if you attempt to decommit an uncommitted page. This means that you can decommit a range of pages without
	 * first determining the current commitment state. </p>
	 * <p> Do not use this value with <b>MEM_RELEASE</b>.</p>
	 * <p> The <b>MEM_DECOMMIT</b> value is not supported when the <i>lpAddress</i> parameter provides the base address for an enclave. </p>
	 * </td>
	 * </tr><tr>
	 * <td><b>MEM_RELEASE</b> 0x8000</td>
	 * <td>
	 * <p> Releases the specified region of pages. After this operation, the pages are in the free state. </p>
	 * <p> If you specify this value, <i>dwSize</i> must be 0 (zero), and <i>lpAddress</i> must point to the base address returned by the
	 * {@link VirtualAlloc} function when the region is reserved. The function fails if either of these conditions is not met. </p>
	 * <p> If any pages in the region are committed currently, the function first decommits, and then releases them. </p>
	 * <p> The function does not fail if you attempt to release pages that are in different states, some reserved and some committed. This means
	 * that you can release a range of pages without first determining the current commitment state. </p>
	 * <p> Do not use this value with <b>MEM_DECOMMIT</b>. </p>
	 * </td>
	 * </tr>
	 * </table>
	 * @return
	 * <p> If the function succeeds, the return value is nonzero. </p>
	 * <p> If the function fails, the return value is 0 (zero). </p>
	 */
	public static native boolean VirtualFree(Pointer<?> lpAddress, SizeT dwSize, int dwFreeType);
	
	/** <p> Changes the protection on a region of committed pages in the virtual address space of the calling process. </p>
	 * 
	 * @param lpAddress
	 * <p> A pointer an address that describes the starting page of the region of pages whose access protection attributes are to be changed. </p>
	 * <p> All pages in the specified region must be within the same reserved region allocated when calling the VirtualAlloc function using
	 * <b>MEM_RESERVE</b>. The pages cannot span adjacent reserved regions that were allocated by separate calls to VirtualAlloc using
	 * <b>MEM_RESERVE</b>. </p>
	 * @param dwSize
	 * <p> The size of the region whose access protection attributes are to be changed, in bytes. The region of affected pages includes all pages
	 * containing one or more bytes in the range from the <i>lpAddress</i> parameter to <tt>(lpAddress+dwSize)</tt>. This means that a 2-byte range
	 * straddling a page boundary causes the protection attributes of both pages to be changed. </p>
	 * @param flNewProtect
	 * <p> The memory protection option. This parameter can be one of the
	 * <a href="https://msdn.microsoft.com/en-us/library/windows/desktop/aa366786(v=vs.85).aspx">memory protection constants</a>. </p>
	 * @param lpflOldProtect A pointer to a variable that receives the previous access protection value of the first page in the specified region of
	 * pages. If this parameter is <b>NULL</b> or does not point to a valid variable, the function fails.
	 * @return
	 * <p> If the function succeeds, the return value is nonzero. </p>
	 * <p> If the function fails, the return value is zero. </p>
	 */
	public static native boolean VirtualProtect(Pointer<?> lpAddress, SizeT dwSize, int flNewProtect, Pointer<Integer> lpflOldProtect);
	
	/** Retrieves the minimum size of a large page.
	 * 
	 * @return
	 * <p> If the processor supports large pages, the return value is the minimum size of a large page. </p>
	 * <p> If the processor does not support large pages, the return value is zero. </p>
	 */
	public static native SizeT GetLargePageMinimum();
	
}
