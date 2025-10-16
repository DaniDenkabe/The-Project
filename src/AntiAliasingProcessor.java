import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.filters.LowPassFS;

public class AntiAliasingProcessor implements AudioProcessor {

    private LowPassFS preFilter;
    private LowPassFS postFilter;

    public AntiAliasingProcessor(float sampleRate, float preCutoff, float postCutoff) {
        preFilter = new LowPassFS(preCutoff, sampleRate);
        postFilter = new LowPassFS(postCutoff, sampleRate);
    }

    @Override
    public boolean process(AudioEvent audioEvent) {
        float[] buffer = audioEvent.getFloatBuffer();

        // Apply pre-filter
        preFilter.process(audioEvent);

        // You can insert an effect here if you want to sandwich it
        // e.g., apply bitcrusher or pitch shift here

        // Apply post-filter
        postFilter.process(audioEvent);

        return true;
    }

    @Override
    public void processingFinished() {
        preFilter.processingFinished();
        postFilter.processingFinished();
    }
}

