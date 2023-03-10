package domainTest;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domainLayer.FoodItem;
import domainLayer.FoodItem.CreationType;
import domainLayer.FoodItem.StockType;

class FoodItemTest {

	private FoodItem item;
	
	@BeforeEach
	public void init() {
		item = new FoodItem("Milk - 3 Bags", StockType.CONTINUOUS, CreationType.USER);
	}
	
	@Test
	public void constructorTest() {
		FoodItem item = new FoodItem();
		LocalDate someExpDate = LocalDate.of(2020, 1, 8);
		assertTrue(item.getName()== null, "Name not set default to null");
		
		item = new FoodItem("Pineapple", StockType.CONTINUOUS);
		assertTrue(item.getName().equals("Pineapple"), "Name not set correctly by constructor.");
		assertTrue(item.getStockType() == StockType.CONTINUOUS, "Type not set correctly by constructor.");
		
		item = new FoodItem("Pineapple", StockType.CONTINUOUS, CreationType.PRESET);
		assertEquals(item.getCreator(), CreationType.PRESET, "Creation Type not set correctly by constructor.");
		
	}
	
	@Test
	public void getSetNameTest() {
		item.setName("Pizza - Slices");
		
		assertTrue(item.getName().equals("Pizza - Slices"), "Name setter is faulty.");
	}
	
	@Test
	public void getSetStockTypeTest() {
		item.setStockType(StockType.DISCRETE);
		
		assertEquals(item.getStockType(), StockType.DISCRETE, "StockType setter is faulty.");
	}
	
	@Test
	public void getSetCreationType() {
		item.setCreator(CreationType.PRESET);
		
		assertEquals(item.getCreator(), CreationType.PRESET, "CreationType setter is faulty.");
	}
	
	@Test 
	public void trueMatchesTest() {
		FoodItem matchee = new FoodItem("Milk", StockType.CONTINUOUS);
		assertTrue(item.matches(matchee), "Whole words are not matched.");
		
		matchee = new FoodItem("Milk - 3 Bags", StockType.CONTINUOUS);
		assertTrue(item.matches(matchee), "Same name doesn't match.");
		
		matchee = new FoodItem("mILk", StockType.CONTINUOUS);
		assertTrue(item.matches(matchee), "Matching is case-sensitive (shouldn't care about case).");
		
		matchee = new FoodItem("", StockType.CONTINUOUS);
		assertTrue(item.matches(matchee), "Doesn't match with empty string");
		
		item = new FoodItem("Yogurt - Containers", StockType.DISCRETE);
		matchee = new FoodItem("og", StockType.DISCRETE);
		assertTrue(item.matches(matchee), "Doesn't match with string subsets.");
	}
	
	@Test
	public void falseMatchesTest() {
		item = new FoodItem("Yogurt - Containers", StockType.DISCRETE);
		FoodItem matchee = new FoodItem();
		matchee.setName("Colgate");
		assertFalse(item.matches(matchee), "Matches completely different items.");
		
		matchee.setName("Yoga");
		assertFalse(item.matches(matchee), "Matches when only parts of the names have similarity.");
	}
	
	@Test
	public void trueSameAsTest() {
		FoodItem matchee = new FoodItem();
		
		matchee.setName("Milk - 3 Bags");
		assertTrue(item.sameAs(matchee), "Items of the same name are being considered different");
		
		matchee.setName("MILK - 3 BAGS");
		assertTrue(item.sameAs(matchee), "Items of the same name in UPPERCASE are being considered different");

		matchee.setName("milk - 3 bags");
		assertTrue(item.sameAs(matchee), "Items of the same name in lowercase are being considered different");

		matchee.setName("mILk - 3 baGs");
		assertTrue(item.sameAs(matchee), "Items of the same name in different casese are being considered different");
		
	}
	
	@Test
	public void falseSameAsTest() {
		FoodItem matchee = new FoodItem();
		
		matchee.setName("Chocolate");
		assertFalse(item.sameAs(matchee), "Completely different named items are being considered same");
		
		matchee.setName("Milk - Cartons");
		assertFalse(item.sameAs(matchee), "Names with same prefix are being considered same");
		
		matchee.setName("Milk");
		assertFalse(item.sameAs(matchee), "Partial name similarity is being considered same");
		
		matchee.setName("Milk - 3 BagsZ");
		assertFalse(item.sameAs(matchee), "Extended name to the item being matched is being considered the same.");
	}

}
