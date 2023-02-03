
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int pennsylvaniaStatus;
        int northCarolinaStatus;
        int pacificStatus;

        int housesNeededP;
        int housesNeededPc;
        int housesNeededNc;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Do you own all the green properties? (y/n) ");
        String colourGroup = scanner.next();
        if (colourGroup.compareTo("n") == 0) {
            System.out.println("Must Own all green properties");
            return;
        }

        System.out.print("What is on Pennsylvania Avenue? (0:nothing, 1:one house, ... 5:a hotel) ");
        pennsylvaniaStatus = scanner.nextInt();
        if (pennsylvaniaStatus == 5) {
            System.out.println("Already have a hotel! No need to buy another");
            return;
        }

        System.out.print("What is on North Carolina Avenue? (0:nothing, 1:one house, ... 5:a hotel) ");
        northCarolinaStatus = scanner.nextInt();
        if (northCarolinaStatus == 5 && pennsylvaniaStatus == 4) {
            System.out.println("Swap North Carolina Hotel with Pennsylvania Houses");
            return;
        }

        System.out.print("What is on What is on Pacific Avenue? (0:nothing, 1:one house, ... 5:a hotel) ");
        pacificStatus = scanner.nextInt();
        if (pacificStatus == 5 && pennsylvaniaStatus == 4) {
            System.out.println("Swap Pacific Hotel with Pennsylvania Houses");
            return;
        }
        housesNeededP = 4 - pennsylvaniaStatus;
        housesNeededNc = 4 - northCarolinaStatus;
        housesNeededPc = 4 - pacificStatus;
        int housesNeeded = housesNeededP + housesNeededNc + housesNeededPc;
        int cashNeeded = (housesNeeded + 1) * 200;
        System.out.print("How much money do you have? $");
        int cash = scanner.nextInt();
        if (cashNeeded > cash) {
            System.out.println("Insufficient Funds!");
            return;
        }
        System.out.print("Is there any hotel available?(0 or more)  ");
        int availableHotel = scanner.nextInt();
        if (availableHotel == 0) {
            System.out.println("No hotels available to purchase!");
            return;
        }

        System.out.print("Number of houses available? $");
        int availableHouses = scanner.nextInt();
        if (availableHouses < housesNeeded) {
            System.out.println("Not enough houses available");
            return;
        }

        System.out.printf("You need %s house(s)\n", housesNeeded);
        System.out.printf("Add %s house(s) to North Carolina\n", housesNeededNc);
        System.out.printf("Add %s house(s) to Pacific\n", housesNeededPc);
        System.out.println("Put 1 hotel on Pennsylvania.");
        System.out.println("Thia will cost $" + cashNeeded);

    }
}