package io.github.codefarmerfox.luminaserverlauncher.classes.tools;

import com.badlogic.gdx.files.*;
import com.badlogic.gdx.*;

public class EULA {
    public static void agree(String serverName) {
        FileHandle eulaFile = Gdx.files.local(String.format("servers/%s/eula.txt", serverName));
        eulaFile.writeString("eula=true", false);
    }
}
