package robot;

import apple.laf.JRSUIConstants;

import java.util.ArrayList;
import java.util.List;

import static apple.laf.JRSUIConstants.Direction;
import static robot.Instruction.*;

public class RoadBookCalculator {

    static RoadBook calculateRoadBook(Direction direction, Coordinates position, Coordinates destination, ArrayList<Instruction> instructions) {
        List<JRSUIConstants.Direction> directionList = new ArrayList<JRSUIConstants.Direction>();
        if (destination.getX() < position.getX()) directionList.add(Direction.WEST);
        if (destination.getX() > position.getX()) directionList.add(Direction.EAST);
        if (destination.getY() < position.getY()) directionList.add(Direction.SOUTH);
        if (destination.getY() > position.getY()) directionList.add(Direction.NORTH);
        if (directionList.isEmpty()) return new RoadBook(instructions);
        if (directionList.contains(direction)) {
            instructions.add(FORWARD);
            return calculateRoadBook(direction, MapTools.nextForwardPosition(position, direction), destination, instructions);
        } else {
            instructions.add(TURNRIGHT);
            return calculateRoadBook(MapTools.clockwise(direction), position, destination, instructions);
        }
    }
}