/*
 * basic-materials
 * Copyright (C) 2021-2021 UpcraftLP
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
package dev.upcraft.materials.base.gen;

import dev.hephaestus.fiblib.FibLib;
import dev.hephaestus.fiblib.blocks.BlockFib;
import dev.upcraft.materials.api.Generatable;
import dev.upcraft.materials.api.GenerationData;
import dev.upcraft.materials.base.BasicMaterials;
import dev.upcraft.materials.base.config.MaterialsOreConfig;
import dev.upcraft.materials.base.init.MaterialTags;
import io.github.glasspane.mesh.util.collections.RegistryHelper;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.advancement.Advancement;
import net.minecraft.block.Blocks;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.ConfiguredDecorator;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class MaterialWorldgenFeatures {

    public static final RuleTest RULE_SANDSTONE = new TagMatchRuleTest(MaterialTags.OVERWORLD_SANDSTONE);

    public static void init() {
        RegistryHelper.visitRegistry(Registry.BLOCK, (blockID, block) -> {
            if (block instanceof Generatable) {
                GenerationData defaultData = ((Generatable) block).getDefaultGenerationData();
                if (defaultData != null) { // only proceed if this ore should actually generate naturally
                    GenerationData data = MaterialsOreConfig.get(block, blockID, b -> defaultData);
                    OreFeatureConfig oreConfig = new OreFeatureConfig(data.getGenerationRules(), ((Generatable) block).getStateForGeneration(), data.getMaxVeinSize());

                    // y = randomInt(maximum - topOffset) + bottomOffset
                    RangeDecoratorConfig rangeConfig = new RangeDecoratorConfig(data.getBottomOffset(), data.getTopOffset(), data.getMaximum());

                    ConfiguredDecorator<?> decorator = Decorator.RANGE.configure(rangeConfig).spreadHorizontally();
                    if (data.getWeight() > 0) {
                        decorator = decorator.repeat(data.getWeight());
                    }

                    ConfiguredFeature<?, ?> feature = Feature.ORE.configure(oreConfig).decorate(decorator);
                    Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, BasicMaterials.id(blockID.toString().replace(':', '.')), feature);
                    RegistryKey<ConfiguredFeature<?, ?>> featureKey = BuiltinRegistries.CONFIGURED_FEATURE.getKey(feature).orElseThrow(() -> new IllegalStateException("Feature not registered!"));
                    if (!data.isDisabled()) {
                        BiomeModifications.addFeature(data.getBiomeSelection(), GenerationStep.Feature.UNDERGROUND_ORES, featureKey);
                    }
                }

                Identifier advancementID = new Identifier(blockID.getNamespace(), String.format("ore_visibility/%s", blockID.getPath()));
                FibLib.Blocks.register(new BlockFib(((Generatable) block).getStateForGeneration(), Blocks.STONE.getDefaultState(), player -> {
                    if(player.isCreativeLevelTwoOp()) { // don't hide from server operators
                        return false;
                    }
                    //noinspection ConstantConditions
                    Advancement adv = player.getServer().getAdvancementLoader().get(advancementID);
                    return adv != null && !player.getAdvancementTracker().getProgress(adv).isDone(); // return true if the block should be hidden
                }));
            }
        });
    }
}
