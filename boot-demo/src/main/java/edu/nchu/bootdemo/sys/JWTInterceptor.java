package edu.nchu.bootdemo.sys;

import edu.nchu.bootdemo.model.Employee;
import edu.nchu.bootdemo.util.JwtException;
import edu.nchu.bootdemo.util.JwtUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;

public class JWTInterceptor implements HandlerInterceptor {

    /**
     * 拦截请求（除了登录注册等请求） 判断token是否合法
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1. 先获取token，从请求头中获取token
        String token = request.getHeader("access-token");
        //2. 对token进行合法性验证
        try {
            //如果验证成功，返回一个代表当前登录用户的employee对象
            Employee employee = JwtUtil.validToken(token);
            //更新当前的token，更新expire time 超时时间
            String newToken = JwtUtil.genToken(employee.getId(), employee.getUsername());
            //将新token存入到响应头当中；
            response.setHeader("access-token", newToken);
            //验证权限成功后，放行给controller处理具体请求
            return true;
        } catch (JwtException | ParseException e) {
            //如果验证不合法，直接返回状态码401，返回失败信息
            response.setStatus(401);
            response.getWriter().write("{\"message\":\"forbidden\"}");
            return false;
        }
    }
}
