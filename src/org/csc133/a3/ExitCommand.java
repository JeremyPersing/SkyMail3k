package org.csc133.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

/**
 * The ExitCommand class displays a Dialog box used for exiting the game
 *
 * @author Jeremy Persing
 * @section 4
 * @SID 9682
 * @version 3.0
 */

public class ExitCommand extends Command {
    private GameWorld gameWorld;
    private boolean gameWorldPausedBefore;

    ExitCommand(GameWorld gameWorld) {
        super("Exit");
        this.gameWorld = gameWorld;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        gameWorldPausedBefore = gameWorld.isGamePaused();
        if (!gameWorldPausedBefore) {
            gameWorld.pauseGame();
        }
        YesCommand yes = new YesCommand();
        NoCommand no = new NoCommand();
        Dialog.show("Are you sure you want to exit?",
                "" , yes, no);
    }

    // YesCommand is only needed when ExitCommand is used
    private class YesCommand extends Command {
        YesCommand() {
            super("Yes");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            System.out.println("Goodbye");
            gameWorld.exit();
        }
    }

    // NoCommand is only needed when ExitCommand is used
    private class NoCommand extends Command {
        NoCommand() {
            super("No");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            if (!gameWorldPausedBefore) {
                gameWorld.unPauseGame();
            }
        }
    }
}
