package Contexte;

public class Génération {
	public IndividuClass[] nouvellePopulation;
	public int nombreDuCroisementPourPopulationInitiale;
	public int nombreDuMutationPourPopulationInitiale;

	public Génération(IndividuClass[] nouvellePopulation, int nombreDuCroisementPourPopulationInitiale, int nombreDuMutationPourPopulationInitiale) {
		this.nouvellePopulation = nouvellePopulation;
		this.nombreDuCroisementPourPopulationInitiale = nombreDuCroisementPourPopulationInitiale;
		this.nombreDuMutationPourPopulationInitiale = nombreDuMutationPourPopulationInitiale;
	}
}
