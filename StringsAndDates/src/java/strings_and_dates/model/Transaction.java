package strings_and_dates.model;

import java.time.LocalDate;

public record Transaction (    String description,
                               double amount,
                               TransactionType type,
                               LocalDate date){

}
