package info.u_team.oauth_account_manager.screen;

import java.net.URL;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization;
import info.u_team.oauth_account_manager.screen.widget.LoadingSpinnerWidget;
import info.u_team.u_team_core.gui.elements.UButton;
import info.u_team.u_team_core.screen.UScreen;
import net.hycrafthd.minecraft_authenticator.login.AuthenticationException;
import net.hycrafthd.simple_minecraft_authenticator.method.AuthenticationMethod;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class AddAccountInformationScreen extends UScreen {
	
	private final Screen lastScreen;
	
	private MultiLineTextWidget messageWidget;
	
	private Thread waitingThread;
	
	public AddAccountInformationScreen(Screen lastScreen) {
		super(Component.translatable(OAuthAccountManagerLocalization.SCREEN_ADD_ACCOUNT_INFORMATION_TITLE));
		this.lastScreen = lastScreen;
	}
	
	@Override
	protected void init() {
		super.init();
		
		messageWidget = addRenderableWidget(new MultiLineTextWidget(25, 60, Component.translatable(OAuthAccountManagerLocalization.SCREEN_ADD_ACCOUNT_INFORMATION_MESSAGE_WAITING_FOR_LOGIN, Component.translatable(OAuthAccountManagerLocalization.SCREEN_ADD_ACCOUNT_INFORMATION_MESSAGE_LINK_NOT_THERE).withStyle(ChatFormatting.ITALIC)), font));
		messageWidget.setMaxWidth(width - 50);
		messageWidget.setCentered(true);
		
		final LoadingSpinnerWidget spinner = addRenderableWidget(new LoadingSpinnerWidget(0, 0, 60, 60));
		spinner.setTooltip(Tooltip.create(Component.translatable(OAuthAccountManagerLocalization.SCREEN_ADD_ACCOUNT_OPEN_LINK_SPINNER_TOOLTIP)));
		FrameLayout.centerInRectangle(spinner, 0, 0, width, height);
		
		final UButton cancelButton = addRenderableWidget(new UButton(0, 0, 100, 20, CommonComponents.GUI_CANCEL));
		cancelButton.setPressable(this::cancelLogin);
		
		FrameLayout.centerInRectangle(cancelButton, 0, height - 64, width, 64);
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
	
	@Override
	protected void repositionElements() {
		super.repositionElements();
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
				} else {
					ex.printStackTrace();
				}
			}
		}, "OAuth-Account-Manager-Waiter");
		waitingThread.setDaemon(true);
		waitingThread.start();
	}
	
	public void loginLink(URL url) {
		final MutableComponent component = Component.literal(url.toString());
		component.withStyle(style -> {
			style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url.toString()));
			return style;
		});
		
		messageWidget.setMessage(Component.translatable(OAuthAccountManagerLocalization.SCREEN_ADD_ACCOUNT_INFORMATION_MESSAGE_WAITING_FOR_LOGIN, component));
	}
	
}
