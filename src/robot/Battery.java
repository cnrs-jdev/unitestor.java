package robot;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Battery {

    private float chargeLevel;

    public Battery() {
        chargeLevel = 100;
    }

    public void charge() {
        chargeLevel = chargeLevel*1.1f + 1;
    }

    public void setUp() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                charge();
            }
        }, 0, 1000);
    }

    public float getChargeLevel(){
        return chargeLevel;
    }

    public void use(float energy) throws InsufficientChargeException {
        if (chargeLevel < energy) throw new InsufficientChargeException();
        chargeLevel -= energy;
    }
}
