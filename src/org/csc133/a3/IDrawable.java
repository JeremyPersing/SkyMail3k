package org.csc133.a3;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;

/**
 * The IDrawable interface is implemented by GameObjects to draw themselves
 *
 * @author Jeremy Persing
 * @section 4
 * @SID 9682
 * @version 3.0
 */

public interface IDrawable {
    void draw(Graphics g, Point containerOrigin);
}
