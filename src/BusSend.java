import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;

public class BusSend implements AudioProcessor {
    private final float[] sharedBuffer;
    private final Object lock;

    public BusSend(float[] sharedBuffer, Object lock) {
        this.sharedBuffer = sharedBuffer;
        this.lock = lock;
    }

    @Override
    public boolean process(AudioEvent audioEvent) {
        float[] buffer = audioEvent.getFloatBuffer();
        synchronized (lock) {
            for (int i = 0; i < buffer.length && i < sharedBuffer.length; i++) {
                sharedBuffer[i] += buffer[i]; // sum into master buffer
            }
        }
        return true;
    }

    @Override
    public void processingFinished() {}
}

