package IntegrationTests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import domainLayer.FoodItem;
import persistenceLayer.RealDB;

class ItemDBTest {
	// Password and user values are machine dependant based on your own sql settings
		private String user = "root";
		private String password = "G-13-Excellent";
		String url = "jdbc:mysql://localhost:3306/SIFSDB";

		class StubFood1 extends FoodItem {
			public String getName() {
				return "Apples";
			}

			public StockType getStockType() {
				return StockType.CONTINUOUS;
			}

			public CreationType getCreator() {
				return CreationType.USER;
			}
		}

		class StubFood2 extends FoodItem {
			public String getName() {
				return "Milk - 3 Bags";
			}

			public StockType getStockType() {
				return StockType.DISCRETE;
			}

			public CreationType getCreator() {
				return CreationType.PRESET;
			}
		}
	@Test
	void findMatchTest() throws SQLException {
		FoodItem item = new StubFood1();
		FoodItem item1 = new StubFood2();
		List<String> flist = new ArrayList<String>();
		flist.add(item.getName());
		flist.add(item1.getName());

		try {	
			RealDB db = new RealDB(user, password);
			
			assertTrue(db.findMatchingFoods(flist.get(0)).get(0).contains(flist.get(0)));
			
			assertTrue(db.findMatchingFoods(flist.get(1)).get(0).contains(flist.get(1)));
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
	}

}
