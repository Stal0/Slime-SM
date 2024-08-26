package com.stalixo.slimerpg.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ObjectHolder;

public class PlayerAttributeMenu extends AbstractContainerMenu {

    private final int strength;
    private final int intelligence;
    private final int attributePoints;

    // Construtor que aceita dados do servidor via FriendlyByteBuf
    public PlayerAttributeMenu(int windowId, Inventory playerInventory, FriendlyByteBuf data) {
        super(ModMenuTypes.ATTRIBUTE_MENU.get(), windowId);

        // Lê os dados enviados pelo servidor
        this.strength = data.readInt();
        this.intelligence = data.readInt();
        this.attributePoints = data.readInt();
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;  // Define as condições para que o menu ainda seja válido
    }

    // Getters para os atributos
    public int getStrength() {
        return strength;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getAttributePoints() {
        return attributePoints;
    }
}
