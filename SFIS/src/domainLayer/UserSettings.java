package domainLayer;

import domainLayer.DBProxy;

public class UserSettings {

	private static int expirationWarningDays;
	private boolean smartFeaturesEnabled; //auto adjust quantity
	private int addGroceryListThreshold;

	public UserSettings() {
	    this.expirationWarningDays = 5;
	    this.smartFeaturesEnabled = true;
	    this.addGroceryListThreshold = 10; //percentage?
	}

	static public int getExpirationWarningDays() {
	    return UserSettings.expirationWarningDays;
	}

	public void setExpirationWarningDays(int expirationWarningDays) {
		this.expirationWarningDays = expirationWarningDays;
	}

	public boolean isSmartFeaturesEnabled() {
		return smartFeaturesEnabled;
	}

	public void setSmartFeaturesEnabled(boolean smartFeaturesEnabled) {
		this.smartFeaturesEnabled = smartFeaturesEnabled;
	}

	public int getAddGroceryListThreshold() {
		return addGroceryListThreshold;
	}

	public void setAddGroceryListThreshold(int addGroceryListThreshold) {
		this.addGroceryListThreshold = addGroceryListThreshold;
	}

	public void saveToDatabase() {
		DBProxy.getInstance().updateUserSettings(this);
	}

	public void loadFromDatabase() {
		UserSettings settings = DBProxy.getInstance().loadUserSettings();
		this.expirationWarningDays = settings.getExpirationWarningDays();
		this.smartFeaturesEnabled = settings.isSmartFeaturesEnabled();
		this.addGroceryListThreshold = settings.getAddGroceryListThreshold();
	}
}

