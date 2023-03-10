package IntegrationTests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import domainLayer.FavoritesList;
import domainLayer.FoodItem;
import domainLayer.FridgeItem;
import domainLayer.StockableItemFactory;
import domainLayer.StoredItem;
import persistenceLayer.RealDB;

class FavDBTest {

	// Password and user values are machine dependant based on your own sql settings
		private String user = "root";
		private String password = "G-13-Excellent";
		String url = "jdbc:mysql://localhost:3306/SIFSDB";

		class StubFood1 extends FoodItem {
			public String getName() {
				return "Chocolate Milk - Cartons";
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
				return "Pizza - Slices";
			}

			public StockType getStockType() {
				return StockType.DISCRETE;
			}

			public CreationType getCreator() {
				return CreationType.PRESET;
			}
		}

		@Test
		void fridgeAddandLoadTest() {
			FridgeItem item1 = new FridgeItem();
			FridgeItem item2 = new FridgeItem();
			List<StoredItem> flist = new ArrayList<StoredItem>();
			flist.add(item1);
			flist.add(item2);
			item1.setFoodItem(new StubFood1());
			item2.setFoodItem(new StubFood2());
			item1.setStockableItem(StockableItemFactory.createStockableItem(item1.getFoodItem().getStockType(), 30));
			item2.setStockableItem(StockableItemFactory.createStockableItem(item2.getFoodItem().getStockType(), 15));
			LocalDate date = LocalDate.of(2023, 01, 05);
			LocalDate date1 = LocalDate.of(2022, 10, 12);
			item1.setExpDate(date);
			item2.setExpDate(date1);
			try {
				Connection con = DriverManager.getConnection(url, user, password);
				Statement createState = con.createStatement();
				createState.executeUpdate("drop table favitem;");

				RealDB db = new RealDB(user, password);
				db.addFavItem(item1);
				db.addFavItem(item2);
				List<StoredItem> dlist = new ArrayList<StoredItem>();
				dlist = db.loadFavoritedItems();

				assertEquals(flist.size(), dlist.size(), "Real and DB dont match size");
				for (int x = 0; x < flist.size(); x++) {
					assertEquals(flist.get(x).getDescription(), dlist.get(x).getDescription(),
							"Item name isn't properly stored");
				}

				for (int x = 0; x < flist.size(); x++) {
					assertEquals(flist.get(x).getStockableItem().getStock(), dlist.get(x).getStockableItem().getStock(),
							"StockTypeFailed");
				}
				for (int x = 0; x < flist.size(); x++) {
					assertEquals(flist.get(x).getFoodItem().getCreator(), dlist.get(x).getFoodItem().getCreator(), "Creator Type Failed");
					}
				
				for (int x = 0; x < flist.size(); x++) {
					FridgeItem fitem = (FridgeItem) flist.get(x);
					FridgeItem fitem1 = (FridgeItem) dlist.get(x);
					assertEquals(fitem.getExpDate(), fitem1.getExpDate(), "Expiry dates are wrong");
					}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		@Test
		void fridgeUpdateTest() {
			FridgeItem item1 = new FridgeItem();
			FridgeItem item2 = new FridgeItem();
			List<StoredItem> flist = new ArrayList<StoredItem>();
			flist.add(item1);
			flist.add(item2);
			item1.setFoodItem(new StubFood1());
			item2.setFoodItem(new StubFood2());
			item1.setStockableItem(StockableItemFactory.createStockableItem(item1.getFoodItem().getStockType(), 30));
			item2.setStockableItem(StockableItemFactory.createStockableItem(item2.getFoodItem().getStockType(), 15));
			LocalDate date = LocalDate.of(2023, 01, 05);
			LocalDate date1 = LocalDate.of(2022, 10, 12);
			item1.setExpDate(date);
			item2.setExpDate(date1);
			FavoritesList fav = new FavoritesList(flist);
			fav.setItems(flist);
			
			try {
				Connection con = DriverManager.getConnection(url, user, password);
				Statement createState = con.createStatement();
				createState.executeUpdate("drop table fridgeitem;");

				RealDB db = new RealDB(user, password);
				
				List<StoredItem> dlist = new ArrayList<StoredItem>();
				db.updateFavoritedItems(fav);
				dlist = db.loadFavoritedItems();
				assertEquals(flist.size(), dlist.size(), "Real and DB dont match size");
				for (int x = 0; x < flist.size(); x++) {
					assertEquals(flist.get(x).getDescription(), dlist.get(x).getDescription(),
							"Item name isn't properly stored");
				}

				for (int x = 0; x < flist.size(); x++) {
					assertEquals(flist.get(x).getStockableItem().getStock(), dlist.get(x).getStockableItem().getStock(),
							"StockTypeFailed");
				}
				for (int x = 0; x < flist.size(); x++) {
					assertEquals(flist.get(x).getFoodItem().getCreator(), dlist.get(x).getFoodItem().getCreator(), "Creator Type Failed");
					}
				
				for (int x = 0; x < flist.size(); x++) {
					FridgeItem fitem = (FridgeItem) flist.get(x);
					FridgeItem fitem1 = (FridgeItem) dlist.get(x);
					assertEquals(fitem.getExpDate(), fitem1.getExpDate(), "Expiry dates are wrong");
					}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}


}
