package org.tinygame.herostory;

import com.google.protobuf.Message;

/**
 * @ClassName GameMsgRecognizer
 * @Deacription 消息识别器
 * @Author gewenle
 * @Date 2021/3/11 16:37
 * @Version 1.0
 **/
public class GameMsgRecognizer {
    private GameMsgRecognizer() {}

    public static Message.Builder getBuilderByMsgCode(int msgCode) {
        Message.Builder msgBuilder = null;
        switch (msgCode) {
            case GameMsgProtocol.MsgCode.USER_ENTRY_CMD_VALUE:
                msgBuilder = GameMsgProtocol.UserEntryCmd.newBuilder();
                break;
            case GameMsgProtocol.MsgCode.WHO_ELSE_IS_HERE_CMD_VALUE:
                msgBuilder = GameMsgProtocol.WhoElseIsHereCmd.newBuilder();
                break;
            case GameMsgProtocol.MsgCode.USER_MOVE_TO_CMD_VALUE:
                msgBuilder = GameMsgProtocol.UserMoveToCmd.newBuilder();
                break;
            default:
                return null;
        }
        return msgBuilder;
    }
}