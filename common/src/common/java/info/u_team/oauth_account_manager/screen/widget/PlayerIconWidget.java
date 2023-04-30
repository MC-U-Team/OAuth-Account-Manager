package info.u_team.oauth_account_manager.screen.widget;

import java.util.List;
import java.util.Optional;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import info.u_team.u_team_core.api.gui.PerspectiveRenderable;
import info.u_team.u_team_core.api.gui.TooltipRenderable;
import info.u_team.u_team_core.util.WidgetUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.PlayerFaceRenderer;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class PlayerIconWidget extends AbstractWidget implements PerspectiveRenderable, TooltipRenderable {
	
	private final Minecraft minecraft = Minecraft.getInstance();
	
	private GameProfile profile;
	
	public PlayerIconWidget(int x, int y, int size, GameProfile profile) {
		super(x, y, size, size, CommonComponents.EMPTY);
		this.profile = profile;
	}
	
	public void setProfile(GameProfile profile) {
		this.profile = profile;
	}
	
	public GameProfile getProfile() {
		return profile;
	}
	
	@Override
	public void renderWidget(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
		if (profile != null) {
			renderBackground(poseStack, mouseX, mouseY, partialTick);
			renderForeground(poseStack, mouseX, mouseY, partialTick);
			WidgetUtil.renderCustomTooltipForWidget(this, poseStack, mouseX, mouseY, partialTick);
		}
	}
	
	@Override
	public void renderBackground(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
		final ResourceLocation skin = minecraft.getSkinManager().getInsecureSkinLocation(profile);
		
		RenderSystem.setShaderTexture(0, skin);
		PlayerFaceRenderer.draw(poseStack, getX(), getY(), getWidth(), false, false);
	}
	
	@Override
	public void renderForeground(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
	}
	
	@Override
	public void renderTooltip(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
		if (isHovered) {
			minecraft.screen.renderTooltip(poseStack, List.of(Component.literal(profile.getName())), Optional.empty(), mouseX, mouseY);
		}
	}
	
	@Override
	protected void updateWidgetNarration(NarrationElementOutput element) {
		defaultButtonNarrationText(element);
	}
	
	@Override
	public void playDownSound(SoundManager pHandler) {
		// Don't play click sound
	}
	
}
