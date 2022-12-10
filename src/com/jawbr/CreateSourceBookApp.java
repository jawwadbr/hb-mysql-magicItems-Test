package com.jawbr;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.jawbr.entity.EquipmentCategory;
import com.jawbr.entity.MagicItems;
import com.jawbr.entity.SourceBook;

public class CreateSourceBookApp {

	public static void main(String[] args) {
		
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(MagicItems.class)
				.addAnnotatedClass(EquipmentCategory.class)
				.addAnnotatedClass(SourceBook.class)
				.buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		
		try {
			
			SourceBook src = new SourceBook("Dungeon Master’s Guide");
			
			session.beginTransaction();
			System.out.println("Salvando novo SourceBook: " + src);
			session.save(src);
			
			session.getTransaction().commit();
			
			System.out.println("Salvo!");
			
		} finally {
			session.close();
			factory.close();
		}

	}

}
