package com.tek.trgp.player;

import java.awt.Canvas;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Display extends JFrame {

	private static final long serialVersionUID = -1763154087082115748L;
	private JPanel contentPane;
	private Canvas canvas;

	public Display() {
		setTitle("TRex Game Player");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 639, 210);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		canvas = new Canvas();
		canvas.setBounds(10, 10, 600, 150);
		canvas.setSize(600, 150);
		contentPane.add(canvas);
		
		setVisible(true);
	}
	
	public Graphics2D getGraphics() {
		return (Graphics2D) canvas.getGraphics();
	}
}
