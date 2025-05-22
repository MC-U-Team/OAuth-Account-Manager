package info.u_team.oauth_account_manager.screen;

import java.util.concurrent.CompletableFuture;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;

import info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization;
import info.u_team.oauth_account_manager.screen.widget.PlayerIconWidget;
import info.u_team.oauth_account_manager.util.AuthenticationUtil;
import info.u_team.oauth_account_manager.util.MinecraftExecutor;
import info.u_team.oauth_account_manager.util.AuthenticationUtil.MinecraftAccountData;
import info.u_team.u_team_core.gui.elements.UButton;
import info.u_team.u_team_core.screen.UScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.client.gui.layouts.EqualSpacingLayout;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class AccountUseScreen extends UScreen {
	
	private final Screen lastScreen;
	
	private final GameProfile gameProfile;
	private final AccountDataCreator accountDataSupplier;
	
	private MultiLineTextWidget messageWidget;
	private PlayerIconWidget playerIconWidget;
	
	public AccountUseScreen(Screen lastScreen, GameProfile gameProfile, AccountDataCreator accountDataSupplier) {
		super(Component.translatable(OAuthAccountManagerLocalization.SCREEN_USE_ACCOUNT_TITLE));
		this.lastScreen = lastScreen;
		this.gameProfile = gameProfile;
		this.accountDataSupplier = accountDataSupplier;
	}
	
	@Override
	protected void init() {
		super.init();
		
		messageWidget = addRenderableWidget(new MultiLineTextWidget(0, (height / 2) - 60, CommonComponents.EMPTY, font).setMaxWidth(width - 50).setCentered(true));
		setInformationMessage(Component.translatable(OAuthAccountManagerLocalization.SCREEN_USE_ACCOUNT_MESSAGE));
		
		playerIconWidget = addRenderableWidget(new PlayerIconWidget(width / 2 - 32, height / 2 - 32, 64, gameProfile));
		
		final UButton doneButton = addRenderableWidget(new UButton(0, 0, 100, 20, CommonComponents.GUI_DONE));
		doneButton.setPressable(this::useAccount);
		
		final UButton cancelButton = addRenderableWidget(new UButton(0, 0, 100, 20, CommonComponents.GUI_CANCEL));
		cancelButton.setPressable(() -> minecraft.setScreen(lastScreen));
		
		final EqualSpacingLayout layout = new EqualSpacingLayout(205, 20, EqualSpacingLayout.Orientation.HORIZONTAL);
		layout.addChild(doneButton);
		layout.addChild(cancelButton);
		layout.arrangeElements();
		
		FrameLayout.centerInRectangle(layout, 0, height - 64, width, 64);
	}
	
	@Override
	public void renderBefore(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		super.renderBefore(guiGraphics, mouseX, mouseY, partialTick);
		
		guiGraphics.drawCenteredString(font, title, width / 2, 20, 0xFFFFFF);
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
	
	private void useAccount() {
		MinecraftExecutor.executeOnMainThread(() -> setInformationMessage(Component.translatable(OAuthAccountManagerLocalization.SCREEN_USE_ACCOUNT_MESSAGE_WAITING)));
		CompletableFuture.runAsync(() -> {
			try {
				final MinecraftAccountData data = accountDataSupplier.create();
				MinecraftExecutor.executeOnMainThread(() -> {
					AuthenticationUtil.setMinecraftAccountData(data);
					Minecraft.getInstance().setScreen(lastScreen);
				});
			} catch (final AuthenticationException ex) {
				MinecraftExecutor.executeOnMainThread(() -> setInformationMessage(Component.translatable(OAuthAccountManagerLocalization.SCREEN_USE_ACCOUNT_MESSAGE_ERROR)));
			}
		});
	}
	
	@FunctionalInterface
	public static interface AccountDataCreator {
		
		MinecraftAccountData create() throws AuthenticationException;
	}
}
