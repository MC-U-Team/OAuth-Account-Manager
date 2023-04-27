package info.u_team.oauth_account_manager.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization;
import info.u_team.u_team_core.gui.elements.UButton;
import info.u_team.u_team_core.screen.UScreen;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class AccountsScreen extends UScreen {
	
	private final Screen lastScreen;
	
	public AccountsScreen(Screen lastScreen) {
		super(Component.translatable(OAuthAccountManagerLocalization.SCREEN_ACCOUNTS_TITLE));
		this.lastScreen = lastScreen;
	}
	
	@Override
	protected void init() {
		super.init();
		
		final UButton useButton = addRenderableWidget(new UButton(0, 0, 74, 20, Component.translatable(OAuthAccountManagerLocalization.SCREEN_ACCOUNTS_USE_BUTTON)));
		
		final UButton addButton = addRenderableWidget(new UButton(0, 0, 74, 20, Component.translatable(OAuthAccountManagerLocalization.SCREEN_ACCOUNTS_ADD_BUTTON)));
		addButton.setPressable(() -> {
			minecraft.setScreen(new AddAccountOpenLinkScreen(this));
			
			// minecraft.setScreen(new ConfirmLinkScreen(autoOpen -> {
			// if (autoOpen) {
			// Util.getPlatform().openUri("https://aka.ms/javablocking");
			// }
			//
			// this.minecraft.setScreen(this);
			// }, Component.literal("Title"), Component.literal("Message"), "https://aka.ms/javablocking",
			// CommonComponents.GUI_CANCEL, true));
		});
		
		final UButton deleteButton = addRenderableWidget(new UButton(0, 0, 74, 20, Component.translatable(OAuthAccountManagerLocalization.SCREEN_ACCOUNTS_DELETE_BUTTON)));
		
		final UButton cancelButton = addRenderableWidget(new UButton(0, 0, 74, 20, CommonComponents.GUI_CANCEL));
		cancelButton.setPressable(() -> minecraft.setScreen(lastScreen));
		
		final LinearLayout layout = new LinearLayout(308, 20, LinearLayout.Orientation.HORIZONTAL);
		layout.addChild(useButton);
		layout.addChild(addButton);
		layout.addChild(deleteButton);
		layout.addChild(cancelButton);
		layout.arrangeElements();
		
		FrameLayout.centerInRectangle(layout, 0, height - 64, width, 64);
		// try {
		// Authenticator authenticator =
		// SimpleMinecraftAuthentication.getMethodOrThrow("web").create().initalAuthentication().buildAuthenticator();
		// authenticator.run(state -> System.out.println("Current state:" + state));
		// System.out.println(authenticator.getUser().get().accessToken());
		//
		// AuthenticationUtil.isAccessTokenValid(authenticator.getUser().get().accessToken()).thenAccept(System.out::println);
		//
		// } catch (IllegalArgumentException e) {
		// e.printStackTrace();
		// } catch (AuthenticationException e) {
		// e.printStackTrace();
		// }
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
	
}
