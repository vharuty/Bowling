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
 * Class represents a regular game frame
 *
 * @version 1.0 May 13, 2016
 * @author Viktorya Harutyunyan
 */
public class GameFrame extends Frame {

	/**
	 * Constructor
	 * 
	 * @param rolls
	 *            - number of maximum rolls
	 * @param pinsPerGame
	 *            - pins per frame
	 */
	GameFrame(int rolls, int pinsPerGame) {
		super(rolls, pinsPerGame);

	}

	/**
	 * Roll function
	 * 
	 * @param pins
	 */
	public void roll(int pins) {
		score += pins;
		rollsCompleted++;

		if (pins == pinsPerGame && rollsCompleted == 1) { // if 10 pins are
															// knocked
															// at first go, 2
															// bonus
															// rolls are added
			isStrike = true;
			bonusRolls = 2;
			isActive = false; // frame is over
		} else if (rollsCompleted == rollsPerGame) {

			isActive = false;
			if (!isStrike && score == pinsPerGame) {
				isSpare = true;
				bonusRolls = 1;
			}

		}

	}

	/**
	 * get score
	 */
	public int score() {
		return super.score() + bonus;
	}

	/**
	 * update observable
	 */
	public void update(Observable o, Object arg) {

		if (bonusRolls != 0) {
			bonusRolls--;
			bonus = bonus + ((Integer) arg).intValue();

		} else if (bonusRolls == 0 && !isActive) {
			((Game) o).deleteObserver(this);
			isStrike = false;
			isSpare = false;
		}

	}

	/**
	 * Get number of pins standing
	 */
	@Override
	public int getNextExpectedPin() {
		if (rollsCompleted == rollsPerGame || score == pinsPerGame)
			return pinsPerGame;
		else
			return pinsPerGame - score;
	}

}
