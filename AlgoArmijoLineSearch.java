public class SDArmijo extends SteepestDescent {

	private double maxStep; // Armijo max step size
	private double beta; // Armijo beta parameter
	private double tau; //Armijo tau parameter
	private int K; // Armijo maximum number of iterations
	
	// constructors
	public SDArmijo() {
		
		super();
		this.maxStep = 1;
		this.beta = 0.0001;
		this.tau = 0.5;
		this.K = 10;
	}
	
	public SDArmijo (double eps, int maxIter, double x0, double maxStep, double beta, double tau, int K) {
		
		super(eps, maxIter, x0);
		this.maxStep = maxStep;
		this.beta = beta;
		this.tau = tau;
		this.K = K;
	}
	
	// getters
	public double getMaxStep () {
		
		return this.maxStep;
	}
	public double getBeta () {
		
		return this.beta;
	}
	public double getTau () {
		
		return this.tau;
	}
	public int getK () {
		
		return this.K;
	}
	
	// setters
	public void setMaxStep (double a) {
		
		this.maxStep = a;
	}
	public void setBeta (double a) {
		
		this.beta = a;
	}
	public void setTau (double a) {
		
		this.tau = a;
	}
	public void setK (int a) {
		
		this.K = a;
	}
	
	// other functions
	
	// Armijo line search
	@Override
	public double lineSearch (Polynomial P, double [] x) {
		
		// set alpha to at maxStep
		double a = this.getMaxStep(); // get the max step
		
		// get the beta and tau values 
		double beta = this.getBeta();
		double tau = this.getTau();
		
		// get the maximum number of iterations
		int maxIter = this.getK();
		
		// get the gradient and the gradient norm
		double[] gradient = P.gradient(x);
		double gradientNorm = P.gradientNorm(x);
		
		boolean flag = true;
		int counter = 0;
		
		// for loop with the conditions for the Armijo algorithm
		do {
			
			// variable for the left side of the inequality
			double LHS;
			double [] LHS_vector = new double[x.length];
			
			// multiply the gradient by alpha
			for (int i = 0; i < x.length; i++) {
				
				LHS_vector[i] = (a * gradient[i]);
			}
			
			// subtract the result above from the current point
			for (int i = 0; i < x.length; i++) {
				
				LHS_vector[i] = x[i] - LHS_vector[i];
			}
			
			// calculate the final LHS
			LHS = P.f(LHS_vector);
			
			// variable for the right hand side of the inequality
			double RHS;
			
			// get the entire right hand side in one calculation
			RHS = P.f(x) - (a * beta * Math.pow(gradientNorm, 2));
			
			if ((LHS > RHS) && !(counter < maxIter)) {
				
				a = -1;
				flag = false;
				System.out.println("   Armijo line search did not converge!");
			}
			else {
				
				if (LHS > RHS && (counter < maxIter)) {
					
					a = a * tau;
					counter++;
					
				} else {
					
					flag = false;
				} 
			}
			
		} while (flag);
		
		
		return a;
	}	
	// get algorithm parameters from user
	@Override
	public boolean getParamsUser () {
		
		double MAX = Pro5_alalwank.getDouble("Enter Armijo max step size (0 to cancel): ", 0.00, Double.MAX_VALUE);
		
		if (MAX == 0) {
			
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false;
		} else {
			
			this.setHasResults(false);
		}
		
		double BETA = Pro5_alalwank.getDouble("Enter Armijo beta (0 to cancel): ", 0.00, 1.00D);
		
		if (BETA == 0) {
			
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false;
		} else {
			
			this.setHasResults(false);
		}
		
		double TAU = Pro5_alalwank.getDouble("Enter Armijo tau (0 to cancel): ", 0.00, 1.00);
		
		if (TAU == 0) {
			
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false;
		} else {
			
			this.setHasResults(false);
		}
		
		int K = Pro5_alalwank.getInteger("Enter Armijo K (0 to cancel): ", 0, Integer.MAX_VALUE);
		
		if (K == 0) {
			
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false;
		} else {
			
			this.setHasResults(false);
		}
		
		double EPS = Pro5_alalwank.getDouble("Enter tolerance epsilon (0 to cancel): ", 0.00, Double.MAX_VALUE);
		
		if (EPS == 0) {
			
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false;
		} else {
			
			this.setHasResults(false);
		}
		
		int ITER = Pro5_alalwank.getInteger("Enter maximum number of iterations (0 to cancel): ", 0, 10000);
		
		if (ITER == 0) {
			
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false;
		} else {
			
			this.setHasResults(false);
		}
		
		double startingPoint = Pro5_alalwank.getDouble("Enter value for starting point (0 to cancel): ", -1 * Double.MAX_VALUE, Double.MAX_VALUE);
		
		if (startingPoint == 0) {
			
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
		} else {
			
			this.setHasResults(false);
		}
		
		// set the algorithm parameters
		
		this.setMaxStep(MAX);
		this.setBeta(BETA);
		this.setTau(TAU);
		this.setK(K);
		this.setEps(EPS);
		this.setMaxIter(ITER);
		this.setX0(startingPoint);
		
		System.out.println("\nAlgorithm parameters set!\n");
		
		return true;
	}
	// print algorithm parameters 
	@Override
	public void print() {
		
		System.out.println("SD with an Armijo line search:");
		System.out.println("Tolerance (epsilon): " + this.getEps());
		System.out.println("Maximum iterations: " + this.getMaxIter());
		System.out.println("Starting point (x0): " + this.getX0());
		System.out.println("Armijo maximum step size: " + this.getMaxStep());
		System.out.println("Armijo beta: " + this.getBeta());
		System.out.println("Armijo tau: " + this.getTau());
		System.out.println("Armijo maximum iterations: " + this.getK());
		System.out.println();
	}
}
