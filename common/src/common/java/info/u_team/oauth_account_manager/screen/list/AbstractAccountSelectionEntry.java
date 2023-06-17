package info.u_team.oauth_account_manager.screen.list;

import java.util.UUID;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.platform.InputConstants;

import info.u_team.u_team_core.gui.elements.ScrollableListEntry;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.PlayerFaceRenderer;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractAccountSelectionEntry extends ScrollableListEntry<AbstractAccountSelectionEntry> {
	
	protected final Screen ourScreen;
	private final AccountSelectionList selectionList;
	
	private final UUID uuid;
	private final GameProfile profile;
	
	private long lastClickTime;
	
	public AbstractAccountSelectionEntry(Screen ourScreen, AccountSelectionList selectionList, UUID uuid, GameProfile profile) {
		this.ourScreen = ourScreen;
		this.selectionList = selectionList;
		this.uuid = uuid;
		this.profile = profile;
	}
	
	@Override
	public void render(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean hovered, float partialTick) {
		final ResourceLocation skin = minecraft.getSkinManager().getInsecureSkinLocation(profile);
		
		PlayerFaceRenderer.draw(guiGraphics, skin, left, top, 32, false, false);
		
		guiGraphics.drawString(minecraft.font, Component.literal(profile.getName()), left + 35, top + 1, 0xFFFFFF, false);
		guiGraphics.drawString(minecraft.font, Component.literal(uuid.toString()), left + 35, top + 12, 0x808080, false);
	}
	
	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (keyCode == InputConstants.KEY_RETURN || keyCode == InputConstants.KEY_NUMPADENTER) {
			selectionList.useSelectedEntry();
		}
		return super.keyPressed(keyCode, scanCode, modifiers);
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (Util.getMillis() - lastClickTime < 250) {
			selectionList.useSelectedEntry();
			return true;
		}
		lastClickTime = Util.getMillis();
		return super.mouseClicked(mouseX, mouseY, button);
	}
	
	@Override
	public Component getNarration() {
		return Component.literal(profile.getName());
	}
	
	public UUID getUuid() {
		return uuid;
	}
	
	protected abstract void useEntry();
	
}
