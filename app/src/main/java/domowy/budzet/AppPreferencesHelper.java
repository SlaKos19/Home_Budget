package domowy.budzet;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_USER_INDEX = "PREF_KEY_USER_INDEX";

    private static final String PREF_FIRST_TIME_INIT = "FIRST_TIME_INIT";

    private static final String PREF_LIST_OF_USERS = "PREFS_LIST_OF_USERS";

    private static final String PREF_USER_ID = "PREF_USER_ID";

    private final SharedPreferences mPrefs;
    private SharedPreferences.Editor editor;

    public AppPreferencesHelper(Context context, String prefFileName){
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public int getUserIndex() {
        return mPrefs.getInt(PREF_KEY_USER_INDEX, 0);
    }

    @Override
    public void setUserIndex(int userIndex) {
        mPrefs.edit().putInt(PREF_KEY_USER_INDEX, userIndex).apply();
    }

    @Override
    public boolean getFirstTimeInit() {
        return mPrefs.getBoolean(PREF_FIRST_TIME_INIT, false);
    }

    @Override
    public void setFirstTimeInit(boolean firstTimeInit) {
        mPrefs.edit().putBoolean(PREF_FIRST_TIME_INIT, firstTimeInit);
    }

    @Override
    public ArrayList<User> getListOfUsers() {
        Gson gson = new Gson();
        String json = mPrefs.getString(PREF_LIST_OF_USERS, null);
        Type type = new TypeToken<ArrayList<User>>() {}.getType();
        ArrayList<User> listOfUsers = gson.fromJson(json, type);
        if(listOfUsers == null){
            listOfUsers = new ArrayList<User>();
        }
        return listOfUsers;
    }

    @Override
    public void setListOfUsers(ArrayList<User> listOfUsers) {
        editor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(listOfUsers);
        editor.putString(PREF_LIST_OF_USERS, json);
        editor.apply();
    }

    @Override
    public void setIDofUser(int id) {
        mPrefs.edit().putInt(PREF_USER_ID, id).apply();
    }

    @Override
    public int getIDofUser() {
        return mPrefs.getInt(PREF_USER_ID, 0);
    }
}
