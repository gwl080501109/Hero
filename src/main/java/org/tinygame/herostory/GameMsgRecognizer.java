package org.tinygame.herostory;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;
import lombok.extern.slf4j.Slf4j;
import org.tinygame.herostory.msg.GameMsgProtocol;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName GameMsgRecognizer
 * @Deacription 消息识别器
 * @Author gewenle
 * @Date 2021/3/11 16:37
 * @Version 1.0
 **/
@Slf4j
public class GameMsgRecognizer {
    private GameMsgRecognizer() {}

    private static Map<Integer, GeneratedMessageV3> builderMap = new HashMap<>();
    private static Map<Class<?>, Integer> resultMap = new HashMap<>();

    public static void init() {

        final Class<?>[] innerClassArray = GameMsgProtocol.class.getDeclaredClasses();
        for (Class<?> innerClass : innerClassArray) {
            if (!GeneratedMessageV3.class.isAssignableFrom(innerClass)) {
                continue;
            }
            String simpleName = innerClass.getSimpleName().toLowerCase();
            GameMsgProtocol.MsgCode[] values = GameMsgProtocol.MsgCode.values();

            for (GameMsgProtocol.MsgCode msgCode : GameMsgProtocol.MsgCode.values()) {
                String toLowerCase = msgCode.name().replaceAll("_", "").toLowerCase();
                if (!toLowerCase.startsWith(simpleName)) {
                    continue;
                }
                try {
                    final Object returnObj = innerClass.getDeclaredMethod("getDefaultInstance").invoke(innerClass);
                    builderMap.put(msgCode.getNumber(), (GeneratedMessageV3) returnObj);
                    resultMap.put(innerClass,msgCode.getNumber());
                    log.info("参数1={},参数2={}", innerClass.getName(),msgCode.getNumber());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
//
//        builderMap.put(GameMsgProtocol.MsgCode.USER_ENTRY_CMD_VALUE, GameMsgProtocol.UserEntryCmd.newBuilder().getDefaultInstanceForType());
//        builderMap.put(GameMsgProtocol.MsgCode.WHO_ELSE_IS_HERE_CMD_VALUE, GameMsgProtocol.WhoElseIsHereCmd.newBuilder().getDefaultInstanceForType());
//        builderMap.put(GameMsgProtocol.MsgCode.USER_MOVE_TO_CMD_VALUE, GameMsgProtocol.UserMoveToCmd.newBuilder().getDefaultInstanceForType());
//
//        resultMap.put(GameMsgProtocol.UserEntryResult.class, GameMsgProtocol.MsgCode.USER_ENTRY_RESULT_VALUE);
//        resultMap.put(GameMsgProtocol.WhoElseIsHereResult.class, GameMsgProtocol.MsgCode.WHO_ELSE_IS_HERE_RESULT_VALUE);
//        resultMap.put(GameMsgProtocol.UserMoveToResult.class, GameMsgProtocol.MsgCode.USER_MOVE_TO_RESULT_VALUE);
//        resultMap.put(GameMsgProtocol.UserQuitResult.class, GameMsgProtocol.MsgCode.USER_QUIT_RESULT_VALUE);
    }

    public static Message.Builder getBuilderByMsgCode(int msgCode) {
        if (msgCode < 0) {
            return null;
        }
        GeneratedMessageV3 msg = builderMap.get(msgCode);
        if (msg == null) {
            return null;
        }
        return msg.newBuilderForType();
    }

    public static int getMsgCodeByMsgClazz(Class<?> clazz) {
        if (clazz == null) {
            return -1;
        }
        return resultMap.get(clazz);
    }
}