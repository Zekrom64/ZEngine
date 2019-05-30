package com.zekrom_64.mathlib.tuple;

public interface Vector<T> {

	public Class<?> type();
	
	public int size();
	
	public T get(int i);
	
	public void set(int i, T val);
	
	public default boolean equals(Vector<T> other) {
		if (size() != other.size()) return false;
		for(int i = 0; i < size(); i++) {
			T t1 = get(i);
			T t2 = other.get(i);
			if (t1 != null ^ t2 != null) return false;
			if (t1 != null && t2 != null && !t1.equals(t2)) return false;
		}
		return true;
	}
	
}
