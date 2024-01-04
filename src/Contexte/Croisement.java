package Contexte;

public class Croisement {
	public static final int Crosses = 0;
	public boolean[] Enfant1;
	public boolean[] Enfant2;

	public int croisement;

	public Croisement(boolean[] Enfant1, boolean[] Enfant2, int croisement) {
		this.Enfant1 = Enfant1;
		this.Enfant2 = Enfant2;

		this.croisement = croisement;
	}
}
