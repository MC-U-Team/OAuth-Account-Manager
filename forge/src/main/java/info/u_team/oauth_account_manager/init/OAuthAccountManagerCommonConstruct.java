package info.u_team.oauth_account_manager.init;

import info.u_team.oauth_account_manager.OAuthAccountManagerMod;
import info.u_team.u_team_core.api.construct.Construct;
import info.u_team.u_team_core.api.construct.ModConstruct;
import net.minecraftforge.fml.IExtensionPoint.DisplayTest;
import net.minecraftforge.fml.ModLoadingContext;

@Construct(modid = OAuthAccountManagerMod.MODID)
public class OAuthAccountManagerCommonConstruct implements ModConstruct {
	
	@SuppressWarnings("removal")
	@Override
	public void construct() {
		ModLoadingContext.get().registerExtensionPoint(DisplayTest.class, () -> new DisplayTest(() -> DisplayTest.IGNORESERVERONLY, (remoteVersion, network) -> true));
	}
}