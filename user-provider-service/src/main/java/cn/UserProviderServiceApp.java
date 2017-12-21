package cn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Hello world!
 *
 */
@EnableEurekaClient     // Eureka Client 标识
@SpringBootApplication  // Spring Boot 应用标识
@MapperScan("cn.dao") //mapper 接口类扫描包配置
public class UserProviderServiceApp extends SpringBootServletInitializer
{
    public static void main( String[] args )
    {
    	SpringApplication.run(UserProviderServiceApp.class, args);
    }
    
    @Override  
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {  
        builder.sources(this.getClass());  
        return super.configure(builder);  
    }  
    
    @Bean  
    public CorsFilter corsFilter() {  
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();  
        final CorsConfiguration config = new CorsConfiguration();  
        config.setAllowCredentials(true); // 允许cookies跨域  
        config.addAllowedOrigin("*");// #允许向该服务器提交请求的URI，*表示全部允许，在SpringMVC中，如果设成*，会自动转成当前请求头中的Origin  
        config.addAllowedHeader("*");// #允许访问的头信息,*表示全部  
        config.setMaxAge(18000L);// 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了  
        config.addAllowedMethod("OPTIONS");// 允许提交请求的方法，*表示全部允许  
        config.addAllowedMethod("HEAD");  
        config.addAllowedMethod("GET");// 允许Get的请求方法  
        config.addAllowedMethod("PUT");  
        config.addAllowedMethod("POST");  
        config.addAllowedMethod("DELETE");  
        config.addAllowedMethod("PATCH");  
        source.registerCorsConfiguration("/**", config);  
        return new CorsFilter(source);  
    }  
}
