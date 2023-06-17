package info.u_team.oauth_account_manager.init;

import info.u_team.oauth_account_manager.screen.MultiplayerScreenAdditions;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class OAuthAccountManagerEventHandler {
	
	private static void onScreenInitPost(ScreenEvent.Init.Post event) {
		if (!(event.getScreen() instanceof JoinMultiplayerScreen multiplayerScreen)) {
			return;
		}
		event.addListener(MultiplayerScreenAdditions.addButton(multiplayerScreen.width, multiplayerScreen));
	}
	
	public static void registerForge(IEventBus bus) {
		bus.addListener(OAuthAccountManagerEventHandler::onScreenInitPost);
	}
	
}
