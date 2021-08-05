package org.csc133.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;

import java.io.IOException;

/**
 * The Helicopter class inherits from Movable and implements the ISteerable
 * interface. This is the main GameObject for the game.
 *
 * @author Jeremy Persing
 * @section 4
 * @SID 9682
 * @version 3.0
 */

public class Helicopter extends Movable implements ISteerable{
    private int maximumSpeed;
    private int stickAngle;
    private int fuelLevel;
    private int fuelConsumptionRate;
    private int damageLevel;
    private int maxDamageLevel; // Helicopter reaches this, player loses a life
    private int lastSkyScraperReached;
    private int helicopterAccelerationSpeed;
    static  Helicopter mainHelicopter = new Helicopter();
    private int minRotorSpeed = 70; // Heli must have this rotor speed to move
    private int rotorSpeed;
    private GameWorld gw;

    private Image [] heliBodyImages = new Image[72];
    private Image [] heliBlades = new Image[6];
    private Image [] heliBladeMasks = new Image[6];
    private int heliBladeCount = 0;

    // Only allowing for one instance of Helicopter so constructor is private
    private Helicopter() {
        super(50, (float) 0, 0, 255, 0, 0);
        fuelConsumptionRate = 1;
        maxDamageLevel = 10;
        stickAngle = 0;
        // We can't reference the ImageCollection with first having an instance
        // of a heli so we need to generate the images here
        initHeliBodyImages();
        initHeliBladesAndMasks();
    }

    // Only NPHs are allowed to utilize this constructor
    protected Helicopter(int size, float x, float y, int r, int g, int b,
                         GameWorld gw) {
        super(size, x, y, r, g, b);
        heliBodyImages = gw.getGameImages().getNphImages();
        for (int i = 0; i < heliBodyImages.length; i++) {
            heliBodyImages[i] = gw.getGameImages().getNphImages()[i];
        }
        for (int i = 0; i < heliBlades.length; i++) {
            heliBlades[i] = gw.getGameImages().getHeliBlades()[i];
            heliBladeMasks[i] = gw.getGameImages().getHeliBladeMasks()[i];
        }
        this.gw = gw;
    }
    protected void setMaxDamageLevel(int damage) {maxDamageLevel = damage;}
    public static Helicopter getInstance() {
        return mainHelicopter;
    }

    public void increaseFuelLevel(int fuelAmount) {
        fuelLevel += fuelAmount;
    }
    public void setFuelLevel(int amount) {fuelLevel = amount;}
    public void setHelicopterAccelerationSpeed(int speed) {
        helicopterAccelerationSpeed = speed;
    }
    public void decreaseFuelLevel() {
        fuelLevel -= fuelConsumptionRate;
    }
    public int getFuelLevel() {
        return fuelLevel;
    }
    public void setMaximumSpeed(int speed) { maximumSpeed = speed; }
    // Helicopter accelerationSpeed is dependent upon fuel level
    private double getFuelLevelRatio() {
        if (fuelLevel < 1000) {
            return 0.5;
        }
        return 1;
    }
    public void setRotorSpeed(int rotorSpeed) {
        this.rotorSpeed = rotorSpeed;
    }
    public void setGameWorld(GameWorld gw) { this.gw = gw; }

    public int getStickAngle() { return stickAngle; }
    public void resetStickAngle() {stickAngle = 0;}
    public void updateHeading() {
        int rate;
        // The more that the stickAngle is turned, the more the heading updates
        if (Math.abs(getStickAngle() / 5) > 3) {
            rate = 5;
        }
        else {
            rate = 2;
        }
        if (getStickAngle() < 0) {
            setHeading(getHeading() - rate);
        }
        else if (getStickAngle() > 0) {
            setHeading(getHeading() + rate);
        }

    }

    public void increaseDamageLevel(int amount) {
        damageLevel += amount;
    }
    public void setDamageLevel(int amount) {damageLevel = amount;}
    public int getDamageLevel() {
        return damageLevel;
    }
    public int getMaxDamageLevel() {return maxDamageLevel;}

    public void setLastSkyScraperReached(int skyScraperNum) {
        lastSkyScraperReached = skyScraperNum;
    }
    public int getLastSkyScraperReached() {
        return lastSkyScraperReached;
    }

    public void increaseHelicopterSpeed() {
        int speed = getSpeed();
        // Make sure that the rotor is spinning fast enough to move
        if (rotorSpeed > minRotorSpeed) {
            // Checks if the current speed is less than the maximum speed
            if ((speed + helicopterAccelerationSpeed) < maximumSpeed) {
                speed += helicopterAccelerationSpeed;
                setSpeed(speed);
            }
            else {
                setSpeed(maximumSpeed);
            }
        }
    }
    public void decreaseHelicopterSpeed() {
        int speed = getSpeed();
        if ((speed - helicopterAccelerationSpeed) > 0) {
            speed -= helicopterAccelerationSpeed;
            setSpeed(speed);
        }
        else {
            setSpeed(0);
        }
    }

    // Ratio of current damage to maximum damage allowed
    private double getDamageLevelRatio() {
        return (1 - ((double) damageLevel / (double) maxDamageLevel));
    }
    public void decreaseMaximumSpeed() {
        double newMaxSpeed =
                getDamageLevelRatio() * maximumSpeed * getFuelLevelRatio() + 10;
        setMaximumSpeed((int) Math.ceil(newMaxSpeed));
    }
    public int getMaximumSpeed() { return maximumSpeed;}

    // Sets the helicopter's acceleration speed after a collision
    public void decreaseHelicopterAccelerationSpeed() {
        double newAccelerationSpeed = getDamageLevelRatio() *
                (double) helicopterAccelerationSpeed;
        // Change the acceleration speed to 1 if it comes out to less than 5
        if (helicopterAccelerationSpeed < 5) {
            setHelicopterAccelerationSpeed(3);
        }
        else {
            setHelicopterAccelerationSpeed(
                    (int) Math.round(newAccelerationSpeed));
        }
    }
    public void changeHelicopterBladeColor() {
        // When the damage is at max, only the mask will show
        double alphaValue = 1000 - (1000 * (damageLevel / maxDamageLevel));
        for (int i = 0; i < heliBlades.length; i++) {
            heliBlades[i] = heliBlades[i]
                    .modifyAlphaWithTranslucency((byte) alphaValue);
        }
    }
    public void initHeliBladesAndMasks() {
        try {
            Image heliBlade = Image.createImage("/HelicopterBlade.png");
            Image heliBladeMask = Image.createImage("/HelicopterBladeMask.png");
            int rotationAmt = 360 / heliBlades.length;
            for (int i = 0; i < heliBlades.length; i++) {
                heliBlades[i] = heliBlade.rotate(i * rotationAmt);
                heliBladeMasks[i] = heliBladeMask.rotate(i * rotationAmt);
            }
        } catch (IOException e) {e.printStackTrace();}
    }
    public void initHeliBodyImages() {
        try {
            Image heliBody = Image.createImage("/Helicopter.png");
            int rotationAmt = 360 / heliBodyImages.length;
            for (int i = 0; i < heliBodyImages.length; i++) {
                heliBodyImages[i] = heliBody.rotate(i * -rotationAmt);
            }
        } catch (IOException e) { e.printStackTrace(); }
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
        if (otherObject instanceof SkyScraper) {
            int seqNum = ((SkyScraper) otherObject).getSequenceNumber();
            gw.helicopterAndSkyScraperCollision(this, seqNum);
        }
        else if (otherObject instanceof RefuelingBlimp) {
            gw.helicopterAndBlimpCollision(this,
                    (RefuelingBlimp) otherObject);
        }
        else if (otherObject instanceof Helicopter) {
            gw.helicopterAndHelicopterCollision(this, (Helicopter) otherObject);
        }
        else if (otherObject instanceof Bird) {
            gw.helicopterAndBirdCollision(this);
        }
    }

    @Override
    // Turn the stickAngle of the Helicopter by 5 degrees to the left
    public void steerLeft() {
        // Heli can only rotate once blade has reached min rotational speed
        if (stickAngle > -40 && rotorSpeed > minRotorSpeed) {
            stickAngle -= 5;
        }
    };

    @Override
    // Turn the stickAngle of the by Helicopter 5 degrees to the right
    public void steerRight() {
        // Heli can only rotate once blade has reached min rotational speed
        if (stickAngle < 40 && rotorSpeed > minRotorSpeed) {
            stickAngle += 5;
        }
    }

    @Override
    public String toString() {
        int color = getColor();
        String helicopterDescription = "Helicopter:"
                + " loc=" + getXLocation() + ", " + getYLocation()
                + " color=[" + ColorUtil.red(color) + ","
                + ColorUtil.green(color) + ","
                + ColorUtil.blue(color) + "]"
                + " heading=" + getHeading()
                + " speed=" + getSpeed()
                + " maxSpeed=" + maximumSpeed
                + " stickAngle=" + stickAngle
                + " fuelLevel=" + fuelLevel
                + " damageLevel=" + damageLevel;
        return helicopterDescription;
    }

    @Override
    public void draw(Graphics g, Point containerOrigin) {
        int headingAngleIndex = getHeading() / (360 / heliBodyImages.length);

        g.drawImage(heliBodyImages[headingAngleIndex], findCorrectXLocation(),
                findCorrectYLocation(), getSize(), getSize());
        // The Heli is stationary on the Home pad
        if (getSpeed() == 0) {
            if (rotorSpeed < 40) {
                drawRotor(g, 0);
            }
            else if (rotorSpeed >= 40 && rotorSpeed < minRotorSpeed) {
                drawRotor(g, heliBladeCount);
                heliBladeCount = (heliBladeCount + 1) % heliBlades.length;
            }
            // Heli can now move
            else {
                drawRotor(g, heliBladeCount);
                heliBladeCount =
                        (heliBladeCount + 1) % (heliBlades.length - 2);
            }
            rotorSpeed++;
        }
        // The Heli is moving and the blade speed depends on current speed
        else {
            if (getSpeed() < (getSpeed() / 4)) {
                drawRotor(g, heliBladeCount);
                heliBladeCount = (heliBladeCount + 1) % (heliBlades.length - 2);
            }
            else if (getSpeed() < (getSpeed() / 2)
                    && getSpeed() > (getSpeed() / 4)) {
                drawRotor(g, heliBladeCount);
                heliBladeCount = (heliBladeCount + 1) % (heliBlades.length - 3);
            }
            else {
                drawRotor(g, heliBladeCount);
                heliBladeCount = (heliBladeCount + 1) % (heliBlades.length - 4);
            }
        }

    }

    private void drawRotor(Graphics g, int heliBladeCount) {
        g.drawImage(heliBladeMasks[heliBladeCount], findCorrectXLocation(),
                findCorrectYLocation(), getSize(), getSize());
        g.drawImage(heliBlades[heliBladeCount], findCorrectXLocation(),
                findCorrectYLocation(), getSize(), getSize());
    }
}
