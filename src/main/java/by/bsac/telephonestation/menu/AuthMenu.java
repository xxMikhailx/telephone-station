package by.bsac.telephonestation.menu;

import by.bsac.telephonestation.domain.User;
import by.bsac.telephonestation.main.Main;
import by.bsac.telephonestation.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

@Component("authMenu")
public class AuthMenu {
    private final Logger log = LoggerFactory.getLogger(AuthMenu.class);

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    @Qualifier("adminMenu")
    private Menu adminMenu;

    @Autowired
    @Qualifier("userMenu")
    private Menu userMenu;

    private boolean isAuth = false;
    private String login;
    private String password;


    public AuthMenu() {
    }

    public boolean isAuth() {
        return isAuth;
    }

    public String getLogin() {
        return login;
    }

    public void run() {
        isAuth = false;
        int count = 0;
        while (!isAuth && count <3) {
            System.out.println("You have " + (3- count) + " attempts before system will shut down.");
            printMenu();
            Optional<User> optionalUser = userService.findAllUsers()
                        .parallelStream()
                        .filter(u -> login.equals(u.getLogin()))
                        .filter(u -> BCrypt.checkpw(password, u.getPassword()))
                        .findAny();
            if (optionalUser.isPresent()){
                User user = optionalUser.get();
                isAuth = true;
                Main.login = user.getLogin();
                Main.id = user.getId();
                Main.role = user.getRole();
                System.out.println("Login successful! Hello, " + login + "! Welcome to our system!");
                if (user.getRole() == 1) {
                    adminMenu.run();
                } else {
                    userMenu.run();
                }
            } else {
                count++;
                System.out.println("Username or password is incorrect!");
            }
        }
        log.error("You have exhausted all the attempts! System is shutting down...");
        System.exit(0);
    }

    private void printMenu() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\nLog in the system:\n");
        try {
            System.out.print("Username: ");
            login = reader.readLine();
            System.out.print("Password: ");
            password = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("");
    }
}
