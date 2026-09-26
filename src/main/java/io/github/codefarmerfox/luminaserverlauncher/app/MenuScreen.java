package io.github.codefarmerfox.luminaserverlauncher.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx. ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.codefarmerfox.luminaserverlauncher.classes.tools.GetFont;
import io.github.codefarmerfox.luminaserverlauncher.classes.tools.UI;

public class MenuScreen extends ScreenAdapter {

    private static final float BTN_W = 300;
    private static final float BTN_H = 54;
    private static final float BTN_GAP = 16;

    private final App app;

    private SpriteBatch batch;
    private ShapeRenderer shapes;
    private Texture white;
    private BitmapFont brandFont;
    private BitmapFont buttonFont;
    private BitmapFont smallFont;
    private final boolean show;

    public MenuScreen(App app, boolean show) {
        this.app = app;
        this.show = show;
    }

    public MenuScreen(App app) {
        this.app = app;
        this.show = false;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        shapes = new ShapeRenderer();
        white = UI.white();
        try {
            GetFont.readContent();
            com.badlogic.gdx.graphics.Color c = UI.TEXT_MAIN;
            brandFont = GetFont.getFont(GetFont.getParameter(46, c, true));
            buttonFont = GetFont.getFont(GetFont.getParameter(22, c));
            smallFont = GetFont.getFont(GetFont.getParameter(16, c));
        } catch (Exception e) {
            brandFont = new BitmapFont();
            buttonFont = new BitmapFont();
            smallFont = new BitmapFont();
        }
        if (show) {
            String last = app.getConfig() != null ? app.getConfig().lastServer : null;
            if (last != null && !last.isEmpty()) {
                app.setScreen(new ServerScreen(app, last));
                return;
            }
        }
    }

    @Override
    public void render(float delta) {
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();

        float centerX = w / 2f;
        float y1 = h / 2f - 30;
        float y2 = y1 - BTN_H - BTN_GAP;

        boolean hoverDownload = UI.hovered(centerX - BTN_W / 2f, y1, BTN_W, BTN_H);
        boolean hoverServers = UI.hovered(centerX - BTN_W / 2f, y2, BTN_W, BTN_H);

        if (UI.clicked(centerX - BTN_W / 2f, y1, BTN_W, BTN_H)) {
            app.setScreen(new NewNameInputScreen(app));
            return;
        }
        if (UI.clicked(centerX - BTN_W / 2f, y2, BTN_W, BTN_H)) {
            app.setScreen(new ServerScreen(app, "原版核心"));
            return;
        }

        batch.begin();
        UI.gradient(batch, white, w, h, UI.BG_TOP, UI.BG_BOTTOM, 24);
        batch.end();

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        UI.buttonShape(shapes, centerX - BTN_W / 2f, y1, BTN_W, BTN_H, hoverDownload, true);
        UI.buttonShape(shapes, centerX - BTN_W / 2f, y2, BTN_W, BTN_H, hoverServers, false);
        shapes.end();

        batch.begin();
        UI.text(brandFont, batch, "LuminaServerLauncher", centerX, h - 120, UI.TEXT_MAIN);
        UI.text(smallFont, batch, "MC Server 管理器", centerX, h - 152, UI.TEXT_DIM);
        UI.text(buttonFont, batch, "准备服务器", centerX, y1 + BTN_H / 2f);
        UI.text(buttonFont, batch, "打开服务器", centerX, y2 + BTN_H / 2f);
        UI.text(smallFont, batch, "v0.2", centerX, 16, UI.TEXT_DIM);
        batch.end();
    }

    @Override
    public void dispose() {
        if (batch != null) batch.dispose();
        if (shapes != null) shapes.dispose();
        if (white != null) white.dispose();
        if (brandFont != null) brandFont.dispose();
        if (buttonFont != null) buttonFont.dispose();
        if (smallFont != null) smallFont.dispose();
    }
}