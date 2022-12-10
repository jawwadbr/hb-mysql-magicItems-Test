package com.jawbr;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.jawbr.entity.EquipmentCategory;
import com.jawbr.entity.MagicItems;
import com.jawbr.entity.SourceBook;

public class GetItemApp {
	
	public static void main(String[] args) {
	
		// criar factory
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(MagicItems.class)
				.addAnnotatedClass(EquipmentCategory.class)
				.addAnnotatedClass(SourceBook.class)
				.buildSessionFactory();
		
		// criar sessao
		Session session = factory.getCurrentSession();
		
		try {
			
			// start transaction
			session.beginTransaction();
			
			// Pegar magicItem no db usando primary key
			int id = 2;
			MagicItems item = session.get(MagicItems.class, id);
			
			System.out.println("\n"+item+"\n");
			
			//commit transaction
			session.getTransaction().commit();
			
			System.out.println("Finalizado!");
			
		} finally {
			
			session.close();
			factory.close();
		}
		
	}
	
}
