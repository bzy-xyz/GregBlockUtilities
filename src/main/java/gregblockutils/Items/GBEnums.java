package gregblockutils.Items;

import gregtech.api.GTValues;
import gregtech.api.unification.material.MaterialIconType;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.Condition;
import net.minecraftforge.common.util.EnumHelper;

public class GBEnums {
    public static void preInit() {
        EnumHelper.addEnum(MaterialIconType.class, "oreChunk", new Class[0]);
        EnumHelper.addEnum(MaterialIconType.class, "oreEnderChunk", new Class[0]);
        EnumHelper.addEnum(MaterialIconType.class, "oreNetherChunk", new Class[0]);
        EnumHelper.addEnum(MaterialIconType.class, "oreFineChunk", new Class[0]);
        EnumHelper.addEnum(MaterialIconType.class, "plateDouble", new Class[0]);

        EnumHelper.addEnum(OrePrefix.class, "oreChunk",
                new Class[]{String.class, long.class, Material.class, MaterialIconType.class, long.class, Condition.class},
                "Ore Chunk", -1L, null, MaterialIconType.valueOf("oreChunk"), OrePrefix.Flags.ENABLE_UNIFICATION | OrePrefix.Flags.DISALLOW_RECYCLING,
                OrePrefix.and(instanceOfDustMat(), OrePrefix.hasFlag(DustMaterial.MatFlags.GENERATE_ORE)));
        EnumHelper.addEnum(OrePrefix.class, "oreEnderChunk",
                new Class[]{String.class, long.class, Material.class, MaterialIconType.class, long.class, Condition.class},
                "Ender Ore Chunk", -1L, null, MaterialIconType.valueOf("oreEnderChunk"), OrePrefix.Flags.ENABLE_UNIFICATION | OrePrefix.Flags.DISALLOW_RECYCLING,
                OrePrefix.and(instanceOfDustMat(), OrePrefix.hasFlag(DustMaterial.MatFlags.GENERATE_ORE)));
        EnumHelper.addEnum(OrePrefix.class, "oreNetherChunk",
                new Class[]{String.class, long.class, Material.class, MaterialIconType.class, long.class, Condition.class},
                "Nether Ore Chunk", -1L, null, MaterialIconType.valueOf("oreNetherChunk"), OrePrefix.Flags.ENABLE_UNIFICATION | OrePrefix.Flags.DISALLOW_RECYCLING,
                OrePrefix.and(instanceOfDustMat(), OrePrefix.hasFlag(DustMaterial.MatFlags.GENERATE_ORE)));
        EnumHelper.addEnum(OrePrefix.class, "oreFineChunk",
                new Class[]{String.class, long.class, Material.class, MaterialIconType.class, long.class, Condition.class},
                "Fine Ore Chunk", -1L, null, MaterialIconType.valueOf("oreFineChunk"), OrePrefix.Flags.ENABLE_UNIFICATION | OrePrefix.Flags.DISALLOW_RECYCLING,
                OrePrefix.and(instanceOfDustMat(), OrePrefix.hasFlag(DustMaterial.MatFlags.GENERATE_ORE)));
        EnumHelper.addEnum(OrePrefix.class, "plateDouble",
                new Class[]{String.class, long.class, Material.class, MaterialIconType.class, long.class, Condition.class},
                "Double Plate", -1L, null, MaterialIconType.valueOf("plateDouble"), OrePrefix.Flags.ENABLE_UNIFICATION | OrePrefix.Flags.DISALLOW_RECYCLING,
                OrePrefix.and(instanceOfDustMat(), OrePrefix.hasFlag(IngotMaterial.MatFlags.GENERATE_ROTOR)));
    }
    public static Condition<Material> instanceOfDustMat() {
        return (mat) -> {
            return mat instanceof DustMaterial;
        };
    }
}
