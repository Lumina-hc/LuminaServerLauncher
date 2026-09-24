package io.github.codefarmerfox.luminaserverlauncher.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import io.github.codefarmerfox.luminaserverlauncher.app.App;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
    static void main(String[] args) {
        System.setProperty("java.library.path", "libs/natives");
        System.setProperty("org.lwjgl.librarypath", "libs/natives");
        if (io.github.codefarmerfox.lwjgl3.StartupHelper.startNewJvmIfRequired()) return;
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new App(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("LuminaServerLauncher");
        configuration.useVsync(true);
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
        configuration.setWindowedMode(640, 480);
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
        return configuration;
    }
}