package eu.nets.portal.fuzzy;

import java.io.PrintStream;
import java.util.Arrays;

public class Levenshtein {
    private PrintStream out=null;
    public Levenshtein() { }
    public Levenshtein(PrintStream out) {
        this.out=out;
    }
    private void vout(int[][] lev) {
        if (out != null) {
//            out.println("[");
            for (int i=0; i<lev.length; i++) {
                out.println(Arrays.toString(lev[i]));
            }
            out.println("");
        }
    }
    private void fillIn(int[][] lev, String testString, String otherString) {
        int testLen = testString.length();
        int otherLen = otherString.length();
        for (int j=1; j <= testLen; j++) {
            char test=testString.charAt(j-1);
            lev[j][0]=j;
            for (int i=1; i <= otherLen; i++) {
                char other = otherString.charAt(i-1);
                int v=lev[j-1][i]+1;
                int h=lev[j][i-1]+1;
                int d = lev[j-1][i-1] + (test==other ? 0 : 1);
                int r = min(v,h,d);
                lev[j][i] = r;
            }
        }
        vout(lev);
    }
    public int compare(String testString, String otherString) {
        int testLen = testString.length();
        int otherLen = otherString.length();
        int[][] lev = new int[testLen+1][otherLen+1];
        for (int i=0; i<=otherLen; i++) lev[0][i]=i;
        fillIn(lev, testString, otherString);
        return lev[testLen][otherLen];
    }

    public int subCompare(String testString, String otherString) {
        int testLen = testString.length();
        int otherLen = otherString.length();
        int[][] lev = new int[testLen+1][otherLen+1];
        fillIn(lev, testString, otherString);
        int cur=lev[testLen][0];
        for (int i=1; i<=otherLen; i++) cur=min(cur, lev[testLen][i]);
        return cur;
    }

    private int min(int v, int h, int d) {
        return v < h ? (v < d ? v : d) : (h < d ? h : d);
    }

    private int min(int a, int b) {
        return (a < b) ? a : b;
    }


}
