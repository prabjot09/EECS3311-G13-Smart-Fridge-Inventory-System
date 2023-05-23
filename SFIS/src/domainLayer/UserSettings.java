package domainLayer;

import domainLayer.DBProxy;

public class UserSettings {

	private int expirationWarningDays;
	private boolean smartFeaturesEnabled; //auto adjust quantity
	private int addGroceryListThreshold;
	private int favoritesListThreshold;
	
	public UserSettings() {
	    this.expirationWarningDays = 5;
	    this.smartFeaturesEnabled = false;
	    this.addGroceryListThreshold = 20; //percentage?
	    this.favoritesListThreshold = 50;
	}

	
	public static UserSettings generateDefaultSettings() {
		return new UserSettings();
	}
	
	public int getExpirationWarningDays() {
	    return this.expirationWarningDays;
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
	
	public int getFavoritesListThreshold() {
		return this.favoritesListThreshold;
	}
	
	public void setFavoritesListThreshold(int threshold) {
		this.favoritesListThreshold = threshold;
	}
	

	public void saveToDatabase() {
		DBProxy.getInstance().updateUserSettings(this);
	}

	public void loadFromDatabase() {
		UserSettings settings = DBProxy.getInstance().loadUserSettings();
		this.expirationWarningDays = settings.getExpirationWarningDays();
		this.smartFeaturesEnabled = settings.isSmartFeaturesEnabled();
		this.addGroceryListThreshold = settings.getAddGroceryListThreshold();
		this.favoritesListThreshold = settings.getFavoritesListThreshold();
	}

}

