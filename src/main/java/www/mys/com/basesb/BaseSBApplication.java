package www.mys.com.basesb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@PropertySource(value = {
        "classpath:/properties/user.properties",
}, encoding = "UTF-8")
public class BaseSBApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseSBApplication.class, args);
    }

}
