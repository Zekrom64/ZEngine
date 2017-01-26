package org.bridj;

public class BridJUtil {
	
	/** Does an unchecked cast from one pointer type to another. This is to allow casts between types
	 * that generics do not think is possible. The object returned is the same passed to it, but the
	 * generic signature is different.
	 * 
	 * @param ptr Original pointer
	 * @return Casted pointer
	 */
	@SuppressWarnings("unchecked")
	public static <S,D> Pointer<D> uncheckedCast(Pointer<S> ptr) {
		Pointer<?> abstracted = (Pointer<?>)ptr;
		return (Pointer<D>)abstracted;
	}
	
}
