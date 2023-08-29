package com.example;

import net.runelite.client.ui.overlay.Overlay;
import javax.inject.Inject;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class BrewTrackerOverlay extends Overlay {

    public final BrewTrackerPlugin plugin;
    private Image restoreImage;
    //Setting the brew image position
    public static final int x_position = 30;
    public static final int y_position = 60;


    @Inject
    public BrewTrackerOverlay(BrewTrackerPlugin plugin) {
        this.plugin = plugin;
        try {
            restoreImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/restore.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Dimension render(Graphics2D graphics) {
        // Set the color for the text
        graphics.setColor(Color.WHITE);

        // todo make text flashing so its more visible
        if(plugin.brewCounter >= plugin.brewsPerRestore) {
            drawRectangle(graphics);
            drawRestoreTextAndImage(graphics);
        }
        drawBrewsText(graphics);

        return null; // Return the appropriate dimension if needed
    }

    private void drawRectangle(Graphics2D graphics) {
        int imageWidth = restoreImage.getWidth(null);
        int imageHeight = restoreImage.getHeight(null);
        graphics.drawRect(x_position, y_position, imageWidth, imageHeight);// Fifteen pixels below the first line
    }

    private void drawRestoreTextAndImage(Graphics2D graphics) {
        String restoreText = "Sip restore";
        graphics.setColor(Color.RED);
        graphics.drawImage(restoreImage, x_position, y_position, null);
        graphics.drawString(restoreText, x_position, 55);
    }

    private void drawBrewsText(Graphics2D graphics) {
        String brewText = "Brew Sips: " + plugin.brewCounter;
        graphics.drawString(brewText, x_position, 40);
    }
}
