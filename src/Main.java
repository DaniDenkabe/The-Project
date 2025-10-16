import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
//		String ans = "";
//		try {
//			EffectsModel model = new EffectsModel();
//			while(!ans.equals(".")) {
//				System.out.print("KEY:");
//				ans = TextIO.getlnString();
//				if (ans.equals("0")) {
//					model.setSelected(0);
//				} else if (ans.equals("q")) {
//					model.play();
//				} else if (ans.equals("w")) {
//					model.changeHp(-500);
//					System.out.println(model.getHp());
//				} else if (ans.equals("e")) {
//					model.changeHp(500);
//					System.out.println(model.getHp());
//				} else if (ans.equals("z")) {
//					
//				}
//			}
//		} catch (Exception e) {
//			
//		}

		try {
			JFrame window = new JFrame();
			window.addKeyListener(new KeyBoardIntake());
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        window.setVisible(true);
	        window.setResizable(false);  
	        window.setSize(100, 100);
	        window.getFocusableWindowState();
		} catch (Exception e) {
			
		}
		
		
	}
}
