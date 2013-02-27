package eu.nets.portal.fuzzy;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class LevenshteinTest {


    private Levenshtein lev;

    @Before
    public void before() {
        lev = new Levenshtein(System.out);
    }

    @Test
    public void testSimple() {
        String otherString="elephant";
        String testString = "";
        int v = levenshtein(testString, otherString);
        assertEquals(otherString.length(), v);
    }

    @Test
    public void testEqual() {
        assertEquals(0, levenshtein("elephant", "elephant"));
    }

    @Test
    public void testClassic() {
        assertEquals(4, levenshtein("levenshtein", "meilenstein"));
    }

    @Test
    public void testList() {
        String[] list = { "elephant", "dlephant", "dephant", "dephhant" };
        int[] levList = { 0, 1, 2, 3 };
        for (int i=0; i< list.length; i++) {
            assertEquals("->" + list[i], levList[i], levenshtein("elephant", list[i]));
            assertEquals("<-" + list[i], levList[i], levenshtein(list[i], "elephant"));
        }
    }

    private int levenshtein(String testString, String otherString) {
        return lev.compare(testString, otherString);
    }

}

