package mx.tesseract.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import mx.tesseract.entidad.PersonContact;

@Repository("personContactDao")
public class PersonContactDao {
	@PersistenceContext
	private EntityManager entityManagerFactory;

	public void delete(PersonContact personContact) {
		entityManagerFactory.remove(personContact);
		entityManagerFactory.flush();
	}
}
