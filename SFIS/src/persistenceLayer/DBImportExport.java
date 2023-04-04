package persistenceLayer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.swing.JOptionPane;

import com.mysql.cj.protocol.x.SyncFlushDeflaterOutputStream;

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
	public void DBExport (String user, String password, String tables, File file) throws IOException {
		String outputPath = file.toString() + File.separator + "sifsDBBackup_" + ApplicationClock.getDate().toString() + ".sql";
		String dump = "mysqldump -u " + user + " -p" + password + " sifsDB " + tables + " --result-file=" + outputPath;
		
		   Runtime rt = Runtime.getRuntime();
		   rt.exec("cmd /c " + dump);
	}
	
	public void DBImport (String user, String password, File file) throws IOException {
		String path = file.toString();
	
		String dumpin = "mysql -u " + user + " -p" + password + " sifsDB < " + path;
		
		 Runtime rt = Runtime.getRuntime();
		   rt.exec("cmd /c " + dumpin);
	}
}
