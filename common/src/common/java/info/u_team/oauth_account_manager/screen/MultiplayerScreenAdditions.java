package info.u_team.oauth_account_manager.screen;

import info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization;
import info.u_team.u_team_core.gui.elements.UButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.network.chat.Component;

public class MultiplayerScreenAdditions {
	
	public static UButton addButton(JoinMultiplayerScreen screen) {
		final UButton button = new UButton(0, 0, 74, 16, Component.translatable(OAuthAccountManagerLocalization.SCREEN_MULTIPLAYER_ADDITION_ACCOUNT_BUTTON));
		FrameLayout.centerInRectangle(button, 117, 0, screen.width, 45);
		button.setPressable(() -> {
			Minecraft.getInstance().setScreen(new AccountsScreen());
		});
		return button;
	}
	
}
