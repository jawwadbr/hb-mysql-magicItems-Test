package com.jawbr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "equipment_category")
public class EquipmentCategory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "indexname")
	private String indexname;
	
	@Column(name = "name")
	private String name;
	
	public EquipmentCategory() {}

	public EquipmentCategory(String indexname, String name) {
		this.indexname = indexname;
		this.name = name;
	}

	public EquipmentCategory(String name) {
		this.name = name;
	}

	public EquipmentCategory(int id, String indexname, String name) {
		this.id = id;
		this.indexname = indexname;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIndexname() {
		return indexname;
	}

	public void setIndexname(String indexname) {
		this.indexname = indexname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "EquipmentCategory [id=" + id + ", indexname=" + indexname + ", name=" + name + "]";
	}
	
	public String printEquip() {
		String print = "Name: " + name;
		return print;
	}

	public static String chooseEquip(EquipmentCategory e, int index) {
		String print = index+1 + " - Name: " + e.getName();
		
		return print;
	}
	
}
