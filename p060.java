/* 
 * Solution to Project Euler problem 60
 * By Nayuki Minase
 * 
 * http://nayuki.eigenstate.org/page/project-euler-solutions
 * https://github.com/nayuki/Project-Euler-solutions
 */

import java.util.Arrays;


public class p060 {
	
	private static final int PRIME_LIMIT = 100000;
	
	private static int[] primes = Library.listPrimes(PRIME_LIMIT);
	
	
	public static void main(String[] args) {
		int sumLimit = PRIME_LIMIT;
		while (true) {
			int sum = findSetSum(new int[]{}, 5, sumLimit - 1);
			if (sum == -1) {
				System.out.println(sumLimit);
				break;
			}
			sumLimit = sum;
		}
	}
	
	
	private static int findSetSum(int[] prefix, int targetSize, int sumLimit) {
		if (prefix.length == targetSize) {
			int sum = 0;
			for (int i : prefix)
				sum += primes[i];
			return sum;
			
		} else {
			int i;
			if (prefix.length == 0)
				i = 0;
			else
				i = prefix[prefix.length - 1] + 1;
			
			for (; i < primes.length && primes[i] <= sumLimit; i++) {
				boolean pass = true;
				for (int j : prefix)
					pass &= isConcatenationPrime(i, j) && isConcatenationPrime(j, i);
				
				if (pass) {
					int[] appended = Arrays.copyOf(prefix, prefix.length + 1);
					appended[appended.length - 1] = i;
					int sum = findSetSum(appended, targetSize, sumLimit - primes[i]);
					if (sum != -1)
						return sum;
				}
			}
			return -1;
		}
	}
	
	
	private static boolean isConcatenationPrime(int x, int y) {
		x = primes[x];
		y = primes[y];
		
		int mult = 1;
		for (int temp = y; temp != 0; temp /= 10)
			mult *= 10;
		
		return isPrime((long)x * mult + y);
	}
	
	
	private static boolean isPrime(long x) {
		if (x < 0)
			throw new IllegalArgumentException();
		if (x == 0 || x == 1)
			return false;
		else if (x == 2)
			return true;
		else {
			long end = Library.sqrt(x);
			for (int p : primes) {
				if (p > end)
					break;
				if (x % p == 0)
					return false;
			}
			for (long i = primes[primes.length - 1] + 2; i <= end; i += 2) {
				if (x % i == 0)
					return false;
			}
			return true;
		}
	}
	
}
