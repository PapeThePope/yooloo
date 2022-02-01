// History of Change
// vernr    |date  | who | lineno | what
//  V0.106  |200107| cic |    -   | add history of change

package allgemein;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import Files.LogFile;
import server.YoolooServer;
import server.YoolooServer.GameMode;

public class StarterServer {

	public static void main(String[] args) {
		
		Logger logger = Logger.getLogger(StarterServer.class.getName());
		LogFile logFile = new LogFile();
		logger.addHandler(logFile.CreateLogFile());
		logger.setLevel(Level.ALL);	
		logger.setUseParentHandlers(false);
				
		System.out.println("Welchen Modus willst Du spielen?");
		System.out.println("1 = normal, 2 = Turniermodus");
		Scanner modusScanner = new Scanner(System.in);
		int modus = Integer.parseInt(modusScanner.nextLine());
		
		logger.info("Spielmodus wurde selektiert");
		
		System.out.println("Wie viele Spieler spielen mit?");
		Scanner playerScanner = new Scanner(System.in);
		
		
		int listeningPort = 44137;
		try{
			int spieleranzahl = Integer.parseInt(playerScanner.nextLine()); // min 1, max Anzahl definierte Farben in Enum YoolooKartenSpiel.KartenFarbe)
			logger.info("Anzahl der Spieler wurde selektiert");
		YoolooServer server;
		if (modus == 2)
		server= new YoolooServer(listeningPort, spieleranzahl, GameMode.GAMEMODE_PLAY_LIGA);
		 else
		server = new YoolooServer(listeningPort, spieleranzahl, GameMode.GAMEMODE_SINGLE_GAME);
		logger.info("Server wird erstellt");
		
		server.startServer(logger);
		}catch(Exception exception){
			System.out.println("Bitte gebe die Spielerzahl in Zahlen ein. Danke.");
			System.exit(1);
		}
		
	}
}
