package org.csc133.a3;

import com.codename1.charts.models.Point;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;

import java.io.IOException;

/**
 * The MapView class is to display objects on the screen
 *
 * @author Jeremy Persing
 * @section 4
 * @SID 9682
 * @version 3.0
 */


public class MapView extends Container {
    private GameWorld gameWorld;

    MapView(GameWorld gameWorld) {
        try {
            this.gameWorld = gameWorld;
            Image bgImage = Image.createImage("/background_1.png");
            this.getAllStyles().setBgImage(bgImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printGameObjectsInformation() {
        for (int i = 0; i < gameWorld.getGameObjectCollection()
                .getGameObjects().size(); i++) {
            System.out.println(
                    gameWorld.getGameObjectCollection()
                            .getGameObjects().get(i).toString());
        }

    }

    public void update() {
        printGameObjectsInformation();
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (GameObject gameObject :
                gameWorld.getGameObjectCollection().getGameObjects()) {
            gameObject.draw(g, new Point(getX(), getY()));
        }
    }
}
