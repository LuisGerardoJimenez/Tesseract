package mx.tesseract.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import mx.tesseract.entidad.Account;

@Repository("accountDao")
public class AccountDao {
	@PersistenceContext
	private EntityManager entityManagerFactory;

	public void delete(Account account) {
		entityManagerFactory.remove(account);
		entityManagerFactory.flush();
	}
}
