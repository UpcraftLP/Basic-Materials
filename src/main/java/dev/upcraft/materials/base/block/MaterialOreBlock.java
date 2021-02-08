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
package dev.upcraft.materials.base.block;

import dev.upcraft.materials.base.BasicMaterials;
import dev.upcraft.materials.base.config.MetalsOreConfig;
import io.github.glasspane.mesh.api.registry.BlockItemProvider;
import net.minecraft.block.OreBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class MaterialOreBlock extends OreBlock implements BlockItemProvider {

    private MetalsOreConfig.GenData defaultGenerationData = null;

    /**
     * the minimum XP when mined
     */
    private final int minXP;

    /**
     * the maximum XP when mined
     */
    private final int maxXP;

    public MaterialOreBlock(Settings settings) {
        this(settings, 0, 0);
    }

    public MaterialOreBlock(Settings settings, int minXP, int maxXP) {
        super(settings);
        this.minXP = minXP;
        this.maxXP = maxXP;
    }

    @Override
    public Item createItem() {
        return new BlockItem(this, new Item.Settings().group(BasicMaterials.ITEM_GROUP));
    }

    @Override
    protected int getExperienceWhenMined(Random random) {
        return this.minXP > 0 ? MathHelper.nextInt(random, this.minXP, this.maxXP) : 0;
    }

    public MaterialOreBlock withDefaultConfig() {
        return this.withConfig(new MetalsOreConfig.GenData(32, 0, 32, 6, 0, true));
    }

    public MaterialOreBlock withConfig(MetalsOreConfig.GenData genData) {
        this.defaultGenerationData = genData;
        return this;
    }

    @Nullable
    public MetalsOreConfig.GenData getDefaultGenerationData() {
        return defaultGenerationData;
    }
}
