package Contexte;

public class IndividuClass {
	boolean[] Chromosome;
	int X;
	double FitnessFunc;
	double Correction;
	IndividuClass Parent1;
	IndividuClass Parent2;

	IndividuClass(boolean[] Chromosome, int X, double FitnessFunc, double Correction, IndividuClass Parent1, IndividuClass Parent2) {
		this.Chromosome = Chromosome;
		this.X = X;
		this.FitnessFunc = FitnessFunc;
		this.Correction=Correction;
		this.Parent1 = Parent1;
		this.Parent2 = Parent2;

	}
	public boolean [] getChromosome() {
		
		return Chromosome;
	}

	public int getX() {
		return X;
	}

	public double getFitnessFunc() {
		return FitnessFunc;
	}

	public IndividuClass getParent1() {
		return Parent1;
	}

	public IndividuClass getParent2() {
		return Parent2;
	}

	public void setChromosome(boolean[] Chromosome) {
		this.Chromosome = Chromosome;
	}

	public void setX(int X) {
		this.X = X;

	}

	public void setFitnessFunc(double FitnessFunc) {
		this.FitnessFunc = FitnessFunc;
	}

	public void setParent1(IndividuClass Parent1) {
		this.Parent1 = Parent1;
	}

	public void setParent2(IndividuClass Parent2) {
		this.Parent2 = Parent2;
	}

}
