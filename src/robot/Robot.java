package robot;

import java.util.ArrayList;
import java.util.List;

import static robot.Direction.*;
import static robot.Instruction.*;

public class Robot {

    private Coordinates position;
    private Direction direction;
    private boolean isLanded;
    private RoadBook roadBook;
    private final double energyConsumption; // energie consommée pour la réalisation d'une action dans les conditions idéales

    public Robot() {
        this(1.0);
    }

    public Robot(double energyConsumption) {
        isLanded = false;
        this.energyConsumption = energyConsumption;
    }

    public void land(Coordinates landPosition) {
        position = landPosition;
        direction = NORTH;
        isLanded = true;
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

    public void moveForward() throws UnlandedRobotException {
        if (!isLanded) throw new UnlandedRobotException();
        nextForwardPosition(position, direction);
    }

    public void moveBackward() throws UnlandedRobotException {
        if (!isLanded) throw new UnlandedRobotException();
        if (direction == NORTH)
            position = new Coordinates(position.getX(), position.getY() - 1);
        else if (direction == SOUTH)
            position = new Coordinates(position.getX(), position.getY() + 1);
        else if (direction == EAST)
            position = new Coordinates(position.getX() - 1, position.getY());
        else
            position = new Coordinates(position.getX() + 1, position.getY());
    }

    public void turnLeft() throws UnlandedRobotException {
        if (!isLanded) throw new UnlandedRobotException();
        direction = counterclockwise(direction);
    }

    public void turnRight() throws UnlandedRobotException {
        if (!isLanded) throw new UnlandedRobotException();
        direction = clockwise(direction);
    }

    private Coordinates nextForwardPosition(Coordinates position, Direction direction) {
        if (direction == NORTH)
            return new Coordinates(position.getX(), position.getY() + 1);
        if (direction == SOUTH)
            return new Coordinates(position.getX(), position.getY() - 1);
        if (direction == EAST)
            return new Coordinates(position.getX() + 1, position.getY());
        return new Coordinates(position.getX() - 1, position.getY());
    }

    private Direction counterclockwise(Direction direction) {
        if (direction == NORTH) return WEST;
        if (direction == WEST) return SOUTH;
        if (direction == SOUTH) return EAST;
        return NORTH;
    }


    private Direction clockwise(Direction direction) {
        if (direction == NORTH) return EAST;
        if (direction == EAST) return SOUTH;
        if (direction == SOUTH) return WEST;
        return NORTH;
    }

    public void setRoadBook(RoadBook roadBook) {
        this.roadBook = roadBook;
    }

    public void letsGo() throws UnlandedRobotException {
        while (roadBook.hasInstruction()) {
            Instruction nextInstruction = roadBook.next();
            if (nextInstruction == FORWARD) moveForward();
            else if (nextInstruction == BACKWARD) moveBackward();
            else if (nextInstruction == TURNLEFT) turnLeft();
            else if (nextInstruction == TURNRIGHT) turnRight();
        }
    }

    public void moveTo(Coordinates destination) throws UnlandedRobotException {
        if (!isLanded) throw new UnlandedRobotException();
        RoadBook book = calculateRoadBook(direction, position, destination, new ArrayList<Instruction>());
        setRoadBook(book);
        letsGo();
    }

    private RoadBook calculateRoadBook(Direction direction, Coordinates position, Coordinates destination, ArrayList<Instruction> instructions) {
        List<Direction> directionList = new ArrayList<Direction>();
        if (destination.getX() < position.getX()) directionList.add(WEST);
        if (destination.getX() > position.getX()) directionList.add(EAST);
        if (destination.getY() < position.getY()) directionList.add(SOUTH);
        if (destination.getY() > position.getY()) directionList.add(NORTH);
        if (directionList.isEmpty()) return new RoadBook(instructions);
        if (directionList.contains(direction)) {
            instructions.add(FORWARD);
            return calculateRoadBook(direction, nextForwardPosition(position, direction), destination, instructions);
        } else {
            instructions.add(TURNRIGHT);
            return calculateRoadBook(clockwise(direction), position, destination, instructions);
        }
    }

}
