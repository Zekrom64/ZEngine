package com.zekrom_64.ze.libav.enums;

import java.util.Arrays;
import java.util.Iterator;

import org.bridj.IntValuedEnum;

public enum AVAudioServiceType implements IntValuedEnum<AVAudioServiceType> {
	AV_AUDIO_SERVICE_TYPE_MAIN,
	AV_AUDIO_SERVICE_TYPE_EFFECTS,
	AV_AUDIO_SERVICE_TYPE_VISUALLY_IMPAIRED,
	AV_AUDIO_SERVICE_TYPE_HEARING_IMPAIRED,
	AV_AUDIO_SERVICE_TYPE_DIALOGUE,
	AV_AUDIO_SERVICE_TYPE_COMMENTARY,
	AV_AUDIO_SERVICE_TYPE_EMERGENCY,
	AV_AUDIO_SERVICE_TYPE_VOICE_OVER,
	AV_AUDIO_SERVICE_TYPE_KARAOKE;

	@Override
	public long value() {
		return ordinal();
	}

	@Override
	public Iterator<AVAudioServiceType> iterator() {
		return Arrays.asList(values()).iterator();
	}

}
