package com.jawbr.util;

public enum Rarity {
	Common("Common"), Uncommon("Uncommon"), Rare("Rare"), Very_Rare("Very Rare"), Legendary("Legendary"), Varies("Varies"), Artifact("Artifact");

	private String displayName;
	
	Rarity(String displayName) {
		this.displayName = displayName;
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public static String valueOfOrDefault(String displayName) {
		//replace space with underscore so it matches enum name
		String value=displayName.toUpperCase().replaceAll("\\s", "_");
		for(Rarity type : Rarity.class.getEnumConstants()) {
			if(type.name().equalsIgnoreCase(value)) {
				return type.toString();
			}
		}
		return displayName;
	}
	
	@Override
	public String toString() {
		return displayName;
	}
}
