package com.example.demo.config;

import com.example.demo.interceptor.AuthorizationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class InterceptorConfig implements WebMvcConfigurer {

//    @Override
//    public void addInterceptors(InterceptorRegistry registry){
//        registry.addInterceptor(new AuthorizationInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/login/zx");
//    }

//    场景：
//    使用Jwt做权限验证，生成的token存入redis，创建一个拦截器，拦截请求，在拦截器里对请求的token进行验证，当在拦截器里使用个人创建的一个redisUtil bean时，无法自动注入redisUtil bean，redisUtil 为null。
//
//    原因：
//    拦截器的执行是在spring容器中bean初始化之前的，拦截器执行时，spring中我们定义的bean还未初始化，自然也就无法自动注入，无法使用。

    //将拦截器bean化
    @Bean
    public AuthorizationInterceptor authorizationInterceptor(){
        return new AuthorizationInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        // 注入拦截器时，不再是new一个拦截器对象，直接使用jwtInterceptor()方法
        //返回的拦截器对象
        registry.addInterceptor(authorizationInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login/zx");
    }

}
