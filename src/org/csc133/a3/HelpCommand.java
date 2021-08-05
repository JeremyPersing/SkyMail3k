package org.csc133.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

/**
 * The HelpCommand class shows a Dialog box displaying actions to play
 *
 * @author Jeremy Persing
 * @section 4
 * @SID 9682
 * @version 3.0
 */

public class HelpCommand extends Command {
    HelpCommand() {
        super("Help");
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        Dialog.show("Help", "Accelerate: a \nBrake: b \nLeft Turn: l" +
                "\nRight Turn: r \n Exit: x", "", "Ok");
    }
}
