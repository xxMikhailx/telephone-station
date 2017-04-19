package by.bsac.telephonestation.config;

import by.bsac.telephonestation.domain.Landline;
import by.bsac.telephonestation.domain.User;
import by.bsac.telephonestation.menu.Menu;
import by.bsac.telephonestation.menu.MenuEntry;
import by.bsac.telephonestation.service.LandlineService;
import by.bsac.telephonestation.service.UserService;
import by.bsac.telephonestation.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@ComponentScan({"by.bsac.telephonestation.menu"})
public class MenuConfig {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    @Qualifier("landlineService")
    private LandlineService landlineService;

    @Bean
    public Menu adminMenu() {
        return new Menu()
                .addEntry(new MenuEntry("Users") {
                    @Override
                    public void run() {
                        adminMenuUsers().run();
                    }
                })
                .addEntry(new MenuEntry("Landlines") {
                    @Override
                    public void run() {
                        adminMenuLandlines().run();
                    }
                });
    }

    @Bean
    public Menu adminMenuUsers() {
        return new Menu()
                .addEntry(new MenuEntry("Users list") {
                    @Override
                    public void run() {
                        System.out.println("---===Users===---");
                        userService.findAllUsers().forEach(System.out::println);
                    }
                })
                .addEntry(new MenuEntry("Back") {
                    @Override
                    public void run() {
                        adminMenu().run();
                    }
                });
    }

    @Bean
    public Menu adminMenuLandlines() {
        return new Menu()
                .addEntry(new MenuEntry("Landlines list") {
                    @Override
                    public void run() {
                        System.out.println("---===Landlines===---");
                        landlineService.findAllLandlines().forEach(System.out::println);
                    }
                })
                .addEntry(new MenuEntry("Create landline") {
                    @Override
                    public void run() {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                        try {
                            Landline landline;
                            System.out.print("\nEnter phone number: ");
                            String phoneNumber = reader.readLine();
                            System.out.print("\nEnter setup year: ");
                            String setupYear = reader.readLine();
                            System.out.println("---===Users===---");
                            List<User> users = userService.findAllUsers()
                                    .stream()
                                    .filter(user -> user.getRole() == 0)
                                    .collect(Collectors.toList());
                            for (int i = 0; i < users.size(); i++) {
                                System.out.print("\n№" + (i + 1) + ".  ");
                                System.out.println(users.get(i).toString());
                            }
                            System.out.print("\nEnter the owner's number: ");
                            String line = reader.readLine();
                            int choice;
                            if (Util.checkString(line) && Util.validatePhoneNumber(phoneNumber) && Util.validateSetupYear(setupYear)) {
                                choice = Integer.parseInt(line);
                                if (choice > 0 && choice <= users.size()) {
                                    User user = users.get(choice - 1);
                                    landline = new Landline();
                                    landline.setPhoneNumber(Integer.parseInt(phoneNumber));
                                    landline.setSetupYear(Year.parse(setupYear));
                                    landline.setUser(user);
                                    landlineService.create(landline);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .addEntry(new MenuEntry("Edit landlines") {
                    @Override
                    public void run() {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                        try {
                            System.out.println("---===Landlines===---");
                            List<Landline> landlines = landlineService.findAllLandlines();
                            for (int i = 0; i < landlines.size(); i++) {
                                System.out.print("\n№" + (i + 1) + ".  ");
                                System.out.println(landlines.get(i).toString());
                            }
                            System.out.print("\nEnter the landline's number to be edited: ");
                            String line = reader.readLine();
                            int choice;
                            if (Util.checkString(line)) {
                                choice = Integer.parseInt(line);
                                if (choice > 0 && choice <= landlines.size()) {
                                    Landline previousLandline = landlines.get(choice - 1);
                                    System.out.print("\nEnter phone number. Leave empty to use previous value(" + previousLandline.getPhoneNumber() + "): ");
                                    String phoneNumber = reader.readLine();
                                    phoneNumber = phoneNumber.isEmpty() ? String.valueOf(previousLandline.getPhoneNumber()) : phoneNumber;
                                    System.out.print("\nEnter setup year. Leave empty to use previous value(" + previousLandline.getSetupYear().getValue() + "): ");
                                    String setupYear = reader.readLine();
                                    setupYear = setupYear.isEmpty() ? previousLandline.getSetupYear().toString() : setupYear;
                                    System.out.println("---===Users===---");
                                    List<User> users = userService.findAllUsers()
                                            .stream()
                                            .filter(user -> user.getRole() == 0)
                                            .collect(Collectors.toList());
                                    for (int i = 0; i < users.size(); i++) {
                                        System.out.print("\n№" + (i + 1) + ".  ");
                                        System.out.println(users.get(i).toString());
                                    }
                                    System.out.print("\nEnter the owner's number. Leave empty to use previous value(" + previousLandline.getUser().getLogin() + "): ");
                                    String userNumber = reader.readLine();
                                    int choice2;
                                    if (Util.validatePhoneNumber(phoneNumber) && Util.validateSetupYear(setupYear)) {
                                        if (userNumber.isEmpty()) {
                                            choice2 = users.indexOf(previousLandline.getUser());
                                        } else {
                                            if (Util.checkString(userNumber)) {
                                                choice2 = Integer.parseInt(userNumber);
                                                choice2--;
                                            } else {
                                                choice2 = -1;
                                            }
                                        }
                                        if (choice2 >= 0 && choice2 < users.size()) {
                                            User user = users.get(choice2);
                                            previousLandline.setPhoneNumber(Integer.parseInt(phoneNumber));
                                            previousLandline.setSetupYear(Year.parse(setupYear));
                                            previousLandline.setUser(user);
                                            landlineService.update(previousLandline);
                                        }
                                    }
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .addEntry(new MenuEntry("Delete landlines") {
                    @Override
                    public void run() {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                        try {
                            System.out.println("---===Landlines===---");
                            List<Landline> landlines = landlineService.findAllLandlines();
                            for (int i = 0; i < landlines.size(); i++) {
                                System.out.print("\n№" + (i + 1) + ".  ");
                                System.out.println(landlines.get(i).toString());
                            }
                            System.out.print("\nEnter the landline's number to be deleted: ");
                            String line = reader.readLine();
                            int choice;
                            if (Util.checkString(line)) {
                                choice = Integer.parseInt(line);
                                if (choice > 0 && choice <= landlines.size()) {
                                    landlineService.remove(landlines.get(choice - 1).getId());
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .addEntry(phoneNumberMenuEntry())
                .addEntry(countMenuEntry())
                .addEntry(new MenuEntry("Back") {
                    @Override
                    public void run() {
                        adminMenu().run();
                    }
                });
    }

    @Bean
    public Menu userMenu() {
        return new Menu()
                .addEntry(phoneNumberMenuEntry())
                .addEntry(countMenuEntry());
    }

    private MenuEntry phoneNumberMenuEntry() {
        return new MenuEntry("Find phone number by user's surname") {
            @Override
            public void run() {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("\nEnter user's surname you want to find by: ");
                try {
                    String line = reader.readLine();
                    int phoneNumber = landlineService.findLandlineNumberByUserSurname(line);
                    System.out.println(phoneNumber != 0 ? "Phone number is " + phoneNumber + "." : "Can't find phone number by this surname!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private MenuEntry countMenuEntry() {
        return new MenuEntry("Phone's count after specific year") {
            @Override
            public void run() {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("\nEnter the year you want to find after: ");
                try {
                    String line = reader.readLine();
                    int landlineCount = 0;
                    if (Util.validateSetupYear(line)) {
                        landlineCount = landlineService.findLandlineCountAfterYear(Year.parse(line));
                    }
                    System.out.println("Phone's count after " + line + " year is " + landlineCount + ".");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
