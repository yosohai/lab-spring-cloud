package com.lab.nosql.handler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
public abstract class HandlerInterceptorAdapter implements HandlerInterceptor{
    // 在业务处理器处理请求之前被调用
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
/*        String authToken = request.getHeader("Authorization");
        String token = authToken.substring("Bearer".length() + 1).trim();
        UserTokenDTO userTokenDTO = JWTUtil.parseToken(token);
        //1.判断请求是否有效
        if (redisService.get(userTokenDTO.getId()) == null
                || !redisService.get(userTokenDTO.getId()).equals(token)) {
            return false;
        }

        //2.判断是否需要续期
        if (redisService.getExpireTime(userTokenDTO.getId()) < 1 * 60 * 30) {
            redisService.set(userTokenDTO.getId(), token);
            log.error("update token info, id is:{}, user info is:{}", userTokenDTO.getId(), token);
        }
        */
        return true;
    }
    // 在业务处理器处理请求完成之后，生成视图之前执行
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
      throws Exception{
    }
    // 在DispatcherServlet完全处理完请求之后被调用，可用于清理资源
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception{
    }
}