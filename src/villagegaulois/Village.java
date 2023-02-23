package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;
	private int nbEtals;
	private Etal[] etals;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}

	private static class Marche{
		private Etal[] etals;
		
		private Marche(int nbEtalMarche) {
			etals = new Etal[nbEtalMarche];
		}
		
		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			Etal etal = etals[indiceEtal];
			etal.occuperEtal(vendeur, produit, nbProduit);
		}
		
		public int trouverEtalLibre() {
			int indiceEtalLibre = -1;
			
			for (int i = 0; i < etals.length && indiceEtalLibre != -1; i++) {
				Etal etal = etals[i];
				if(etal.isEtalOccupe() == false) {
					indiceEtalLibre ++;
				}
			}
			return indiceEtalLibre;
		}
		
		public Etal[] trouverEtals(String produit) {
			int nbEtalAvecProduit = 0;
			for(int i=0; i<etals.length; i++) {
				Etal etal = etals[i];
				if(etal.isEtalOccupe() && etal.contientProduit(produit)) {
					nbEtalAvecProduit++;
				}
			}
			Etal[] etalsProduit = new Etal[nbEtalAvecProduit];
			
			int indiceNouvelEtalProduit = 0;
			for (int i = 0; i < etals.length; i++) {
				Etal etal = etals[i];
				if(etal.isEtalOccupe() && etal.contientProduit(produit)) {
					etalsProduit[indiceNouvelEtalProduit] = etals[i];
					indiceNouvelEtalProduit++;
				}
			}
			return etalsProduit;
		}
		
		public Etal trouverVendeur(Gaulois gaulois) {
			Etal etalGaulois = null;
			for (int i = 0; i < etals.length && etalGaulois != null; i++) {
				Etal etal = etals[i];
				if(etal.getVendeur().getNom().equals(gaulois.getNom())) {
					etalGaulois = etal;
				}
			}
			return etalGaulois;
		}
		
		public String afficherMarche() {
			int nbEtalVide = 0;
			StringBuilder chaine = new StringBuilder();
			for (int i = 0; i < etals.length; i++) {
				Etal etal = etals[i];
				if (etal.isEtalOccupe()) {
					chaine.append(etal.afficherEtal());
				}else{ 
					nbEtalVide ++; 
				}
			}
			chaine.append("Il reste " + nbEtalVide + "�tals non utilis�s dans le march�.\n"); 
			return chaine.toString();
		}
	}
	
	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException {
		if (chef == null) {
			throw new VillageSansChefException("Le nillage n'a pas de chef");
		}
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherhce un endroit pour vendre " + nbProduit + produit + ".\n");
		int NumEtal = marche.trouverEtalLibre();
		marche.utiliserEtal(NumEtal, vendeur, produit, nbProduit);
		chaine.append("Le vendeur " + vendeur.getNom() + "vend des " + produit + " à l'étal n°" + NumEtal + ".\n");
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Les vendeurs qui proposent des " + produit + " sont :\n");
		Etal[] ProduitEtals = marche.trouverEtals(produit);
		for(int i = 0; i < ProduitEtals.length; i++) {
			chaine.append("- " + ProduitEtals[i].getVendeur().getNom() + "\n");
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		StringBuilder chaine = new StringBuilder();
		Etal numEtal = marche.trouverVendeur(vendeur);
		chaine.append(vendeur.getNom() + " est à l'étal numéro " + numEtal);
		chaine.toString();
		return numEtal;
	}
	
	public String partirVendeur(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur).libererEtal();
	}
	
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Le marché du village " + nom + " possède plusieurs étals :\n");
		int nbEtalsVides = 0;
		for(int i = 0; i < etals.length; i++) {
			Etal etal = etals[i];
			if (etal.isEtalOccupe()) {
				chaine.append(etals[i].getVendeur() + " vend " + etals[i].getQuantite() + etals[i].getProduit() + "\n");
			}else{ 
				nbEtalsVides ++; 
			}
		}
		chaine.append("Il reste " + nbEtalsVides + "�tals non utilis�s dans le march�.\n"); 
		return chaine.toString();
	}
}