import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;

public class Clipper implements AudioProcessor {
    private float threshold; // e.g., 0.5f for ±0.5 amplitude

    public Clipper(float threshold) {
        this.threshold = Math.max(0, Math.min(threshold, 1f));
    }

    @Override
    public boolean process(AudioEvent audioEvent) {
        float[] buffer = audioEvent.getFloatBuffer();
        for (int i = 0; i < buffer.length; i++) {
            if (buffer[i] > threshold) buffer[i] = threshold;
            else if (buffer[i] < -threshold) buffer[i] = -threshold;
        }
        return true;
    }
    
    public void setThreshold(float newThreshold) {
    	threshold = newThreshold;
    }

    @Override
    public void processingFinished() {}
}

