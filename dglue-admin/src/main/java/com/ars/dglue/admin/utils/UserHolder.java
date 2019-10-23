package com.ars.dglue.admin.utils;

import com.ars.dglue.admin.dto.UserDTO;

/**
 * 〈一句话介绍功能〉<br>
 * user thread local holder
 *
 * @author jierui on 2019-10-18.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class UserHolder {

    private static final ThreadLocal<UserDTO> USER_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * get user
     *
     * @return user info
     */
    public static UserDTO getUser() {
        return USER_THREAD_LOCAL.get();
    }

    /**
     * set user
     *
     * @param user user info
     */
    public static void setUser(UserDTO user) {
        USER_THREAD_LOCAL.set(user);
    }

    /**
     * remove user
     */
    public static void removeUser() {
        USER_THREAD_LOCAL.remove();
    }

}
