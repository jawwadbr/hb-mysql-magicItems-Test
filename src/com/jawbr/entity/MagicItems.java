package com.jawbr.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "magic_items")
public class MagicItems {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "indexname")
	private String indexname;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "descr")
	private String descr;
	
	@Column(name = "rarity")
	private String rarity;
	
	@Column(name = "url")
	private String url;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "equipment_category_fk")
	private EquipmentCategory equipmentCategory;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "source_name_fk")
	private SourceBook sourceBook;
	
	@Transient
	private String descr_top, descr_down;
	
	// Metodo para dividir a descrição do item
	public void splitDescr() {
		String[] s = descr.split("\n");
		setDescr_top(s[0]);
		setDescr_down(s[1]);
	}
	
	public MagicItems() {}

	public MagicItems(String name, String descr, String rarity) {
		this.name = name;
		this.descr = descr;
		this.rarity = rarity;
		splitDescr();
	}

	public MagicItems(String indexname, String name, String descr, String rarity, String url) {
		super();
		this.indexname = indexname;
		this.name = name;
		this.descr = descr;
		this.rarity = rarity;
		this.url = url;
	}

	public MagicItems(int id, String indexname, String name, String descr, String rarity, String url) {
		super();
		this.id = id;
		this.indexname = indexname;
		this.name = name;
		this.descr = descr;
		this.rarity = rarity;
		this.url = url;
		splitDescr();
	}

	public MagicItems(String indexname, String name, String descr, String rarity, String url,
			EquipmentCategory equipmentCategory, SourceBook sourceBook) {
		super();
		this.indexname = indexname;
		this.name = name;
		this.descr = descr;
		this.rarity = rarity;
		this.url = url;
		this.equipmentCategory = equipmentCategory;
		this.sourceBook = sourceBook;
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

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getRarity() {
		return rarity;
	}

	public void setRarity(String rarity) {
		this.rarity = rarity;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public EquipmentCategory getEquipmentCategory() {
		return equipmentCategory;
	}

	public void setEquipmentCategory(EquipmentCategory equipmentCategory) {
		this.equipmentCategory = equipmentCategory;
	}

	public SourceBook getSourceBook() {
		return sourceBook;
	}

	public void setSourceBook(SourceBook sourceBook) {
		this.sourceBook = sourceBook;
	}

	public String getDescr_top() {
		return descr_top;
	}

	public void setDescr_top(String descr_top) {
		this.descr_top = descr_top;
	}

	public String getDescr_down() {
		return descr_down;
	}

	public void setDescr_down(String descr_down) {
		this.descr_down = descr_down;
	}

	@Override
	public String toString() {
		splitDescr();
		return "\nMagicItems \n\n[id=" + id + ", \nindexname=" + indexname + ", \nname=" + name + ", \ndescr_top=" + descr_top
				+ ", \ndescr_down=" + descr_down + ", \nrarity=" + rarity + ", \nnurl=" + url + ", \nequipmentCategory="
				+ equipmentCategory + ", \nsourceBook=" + sourceBook + "]";
	}
	
	public String printItem() {
		splitDescr();
		String print = "Name: " + name
				+ "\n" + descr_top + "\nDescription: " + descr_down + "\nRarity: " + rarity + "\nEquipment Category: " + equipmentCategory.getName() + "\nSource Book: " + sourceBook.getName();
		return print;
	}

	public String chooseItem() {
		String print = id + " - Name: " + name;
		return print;
	}
	
	public static String chooseItem(MagicItems item, int index) {
		
		String print = index+1 + " - Name: " + item.getName();
		
		return print;
	}
}
