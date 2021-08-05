package org.csc133.a3;

import java.util.ArrayList;

/**
 * The GameObjectCollection class contains all of the GameObjects
 *
 * @author Jeremy Persing
 * @section 4
 * @SID 9682
 * @version 3.0
 */

public class GameObjectCollection {
    private ArrayList<GameObject> gameObjects = new ArrayList<>();


    public void add(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void clear() {
        gameObjects.clear();
    }

    public Helicopter getMainHelicopter() {
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Helicopter) {
                return (Helicopter) gameObject;
            }
        }
        return null;
    }

    public ArrayList<SkyScraper> getAllSkyScrapers() {
        ArrayList<SkyScraper> allSkyScrapers = new ArrayList<>();
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof SkyScraper) {
                allSkyScrapers.add((SkyScraper) gameObject);
            }
        }
        return allSkyScrapers;
    }

    public ArrayList<NonPlayerHelicopter> getAllNonPlayerHelicopters() {
        ArrayList<NonPlayerHelicopter> allNPHs = new ArrayList<>();
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof NonPlayerHelicopter) {
                allNPHs.add((NonPlayerHelicopter) gameObject);
            }
        }
        return allNPHs;
    }

    public ArrayList<GameObject> getGameObjects() { return gameObjects; };
}
