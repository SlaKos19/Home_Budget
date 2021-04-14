package domowy.budzet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextSwitcher;
import android.widget.ViewSwitcher;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDate;

import java.time.Clock;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity MYLOGS";


    private ImageView arrowLeft, arrowRight;
    private Button btnZaloguj, btnStworzNowegoUzytkownika;

    private ArrayList<User> listOfUsers;

    private TextSwitcher loginPanelTextSwitcher;
    private TextView textViewUserName;

    private int userIndex = 0;

    private AppPreferencesHelper sharedPrefs;

    private Utils util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AndroidThreeTen.init(this);


        loadData();
        getSupportActionBar().hide();
        initWidgets();

        Log.d(TAG, "onCreate myLogs: userIndex = " + userIndex + " listOfUsers.size(); = " + listOfUsers.size());
        if(userIndex >= listOfUsers.size()){
            Log.d(TAG, "UNEXPECTED ERROR. userIndex = " + userIndex + " and it makes ArrayOutOfBound");
            userIndex = 0;
        }
        setTextSwitcherAndArrowsOnClickListeners();
        setOnClickListeners();

    }

    private void initWidgets() {

        arrowLeft = (ImageView) findViewById(R.id.loginPanelArrowLeft);
        arrowRight = (ImageView) findViewById(R.id.loginPanelArrowRight);

        btnZaloguj = (Button) findViewById(R.id.loginPanelBtnZaloguj);
        btnStworzNowegoUzytkownika = (Button) findViewById(R.id.loginPanelStworzNowegoUzytkownika);

    }

    private void setOnClickListeners() {

        btnZaloguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainPanelActivity.class);
                intent.putExtra("loginActivityUserIndex", userIndex);
                Log.d(TAG, "setOnClickListeners - my Logs - userIndex = " + userIndex + " loginPaneListOfUsers.size = " + listOfUsers.size() + " btnZaloguj");

                if(userIndex < listOfUsers.size()){
                    sharedPrefs.setUserIndex(userIndex);
                } else {
                    Log.d(TAG, "setOnClickListeners - my Logs - UNEXPECTED ERROR. userIndex = " + userIndex + " and it makes ArrayOutOfBound");
                    userIndex = 0;
                    sharedPrefs.setUserIndex(userIndex);
                }

                startActivity(intent);
            }
        });
        btnStworzNowegoUzytkownika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CreatingNewUserActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setTextSwitcherAndArrowsOnClickListeners() {

        final Animation inOpposite = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        final Animation outOpposite = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);
        final Animation outNormal = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        final Animation inNormal = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);

        loginPanelTextSwitcher = findViewById(R.id.loginPanelTextSwitcher);

        loginPanelTextSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                textViewUserName = new TextView(LoginActivity.this);
                textViewUserName.setTextSize(30);
                textViewUserName.setTextColor(Color.BLACK);
                textViewUserName.setGravity(Gravity.CENTER);
                return textViewUserName;
            }
        });

        if (listOfUsers.size() > 1 && listOfUsers != null) {
            loginPanelTextSwitcher.setText(listOfUsers.get(userIndex).getUserName());

            arrowLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loginPanelTextSwitcher.setInAnimation(inNormal);
                    loginPanelTextSwitcher.setOutAnimation(outNormal);
                    if (userIndex == 0) {
                        userIndex = listOfUsers.size() - 1;
                        loginPanelTextSwitcher.setText(listOfUsers.get(userIndex).getUserName());
                        Log.d(TAG, "userIndex = " + userIndex + " loginPaneListOfUsers.size = " + listOfUsers.size());
                    } else {
                        loginPanelTextSwitcher.setText(listOfUsers.get(--userIndex).getUserName());
                        Log.d(TAG, "userIndex = " + userIndex + " loginPaneListOfUsers.size = " + listOfUsers.size());
                    }
                }
            });

            arrowRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    loginPanelTextSwitcher.setInAnimation(inOpposite);
                    loginPanelTextSwitcher.setOutAnimation(outOpposite);
                    if (userIndex == listOfUsers.size() - 1) {
                        userIndex = 0;
                        loginPanelTextSwitcher.setText(listOfUsers.get(userIndex).getUserName());
                        Log.d(TAG, "userIndex = " + userIndex + " loginPaneListOfUsers.size = " + listOfUsers.size());
                    } else {
                        loginPanelTextSwitcher.setText(listOfUsers.get(++userIndex).getUserName());
                        Log.d(TAG, "userIndex = " + userIndex + " loginPaneListOfUsers.size = " + listOfUsers.size());
                    }
                }
            });
        } else if (listOfUsers.size() == 1) {
            loginPanelTextSwitcher.setText(listOfUsers.get(userIndex).getUserName());
            arrowRight.setVisibility(View.INVISIBLE);
            arrowLeft.setVisibility(View.INVISIBLE);
        } else {
            loginPanelTextSwitcher.setText("Brak użytkowników");
            arrowRight.setVisibility(View.INVISIBLE);
            arrowLeft.setVisibility(View.INVISIBLE);
            btnZaloguj.setVisibility(View.INVISIBLE);
        }
    }

    private void loadData() {
        sharedPrefs = new AppPreferencesHelper(this, "Shared_Preferences");
        userIndex = sharedPrefs.getUserIndex();
        listOfUsers = sharedPrefs.getListOfUsers();
        Log.d(TAG, "loadData - myLogs : userIndex = " + userIndex);
        Log.d(TAG, "loadData - myLogs : listOfUsers.size(); = " + listOfUsers.size());
        util = new Utils();
        util.setListOfUsers(listOfUsers);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sharedPrefs.setListOfUsers(listOfUsers);
    }
}
