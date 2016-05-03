package com.xiongyingqi.mock;

/**
 * @author xiongyingqi
 * @version 2016-05-03 11:27
 */
public class UserServiceImpl implements UserService {
    private String  userId;
    private UserDao userRepository;

    @Override
    public void setUserId(String userId) {
        userRepository.setUserId(userId);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserRepository(UserDao userRepository) {
        this.userRepository = userRepository;
    }
}
