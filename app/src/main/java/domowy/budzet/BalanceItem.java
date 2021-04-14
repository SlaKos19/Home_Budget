package domowy.budzet;


import org.threeten.bp.LocalDateTime;

public interface BalanceItem {

    void setId(int id);

    int getId();

    void setName(String name);

    String getName();

    void setCategory(Category category);

    Category getCategory();

    void setAmount(double amount);

    double getAmount();

    void setLocalDateTime(LocalDateTime localDate);

    LocalDateTime getLocalDateTime();
}
