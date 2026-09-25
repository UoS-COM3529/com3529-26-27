package uk.ac.shef.com3529;

public class VendingMachine {

    private int totalCoins, currentCoins;
    private boolean allowVend;

    public VendingMachine() {
        totalCoins = 0;
        currentCoins = 0;
        allowVend = false;
    }

    public void returnCoins() {
        currentCoins = 0;
    }

    public void addCoin() {
        currentCoins ++;
        if (currentCoins > 1) {
            allowVend = true;
        }
    }

    public void vend() {
        if (allowVend) {
            totalCoins += currentCoins;
            currentCoins = 0;
            allowVend = false;
        }
    }
}
