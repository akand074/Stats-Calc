package com.android.statscalc.stats;

import java.util.ArrayList;

public class Descriptive {
	private ArrayList<Double> sample;
	boolean sorted;
	
	public Descriptive(){
		sample = new ArrayList<Double>();
		sorted = false;
	}
	
	public void addValue(double value){
		sample.add(value);
	}
	
	public void clear(){
		sample.clear();
	}
	
	public double getValue(int index){
		return sample.get(index);
	}
	
	public int getSampleSize(){
		return sample.size();
	}
	
	public double getMin(){
		if(!sorted){
			sortArray();
		}
		return sample.get(0);
	}
	
	public double getMax(){
		if(!sorted){
			sortArray();
		}
		return sample.get(sample.size() - 1);
	}
	
	public double getSum(){
		double sum = 0;
		for(int i = 0; i < sample.size(); i ++){
			sum += sample.get(i);
		}
		return sum;
	}
	
	public double getMean(){
		double mean = 0;
		mean = this.getSum();
                mean /= sample.size();
		return mean;
	}
	
	public double getMedian(){
		if(!sorted){
			sortArray();
		}
		if (sample.size() % 2 == 0) {
			return (sample.get(sample.size() / 2 - 1) + sample.get(sample.size() / 2)) / 2;
		} else {
			return sample.get(sample.size() / 2);
		}
	}
	
	public double getMode(){
		int temp = 0;
		int index = 0;
		for(int i = 0; i < sample.size() - 1; i++){
			int count = 0;
			for(int j = 0; j < sample.size(); j++){
				if(sample.get(i).equals(sample.get(j))){
					count++;
				}
			}
			if(count > temp){
				temp = count;
				index = i;
			}
		}
		if (temp < 2){
			return 0;
		}
		return sample.get(index);
	}
	
	public double getStandardDeviation(){
		double mean = this.getMean();
		double standardDeviation = 0;
		for(int i = 0; i < sample.size(); i++){
			standardDeviation += Math.pow(sample.get(i) - mean, 2);
		}
		standardDeviation /= this.getSampleSize();
		standardDeviation = Math.sqrt(standardDeviation);
		return standardDeviation;
		
	}
	
	public double getStandardError(){
		return this.getStandardDeviation() / Math.sqrt(this.getSampleSize());
	}
	
	public double getSkewness(){
		double meanY = this.getMean();
		double skewness = 0;
		for (int i = 0; i < this.getSampleSize(); i++){
			skewness += Math.pow(this.getValue(i) - meanY, 3);
		}
		skewness /= (this.getSampleSize() - 1) * Math.pow(this.getStandardDeviation(), 3);
		return skewness;
	}
	
	public double getKurtosis(){
		double meanY = this.getMean();
		double kurtosis = 0;
		for (int i = 0; i < this.getSampleSize(); i++){
			kurtosis += Math.pow(this.getValue(i) - meanY, 4);
		}
		kurtosis /= (this.getSampleSize() - 1) * Math.pow(this.getStandardDeviation(), 4);
		return kurtosis;
	}

	private void sortArray() {
		for(int i = 0; i < sample.size() - 1; i++){
			for(int j = i + 1; j < sample.size(); j ++){
				if(sample.get(j) < sample.get(i)){
					Double temp = sample.get(j);
					sample.set(j, sample.get(i));
					sample.set(i, temp);
					temp = null;
				}
			}
		}
		
	}

}
