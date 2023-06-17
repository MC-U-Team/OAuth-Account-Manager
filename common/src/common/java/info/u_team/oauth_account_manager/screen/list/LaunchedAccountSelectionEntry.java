package info.u_team.oauth_account_manager.screen.list;

import com.mojang.authlib.minecraft.UserApiService;
import com.mojang.authlib.properties.PropertyMap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.User;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.social.PlayerSocialManager;
import net.minecraft.client.multiplayer.ProfileKeyPairManager;
import net.minecraft.client.multiplayer.chat.report.ReportingContext;
import net.minecraft.client.telemetry.ClientTelemetryManager;

public class LaunchedAccountSelectionEntry extends AbstractAccountSelectionEntry {
	
	private static final Minecraft minecraft = Minecraft.getInstance();
	
	private static final UserApiService userApiService = minecraft.userApiService;
	private static final User user = minecraft.user;
	private static final PlayerSocialManager playerSocialManager = minecraft.playerSocialManager;
	private static final ClientTelemetryManager clientTelemetryManager = minecraft.telemetryManager;
	private static final ProfileKeyPairManager profileKeyPairManager = minecraft.profileKeyPairManager;
	private static final ReportingContext reportingContext = minecraft.reportingContext;
	private static final PropertyMap profileProperties = minecraft.profileProperties;
	
	public LaunchedAccountSelectionEntry(Screen ourScreen, AccountSelectionList selectionList) {
		super(ourScreen, selectionList, user.getProfileId(), user.getGameProfile());
	}
	
	@Override
	protected void useEntry() {
		minecraft.userApiService = userApiService;
		minecraft.user = user;
		minecraft.playerSocialManager = playerSocialManager;
		minecraft.telemetryManager = clientTelemetryManager;
		minecraft.profileKeyPairManager = profileKeyPairManager;
		minecraft.reportingContext = reportingContext;
		minecraft.profileProperties = profileProperties;
		minecraft.setScreen(ourScreen);
	}
	
}
