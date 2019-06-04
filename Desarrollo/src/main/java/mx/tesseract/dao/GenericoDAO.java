package mx.tesseract.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


public class GenericoDAO {

	@PersistenceContext
	private EntityManager em;

	public <T extends Class> List<T> buscarTodos(T clase) {
		List<T> resultados = new ArrayList<T>();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clase);
		Root<T> root = criteriaQuery.from(clase);
		criteriaQuery.select(root);
		resultados = em.createQuery(criteriaQuery).getResultList();
		return resultados;
	}
	
	public <T extends Class> T buscarById(Class clase, Object id) {
		Object entidad = null;
		try {
            entidad = em.find(clase, id);
        } catch (Exception e) {
        	System.err.println(e.getMessage());
        }
		return (T) entidad;
	}

}
