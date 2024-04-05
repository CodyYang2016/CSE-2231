import components.map.Map;
import components.map.Map.Pair;
import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class ProgramSkeleton {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private ProgramSkeleton() {
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        /*
         * Put your main program code here
         */

        SimpleWriter out = new SimpleWriter1L();
        SimpleReader in = new SimpleReader1L();

        out.println("Enter the name of the input file: ");
        SimpleReader inFile = new SimpleReader1L(in.nextLine());

        out.println("Enter the name of the output file: ");
        SimpleWriter outFile = new SimpleWriter1L(in.nextLine());

        out.println(
                "Enter the numeric amount of words you would like in the tagFile");
        int count = in.nextInteger();

        in.close();
        out.close();
        inFile.close();
        outFile.close();

    }

    public static void outputHeader(SimpleWriter out, SimpleReader in,
            int numWords, Queue<Map<String, Integer>> glossaryWordCounter) {
        out.println("<html>");
        out.println("<head>");
        out.println("<title> Top " + numWords + "in " + in.name() + "</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1> Top " + numWords + "in " + in.name() + "</h1>");
        out.println("<hr>");

        out.println("<div class=\"cdiv\">");

        out.println("<p class =" + '"' + "cbox" + '"' + ">");
        int length = glossaryWordCounter.length();
        for (int i = 0; i < length; i++) {
            Map<String, Integer> removeFirst = glossaryWordCounter.dequeue();
            Pair<String, Integer> wordCountPair = removeFirst.removeAny();
            String word = wordCountPair.key();
            double moddedValue = Math.log(wordCountPair.value());
            int fontSize = (int) (Math.round(moddedValue) + 10);

            out.println("<span style =\"cursor:default\" class = f" + fontSize
                    + " title=" + '"' + "count: " + wordCountPair.value() + '"'
                    + ">" + wordCountPair.key() + "</span>");

        }

        out.println("</p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");

    }

}
