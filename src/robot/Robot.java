package robot;

import java.util.ArrayList;

import static apple.laf.JRSUIConstants.Direction;
import static apple.laf.JRSUIConstants.Direction.*;
import static robot.Instruction.*;
import static robot.RoadBookCalculator.calculateRoadBook;

public class Robot {

    private Coordinates position;
    private Direction direction;
    private boolean isLanded;
    private RoadBook roadBook;
    private final double energyConsumption; // energie consommée pour la réalisation d'une action dans les conditions idéales
    private LandSensor landSensor;
    private Battery cells;

    public Robot() {
        this(1.0, new Battery());
    }

    public Robot(double energyConsumption, Battery cells) {
        isLanded = false;
        this.energyConsumption = energyConsumption;
        this.cells = cells;
    }

    public void land(Coordinates landPosition, LandSensor sensor) {
        position = landPosition;
        direction = NORTH;
        isLanded = true;
        landSensor = sensor;
    }

    public int getXposition() throws UnlandedRobotException {
        if (!isLanded) throw new UnlandedRobotException();
        return position.getX();
    }

    public int getYposition() throws UnlandedRobotException {
        if (!isLanded) throw new UnlandedRobotException();
        return position.getY();
    }

    public Direction getDirection() throws UnlandedRobotException {
        if (!isLanded) throw new UnlandedRobotException();
        return direction;
    }

    public void moveForward() throws UnlandedRobotException, InterruptedException {
        if (!isLanded) throw new UnlandedRobotException();
        Coordinates nextPosition = MapTools.nextForwardPosition(position, direction);
        double neededEnergy = landSensor.getPointToPointEnergyCoefficient(position, nextPosition) * energyConsumption;
        try {
            cells.use(neededEnergy);
        } catch (InsufficientChargeException e) {
            wait(cells.timeToSufficientCharge(neededEnergy));
        }
        position = nextPosition;
    }

    public void moveBackward() throws UnlandedRobotException {
        if (!isLanded) throw new UnlandedRobotException();
        position = MapTools.nextBackwardPosition(position, direction);
    }

    public void turnLeft() throws UnlandedRobotException {
        if (!isLanded) throw new UnlandedRobotException();
        direction = MapTools.counterclockwise(direction);
    }

    public void turnRight() throws UnlandedRobotException {
        if (!isLanded) throw new UnlandedRobotException();
        direction = MapTools.clockwise(direction);
    }

    public void setRoadBook(RoadBook roadBook) {
        this.roadBook = roadBook;
    }

    public void letsGo() throws UnlandedRobotException, UndefinedRoadbookException, InterruptedException {
        if (roadBook==null) throw new UndefinedRoadbookException();
        while (roadBook.hasInstruction()) {
            Instruction nextInstruction = roadBook.next();
            if (nextInstruction == FORWARD) moveForward();
            else if (nextInstruction == BACKWARD) moveBackward();
            else if (nextInstruction == TURNLEFT) turnLeft();
            else if (nextInstruction == TURNRIGHT) turnRight();
        }
    }

    public void computeRoadTo(Coordinates destination) throws UnlandedRobotException {
        if (!isLanded) throw new UnlandedRobotException();
        setRoadBook(calculateRoadBook(direction, position, destination, new ArrayList<Instruction>()));
    }

}
