package com.brewTracker;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.api.events.MenuOptionClicked;

@Slf4j
@PluginDescriptor(
	name = "Sara Brew Tracker",
	description = "Tracks the number of Sara brew sips",
	tags = {"sara", "brew", "tracker"}
)
public class BrewTracker extends Plugin
{

	@Inject
	private Client client;

	@Inject
	private ExampleConfig config;

	@Override
	protected void startUp() throws Exception
	{
		log.info("Example started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Example stopped!");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null);
		}
	}

	@PluginDescriptor(
			name = "Sara Brew Tracker",
			description = "Tracks the number of Sara brew sips",
			tags = {"sara", "brew", "tracker"}
	)

	public class SaraBrewTrackerPlugin extends Plugin  {
		private int sipCount = 0;

		@Override
		public void startUp() {
			sipCount = 0;
		}

		@Subscribe
		public void onMenuOptionClicked(MenuOptionClicked event) {
			if (event.getMenuOption().equals("Drink") && event.getMenuTarget().contains("Saradomin brew")) {
				sipCount++;
				// You can also add a notification or some other action here.
			}
		}
	}

	@Provides
	ExampleConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ExampleConfig.class);
	}
}
