package io.github.codefarmerfox.luminaserverlauncher.app.serverscreen;

import io.github.codefarmerfox.luminaserverlauncher.app.ServerScreen;
import io.github.codefarmerfox.luminaserverlauncher.classes.tools.UI;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PropertiesScreen {

    private final ServerScreen host;
    private final Map<String, String> props = new LinkedHashMap<>();

    public PropertiesScreen(ServerScreen host) {
        this.host = host;
        load();
    }

    public void load() {
        props.clear();
        Path p = host.serverDir().resolve("server.properties");
        if (!Files.exists(p)) return;
        try {
            for (String line : Files.readAllLines(p, StandardCharsets.UTF_8)) {
                if (line.startsWith("#") || line.trim().isEmpty()) continue;
                int eq = line.indexOf('=');
                if (eq <= 0) continue;
                props.put(line.substring(0, eq).trim(), line.substring(eq + 1));
            }
        } catch (IOException ignored) {
        }
    }

    public void drawText() {
        float lx = host.lx();
        float y = host.cardY() + host.cardH() - 60;
        UI.textLeft(host.headingFont(), host.batch(), "server.properties", lx, y, UI.TEXT_DIM);
        y -= 36;
        if (props.isEmpty()) {
            UI.textLeft(host.bodyFont(), host.batch(), "尚未生成 server.properties（先启动一次服务器）", lx, y, UI.TEXT_DIM);
            return;
        }
        for (Map.Entry<String, String> e : props.entrySet()) {
            if (y < host.cardY() + 30) break;
            UI.textLeft(host.smallFont(), host.batch(), e.getKey(), lx, y, UI.TEXT_DIM);
            UI.textLeft(host.bodyFont(), host.batch(), truncate(e.getValue(), 50), lx + 280, y, UI.TEXT_MAIN);
            y -= 30;
        }
    }

    private static String truncate(String s, int max) {
        if (s == null) return "";
        return s.length() <= max ? s : s.substring(0, max) + "...";
    }
}