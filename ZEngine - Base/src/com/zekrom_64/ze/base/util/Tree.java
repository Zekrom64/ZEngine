package com.zekrom_64.ze.base.util;

import java.util.HashMap;
import java.util.Map;

public class Tree<K,T> {

	public final Map<K, Tree<K,T>> trees = new HashMap<>();
	public Map<K, T> values = new HashMap<>();
	
}
