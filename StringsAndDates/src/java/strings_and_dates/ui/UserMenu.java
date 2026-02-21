package strings_and_dates.ui;

import strings_and_dates.model.Transaction;
import strings_and_dates.model.TransactionType;
import strings_and_dates.service.FinancialAccounting;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UserMenu {
    private final FinancialAccounting accounting;
    private final Scanner scanner = new Scanner(System.in);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public UserMenu(FinancialAccounting accounting) {
        this.accounting = accounting;
    }

    public void start() {
        // КРИТЕРИЙ: Вывод инструкции при запуске
        printHelp();

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;

            String command = input.toUpperCase();
            switch (command) {
                case "HELP" -> printHelp();
                case "REPORT" -> printReport();
                case "EXIT" -> {
                    // КРИТЕРИЙ: Вывод отчета перед завершением
                    printReport();
                    return;
                }
                default -> processTransaction(input);
            }
        }
    }

    private void processTransaction(String input) {
        try {
            // Разделяем по точке с запятой
            String[] parts = input.split(";");
            if (parts.length != 4) throw new Exception();

            String desc = parts[0].trim();
            double amount = Double.parseDouble(parts[1].trim());
            // КРИТЕРИЙ: Использование Enum.valueOf()
            TransactionType type = TransactionType.valueOf(parts[2].trim().toUpperCase());
            // КРИТЕРИЙ: Форматирование дат
            LocalDate date = LocalDate.parse(parts[3].trim(), formatter);

            accounting.addTransaction(new Transaction(desc, amount, type, date));
            System.out.println("[Система]: Запись успешно добавлена.");
        } catch (Exception e) {
            System.out.println("[Ошибка]: Неверный формат данных.");
            System.out.println("Пример: Билет на Марс; 2499.99; EXPENSE; 24.03.2036");
        }
    }

    private void printReport() {
        // КРИТЕРИЙ: Суммы доходов, расходов и баланс
        System.out.println("\n================ ФИНАНСОВЫЙ ОТЧЕТ ================");
        System.out.printf(" СУММА ДОХОДОВ:  %15.2f\n", accounting.getTotalIncome());
        System.out.printf(" СУММА РАСХОДОВ: %15.2f\n", accounting.getTotalExpense());
        System.out.printf(" ТЕКУЩИЙ БАЛАНС: %15.2f\n", accounting.getBalance());
        System.out.println("--------------------------------------------------");
        System.out.println(" ПОСЛЕДНИЕ ОПЕРАЦИИ (MAX 5):");

        // КРИТЕРИЙ: Отчет в форме таблицы
        System.out.println("+------------+------------+----------+----------------------+");
        System.out.println("|    Дата    |    Сумма   |    Тип   |      Описание        |");
        System.out.println("+------------+------------+----------+----------------------+");

        // КРИТЕРИЙ: Не более 5 последних транзакций
        Transaction[] lastOnes = accounting.getLastTransactions(5);
        for (Transaction t : lastOnes) {
            System.out.printf("| %10s | %10.2f | %-8s | %-20s |\n",
                    t.date().format(formatter),
                    t.amount(),
                    t.type(),
                    truncate(t.description(), 20));
        }
        System.out.println("+------------+------------+----------+----------------------+");
    }

    private String truncate(String text, int length) {
        if (text.length() <= length) return text;
        return text.substring(0, length - 3) + "...";
    }

    private void printHelp() {
        // КРИТЕРИЙ: Список команд и пример строки
        System.out.println("\n--- ИНСТРУКЦИЯ ---");
        System.out.println("HELP   - вывести список команд");
        System.out.println("REPORT - сформировать финансовый отчет");
        System.out.println("EXIT   - завершить работу");
        System.out.println("\nПример ввода новой транзакции:");
        System.out.println("Билет на Марс; 2499.99; EXPENSE; 24.03.2036");
        System.out.println("------------------\n");
    }
}
