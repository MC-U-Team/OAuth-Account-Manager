package info.u_team.oauth_account_manager.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization;
import info.u_team.u_team_core.gui.elements.UButton;
import info.u_team.u_team_core.screen.UScreen;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

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
		
		final LoadingSpinnerWidget spinner = addRenderableWidget(new LoadingSpinnerWidget(0, 0, 60, 60));
		spinner.setTooltip(Tooltip.create(Component.translatable(OAuthAccountManagerLocalization.SCREEN_ADD_ACCOUNT_OPEN_LINK_SPINNER_TOOLTIP)));
		FrameLayout.centerInRectangle(spinner, 0, 0, width, height);
		
		final UButton openLink = addRenderableWidget(new UButton(0, 0, 100, 20, Component.translatable("chat.link.open")));
		final UButton copyClipboard = addRenderableWidget(new UButton(0, 0, 100, 20, Component.translatable("chat.copy")));
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
		message.renderCentered(poseStack, this.width / 2, 60);
	}
	
	@Override
	public void onClose() {
		minecraft.setScreen(lastScreen);
	}
}