package com.wynnvp.wynncraftvp.packet;

import com.wynnvp.wynncraftvp.events.custom.PacketEvent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;

public class PacketIncomingFilter extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg == null) return;

        PacketEvent.Incoming<? extends Packet<?>> event = new PacketEvent.Incoming<>((Packet<?>) msg, Minecraft.getMinecraft().getConnection(), this, ctx);
        boolean cancel = MinecraftForge.EVENT_BUS.post(event);
        if (cancel) return;

        super.channelRead(ctx, msg);
    }
}
