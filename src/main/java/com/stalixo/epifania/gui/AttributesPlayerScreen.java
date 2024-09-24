package com.stalixo.epifania.gui;

import com.stalixo.epifania.capability.playerCapability.PlayerAttributesProvider;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class AttributesPlayerScreen extends Screen {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("epifania", "textures/gui/attributes_gui.png");

    private Player player;
    private int playerLevel;
    private String playerXP; // Usando String para XP formatado
    private int xpForNextLevel;
    private int attributePoints;

    private Button closeButton;

    // Construtor da tela
    public AttributesPlayerScreen(Component title, Player player) {
        super(title);
        this.player = player;
    }

    @Override
    protected void init() {
        super.init();

        // Inicializa a fonte do Minecraft
        if (this.minecraft != null && this.minecraft.font != null) {
            this.font = this.minecraft.font;
        } else {
            // Verifica se a fonte está sendo inicializada corretamente
            throw new NullPointerException("A fonte não foi inicializada corretamente.");
        }

        // Posições centrais para a tela
        int centerX = (this.width - 176) / 2;
        int centerY = (this.height - 166) / 2;

        // Inicializa o botão de fechar
        closeButton = Button.builder(Component.literal("Fechar"), button -> this.onClose())
                .pos(centerX + 40, centerY + 120)
                .size(100, 20)
                .build();
        this.addRenderableWidget(closeButton);

        // Obtém os atributos do jogador a partir da Capability
        player.getCapability(PlayerAttributesProvider.PLAYER_ATTRIBUTES).ifPresent(attributes -> {
            this.playerLevel = attributes.getLevelPlayer();

            // Formata o XP com duas casas decimais diretamente
            this.playerXP = String.format("%.2f", attributes.getExperiencePoints());

            this.xpForNextLevel = attributes.getExperienceToNextLevel();
            this.attributePoints = attributes.getAttributePoints();
        });
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        // Verifica se a fonte está disponível
        if (this.font == null) {
            this.font = this.minecraft.font;
        }

        // Calcular a posição central da GUI
        int centerX = (this.width - 176) / 2;
        int centerY = (this.height - 166) / 2;

        // Renderiza o fundo da GUI (a textura)
        guiGraphics.blit(BACKGROUND_TEXTURE, centerX, centerY, 0, 0, 176, 220);

        // Renderiza os campos de texto e botões
        super.render(guiGraphics, mouseX, mouseY, partialTicks);

        // Renderiza os atributos do jogador
        if (this.font != null) {
            guiGraphics.drawString(this.font, "Nível do Jogador: " + playerLevel, centerX + 10, centerY + 20, 0xFFFFFF);
            guiGraphics.drawString(this.font, "XP Atual: " + playerXP, centerX + 10, centerY + 40, 0xFFFFFF);
            guiGraphics.drawString(this.font, "XP para próximo nível: " + xpForNextLevel, centerX + 10, centerY + 60, 0xFFFFFF);
            guiGraphics.drawString(this.font, "Pontos de Atributo Disponíveis: " + attributePoints, centerX + 10, centerY + 80, 0xFFFFFF);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    // Método para fechar a tela
    @Override
    public void onClose() {
        this.minecraft.setScreen(null); // Fecha a tela
    }
}
