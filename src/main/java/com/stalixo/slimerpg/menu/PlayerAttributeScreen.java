package com.stalixo.slimerpg.menu;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class PlayerAttributeScreen extends AbstractContainerScreen<PlayerAttributeMenu> {

    public PlayerAttributeScreen(PlayerAttributeMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 256;
        this.imageHeight = 180;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        // Renderizando um fundo simples usando o método fill
        guiGraphics.fill(this.leftPos, this.topPos, this.leftPos + this.imageWidth, this.topPos + this.imageHeight, 0xFF202020); // Fundo cinza escuro
        guiGraphics.fill(this.leftPos, this.topPos, this.leftPos + this.imageWidth, this.topPos + 2, 0xFFFFFFFF); // Borda superior branca
        guiGraphics.fill(this.leftPos, this.topPos + this.imageHeight - 2, this.leftPos + this.imageWidth, this.topPos + this.imageHeight, 0xFFFFFFFF); // Borda inferior
        guiGraphics.fill(this.leftPos, this.topPos, this.leftPos + 2, this.topPos + this.imageHeight, 0xFFFFFFFF); // Borda esquerda
        guiGraphics.fill(this.leftPos + this.imageWidth - 2, this.topPos, this.leftPos + this.imageWidth, this.topPos + this.imageHeight, 0xFFFFFFFF); // Borda direita
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics); // Renderiza o fundo padrão
        super.render(guiGraphics, mouseX, mouseY, partialTicks);

        // Usando os dados do PlayerAttributeMenu em vez do LocalPlayer
        guiGraphics.drawString(this.font, "Strength: " + this.menu.getStrength() + "!!!", this.leftPos + 10, this.topPos + 10, 0xFFFFFF);
        guiGraphics.drawString(this.font, "Intelligence: " + this.menu.getIntelligence() + "!!!", this.leftPos + 10, this.topPos + 30, 0xFFFFFF);
        guiGraphics.drawString(this.font, "Points Available: " + this.menu.getAttributePoints() + "!!!", this.leftPos + 10, this.topPos + 50, 0xFFFFFF);

        this.renderTooltip(guiGraphics, mouseX, mouseY); // Renderiza as dicas de ferramenta, se necessário
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
