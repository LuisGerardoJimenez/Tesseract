package mx.tesseract.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import mx.tesseract.entidad.Access;

@Repository("accessDao")
public class AccessDao {
	@PersistenceContext
	private EntityManager entityManagerFactory;
	
	public void delete(Access access) {
		entityManagerFactory.remove(access);
		entityManagerFactory.flush();
	}
}
