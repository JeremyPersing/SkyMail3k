package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;

import java.util.Random;

/**
 * The NonPlayerHelicopter class introduces NonPlayerHelicopters into the game
 *
 * @author Jeremy Persing
 * @section 4
 * @SID 9682
 * @version 3.0
 */

public class NonPlayerHelicopter extends Helicopter {
    private Strategy strategy;
    private Strategy [] strategies = new Strategy[3];

    NonPlayerHelicopter(int size, float x, float y, int r, int g, int b,
                        GameWorld gw) {
        super(size, x, y, r, g, b, gw);
        setDamageLevel(0);
        setSpeed(10);
        setRotorSpeed(300);
        setMaxDamageLevel(10);
        setHeading(0);

        Strategy advance = new AdvanceStrategy(gw);
        Strategy attack = new AttackStrategy(gw);
        Strategy gateKeeper = new GateKeeperStrategy(gw);
        strategies[0] = advance;
        strategies[1] = attack;
        strategies[2] = gateKeeper;
        assignRandomStrategy();
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public void assignRandomStrategy() {
        Random random = new Random();
        int randomInt = random.nextInt(3); // Value between 0 and 2
        // The strategy must be a new one
        if (strategies[randomInt] == strategy) {
            int maxIndex = strategies.length - 1;
            if (randomInt + 1 > maxIndex) {
                randomInt -= 1;
            }
            else {
                randomInt += 1;
            }
        }
        setStrategy(strategies[randomInt]);
    }

    public void invokeStrategy() {
        strategy.executeStrategy(this);
    }

    @Override
    public String toString() {
        int color = getColor();
        String helicopterDescription = "NonPlayerHelicopter:"
                + " loc=" + getXLocation() + ", " + getYLocation()
                + " color=[" + ColorUtil.red(color) + ","
                + ColorUtil.green(color) + ","
                + ColorUtil.blue(color) + "]"
                + " heading=" + getHeading()
                + " speed=" + getSpeed()
                + " damageLevel=" + getDamageLevel()
                + " strategy=" + strategy.getName();

        return helicopterDescription;
    }
}
