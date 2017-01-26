package com.zekrom_64.ze.logic.synth;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;

public class LogicSynth {
	
	private static final Synthesizer masterSynth;
	
	static {
		masterSynth = JSyn.createSynthesizer();
		masterSynth.start();
		Runtime.getRuntime().addShutdownHook(new Thread() {
		
			@Override
			public void run() {
				masterSynth.stop();
			}
			
		});
	}
	
	public static boolean useMultiThreading = false;
	
	public static Synthesizer allocSynth() {
		if (useMultiThreading) {
			Synthesizer s = JSyn.createSynthesizer();
			s.start();
			return s;
		} else return masterSynth;
	}
	
	public static void freeSynth(Synthesizer s) {
		if (s!=masterSynth) s.stop();
	}
	
}
