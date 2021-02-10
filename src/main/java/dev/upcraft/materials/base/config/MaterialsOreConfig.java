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
package dev.upcraft.materials.base.config;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.JsonPrimitive;
import blue.endless.jankson.api.SyntaxError;
import dev.upcraft.materials.api.GenerationData;
import dev.upcraft.materials.base.BasicMaterials;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class MaterialsOreConfig {

    private static final Path configFile = FabricLoader.getInstance().getConfigDir().resolve("materials/worldgen.json5");
    private static final Jankson SERIALIZER = Jankson.builder().build();
    private static final Map<Block, GenerationData> GENERATION_DATA = new HashMap<>();

    public static GenerationData get(Block block, Identifier id, Function<Block, GenerationData> defaultValue) {
        if (!GENERATION_DATA.containsKey(block)) {
            synchronized (GENERATION_DATA) {
                GENERATION_DATA.put(block, defaultValue.apply(block));
            }
            saveAll();
        }
        return GENERATION_DATA.get(block);
    }

    private static void saveAll() {
        JsonObject config = read();
        save(config);
        parseConfig(config);
    }

    public static void init() {
        final Identifier reloaderID = BasicMaterials.id("config");
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return reloaderID;
            }

            @Override
            public void apply(ResourceManager manager) {
                BasicMaterials.getLogger().debug("reloading worldgen config");
                parseConfig(read());
            }
        });
    }

    public static JsonObject createDefaultConfig() {
        JsonObject root = new JsonObject();
        synchronized (GENERATION_DATA) {
            GENERATION_DATA.forEach((block, genData) -> {
                Identifier id = Registry.BLOCK.getId(block);
                JsonObject data = new JsonObject();
                data.put("generation_disabled", new JsonPrimitive(genData.isDisabled()));
                root.put(id.toString(), data);
            });
        }
        return root;
    }

    public static void parseConfig(JsonObject root) {
        synchronized (GENERATION_DATA) {
            GENERATION_DATA.replaceAll((block, genData) -> {
                Identifier id = Registry.BLOCK.getId(block);
                JsonObject current = root.getObject(id.toString());
                if (current != null) {
                    boolean disabled = current.getBoolean("generation_disabled", genData.isDisabled());
                    // TODO actually read values

                    // copy old object and set new values
                    return new GenData(genData.getBottomOffset(), genData.getTopOffset(), genData.getMaximum(), genData.getMaxVeinSize(), genData.getWeight(), disabled, genData.getBiomeSelection(), genData.getGenerationRules());
                }
                return genData;
            });
        }
    }

    public static JsonObject read() {
        JsonObject defaultConfig = createDefaultConfig();
        if (Files.exists(configFile)) {
            try (InputStream input = Files.newInputStream(configFile)) {
                JsonObject deserialized = SERIALIZER.load(input);
                defaultConfig.forEach((key, value) -> deserialized.merge(key, value, (oldValue, newValue) -> oldValue)); // TODO recursive merge
                return deserialized;
            } catch (IOException | SyntaxError e) {
                BasicMaterials.getLogger().error("unable to read ore config", e);
                return defaultConfig;
            }
        } else {
            return defaultConfig;
        }
    }

    public static void save(JsonObject toSave) {
        try {
            Files.createDirectories(configFile.getParent());
            Files.write(configFile, toSave.toJson(true, true).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            BasicMaterials.getLogger().error("unable to save config", e);
        }
    }

    public static class GenData implements GenerationData {

        private final int bottomOffset;
        private final int topOffset;
        private final int maximum;
        private final int maxVeinSize;
        private final int weight;
        private final boolean disabled;

        private final Predicate<BiomeSelectionContext> biomeSelection;
        private final RuleTest generationRules;

        public GenData(int bottomOffset, int topOffset, int maximum, int maxVeinSize, int weight, boolean disabled, Predicate<BiomeSelectionContext> biomeSelection, RuleTest generationRules) {
            this.bottomOffset = bottomOffset;
            this.topOffset = topOffset;
            this.maximum = maximum;
            this.maxVeinSize = maxVeinSize;
            this.weight = weight;
            this.disabled = disabled;
            this.biomeSelection = biomeSelection;
            this.generationRules = generationRules;
        }

        public GenData(int bottomOffset, int topOffset, int maximum, int maxVeinSize, int weight) {
            this(bottomOffset, topOffset, maximum, maxVeinSize, weight, false);
        }

        public GenData(int bottomOffset, int topOffset, int maximum, int maxVeinSize, int weight, boolean disabled) {
            this(bottomOffset, topOffset, maximum, maxVeinSize, weight, disabled, BiomeSelectors.foundInOverworld(), OreFeatureConfig.Rules.BASE_STONE_OVERWORLD);
        }

        @Override
        public int getBottomOffset() {
            return bottomOffset;
        }

        @Override
        public int getTopOffset() {
            return topOffset;
        }

        @Override
        public int getMaximum() {
            return maximum;
        }

        @Override
        public int getMaxVeinSize() {
            return maxVeinSize;
        }

        @Override
        public int getWeight() {
            return weight;
        }

        @Override
        public boolean isDisabled() {
            return disabled;
        }

        @Override
        public Predicate<BiomeSelectionContext> getBiomeSelection() {
            return biomeSelection;
        }

        @Override
        public RuleTest getGenerationRules() {
            return generationRules;
        }
    }
}
