package org.csc133.a3;

import java.util.Random;

/**
 * The Movable class inherits from GameObject and is used by objects that
 * can be moved
 *
 * @author Jeremy Persing
 * @section 4
 * @SID 9682
 * @version 3.0
 */

public abstract class Movable extends GameObject {
    private int heading;
    private int speed;

    Movable(int size, float x, float y, int r, int g, int b) {
        super(size, x, y, r, g, b);
    }

    public void move(int timeValue) {
        // timeValue is the UITimer event time in msec
        if (timeValue > 10) {
            timeValue = 5; // If this number is > 10 the game feels very slow
        }

        float [] currentLocation = getLocation();
        double theta = 90 - heading;
        double radiansTheta = Math.toRadians(theta);
        double velocity = (double) (speed) / timeValue;
        double deltaX = Math.cos(radiansTheta) * velocity;
        double deltaY = Math.sin(radiansTheta) * velocity;

        currentLocation[0] += deltaX;
        currentLocation[1] += deltaY;
        setLocation(currentLocation[0], currentLocation[1]);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public int getSpeed() { return speed; }
    public void setHeading(int heading) {
        // Account for when heading will go from 0 to 359
        if (heading < 0) {
            heading = 360 + heading;
        }
        else if (heading > 359) {
            heading = 0;
        }
        this.heading = heading;
    }
    public int getHeading() { return heading; }

    // Designed to generate a random speed for the Bird class
    public int generateRandomSpeed() {
        Random random = new Random();
        return random.nextInt(20) + 5;
    }
    public int generateRandomHeading() {
        Random random = new Random();
        return random.nextInt(360);
    }
}
