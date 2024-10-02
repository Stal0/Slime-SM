package com.stalixo.epifania.item.custom;

import com.stalixo.epifania.capability.mobCapability.MobAttributesProvider;
import com.stalixo.epifania.gui.MobSettingsScreen;
import com.stalixo.epifania.util.MobStarRating;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PencilMobEditorItem extends Item {

    public PencilMobEditorItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
        if (pInteractionTarget instanceof Mob mob) {

            if (pPlayer.level().isClientSide) {
                Minecraft.getInstance().setScreen(new MobSettingsScreen(Component.literal("Configurar Mob"), mob));
            }
            return InteractionResult.SUCCESS;
        }
        pPlayer.sendSystemMessage(Component.literal("Mob not found!"));
        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.epifania.pencil.tooltip"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
