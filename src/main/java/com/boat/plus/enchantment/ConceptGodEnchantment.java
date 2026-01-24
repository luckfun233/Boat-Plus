package com.boat.plus.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem; // 新增导入

public class ConceptGodEnchantment extends Enchantment {
    // 1. 将构造函数改为 public (解决访问权限问题)
    public ConceptGodEnchantment() {
        super(
                Rarity.VERY_RARE,
                EnchantmentTarget.WEAPON,
                new EquipmentSlot[]{EquipmentSlot.MAINHAND}
        );
    }

    @Override
    public int getMaxLevel() {
        return 1; // 仅限等级 I
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return false; // 商人不可出售
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return false; // 附魔台不可获得（避免平衡性问题）
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        // 2. 修复API变更：1.20.1中不再有isEffectiveOn方法
        // 直接检查是否为剑类物品
        return stack.getItem() instanceof SwordItem;
    }
}
