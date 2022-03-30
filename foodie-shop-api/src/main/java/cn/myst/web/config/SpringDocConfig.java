package cn.myst.web.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SpringDoc API文档相关配置
 * 参考文档：https://mp.weixin.qq.com/s/scityFhZp9BOJorZSdCG0w
 *
 * @author ziming.xing
 * Create Date：2022/3/30
 */
@Configuration
public class SpringDocConfig {
    private static final String SECURITY_SCHEME_NAME = "BearerAuth";
    // 应用名称
    @Value("${spring.application.name}")
    private String applicationName;
    // 应用版本号
    public static final String APPLICATION_VERSION = "1.0";
    // DOC_URL
    public static final String DOC_URL = "https://www.mystweb.cn";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(apiInfo())
                // 外部文档设置
                .externalDocs(new ExternalDocumentation()
                        .description("SpringBoot实战电商项目 foodie-shop 全套文档")
                        .url(DOC_URL))
                // 授权信息设置，必要的header token等认证信息
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme().name(SECURITY_SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToExclude("/passport/**")
                .build();
    }

    @Bean
    public GroupedOpenApi loginApi() {
        return GroupedOpenApi.builder()
                .group("login")
                .pathsToMatch("/passport/**")
                .build();
    }

    /**
     * 昵称、博客链接、邮箱
     */
    Contact contact = new Contact()
            .name("Myst")
            .url(DOC_URL)
            .email("your@email.com");

    /**
     * API 页面上半部分展示信息
     */
    private Info apiInfo() {
        return new Info().title(applicationName + " API Doc")
                .description("SpringDoc API Document")
                .contact(contact)
                .version("Application Version: " + APPLICATION_VERSION + ", Spring Boot Version: " + SpringBootVersion.getVersion())
                .license(new License().name("Apache 2.0").url("https://github.com/MystWeb/foodie-shop"));
    }

}
