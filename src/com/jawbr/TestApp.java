package com.jawbr;

import java.util.Arrays;

import com.jawbr.util.Rarity;

public class TestApp {

	public static void main(String[] args) {
		
		String rar = Arrays.toString(Rarity.values());
		System.out.println(rar);
		
		for (Rarity r : Rarity.values()) {
			System.out.println(r.ordinal()+1 + " - " + r.name().replaceAll("_", "\s"));
		}
		
		System.out.println(Rarity.values().length);
		
		System.out.println(Rarity.values()[3].getDisplayName());
	}

}
