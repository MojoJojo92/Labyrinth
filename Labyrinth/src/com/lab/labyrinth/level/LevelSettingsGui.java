package com.lab.labyrinth.level;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.border.BevelBorder;

import com.lab.labyrinth.account.AccountGui;

import java.awt.Font;
import java.io.File;
import java.util.ArrayList;

public class LevelSettingsGui extends JFrame {

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JPanel mainPanel, namePanel, sizePanel, spawnPanel, timePanel;
	private JTextField widthTF, heightTF, xTF, yTF, minTF, secTF, nameTF;
	private JLabel lbName, lblWidth, lblHeight, lblSpawnPisition, lblTime, lblX, lblY, lblMin, lblSec;
	private JSlider widthSlider, heightSlider, xSlider, ySlider, minSlider, secSlider;
	private JButton btnOk;

	private LevelCreate create;
	private int lvlWidth, lvlHeight, spawnX, spawnY;
	private int flag[][];
	private boolean draw = false;
	private ArrayList<String> rankings;

	public LevelSettingsGui(LevelCreate create) {
		this.create = create;

		frame = new JFrame("New Labyrinth");
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setSize(350, 400);
		frame.setLocationRelativeTo(null);

		drawMainPanel();
		frame.repaint();
	}

	private void drawMainPanel() {
		mainPanel = new JPanel(null);
		frame.getContentPane().add(mainPanel);

		drawNamePanel();
		drawSizePanel();
		drawSpawnPanel();
		drawTimePanel();

		btnOk = new JButton("OK");
		btnOk.setBounds(126, 338, 89, 23);
		mainPanel.add(btnOk);

		btnOk.addActionListener(new ButtonListenerOk());
	}

	private void drawNamePanel() {
		namePanel = new JPanel(null);
		namePanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		namePanel.setBounds(10, 11, 324, 50);
		mainPanel.add(namePanel);

		lbName = new JLabel("Labyrinth Name:");
		lbName.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lbName.setBounds(10, 15, 115, 20);
		namePanel.add(lbName);

		nameTF = new JTextField();
		nameTF.setBounds(165, 15, 149, 20);
		nameTF.setColumns(10);
		namePanel.add(nameTF);
	}

	private void drawSizePanel() {
		sizePanel = new JPanel(null);
		sizePanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		sizePanel.setBounds(10, 72, 324, 79);
		mainPanel.add(sizePanel);

		lblWidth = new JLabel("Labyrinth Width:");
		lblWidth.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblWidth.setBounds(10, 14, 113, 20);
		sizePanel.add(lblWidth);

		widthTF = new JTextField("20");
		widthTF.setBounds(161, 14, 30, 20);
		widthTF.setColumns(10);
		widthTF.setEditable(false);
		sizePanel.add(widthTF);

		widthSlider = new JSlider(JSlider.HORIZONTAL, 20, 500, 20);
		widthSlider.setBounds(201, 14, 113, 20);
		sizePanel.add(widthSlider);

		lblHeight = new JLabel("Labyrinth Height:");
		lblHeight.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblHeight.setBounds(10, 48, 113, 20);
		sizePanel.add(lblHeight);

		heightTF = new JTextField("20");
		heightTF.setBounds(161, 48, 30, 20);
		heightTF.setColumns(10);
		heightTF.setEditable(false);
		sizePanel.add(heightTF);

		heightSlider = new JSlider(JSlider.HORIZONTAL, 20, 500, 20);
		heightSlider.setBounds(201, 48, 113, 20);
		sizePanel.add(heightSlider);

		WidthListener widthListener = new WidthListener();
		widthSlider.addChangeListener(widthListener);

		HeightListener heightListener = new HeightListener();
		heightSlider.addChangeListener(heightListener);
	}

	private void drawSpawnPanel() {
		spawnPanel = new JPanel(null);
		spawnPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		spawnPanel.setBounds(10, 162, 324, 79);
		mainPanel.add(spawnPanel);

		lblSpawnPisition = new JLabel("Spawn Pisition:");
		lblSpawnPisition.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblSpawnPisition.setBounds(10, 17, 103, 20);
		spawnPanel.add(lblSpawnPisition);

		lblX = new JLabel("x");
		lblX.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblX.setBounds(123, 17, 15, 20);
		spawnPanel.add(lblX);

		xTF = new JTextField("0");
		xTF.setBounds(161, 17, 30, 20);
		xTF.setColumns(10);
		xTF.setEditable(false);
		spawnPanel.add(xTF);

		xSlider = new JSlider(JSlider.HORIZONTAL, -Math.abs(widthSlider.getValue() / 2) + 1, Math.abs(widthSlider.getValue() / 2) - 2, 0);
		xSlider.setBounds(201, 17, 113, 23);
		spawnPanel.add(xSlider);

		lblY = new JLabel("y");
		lblY.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblY.setBounds(123, 48, 15, 20);
		spawnPanel.add(lblY);

		yTF = new JTextField("0");
		yTF.setBounds(161, 48, 30, 20);
		yTF.setColumns(10);
		yTF.setEditable(false);
		spawnPanel.add(yTF);

		ySlider = new JSlider(JSlider.HORIZONTAL, -Math.abs(heightSlider.getValue() / 2) + 1, Math.abs(heightSlider.getValue() / 2) - 2, 0);
		ySlider.setBounds(201, 45, 113, 23);
		spawnPanel.add(ySlider);

		XListener xListener = new XListener();
		xSlider.addChangeListener(xListener);

		YListener yListener = new YListener();
		ySlider.addChangeListener(yListener);
	}

	private void drawTimePanel() {
		timePanel = new JPanel(null);
		timePanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		timePanel.setBounds(10, 252, 324, 79);
		mainPanel.add(timePanel);

		lblTime = new JLabel("Time Limit:");
		lblTime.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblTime.setBounds(10, 14, 90, 20);
		timePanel.add(lblTime);

		lblMin = new JLabel("min");
		lblMin.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblMin.setBounds(98, 15, 25, 20);
		timePanel.add(lblMin);

		minTF = new JTextField("0");
		minTF.setBounds(161, 15, 30, 20);
		minTF.setColumns(10);
		minTF.setEditable(false);
		timePanel.add(minTF);

		minSlider = new JSlider(JSlider.HORIZONTAL, 0, 60, 0);
		minSlider.setBounds(201, 14, 113, 20);
		timePanel.add(minSlider);

		lblSec = new JLabel("sec");
		lblSec.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblSec.setBounds(98, 48, 30, 20);
		timePanel.add(lblSec);

		secTF = new JTextField("30");
		secTF.setBounds(161, 48, 30, 20);
		secTF.setColumns(10);
		secTF.setEditable(false);
		timePanel.add(secTF);

		secSlider = new JSlider(JSlider.HORIZONTAL, 30, 60, 30);
		secSlider.setBounds(201, 45, 113, 20);
		timePanel.add(secSlider);

		MinListener minListener = new MinListener();
		minSlider.addChangeListener(minListener);

		SecListener secListener = new SecListener();
		secSlider.addChangeListener(secListener);
	}

	private class ButtonListenerOk implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {

			int x = xSlider.getValue() - xSlider.getMinimum();
			int y = ySlider.getValue() - ySlider.getMinimum();
			int z = xSlider.getMaximum() - xSlider.getValue();
			int v = ySlider.getMaximum() - ySlider.getValue();

			lvlWidth = widthSlider.getValue();
			lvlHeight = heightSlider.getValue();
			spawnX = xSlider.getValue();
			spawnY = ySlider.getValue();

			flag = new int[lvlWidth][lvlHeight];
			rankings = new ArrayList<String>();

			if (x > 9)
				x = 9;
			if (x < 0)
				x = 0;
			if (y > 9)
				y = 9;
			if (y < 0)
				y = 0;

			if (lvlWidth == 20) {
				if (z < 8)
					x += (x - z - 1);
			} else {
				if (z < 8)
					x += (x - z);
			}

			if (lvlHeight == 20) {
				if (v < 8)
					y += (y - v - 1);
			} else {
				if (v < 8)
					y += (y - v);
			}
			
			for(int i = 0;i<3;i++) {
				rankings.add("None  00 : 00");
			}

			create.setLvlWidth(lvlWidth);
			create.setLvlHeight(lvlHeight);
			create.setSpawnX(spawnX + Math.abs(lvlWidth / 2) - 1 - x);
			create.setSpawnY(spawnY + Math.abs(lvlHeight / 2) - 1 - y);
			create.setMinTime(minSlider.getValue());
			create.setSecTime(secSlider.getValue());
			create.setMinBest(0);
			create.setSecBest(0);
			create.setLevelName(nameTF.getText() + "_" + AccountGui.Username);
			create.setGo(true);
			create.setRankings(rankings);

			for (int i = 0; i < lvlWidth; i++)
				for (int j = 0; j < lvlHeight; j++)
					fill(i, j);

			if (checkFileName()) {
				create.setDraw(true);
				create.setFlag(flag);
				frame.dispose();
			}

		}
	}

	private void fill(int i, int j) {
		if (border(i, j))
			flag[i][j] = 2;
		else
			flag[i][j] = 0;
		flag[spawnX + Math.abs(lvlWidth / 2)][spawnY + Math.abs(lvlHeight / 2)] = 3;
	}

	private boolean border(int i, int j) {
		if (i == 0)
			return true;
		if (i == lvlWidth - 1)
			return true;
		if (j == 0)
			return true;
		if (j == lvlHeight - 1)
			return true;
		return false;
	}

	private boolean checkFileName() {

		File file = new File("res/levels/" + nameTF.getText() + "_" + AccountGui.Username + ".ser");
		if (file.exists()) {
			JOptionPane.showMessageDialog(frame, "The level name already exists ", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (nameTF.getText().equals("")) {
			JOptionPane.showMessageDialog(frame, "The level name is empty ", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (nameTF.getText().length() > 10) {
			JOptionPane.showMessageDialog(frame, "Name is too long", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	private class WidthListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			int value = widthSlider.getValue();
			widthTF.setText(Integer.toString(value));
			xSlider.setMaximum(Math.abs(widthSlider.getValue() / 2) - 2);
			xSlider.setMinimum(-Math.abs(widthSlider.getValue() / 2) + 1);
		}
	}

	private class HeightListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			int value = heightSlider.getValue();
			heightTF.setText(Integer.toString(value));
			ySlider.setMaximum(Math.abs(heightSlider.getValue() / 2) - 2);
			ySlider.setMinimum(-Math.abs(heightSlider.getValue() / 2) + 1);
		}
	}

	private class XListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			int value = xSlider.getValue();
			xTF.setText(Integer.toString(value));
		}
	}

	private class YListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			int value = ySlider.getValue();
			yTF.setText(Integer.toString(value));
		}
	}

	private class MinListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			int value = minSlider.getValue();
			minTF.setText(Integer.toString(value));
			if (value == 0)
				secSlider.setMinimum(30);
			else
				secSlider.setMinimum(0);
		}
	}

	private class SecListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			int value = secSlider.getValue();
			secTF.setText(Integer.toString(value));
		}
	}

	public int getLvlWidth() {
		return lvlWidth;
	}

	public int getLvlHeight() {
		return lvlHeight;
	}

	public int getSpawnX() {
		return spawnX;
	}

	public int getSpawnY() {
		return spawnY;
	}

	public boolean isDraw() {
		return draw;
	}
}