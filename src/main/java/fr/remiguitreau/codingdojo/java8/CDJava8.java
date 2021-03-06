package fr.remiguitreau.codingdojo.java8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CDJava8 {

	@NonNull
	private final MeasurementSubmitter measurementSubmitter;
	
	public void submitPositiveMeasurementByDecreasingValueOrder(
			List<Measurement> measurements) {
		Collections.sort(measurements, new Comparator<Measurement>() {
			@Override
			public int compare(Measurement meas1, Measurement meas2) {
				return Float.valueOf(meas2.getValue()).compareTo(meas1.getValue());
			}
		});
		
		for(final Measurement measurement : measurements) {
			if(measurement.getValue() > 0) {
				measurementSubmitter.submitMeasurement(measurement);
			}
		}
	}
	
	public List<Float> onlyGoodValues(final List<Measurement> measurements) {
		final List<Float> goodValues = new ArrayList<Float>();
		for(final Measurement measurement : measurements) {
			if(measurement.getQualityOrNull() != null && measurement.getQualityOrNull().ordinal() <= Quality.GOOD.ordinal()) {
				goodValues.add(measurement.getValue());
			}
		}
		return goodValues;
	}

	public Map<Quality, List<Measurement>> organizeMeasurementsByQualityAndSortedByTimestamp(
			List<Measurement> measurements) {
		final Map<Quality, List<Measurement>> result = new HashMap<Quality, List<Measurement>>();
		for(final Measurement measurement : measurements) {
			if(measurement.getQualityOrNull() != null) {
				if(!result.containsKey(measurement.getQualityOrNull())) {
					result.put(measurement.getQualityOrNull(), new ArrayList<Measurement>());
				}
				result.get(measurement.getQualityOrNull()).add(measurement);
			}
		}
		for(final List<Measurement> qualityMeasurements : result.values()) {
			Collections.sort(qualityMeasurements, MeasurementComparatorByTimestamp.getInstance());
		}
		return result;
	}

}
