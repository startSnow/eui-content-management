package com.leeco.eui.api.utils;

public enum RegionEnum {
	US(1),
	ASIA(2);
	
	private long id;
	
	private RegionEnum(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
	RegionEnum getRegionById(long id) {
		for (RegionEnum reg : RegionEnum.values()) {
			if (reg.getId() == id) {
				return reg;
			}
		}
		return null;
	}
}
