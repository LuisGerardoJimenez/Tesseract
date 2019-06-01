package mx.tesseract.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import mx.tesseract.entidad.User;

@Repository("usersDao")
public class UsersDao {

	@PersistenceContext
	private EntityManager entityManagerFactory;

	@SuppressWarnings("unchecked")
	public List<User> findAllUsers() {
		CriteriaBuilder criteriaBuilder = entityManagerFactory.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);
		criteriaQuery.select(root);
		Query query = entityManagerFactory.createQuery(criteriaQuery);
		List<User> users = query.getResultList();
		return users;
	}

	public User findById(Integer id) {
		return entityManagerFactory.find(User.class, id);
	}

	public void delete(User user) {
		entityManagerFactory.remove(user);
		entityManagerFactory.flush();
	}
	
	public User update(User user) {
		User updatedUser =  entityManagerFactory.merge(user);
		return updatedUser;
	}
}
