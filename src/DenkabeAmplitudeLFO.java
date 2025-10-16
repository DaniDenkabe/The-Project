/*
*      _______                       _____   _____ _____  
*     |__   __|                     |  __ \ / ____|  __ \ 
*        | | __ _ _ __ ___  ___  ___| |  | | (___ | |__) |
*        | |/ _` | '__/ __|/ _ \/ __| |  | |\___ \|  ___/ 
*        | | (_| | |  \__ \ (_) \__ \ |__| |____) | |     
*        |_|\__,_|_|  |___/\___/|___/_____/|_____/|_|     
*                                                         
* -------------------------------------------------------------
*
* TarsosDSP is developed by Joren Six at IPEM, University Ghent
*  
* -------------------------------------------------------------
*
*  Info: http://0110.be/tag/TarsosDSP
*  Github: https://github.com/JorenSix/TarsosDSP
*  Releases: http://0110.be/releases/TarsosDSP/
*  
*  TarsosDSP includes modified source code by various authors,
*  for credits and info, see README.
* 
*/


import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;

public class DenkabeAmplitudeLFO implements AudioProcessor {
	
	private double frequency;
	private double scaleParameter;
	private int waveType;
	private double phase;
	private float gain;
	
	public DenkabeAmplitudeLFO(){
		this(1.5,0.75, 0);
	}
	
	public DenkabeAmplitudeLFO(double frequency, double scaleParameter, int waveType){
		this.frequency = frequency;
		this.scaleParameter = scaleParameter;
		this.waveType = waveType;
		phase = 0;
//		Thread t1 = new Thread(new Runnable() {
//		    @Override
//		    public void run() {
//		    	while(true) {
//		    		float newGain = gain;
//		    		System.out.println(newGain);
//		    	}
//		    }
//		});  
//		t1.start();
	}
	

	@Override
	public boolean process(AudioEvent audioEvent) {
		float[] buffer = audioEvent.getFloatBuffer();
		double sampleRate = audioEvent.getSampleRate();
		double twoPiF = 2 * Math.PI * frequency;
		double time = 0;
		int counter = 10000000;
		for(int i = 0 ; i < buffer.length ; i++){
			time = i / sampleRate;
			float gain = 0;
			if (waveType == 1) {
				gain =  (float) (scaleParameter * Math.sin(twoPiF * time + phase));
				if (gain < 0) {
					gain = 0;
				} else {
					gain = (float) scaleParameter;
				}
			} else if (waveType == 2) {
				if (counter >= 10000000) {
					gain =  (float) (Math.random() * scaleParameter);
					counter = 0;
				} else {
					gain = this.gain;
				}
				counter += 1;
			} else {
				gain =  (float) (scaleParameter * Math.sin(twoPiF * time + phase));
			}
			
			this.gain = gain;
			buffer[i] = gain * buffer[i];
		}
		
		phase = twoPiF * buffer.length / sampleRate + phase;
		return true;
	}
	
	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}
	
	public void setScaleParameter(double scaleParameter) {
		this.scaleParameter = scaleParameter;
	}
	
	public void setWaveType(int waveType) {
		this.waveType = waveType;
	}

	@Override
	public void processingFinished() {
	}

}
