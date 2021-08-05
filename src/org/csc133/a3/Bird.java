package org.csc133.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;

import java.util.Random;

/**
 * The Bird class inherits from Movable. A Bird poses as an object that the
 * helicopter must not intersect with.
 *
 * @author Jeremy Persing
 * @section 4
 * @SID 9682
 * @version 3.0
 */

public class Bird extends Movable{
    private Image [] flapOneImages;
    private Image [] flapTwoImages;
    private Image [] flapThreeImages;
    private int rotationAmt;
    private int cycleCount = 0;

    Bird(int size, float x, float y, int r, int g, int b, GameWorld gw) {
        super(size, x, y, r, g, b);
        setSpeed(generateRandomSpeed());
        setHeading(generateRandomHeading());
        flapOneImages = gw.getGameImages().getFlapOneImages();
        flapTwoImages = gw.getGameImages().getFlapTwoImages();
        flapThreeImages = gw.getGameImages().getFlapThreeImages();
        rotationAmt = 360 / flapOneImages.length;
    }

    public void updateHeading() {
        int randomNum = -5 + new Random().nextInt(10);
        setHeading(getHeading() + randomNum);
    }

    @Override
    public void setColor(int r, int g, int b) {
        System.out.println("Birds can't update their color");
    }

    @Override
    public boolean collidesWith(GameObject otherObject) {
        float xDistance = otherObject.getXLocation() - this.getXLocation();
        float yDistance = otherObject.getYLocation() - this.getYLocation();

        float distanceBetweenObjects =
                (xDistance * xDistance + yDistance * yDistance);


        float firstRadius = this.getSize() / 2;
        float secondRadius = otherObject.getSize() / 2;
        float radiiSquare = (firstRadius * firstRadius  +
                2 * firstRadius * secondRadius +
                firstRadius * firstRadius);

        if (distanceBetweenObjects <= radiiSquare) {
            return true;
        }
        return false;
    }

    @Override
    public void handleCollision(GameObject otherObject) {}

    @Override
    public String toString() {
        int color = getColor();

        String birdDescription = "Bird:"
                + " loc=" + getXLocation() + ", "  + getYLocation()
                + " color=[" + ColorUtil.red(color) + ","
                + ColorUtil.green(color) + ","
                + ColorUtil.blue(color) + "]"
                + " heading=" + getHeading()
                + " speed=" + getSpeed()
                + " size=" + getSize();

        return birdDescription;
    }

    @Override
    // Starts drawing at the top left corner of the object
    public void draw(Graphics g, Point containerOrigin) {
        int index = getHeading() / rotationAmt; // For rotating the bird

        if (cycleCount == 0) {
            g.drawImage(flapOneImages[index], findCorrectXLocation(),
                    findCorrectYLocation(), getSize(), getSize());
        }
        else if (cycleCount == 1) {
            g.drawImage(flapTwoImages[index], findCorrectXLocation(),
                    findCorrectYLocation(), getSize(), getSize());
        }
        else {
            g.drawImage(flapThreeImages[index], findCorrectXLocation(),
                    findCorrectYLocation(), getSize(), getSize());
        }

        // Flap the birds wings
        cycleCount = (cycleCount + 1) % 3; // % 3 as that is the num of flapImgs
    }
}