package eu.nets.portal.fuzzy;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class LevenshteinSubTest {

    private Levenshtein lev;

    @Before
    public void before() {
        lev = new Levenshtein(System.out);
    }

    @Test
    public void testSimple() {
        String otherString="elephant";
        String testString = "";
        int v = subLevenshtein(testString, otherString);
        assertEquals(0, v);
    }

    @Test
    public void testEqual() {
        assertEquals(0, subLevenshtein("elephant", "elephant"));
    }

    @Test
    public void testClassic() {
        assertEquals(0, subLevenshtein("lens", "meilenstein"));
    }

    @Test
    public void testList() {
        String[] list = { "elep", "dlep", "dep", "deph", "dephn" };
        int[] levList = { 0, 1, 1, 1, 2 };
        for (int i=0; i< list.length; i++) {
            assertEquals("<-" + list[i], levList[i], subLevenshtein(list[i], "elephant"));
        }
    }

    @Test
    public void testList2() {
        String[] list = { "elep", "dlep", "dep", "deph", "dephn", "dephn ", "dephn b", "dephn baa" };
        int[] levList = { 0, 1, 1, 1, 2, 3, 3, 4 };
        for (int i=0; i< list.length; i++) {
            assertEquals("<-" + list[i], levList[i], subLevenshtein(list[i], "my little cute elephant bastard"));
        }
    }

    private int subLevenshtein(String testString, String otherString) {
        return lev.subCompare(testString, otherString);
    }



}
