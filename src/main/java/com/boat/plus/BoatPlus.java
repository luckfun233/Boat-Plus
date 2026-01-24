package com.boat.plus;

import com.boat.plus.enchantment.ConceptGodEnchantment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries; // 新增导入
import net.minecraft.registry.Registry; // 新增导入
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BoatPlus implements ModInitializer {
	public static final String MOD_ID = "boat-plus";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// 注册概念神附魔
	public static final ConceptGodEnchantment CONCEPT_GOD = new ConceptGodEnchantment();

	@Override
	public void onInitialize() {
		// 3. 修复注册方式：Fabric 1.20.1使用Registry.register()方法
		Registry.register(Registries.ENCHANTMENT, new Identifier(MOD_ID, "concept_god"), CONCEPT_GOD);

		// 拦截玩家攻击事件
		AttackEntityCallback.EVENT.register(this::onAttackEntity);

		LOGGER.info("概念神附魔已加载！剑附魔后可秒杀一切实体。");
	}

	private ActionResult onAttackEntity(
			PlayerEntity player,
			net.minecraft.world.World world,
			net.minecraft.util.Hand hand,
			Entity target,
			net.minecraft.util.hit.HitResult hitResult
	) {
		// 检查主手物品是否附魔了概念神
		if (EnchantmentHelper.getLevel(CONCEPT_GOD, player.getMainHandStack()) > 0) {
			// 核心秒杀逻辑
			if (target instanceof LivingEntity living) {
				living.setHealth(0); // 强制归零血量（无视一切保护）
			} else {
				target.discard(); // 非生物实体直接移除（如TNT）
			}
			return ActionResult.SUCCESS; // 阻止原版伤害计算
		}
		return ActionResult.PASS;
	}
}
