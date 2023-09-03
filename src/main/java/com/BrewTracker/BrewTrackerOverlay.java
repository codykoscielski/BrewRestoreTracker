package com.BrewTracker;

import net.runelite.client.ui.overlay.Overlay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;

public class BrewTrackerOverlay extends Overlay {

    public final BrewTrackerPlugin plugin;
    private Image restoreImage;
    //Setting the brew image position
    public static final int x_position = 30;
    public static final int y_position = 60;
    private Color currentColor = Color.WHITE;
    private Timer timer;
    private static final Logger logger = LoggerFactory.getLogger(BrewTrackerOverlay.class);

    @Inject
    public BrewTrackerOverlay(BrewTrackerPlugin plugin) {
        this.plugin = plugin;
        setColorSwapperTimer();
        try {
            restoreImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/restore.png")));
        } catch (IOException e) {
            logger.error("Failed to load image", e);
        }
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        if (plugin.brewCounter >= plugin.brewsPerRestore) {
            if (!timer.isRunning()) {
                timer.start();
            }
            graphics.setColor(currentColor);
            drawRectangleAroundRestore(graphics);
            drawRestoreTextAndImage(graphics);
        } else {
            if (timer.isRunning()) {
                timer.stop();
            }
            graphics.setColor(Color.WHITE);
        }

        drawBrewsText(graphics);
        return null;
    }

    private void drawRectangleAroundRestore(Graphics2D graphics) {
        int imageWidth = restoreImage.getWidth(null);
        int imageHeight = restoreImage.getHeight(null);
        graphics.drawRect(x_position - 3, y_position - 3, imageWidth + 6, imageHeight + 6);
    }

    private void drawRestoreTextAndImage(Graphics2D graphics) {
        String restoreText = "Sip restore";
        graphics.drawImage(restoreImage, x_position, y_position, null);
        graphics.drawString(restoreText, x_position, 55);
    }

    private void drawBrewsText(Graphics2D graphics) {
        String brewText = "Brew Sips: " + plugin.brewCounter;
        graphics.drawString(brewText, x_position, 40);
    }

    private void setColorSwapperTimer() {
        timer = new Timer(500, e -> {
            if (currentColor.equals(Color.WHITE)) {
                currentColor = Color.RED;
            } else {
                currentColor = Color.WHITE;
            }
        });
    }
}
