package org.tinygame.herostory;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName GameMsgDecoder
 * @Deacription 编码器
 * @Author gewenle
 * @Date 2021/3/1 22:47
 * @Version 1.0
 **/
@Slf4j
public class  GameMsgDecoder  extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("收到客户端消息2,msg = " + msg);
        BinaryWebSocketFrame frame = (BinaryWebSocketFrame)msg;
        ByteBuf byteBuf = frame.content();
        byteBuf.readShort();//读取消息长度
        int msgCode = byteBuf.readShort();//读取消息编号

        Message.Builder msgBuilder = GameMsgRecognizer.getBuilderByMsgCode(msgCode);
        if (null == msgBuilder) {
            log.info("builderByMsgCode is null");
            return;
        }
        //拿到消息体
        byte[] msgBody = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(msgBody);

        msgBuilder.mergeFrom(msgBody);
        final Message newMsg = msgBuilder.build();

        if (newMsg != null) {
            ctx.fireChannelRead(newMsg);
        }
    }
}