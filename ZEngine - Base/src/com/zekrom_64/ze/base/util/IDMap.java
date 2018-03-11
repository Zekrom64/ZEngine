package com.zekrom_64.ze.base.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/** Class for mapping a value to a sequentially generated ID.
 * 
 * @author Zekrom_64
 *
 * @param <V> Value to map to
 */
public class IDMap<V> {

	private ArrayList<V> mapping = new ArrayList<>();
	private HashSet<Integer> recyclableIds = new HashSet<>();
	
	/** Maps a value to an available ID.
	 * 
	 * @param value Value to map
	 * @return Mapped ID
	 */
	public int map(V value) {
		Optional<Integer> optId = recyclableIds.stream().findFirst();
		if (optId.isPresent()) {
			int id = optId.get();
			mapping.set(id, value);
			return id;
		}
		int id = mapping.size();
		mapping.add(id, value);
		return id;
	}
	
	/** Unmaps an ID if it is in use.
	 * 
	 * @param id ID to unmap
	 */
	public void unmap(int id) {
		if (id >= 0 && id < mapping.size()) {
			mapping.set(id, null);
			recyclableIds.add(Integer.valueOf(id));
		}
	}
	
	/** Gets the value mapped to an ID if it exists, returning <b>null</b> if
	 * no such ID is in use.
	 * 
	 * @param id ID
	 * @return Mapped value, or <b>null</b>
	 */
	public V getValue(int id) {
		if (id < 0 || id >= mapping.size()) return null;
		return mapping.get(id);
	}
	
	/** Tests if a given ID is not in use.
	 * 
	 * @param id ID
	 * @return If the ID is not in use
	 */
	public boolean isFree(int id) {
		return id < 0 || id >= mapping.size() || recyclableIds.contains(Integer.valueOf(id));
	}
	
	/** Iterates the mapped elements, passing them as entries to the consumer.
	 * 
	 * @param consumer Consumer
	 */
	public void forEach(Consumer<Entry<Integer,V>> consumer) {
		IntStream.range(0, mapping.size())
				.filter((int i) -> !recyclableIds.contains(Integer.valueOf(i)))
				.forEach((int index) -> {
			consumer.accept(new Entry<Integer, V>() {

				@Override
				public Integer getKey() {
					return Integer.valueOf(index);
				}

				@Override
				public V getValue() {
					return mapping.get(index);
				}

				@Override
				public V setValue(V value) {
					return mapping.set(index, value);
				}
				
			});
		});
	}
	
	/** Provides a stream of the mapped elements.
	 * 
	 * @return Mapped element stream
	 */
	public Stream<Entry<Integer,V>> stream() {
		return IntStream.range(0, mapping.size())
				.filter((int i) -> !recyclableIds.contains(Integer.valueOf(i)))
				.mapToObj(new IntFunction<Entry<Integer,V>>() {
			
			@Override
			public Entry<Integer,V> apply(int i) {
				return new Entry<Integer,V>() {
	
					@Override
					public Integer getKey() {
						return Integer.valueOf(i);
					}
				
					@Override
					public V getValue() {
						return mapping.get(i);
					}
				
					@Override
					public V setValue(V value) {
						return mapping.set(i, value);
					}
					
				};
			}
		});
	}
	
	/** Provides a stream of all keys in use.
	 * 
	 * @return Key stream
	 */
	public IntStream keyStream() {
		return IntStream.range(0, mapping.size())
				.filter((int i) -> !recyclableIds.contains(Integer.valueOf(i)));
	}
	
	/** Provides a stream of all mapped values. This is essentially invoking
	 * {@link IntStream#mapToObj(IntFunction) mapToObj} on the result of
	 * {@link #keyStream()}.
	 * 
	 * @return Value stream
	 */
	public Stream<V> valueStream() {
		return IntStream.range(0, mapping.size())
				.filter((int i) -> !recyclableIds.contains(Integer.valueOf(i)))
				.mapToObj((int i) -> mapping.get(i));
	}
	
	/** Clears the ID map of all entries.
	 * 
	 */
	public void reset() {
		mapping.clear();
		recyclableIds.clear();
	}
	
}
