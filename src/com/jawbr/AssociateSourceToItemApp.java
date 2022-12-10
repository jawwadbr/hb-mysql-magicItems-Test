package com.jawbr;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.jawbr.entity.EquipmentCategory;
import com.jawbr.entity.MagicItems;
import com.jawbr.entity.SourceBook;

public class AssociateSourceToItemApp {

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
			
			// Pegando o item de Primary Key 2
			MagicItems item = session.get(MagicItems.class, 2);
			
			System.out.println("\nPegando Item Magico: " + item);
			
			// Query para pegar o EquipmentCategor especifico dentro do banco de dados
			Query<EquipmentCategory> query = session.createQuery("select e from EquipmentCategory e "
														+ "where e.name=:theEquipName", EquipmentCategory.class);
			
			query.setParameter("theEquipName", "armor");
			
			List<EquipmentCategory> equip = query.getResultList();
			
			System.out.println("\nPegando EquipmentCategory: " + equip);
			
			System.out.println("Associando " + item.getName() + " com o EquipmentCategory" + equip.get(0).getName());
			
			// Associando o source book ao item
			item.setEquipmentCategory(equip.get(0));
			
			System.out.println("\nFinalizado, Item agora ficou: " + item + "\n");
			
			session.getTransaction().commit();
			
			System.out.println("\nPronto!");
			
		} finally {
			session.close();
			factory.close();
		}
	}

}
