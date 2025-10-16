import be.tarsos.dsp.*;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.io.jvm.AudioPlayer;

import java.io.File;

import javax.sound.sampled.AudioFormat;

public class TestAudio {
    public static void main(String[] args) throws Exception {
    	System.out.println("Starting");
    	File file = new File("sample16.wav");
    	AudioDispatcher dispatcher = AudioDispatcherFactory.fromPipe("input2.wav", 44100, 1024, 0);
    	dispatcher.addAudioProcessor(new AudioPlayer(new AudioFormat(44100, 16, 1, true, false)));
    	dispatcher.run();
        System.out.println("Finished");
    }
}
