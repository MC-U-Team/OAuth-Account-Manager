package info.u_team.oauth_account_manager.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import info.u_team.oauth_account_manager.OAuthAccountManagerReference;
import info.u_team.oauth_account_manager.init.OAuthAccountManagerLocalization;
import info.u_team.u_team_core.gui.elements.UButton;
import info.u_team.u_team_core.screen.UScreen;
import info.u_team.u_team_core.util.RGBA;
import info.u_team.u_team_core.util.RenderUtil;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

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
		
		message = MultiLineLabel.create(font, Component.translatable(OAuthAccountManagerLocalization.SCREEN_ADD_ACCOUNT_OPEN_LINK_MESSAGE), width - 50);
		
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
	
	private int count;
	
	@Override
	public void tick() {
		super.tick();
		count++;
	}
	
	int rotation = 0;
	
	@Override
	public void renderForeground(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
		super.renderForeground(poseStack, mouseX, mouseY, partialTick);
		
		drawCenteredString(poseStack, font, title, width / 2, 20, 0xFFFFFF);
		message.renderCentered(poseStack, this.width / 2, 60);
		
		poseStack.pushPose();
		
		poseStack.translate(25, 25, 0);
		
		if (count % 20 == 0) {
			rotation += 30;
		}
		
		poseStack.mulPose(Axis.ZP.rotationDegrees(rotation));
		poseStack.translate(-25, -25, 0);
		// poseStack.mulPose(Axis.YP.rotationDegrees(60));
		
		RenderUtil.drawTexturedQuad(poseStack, 0, 0 + 50, 0, 0 + 50, 0, 1, 0, 1, 0, new ResourceLocation(OAuthAccountManagerReference.MODID, "textures/gui/spinner.png"), RGBA.WHITE);
		
		// RenderSystem.setShader(GameRenderer::getPositionColorShader);
		// BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
		// bufferbuilder.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION);
		// bufferbuilder.vertex(poseStack.last().pose(), -0.5f, -0.5f, 0).color(0xFFFFFFFF).endVertex();
		// bufferbuilder.vertex(poseStack.last().pose(), 0.5f, -0.5f, 0).color(0xFFFFFFFF).endVertex();
		// bufferbuilder.vertex(poseStack.last().pose(), 0, 0.5f, 0).color(0xFFFFFFFF).endVertex();
		// BufferUploader.drawWithShader(bufferbuilder.end());
		
		poseStack.popPose();
	}
	
	@Override
	public void onClose() {
		minecraft.setScreen(lastScreen);
	}
}