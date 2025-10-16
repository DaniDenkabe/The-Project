import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;

/**
 * A simple Schroeder/Moorer-style reverb for TarsosDSP.
 * 
 * Parameters:
 * - roomSize: how big the virtual space feels (0.0 to 1.0)
 * - damping: how much high frequency energy is absorbed (0.0 = bright, 1.0 = dark)
 * - wetLevel: mix between dry and wet sound (0.0 = dry only, 1.0 = fully wet)
 */
public class ReverbProcessor implements AudioProcessor {

    private final CombFilter[] combs;
    private final AllPassFilter[] allpasses;
    private float wetLevel;
    private float dryLevel;

    public ReverbProcessor(float roomSize, float damping, float wetLevel) {
        this.wetLevel = wetLevel;
        this.dryLevel = 1.0f - wetLevel;

        // Comb filters (reverberation tail)
        int[] combDelays = {1116, 1188, 1277, 1356, 1422, 1491, 1557, 1617};
        combs = new CombFilter[combDelays.length];
        for (int i = 0; i < combs.length; i++) {
            combs[i] = new CombFilter(combDelays[i], damping, roomSize);
        }

        // All-pass filters (diffusion)
        int[] allpassDelays = {225, 341, 441, 556};
        allpasses = new AllPassFilter[allpassDelays.length];
        for (int i = 0; i < allpasses.length; i++) {
            allpasses[i] = new AllPassFilter(allpassDelays[i]);
        }
    }

    @Override
    public boolean process(AudioEvent audioEvent) {
        float[] buffer = audioEvent.getFloatBuffer();
        for (int i = 0; i < buffer.length; i++) {
            float input = buffer[i];

            // Process comb filters in parallel
            float out = 0f;
            for (CombFilter comb : combs) {
                out += comb.process(input);
            }
            out /= combs.length;

            // Pass through allpass filters in series
            for (AllPassFilter ap : allpasses) {
                out = ap.process(out);
            }

            // Mix wet/dry
            buffer[i] = dryLevel * input + wetLevel * out;
        }
        return true;
    }

    @Override
    public void processingFinished() {
        // Nothing to clean up
    }

    // ==============================
    // --- Internal Filter Classes ---
    // ==============================

    private static class CombFilter {
        private final float[] buffer;
        private int index = 0;
        private final float feedback;
        private final float damping;
        private float filterStore = 0;

        CombFilter(int delay, float damping, float feedback) {
            buffer = new float[delay];
            this.damping = damping;
            this.feedback = feedback;
        }

        float process(float input) {
            float output = buffer[index];
            filterStore = (output * (1 - damping)) + (filterStore * damping);
            buffer[index] = input + filterStore * feedback;
            index = (index + 1) % buffer.length;
            return output;
        }
    }

    private static class AllPassFilter {
        private final float[] buffer;
        private int index = 0;
        private final float feedback = 0.5f;

        AllPassFilter(int delay) {
            buffer = new float[delay];
        }

        float process(float input) {
            float bufout = buffer[index];
            float output = -input + bufout;
            buffer[index] = input + (bufout * feedback);
            index = (index + 1) % buffer.length;
            return output;
        }
    }

	public float getWetLevel() {
		return wetLevel;
	}

	public void setWetLevel(float wetLevel) {
		this.wetLevel = wetLevel;
	}

	public float getDryLevel() {
		return dryLevel;
	}

	public void setDryLevel(float dryLevel) {
		this.dryLevel = dryLevel;
	}
    
    
}
