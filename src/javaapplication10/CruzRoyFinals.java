
package javaapplication10;

import java.util.Scanner;

public class CruzRoyFinals {
    
    public static int accNum = 0;
    public static int maxAcc = 3;
    public static int e = 1;
    public static int p = 2;
    public static String user[][] = new String[maxAcc][3];
    public static Scanner inp = new Scanner(System.in);
    
    public static void main(String[] args) {   
        String vTransac = "";
        int tNumber = 1;
        int att = 0;
        boolean loggedIn = false;
        int bal = 100;
        Exit:
        do {
            System.out.println("MAIN MENU:\n[1] register\n[2] log in\n[3] view acc\n[4] change pass\n[5] view transaction\n[6] atm machine\n[7] log out\n[8] exit"); 
            int choice = inp.nextInt();
            inp.nextLine();
            switch(choice){ 
                case 1:
                    System.out.println("REGISTER"); 
                    System.out.println("Account Number: "+accNum);
                    String inpEmail = inpEmail();
                    String inpPass = inpPass();
                    if (validCred(inpEmail,inpPass)) {                        
                        if (doubleEmail(inpEmail)) {                            
                            if (accNum >= maxAcc) {
                                maxAcc *= 2;
                                String[][] newUser = new String[maxAcc][3];                               
                                System.arraycopy(user, 0, newUser, 0, accNum);
                                user = newUser;
                            }                            
                            user[accNum][e] = inpEmail;
                            user[accNum][p] = inpPass;
                            accNum++;
                            System.out.println("|| REGISTERED ||\n");
                        }else{
                             System.out.println("Email already existing!!!\n"); 
                        }
                    }else{
                    System.out.println("INVALID CREDENTIALS\n");
                    }
                    break;
                
                case 2:
                    System.out.println("LOG IN");
                    do {
                        int accNumber = accNumber();
                        String logEmail = inpEmail();
                        String logPass = inpPass();
                        if (logEmail.contains(user[accNumber][e]) && logPass.contains(user[accNumber][p])) {
                            System.out.println("LOGIN SUCCESS");
                            loggedIn = true;
                            break;
                        }else{ 
                            att++;
                            System.out.println("INVALID CREDENTIALS \n"+(att-3)+" REMAINING ATTEMPTS");
                        }     
                    } while (att != 3);
                    att=0;
                    break;
                    
                case 3:
                    System.out.println("VIEW ACCOUNT");
                    if (loggedIn) {
                        if (accNum > 0) {
                            int accNumber = accNumber();
                            System.out.println("Account Number: " + accNumber);
                            System.out.println("Email: " + user[accNumber][e]);
                            System.out.println("Password: " + user[accNumber][p]);    
                        }else{
                            System.out.println("No registered users.");     
                        }
                    }else{
                        System.out.println("NOT LOGGED IN");
                    }
                    break;
                    
                case 4:
                    System.out.println("CHANGE PASS");
                    do {
                        if (loggedIn) {
                            if (accNum > 0) {
                                int accNumber = accNumber();
                                String newPass = inpPass();
                                if ((newPass.length())>=8 && newPass.matches(".*[^a-zA-Z0-9].*")) {
                                    user[accNumber][p] = newPass;
                                    System.out.println("SUCESSFULLY CHANGED");
                                    break;
                                }else{
                                    att++;
                                    System.out.println("INVALID CREDENTIALS \n"+(att-3)+" REMAINING ATTEMPTS");
                                }
                            }else{
                                System.out.println("No registered users");
                            }  
                        }else{
                            System.out.println("NOT LOGGED IN");
                            break;
                        }
                    } while (att != 3);
                    att=0;
                    break;
                case 5:
                    if (loggedIn) {
                        System.out.println("VIEW TRANSACTION");
                        if (vTransac.isEmpty()) {
                            System.out.println("YOU HAVE NO TRANSACTION");
                        }else{
                            System.out.println("TRANSACTIONS:\n"+vTransac);
                        }
                    }else{
                        System.out.println("NOT LOGGED IN");
                    }
                    break;
                    
                case 6:
                    if (loggedIn) {
                        System.out.println("ATM MACHINE");
                        Menu:
                        do {
                            System.out.println("\nATM MENU:\n[1] check balance\n[2] deposit\n[3] withdraw\n[4] exit");                     
                            int c = inp.nextInt();
                            inp.nextLine();
                            
                            switch (c) {
                                case 1:
                                    System.out.println("Your Balance is: " + bal);
                                    vTransac += tNumber+" Check Balance\n";
                                    tNumber++;
                                    break;
                                    
                                case 2:
                                    System.out.print("Deposit Amount: ");
                                    int depo = inp.nextInt();
                                    inp.nextLine();
                                    bal += depo;
                                    System.out.println("Total Balance is :" + bal);
                                    vTransac += tNumber+" Deposit\n";
                                    tNumber++;
                                    break;
                                    
                                case 3:
                                    System.out.print("Enter amount to withdraw: ");
                                    int withdraw = inp.nextInt();
                                    inp.nextLine();
                                    vTransac += tNumber+" Withdraw\n";
                                    tNumber++;
                                    if (withdraw <= bal){
                                        bal -= withdraw;
                                        System.out.println("WITHDRAW SUCCESSFULL\nTotal Balance is :" + bal);
                                    }else {
                                        System.out.println("NOT ENOUGH FUNDS");
                                    } 
                                    break;
                                    
                                case 4:
                                    System.out.println("Thank you for using ATM!!");
                                    vTransac += tNumber+" Exit ATM\n";
                                    tNumber++;
                                    break Menu;
                                    
                                default:
                                    System.out.println("Invalid choice. TRY AGAIN!");
                                    
                            }
                        } while (true);
                    }else{
                        System.out.println("NOT LOGGED IN");
                        break;
                    }
                    break;
                case 7:
                    if (loggedIn) {
                        System.out.println("LOGGING OUT\n\nLOGGED OUT");
                        loggedIn = false;
                    }else{
                        System.out.println("NOT LOGGED IN");
                    }
                    break;
                case 8:
                    System.out.println("EXIT");
                    System.exit(0);
                
                default:
                    System.out.println("Invalid choice. TRY AGAIN!");
            }    
        } while (true);
    }
    
    public static boolean doubleEmail(String email){
        boolean checker = true;  
        for (int i = 0; i < accNum; i++) {
            if (user[i][e] != null && user[i][e].equals(email)) {
                checker = false;
            }
        }return checker;
    }
    
    public static boolean validCred(String inpEmail, String inpPass){
        boolean validCred = false;
        if (inpEmail.contains("@") && (inpPass.length())>=8 && inpPass.matches(".*[^a-zA-Z0-9].*")) {
            validCred = true;
        }return validCred;
    }  
    
    public static String inpEmail(){
        System.out.print("Enter Email: ");
        String inpEmail = inp.nextLine();
    return inpEmail;
    }
    public static String inpPass(){
        System.out.print("Enter Password: ");
        String inpPass = inp.nextLine();
    return inpPass;
    }
    public static int accNumber(){
        System.out.print("Enter Acount Number: ");
        int accNumber = inp.nextInt();
        inp.nextLine();
    return accNumber;
    }
    
}
