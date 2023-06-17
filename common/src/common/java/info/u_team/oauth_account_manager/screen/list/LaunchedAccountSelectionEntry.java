package info.u_team.oauth_account_manager.screen.list;

import info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization;
import info.u_team.oauth_account_manager.screen.AccountUseScreen;
import info.u_team.oauth_account_manager.util.AuthenticationUtil.MinecraftAccountData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class LaunchedAccountSelectionEntry extends AbstractAccountSelectionEntry {
	
	private static final MinecraftAccountData LAUNCHED_DATA;
	
	static {
		final Minecraft minecraft = Minecraft.getInstance();
		LAUNCHED_DATA = new MinecraftAccountData(minecraft.userApiService, minecraft.user, minecraft.playerSocialManager, minecraft.telemetryManager, minecraft.profileKeyPairManager, minecraft.reportingContext, minecraft.profileProperties);
	}
	
	public LaunchedAccountSelectionEntry(Screen ourScreen, AccountSelectionList selectionList) {
		super(ourScreen, selectionList, LAUNCHED_DATA.user().getProfileId(), LAUNCHED_DATA.user().getGameProfile());
	}
	
	@Override
	public void render(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean hovered, float partialTick) {
		super.render(guiGraphics, index, top, left, width, height, mouseX, mouseY, hovered, partialTick);
		if (hovered && currentlyUsed) {
			ourScreen.setTooltipForNextRenderPass(minecraft.font.split(Component.translatable(OAuthAccountManagerLocalization.SCREEN_ACCOUNTS_LIST_LAUNCHED_TOOLTIP, Component.translatable(OAuthAccountManagerLocalization.SCREEN_ACCOUNTS_LIST_USED_TOOLTIP)), 175));
		}
	}
	
	@Override
	protected void useEntry() {
		final AccountUseScreen useScreen = new AccountUseScreen(ourScreen, getProfile(), () -> LAUNCHED_DATA);
		minecraft.setScreen(useScreen);
	}
	
	@Override
	protected MutableComponent getTitleName() {
		return Component.translatable(OAuthAccountManagerLocalization.SCREEN_ACCOUNTS_LIST_LAUNCHED_TITLE, super.getTitleName()).withStyle(style -> style.withColor(0xCCCCCC).withItalic(true));
	}
	
}
