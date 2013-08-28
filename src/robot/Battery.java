package robot;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Battery {

    private final long CHARGE_TOP = 1000;
    private float chargeLevel;

    public Battery() {
        chargeLevel = 100;
    }

    public void charge() {
        chargeLevel = chargeFunction(chargeLevel);
    }

    private static float chargeFunction(float charge) {
        return charge*1.1f + 1;
    }

    public void setUp() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                charge();
            }
        }, 0, CHARGE_TOP);
    }

    public float getChargeLevel(){
        return chargeLevel;
    }

    public void use(double energy) throws InsufficientChargeException {
        if (chargeLevel < energy) throw new InsufficientChargeException();
        chargeLevel -= energy;
    }

    public long timeToSufficientCharge(double neededEnergy) {
        int clock = 0;
        float charge = chargeLevel;
        while (charge<neededEnergy) {
            charge = chargeFunction(charge);
            clock++;
        }
        return clock*CHARGE_TOP;
    }
}