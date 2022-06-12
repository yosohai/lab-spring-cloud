package com.lab.nosql.web;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
/*    public String login(LoginUserVO loginUserVO) {
        //1.判断用户名密码是否正确
        UserPO userPO = userMapper.getByUsername(loginUserVO.getUsername());
        if (userPO == null) {
            throw new UserException(ErrorCodeEnum.TNP1001001);
        }
        if (!loginUserVO.getPassword().equals(userPO.getPassword())) {
            throw new UserException(ErrorCodeEnum.TNP1001002);
        }

        //2.用户名密码正确生成token
        UserTokenDTO userTokenDTO = new UserTokenDTO();
        PropertiesUtil.copyProperties(userTokenDTO, loginUserVO);
        userTokenDTO.setId(userPO.getId());
        userTokenDTO.setGmtCreate(System.currentTimeMillis());
        String token = JWTUtil.generateToken(userTokenDTO);

        //3.存入token至redis
        redisService.set(userPO.getId(), token);
        return token;
    }
    public boolean loginOut(String id) {
        boolean result = redisService.delete(id);
        if (!redisService.delete(id)) {
            throw new UserException(ErrorCodeEnum.TNP1001003);
        }

        return result;
    }


    public String updatePassword(UpdatePasswordUserVO updatePasswordUserVO) {
        //1.修改密码
        UserPO userPO = UserPO.builder().password(updatePasswordUserVO.getPassword())
                .id(updatePasswordUserVO.getId())
                .build();
        UserPO user = userMapper.getById(updatePasswordUserVO.getId());
        if (user == null) {
            throw new UserException(ErrorCodeEnum.TNP1001001);
        }

        if (userMapper.updatePassword(userPO) != 1) {
            throw new UserException(ErrorCodeEnum.TNP1001005);
        }
        //2.生成新的token
        UserTokenDTO userTokenDTO = UserTokenDTO.builder()
                .id(updatePasswordUserVO.getId())
                .username(user.getUsername())
                .gmtCreate(System.currentTimeMillis()).build();
        String token = JWTUtil.generateToken(userTokenDTO);
        //3.更新token
        redisService.set(user.getId(), token);
        return token;
    }

    更新用户密码时需要重新生成新的token，并将新的token返回给前端，由前端更新保存在local storage中的token，
    同时更新存储在redis中的token，这样实现可以避免用户重新登陆，用户体验感不至于太差*/
}
