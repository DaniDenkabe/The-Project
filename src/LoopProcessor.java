import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;

public class LoopProcessor implements AudioProcessor {
    private float[] circularBuffer;
    private float[] loopBuffer;
    private int loopSize;
    private int sampleRate;
    private int writeIndex = 0;
    private int readIndex = 0;
    private boolean isLooping = false;
    private boolean hasLoop = false;

    public LoopProcessor(int sampleRate, double loopLengthSeconds) {
        this.sampleRate = sampleRate;
        setLoopLength(loopLengthSeconds);
    }

    public void setLoopLength(double seconds) {
        loopSize = (int)(sampleRate * seconds);
        circularBuffer = new float[loopSize];
        loopBuffer = new float[loopSize];
        writeIndex = 0;
        readIndex = 0;
        hasLoop = false;
    }

    public void setLooping(boolean looping) {
        if (looping && !isLooping) {
            // Capture the most recent loopSize samples into loopBuffer
            for (int i = 0; i < loopSize; i++) {
                int index = (writeIndex - loopSize + i + loopSize) % loopSize;
                loopBuffer[i] = circularBuffer[index];
            }
            readIndex = 0;
            hasLoop = true;
        }
        isLooping = looping;
    }

    @Override
    public boolean process(AudioEvent audioEvent) {
        float[] buffer = audioEvent.getFloatBuffer();
        int bufferSize = buffer.length;

        for (int i = 0; i < bufferSize; i++) {
            if (!isLooping) {
                // Record incoming samples into the circular buffer
                circularBuffer[writeIndex] = buffer[i];
                writeIndex = (writeIndex + 1) % loopSize;
            } else if (hasLoop) {
                // Replace live audio with loop playback
                buffer[i] = loopBuffer[readIndex];
                readIndex = (readIndex + 1) % loopSize;
            }
        }

        return true;
    }

    @Override
    public void processingFinished() {}
}
