package mx.tesseract.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import mx.tesseract.entidad.Contact;

@Repository("contactDao")
public class ContactDao {
	@PersistenceContext
	private EntityManager entityManagerFactory;

	public void delete(Contact contact) {
		entityManagerFactory.remove(contact);
		entityManagerFactory.flush();
	}
}
