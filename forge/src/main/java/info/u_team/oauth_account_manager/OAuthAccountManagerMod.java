package info.u_team.oauth_account_manager;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import info.u_team.u_team_core.util.annotation.AnnotationManager;
import info.u_team.u_team_core.util.verify.JarSignVerifier;
import net.minecraftforge.fml.common.Mod;

@Mod(OAuthAccountManagerMod.MODID)
public class OAuthAccountManagerMod {
	
	public static final String MODID = "oauthaccountmanager";
	public static final Logger LOGGER = LogUtils.getLogger();
	
	public OAuthAccountManagerMod() {
		JarSignVerifier.checkSigned(MODID);
		
		AnnotationManager.callAnnotations(MODID);
	}
	
}
