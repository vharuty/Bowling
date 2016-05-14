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

import java.util.ArrayList;
import java.util.Observable;
import com.cdi.gameclasses.LastFrame;

//Game class is a Singletone 
/**
 * Class represent the game singleton
 * which encapsulates the game logicc
 *
 * @version 1.0 May 13, 2016
 * @author Viktorya Harutyunyan
 */
public class Game extends Observable {

	private static Game instance;
	private ArrayList<Frame> gameFrames = null; // frames collection of whole game
	private int numberOfFrames;
	private int pinsPerGame; // configuration - pins per frame
	private int rollsPerGame; // configuration - rolls per frame
	private int activeFrameIndex; // current frame number
	private boolean gameOver; // if the game is over or not
	private Frame activeFrame; // current frame
	private int currentExpectedMax;

	/**
	 * this is a singleton need a private constructir
	 */
	private Game() {
	};

	/**
	 * Get singleton instance
	 * @return singleton instance
	 */
	public static Game getGameInstance() {

		if (instance == null)
			instance = new Game();
		return instance;
	}
	
	/**
	 * Get frame by index
	 * @param frame index
	 * @return frame
	 */
	Frame getFrameWithIndex(int index){
		return gameFrames.get(index);
	}
	
	/**
	 * Get active frame index 
	 * @return active frame index
	 */
	public int getActiveFrameIndex(){
		return activeFrameIndex;
	}
	
	/**
	 * Get current frame
	 * @return current frame
	 */
	public  Frame getActiveFrame(){
		return getFrameWithIndex(activeFrameIndex);
		
	}
	
	/**
	 * Get the remaining pins count
	 * @return pin count
	 */
	public int getCurrentExpectedMax(){
		return currentExpectedMax;
	}
	
	/**
	 * Is the game over?
	 * @return game over or not
	 */
	public boolean isOver(){
		return gameOver;
	}

	/**
	 * Get frame count for current game
	 * @return frame count
	 */
	public int getFrameSize(){
		return gameFrames.size();
	}
	
	/**
	 * Initialization funciton
	 * @param numberOfFrames - frames per game
	 * @param pinsPerGame - pins per frame
	 * @param rollsPerGame - max roll count perframe
	 */
	public void init(int numberOfFrames, int pinsPerGame, int rollsPerGame) {
		this.numberOfFrames = numberOfFrames;
		this.pinsPerGame = pinsPerGame;
		this.rollsPerGame = rollsPerGame;
		activeFrameIndex = 0;
		gameFrames = new ArrayList<Frame>();
		activeFrame = new GameFrame(rollsPerGame, pinsPerGame);
		gameFrames.add(activeFrame);
		this.addObserver(activeFrame);
		currentExpectedMax = pinsPerGame;
		gameOver = false;
	}

	/**
	 * Roll
	 * @param pins - how many pins were knocked down
	 */
	public void roll(int pins) {
		if (pins <= currentExpectedMax && pins > 0) {
			currentExpectedMax = pinsPerGame - pins;

			if (!gameOver) {
				setChanged();
				notifyObservers(new Integer(pins));

				if (!activeFrame.isActive) {

					if (!activeFrame.isSpare && !activeFrame.isStrike)
						this.deleteObserver(activeFrame);

					if (activeFrameIndex < numberOfFrames - 1) {
						if (activeFrameIndex == numberOfFrames - 2)
							activeFrame = new LastFrame(rollsPerGame, pinsPerGame);
						else
							activeFrame = new GameFrame(rollsPerGame, pinsPerGame);
						activeFrameIndex++;
						currentExpectedMax = pinsPerGame;
						gameFrames.add(activeFrame);
						this.addObserver(activeFrame);
					} else {

						gameOver = true;
						this.deleteObservers();
					}
				}

				if (!gameOver) {

					activeFrame.roll(pins);
					currentExpectedMax = activeFrame.getNextExpectedPin();
				}

			} // if condition Game is over
		} // if condition pins <= currentExpectedMax
	}

	/**
	 * Unsubscribe current frame
	 * @param frame
	 */
	public void unsubscribe(Frame frame) {
		this.deleteObserver(frame);
	}

	/**
	 * Get score and bonus
	 * @return score and bonus
	 */
	public int score() {
		int gameScore = 0;
		for (int i = 0; i < gameFrames.size(); i++)
			gameScore += gameFrames.get(i).score();
		return gameScore;
	}
}
