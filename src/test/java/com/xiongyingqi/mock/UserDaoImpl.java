package com.xiongyingqi.mock;

/**
 * @author xiongyingqi
 * @version 2016-05-03 11:27
 */
public class UserDaoImpl implements UserDao {
    private String userId;

    @Override
    public void setUserId(String userId) {
        System.out.println(getClass().getSimpleName() + "#setUserId=" + userId);
        this.userId = userId;
    }
}
