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
import dev.upcraft.materials.base.item.IngredientItem;
import io.github.glasspane.mesh.api.annotation.AutoRegistry;
import net.minecraft.item.Item;

@SuppressWarnings("unused")
@AutoRegistry.Register(value = Item.class, modid = BasicMaterials.MODID, registry = "item")
public class MaterialItems {

    // ingots
    public static final Item ALUMINUM_INGOT = new IngredientItem();
    public static final Item COPPER_INGOT = new IngredientItem();
    public static final Item LEAD_INGOT = new IngredientItem();
    public static final Item NICKEL_INGOT = new IngredientItem();
    public static final Item PLATINUM_INGOT = new IngredientItem();
    public static final Item SILVER_INGOT = new IngredientItem();
    public static final Item TIN_INGOT = new IngredientItem();
    public static final Item ZINC_INGOT = new IngredientItem();

    // alloys / misc
    public static final Item CINNABAR_CRYSTAL = new IngredientItem();
    public static final Item BRASS_INGOT = new IngredientItem();
    public static final Item BRONZE_INGOT = new IngredientItem();
    public static final Item ELECTRUM_INGOT = new IngredientItem();
    public static final Item INVAR_INGOT = new IngredientItem();

    // dusts
    public static final Item CINNABAR_DUST = new IngredientItem();
    public static final Item LAPIS_LAZULI_DUST = new IngredientItem();
}
