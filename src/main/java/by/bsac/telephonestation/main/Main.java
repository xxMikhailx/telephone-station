package by.bsac.telephonestation.main;

import by.bsac.telephonestation.config.MainConfig;
import by.bsac.telephonestation.menu.AuthMenu;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Main {

    public static long id;
    public static String login;
    public static int role;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(MainConfig.class);
        ctx.refresh();
        AuthMenu authMenu = (AuthMenu) ctx.getBean("authMenu");
        authMenu.run();
        ctx.close();
    }

}
