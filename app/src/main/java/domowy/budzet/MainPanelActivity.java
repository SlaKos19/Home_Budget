package domowy.budzet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;

import java.util.List;

public class MainPanelActivity extends AppCompatActivity {
    private static final String TAG = "MainPanelActi myLogs";

    private DisplayMetrics displayMetrics = new DisplayMetrics();
    private CardView cardViewExpenditures;
    private Button btnChangeUser, btnAddOutcomeOK, btnAddOutcomeCancel, creatingNewIncomeButtonCancel, creatingNewIncomeButtonOK
                        ,mainPanelButtonAddIncome, mainPanelButtonAddOutcome, creatingNewCategoryButtonCancel, creatingNewCategoryButtonOK
                            ,dialogSelectCategoryButtonCreateNewCategory;
    private TextView textViewPodsumowanie, textViewTenTydzien, textViewDochody, textViewWydatki, textViewBilans,
                        textViewDochodyDouble, textViewWydatkiDouble, textViewBilansDouble, textViewUserName
                            ,textViewCategoryName, creatingNewIncomeCategoryName;

    private EditText editTextAddOutcomeAmount, editTextAddOutcomeName, creatingNewIncomeAmount, creatingNewIncomeName, creatingNewCategoryName;

    private boolean isCategoryChosen;
    private int indexOfSelectedCategory;
    private int userIndex;

    private ArrayList<User> listOfUsers;
    private User user;

    private RelativeLayout relLayIncomeList, relLayOutcomeList, relLayBarChart, relLayWithCategoriesOutcome, creatingNewUserRelLayWithCategoryIncome;
    private ScrollView mainPanelScrollView;

    private RecyclerView incomeListRecyclerView, outcomeListRecyclerView;
    private Utils util;
    private Context context;
    private BarChart barChart;
    public AppPreferencesHelper sharedPrefs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_panel);
        AndroidThreeTen.init(this);

        loadData();
        initWidgets();
        setParametersOfLayouts();
        setRecyclerViews();
        setOnClickListeners();
        setData();
        generateBarChart();

    }

    private void initWidgets(){
        relLayOutcomeList = (RelativeLayout) findViewById(R.id.mainPanelRelLayOutcomeList);
        relLayIncomeList = (RelativeLayout) findViewById(R.id.mainPanelRelLayIncomeList);
        relLayBarChart = (RelativeLayout) findViewById(R.id.mainPanelRelLayBarChart);

        mainPanelScrollView = findViewById(R.id.mainPanelScrollView);

        cardViewExpenditures = (CardView) findViewById(R.id.mainPanelCardViewExpendituresGraph);

        textViewPodsumowanie = (TextView) findViewById(R.id.mainPanelTextViewPodsumowanie);
        textViewTenTydzien = (TextView) findViewById(R.id.mainPanelTextViewTenTydzien);
        textViewDochody = (TextView) findViewById(R.id.mainPanelTextViewDochody);
        textViewWydatki = (TextView) findViewById(R.id.mainPanelTextViewWydatki);
        textViewBilans = (TextView) findViewById(R.id.mainPanelTextViewBilans);
        textViewDochodyDouble = (TextView) findViewById(R.id.mainPanelTextViewDochodyDouble);
        textViewWydatkiDouble = (TextView) findViewById(R.id.mainPanelTextViewWydatkiDouble);
        textViewBilansDouble = (TextView) findViewById(R.id.mainPanelTextViewBilansDouble);
        textViewUserName = (TextView) findViewById(R.id.mainPanelTextViewUserName);

        btnChangeUser = (Button) findViewById(R.id.mainPanelButtonChangeUser);
        incomeListRecyclerView = (RecyclerView) findViewById(R.id.mainPanelIncomeRecView);
        outcomeListRecyclerView = (RecyclerView) findViewById(R.id.mainPanelOutcomeRecView);
        mainPanelButtonAddIncome = (Button) findViewById(R.id.mainPanelButtonAddIncome);
        mainPanelButtonAddOutcome = (Button) findViewById(R.id.mainPanelButtonAddOutcome);
    }

    private void setRecyclerViews(){

        user = util.getListOfUsers().get(sharedPrefs.getUserIndex());
        RecyclerViewAdapter adapterIncome = new RecyclerViewAdapter(this);
        incomeListRecyclerView.setAdapter(adapterIncome);
        incomeListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapterIncome.notifyDataSetChanged();
        ArrayList<BalanceItem> listOfIncomes = new ArrayList<>();
        listOfIncomes.addAll(user.getBalance().getListOfIncomes());
        listOfIncomes = createListWithItemsFromThisWeekOnly(listOfIncomes);
        adapterIncome.setListOfItems(listOfIncomes);

        RecyclerViewAdapter adapterOutcome = new RecyclerViewAdapter(this);
        outcomeListRecyclerView.setAdapter(adapterOutcome);
        outcomeListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapterOutcome.notifyDataSetChanged();
        ArrayList<BalanceItem> listOfOutcomes = new ArrayList<>();
        listOfOutcomes.addAll(user.getBalance().getListOfOutcomes());
        listOfOutcomes = createListWithItemsFromThisWeekOnly(listOfOutcomes);
        adapterOutcome.setListOfItems(listOfOutcomes);

    }

    private void setData(){
        textViewUserName.setText("Witaj " + user.getUserName() + " !");

        ArrayList<BalanceItem> listOfIncomes = new ArrayList<>();
        ArrayList<BalanceItem> listOfOutcomes = new ArrayList<>();
        listOfIncomes.addAll(user.getBalance().getListOfIncomes());
        listOfOutcomes.addAll(user.getBalance().getListOfOutcomes());

        listOfIncomes = createListWithItemsFromThisWeekOnly(listOfIncomes);
        listOfOutcomes = createListWithItemsFromThisWeekOnly(listOfOutcomes);

        double incomeDouble = 0.0;
        for (BalanceItem item : listOfIncomes){
            incomeDouble += item.getAmount();
        }

        String income = String.format("%.2f", incomeDouble);
        textViewDochodyDouble.setText(income + " zł");

        double outcomeDouble = 0.0;
        for (BalanceItem item : listOfOutcomes){
            outcomeDouble += item.getAmount();
        }
        String outcome = String.format("%.2f", outcomeDouble);
        textViewWydatkiDouble.setText(outcome + " zł");
        double bilansDouble = incomeDouble - outcomeDouble;
        String bilans = String.format("%.2f", bilansDouble);
        if(bilansDouble > 0){
            textViewBilansDouble.setText("+ " + bilans + " zł");
            textViewBilansDouble.setTextColor(ContextCompat.getColor(context, R.color.colorGreen));
        } else if(bilansDouble < 0){
            textViewBilansDouble.setText(bilans + " zł");
            textViewBilansDouble.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
        } else {
            textViewBilansDouble.setText(bilans + " zł");
        }
    }

    private void setOnClickListeners(){
        btnChangeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPanelActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        mainPanelButtonAddIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createIncomeDialogNumberTwo();
            }
        });
        mainPanelButtonAddOutcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createOutcomeDialogNumberTwo();
            }
        });
    }

    private void setParametersOfLayouts(){
        context = getApplicationContext();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float pixels =  context.getResources().getDisplayMetrics().density;

        ArrayList<BalanceItem> listOfOutcomes = new ArrayList<>();
        listOfOutcomes.addAll(user.getBalance().getListOfOutcomes());
        listOfOutcomes = createListWithItemsFromThisWeekOnly(listOfOutcomes);

        if(listOfOutcomes.size() == 0){
            ViewGroup.LayoutParams params = relLayOutcomeList.getLayoutParams();
            params.height = (int) (40 * pixels);
            params.width = displayMetrics.widthPixels;
            relLayOutcomeList.setLayoutParams(params);
        } else if(listOfOutcomes.size() == 1){
            ViewGroup.LayoutParams params = relLayOutcomeList.getLayoutParams();
            params.height = (int) (115 * pixels);
            params.width = displayMetrics.widthPixels;
            relLayOutcomeList.setLayoutParams(params);
        } else if(listOfOutcomes.size() == 2){
            ViewGroup.LayoutParams params = relLayOutcomeList.getLayoutParams();
            params.height = (int) (200 * pixels);
            params.width = displayMetrics.widthPixels;
            relLayOutcomeList.setLayoutParams(params);
        }
        else if(listOfOutcomes.size() == 3){
            ViewGroup.LayoutParams params = relLayOutcomeList.getLayoutParams();
            params.height = (int) (285 * pixels);
            params.width = displayMetrics.widthPixels;
            relLayOutcomeList.setLayoutParams(params);
        }
        else if(listOfOutcomes.size() > 3){
            ViewGroup.LayoutParams params = relLayOutcomeList.getLayoutParams();
            params.height = (int) (370 * pixels);
            params.width = displayMetrics.widthPixels;
            relLayOutcomeList.setLayoutParams(params);
        }

        ArrayList<BalanceItem> listOfIncomes = new ArrayList<>();
        listOfIncomes.addAll(user.getBalance().getListOfIncomes());
        listOfIncomes = createListWithItemsFromThisWeekOnly(listOfIncomes);

        if(listOfIncomes.size() == 0){
            ViewGroup.LayoutParams params = relLayIncomeList.getLayoutParams();
            params.height = (int) (40 * pixels);
            params.width = displayMetrics.widthPixels;
            relLayIncomeList.setLayoutParams(params);
        }   else if(listOfIncomes.size() == 1){
            ViewGroup.LayoutParams params = relLayIncomeList.getLayoutParams();
            params.height = (int) (115 * pixels);
            params.width = displayMetrics.widthPixels;
            relLayIncomeList.setLayoutParams(params);
        } else if(listOfIncomes.size() == 2){
            ViewGroup.LayoutParams params = relLayIncomeList.getLayoutParams();
            params.height = (int) (200 * pixels);
            params.width = displayMetrics.widthPixels;
            relLayIncomeList.setLayoutParams(params);
        } else if(listOfIncomes.size() == 3){
            ViewGroup.LayoutParams params = relLayIncomeList.getLayoutParams();
            params.height = (int) (285 * pixels);
            params.width = displayMetrics.widthPixels;
            relLayIncomeList.setLayoutParams(params);
        }else if(listOfIncomes.size() > 3){
            ViewGroup.LayoutParams params = relLayIncomeList.getLayoutParams();
            params.height = (int) (370 * pixels);
            params.width = displayMetrics.widthPixels;
            relLayIncomeList.setLayoutParams(params);
        }

    }

    private ArrayList<BalanceItem> createListWithItemsFromThisWeekOnly(ArrayList<BalanceItem> list){
        ArrayList<BalanceItem> listOfOneWeekItems = new ArrayList<>();
        LocalDateTime currentMoment = LocalDateTime.now();
        for (BalanceItem item : list){
            if(item.getLocalDateTime().plusDays(7).isAfter(currentMoment)){
                listOfOneWeekItems.add(item);
            }
        }
        return listOfOneWeekItems;
    }

    private void generateBarChart(){

        ArrayList<BalanceItem> listOfOutcomes = new ArrayList<>();
        listOfOutcomes.addAll(user.getBalance().getListOfOutcomes());
        listOfOutcomes = createListWithItemsFromThisWeekOnly(listOfOutcomes);
        barChart = (BarChart) findViewById(R.id.mainPanelBarChart);

        if(listOfOutcomes.size() != 0 && listOfOutcomes != null){

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM");

            ArrayList listOfValues = new ArrayList();
            ArrayList day = new ArrayList();

            LocalDate today = LocalDate.now();
            LocalDate dayOfAnalysis;
            int index = 0;
            for (int i = 6; i >= 0; i--) {
                dayOfAnalysis = today.minusDays(i);
                double outcome = 0.0;
                for (BalanceItem item : listOfOutcomes){
                    if(dayOfAnalysis.getDayOfMonth() == item.getLocalDateTime().getDayOfMonth()){
                        outcome += item.getAmount();
                    }
                }
                listOfValues.add(new BarEntry((float)outcome, index));
                day.add(dayOfAnalysis.format(formatter));
                index++;
            }
            BarDataSet barDataSet = new BarDataSet(listOfValues, "Wydatki w ostatnich 7 dniach");
            barChart.animateY(3000);
            BarData data = new BarData(day, barDataSet);
            barDataSet.setColor((ContextCompat.getColor(context, R.color.colorRed)));
            barChart.setData(data);
        } else {
            context = getApplicationContext();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            float pixels =  context.getResources().getDisplayMetrics().density;
            ViewGroup.LayoutParams params = relLayBarChart.getLayoutParams();
            params.height = (int) (40 * pixels);
            params.width = displayMetrics.widthPixels;
            relLayBarChart.setLayoutParams(params);
        }
    }

    private void saveData(ArrayList<User> listOfUsers){
        sharedPrefs = new AppPreferencesHelper(this, "Shared_Preferences");
        sharedPrefs.setListOfUsers(listOfUsers);
    }

    private void loadData() {
        sharedPrefs = new AppPreferencesHelper(this, "Shared_Preferences");
        userIndex = sharedPrefs.getUserIndex();
        listOfUsers = sharedPrefs.getListOfUsers();
        Log.d(TAG, "loadData - myLogs : userIndex = " + userIndex);
        Log.d(TAG, "loadData - myLogs : listOfUsers.size(); = " + listOfUsers.size());
        util = new Utils();
        util.setListOfUsers(listOfUsers);
        Log.d(TAG, "loadData - myLogs : util.getListOfUsers.size(); = " + util.getListOfUsers().size());
        user = listOfUsers.get(userIndex);
        Log.d(TAG, "loadData: myLogs outcomes size = " + listOfUsers.get(0).getBalance().getListOfOutcomes().size());
    }

    private void createOutcomeDialogNumberOne(int categoryIndex){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.creating_new_outcome_dialog, null);
        btnAddOutcomeOK = view.findViewById(R.id.creatingNewOutcomeButtonOK);
        btnAddOutcomeCancel = view.findViewById(R.id.creatingNewOutcomeButtonCancel);
        relLayWithCategoriesOutcome = view.findViewById(R.id.creatingNewUserRelLayWithCategoryOutcome);
        editTextAddOutcomeAmount = view.findViewById(R.id.creatingNewOutcomeAmount);
        editTextAddOutcomeName = view.findViewById(R.id.creatingNewOutcomeName);
        textViewCategoryName = view.findViewById(R.id.creatingNewOutcomeCategoryName);

        alertBuilder.setView(view);
        AlertDialog dialog = alertBuilder.create();

        if(categoryIndex >= 0){ // Jesli metoda zostala wywolania z wybrana konkretna kategoria
            textViewCategoryName.setText(user.getListOfOutcomeCategories().get(categoryIndex).getName());
            btnAddOutcomeOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isEditTextCorrectDouble = Validation.isEditTextCorrectDouble(editTextAddOutcomeAmount.getText().toString());
                    if(isEditTextCorrectDouble){
                        LocalDateTime currentMoment = LocalDateTime.now();
                        Outcome outcome = new Outcome(editTextAddOutcomeName.getText().toString()
                                ,user.getListOfOutcomeCategories().get(categoryIndex)
                                ,Double.valueOf(editTextAddOutcomeAmount.getText().toString()), currentMoment);
                        for (User user2 :
                                util.getListOfUsers()) {
                            if(user2.getUserId() == user.getUserId()){
                                Log.d(TAG, "myLogs AddingNewOutcome: "+user2.getBalance().getListOfOutcomes().size());
                                user2.getBalance().addOutcomeToOutcomeList(outcome);
                                checkIfUserIsNotSpendingTooMuchOnSingleCategory(user2, outcome);
                                int result = checkIfThereArePotentialSavingCrossingWarnings(user2);
                                if(result == 0){
                                    continue;
                                } else if (result == 1){
                                    showWarningAboutSpendingMoreThanEarning();
                                    Log.d(TAG, "onClick: myLogs Crossing Spending Limit");
                                } else if (result == 2){
                                    Log.d(TAG, "onClick: myLogs Spending more than earning");
                                    showWarningAboutCrossingSpendingLimit();
                                }
                                Log.d(TAG, "myLogs AddingNewOutcome: "+user2.getBalance().getListOfOutcomes().size());
                            }
                        }
                        listOfUsers = util.getListOfUsers();
                        saveData(listOfUsers);
                        setRecyclerViews();
                        generateBarChart();
                        setData();
                        setParametersOfLayouts();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(MainPanelActivity.this, "Wprowadzono zla wartosc", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            btnAddOutcomeCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            relLayWithCategoriesOutcome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createOutcomeDialogNumberTwo();
                    dialog.dismiss();
                }
            });
        } else { // Metoda jest wywolywana po raz pierwszy i trzeba wybrac kategorie
            btnAddOutcomeOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainPanelActivity.this, "Nie wybrano kategorii", Toast.LENGTH_SHORT).show();
                }
            });
            relLayWithCategoriesOutcome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createOutcomeDialogNumberTwo();
                    dialog.dismiss();
                }
            });
            btnAddOutcomeCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }

    private void createOutcomeDialogNumberTwo(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainPanelActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_select_category, null);
        dialogSelectCategoryButtonCreateNewCategory = mView.findViewById(R.id.dialogSelectCategoryButtonCreateNewCategory);
        List<Category> listOfCategory = user.getListOfOutcomeCategories();
        List<String> listOfCategoryNames = new ArrayList<>();
        for(Category category : listOfCategory){
            listOfCategoryNames.add(category.getName());
        }
        ListView listView = (ListView) mView.findViewById(R.id.dialogSelectCategoryListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainPanelActivity.this
                , android.R.layout.simple_list_item_1, listOfCategoryNames);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        alertBuilder.setView(mView);
        AlertDialog alertDialog = alertBuilder.create();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                alertDialog.dismiss();
                createOutcomeDialogNumberOne(i);
            }
        });
        dialogSelectCategoryButtonCreateNewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                createOutcomeDialogNumberThree();
            }
        });
        alertDialog.show();
    }

    private void createOutcomeDialogNumberThree(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainPanelActivity.this);
        View mViewCreatingCat = getLayoutInflater().inflate(R.layout.creating_new_category_dialog, null);
        creatingNewCategoryName = mViewCreatingCat.findViewById(R.id.creatingNewCategoryName);
        creatingNewCategoryButtonCancel = mViewCreatingCat.findViewById(R.id.creatingNewCategoryButtonCancel);
        creatingNewCategoryButtonOK = mViewCreatingCat.findViewById(R.id.creatingNewCategoryButtonOK);
        alertBuilder.setView(mViewCreatingCat);
        AlertDialog dialogCreateCat = alertBuilder.create();

        creatingNewCategoryButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCreateCat.dismiss();
            }
        });

        creatingNewCategoryButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(creatingNewCategoryName.getText().toString().isEmpty()){

                } else {
                    Category category = new Category(creatingNewCategoryName.getText().toString());
                    ArrayList<Category> listOfCategories = user.getListOfOutcomeCategories();
                    listOfCategories.add(category);
                    user.setListOfOutcomeCategories(listOfCategories);
                    listOfUsers = util.getListOfUsers();
                    saveData(listOfUsers);
                    dialogCreateCat.dismiss();
                    createOutcomeDialogNumberTwo();
                }
            }
        });
        dialogCreateCat.show();
    }

    private void createIncomeDialogNumberOne(int categoryIndex){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.creating_new_income_dialog, null);
        creatingNewIncomeButtonOK = view.findViewById(R.id.creatingNewIncomeButtonOK);
        creatingNewIncomeButtonCancel = view.findViewById(R.id.creatingNewIncomeButtonCancel);
        creatingNewUserRelLayWithCategoryIncome = view.findViewById(R.id.creatingNewUserRelLayWithCategoryIncome);
        creatingNewIncomeAmount = view.findViewById(R.id.creatingNewIncomeAmount);
        creatingNewIncomeName = view.findViewById(R.id.creatingNewIncomeName);
        creatingNewIncomeCategoryName = view.findViewById(R.id.creatingNewIncomeCategoryName);

        alertBuilder.setView(view);
        AlertDialog dialog = alertBuilder.create();

        if(categoryIndex >= 0){ // Jesli metoda zostala wywolania z wybrana konkretna kategoria
            creatingNewIncomeCategoryName.setText(user.getListOfIncomeCategories().get(categoryIndex).getName());
            creatingNewIncomeButtonOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isEditTextCorrectDouble = Validation.isEditTextCorrectDouble(creatingNewIncomeAmount.getText().toString());
                    if(isEditTextCorrectDouble){
                        LocalDateTime currentMoment = LocalDateTime.now();
                        Income income = new Income(creatingNewIncomeName.getText().toString()
                                ,user.getListOfIncomeCategories().get(categoryIndex)
                                ,Double.valueOf(creatingNewIncomeAmount.getText().toString()), currentMoment);
                        for (User user2 :
                                util.getListOfUsers()) {
                            if(user2.getUserId() == user.getUserId()){
                                Log.d(TAG, "myLogs AddingNewIncome: "+user2.getBalance().getListOfIncomes().size());
                                user2.getBalance().addIncomeToIncomeList(income);
                                Log.d(TAG, "myLogs AddingNewIncome: "+user2.getBalance().getListOfIncomes().size());
                            }
                        }
                        listOfUsers = util.getListOfUsers();
                        saveData(listOfUsers);
                        setRecyclerViews();
                        generateBarChart();
                        setData();
                        setParametersOfLayouts();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(MainPanelActivity.this, "Wprowadzono zla wartosc", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            creatingNewIncomeButtonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            creatingNewUserRelLayWithCategoryIncome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createIncomeDialogNumberTwo();
                    dialog.dismiss();
                }
            });
        } else { // Metoda jest wywolywana po raz pierwszy i trzeba wybrac kategorie
            creatingNewIncomeButtonOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainPanelActivity.this, "Nie wybrano kategorii", Toast.LENGTH_SHORT).show();
                }
            });
            creatingNewUserRelLayWithCategoryIncome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createIncomeDialogNumberTwo();
                    dialog.dismiss();
                }
            });
            creatingNewIncomeButtonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }

    private void createIncomeDialogNumberTwo(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainPanelActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_select_category, null);
        dialogSelectCategoryButtonCreateNewCategory = mView.findViewById(R.id.dialogSelectCategoryButtonCreateNewCategory);
        List<Category> listOfCategory = user.getListOfIncomeCategories();
        List<String> listOfCategoryNames = new ArrayList<>();
        for(Category category : listOfCategory){
            listOfCategoryNames.add(category.getName());
        }
        ListView listView = (ListView) mView.findViewById(R.id.dialogSelectCategoryListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainPanelActivity.this
                , android.R.layout.simple_list_item_1, listOfCategoryNames);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        alertBuilder.setView(mView);
        AlertDialog alertDialog = alertBuilder.create();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                alertDialog.dismiss();
                createIncomeDialogNumberOne(i);
            }
        });
        dialogSelectCategoryButtonCreateNewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                createIncomeDialogNumberThree();
            }
        });
        alertDialog.show();
    }

    private void createIncomeDialogNumberThree(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainPanelActivity.this);
        View mViewCreatingCat = getLayoutInflater().inflate(R.layout.creating_new_category_dialog, null);
        creatingNewCategoryName = mViewCreatingCat.findViewById(R.id.creatingNewCategoryName);
        creatingNewCategoryButtonCancel = mViewCreatingCat.findViewById(R.id.creatingNewCategoryButtonCancel);
        creatingNewCategoryButtonOK = mViewCreatingCat.findViewById(R.id.creatingNewCategoryButtonOK);
        alertBuilder.setView(mViewCreatingCat);
        AlertDialog dialogCreateCat = alertBuilder.create();

        creatingNewCategoryButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCreateCat.dismiss();
            }
        });

        creatingNewCategoryButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(creatingNewCategoryName.getText().toString().isEmpty()){

                } else {
                    Category category = new Category(creatingNewCategoryName.getText().toString());
                    ArrayList<Category> listOfCategories = user.getListOfIncomeCategories();
                    listOfCategories.add(category);
                    user.setListOfIncomeCategories(listOfCategories);
                    listOfUsers = util.getListOfUsers();
                    saveData(listOfUsers);
                    dialogCreateCat.dismiss();
                    createIncomeDialogNumberTwo();
                }
            }
        });
        dialogCreateCat.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(TAG, "onBackPressed: myLogs started");
        saveData(listOfUsers);
        Log.d(TAG, "onBackPressed: myLogs outcomes size = " + listOfUsers.get(0).getBalance().getListOfOutcomes().size());
        Intent intent = new Intent(MainPanelActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: myLogs started");
        saveData(listOfUsers);
        Log.d(TAG, "onPause: myLogs outcomes size = " + listOfUsers.get(0).getBalance().getListOfOutcomes().size());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: myLogs started");
        saveData(util.getListOfUsers());
        Log.d(TAG, "onDestroy: myLogs outcomes size = " + util.getListOfUsers().get(0).getBalance().getListOfOutcomes().size());

    }

    private int checkIfThereArePotentialSavingCrossingWarnings(User user){
        double moneyToSpendSafely;
        double userSavingPreferences;
        double userMonthlyIncome = user.getIncome();
        if(user.getPersonalPreferences().isSaving()){
            userSavingPreferences = user.getPersonalPreferences().getHowMuchWantToSave();
            moneyToSpendSafely = userMonthlyIncome - userSavingPreferences;
        } else {
            moneyToSpendSafely = user.getIncome();
        }
        LocalDateTime dateOfOneMonthPast = LocalDateTime.now().minusDays(30);
        ArrayList<Outcome> listOfUserOutcomes = user.getBalance().getListOfOutcomes();
        double amountOfOutcomes = 0.0;
        for(Outcome outcome : listOfUserOutcomes){
            if(outcome.getLocalDateTime().isAfter(dateOfOneMonthPast)){
                amountOfOutcomes += outcome.getAmount();
            }
        }
        if(amountOfOutcomes > user.getIncome()){
            return 1; // One means that an user spent more than he is earning
        } else if(amountOfOutcomes > moneyToSpendSafely && user.getPersonalPreferences().isSaving()){
            return 2; // Two means that an user is not gonna save as much as he would like
        } else {
            return 0; // No point to warn user
        }
    }

    private void checkIfUserIsNotSpendingTooMuchOnSingleCategory(User user, Outcome lastOutcome){
        Category categoryOfCurrentOutcome = lastOutcome.getCategory();
        LocalDateTime dayOfLastMonth = LocalDateTime.now().minusDays(30);
        if(categoryOfCurrentOutcome.getName().equals("Jedzenie i napoje")){
            double amountOfAllOutcomes = 0.0;
            for (Outcome outcome : user.getBalance().getListOfOutcomes()){
                if(outcome.getLocalDateTime().isAfter(dayOfLastMonth)){
                    amountOfAllOutcomes += outcome.getAmount();
                }
            }
            if(amountOfAllOutcomes > user.getPersonalPreferences().getAvgPayForFood()){
                showWarningOfSpendingMoreThanUsuallOnSingleCategory(categoryOfCurrentOutcome.getName());
                return;
            }
        }
        if(categoryOfCurrentOutcome.getName().equals("Alkohol i papierosy")){
            double amountOfAllOutcomes = 0.0;
            for (Outcome outcome : user.getBalance().getListOfOutcomes()){
                if(outcome.getLocalDateTime().isAfter(dayOfLastMonth)){
                    amountOfAllOutcomes += outcome.getAmount();
                }
            }
            if(amountOfAllOutcomes > user.getPersonalPreferences().getHowMuchIsPayingForSmoking() && user.getPersonalPreferences().isSmoking()){
                showWarningOfSpendingMoreThanUsuallOnSingleCategory(categoryOfCurrentOutcome.getName());
                return;
            }
        }
        if(categoryOfCurrentOutcome.getName().equals("Paliwo i bilety")){
            double amountOfAllOutcomes = 0.0;
            for (Outcome outcome : user.getBalance().getListOfOutcomes()){
                if(outcome.getLocalDateTime().isAfter(dayOfLastMonth)){
                    amountOfAllOutcomes += outcome.getAmount();
                }
            }
            if(amountOfAllOutcomes > user.getPersonalPreferences().getAvgPayForTransport()){
                showWarningOfSpendingMoreThanUsuallOnSingleCategory(categoryOfCurrentOutcome.getName());
                return;
            }
        }
        if(categoryOfCurrentOutcome.getName().equals("Rozrywka")){
            double amountOfAllOutcomes = 0.0;
            for (Outcome outcome : user.getBalance().getListOfOutcomes()){
                if(outcome.getLocalDateTime().isAfter(dayOfLastMonth)){
                    amountOfAllOutcomes += outcome.getAmount();
                }
            }
            if(amountOfAllOutcomes > user.getPersonalPreferences().getAvgPayForEntertainment()){
                showWarningOfSpendingMoreThanUsuallOnSingleCategory(categoryOfCurrentOutcome.getName());
                return;
            }
        }
        if(categoryOfCurrentOutcome.getName().equals("Zakupy - ubrania")){
            double amountOfAllOutcomes = 0.0;
            for (Outcome outcome : user.getBalance().getListOfOutcomes()){
                if(outcome.getLocalDateTime().isAfter(dayOfLastMonth)){
                    amountOfAllOutcomes += outcome.getAmount();
                }
            }
            if(amountOfAllOutcomes > user.getPersonalPreferences().getAvgPayForCloths()){
                showWarningOfSpendingMoreThanUsuallOnSingleCategory(categoryOfCurrentOutcome.getName());
                return;
            }
        }
        if(categoryOfCurrentOutcome.getName().equals("Zakupy - kosmetyki")){
            double amountOfAllOutcomes = 0.0;
            for (Outcome outcome : user.getBalance().getListOfOutcomes()){
                if(outcome.getLocalDateTime().isAfter(dayOfLastMonth)){
                    amountOfAllOutcomes += outcome.getAmount();
                }
            }
            if(amountOfAllOutcomes > user.getPersonalPreferences().getAvgPayForCosmetics()){
                showWarningOfSpendingMoreThanUsuallOnSingleCategory(categoryOfCurrentOutcome.getName());
                return;
            }
        }

    }

    private void showWarningAboutCrossingSpendingLimit(){
        Dialog warningDialog = new Dialog(this);
        warningDialog.setContentView(R.layout.warning_crossing_savings_dialog);
        warningDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        CardView cardView = (CardView) warningDialog.findViewById(R.id.warning_crossing_savings_dialogCardView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                warningDialog.dismiss();
            }
        });
        warningDialog.show();
    }

    private void showWarningAboutSpendingMoreThanEarning(){
        Dialog warningDialog = new Dialog(this);
        warningDialog.setContentView(R.layout.warning_outcomes_higher_than_income_dialog);
        warningDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        CardView cardView = (CardView) warningDialog.findViewById(R.id.warning_outcomes_higher_than_income_dialogCardView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                warningDialog.dismiss();
            }
        });
        warningDialog.show();
    }

    private void showWarningOfSpendingMoreThanUsuallOnSingleCategory(String categoryName){
        Dialog warningDialog = new Dialog(this);
        warningDialog.setContentView(R.layout.warning_spending_too_much_on_single_cat_dialog);
        warningDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView warningMessage = warningDialog.findViewById(R.id.warningMessage);
        CardView cardView = (CardView) warningDialog.findViewById(R.id.warningSpendingTooMuchOnSingleCatCardView);
        warningMessage.setText("Twój ostatni wydatek na " + categoryName + " przekroczył średnią miesięczną jaką przeznaczasz na" +
                " tę kategorię. Zalecamy ograniczenie wydatków na "+ categoryName);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                warningDialog.dismiss();
            }
        });
        warningDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.itemRaport:
                Intent intent = new Intent(MainPanelActivity.this, ReportActivity.class);
                startActivity(intent);
                return true;
            case R.id.itemPrognosis:
                Intent intent2 = new Intent(MainPanelActivity.this, PrognosisActivity.class);
                startActivity(intent2);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
