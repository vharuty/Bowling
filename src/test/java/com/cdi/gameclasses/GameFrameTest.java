package com.cdi.gameclasses;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class GameFrameTest extends TestCase {
	Frame frame;
	Game game;

	@Before
	protected void setUp() {
		frame = new GameFrame(2, 10);
		game = Game.getGameInstance();
		// init( int numberOfFrames, int pinsPerGame, int rollsPerGame);
		game.init(2, 10, 2);
	}

	@After
	protected void tearDown() {
		game.deleteObservers();
		game = null;
		frame = null;
	}

	/*
	 * Basic test case to check if number of pins affect the score of a frame
	 */
	@Test
	public void testScoreForFrameChanged() {
		frame.roll(1);
		frame.roll(1);
		int score = frame.score();
		assertTrue(score == 2);
	}

	/*
	 * After 2 consecutive rolls the status of frame should not be active
	 */
	@Test
	public void testFrameStatusisActiveChanged() {
		frame.roll(1);
		frame.roll(1);
		assertTrue(frame.isActive == false);
	}

	/*
	 * A status of frame is changed to "Spare" when number of pins becomes
	 * maximum for that frame after 2 consecutive rolls
	 */
	@Test
	public void testFrameStatusisSpareChanged() {
		frame.roll(4);
		frame.roll(6);
		assertTrue(frame.isSpare == true);
	}

	/*
	 * The amount of bonus is changed when status is changed to "Spare"
	 */
	@Test
	public void testFrameBonuseisSpare() {
		frame.roll(4);
		frame.roll(6);
		assertTrue(frame.bonusRolls == 1);
	}

	/*
	 * A status of frame is changed to "Strike" when the first ball kicks
	 * maximum number of pins
	 */
	@Test
	public void testFrameStatusStrikeChanged() {
		frame.roll(10);
		assertTrue(frame.isStrike == true);
	}

	/*
	 * The number of bonus steps is changed when status is changed to "Strike"
	 */
	@Test
	public void testFrameBonuseisStrike() {
		frame.roll(10);
		assertTrue(frame.bonusRolls == 2);
	}

	/*
	 * Score + bonus for the last and first frames when the last frame
	 * accumulates maximum number of pins in 2 rolls
	 */
	@Test
	public void testFrameBonuseScoreChange1() {
		game.roll(10);
		game.roll(5);
		game.roll(5);
		game.roll(6);

		assertTrue(game.getFrameWithIndex(0).score() == 20);
		assertTrue(game.getFrameWithIndex(1).score() == 16);
	}

	/*
	 * Score + bonus for the last and first frames when the last frame
	 * accumulates maximum number of pins in 1 rolls
	 */
	@Test
	public void testFrameBonuseScoreChange2() {
		game.roll(10);
		game.roll(10);
		game.roll(5);
		game.roll(6);
		assertTrue(game.getFrameWithIndex(0).score() == 25);
		assertTrue(game.getFrameWithIndex(1).score() == 21);
	}
}
