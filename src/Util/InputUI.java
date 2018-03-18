package Util;

import ForceDirected.Eades3D;
import Graph.Graph3D;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.IOException;

public class InputUI extends JFrame
{
	private final String BUTTON_TEXT = "Generate";



	public InputUI(Graph3D graph3D)
	{
		initComponents(graph3D);
	}

	private void initComponents(Graph3D graph3D)
	{

		final String[][] fields = {
				{"SpringForce1:",		  "2"},
				{"SpringForce2:", 		  "1"},
				{"ElectrostaticForces:",  "60000"},
				{"Inertia:", 			  "0.2"},
				{"Simulations",			  "10000"}
		};

		final JTextField[] textFields = new JTextField[fields.length];

		JPanel p = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;

		for(int i = 0; i < fields.length ; i++)
		{
			c.gridx = 0;
			c.gridy = i;
			c.gridwidth = 1;
			JLabel label = new JLabel(fields[i][0], JLabel.TRAILING);
			p.add(label, c);

			textFields[i] = new JTextField();
			textFields[i].setText(fields[i][1]);
			c.gridx = 2;
			c.gridy = i;
			c.gridwidth = 2;
			p.add(textFields[i],c);
		}

		JButton button = new JButton(BUTTON_TEXT);
		c.gridx = 3;
		c.gridy = 5;
		c.gridheight = 4;

		button.addActionListener(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent actionEvent)
				{
					button.setText("Working...");
					button.setEnabled(false);
					button.repaint();
					button.revalidate();

					Thread t = new Thread()
					{
						public void run()
						{
							double springForce1 = Double.valueOf(textFields[0].getText());
							double springForce2 = Double.valueOf(textFields[1].getText());
							double electroStaticForce = Double.valueOf(textFields[2].getText());
							double edgeForce = Double.valueOf(textFields[3].getText());
							int simulations = Integer.valueOf(textFields[4].getText());
							Eades3D.simulate(simulations, graph3D, edgeForce, springForce1, springForce2, electroStaticForce);

							button.setText(BUTTON_TEXT);
							button.setEnabled(true);

							try
							{
								Runtime.getRuntime().exec("xdg-open ../0.svg");
							} catch (IOException ex)
							{
								ex.printStackTrace();
							}
						}
					};
					t.start();
				}
			}
		);

		p.add(button,c);
		JFrame frame = new JFrame("Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(p);
		frame.pack();
		frame.setVisible(true);
	}


}
