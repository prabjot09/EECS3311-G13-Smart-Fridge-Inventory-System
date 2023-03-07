package domainTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import domainLayer.Pair;

class PairTest {

	@Test
	void pairConstructionTest() {
		Pair<Integer, Integer> ord_pair = new Pair<>(1, 2);
		assertEquals(1, ord_pair.getA(), "First object is not set correctly.");
		assertEquals(2, ord_pair.getB(), "Second object is not set correctly.");
	}

}
