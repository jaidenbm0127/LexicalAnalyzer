import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class LexicalAnalyzer {

    private final String file;
    public LexicalAnalyzer(String fileName)
    {
        file = fileName;
    }

    public void ProcessLines() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String strCurrentLine;
        int row = 1;

        while ((strCurrentLine = reader.readLine()) != null) {

            char[] characters = strCurrentLine.toCharArray();
            CheckCharacters(characters, row);

            row += 1;
        }
    }

    public void CheckCharacters(char[] charArray, int row)
    {
        for(int column = 0; column < charArray.length; column++)
        {
            // System.out.println(charArray[column]);
            if(charArray[column] == 'i')
            {
                String keyword = CheckKeyword(charArray, column, column + 3);
                if(!keyword.equals(""))
                {
                    System.out.println(row + ":" + (column + 1) + " keyword: " + keyword);
                    column += 3;
                }
            }
            else if(charArray[column] == 'd' || charArray[column] == 'S')
            {
                String keyword = CheckKeyword(charArray, column, column + 6);
                if(!keyword.equals(""))
                {
                    System.out.println(row + ":" + (column + 1) + " keyword: " + keyword);
                    column += 6;
                }
            }

            else if(CheckOperators(charArray[column]))
            {
                System.out.println(row + ":" + (column + 1) + " operator: " + charArray[column]);
            }
            else if(Character.isLetter(charArray[column]))
            {
                if (IsIdentifier(charArray, column))
                {
                    System.out.println(row + ":" + (column + 1) + " identifier: " + charArray[column] + charArray[column+1]);
                    column++;
                }
                else
                {
                    System.out.println(row + ":" + (column + 1) + " identifier: " + charArray[column]);
                }
            }
            else if(Character.isDigit(charArray[column]))
            {
                String constant = GetInt(charArray, column);
                if(constant.contains("."))
                {
                    System.out.println(row + ":" + (column + 1) + " constant double: " + constant);
                }
                else
                {
                    System.out.println(row + ":" + (column + 1) + " constant int: " + constant);
                }
                column += constant.length() - 1;
            }
            else if(charArray[column] == '"')
            {
                String stringLiteral = GetString(charArray, column);

                if(stringLiteral.chars().filter(ch -> ch == '"').count() == 2)
                {
                    System.out.println(row + ":" + (column + 1) + " string literal: " + stringLiteral);
                }
                else
                {
                    System.out.println(row + ":" + (column + 1) + " error: " + stringLiteral + " not recognized");
                }
                column +=  stringLiteral.length() - 1;
            }
        }
    }

    public String GetString(char[] charArray, int start)
    {
        StringBuilder temp = new StringBuilder();
        temp.append("\"");
        for(int i = start + 1; i < charArray.length; i++)
        {
            temp.append(charArray[i]);
            if(charArray[i] == '"')
            {
                break;
            }
        }
        return temp.toString();
    }

    public String GetInt(char[] charArray, int start)
    {
        int indexer = start;
        StringBuilder temp = new StringBuilder();
        while(Character.isDigit(charArray[indexer]) || charArray[indexer] == '.')
        {
            if(charArray[indexer] == '.')
            {
                return GetDouble(charArray, start);
            }
            else
            {
                temp.append(charArray[indexer]);
                indexer++;
            }
        }

        return temp.toString();
    }

    public String GetDouble(char[] charArray, int start)
    {
        int indexer = start;
        StringBuilder temp = new StringBuilder();
        while(Character.isDigit(charArray[indexer]) || charArray[indexer] == '.')
        {
            temp.append(charArray[indexer]);
            indexer++;
        }

        return temp.toString();
    }

    public boolean IsIdentifier(char[] charArray, int start)
    {
        return Character.isLetter(charArray[start + 1]) || Character.isDigit(charArray[start + 1]);
    }

    public boolean CheckOperators(char c)
    {
        switch(c)
        {
            case Operators.EQUAL, Operators.MULTIPLICATION, Operators.LEFT_PARENTHESIS, Operators.RIGHT_PARENTHESIS,
                    Operators.PLUS, Operators.MINUS, Operators.DIVISION, Operators.COMMA, Operators.SEMICOLON ->
            {
                return true;
            }
            default ->
            {
                return false;
            }
        }
    }

    public String CheckKeyword(char[] charArray, int start, int end)
    {
        StringBuilder temp = new StringBuilder();

        for(int i = start; i < end; i ++)
        {
            temp.append(charArray[i]);
        }

        switch (temp.toString()) {
            case Keywords.INT ->
            {
                return "int";
            }
            case Keywords.DOUBLE ->
            {
                return "double";
            }
            case Keywords.STRING ->
            {
                return "String";
            }
            default ->
            {
                return "";
            }
        }
    }
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter file name: ");
        String fileName = input.nextLine();

        LexicalAnalyzer analyzer = new LexicalAnalyzer(fileName);
        analyzer.ProcessLines();
    }
}
