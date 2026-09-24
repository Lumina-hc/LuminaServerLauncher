package io.github.codefarmerfox.luminaserverlauncher.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.files.FileHandle;

public class GetConfig {

    private static Json json = new Json();

    public static Config get() {
        FileHandle fileConfig = Gdx.files.internal("assets/config.json");
        if (!fileConfig.exists()) {
            Config config = new Config();
            config.fps = 60;
            config.width = 800;
            config.height = 600;
            return set(config);
        }
        return json.fromJson(Config.class, fileConfig);
    }
    public static Config set(Config config) {
        json.toJson(config, Gdx.files.local("assets/config.json"));
        return get();
    }
}
