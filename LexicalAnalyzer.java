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
            int column = 1;
            String[] spaceDelimited = strCurrentLine.split(" ");
            for(String token : spaceDelimited)
            {
                String keyword = IsKeyword(token);
                char[] individualCharacters = token.toCharArray();

                if(!keyword.equals(""))
                {
                    System.out.println(row + ":" + column + " " + keyword);
                    column += keyword.toCharArray().length + 1;
                }
            }
            row += 1;
        }
    }

    public String IsKeyword(String token)
    {
        switch (token) {
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
