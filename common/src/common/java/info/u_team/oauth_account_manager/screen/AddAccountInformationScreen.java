package info.u_team.oauth_account_manager.screen;

import info.u_team.oauth_account_manager.OAuthAccountManagerReference;
import info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization;
import net.hycrafthd.minecraft_authenticator.login.AuthenticationException;
import net.hycrafthd.simple_minecraft_authenticator.method.AuthenticationMethod;
import net.hycrafthd.simple_minecraft_authenticator.result.AuthenticationResult;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class AddAccountInformationScreen extends CommonWaitingScreen {
	
	public AddAccountInformationScreen(Screen lastScreen) {
		super(lastScreen, Component.translatable(OAuthAccountManagerLocalization.SCREEN_ADD_ACCOUNT_INFORMATION_TITLE), Component.translatable(OAuthAccountManagerLocalization.SCREEN_ADD_ACCOUNT_INFORMATION_SPINNER_TOOLTIP_WAITING));
	}
	
	@Override
	protected void init() {
		super.init();
		setInformationMessage(Component.translatable(OAuthAccountManagerLocalization.SCREEN_ADD_ACCOUNT_INFORMATION_MESSAGE_WAITING_FOR_LOGIN));
	}
	
	public void authenticate(AuthenticationMethod method) {
		createWaitingThread(() -> {
			final AuthenticationResult result;
			try {
				result = method.initalAuthentication();
			} catch (final AuthenticationException ex) {
				if (ex.getCause() instanceof InterruptedException) {
					// Canceled from us, do not do anything
				} else {
					// setInformationMessage(Component.literal(ex.getMessage())); // TODO extra error state
					doneButton.active = true;
					OAuthAccountManagerReference.LOGGER.warn("Microsoft oauth was not completed sucessfully", ex);
				}
				return;
			}
			
			minecraft.execute(() -> {
				final AccountLoginScreen screen = new AccountLoginScreen(lastScreen);
				screen.login(result);
				minecraft.setScreen(screen);
			});
		});
	}
}
