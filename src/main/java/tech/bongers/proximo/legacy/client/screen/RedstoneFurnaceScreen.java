/*
 *     Copyright © 2020 bongers-tech
 *     This file is part of Proximo.
 *
 *     Proximo is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Proximo is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Proximo. If not, see <http://www.gnu.org/licenses/>.
 */
package tech.bongers.proximo.legacy.client.screen;

public class RedstoneFurnaceScreen /*extends ContainerScreen<RedstoneFurnaceContainer>*/ {

/*    private static final ResourceLocation TEXTURE = new ResourceLocation(Proximo.MOD_ID, PATH_GUI + REDSTONE_FURNACE + ".png");

    public RedstoneFurnaceScreen(final RedstoneFurnaceContainer container, final PlayerInventory inv, final ITextComponent titleIn) {
        super(container, inv, titleIn);
    }

    @Override
    public void render(final MatrixStack stack, final int mouseX, final int mouseY, final float partialTicks) {
        this.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        this.func_230459_a_(stack, mouseX, mouseY); // @mcp: func_230459_a_ = renderHoveredToolTip
    }

    @Override
    public void drawGuiContainerBackgroundLayer(final MatrixStack stack, final float partialTicks, final int mouseX, final int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        getMinecraft().getTextureManager().bindTexture(TEXTURE);
        blit(stack, guiLeft, guiTop, 0, 0, xSize, ySize);

        //Fire
        int remaining = container.getBurnLeftScaled();
        blit(stack, guiLeft + 56, guiTop + 36 + 13 - remaining, 176, 13 - remaining, 14, remaining + 1);

        //Progression arrow
        int progression = container.getSmeltProgressionScaled();
        blit(stack, guiLeft + 79, guiTop + 34, 176, 14, progression + 1, 16);
    }*/
}
