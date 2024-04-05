import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Creates a tag cloud of the number of tags requested, selected by descending
 * order of frequency. The words are sorted in alphabetical order. Larger words
 * have higher count values than smaller words.
 *
 * @author Bryce Putman and Cody Yang
 *
 */
public final class TagCloudGenerator {

    /**
     * No argument constructor--private to prevent instantiation.
     */

    private TagCloudGenerator() {
    }

    /**
     * Comparator used to sort integers in descending order.
     */
    private static class IntegerGT
            implements Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> o1,
                Map.Entry<String, Integer> o2) {
            // return descending integer order
            return -o1.getValue().compareTo(o2.getValue());
        }
    }

    /**
     * Comparator used to sort map entries by their key in alphabetical order.
     */
    private static class StringAlphabetical
            implements Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> o1,
                Map.Entry<String, Integer> o2) {
            // return alphabetical order, ignoring case
            return o1.getKey().compareToIgnoreCase(o2.getKey());
        }
    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection separators = {}
     * then
     *   entries(nextWordOrSeparator) intersection separators = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection separators /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of separators  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of separators)
     * </pre>
     */

    private static String nextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        /*
         * create nextWordOrSeparator to store either a word or string of
         * separators
         */
        String nextWordOrSeparator = "";

        // store whether first character is start of word or string of separators
        boolean firstCharIsSeparator = separators
                .contains(text.charAt(position));

        // create new variable to store position to avoid warnings
        int newPosition = position;

        /*
         * continue until end of text is reached or first change in separator vs
         * other character is reached
         */
        while (newPosition < text.length() && separators
                .contains(text.charAt(newPosition)) == firstCharIsSeparator) {
            // add next character to next word or separator
            nextWordOrSeparator += text.charAt(newPosition);
            // increment position to move through text
            newPosition++;
        }

        return nextWordOrSeparator;
    }

    /**
     * Creates a map containing the words in the files as keys, which are mapped
     * to the number of occurrences of those words as values.
     *
     * @param inFile
     *            the input stream, used to read in the words
     * @param separators
     *            a set containing separator characters
     * @return map with keys being words and values being the word's respective
     *         number of occurrences
     * @ensures wordToCount = the words contained within in, mapped to the
     *          number of appearances of the word with in
     */
    private static Map<String, Integer> createWordToCountMap(
            BufferedReader inFile, Set<Character> separators) {

        // create map of words to their respective counts
        Map<String, Integer> wordToCount = new HashMap<>();

        // store text from input file
        String text = "";

        // index in text to look for next word or separator
        int position = 0;

        // store text from input file
        try {
            String line = inFile.readLine();
            while (line != null) {
                text += line;
                line = inFile.readLine();
            }
        } catch (IOException e) {
            System.err.println(e);
        }

        // loops until end of text is reached
        while (position < text.length()) {
            // get next word or separator
            String string = nextWordOrSeparator(text, position, separators);
            // check if next string is a word
            if (!separators.contains(string.charAt(0))) {
                /*
                 * increment word count by one, assuming count starts at 0 if
                 * not found
                 */
                wordToCount.put(string,
                        wordToCount.getOrDefault(string, 0) + 1);
            }
            // move along text according to length of string
            position += string.length();
        }
        return wordToCount;
    }

    /**
     * Creates a tag cloud of the number of tags requested, with descending
     * order from the map given. The words are sorted in alphabetical order.
     * Larger words have higher count values than smaller words.
     *
     * @param inFile
     *            the input stream, used to read in the words
     * @param inFileName
     *            the name of the input stream, used for descriptive output
     * @param outFile
     *            the output file
     * @param numWordsToOutput
     *            the number of words to output to tag cloud
     * @param separators
     *            a set containing separator characters
     * @ensures the output file is populated with HTML which creates the
     *          described tag cloud.
     */

    public static void createTagCloud(BufferedReader inFile, String inFileName,
            PrintWriter outFile, Set<Character> separators,
            int numWordsToOutput) {

        // create map of words to their respective counts
        Map<String, Integer> wordToCount = createWordToCountMap(inFile,
                separators);

        // create integer descending order comparator
        IntegerGT descendingOrder = new IntegerGT();

        // create sorting machine with descending order for string, integer pairs
        List<Map.Entry<String, Integer>> descendingOrderWordsToCounts;
        descendingOrderWordsToCounts = new ArrayList<Map.Entry<String, Integer>>();

        // add entries from map to sorting machine
        for (Map.Entry<String, Integer> entry : wordToCount.entrySet()) {
            descendingOrderWordsToCounts.add(entry);
        }

        // sort list in descending order
        descendingOrderWordsToCounts.sort(descendingOrder);

        // track minimum and maximum counts for words
        int minCount = 0;
        int maxCount = 0;

        // create sorting machine with descending order for string, integer pairs
        List<Map.Entry<String, Integer>> alphabeticalOrderWordsToCounts;
        alphabeticalOrderWordsToCounts = new ArrayList<Map.Entry<String, Integer>>();

        /*
         * move top numWordsToOutput words from sortedWordsToCounts to list,
         * storing the min and max counts for the words
         */
        for (int i = 0; i < numWordsToOutput
                && descendingOrderWordsToCounts.size() > 0; i++) {
            Map.Entry<String, Integer> firstWord = descendingOrderWordsToCounts
                    .get(i);

            // if first removed, store max count
            if (i == 0) {
                maxCount = firstWord.getValue();
            }
            // if last removed, store min count
            if (i == numWordsToOutput - 1
                    || alphabeticalOrderWordsToCounts.size() == 0) {
                minCount = firstWord.getValue();
            }

            alphabeticalOrderWordsToCounts.add(i, firstWord);
        }

        StringAlphabetical alphabeticalOrder = new StringAlphabetical();

        // sort list alphabetically
        alphabeticalOrderWordsToCounts.sort(alphabeticalOrder);

        // print HTML table in output file
        outputTable(inFile, inFileName, outFile, alphabeticalOrderWordsToCounts,
                minCount, maxCount, numWordsToOutput);
    }

    /**
     * Calculates the font size for a given word count. Linearly scales the word
     * count in the range [minCount, maxCount] to a font size in the range
     * [minFontSize, maxFontSize].
     *
     * @param wordCount
     *            the count of the specific word for which the font size is
     *            being calculated
     *
     * @param minCount
     *            the minimum word count among the words to be displayed
     * @param maxCount
     *            the maximum word count among the words to be displayed
     * @param minFontSize
     *            the minimum font size for the words in the output
     * @param maxFontSize
     *            the maximum font size for the words in the output
     * @return the calculated font size for the given word count
     */
    public static int calculateFontSize(int wordCount, int minCount,
            int maxCount, int minFontSize, int maxFontSize) {
        // sentry checks for wordCount being outside the domain
        if (wordCount <= minCount) {
            return minFontSize;
        } else if (wordCount >= maxCount) {
            return maxFontSize;
        }

        /*
         * scale linearly from [minWordCount, maxWordCount] to [minFontSize,
         * maxFontSize]. If wordCount is max, return maxFontSize (minFontSize +
         * (max-min) * (max-min) / (max-min). If wordCount is min, return
         * minFontSize ((min + (max-min) * (min-min) / (max-min)) -> min + 0).
         */
        return minFontSize + (int) (((double) (maxFontSize - minFontSize)
                * (wordCount - minCount)) / (maxCount - minCount));
    }

    /**
     * Outputs an HTML table containing words and their corresponding font sizes
     * based on their frequency. The method dequeues words from a given glossary
     * word counter queue, calculates their font size using the
     * calculateFontSize method, and outputs each word in a span with the
     * calculated font size.
     *
     * @param inFile
     *            the input stream, used for reading in the words
     * @param inFileName
     *            the name of the input stream, used for descriptive output
     * @param outFile
     *            the output writer to write the HTML content
     * @param alphaSortedList
     *            a queue of entries, each containing a word and its frequency
     * @param minCount
     *            the minimum frequency count among the words
     * @param maxCount
     *            the maximum frequency count among the words
     * @param numWordsToOutput
     *            the number of words to output in the tag cloud
     */
    public static void outputTable(BufferedReader inFile, String inFileName,
            PrintWriter outFile,
            List<Map.Entry<String, Integer>> alphaSortedList, int minCount,
            int maxCount, int numWordsToOutput) {

        // output header
        outFile.println("<html>");
        outFile.println("<head>");

        // output styling
        outFile.println(
                "<link href=\"http://web.cse.ohio-state.edu/software/2231/"
                        + "web-sw2/assignments/projects/tag-cloud-generator/data/"
                        + "tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">"
                        + "<link href=\"tagcloud.css\" rel=\"stylesheet\" "
                        + "type=\"text/css\">");

        // output web-page title including number of words to output
        outFile.println("<title> Top " + numWordsToOutput + " in " + inFileName
                + "</title>");
        outFile.println("</head>");
        outFile.println("<body>");

        // output page title including number of words to output
        outFile.println(
                "<h1> Top " + numWordsToOutput + " in " + inFileName + "</h1>");
        outFile.println("<hr>");

        outFile.println("<div class=\"cdiv\">");

        outFile.println("<p class =" + '"' + "cbox" + '"' + ">");

        // set minimum and maximum font sizes for output
        final int minFontSize = 12;
        final int maxFontSize = 48;

        // get entries from list, outputting each word
        for (int i = 0; i < alphaSortedList.size(); i++) {
            // get first word alphabetically
            Map.Entry<String, Integer> firstWord = alphaSortedList.get(i);

            // get font size of word using its count
            int fontSize = calculateFontSize(firstWord.getValue(), minCount,
                    maxCount, minFontSize, maxFontSize);

            // output each word with correct font size
            outFile.println("<span style =\"cursor:default\" class = \"f"
                    + fontSize + "\" title=" + '"' + "count: "
                    + firstWord.getValue() + '"' + ">" + firstWord.getKey()
                    + "</span>");

        }

        // output footer
        outFile.println("</p>");
        outFile.println("</div>");
        outFile.println("</body>");
        outFile.println("</html>");

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {

        // create streams
        SimpleWriter out = new SimpleWriter1L();
        SimpleReader in = new SimpleReader1L();

        // request input file name
        out.println("Enter the name of the input file: ");

        // ask for input file name until a valid string ending with ".txt" is given
        String inFileName = in.nextLine();
        while (!inFileName.endsWith(".txt")) {
            out.println(
                    "Please enter a valid input file name ending in \".txt\"");
            inFileName = in.nextLine();
        }
        // create an input stream using the input file name
        BufferedReader inFile;
        try {
            inFile = new BufferedReader(new FileReader(inFileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // request output file name
        out.println("Enter the name of the output file: ");

        // ask for output file name until a valid string ending with ".html" is given
        String outFileName = in.nextLine();
        while (!outFileName.endsWith(".html")) {
            out.println(
                    "Please enter a valid output file name ending in \".html\"");
            outFileName = in.nextLine();
        }

        // create an output stream using the output file name
        PrintWriter outFile;
        try {
            outFile = new PrintWriter(
                    new BufferedWriter(new FileWriter(outFileName)));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // request number of words to output in tag cloud
        out.println(
                "Please enter the number of words you would like in the tag cloud");

        // while number of words is negative, request non-negative number
        int numWords = in.nextInteger();
        while (numWords < 0) {
            out.println("Please input a non-negative number");
            numWords = in.nextInteger();
        }

        // create separator set
        final Set<Character> separatorSet = new HashSet<>();

        // create string of separators
        String separators = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~\r\n ";

        // add separators to set
        for (int i = 0; i < separators.length(); i++) {
            separatorSet.add(separators.charAt(i));
        }

        // close input/output streams
        in.close();
        out.close();

        // create tag cloud
        createTagCloud(inFile, inFileName, outFile, separatorSet, numWords);

        try {
            inFile.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        outFile.close();

    }

}
