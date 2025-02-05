package info.u_team.oauth_account_manager.init;

import info.u_team.oauth_account_manager.screen.MultiplayerScreenAdditions;
import info.u_team.u_team_core.util.registry.BusRegister;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.neoforged.neoforge.client.event.ScreenEvent;

public class OAuthAccountManagerEventHandler {
	
	private static void onScreenInitPost(ScreenEvent.Init.Post event) {
		if (!(event.getScreen() instanceof JoinMultiplayerScreen multiplayerScreen)) {
			return;
		}
		event.addListener(MultiplayerScreenAdditions.addButton(multiplayerScreen.width, multiplayerScreen));
	}
	
	static void register() {
		BusRegister.registerNeoForge(bus -> bus.addListener(OAuthAccountManagerEventHandler::onScreenInitPost));
	}
	
}
