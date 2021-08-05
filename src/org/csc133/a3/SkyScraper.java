package org.csc133.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;

/**
 * The SkyScraper class inherits from Fixed. A SkyScraper acts as objective
 * for users to reach with a Helicopter
 *
 * @author Jeremy Persing
 * @section 4
 * @SID 9682
 * @version 3.0
 */

public class SkyScraper extends Fixed{
    private int sequenceNumber;
    private Image skyScraperMask;
    private Image [] skyScraperImages = new Image[10];
    GameWorld gw;

    SkyScraper(int size,
               float x, float y,
               int r, int g, int b,
               int sequenceNumber, GameWorld gw
    ) {
        super(size, x, y, r, g, b);
        this.sequenceNumber = sequenceNumber;
        this.gw = gw;

        skyScraperMask = gw.getGameImages().getSkyScraperMask();
        for (int i = 0; i < skyScraperImages.length; i++) {
            skyScraperImages[i] = gw.getGameImages().getSkyScraperImages()[i];
        }
    }

    public int getSequenceNumber() { return sequenceNumber; }

    public void drainScraperColor() {
        skyScraperImages[sequenceNumber] = skyScraperImages[sequenceNumber]
                .modifyAlphaWithTranslucency((byte) 180);
        setColor(60,60,60);
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
    public void handleCollision(GameObject otherObject) {
    }

    @Override
    public String toString() {
        int color = getColor();
        int size = getSize();

        String skyScraperDescription = "SkyScraper:"
                + " loc=" + getXLocation() + ", " + getYLocation()
                + " color=[" + ColorUtil.red(color) + ","
                + ColorUtil.green(color) + ","
                + ColorUtil.blue(color) + "]"
                + " size=" + size
                + " seqNum=" + this.sequenceNumber;
        return skyScraperDescription;
    }


    @Override
    public void draw(Graphics g, Point containerOrigin) {
        g.setColor(getColor());
        g.drawImage(skyScraperMask, findCorrectXLocation(),
                findCorrectYLocation(), getSize(), getSize());
        g.drawImage(skyScraperImages[sequenceNumber], findCorrectXLocation(),
                findCorrectYLocation(), getSize(), getSize());
    }
}
