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
package dev.upcraft.materials.base;

import dev.upcraft.materials.base.gen.MaterialWorldgenFeatures;
import dev.upcraft.materials.base.config.MaterialConfig;
import dev.upcraft.materials.base.config.MetalsOreConfig;
import dev.upcraft.materials.base.init.MaterialBlocks;
import io.github.glasspane.mesh.api.annotation.CalledByReflection;
import io.github.glasspane.mesh.api.logging.MeshLoggerFactory;
import io.github.glasspane.mesh.api.util.config.ConfigHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Logger;

@CalledByReflection
public class BasicMaterials implements ModInitializer {

    public static final String MODID = "basic_materials";
    private static final Logger logger = MeshLoggerFactory.createLogger("BasicMaterials");
    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(MODID, "items"), () -> new ItemStack(MaterialBlocks.ALUMINUM_BLOCK));

    public static Identifier id(String path) {
        return new Identifier(MODID, path);
    }

    @Override
    public void onInitialize() {
        ConfigHandler.registerConfig(MODID, "materials/base", MaterialConfig.class);
        MetalsOreConfig.init();
        MaterialWorldgenFeatures.init();
    }

    public static Logger getLogger() {
        return logger;
    }
}
