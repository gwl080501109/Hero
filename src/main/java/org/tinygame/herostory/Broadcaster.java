package org.tinygame.herostory;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @ClassName Broadcaster
 * @Deacription
 * @Author gewenle
 * @Date 2021/3/5 9:17
 * @Version 1.0
 **/
public final class Broadcaster {
    /**
     * 客户端信道数组 一定要用static  否则有多个实例就无法实现群发
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private Broadcaster() {}

    public static void addChannel(Channel channel) {
        channelGroup.add(channel);
    }

    public static void removeChannel(Channel channel) {
        channelGroup.remove(channel);
    }

    /**
     * 广播消息
     * @param msg
     */
    public static void broadcast(Object msg) {
        if (null == msg) {
            return;
        }
        channelGroup.writeAndFlush(msg);
    }
}