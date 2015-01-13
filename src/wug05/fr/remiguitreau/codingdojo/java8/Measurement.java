package fr.remiguitreau.codingdojo.java8;

import java.util.Date;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Measurement {
	private final Date timestamp;

	private final float value;

	private Quality qualityOrNull;

	Optional<Quality> getQuality() {
		return Optional.ofNullable(qualityOrNull);
	}

	boolean valueHasGoodQuality() {
		return getQuality().orElse(Quality.AWFUL).ordinal() <= Quality.GOOD
				.ordinal();
	}
}
