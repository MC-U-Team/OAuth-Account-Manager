package info.u_team.oauth_account_manager.screen.list;

import info.u_team.oauth_account_manager.screen.AccountUseScreen;
import info.u_team.oauth_account_manager.util.AuthenticationUtil.MinecraftAccountData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

public class LaunchedAccountSelectionEntry extends AbstractAccountSelectionEntry {
	
	private static final MinecraftAccountData LAUNCHED_DATA;
	
	static {
		final Minecraft minecraft = Minecraft.getInstance();
		LAUNCHED_DATA = new MinecraftAccountData(minecraft.userApiService, minecraft.user, minecraft.playerSocialManager, minecraft.telemetryManager, minecraft.profileKeyPairManager, minecraft.reportingContext, minecraft.profileProperties);
	}
	
	public LaunchedAccountSelectionEntry(Screen ourScreen, AccountSelectionList selectionList) {
		super(ourScreen, selectionList, LAUNCHED_DATA.user().getProfileId(), LAUNCHED_DATA.user().getGameProfile());
	}
	
	@Override
	protected void useEntry() {
		final AccountUseScreen useScreen = new AccountUseScreen(ourScreen, getProfile(), () -> LAUNCHED_DATA);
		minecraft.setScreen(useScreen);
	}
	
}
