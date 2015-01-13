package fr.remiguitreau.codingdojo.java8;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CDJava8 {

	@NonNull
	private final MeasurementSubmitter measurementSubmitter;

	public void submitPositiveMeasurementByDecreasingValueOrder(
			List<Measurement> measurements) {

		measurements.sort(comparing(Measurement::getValue).reversed());
		measurements
				.stream()
				.filter(measurement -> measurement.getValue() > 0)
				.forEach(measurementSubmitter::submitMeasurement);

	}

	public List<Float> onlyGoodValues(final List<Measurement> measurements) {
		return measurements
				.stream()
				.filter(Measurement::valueHasGoodQuality)
				.map(Measurement::getValue)
				.collect(toList());
	}

	public Map<Quality, List<Measurement>> organizeMeasurementsByQualityAndSortedByTimestamp(
			List<Measurement> measurements) {
		final ConcurrentHashMap<Quality, List<Measurement>> result = new ConcurrentHashMap<>();

		measurements
				.stream()
				.filter(measurement -> measurement.getQuality().isPresent())
				.forEach(measurement -> addToMap(result, measurement));
		result.values().forEach(
				qualityMeasurements -> qualityMeasurements
						.sort(comparing(Measurement::getTimestamp)));
		return result;
	}

	private void addToMap(
			final ConcurrentHashMap<Quality, List<Measurement>> result,
			final Measurement measurement) {
		result.computeIfAbsent(
				measurement.getQualityOrNull(),
				quality -> new ArrayList<Measurement>())
				.add(measurement);
	}
}
