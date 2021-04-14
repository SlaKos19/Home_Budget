package domowy.budzet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {
    private static final String TAG = "ReportActivity myLogs";

    private AppPreferencesHelper sharedPrefs;
    private int userIndex;
    private ArrayList<User> listOfUsers;
    private ArrayList<Category> listOfUserIncomeCategories, listOfUserOutcomeCategories;
    private Utils util;
    private User user;

    private Context context;
    private DisplayMetrics displayMetrics = new DisplayMetrics();

    private Dialog welcomeDialog;
    private Button reportActivityButtonChange;
    private TextView reportActivityTitle, reportActivityAllOutcomeAmount, reportActivityAllIncomeAmount, reportActivityBalanceAmount;
    private ListView listViewIncome, listViewOutcome;
    private RelativeLayout reportActivityRelLayWithIncomeCategories, reportActivityRelLayWithOutcomeCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        loadData();
        initWidgets();
        setOnClickListeners();
        generateAndShowDialog();

    }
    private void initWidgets(){
        reportActivityButtonChange = (Button) findViewById(R.id.reportActivityButtonChange);
        reportActivityTitle = (TextView) findViewById(R.id.reportActivityTitle);
        reportActivityAllOutcomeAmount = (TextView) findViewById(R.id.reportActivityAllOutcomeAmount);
        reportActivityAllIncomeAmount = (TextView) findViewById(R.id.reportActivityAllIncomeAmount);
        reportActivityBalanceAmount = (TextView) findViewById(R.id.reportActivityBalanceAmount);

        listViewIncome = (ListView) findViewById(R.id.reportActivityIncomeListView);
        listViewOutcome = (ListView) findViewById(R.id.reportActivityOutcomeListView);

        reportActivityRelLayWithIncomeCategories = (RelativeLayout) findViewById(R.id.reportActivityRelLayWithIncomeCategories);
        reportActivityRelLayWithOutcomeCategories = (RelativeLayout) findViewById(R.id.reportActivityRelLayWithOutcomeCategories);
    }

    private void loadData(){
        sharedPrefs = new AppPreferencesHelper(this, "Shared_Preferences");
        userIndex = sharedPrefs.getUserIndex();
        listOfUsers = sharedPrefs.getListOfUsers();
        Log.d(TAG, "loadData - myLogs : userIndex = " + userIndex);
        Log.d(TAG, "loadData - myLogs : listOfUsers.size(); = " + listOfUsers.size());
        util = new Utils();
        util.setListOfUsers(listOfUsers);
        Log.d(TAG, "loadData - myLogs : util.getListOfUsers.size(); = " + util.getListOfUsers().size());
        user = listOfUsers.get(userIndex);
    }

    private void setOnClickListeners(){
        reportActivityButtonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateAndShowDialog();
            }
        });
    }

    private void generateReport(int amountOfDaysToReport){
        LocalDateTime dayToGenerateFrom = LocalDateTime.now().minusDays(amountOfDaysToReport);
        reportActivityTitle.setText("Raport z ostatnich " + amountOfDaysToReport + " dni");
        listOfUserIncomeCategories = new ArrayList<>();
        listOfUserIncomeCategories.addAll(user.getListOfIncomeCategories());
        ArrayList<SummaryOfUserBalance> listOfUserIncomeBalanceSummary = new ArrayList<>();
        for (Category category : listOfUserIncomeCategories){
            String categoryName = category.getName();
            double amount = 0;
            for (Income income : user.getBalance().getListOfIncomes()){
                if(income.getCategory().getName().equals(category.getName()) && income.getLocalDateTime().isAfter(dayToGenerateFrom)){
                    amount += income.getAmount();
                }
            }
            if(amount == 0){
                continue;
            } else {
                SummaryOfUserBalance objectOfUserData = new SummaryOfUserBalance(categoryName, amount);
                listOfUserIncomeBalanceSummary.add(objectOfUserData);
            }
        }
        listOfUserOutcomeCategories = new ArrayList<>();
        listOfUserOutcomeCategories.addAll(user.getListOfOutcomeCategories());
        ArrayList<SummaryOfUserBalance> listOfUserOutcomeBalanceSummary = new ArrayList<>();
        for (Category category : listOfUserOutcomeCategories){
            String categoryName = category.getName();
            double amount = 0;
            for (Outcome outcome : user.getBalance().getListOfOutcomes()){
                if(outcome.getCategory().getName().equals(category.getName()) && outcome.getLocalDateTime().isAfter(dayToGenerateFrom)){
                    amount += outcome.getAmount();
                }
            }
            if(amount == 0){
                continue;
            } else {
                SummaryOfUserBalance objectOfUserData = new SummaryOfUserBalance(categoryName, amount);
                listOfUserOutcomeBalanceSummary.add(objectOfUserData);
            }
        }

        double sumOfIncomes = 0.0;
        for (SummaryOfUserBalance item : listOfUserIncomeBalanceSummary){
            sumOfIncomes += item.getAmount();
        }
        String sumOfIncomesString = String.format("%.2f", sumOfIncomes);
        reportActivityAllIncomeAmount.setText(sumOfIncomesString);
        reportActivityAllIncomeAmount.setTextColor(ContextCompat.getColor(ReportActivity.this, R.color.colorGreen));
        double sumOfOutcomes = 0.0;
        for (SummaryOfUserBalance item : listOfUserOutcomeBalanceSummary){
            sumOfOutcomes += item.getAmount();
        }
        String sumOfOutcomesString = String.format("%.2f", sumOfOutcomes);
        reportActivityAllOutcomeAmount.setText("- " + sumOfOutcomesString);
        reportActivityAllOutcomeAmount.setTextColor(ContextCompat.getColor(ReportActivity.this, R.color.colorRed));
        double balance = sumOfIncomes - sumOfOutcomes;
        String balanceString = String.format("%.2f", balance);
        if(balance > 0){
            reportActivityBalanceAmount.setText("+ " + balanceString);
            reportActivityBalanceAmount.setTextColor(ContextCompat.getColor(ReportActivity.this, R.color.colorGreen));
        } else if (balance < 0){
            reportActivityBalanceAmount.setText(balanceString);
            reportActivityBalanceAmount.setTextColor(ContextCompat.getColor(ReportActivity.this, R.color.colorRed));
        } else {
            reportActivityBalanceAmount.setText(balanceString);
        }

        String[] categoryNamesIncome = new String[listOfUserIncomeBalanceSummary.size()];
        Double[] balanceValueIncome = new Double[listOfUserIncomeBalanceSummary.size()];
        for (int i = 0; i < categoryNamesIncome.length; i++) {
            categoryNamesIncome[i] = listOfUserIncomeBalanceSummary.get(i).getCategoryName();
            balanceValueIncome[i] = listOfUserIncomeBalanceSummary.get(i).getAmount();
        }
        String[] categoryNamesOutcome = new String[listOfUserOutcomeBalanceSummary.size()];
        Double[] balanceValueOutcome = new Double[listOfUserOutcomeBalanceSummary.size()];
        for (int i = 0; i < categoryNamesOutcome.length; i++) {
            categoryNamesOutcome[i] = listOfUserOutcomeBalanceSummary.get(i).getCategoryName();
            balanceValueOutcome[i] = listOfUserOutcomeBalanceSummary.get(i).getAmount();
        }
        createIncomeList(categoryNamesIncome, balanceValueIncome);
        createOutcomeList(categoryNamesOutcome, balanceValueOutcome);

    }

    private void createIncomeList(String[] categoryNamesIncome, Double[] balanceValueIncome){
        CustomListAdapterIncome adapter = new CustomListAdapterIncome(this, categoryNamesIncome, balanceValueIncome);
        listViewIncome.setAdapter(adapter);

        context = getApplicationContext();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float pixels =  context.getResources().getDisplayMetrics().density;

        int baseValueForTitle = 45;
        int incrementationPerOneItem = 0;
        for (int i = 0; i < categoryNamesIncome.length+1; i++) {
            ViewGroup.LayoutParams params = reportActivityRelLayWithIncomeCategories.getLayoutParams(); // 45 napis // 80 item
            params.height = (int) ((baseValueForTitle + incrementationPerOneItem) * pixels);
            params.width = displayMetrics.widthPixels;
            reportActivityRelLayWithIncomeCategories.setLayoutParams(params);
            incrementationPerOneItem += 81;
        }
    }

    private void createOutcomeList(String[] categoryNamesOutcome, Double[] balanceValueOutcome){
        CustomListAdapterOutcome adapter = new CustomListAdapterOutcome(this, categoryNamesOutcome, balanceValueOutcome);
        listViewOutcome.setAdapter(adapter);

        context = getApplicationContext();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float pixels =  context.getResources().getDisplayMetrics().density;

        int baseValueForTitle = 45;
        int incrementationPerOneItem = 0;
        for (int i = 0; i < categoryNamesOutcome.length+1; i++) {
            ViewGroup.LayoutParams params = reportActivityRelLayWithOutcomeCategories.getLayoutParams(); // 45 napis // 80 item
            params.height = (int) ((baseValueForTitle + incrementationPerOneItem) * pixels);
            params.width = displayMetrics.widthPixels;
            reportActivityRelLayWithOutcomeCategories.setLayoutParams(params);
            incrementationPerOneItem += 81;
        }
    }

    private void generateAndShowDialog(){
        welcomeDialog = new Dialog(this);
        welcomeDialog.setContentView(R.layout.choosing_report_dialog);
        welcomeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        welcomeDialog.setCancelable(false);

        Button reportDialogButton7days = (Button) welcomeDialog.findViewById(R.id.reportDialogButton7days);
        Button reportDialogButton30days = (Button) welcomeDialog.findViewById(R.id.reportButtonDialog30days);
        Button reportDialogButton90days = (Button) welcomeDialog.findViewById(R.id.reportDialogButton90days);
        Button reportDialogButton365days = (Button) welcomeDialog.findViewById(R.id.reportDialogButton365days);

        reportDialogButton7days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateReport(7);
                welcomeDialog.dismiss();
            }
        });
        reportDialogButton30days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateReport(30);
                welcomeDialog.dismiss();
            }
        });
        reportDialogButton90days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateReport(90);
                welcomeDialog.dismiss();
            }
        });
        reportDialogButton365days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateReport(365);
                welcomeDialog.dismiss();
            }
        });

        welcomeDialog.show();
    }
}
