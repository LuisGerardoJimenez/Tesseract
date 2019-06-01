package mx.tesseract.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import mx.tesseract.entidad.Address;

@Repository("addressDao")
public class AddressDao {
	@PersistenceContext
	private EntityManager entityManagerFactory;

	public void delete(Address address) {
		entityManagerFactory.remove(address);
		entityManagerFactory.flush();
	}
}
