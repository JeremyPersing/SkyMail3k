package org.csc133.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

/**
 * The AboutCommand class shows a Dialog box containing general information
 *
 * @author Jeremy Persing
 * @section 4
 * @SID 9682
 * @version 3.0
 */

public class AboutCommand extends Command {
    private int versionNumber = 3;
    AboutCommand() {
        super("About");
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        Dialog.show("About",
                "Built by: Jeremy Persing." +
                        "\nCourse Name: CSC 133" +
                        "\nVersion number: " + versionNumber + ".0",
                "", "Ok");
    }
}
