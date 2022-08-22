package com.lab.ws.server;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import java.util.HashMap;
import java.util.Map;

/**
 * serviceName UserService 对外发布的服务名
 * targetNamespace  指定名称空间，通常为包名倒序 server.ws.lab.com
 * endpointInterface 指定做SEI（Service EndPoint Interface）服务端点接口
 *
 * @author Administrator
 */
@WebService(serviceName = "UserService",
        targetNamespace = "http://server.ws.lab.com",
        endpointInterface = "com.lab.ws.server.UserService")
@Component
public class UserServiceImpl implements UserService {

    private Map<Integer, User> userMap = new HashMap<>();

    public UserServiceImpl() {
        User user = new User();
        user.setId(1);
        user.setName("张三");
        user.setEmail("zhangsan@test.com");
        userMap.put(user.getId(), user);

        User user1 = new User();
        user1.setId(2);
        user1.setName("李四");
        user1.setEmail("lisi@test.com");
        userMap.put(user1.getId(), user1);
    }

    @Override
    public String getUser(int id) {
        return JSONObject.toJSONString(userMap.get(id));
    }

    @Override
    public String getUserName(int id) {
        System.out.println("getUserName: " + id);
        return userMap.get(id).getName();
    }
}
