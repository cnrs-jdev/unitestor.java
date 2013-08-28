package robot;

import junit.framework.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Random;

import static robot.LandSensor.distance;

public class LandSensorUnitTest {

    @Test
    public void testDistance() {
        Assert.assertEquals(0.0, distance(new Coordinates(0, 0), new Coordinates(0, 0)));
        Assert.assertEquals(1.0, distance(new Coordinates(0,0), new Coordinates(1,0)));
        Assert.assertEquals(1.0, distance(new Coordinates(0,0), new Coordinates(0,1)));
        Assert.assertEquals(Math.sqrt(2), distance(new Coordinates(0,0), new Coordinates(1,1)));
        Assert.assertEquals(5.0, distance(new Coordinates(1,2), new Coordinates(5,5)));
    }


    @Test
    public void testgetPointToPointEnergyCoefficient() {
        Random random = Mockito.mock(Random.class);
        Mockito.when(random.nextDouble()).thenReturn(1.0, 2.0);
        LandSensor sensor = new LandSensor(random);
        Assert.assertEquals(2.0, sensor.getPointToPointEnergyCoefficient(new Coordinates(0,0), new Coordinates(1,0)));
        Assert.assertEquals(1.5, sensor.getPointToPointEnergyCoefficient(new Coordinates(1,0), new Coordinates(1,1)));

    }
}
