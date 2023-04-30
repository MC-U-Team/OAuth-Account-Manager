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
