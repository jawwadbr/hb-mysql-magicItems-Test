package com.jawbr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "source_book")
public class SourceBook {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "source_name")
	private String name;
	
	public SourceBook() {}

	public SourceBook(String name) {
		this.name = name;
	}

	public SourceBook(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "SourceBook [id=" + id + ", name=" + name + "]";
	}
	
	public String printBook() {
		String print = "Name: " + name;
		return print;
	}
	
	public static String chooseBook(SourceBook book, int index) {
		
		String print = index+1 + " - Name: " + book.getName();
		
		return print;
	}
	
}
