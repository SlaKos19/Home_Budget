package domowy.budzet;

import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class User {

    private static int IDcounter = 0;
    private int userId;
    private String userName;
    private double income;
    private Balance balance;
    private PersonalPreferences personalPreferences;
    private ArrayList<Category> listOfIncomeCategories;
    private ArrayList<Category> listOfOutcomeCategories;

    public User(Balance balance, PersonalPreferences personalPreferences){
        Utils util = new Utils();
        for (User user : util.getListOfUsers()){
            if(user.getUserId() == IDcounter){
                IDcounter++;
            }
        }
        this.userId = IDcounter;
        this.balance = balance;
        this.personalPreferences = personalPreferences;
        listOfIncomeCategories = util.getListOfBasicIncomeCategories();
        listOfOutcomeCategories = util.getListOfBasicOutcomeCategories();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public PersonalPreferences getPersonalPreferences() {
        return personalPreferences;
    }

    public void setPersonalPreferences(PersonalPreferences personalPreferences) {
        this.personalPreferences = personalPreferences;
    }

    public ArrayList<Category> getListOfIncomeCategories() {
        return listOfIncomeCategories;
    }

    public void setListOfIncomeCategories(ArrayList<Category> listOfIncomeCategories) {
        this.listOfIncomeCategories = listOfIncomeCategories;
    }

    public ArrayList<Category> getListOfOutcomeCategories() {
        return listOfOutcomeCategories;
    }

    public void setListOfOutcomeCategories(ArrayList<Category> listOfOutcomeCategories) {
        this.listOfOutcomeCategories = listOfOutcomeCategories;
    }
}
