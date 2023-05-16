package top.sleepnano.edusys.eduadminsys.filter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import top.sleepnano.edusys.eduadminsys.EduAdminSysApplication;
import top.sleepnano.edusys.eduadminsys.dto.LoginUser;
import top.sleepnano.edusys.eduadminsys.dto.LoginUserMap;
import top.sleepnano.edusys.eduadminsys.util.JwtUtil;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class JwtAuthTokenFilter extends OncePerRequestFilter {

    /**
     *
     * @param request 请求
     * @param response 响应
     * @param filterChain 过滤器联调
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 截取token
        String token = request.getHeader("token");
        if (Objects.isNull(token)){
            filterChain.doFilter(request, response);
            return;
        }
        // 解析token
        String userNo = null;
        try {
            userNo = JwtUtil.parseJWT(token).getSubject();
            // TODO 需要判断userNo为null的情况 但是现在不需要
            LoginUser loginUser = ((LoginUserMap) EduAdminSysApplication.LOGIN_USER.get("login:" + userNo)).getLoginUser();

            if (Objects.nonNull(loginUser)){
                List<SimpleGrantedAuthority> simpleGrantedAuthorities =
                        loginUser.getPermissions().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =

                        new UsernamePasswordAuthenticationToken(loginUser,null,simpleGrantedAuthorities);
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        } catch (ExpiredJwtException e){
//            开发期间 放行所有
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
//             异常捕获，发送到error controller
            request.setAttribute("filter.error", e);
//            将异常分发到/error/exthrow控制器
            request.getRequestDispatcher("/error/exthrow").forward(request, response);

        }
        filterChain.doFilter(request,response);
    }
}
