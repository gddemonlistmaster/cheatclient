package com.example.mojmod;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class InfoScreen extends Screen {

    // Toggle states, kept static so they persist while the game is running
    public static boolean showCoords = true;
    public static boolean showFps = true;
    public static boolean showTime = true;

    public InfoScreen() {
        super(Text.literal("Info Panel"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int top = this.height / 2 - 60;

        this.addDrawableChild(ButtonWidget.builder(toggleLabel("Coordinates", showCoords), button -> {
            showCoords = !showCoords;
            button.setMessage(toggleLabel("Coordinates", showCoords));
        }).dimensions(centerX - 100, top, 200, 20).build());

        this.addDrawableChild(ButtonWidget.builder(toggleLabel("FPS", showFps), button -> {
            showFps = !showFps;
            button.setMessage(toggleLabel("FPS", showFps));
        }).dimensions(centerX - 100, top + 24, 200, 20).build());

        this.addDrawableChild(ButtonWidget.builder(toggleLabel("In-game time", showTime), button -> {
            showTime = !showTime;
            button.setMessage(toggleLabel("In-game time", showTime));
        }).dimensions(centerX - 100, top + 48, 200, 20).build());
    }

    private Text toggleLabel(String name, boolean on) {
        return Text.literal(name + ": " + (on ? "ON" : "OFF"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);

        // Title
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);

        MinecraftClient client = MinecraftClient.getInstance();
        int x = this.width / 2 - 100;
        int y = this.height / 2 + 10;

        if (client.player == null || client.world == null) {
            context.drawTextWithShadow(this.textRenderer, "Not in a world", x, y, 0xAAAAAA);
        } else {
            if (showCoords) {
                String coords = String.format("X: %.1f  Y: %.1f  Z: %.1f",
                        client.player.getX(), client.player.getY(), client.player.getZ());
                context.drawTextWithShadow(this.textRenderer, coords, x, y, 0xFFFFFF);
                y += 14;
            }

            if (showFps) {
                String fps = "FPS: " + client.getCurrentFps();
                context.drawTextWithShadow(this.textRenderer, fps, x, y, 0xFFFFFF);
                y += 14;
            }

            if (showTime) {
                // Minecraft day time: 0 = 06:00, one day = 24000 ticks
                long ticks = (client.world.getTimeOfDay() + 6000) % 24000;
                int hours = (int) (ticks / 1000);
                int minutes = (int) ((ticks % 1000) * 60 / 1000);
                String time = String.format("Time: %02d:%02d", hours, minutes);
                context.drawTextWithShadow(this.textRenderer, time, x, y, 0xFFFFFF);
            }
        }

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
