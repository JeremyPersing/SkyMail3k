package org.csc133.a3;

/**
 * The Fixed class inherits from GameObject and is used by objects that
 * cannot be moved
 *
 * @author Jeremy Persing
 * @section 4
 * @SID 9682
 * @version 3.0
 */

public abstract class Fixed extends GameObject{
    Fixed(int size,float x, float y, int r, int g, int b) {
        super(size, x, y, r, g, b);
    }


    @Override
    public void setLocation(float x, float y) {
        System.out.println("Fixed objects can't move");
    }
}
