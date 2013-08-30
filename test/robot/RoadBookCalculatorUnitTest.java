package robot;

import static robot.Direction.EAST;
import static robot.Direction.NORTH;
import static robot.Direction.SOUTH;
import static robot.Direction.WEST;
import static robot.RoadBookCalculator.calculateRoadBook;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class RoadBookCalculatorUnitTest {

    RoadBook book;
    Coordinates startPosition;
    ArrayList<Instruction> instructions;

    @Before
    public void setUp(){
        book = null;
        startPosition = new Coordinates(1,1);
        instructions = new ArrayList<Instruction>();
    }


    @Test
    public void testCalculateAtDestination() {
        book = calculateRoadBook(NORTH, startPosition, startPosition, instructions);
        Assert.assertFalse(book.hasInstruction());
    }

    @Test
    public void testCalculateOneInstructionNorthRoad() {
        book = calculateRoadBook(NORTH, startPosition, new Coordinates(1,2), instructions);
        Assert.assertEquals(Instruction.FORWARD, book.next());
        Assert.assertFalse(book.hasInstruction());
    }

    @Test
    public void testCalculateOneInstructionSouthRoad() {
        book = calculateRoadBook(SOUTH, startPosition, new Coordinates(1,0), instructions);
        Assert.assertEquals(Instruction.FORWARD, book.next());
        Assert.assertFalse(book.hasInstruction());
    }

    @Test
    public void testCalculateOneInstructionEastRoad() {
        book = calculateRoadBook(EAST, startPosition, new Coordinates(2,1), instructions);
        Assert.assertEquals(Instruction.FORWARD, book.next());
        Assert.assertFalse(book.hasInstruction());
    }

    @Test
    public void testCalculateOneInstructionWestRoad() {
        book = calculateRoadBook(WEST, startPosition, new Coordinates(0,1), instructions);
        Assert.assertEquals(Instruction.FORWARD, book.next());
        Assert.assertFalse(book.hasInstruction());
    }

    @Test
    public void testCalculateStraightForwardRoad() {
        book = calculateRoadBook(WEST, startPosition, new Coordinates(-4,1), instructions);
        Assert.assertEquals(Instruction.FORWARD, book.next());
        Assert.assertEquals(Instruction.FORWARD, book.next());
        Assert.assertEquals(Instruction.FORWARD, book.next());
        Assert.assertEquals(Instruction.FORWARD, book.next());
        Assert.assertEquals(Instruction.FORWARD, book.next());
        Assert.assertFalse(book.hasInstruction());
    }

    @Test
    public void testCalculateNEroad() {
        book = calculateRoadBook(NORTH, startPosition, new Coordinates(2,2), instructions);
        Assert.assertEquals(Instruction.FORWARD, book.next());
        Assert.assertEquals(Instruction.TURNRIGHT, book.next());
        Assert.assertEquals(Instruction.FORWARD, book.next());
        Assert.assertFalse(book.hasInstruction());
    }

    @Test
    public void testCalculateNWroad() {
        book = calculateRoadBook(NORTH, startPosition, new Coordinates(0,2), instructions);
        Assert.assertEquals(Instruction.FORWARD, book.next());
        Assert.assertEquals(Instruction.TURNRIGHT, book.next());
        Assert.assertEquals(Instruction.TURNRIGHT, book.next());
        Assert.assertEquals(Instruction.TURNRIGHT, book.next());
        Assert.assertEquals(Instruction.FORWARD, book.next());
        Assert.assertFalse(book.hasInstruction());
    }

    @Test
    public void testCalculateSEroad() {
        book = calculateRoadBook(NORTH, startPosition, new Coordinates(2,0), instructions);
        Assert.assertEquals(Instruction.TURNRIGHT, book.next());
        Assert.assertEquals(Instruction.FORWARD, book.next());
        Assert.assertEquals(Instruction.TURNRIGHT, book.next());
        Assert.assertEquals(Instruction.FORWARD, book.next());
        Assert.assertFalse(book.hasInstruction());
    }

    @Test
    public void testCalculateSWroad() {
        book = calculateRoadBook(NORTH, startPosition, new Coordinates(0,0), instructions);
        Assert.assertEquals(Instruction.TURNRIGHT, book.next());
        Assert.assertEquals(Instruction.TURNRIGHT, book.next());
        Assert.assertEquals(Instruction.FORWARD, book.next());
        Assert.assertEquals(Instruction.TURNRIGHT, book.next());
        Assert.assertEquals(Instruction.FORWARD, book.next());
        Assert.assertFalse(book.hasInstruction());
    }


}
