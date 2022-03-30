//package cn.myst.web.config;
//
//import io.swagger.models.auth.In;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.SpringBootVersion;
//import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.oas.annotations.EnableOpenApi;
//import springfox.documentation.service.*;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spi.service.contexts.SecurityContext;
//import springfox.documentation.spring.web.plugins.Docket;
//
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//
///**
// * Swagger配置类
// * 参考文档：https://blog.csdn.net/Mrqiang9001/article/details/113573542
// *
// * @author ziming.xing
// * Create Date：2021/5/20
// */
//@Configuration
//@EnableOpenApi
//public class SwaggerConfig {
//    // 应用名称
//    @Value("${spring.application.name}")
//    private String applicationName;
//    // 应用版本号
//    public static final String APPLICATION_VERSION = "1.0";
//    // 定义 Swagger UI 上边的权限认证的头部信息的 key，如使用 token 作为权限认证方式，其 header 的 key 为 token，则 swagger3.authHeaderKey=token
//    public static final String AUTH_HEADER_KEY = "BASE_TOKEN";
//    public static final String AUTH_HEADER_KEY_NAME = "token";
//
//    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.OAS_30)
//                .pathMapping("/")
//
//                // 定义是否开启swagger，false为关闭，可以通过变量控制
//                .enable(true)
//
//                // 将api的元信息设置为包含在json ResourceListing响应中。
//                .apiInfo(apiInfo())
//
//                // 选择哪些接口作为swagger的doc发布
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                // .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
//                .paths(PathSelectors.regex("(?!/error.*).*"))
//                .paths(PathSelectors.regex("(?!/actuator.*).*"))
//                .build()
//
//                // 授权信息设置，必要的header token等认证信息
//                .securitySchemes(securitySchemes())
//
//                // 授权信息全局应用
//                .securityContexts(securityContexts())
//
//                .ignoredParameterTypes(SpringDataWebProperties.Pageable.class)
//                .ignoredParameterTypes(java.sql.Date.class)
//                .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
//                .directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
//                .directModelSubstitute(java.time.LocalDateTime.class, Date.class);
//    }
//
//    Contact contact = new Contact(
//            "Myst",
//            "https://mystweb.cn",
//            "your@email.com");
//
//    /**
//     * API 页面上半部分展示信息
//     */
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder().title(applicationName + " Api Doc")
//                .description("swagger 3 api document")
//                .contact(contact)
//                .version("Application Version: " + APPLICATION_VERSION + ", Spring Boot Version: " + SpringBootVersion.getVersion())
//                .build();
//    }
//
//    /**
//     * 设置授权信息
//     */
//    private List<SecurityScheme> securitySchemes() {
//        ApiKey apiKey = new ApiKey(AUTH_HEADER_KEY, AUTH_HEADER_KEY_NAME, In.HEADER.toValue());
//        return Collections.singletonList(apiKey);
//    }
//
//    /**
//     * 授权信息全局应用
//     */
//    private List<SecurityContext> securityContexts() {
//        return Collections.singletonList(
//                SecurityContext.builder()
//                        .securityReferences(Collections.singletonList(new SecurityReference(AUTH_HEADER_KEY,
//                                new AuthorizationScope[]{new AuthorizationScope("global", "")})))
//                        .build()
//        );
//    }
//}
