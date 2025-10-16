import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;

public class FlutterEffect implements AudioProcessor {

    private float rateHz;     // Flutter frequency
    private float depth;      // Pitch modulation depth (fractional semitones)
    private float sampleRate;
    private float phase = 0;
    private float mix;
    private int wave;

    public FlutterEffect(float rateHz, float depth, float sampleRate, float mix, int wave) {
        this.rateHz = rateHz;
        this.depth = depth;
        this.sampleRate = sampleRate;
        this.mix = mix;
        this.wave = wave;
    }

    @Override
    public boolean process(AudioEvent audioEvent) {
        float[] buffer = audioEvent.getFloatBuffer();
        float[] modulated = new float[buffer.length];

        for (int i = 0; i < buffer.length; i++) {
            // Calculate LFO phase (sine wave)
            float lfo = (float) Math.sin(2 * Math.PI * phase);
            phase += rateHz / sampleRate;
            if (phase >= 1.0f) phase -= 1.0f;
            
            if (wave == 1 && lfo >= 0) {
            	lfo = 1;
            } else if (wave == 1 && lfo < 0) {
            	lfo = -1;
            } else if (wave == 2) {
            	lfo = (float) 0.9;
            }

            // Apply flutter: simulate small pitch modulation using resampling (simple nearest-neighbor)
            float flutterAmount = 1.0f + lfo * depth;
            int readIndex = (int)(i / flutterAmount);
            
            // Bounds check
            if (readIndex >= 0 && readIndex < buffer.length) {
                modulated[i] = buffer[readIndex];
            } else {
                modulated[i] = buffer[i]; // fallback
            }
            modulated[i] = buffer[readIndex];

            // Mix dry/wet
            buffer[i] = buffer[i] * (1 - mix) + modulated[i] * mix;
        }

        return true;
    }

    @Override
    public void processingFinished() {}
}
