// History of Change
// vernr    |date  | who | lineno | what
//  V0.106  |200107| cic |    -   | add history of change

package common;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;

import common.YoolooKartenspiel.Kartenfarbe;

public class YoolooSpieler implements Serializable,  Comparable<YoolooSpieler> {

	private static final long serialVersionUID = 376078630788146549L;
	private String name;
	private Kartenfarbe spielfarbe;
	private int clientHandlerId = -1;
	private int punkte = 0;
	private YoolooKarte[] aktuelleSortierung;

	public YoolooSpieler(String name, int maxKartenWert) {
		this.name = name;
		this.punkte = 0;
		this.spielfarbe = null;
		this.aktuelleSortierung = new YoolooKarte[maxKartenWert];
	}

	@Override
	public int compareTo(YoolooSpieler e) {
		return this.getPunkte() - e.getPunkte();
	}

	// Standardsortierungen (Zufällige Auswahl zwischen mehreren Strategien) - Nur für Simulation
	public void sortierungFestlegen() {
		YoolooKarte[] neueSortierung = new YoolooKarte[this.aktuelleSortierung.length];
		int r = (int) (Math.random() * (4 - 1)) + 1;
		switch(r){
			//Random
			case 1:
				for (int i = 0; i < neueSortierung.length; i++) {
					int neuerIndex = (int) (Math.random() * neueSortierung.length);
					while (neueSortierung[neuerIndex] != null) {
						neuerIndex = (int) (Math.random() * neueSortierung.length);
					}
					neueSortierung[neuerIndex] = aktuelleSortierung[i];
				}
				break;
			// 1-10
			case 2:
				common.YoolooKartenspiel.Kartenfarbe currentcolor= aktuelleSortierung[0].getFarbe();
				for(int i = 0; i < neueSortierung.length; i++){
					neueSortierung[i] = new YoolooKarte(currentcolor, i+1);
				}
				break;
			// 10-1
			case 3:
				common.YoolooKartenspiel.Kartenfarbe mycolor= aktuelleSortierung[0].getFarbe();
				for(int i = 0; i < neueSortierung.length; i++){
					neueSortierung[i] = new YoolooKarte(mycolor, 10-i);
				}
				break;
			case 4:
				int countone = 1;
				int counttwo = 6;
				common.YoolooKartenspiel.Kartenfarbe thecolor= aktuelleSortierung[0].getFarbe();
				for(int i = 0; i < neueSortierung.length; i++){
					if(countone+5>counttwo){
						neueSortierung[i] = new YoolooKarte(thecolor, counttwo);
						counttwo++;
					}
					else{
						neueSortierung[i] = new YoolooKarte(thecolor, countone);
						countone++;
					}
				}
				break;	
					
		}
		

		aktuelleSortierung = neueSortierung;
	}

	public int erhaeltPunkte(int neuePunkte) {
		System.out.print(name + " hat " + punkte + " P - erhaelt " + neuePunkte + " P - neue Summe: ");
		this.punkte = this.punkte + neuePunkte;
		System.out.println(this.punkte);
		return this.punkte;
	}

	@Override
	public String toString() {
		return "YoolooSpieler [name=" + name + ", spielfarbe=" + spielfarbe + ", puntke=" + punkte
				+ ", altuelleSortierung=" + Arrays.toString(aktuelleSortierung) + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Kartenfarbe getSpielfarbe() {
		return spielfarbe;
	}

	public void setSpielfarbe(Kartenfarbe spielfarbe) {
		this.spielfarbe = spielfarbe;
	}

	public int getClientHandlerId() {
		return clientHandlerId;
	}

	public void setClientHandlerId(int clientHandlerId) {
		this.clientHandlerId = clientHandlerId;
	}

	public int getPunkte() {
		return punkte;
	}

	public void setPunkte(int puntke) {
		this.punkte = puntke;
	}

	public YoolooKarte[] getAktuelleSortierung() {
		return aktuelleSortierung;
	}

	public void setAktuelleSortierung(YoolooKarte[] aktuelleSortierung) {
		this.aktuelleSortierung = aktuelleSortierung;
	}

	public void stichAuswerten(YoolooStich stich) {
		System.out.println(stich.toString());

	}

}
