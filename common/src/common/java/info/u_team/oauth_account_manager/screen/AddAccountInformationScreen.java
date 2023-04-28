package info.u_team.oauth_account_manager.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization;
import info.u_team.u_team_core.gui.elements.UButton;
import info.u_team.u_team_core.screen.UScreen;
import net.hycrafthd.minecraft_authenticator.login.AuthenticationException;
import net.hycrafthd.simple_minecraft_authenticator.method.AuthenticationMethod;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class AddAccountInformationScreen extends UScreen {
	
	private final Screen lastScreen;
	
	private Thread waitingThread;
	
	public AddAccountInformationScreen(Screen lastScreen) {
		super(Component.translatable(OAuthAccountManagerLocalization.SCREEN_ADD_ACCOUNT_OPEN_LINK_TITLE));
		this.lastScreen = lastScreen;
	}
	
	@Override
	protected void init() {
		super.init();
		
		final LoadingSpinnerWidget spinner = addRenderableWidget(new LoadingSpinnerWidget(0, 0, 60, 60));
		spinner.setTooltip(Tooltip.create(Component.translatable(OAuthAccountManagerLocalization.SCREEN_ADD_ACCOUNT_OPEN_LINK_SPINNER_TOOLTIP)));
		FrameLayout.centerInRectangle(spinner, 0, 0, width, height);
		
		final UButton cancelButton = addRenderableWidget(new UButton(0, 0, 100, 20, CommonComponents.GUI_CANCEL));
		cancelButton.setPressable(this::cancelLogin);
		
		final LinearLayout layout = new LinearLayout(308, 20, LinearLayout.Orientation.HORIZONTAL);
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
		cancelLogin();
	}
	
	private void cancelLogin() {
		if (waitingThread != null) {
			waitingThread.interrupt();
		}
		minecraft.setScreen(lastScreen);
	}
	
	public void authenticate(AuthenticationMethod method) {
		waitingThread = new Thread(() -> {
			try {
				method.initalAuthentication();
			} catch (AuthenticationException ex) {
				if (ex.getCause() instanceof InterruptedException) {
					System.out.println("INTERRUPTED. STopping");
				} else {
					ex.printStackTrace();
				}
			}
		}, "OAuth-Account-Manager-Waiter");
		waitingThread.setDaemon(true);
		waitingThread.start();
	}
	
}
