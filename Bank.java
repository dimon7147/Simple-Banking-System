package banking;

import java.util.Random;
import java.util.Scanner;

public class Bank {
    private final SqlLiteDB sql;

    Bank(SqlLiteDB sql){
        this.sql = sql;
    }

    public void init(){
        sql.createNewTable();
        menu();
        System.out.println("Bye!");
    }

    private int loggedMenu(String card){
        var scanner = new Scanner(System.in);
        while (true){
            System.out.println("1. Balance\n" +
                    "2. Add income\n" +
                    "3. Do transfer\n" +
                    "4. Close account\n" +
                    "5. Log out");
            var choose = scanner.nextInt();
            switch (choose){
                case 1: {
                    System.out.println();
                    System.out.println("Balance: " + sql.getBalance(card));
                    System.out.println();
                    break;
                }
                case 2: {
                    System.out.println();
                    System.out.println("Enter income:");
                    var amount = scanner.nextInt();
                    sql.addMoney(card, amount);
                    System.out.println("Income was added!");
                    System.out.println();
                    break;
                }
                case 3: {
                    System.out.println();
                    System.out.println("Transfer\n" +
                            "Enter card number:");
                    var cardN = scanner.next();
                    if (LuhnAlgorithm.checkIfNumberCorrect(cardN)){
                        if (!cardN.equals(card)){
                            if (sql.isCardExists(cardN)){
                                System.out.println("Enter how much money you want to transfer:");
                                var amountToTransfer = scanner.nextInt();
                                if (sql.getBalance(card) > amountToTransfer){
                                    sql.addMoney(card, amountToTransfer * -1);
                                    sql.addMoney(cardN, amountToTransfer);
                                    System.out.println("Success!");
                                } else {
                                    System.out.println("Not enough money!");
                                }
                            } else {
                                System.out.println("Such a card does not exist.");
                            }
                        } else {
                            System.out.println("You can't transfer money to the same account!");
                        }
                        System.out.println();
                        break;
                    } else {
                        System.out.println("Probably you made mistake in the card number. Please try again!");
                    }
                    System.out.println();
                    break;
                }
                case 4: {
                    sql.removeAccount(card);
                    System.out.println();
                    System.out.println("The account has been closed!");
                    System.out.println();
                    return 0;
                }
                case 5: {
                    System.out.println();
                    System.out.println("You have successfully logged out!");
                    System.out.println();
                    return 0;
                }
                case 0: {
                    System.out.println();
                    return 1;
                }
            }
        }
    }

    private void menu(){
        var scanner = new Scanner(System.in);
        while (true){
            System.out.println("1. Create an account\n" +
                    "2. Log into account\n" +
                    "0. Exit");
            switch (scanner.nextInt()){
                case 1: {
                    while (true){
                        var random = new Random();
                        StringBuilder accountIdentifier = new StringBuilder();
                        for (int i = 0; i < 9; i++){
                            accountIdentifier.append(random.nextInt(10));
                        }
                        var cardNumber = "400000" + accountIdentifier + "" + LuhnAlgorithm.generateCheckNumber("400000" + accountIdentifier);
                        if (sql.isCardExists(cardNumber)) continue;
                        var PIN = new StringBuilder();
                        for (int i = 0; i < 4; i++){
                            PIN.append(random.nextInt(10));
                        }
                        sql.insertCard(cardNumber, String.valueOf(PIN));
                        System.out.println();
                        System.out.println("Your card has been created");
                        System.out.println("Your card number:");
                        System.out.println(cardNumber);
                        System.out.println("Your card PIN:");
                        System.out.println(PIN);
                        System.out.println();
                        break;
                    }
                    break;
                }
                case 2: {
                    System.out.println();
                    System.out.println("Enter your card number:");
                    scanner.nextLine();
                    var cardNumber = scanner.nextLine();
                    System.out.println("Enter your PIN:");
                    var PIN = scanner.nextLine();
                    if (sql.isCardExists(cardNumber, PIN)){
                            System.out.println();
                            System.out.println("You have successfully logged in!");
                            System.out.println();
                            var returnType = loggedMenu(cardNumber);
                            if (returnType == 0){
                                break;
                            } else return;
                        }
                    else {
                        System.out.println();
                        System.out.println("Wrong card number or PIN!");
                        System.out.println();
                        break;
                    }
                }
                case 0: {
                    return;
                }
            }
        }
    }
}
