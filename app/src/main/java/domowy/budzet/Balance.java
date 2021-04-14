package domowy.budzet;

import java.util.ArrayList;

public class Balance {

    private ArrayList<Income> listOfIncomes;
    private ArrayList<Outcome> listOfOutcomes;

    public Balance(){
        initAllLists();
    }
    private void initAllLists(){
        if(listOfIncomes == null){
            listOfIncomes = new ArrayList<>();
        }
        if(listOfOutcomes == null){
            listOfOutcomes = new ArrayList<>();
        }
    }

    public void setListOfIncomes(ArrayList<Income> listOfIncomes) {
        this.listOfIncomes = listOfIncomes;
    }

    public void setListOfOutcomes(ArrayList<Outcome> listOfOutcomes) {
        this.listOfOutcomes = listOfOutcomes;
    }

    public ArrayList<Income> getListOfIncomes(){
        return listOfIncomes;
    }
    public void addIncomeToIncomeList(Income income){
        listOfIncomes.add(income);
    }
    public void removeIncomeFromIncomeList(Income income){
        listOfIncomes.remove(income);
    }
    public ArrayList<Outcome> getListOfOutcomes(){
        return listOfOutcomes;
    }
    public void addOutcomeToOutcomeList(Outcome outcome){
        listOfOutcomes.add(outcome);
    }
    public void removeOutcomeFromOutcomeList(Outcome outcome){
        listOfOutcomes.remove(outcome);
    }
}
