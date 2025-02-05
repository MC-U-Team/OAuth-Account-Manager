package info.u_team.oauth_account_manager;

import org.slf4j.Logger;

import info.u_team.u_team_core.util.annotation.AnnotationManager;
import net.minecraftforge.fml.common.Mod;

@Mod(OAuthAccountManagerMod.MODID)
public class OAuthAccountManagerMod {
	
	public static final String MODID = OAuthAccountManagerReference.MODID;
	public static final Logger LOGGER = OAuthAccountManagerReference.LOGGER;
	
	public OAuthAccountManagerMod() {
		AnnotationManager.callAnnotations(MODID);
	}
	
}
