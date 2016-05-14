package com.cdi.GUI;

/* Version 1.0
*
*  Copyright (C) 2016 Viktorya Harutyunyan
*
* This program is free software; you can redistribute it and/or modify it
* under the terms of the GNU General Public License as published by the Free
* Software Foundation; either version 2 of the License.
*
* This program is distributed in the hope that it will be useful, but WITHOUT
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
* FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
* more details.
* 
* You should have received a copy of the GNU General Public License along with
* this program; if not, write to the Free Software Foundation, Inc., 51
* Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*
*/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import com.cdi.gameclasses.Game;

/**
 * Class represents a the UI part of the application
 *
 * @version 1.0 May 13, 2016
 * @author Viktorya Harutyunyan
 */
public class BowlingUI extends JFrame {
	Controller contraller;
	int numberOfFrame = 10;
	int numberOfPins = 10;

	JPanel controlPanel;
	JPanel gamePlaypanel;
	JPanel gameVisualizationPanel;

	JButton startGameButton;
	JButton rollButton;
	JButton randomRollButton;
	JTextField userInputPins;
	JTextField numberOfFramesText;
	JTextField numberOfPinsText;

	JTextArea output;

	JLabel numberOfFramesLabel;
	JLabel numberOfPinsLabel;

	StringBuilder outputBuffer = new StringBuilder();

	Random random = new Random();

	Game game;

	/**
	 * Constructor
	 * 
	 * @param controller
	 */
	public BowlingUI(final Controller controller) {

		this.contraller = controller;

		Font font = new Font("Verdana", Font.BOLD, 16);
		this.setSize(800, 600);
		this.setMinimumSize(new Dimension(800, 600));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(0, 2));

		// ----------Button initialization -------------

		startGameButton = new JButton("Start Game");
		rollButton = new JButton("ROLL");

		randomRollButton = new JButton("Random ROLL");
		// ---------------End Button initialization------------

		// ------------JTextField initialization --------------------

		numberOfFramesText = new JTextField("10");
		numberOfPinsText = new JTextField("10");

		userInputPins = new JTextField("10");

		// ---------End TextField initialization

		// ---------- Label Initialization------
		numberOfFramesLabel = new JLabel("Number of Frames: ", JLabel.CENTER);
		numberOfPinsLabel = new JLabel("Number of Pins: ", JLabel.CENTER);

		// -------End Label Initialization----------

		// --------Panel Initialization--------------
		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());

		gamePlaypanel = new JPanel();
		gamePlaypanel.setBackground(Color.lightGray);
		GridLayout layout = new GridLayout(0, 2);
		gamePlaypanel.setLayout(layout);
		gamePlaypanel.setFont(font);

		gamePlaypanel.add(numberOfFramesLabel);
		gamePlaypanel.add(numberOfFramesText);
		gamePlaypanel.add(numberOfPinsLabel);
		gamePlaypanel.add(numberOfPinsText);
		gamePlaypanel.add(startGameButton);
		gamePlaypanel.add(new JLabel(""));
		gamePlaypanel.add(new JLabel(""));
		gamePlaypanel.add(new JLabel(""));
		gamePlaypanel.add(rollButton);
		gamePlaypanel.add(userInputPins);
		gamePlaypanel.add(randomRollButton);

		output = new JTextArea();

		JScrollPane scrollPane = new JScrollPane(output);
		scrollPane.setBounds(10, 60, 780, 500);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		controlPanel.add(gamePlaypanel);
		// controlPanel.add(gameVisualizationPanel);

		// -----End Panel Initialization-------------

		this.add(controlPanel);
		this.add(scrollPane);
		this.setVisible(true);

		enableControls(false);
		// ======================add action ============================

		// generate pins interactively as user input
		rollButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isInteger(userInputPins.getText())) {
					numberOfPins = Integer.parseInt(userInputPins.getText());
					controller.playGame(numberOfPins);
				}
			}
		});

		// randomly generate pins
		randomRollButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.playGame(0);
			}
		});

		startGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String frames = numberOfFramesText.getText();
				String pins = numberOfPinsText.getText();

				if (isInteger(frames))
					numberOfFrame = Integer.parseInt(frames);
				if (isInteger(pins))
					numberOfPins = Integer.parseInt(pins);

				initGame(numberOfFrame, numberOfPins, 2);
				outputBuffer.append("======== Game started =======\r\n");
				output.setText(outputBuffer.toString());
				enableControls(true);

				controller.start();
			}
		});

	}

	/**
	 * Roll function
	 * @param numberOfPins - number of pins 
	 */
	private void roll(int numberOfPins) {
		outputBuffer.append("frame#: " + (game.getActiveFrameIndex() + 1) + ", knocked pins: " + numberOfPins
				+ ", score: " + game.score() + "\r\n");

		output.setText(outputBuffer.toString());

		if (game.isOver())
			gameOver();
	}
	
	/**
	 * Perform action after game is over
	 */
	private void gameOver() {
		outputBuffer.append("=========== Game Over ===========\r\n");
		outputBuffer.append("Final score: " + game.score());
		output.setText(outputBuffer.toString());
		outputBuffer.setLength(0);
		enableControls(false);
	}

	/**
	 * Enable / disable controls
	 * @param play - play mode or setup mode
	 */
	private void enableControls(boolean play) {
		numberOfFramesLabel.setEnabled(!play);
		numberOfFramesText.setEnabled(!play);
		numberOfPinsLabel.setEnabled(!play);
		numberOfPinsText.setEnabled(!play);
		startGameButton.setEnabled(!play);

		rollButton.setEnabled(play);
		randomRollButton.setEnabled(play);
		userInputPins.setEnabled(play);

	}

	/**
	 * Initialize teh game
	 * @param numberOfFrames
	 * @param pinsPerGame
	 * @param rollsPerGame
	 */
	public void initGame(int numberOfFrames, int pinsPerGame, int rollsPerGame) {
		game = Game.getGameInstance();
		game.init(numberOfFrames, pinsPerGame, rollsPerGame);
	}

	/**
	 * Check if the string is integer convertible
	 * @param input string
	 * @return true if the string is convertable to int
	 */
	public boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Notify the to update the contents from model
	 * @param pins - numnber of pins knocked down
	 */
	public void updateView(int pins) {
		roll(pins);
	}

}
