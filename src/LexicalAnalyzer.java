//CSCI 4200-DB, Fall 2018, Lexical Analyzer

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LexicalAnalyzer {

    //GLOBAL DECLARATIONS

    //CHARACTER CLASSES
    private static final String LETTER = "LETTER";
    private static final String DIGIT = "DIGIT";
    private static final String UNKNOWN = "UNKNOWN";

    //TOKEN CODES
    private static final String INT_LIT = "INT_LIT";
    private static final String IDENT = "IDENT";
    private static final String ASSIGN_OP = "ASSIGN_OP";
    private static final String ADD_OP = "ADD_OP";
    private static final String SUB_OP = "SUB_OP";
    private static final String MULT_OP = "MULT_OP";
    private static final String DIV_OP = "DIV_OP";
    private static final String EXP_OP = "EXP_OP";
    private static final String LEFT_PAREN = "LEFT_PAREN";
    private static final String RIGHT_PAREN = "RIGHT_PAREN";
    private static final String END_OF_FILE = "END_OF_FILE";
    private static final String APOSTROPHE = "APOSTROPHE";
    private static final String COLON = "COLON";
    private static final String SEMI_COLON = "SEMI_COLON";
    private static final String LEFT_BRACKET = "LEFT_BRACKET";
    private static final String RIGHT_BRAKCET = "RIGHT_BRAKCET";
    private static final String LEFT_CURLY_BRACKET = "LEFT_CURLY_BRACKET";
    private static final String RIGHT_CURLY_BRACKET = "RIGHT_CURLY_BRACKET";
    private static final String PERIOD = "PERIOD";
    private static final String BACKSLASH = "BACKSLASH";
    private static final String UNDERSCORE = "UNDERSCORE";
    private static final String QUOTATIONS = "QUOTATIONS";
    private static final String VERTICAL_BAR = "VERTICAL_BAR";
    private static final String COMMA = "COMMA";
    private static final String EXCLAMATION = "EXCLAMATION";
    private static final String LESS_THAN = "LESS_THAN";
    private static final String GREATER_THAN = "GREATER_THAN";
    private static final String BACKTICK = "BACKTICK";
    private static final String TILDE = "TILDE";
    private static final String PERCENT = "PERCENT";
    private static final String ERROR = "TOO_LONG";


    //VARIABLES
    private static String charClass;
    private static String nextToken;
    private static int nextChar;

    //FILE VARIABLES
    private static File file;
    private static FileReader fileReader1;
    private static FileReader fileReader2;
    private static BufferedReader bufferedReader1;
    private static BufferedReader bufferedReader2;
    private static String fileName = "src/lexInputTest.txt";
    private static final int MAX_LEXEME_LENGTH = 100;
    private static String line;
    private static String lineCompare;
    private static char[] lexeme = new char[MAX_LEXEME_LENGTH];
    private static int lexLen;

    //Main Method
    public static void main(String[] args) {

        try {
            //Read file by line
            file = new File(fileName);
            fileReader1 = new FileReader(file);
            bufferedReader1 = new BufferedReader(fileReader1);
            //Read file by character
            fileReader2 = new FileReader(file);
            bufferedReader2 = new BufferedReader(fileReader2);

            //Output tokens and lexemes for each line.
            //If reader2 has reached the end of the line it
            //reads the next line from reader1 and then continues
            //with reader2
            System.out.println("Chris Mancuso"
                    + ". \nCSCI 4200-DB, Fall 2018, Lexical Analyzer");
            System.out.println("********************************************************************************");
            while((line = bufferedReader1.readLine()) != null) {

                getChar();
                if(line.trim().length() > 0) {
                    System.out.println("Input: " + line);
                    do {
                        lex();
                    } while (!line.trim().equals(lineCompare.trim()));
                    System.out.println("********************************************************************************");
                }
                getChar();
                lineCompare = null;
            }
            System.out.printf("%-30.50s  %-30.50s%n","Next token is: " + END_OF_FILE, "Next lexeme is EOF");
            System.out.println("Lexical analysis of the program is complete!");

        } catch (Exception e) {
            System.out.println("""
                    ERROR - cannot open the lexical text file
                     Make sure the file is placed in the correct directory
                     and has the correct name, lexInputTest.txt""");
            System.out.println();
            e.printStackTrace();
        }
    }

    //Get next character in file and determine character class
    public static void getChar() {
        try {
            if ((nextChar = bufferedReader2.read()) != -1) {
                if (Character.isLetter(nextChar)) {
                    charClass = LETTER;
                } else if (Character.isDigit(nextChar)) {
                    charClass = DIGIT;
                } else {
                    charClass = UNKNOWN;
                }
            } else {
                charClass = END_OF_FILE;
            }
        } catch (IOException e) {
            System.out.println("IOException Occured");
            e.printStackTrace();
        }
    }

    //Add character to lexeme and the lineCompare()
    public static void addChar() {
        if(lineCompare == null)
            lineCompare = String.valueOf((char)nextChar);
        else
            lineCompare += (char)nextChar;

        if (lexLen < (MAX_LEXEME_LENGTH - 2)) {
            lexeme[lexLen++] = (char) nextChar;
            lexeme[lexLen] = 0;
        }
    }

    //Find next non-white space character
    public static void getNonBlank() {
        while (Character.isWhitespace(nextChar)) {
            if(lineCompare == null)
                lineCompare = String.valueOf((char)nextChar);
            else
                lineCompare += (char)nextChar;
            getChar();
        }
    }

    // Switch statements for tokens
    public static void lookup(char ch) {
        switch (ch) {
            case '(' -> {
                addChar();
                nextToken = LEFT_PAREN;
            }
            case ')' -> {
                addChar();
                nextToken = RIGHT_PAREN;
            }
            case '=' -> {
                addChar();
                nextToken = ASSIGN_OP;
            }
            case '+' -> {
                addChar();
                nextToken = ADD_OP;
            }
            case '-' -> {
                addChar();
                nextToken = SUB_OP;
            }
            case '*' -> {
                addChar();
                nextToken = MULT_OP;
            }
            case '/' -> {
                addChar();
                nextToken = DIV_OP;
            }
            case '^' -> {
                addChar();
                nextToken = EXP_OP;
            }
            case '\'' -> {
                addChar();
                nextToken = APOSTROPHE;
            }
            case ':' -> {
                addChar();
                nextToken = COLON;
            }
            case ';' -> {
                addChar();
                nextToken = SEMI_COLON;
            }
            case '[' -> {
                addChar();
                nextToken = LEFT_BRACKET;
            }
            case ']' -> {
                addChar();
                nextToken = RIGHT_BRAKCET;
            }
            case '{' -> {
                addChar();
                nextToken = LEFT_CURLY_BRACKET;
            }
            case '}' -> {
                addChar();
                nextToken = RIGHT_CURLY_BRACKET;
            }
            case '.' -> {
                addChar();
                nextToken = PERIOD;
            }
            case '\\' -> {
                addChar();
                nextToken = BACKSLASH;
            }
            case '_' -> {
                addChar();
                nextToken = UNDERSCORE;
            }
            case '"' -> {
                addChar();
                nextToken = QUOTATIONS;
            }
            case '|' -> {
                addChar();
                nextToken = VERTICAL_BAR;
            }
            case ',' -> {
                addChar();
                nextToken = COMMA;
            }
            case '!' -> {
                addChar();
                nextToken = EXCLAMATION;
            }
            case '<' -> {
                addChar();
                nextToken = LESS_THAN;
            }
            case '>' -> {
                addChar();
                nextToken = GREATER_THAN;
            }
            case '`' -> {
                addChar();
                nextToken = BACKTICK;
            }
            case '~' -> {
                addChar();
                nextToken = TILDE;
            }
            case '%' -> {
                addChar();
                nextToken = PERCENT;
            }
            default -> {
                addChar();
                nextToken = UNKNOWN;
            }
        }
    }

    //Generate lexeme and matching token
    public static void lex() {
        lexLen = 0;
        lexeme = new char[MAX_LEXEME_LENGTH];
        getNonBlank();
        switch (charClass) {
            case LETTER -> {
                addChar();
                getChar();
                while (charClass == LETTER || charClass == DIGIT) {
                    addChar();
                    getChar();
                }
                if (!(lexLen < (MAX_LEXEME_LENGTH - 2)))
                    nextToken = ERROR;
                else
                    nextToken = IDENT;
            }
            case DIGIT -> {
                addChar();
                getChar();
                while (charClass == DIGIT) {
                    addChar();
                    getChar();
                }
                if (!(lexLen < (MAX_LEXEME_LENGTH - 2)))
                    nextToken = ERROR;
                else
                    nextToken = INT_LIT;
            }
            case UNKNOWN -> {
                lookup((char) nextChar);
                getChar();
            }
        }
        System.out.printf("%-30.113s %-30.113s%n","Next token is: " + nextToken, "Next lexeme is " + new String(lexeme));

    }

}