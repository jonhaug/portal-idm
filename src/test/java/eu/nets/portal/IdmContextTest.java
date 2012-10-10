package eu.nets.portal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Test;

public class IdmContextTest {

    @Test public void testSimpleContext() {
        String ctxString = "{ \"jon\": 100}";
        IdmContext idmContext = new IdmContext(ctxString);
        assertEquals(100L, idmContext.getValue("jon"));
        assertWhiteSpaceStringEquals(ctxString, idmContext.toString());
    }

    @Test (expected=Exception.class) public void testParseException() {
        new IdmContext("abd, {");
    }
    @Test public void testList() {
        String ctxString = "{ \"jon\": [1, 2, 3, 4] }";
        IdmContext idmContext = new IdmContext(ctxString);
         List<Object> lst = idmContext.getList("jon");
        assertEquals(4, lst.size());
        assertEquals(3L, lst.get(2));
    }

    @Test public void testDepth() {
        String ctxString = "{ \"haugsand\": {  \"jon\": {}, \"siri\": {\"born\": 1994, \"female\": true, \"occupation\": \"student\"}}}";
        IdmContext idmContext = new IdmContext(ctxString);
        assertEquals("student", idmContext.getValue("haugsand.siri.occupation"));
        assertTrue((Boolean) idmContext.getValue("haugsand.siri.female"));
        assertNull(idmContext.getValue("haugsand.jon.female"));
        //	    assertWhiteSpaceStringEquals(ctxString, idmContext.toString());  Will not work...
    }



    @Test public void subset() {
        String sctx1 = "{\"jon\": 44 }";
        String sctx2 = "{\"jon\": 44, \"siri\": 55 }";
        IdmContext ctx1 = new IdmContext(sctx1);
        IdmContext ctx2 = new IdmContext(sctx2);
        assertTrue(ctx1.subsetOf(ctx2));
        assertTrue(ctx2.subsetOf(ctx2));
        assertFalse(ctx2.subsetOf(ctx1));
    }

    @Test public void subsetWithArray() {
        String sctx1 = "{\"jon\": [2, 5, \"Siri\"], \"Anne\": 9}";
        String sctx2 = "{ \"Anne\": 9, \"Ingrid\": 10, \"jon\": [1,2,3,4,5,6,\"Siri\"]}";
        IdmContext ctx1 = new IdmContext(sctx1);
        IdmContext ctx2 = new IdmContext(sctx2);
        assertTrue(ctx1.subsetOf(ctx2));
        assertTrue(ctx2.subsetOf(ctx2));
        assertFalse(ctx2.subsetOf(ctx1));
        assertTrue(ctx1.equals(ctx1));
    }

    @Test public void subsetComplicated() {
        String sctx1 = "{\"jon\": [2, 5, \"Siri\", {\"Erling\": [\"x\"]}], \"Anne\": {}}";
        String sctx2 = "{ \"Anne\": {\"ss\": 4}, \"Ingrid\": 10, \"jon\": [{\"y\": 5, \"Erling\": [55, \"x\"]}, 1,2,3,4,5,6,\"Siri\"]}";
        IdmContext ctx1 = new IdmContext(sctx1);
        IdmContext ctx2 = new IdmContext(sctx2);
        assertTrue(ctx1.subsetOf(ctx2));
        assertTrue(ctx2.subsetOf(ctx2));
        assertFalse(ctx2.subsetOf(ctx1));
        assertTrue(ctx1.equals(ctx1));
    }

    @Test public void testWildCard() {
        String sctx1 = "{\"jon\": [2, 5, \"Siri\", {\"Erling\": [\"x\"]}], \"Anne\": {}}";
        String sctx2 = "{\"jon\": \"*\", \"Anne\": {} }";
        IdmContext ctx1 = new IdmContext(sctx1);
        IdmContext ctx2 = new IdmContext(sctx2);
        assertTrue(ctx1.subsetOf(ctx2));
        assertTrue(ctx2.subsetOf(ctx2));
        assertFalse(ctx2.subsetOf(ctx1));
        assertTrue(ctx1.equals(ctx1));
    }

    @Test public void testIntersect() {
        String sctx1 = "{\"jon\": [2, 5, \"Siri\", {\"Erling\": [\"x\"]}], \"Anne\": {\"x\": 4, \"y\": 5}}";
        String sctx2 = "{\"jon\": [5, 6, {\"Erling\": [\"x\"]}], \"Anne\": {\"x\": 4, \"y\": 6} }";
        IdmContext ctx1 = new IdmContext(sctx1);
        IdmContext ctx2 = new IdmContext(sctx2);
        IdmContext ctxIntersect = ctx1.intersect(ctx2);
//        System.out.println(ctxIntersect);
        assertTrue(ctxIntersect.subsetOf(ctx1));
        assertTrue(ctxIntersect.subsetOf(ctx2));
        assertEquals(4L, ctxIntersect.getValue("Anne.x"));
        List arr = (List) (ctxIntersect.getValue("jon"));
        Map o = (Map) arr.get(1);
        List arr2 = (List) o.get("Erling");
        assertEquals("x", arr2.get(0));
    }

    @Test public void testunion() {
        String sctx1 = "{\"jon\": [2, 5, \"Siri\", {\"Erling\": [\"x\"]}], \"Anne\": {\"x\": 4, \"y\": 5}}";
        String sctx2 = "{\"jon\": [5, 6, {\"Erling\": [\"x\"]}], \"Anne\": {\"x\": 4, \"y\": 6} }";
        IdmContext ctx1 = new IdmContext(sctx1);
        IdmContext ctx2 = new IdmContext(sctx2);
        IdmContext ctxIntersect = ctx1.union(ctx2);
        System.out.println(ctxIntersect);
        assertTrue(ctx1.subsetOf(ctxIntersect));
        assertEquals(4L, ctxIntersect.getValue("Anne.x"));
        List arr = (List) (ctxIntersect.getValue("jon"));
        Map o = (Map) arr.get(3);
        List arr2 = (List) o.get("Erling");
        assertEquals("x", arr2.get(0));
    }

    /* *********************************************************************** */
    private void assertWhiteSpaceStringEquals(String a, String b) {
        assertEquals(a.replace(" ", ""), b.replace(" ", ""));
    }

}
