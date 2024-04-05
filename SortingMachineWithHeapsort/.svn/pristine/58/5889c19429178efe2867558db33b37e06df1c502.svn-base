import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Bryce Putman and Cody Yang
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(
            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(
            Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     * Sample test cases.
     */

    /*
     * Routine Case: Testing constructor, passing in a comparator.
     */
    @Test
    public final void testConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);

        assertEquals(mExpected, m);
    }

    /*
     * Boundary Case: Testing adding an element to an empty sorting machine.
     */
    @Test
    public final void testAddEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");

        m.add("green");

        assertEquals(mExpected, m);
    }

    /*
     * Boundary Case: Testing adding an element to a sorting machine of size 1.
     */
    @Test
    public final void testAddSize1() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "blue", "green");

        m.add("green");

        assertEquals(mExpected, m);
    }

    /*
     * Boundary Case: Testing adding an element to a sorting machine of size 5,
     * with the element being the new smallest element.
     */
    @Test
    public final void testAddSize5Smallest() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "blue", "red", "yellow", "purple");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "blue", "red", "yellow", "purple", "black");

        m.add("black");

        assertEquals(mExpected, m);
    }

    /*
     * Routine Case: Testing adding an element to a sorting machine of size 5,
     * with the element being neither the smallest nor largest.
     */
    @Test
    public final void testAddSize5Middle() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "blue", "red", "yellow", "purple");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "blue", "red", "yellow", "purple", "magenta");

        m.add("magenta");

        assertEquals(mExpected, m);
    }

    /*
     * Boundary Case: Testing adding an element to a sorting machine of size 5,
     * with the element being the new largest element.
     */
    @Test
    public final void testAddSize5Largest() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "blue", "red", "yellow", "purple");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "blue", "red", "yellow", "purple", "zebra");

        m.add("zebra");

        assertEquals(mExpected, m);
    }

    /*
     * Boundary Case: Testing adding a duplicate element to a sorting machine.
     */
    @Test
    public final void testAddDuplicate() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "blue", "blue");

        m.add("blue");

        assertEquals(mExpected, m);
    }

    /*
     * Routine Case: Testing adding multiple duplicate elements to a sorting
     * machine.
     */
    @Test
    public final void testAdd2Duplicate() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "blue",
                "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "blue", "blue", "blue");

        m.add("blue");

        assertEquals(mExpected, m);
    }

    /*
     * Boundary Case: Testing changing to extraction mode when all elements are
     * ordered.
     */
    @Test
    public final void testChangeToExtractionModeOrdered() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "blue",
                "green", "purple", "red", "yellow");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "blue", "green", "purple", "red", "yellow");

        m.changeToExtractionMode();

        assertEquals(mExpected, m);
    }

    /*
     * Routine Case: Testing changing to extraction mode when elements are in
     * mixed order.
     */
    @Test
    public final void testChangeToExtractionModeMixed() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "yellow", "blue", "red", "purple");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "yellow", "blue", "red", "purple");

        m.changeToExtractionMode();

        assertEquals(mExpected, m);
    }

    /*
     * Boundary Case: Testing changing to extraction mode when all elements are
     * in reverse order.
     */
    @Test
    public final void testChangeToExtractionModeReverseOrdered() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "yellow", "red", "purple", "green", "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "yellow", "red", "purple", "green", "blue");

        m.changeToExtractionMode();

        assertEquals(mExpected, m);
    }

    /*
     * Challenging Case: Testing changing to extraction mode with duplicates
     * when elements are ordered.
     */
    @Test
    public final void testChangeToExtractionModeDuplicatesOrdered() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "blue",
                "blue", "green", "red", "red");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "blue", "blue", "green", "red", "red");

        m.changeToExtractionMode();

        assertEquals(mExpected, m);
    }

    /*
     * Challenging Case: Testing changing to extraction mode with duplicate when
     * elements are in mixed order.
     */
    @Test
    public final void testChangeToExtractionModeDuplicatesMixed() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "blue",
                "red", "blue", "green", "red");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "blue", "red", "blue", "green", "red");

        m.changeToExtractionMode();

        assertEquals(mExpected, m);
    }

    /*
     * Challenging Case: Testing changing to extraction mode with duplicate
     * values when elements are in reverse order.
     */
    @Test
    public final void testChangeToExtractionModeDuplicatesReverseOrdered() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "red",
                "red", "green", "blue", "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "red", "red", "green", "blue", "blue");

        m.changeToExtractionMode();

        assertEquals(mExpected, m);
    }

    /*
     * Challenging Case: Testing changing to extraction mode with entirely
     * duplicates.
     */
    @Test
    public final void testChangeToExtractionModeAllDuplicates() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "blue",
                "blue", "blue", "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "blue", "blue", "blue", "blue");

        m.changeToExtractionMode();

        assertEquals(mExpected, m);
    }

    /*
     * Challenging Case: Testing changing to extraction mode when two later
     * elements are smaller and identical.
     */
    @Test
    public final void testChangeToExtractionMode2SmallerChildren() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "red",
                "blue", "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "red", "blue", "blue");

        m.changeToExtractionMode();

        assertEquals(mExpected, m);
    }

    /*
     * Boundary Case: Testing removeFirst when size of heap is 1.
     */
    @Test
    public final void testRemoveFirstSize1() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);

        String first = m.removeFirst();

        assertEquals(mExpected, m);
        assertEquals("blue", first);
    }

    /*
     * Routine Case: Testing removeFirst when size of heap is 5.
     */
    @Test
    public final void testRemoveFirstSize5() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "blue",
                "green", "purple", "red", "yellow");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "purple", "red", "yellow");

        String first = m.removeFirst();

        assertEquals(mExpected, m);
        assertEquals("blue", first);
    }

    /*
     * Boundary Case: Testing isInInsertionMode when size of heap is 0 and in
     * extraction mode.
     */
    @Test
    public final void testIsInInsertionModeSize0Extraction() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);

        assertEquals(mExpected, m);
        assertEquals(mExpected.isInInsertionMode(), m.isInInsertionMode());
    }

    /*
     * Boundary Case: Testing isInInsertionMode when size of heap is 0 and in
     * insertion mode.
     */
    @Test
    public final void testIsInInsertionModeSize0Insertion() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);

        assertEquals(mExpected, m);
        assertEquals(mExpected.isInInsertionMode(), m.isInInsertionMode());
    }

    /*
     * Boundary Case: Testing isInInsertionMode when size of heap is 1 and in
     * extraction mode.
     */
    @Test
    public final void testIsInInsertionModeSize1Extraction() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "blue");

        assertEquals(mExpected, m);
        assertEquals(mExpected.isInInsertionMode(), m.isInInsertionMode());
    }

    /*
     * Boundary Case: Testing isInInsertionMode when size of heap is 1 and in
     * insertion mode.
     */
    @Test
    public final void testIsInInsertionModeSize1Insertion() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "blue",
                "green", "purple", "red", "yellow");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "blue", "green", "purple", "red", "yellow");

        assertEquals(mExpected, m);
        assertEquals(mExpected.isInInsertionMode(), m.isInInsertionMode());

    }

    /*
     * Routine Case: Testing isInInsertionMode when size of heap is 5 and in
     * extraction mode.
     */
    @Test
    public final void testIsInInsertionModeSize5Extraction() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "blue",
                "green", "purple", "red", "yellow");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "blue", "green", "purple", "red", "yellow");

        assertEquals(mExpected, m);
        assertEquals(mExpected.isInInsertionMode(), m.isInInsertionMode());
    }

    /*
     * Routine Case: Testing isInInsertionMode when size of heap is 5 and in
     * insertion mode.
     */
    @Test
    public final void testIsInInsertionModeSize5Insertion() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "blue",
                "green", "purple", "red", "yellow");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "blue", "green", "purple", "red", "yellow");

        assertEquals(mExpected, m);
        assertEquals(mExpected.isInInsertionMode(), m.isInInsertionMode());
    }

    /*
     * Boundary Case: Testing order when size of heap is 0.
     */
    @Test
    public final void testOrderSize0() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);

        assertEquals(mExpected, m);
        assertEquals(mExpected.order(), m.order());
    }

    /*
     * Boundary Case: Testing order when size of heap is 1.
     */
    @Test
    public final void testOrderSize1() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "blue");

        assertEquals(mExpected, m);
        assertEquals(mExpected.order(), m.order());
    }

    /*
     * Routine Case: Testing order when size of heap is 5.
     */
    @Test
    public final void testOrderSize5() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "blue",
                "green", "purple", "red", "yellow");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "blue", "green", "purple", "red", "yellow");

        assertEquals(mExpected, m);
        assertEquals(mExpected.order(), m.order());
    }

    /*
     * Boundary Case: Testing size when size of heap is 0.
     */
    @Test
    public final void testSize0() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);

        assertEquals(mExpected, m);
        assertEquals(mExpected.size(), m.size());
    }

    /*
     * Boundary Case: Testing size when size of heap is 1.
     */
    @Test
    public final void testSize1() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "blue");

        assertEquals(mExpected, m);
        assertEquals(mExpected.size(), m.size());
    }

    /*
     * Routine Case: Testing size when size of heap is 5.
     */
    @Test
    public final void testSize5() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "blue",
                "green", "purple", "red", "yellow");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "blue", "green", "purple", "red", "yellow");

        assertEquals(mExpected, m);
        assertEquals(mExpected.size(), m.size());
    }

    /*
     * Challenging Case: Testing multiple duplicate elements in a sorting
     * machine.
     */
    @Test
    public final void testSizeDuplicates() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "blue",
                "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "blue", "blue");

        assertEquals(mExpected, m);
        assertEquals(mExpected.size(), m.size());
    }

    /*
     * Challenging Case: Testing multiple sets of duplicate elements in a
     * sorting machine.
     */
    @Test
    public final void testSizeMultipleDuplicates() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "blue",
                "blue", "green", "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "blue", "blue", "green", "green");

        assertEquals(mExpected, m);
        assertEquals(mExpected.size(), m.size());
    }
}
