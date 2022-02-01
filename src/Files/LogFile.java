package Files;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

// Log file 
public class LogFile 
{
	// Creates a new LogFile 
	public FileHandler CreateLogFile()
		{
			// Creating file handler
			FileHandler textFile = null;
			
			try 
			{
				 textFile = new FileHandler("Log.txt");
			} 
			catch (SecurityException | IOException e) 
			{
				e.printStackTrace();
			}
			
			// Creating formatter
			textFile.setFormatter(new Formatter() 
			{
				public String format(LogRecord record)
				{
					String ret = "";
					if(record.getLevel().intValue() >= Level.WARNING.intValue())
					{
						ret += "ATTENTION:";
					}
					
					ret += record.getLevel();
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
					Date date = new Date(record.getMillis());
					ret += " " + dateFormat.format(date) + " ";				
					ret += this.formatMessage(record);
					ret += "\r\n";
							
					return ret;
				}
			});
			
			return textFile;
		}
}
