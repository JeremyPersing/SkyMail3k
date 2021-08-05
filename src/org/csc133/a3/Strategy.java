package org.csc133.a3;

import com.codename1.util.MathUtil;

import java.util.ArrayList;

/**
 * The Strategy class is a class that more specific strategies extend
 *
 * @author Jeremy Persing
 * @section 4
 * @SID 9682
 * @version 3.0
 */

public abstract class Strategy {
    private GameWorld gameWorld;

    Strategy(GameWorld gw) {
        gameWorld = gw;
    }

    abstract void executeStrategy(NonPlayerHelicopter nph);
    abstract String getName();

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public int getAngleBetweenObjects(GameObject obj1, GameObject obj2) {
        float xDistance = obj2.getXLocation() - obj1.getXLocation();
        float yDistance = obj2.getYLocation() - obj1.getYLocation();

        // Find the angle that the heading needs to be to reach the object
        double angle = Math.toDegrees(MathUtil.atan2(xDistance, yDistance));
        return (int) angle;
    }

    public boolean checkCollision(GameObject obj1, GameObject obj2) {
        float xDistance = obj2.getXLocation() - obj1.getXLocation();
        float yDistance = obj2.getYLocation() - obj1.getYLocation();

        float distanceBetweenObjects =
                (xDistance * xDistance + yDistance * yDistance);


        float firstRadius = obj1.getSize() / 2;
        float secondRadius = obj2.getSize() / 2;
        float radiiSquare = (firstRadius * firstRadius  +
                2 * firstRadius * secondRadius +
                firstRadius * firstRadius);

        if (distanceBetweenObjects <= radiiSquare) {
            return true;
        }
        return false;
    }

    public ArrayList<SkyScraper> getAllSkyScrapers() {
        return gameWorld.getGameObjectCollection().getAllSkyScrapers();
    }
}
