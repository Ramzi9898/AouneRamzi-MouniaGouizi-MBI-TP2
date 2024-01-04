package Contexte;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

public class Génétique {
static Scanner scanner = new Scanner(System.in);	//Scanner declaration

    
	public static void main(String[] args) {

		int TaillePopulation = 4;

		System.out.print("Generations : ");
		int MaximumGeneration    =  scanner.nextInt();			//int
		System.out.print("Pc : ");
	    double Pc  =  scanner.nextFloat();			//float
		System.out.print("Pm : ");
	    double Pm =  scanner.nextDouble();			//double
	   

//		int MaxGen = 30;
//		double Pc = 0.9;
//		double Pm = 0.01;
	    	
		int NombreCroisementFinale = 0;
		int NombreMutationFinale = 0;
		boolean[][] PopulationInitiale = {  { true, false, false, true, false }, 
										    { false, false, true, true, false },
										    { false, true, false, true, true }, 
										    { true, true, false, true, true }   };

		IndividuClass[] PremierePopulation = initPop(PopulationInitiale);


		
		
		double[] TableDeStatistiques = statistique(PremierePopulation);
		double Minimum = TableDeStatistiques[0];
		double Maximum = TableDeStatistiques[1];
		double SommeIndividus = TableDeStatistiques[2];
		double Moyenne = TableDeStatistiques[3];
		int Generation = 0;

	 
		System.out.println(Minimum);  
		System.out.println(Maximum);
		System.out.println(SommeIndividus);
		System.out.println(Moyenne);

		while (Generation < MaximumGeneration) {
			Generation++;

			Génération Gen = generate(PremierePopulation, TaillePopulation, SommeIndividus , Pc , Pm );
			IndividuClass[] nouvellePopulation = Gen.nouvellePopulation;
		
			
			int nombreDuCroisementPourPopulationInitiale = Gen.nombreDuCroisementPourPopulationInitiale;
			int nombreDuMutationPourPopulationInitiale = Gen.nombreDuMutationPourPopulationInitiale;

			double[] results = statistique(PremierePopulation);
			Minimum = results[0];
			Maximum = results[1];
			SommeIndividus = results[2];
			Moyenne = results[3];

			NombreCroisementFinale += nombreDuCroisementPourPopulationInitiale;
			NombreMutationFinale += nombreDuMutationPourPopulationInitiale;

			System.out.println("Generation" + Gen);

			for (IndividuClass ind : nouvellePopulation) {
				System.out.println("Genome: " + getGenomeString(ind.getChromosome()) + ", x: " + ind.getX() + ", fitness: "
						+ ind.getFitnessFunc());
			}
//	         // Display average fitness
			System.out.println("\nAverage Fitness-------------------------------:" + Moyenne);
//
//	         // Display maximum fitness
			System.out.println("Maximum Fitness---------------------------------:" + Maximum);

//	         // Display number of crossovers and mutations
			System.out.println("Number of Crossovers:" + nombreDuCroisementPourPopulationInitiale);
			System.out.println("Number of Mutations:" + nombreDuMutationPourPopulationInitiale);

			PremierePopulation = nouvellePopulation;

		}
		System.out.println("Number of Crossovers total :" + NombreCroisementFinale);
		System.out.println("Number of mutations  total :" + NombreMutationFinale);

	}

	// init
	static IndividuClass[] initPop(boolean[][] PopulationInitiale) {
		IndividuClass[] Population = new IndividuClass[4];
		boolean[] Chromosome ;
		int X = 0;
		double Correction = 0;
		IndividuClass Parent1 = null;
		IndividuClass Parent2 = null;
		for (int i = 0; i < PopulationInitiale.length; i++) {
			Chromosome = PopulationInitiale[i];
			
			X = decode(Chromosome);
			float FitnessFunc = f(X);
			IndividuClass individu = new IndividuClass(Chromosome, X, FitnessFunc, Correction, Parent1, Parent2);
			Population[i] = individu;
		 
		}
		return Population;
	}

	// change to string I O
	static String getGenomeString(boolean[] bs) {
		StringBuilder result = new StringBuilder();
		for (boolean bit : bs) {
			result.append(bit == true ? 'I' : 'O');
		}
		return result.toString();
	}

	// decode
	static int decode(boolean[] Chromosome) {


		int S = 0;
		int D = 1;
		
	 
			for (int i = Chromosome.length - 1; i >= 0; i--) {
				if (Chromosome[i] == true ) {
					 
					S += D;
				}
				D *= 2;
			}
	
		return S;
	}

	// f
	static float f(int X) {

		return -X * X + 4 * X;

	}

	// crossover
	public static Croisement crossover(boolean[] Parent1, boolean[] Parent2, double Pc) {

		int Crosses = 0;
		
		

		Random aléatoire = new Random();
		
		boolean[] NouveauParent1 = Parent1;
		boolean[] NouveauParent2 = Parent2;
		
		
		for (int i = 0; i < 5; i++) {

			if (aléatoire.nextDouble() <= Pc) {
				boolean temp = NouveauParent1[i];
				NouveauParent1[i] = NouveauParent2[i];
				NouveauParent2[i] = temp;
				Crosses++;
			}
		}

		boolean[] Enfant1, Enfant2;
	
		if (decode(NouveauParent1) > 0 && decode(NouveauParent2) > 0 && decode(NouveauParent1) < 31 && decode(NouveauParent2) < 31) {
			Enfant1 = NouveauParent1;
			Enfant2 = NouveauParent2;
		} else {
			// no crosses done
			Enfant1 = Parent1;
			Enfant2 = Parent2;
			Crosses--;
		}
	
		return new Croisement(Enfant1, Enfant2, Crosses);
	}

// mutation 
	static Mutation mutate(boolean[] Individu, double Pm) {
		boolean[] IndividuOriginal = Individu;
		int nombreMutation = 0;
		Random aléatoire = new Random();

		for (int i = 0; i < 5; i++) {
			if (aléatoire.nextDouble() <= Pm) {
				nombreMutation++;
				Individu[i] = !Individu[i];
			}
		}

		boolean[] Enfant = Individu;

		int x = decode(Individu);
		
		if (x < 1 && x > 30) {
			Enfant = IndividuOriginal;
			nombreMutation--;
		}
	 
		return new Mutation(Enfant, nombreMutation);
	}

	// statistique
	static double[] statistique(IndividuClass[] population) {
		double Minimum = population[0].FitnessFunc;
		double Maximum = population[0].FitnessFunc;
		double SommeIndividus = population[0].FitnessFunc;
		double Moyenne = 0;

		for (int i = 1; i < population.length; i++) {
			IndividuClass individu = population[i];
			SommeIndividus += individu.FitnessFunc;
			if (individu.FitnessFunc > Maximum) {
				Maximum = individu.FitnessFunc;
			}
			if (individu.FitnessFunc < Minimum) {
				Minimum = individu.FitnessFunc;
			}
		}
		Moyenne = SommeIndividus / population.length;
		return new double[] { Minimum, Maximum, SommeIndividus, Moyenne };
	}

	public static Génération generate(IndividuClass[] PremierePopulation, int TaillePopulation, double SommeIndividus, double pc, double pm) {

		IndividuClass[] nouvPopulation = new IndividuClass[4];
		int taille = TaillePopulation;

		int numbCrossPopInit = 0, numbMutPopInit = 0;

		for (int i = 0; i < taille; i += 2) {

			Arrays.sort(PremierePopulation, new Comparator<IndividuClass>() {
				@Override
				public int compare(IndividuClass c1, IndividuClass c2) {
					return Double.compare(c2.getFitnessFunc(), c1.getFitnessFunc());
				}
			});
			


			IndividuClass gars1 = PremierePopulation[0];
			IndividuClass gars2 = PremierePopulation[1];
			double minFitness = PremierePopulation[1].getFitnessFunc();

			System.out.println("couple : " + gars1.getX() + " " + gars2.getX());
			


			Croisement X = crossover(gars1.getChromosome(), gars2.getChromosome(), pc);


			int Crosses = Croisement.Crosses;
			numbCrossPopInit += Crosses; 
		
			
			boolean[] fils_1_beforeMutation = X.Enfant1;
			boolean[] fils_2_beforeMutation = X.Enfant2; 
			
			Mutation mutationResult1 = mutate(fils_1_beforeMutation, pm); 
			int nombreMutation1 = mutationResult1.nombreMutation;
			numbMutPopInit += nombreMutation1;
			boolean[] Enfant1 = mutationResult1.Enfant; 
			
			Mutation mutationResult2 = mutate(fils_2_beforeMutation, pm);
			int nombreMutation2 = mutationResult2.nombreMutation;
			numbMutPopInit += nombreMutation2;
			boolean[] Enfant2 = mutationResult2.Enfant;


			int X1 = decode(Enfant1);
			int X2 = decode(Enfant2);

			System.out.println(" kids : " + X1 + " " + X2);

			double FitnessFunc1 = f(X1);
			double FitnessFunc2 = f(X2);

			// accept best fitness
			IndividuClass individu1, individu2;

			if (minFitness < FitnessFunc1) {
				individu1 = new IndividuClass(Enfant1, X1, FitnessFunc1, FitnessFunc2 = 0, gars1, gars2);
			} else {
				individu1 = new IndividuClass(gars1.getChromosome(), gars1.getX(), gars1.getFitnessFunc(), FitnessFunc2, gars1.getParent1(),
						gars1.getParent2());
			}

			if (minFitness < FitnessFunc2) {
				individu2 = new IndividuClass(Enfant2, X2, FitnessFunc1 = 0, FitnessFunc2, gars1, gars2);
			} else {
				individu2 = new IndividuClass(gars2.getChromosome(), gars2.getX(), gars2.getFitnessFunc(), FitnessFunc2, gars2.getParent1(),
						gars2.getParent2());
			}

			nouvPopulation[i] = individu1;
			nouvPopulation[i + 1] = individu2;
		}
		 
	 
		return new Génération(nouvPopulation, numbCrossPopInit, numbMutPopInit);
	}


}