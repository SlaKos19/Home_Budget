package domowy.budzet;


import org.threeten.bp.LocalDateTime;

public class Outcome implements BalanceItem{

    private static int IDcounter = 0;
    private int id;
    private String name;
    private Category category;
    private double amount;
    private LocalDateTime time;

    public Outcome(){

    }

    public Outcome(String name, Category category, double amount, LocalDateTime time){
        IDcounter++;
        this.id = IDcounter;
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.time = time;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public Category getCategory() {
        return category;
    }

    @Override
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public void setLocalDateTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public LocalDateTime getLocalDateTime() {
        return time;
    }
}