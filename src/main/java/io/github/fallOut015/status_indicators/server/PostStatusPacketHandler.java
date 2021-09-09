package io.github.fallOut015.status_indicators.server;

import io.github.fallOut015.status_indicators.MainStatusIndicator;
import io.github.fallOut015.status_indicators.client.StatusType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class PostStatusPacketHandler extends PacketHandlerStatusIndicators {
    public static final int ID = PacketHandlerStatusIndicators.getNewID();
    final UUID uuid;
    final StatusType statusType;

    public PostStatusPacketHandler(final UUID uuid, final StatusType statusType) {
        super(ID);
        this.uuid = uuid;
        this.statusType = statusType;
    }

    public static void encoder(PostStatusPacketHandler msg, FriendlyByteBuf buffer) {
        buffer.writeUUID(msg.uuid);
        buffer.writeInt(msg.statusType.ordinal());
    }
    public static PostStatusPacketHandler decoder(FriendlyByteBuf buffer) {
        return new PostStatusPacketHandler(buffer.readUUID(), StatusType.values()[buffer.readInt()]);
    }
    public static void handle(PostStatusPacketHandler msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
                System.out.println("Received status update on client");
                MainStatusIndicator.updatePlayerStatus(msg.uuid, msg.statusType);
            } else if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
                System.out.println("Received status update on server");
                System.out.println("Sending status update to clients");
                PacketHandlerStatusIndicators.INSTANCE.sendTo(msg, ctx.get().getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}