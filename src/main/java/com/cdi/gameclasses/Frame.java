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

import java.util.Observer;

/**
 * Class represent a frame in the bowling game
 * inherits from observer and is going to receive notification 
 * about game events
 * is an abstract class and is the base class of regular frame and the last frame
 *
 * @version 1.0 May 13, 2016
 * @author Viktorya Harutyunyan
 */
public abstract class Frame implements Observer{
	protected boolean isActive = true; 
	protected boolean isStrike  = false; // 10 pins at one go
	protected boolean isSpare = false; 
	
	protected int pinsPerGame ;
	protected int bonusRolls ; // number of exstra rolls to be counted as a bonus of spare or strike
	protected int rollsPerGame; // maximum number of rolls for the frame
	protected int bonus;
	protected int nextExpectedPin;
	protected int score ;
	protected int rollsCompleted; // how many rolls does a player completed for the frame

	Frame( int rolls, int pinsPerGame  ){
		this.rollsPerGame = rolls;
		this.pinsPerGame = pinsPerGame;
		
	}

	/**
	 * Getter provides the value if the frame is the current one or not
	 * @return true if current frame is active.
	 */
	boolean statusIsActive(){
		return isActive;
	}
	
	
	/**
	 * if during set frame there was a strike
	 * @return if during set frame there was a strike
	 */
	boolean statusIsAtrike(){
		return isStrike;
	}	
	
	/**
	 * How many rolls completed
	 * @return number of rolls completed
	 */
	public int getRollsCompleted(){
		
		return rollsCompleted;
	}
	
	/**
	 * Whether there was a spare during the frame
	 * @return
	 */
	boolean statusIsSpare(){
		return isSpare;
	}	
	
	/**
	 * returns the current score
	 * @return score
	 */
	public int score(){
		return score;
	}
	
	// abstract methods
	public  abstract void  roll(int i) ;
	public abstract int getNextExpectedPin();
}
