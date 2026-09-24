package io.github.codefarmerfox.luminaserverlauncher.classes.tools;

import com.badlogic.gdx.files.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.*;
import com.badlogic.gdx.*;

public class GetFont {

    private static String content;
    private static FreeTypeFontGenerator generator;

    public static void readContent() {
        FileHandle fileConfig = Gdx.files.internal("assets/fonts/content.txt");
        content = fileConfig.readString();
    }

    public static FreeTypeFontGenerator.FreeTypeFontParameter getParameter(int size, Color color) {
        generator =
                new FreeTypeFontGenerator(Gdx.files.internal("fonts/myfont.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = content;
        return parameter;
    }

    public static BitmapFont getFont(FreeTypeFontGenerator.FreeTypeFontParameter parameter) {
        BitmapFont font = generator.generateFont(parameter);
        dispose();
        return font;
    }

    public static String getContent() {
        return content;
    }

    public static void setContent(String content) {
        GetFont.content = content;
    }

    public static void dispose() {
        generator.dispose();
        generator = null;
    }

}
