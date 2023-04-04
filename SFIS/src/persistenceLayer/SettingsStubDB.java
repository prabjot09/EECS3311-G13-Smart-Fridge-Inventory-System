package persistenceLayer;

import domainLayer.UserSettings;

public class SettingsStubDB {
	
	private UserSettings settings;
	
	public SettingsStubDB() {
		settings = UserSettings.generateDefaultSettings();
	}
	
	public UserSettings loadUserSettings() {
		return this.settings;
	}
	
	public void updateUserSettings(UserSettings settings) { 
		this.settings = settings;
	}
}
