package com.example.ufosighting.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.ufosighting.dto.UfoSighting;

@Service("ufoSightingService")
public class UfoSightingServiceImpl implements UfoSightingService {
	private static final String DATE_PATTERN = "\\d{8}";
	private static final String EMPTY = "";
	private static final String TAB_REGEX = "\\t";
	private static final String UFO_AWESOME_TSV = "ufo_awesome.tsv";
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

	/**
	 * Returns all the sightings in the tsv file.
	 */
	public List<String> getAllSightings() {
		return getAllUfoSightingList();
	}

	/**
	 * Search for sightings happened in the specified year and month;
	 *
	 * @param yearSeen
	 *            The year when the sighting happened
	 * @param monthSeen
	 *            The month when the sightings happened
	 */
	public List<String> search(int yearSeen, int monthSeen) {
		return filterByMonthYear(yearSeen, monthSeen);
	}

	/**
	 * This method filters UfoSightings list for given yearSeen and monthSeen 
	 * @param yearSeen
	 * @param monthSeen
	 * @return
	 */
	private static List<String> filterByMonthYear(final int yearSeen, final int monthSeen) {
		final List<String> list = getAllUfoSightingList();

		final Pattern pattern = Pattern.compile(DATE_PATTERN);

		final Predicate<String> filter = entry -> {
			final String[] strs = entry.split(TAB_REGEX);
			final String dateSeen = isValueExists(strs, 0) ? strs[0] : EMPTY;

			Optional<String> option = Optional.of(dateSeen);
			if (option.isPresent()) {
				Date date = null;
				try {
					if (pattern.matcher(dateSeen).matches()) {
						date = DATE_FORMAT.parse(dateSeen);
						final LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						int year = localDate.getYear();
						int month = localDate.getMonthValue();
						if ((yearSeen == year) && (monthSeen == month)) {
							return true;
						}
					}
				} catch (ParseException e) {// This is not best practice to catch all application exceptions.
					// All exceptions should be propagated to caller with meaningful messages.	
					e.printStackTrace();
				}
			}
			return false;
		};

		return list.stream().filter(filter).collect(Collectors.toList());
	}

	/**
	 * This method is not used and left uncommented, as this is what i intended to do if i were allowed to modify 
	 * interface
	 * @param yearSeen
	 * @param monthSeen
	 * @return
	 */
	private static List<UfoSighting> findByMonthYear(final int yearSeen, final int monthSeen) {
		final List<UfoSighting> ufoSighingList = getAllUfoSightings();

		final Pattern pattern = Pattern.compile(DATE_PATTERN);

		final Predicate<UfoSighting> filter = entry -> {
			final String dateSeen = entry.getDateSeen();
			Optional<String> option = Optional.of(dateSeen);
			if (option.isPresent()) {
				Date date = null;
				try {
					if (pattern.matcher(dateSeen).matches()) {
						date = DATE_FORMAT.parse(dateSeen);
						final LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						int year = localDate.getYear();
						int month = localDate.getMonthValue();
						if ((yearSeen == year) && (monthSeen == month)) {
							return true;
						}
					}
				} catch (ParseException e) { // This is not best practice to catch all application exceptions. 
											// All exceptions should be propagated to caller with meaningful messages.											
					e.printStackTrace();
				}
			}
			return false;
		};
		return ufoSighingList.stream().filter(filter).collect(Collectors.toList());
	}

	/**
	 * This method is not used and left as this is what i intended to do if i were allowed to modify 
	 * interface
	 * @return
	 */
	public static List<UfoSighting> getAllUfoSightings() {
		try (final InputStream inputStream = UfoSightingServiceImpl.class.getClassLoader()
				.getResourceAsStream(UFO_AWESOME_TSV);
				final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));) {
			final List<UfoSighting> ufoSightingList = reader.lines().map(line -> {
				return populateUfoSigntings(line);
			}).collect(Collectors.toList());
			return ufoSightingList;
		} catch (IOException e) {// This is not best practice to catch all application exceptions. 
			// All exceptions should be propagated to caller with meaningful messages.	
			e.printStackTrace();
		}
		return null;
	}

	private static List<String> getAllUfoSightingList() {
		try (final InputStream inputStream = UfoSightingServiceImpl.class.getClassLoader()
				.getResourceAsStream(UFO_AWESOME_TSV);
				final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));) {
			final List<String> ufoSightingList = reader.lines().map(line -> {
				return line;
			}).collect(Collectors.toList());
			return ufoSightingList;
		} catch (IOException e) {// This is not best practice to catch all application exceptions. 
			// All exceptions should be propagated to caller with meaningful messages.	
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * This method making sure that all the required data is available to process and it will avoid runtime errors.
	 * @param line
	 * @return
	 */
	private static UfoSighting populateUfoSigntings(String line) {
		final String[] strs = line.split(TAB_REGEX);
		final String dateSeen = isValueExists(strs, 0) ? strs[0] : EMPTY;
		final String dateReported = isValueExists(strs, 1) ? strs[1] : EMPTY;
		final String placeSeen = isValueExists(strs, 2) ? strs[2] : EMPTY;
		final String shape = isValueExists(strs, 3) ? strs[3] : EMPTY;
		final String duration = isValueExists(strs, 4) ? strs[4] : EMPTY;
		final String description = isValueExists(strs, 5) ? strs[5] : EMPTY;

		return new UfoSighting(dateSeen, dateReported, placeSeen, shape, duration, description);
	}

	private static boolean isValueExists(final String[] strings, final int index) {
		if (strings.length > index && strings[index] != null) {
			return true;
		}
		return false;
	}
}
