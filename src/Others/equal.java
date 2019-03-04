package Others;

public class equal {

	public static void main(String[] args) {
		/*
		 * String类型中比较用 == 和 equals 的差异
		 */
		String s1 = new String("java");
		String s2 = new String("java");

		System.out.println(s1 == s2);
		System.out.println(s1.equals(s2));

	}
}
