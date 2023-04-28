package info.u_team.oauth_account_manager.data.provider;

import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_ACCOUNTS_ADD_BUTTON;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_ACCOUNTS_DELETE_BUTTON;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_ACCOUNTS_TITLE;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_ACCOUNTS_USE_BUTTON;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_ADD_ACCOUNT_INFORMATION_MESSAGE_LINK_NOT_THERE;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_ADD_ACCOUNT_INFORMATION_MESSAGE_WAITING_FOR_LOGIN;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_ADD_ACCOUNT_INFORMATION_TITLE;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_ADD_ACCOUNT_OPEN_LINK_MESSAGE;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_ADD_ACCOUNT_OPEN_LINK_SPINNER_TOOLTIP;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_ADD_ACCOUNT_OPEN_LINK_TITLE;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_MULTIPLAYER_ADDITION_ACCOUNT_BUTTON;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_MULTIPLAYER_ADDITION_ACCOUNT_BUTTON_TOOLTIP_CHECKING;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_MULTIPLAYER_ADDITION_ACCOUNT_BUTTON_TOOLTIP_INVALID;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_MULTIPLAYER_ADDITION_ACCOUNT_BUTTON_TOOLTIP_VALID;

import info.u_team.u_team_core.data.CommonLanguagesProvider;
import info.u_team.u_team_core.data.GenerationData;

public class OAuthAccountManagerLanguagesProvider extends CommonLanguagesProvider {
	
	public OAuthAccountManagerLanguagesProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void register() {
		add(SCREEN_ACCOUNTS_TITLE, "Manage Minecraft Accounts");
		add(SCREEN_ACCOUNTS_USE_BUTTON, "Use Account");
		add(SCREEN_ACCOUNTS_ADD_BUTTON, "Add Account");
		add(SCREEN_ACCOUNTS_DELETE_BUTTON, "Delete");
		
		add(SCREEN_ADD_ACCOUNT_OPEN_LINK_TITLE, "Add new Minecraft Account");
		add(SCREEN_ADD_ACCOUNT_OPEN_LINK_MESSAGE, "Open the microsoft login page with the button '%s' or '%s'. Then log into the account you want to add.");
		add(SCREEN_ADD_ACCOUNT_OPEN_LINK_SPINNER_TOOLTIP, "Waiting for user action");
		
		add(SCREEN_ADD_ACCOUNT_INFORMATION_TITLE, "Add new Minecraft Account");
		add(SCREEN_ADD_ACCOUNT_INFORMATION_MESSAGE_LINK_NOT_THERE, "Generating link");
		add(SCREEN_ADD_ACCOUNT_INFORMATION_MESSAGE_WAITING_FOR_LOGIN, "If not already done open the link '%s' in the text field and log into your account. Waiting for login to be completed in web browser.");
		
		add(SCREEN_MULTIPLAYER_ADDITION_ACCOUNT_BUTTON, "Accounts");
		add(SCREEN_MULTIPLAYER_ADDITION_ACCOUNT_BUTTON_TOOLTIP_CHECKING, "Checking current account session");
		add(SCREEN_MULTIPLAYER_ADDITION_ACCOUNT_BUTTON_TOOLTIP_VALID, "You are currently logged in as %s");
		add(SCREEN_MULTIPLAYER_ADDITION_ACCOUNT_BUTTON_TOOLTIP_INVALID, "Session token is invalid");
	}
	
}
