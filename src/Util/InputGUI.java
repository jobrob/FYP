package Util;

import ForceDirected.Eades3D;
import Graph.Graph3D;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputGUI
{
	private JTextField springForce1TextField;
	private JTextField springForce2TextField;
	private JTextField electroStaticForceTextField;
	private JTextField edgeForceTextField;
	private JButton generateButton;
	public JPanel panel;

	public InputGUI(Graph3D graph3D)
	{
		generateButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent actionEvent)
			{
				double springForce1 = Double.valueOf(springForce1TextField.getText());
				double springForce2 = Double.valueOf(springForce2TextField.getText());
				double electroStaticForce = Double.valueOf(electroStaticForceTextField.getText());
				double edgeForce = Double.valueOf(edgeForceTextField.getText());
				Eades3D.simulate(10000,graph3D,springForce1,springForce2,electroStaticForce,edgeForce);
			}
		});
	}

}
