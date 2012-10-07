package eu.nets.portal;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IdmContextTest {

	@Test
	public void testSimpleContext() {
		String ctxString = "{ \"jon\": 100}";
		IdmContext idmContext = new IdmContext(ctxString);
		assertEquals(100L, idmContext.getValue("jon"));
		assertWhiteSpaceStringEquals(ctxString, idmContext.toString());
	}

	@Test
	public void testArray() {
		String ctxString = "{ \"jon\": [1, 2, 3, 4] }";
		IdmContext idmContext = new IdmContext(ctxString);
		Object[] arr = idmContext.getArray("jon");
		assertEquals(4, arr.length);
		assertEquals(3L, arr[2]);
	}

	private void assertWhiteSpaceStringEquals(String a, String b) {
		assertEquals(a.replace(" ", ""), b.replace(" ", ""));
	}

}
