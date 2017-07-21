package Map;

import java.awt.Dimension;


import javax.swing.JFrame;

public class Main {
	public static void main(String[] args){
		JFrame frame = new JFrame("Map");
		frame.add(new Game());
		frame.setPreferredSize(new Dimension(1200, 900));
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
