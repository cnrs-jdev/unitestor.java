package robot;

import junit.framework.Assert;
import org.junit.Test;

import java.util.Arrays;

public class RobotUnitTest {

    @Test
    public void testLand() {
        Robot robot = new Robot();
        robot.land(new Coordinates(3,0));
        Assert.assertEquals(3f, robot.getXposition());
        Assert.assertEquals(0f, robot.getYposition());
    }

    @Test (expected = UnlandedRobotException.class)
    public void testRobotMustBeLandedBeforeAnyMove() throws Exception {
        Robot robot = new Robot();
        robot.moveForward();
    }

    @Test
    public void testMoveForward() throws UnlandedRobotException {
        Robot robot = new Robot();
        robot.land(new Coordinates(3,0));
        float currentXposition = robot.getXposition();
        robot.moveForward();
        Assert.assertEquals(currentXposition+1, robot.getXposition());

    }

    @Test
    public void testFollowInstruction() {
        Robot robot = new Robot();
        robot.land(new Coordinates(5, 7));
        robot.followInstructions(Arrays.asList(Instruction.FORWARD,Instruction.FORWARD, Instruction.TURNLEFT, Instruction.FORWARD));
        Assert.assertEquals(6, robot.getXposition());
        Assert.assertEquals(9, robot.getYposition());
    }
}
