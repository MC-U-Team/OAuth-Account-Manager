package info.u_team.oauth_account_manager.screen;

import info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization;
import info.u_team.oauth_account_manager.util.AuthenticationUtil;
import info.u_team.u_team_core.screen.UScreen;
import net.hycrafthd.minecraft_authenticator.login.AuthenticationException;
import net.hycrafthd.minecraft_authenticator.login.Authenticator;
import net.hycrafthd.simple_minecraft_authenticator.SimpleMinecraftAuthentication;
import net.minecraft.network.chat.Component;

public class AccountsScreen extends UScreen {
	
	public AccountsScreen() {
		super(Component.translatable(OAuthAccountManagerLocalization.SCREEN_ACCOUNTS_TITLE));
	}
	
	@Override
	protected void init() {
		super.init();
		
		try {
			Authenticator authenticator = SimpleMinecraftAuthentication.getMethodOrThrow("web").create().initalAuthentication().buildAuthenticator();
			authenticator.run(state -> System.out.println("Current state:" + state));
			System.out.println(authenticator.getUser().get().accessToken());
			
			AuthenticationUtil.isAccessTokenValid(authenticator.getUser().get().accessToken()).thenAccept(System.out::println);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}
	}
	
}
