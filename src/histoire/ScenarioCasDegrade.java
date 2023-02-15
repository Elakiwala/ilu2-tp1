package histoire;

import personnages.Chef;
import personnages.Druide;
import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;

public class ScenarioCasDegrade {
	public static void main(String[] args) {
		Village village = new Village("le village des irréductibles", 10, 5);
		Chef abraracourcix = new Chef("Abraracourcix", 10, village);
		village.setChef(abraracourcix);
		Gaulois obelix = new Gaulois("Obélix", 25);
		Gaulois asterix = new Gaulois("Astérix", 8);
		Gaulois assurancetourix = new Gaulois("Assurancetourix", 2);
		Gaulois bonemine = new Gaulois("Bonemine", 7);
		Druide druide = new Druide("Panoramix", 2, 5, 10);
		
		Etal etal = new Etal();
		etal.occuperEtal(bonemine, "fleurs", 20);
		etal.occuperEtal(assurancetourix, "lyres", 5);
		etal.occuperEtal(obelix, "menhirs", 2);
		etal.occuperEtal(druide, "fleurs", 10);
		
		try {
			etal.libererEtal();
		}catch (NullPointerException e) {
			// TODO: handle exception
		}
		System.out.println("Fin du test");
	}
}
