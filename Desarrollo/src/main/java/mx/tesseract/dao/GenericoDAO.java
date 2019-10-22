package mx.tesseract.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import mx.tesseract.util.GenericInterface;

@Repository("genericoDAO")
public class GenericoDAO {
	
	private static final Logger TESSERACT_LOGGER = LogManager.getLogger();

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
        	TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "findAll", e);
        }
		return entidad;
	}
	
	public <T extends GenericInterface> T save(T entidad) {
		em.persist(entidad);
		em.flush();
		return entidad;
	}
	
	public <T extends GenericInterface> List<T> saveList(List<T> lista) {
		for (Object o : lista) {
			em.persist(o);
			em.flush();
		}
		return lista;
	}
	
	public <T extends GenericInterface> T update(T entidad) {
		T entidadActualizada = em.merge(entidad);
		em.flush();
		return entidadActualizada;
	}
	
	public <T extends GenericInterface> List<T> updateList(List<T> lista) {
		for (Object o : lista) {
			em.merge(o);
			em.flush();
		}
		return lista;
	}
	
	public void delete(Object entidad) {
		em.remove(entidad);
		em.flush();
	}
	
	public <T extends GenericInterface> List<T> deleteList(List<T> lista) {
		for (Object o : lista) {
			em.remove(o);
			em.flush();
		}
		return lista;
	}
	
	public <T extends GenericInterface> T refresh(T entidad) {
		em.refresh(entidad);
		return entidad;
	}
	
	public <T extends GenericInterface> Boolean contains(T entidad) {
		return em.contains(entidad);
	}

}
