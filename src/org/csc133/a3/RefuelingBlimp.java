package org.csc133.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;

/**
 * The RefuelingBlimp class inherits from Fixed and is used by helicopters
 * to refuel during the game.
 *
 * @author Jeremy Persing
 * @section 4
 * @SID 9682
 * @version 3.0
 */

public class RefuelingBlimp extends Fixed{
    private int capacity;
    private int blimpWidth;
    private Image blimpImage;
    private Image blimpMask;

    RefuelingBlimp(int size, float x, float y, int r, int g, int b,
                   GameWorld gw) {
        super(size, x, y, r, g, b);
        capacity = size; // Will represent the blimp length
        blimpWidth = size / 2; // Blimps fit in a rectangle => width < length
        blimpImage = gw.getGameImages().getBlimpImage();
        blimpMask = gw.getGameImages().getBlimpMask();
    }

    // Get the amount of fuel the blimp contains
    public int getCapacity() {
        return capacity;
    }

    // Changes the color of the blimp after it's fuel has been used
    private void drainBlimpColor() {
        blimpImage = blimpImage.modifyAlphaWithTranslucency((byte) 120);
        setColor(162,162,163);
    }

    // Drains the blimp of its color and fuel
    public void drainBlimp() {
        drainBlimpColor();
        capacity = 0;
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
    public void handleCollision(GameObject otherObject) { }

    @Override
    public String toString() {
        int color = getColor();
        String blimpDescription = "RefuelingBlimp:"
                + " loc=" + getXLocation() + ", " + getYLocation()
                + " color=[" + ColorUtil.red(color) + ","
                + ColorUtil.green(color) + ","
                + ColorUtil.blue(color) + "]"
                + " size=" + getSize()
                + " capacity=" + capacity;
        return blimpDescription;
    }

    @Override
    // The width of the Blimp is not the same as the size (height)
    public int findCorrectXLocation() {
        return (int) (getXLocation() - blimpWidth / 2);
    }

    @Override
    public void draw(Graphics g, Point containerOrigin) {
        g.drawImage(blimpMask, findCorrectXLocation(), findCorrectYLocation(),
                blimpWidth, getSize());
        g.drawImage(blimpImage, findCorrectXLocation(), findCorrectYLocation(),
                blimpWidth, getSize());
        String capacity = String.valueOf(getCapacity());
        g.setColor(ColorUtil.BLACK);
        g.drawString(capacity, (int) getXLocation() - 10,
                (int) getYLocation());
    }
}
