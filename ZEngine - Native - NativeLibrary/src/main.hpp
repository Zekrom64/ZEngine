/*
 * main.hpp
 *
 *  Created on: Apr 1, 2017
 *      Author: Zekrom_64
 */

#ifndef MAIN_HPP_
#define MAIN_HPP_


#include <jni.h>

class Global {
public:
	static jclass class_arrayBoolean;
	static jclass class_arrayByte;
	static jclass class_arrayShort;
	static jclass class_arrayChar;
	static jclass class_arrayInt;
	static jclass class_arrayLong;
	static jclass class_arrayFloat;
	static jclass class_arrayDouble;

	static jclass class_ZEArrayMemory;

	static jclass class_NullPointerException;
	static jclass class_IllegalArgumentException;

	static jfieldID field_ZEArrayMemory_array;
	static jfieldID field_ZEArrayMemory_address;
	static jfieldID field_ZEArrayMemory_writeBack;
	static jfieldID field_ZEArrayMemory_type;

	static jmethodID ctor_ZEArrayMemory;

};


#endif /* MAIN_HPP_ */
