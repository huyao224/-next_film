package com.next.huyao.film.controller.common.filter;


import com.alibaba.fastjson.JSON;
import com.next.huyao.film.config.properties.JwtProperties;
import com.next.huyao.film.controller.auth.util.JwtTokenUtil;
import com.next.huyao.film.controller.common.BaseResponseVO;
import com.next.huyao.film.controller.common.TraceUtil;
import com.next.huyao.film.service.common.exception.CommonServiceException;
import com.next.huyao.film.service.user.UserServiceAPI;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 对客户端请求的jwt token验证过滤器
 *
 * @author jiangzh
 * @Date 2019/3/23 14:04
 */
@Slf4j
public class AuthFilter extends OncePerRequestFilter {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    UserServiceAPI userServiceAPI;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException{
        if (request.getServletPath().equals("/auth") || request.getServletPath().equals("/register")
                || request.getServletPath().equals("/user/register")
                || request.getServletPath().equals("/swagger-ui.html")
                || request.getServletPath().startsWith("/swagger-resources")
                || request.getServletPath().startsWith("/v2/api-docs")
                || request.getServletPath().startsWith("/webjars/springfox-swagger-ui/")) {
            chain.doFilter(request, response);
            return;
        }
        final String requestHeader = request.getHeader(jwtProperties.getHeader());
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith("Next ")) {
            authToken = requestHeader.substring(5);

            String userName = jwtTokenUtil.getUsernameFromToken(authToken);
            String userId = null;
            try {
                userId = userServiceAPI.describeUserId(userName);
            } catch (CommonServiceException e) {
                e.printStackTrace();
            }
            TraceUtil.initThread(userId);
            log.info("username:{}",jwtTokenUtil.getUsernameFromToken(authToken));

            //验证token是否过期,包含了验证jwt是否正确
            try {
                boolean flag = jwtTokenUtil.isTokenExpired(authToken);
                if (flag) {
                    renderJson(response, BaseResponseVO.noLogin());
                    return;
                }
            } catch (JwtException e) {
                //有异常就是token解析失败
                renderJson(response, BaseResponseVO.noLogin());
                return;
            }
        } else {
            //header没有带Next字段
            renderJson(response, BaseResponseVO.noLogin());
            return;
        }
        chain.doFilter(request, response);
    }


    /**
     * 渲染json对象
     */
    public static void renderJson(HttpServletResponse response, Object jsonObject) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(jsonObject));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}