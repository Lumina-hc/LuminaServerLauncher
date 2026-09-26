package io.github.codefarmerfox.luminaserverlauncher.classes.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Input {

    private BitmapFont font;
    private float x, y, w, h;

    private final StringBuilder text = new StringBuilder();
    private String placeholder = "";
    private int maxLength = 64;
    private boolean focused = true;

    // 字符过滤：默认允许字母、数字、下划线、连字符、中文
    private boolean filterEnabled = true;

    private float cursorBlink = 0f;
    private float glyphW = 12f;   // 简易宽度估算
    private float padding = 20f;

    public Input(BitmapFont font) {
        this.font = font;
    }

    // ---------- 配置 ----------
    public Input setBounds(float x, float y, float w, float h) {
        this.x = x; this.y = y; this.w = w; this.h = h;
        return this;
    }

    public Input setPlaceholder(String placeholder) {
        this.placeholder = placeholder == null ? "" : placeholder;
        return this;
    }

    public Input setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    public Input setFilterEnabled(boolean enabled) {
        this.filterEnabled = enabled;
        return this;
    }

    public Input setFocused(boolean focused) {
        this.focused = focused;
        return this;
    }

    public Input setFont(BitmapFont font) {
        this.font = font;
        return this;
    }

    // ---------- 取值 ----------
    public String getText() {
        return text.toString();
    }

    /** 返回过滤后的文本（去掉非法字符），可在提交时使用。 */
    public String getSanitized() {
        return sanitize(text.toString());
    }

    public Input setText(String value) {
        text.setLength(0);
        if (value != null) text.append(value);
        return this;
    }

    public Input clear() {
        text.setLength(0);
        return this;
    }

    public boolean isEmpty() {
        return text.length() == 0;
    }

    public int length() {
        return text.length();
    }

    // ---------- 更新 ----------
    public void update(float delta) {
        cursorBlink += delta;
    }

    // ---------- 绘制（图形） ----------
    /** 只绘制背景与光标，需在 ShapeRenderer 的 begin/end 之间调用。 */
    public void drawShapes(ShapeRenderer shapes) {
        shapes.setColor(UI.LIST_BG);
        UI.roundedFilled(shapes, x, y, w, h, 10);

        boolean showCaret = focused && cursorBlink % 1f < 0.5f;
        if (showCaret) {
            float caretX = x + padding + (text.length() == 0 ? 0 : glyphWidth(text.toString()));
            shapes.setColor(UI.ACCENT);
            shapes.rect(caretX + 2, y + 14, 2, h - 28);
        }
    }

    // ---------- 绘制（文字） ----------
    /** 只绘制文本，需在 SpriteBatch 的 begin/end 之间调用。 */
    public void drawText(SpriteBatch batch) {
        String shown = text.toString();
        Color color;
        if (shown.isEmpty()) {
            shown = placeholder;
            color = UI.TEXT_DIM;
        } else {
            color = UI.TEXT_MAIN;
        }
        UI.textLeft(font, batch, shown, x + padding, y + h / 2f, color);
    }

    // ---------- 输入事件 ----------
    /** 返回 true 表示已消费该按键。 */
    public boolean keyDown(int keycode) {
        if (!focused) return false;
        if (keycode == com.badlogic.gdx.Input.Keys.BACKSPACE) {
            if (text.length() > 0) text.deleteCharAt(text.length() - 1);
            return true;
        }
        return false;
    }

    /** 返回 true 表示已消费该字符输入。 */
    public boolean keyTyped(char character) {
        if (!focused) return false;
        if (character == '\b' || character == '\r' || character == '\n') return false;
        if (text.length() >= maxLength) return false;
        if (!filterEnabled || isAllowed(character)) {
            text.append(character);
            return true;
        }
        return false;
    }

    private boolean isAllowed(char c) {
        return Character.isLetterOrDigit(c)
                || c == '_' || c == '-'
                || c > 0x2E80;   // 允许中文等
    }

    private float glyphWidth(String s) {
        float w = 0;
        for (int i = 0; i < s.length(); i++) {
            w += (s.charAt(i) > 0x2E80) ? glyphW * 2 : glyphW;
        }
        return w;
    }

    private static String sanitize(String s) {
        return s.replaceAll("[^\\p{L}\\p{N}_\\-]", "").trim();
    }
}