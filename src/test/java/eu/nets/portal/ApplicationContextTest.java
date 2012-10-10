package eu.nets.portal;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class ApplicationContextTest {

    @Test public void testTranshist() {
        String customerCtxString = "{\"accounts\": [\"11111\", \"22222\",\"33333\"] }";
        String userCtxString = "{\"accounts\": [\"44444\", \"22222\",\"33333\"] }";
        IdmContext customerCtx = new IdmContext(customerCtxString);
        IdmContext userCtx = new IdmContext(userCtxString);
        IdmContext ctx = customerCtx.intersect(userCtx);
        System.out.println(ctx);
        List lst = (List) ctx.getValue("accounts");
        assertEquals(2, lst.size());
    }

    @Test public void testInvoiceArchive() {
        String customerCtxString =
                "{ \"default\": {\"senders\": [1, 2, 3], \"receivers\": [1, 2, 5], \"long\": true}, " +
                        "\"extra\": {\"senders\": [\"a\"], \"receivers\": [4], \"long\": false}}";
        String userCtxString =
                "{ \"default\": \"*\", " +
                        "\"extra\": {\"senders\": [\"b\"], \"receivers\": [4], \"long\": true}}";
        IdmContext customerCtx = new IdmContext(customerCtxString);
        IdmContext userCtx = new IdmContext(userCtxString);
        IdmContext ctx = customerCtx.intersect(userCtx);
        System.out.println(ctx);
        assertEquals(2L, ((List) ctx.getValue("default.senders")).get(1));
    }

    @Test public void testUserAdmin() {

    }

}
