/*
 * http://www.dartmouth.edu/~rc/classes/intro_mpi/Numerical_integration_example.html
 * rewritten as a multi-threaded Java program
 * Jon Hitchcock March 2010
 * Jon Hitchcock February 2019 - changed to use Java 8 java.time, etc
 * Jon Hitchcock September 2019 - use_threads, get_result, printf, etc
 * Jon Hitchcock February 2021 - changed to use Java 10 var, etc
 * Jon Hitchcock September 2023 - changed n to be 1 billion
 */
import java.time.Instant;
import java.time.Duration;
import java.time.LocalDateTime;
import java.lang.management.*;  /* for ManagementFactory */

public class NumIntThreaded {

    /* The function being integrated */
    static double fct(double x) {
        return Math.cos(x);
    }
    
    /* Calculate the integral from x=a, with n intervals each with width h */
    static double integral(double a, int n, double h) {
        double integ = 0.0;     /* initialise integral */
        double h2 = h/2.0;
        for (int j=0; j<n; j++) {   /* sum over all "n" intervals */
            double aij = a + j*h;   /* lower limit of interval "j" */
            integ += fct(aij+h2) * h;
        }
        return integ;
    }

    /* Get the thread compute time in seconds.
     * Under Windows 10, the resolution is 1/64 second.
     */
    static double getThreadComputeTime() {
        var bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported() ?
            bean.getCurrentThreadCpuTime() * 0.000_000_001: 0.0;
    }

    /* A class whose instances can be executed by separate threads */
    static class Integral extends Thread {
        private int my_id;      /* identifier for the thread */
        private double my_a;    /* start of range */
        private int my_n;       /* number of intervals */
        private double my_h;    /* width of each interval */
        private double my_result = 0.0;
        
        /* Constructor */
        public Integral(int id, double a, int num, double range) {
            this.my_id = id;
            this.my_a = a;
            this.my_n = num;
            this.my_h = range / num;
        }
        
        /* Method that performs the calculation */
        @Override
        public void run() {
            my_result = integral(my_a, my_n, my_h);
            System.out.printf(
                "Thread %d partial result = %.14f (Processor time used = %.3f)%n",
                my_id, my_result, getThreadComputeTime());
        }
        
        public double get_result() {
            return my_result;
        }
    }

    public static void use_threads(int number_of_threads) {
        double pi = Math.PI;    /* = 3.14159... */
        double a = 0.0;         /* lower limit of integration */
        double b = pi/2.0;      /* upper limit of integration */
        int n = 1_000_000_000;  /* number of intervals */

        var startTime = Instant.now();
        var it = new Integral[number_of_threads];
        /* Find range of values calculated by each thread */
        double my_range = (b - a) / number_of_threads;
        /* Create and start the threads */
        for (int i = 0; i < number_of_threads; i++) {
            double my_a = a + i*my_range;
            it[i] = new Integral(i, my_a, n / number_of_threads, my_range);
            it[i].start();
        }
        
        /* Wait for the threads to terminate and add up their results */
        double result = 0;
        for (int i = 0; i < number_of_threads; i++) {
            try {
                it[i].join();   /* wait for thread to finish! */
            } catch (Exception e) {
                e.printStackTrace();
            }
            result += it[i].get_result();
        }
        var stopTime = Instant.now();

        System.out.printf("The result = %.10f%n", result);
        System.out.printf("Number of intervals = %d%n", n);
        System.out.printf("Number of threads = %d%n", number_of_threads);
        /* Output current date and time */
        System.out.printf("%s%n", LocalDateTime.now().toString());
        /* Output the elapsed time for the calculation.  In Java 17 with
         * Windows 10, the Instant class has a resolution of 100 nanoseconds.
         */
        System.out.printf("Elapsed time = %.7f seconds%n",
            Duration.between(startTime, stopTime).toNanos() * 0.000_000_001);
        System.out.printf("----------%n");
    }
    
    public static void main(String[] args) {
        int number_of_threads;
        /* Process the command-line argument */
        try {
            number_of_threads = Integer.parseInt(args[0]);
        } catch (Exception e) {
            System.err.println(
                "Number of threads must be passed as an argument");
            return;
        }
        use_threads(number_of_threads);
    }
}
