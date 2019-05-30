package com.zekrom_64.ze.base.backend.render.shader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;

import com.zekrom_64.ze.base.backend.render.obj.ZETextureDimension;
import com.zekrom_64.ze.base.util.ZEPrimitiveType;

/** A shader type describes a type used in a shader. Shader types have a base 'variant' that describes how they function
 * in a shader and what they are composed of.
 * 
 * @author Zekrom_64
 *
 */
@Deprecated
public interface ZEShaderType {

	/** Gets the size of the shader type in bytes, or -1 if the type has no definite size.
	 * 
	 * @return Size of type in bytes, or -1
	 */
	public int sizeOf();
	
	public enum ZEShaderTypeVariant {
		/** A type variant that contains a primitive type. */
		PRIMITIVE,
		/** A type variant that is a vector of another type. */
		VECTOR,
		/** A type variant that is a structure with some number of members. */
		STRUCTURE,
		/** A type variant that is a pointer to a value of another type. */
		POINTER,
		/** A type variant that is an array of another type. */
		ARRAY,
		/** A type variant that is an opaque handle to an object. */
		OPAQUE,
		/** A type variant that is a vector of vector types. */
		MATRIX,
		/** A type variant that is a sampler for an image. */
		IMAGE_SAMPLER
		
	}
	
	/** Gets the type variant of the shader type.
	 * 
	 * @return Type variant
	 */
	public ZEShaderTypeVariant variant();
	
	/** Shader type for a primitive value.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public static class ZEShaderTypePrimitive implements ZEShaderType {
		
		private static ZEShaderTypePrimitive[] primitiveTypes = new ZEShaderTypePrimitive[ZEPrimitiveType.values().length];
		
		/** Gets an instance of the class for the given primitive from a cached pool.
		 * 
		 * @param t Primitive type
		 * @return Shader primitive type
		 */
		public static ZEShaderTypePrimitive get(ZEPrimitiveType t) {
			if (t == null) return null;
			ZEShaderTypePrimitive type = primitiveTypes[t.ordinal()];
			if (type == null) {
				type = new ZEShaderTypePrimitive(t);
				primitiveTypes[t.ordinal()] = type;
			}
			return type;
		}

		public static final ZEShaderTypePrimitive INT = get(ZEPrimitiveType.INT);
		public static final ZEShaderTypePrimitive FLOAT = get(ZEPrimitiveType.FLOAT);
		public static final ZEShaderTypePrimitive DOUBLE = get(ZEPrimitiveType.DOUBLE);
		
		public final ZEPrimitiveType primitive;
		
		private ZEShaderTypePrimitive(ZEPrimitiveType type) {
			primitive = type;
		}
		
		public int sizeOf() {
			return primitive.size;
		}
		
		public final ZEShaderTypeVariant variant() {
			return ZEShaderTypeVariant.PRIMITIVE;
		}
		
		@Override
		public boolean equals(Object o) {
			if (o instanceof ZEShaderTypePrimitive) return equals((ZEShaderTypePrimitive)o);
			return false;
		}
		
		public boolean equals(ZEShaderTypePrimitive t) {
			if (t == null) return false;
			return primitive == t.primitive;
		}
		
	}
	
	/** Shader type for a vector of values.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public static class ZEShaderTypeVector implements ZEShaderType {
		
		private static Map<ZEShaderType,ArrayList<ZEShaderTypeVector>> vectorTypes = new HashMap<>();
		
		public static ZEShaderTypeVector get(ZEShaderType elemType, int size) {
			if (size < 1) return null;
			ArrayList<ZEShaderTypeVector> typeList = vectorTypes.get(elemType);
			if (typeList == null) {
				typeList = new ArrayList<>();
				vectorTypes.put(elemType, typeList);
			}
			typeList.ensureCapacity(size);
			ZEShaderTypeVector type = typeList.get(size - 1);
			if (type == null) {
				type = new ZEShaderTypeVector(elemType, size);
				typeList.set(size - 1, type);
			}
			return type;
		}
		
		public final ZEShaderType elementType;
		public final int size;
		
		public ZEShaderTypeVector(ZEShaderType elemType, int count) {
			elementType = elemType;
			size = count;
		}
		
		public int sizeOf() {
			int sizeof = elementType.sizeOf();
			if (sizeof == -1) return -1;
			return sizeof * size;
		}
		
		public final ZEShaderTypeVariant variant() {
			return ZEShaderTypeVariant.VECTOR;
		}
		
		@Override
		public boolean equals(Object o) {
			if (o instanceof ZEShaderTypeVector) return equals((ZEShaderTypeVector)o);
			return false;
		}
		
		public boolean equals(ZEShaderTypeVector t) {
			if (t == null) return false;
			return t.elementType.equals(elementType) && t.size == size;
		}
		
	}
	
	/** Shader type for a matrix of values, with the matrix arranged as columns of vectors.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public static class ZEShaderTypeMatrix implements ZEShaderType {
		
		private static Map<ZEShaderTypeVector,ArrayList<ZEShaderTypeMatrix>> matrixTypes = new HashMap<>();
		
		public static ZEShaderTypeMatrix get(ZEShaderTypeVector elemType, int size) {
			if (size < 1) return null;
			ArrayList<ZEShaderTypeMatrix> typeList = matrixTypes.get(elemType);
			if (typeList == null) {
				typeList = new ArrayList<>();
				matrixTypes.put(elemType, typeList);
			}
			typeList.ensureCapacity(size);
			ZEShaderTypeMatrix type = typeList.get(size - 1);
			if (type == null) {
				type = new ZEShaderTypeMatrix(elemType, size);
				typeList.set(size - 1, type);
			}
			return type;
		}

		public final ZEShaderTypeVector vectorType;
		public final int columnCount;
		
		public ZEShaderTypeMatrix(ZEShaderTypeVector vect, int coln) {
			vectorType = vect;
			columnCount = coln;
		}
		
		@Override
		public int sizeOf() {
			int size = vectorType.size;
			if (size == -1) return -1;
			return size * columnCount;
		}

		@Override
		public ZEShaderTypeVariant variant() {
			return ZEShaderTypeVariant.MATRIX;
		}
		
		@Override
		public boolean equals(Object o) {
			if (o instanceof ZEShaderTypeMatrix) return equals((ZEShaderTypeMatrix)o);
			return false;
		}
		
		public boolean equals(ZEShaderTypeMatrix t) {
			if (t == null) return false;
			return t.vectorType.equals(vectorType) && t.columnCount == t.columnCount;
		}
		
	}
	
	/** Shader type for a data structure value. A data structure has an ordered series of values
	 * mapped to a name.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public static class ZEShaderTypeStruct implements ZEShaderType {
		
		public final SortedMap<String,ZEShaderType> memberMap;
		
		public ZEShaderTypeStruct(SortedMap<String,ZEShaderType> members) {
			memberMap = Collections.unmodifiableSortedMap(members);
		}

		@Override
		public int sizeOf() {
			if (memberMap.isEmpty()) return 0;
			int size = 0;
			for(ZEShaderType type : memberMap.values()) {
				if (type.sizeOf() == -1) return -1;
				size += type.sizeOf();
			}
			return size;
		}

		@Override
		public ZEShaderTypeVariant variant() {
			return ZEShaderTypeVariant.STRUCTURE;
		}
		
		@Override
		public boolean equals(Object o) {
			if (o instanceof ZEShaderTypeStruct) return equals((ZEShaderTypeStruct)o);
			return false;
		}
		
		public boolean equals(ZEShaderTypeStruct t) {
			for(Entry<String,ZEShaderType> entry : memberMap.entrySet())
				if (!entry.getValue().equals(t.memberMap.get(entry.getKey()))) return false;
			return true;
		}
		
	}
	
	/** Shader type for a fixed length array of values.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public static class ZEShaderTypeArray implements ZEShaderType {
		
		public final ZEShaderType elementType;
		public final int size;
		
		public ZEShaderTypeArray(ZEShaderType elemType, int count) {
			elementType = elemType;
			size = count;
		}
		
		public int sizeOf() {
			int sizeof = elementType.sizeOf();
			if (sizeof == -1) return -1;
			return sizeof * size;
		}
		
		public final ZEShaderTypeVariant variant() {
			return ZEShaderTypeVariant.ARRAY;
		}
		
		@Override
		public boolean equals(Object o) {
			if (o instanceof ZEShaderTypeArray) return equals((ZEShaderTypeArray)o);
			return false;
		}
		
		public boolean equals(ZEShaderTypeArray a) {
			if (a == null) return false;
			return a.elementType.equals(elementType) && a.size == size;
		}
		
	}
	
	/** Shader type for a pointer (address-of) an indirect value.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public static class ZEShaderTypePointer implements ZEShaderType {
		
		private static final Map<ZEShaderType,ZEShaderTypePointer> pointerTypes = new HashMap<>();
		
		public static ZEShaderTypePointer get(ZEShaderType indType) {
			ZEShaderTypePointer type = pointerTypes.get(indType);
			if (type == null) {
				type = new ZEShaderTypePointer(indType);
				pointerTypes.put(indType, type);
			}
			return type;
		}

		public final ZEShaderType indirectType;
		
		public ZEShaderTypePointer(ZEShaderType t) {
			indirectType = t;
		}
		
		@Override
		public int sizeOf() {
			return -1;
		}

		@Override
		public ZEShaderTypeVariant variant() {
			return ZEShaderTypeVariant.POINTER;
		}
		
		public boolean equals(Object o) {
			if (o instanceof ZEShaderTypePointer) return equals((ZEShaderTypePointer)o);
			return false;
		}
		
		public boolean equals(ZEShaderTypePointer p) {
			if (p == null) return false;
			return p.indirectType.equals(indirectType);
		}
		
	}
	
	public static class ZEShaderTypeImageSampler implements ZEShaderType {
		
		private static final Map<ZEShaderTypeVector,ArrayList<ZEShaderTypeImageSampler>> samplerTypes = new HashMap<>();
		
		public static ZEShaderTypeImageSampler get(ZEShaderTypeVector sampleType, ZETextureDimension targetDimension) {
			ArrayList<ZEShaderTypeImageSampler> typeArray = samplerTypes.get(sampleType);
			if (typeArray == null) {
				typeArray = new ArrayList<>(ZETextureDimension.values().length);
				samplerTypes.put(sampleType, typeArray);
			}
			ZEShaderTypeImageSampler type = typeArray.get(targetDimension.ordinal());
			if (type == null) {
				type = new ZEShaderTypeImageSampler(sampleType, targetDimension);
				typeArray.set(targetDimension.ordinal(), type);
			}
			return type;
		}
		
		public final ZEShaderTypeVector sampledType;
		public final ZETextureDimension samplingDimensions;
		
		public ZEShaderTypeImageSampler(ZEShaderTypeVector st, ZETextureDimension sd) {
			sampledType = st;
			samplingDimensions = sd;
		}

		@Override
		public int sizeOf() {
			return -1;
		}

		@Override
		public ZEShaderTypeVariant variant() {
			return ZEShaderTypeVariant.IMAGE_SAMPLER;
		}
		
		public boolean equals(Object o) {
			if (o instanceof ZEShaderTypeImageSampler) return equals((ZEShaderTypeImageSampler)o);
			return false;
		}
		
		public boolean equals(ZEShaderTypeImageSampler smp) {
			if (smp == null) return false;
			return smp.sampledType.equals(sampledType);
		}
		
	}
	
}
