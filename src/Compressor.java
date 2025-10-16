import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;

public class Compressor implements AudioProcessor {

    private  double threshold; // in dB
    private  double ratio; // compression ratio
    private  double attack; // in seconds
    private  double release; // in seconds
    private  double makeUpGain; // in dB

    private double gain; // current gain
    private float envelope; // current envelope
    private final double sampleRate;

    public Compressor(double threshold, double ratio, double attack, double release, double makeUpGain, double sampleRate) {
        this.threshold = threshold;
        this.ratio = ratio;
        this.attack = attack;
        this.release = release;
        this.makeUpGain = makeUpGain;
        this.sampleRate = sampleRate;

        this.gain = 1.0f;
        this.envelope = 0.0f;
    }

    @Override
    public boolean process(AudioEvent audioEvent) {
    	float[] audioBuffer = audioEvent.getFloatBuffer();
        for (int i = 0; i < audioBuffer.length; i++) {
            float sample = audioBuffer[i];

            // Calculate the envelope
            float absSample = Math.abs(sample);
            if (absSample > envelope) {
                envelope = absSample;
            } else {
                envelope *= Math.exp(-1.0f / (release * sampleRate)); // Release
            }

            // Calculate gain reduction
            double gainReduction;
            if (envelope > thresholdToLinear(threshold)) {
                float excess = envelope - thresholdToLinear(threshold);
                gainReduction = 1.0f - (excess / (1.0f + excess / ratio));
            } else {
                gainReduction = 1.0f; // No gain reduction applied
            }

            // Apply gain and make-up gain
            gain = gainReduction * (float) Math.pow(10, makeUpGain / 20);
            audioBuffer[i] *= gain;
        }
        return true; // Processed successfully
    }

    private float thresholdToLinear(double threshold) {
        return (float) Math.pow(10, threshold / 20);
    }

    @Override
    public void processingFinished() {
        // No specific action required
    }

	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

	public double getAttack() {
		return attack;
	}

	public void setAttack(double attack) {
		this.attack = attack;
	}

	public double getRelease() {
		return release;
	}

	public void setRelease(double release) {
		this.release = release;
	}

	public double getMakeUpGain() {
		return makeUpGain;
	}

	public void setMakeUpGain(double makeUpGain) {
		this.makeUpGain = makeUpGain;
	}
    
    
    
}
