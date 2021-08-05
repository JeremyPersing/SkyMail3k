package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.UITimer;

import java.io.IOException;

/**
 * Game allows users to interact with the GameWorld.
 *
 * @author Jeremy Persing
 * @section 4
 * @SID 9682
 * @version 3.0
 */

public class Game extends Form implements Runnable{
    private GameWorld gw;
    private GlassCockpit cockPit;
    private Command accelerateCommand;
    private Command brakeCommand;
    private Command turnLeft;
    private Command turnRight;
    private Command aboutCommand;
    private Command helpCommand;
    private Command changeStrategies;
    private Command exit;
    private UITimer uiTimer;
    private Command pausePlayCommand;
    private Command soundCommand;
    private final int timerEventRate = 20;
    private boolean keyBindingsExist = false;

    public Game() {
        this.setLayout(new BorderLayout());
        gw = new GameWorld();
        createHeader();
        createCommands();
        createKeyBindings();
        createSideMenuListeners();

        // Create Views and add to the form prior to initialization to set
        cockPit = new GlassCockpit(gw);
        gw.setGlassCockPit(cockPit);
        this.add(BorderLayout.NORTH, cockPit);
        cockPit.pauseTimer();

        MapView map = new MapView(gw);
        gw.setMapView(map);
        this.add(BorderLayout.CENTER, map);

        Container container = createSouthButtonContainer();
        gw.setButtonContainer(container);
        this.add(BorderLayout.SOUTH, container);

        uiTimer = new UITimer(this);
        uiTimer.schedule(timerEventRate, true, this);

        // Call show prior to init because init is dependent on getX() and
        // getY()
        this.show();
        gw.init();
        cockPit.unPauseTimer();
    }

    private void createCommands() {
        accelerateCommand = new AccelerateCommand(gw);
        brakeCommand = new BrakeCommand(gw);
        turnLeft = new TurnLeftCommand(gw);
        turnRight = new TurnRightCommand(gw);
        aboutCommand = new AboutCommand();
        helpCommand = new HelpCommand();
        changeStrategies = new ChangeStrategiesCommand(gw);
        exit = new ExitCommand(gw);
        pausePlayCommand = new PausePlayCommand(gw);
        soundCommand = new SoundOffOnCommand(gw);
    }

    private void createKeyBindings() {
        addKeyListener('a', accelerateCommand);
        addKeyListener('b', brakeCommand);
        addKeyListener('l', turnLeft);
        addKeyListener('r', turnRight);
        addKeyListener('x', exit);
        addKeyListener('1', pausePlayCommand);
        addKeyListener('5', soundCommand);
        keyBindingsExist = true;
    }

    private void createSideMenuListeners() {
        this.getToolbar().addCommandToSideMenu(aboutCommand);
        this.getToolbar().addCommandToSideMenu(changeStrategies);
        this.getToolbar().addCommandToSideMenu(helpCommand);
        this.getToolbar().addCommandToSideMenu(pausePlayCommand);
        this.getToolbar().addCommandToSideMenu(soundCommand);
        this.getToolbar().addCommandToSideMenu(exit);
    }

    private void removeKeyBindings() {
        removeKeyListener('a', accelerateCommand);
        removeKeyListener('b', brakeCommand);
        removeKeyListener('l', turnLeft);
        removeKeyListener('r', turnRight);
        keyBindingsExist = false;
    }

    private Button createNewButton(Command command, Image icon) {
        Button button = new Button(icon);
        button.setCommand(command);
        button.setText("");
        return button;
    }

    // Creates the buttons and container for helicopter movement
    private Container createSouthButtonContainer() {
        try {
            Image upArrow = Image.createImage("/up-arrow.png");
            Image downArrow = Image.createImage("/down-arrow.png");
            Image rightArrow = Image.createImage("/right-arrow.png");
            Image leftArrow = Image.createImage("/left-arrow.png");
            Button leftTurnButton = createNewButton(turnLeft, leftArrow);
            Button accelerateButton = createNewButton(accelerateCommand, upArrow);
            Button brakeButton = createNewButton(brakeCommand, downArrow);
            Button rightTurnButton = createNewButton(turnRight, rightArrow);

            Container buttonContainer =
                    FlowLayout.encloseCenter(leftTurnButton, accelerateButton,
                            brakeButton, rightTurnButton);
            // Setting the background style of button container to white
            buttonContainer.getAllStyles().setBorder(Border.createEmpty());
            buttonContainer.getAllStyles().setBackgroundType
                    (Style.BACKGROUND_NONE);
            buttonContainer.getAllStyles().setBgTransparency(255);
            buttonContainer.getAllStyles().setBgColor(ColorUtil.WHITE);

            return buttonContainer;
        } catch (IOException e) { e.printStackTrace(); }
        return null;
    }

    private void createHeader() {
        this.setTitle("SkyMail 3000");
        this.getToolbar().setTitleCentered(true);
        this.getToolbar().getTitleComponent().
                getUnselectedStyle().
                setFgColor(ColorUtil.rgb(66, 135, 245));
        this.getToolbar().getTitleComponent().getUnselectedStyle().
                setPadding(2, 2, 0,0);
    }

    @Override
    public void run() {
        // Only have the GameWorld objects move if the game is not paused
        if (!gw.isGamePaused()) {
            if (!keyBindingsExist) {
                createKeyBindings();
            }
            gw.tick(timerEventRate);
        }
        else {
            // Keep the clock stopped when clicking hamburger menu
            this.deregisterAnimated(cockPit.getClock());
            if (keyBindingsExist) {
                removeKeyBindings();
            }
        }
    }
}
