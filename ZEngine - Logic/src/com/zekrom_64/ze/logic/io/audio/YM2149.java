package com.zekrom_64.ze.logic.io.audio;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.MixerMono;
import com.jsyn.unitgen.SquareOscillator;
import com.zekrom_64.ze.logic.IDevice.IDeviceState;
import com.zekrom_64.ze.logic.Port8;
import com.zekrom_64.ze.logic.io.audio.YM2149.YM2149State;
import com.zekrom_64.ze.logic.synth.LogicSynth;
import com.zekrom_64.ze.logic.synth.NoiseOscillator;

/** Emulates a YM2149 sound synthesis chip. 
 * 
 * @author Zekrom_64
 *
 */
public class YM2149 implements IDeviceAudioOutput<YM2149State> {
	
	private final Synthesizer synth;
	
	private SquareOscillator a;
	private SquareOscillator b;
	private SquareOscillator c;
	private NoiseOscillator noise;
	
	private MixerMono mixerA;
	private MixerMono mixerB;
	private MixerMono mixerC;
	
	public final UnitOutputPort outputA;
	public final UnitOutputPort outputB;
	public final UnitOutputPort outputC;
	
	public final Port8 portA = new Port8();
	public final Port8 portB = new Port8();
	
	public YM2149() {
		synth = LogicSynth.allocSynth();
		
		synth.add(a = new SquareOscillator());
		synth.add(b = new SquareOscillator());
		synth.add(c = new SquareOscillator());
		synth.add(noise = new NoiseOscillator());
		
		synth.add(mixerA = new MixerMono(2));
		synth.add(mixerB = new MixerMono(2));
		synth.add(mixerC = new MixerMono(2));
		
		outputA = mixerA.output;
		outputB = mixerB.output;
		outputC = mixerC.output;
	}
	
	public static class YM2149State implements IDeviceState {

		public short channelA;
		public short channelB;
		public short channelC;
		public byte noiseFreq;
		public byte settings;
		public byte levelA;
		public byte levelB;
		public byte levelC;
		public short envelopeFreq;
		public byte envelopeShape;
		public byte portA;
		public byte portB;
		
		@Override
		public void write(DataOutput out) throws IOException {
			out.writeShort(channelA);
			out.writeShort(channelB);
			out.writeShort(channelC);
			out.writeByte(noiseFreq);
			out.writeByte(settings);
			out.writeByte(levelA);
			out.writeByte(levelB);
			out.writeByte(levelC);
			out.writeShort(envelopeFreq);
			out.writeByte(envelopeShape);
			out.writeByte(portA);
			out.writeByte(portB);
		}

		@Override
		public void read(DataInput in) throws IOException {
			channelA = in.readShort();
			channelB = in.readShort();
			channelC = in.readShort();
			noiseFreq = in.readByte();
			settings = in.readByte();
			levelA = in.readByte();
			levelB = in.readByte();
			levelC = in.readByte();
			envelopeFreq = in.readShort();
			envelopeShape = in.readByte();
			portA = in.readByte();
			portB = in.readByte();
		}
		
		@Override
		public YM2149State clone() {
			YM2149State newstate = new YM2149State();
			newstate.channelA = channelA;
			newstate.channelB = channelB;
			newstate.channelC = channelC;
			newstate.noiseFreq = noiseFreq;
			newstate.settings = settings;
			newstate.levelA = levelA;
			newstate.levelB = levelB;
			newstate.levelC = levelC;
			newstate.envelopeFreq = envelopeFreq;
			newstate.envelopeShape = envelopeShape;
			newstate.portA = portA;
			newstate.portB = portB;
			return newstate;
		}
		
	}
	
	private YM2149State state = new YM2149State();

	@Override
	public IDeviceState saveState() {
		return state.clone();
	}

	@Override
	public void loadState(YM2149State state) {
		if (state!=null) this.state = state;
	}
	
	@Override
	protected void finalize() {
		LogicSynth.freeSynth(synth);
	}

	@Override
	public void write(int address, byte data) {
		
	}

	@Override
	public byte read(int address) {
		return 0;
	}

	@Override
	public UnitInputPort[] getInputs() {
		return new UnitInputPort[0];
	}

	@Override
	public UnitOutputPort[] getOutputs() {
		return new UnitOutputPort[] {
			outputA,
			outputB,
			outputC
		};
	}

}
