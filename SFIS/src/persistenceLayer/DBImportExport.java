package persistenceLayer;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import domainLayer.ApplicationClock;
/*
 * This class requires mysql to be added to command line's PATH for windows devices
 * Instructions will be available on the github wiki
 */
public class DBImportExport {


	
	public DBImportExport (){
		
	}
	//Takes a series of tables and a designated filepath
	//tables must be separated by single spaces
	public void DBExport (String user, String password, String tables, File file) {
		String outputPath = file.toString() + "/sifsDBBackup_" + ApplicationClock.getDate().toString() + ".sql";
		String dump = "mysqldump -u " + user + " -p" + password + " sifsDB " + tables + " > " + outputPath;
		try {
			Process runtimeProcess = Runtime.getRuntime().exec(dump);
			int processComplete = runtimeProcess.waitFor();
		} catch (IOException | InterruptedException e) {
			 JOptionPane.showMessageDialog(null, "Error at Backuprestore");
		}
	}
	
	public void DBImport (String user, String password, File file) {
		String path = file.toString() + "/sifsDBBackup_" + ApplicationClock.getDate().toString() + ".sql";
		String dumpin = "mysql -u " + user + "-p" + password + " sifsDB < " + path;
		try {
			Process runtimeProcess = Runtime.getRuntime().exec(dumpin);
			int processComplete = runtimeProcess.waitFor();
		} catch (IOException | InterruptedException e) {
			 JOptionPane.showMessageDialog(null, "Error at Backuprestore");
		}
	}
}
