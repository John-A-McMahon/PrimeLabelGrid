package code;

import java.util.ArrayList;

public class Program {

	public static int[] crossover(int[] genome1, int[] genome2) {

		int split = (int) (genome1.length * Math.random());

		int[] newGenome = new int[genome1.length];
		for (int i = 0; i < split; i++) {

			newGenome[i] = genome1[i];

		}
		int shift = 0;
		for (int i = 0; i < genome2.length; i++) {
			if (!contains(newGenome, genome2[i])) {
				newGenome[split + i - shift] = genome2[i];
			} else {
				shift++;
			}
		}
		return newGenome;

	}

	public static boolean contains(int[] genome, int value) {

		for (int i = 0; i < genome.length; i++) {
			if (genome[i] == value) {
				return true;
			}
		}
		return false;
	}

	public static void swap(int[] genome, int index1, int index2) {

		int temp = genome[index1];
		genome[index1] = genome[index2];
		genome[index2] = temp;

	}

	// Stability determines the probability of a mutation (swap) lower is more
	// likely to mutate/swap
	public static int[] mutate(int[] genome, double stability, ArrayList<Integer> badIndexes) {

		for (int i = 0; i < genome.length; i++) {

			if (Math.random() > stability && badIndexes.contains(i)) {
				swap(genome, i, (int) (genome.length * Math.random()));
			}

		}
		return genome;

	}

	public static int[] mutate(int[] genome, double stability) {

		for (int i = 0; i < genome.length; i++) {

			swap(genome, i, (int) (genome.length * Math.random()));

		}
		return genome;

	}

	public static int[] mutate(int[] genome, int width, int height, double stability) {

		for (int i = 0; i < genome.length; i++) {

			genome = generateAgent(width, height);

		}
		return genome;

	}

	public static boolean isCoprime(int a, int b) {

		int min = Math.min(a, b);
		int max = Math.max(a, b);

		for (int i = 2; i <= min; i++) {
			if (max % i == 0 && min % i == 0) {
				return false;
			}
		}
		return true;
	}

	// higher score is bad like golf
	public static int score(int[] genome, int width, int height) {

		int score = 0;
		int size = width * height;

		for (int i = 0; i < size; i++) {

			// checking above
			if (i >= width) {
				if (!isCoprime(genome[i - width], genome[i])) {
					score++;
				}
			}

			// checking below
			if (i < size - width) {
				if (!isCoprime(genome[i + width], genome[i])) {
					score++;
				}
			}

			// checking right
			if ((i % width) != width - 1) {
				if (!isCoprime(genome[i], genome[i + 1])) {
					score++;
				}
			}

			// checking left
			if ((i % width) != 0) {
				if (!isCoprime(genome[i], genome[i - 1])) {
					score++;
				}
			}

		}
		return score;

	}

	// higher score is bad like golf
	public static ArrayList<Integer> scoreIndexes(int[] genome, int width, int height) {
		ArrayList<Integer> badIndexes = new ArrayList<>();
		int score = 0;
		int size = width * height;

		for (int i = 0; i < size; i++) {

			// checking above
			if (i >= width) {
				if (!isCoprime(genome[i - width], genome[i])) {
					score++;
					badIndexes.add(i);
				}
			}

			// checking below
			if (i < size - width) {
				if (!isCoprime(genome[i + width], genome[i])) {
					score++;
					badIndexes.add(i);
				}
			}

			// checking right
			if ((i % width) != width - 1) {
				if (!isCoprime(genome[i], genome[i + 1])) {
					score++;
					badIndexes.add(i);
				}
			}

			// checking left
			if ((i % width) != 0) {
				if (!isCoprime(genome[i], genome[i - 1])) {
					score++;
					badIndexes.add(i);
				}
			}

		}
		return badIndexes;

	}

	public static ArrayList<Integer> generateList(int width, int height) {
		int size = width * height;

		ArrayList<Integer> list = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			list.add(i + 1);
		}
		return list;

	}

	public static int[] generateAgent(int width, int height) {

		int size = width * height;

		ArrayList<Integer> nums = generateList(width, height);

		int index = 0;
		int[] genome = new int[size];

		while (nums.size() > 0) {

			genome[index] = nums.remove((int) (Math.random() * nums.size()));
			index++;

		}
		return genome;

	}

	public static void printGenome(int[] genome, int width, int height) {
		for (int i = 0; i < width * height; i++) {
			System.out.print(genome[i] + ",");
			if ((i + 1) % width == 0 && i > 0) {
				System.out.println("");
			}
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// System.out.println(score(new int[] { 1, 2, 3, 4,5,6,7,8,9 }, 3, 3));
		int prev = 9999999;

		int numAgents = 1000;

		int width = 5;
		int height =5;

		// how many mutations take place 0<x<1
		double stability = 0.9;

		// probability of an agent undergoing a mutation 0<x<1
		double mutationRate = 0.0005;

		// Probability of a bad agent being removed
		double deathRate = 0.0000;

		// Probability of a bad agent being removed
		// note high values make it converge too quickly on crappy values
		double crossoverRate = 0.1;

		ArrayList<int[]> agents = new ArrayList<>();

		for (int i = 0; i < numAgents; i++) {
			agents.add(generateAgent(width, height));
		}

		long startTime = System.currentTimeMillis();

		while (true) {

			// System.out.println(agents.size());

			// crossover
//			for (int i = 0; i < agents.size() - i; i++) {
//
//				if (Math.random() < crossoverRate) {
//					int par1 = (int) (Math.random() * agents.size());
//					int par2 = (int) (Math.random() * agents.size());
//					agents.set(i, crossover(agents.get(par1), agents.get(par2)));
//				}
//			}

			// simulate tournament
			for (int i = 0; i < 10; i++) {

				if (Math.random() < crossoverRate) {
					int par1_0 = (int) (Math.random() * agents.size());
					int par1_1 = (int) (Math.random() * agents.size());

					int minPar1 = par1_0;

					if (score(agents.get(par1_0), width, height) > score(agents.get(par1_1), width, height)) {
						minPar1 = par1_1;
					}

					int par2_0 = (int) (Math.random() * agents.size());
					int par2_1 = (int) (Math.random() * agents.size());

					int minPar2 = par1_0;

					if (score(agents.get(par2_0), width, height) > score(agents.get(par2_1), width, height)) {
						minPar2 = par2_1;
					}

					agents.set(minPar1, crossover(agents.get(minPar1), agents.get(minPar2)));
				}
			}
			int elite = (int) (Math.random() * agents.size());
			
			if(score(agents.get(elite),width,height)<=prev&&Math.random()<0.001) {
				//System.out.println("best");
				for(int i=0; i<agents.size(); i++) {
					if (Math.random() < crossoverRate) {
						agents.set(i, crossover(agents.get(i),agents.get(elite)));
					}
				}
			}

			// mutation (swapping)
//			for (int i = 0; i < agents.size(); i++) {
//				// agents.set(i, mutate(agents.get(i),0.999));
//				if (Math.random() < mutationRate) {
//					agents.set(i, mutate(agents.get(i), stability));
//				}
//
//			}

			for (int i = 0; i < 10; i++) {
				if (Math.random() < mutationRate) {
					agents.set((int) (Math.random() * agents.size()),
							mutate(agents.get((int) (Math.random() * agents.size())), stability));
				}
			}
			// fitness
//			double average = 0;

			// average /= agents.size();

//			if(Math.random()<1) {
//				for (int i = 0; i < agents.size(); i++) {
//
//					int score = score(agents.get(i), width, height);
//					if (score == 0) {
//
//						System.out.println("MINIMUM FOUND!");
//						long endTime = System.currentTimeMillis();
//						
//						long totalTime = endTime-startTime;
//						
//						System.out.println("TIME TAKEN: "+totalTime+" milliseconds");
//						
//						
//						printGenome(agents.get(i),width,height);
//						
//						
//						return;
//					}
//
//				}
//
//			}

			int cur = (int) (Math.random() * agents.size());
			
			
			//Uncomment to show progression
//			if (score(agents.get(cur), width, height) < prev) {
//				prev = score(agents.get(cur), width, height);
//				System.out.println("("+(System.currentTimeMillis()-startTime)+","+score(agents.get(cur), width, height)+")");
//			}

			
			if (score(agents.get(cur), width, height) == 0) {
				System.out.println("MINIMUM FOUND!");
				long endTime = System.currentTimeMillis();

				long totalTime = endTime - startTime;

				System.out.println("TOTAL TIME: "+width+" X "+height+" grid solved in " + totalTime + " milliseconds");
				printGenome(agents.get(cur),width,height);
				return;
			}

//			if (Math.random() < 0.1) {
//				System.out.println("AVERAGE: "+average);
//			}

			// dying
//			for (int i = 0; i < agents.size(); i++) {
//				if (score(agents.get(i), width, height) > average && Math.random() < deathRate) {
//					agents.remove(i);
//				}
//			}

			// dying
			for(int i=0; i<10; i++) {
				if (Math.random() < deathRate) {
					int agent1 = (int) (Math.random() * agents.size());

					int agent2 = (int) (Math.random() * agents.size());

					if (score(agents.get(agent1), width, height) > score(agents.get(agent2), width, height)) {
						agents.remove(agent1);
					} else {
						agents.remove(agent2);
					}

				}
			}
			

			// replacement
			int numReplaced = 0;
			for (int i = 0; i < numAgents - agents.size(); i++) {

				if (Math.random() < 0.1) {
					int par1 = (int) (Math.random() * agents.size()) % agents.size();
					int par2 = (int) (Math.random() * agents.size()) % agents.size();

					if (score(agents.get(par1), width, height) < score(agents.get(par2), width, height)) {
						// agents.add(crossover(agents.get(par1), agents.get(par2)));
						agents.add(crossover(agents.get(par1), agents.get(par2)));
						numReplaced++;
					}
					else {
						agents.add(generateAgent(width,height));
						
					}

				}
			}
			
			
			
//			if(System.currentTimeMillis()-startTime>10000&&Math.random()<0.00001&&agents.size()<10*numAgents) {
//				agents.add(generateAgent(width,height));
//				int par1 = (int) (Math.random() * agents.size()) % agents.size();
//				int par2 = (int) (Math.random() * agents.size()) % agents.size();
//				agents.add(crossover(agents.get(par1), agents.get(par2)));
//				mutate(agents.get(par1),0.5);
//				System.out.println("hi");
//			}
			
			
			
			// System.out.println("NUMBER REPLACED "+numReplaced+"/"+agents.size());

		}

	}

}
