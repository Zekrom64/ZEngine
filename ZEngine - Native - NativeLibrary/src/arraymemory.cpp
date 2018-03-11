/*
 * arraymemory.cpp
 *
 *  Created on: Apr 1, 2017
 *      Author: Zekrom_64
 */

#include "main.hpp"
#include "com_zekrom_64_ze_nat_ZEArrayMemory.h"

enum ArrayType {
	ARRAYTYPE_BOOLEAN = 0,
	ARRAYTYPE_BYTE,
	ARRAYTYPE_SHORT,
	ARRAYTYPE_CHAR,
	ARRAYTYPE_INT,
	ARRAYTYPE_LONG,
	ARRAYTYPE_FLOAT,
	ARRAYTYPE_DOUBLE
};

JNIEXPORT jobject JNICALL Java_com_zekrom_164_ze_nat_ZEArrayMemory_map__Ljava_lang_Object_2Z
  (JNIEnv *_env, jclass clazz, jobject array, jboolean wb) {
	if (array == nullptr) _env->ThrowNew(Global::class_NullPointerException, "");
	jobject obj = _env->NewObject(Global::class_ZEArrayMemory, Global::ctor_ZEArrayMemory);

	_env->SetObjectField(obj, Global::field_ZEArrayMemory_array, array);
	_env->SetBooleanField(obj, Global::field_ZEArrayMemory_writeBack, wb);

	void* address = nullptr;
	ArrayType type = 0;

	if (_env->IsInstanceOf(array, Global::class_arrayBoolean)) {
		address = _env->GetBooleanArrayElements((jbooleanArray)array, nullptr);
		type = ARRAYTYPE_BOOLEAN;
	} else if (_env->IsInstanceOf(array, Global::class_arrayByte)) {
		address = _env->GetByteArrayElements((jbyteArray)array, nullptr);
		type = ARRAYTYPE_BYTE;
	} else if (_env->IsInstanceOf(array, Global::class_arrayShort)) {
		address = _env->GetShortArrayElements((jshortArray)array, nullptr);
		type = ARRAYTYPE_SHORT;
	} else if (_env->IsInstanceOf(array, Global::class_arrayChar)) {
		address = _env->GetCharArrayElements((jcharArray)array, nullptr);
		type = ARRAYTYPE_CHAR;
	} else if (_env->IsInstanceOf(array, Global::class_arrayInt)) {
		address = _env->GetIntArrayElements((jintArray)array, nullptr);
		type = ARRAYTYPE_INT;
	} else if (_env->IsInstanceOf(array, Global::class_arrayLong)) {
		address = _env->GetLongArrayElements((jlongArray)array, nullptr);
		type = ARRAYTYPE_LONG;
	} else if (_env->IsInstanceOf(array, Global::class_arrayFloat)) {
		address = _env->GetFloatArrayElements((jfloatArray)array, nullptr);
		type = ARRAYTYPE_FLOAT;
	} else if (_env->IsInstanceOf(array, Global::class_arrayDouble)) {
		address = _env->GetDoubleArrayElements((jdoubleArray)array, nullptr);
		type = ARRAYTYPE_DOUBLE;
	} else _env->ThrowNew(Global::class_IllegalArgumentException, "Array is not mappable to native memory");

	_env->SetIntField(obj, Global::field_ZEArrayMemory_type, type);
	_env->SetLongField(obj, Global::field_ZEArrayMemory_address, (jlong)address);

	return obj;
}

JNIEXPORT jobject JNICALL Java_com_zekrom_164_ze_nat_ZEArrayMemory_map___3ZZ
  (JNIEnv *_env, jclass clazz, jbooleanArray array, jboolean wb) {
	jobject obj = _env->NewObject(Global::class_ZEArrayMemory, Global::ctor_ZEArrayMemory);

	_env->SetObjectField(obj, Global::field_ZEArrayMemory_array, array);
	_env->SetBooleanField(obj, Global::field_ZEArrayMemory_writeBack, wb);
	_env->SetIntField(obj, Global::field_ZEArrayMemory_type, ARRAYTYPE_BOOLEAN);
	jboolean* address = _env->GetBooleanArrayElements(array, nullptr);
	_env->SetLongField(obj, Global::field_ZEArrayMemory_address, (jlong)address);

	return obj;
}

JNIEXPORT jobject JNICALL Java_com_zekrom_164_ze_nat_ZEArrayMemory_map___3BZ
  (JNIEnv *_env, jclass clazz, jbyteArray array, jboolean wb) {
	jobject obj = _env->NewObject(Global::class_ZEArrayMemory, Global::ctor_ZEArrayMemory);

	_env->SetObjectField(obj, Global::field_ZEArrayMemory_array, array);
	_env->SetBooleanField(obj, Global::field_ZEArrayMemory_writeBack, wb);
	_env->SetIntField(obj, Global::field_ZEArrayMemory_type, ARRAYTYPE_BYTE);
	jbyte* address = _env->GetByteArrayElements(array, nullptr);
	_env->SetLongField(obj, Global::field_ZEArrayMemory_address, (jlong)address);

	return obj;
}

JNIEXPORT jobject JNICALL Java_com_zekrom_164_ze_nat_ZEArrayMemory_map___3SZ
  (JNIEnv *_env, jclass clazz, jshortArray array, jboolean wb) {
	jobject obj = _env->NewObject(Global::class_ZEArrayMemory, Global::ctor_ZEArrayMemory);

	_env->SetObjectField(obj, Global::field_ZEArrayMemory_array, array);
	_env->SetBooleanField(obj, Global::field_ZEArrayMemory_writeBack, wb);
	_env->SetIntField(obj, Global::field_ZEArrayMemory_type, ARRAYTYPE_SHORT);
	jshort* address = _env->GetShortArrayElements(array, nullptr);
	_env->SetLongField(obj, Global::field_ZEArrayMemory_address, (jlong)address);

	return obj;
}

JNIEXPORT jobject JNICALL Java_com_zekrom_164_ze_nat_ZEArrayMemory_map___3CZ
  (JNIEnv *_env, jclass clazz, jcharArray array, jboolean wb) {
	jobject obj = _env->NewObject(Global::class_ZEArrayMemory, Global::ctor_ZEArrayMemory);

	_env->SetObjectField(obj, Global::field_ZEArrayMemory_array, array);
	_env->SetBooleanField(obj, Global::field_ZEArrayMemory_writeBack, wb);
	_env->SetIntField(obj, Global::field_ZEArrayMemory_type, ARRAYTYPE_CHAR);
	jchar* address = _env->GetCharArrayElements(array, nullptr);
	_env->SetLongField(obj, Global::field_ZEArrayMemory_address, (jlong)address);

	return obj;
}

JNIEXPORT jobject JNICALL Java_com_zekrom_164_ze_nat_ZEArrayMemory_map___3IZ
  (JNIEnv *_env, jclass clazz, jintArray array, jboolean wb) {
	jobject obj = _env->NewObject(Global::class_ZEArrayMemory, Global::ctor_ZEArrayMemory);

	_env->SetObjectField(obj, Global::field_ZEArrayMemory_array, array);
	_env->SetBooleanField(obj, Global::field_ZEArrayMemory_writeBack, wb);
	_env->SetIntField(obj, Global::field_ZEArrayMemory_type, ARRAYTYPE_INT);
	jint* address = _env->GetIntArrayElements(array, nullptr);
	_env->SetLongField(obj, Global::field_ZEArrayMemory_address, (jlong)address);

	return obj;
}

JNIEXPORT jobject JNICALL Java_com_zekrom_164_ze_nat_ZEArrayMemory_map___3JZ
  (JNIEnv *_env, jclass clazz, jlongArray array, jboolean wb) {
	jobject obj = _env->NewObject(Global::class_ZEArrayMemory, Global::ctor_ZEArrayMemory);

	_env->SetObjectField(obj, Global::field_ZEArrayMemory_array, array);
	_env->SetBooleanField(obj, Global::field_ZEArrayMemory_writeBack, wb);
	_env->SetIntField(obj, Global::field_ZEArrayMemory_type, ARRAYTYPE_LONG);
	jlong* address = _env->GetLongArrayElements(array, nullptr);
	_env->SetLongField(obj, Global::field_ZEArrayMemory_address, (jlong)address);

	return obj;
}

JNIEXPORT jobject JNICALL Java_com_zekrom_164_ze_nat_ZEArrayMemory_map___3FZ
  (JNIEnv *_env, jclass clazz, jfloatArray array, jboolean wb) {
	jobject obj = _env->NewObject(Global::class_ZEArrayMemory, Global::ctor_ZEArrayMemory);

	_env->SetObjectField(obj, Global::field_ZEArrayMemory_array, array);
	_env->SetBooleanField(obj, Global::field_ZEArrayMemory_writeBack, wb);
	_env->SetIntField(obj, Global::field_ZEArrayMemory_type, ARRAYTYPE_FLOAT);
	jfloat* address = _env->GetFloatArrayElements(array, nullptr);
	_env->SetLongField(obj, Global::field_ZEArrayMemory_address, (jlong)address);

	return obj;
}

JNIEXPORT jobject JNICALL Java_com_zekrom_164_ze_nat_ZEArrayMemory_map___3DZ
  (JNIEnv *_env, jclass clazz, jdoubleArray array, jboolean wb) {
	jobject obj = _env->NewObject(Global::class_ZEArrayMemory, Global::ctor_ZEArrayMemory);

	_env->SetObjectField(obj, Global::field_ZEArrayMemory_array, array);
	_env->SetBooleanField(obj, Global::field_ZEArrayMemory_writeBack, wb);
	_env->SetIntField(obj, Global::field_ZEArrayMemory_type, ARRAYTYPE_DOUBLE);
	jdouble* address = _env->GetDoubleArrayElements(array, nullptr);
	_env->SetLongField(obj, Global::field_ZEArrayMemory_address, (jlong)address);

	return obj;
}

JNIEXPORT void JNICALL Java_com_zekrom_164_ze_nat_ZEArrayMemory_close
  (JNIEnv *_env, jobject obj) {
	jobject array = _env->GetObjectField(obj, Global::field_ZEArrayMemory_array);
	bool wb = _env->GetBooleanField(obj, Global::field_ZEArrayMemory_writeBack);
	jlong address = _env->GetLongField(obj, Global::field_ZEArrayMemory_address);
	jint mode = wb ? 0 : JNI_ABORT;
	jint type = _env->GetIntField(obj, Global::field_ZEArrayMemory_type);

	switch(type) {
	case ARRAYTYPE_BOOLEAN:
		_env->ReleaseBooleanArrayElements((jbooleanArray)array, (jboolean*)address, mode);
		break;
	case ARRAYTYPE_BYTE:
		_env->ReleaseByteArrayElements((jbyteArray)array, (jbyte*)address, mode);
		break;
	case ARRAYTYPE_SHORT:
		_env->ReleaseShortArrayElements((jshortArray)array, (jshort*)address, mode);
		break;
	case ARRAYTYPE_CHAR:
		_env->ReleaseCharArrayElements((jcharArray)array, (jchar*)address, mode);
		break;
	case ARRAYTYPE_INT:
		_env->ReleaseIntArrayElements((jintArray)array, (jint*)address, mode);
		break;
	case ARRAYTYPE_LONG:
		_env->ReleaseLongArrayElements((jlongArray)array, (jlong*)address, mode);
		break;
	case ARRAYTYPE_FLOAT:
		_env->ReleaseFloatArrayElements((jfloatArray)array, (jfloat*)address, mode);
		break;
	case ARRAYTYPE_DOUBLE:
		_env->ReleaseDoubleArrayElements((jdoubleArray)array, (jdouble*)address, mode);
		break;
	}

	_env->SetLongField(obj, Global::field_ZEArrayMemory_address, 0);
}
