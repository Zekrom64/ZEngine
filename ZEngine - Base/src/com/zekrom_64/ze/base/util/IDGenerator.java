package com.zekrom_64.ze.base.util;

import java.util.HashSet;

/** Class for generating numeric IDs
 * 
 * @author Zekrom_64
 *
 */
public class IDGenerator {

	private HashSet<Integer> recyclableIds = new HashSet<>();
	private int nextId = 0;
	
	public int alloc() {
		if (recyclableIds.size()>0) {
			Integer[] ids = recyclableIds.toArray(new Integer[0]);
			return ids[0];
		}
		else return nextId++;
	}
	
	public void release(int id) {
		if (id<nextId) recyclableIds.add(id);
	}
	
	public boolean isFree(int id) {
		return id >= nextId || recyclableIds.contains(id);
	}
	
}
