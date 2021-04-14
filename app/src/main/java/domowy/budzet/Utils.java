package domowy.budzet;

import java.util.ArrayList;

public class Utils {

    private static final String TAG = "Utils";

    public static ArrayList<User> listOfUsers;
    public ArrayList<Category> listOfIncomeCategories;
    public ArrayList<Category> listOfOutcomesCategories;
    public static ArrayList<String> listOfQuestionsForUserDuringCreation;


    public Utils() {
        initializeAll();
    }

    private void initializeAll() {
        if (listOfUsers == null) {
            listOfUsers = new ArrayList<>();
        }
        if (listOfIncomeCategories == null) {
            listOfIncomeCategories = new ArrayList<>();
            listOfIncomeCategories.add(new Category("Wyplata"));
            listOfIncomeCategories.add(new Category("Przychody finansowe"));
            listOfIncomeCategories.add(new Category("Odsetki"));
            listOfIncomeCategories.add(new Category("Prezent"));

        }
        if (listOfOutcomesCategories == null) {
            listOfOutcomesCategories = new ArrayList<>();
            listOfOutcomesCategories.add(new Category("Jedzenie i napoje"));
            listOfOutcomesCategories.add(new Category("Raty i kredyty"));
            listOfOutcomesCategories.add(new Category("Rachunki i opłaty"));
            listOfOutcomesCategories.add(new Category("Alkohol i papierosy"));
            listOfOutcomesCategories.add(new Category("Paliwo i bilety"));
            listOfOutcomesCategories.add(new Category("Rozrywka"));
            listOfOutcomesCategories.add(new Category("Zakupy - ubrania"));
            listOfOutcomesCategories.add(new Category("Zakupy - kosmetyki"));
            listOfOutcomesCategories.add(new Category("Zakupy ogólne"));
        }
        if (listOfQuestionsForUserDuringCreation == null) {
            listOfQuestionsForUserDuringCreation = new ArrayList<>();
            listOfQuestionsForUserDuringCreation.add("Jak masz na imie, bądź jaki masz pseudonim ?");
            listOfQuestionsForUserDuringCreation.add("Jaki jest Twój całkowity dochód netto miesięcznie ?");
            listOfQuestionsForUserDuringCreation.add("Czy planujesz oszczędzac ?");
            listOfQuestionsForUserDuringCreation.add("Ile pieniędzy chciałbyś oszczędzać miesięcznie ?");
            listOfQuestionsForUserDuringCreation.add("Czy w sezonie zimowym ponosisz koszty ogrzewania mieszkania ?");
            listOfQuestionsForUserDuringCreation.add("Ile miesięcznie wydajesz na ogrzewanie w sezonie zimowym ?");
            listOfQuestionsForUserDuringCreation.add("Czy posiadasz kredyty / raty które spłacasz ?");
            listOfQuestionsForUserDuringCreation.add("Ile miesięcznie pieniędzy przeznaczasz na spłatę kredytów / rat ?");
            listOfQuestionsForUserDuringCreation.add("Czy palisz papierosy bądź tyton ?");
            listOfQuestionsForUserDuringCreation.add("Ile miesięcznie pieniędzy przeznaczasz na papierosy bądz tytoń ?");
            listOfQuestionsForUserDuringCreation.add("Ile średnio wydajesz miesięcznie na rachunku ( nie licz ogrzewania )");
            listOfQuestionsForUserDuringCreation.add("Ile średnio wydajesz miesięcznie na jedzenie ?");
            listOfQuestionsForUserDuringCreation.add("Ile średnio wydajesz miesięcznie na paliwo bądź transport publiczny");
            listOfQuestionsForUserDuringCreation.add("Ile średnio wydajesz miesięcznie na rozrywkę ?");
            listOfQuestionsForUserDuringCreation.add("Ile średnio wydajesz miesięcznie na ubrania ?");
            listOfQuestionsForUserDuringCreation.add("Ile średnio wydajesz miesięcznie na kosmetyki oraz środki czystości ?");
        }
    }

    public ArrayList<User> getListOfUsers() {
        return listOfUsers;
    }

    public void addUserToListOfUsers(User user) {
        listOfUsers.add(user);
    }

    public ArrayList<String> getListOfQuestionForUserDuringCreation() {
        return listOfQuestionsForUserDuringCreation;
    }

    public void setListOfUsers(ArrayList<User> listOfUsers) {
        this.listOfUsers = listOfUsers;
    }
    public ArrayList<Category> getListOfBasicIncomeCategories(){
        return listOfIncomeCategories;
    }
    public ArrayList<Category> getListOfBasicOutcomeCategories(){
        return listOfOutcomesCategories;
    }
}
