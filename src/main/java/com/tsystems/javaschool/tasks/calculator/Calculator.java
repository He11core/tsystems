package com.tsystems.javaschool.tasks.calculator;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        // TODO: Implement the logic here

                if (!inputStringCheck(statement)) {
                    return null;
                }

                String currentElement = "";
                Stack<Character> operations = new Stack<>();
                Stack<Double> numbersStack = new Stack<>();

                char symbol;

                for (int i = 0; i < statement.length(); i++) {
                    symbol = statement.charAt(i);
                    if ((Character.isDigit(symbol) || symbol == '.')) {
                        currentElement += symbol;
                        if (i == statement.length() - 1) {
                            numbersStack.push(Double.parseDouble(currentElement));
                        }

                    } else {

                        if (!currentElement.isEmpty()) {
                            numbersStack.push(Double.parseDouble(currentElement));
                        }
                        currentElement = "";

                        if (symbol == '(') {
                            operations.push(symbol);
                        } else if (symbol == ')') {
                            while (operations.peek() != '(') {
                                try {
                                    makeOperation(numbersStack, operations.pop());
                                } catch (ArithmeticException e) {
                                    return null;
                                }
                            }
                            operations.pop();
                        } else if (isOperator(symbol)) {

                            while (!operations.isEmpty() && getPriority(operations.peek()) >= getPriority(symbol)) {
                                try {
                                    makeOperation(numbersStack, operations.pop());
                                } catch (ArithmeticException e) {
                                    return null;
                                }
                            }
                            operations.push(symbol);
                        }
                    }

                }

                while (!operations.isEmpty()) {
                    try {
                        makeOperation(numbersStack, operations.pop());
                    } catch (ArithmeticException e) {
                        return null;
                    }
                }


                Double result = numbersStack.pop();
                DecimalFormatSymbols separator = new DecimalFormatSymbols(Locale.getDefault());
                separator.setDecimalSeparator('.');
                DecimalFormat df = new DecimalFormat("#.####", separator);
                if (result % 1 == 0) {
                    df = new DecimalFormat("#");
                }
                df.setRoundingMode(RoundingMode.CEILING);
                String resultString = df.format(result);
                return resultString;

        }


    static void makeOperation(Stack<Double> numbersStack, char operation) {
        double right = numbersStack.pop();
        double left = numbersStack.pop();
        switch (operation) {
            case '+':
                numbersStack.push(left + right);
                break;
            case '-':
                numbersStack.push(left - right);
                break;
            case '*':
                numbersStack.push(left * right);
                break;
            case '/':
                    if (right == 0) {
                        throw new ArithmeticException();
                    }
                    numbersStack.push(left / right);
                    break;

        }
    }

    static boolean isOperator(char symbol) {
        return symbol == '+' || symbol == '-' || symbol == '*' || symbol == '/';
    }

    static int getPriority(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0;
        }
    }

    /* checking input string  */

    static boolean inputStringCheck(String statement) {
        boolean satisfied = true;
        try {

            Pattern incorrectOperations = Pattern.compile("[-.*+\\/]{2,}");
            Pattern deniedSymbols = Pattern.compile("[^0-9-.*+\\/()]");

            Matcher mt = incorrectOperations.matcher(statement);
            if (mt.find()) {
                satisfied = false;
            }
            mt = deniedSymbols.matcher(statement);
            if (mt.find()) {
                satisfied = false;
            }

            if (statement.isEmpty()) {
                satisfied = false;
            }

            if (statement.indexOf(')') < statement.indexOf('(')) {
                satisfied = false;
            }

            if (!Character.isDigit(statement.charAt(0)) && statement.charAt(0) != '(') {
                satisfied = false;
            }

            if (!Character.isDigit(statement.charAt(statement.length() - 1)) && statement.charAt(statement.length() - 1) != ')') {
                satisfied = false;
            }

            int openingParenthesesCount = 0;
            int closingParenthesesCount = 0;
            for (int i = 0; i < statement.length(); i++) {
                if (statement.charAt(i) == '(') {
                    openingParenthesesCount++;
                }
                if (statement.charAt(i) == ')') {
                    closingParenthesesCount++;
                }
            }
            if (openingParenthesesCount != closingParenthesesCount) {
                satisfied = false;
            }

        }  catch (Exception e) {
            satisfied = false;
        }
        return satisfied;
    }

}
