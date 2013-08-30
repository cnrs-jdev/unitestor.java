package robot;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static robot.Direction.EAST;
import static robot.Direction.WEST;


public class RobotUnitTest {

	private Robot robot;

	@Before
	void setUp() {
		robot = new Robot();
	}

	@Test
	public void testLand() throws UnlandedRobotException {
		//__WHEN__
		robot.land(new Coordinates(3,0));

		//__THEN__
		Assert.assertEquals(3, robot.getXposition());
		Assert.assertEquals(0, robot.getYposition());
	}

	@Test (expected = UnlandedRobotException.class)
	public void testRobotMustBeLandedBeforeAnyMove() throws Exception {
		robot.moveForward();
	}

	@Test
	public void testMoveForward() throws UnlandedRobotException {
		//__GIVEN__
		robot.land(new Coordinates(3, 0));
		int currentXposition = robot.getXposition();
		int currentYposition = robot.getYposition();

		//__WHEN__
		robot.moveForward();

		//__THEN__
		Assert.assertEquals(currentXposition, robot.getXposition());
		Assert.assertEquals(currentYposition+1, robot.getYposition());

	}

	@Test
	public void testMoveBackward() throws UnlandedRobotException {
		// __GIVEN__
		robot.land(new Coordinates(3,0));
		int currentXposition = robot.getXposition();
		int currentYposition = robot.getYposition();
		
		// __WHEN__
		robot.moveBackward();
		
		// __THEN__
		Assert.assertEquals(currentXposition, robot.getXposition());
		Assert.assertEquals(currentYposition - 1, robot.getYposition());
	}

	@Test
	public void testTurnLeft() throws UnlandedRobotException {
		robot.land(new Coordinates(3, 0));
		robot.turnLeft();
		Assert.assertEquals(WEST, robot.getDirection());
	}

	@Test
	public void testTurnRight() throws UnlandedRobotException {
		robot.land(new Coordinates(3, 0));
		robot.turnRight();
		Assert.assertEquals(EAST, robot.getDirection());
	}

	@Test (expected = UndefinedRoadbookException.class)
	public void testLetsGoWithoutRoadbook() throws Exception {
		robot.land(new Coordinates(3,0));
		robot.letsGo();
	}

	@Test
	public void testFollowInstruction() throws Exception {
		// __GIVEN__
		robot.land(new Coordinates(5, 7));
		
		// __WHEN__
		robot.setRoadBook(new RoadBook(Arrays.asList(Instruction.FORWARD, Instruction.FORWARD, Instruction.TURNLEFT, Instruction.FORWARD)));
		robot.letsGo();

		// __THEN__
		Assert.assertEquals(4, robot.getXposition());
		Assert.assertEquals(9, robot.getYposition());
	}

	@Test (expected = UnlandedRobotException.class)
	public void testMoveToWithUnlandedRobot() throws Exception {
		robot.computeRoadTo(new Coordinates(3, 5));
	}

	@Test
	public void testMoveTo() throws UnlandedRobotException {
		robot.land(new Coordinates(3, 0));
		robot.computeRoadTo(new Coordinates(7, 5));
	}

	@Test
	public void testComputeRoadTo() throws UnlandedRobotException, UndefinedRoadbookException {
		// __GIVEN__
		robot.land(new Coordinates(3,0));
		
		// __WHEN__
		robot.computeRoadTo(new Coordinates(0, -6));
		robot.letsGo();
		
		// __THEN__
		Assert.assertEquals(0, robot.getXposition());
		Assert.assertEquals(-6, robot.getYposition());
	}
}
