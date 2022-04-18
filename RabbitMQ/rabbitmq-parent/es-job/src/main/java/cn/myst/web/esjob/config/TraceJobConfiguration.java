package cn.myst.web.esjob.config;

import cn.myst.web.esjob.annotation.JobTraceInterceptor;
import org.springframework.context.annotation.Bean;

//@Configuration
public class TraceJobConfiguration {

    @Bean
    public JobTraceInterceptor jobTraceInterceptor() {
        System.err.println("init --------------->");
        return new JobTraceInterceptor();
    }

}
