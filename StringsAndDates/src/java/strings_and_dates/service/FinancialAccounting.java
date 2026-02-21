package strings_and_dates.service;

import strings_and_dates.model.Transaction;
import strings_and_dates.model.TransactionType;


public class FinancialAccounting {
    // Начальная емкость массива - 5 элементов
    private Transaction[] transactions = new Transaction[6];
    // Переменная size хранит реальное кол-во доб. транкз.
    private int size = 0;

    // Добавление новой транкзации
    public void addTransaction(Transaction t) {
        transactions[size] = t;
        size++;
    }

    // Считаем сумму расходов общ
    public double getTotalExpense() {
        double sum = 0;
        for (int i = 0; i < size; i++) {
            if (transactions[i].type() == TransactionType.EXPENSE) {
                sum += transactions[i].amount();
            }
        }
        return sum;
    }

    // Считаем общую сумму доходов
    public double getTotalIncome() {
        double sum = 0;
        for (int i = 0; i < size; i++) {
            if (transactions[i].type() == TransactionType.INCOME) {
                sum += transactions[i].amount();
            }
        }
        return sum;
    }

    // Вычисляем баланс
    public double getBalance() {
        return getTotalIncome() - getTotalExpense();
    }

    // Метод для получения массива последних 5 транзакций для отчета
    public Transaction[] getLastTransactions(int count) {
        // Если транзакций меньше, чем просят, берем сколько есть
        int actualCount = Math.min(size, count);
        Transaction[] lastOnes = new Transaction[actualCount];

        // Копируем последние элементы из основного массива в новый
        for (int i = 0; i < actualCount; i++) {
            lastOnes[i] = transactions[size - actualCount + i];
        }
        return lastOnes;
    }

}
