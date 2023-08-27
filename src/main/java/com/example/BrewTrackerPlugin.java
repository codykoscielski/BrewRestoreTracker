package com.example;

import com.google.inject.Provides;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
	name = "Brew Tracker",
	description = "Tracks the number of Sara brew sips",
	tags = {"sara", "brew", "tracker"}
)
public class BrewTrackerPlugin extends Plugin
{

	@Inject
	public Client client;

	@Inject
	public BrewTracker config;

	@Inject
	public BrewTrackerOverlay brewTrackerOverlay;
	@Inject
	public OverlayManager overlayManager;


	//Initial sip counter
	public int brewCounter = 0;
	public int restoreCounter = 0;

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(brewTrackerOverlay);
		brewCounter = 0;
		restoreCounter = 0;
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(brewTrackerOverlay);
	}

	public int getBrewCounter()
	{
		return brewCounter;
	}

	public int getRestoreCounter()
	{
		return restoreCounter;
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked event) {
		if (event.getMenuOption().equals("Drink") && event.getMenuTarget().contains("Saradomin brew") || event.getMenuTarget().contains("Nectar")) {
			brewCounter++;
		}
		if (event.getMenuOption().equals("Drink") && event.getMenuTarget().contains("Super restore") || event.getMenuTarget().contains("Tears of elidinis")) {
			restoreCounter++;
			brewCounter -= 3; // Reset 3 sips of Saradomin brews for each sip of Super restore
			if (brewCounter < 0) {
				brewCounter = 0;
			}
		}
		int requiredRestores = (int) Math.floor((double) brewCounter / 3.0);
	}

//	public void onMenuOptionClicked(MenuOptionClicked event) {
//		if (event.getMenuOption().equals("Drink") && event.getMenuTarget().contains("Saradomin brew")) {
//			sipCount++;
//		}
//		if (event.getMenuOption().equals("Drink") && event.getMenuTarget().contains("Super restore")) {
//			restoreCount++;
//			sipCount -= 3; // Reset 3 sips of Saradomin brews for each sip of Super restore
//			if (sipCount < 0) {
//				sipCount = 0;
//			}
//		}
//		int requiredRestores = (int) Math.floor((double) sipCount / 3.0);
//		// Display `requiredRestores` on the screen as the restore amount.
//	}

	@Provides
	BrewTracker provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(BrewTracker.class);
	}
}
