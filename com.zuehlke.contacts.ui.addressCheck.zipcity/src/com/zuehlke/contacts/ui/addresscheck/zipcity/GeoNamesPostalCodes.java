package com.zuehlke.contacts.ui.addresscheck.zipcity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class GeoNamesPostalCodes {

	private static final GeoNamesPostalCodes INSTANCE = new GeoNamesPostalCodes();

	private Set<PostalCodeCity> data = new HashSet<PostalCodeCity>();

	private GeoNamesPostalCodes() {
		try {
			loadData();
		} catch (IOException e) {
			throw new RuntimeException(
					"Could not load postal code/city data for Germany");
		}
	}

	private void loadData() throws IOException {
		InputStream in = getClass().getResourceAsStream("/geonames/DE.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(in,
				"UTF-8"));

		try {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split("\\t");
				PostalCodeCity pcc = new PostalCodeCity(parts[1], parts[2]);
				System.out.println(pcc);
				data.add(pcc);
			}
		} finally {
			reader.close();
		}
	}

	public boolean exists(String postalCode, String city) {
		return data.contains(new PostalCodeCity(postalCode, city));
	}

	public static GeoNamesPostalCodes getInstance() {
		return INSTANCE;
	}

	private class PostalCodeCity {
		private final String postalCode;
		private final String city;

		private PostalCodeCity(String postalCode, String city) {
			this.postalCode = postalCode;
			this.city = city;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((city == null) ? 0 : city.hashCode());
			result = prime * result
					+ ((postalCode == null) ? 0 : postalCode.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			PostalCodeCity other = (PostalCodeCity) obj;
			if (city == null) {
				if (other.city != null) {
					return false;
				}
			} else if (!city.equals(other.city)) {
				return false;
			}
			if (postalCode == null) {
				if (other.postalCode != null) {
					return false;
				}
			} else if (!postalCode.equals(other.postalCode)) {
				return false;
			}
			return true;
		}

		@Override
		public String toString() {
			return postalCode + " " + city;
		}
	}
}
