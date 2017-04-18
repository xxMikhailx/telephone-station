package by.bsac.telephonestation.main;

import by.bsac.telephonestation.config.MainConfig;
import by.bsac.telephonestation.domain.User;
import by.bsac.telephonestation.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.Resource;

public class UserCreator {

    @Resource(name = "userService")
    private UserService userService;

    public UserCreator(){
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(MainConfig.class);
        ctx.refresh();
        UserCreator userCreator = (UserCreator) ctx.getBean("testClass");
        /*User user = new User();
        user.setLogin("admin");
        user.setPassword("admin");
        user.setRole((byte)1);
        user.setName("Mikhail");
        user.setSurname("Kaziuchyts");
        user.setAddress("BSAC");*/
        /*User user = new User();
        user.setLogin("user");
        user.setPassword("user");
        user.setRole((byte)0);
        user.setName("General");
        user.setSurname("User");
        user.setAddress("BSAC");*/
        User user = new User();
        user.setLogin("user2");
        user.setPassword("user2");
        user.setRole((byte)0);
        user.setName("General2");
        user.setSurname("User2");
        user.setAddress("BSAC");
        userCreator.userService.create(user);
        ctx.close();
    }
}
