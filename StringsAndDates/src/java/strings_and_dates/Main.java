package strings_and_dates;

import strings_and_dates.service.FinancialAccounting;
import strings_and_dates.ui.UserMenu;

public class Main {
    public static void main(String[] args) {
        // 1. Создаем "объект"
        FinancialAccounting accounting = new FinancialAccounting();

        // 2. Создаем интерфейс и передаем ему
        UserMenu menu = new UserMenu(accounting);

        // 3. Запускаем
        menu.start();
    }
    }

