package org.example;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ProjectBank {
    public static void main(String[] args) {
        List<Account> accountList = AccountRepository.getAccounts();

        Scanner scanner = new Scanner(System.in);

        int option = -1;

        while (option != 0) {
            try {
                System.out.println("""
                        Welcome to DigiAll Bank! Please choose an option below:
                        
                        1 - Log in to your account
                        2 - Create Account
                        0 - Exit""");
                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        login(accountList, scanner);
                        break;
                    case 2:
                        createAccount(scanner, accountList);
                        AccountRepository.saveAccounts(accountList);
                        break;
                    case 0:
                        System.out.println("Thank you, see you later!");
                        break;
                    default:
                        System.out.println("Invalid option! Please try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please use numbers only!");
                scanner.next();  // Consumir a entrada inválida
            }
        }
        scanner.close();
    }

    // Login
    private static void login(List<Account> accountList, Scanner scanner) {
        System.out.println("Type your Email: ");
        String inputEmail = scanner.nextLine();

        System.out.println("Type your Password: ");
        String inputPassword = scanner.nextLine();

        Account account = findAccountByEmail(accountList, inputEmail);

        if (account != null && account.getPassword().equals(inputPassword)) {
            System.out.println("Login successful! Welcome, " + account.getClient().getName());
            bankOperations(scanner, account, accountList);
        } else {
            System.out.println("Email or password incorrect. Please try again!");
        }
    }

    // Operações bancárias
    private static void bankOperations(Scanner scanner, Account account, List<Account> accountList) {
        int operation = -1;

        while (operation != 0) {
            System.out.println("""
                    Choose a banking operation:
                    1 - Check Balance
                    2 - Deposit
                    3 - Cash out
                    4 - Transfer
                    0 - Return to Main Menu""");

            operation = scanner.nextInt();

            switch (operation) {
                case 1:
                    System.out.println("Your balance is US$" + account.getBalance());
                    break;
                case 2:
                    depositOperation(scanner, account);
                    break;
                case 3:
                    cashOutOperation(scanner, account);
                    break;
                case 4:
                    transferOperation(scanner, account, accountList);
                    break;
                case 0:
                    System.out.println("Returning to the main menu...");
                    break;
                default:
                    System.out.println("Invalid operation! Please try again.");
                    break;
            }

            AccountRepository.saveAccounts(accountList);
        }
    }

    private static void depositOperation(Scanner scanner, Account account) {
        System.out.println("Type the deposit amount:");
        double deposit = scanner.nextDouble();
        account.deposit(deposit);
        System.out.println("You deposited US$" + deposit);
    }

    private static void cashOutOperation(Scanner scanner, Account account) {
        System.out.println("Type the Cash out amount:");
        double cashOut = scanner.nextDouble();
        if (account.cashOut(cashOut)) {
            System.out.println("You withdrew US$" + cashOut);
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    private static void transferOperation(Scanner scanner, Account sourceAccount, List<Account> accountList) {
        System.out.println("Type the transfer amount:");
        double transferAmount = scanner.nextDouble();

        System.out.println("Type the recipient's email:");
        scanner.nextLine();
        String inputedEmail = scanner.nextLine();

        Account findedAccount = findAccountByEmail(accountList, inputedEmail);

        if (findedAccount != null) {
            if (sourceAccount.transfer(transferAmount, findedAccount)) {
                System.out.println("Transfer successful! US$" + transferAmount + " transferred to "
                        + findedAccount.getClient().getName() );

            } else {
                System.out.println("Insufficient balance for transfer.");
            }
        } else {
                System.out.println("Recipient not found.");
        }
    }

    private static Account findAccountByEmail(List<Account> accountList, String email) {
        return accountList.stream()
                .filter(account -> account.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    private static void createAccount(Scanner scanner, List<Account> accountList) {
        System.out.println("Let's create your account at DigiAll Bank!");

        System.out.println("Type your Name:");
        String name = scanner.nextLine();

        System.out.println("Type your CPF:");
        String cpf = scanner.nextLine();

        System.out.println("Type your Age:");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Type your Email:");
        String email = scanner.nextLine();

        System.out.println("Type your Password:");
        String password = scanner.nextLine();

        boolean accountAlreadyExists = accountList.stream().anyMatch(account ->
                    account.getEmail().equals(email) || account.getClient().getCpf().equals(cpf));

        if (accountAlreadyExists) {
            System.out.println("You already have this Customer in our Database. Try Again!");
        } else {
            Client client = new Client(name, age, cpf);
            accountList.add(new Account(5000, password, email, client));
            System.out.println("Account successfully created!");
            System.out.println("Name: " + name);
            System.out.println("Email: " + email);
        }
    }
}



