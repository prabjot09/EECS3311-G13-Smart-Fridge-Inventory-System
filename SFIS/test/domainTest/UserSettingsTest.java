package domainTest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import domainLayer.UserSettings;

public class UserSettingsTest {
	
	private UserSettings userSettings;
	
	@Before
	public void setUp() {
		userSettings = new UserSettings();
	}
	
	@Test
	public void testGetExpirationWarningDays() {
		assertEquals(5, userSettings.getExpirationWarningDays());
	}
	
	@Test
	public void testSetExpirationWarningDays() {
		userSettings.setExpirationWarningDays(10);
		assertEquals(10, userSettings.getExpirationWarningDays());
	}
	
	@Test
	public void testIsSmartFeaturesEnabled() {
		assertEquals(false, userSettings.isSmartFeaturesEnabled());
	}
	
	@Test
	public void testSetSmartFeaturesEnabled() {
		userSettings.setSmartFeaturesEnabled(true);
		assertEquals(true, userSettings.isSmartFeaturesEnabled());
	}
	
	@Test
	public void testGetAddGroceryListThreshold() {
		assertEquals(20, userSettings.getAddGroceryListThreshold());
	}
	
	@Test
	public void testSetAddGroceryListThreshold() {
		userSettings.setAddGroceryListThreshold(30);
		assertEquals(30, userSettings.getAddGroceryListThreshold());
	}
	
	@Test
	public void testGenerateDefaultSettings() {
		UserSettings defaultSettings = UserSettings.generateDefaultSettings();
		assertEquals(5, defaultSettings.getExpirationWarningDays());
		assertEquals(false, defaultSettings.isSmartFeaturesEnabled());
		assertEquals(20, defaultSettings.getAddGroceryListThreshold());
	}
	
}
