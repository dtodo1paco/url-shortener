/**
 * 
 */
package org.dtodo1paco.util;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author pac
 *
 */
public class StringUtil {
	static Random random = new SecureRandom();

	public final static String COLON = ":";
	public final static String PIPE = "|";

	private static Stream<Character> getRandomNumbers(int count) {
		IntStream numbers = random.ints(count, 48, 57);
		return numbers.mapToObj(data -> (char) data);
	}
	private static Stream<Character> getRandomSpecialChars(int count) {
		Random random = new SecureRandom();
		IntStream specialChars = random.ints(count, 33, 45);
		return specialChars.mapToObj(data -> (char) data);
	}
	public static Stream<Character> getRandomAlphabets(int count, boolean upperCase) {
		IntStream characters = null;
		if (upperCase) {
			characters = random.ints(count, 65, 90);
		} else {
			characters = random.ints(count, 97, 122);
		}
		return characters.mapToObj(data -> (char) data);
	}

	public static String generateSecureRandomPassword() {
		Stream<Character> pwdStream = Stream.concat(getRandomNumbers(2),
			Stream.concat(getRandomSpecialChars(2),
				Stream.concat(getRandomAlphabets(2, true), getRandomAlphabets(4, false))));
		List<Character> charList = pwdStream.collect(Collectors.toList());
		Collections.shuffle(charList);
		String password = charList.stream()
												.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
												.toString();
		return password;
	}
}
