package com.jawbr;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.jawbr.entity.EquipmentCategory;
import com.jawbr.entity.MagicItems;
import com.jawbr.entity.SourceBook;

public class CreateMagicItemApp {

	public static void main(String[] args) {
		
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(EquipmentCategory.class)
				.addAnnotatedClass(MagicItems.class)
				.addAnnotatedClass(SourceBook.class)
				.buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		
		try {
			
			// Criar o Item Magico
			MagicItems item = new MagicItems("armor-of-invulnerability", "Armor of Invulnerability", 
					"Armor (plate), legendary (requires attunement)\nYou have resistance to nonmagical damage while you wear this armor. "
					+ "Additionally, you can use an action to make yourself immune to nonmagical damage for 10 minutes or until you are no longer wearing the armor. "
					+ "Once this special action is used, it can't be used again until the next dawn.", "Legendary", "/api/magic-items/armor-of-invulnerability");
			
			session.beginTransaction();
			
			// Salvar o Item magico no banco de dados
			System.out.println("Salvando o Item: " + item);
			session.save(item);
			
			session.getTransaction().commit();
			
		} finally {
			
			session.close();
			factory.close();
		}
	}

}
