package com.android.statscalc.stats;

public class Bernoulli {
	
	public static double calcProbability(int x, int n, double p){
		return (Factorial.calcFactorial(n) / (Factorial.calcFactorial(x) * Factorial.calcFactorial(n - x))) * Math.pow(p, x) * Math.pow(1 - p, n - x);
	}
	
	public static double calcProbabilityLessThan(int x, int n, double p){
		double probability = 0;
		for(int i = 0; i < x; i++){
			probability += calcProbability(i, n, p);
		}
		return probability;
	}
	
	public static double calcProbabilityLessThanOrEqual(int x, int n, double p){
		double probability = 0;
		for(int i = 0; i <= x; i++){
			probability += calcProbability(i, n, p);
		}
		return probability;
	}
	
	public static double calcProbabilityGreaterThan(int x, int n, double p){
		double probability = 0;
		for(int i = x + 1; i <= n; i++){
			probability += calcProbability(i, n, p);
		}
		return probability;
	}
	
	public static double calcProbabilityGreaterThanOrEqual(int x, int n, double p){
		double probability = 0;
		for(int i = x; i <= n; i++){
			probability += calcProbability(i, n, p);
		}
		return probability;
	}

}
