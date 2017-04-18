package by.bsac.telephonestation.menu;

import by.bsac.telephonestation.main.Main;
import by.bsac.telephonestation.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Menu {
    private static final String MENU_PATTERN = "%s - %s\n";
    private List<MenuEntry> entries = new ArrayList<>();
    private boolean isExit = false;
    @Autowired
    @Qualifier("authMenu")
    private AuthMenu authMenu;

    public Menu() {
        entries.add(new MenuEntry("Exit") {
            @Override
            public void run() {
                isExit = true;
            }
        });
    }

    public void run() {
        isExit = false;
        while (!isExit) {
            printMenu();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                String line = reader.readLine();
                int choice;
                if (Util.checkString(line)) {
                    choice = Integer.parseInt(line);
                    if (choice > 0 && choice <= entries.size()) {
                        MenuEntry entry = entries.get(choice - 1);
                        entry.run();
                    }
                } else {
                    System.out.println("Incorrect number. Try again, please!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        authMenu.run();
    }

    public Menu addEntry(MenuEntry entry) {
        int index = entries.size() - 1;
        entries.add(index, entry);
        return this;
    }

    private void printMenu() {
        StringBuilder builder = new StringBuilder();
        builder.append("\nMenu: | User: ").append(Main.login).append(" | Role: ").append((Main.role == 1) ? "Admin" : "User").append("\n");
        for (int i = 0; i < entries.size(); i++) {
            MenuEntry entry = entries.get(i);
            String entryFormatted = String.format(MENU_PATTERN, (i + 1), entry.getTitle());
            builder.append(entryFormatted);
        }
        System.out.print(builder.toString());
    }
}
