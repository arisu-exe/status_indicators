package io.github.fallOut015.status_indicators.server;

import io.github.fallOut015.status_indicators.MainStatusIndicator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

public abstract class PacketHandlerStatusIndicators {
    private static int ids = 0;
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MainStatusIndicator.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    final int id;

    protected PacketHandlerStatusIndicators(int id) {
        this.id = id;
    }

    public static int getNewID() {
        return ids++;
    }
    public static void setup(final FMLCommonSetupEvent event) {
        INSTANCE.registerMessage(PostStatusPacketHandler.ID, PostStatusPacketHandler.class, PostStatusPacketHandler::encoder, PostStatusPacketHandler::decoder, PostStatusPacketHandler::handle);
    }
}