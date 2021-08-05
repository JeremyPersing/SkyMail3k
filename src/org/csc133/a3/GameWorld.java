package org.csc133.a3;

import com.codename1.ui.Container;

import java.util.ArrayList;
import java.util.Random;

/**
 * The GameWorld class holds GameObjects, various states, and holds the
 * methods that can change GameObjects based off of user input
 *
 * @author Jeremy Persing
 * @section 4
 * @SID 9682
 * @version 3.0
 */

public class GameWorld {
    private int lives = 3;
    private int numberOfSkyScrapers;
    private GameObjectCollection gameObjectCollection =
            new GameObjectCollection();
    private ArrayList<GameObject> addedGameObjects = new ArrayList<>();
    // Represents if the stickAngle has been turned for the current clock tick
    private boolean stickAngleTurned = false;
    // Holds the actions that need to be performed when tick() occurs
    private char tickAction;
    private Helicopter mainHelicopter;
    private GlassCockpit cockPit;
    private MapView mapView;
    private Container buttonContainer;
    private int mapHeightStart, mapWidth, mapHeightEnd;
    private BackgroundSound bgSound, initialBgSound, middleScraperBgSound;
    private Sound heliCollisionSound, birdCollisionSound, blimpCollisionSound,
            deathSound, scraperCollisionSound, nphCollisionSound;
    private boolean soundOff = false;
    private boolean gamePaused = false;
    private boolean soundsInitialized = false;
    private GameObjectImageCollection gameImages =
            new GameObjectImageCollection();

    public void init() {
        mapWidth = mapView.getWidth();
        mapHeightStart = cockPit.getHeight();
        mapHeightEnd = mapView.getHeight() + buttonContainer.getHeight();

        SkyScraper homeSkyScraper = createSkyScraper(0, 0, 0);
        homeSkyScraper.drainScraperColor();
        gameObjectCollection.add(homeSkyScraper);

        gameObjectCollection.add(createSkyScraper(1000, 0, 1));
        gameObjectCollection.add(createSkyScraper(500, 500, 2));
        gameObjectCollection.add(createSkyScraper(0, 1000, 3));
        gameObjectCollection.add(createSkyScraper(1000, 1000, 4));
        gameObjectCollection.add(createSkyScraper(1000, 500, 5));


        gameObjectCollection.add(createBlimp());
        gameObjectCollection.add(createBlimp());

        Helicopter helicopter = Helicopter.getInstance();
        helicopter.setGameWorld(this);
        helicopter.setSize(homeSkyScraper.getSize());
        helicopter.setLocation(homeSkyScraper.getXLocation(),
                homeSkyScraper.getYLocation());
        helicopter.setFuelLevel(2500);
        helicopter.setSpeed(0);
        helicopter.setRotorSpeed(0);
        helicopter.setMaximumSpeed(100);
        helicopter.setHeading(0);
        helicopter.resetStickAngle();
        helicopter.setDamageLevel(0);
        helicopter.setLastSkyScraperReached(0);
        helicopter.setHelicopterAccelerationSpeed(10);
        helicopter.setColor(255, 0, 0);
        helicopter.initHeliBladesAndMasks();
        gameObjectCollection.add(helicopter);

        // Must access objects through collection
        mainHelicopter = gameObjectCollection.getMainHelicopter();

        // NPHs can't all start with the same strategy
        NonPlayerHelicopter nph1 = createNPH(10, 1.5);
        nph1.setStrategy(new AttackStrategy(this));
        gameObjectCollection.add(nph1);
        NonPlayerHelicopter nph2 = createNPH(10, 2);
        nph2.setStrategy(new GateKeeperStrategy(this));
        gameObjectCollection.add(nph2);
        gameObjectCollection.add(createNPH(7, 3));

        gameObjectCollection.add(createBird());
        gameObjectCollection.add(createBird());

        numberOfSkyScrapers = gameObjectCollection.
                getAllSkyScrapers().toArray().length;

        initializeSounds();
    }
    private void initializeSounds() {
        // Initializing the sounds is slow so we only want to do it once
        if (!soundsInitialized) {
            try {
                initialBgSound =
                        new BackgroundSound("PimPoyPocket.wav");
                bgSound = initialBgSound;
                middleScraperBgSound =
                        new BackgroundSound("PimPoy.wav");

                if (!soundOff) {
                    bgSound.play();
                }
                heliCollisionSound = new Sound("heliAndHeli.wav");
                birdCollisionSound = new Sound("heliAndBird.wav");
                blimpCollisionSound = new Sound("heliAndBlimp.wav");
                scraperCollisionSound = new Sound("heliAndScraper.wav");
                deathSound = new Sound("death.wav");
                nphCollisionSound = new Sound("nphCollision.wav");
                soundsInitialized = true;
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        // Player could've reached half of scrapers so set bgSound back
        else {
            bgSound = initialBgSound;
            bgSound.play();
        }
    }
    // Only plays sound if the music is on
    private void playSound(Sound sound) {
        if (!soundOff && soundsInitialized) {
            sound.play();
        }
    }
    // User can only turn the sound off and on when the game is not paused
    public void turnSoundOff() {
        bgSound.pause();
        soundOff = true;
    }
    public void turnSoundOn() {
        bgSound.play();
        soundOff = false;
    }
    public boolean isSoundOff() { return soundOff; }
    public boolean isGamePaused() { return gamePaused; }
    public GameObjectImageCollection getGameImages() { return gameImages; }

    private Bird createBird() {
        int birdSize = (int) (mapWidth * .05);
        return new Bird(birdSize, generateRandomX(), generateRandomY(),
                98,84,63, this);
    }
    private RefuelingBlimp createBlimp() {
        int blimpSize = generateRandomSize();
        return new RefuelingBlimp(blimpSize, generateRandomX(),
                generateRandomY(), 68,115,192, this);
    }
    // The x and yMultiplier scale the x and y location of the heli
    private NonPlayerHelicopter createNPH
            (double xMultiplier, double yMultiplier) {
        int npHelicopterSize = (int) (mapWidth * .07);
        return new NonPlayerHelicopter(npHelicopterSize,
                (float) (mainHelicopter.getXLocation() * xMultiplier),
                (float) (mainHelicopter.getYLocation() * yMultiplier),
                156,196,227, this);
    }
    private SkyScraper createSkyScraper(int xLoc, int yLoc, int seqNum) {
        int skyScraperSize = (int) (mapWidth * .1);
        return new SkyScraper(skyScraperSize,
                setScalableXLocation(xLoc, skyScraperSize),
                setScalableYLocation(yLoc, skyScraperSize),
                115,115,115, seqNum,this);
    }

    // Set Views
    public void setGlassCockPit(GlassCockpit cockPit) {this.cockPit = cockPit;}
    public void setMapView(MapView mapView) { this.mapView = mapView; }
    public void setButtonContainer(Container c) { buttonContainer = c;}

    // Scales the location as if the object was on a 1000 X 1000 map
    private float setScalableXLocation(float xValue, int objSize) {
        double quotient = (double) xValue / 1000;
        double location = quotient * mapWidth;
        double halfWidthOfObj = objSize / 2;
        // Make sure the whole object will display on the map
        if ((location - halfWidthOfObj) < 0) {
            location += halfWidthOfObj;
        }
        if ((location + halfWidthOfObj) > mapWidth) {
            location -= halfWidthOfObj;
        }

        return (float) location;
    }
    // Scales the location as if the object was on a 1000 X 1000 map
    private float setScalableYLocation(float yValue, int objSize) {
        double quotient = (double) yValue / 1000;
        double location = quotient * mapHeightEnd;

        if (location < mapHeightStart) {
            location += mapHeightStart;
        }
        double halfHeightOfObj = objSize / 2;
        // Make sure the whole object will display on the map
        if ((location - halfHeightOfObj) < mapHeightStart) {
            location += halfHeightOfObj;
        }
        if ((location + halfHeightOfObj) > mapHeightEnd) {
            location -= halfHeightOfObj;
        }
        return (float) location;
    }

    public int generateRandomSize() {
        int scale = (int) (mapWidth * .1);
        Random randomNum = new Random();
        int randomSize = randomNum.nextInt(scale) + 50;
        return randomSize;
    }
    public float generateRandomX() {
        Random randomNum = new Random();
        float xLocation = randomNum.nextFloat() * mapWidth;
        return xLocation;
    }
    public float generateRandomY() {
        Random randomNum = new Random();
        float yLocation = randomNum.nextFloat() * mapHeightEnd;
        //
        if (yLocation < mapHeightStart) {
            yLocation += mapHeightStart;
        }
        return yLocation;
    }

    // Needed for MapView to iterate and paint all GameObjects
    public GameObjectCollection getGameObjectCollection() {
        return gameObjectCollection;
    }

    public void pauseGame() {
        cockPit.pauseTimer();
        gamePaused = true;
    }
    public void unPauseGame() {
        cockPit.unPauseTimer();
        gamePaused = false;
    }
    public void reInit() {
        gameObjectCollection.clear();
        bgSound.pause();
        init();
    }
    // Used when the helicopter can't move
    public void loseLife() {
        lives--;
        playSound(deathSound);
        if (lives > 0) {
            reInit();
        }
        else {
            gameOver();
        }
    }

    // Used to set the direction to turn the stickAngle for the next tick
    public void assignTickAction(char direction) {
        if (!stickAngleTurned) {
            tickAction = direction;
            stickAngleTurned = true;
        }
    }

    public void tick(int timerRate) {
        turnStickAngle();
        setBoundedHelicopterHeading(mainHelicopter);

        // Update bird headings
        for (GameObject gameObject : gameObjectCollection.getGameObjects()) {
            if (gameObject instanceof Bird) {
                setBoundedBirdHeading((Bird) gameObject);
            }
        }

        // Move all movable items
        for (GameObject gameObject : gameObjectCollection.getGameObjects()) {
            if (gameObject instanceof Movable) {
                ((Movable) gameObject).move(timerRate);
            }
        }

        // Invoke the strategies for the NonPlayerHelicopters
        for (GameObject gameObject : gameObjectCollection.getGameObjects()) {
            if (gameObject instanceof NonPlayerHelicopter) {
                ((NonPlayerHelicopter) gameObject).invokeStrategy();
            }
        }

        // Check for collisions
        checkCollisions();
        // Place any added objects into the GameWorld
        if (addedGameObjects.size() > 0) {
            for (int i = 0; i < addedGameObjects.size(); i++) {
                gameObjectCollection.add(addedGameObjects.get(i));
            }
            addedGameObjects.clear();
        }

        // Fuel decreases by 1 every time the clock ticks
        mainHelicopter.decreaseFuelLevel();
        notifyObservers();

        // Helicopter can't move
        if (mainHelicopter.getFuelLevel() == 0 ||
                mainHelicopter.getMaximumSpeed() <= 0 ||
                mainHelicopter.getDamageLevel() >=
                mainHelicopter.getMaxDamageLevel()) {
            loseLife();
        }
    }

    private void checkCollisions() {
        for (GameObject obj1 : gameObjectCollection.getGameObjects()) {
            for (GameObject obj2 : gameObjectCollection.getGameObjects()) {
                // Make sure the colliding objects are 2 different objects
                if (obj1 != obj2) {
                    // Helicopters must both check if they collide w/ each other
                    checkObjectCollision(obj1, obj2);
                }
            }
        }
    }
    private void checkObjectCollision(GameObject obj1, GameObject obj2) {
        // Objects are colliding
        if (obj1.collidesWith(obj2) && obj2.collidesWith(obj1)
                && !obj1.getCollisionList().contains(obj2)
                && !obj2.getCollisionList().contains(obj1)) {
            // Only helicopters can (& need to) handle collisions
            if (obj1 instanceof Helicopter) {
                obj1.handleCollision(obj2);
            }
            else {
                obj2.handleCollision(obj1);
            }
            obj1.addToCollisionList(obj2);
            obj2.addToCollisionList(obj1);
        }
        // Objects are no longer colliding
        else if (!(obj1.collidesWith(obj2)
                && obj2.collidesWith(obj1)) &&
                obj1.getCollisionList().contains(obj2)
                && obj2.getCollisionList().contains(obj1)) {
            obj1.getCollisionList().remove(obj2);
            obj2.getCollisionList().remove(obj1);
        }
    }

    // Makes sure that the helicopter stays in bounds
    public void setBoundedHelicopterHeading(Helicopter heli) {
        // Helicopter is within playing bounds
        if (!movableObjectOutOfBounds(heli)){
            heli.updateHeading();
        }
        else {
            heli.resetStickAngle();
        }
    }
    // Makes sure that the bird stays in bounds
    public void setBoundedBirdHeading(Bird bird) {
        // Bird is within playing bounds
        if (!movableObjectOutOfBounds(bird)) {
            bird.updateHeading();
        }
    }

    // Checks if a movable objects is out of bounds, switches heading if it is
    private boolean movableObjectOutOfBounds(Movable gameObject) {
        if (gameObject.getXLocation() <= 0) {
            gameObject.setHeading(90);
            return true;
        }
        else if (gameObject.getXLocation() >= mapWidth) {
            gameObject.setHeading(270);
            return true;
        }
        else if (gameObject.getYLocation() <= mapHeightStart) {
            gameObject.setHeading(0);
            return true;
        }
        else if (gameObject.getYLocation() >= mapHeightEnd) {
            gameObject.setHeading(180);
            return true;
        }
        return false;
    }

    public void accelerateHelicopter() {
        mainHelicopter.increaseHelicopterSpeed();
    }
    public void brakeHelicopter() { mainHelicopter.decreaseHelicopterSpeed(); }
    public void turnStickAngle() {
        if (stickAngleTurned) {
            stickAngleTurned = false;
            if (tickAction == 'l') {
                mainHelicopter.steerLeft();
            }
            else {
                mainHelicopter.steerRight();
            }
        }
    }

    // Getting info about the Helicopter to pass to GlassCockpit
    public int getHelicopterFuelLevel() { return mainHelicopter.getFuelLevel(); }
    public int getHelicopterDamage() { return mainHelicopter.getDamageLevel(); }
    public int getHelicopterLastSkyScraperReached() {
        return mainHelicopter.getLastSkyScraperReached();
    }
    public int getHelicopterHeading() { return mainHelicopter.getHeading(); }
    public int getLives() { return lives; }
    // Needs the Heli and Scraper num to determine which heli hit which scraper
    public void helicopterAndSkyScraperCollision(Helicopter heli,
                                                 int skyScraperNumber) {
        int lastSkyScraperReached = heli.getLastSkyScraperReached();
        // The current skyscraper is 1 greater than the prev skyscraper
        if ((lastSkyScraperReached + 1) == skyScraperNumber) {
            SkyScraper scraper = gameObjectCollection
                    .getAllSkyScrapers().get(skyScraperNumber);
            if (heli == mainHelicopter) {
                playSound(scraperCollisionSound);
                scraper.drainScraperColor();
            }
            lastSkyScraperReached++;
            heli.setLastSkyScraperReached(lastSkyScraperReached);
        }
        // Change music for getting to the middle scraper
        if (heli == mainHelicopter &&
                lastSkyScraperReached == (numberOfSkyScrapers / 2)
                && bgSound != middleScraperBgSound) {
            bgSound.pause();
            bgSound = middleScraperBgSound;
            bgSound.play();
        }
        // SkyScrapers start at index 0
        if (lastSkyScraperReached == (numberOfSkyScrapers - 1)) {
            // Player wins on last scraper for single game type
            if (heli == mainHelicopter) {
                gameWon();
            }
            else {
                nphWon();
            }
        }
    }
    public void helicopterAndBlimpCollision(Helicopter heli,
                                            RefuelingBlimp blimp) {
        int blimpFuel = 0; // Will hold the blimps fuel capacity
        // Find the first blimp that has fuel
        if (blimp.getCapacity() > 0) {
            // Assign the amount of fuel to blimpFuel
            blimpFuel = blimp.getCapacity();
            blimp.drainBlimp();
            addedGameObjects.add(createBlimp());
            if (heli == mainHelicopter) {
                playSound(blimpCollisionSound);
            }
        }
        heli.increaseFuelLevel(blimpFuel);
    }
    public void helicopterAndHelicopterCollision(Helicopter heli1,
                                                 Helicopter heli2) {
        Helicopter main, nph1, nph2;

        if (heli1 == mainHelicopter) {
            main = heli1;
            nph1 = heli2;
            mainHelicopterCollision(main);
            nphCollision(nph1);
            playSound(heliCollisionSound);
        }
        else if (heli2 == mainHelicopter) {
            main = heli2;
            nph1 = heli1;
            mainHelicopterCollision(main);
            nphCollision(nph1);
            playSound(heliCollisionSound);
        }
        else {
            nph1 = heli1;
            nph2 = heli2;
            nphCollision(nph1);
            nphCollision(nph2);
            playSound(nphCollisionSound);
        }
    }
    private void nphCollision(Helicopter nph) {
        nph.increaseDamageLevel(5);
        if (nph.getDamageLevel() == 10) {
            nph.setSpeed(0);
        }
        nph.changeHelicopterBladeColor();
    }
    private void mainHelicopterCollision(Helicopter main) {
        main.increaseDamageLevel(2);
        increaseDamageLevelCollision(main);
    }
    public void helicopterAndBirdCollision(Helicopter heli) {
        heli.increaseDamageLevel(1);
        increaseDamageLevelCollision(heli);
        if (heli == mainHelicopter) {
            playSound(birdCollisionSound);
        }
    }
    // Helicopter collides with something that causes its damage level to rise
    public void increaseDamageLevelCollision(Helicopter heli) {
        heli.changeHelicopterBladeColor();
        heli.decreaseMaximumSpeed(); // More damage, lower max speed
        // Speed can't be greater than the new maximum speed
        if ( heli.getSpeed() > heli.getMaximumSpeed()
                && heli.getMaximumSpeed() > 10) {
            heli.setSpeed(10);
        }
        // More damage, less acceleration
        heli.decreaseHelicopterAccelerationSpeed();
    }

    public void notifyObservers() {
        mapView.update();
        cockPit.update();
    }

    public void gameWon() {
        System.out.println("Game over, you win! Total time: " +
                cockPit.getCurrentTimeFormatted());
        exit();
    }
    public void gameOver() {
        System.out.println("Game over, better luck next time!");
        exit();
    }
    public void nphWon() {
        System.out.println("Game over, an nph got to the last SkyScraper " +
                "before you");
        exit();
    }
    // Exit the game and close the program
    public void exit(){System.exit(0);}
}
