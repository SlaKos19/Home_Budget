package domowy.budzet;

public class PersonalPreferences {

    private boolean isSaving;
    private double howMuchWantToSave;
    private boolean isPayingForHeating;
    private double howMuchIsPayingForHeating;
    private boolean isPayingCredits;
    private double howMuchIsPayingForCredits;
    private boolean isSmoking;
    private double howMuchIsPayingForSmoking;
    private double avgPayForBills;
    private double avgPayForFood;
    private double avgPayForTransport;
    private double avgPayForEntertainment;
    private double avgPayForCloths;
    private double avgPayForCosmetics;

    public PersonalPreferences(){

    }

    public boolean isSaving() {
        return isSaving;
    }

    public void setSaving(boolean saving) {
        isSaving = saving;
    }

    public double getHowMuchWantToSave() {
        return howMuchWantToSave;
    }

    public void setHowMuchWantToSave(double howMuchWantToSave) {
        this.howMuchWantToSave = howMuchWantToSave;
    }

    public boolean isPayingForHeating() {
        return isPayingForHeating;
    }

    public void setPayingForHeating(boolean payingForHeating) {
        isPayingForHeating = payingForHeating;
    }

    public double getHowMuchIsPayingForHeating() {
        return howMuchIsPayingForHeating;
    }

    public void setHowMuchIsPayingForHeating(double howMuchIsPayingForHeating) {
        this.howMuchIsPayingForHeating = howMuchIsPayingForHeating;
    }

    public boolean isPayingCredits() {
        return isPayingCredits;
    }

    public void setPayingCredits(boolean payingCredits) {
        isPayingCredits = payingCredits;
    }

    public double getHowMuchIsPayingForCredits() {
        return howMuchIsPayingForCredits;
    }

    public void setHowMuchIsPayingForCredits(double howMuchIsPayingForCredits) {
        this.howMuchIsPayingForCredits = howMuchIsPayingForCredits;
    }

    public boolean isSmoking() {
        return isSmoking;
    }

    public void setSmoking(boolean smoking) {
        isSmoking = smoking;
    }

    public double getHowMuchIsPayingForSmoking() {
        return howMuchIsPayingForSmoking;
    }

    public void setHowMuchIsPayingForSmoking(double howMuchIsPayingForSmoking) {
        this.howMuchIsPayingForSmoking = howMuchIsPayingForSmoking;
    }

    public double getAvgPayForBills() {
        return avgPayForBills;
    }

    public void setAvgPayForBills(double avgPayForBills) {
        this.avgPayForBills = avgPayForBills;
    }

    public double getAvgPayForFood() {
        return avgPayForFood;
    }

    public void setAvgPayForFood(double avgPayForFood) {
        this.avgPayForFood = avgPayForFood;
    }

    public double getAvgPayForTransport() {
        return avgPayForTransport;
    }

    public void setAvgPayForTransport(double avgPayForTransport) {
        this.avgPayForTransport = avgPayForTransport;
    }

    public double getAvgPayForEntertainment() {
        return avgPayForEntertainment;
    }

    public void setAvgPayForEntertainment(double avgPayForEntertainment) {
        this.avgPayForEntertainment = avgPayForEntertainment;
    }

    public double getAvgPayForCloths() {
        return avgPayForCloths;
    }

    public void setAvgPayForCloths(double avgPayForCloths) {
        this.avgPayForCloths = avgPayForCloths;
    }

    public double getAvgPayForCosmetics() {
        return avgPayForCosmetics;
    }

    public void setAvgPayForCosmetics(double avgPayForCosmetics) {
        this.avgPayForCosmetics = avgPayForCosmetics;
    }

}
