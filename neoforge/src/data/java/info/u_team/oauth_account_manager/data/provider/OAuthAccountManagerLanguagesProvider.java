package info.u_team.oauth_account_manager.data.provider;

import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_ACCOUNTS_ADD_BUTTON;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_ACCOUNTS_DELETE_BUTTON;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_ACCOUNTS_LIST_LAUNCHED_TITLE;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_ACCOUNTS_LIST_LAUNCHED_TOOLTIP;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_ACCOUNTS_LIST_USED_TOOLTIP;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_ACCOUNTS_TITLE;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_ACCOUNTS_USE_BUTTON;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_ACOUNT_LOGIN_INFORMATION_MESSAGE_ERROR;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_ACOUNT_LOGIN_INFORMATION_MESSAGE_SUCCESS;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_ACOUNT_LOGIN_LOGIN_STATE_INFO_ACCESS_TOKEN;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_ACOUNT_LOGIN_LOGIN_STATE_INFO_ENTITLEMENT;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_ACOUNT_LOGIN_LOGIN_STATE_INFO_INITAL_FILE;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_ACOUNT_LOGIN_LOGIN_STATE_INFO_LOGIN_MICOSOFT;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_ACOUNT_LOGIN_LOGIN_STATE_INFO_PROFILE;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_ACOUNT_LOGIN_LOGIN_STATE_INFO_XBL_TOKEN;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_ACOUNT_LOGIN_LOGIN_STATE_INFO_XBOX_PROFILE;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_ACOUNT_LOGIN_LOGIN_STATE_INFO_XSTS_TOKEN;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_ACOUNT_LOGIN_SPINNER_TOOLTIP_LOGGING;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_ACOUNT_LOGIN_TITLE;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_ADD_ACCOUNT_INFORMATION_MESSAGE_ERROR;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_ADD_ACCOUNT_INFORMATION_MESSAGE_WAITING_FOR_LOGIN;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_ADD_ACCOUNT_INFORMATION_SPINNER_TOOLTIP_WAITING;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_ADD_ACCOUNT_INFORMATION_TITLE;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_ADD_ACCOUNT_OPEN_LINK_MESSAGE;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_ADD_ACCOUNT_OPEN_LINK_TITLE;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_CHECK_ACCOUNT_VALID_BUTTON_RETRY;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_CHECK_ACCOUNT_VALID_MESSAGE_CHECKING;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_CHECK_ACCOUNT_VALID_MESSAGE_INVALID;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_CHECK_ACCOUNT_VALID_TITLE;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_DELETE_ACCOUNT_DELETE_BUTTON;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_DELETE_ACCOUNT_MESSAGE;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_DELETE_ACCOUNT_TITLE;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_MULTIPLAYER_ADDITION_ACCOUNT_BUTTON;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_MULTIPLAYER_ADDITION_ACCOUNT_BUTTON_TOOLTIP_CHECKING;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_MULTIPLAYER_ADDITION_ACCOUNT_BUTTON_TOOLTIP_INVALID;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_MULTIPLAYER_ADDITION_ACCOUNT_BUTTON_TOOLTIP_VALID;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_USE_ACCOUNT_MESSAGE;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_USE_ACCOUNT_MESSAGE_ERROR;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_USE_ACCOUNT_MESSAGE_WAITING;
import static info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization.SCREEN_USE_ACCOUNT_TITLE;

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
		add(SCREEN_ACCOUNTS_LIST_LAUNCHED_TITLE, "Launched Account (%s)");
		add(SCREEN_ACCOUNTS_LIST_LAUNCHED_TOOLTIP, "%s and was used to launch the game. Might not have a valid access token");
		add(SCREEN_ACCOUNTS_LIST_USED_TOOLTIP, "This account is currently used in this minecraft instance");
		
		add(SCREEN_ADD_ACCOUNT_OPEN_LINK_TITLE, "Add new Minecraft Account");
		add(SCREEN_ADD_ACCOUNT_OPEN_LINK_MESSAGE, "Open the microsoft login page with the button '%s' or '%s'. Then log into the account you want to add");
		
		add(SCREEN_ADD_ACCOUNT_INFORMATION_TITLE, "Add new Minecraft Account");
		add(SCREEN_ADD_ACCOUNT_INFORMATION_MESSAGE_WAITING_FOR_LOGIN, "Waiting for login to be completed in web browser");
		add(SCREEN_ADD_ACCOUNT_INFORMATION_MESSAGE_ERROR, "Microsoft authentication didn't complete sucessfully because: %s");
		add(SCREEN_ADD_ACCOUNT_INFORMATION_SPINNER_TOOLTIP_WAITING, "Waiting for user action");
		
		add(SCREEN_CHECK_ACCOUNT_VALID_TITLE, "Checking minecraft access token");
		add(SCREEN_CHECK_ACCOUNT_VALID_MESSAGE_CHECKING, "Checking if access token for minecraft account is valid");
		add(SCREEN_CHECK_ACCOUNT_VALID_MESSAGE_INVALID, "Access token for minecraft is currently invalid. You can retry to get a valid access token or cancel the action");
		add(SCREEN_CHECK_ACCOUNT_VALID_BUTTON_RETRY, "Retry");
		
		add(SCREEN_USE_ACCOUNT_TITLE, "Use minecraft account");
		add(SCREEN_USE_ACCOUNT_MESSAGE, "Change the Minecraft account to the selected account for Singleplayer and Multiplayer use");
		add(SCREEN_USE_ACCOUNT_MESSAGE_WAITING, "Waiting for account to be switched");
		add(SCREEN_USE_ACCOUNT_MESSAGE_ERROR, "An error occured while switching account: %s");
		
		add(SCREEN_DELETE_ACCOUNT_TITLE, "Delete minecraft account");
		add(SCREEN_DELETE_ACCOUNT_MESSAGE, "Delete %s minecraft account from the selection list. Cannot be undone");
		add(SCREEN_DELETE_ACCOUNT_DELETE_BUTTON, "Delete");
		
		add(SCREEN_ACOUNT_LOGIN_TITLE, "Logging into minecraft account");
		add(SCREEN_ACOUNT_LOGIN_INFORMATION_MESSAGE_ERROR, "Minecraft login didn't complete sucessfully because: %s");
		add(SCREEN_ACOUNT_LOGIN_INFORMATION_MESSAGE_SUCCESS, "Authenticated sucessfully as %s to minecraft services. Account, profile and Xbox profile were loaded");
		add(SCREEN_ACOUNT_LOGIN_SPINNER_TOOLTIP_LOGGING, "Retrieving minecraft access token and user profile");
		add(SCREEN_ACOUNT_LOGIN_LOGIN_STATE_INFO_INITAL_FILE, "Resolve inital authentication file");
		add(SCREEN_ACOUNT_LOGIN_LOGIN_STATE_INFO_LOGIN_MICOSOFT, "Login to microsoft account");
		add(SCREEN_ACOUNT_LOGIN_LOGIN_STATE_INFO_XBL_TOKEN, "Authenticate to xbox live");
		add(SCREEN_ACOUNT_LOGIN_LOGIN_STATE_INFO_XSTS_TOKEN, "Authorize to minecraft services");
		add(SCREEN_ACOUNT_LOGIN_LOGIN_STATE_INFO_ACCESS_TOKEN, "Retrieve access token for minecraft");
		add(SCREEN_ACOUNT_LOGIN_LOGIN_STATE_INFO_ENTITLEMENT, "Check for entitlement of account");
		add(SCREEN_ACOUNT_LOGIN_LOGIN_STATE_INFO_PROFILE, "Retrieve minecraft profile");
		add(SCREEN_ACOUNT_LOGIN_LOGIN_STATE_INFO_XBOX_PROFILE, "Retrieve xbox profile");
		
		add(SCREEN_MULTIPLAYER_ADDITION_ACCOUNT_BUTTON, "Accounts");
		add(SCREEN_MULTIPLAYER_ADDITION_ACCOUNT_BUTTON_TOOLTIP_CHECKING, "Checking current account session");
		add(SCREEN_MULTIPLAYER_ADDITION_ACCOUNT_BUTTON_TOOLTIP_VALID, "You are currently logged in as %s");
		add(SCREEN_MULTIPLAYER_ADDITION_ACCOUNT_BUTTON_TOOLTIP_INVALID, "Session token is invalid");
	}
	
}
