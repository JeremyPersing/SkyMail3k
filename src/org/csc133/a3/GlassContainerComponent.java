package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.Component;

import java.io.IOException;

/**
 * The GlassContainerComponent class is used to create a single pane that
 * goes onto the GlassCockpit view
 *
 * @author Jeremy Persing
 * @section 4
 * @SID 9682
 * @version 3.0
 */

public class GlassContainerComponent extends Component{
    Image [] digitImages = new Image[10];
    private int ledColor;
    private int value;
    private int prevValue = value;
    private int numDigitsShowing;
    Image [] glassDigits;


    public GlassContainerComponent(int numDigitsShowing, int ledColor) {
        this.numDigitsShowing = numDigitsShowing;
        glassDigits = new Image[numDigitsShowing];

        try {
            // Create the images for each digit
            for (int i = 0; i < 10; i++) {
                digitImages[i] = Image.createImage("/LED_digit_" + i + ".png");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Setting the default to all 0's
        for (int i = 0; i < numDigitsShowing; i++) {
            glassDigits[i] = digitImages[0];
        }

        this.ledColor = ledColor;
        setGlassDigits();
    }

    public void setLedColor(int ledColor) {this.ledColor = ledColor;}

    public int getLedColor() {return ledColor;}

    public void start() {
        getComponentForm().registerAnimated(this);
    }

    private void calculateDigits(int value) {
        // Finding the amount of places the value takes up
        // is it 1, 2, 3, or 4 digits
        int digitsPlace = 1;

        // Get the place that value ends on (ones place, tens place, etc)
        while ((value / digitsPlace) >= 10) {
            digitsPlace *= 10;
        }

        int valueLength = String.valueOf(value).length();
        int startIndexOfDigitsInGlass = numDigitsShowing - valueLength;

        // index 0 is far left in the glass container display
        for (int i = 0; i < numDigitsShowing; i++) {
            if (i < startIndexOfDigitsInGlass) {
                // Make sure that preceding numbers are zeros
                // If not implemented, will cause a problem when counting back
                if (glassDigits[i] != digitImages[0]) {
                    glassDigits[i] = digitImages[0];
                }
            }
            // We are now past the zeros and at where value needs to be placed
            else {
                // Find the value of the digit in the current place
                int quotient = value / digitsPlace;


                glassDigits[i] = digitImages[quotient];

                // Value is now = to the digits to the right of the curr digit
                value = value - (quotient * digitsPlace);
                digitsPlace /= 10;

            }
        }
    }

    private void setGlassDigits() {
        calculateDigits(value);
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() { return value; }

    public void stop() {
        getComponentForm().deregisterAnimated(this);
    }

    public void laidOut() {
        this.start();
    }

    // Used to reduce the number of repaints
    private boolean hasValueChanged() {
        if (value != prevValue) return true;
        return false;
    }

    public boolean animate() {
        if (hasValueChanged()) {
            prevValue = value;
            setGlassDigits();
            return true;
        }
        return false;
    }

    protected Dimension calcPreferredSize() {
        return new Dimension(digitImages[1].getWidth() * numDigitsShowing,
                digitImages[1].getHeight());
    }

    public void paint(Graphics g) {
        super.paint(g);
        final int COLOR_PAD = 1;

        int digitWidth = glassDigits[0].getWidth();
        int digitHeight = glassDigits[0].getHeight();
        int glassWidth = numDigitsShowing * digitWidth;

        float scaleFactor = Math.min(
                getInnerHeight()/(float)digitHeight,
                getInnerWidth()/(float)glassWidth
        );

        int displayDigitWidth = (int)(scaleFactor * digitWidth);
        int displayDigitHeight = (int)(scaleFactor * digitHeight);
        int displayGlassWidth = displayDigitWidth * numDigitsShowing;

        int displayX = getX() + (getWidth() - displayGlassWidth) / 2;
        int displayY = getY() + (getHeight() - displayDigitHeight) / 2;

        // Setting background color & size
        g.setColor(ColorUtil.BLACK);
        g.fillRect(getX(), getY(), getWidth(), getHeight());

        // Setting a color & size for a colored rectangle
        g.setColor(ledColor);
        g.fillRect( displayX + COLOR_PAD,
                displayY + COLOR_PAD,
                displayGlassWidth - COLOR_PAD * 2,
                displayDigitHeight - COLOR_PAD * 2
        );



        // Drawing the digits
        for (int digitIndex = 0; digitIndex < numDigitsShowing; digitIndex++) {
            g.drawImage(
                    glassDigits[digitIndex],
                    displayX + digitIndex * displayDigitWidth,
                    displayY,
                    displayDigitWidth,
                    displayDigitHeight);
        }
    }
}
