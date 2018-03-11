/*
 * main.cpp
 *
 *  Created on: Apr 1, 2017
 *      Author: Zekrom_64
 */

#include <main.hpp>
#include <iostream>

#define TRY_FIND_CLASS(VAR,NAME)  VAR = nullptr; \
		VAR = env->FindClass(NAME); \
		if (VAR == nullptr) { \
			std::cerr << "[ZEngine][Native]: Failed to get class \"" << NAME << "\"!" << std::endl; \
			return -1; \
		}

#define TRY_FIND_FIELD(VAR,CLAZZ,NAME,SIG) VAR = nullptr; \
	VAR = env->GetFieldID(CLAZZ,NAME,SIG); \
	if (VAR == nullptr) { \
		std::cerr << "[ZEngine][Native]: Failed to get field \"" << NAME << "\" of signature \"" << SIG << "\"!" << std::endl; \
		return -1; \
	}

#define TRY_FIND_METHOD(VAR,CLAZZ,NAME,SIG) VAR = nullptr; \
	VAR = env->GetMethodID(CLAZZ,NAME,SIG); \
	if (VAR == nullptr) { \
		std::cerr << "[ZEngine][Native]: Failed to get method \"" << NAME << "\" of signature \"" << SIG << "\"!" << std::endl; \
		return -1;  \
	}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
	JNIEnv* env;
	if (vm->GetEnv((void**)&env, JNI_VERSION_1_8) != JNI_OK) {
		std::cerr << "[ZEngine][Native]: Failed to get JNI environment!" << std::endl;
		return -1;
	}

	TRY_FIND_CLASS(Global::class_arrayBoolean, "[Z")
	TRY_FIND_CLASS(Global::class_arrayByte, "[B")
	TRY_FIND_CLASS(Global::class_arrayShort, "[S")
	TRY_FIND_CLASS(Global::class_arrayChar, "[C")
	TRY_FIND_CLASS(Global::class_arrayInt, "[I")
	TRY_FIND_CLASS(Global::class_arrayLong, "[J")
	TRY_FIND_CLASS(Global::class_arrayFloat, "[F")
	TRY_FIND_CLASS(Global::class_arrayDouble, "[D")

	TRY_FIND_CLASS(Global::class_ZEArrayMemory, "Lcom/zekrom_64/ze/nat/ZEArrayMemory;")

	TRY_FIND_CLASS(Global::class_NullPointerException, "Ljava/lang/NullPointerException;")
	TRY_FIND_CLASS(Global::class_IllegalArgumentException, "Ljava/lang/IllegalArgumentException;")

	TRY_FIND_FIELD(Global::field_ZEArrayMemory_address, Global::class_ZEArrayMemory, "address", "J")
	TRY_FIND_FIELD(Global::field_ZEArrayMemory_array, Global::class_ZEArrayMemory, "array", "Ljava/lang/Object;")
	TRY_FIND_FIELD(Global::field_ZEArrayMemory_writeBack, Global::class_ZEArrayMemory, "address", "Z")
	TRY_FIND_FIELD(Global::field_ZEArrayMemory_type, Global::class_ZEArrayMemory, "type", "I")

	TRY_FIND_METHOD(Global::ctor_ZEArrayMemory, Global::class_ZEArrayMemory, "<ctor>", "")

	return JNI_VERSION_1_8;
}

JNIEXPORT void JNICALL JNI_OnUnload(JavaVM *vm, void *reserved) {

}
