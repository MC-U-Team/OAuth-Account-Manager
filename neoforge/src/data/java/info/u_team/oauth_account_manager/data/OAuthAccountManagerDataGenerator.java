package info.u_team.oauth_account_manager.data;

import info.u_team.oauth_account_manager.OAuthAccountManagerMod;
import info.u_team.oauth_account_manager.data.provider.OAuthAccountManagerLanguagesProvider;
import info.u_team.u_team_core.data.GenerationData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = OAuthAccountManagerMod.MODID, bus = Bus.MOD)
public class OAuthAccountManagerDataGenerator {
	
	@SubscribeEvent
	public static void data(GatherDataEvent event) {
		final GenerationData data = new GenerationData(OAuthAccountManagerMod.MODID, event);
		data.addProvider(event.includeClient(), OAuthAccountManagerLanguagesProvider::new);
	}
	
}
