package com.lab.labyrinth.launcher;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.JSlider;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.SwingConstants;

import java.awt.Font;

public class OptionsGui extends JFrame {

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JPanel mainPanel, volumePanel, sensitivityPanel, brightnessPanel, controlsPanel;
	private JLabel lblControls, lblRight, lblLeft, lblBackwards, lblForward, lblJump, lblTurnLeft, lblTurnRight, lblRun, lblVolume, lblSensitivity, lblBrightness;
	private JSlider volumeSlider, sensitivitySlider, brightnessSlider;
	private JButton btnDefault, btnOk;
	private JTextField forwardTF, backwardsTF, leftTF, rightTF, turnLeftTF, turnRightTF, runTF, jumpTF, volumeTF, sensitivityTF, brightnessTF;

	private int volume, sensitivity, brightness;
	private String forward, backwards, right, turnRight, turnLeft, run, jump, left;
	private OptionsConfiguration config;

	private String path = "res/settings/config.xml";

	public OptionsGui() {

		config = new OptionsConfiguration();
		config.loadConfiguration(path);

		volume = config.getVolume();
		sensitivity = config.getSensitivity();
		brightness = config.getBrightness();
		forward = config.getForward();
		backwards = config.getBack();
		right = config.getRight();
		turnRight = config.getTurnRight();
		turnLeft = config.getTurnLeft();
		run = config.getRun();
		jump = config.getJump();
		left = config.getLeft();

		frame = new JFrame("Options");
		frame.setSize(new Dimension(325, 400));
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);

		drawMainPanel();

		frame.repaint();
	}

	private void drawMainPanel() {

		mainPanel = new JPanel(null);
		frame.add(mainPanel);

		drawVolumePanel();
		drawSensitivityPanel();
		drawBrightnessPanel();
		drawControlsPanel();

		btnDefault = new JButton("Default");
		btnDefault.setBounds(10, 338, 89, 23);
		mainPanel.add(btnDefault);

		btnOk = new JButton("Ok");
		btnOk.setBounds(220, 338, 89, 23);
		mainPanel.add(btnOk);

		btnOk.addActionListener(new ButtonListenerOk());
		btnDefault.addActionListener(new ButtonListenerDefault());

	}

	private void drawVolumePanel() {

		volumePanel = new JPanel(null);
		volumePanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		volumePanel.setBounds(10, 11, 299, 45);
		mainPanel.add(volumePanel);

		lblVolume = new JLabel("Volume");
		lblVolume.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblVolume.setBounds(10, 11, 46, 25);
		volumePanel.add(lblVolume);

		volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, volume);
		volumeSlider.setBounds(139, 11, 150, 25);
		volumePanel.add(volumeSlider);

		volumeTF = new JTextField();
		volumeTF.setText(Integer.toString(volumeSlider.getValue()));
		volumeTF.setEditable(false);
		volumeTF.setBounds(89, 11, 40, 20);
		volumeTF.setColumns(10);
		volumePanel.add(volumeTF);

		VolumeListener listener = new VolumeListener();
		volumeSlider.addChangeListener(listener);
	}

	private void drawSensitivityPanel() {

		sensitivityPanel = new JPanel(null);
		sensitivityPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		sensitivityPanel.setBounds(10, 63, 299, 45);
		mainPanel.add(sensitivityPanel);

		lblSensitivity = new JLabel("Sensitivity");
		lblSensitivity.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblSensitivity.setBounds(10, 11, 66, 25);
		sensitivityPanel.add(lblSensitivity);

		sensitivitySlider = new JSlider(SwingConstants.HORIZONTAL, 0, 100, sensitivity);
		sensitivitySlider.setBounds(139, 11, 150, 25);
		sensitivityPanel.add(sensitivitySlider);

		sensitivityTF = new JTextField();
		sensitivityTF.setText(Integer.toString(sensitivitySlider.getValue()));
		sensitivityTF.setEditable(false);
		sensitivityTF.setBounds(89, 11, 40, 20);
		sensitivityTF.setColumns(10);
		sensitivityPanel.add(sensitivityTF);

		SensitivityListener listener = new SensitivityListener();
		sensitivitySlider.addChangeListener(listener);

	}

	private void drawBrightnessPanel() {

		brightnessPanel = new JPanel(null);
		brightnessPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		brightnessPanel.setBounds(10, 119, 299, 45);
		mainPanel.add(brightnessPanel);

		lblBrightness = new JLabel("Brightness");
		lblBrightness.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblBrightness.setBounds(10, 11, 66, 25);
		brightnessPanel.add(lblBrightness);

		brightnessSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 100, brightness);
		brightnessSlider.setBounds(139, 11, 150, 25);
		brightnessPanel.add(brightnessSlider);

		brightnessTF = new JTextField();
		brightnessTF.setText(Integer.toString(brightnessSlider.getValue()));
		brightnessTF.setEditable(false);
		brightnessTF.setBounds(89, 11, 40, 20);
		brightnessTF.setColumns(10);
		brightnessPanel.add(brightnessTF);

		BrightnessListener listener = new BrightnessListener();
		brightnessSlider.addChangeListener(listener);

	}

	private void drawControlsPanel() {

		controlsPanel = new JPanel(null);
		controlsPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		controlsPanel.setBounds(10, 175, 299, 152);
		mainPanel.add(controlsPanel);

		lblControls = new JLabel("Controls");
		lblControls.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblControls.setBounds(10, 11, 60, 14);
		controlsPanel.add(lblControls);

		lblRight = new JLabel("Right");
		lblRight.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblRight.setBounds(10, 124, 60, 14);
		controlsPanel.add(lblRight);

		rightTF = new JTextField(right);
		rightTF.setBounds(73, 121, 46, 20);
		rightTF.setColumns(10);
		controlsPanel.add(rightTF);

		lblLeft = new JLabel("Left");
		lblLeft.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblLeft.setBounds(10, 96, 60, 14);
		controlsPanel.add(lblLeft);

		leftTF = new JTextField(left);
		leftTF.setBounds(73, 93, 46, 20);
		leftTF.setColumns(10);
		controlsPanel.add(leftTF);

		lblBackwards = new JLabel("Backwards");
		lblBackwards.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblBackwards.setBounds(10, 65, 60, 14);
		controlsPanel.add(lblBackwards);

		backwardsTF = new JTextField(backwards);
		backwardsTF.setBounds(73, 62, 46, 20);
		backwardsTF.setColumns(10);
		controlsPanel.add(backwardsTF);

		lblForward = new JLabel("Forward");
		lblForward.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblForward.setBounds(10, 36, 60, 14);
		controlsPanel.add(lblForward);

		forwardTF = new JTextField(forward);
		forwardTF.setBounds(73, 33, 46, 20);
		forwardTF.setColumns(10);
		controlsPanel.add(forwardTF);

		lblJump = new JLabel("Jump");
		lblJump.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblJump.setBounds(173, 124, 60, 14);
		controlsPanel.add(lblJump);

		jumpTF = new JTextField(jump);
		jumpTF.setBounds(243, 121, 46, 20);
		jumpTF.setColumns(10);
		controlsPanel.add(jumpTF);

		lblTurnLeft = new JLabel("Turn Left");
		lblTurnLeft.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblTurnLeft.setBounds(173, 36, 60, 14);
		controlsPanel.add(lblTurnLeft);

		turnLeftTF = new JTextField(turnLeft);
		turnLeftTF.setBounds(243, 33, 46, 20);
		turnLeftTF.setColumns(10);
		controlsPanel.add(turnLeftTF);

		lblTurnRight = new JLabel("Turn Right");
		lblTurnRight.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblTurnRight.setBounds(173, 68, 60, 14);
		controlsPanel.add(lblTurnRight);

		turnRightTF = new JTextField(turnRight);
		turnRightTF.setBounds(243, 64, 46, 20);
		turnRightTF.setColumns(10);
		controlsPanel.add(turnRightTF);

		lblRun = new JLabel("Run");
		lblRun.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblRun.setBounds(173, 96, 60, 14);
		controlsPanel.add(lblRun);

		runTF = new JTextField(run);
		runTF.setBounds(243, 93, 46, 20);
		runTF.setColumns(10);
		controlsPanel.add(runTF);

	}

	public class ButtonListenerOk implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {

			volume = volumeSlider.getValue();
			sensitivity = sensitivitySlider.getValue();
			brightness = brightnessSlider.getValue();
			forward = forwardTF.getText();
			backwards = backwardsTF.getText();
			right = rightTF.getText();
			left = leftTF.getText();
			turnRight = turnRightTF.getText();
			turnLeft = turnLeftTF.getText();
			run = runTF.getText();
			jump = jumpTF.getText();

			config = new OptionsConfiguration();
			config.saveConfiguration(volume, sensitivity, brightness, forward, backwards, right, left, turnRight, turnLeft, run, jump, path);

			frame.dispose();
		}
	}

	private class ButtonListenerDefault implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {

			volumeSlider.setValue(50);
			brightnessSlider.setValue(50);
			sensitivitySlider.setValue(50);
			forwardTF.setText("W");
			backwardsTF.setText("S");
			rightTF.setText("D");
			leftTF.setText("A");
			turnRightTF.setText("Right");
			turnLeftTF.setText("Left");
			runTF.setText("Shift");
			jumpTF.setText("Space");

		}
	}

	public class VolumeListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			volumeTF.setText(Integer.toString(volumeSlider.getValue()));
		}
	}

	public class SensitivityListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			sensitivityTF.setText(Integer.toString(sensitivitySlider.getValue()));
		}
	}

	public class BrightnessListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			brightnessTF.setText(Integer.toString(brightnessSlider.getValue()));
		}
	}
}
