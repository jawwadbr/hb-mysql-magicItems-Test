package com.jawbr;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.jawbr.entity.EquipmentCategory;
import com.jawbr.entity.MagicItems;
import com.jawbr.entity.SourceBook;

public class AssociateEquipmentToItemApp {

	public static void main(String[] args) {
		
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(MagicItems.class)
				.addAnnotatedClass(EquipmentCategory.class)
				.addAnnotatedClass(SourceBook.class)
				.buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		
		try {
			
			session.beginTransaction();
			
			// Pegando o item de Primary Key 1
			MagicItems item = session.get(MagicItems.class, 2);
			
			System.out.println("\nPegando Item Magico: " + item);
			
			// Query para pegar o source book especifico dentro do banco de dados
			Query<SourceBook> query = session.createQuery("select src from SourceBook src "
														+ "where src.name=:theSrcName", SourceBook.class);
			
			query.setParameter("theSrcName", "Dungeon Master’s Guide");
			
			List<SourceBook> src = query.getResultList();
			
			System.out.println("\nPegando Source Book: " + src);
			
			System.out.println("Associando " + item.getName() + " com o Source Book" + src.get(0).getName());
			
			// Associando o source book ao item
			item.setSourceBook(src.get(0));
			
			System.out.println("\nFinalizado, Item agora ficou: " + item + "\n");
			
			session.getTransaction().commit();
			
			System.out.println("\nPronto!");
			
		} finally {
			session.close();
			factory.close();
		}
	}

}
