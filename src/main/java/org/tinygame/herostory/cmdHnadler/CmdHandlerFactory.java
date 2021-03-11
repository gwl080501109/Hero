package org.tinygame.herostory.cmdHnadler;

import com.google.protobuf.GeneratedMessageV3;
import org.tinygame.herostory.GameMsgProtocol;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName CmdHandlerFactory
 * @Deacription 指令处理器工厂
 * @Author gewenle
 * @Date 2021/3/10 20:57
 * @Version 1.0
 **/
public final class CmdHandlerFactory {

    private static Map<Class<?>, ICmdHandler<? extends GeneratedMessageV3>> handlerMap = new HashMap();

    public static void init() {
        handlerMap.put(GameMsgProtocol.UserEntryCmd.class, new UserEntryCmdHandler());
        handlerMap.put(GameMsgProtocol.WhoElseIsHereCmd.class, new WhoElseIsHereCmdHandler());
        handlerMap.put(GameMsgProtocol.UserMoveToCmd.class, new UserMoveToCmdHandler());
    }

    public static ICmdHandler<? extends GeneratedMessageV3> create(Class msgClazz) {
        if (msgClazz == null) {
            return null;
        }
        return handlerMap.get(msgClazz);
    }

}