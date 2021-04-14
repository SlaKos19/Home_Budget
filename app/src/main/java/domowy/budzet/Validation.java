package domowy.budzet;

public class Validation {

    public static boolean isEditTextCorrectDouble(String value){


        if(value.isEmpty()){
            return false;
        } else {
            value.replaceAll("\\s+","");
        }
        try{
            if(Double.valueOf(value) > Double.MAX_VALUE){
                return false;
            }
        }
        catch(NumberFormatException e){

        }
        char[] charSeq = value.toCharArray();
        char[] acceptedLetters = {'0','1','2','3','4','5','6','7','8','9','.'};
        boolean charSeqContainAcceptedLetter = false;
        int numberOfDots = 0;

        for (int i = 0; i < charSeq.length; i++) {
            if(charSeq[i] == '.'){
                numberOfDots++;
                if(numberOfDots > 1){
                    return false;
                }
            }
            for (int j = 0; j < acceptedLetters.length; j++) {
                if(charSeq[i] == acceptedLetters[j]){
                    charSeqContainAcceptedLetter = true;
                    break;
                } else {
                    charSeqContainAcceptedLetter = false;
                }
            }
            if(charSeqContainAcceptedLetter == false){
                return false;
            }
        }
        if(charSeqContainAcceptedLetter == true && numberOfDots <= 1){
            return true;
        } else {
            return false;
        }
    }
}
