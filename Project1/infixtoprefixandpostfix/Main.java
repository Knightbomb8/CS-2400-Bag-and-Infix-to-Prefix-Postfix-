package infixtoprefixandpostfix;

import java.util.Stack;
import java.util.Vector;
import java.util.LinkedList;
import java.util.Scanner;

public class Main
{

    public static String infix_expression = "";
    static Scanner scanner = new Scanner(System.in);
    private static boolean run_again = true;
    private static String stack_type = "";

    //main class runs some simple start up functions to get the expression then returns the postfix and prefix equivalents
    public static void main(String[] args) throws InvalidExpression
    {
        getStackType();
        while(run_again)
        {
            startUp();
            System.out.println("The postfix and prefix equivalents of " + infix_expression + " are as follows:");
            System.out.println("Postfix: " + infixToPostfix(infix_expression));
            System.out.println("Prefix: " + infixToPrefix(infix_expression) + "\n");
            runAgain();
        }
    }

    private static void runAgain()
    {
        String result = "";
        boolean correct = false;
        do
        {
            System.out.print("Would you like to use another expression: yes or no? ");
            result = scanner.nextLine();
            result = result.toLowerCase();
            result = result.strip();
            if(result.equals("yes") || result.equals("no"))
            {
                correct = true;
            }
            else
            {
                System.out.println("That was not a valid response");
            }
        }
        while(!correct);

        if(result.equals("no"))
        {
            run_again = false;
        }
    }

    //makes sure the entered expression is valid 
    private static void startUp() throws InvalidExpression
    {
        getExpression();

        boolean valid_expression = checkBalance(infix_expression);
        checkValidity(valid_expression);
    }

    private static void getStackType()
    {
        boolean valid_stack_type = false;
        String type = "";
        do
        {
            System.out.print("What type of stack do you want to use: array, vector, or linked? ");
            type = scanner.nextLine();
            type = type.toLowerCase();
            type = type.strip();
            if(type.equals("array") || type.equals("vector") || type.equals("linked"))
            {
                valid_stack_type = true;
            }
            else
            {
                System.out.println("Invalid Stack type, please try again");
            }
        }
        while(!valid_stack_type);
        stack_type = type;
    }
    
    //uses Scanner to get input from user in form of an expression
    private static void getExpression()
    {
        System.out.print("Please enter an equation you want changed to postfix and prefix: ");
        infix_expression = scanner.nextLine();
    }

    //turns infix to Prefix using the given expression
    private static String infixToPrefix(String expression)
    {
        String result = "";
        String reverse_expression = "";
        int expression_length = expression.length();
        Character current_char = ' ';

        //turns the expression around
        for(int index = expression_length - 1; index >= 0; index--)
        {
            current_char = expression.charAt(index);
            switch(current_char)
            {
                case '{':
                    current_char = '}';
                    break;
                case '}':
                    current_char = '{';
                    break;
                case '[':
                    current_char = ']';
                    break;
                case ']':
                    current_char = '[';
                    break;
                case '(':
                    current_char = ')';
                    break;
                case ')':
                    current_char = '(';
                    break;
                default:
                    break;
            }

            reverse_expression += current_char;
        }

        //gets the postfix expression of the reversed expression
        String postfix_expression = infixToPostfix(reverse_expression);
        expression_length = postfix_expression.length();

        //reverses the postfix expression of the reversed expression
        for(int index = expression_length - 1; index >= 0; index--)
        {
            result += postfix_expression.charAt(index);
        }

        return result;
    }

    //turns infix to postfix
    private static String infixToPostfix(String expression)
    {
        String result = "";
        char current_char = ' ';
        int index = 0;
        StackInterface<Character> operand_stack = new ArrayStack<>();
        switch(stack_type)
        {
            case("vector"):
                operand_stack = new VectorStack<>();
                break;
            
            case("linked"):
                operand_stack = new LinkedListStack<>();
                break;
        }

        //iterates through every character and puts them in the stack or the result accordingly
        while(index < expression.length())
        {
            current_char = expression.charAt(index);
            switch(current_char)
            {
                //checks if the character is an operand and if so checks if it should pop the previous input or not
                case '+': case '-': case '*': case '/':
                    if(!operand_stack.isEmpty())
                    {
                        while(!operand_stack.isEmpty() && getOperandImportance(current_char) <= getOperandImportance(operand_stack.peek()))
                        {
                            result += operand_stack.pop();
                        }
                        operand_stack.push(current_char);
                    }
                    else
                    {
                        operand_stack.push(current_char);
                    }
                    break;

                //exponents are always pushed onto the stack
                case '^':
                    operand_stack.push(current_char);
                    break;

                //if an open delimiter is there, we add it to the stack
                case '(': case '{': case '[':
                    operand_stack.push(current_char);
                    break;
                
                //if the delimiter is closing we pop until we find the open equivalent
                case ')': case '}': case ']':
                    while(operand_stack.peek() != delimiterEquivalent(current_char))
                    {
                        result += operand_stack.pop();
                    }
                    operand_stack.pop();
                    break;

                case ' ':
                    break;

                default: 
                    result += current_char;
                    break;
            }
            index++;
        }

        //pops everything left in the stack to the result at the end
        while(!operand_stack.isEmpty())
        {
            result += operand_stack.pop();
        }

        return result;
    }

    //returns a certain delimiters equivalent
    private static char delimiterEquivalent(char delimiter)
    {
        char result = ' ';
        switch(delimiter)
        {
            case '}':
                result = '{';
                break;

            case ')':
                result = '(';
                break;
            
            case ']':
                result = '[';
                break;
        }
        return result;
    }

    //finds out how important an operand is
    //this will help us determing whether to pop or not
    private static int getOperandImportance(char operand)
    {
        int result = 0;
        switch(operand)
        {
            case '+':case '-':
                result = 1;
                break;
            case '*':case '/':
                result = 2;
                break;
            case '^':
                result = 3;
        }
        return result;
    }

    //if expression is valid, do nothing otherwise throw an error
    private static void checkValidity(boolean valid) throws InvalidExpression
    {
        if(!valid)
        {
            throw new InvalidExpression("The expression entered is not valid");
        }
    }

    //a new exception for if the expression is invalid
    @SuppressWarnings("serial")
    static class InvalidExpression extends Exception
    {
        public InvalidExpression(String message)
        {
            super(message);
        }
    }

    //checks the balance of the given expression to make sure delimiters match
    private static boolean checkBalance(String expression)
    {
        StackInterface<Character> open_delimiter_stack = new ArrayStack<>();

        switch(stack_type)
        {
            case("vector"):
                open_delimiter_stack = new VectorStack<>();
                break;
            
            case("linked"):
                open_delimiter_stack = new LinkedListStack<>();
                break;
        }

        int character_count = expression.length();
        boolean is_balanced = true;
        int index = 0;
        char next_character = ' ';

        //makes sure open delimiter hits its equivalent closer
        while(is_balanced && (index < character_count))
        {
            next_character = expression.charAt(index);
            switch(next_character)
            {
                case '(': case '{': case '[':
                    open_delimiter_stack.push(next_character);
                    break;
                case ')': case '}': case ']':
                    if(open_delimiter_stack.isEmpty())
                    {
                        is_balanced = false;
                    }
                    else
                    {
                        char open_delimiter = open_delimiter_stack.pop();
                        is_balanced = isPaired(open_delimiter, next_character);
                    }
                    break;
                default: break;
            }
            index++;
        }
        if (!open_delimiter_stack.isEmpty())
        {
            is_balanced = false;
        }

        return is_balanced;
    }

    //returns true if a delimiter is matched with its closer and vice versa
    private static boolean isPaired(char open, char closed)
    {
        return(open == '(' && closed == ')' ||
               open == '[' && closed == ']' ||
               open == '{' && closed == '}');
    }
}