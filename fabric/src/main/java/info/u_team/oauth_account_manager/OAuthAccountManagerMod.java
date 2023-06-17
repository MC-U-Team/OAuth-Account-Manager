package info.u_team.oauth_account_manager;

import org.slf4j.Logger;

import info.u_team.u_team_core.util.annotation.AnnotationManager;
import net.fabricmc.api.ClientModInitializer;

public class OAuthAccountManagerMod implements ClientModInitializer {
	
	public static final String MODID = OAuthAccountManagerReference.MODID;
	public static final Logger LOGGER = OAuthAccountManagerReference.LOGGER;
	
	@Override
	public void onInitializeClient() {
		AnnotationManager.callAnnotations(MODID);
	}
	
}
