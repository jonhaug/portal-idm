package eu.nets.portal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

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
        String sctx1 = "";
        String sctx2 = "";
        IdmContext ctx1 = new IdmContext(sctx1);
        IdmContext ctx2 = new IdmContext(sctx2);
    }

    /* *********************************************************************** */
    private void assertWhiteSpaceStringEquals(String a, String b) {
        assertEquals(a.replace(" ", ""), b.replace(" ", ""));
    }

}
