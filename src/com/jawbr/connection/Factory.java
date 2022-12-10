package com.jawbr.connection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.jawbr.entity.EquipmentCategory;
import com.jawbr.entity.MagicItems;
import com.jawbr.entity.SourceBook;

public class Factory {

	private SessionFactory factory;
	
	private Session session;
	
	public Factory() {
		
		factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(MagicItems.class)
				.addAnnotatedClass(EquipmentCategory.class)
				.addAnnotatedClass(SourceBook.class)
				.buildSessionFactory();
		
	}

	public SessionFactory getFactory() {
		return factory;
	}

	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
	
	public Session createSession() {
		this.session = factory.getCurrentSession();
		return this.session;
	}
}
