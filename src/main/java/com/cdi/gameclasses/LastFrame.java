package com.cdi.gameclasses;
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

import java.util.Observable;

/**
 * Class represents the last fram of the game
 *
 * @version 1.0 May 13, 2016
 * @author Viktorya Harutyunyan
 */
public class LastFrame extends Frame {

	int rollRemaining = 0;
	int pinsRemaning = 0;

	/**
	 * Constructor
	 * 
	 * @param rolls - number of rolls
	 * @param pinsPerGame - number of pins
	 */
	LastFrame(int rolls, int pinsPerGame) {
		super(rolls, pinsPerGame);
	}

	/**
	 * Roll function
	 * @param - numnber of knocked down pins
	 */
	public void roll(int pins) {
		rollsCompleted++;
		score += pins;
		if (rollsCompleted == 1) {
			if (pins == pinsPerGame) {
				isStrike = true;
				rollsPerGame++;
				nextExpectedPin = pinsPerGame;
			} else {
				nextExpectedPin = pinsPerGame - pins;
			}
		} else if (rollsCompleted == 2) {

			if (pins == pinsPerGame)
				nextExpectedPin = pinsPerGame;

			if (score == pinsPerGame && (!isStrike)) {
				nextExpectedPin = pinsPerGame;
				isSpare = true;
				rollsPerGame++;
			}
		}
		if (rollsCompleted == rollsPerGame) {

			isActive = false;
		}

	}

	/**
	 * get the score
	 */
	public int score() {
		return score;
	}

	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
	}

	/**
	 * Get number of pins remaining standing
	 */
	@Override
	public int getNextExpectedPin() {

		return nextExpectedPin;
	}

}
