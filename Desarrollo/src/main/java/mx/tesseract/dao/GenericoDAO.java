package mx.tesseract.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import mx.tesseract.util.GenericInterface;

@Repository("genericoDAO")
public class GenericoDAO {

	@PersistenceContext
	private EntityManager em;

	public <T extends GenericInterface> List<T> findAll(Class<T> clase) {
		List<T> resultados = new ArrayList<T>();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clase);
		Root<T> root = criteriaQuery.from(clase);
		criteriaQuery.select(root);
		resultados = em.createQuery(criteriaQuery).getResultList();
		return resultados;
	}
	
	public <T extends GenericInterface> T findById(Class<T> clase, Serializable id) {
		T entidad = null;
		try {
            entidad = em.find(clase, id);
        } catch (Exception e) {
        	System.err.println(e.getMessage());
        }
		return entidad;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends GenericInterface> T save(Object entidad) {
		em.persist(entidad);
		em.flush();
		return (T) entidad;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends GenericInterface> T update(Object entidad) {
		T entidadActualizada = (T) em.merge(entidad);
		em.flush();
		return entidadActualizada;
	}
	
	public void eliminar(Object entidad) {
		System.out.println("Eliminado2");
		em.remove(entidad);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends GenericInterface> T refresh(Object entidad) {
		em.refresh(entidad);
		return (T) entidad;
	}

}
