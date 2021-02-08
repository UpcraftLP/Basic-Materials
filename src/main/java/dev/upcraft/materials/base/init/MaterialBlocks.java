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
package dev.upcraft.materials.base.init;

import dev.upcraft.materials.base.BasicMaterials;
import dev.upcraft.materials.base.block.MaterialBlock;
import dev.upcraft.materials.base.block.MaterialOreBlock;
import dev.upcraft.materials.base.config.MetalsOreConfig;
import dev.upcraft.materials.base.gen.MaterialWorldgenFeatures;
import io.github.glasspane.mesh.api.annotation.AutoRegistry;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.world.biome.Biome;

@AutoRegistry.Register(value = Block.class, modid = BasicMaterials.MODID, registry = "block")
public class MaterialBlocks {

    //ore blocks
    public static final Block BAUXITE_ORE = new MaterialOreBlock(Block.Settings.of(Material.STONE).strength(3.0F, 3.0F)).withDefaultConfig(); //Aluminum
    public static final Block CINNABAR_ORE = new MaterialOreBlock(Block.Settings.of(Material.STONE).strength(3.0F, 3.0F), 0, 2).withConfig(new MetalsOreConfig.GenData(0, 0, 100, 8, 0, false, BiomeSelectors.categories(Biome.Category.DESERT), MaterialWorldgenFeatures.RULE_SANDSTONE)); //Mercury
    public static final Block COPPER_ORE = new MaterialOreBlock(Block.Settings.of(Material.STONE).strength(3.0F, 3.0F)).withDefaultConfig();
    public static final Block LEAD_ORE = new MaterialOreBlock(Block.Settings.of(Material.STONE).strength(3.0F, 3.0F)).withDefaultConfig();
    public static final Block NICKEL_ORE = new MaterialOreBlock(Block.Settings.of(Material.STONE).strength(3.0F, 3.0F)).withDefaultConfig();
    public static final Block PLATINUM_ORE = new MaterialOreBlock(Block.Settings.of(Material.STONE).strength(3.0F, 3.0F)).withDefaultConfig();
    public static final Block SILVER_ORE = new MaterialOreBlock(Block.Settings.of(Material.STONE).strength(3.0F, 3.0F)).withConfig(new MetalsOreConfig.GenData(32, 0, 64, 6, 0));
    public static final Block TIN_ORE = new MaterialOreBlock(Block.Settings.of(Material.STONE).strength(3.0F, 3.0F)).withDefaultConfig();
    public static final Block ZINC_ORE = new MaterialOreBlock(Block.Settings.of(Material.STONE).strength(3.0F, 3.0F)).withDefaultConfig();

    //TODO material colors; different strengths
    //storage blocks
    public static final Block ALUMINUM_BLOCK = new MaterialBlock(FabricBlockSettings.of(Material.METAL, MaterialColor.IRON).strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL));
    public static final Block COPPER_BLOCK = new MaterialBlock(FabricBlockSettings.of(Material.METAL, MaterialColor.IRON).strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL));
    public static final Block LEAD_BLOCK = new MaterialBlock(FabricBlockSettings.of(Material.METAL, MaterialColor.IRON).strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL));
    public static final Block NICKEL_BLOCK = new MaterialBlock(FabricBlockSettings.of(Material.METAL, MaterialColor.IRON).strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL));
    public static final Block PLATINUM_BLOCK = new MaterialBlock(FabricBlockSettings.of(Material.METAL, MaterialColor.IRON).strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL));
    public static final Block SILVER_BLOCK = new MaterialBlock(FabricBlockSettings.of(Material.METAL, MaterialColor.IRON).strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL));
    public static final Block TIN_BLOCK = new MaterialBlock(FabricBlockSettings.of(Material.METAL, MaterialColor.IRON).strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL));
    public static final Block ZINC_BLOCK = new MaterialBlock(FabricBlockSettings.of(Material.METAL, MaterialColor.IRON).strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL));

    //alloy storage blocks
    public static final Block BRASS_BLOCK = new MaterialBlock(FabricBlockSettings.of(Material.METAL, MaterialColor.IRON).strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL)); //zinc + copper or aluminum + copper
    public static final Block BRONZE_BLOCK = new MaterialBlock(FabricBlockSettings.of(Material.METAL, MaterialColor.IRON).strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL));
    public static final Block ELECTRUM_BLOCK = new MaterialBlock(FabricBlockSettings.of(Material.METAL, MaterialColor.IRON).strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL));
    public static final Block INVAR_BLOCK = new MaterialBlock(FabricBlockSettings.of(Material.METAL, MaterialColor.IRON).strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL));
}
