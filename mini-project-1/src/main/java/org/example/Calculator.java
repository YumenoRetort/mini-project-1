package org.example;
import java.util.Stack;

public class Calculator{

    //Method for the bulk of the thing
    static Double evaluateExpression(String expression) {

        expression = expression.replaceAll("\\s+",""); //Remove whitespace

        //Error Handling
        if(!checkExpression(expression)){
            throw new UnsupportedOperationException(
                    "\nYour expression has either: \n" +
                            "- Alphabetical characters \n" +
                            "- Mismatched parenthesis \n" +
                            "- Negative numbers \n" +
                            "- Consecutive, leading, or trailing operators \n" +
                            "All of which, this calculator was not built to handle.\n");
        }

        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();
        StringBuilder num = new StringBuilder();
        String regexNum = "[0-9.]";
        String regexOp = "[+\\-/*]";

        //Loop through each character in the expression
        for (int i = 0; i < expression.length(); i++) {
            char parsedChar = expression.charAt(i);

            //Check that a character is either a digit or decimal
            if (Character.toString(parsedChar).matches(regexNum)) {
                num.setLength(0); //reset num in case of previous use

                //Append character to StringBuilder. For numbers that are more than single digits or are decimals
                while (i < expression.length() && Character.toString(expression.charAt(i)).matches(regexNum)) {
                    num.append(expression.charAt(i));
                    i++; //increment to the next char
                }
                //Push new String to numbers stack as double
                numbers.push(Double.parseDouble(num.toString()));
                i--; //reset to the right position
            }
            //Push Opening parenthesis into operator stack
            else if (parsedChar == '('){
                operators.push(parsedChar);
            }
            //If closed parenthesis is found, pop out the last two digits in the number stack and last operator and solve
            else if (parsedChar == ')'){
                while (operators.peek() != '(') {
                    numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));//Push answer into number stack
                }
                operators.pop(); //Get rid of the opening parenthesis
            }
            //Other operators
            else if (Character.toString(parsedChar).matches(regexOp)) {
                //Check if the current operator is of lower or equal precedence than the latest operator in the stack,
                //If it is, pop out the last two digits and the last operator and solve
                while (!operators.empty() && setPrecedence(parsedChar, operators.peek())) {
                    numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop())); //Push answer into number stack
                }
                operators.push(parsedChar); //If it's of greater precedence, push into operator stack
            }
        }

        //After expression has been evaluated, solve whatever remains
        while (!operators.empty()) {
            numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
        }

        //Return the result
        return numbers.pop();
    }

    //Method for error handling
    public static boolean checkExpression(String expression){

        Stack<Character> check = new Stack<>();
        char ch = expression.charAt(0);
        char lastChar = ' ';
        String regex = "[0-9+\\-*/.()]";
        String regexOp = "[+\\-/*]";

        //Check if leading character is an operator
        if (Character.toString(ch).matches(regexOp)){
            return false;
        }

        for (int i = 0; i < expression.length(); i++){
            ch = expression.charAt(i);

            //Check that parenthesis comes in opening and closing pairs
            if (ch == '('){
                check.push(expression.charAt(i));
            }else if (ch == ')' && lastChar != '('){
                if (check.isEmpty() || check.pop() != '(') {
                    return false;
                }
            }

            //Check that all characters are digits or operators
            if (!Character.toString(ch).matches(regex)){
                return false;
            }

            //Check that there are no consecutive operators
            if (Character.toString(ch).matches(regexOp) && Character.toString(lastChar).matches(regexOp)){
                return false;
            }

            lastChar = ch;
        }

        //Check that the whole expression has been evaluated and there are no trailing operators
        return check.isEmpty() && !Character.toString(lastChar).matches(regexOp);
    }

    //Method for operator precedence
    public static boolean setPrecedence(char op1, char op2) {

        if(op2 == '(' || op2 ==')'){
            return false;
        }
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return false;
        }
        else {
            return true;
        }
    }

    //Method for selecting which operation to apply and sending back the answers
    public static double applyOperation(char op, double b, double a) {
        switch (op) {
            case '+':
                return add(a,b);
            case '-':
                return subtract(a,b);
            case '*':
                return multiply(a,b);
            case '/':
                if (b == 0) {
                    throw new UnsupportedOperationException("Cannot divide by zero");
                }
                return divide(a,b);
        }
        return 0;
    }

    //Methods for each operation
    public static double add(double a, double b){
        return a+b;
    }
    public static double subtract(double a, double b){
        return a-b;
    }
    public static double multiply(double a, double b){
        return a*b;
    }
    public static double divide(double a, double b){
        return a/b;
    }

}
