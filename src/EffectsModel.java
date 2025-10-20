import be.tarsos.dsp.*;
//import java.io.File;
import java.io.IOException;

import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.io.jvm.AudioPlayer;
//import be.tarsos.dsp.io.jvm.WaveformWriter;
import be.tarsos.dsp.filters.HighPass;
//import be.tarsos.dsp.filters.LowPassFS;
import be.tarsos.dsp.filters.LowPassSP;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
//import javax.sound.sampled.*;
//import javax.sound.sampled.AudioInputStream;
//import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import be.tarsos.dsp.effects.DelayEffect;
//import be.tarsos.dsp.resample.Resampler;
//import be.tarsos.dsp.synthesis.AmplitudeLFO;
import be.tarsos.dsp.resample.RateTransposer;
//import be.tarsos.dsp.io.TarsosDSPAudioFormat;
//import be.tarsos.dsp.io.TarsosDSPAudioInputStream;

/* intVariables:
 * 		0: Wave
 * 		1: BitDepth
 * floatVariables:
 * 		0:pre
 * 		1:post
 * 		2:gain
 * 		3:clip
 * 		4:pitch
 * 		5:humGain
 * 		6:popsGain
 * 		7:clicksGain
 * 		8:rumbleGain
 * 		9:hpFrequency
 * 		10:lpFrequency
 * 		11:cracklesGain
 * 		12:vinylNoiseGain
 * doubleVariables:
 * 		0:ratio
 * 		1:decay
 * 		2:waveFreq
 * 		3:threshold
 * 		4:noiseGain
 * 		5:echoLength
 * 		6:sampleRate
 * 		7:attackCoeff
 * 		8:releaseCoeff
 * 		9:scaleParameter
 * 		10:makeupGainLinear
 * booleanVariables:
 * 		0:anti-alias
 * 		1:flutterOn
 */


public class EffectsModel {
	
	public static final int WAVE = 0;
	public static final int BITDEPTH = 1;
	public static final int PRE = 2;
	public static final int POST = 3;
	public static final int GAIN = 4;
	public static final int CLIP = 5;
	public static final int PITCH = 6;
	public static final int HUMGAIN = 7;
	public static final int POPSGAIN = 8;
	public static final int CLICKSGAIN = 9;
	public static final int RUMBLEGAIN = 10;
	public static final int HPFREQ = 11;
	public static final int LPFREQ = 12;
	public static final int CRACKLESGAIN = 13;
	public static final int VINYLNOISEGAIN = 14;
	public static final int RATIO = 15;
	public static final int DECAY = 16;
	public static final int WAVEFREQ = 17;
	public static final int THRESHOLD = 18;
	public static final int NOISEGAIN = 19;
	public static final int ECHOLENGTH = 20;
	public static final int SAMPLERATE = 21;
	public static final int ATTACK = 22;
	public static final int RELEASE = 23;
	public static final int SCALEPARAM = 24;
	public static final int MAKEUPGAIN = 25;
	public static final int LOOPSIZE = 26;
	public static final int ANTIALIAS = 27;
	public static final int FLUTTERON = 28;
	public static final int LOOPERON = 29;
	
	private String fileName = "input2.wav";
	
	private boolean isProcessing;
	private int sampleRate = 44100;
    private final int bufferSize = 1024;
    private final int overlap = 0;
	private int selected;
	private boolean mute;
	private Thread thread;
	private Sample[] sampleValues;
	private AudioDispatcher outputDispatcher;
	private GainProcessor muteGain;
	
	
//	MOD VARIABLES--=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=
	private HighPass highPass;
    private LowPassSP lowPass;
    private PitchShifter pitchShifter;
    private RateTransposer rateTransposer;
    private GainProcessor gain;
    
//  FLY-LOW VARIABLES--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private LowPassSP pre;
    private LowPassSP post;
    private BitDepthProcessor bitDepth;
    private Clipper clipper;
    private DenkabeNoiseGenerator noiseGain;
    
//  TIMBRE / DEL-REV VAIABLES--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private DenkabeAmplitudeLFO lfo;
    private DelayEffect delay;
    private LoopProcessor looper;
	
//	COMPRESSOR VARIABLES--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
	private Compressor compressor;
	
//	VINYL VARIABLES--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	private AudioDispatcher[] noiseDispatchers;
	private AudioDispatcher vinylNoiseDispatcher;
	private AudioDispatcher humDispatcher;	
	private AudioDispatcher rumbleDispatcher;	
	private AudioDispatcher clicksDispatcher;
	private AudioDispatcher popsDispatcher;
	private AudioDispatcher cracklesDispatcher;
    private GainProcessor vinylNoiseGain;
    private GainProcessor humGain;
    private GainProcessor rumbleGain;
    private GainProcessor clicksGain;
    private GainProcessor popsGain;
    private GainProcessor cracklesGain;

    
	public EffectsModel() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		
		if (fileName.equals(null)) fileName = "";
		
		mute = false;
		selected = 0;
		sampleValues = new Sample[4];
		
		for (int i = 0; i < sampleValues.length; i++) {
			sampleValues[i] = new Sample();
		}
		
		muteGain = new GainProcessor(1);
		
		highPass = new HighPass(0, sampleRate);
		lowPass = new LowPassSP(0, sampleRate);
		pitchShifter = new PitchShifter(0, sampleRate, bufferSize, overlap);
		rateTransposer = new RateTransposer(1.0);
		gain = new GainProcessor(0);
		
		pre = new LowPassSP(0, sampleRate);
		bitDepth = new BitDepthProcessor();
    	clipper = new Clipper(0);
    	noiseGain = new DenkabeNoiseGenerator(0);
    	post = new LowPassSP(0, sampleRate);
    	
    	lfo =  new DenkabeAmplitudeLFO(0, 0, 0);
    	delay =  new DelayEffect(sampleValues[selected].getVariable(20, 0), sampleValues[selected].getVariable(16, 0), sampleRate);
    	looper = new LoopProcessor(sampleRate, 0);
    	
    	compressor = new Compressor(0, 0, 0, 0, 0, sampleRate);
		
    	noiseDispatchers = new AudioDispatcher[6];
    	for (int i = 0; i < noiseDispatchers.length; i++) {
    		noiseDispatchers[i] = AudioDispatcherFactory.fromPipe("VINYLNOISE1.wav", sampleRate, bufferSize, overlap);
    	}
		vinylNoiseGain = new GainProcessor(0);
		humGain = new GainProcessor(0);
		rumbleGain = new GainProcessor(0);
		clicksGain = new GainProcessor(0);
		popsGain = new GainProcessor(0);
		cracklesGain = new GainProcessor(0);
		
		for (int i = 0; i < 30; i++) {
			this.updateProcessor(i);
		}
//		outputDispatcher = AudioDispatcherFactory.fromPipe("input2.wav", sampleRate, bufferSize, overlap);
		
		isProcessing = false;
		
		this.process();
	}
	
	public void process() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
	    if (isProcessing) return;
	    isProcessing = true;
	    
	    if (outputDispatcher != null) outputDispatcher.stop();
	    
	    outputDispatcher = AudioDispatcherFactory.fromPipe(fileName, sampleRate, bufferSize, overlap);
//	    new Thread(outputDispatcher::run).start();

	    
	    outputDispatcher.addAudioProcessor(muteGain);
	    

	    // HP, LP, Pitch, Gain
	    if (sampleValues[selected].getOnOff()[0]) {
	        outputDispatcher.addAudioProcessor(highPass);
	        outputDispatcher.addAudioProcessor(lowPass);
//	        outputDispatcher.addAudioProcessor(pitchShifter);
	        outputDispatcher.addAudioProcessor(rateTransposer);
	        outputDispatcher.addAudioProcessor(gain);
	    }

	    // BitCrusher, Clipper, Noise Mod
	    if (sampleValues[selected].getOnOff()[1]) {
	        outputDispatcher.addAudioProcessor(pre);
	        outputDispatcher.addAudioProcessor(bitDepth);
	        outputDispatcher.addAudioProcessor(clipper);
	        outputDispatcher.addAudioProcessor(noiseGain);
	        outputDispatcher.addAudioProcessor(post);
	    }

	    // Flutter + Delay
	    if (sampleValues[selected].getOnOff()[2]) {
	        if (sampleValues[selected].getBooleanVariable(FLUTTERON)) {
	            outputDispatcher.addAudioProcessor(lfo);
	        }
	        outputDispatcher.addAudioProcessor(delay);
	        outputDispatcher.addAudioProcessor(looper);
	    }

	    // Compressor
	    if (sampleValues[selected].getOnOff()[3]) {
	        outputDispatcher.addAudioProcessor(compressor);
	    }
	    
	    // EXTRA NOISE
	    if (sampleValues[selected].getOnOff()[4]) {
	    	
	    	new Thread(() -> {
	    	    try {
	    	        while (true) {
	    	        	vinylNoiseDispatcher = AudioDispatcherFactory.fromPipe("VINYLNOISE1.wav", sampleRate, bufferSize, overlap);
	    	        	vinylNoiseDispatcher.addAudioProcessor(vinylNoiseGain);	
	    	        	vinylNoiseDispatcher.addAudioProcessor(
	    	        			new AudioPlayer(new AudioFormat(sampleRate, 16, 1, true, false)));
	    	        	vinylNoiseDispatcher.run();
	    	        }
	    	    } catch (Exception e) {
	    	        e.printStackTrace();
	    	    }
	    	}).start();
	    	
	    	new Thread(() -> {
	    	    try {
	    	        while (true) {
	    	        	humDispatcher = AudioDispatcherFactory.fromPipe("VINYLHUM1.wav", sampleRate, bufferSize, overlap);
	    	        	humDispatcher.addAudioProcessor(humGain);	
	    	        	humDispatcher.addAudioProcessor(
	    	        			new AudioPlayer(new AudioFormat(sampleRate, 16, 1, true, false)));
	    	        	humDispatcher.run();
	    	        }
	    	    } catch (Exception e) {
	    	        e.printStackTrace();
	    	    }
	    	}).start();
	    	
	    	new Thread(() -> {
	    	    try {
	    	        while (true) {
	    	        	rumbleDispatcher = AudioDispatcherFactory.fromPipe("VINYLRUMBLE1.wav", sampleRate, bufferSize, overlap);
	    	        	rumbleDispatcher.addAudioProcessor(rumbleGain);	
	    	        	rumbleDispatcher.addAudioProcessor(
	    	        			new AudioPlayer(new AudioFormat(sampleRate, 16, 1, true, false)));
	    	        	rumbleDispatcher.run();
	    	        }
	    	    } catch (Exception e) {
	    	        e.printStackTrace();
	    	    }
	    	}).start();  	
	    	
	    	new Thread(() -> {
	    	    try {
	    	        while (true) {
	    	        	clicksDispatcher = AudioDispatcherFactory.fromPipe("VINYLCLICKS1.wav", sampleRate, bufferSize, overlap);
	    	        	clicksDispatcher.addAudioProcessor(clicksGain);	
	    	        	clicksDispatcher.addAudioProcessor(
	    	        			new AudioPlayer(new AudioFormat(sampleRate, 16, 1, true, false)));
	    	        	clicksDispatcher.run();
	    	        }
	    	    } catch (Exception e) {
	    	        e.printStackTrace();
	    	    }
	    	}).start();  
	    	
	    	new Thread(() -> {
	    	    try {
	    	        while (true) {
	    	        	popsDispatcher = AudioDispatcherFactory.fromPipe("VINYLPOPS1.wav", sampleRate, bufferSize, overlap);
	    	        	popsDispatcher.addAudioProcessor(popsGain);	
	    	        	popsDispatcher.addAudioProcessor(
	    	        			new AudioPlayer(new AudioFormat(sampleRate, 16, 1, true, false)));
	    	        	popsDispatcher.run();
	    	        }
	    	    } catch (Exception e) {
	    	        e.printStackTrace();
	    	    }
	    	}).start();  
	    	
	    	new Thread(() -> {
	    	    try {
	    	        while (true) {
	    	        	cracklesDispatcher = AudioDispatcherFactory.fromPipe("VINYLCRACKLES1.wav", sampleRate, bufferSize, overlap);
	    	        	cracklesDispatcher.addAudioProcessor(cracklesGain);	
	    	        	cracklesDispatcher.addAudioProcessor(
	    	        			new AudioPlayer(new AudioFormat(sampleRate, 16, 1, true, false)));
	    	        	cracklesDispatcher.run();
	    	        }
	    	    } catch (Exception e) {
	    	        e.printStackTrace();
	    	    }
	    	}).start();  
	    	
	    }

	    // List available output devices (Mixers)
        Mixer.Info[] mixers = AudioSystem.getMixerInfo();
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, new AudioFormat(sampleRate, 16, 1, true, false));

        for (int i = 0; i < mixers.length; i++) {
            Mixer m = AudioSystem.getMixer(mixers[i]);
            if (m.isLineSupported(info)) {
                System.out.println(i + ": " + mixers[i].getName());
            } else {
                System.out.println(i + ": " + mixers[i].getName() + " (no playback support)");
            }
        }

        Mixer mixer = AudioSystem.getMixer(mixers[12]); // change index to your device
		outputDispatcher.addAudioProcessor(new DenkabeAudioPlayer(new AudioFormat(sampleRate, 16, 1, true, false), mixer));

		thread = new Thread(outputDispatcher);
		
		thread.start();
		
	    isProcessing = false;
	}
	
	public void mute() {
		if (mute) {
			muteGain.setGain(1);
		} else {
			gain.setGain(0);
		}		
		mute = !mute;
	}

	
	public void setSelected(int selected) {
		this.selected = selected;
	}
	
	public void updateProcessor(int index) {
		switch (index) {
			case WAVE:
				lfo.setWaveType((int) sampleValues[selected].getVariable(index, 0));
				break;
			case BITDEPTH:
				bitDepth.setBitDepth((int) sampleValues[selected].getVariable(index, 0));
				break;
			case PRE:
				pre.setFrequency((float) sampleValues[selected].getVariable(index, 0));
				break;
			case POST:
				post.setFrequency((float) sampleValues[selected].getVariable(index, 0));
				break;
			case GAIN:
				gain.setGain((float) sampleValues[selected].getVariable(index, 0));
				break;
			case CLIP:
				clipper.setThreshold((float) sampleValues[selected].getVariable(index, 0));
				break;
			case PITCH:
				rateTransposer.setFactor((float) sampleValues[selected].getVariable(index, 0));
				break;
			case HUMGAIN:
				humGain.setGain((float) sampleValues[selected].getVariable(index, 0));
				break;
			case POPSGAIN:
				popsGain.setGain((float) sampleValues[selected].getVariable(index, 0));
				break;
			case CLICKSGAIN:
				clicksGain.setGain((float) sampleValues[selected].getVariable(index, 0));
				break;
			case RUMBLEGAIN:
				rumbleGain.setGain((float) sampleValues[selected].getVariable(index, 0));
				break;
			case HPFREQ:
				highPass.setFrequency((float) sampleValues[selected].getVariable(index, 0));	
				break;
			case LPFREQ:	
				lowPass.setFrequency((float) sampleValues[selected].getVariable(index, 0));	
				break;
			case CRACKLESGAIN:
				cracklesGain.setGain((float) sampleValues[selected].getVariable(index, 0));
				break;
			case VINYLNOISEGAIN:
				vinylNoiseGain.setGain((float) sampleValues[selected].getVariable(index, 0));
				break;
			case RATIO:
				compressor.setRatio(sampleValues[selected].getVariable(index, 0));
				break;
			case DECAY:
				delay.setDecay(sampleValues[selected].getVariable(index, 0));
				break;
			case WAVEFREQ:
				lfo.setFrequency(sampleValues[selected].getVariable(index, 0));
				break;
			case THRESHOLD:
				compressor.setThreshold(sampleValues[selected].getVariable(index, 0));
				break;
			case NOISEGAIN:
				noiseGain.setGain(sampleValues[selected].getVariable(index, 0));
				break;
			case ECHOLENGTH:
				delay.setEchoLength(sampleValues[selected].getVariable(index, 0));
				break;
			case SAMPLERATE:
				sampleRate = (int) sampleValues[selected].getVariable(index, 0);
				break;
			case ATTACK:
				compressor.setAttack(sampleValues[selected].getVariable(index, 0));
				break;
			case RELEASE:
				compressor.setRelease(sampleValues[selected].getVariable(index, 0));
				break;
			case SCALEPARAM:
				lfo.setScaleParameter(sampleValues[selected].getVariable(index, 0));
				break;
			case MAKEUPGAIN:
				compressor.setMakeUpGain(sampleValues[selected].getVariable(index, 0));
				break;
			case LOOPSIZE:
				looper.setLoopLength(sampleValues[selected].getVariable(index, 0));
				break;
			case LOOPERON:
				looper.setLooping(sampleValues[selected].getBooleanVariable(index));
				break;
		}	
	}
    
    public void changeVariable(int index, double change) {
    	if (sampleValues[selected].getVariable(index, 0) + change >= sampleValues[selected].getVariable(index, 1) && sampleValues[selected].getVariable(index, 0) + change <= sampleValues[selected].getVariable(index, 2)) {
    		sampleValues[selected].setVariable(index, sampleValues[selected].getVariable(index, 0) + change);
    	} else if (sampleValues[selected].getVariable(index, 0) + change <= sampleValues[selected].getVariable(index, 1) && sampleValues[selected].getVariable(index, 0) != sampleValues[selected].getVariable(index, 1)) {
    		sampleValues[selected].setVariable(index, sampleValues[selected].getVariable(index, 1));
    	} else if (sampleValues[selected].getVariable(index, 0) + change >= sampleValues[selected].getVariable(index, 2) && sampleValues[selected].getVariable(index, 0) != sampleValues[selected].getVariable(index, 2)) {
    		sampleValues[selected].setVariable(index, sampleValues[selected].getVariable(index, 2));
    	}
    	updateProcessor(index);
    }
    
    public void changeVariable(int index, boolean change) {
    	sampleValues[selected].setVariable(index, change);
    	updateProcessor(index);
    }
    
    public double getVariable(int index) {
    	return sampleValues[selected].getVariable(index, 0);
    }
    
    public boolean getBooleanVariable(int index) {
    	return sampleValues[selected].getBooleanVariable(index);
    }
   
}

