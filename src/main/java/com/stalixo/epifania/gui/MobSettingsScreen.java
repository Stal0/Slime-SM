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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MobSettingsScreen extends Screen {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("epifania", "textures/gui/attributes_gui.png");

    // Campos para selecionar nível e raridade
    private EditBox mobLevelField;
    private Button mainButton;
    private Button saveButton;
    private Mob mob;

    private List<String> options = new ArrayList<>();
    private String selectedOption;
    private boolean isExpanded = false; // Estado de expansão do menu suspenso

    // Construtor da tela
    public MobSettingsScreen(Component title, Mob mob) {
        super(title);
        this.mob = mob;
        options = Arrays.asList("Comum", "Incomum", "Raro", "Épico", "Lendário", "Mítico");
        selectedOption = options.get(0); // Definindo o valor padrão
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
        int centerY = (this.height - 166) / 2;

        // Criação dos campos interativos para o nível e raridade
        mobLevelField = new EditBox(this.font, centerX + 10, centerY + 20, 150, 20, Component.literal("Mob Level"));
        this.addRenderableWidget(mobLevelField);

        // Botão principal para exibir a opção selecionada
        mainButton = Button.builder(Component.literal(selectedOption), button -> toggleDropdown())
                .pos(centerX + 10, centerY + 60)
                .size(150, 20)
                .build();
        this.addRenderableWidget(mainButton);

        // Botão de salvar
        saveButton = Button.builder(Component.literal("Salvar"), button -> this.saveSettings())
                .pos(centerX + 40, centerY + 100)
                .size(100, 20)
                .build();
        this.addRenderableWidget(saveButton);
    }

    // Alterna o estado do drop-down (expandido ou fechado)
    private void toggleDropdown() {
        isExpanded = !isExpanded;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        // Renderiza o fundo da GUI
        int centerX = (this.width - 176) / 2;
        int centerY = (this.height - 166) / 2;
        guiGraphics.blit(BACKGROUND_TEXTURE, centerX, centerY, 0, 0, 176, 166);

        // Renderiza os campos de texto e botões
        super.render(guiGraphics, mouseX, mouseY, partialTicks);

        // Renderiza o texto dos campos
        if (this.font != null) {
            guiGraphics.drawString(this.font, "Nível do Mob:", centerX + 10, centerY + 10, 0xFFFFFF);
            guiGraphics.drawString(this.font, "Raridade do Mob:", centerX + 10, centerY + 50, 0xFFFFFF);
        }

        // Renderiza a lista de opções quando o drop-down está expandido
        if (isExpanded) {
            int startY = centerY + 80; // Posição inicial para as opções
            int optionWidth = 150;
            int optionHeight = 20;

            // Desenha um fundo opaco para a lista suspensa (usando 0xCC000000 para preto semi-transparente)
            guiGraphics.fill(centerX + 10, startY - 5, centerX + 10 + optionWidth, startY + options.size() * optionHeight + 5, 0xCC000000); // Fundo opaco

            for (int i = 0; i < options.size(); i++) {
                String option = options.get(i);
                int optionY = startY + i * optionHeight;

                // Verifica se o mouse está sobre a opção
                boolean isHovered = mouseX >= centerX + 10 && mouseX <= centerX + 10 + optionWidth && mouseY >= optionY && mouseY <= optionY + optionHeight;

                // Desenha a opção com uma cor de fundo diferente se estiver sendo destacada
                if (isHovered) {
                    guiGraphics.fill(centerX + 10, optionY, centerX + 10 + optionWidth, optionY + optionHeight, 0xFF1A1A8F ); // Cor de destaque
                }

                // Centraliza o texto da opção
                int textWidth = this.font.width(option);
                int textX = centerX + 10 + (optionWidth - textWidth) / 2; // Centraliza horizontalmente
                int textY = optionY + (optionHeight - this.font.lineHeight) / 2; // Centraliza verticalmente

                guiGraphics.drawString(this.font, option, textX, textY, 0xFFFFFF); // Texto branco
            }
        }

        // Renderiza os widgets (campos de texto e botões)
        mobLevelField.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    // Método para capturar cliques do mouse e selecionar opções
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isExpanded) {
            // Verifica se o jogador clicou em uma das opções do drop-down
            int centerX = (this.width - 176) / 2;
            int centerY = (this.height - 166) / 2;
            int startY = centerY + 80;
            int optionWidth = 150;
            int optionHeight = 20;

            for (int i = 0; i < options.size(); i++) {
                int optionY = startY + i * optionHeight;
                if (mouseX >= centerX + 10 && mouseX <= centerX + 10 + optionWidth && mouseY >= optionY && mouseY <= optionY + optionHeight) {
                    this.selectOption(options.get(i)); // Seleciona a opção
                    return true;
                }
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    // Método para selecionar uma opção do drop-down
    private void selectOption(String option) {
        this.selectedOption = option; // Define a nova opção selecionada
        this.mainButton.setMessage(Component.literal(selectedOption)); // Atualiza o botão principal
        this.isExpanded = false; // Fecha o drop-down
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    // Método para salvar as configurações
    private void saveSettings() {
        String mobLevel = mobLevelField.getValue();

        try {
            int level = Integer.parseInt(mobLevel);
            int rarity = selectRarityOption(selectedOption);
            // Validação dos valores
            if (level <= 0) {
                this.minecraft.player.sendSystemMessage(Component.literal("O nível do mob deve ser positivo."));
                return;
            }

            // Aplicar as informações ao mob
            if (mob != null) {
                mob.getCapability(MobAttributesProvider.MOB_ATTRIBUTES).ifPresent(mobAttributes -> {
                    mobAttributes.setMobLevel(level);
                    mobAttributes.setRarity(rarity);

                    MobStarRating.applyStarRating(mob, level);
                });

                this.minecraft.player.sendSystemMessage(Component.literal("Configurações do mob aplicadas com sucesso!"));
                this.minecraft.setScreen(null); // Fecha a tela após salvar
            }
        } catch (NumberFormatException e) {
            this.minecraft.player.sendSystemMessage(Component.literal("Por favor, insira um número válido para o nível do mob."));
        }
    }

    // Converte a string de raridade para um valor numérico
    private int selectRarityOption(String rarity) {
        switch (rarity) {
            case "Comum":
                return 1;
            case "Incomum":
                return 2;
            case "Raro":
                return 3;
            case "Épico":
                return 4;
            case "Lendário":
                return 5;
            case "Mítico":
                return 6;
            default:
                return 1;
        }
    }
}
