package domowy.budzet;

import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {

    // Test sprawdza, czy podczas zakladania nowego uzytkownika nie dochodzi do konfliktu ID
    @Test
    public void creatingUser(){
        User user = new User(new Balance(), new PersonalPreferences());
        Utils utils = new Utils();
        boolean isDuplicated = false;
        for (User user1 : utils.getListOfUsers()){
            if(user.getUserId() == user1.getUserId()){
                isDuplicated = true;
            }
        }
        assertEquals(false, isDuplicated);
    }
}
