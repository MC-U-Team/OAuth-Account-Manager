package info.u_team.oauth_account_manager.init;

import info.u_team.oauth_account_manager.util.MinecraftAccounts;

public class OAuthAccountManagerClientConstruct {
	
	public static void construct() {
		MinecraftAccounts.enqueueLoad();
		
		OAuthAccountManagerEventHandler.register();
	}
}