package org.csc133.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

import java.util.ArrayList;

/**
 * The ChangeStrategiesCommand class is used for changing a
 * NonPlayerHelicopter's strategy
 *
 * @author Jeremy Persing
 * @section 4
 * @SID 9682
 * @version 3.0
 */

public class ChangeStrategiesCommand extends Command {
    private GameWorld gameWorld;
    ChangeStrategiesCommand(GameWorld gameWorld) {
        super("Change Strategies");
        this.gameWorld = gameWorld;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        ArrayList<NonPlayerHelicopter> nPHs =
                gameWorld.getGameObjectCollection().getAllNonPlayerHelicopters();
        for (int i = 0; i < nPHs.size(); i++) {
            nPHs.get(i).assignRandomStrategy();
        }
    }
}
