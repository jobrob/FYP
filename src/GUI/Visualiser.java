package GUI;

import java.awt.*;
import javax.swing.*;

public class Visualiser {
	public static void main(String[] args) {
		JFrame jf = new JFrame("Graph Visualiser");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel content = new JPanel(new BorderLayout());
		JPanel visPane = new JPanel(new BorderLayout());
		JPanel optPane = new JPanel(new GridLayout(0,1));
		
		//Add stuff to the visualiser pane.
		
		//Add stuff to the option pane.
		
		content.add(visPane, BorderLayout.CENTER);
		content.add(optPane, BorderLayout.LINE_END);
		
		jf.setContentPane(content);
		jf.pack();
		jf.setVisible(true);
	}
}