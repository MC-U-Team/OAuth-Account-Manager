package info.u_team.oauth_account_manager.screen.list;

import java.util.UUID;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.oauth_account_manager.util.MinecraftAccounts;
import info.u_team.u_team_core.gui.elements.ScrollableListEntry;
import net.minecraft.Util;
import net.minecraft.client.gui.components.PlayerFaceRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class AccountSelectionEntry extends ScrollableListEntry<AccountSelectionEntry> {
	
	private final AccountSelectionList selectionList;
	
	private final UUID uuid;
	private final GameProfile profile;
	
	private long lastClickTime;
	
	public AccountSelectionEntry(AccountSelectionList selectionList, UUID uuid) {
		this.selectionList = selectionList;
		this.uuid = uuid;
		profile = MinecraftAccounts.getGameProfile(uuid);
	}
	
	@Override
	public void render(PoseStack poseStack, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean hovered, float partialTick) {
		final ResourceLocation skin = minecraft.getSkinManager().getInsecureSkinLocation(profile);
		
		RenderSystem.setShaderTexture(0, skin);
		PlayerFaceRenderer.draw(poseStack, left, top, 32, false, false);
		
		minecraft.font.draw(poseStack, Component.literal(profile.getName()), left + 35, top + 1, 0xFFFFFF);
		minecraft.font.draw(poseStack, Component.literal(uuid.toString()), left + 35, top + 12, 0x808080);
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
	
}
