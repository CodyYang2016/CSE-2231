
private static String nextWordOrSeparator(String text, int position) {
        assert text != null : "Violation of: text is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";

        // TODO - fill in body
        Set<Character> strSet = new Set1L<Character>();

        for (int i = 0; i < SEPARATORS.length(); i++) {
            char c = SEPARATORS.charAt(i);
            if (!strSet.contains(c)) {
                strSet.add(c);
            }
        }

        int endIndex = position;
        boolean ifSep = strSet.contains(text.charAt(position));
        while (endIndex < text.length()
                && strSet.contains(text.charAt(endIndex)) == ifSep) {
            endIndex++;
        }

        return text.substring(position, endIndex);


    }


    /*
     * Public members ---------------------------------------------------------
     */

    /**
     * Token to mark the end of the input. This token cannot come from the input
     * stream because it contains whitespace.
     */
    public static final String END_OF_INPUT = "### END OF INPUT ###";

    /**
     * Tokenizes the entire input getting rid of all whitespace separators and
     * returning the non-separator tokens in a {@code Queue<String>}.
     *
     * @param in
     *            the input stream
     * @return the queue of tokens
     * @requires in.is_open
     * @ensures
     *
     *          <pre>
     * tokens =
     *   [the non-whitespace tokens in #in.content] * <END_OF_INPUT>  and
     * in.content = <>
     *          </pre>
     */
    public static Queue<String> tokens(SimpleReader in) {
        assert in != null : "Violation of: in is not null";
        assert in.isOpen() : "Violation of: in.is_open";

        // TODO - fill in body

        Set<Character> strSet = new Set1L<Character>();
        for (int i = 0; i < SEPARATORS.length(); i++) {
            char c = SEPARATORS.charAt(i);
            if (!strSet.contains(c)) {
                strSet.add(c);
            }
        }
        Queue<String> queueOfTokens = new Queue1L<String>();
        while (!in.atEOS()) {
            int position = 0;
            String line = in.nextLine();
            while (position < line.length()) {
                String token = nextWordOrSeparator(line, position);
                if (!strSet.contains(line.charAt(position))) {
                    queueOfTokens.enqueue(token);
                }
                position += token.length();
            }
        }
        queueOfTokens.enqueue(END_OF_INPUT);

        // This line added just to make the program compilable.
        return queueOfTokens;
    }

}