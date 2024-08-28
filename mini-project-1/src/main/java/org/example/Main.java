package org.example;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner myScan = new Scanner(System.in);
        String loop = "";

        do {
            System.out.print("\nEnter a mathematical expression: ");
            String expression = myScan.nextLine();
            loop = "";

            if (expression.trim().isEmpty()){
                System.out.println("\nInput is empty. Please enter a valid mathematical expression.\n");
            } else {
                try{
                    System.out.println(Calculator.evaluateExpression(expression));
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }

            //Will loop until you give it one of the two options it offers
            while(!loop.equalsIgnoreCase("Y") && !loop.equalsIgnoreCase("N")){
                System.out.println("Try again? Y/N?");
                loop = myScan.nextLine();

                if(loop.matches("[yYnN]")){
                    loop = loop.trim();
                }else{
                    System.out.println("\nInvalid input. Please type Y/N. \n");
                }
            }
        }
        while (loop.equalsIgnoreCase("Y"));
        System.out.print("Thank you for trying out the calculator!");

    }
}