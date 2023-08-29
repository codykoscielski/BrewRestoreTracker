package com.example;

import javax.inject.Inject;

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
	public BrewTrackerOverlay brewTrackerOverlay;
	@Inject
	public OverlayManager overlayManager;


	//Initial sip counters
	public int brewCounter = 0;
	// todo implement restores-till-0 functionality
//	public int restoreCounter = 0;

	public int brewsPerRestore = 3;

	@Override
	protected void startUp()
	{
		overlayManager.add(brewTrackerOverlay);
	}

	@Override
	protected void shutDown()
	{
		overlayManager.remove(brewTrackerOverlay);
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked event) {
		// todo confirm user actually drank the item and not just spam clicked it
		if (drankBrewOrNectar(event)) {
			drinkBrew();
		}
		if (drankRestoreOrTears(event)) {
			drinkRestore();
		}
	}

	private boolean drankBrewOrNectar(MenuOptionClicked event) {
		return event.getMenuOption().equals("Drink") && event.getMenuTarget().contains("Saradomin brew") || event.getMenuTarget().contains("Nectar");
	}

	private boolean drankRestoreOrTears(MenuOptionClicked event) {
		return event.getMenuOption().equals("Drink") && event.getMenuTarget().contains("Super restore") || event.getMenuTarget().contains("Tears of elidinis");
	}

	private void drinkBrew() {
		brewCounter++;
	}

	private void drinkRestore() {
		brewCounter -= brewsPerRestore;
		if (brewCounter < 0) { // Never allow us to go below 0
			brewCounter = 0;
		}
	}
}
