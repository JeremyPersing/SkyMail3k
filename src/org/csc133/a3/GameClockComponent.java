package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;

import java.io.IOException;

/**
 * The GameClockComponent class to keep track of elapsed time
 *
 * @author Jeremy Persing
 * @section 4
 * @SID 9682
 * @version 3.0
 */

public class GameClockComponent extends GlassContainerComponent{
    Image colonImage;
    Image [] digitsWithDecimal = new Image[10];
    private final static int COLON_IDX = 2;
    private final static int DECIMAL_IDX = 4;
    private final static int numDigitsShowing = 6;
    private int darkerLEDBackgroundColor = ColorUtil.BLUE;
    private Timer timer;
    private int colorChangeTime = 10;

    public GameClockComponent() {
        super(numDigitsShowing, ColorUtil.CYAN);

        try {
            colonImage = Image.createImage("/LED_colon.png");
            for (int i = 0; i < 10; i++) {
                digitsWithDecimal[i] = Image.createImage("/LED_digit_" + i +
                        "_with_dot.png");
            }
        } catch (IOException e) {e.printStackTrace();}

        glassDigits[COLON_IDX] = colonImage;
        glassDigits[DECIMAL_IDX] = digitsWithDecimal[0];
        timer = new Timer();
        startElapsedTime();
    }

    public void resetElapsedTime() {
        timer.startTimer();
    }

    public void startElapsedTime() {
        timer.startTimer();
    }

    public void stopElapsedTime() {
        stop();
    }

    public void pauseElapsedTime() {
        timer.pauseTimer();
        stop();
    }

    public void unPauseElapsedTime() {
        timer.startPausedTimer();
        start();
    }

    public long getElapsedTime() {
        return timer.getElapsedTime();
    }

    // Used when printing ending game time, formatted like XX:XX.X
    public String getCurrentTimeFormatted() {
        String minutesTens = String.valueOf(getMinutesTensSpot());
        String minutesOnes = String.valueOf(getMinutesOnesSpot());
        String secondsTens = String.valueOf(getSecondsTensSpot());
        String secondsOnes = String.valueOf(getSecondsOnesSpot());

        return minutesTens + minutesOnes + ":" + secondsTens + secondsOnes +
                "." + getDeciSecondsOneSpot();
    }

    private void setCurrentTime() {
        glassDigits[0] = digitImages[getMinutesTensSpot()];
        glassDigits[1] = digitImages[getMinutesOnesSpot()];
        glassDigits[3] = digitImages[getSecondsTensSpot()];
        glassDigits[DECIMAL_IDX] = digitsWithDecimal[getSecondsOnesSpot()];
        glassDigits[5] = digitImages[getDeciSecondsOneSpot()];
    }

    private int getMinutesTensSpot() {
        return (int) timer.getElapsedMinutes() / 10;
    }

    private int getMinutesOnesSpot() {
        return (int) timer.getElapsedMinutes() % 10;
    }

    private int getSecondsTensSpot() {
        return ((int) timer.getElapsedSeconds() % 60) / 10;
    }

    private int getSecondsOnesSpot() {
        return (int) timer.getElapsedSeconds() % 10;
    }

    private int getDeciSecondsOneSpot() {
        return (int) timer.getElapsedDeciSeconds() % 10;
    }

    public boolean animate() {
        setCurrentTime();
        return true;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        final int COLOR_PAD = 1;

        int digitWidth = glassDigits[0].getWidth();
        int digitHeight = glassDigits[0].getHeight();
        int clockWidth = numDigitsShowing*digitWidth;

        float scaleFactor = Math.min(
                getInnerHeight()/(float)digitHeight,
                getInnerWidth()/(float)clockWidth
        );

        int displayDigitWidth = (int)(scaleFactor * digitWidth);
        int displayDigitHeight = (int)(scaleFactor * digitHeight);
        int displayClockWidth = displayDigitWidth * numDigitsShowing;

        int displayX = getX() + (getWidth() - displayClockWidth) / 2;
        int displayY = getY() + (getHeight() - displayDigitHeight) / 2;

        // Setting background color & size
        g.setColor(ColorUtil.BLACK);
        g.fillRect(getX(), getY(), getWidth(), getHeight());

        // Setting offset background shade
        if (timer.getElapsedMinutes() >= colorChangeTime)
            darkerLEDBackgroundColor = ColorUtil.rgb(158, 0, 15);
        g.setColor(darkerLEDBackgroundColor);
        g.fillRect( displayX + COLOR_PAD,
                displayY + COLOR_PAD,
                displayClockWidth - COLOR_PAD * 2,
                displayDigitHeight - COLOR_PAD * 2
        );

        // Setting normal shade
        if (timer.getElapsedMinutes() >= colorChangeTime)
            setLedColor(ColorUtil.rgb(255, 82, 98));
        g.setColor(getLedColor());
        g.fillRect(displayX + COLOR_PAD,
                displayY + COLOR_PAD,
                5 * displayDigitWidth,
                displayDigitHeight - COLOR_PAD * 2);

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
