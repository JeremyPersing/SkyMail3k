package org.csc133.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;

import java.lang.Math;
import java.util.ArrayList;

/**
 * The GameObject class lays the framework for all game objects by setting
 * the size, location, and color of a GameObject
 *
 * @author Jeremy Persing
 * @section 4
 * @SID 9682
 * @version 3.0
 */


public abstract class GameObject implements IDrawable, ICollider{
    private int size;
    private Point location;
    private int color;
    private ArrayList<GameObject> collisionList = new ArrayList<>();

    // Constructor for when all values are specified
    GameObject(int size, float x, float y,
               int r, int g, int b) {
        this.size = size;
        location = new Point(roundFloat(x), roundFloat(y));
        color = ColorUtil.rgb(r, g, b);
    }

    public void addToCollisionList(GameObject gameObject) {
        collisionList.add(gameObject);
    }

    public ArrayList<GameObject> getCollisionList() { return collisionList; }

    public int getSize() {
        return size;
    }
    public void setSize(int size) { this.size = size; }

    public float[] getLocation() {
        float [] coordinates = new float [2];
        coordinates[0] = location.getX();
        coordinates[1] = location.getY();
        return coordinates;
    }

    public float getXLocation() {return location.getX();}
    public float getYLocation() {return location.getY();}

    public void setLocation(float x, float y) {
        location.setX(roundFloat(x));
        location.setY(roundFloat(y));
    }

    public int getColor() {
        return color;
    }

    public void setColor(int r, int g, int b) {
        this.color = ColorUtil.rgb(r, g, b);
    }

    // Round float values to one decimal place
    public float roundFloat(float value) {
        float roundedValue = Math.round(value * 10.0) / 10;
        return roundedValue;
    }

    // Finds the x location that coincides with the object's center being drawn
    // on it's location
    public int findCorrectXLocation() {
        return (int) (getXLocation() - getSize() / 2);
    }

    // Finds the y location that coincides with the object's center being drawn
    // on it's location
    public int findCorrectYLocation() {
        return (int) (getYLocation() - getSize() / 2);
    }
}
