public class SteepestDescent {

	//tolerance
	private double eps;
	//maximum number of iterations
	private int maxIter;
	//step size alpha
	private double stepSize;
	//starting point
	private double[] x0;
	//best point found
	private double[] bestPoint;
	//best object function value found
	private double bestObjVal;
	//best gradient norm found
	private double bestGradNorm;
	//computation time needed
	private long compTime;
	//number of iterations needed
	private int nIter;
	//whether or not results exist
	private boolean resultsExist;
	
	//constructors
	public SteepestDescent() {
		
		this.eps = 0;
		this.maxIter = 0;
		this.stepSize = 0;
		this.x0 = new double[0];
		this.bestPoint = new double[0];
		this.bestObjVal = 0;
		this.bestGradNorm = 0;
		this.compTime = 0;
		this.nIter = 0;
		this.resultsExist = false;
	}
	public SteepestDescent(double eps, int maxIter, double stepSize, double[] x0) {
		
		this.eps = eps;
		this.maxIter = maxIter;
		this.stepSize = stepSize;
		this.x0 = x0;
	}

	//getters
	public double getEps() {
		return this.eps;
	}
	public int getMaxIter() {
		return this.maxIter;
	}
	public double getStepSize() {
		return this.stepSize;
	}
	public double[] getX0() {
		return this.x0;
	}
	public double getBestObjVal() {
		return this.bestObjVal;
	}
	public double getBestGradNorm() {
		return this.bestGradNorm;
	}
	public double[] getBestPoint() {
		return this.bestPoint;
	}
	public int getNIter() {
		return this.nIter;
	}
	public long getCompTime() {
		return this.compTime;
	}
	public boolean hasResults() {
		return this.resultsExist;
	}

	//setters
	public void setEps (double a) {
		this.eps = a;
	}
	public void setMaxIter (int a) {
		this.maxIter = a;
	}
	public void setStepSize (double a) {
		this.stepSize = a;
	}
	public void setXO (int j, double a) {
		this.x0[j] = a;
	}
	public void setBestObjVal (double a) {
		this.bestObjVal = a;
	}
	public void setBestGradNorm (double a) {
		this.bestGradNorm = a;
	}
	public void setBestPoint (double[] a) {
		this.bestPoint = a;
	}
	public void setCompTime (long a) {
		this.compTime = a;
	}
	public void setNIter (int a) {
		this.nIter = a;
	}
	public void setHasResults (boolean a) {
		this.resultsExist = a;
	}

	//other methods
	
	//initialize member arrays to correct size
	public void init (int n) {
		
		this.x0 = new double[n];
		this.bestPoint = new double[n];
	}
	//run the steepest descent algorithm
	public void run (Polynomial P) {
		
		if (this.getEps() == 0 || this.getMaxIter() == 0 || this.getStepSize() == 0) {
			
			this.setEps(0.001);
			this.setMaxIter(100);
			this.setStepSize(0.05);
			
			this.init(P.getN());
			
			for (int i = 0; i < P.getN(); i++) {
				
				this.setXO(i, 1);
			}
		}
		
		System.out.println("--------------------------------------------------------------");
		System.out.println("      f(x)   norm(grad)   # iter   Comp time (ms)   Best point   ");
		System.out.println("--------------------------------------------------------------");
		
		int iter = 0;
		double norm;
		double [] curr = this.x0;
		long compTime = 0;
		double f_x;
		
		do {
			
			long temp = System.currentTimeMillis();
			
			f_x = P.f(curr);
			System.out.printf("%10.6f", f_x);
			
			norm = P.gradientNorm(curr);
			System.out.printf("%13.6f", norm);
			
			System.out.printf("%9d", iter);
			iter++;
			
			double[] next = takeStep(P, curr, P.getN());
			
			long elapsed = System.currentTimeMillis()-temp;
			
			compTime += elapsed;
			
			int tempCompTime = (int)compTime;
			
			System.out.printf("%17d   ", tempCompTime);
			
			//print out current point
			for (int i = 0; i < curr.length; i++) {
				
				if (i == curr.length - 1) {
					
					System.out.printf("%.4f", curr[i]);
				} else {
					
					System.out.printf("%.4f, ", curr[i]);
				}
			}
			
			if (iter <= this.maxIter || norm > this.eps) {
				
				curr = next;
			}
			
			System.out.println();
			
		} while (iter <= this.maxIter && norm >= this.eps);
		
		System.out.println();		
		//best function value
		setBestObjVal(f_x);
		//best point
		setBestPoint(curr);
		//best norm grad
		setBestGradNorm(norm);
		//number of iterations
		setNIter(iter);
		//Compile time
		setCompTime(compTime);
		
	}
	//get the next step
	public double [] takeStep(Polynomial P, double [] curr, int n) {
		
		double [] next = new double [this.x0.length];
		
		for (int i = 0; i < n; i++) {
			
			next[i] = curr[i] + (this.lineSearch() * this.direction(P, curr)[i]);
		}
		
		return next;
	}
	//find the next step size
	public double lineSearch () {
		
		return this.stepSize;
	}
	//find the next direction
	public double [] direction (Polynomial P, double[] x) {
		
		double [] direction = new double[P.getN()];
		
		for (int i = 0; i < P.getN(); i++) {
			
			direction[i] = P.gradient(x)[i];
		}
		
		return direction;
	}
	//get parameters from user for n-dim polynomial
	public void getParamsUser (int n) {
		
		double EPS = Pro3_alalwank.getDouble("Enter tolerance epsilon (0 to cancel): ", 0, Double.MAX_VALUE);
		
		if (EPS == 0) {
			
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			
			return;
		}
		
		int ITER = Pro3_alalwank.getInteger("Enter maximum number of iterations (0 to cancel): ", 0, 10000);
		
		if (ITER == 0) {
			
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			
			return;
		}

		double STEP = Pro3_alalwank.getDouble("Enter step size alpha (0 to cancel): ", 0, Double.MAX_VALUE);
		
		if (STEP == 0) {
			
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			
			return;
		}

		this.setEps(EPS);
		
		this.setMaxIter(ITER);
		
		this.setStepSize(STEP);
		
		this.init(n);
		
		System.out.println("Enter values for starting point: ");
		
		double choice = 0;
		
		for (int i = 0; i < n; i++) {
			
			choice = Pro3_alalwank.getDouble("   x" + (i + 1) + ": ", -1 * Double.MAX_VALUE, Double.MAX_VALUE);

			this.setXO(i, choice);
		}
		
		System.out.println("\nAlgorithm parameters set!\n");
	}
	//print algorithm parameters
	public void print () {
		
		System.out.println("Tolerance (epsilon): " + this.eps);
		System.out.println("Maximum iterations: " + this.maxIter);
		System.out.println("Step size (alpha): " + this.stepSize);
		System.out.print("Starting point (x0): ( ");
		for (int i = 0; i < x0.length; i++) {
			
			if (i == x0.length - 1) {
				
				System.out.printf("%.2f )\n\n", this.x0[i]);
			} else {
				
				System.out.printf("%.2f, ", this.x0[i]);
			}
		}
	}
	//print iteration results, column header optional
	public void printResults (boolean rowOnly) {
		
		System.out.println("--------------------------------------------------------------");
		System.out.println("      f(x)   norm(grad)   # iter   Comp time (ms)   Best point   ");
		System.out.println("--------------------------------------------------------------");
		
		System.out.printf("%10.6f%13.6f%9d%17d   ", this.bestObjVal, this.bestGradNorm, this.nIter - 1, (int)this.compTime);
		
		for (int i = 0; i < this.bestPoint.length; i++) {
			
			if (i == this.bestPoint.length - 1) {
				
				if (this.bestPoint[i] < Double.MAX_VALUE) {
					
					System.out.printf("%.4f", this.bestPoint[i]);
					
				} else {
					
					System.out.print("Infinity");
					
				}
			} else {
				
				if (this.bestPoint[i] < Double.MAX_VALUE) {
					
					System.out.printf("%.4f, ", this.bestPoint[i]);
					
				} else {
					
					System.out.print("Infinity, ");
					
				}
			}
		}
		
		System.out.println("\n");
	}
}
