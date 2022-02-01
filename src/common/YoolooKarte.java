// History of Change
// vernr    |date  | who | lineno | what
//  V0.106  |200107| cic |    -   | add history of change

package common;

import java.io.Serializable;

public class YoolooKarte implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8114061865027839486L;
	private common.YoolooKartenspiel.Kartenfarbe farbe;
	private int wert;

	public YoolooKarte(common.YoolooKartenspiel.Kartenfarbe kartenfarbe, int kartenwert) {
		this.farbe = kartenfarbe;
		this.wert = kartenwert;
	}

	public common.YoolooKartenspiel.Kartenfarbe getFarbe() {
		return farbe;
	}

	public int getWert() {
		return wert;
	}

	public void setFarbe(common.YoolooKartenspiel.Kartenfarbe kartenfarbe) {
		farbe =  kartenfarbe;
	}

	public void setWert(int wert){
		this.wert=wert;
	}

	@Override
	public String toString() {
		return "YoolooKarte [farbe=" + farbe + ", wert=" + wert + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		YoolooKarte that = (YoolooKarte) o;
		return wert == that.wert && farbe == that.farbe;
	}

}
