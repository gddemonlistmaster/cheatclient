package com.example.mojmod;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class InfoScreen extends Screen {
    private boolean showCoords = true;
    private boolean showFps = true;
    private boolean showTime = true;

    protected InfoScreen() {
        super(Text.literal("Moj Mod - Panel"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2 - 100;

        addDrawableChild(ButtonWidget.builder(
                Text.literal("Wspolrzedne: WL"), b -> {
                    showCoords = !showCoords;
                    b.setMessage(Text.literal("Wspolrzedne: " + (showCoords ? "WL" : "WYL")));
                }).dimensions(centerX, this.height / 2 - 60, 200, 20).build());

        addDrawableChild(ButtonWidget.builder(
                Text.literal("FPS: WL"), b -> {
                    showFps = !showFps;
                    b.setMessage(Text.literal("FPS: " + (showFps ? "WL" : "WYL")));
                }).dimensions(centerX, this.height / 2 - 30, 200, 20).build());

        addDrawableChild(ButtonWidget.builder(
                Text.literal("Czas gry: WL"), b -> {
                    showTime = !showTime;
                    b.setMessage(Text.literal("Czas gry: " + (showTime ? "WL" : "WYL")));
                }).dimensions(centerX, this.height / 2, 200, 20).build());

        addDrawableChild(ButtonWidget.builder(
                Text.literal("Zamknij (prawy Shift)"),
                b -> close()).dimensions(centerX, this.height / 2 + 40, 200, 20).build());
    }

    @Override
    public void render(net.minecraft.client.util.math.MatrixStack matrices,
                       int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title,
                this.width / 2, this.height / 2 - 90, 0xFFFFFF);

        MinecraftClient mc = MinecraftClient.getInstance();
        int y = this.height / 2 + 70;
        if (mc.player != null) {
            if (showCoords) {
                String c = String.format("XYZ: %.1f / %.1f / %.1f",
                        mc.player.getX(), mc.player.getY(), mc.player.getZ());
                drawCenteredText(matrices, this.textRenderer, Text.literal(c),
                        this.width / 2, y, 0xA0FFA0);
                y += 12;
            }
            if (showFps) {
                drawCenteredText(matrices, this.textRenderer,
                        Text.literal("FPS: " + mc.getCurrentFps()),
                        this.width / 2, y, 0xA0C8FF);
                y += 12;
            }
            if (showTime) {
                long ticks = mc.world.getTimeOfDay() % 24000;
                drawCenteredText(matrices, this.textRenderer,
                        Text.literal("Ticki dnia: " + ticks),
                        this.width / 2, y, 0xFFE0A0);
            }
        }
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_SHIFT) {
            close();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
