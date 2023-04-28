package info.u_team.oauth_account_manager.screen;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization;
import info.u_team.u_team_core.screen.UScreen;
import net.hycrafthd.minecraft_authenticator.login.AuthenticationException;
import net.hycrafthd.simple_minecraft_authenticator.method.AuthenticationMethod;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class AddAccountInformationScreen extends UScreen {
	
	private final Screen lastScreen;
	
	private Future<?> future;
	
	public AddAccountInformationScreen(Screen lastScreen) {
		super(Component.translatable(OAuthAccountManagerLocalization.SCREEN_ADD_ACCOUNT_OPEN_LINK_TITLE));
		this.lastScreen = lastScreen;
	}
	
	public void authenticate(AuthenticationMethod method) {
		future = Executors.newSingleThreadExecutor().submit(() -> {
			try {
				method.initalAuthentication();
			} catch (AuthenticationException ex) {
				ex.printStackTrace();
			}
		});
	}
	
	@Override
	protected void init() {
		super.init();
	}
	
	@Override
	public void onClose() {
		future.cancel(true);
		minecraft.setScreen(lastScreen);
	}
	
}
