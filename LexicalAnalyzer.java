import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
        int column = 0;
        for(char c : charArray)
        {
            if(c == 'i')
            {
                String keyword = CheckKeyword(charArray, column, column + 3);
                if(!keyword.equals(""))
                {
                    System.out.println(row + ":" + (column + 1) + " " + keyword);
                }
            }
            else if(c == 'd' || c == 'S')
            {
                String keyword = CheckKeyword(charArray, column, column + 6);
                if(!keyword.equals(""))
                {
                    System.out.println(row + ":" + (column + 1) + " " + keyword);
                }
            }

            if(CheckOperators(c))
            {
                System.out.println(row + ":" + (column + 1) + " " + c);
            }
            column += 1;
        }
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
        LexicalAnalyzer analyzer = new LexicalAnalyzer("test.txt");
        analyzer.ProcessLines();
    }
}
