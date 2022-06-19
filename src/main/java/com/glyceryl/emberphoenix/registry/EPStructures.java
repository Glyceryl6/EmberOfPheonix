package com.glyceryl.emberphoenix.registry;

import com.glyceryl.emberphoenix.EmberOfPhoenix;
import com.glyceryl.emberphoenix.RegistryBase;
import com.glyceryl.emberphoenix.common.world.structures.VolcanoPiece;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.Locale;

public class EPStructures {

    public static final StructurePieceType PIECE_VOLCANO = registerPiece("piece_volcano", VolcanoPiece::new);

    public static StructurePieceType registerPiece(String name, StructurePieceType piece) {
        return Registry.register(Registry.STRUCTURE_PIECE, EmberOfPhoenix.prefix(name.toLowerCase(Locale.ROOT)), piece);
    }

    public static void register(IEventBus eventBus) {
        RegistryBase.STRUCTURES.register(eventBus);
    }

}