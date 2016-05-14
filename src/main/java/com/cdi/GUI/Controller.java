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

import java.util.Random;

import com.cdi.gameclasses.Game;

/**
 * Class represents a the game controller
 *
 * @version 1.0 May 13, 2016
 * @author Viktorya Harutyunyan
 */
public class Controller {

	Game game;
	BowlingUI view;
	private int pins;

	/**
	 * Constructor
	 */
	public Controller() {
		view = new BowlingUI( this);
		view.setVisible(true);
		game = Game.getGameInstance();
	}

	/**
	 * Play the game - roll the ball
	 * @param userInput - number of pins
	 */
	public void playGame(int userInput) {
		if (!game.isOver()) {	
			if(userInput == 0) 	
				pins = Controller.rundomGenerater(game.getCurrentExpectedMax());
			else 
				pins = userInput;
			
			while (!(pins <= game.getCurrentExpectedMax() && pins > 0)) {
				pins = Controller.rundomGenerater(game.getCurrentExpectedMax());
			}
			game.roll(pins);
			view.updateView(pins);
		} else
			view.updateView(0);
	}

	/**
	 * Start the game
	 */
	public void start() {
		// int numberOfFrames, int pinsPerGame, int rollsPerGame
		game.init(view.numberOfFrame, view.numberOfPins, 2);
	}

	static Random rn = new Random();

	/**
	 * Random number generator
	 * @param bound - upper bound [inclusive], 0 is not needed
	 * @return the random number
	 */
	public static int rundomGenerater(int bound) {
		return java.lang.Math.abs(rn.nextInt(bound)) + 1;
	}
}
