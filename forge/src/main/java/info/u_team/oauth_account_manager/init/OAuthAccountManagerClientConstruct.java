package info.u_team.oauth_account_manager.init;

import info.u_team.oauth_account_manager.OAuthAccountManagerMod;
import info.u_team.oauth_account_manager.util.MinecraftAccounts;
import info.u_team.u_team_core.api.construct.Construct;
import info.u_team.u_team_core.api.construct.ModConstruct;
import info.u_team.u_team_core.util.registry.BusRegister;
import net.minecraft.client.Minecraft;

@Construct(modid = OAuthAccountManagerMod.MODID, client = true)
public class OAuthAccountManagerClientConstruct implements ModConstruct {
	
	@Override
	public void construct() {
		if (Minecraft.getInstance() != null) {
			MinecraftAccounts.enqueueLoad();
		}
		
		BusRegister.registerForge(OAuthAccountManagerEventHandler::registerForge);
	}
}