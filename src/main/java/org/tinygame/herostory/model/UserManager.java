package org.tinygame.herostory.model;

import sun.util.resources.cldr.es.CalendarData_es_UY;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName UserManager
 * @Deacription
 * @Author gewenle
 * @Date 2021/3/7 16:03
 * @Version 1.0
 **/
public class UserManager {
    /**
     * 用户字典
     */
    private static final Map<Integer, User> userMap = new HashMap<>();

    private UserManager() {}

    /**
     * 添加用户
     * @param newUser
     */
    public static void addUser(User newUser) {
        if (newUser != null) {

            userMap.put(newUser.getUserId(), newUser);
        }
    }

    /**
     * 根据用户id移出用户
     * @param userId
     */
    public static void removeUserById(int userId) {
        userMap.remove(userId);
    }

    /**
     * 获取用户列表
     * @return
     */
    public static Collection<User> listUser() {
        return userMap.values();
    }
}