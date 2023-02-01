import java.io.BufferedReader;
import java.io.FileNotFoundException;
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
            int column = 0;

            char[] characters = strCurrentLine.toCharArray();
            for(char c : characters)
            {
                String keyword;
                if(c == 'i')
                {
                    keyword = IsKeyword(characters, column, column + 3);
                    if(!keyword.equals(""))
                    {
                        System.out.println(row + ":" + (column + 1) + " " + keyword);
                    }
                }
                else if(c == 'd' || c == 'S')
                {
                    keyword = IsKeyword(characters, column, column + 6);
                    if(!keyword.equals(""))
                    {
                        System.out.println(row + ":" + (column + 1) + " " + keyword);
                    }
                }
                column += 1;
            }

            row += 1;
        }
    }

    public String IsKeyword(char[] charArray, int start, int end)
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
