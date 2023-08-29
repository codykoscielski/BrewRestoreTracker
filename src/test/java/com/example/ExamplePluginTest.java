package com.example;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;
import net.runelite.client.plugins.Plugin;

public class ExamplePluginTest
{
	public static void main(String[] args) throws Exception
	{
		@SuppressWarnings("unchecked")
		Class<? extends Plugin>[] pluginClasses = new Class[]{ BrewTrackerPlugin.class };
		ExternalPluginManager.loadBuiltin(pluginClasses);
		RuneLite.main(args);
	}
}