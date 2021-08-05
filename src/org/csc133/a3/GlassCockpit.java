package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;

/**
 * The GlassCockpit class shows information about the Game and Helicopter state
 *
 * @author Jeremy Persing
 * @section 4
 * @SID 9682
 * @version 3.0
 */

public class GlassCockpit extends Container {
    private GameClockComponent clock;
    private GlassContainerComponent fuel, damage, lives, last, heading;
    private GameWorld gameWorld;

    public GlassCockpit(GameWorld gw) {
        gameWorld = gw;
        this.setLayout(new GridLayout(1,6));

        Container clockContainer = new Container(new BorderLayout());
        clock = new GameClockComponent();
        clockContainer.add(BorderLayout.NORTH, "GAMETIME");
        clockContainer.add(BorderLayout.CENTER, clock);
        this.add(clockContainer);

        Container fuelContainer = new Container(new BorderLayout());
        fuel = new GlassContainerComponent(4,
                ColorUtil.GREEN);
        fuelContainer.add(BorderLayout.NORTH, "FUEL");
        fuelContainer.add(BorderLayout.CENTER, fuel);
        this.add(fuelContainer);

        Container damageContainer = new Container(new BorderLayout());
        damage = new GlassContainerComponent(2,
                ColorUtil.GREEN);
        damageContainer.add(BorderLayout.NORTH, "DAMAGE");
        damageContainer.add(BorderLayout.CENTER, damage);
        this.add(damageContainer);

        Container livesContainer = new Container(new BorderLayout());
        lives = new GlassContainerComponent(1,
                ColorUtil.CYAN);
        livesContainer.add(BorderLayout.NORTH, "LIVES");
        livesContainer.add(BorderLayout.CENTER, lives);
        this.add(livesContainer);

        Container lastContainer = new Container(new BorderLayout());
        last = new GlassContainerComponent(1,
                ColorUtil.CYAN);
        lastContainer.add(BorderLayout.NORTH, "LAST");
        lastContainer.add(BorderLayout.CENTER, last);
        this.add(lastContainer);

        Container headingContainer = new Container(new BorderLayout());
        heading = new GlassContainerComponent(3,
                ColorUtil.YELLOW);
        headingContainer.add(BorderLayout.NORTH, "HEADING");
        headingContainer.add(BorderLayout.CENTER, heading);
        this.add(headingContainer);
    }

    // Methods for timer manipulation
    public void startTimer() { clock.startElapsedTime(); }
    public void pauseTimer() {
        clock.pauseElapsedTime();
    }
    public void unPauseTimer() {
        clock.unPauseElapsedTime();
    }
    public GameClockComponent getClock() {
        return clock;
    }

    public void setFuel(int fuelAmount) {
        fuel.setValue(fuelAmount);
    }
    public void setDamage(int damageAmount) {
        damage.setValue(damageAmount);
        setDamageLedColor();
    }
    private void setDamageLedColor() {
        if (damage.getValue() >= 50 && damage.getValue() <= 85) {
            damage.setLedColor(ColorUtil.YELLOW);
        }
        else if (damage.getValue() > 85) {
            damage.setLedColor(ColorUtil.rgb(255, 0, 0));
        }
        else {
            damage.setLedColor(ColorUtil.GREEN);
        }
    }
    public void setLives(int livesAmount) {
        lives.setValue(livesAmount);
    }
    public void setLast(int lastAmount) {
        last.setValue(lastAmount);
    }
    public void setHeading(int headingAmount) {
        heading.setValue(headingAmount);
    }

    // Called whenever an update occurs in the game
    public void update() {
        setFuel(gameWorld.getHelicopterFuelLevel());
        setDamage(gameWorld.getHelicopterDamage() * 10);
        setDamageLedColor();
        setLives(gameWorld.getLives());
        setLast(gameWorld.getHelicopterLastSkyScraperReached());
        setHeading(gameWorld.getHelicopterHeading());
    }

    public String getCurrentTimeFormatted() {
        return clock.getCurrentTimeFormatted();
    }
}
