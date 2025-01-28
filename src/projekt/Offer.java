package projekt;

import projekt.Crops.Crop;
import projekt.Farms.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Random;

public class Offer {
    Crop[] crops;
    Random rand;
    int nrOfCrops;

    public Offer(Boolean[] planets) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        int count = 4;
        Farm[] farm = new Farm[4];
        farm[0] = new Farm1();
        farm[1] = new Farm2();
        farm[2] = new Farm3();
        farm[3] = new Farm4();
        for (int i = 1; i < 4; i++) {
            if (!planets[i - 1]) {
                count--;
                for (int j = i; j < 3; j++) {
                    farm[j] = farm[j + 1];
                }
            }
        }
        rand = new Random();
        nrOfCrops = rand.nextInt(2, 6);

        crops = new Crop[nrOfCrops];
        for (int i = 0; i < nrOfCrops; i++) {
            crops[i] = farm[rand.nextInt(count)].getCrop(rand.nextInt(2));
        }
    }

    public Boolean isAvailable(Inventory inventory) {
        Inventory inv = new Inventory(inventory);
        for (Crop crop : crops) {
            if (inv.getCrop(crop.getName()) > 0) {
                inv.setCrops(crop.getName(), inv.getCrop(crop.getName()) - 1);
            } else return false;
        }
        return true;
    }

    public int price() {
        int pr = 0;
        for (int i = 0; i < nrOfCrops; i++) {
            pr += crops[i].getPrice();
        }
        return pr;
    }

    public Crop[] getCrops() {
        return crops;
    }

    public Inventory sellOffer(Inventory inventory) {
        inventory.setMoney(inventory.getMoney() + price());
        for (int i = 0; i < nrOfCrops; i++) {
            System.out.println(crops[i].getName() + ": " + inventory.getCrop(crops[i].getName()));
            inventory.setCrops(crops[i].getName(), inventory.getCrop(crops[i].getName()) - 1);
            System.out.println(crops[i].getName() + ": " + inventory.getCrop(crops[i].getName()));
        }
        return new Inventory(inventory);
    }
}
