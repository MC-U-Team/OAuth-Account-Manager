package info.u_team.oauth_account_manager.screen;

import info.u_team.oauth_account_manager.OAuthAccountManagerReference;
import info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization;
import net.hycrafthd.minecraft_authenticator.login.AuthenticationException;
import net.hycrafthd.minecraft_authenticator.login.Authenticator;
import net.hycrafthd.minecraft_authenticator.login.LoginState;
import net.hycrafthd.simple_minecraft_authenticator.result.AuthenticationResult;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class AccountLoginScreen extends CommonWaitingScreen {
	
	public AccountLoginScreen(Screen lastScreen) {
		super(lastScreen, Component.translatable(OAuthAccountManagerLocalization.SCREEN_ACOUNT_LOGIN_TITLE));
	}
	
	@Override
	protected void init() {
		super.init();
		setInformationMessage(getLoginStateComponent(LoginState.INITAL_FILE));
		spinnerWidget.setTooltip(Tooltip.create(Component.translatable(OAuthAccountManagerLocalization.SCREEN_ACOUNT_LOGIN_SPINNER_TOOLTIP_LOGGING)));
	}
	
	private Component getLoginStateComponent(LoginState state) {
		return Component.translatable(switch (state) {
		case INITAL_FILE -> OAuthAccountManagerLocalization.SCREEN_ACOUNT_LOGIN_LOGIN_STATE_INFO_INITAL_FILE;
		case LOGIN_MICOSOFT -> OAuthAccountManagerLocalization.SCREEN_ACOUNT_LOGIN_LOGIN_STATE_INFO_LOGIN_MICOSOFT;
		case XBL_TOKEN -> OAuthAccountManagerLocalization.SCREEN_ACOUNT_LOGIN_LOGIN_STATE_INFO_XBL_TOKEN;
		case XSTS_TOKEN -> OAuthAccountManagerLocalization.SCREEN_ACOUNT_LOGIN_LOGIN_STATE_INFO_XSTS_TOKEN;
		case ACCESS_TOKEN -> OAuthAccountManagerLocalization.SCREEN_ACOUNT_LOGIN_LOGIN_STATE_INFO_ACCESS_TOKEN;
		case ENTITLEMENT -> OAuthAccountManagerLocalization.SCREEN_ACOUNT_LOGIN_LOGIN_STATE_INFO_ENTITLEMENT;
		case PROFILE -> OAuthAccountManagerLocalization.SCREEN_ACOUNT_LOGIN_LOGIN_STATE_INFO_PROFILE;
		case XBOX_PROFILE -> OAuthAccountManagerLocalization.SCREEN_ACOUNT_LOGIN_LOGIN_STATE_INFO_XBOX_PROFILE;
		});
	}
	
	public void login(AuthenticationResult authenticationResult) {
		createWaitingThread(() -> {
			final Authenticator authenticator = authenticationResult.buildAuthenticator(true);
			try {
				authenticator.run(state -> {
					setInformationMessage(getLoginStateComponent(state));
				});
			} catch (final AuthenticationException ex) {
				if (!(ex.getCause() instanceof InterruptedException)) {
					setFinalMessage(Component.translatable(OAuthAccountManagerLocalization.SCREEN_ACOUNT_LOGIN_INFORMATION_MESSAGE_ERROR, ex.getLocalizedMessage()));
					OAuthAccountManagerReference.LOGGER.warn("Authentication with minecraft services didn't complete sucessfully", ex);
				}
				return;
			}
			
			setFinalMessage(Component.translatable(OAuthAccountManagerLocalization.SCREEN_ACOUNT_LOGIN_INFORMATION_MESSAGE_SUCCESS, authenticator.getUser().get().name()));
		});
	}
}
