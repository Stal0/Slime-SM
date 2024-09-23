package com.stalixo.epifania.gui;

import com.stalixo.epifania.capability.mobCapability.MobAttributesProvider;
import com.stalixo.epifania.util.MobStarRating;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;

public class AttributesPlayerScreen extends Screen {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("epifania", "textures/gui/attributes_gui.png");

    // Campos para selecionar nível e raridade
    private EditBox mobLevelField;
    private EditBox mobRarityField;
    private Button saveButton;

    // Construtor da tela
    public AttributesPlayerScreen(Component title) {
        super(title);
    }

    @Override
    protected void init() {
        super.init();

        // Inicializar a fonte
        if (this.minecraft != null) {
            this.font = this.minecraft.font;
        }

        // Posições centrais para a tela
        int centerX = (this.width - 176) / 2;
        int centerY = (this.height - 100) / 2;

        // Inicializa o botão de salvar e define a ação de clique
        saveButton = Button.builder(Component.literal("Salvar"), button -> this.saveSettings())
                .pos(centerX + 40, centerY + 100)
                .size(100, 20)
                .build();

        // Adiciona o botão à lista de widgets interativos
        this.addRenderableWidget(saveButton);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        // Verificar se a fonte foi inicializada corretamente
        if (this.font == null && this.minecraft != null) {
            this.font = this.minecraft.font;
        }

        // Calcular a posição central da GUI
        int centerX = (this.width - 176) / 2;
        int centerY = (this.height - 166) / 2;

        // Renderiza o fundo da GUI (a textura)
        guiGraphics.blit(BACKGROUND_TEXTURE, centerX, centerY, 0, 0, 176, 166);

        // Renderiza os campos de texto e o botão
        super.render(guiGraphics, mouseX, mouseY, partialTicks);

        // Renderizar o texto dos campos (Nível e Raridade)
        if (this.font != null) {
            guiGraphics.drawString(this.font, "Nível do Mob:", centerX + 10, centerY + 10, 0xFFFFFF);
            guiGraphics.drawString(this.font, "Raridade do Mob:", centerX + 10, centerY + 50, 0xFFFFFF);
        }

        // Renderizar os widgets (campos de texto e botões)
        mobLevelField.render(guiGraphics, mouseX, mouseY, partialTicks);
        mobRarityField.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    // Método para salvar as configurações
    private void saveSettings() {
        String mobLevel = mobLevelField.getValue();
        String mobRarity = mobRarityField.getValue();

       /* try {
            int level = Integer.parseInt(mobLevel);
            int rarity = Integer.parseInt(mobRarity);

            // Validar os valores
            if (level <= 0) {
                this.minecraft.player.sendSystemMessage(Component.literal("O nível do mob deve ser positivo."));
                return;
            }

            if (rarity <= 0) {
                this.minecraft.player.sendSystemMessage(Component.literal("Por favor, insira uma raridade válida para o mob."));
                return;
            }

            // Aplicar as informações diretamente à entidade
            if (mob != null) {
                mob.getCapability(MobAttributesProvider.MOB_ATTRIBUTES).ifPresent(mobAttributes -> {
                    mobAttributes.setMobLevel(level);
                    mobAttributes.setRarity(rarity);

                    MobStarRating.applyStarRating(mob, level);
                });

                this.minecraft.player.sendSystemMessage(Component.literal("Configurações do mob aplicadas com sucesso!"));
                this.minecraft.setScreen(null); // Fechar a tela após salvar
            }
        } catch (NumberFormatException e) {
            this.minecraft.player.sendSystemMessage(Component.literal("Por favor, insira um número válido para o nível do mob."));
        } */
    }
}
