package edu.byu.emergency;

/* Use this to run as stand-alone Spring Boot server with "./gradlew bootRun */
//@SpringBootApplication
//public class SocketApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(SocketApplication.class, args);
//	}
//
//}

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/* Use this to run as a .war file on Tomcat in the webapps directory */
@SpringBootApplication
public class SocketApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
        return applicationBuilder.sources(SocketApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SocketApplication.class, args);
    }
}