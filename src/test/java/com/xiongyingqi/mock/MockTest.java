package com.xiongyingqi.mock;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author xiongyingqi
 * @version 2016-03-24 12:21
 */
public class MockTest extends MockBaseTest {
//    @Mock(name = "userRepository")
    @Spy
    private UserDaoImpl     userDao;
    @InjectMocks
    private UserServiceImpl userService;
    //    @Spy
    private String[] users = new String[] { "tom", "jerry" };

    @SuppressWarnings("unchecked")
    @Test
    public void testName() throws Exception {
        List<String> mock = mock(List.class);
        mock.add("111");
        mock.add("222");
        verify(mock).add("111");
        verify(mock).add("222");
    }

    @Test
    public void testDao() throws Exception {
        UserDao mockUserDao = mock(UserDao.class);
        mockUserDao.setUserId("admin");
        verify(mockUserDao).setUserId("admin");
    }

    @Test
    public void testInject() throws Exception {
        userService.setUserId("tom");
        verify(userDao).setUserId(Mockito.eq("tom"));
    }

}

interface UserDao {
    void setUserId(String userId);
}

class UserDaoImpl implements UserDao {
    private String userId;

    @Override
    public void setUserId(String userId) {
        System.out.println(getClass().getSimpleName() + "#setUserId=" + userId);
        this.userId = userId;
    }
}

interface UserService {
    void setUserId(String userId);
}

class UserServiceImpl implements UserService {
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

class Hello{

}
