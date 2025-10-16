

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;

public class DenkabeNoiseGenerator implements AudioProcessor{
	
	private double gain;
	
	public DenkabeNoiseGenerator(){
		this(1.0);
	}
	
	public DenkabeNoiseGenerator(double gain){
		this.gain = gain;
	}

	@Override
	public boolean process(AudioEvent audioEvent) {
		float[] buffer = audioEvent.getFloatBuffer();
		for(int i = 0 ; i < buffer.length ; i++){
			buffer[i] += (float) (Math.random() * gain);
		}
		return true;
	}
	
	public void setGain(double gain) {
		this.gain = gain;
	}

	@Override
	public void processingFinished() {
	}
	
	

}
