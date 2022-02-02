// History of Change
// vernr    |date  | who | lineno | what
//  V0.106  |200107| cic |    -   | add history of change

package allgemein;

import client.YoolooClient;
import java.util.Scanner;


public class StarterClient {
	public static void main(String[] args) {
		// Abfrage Hostnamen/IP
		System.out.println("Spieleclient von Yooloo GRP 1 wird gestartet...");
		System.out.println("Bitte geben Sie den Hostnamen/IP von dem Server ein: ");
		Scanner hostnameScanner = new Scanner(System.in);
		// Starte Client
		String hostname = hostnameScanner.nextLine();
//		String hostname = "10.101.251.247";
		int port = 44137;
		YoolooClient client = new YoolooClient(hostname, port);
		client.startClient();

	}
}
