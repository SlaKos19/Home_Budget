package domowy.budzet;

import org.junit.Test;
import static org.junit.Assert.*;


public class ValidationTest {

    @Test
    public void isEditTextCorrectDoubleTest1(){
        boolean isCorrectDouble = Validation.isEditTextCorrectDouble("");
        assertEquals(false, isCorrectDouble);
    }
    @Test
    public void isEditTextCorrectDoubleTest2(){
        boolean isCorrectDouble = Validation.isEditTextCorrectDouble(" ");
        assertEquals(false, isCorrectDouble);
    }
    @Test
    public void isEditTextCorrectDoubleTest3(){
        boolean isCorrectDouble = Validation.isEditTextCorrectDouble("3");
        assertEquals(true, isCorrectDouble);
    }
    @Test
    public void isEditTextCorrectDoubleTest4(){
        boolean isCorrectDouble = Validation.isEditTextCorrectDouble("3.10");
        assertEquals(true, isCorrectDouble);
    }
    @Test
    public void isEditTextCorrectDoubleTest5(){
        boolean isCorrectDouble = Validation.isEditTextCorrectDouble("3.3.3");
        assertEquals(false, isCorrectDouble);
    }
    @Test
    public void isEditTextCorrectDoubleTest6(){
        boolean isCorrectDouble = Validation.isEditTextCorrectDouble("3.345613123512351233123");
        assertEquals(true, isCorrectDouble);
    }
    @Test
    public void isEditTextCorrectDoubleTest7(){
        boolean isCorrectDouble = Validation.isEditTextCorrectDouble("3.");
        assertEquals(true, isCorrectDouble);
    }
    @Test
    public void isEditTextCorrectDoubleTest8(){
        boolean isCorrectDouble = Validation.isEditTextCorrectDouble(".3");
        assertEquals(true, isCorrectDouble);
    }
    @Test
    public void isEditTextCorrectDoubleTest9(){
        boolean isCorrectDouble = Validation.isEditTextCorrectDouble(".");
        // Test powinien zwrocic true, poniewaz uznaje, iz "." to w istocie "0.0"
        assertEquals(true, isCorrectDouble);
    }
    @Test
    public void isEditTextCorrectDoubleTest10(){
        boolean isCorrectDouble = Validation.isEditTextCorrectDouble("..");
        assertEquals(false, isCorrectDouble);
    }

}
