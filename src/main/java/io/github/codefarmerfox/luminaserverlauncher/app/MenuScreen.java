package io.github.codefarmerfox.luminaserverlauncher.app;

import com.badlogic.gdx.*;

public class MenuScreen implements Screen {

    private App app;

    public MenuScreen(App app) {
        this.app = app;
    }

    @Override
    public void show() {
        if (!this.app.getConfig().lastServer.isEmpty()) this.app.setScreen(new ServerScreen(app, this.app.getConfig().lastServer));
    }

    @Override
    public void render(float v) {

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
