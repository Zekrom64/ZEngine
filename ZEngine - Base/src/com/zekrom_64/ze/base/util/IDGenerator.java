package com.zekrom_64.ze.base.util;

import java.util.HashSet;
import java.util.Optional;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/** Class for generating sequential numeric IDs
 * 
 * @author Zekrom_64
 *
 */
public class IDGenerator {

	private HashSet<Integer> recyclableIds = new HashSet<>();
	private int nextId = 0;
	
	/** Allocates an ID.
	 * 
	 * @return Allocated ID
	 */
	public int alloc() {
		Optional<Integer> idOpt = recyclableIds.stream().findFirst();
		if (idOpt.isPresent()) {
			Integer id = idOpt.get();
			recyclableIds.remove(id);
			return id;
		}
		else return nextId++;
	}
	
	/** Releases an ID for reuse by the generator.
	 * 
	 * @param id ID to release
	 */
	public void release(int id) {
		if (id > 0 && id < nextId) recyclableIds.add(id);
	}
	
	/** Tests if a given ID is not allocated.
	 * 
	 * @param id ID to test
	 * @return If the ID is not allocated
	 */
	public boolean isFree(int id) {
		return id < 0 || id >= nextId || recyclableIds.contains(Integer.valueOf(id));
	}
	
	/** Iterates all the IDs in use.
	 * 
	 * @param consumer Consumer
	 */
	public void forEach(IntConsumer consumer) {
		IntStream.range(0, nextId).forEach((int i) -> {
			if (!recyclableIds.contains(Integer.valueOf(i))) consumer.accept(i);
		});
	}
	
	/** Provides a stream of all IDs in use
	 * 
	 * @return
	 */
	public IntStream stream() {
		return IntStream.range(0, nextId).filter((int i) -> !recyclableIds.contains(Integer.valueOf(i)));
	}
	
	/** Frees all IDs in use.
	 * 
	 */
	public void reset() {
		recyclableIds.clear();
		nextId = 0;
	}
	
}
