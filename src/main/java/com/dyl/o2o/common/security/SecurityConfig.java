package com.dyl.o2o.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/** security核心处理类
 * @author ：dyl
 * @date ：Created in 2019/12/9 14:25
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    //UserDetailsService的实现类有很多，通过此注解限制注入的实现类
    @Qualifier("userDetailsServiceImpl")
    UserDetailsService userDetailsService;

    @Autowired
    JWTAuthorizationFilter jwtAuthorizationFilter;
    @Autowired
    EntryPointUnauthorizedHandler entryPointUnauthorizedHandler;
    @Autowired
    MyAccessDeniedHandler myAccessDeniedHandler;

    //加密密码
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //登陆时调用;AuthenticationManager的建造器，配置 AuthenticationManagerBuilder 会让Security 自动构建一个 AuthenticationManager
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    //配置静态资源的处理方式
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/resource/static/**/*.js","/resource/static/**/*.css","/resource/templates/**/*.html");
    }

    //配置security
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //禁用CSRF（security自带的跨域处理）
        http.csrf().disable()
                .authorizeRequests()
                // swagger start
//                .antMatchers("/swagger-ui.html").anonymous()
//                .antMatchers("/swagger-resources/**").anonymous()
//                .antMatchers("/webjars/**").anonymous()
//                .antMatchers("/*/api-docs").anonymous()
                //其他的都放行
                .anyRequest().permitAll().and()

                //配置拦截时的处理
                .exceptionHandling()
                .authenticationEntryPoint(entryPointUnauthorizedHandler)//未登录、添加token无效或未携带token的处理
                .accessDeniedHandler(myAccessDeniedHandler)//无权限时的处理
                .and()

                //设置session策略，不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        /**
         * 本次 json web token 权限控制的核心配置部分
         * 在 Spring Security 开始判断本次会话是否有权限时的前一瞬间
         * 通过添加过滤器将 token 解析，将用户所有的权限写入本次会话
         */
        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
