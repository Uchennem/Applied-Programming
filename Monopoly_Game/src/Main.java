

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int houseP = 0;
        int houseNc = 0;
        int housePc = 0;

        int hotelP = 0;
        int hotelNc = 0;
        int hotelPc = 0;

        int houseNeededP;
        int houseNeededPc;
        int houseNeededNc;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Do you own all the green properties? (y/n) ");
        String colourGroup = scanner.next();
        switch(colourGroup) {

            case("y"):
                System.out.print("What is on Pennsylvania Avenue? (0:nothing, 1:one house, ... 5:a hotel) ");
                int pennsylvania = scanner.nextInt();
                switch(pennsylvania) {
                    case 5:
                        System.out.println("Already have a hotel! We are done here!");
                        hotelP = 1;
                        break;
                    case 0:
                        houseP = 0;
                        break;
                    case 1:
                        houseP = 1;
                        break;
                    case 2:
                        houseP = 2;
                        break;
                    case 3:
                        houseP = 3;
                        break;
                    case 4:
                        houseP = 4;
                        break;
                }

                System.out.print("What is on northCarolina Avenue? (0:nothing, 1:one house, ... 5:a hotel) ");
                int northCarolina = scanner.nextInt();
                switch(northCarolina) {
                    case 5:
                        System.out.println("Already have a hotel! We are done here!");
                        hotelNc = 1;
                        break;
                    case 0:
                        houseNc = 0;
                        break;
                    case 1:
                        houseNc = 1;
                        break;
                    case 2:
                        houseNc = 2;
                        break;
                    case 3:
                        houseNc = 3;
                        break;
                    case 4:
                        houseNc = 4;
                        break;
                }

                System.out.print("What is on Pacific Avenue? (0:nothing, 1:one house, ... 5:a hotel) ");
                int pacific = scanner.nextInt();
                switch(pacific) {
                    case 5:
                        System.out.println("Already have a hotel! We are done here!");
                        hotelPc = 1;
                        break;
                    case 0:
                        housePc = 0;
                        break;
                    case 1:
                        housePc = 1;
                        break;
                    case 2:
                        housePc = 2;
                        break;
                    case 3:
                        housePc = 3;
                        break;
                    case 4:
                        housePc = 4;
                        break;


                }
                System.out.print("Is there any hotel available?(0..3)  ");
                int availableHotel = scanner.nextInt();
                while (housePc != 4 && houseNc != 4 && housePc != 4) {
                    switch (availableHotel) {
                        case 0:
                            System.out.println("No Hotels.");
                            break;
                        case 1:
                        case 2:
                        case 3:
                            int availableHouses = (4 - houseP) + (4 - houseNc) + (4 - housePc);
                            int cashNeeded = (availableHouses * 200) + 200;
                            System.out.print("How much money do you have? $");
                            int cash = scanner.nextInt();
                            if (cash > cashNeeded) {
                                System.out.print("Number of Houses Needed? $");
                                int housesNeeded = scanner.nextInt();
                                if (availableHouses < housesNeeded) {
                                    System.out.print("Purchase 1 hotel and " + availableHouses + " house(s)");
                                    if (houseNc < 4) {
                                        System.out.print("Put" + (4 - houseNc) + " on North Carolina");
                                    }
                                    if (housePc < 4) {
                                        System.out.print("Put" + (4 - housePc) + " on Pacific");
                                    }
                                } else {
                                    System.out.println("No Houses To purchase!");
                                }

                            } else {
                                System.out.println("Insufficient Funds!");
                            }
                            break;
                    }
                    break;
                }

                if (housePc == 4 && houseNc == 4 && housePc == 4){
                    System.out.println("No houses available!");
                }

            case("n"):
                System.out.println("No properties! We are done here!");
                break;

        }



    }
}