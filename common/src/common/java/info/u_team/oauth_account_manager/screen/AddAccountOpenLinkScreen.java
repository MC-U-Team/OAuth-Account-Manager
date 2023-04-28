package info.u_team.oauth_account_manager.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization;
import info.u_team.u_team_core.gui.elements.UButton;
import info.u_team.u_team_core.screen.UScreen;
import net.hycrafthd.simple_minecraft_authenticator.SimpleMinecraftAuthentication;
import net.hycrafthd.simple_minecraft_authenticator.method.AuthenticationMethod;
import net.minecraft.Util;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.LoggedPrintStream;

public class AddAccountOpenLinkScreen extends UScreen {
	
	private final Screen lastScreen;
	
	private MultiLineLabel message = MultiLineLabel.EMPTY;
	
	public AddAccountOpenLinkScreen(Screen lastScreen) {
		super(Component.translatable(OAuthAccountManagerLocalization.SCREEN_ADD_ACCOUNT_OPEN_LINK_TITLE));
		this.lastScreen = lastScreen;
	}
	
	@Override
	protected void init() {
		super.init();
		
		message = MultiLineLabel.create(font, Component.translatable(OAuthAccountManagerLocalization.SCREEN_ADD_ACCOUNT_OPEN_LINK_MESSAGE, Component.translatable("chat.link.open"), Component.translatable("chat.copy")), width - 50);
		
		final UButton openLink = addRenderableWidget(new UButton(0, 0, 100, 20, Component.translatable("chat.link.open")));
		openLink.setPressable(() -> startAuthenticationProcess(true));
		
		final UButton copyClipboard = addRenderableWidget(new UButton(0, 0, 100, 20, Component.translatable("chat.copy")));
		copyClipboard.setPressable(() -> startAuthenticationProcess(false));
		
		final UButton cancelButton = addRenderableWidget(new UButton(0, 0, 100, 20, CommonComponents.GUI_CANCEL));
		cancelButton.setPressable(() -> minecraft.setScreen(lastScreen));
		
		final LinearLayout layout = new LinearLayout(308, 20, LinearLayout.Orientation.HORIZONTAL);
		layout.addChild(openLink);
		layout.addChild(copyClipboard);
		layout.addChild(cancelButton);
		layout.arrangeElements();
		
		FrameLayout.centerInRectangle(layout, 0, height - 64, width, 64);
	}
	
	@Override
	public Component getNarrationMessage() {
		return CommonComponents.joinForNarration(super.getNarrationMessage(), Component.translatable(OAuthAccountManagerLocalization.SCREEN_ADD_ACCOUNT_OPEN_LINK_MESSAGE));
	}
	
	@Override
	public void renderForeground(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
		super.renderForeground(poseStack, mouseX, mouseY, partialTick);
		
		drawCenteredString(poseStack, font, title, width / 2, 20, 0xFFFFFF);
		message.renderCentered(poseStack, width / 2, height / 2 - 40);
	}
	
	@Override
	public void onClose() {
		minecraft.setScreen(lastScreen);
	}
	
	private void startAuthenticationProcess(boolean open) {
		final AuthenticationMethod method = SimpleMinecraftAuthentication.getMethod("web").get().create(new LoggedPrintStream("OAuth-Account-Manager", System.out), System.in);
		
		final AddAccountInformationScreen screen = new AddAccountInformationScreen(lastScreen);
		
		method.registerLoginUrlCallback(url -> {
			if (open) {
				Util.getPlatform().openUrl(url);
			} else {
				minecraft.keyboardHandler.setClipboard(url.toString());
			}
		});
		screen.authenticate(method);
		
		minecraft.setScreen(screen);
	}
}