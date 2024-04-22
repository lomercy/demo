package com.example.xxl.job.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@Data
public class Config4xxlJob {

    @Value("${xxl.job.admin.addresses}")
    private String adminAddresses;

    @Value("${xxl.job.accessToken}")
    private String accessToken;

    @Value("${xxl.job.executor.appname}")
    private String appname;

//    @Value("${xxl.job.executor.address}")
//    private String address;
//
//    @Value("${xxl.job.executor.ip}")
//    private String ip;
//
//    @Value("${xxl.job.executor.port}")
//    private Integer port;
//
//    @Value("${xxl.job.executor.logpath}")
//    private String logPath;
//
//    @Value("${xxl.job.executor.logretentiondays}")
//    private Integer logRetentionDays;


    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        log.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        if (adminAddresses != null && !adminAddresses.isEmpty()) {
            xxlJobSpringExecutor.setAdminAddresses(adminAddresses);
        }
        if (accessToken != null && !accessToken.isEmpty()) {
            xxlJobSpringExecutor.setAccessToken(accessToken);
        }
        // 需要在xxl-admin配置中心手动添加同名执行器后才能使用该执行器；
        if (appname != null && !appname.isEmpty()) {
            xxlJobSpringExecutor.setAppname(appname);
        }
//        if (address != null && !address.isEmpty()) {
//            xxlJobSpringExecutor.setAddress(address);
//        }
//        if (ip != null && !ip.isEmpty()) {
//            xxlJobSpringExecutor.setIp(ip);
//        }
//        if (logPath != null && !logPath.isEmpty()) {
//            xxlJobSpringExecutor.setLogPath(logPath);
//        }
//        if (logRetentionDays != null && logRetentionDays > 0) {
//            xxlJobSpringExecutor.setLogRetentionDays(logRetentionDays);
//        }

        return xxlJobSpringExecutor;
    }


}
