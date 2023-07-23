package strings;

public class EqualsMethod {
	public static void main(String args[]) {
		String s1 = "Rashmi";
		String s2 = "Rashmi";
		String s3 = new String("Rashmi");
		String s4=s1;
		
		if(s1==s2) {
			System.out.println("Both strings at same memory loc STRING POOL");
		}
		if(s1==s3) {
			System.out.println("Strings not at same memory loc");
		}
		if(s1.equals(s2)) {
			System.out.println("Values stored in both strings are equal");
		}
		if(s1==s4) {
			System.out.println("Both strings pointing the same location");
		}
	}
}
