package robot;

import java.util.List;

public class Robot {

    private Coordinates position;
    private boolean isLanded;

    public Robot() {
        isLanded = false;
    }

    public float getXposition() {
        return position.getX();
    }

    public float getYposition() {
        return position.getY();
    }

    public void land(Coordinates landPosition) {
        position = landPosition;
        isLanded = true;
    }

    public void followInstructions(List<Instruction> instructions) {
        //To change body of created methods use File | Settings | File Templates.
    }

    public void moveForward() throws UnlandedRobotException {
        if  (!isLanded) throw new UnlandedRobotException();

    }
}
