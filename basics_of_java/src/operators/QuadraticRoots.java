package operators;

import java.util.Scanner;

public class QuadraticRoots {
	public static void main(String arrgs[]) {
		Scanner sc = new Scanner(System.in);
		System.out.println("For equation ax^2+ bx+ c: ");
		System.out.println("Enter the value of a: ");
		double a = sc.nextDouble();
		System.out.println("Enter the value pf b: ");
		double b = sc.nextDouble();
		System.out.println("Enter the value of c: ");
		double c = sc.nextDouble();
		
		//discriminant 
		double d = Math.sqrt(b*b - 4*a*c);
		double res1 = (-b + d)/2*a;
		double res2 = (-b - d)/2*a;
		
		System.out.println("The roots of quadratic equation "+a+"x^2 + "+b+"x + "+c+" = "+res1+" and, "+res2);
		
		
		
	}
}
