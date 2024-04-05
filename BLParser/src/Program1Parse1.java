import components.map.Map;
import components.map.Map1L;
import components.program.Program;
import components.program.Program1;
import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.statement.Statement1;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary method {@code parse} for {@code Program}.
 *
 * @author Bryce Putman and Cody Yang
 *
 */
public final class Program1Parse1 extends Program1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Parses a single BL instruction from {@code tokens} returning the
     * instruction name as the value of the function and the body of the
     * instruction in {@code body}.
     *
     * @param tokens
     *            the input tokens
     * @param body
     *            the instruction body
     * @return the instruction name
     * @replaces body
     * @updates tokens
     * @requires <pre>
     * [<"INSTRUCTION"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [an instruction string is a proper prefix of #tokens]  and
     *    [the beginning name of this instruction equals its ending name]  and
     *    [the name of this instruction does not equal the name of a primitive
     *     instruction in the BL language] then
     *  parseInstruction = [name of instruction at start of #tokens]  and
     *  body = [Statement corresponding to the block string that is the body of
     *          the instruction string at start of #tokens]  and
     *  #tokens = [instruction string at start of #tokens] * tokens
     * else
     *  [report an appropriate error message to the console and terminate client]
     * </pre>
     */
    private static String parseInstruction(Queue<String> tokens,
            Statement body) {
        assert tokens != null : "Violation of: tokens is not null";
        assert body != null : "Violation of: body is not null";
        assert tokens.length() > 0 && tokens.front().equals("INSTRUCTION") : ""
                + "Violation of: <\"INSTRUCTION\"> is proper prefix of tokens";

        tokens.dequeue();
        String instrName = tokens.dequeue();
        System.out.println(instrName);
        Reporter.assertElseFatalError(
                !instrName.equals("infect") && !instrName.equals("turnleft")
                        && !instrName.equals("turnright")
                        && !instrName.equals("move")
                        && !instrName.equals("skip"),
                "Violation of: the name of this instruction does not equal the name "
                        + "of a primitive instruction in the BL language");

        Reporter.assertElseFatalError(tokens.dequeue().equals("IS"),
                "Violation of: expected IS after [Instruction name]");
        Statement instructionBody = new Statement1Parse1();

        instructionBody.parseBlock(tokens);

        System.out.println(tokens);
        Reporter.assertElseFatalError(
                tokens.dequeue().equals("END")
                        && tokens.dequeue().equals(instrName),
                "Violation of: Instruction ends with 'END [instructionName]'");

        body.transferFrom(instructionBody);

        return instrName;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Program1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(SimpleReader in) {
        assert in != null : "Violation of: in is not null";
        assert in.isOpen() : "Violation of: in.is_open";
        Queue<String> tokens = Tokenizer.tokens(in);
        this.parse(tokens);
    }

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        String firstToken = tokens.dequeue();
        String programNameStart = tokens.dequeue();
        String thirdToken = tokens.dequeue();

        Reporter.assertElseFatalError(firstToken.equals("PROGRAM"),
                "Violation of: the first token is 'PROGRAM'. Found: '"
                        + firstToken + "'");

        Reporter.assertElseFatalError(thirdToken.equals("IS"),
                "Violation of: the third token is 'IS'. Found: '" + thirdToken
                        + "'");

        Map<String, Statement> context = new Map1L<>();
        Statement programBody = new Statement1();

        System.out.println(tokens);
        while (tokens.front().equals("INSTRUCTION")) {
            System.out.println("Front is INSTRUCTION");
            Statement body = new Statement1Parse1();
            String instrName = parseInstruction(tokens, body);
            Reporter.assertElseFatalError(!context.hasKey(instrName),
                    "Violation of : the name of each new user-defined instruction "
                            + "must be unique");
            context.add(instrName, body);
        }
        System.out.println(tokens);

        Reporter.assertElseFatalError(tokens.dequeue().equals("BEGIN"),
                "Violation of: BEGIN is prefix of body");

        programBody.parseBlock(tokens);
        System.out.println(programBody);

        String penultimateToken = tokens.dequeue();
        String programNameEnd = tokens.dequeue();

        Reporter.assertElseFatalError(programNameEnd.equals(programNameStart),
                "Violation of: the program name at the end of the program matches the initial program name. Found: '"
                        + programNameEnd + "' but expected: '"
                        + programNameStart + "'");

        Reporter.assertElseFatalError(penultimateToken.equals("END"),
                "Violation of: the penultimate token is 'END'. Found: '"
                        + penultimateToken + "'");

        this.setName(programNameStart);
        this.swapContext(context);
//        this.swapBody(programBody);
    }

    /*
     * Main test method -------------------------------------------------------
     */

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Get input file name
         */
        out.print("Enter valid BL program file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Program p = new Program1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        p.parse(tokens);
        /*
         * Pretty print the program
         */
        out.println("*** Pretty print of parsed program ***");
        p.prettyPrint(out);

        in.close();
        out.close();
    }

}
