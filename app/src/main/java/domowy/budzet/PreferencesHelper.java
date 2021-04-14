package domowy.budzet;

import java.util.ArrayList;

public interface PreferencesHelper {

    int getUserIndex();

    void setUserIndex(int userIndex);

    boolean getFirstTimeInit();

    void setFirstTimeInit(boolean firstTimeInit);

    ArrayList<User> getListOfUsers();

    void setListOfUsers(ArrayList<User> listOfUsers);

    void setIDofUser(int id);

    int getIDofUser();
}
