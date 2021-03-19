package org.tinygame.herostory.cmdHnadler;

import com.google.protobuf.GeneratedMessageV3;
import lombok.extern.slf4j.Slf4j;
import org.tinygame.herostory.msg.GameMsgProtocol;
import org.tinygame.herostory.util.PackageUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName CmdHandlerFactory
 * @Deacription 指令处理器工厂
 * @Author gewenle
 * @Date 2021/3/10 20:57
 * @Version 1.0
 **/
@Slf4j
public final class CmdHandlerFactory {

    private static Map<Class<?>, ICmdHandler<? extends GeneratedMessageV3>> handlerMap = new HashMap();

    public static void init() {
//        handlerMap.put(GameMsgProtocol.UserEntryCmd.class, new UserEntryCmdHandler());
//        handlerMap.put(GameMsgProtocol.WhoElseIsHereCmd.class, new WhoElseIsHereCmdHandler());
//        handlerMap.put(GameMsgProtocol.UserMoveToCmd.class, new UserMoveToCmdHandler());
        //拿出当前包下所有实现了ICmdHandler接口的实现类
        Set<Class<?>> clazzSet = PackageUtil.listSubClazz(CmdHandlerFactory.class.getPackage().getName(), true, ICmdHandler.class);
        for (Class<?> clazz : clazzSet) {
            //过滤掉上面实现类中的接口
            if ((clazz.getModifiers() & Modifier.ABSTRACT) != 0) {
                continue;
            }
            //消息类型
            Class<?> msgType = null;
            //获取方法数组
            Method[] methodArray = clazz.getDeclaredMethods();

            for (Method currMethod : methodArray) {
                if (!currMethod.getName().equals("handler")) {
                    continue;
                }
                //获取函数参数类型
                Class<?>[] parameterTypes = currMethod.getParameterTypes();
                if (parameterTypes.length < 2 || !GeneratedMessageV3.class.isAssignableFrom(parameterTypes[1])) {
                    continue;
                }
                msgType = parameterTypes[1];
                break;
            }

            if (null == msgType) {
                continue;
            }

            try {
                ICmdHandler<?> newHandler = (ICmdHandler<?>) clazz.newInstance();
                handlerMap.put(msgType, newHandler);
                log.info("msgType={}, clazzName={}", msgType, clazz.getName());
            } catch (Exception e) {
                log.error("errMsg={}", e.getMessage());
            }
        }

    }

    public static ICmdHandler<? extends GeneratedMessageV3> create(Class msgClazz) {
        if (msgClazz == null) {
            return null;
        }
        return handlerMap.get(msgClazz);
    }

}