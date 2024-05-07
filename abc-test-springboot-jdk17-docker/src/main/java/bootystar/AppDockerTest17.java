package bootystar;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;


import java.net.InetAddress;

/**
 * @author booty
 *
 */
@Slf4j
@SpringBootApplication
public class AppDockerTest17 {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext application = SpringApplication.run(AppDockerTest17.class, args);
        Environment env = application.getEnvironment();
        String host= InetAddress.getLocalHost().getHostAddress();
        String port=env.getProperty("server.port");
        String context=env.getProperty("server.servlet.context-path");
        if (context==null){
            context="";
        }
        if (!context.startsWith("/")){
            context="/"+context;
        }
        if (!context.endsWith("/")){
            context=context+"/";
        }
        log.info("Application started doc at: {}:{}{}swagger-ui/index.html", host, port, context);

    }
}
