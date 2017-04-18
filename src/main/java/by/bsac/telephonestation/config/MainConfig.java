package by.bsac.telephonestation.config;

import by.bsac.telephonestation.main.UserCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({"by.bsac.telephonestation.domain"
                ,"by.bsac.telephonestation.service"
                ,"by.bsac.telephonestation.dao"})
@Import({DBConfig.class, MenuConfig.class})
public class MainConfig {

    @Bean
    public UserCreator testClass(){
        return new UserCreator();
    }
}
