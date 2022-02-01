// History of Change
// vernr    |date  | who | lineno | what
//  V0.106  |200107| cic |    -   | add history of change

package allgemein;

import java.util.Scanner;

import server.YoolooServer;
import server.YoolooServer.GameMode;

public class StarterServer {

	public static void main(String[] args) {
		System.out.println("Welchen Modus willst Du spielen?");
		System.out.println("1 = normal, 2 = Turniermodus");
		Scanner modusScanner = new Scanner(System.in);
int modus = 		Integer.parseInt(modusScanner.nextLine());
		System.out.println("Wie viele Spieler spielen mit?");
		Scanner playerScanner = new Scanner(System.in);
		int listeningPort = 44137;
		int spieleranzahl = Integer.parseInt(playerScanner.nextLine()); // min 1, max Anzahl definierte Farben in Enum YoolooKartenSpiel.KartenFarbe)
		YoolooServer server;
		if (modus == 2)
		server= new YoolooServer(listeningPort, spieleranzahl, GameMode.GAMEMODE_PLAY_LIGA);
		 else
		server = new YoolooServer(listeningPort, spieleranzahl, GameMode.GAMEMODE_SINGLE_GAME);
		server.startServer();
	}

}
