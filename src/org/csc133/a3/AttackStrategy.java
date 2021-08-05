package org.csc133.a3;

/**
 * The AttackStrategy class causes NonPlayerHelicopters to go towards the
 * player's helicopter
 *
 * @author Jeremy Persing
 * @section 4
 * @SID 9682
 * @version 3.0
 */

public class AttackStrategy extends Strategy {
    private Helicopter mainHelicopter;

    AttackStrategy(GameWorld gameWorld) {
        super(gameWorld);
        mainHelicopter = gameWorld.getGameObjectCollection().getMainHelicopter();
    }

    public void executeStrategy(NonPlayerHelicopter nph) {
        int angle = getAngleBetweenObjects(nph, mainHelicopter);

        if (angle > 0) {
            nph.steerRight();
        }
        else if (angle < 0) {
            nph.steerLeft();
        }
        getGameWorld().setBoundedHelicopterHeading(nph);

        // If the heading is +- 35 degrees, set the heading to the angle
        if (nph.getHeading() < Math.abs(angle) + 35
                && nph.getHeading() > Math.abs(angle) - 35) {
            nph.setHeading(angle);
        }
    }

    public String getName() {
        return "Attack Strategy";
    }
}