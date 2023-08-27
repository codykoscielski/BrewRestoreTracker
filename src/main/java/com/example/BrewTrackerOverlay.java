package com.example;

import net.runelite.client.ui.overlay.Overlay;
import javax.inject.Inject;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BrewTrackerOverlay extends Overlay {

    public final BrewTrackerPlugin plugin;
    private Image restoreImage;

    @Inject
    public BrewTrackerOverlay(BrewTrackerPlugin plugin) {
        this.plugin = plugin;
        try {
            restoreImage = ImageIO.read(getClass().getResourceAsStream("/restore.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Dimension render(Graphics2D graphics) {
        int brewCount = plugin.getBrewCounter();

        //Setting the brew image position
        int x_position = 30;
        int y_position = 60;

        // Draw the text on the overlay
        Point startPoint = new Point(30, 40);

        // Display the brew and restore sip count

        String brewText = "Brew Sips: " + brewCount;
        String restoreText = "Sip restore";

        // Set the color for the text
        graphics.setColor(Color.WHITE);

        if(brewCount >= 3) {
            graphics.setColor(Color.RED);
            int imageWidth = restoreImage.getWidth(null);
            int imageHeight = restoreImage.getHeight(null);
            graphics.drawImage(restoreImage, x_position, y_position, null);
            graphics.drawString(restoreText, startPoint.x, startPoint.y + 15);
            graphics.drawRect(x_position, y_position, imageWidth, imageHeight);// 15 pixels below the first line
        }

        graphics.drawString(brewText, startPoint.x, startPoint.y);

        return null; // Return the appropriate dimension if needed
    }
}
