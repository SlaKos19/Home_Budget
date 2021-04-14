package domowy.budzet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;

public class PrognosisActivity extends AppCompatActivity {

    private AppPreferencesHelper sharedPrefs;
    private User user;
    private ArrayList<User> listOfUsers;
    private int userIndex;
    private TextView prognosisTitle, prognosisAmount;
    private DisplayMetrics displayMetrics = new DisplayMetrics();
    private ListView listView;
    private Context context;
    private RelativeLayout prognosisActivityRelLayWithOutcomeCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prognosis);

        loadData();
        initWidgets();
        calculatePrognosis();

    }

    private void loadData() {
        sharedPrefs = new AppPreferencesHelper(this, "Shared_Preferences");
        userIndex = sharedPrefs.getUserIndex();
        listOfUsers = sharedPrefs.getListOfUsers();
        user = listOfUsers.get(userIndex);
    }

    private void initWidgets() {
        prognosisTitle = (TextView) findViewById(R.id.prognosisTitle);
        prognosisAmount = (TextView) findViewById(R.id.prognosisAmount);
        listView = (ListView) findViewById(R.id.prognosisActivityIncomeListView);
        prognosisActivityRelLayWithOutcomeCategories = (RelativeLayout) findViewById(R.id.prognosisActivityRelLayWithOutcomeCategories);
    }

    private void calculatePrognosis() {
        Double[] tabOfAmounts = new Double[9];
        String[] tabOfCategories = {"Jedzenie i napoje", "Raty i kredyty", "Alkohol i papierosy"
                , "Rachunki i opłaty", "Paliwo i bilety", "Rozrywka", "Zakupy - ubrania", "Zakupy - kosmetyki", "Zakupy ogólne"};
        LocalDateTime dayToCountOutcomes = LocalDateTime.now().minusDays(30);
        double amountOfPrognosedOutcomes = 0.0;
        tabOfAmounts[0] = countAmountForCategory80procent(dayToCountOutcomes, "Jedzenie i napoje");
        tabOfAmounts[1] = user.getPersonalPreferences().getHowMuchIsPayingForCredits();
        tabOfAmounts[2] = user.getPersonalPreferences().getHowMuchIsPayingForSmoking();
        if ((dayToCountOutcomes.getMonthValue() >= 1 && dayToCountOutcomes.getDayOfMonth() <= 3) ||
                (dayToCountOutcomes.getMonthValue() >= 10 && dayToCountOutcomes.getMonthValue() <= 12)) {
            tabOfAmounts[3] = user.getPersonalPreferences().getAvgPayForBills() + user.getPersonalPreferences().getHowMuchIsPayingForHeating();
        } else {
            tabOfAmounts[3] = user.getPersonalPreferences().getAvgPayForBills();
        }
        tabOfAmounts[4] = user.getPersonalPreferences().getAvgPayForTransport();
        tabOfAmounts[5] = countAmountForCategory80procent(dayToCountOutcomes, "Rozrywka");
        tabOfAmounts[6] = countAmountForCategory80procent(dayToCountOutcomes, "Zakupy - ubrania");
        tabOfAmounts[7] = countAmountForCategory80procent(dayToCountOutcomes, "Zakupy - kosmetyki");
        tabOfAmounts[8] = countAmountForCategory80procent(dayToCountOutcomes, "Zakupy ogólne");

        for (int i = 0; i < tabOfAmounts.length; i++) {
            amountOfPrognosedOutcomes += tabOfAmounts[i];
        }
        prognosisAmount.setText("Spodziewane wydatki : " + amountOfPrognosedOutcomes + " zł");
        createAndDisplayListOfCategories(tabOfCategories, tabOfAmounts);

    }

    private double countAmountForCategory80procent(LocalDateTime dayToCountOutcomes, String categoryName) {
        double avgAmount = user.getPersonalPreferences().getAvgPayForFood();
        double amountOfLastMonthOutcomes = 0.0;
        double prognosedAmountOfOutcomes = 0.0; // ta co zwracam na koncu
        for (Outcome outcome : user.getBalance().getListOfOutcomes()) {
            if (outcome.getCategory().getName().equals(categoryName) && outcome.getLocalDateTime().isAfter(dayToCountOutcomes)) {
                amountOfLastMonthOutcomes = outcome.getAmount();
            }
        }
        if (amountOfLastMonthOutcomes <= avgAmount) {
            prognosedAmountOfOutcomes = avgAmount;
        } else { // PROG wieksze, AVG mniejsze
            // sprawdz o ile procent wiecej wydal w tym miesiacu niz srednia X
            // jesli o wiecej niz 20% zwroc srednia -20%
            // jesli o mniej niz 20%, zwroc srednia pomniejszona o ta wartosc
            double procent = amountOfLastMonthOutcomes / avgAmount;
            if (procent >= 1.2) {
                return avgAmount * 0.8;
            } else {
                return avgAmount - (avgAmount * (procent - 1));
            }
        }
        return prognosedAmountOfOutcomes;
    }

    private void createAndDisplayListOfCategories(String[] categoriesNames, Double[] categoriesAmounts) {
        CustomListAdapterOutcome adapter = new CustomListAdapterOutcome(this, categoriesNames, categoriesAmounts);
        listView.setAdapter(adapter);

        context = getApplicationContext();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float pixels = context.getResources().getDisplayMetrics().density;

        int baseValueForTitle = 45;
        int incrementationPerOneItem = 0;
        for (int i = 0; i < categoriesNames.length + 1; i++) {
            ViewGroup.LayoutParams params = prognosisActivityRelLayWithOutcomeCategories.getLayoutParams(); // 45 napis // 80 item
            params.height = (int) ((baseValueForTitle + incrementationPerOneItem) * pixels);
            params.width = displayMetrics.widthPixels;
            prognosisActivityRelLayWithOutcomeCategories.setLayoutParams(params);
            incrementationPerOneItem += 82;
        }
    }
}