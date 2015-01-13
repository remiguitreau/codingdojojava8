package fr.remiguitreau.codingdojo.java8;

import java.util.Comparator;

public class MeasurementComparatorByTimestamp implements Comparator<Measurement> {

	final static MeasurementComparatorByTimestamp instance = new MeasurementComparatorByTimestamp();
	
	static MeasurementComparatorByTimestamp getInstance() {
		return instance;
	}
	
	private MeasurementComparatorByTimestamp() {
		
	}
	
	@Override
	public int compare(Measurement meas1, Measurement meas2) {
		return meas1.getTimestamp().compareTo(meas2.getTimestamp());
	}

}
