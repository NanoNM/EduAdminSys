package top.sleepnano.edusys.eduadminsys.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import top.sleepnano.edusys.eduadminsys.filter.JwtAuthTokenFilter;
import top.sleepnano.edusys.eduadminsys.service.impl.CustomUserDetailsServiceImpl;
import top.sleepnano.edusys.eduadminsys.util.StatusCodeUtil;
import top.sleepnano.edusys.eduadminsys.util.VoBuilderUtil;
import top.sleepnano.edusys.eduadminsys.vo.Result;


import java.io.IOException;


/**
 * SpringSecurity配置类
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig{
    @Autowired
    JwtAuthTokenFilter jwtAuthTokenFilter;

//    @Value("${KK.needlogin}")
//    String needLogin;

    @Autowired
    @Qualifier(value = "customUserDetailsServiceImpl")
    CustomUserDetailsServiceImpl customUserDetailsService;


    @Bean
    public SecurityFilterChain filerChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .exceptionHandling().authenticationEntryPoint().accessDeniedHandler().and()
                .authorizeHttpRequests()
//                .requestMatchers("/student/reg","/needLogin","/user/login/**").permitAll()
                .requestMatchers("/**").permitAll()
                .and().formLogin()
                .loginPage("/needLogin")
                .loginProcessingUrl("/user/login")
                .successHandler(new MyAuthenticationSuccessHandler())
                .failureHandler(new MyAuthenticationFailureHandler())
                .and()
                .logout()
                .logoutUrl("/user/lgout")
                .addLogoutHandler(new MyLogoutHandler())
                .logoutSuccessHandler(new MyLogoutSuccessHandler())
                .and().csrf().disable()
                .userDetailsService(customUserDetailsService).cors().configurationSource(corsConfigurationSource()).and()
                .addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    /**
     * 配置跨源访问(CORS)
     * @return
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    /**
     * 指定密码加密器
     * @return 密码加密器
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    /**
     * 自定义登出操作
     */
    class MyLogoutHandler implements LogoutHandler{
        @Override
        public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
            String token = request.getHeader("token");
            try {
                customUserDetailsService.logout(token,response,request);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * 登出成功后的逻辑
     */
    class MyLogoutSuccessHandler implements LogoutSuccessHandler {
        @Override
        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            Result result = VoBuilderUtil.ok(StatusCodeUtil.success.LOGIN_OUT_SUCCESS, "登出成功", null);
            response.setContentType("application/json;charset=UTF-8");
            String s = new ObjectMapper().writeValueAsString(result);
            response.getWriter().println(s);
        }
    }

    /**
     * 登录成功后返回的数据
     */
    class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            Result result = customUserDetailsService.successLogin(authentication);
            response.setContentType("application/json;charset=UTF-8");
            String s = new ObjectMapper().writeValueAsString(result);
            response.getWriter().println(s);
        }
    }

    /**
     * 登陆失败后返回的数据
     */
    class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
            Result result = VoBuilderUtil.failed(StatusCodeUtil.failed.LOGIN_FAILED, "登录失败", null);
            response.setContentType("application/json;charset=UTF-8");
            String s = new ObjectMapper().writeValueAsString(result);
            response.getWriter().println(s);
        }
    }



}
