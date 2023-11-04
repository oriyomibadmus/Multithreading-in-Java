/*
 * http://www.dartmouth.edu/~rc/classes/intro_mpi/Numerical_integration_example.html
 * rewritten as a single-threaded Java program
 * Jon Hitchcock March 2010
 * Jon Hitchcock February 2019 - changed to use Java 8 java.time, etc
 * Jon Hitchcock September 2019 - changed to use printf
 * Jon Hitchcock February 2021 - changed to use Java 10 var, etc
 * Jon Hitchcock September 2023 - changed n to be 1 billion
 */
import java.time.Instant;
import java.time.Duration;
import java.time.LocalDateTime;

public class NumInt {

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

    public static void main(String[] args) {
        double pi = Math.PI;    /* = 3.14159... */
        double a = 0.0;         /* lower limit of integration */
        double b = pi/2.0;      /* upper limit of integration */
        int n = 1_000_000_000;  /* number of intervals */
        
        var startTime = Instant.now();
        double h = (b - a) / n;     /* width of each interval */
        double result = integral(a, n, h);
        var stopTime = Instant.now();

        System.out.printf("The result = %.10f%n", result);
        System.out.printf("Number of intervals = %d%n", n);
        /* Output current date and time */
        System.out.printf("%s%n", LocalDateTime.now().toString());
        /* Output the elapsed time for the calculation.  In Java 17 with
         * Windows 10, the Instant class has a resolution of 100 nanoseconds.
         */
        System.out.printf("Elapsed time = %.7f seconds%n",
            Duration.between(startTime, stopTime).toNanos() * 0.000_000_001);
        System.out.printf("----------%n");
    }
}
