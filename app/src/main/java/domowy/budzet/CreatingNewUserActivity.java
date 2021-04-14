package domowy.budzet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.ArrayList;
import java.util.HashMap;

import static java.security.AccessController.getContext;

public class CreatingNewUserActivity extends AppCompatActivity implements
        FragmentCreatingUserWithEditText.FragmentListener
        ,FragmentCreatingUserWithButtons.FragmentListener{

    private static final String TAG = "myLogs CreatingUserAct";

    private Dialog welcomeDialog;
    private Button btnOK;

    private ArrayList<String> listOfQuestionsForUserDuringCreation;
    private ArrayList<User> listOfUsers;

    private HashMap<Integer, Boolean> listOfBooleanAnswers;
    private HashMap<Integer, String> listOfEditTextAnswers;

    private int userIndex;

    private Utils util;

    int[] questionsWithButtons = {2,4,6,8};
    int[] questionsWithEdit = {0,1,3,5,7,9,10,11,12,13,14,15};

    private AppPreferencesHelper sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_new_user);
        AndroidThreeTen.init(this);

        createAndDisplayDialog();
        prepareListsForInterview();
        setFragment(0);



    }

    public void createAndDisplayDialog(){
        welcomeDialog = new Dialog(this);
        welcomeDialog.setContentView(R.layout.create_user_welcome_dialog);
        btnOK = (Button) welcomeDialog.findViewById(R.id.createUserDialogBtnOk);
        welcomeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        welcomeDialog.setCancelable(false);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                welcomeDialog.dismiss();
            }
        });
        welcomeDialog.show();
    }

    public void setFragment(int questionIndex){
        if(questionIndex == listOfQuestionsForUserDuringCreation.size()){
            creatingNewUser();
            Intent intent = new Intent(CreatingNewUserActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            for (int number : questionsWithButtons){
                if(number == questionIndex){
                    FragmentCreatingUserWithButtons fragment = FragmentCreatingUserWithButtons.newInstance(listOfQuestionsForUserDuringCreation.get(questionIndex), questionIndex);
                    getSupportFragmentManager().beginTransaction().replace(R.id.createNewUserContainer, fragment).commit();
                    break;
                }
                for (int number2 : questionsWithEdit){
                    if(number2 == questionIndex){
                        FragmentCreatingUserWithEditText fragment = FragmentCreatingUserWithEditText.newInstance(listOfQuestionsForUserDuringCreation.get(questionIndex), questionIndex);
                        getSupportFragmentManager().beginTransaction().replace(R.id.createNewUserContainer, fragment).commit();
                        break;
                    }
                }
            }
        }
    }

    public void prepareListsForInterview(){
        util = new Utils();
        listOfQuestionsForUserDuringCreation = util.getListOfQuestionForUserDuringCreation();
        listOfBooleanAnswers = new HashMap<>();
        listOfEditTextAnswers = new HashMap<>();
    }

    @Override
    public void onInputButtonsSent(Boolean input, int questionIndex) {
        listOfBooleanAnswers.put(questionIndex, input);
        if(input == true){
            setFragment(questionIndex+1);
        } else {
            setFragment(questionIndex+2);
        }
    }
    @Override
    public void onInputEditSent(String answer, int questionIndex) {
        listOfEditTextAnswers.put(questionIndex, answer);
        setFragment(questionIndex+1);
    }

    private void creatingNewUser(){
        User newUser = new User(new Balance(), new PersonalPreferences());
        newUser.setUserName((String) listOfEditTextAnswers.get(0));
        newUser.setIncome(Double.valueOf(listOfEditTextAnswers.get(1).toString()));

        // Czy oszczedza
        newUser.getPersonalPreferences().setSaving(listOfBooleanAnswers.get(2));
        if(listOfBooleanAnswers.get(2)){
            newUser.getPersonalPreferences().setHowMuchWantToSave(Double.valueOf(listOfEditTextAnswers.get(3).toString()));
        }
        // Czy placi za ogrzewanie
        newUser.getPersonalPreferences().setPayingForHeating(listOfBooleanAnswers.get(4));
        if(listOfBooleanAnswers.get(4)){
            newUser.getPersonalPreferences().setHowMuchIsPayingForHeating(Double.valueOf(listOfEditTextAnswers.get(5).toString()));
        }
        // Czy posiada kredyty
        newUser.getPersonalPreferences().setPayingCredits(listOfBooleanAnswers.get(6));
        if(listOfBooleanAnswers.get(6)){
            newUser.getPersonalPreferences().setHowMuchIsPayingForCredits(Double.valueOf(listOfEditTextAnswers.get(7).toString()));
        }
        // Czy pali tyton
        newUser.getPersonalPreferences().setSmoking(listOfBooleanAnswers.get(8));
        if(listOfBooleanAnswers.get(8)){
            newUser.getPersonalPreferences().setHowMuchIsPayingForSmoking(Double.valueOf(listOfEditTextAnswers.get(9).toString()));
        }
        newUser.getPersonalPreferences().setAvgPayForBills(Double.valueOf(listOfEditTextAnswers.get(10).toString()));
        newUser.getPersonalPreferences().setAvgPayForFood(Double.valueOf(listOfEditTextAnswers.get(11).toString()));
        newUser.getPersonalPreferences().setAvgPayForTransport(Double.valueOf(listOfEditTextAnswers.get(12).toString()));
        newUser.getPersonalPreferences().setAvgPayForEntertainment(Double.valueOf(listOfEditTextAnswers.get(13).toString()));
        newUser.getPersonalPreferences().setAvgPayForCloths(Double.valueOf(listOfEditTextAnswers.get(14).toString()));
        newUser.getPersonalPreferences().setAvgPayForCosmetics(Double.valueOf(listOfEditTextAnswers.get(15).toString()));

        util = new Utils();
        util.addUserToListOfUsers(newUser);
        listOfUsers = util.getListOfUsers();
        sharedPrefs = new AppPreferencesHelper(CreatingNewUserActivity.this, "Shared_Preferences");
        sharedPrefs.setListOfUsers(util.getListOfUsers());
        Log.d(TAG, "creatingNewUser: " + util.getListOfUsers().size());
        sharedPrefs.setUserIndex(util.getListOfUsers().size()-1);
    }
}
