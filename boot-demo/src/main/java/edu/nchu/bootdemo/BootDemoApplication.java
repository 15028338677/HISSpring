package edu.nchu.bootdemo;


import edu.nchu.bootdemo.sys.JWTInterceptor;
import edu.nchu.bootdemo.util.IdWorker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * springboot穷启动类 同时还是一个spring的配置类
 * WebMvcConfigurer  用来配置SpringMVC的
 */
@SpringBootApplication
@MapperScan("edu.nchu.bootdemo.dao")  // 指定Mybatis mapper接口的所在的包位置，配置了Mybatis接口的扫描器
public class BootDemoApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(BootDemoApplication.class, args);
    }

    /**
     * 密码加密解密工具
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     *  注入id主键生成器
     */
    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }


    /**
     * 除了/login请求不拦截
     * @param registry 注册器
//     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor())
                .addPathPatterns("/**") // 置顶拦截哪些请求
                .excludePathPatterns("/login"); // 排除登录请求
    }
}
