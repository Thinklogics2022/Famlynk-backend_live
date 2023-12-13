package com.famlynk.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class IdGenerator {

	final static String FAM_ID = "FAMLYNK";
	final static String SYMBOL = "-";

	public static String randomIdGenerator() {
		String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String currentTime = new SimpleDateFormat("HHmmssSSS").format(new Date());
		String randomString = generateRandomString(6); // Change the number to specify the length of the random string
		return FAM_ID + SYMBOL + currentDate + SYMBOL + currentTime + SYMBOL + randomString;
	}

	public static String randomUniqueUserId(String userName) {
		String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String currentTime = new SimpleDateFormat("HHmmssSSS").format(new Date());
		String randomString = generateRandomString(6);
		return userName + SYMBOL + currentDate + SYMBOL + currentTime + SYMBOL + randomString;
	}

	public static String generateRandomString(int length) {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder sb = new StringBuilder();

		Random random = new Random();
		for (int i = 0; i < length; i++) {
			int index = random.nextInt(characters.length());
			char randomChar = characters.charAt(index);
			sb.append(randomChar);
		}
		return sb.toString();
	}

}