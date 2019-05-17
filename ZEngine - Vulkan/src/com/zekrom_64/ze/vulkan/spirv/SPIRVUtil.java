package com.zekrom_64.ze.vulkan.spirv;

import java.nio.charset.StandardCharsets;

public class SPIRVUtil {
	
	public static int stringWordLength(String s) {
		byte[] utf8 = s.getBytes(StandardCharsets.UTF_8);
		int count = utf8.length / 4;
		if ((utf8.length % 4) != 0) count++;
		return count;
	}
	
}
