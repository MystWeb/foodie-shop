package cn.myst.web.config;

import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 处理跨域请求的配置类
 *
 * @author ziming.xing
 * Create Date：2021/5/20
 */
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    static final String[] ORIGINS = new String[]{"GET", "POST", "PUT", "DELETE", "PATCH", "HEAD", "OPTIONS"};

    /**
     * 设置CORS（跨域）配置信息
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // url映射路径
        registry.addMapping("/**")
                // 设置允许跨域信息的内容  注：当allowCredentials为true时，allowedOrigins不能包含特殊值"*"
                .allowedOrigins("http://localhost:8080")
                // 设置允许请求的方式
                .allowedMethods(ORIGINS)
                // 设置是否发送cookie信息
                .allowCredentials(true)
                // 设置客户端请求响应缓存时间。
                .maxAge(3600);
    }

    /**
     * 解决：An invalid character [34] was present in the Cookie value
     * 上面的 [34] 中的 34 是指 ASCII 码（十进制）对应的字符 "（双引号）
     * tomcat 8 更换默认的 CookieProcessor 实现为 Rfc6265CookieProcessor ，
     * 之前的实现为 LegacyCookieProcessor 。前者是基于 RFC6265 ，而后者基于 RFC6265、RFC2109、RFC2616
     */
    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieProcessorCustomizer() {
        return factory -> factory.addContextCustomizers(
                context -> context.setCookieProcessor(new LegacyCookieProcessor()));
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
