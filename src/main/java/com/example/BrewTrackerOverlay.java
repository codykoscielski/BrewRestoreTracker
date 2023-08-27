package com.example;

import net.runelite.client.ui.overlay.Overlay;
import javax.inject.Inject;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Color;
public class BrewTrackerOverlay extends Overlay {

    public final BrewTrackerPlugin plugin;

    @Inject
    public BrewTrackerOverlay(BrewTrackerPlugin plugin) {
        this.plugin = plugin;
    }


    @Override
    public Dimension render(Graphics2D graphics) {
        int brewCount = plugin.getBrewCounter();

        // Draw the text on the overlay
        Point startPoint = new Point(30, 40);

        // Display the brew and restore sip count
        String brewText = "Brew Sips: " + brewCount;
        String restoreText = "Sip restore";

        // Set the color for the text
        graphics.setColor(Color.WHITE);

        if(brewCount >= 3) {
            graphics.setColor(Color.RED);
            graphics.drawString(restoreText, startPoint.x, startPoint.y + 15); // 15 pixels below the first line
        }

        graphics.drawString(brewText, startPoint.x, startPoint.y);

        return null; // Return the appropriate dimension if needed
    }
}
