import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoardIntake implements KeyListener {
	
	private EffectsModel model;
	
	public KeyBoardIntake() throws Exception {
		model = new EffectsModel(false);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		try {
			if (e.getKeyChar() == 'q') {
				System.out.println("Found");
				System.out.println("Q PRESSED");
			} 
			
			else if (e.getKeyChar() == 's') {

				model.changeVariable(model.HPFREQ, -30);
				System.out.println(model.getVariable(model.HPFREQ));
				System.out.println("S PRESSED");
			} else if (e.getKeyChar() == 'w') {

				model.changeVariable(model.HPFREQ, 30);
				System.out.println(model.getVariable(model.HPFREQ));
				System.out.println("W PRESSED");
			} 
			
			else if (e.getKeyChar() == 'z') {

				model.changeVariable(model.ANTIALIAS, !model.getBooleanVariable(0));
				System.out.println(model.getBooleanVariable(model.ANTIALIAS));
				System.out.println("Z PRESSED");
			} 
			
			else if (e.getKeyChar() == 'd') {

				model.changeVariable(model.LPFREQ, -30);
				System.out.println(model.getVariable(model.LPFREQ));
				System.out.println("D PRESSED");
			} else if (e.getKeyChar() == 'e') {

				model.changeVariable(model.LPFREQ, 30);
				System.out.println(model.getVariable(model.LPFREQ));
				System.out.println("E PRESSED");
			} 
			
			else if (e.getKeyChar() == 'r') {

				model.changeVariable(model.GAIN, 0.1);
				System.out.println(model.getVariable(model.GAIN));
				System.out.println("R PRESSED");
			} else if (e.getKeyChar() == 'f') {

				model.changeVariable(model.GAIN, -0.1);
				System.out.println(model.getVariable(model.GAIN));
				System.out.println("F PRESSED");
			}
			
			else if (e.getKeyChar() == 't') {

				model.changeVariable(model.PITCH, 0.1);
				System.out.println(model.getVariable(model.PITCH));
				System.out.println("T PRESSED");
			} else if (e.getKeyChar() == 'g') {

				model.changeVariable(model.PITCH, -0.1);
				System.out.println(model.getVariable(model.PITCH));
				System.out.println("G PRESSED");
			}
			
			else if (e.getKeyChar() == 'y') {

				model.changeVariable(model.PRE, 50);
				System.out.println(model.getVariable(model.PRE));
				System.out.println("Y PRESSED");
			} else if (e.getKeyChar() == 'h') {

				model.changeVariable(model.PRE, -50);
				System.out.println(model.getVariable(model.PRE));
				System.out.println("H PRESSED");
			}
			
			else if (e.getKeyChar() == 'u') {

				model.changeVariable(model.POST, 50);
				System.out.println(model.getVariable(model.POST));
				System.out.println("U PRESSED");
			} 
			
			else if (e.getKeyChar() == 'j') {

				model.changeVariable(model.POST, -50);
				System.out.println(model.getVariable(model.POST));
				System.out.println("J PRESSED");
			}
			
			else if (e.getKeyChar() == 'i') {

				model.changeVariable(model.BITDEPTH, 1);
				System.out.println(model.getVariable(model.BITDEPTH));
				System.out.println("I PRESSED");
			}
			
			else if (e.getKeyChar() == 'k') {

				model.changeVariable(model.BITDEPTH, -1);
				System.out.println(model.getVariable(model.BITDEPTH));
				System.out.println("K PRESSED");
			}
			
			else if (e.getKeyChar() == 'o') {

				model.changeVariable(model.CLIP, 0.05);
				System.out.println(model.getVariable(model.CLIP));
				System.out.println("O PRESSED");
			}
			
			else if (e.getKeyChar() == 'l') {

				model.changeVariable(model.CLIP, -0.05);
				System.out.println(model.getVariable(model.CLIP));
				System.out.println("L PRESSED");
			}
			
			else if (e.getKeyChar() == 'p') {

				model.changeVariable(model.SAMPLERATE, 1000);
				System.out.println(model.getVariable(model.SAMPLERATE));
				System.out.println("P PRESSED");
			}
			
			else if (e.getKeyChar() == ';') {

				model.changeVariable(model.SAMPLERATE, -1000);
				System.out.println(model.getVariable(model.SAMPLERATE));
				System.out.println("; PRESSED");
			}	
			
			else if (e.getKeyChar() == '[') {

				model.changeVariable(model.NOISEGAIN, 0.005);
				System.out.println(model.getVariable(model.NOISEGAIN));
				System.out.println("[ PRESSED");
			}
			
			else if (e.getKeyChar() == '\'') {

				model.changeVariable(model.NOISEGAIN, -0.005);
				System.out.println(model.getVariable(model.NOISEGAIN));
				System.out.println("| PRESSED");
			}
			
			else if (e.getKeyChar() == 'x') {

				model.changeVariable(model.ECHOLENGTH, 0.05);
				System.out.println(model.getVariable(model.ECHOLENGTH));
				System.out.println("X PRESSED");
			}
			
			else if (e.getKeyChar() == 'c') {

				model.changeVariable(model.ECHOLENGTH, -0.05);
				System.out.println(model.getVariable(model.ECHOLENGTH));
				System.out.println("C PRESSED");
			}
			
			else if (e.getKeyChar() == 'v') {

				model.changeVariable(model.DECAY, 0.05);
				System.out.println(model.getVariable(model.DECAY));
				System.out.println("V PRESSED");
			}
			
			else if (e.getKeyChar() == 'b') {

				model.changeVariable(model.DECAY, -0.05);
				System.out.println(model.getVariable(model.DECAY));
				System.out.println("B PRESSED");
			}
			
			else if (e.getKeyChar() == 'a') {
				model.process();
				System.out.println("A PRESSED");
				System.out.println("Re-processing");
			}
			
			else if (e.getKeyChar() == 'n') {

				model.changeVariable(model.THRESHOLD, -1);
				System.out.println(model.getVariable(model.THRESHOLD));
				System.out.println("N PRESSED");
			}
			
			else if (e.getKeyChar() == 'm') {

				model.changeVariable(model.THRESHOLD, 1);
				System.out.println(model.getVariable(model.THRESHOLD));
				System.out.println("M PRESSED");
			}
			
			else if (e.getKeyChar() == ',') {

				model.changeVariable(model.RATIO, -0.5);
				System.out.println(model.getVariable(model.RATIO));
				System.out.println(", PRESSED");
			}
			
			else if (e.getKeyChar() == '.') {

				model.changeVariable(model.RATIO, 0.5);
				System.out.println(model.getVariable(model.RATIO));
				System.out.println(". PRESSED");
			}
			
			else if (e.getKeyChar() == '1') {

				model.changeVariable(model.ATTACK, -0.01);
				System.out.println(model.getVariable(model.ATTACK));
				System.out.println("1 PRESSED");
			}
			
			else if (e.getKeyChar() == '2') {

				model.changeVariable(model.ATTACK, 0.01);
				System.out.println(model.getVariable(model.ATTACK));
				System.out.println("2 PRESSED");
			}
			
			else if (e.getKeyChar() == '3') {

				model.changeVariable(model.RELEASE, -0.01);
				System.out.println(model.getVariable(model.RELEASE));
				System.out.println("3 PRESSED");
			}
			
			else if (e.getKeyChar() == '4') {

				model.changeVariable(model.RELEASE, 0.01);
				System.out.println(model.getVariable(model.RELEASE));
				System.out.println("4 PRESSED");
			}
			
			else if (e.getKeyChar() == '5') {

				model.changeVariable(model.MAKEUPGAIN, -0.1);
				System.out.println(model.getVariable(model.MAKEUPGAIN));
				System.out.println("5 PRESSED");
			}
			
			else if (e.getKeyChar() == '6') {

				model.changeVariable(model.MAKEUPGAIN, 0.1);
				System.out.println(model.getVariable(model.MAKEUPGAIN));
				System.out.println("6 PRESSED");
			}
			
			else if (e.getKeyChar() == '7') {

				model.changeVariable(model.VINYLNOISEGAIN, -0.1);
				System.out.println(model.getVariable(model.VINYLNOISEGAIN));
				System.out.println("7 PRESSED");
			}
			
			else if (e.getKeyChar() == '8') {

				model.changeVariable(model.VINYLNOISEGAIN, 0.1);
				System.out.println(model.getVariable(model.VINYLNOISEGAIN));
				System.out.println("8 PRESSED");
			}
			
			else if (e.getKeyChar() == '9') {

				model.changeVariable(model.HUMGAIN, -0.1);
				System.out.println(model.getVariable(model.HUMGAIN));
				System.out.println("9 PRESSED");
			}
			
			else if (e.getKeyChar() == '0') {

				model.changeVariable(model.HUMGAIN, 0.1);
				System.out.println(model.getVariable(model.HUMGAIN));
				System.out.println("0 PRESSED");
			}
			
		} catch (Exception x) {
			
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
