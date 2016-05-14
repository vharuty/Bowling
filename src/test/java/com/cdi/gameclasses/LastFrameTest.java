package com.cdi.gameclasses;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class LastFrameTest extends TestCase {

	Game game = Game.getGameInstance();

	@Before
	protected void setUp() {
		game = Game.getGameInstance();
	}

	@After
	protected void tearDown() {
		game.deleteObservers();
		game = null;
	}

	/*
	 * Score + Bonus of last frame with last 3 rolls Strike
	 */
	@Test
	public void testLastFrameScore1() {

		int[] rolls = new int[12];
		game.init(5, 10, 2);
		rolls[0] = 1;
		rolls[1] = 3;
		rolls[2] = 4;
		rolls[3] = 3;
		rolls[4] = 4;
		rolls[5] = 3;
		rolls[6] = 7;
		rolls[7] = 1;
		rolls[8] = 10;
		rolls[9] = 10;
		rolls[10] = 10;
		rolls[11] = 2;

		Game game = Game.getGameInstance();
		// int numberOfFrames, int pinsPerGame, int rollsPerGame
		game.init(5, 10, 2);
		int index = 0;
		while (!game.isOver()) {
			game.roll(rolls[index]);
			index++;

		}

		// int lastFrameScore = game.gameFram.get(game.gameFram.size() -
		// 1).score();
		int lastFrameScore = game.getFrameWithIndex(game.getFrameSize() - 1).score();

		assertTrue(lastFrameScore == 30);
	}

	/*
	 * Score + Bonus of last frame with last frame Spare
	 */
	@Test
	public void testLastFrameScore() {

		int[] rolls = new int[12];
		game.init(5, 10, 2);
		rolls[0] = 1;
		rolls[1] = 3;
		rolls[2] = 4;
		rolls[3] = 3;
		rolls[4] = 4;
		rolls[5] = 3;
		rolls[6] = 7;
		rolls[7] = 1;
		rolls[8] = 5;
		rolls[9] = 5;
		rolls[10] = 7;
		rolls[11] = 2;

		Game game = Game.getGameInstance();
		// int numberOfFrames, int pinsPerGame, int rollsPerGame
		game.init(5, 10, 2);
		int index = 0;
		while (!game.isOver()) {
			game.roll(rolls[index]);
			index++;

		}

		int lastFrameScore = game.getFrameWithIndex(game.getFrameSize() - 1).score();

		assertTrue(lastFrameScore == 17);
	}

}
