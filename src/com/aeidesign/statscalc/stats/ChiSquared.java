package com.aeidesign.statscalc.stats;

import java.util.ArrayList;

public class ChiSquared {
	ArrayList<Double> expected;
	ArrayList<Double> observed;
	
	public ChiSquared(){
		expected = new ArrayList<Double>();
		observed = new ArrayList<Double>();
	}
	
	public void addExpectedValue(double value){
		expected.add(value);
	}
	
	public void clearExpected(){
		expected.clear();
	}
	
	public int expectedSize(){
		return expected.size();
	}
	
	public void addObservedValue(double value){
		observed.add(value);
	}
	
	public void clearObserved(){
		observed.clear();
	}
	
	public int observedSize(){
		return observed.size();
	}
	
	public double getChiSquared(){
		double X = 0;
		int observedSize = observed.size();
		
		for(int i = 0; i < observedSize; i++){
			X += Math.pow(observed.get(i) - expected.get(i), 2) / expected.get(i);
		}
		return X;
	}
}
