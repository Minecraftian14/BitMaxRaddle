package org.that;

import main.generalLogger.LOGGER;
import org.that.gui.Body;

import java.util.ArrayList;

public class Launcher {

    public static void main(String[] args) {

        LOGGER.isAudioAllowed = true;
        new Body();

    }

}
