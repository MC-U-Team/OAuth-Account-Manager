package info.u_team.oauth_account_manager.screen;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.minecraft.UserApiService;
import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization;
import info.u_team.oauth_account_manager.screen.widget.PlayerIconWidget;
import info.u_team.oauth_account_manager.util.LoadedAccount;
import info.u_team.u_team_core.gui.elements.UButton;
import info.u_team.u_team_core.screen.UScreen;
import net.minecraft.client.User;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.social.PlayerSocialManager;
import net.minecraft.client.multiplayer.ProfileKeyPairManager;
import net.minecraft.client.multiplayer.chat.report.ReportEnvironment;
import net.minecraft.client.multiplayer.chat.report.ReportingContext;
import net.minecraft.client.telemetry.ClientTelemetryManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class AccountUseScreen extends UScreen {
	
	private final Screen lastScreen;
	
	private final GameProfile gameProfile;
	private final LoadedAccount loadedAccount;
	
	private MultiLineTextWidget messageWidget;
	private PlayerIconWidget playerIconWidget;
	private UButton doneButton;
	private UButton cancelButton;
	
	public AccountUseScreen(Screen lastScreen, GameProfile gameProfile, LoadedAccount loadedAccount) {
		super(Component.translatable(OAuthAccountManagerLocalization.SCREEN_USE_ACCOUNT_TITLE));
		this.lastScreen = lastScreen;
		this.gameProfile = gameProfile;
		this.loadedAccount = loadedAccount;
	}
	
	@Override
	protected void init() {
		super.init();
		
		messageWidget = addRenderableWidget(new MultiLineTextWidget(0, (height / 2) - 60, CommonComponents.EMPTY, font).setMaxWidth(width - 50).setCentered(true));
		setInformationMessage(Component.translatable(OAuthAccountManagerLocalization.SCREEN_USE_ACCOUNT_MESSAGE));
		
		playerIconWidget = addRenderableWidget(new PlayerIconWidget(width / 2 - 32, height / 2 - 32, 64, gameProfile));
		
		doneButton = addRenderableWidget(new UButton(0, 0, 100, 20, CommonComponents.GUI_DONE));
		doneButton.setPressable(() -> {
			try {
				useAccount();
			} catch (AuthenticationException e) {
				e.printStackTrace();
			}
		});
		
		cancelButton = addRenderableWidget(new UButton(0, 0, 100, 20, CommonComponents.GUI_CANCEL));
		cancelButton.setPressable(() -> minecraft.setScreen(lastScreen));
		
		final LinearLayout layout = new LinearLayout(205, 20, LinearLayout.Orientation.HORIZONTAL);
		layout.addChild(doneButton);
		layout.addChild(cancelButton);
		layout.arrangeElements();
		
		FrameLayout.centerInRectangle(layout, 0, height - 64, width, 64);
	}
	
	@Override
	public void renderForeground(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
		super.renderForeground(poseStack, mouseX, mouseY, partialTick);
		
		drawCenteredString(poseStack, font, title, width / 2, 20, 0xFFFFFF);
	}
	
	@Override
	public void onClose() {
		minecraft.setScreen(lastScreen);
	}
	
	@Override
	protected void repositionElements() {
		final Component messageWidgetMessage = messageWidget.getMessage();
		final GameProfile profile = playerIconWidget.getProfile();
		super.repositionElements();
		setInformationMessage(messageWidgetMessage);
		playerIconWidget.setProfile(profile);
	}
	
	protected void setInformationMessage(Component component) {
		messageWidget.setMessage(component);
		messageWidget.setX((width / 2) - (messageWidget.getWidth() / 2));
	}
	
	private void useAccount() throws AuthenticationException {
		setInformationMessage(Component.translatable(OAuthAccountManagerLocalization.SCREEN_USE_ACCOUNT_MESSAGE_WAITING));
		CompletableFuture.runAsync(() -> {
			try {
				final var msUser = loadedAccount.user();
				
				final UserApiService userApiService = minecraft.authenticationService.createUserApiService(msUser.accessToken());
				final User user = new User(msUser.name(), msUser.uuid(), msUser.accessToken(), Optional.of(msUser.xuid()), Optional.of(msUser.clientId()), User.Type.byName(msUser.type()));
				final PlayerSocialManager playerSocialManager = new PlayerSocialManager(minecraft, userApiService);
				final ClientTelemetryManager clientTelemetryManager = new ClientTelemetryManager(minecraft, userApiService, user);
				final ProfileKeyPairManager profileKeyPairManager = ProfileKeyPairManager.create(userApiService, user, minecraft.gameDirectory.toPath());
				final ReportingContext reportingContext = ReportingContext.create(ReportEnvironment.local(), userApiService);
				
				minecraft.execute(() -> {
					minecraft.userApiService = userApiService;
					minecraft.user = user;
					minecraft.playerSocialManager = playerSocialManager;
					minecraft.telemetryManager = clientTelemetryManager;
					minecraft.profileKeyPairManager = profileKeyPairManager;
					minecraft.reportingContext = reportingContext;
					minecraft.profileProperties = gameProfile.getProperties();
					
					minecraft.setScreen(lastScreen);
				});
			} catch (final AuthenticationException ex) {
				setInformationMessage(Component.translatable(OAuthAccountManagerLocalization.SCREEN_USE_ACCOUNT_MESSAGE_ERROR));
			}
		});
	}
}
