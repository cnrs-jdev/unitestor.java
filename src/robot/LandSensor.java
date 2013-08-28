package robot;

import java.util.Random;

public class LandSensor {

    public double getPointToPointEnergyCoefficient(Coordinates coordinate1, Coordinates coordinate2) {
        Random random = new Random();
        double distance = distance(coordinate1, coordinate2);
        return 1 + distance / (distance *random.nextDouble());
    }

    public double distance(Coordinates coordinate1, Coordinates coordinate2) {
        return Math.sqrt(Math.pow(coordinate1.getX()-coordinate2.getX(), 2) + Math.pow(coordinate1.getX()-coordinate2.getX(),2));
    }
}
