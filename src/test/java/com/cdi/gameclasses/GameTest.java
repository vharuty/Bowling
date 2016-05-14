package com.cdi.gameclasses;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class GameTest extends TestCase {

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
	 * Basic test to check the maximum score for 10 frames
	 */
	@Test
	public void testOnePinPerRoll() {
		game.init(10, 10, 2);
		int[] rolls = new int[20];

		for (int i = 0; i < 20; i++)
			rolls[i] = 1;

		for (int i = 0; i < 20; i++) {

			game.roll(rolls[i]);
		}
		int gameScor = game.score();
		assertTrue(gameScor == 20);
	}

	/*
	 * Checks maximum score+ bonus in case of mixed values for 5 frames
	 */
	@Test
	public void testRandomPinPerRoll() {
		int[] rolls = new int[10];
		game.init(5, 10, 2);
		rolls[0] = 1;
		rolls[1] = 5;
		rolls[2] = 5;
		rolls[3] = 5;
		rolls[4] = 10;
		rolls[5] = 3;
		rolls[6] = 4;
		rolls[7] = 10;
		rolls[8] = 3;
		rolls[9] = 4;

		for (int i = 0; i < 10; i++) {

			game.roll(rolls[i]);
		}
		int gameScor = game.score();
		assertTrue(gameScor == 67);
	}

	@Test
	public void testRandomPinGenerater() {
		int startRange = 1;
		int endRange = 10;
		Random random = new Random();
		for (int idx = 1; idx <= 10; ++idx) {

			long range = (long) endRange - (long) startRange + 1;
			// compute a fraction of the range, 0 <= frac < range
			long fraction = (long) (range * random.nextDouble());
			int randomNumber = (int) (fraction + startRange);
		}
		assertTrue(true);
	}

	/*
	 * Number of steps to complete the game without Spare or Strike
	 */

	@Test
	public void testNumberOfStepsToComplete1() {

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
		rolls[8] = 3;
		rolls[9] = 4;
		rolls[10] = 6;
		rolls[11] = 2;

		Game game = Game.getGameInstance();
		// int numberOfFrames, int pinsPerGame, int rollsPerGame
		game.init(5, 10, 2);
		int index = 0;
		while (!game.isOver()) {
			game.roll(rolls[index]);
			index++;
		}

		int numberOfSteps = 0;
		for (int k = 0; k < game.getFrameSize(); k++)
			numberOfSteps += game.getFrameWithIndex(k).rollsCompleted;
		assertTrue(numberOfSteps == 10);
	}

	/*
	 * Number of steps to complete the game without last frame Spare
	 */
	@Test
	public void testNumberOfStepsToComplete2() {

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
		rolls[10] = 6;
		rolls[11] = 2;

		Game game = Game.getGameInstance();
		// int numberOfFrames, int pinsPerGame, int rollsPerGame
		game.init(5, 10, 2);
		int index = 0;
		while (!game.isOver()) {
			game.roll(rolls[index]);
			index++;

		}

		int numberOfSteps = 0;
		for (int k = 0; k < game.getFrameSize(); k++)
			numberOfSteps += game.getFrameWithIndex(k).getRollsCompleted();
		assertTrue(numberOfSteps == 11);
	}

	/*
	 * Number of steps to complete the game without last frame Strike
	 */
	@Test
	public void testNumberOfStepsToComplete3() {

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
		rolls[9] = 5;
		rolls[10] = 6;
		rolls[11] = 2;

		Game game = Game.getGameInstance();
		// int numberOfFrames, int pinsPerGame, int rollsPerGame
		game.init(5, 10, 2);
		int index = 0;
		while (!game.isOver()) {
			game.roll(rolls[index]);
			index++;

		}

		int numberOfSteps = 0;
		for (int k = 0; k < game.getFrameSize(); k++)
			numberOfSteps += game.getFrameWithIndex(k).getRollsCompleted();
		assertTrue(numberOfSteps == 11);
	}

	/*
	 * Number of steps to complete the game without last 3 rolls Strike
	 */
	@Test
	public void testNumberOfStepsToComplete4() {

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

		int numberOfSteps = 0;
		for (int k = 0; k < game.getFrameSize(); k++)
			numberOfSteps += game.getFrameWithIndex(k).getRollsCompleted();
		assertTrue(numberOfSteps == 11);
	}
}
