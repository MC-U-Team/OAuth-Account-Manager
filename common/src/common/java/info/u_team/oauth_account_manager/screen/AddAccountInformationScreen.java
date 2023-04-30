package info.u_team.oauth_account_manager.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.oauth_account_manager.OAuthAccountManagerReference;
import info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization;
import info.u_team.oauth_account_manager.screen.widget.LoadingSpinnerWidget;
import info.u_team.u_team_core.gui.elements.UButton;
import info.u_team.u_team_core.screen.UScreen;
import net.hycrafthd.minecraft_authenticator.login.AuthenticationException;
import net.hycrafthd.minecraft_authenticator.login.Authenticator;
import net.hycrafthd.simple_minecraft_authenticator.method.AuthenticationMethod;
import net.hycrafthd.simple_minecraft_authenticator.result.AuthenticationResult;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class AddAccountInformationScreen extends UScreen {
	
	private final Screen lastScreen;
	
	private MultiLineTextWidget messageWidget;
	private UButton doneButton;
	
	private Thread waitingThread;
	
	public AddAccountInformationScreen(Screen lastScreen) {
		super(Component.translatable(OAuthAccountManagerLocalization.SCREEN_ADD_ACCOUNT_INFORMATION_TITLE));
		this.lastScreen = lastScreen;
	}
	
	@Override
	protected void init() {
		super.init();
		
		messageWidget = addRenderableWidget(new MultiLineTextWidget(0, (height / 2) - 60, CommonComponents.EMPTY, font).setMaxWidth(width - 50).setCentered(true));
		setInformationMessage(Component.translatable(OAuthAccountManagerLocalization.SCREEN_ADD_ACCOUNT_INFORMATION_MESSAGE_WAITING_FOR_LOGIN));
		
		final LoadingSpinnerWidget spinner = addRenderableWidget(new LoadingSpinnerWidget(0, 0, 60, 60));
		spinner.setTooltip(Tooltip.create(Component.translatable(OAuthAccountManagerLocalization.SCREEN_ADD_ACCOUNT_INFORMATION_SPINNER_TOOLTIP_WAITING)));
		FrameLayout.centerInRectangle(spinner, 0, 0, width, height);
		
		doneButton = addRenderableWidget(new UButton(0, 0, 100, 20, CommonComponents.GUI_DONE));
		doneButton.setPressable(() -> minecraft.setScreen(lastScreen));
		doneButton.active = false;
		
		final UButton cancelButton = addRenderableWidget(new UButton(0, 0, 100, 20, CommonComponents.GUI_CANCEL));
		cancelButton.setPressable(this::cancelLogin);
		
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
		cancelLogin();
	}
	
	@Override
	protected void repositionElements() {
		final Component message = messageWidget.getMessage();
		final boolean active = doneButton.active;
		super.repositionElements();
		setInformationMessage(message);
		doneButton.active = active;
	}
	
	private void setInformationMessage(Component component) {
		messageWidget.setMessage(component);
		messageWidget.setX((width / 2) - (messageWidget.getWidth() / 2));
	}
	
	private void cancelLogin() {
		if (waitingThread != null) {
			waitingThread.interrupt();
		}
		minecraft.setScreen(lastScreen);
	}
	
	public void authenticate(AuthenticationMethod method) {
		waitingThread = new Thread(() -> {
			final AuthenticationResult result;
			try {
				result = method.initalAuthentication();
			} catch (final AuthenticationException ex) {
				if (ex.getCause() instanceof InterruptedException) {
					// Canceled from us, do not do anything
				} else {
					// setInformationMessage(Component.literal(ex.getMessage())); // TODO extra error state
					doneButton.active = true;
					OAuthAccountManagerReference.LOGGER.warn("Microsoft oauth was not completed sucessfully", ex);
				}
				return;
			}
			
			final Authenticator authenticator = result.buildAuthenticator();
			try {
				authenticator.run(state -> {
					setInformationMessage(Component.literal(state.name())); // TODO extra messages here
				});
			} catch (final AuthenticationException ex) {
				doneButton.active = true;
				// setInformationMessage(Component.literal(ex.getMessage())); // TODO extra error state
				OAuthAccountManagerReference.LOGGER.warn("Authentication with minecraft services did not complete sucessfully", ex);
				return;
			}
			// TODO save;
			
			doneButton.active = true;
		}, "OAuth-Account-Manager-Waiter");
		waitingThread.setDaemon(true);
		waitingThread.start();
	}
}
