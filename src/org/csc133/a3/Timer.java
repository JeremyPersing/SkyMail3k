package org.csc133.a3;

/**
 * The Timer class is used to get calculate elapsed time
 *
 * @author Jeremy Persing
 * @section 4
 * @SID 9682
 * @version 3.0
 */

public class Timer {
    private long startTime;
    private long elapsedTime;

    public void startTimer() {
        startTime = System.currentTimeMillis();
    }

    public void pauseTimer() {
        elapsedTime = getElapsedTime();
    }

    public void startPausedTimer() {
        startTime = System.currentTimeMillis() - elapsedTime;
    }

    public long getElapsedTime() {
        return System.currentTimeMillis() - startTime;
    }

    public long getElapsedDeciSeconds() {
        return  getElapsedTime() / 100;
    }

    public long getElapsedSeconds() {
        return getElapsedTime() / 1000;
    }

    public long getElapsedMinutes() {
        return getElapsedSeconds() / 60;
    }
}
