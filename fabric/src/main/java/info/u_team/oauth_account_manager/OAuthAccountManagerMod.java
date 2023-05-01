package info.u_team.oauth_account_manager;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import info.u_team.oauth_account_manager.init.OAuthAccountManagerClientConstruct;
import net.fabricmc.api.ClientModInitializer;

public class OAuthAccountManagerMod implements ClientModInitializer {
	
	public static final String MODID = OAuthAccountManagerReference.MODID;
	public static final Logger LOGGER = LogUtils.getLogger();
	
	@Override
	public void onInitializeClient() {
		OAuthAccountManagerClientConstruct.construct();
	}
	
}
