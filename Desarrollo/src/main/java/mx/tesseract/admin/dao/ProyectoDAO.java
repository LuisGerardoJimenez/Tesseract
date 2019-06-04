package mx.tesseract.admin.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.entidad.User;

@Repository("proyectoDao")
public class ProyectoDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	//@Autowired
	//private GenericoDAO genericoDAO;
	
	@SuppressWarnings("unchecked")
	public List<Proyecto> findAll() {
		List<Proyecto> lista = new ArrayList<Proyecto>();
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Proyecto> criteriaQuery = criteriaBuilder.createQuery(Proyecto.class);
		Root<Proyecto> root = criteriaQuery.from(Proyecto.class);
		criteriaQuery.select(root);
		Query query = entityManager.createQuery(criteriaQuery);
		lista = query.getResultList();
		return lista;
	}
	
	public Proyecto findById(Integer id) {
		return entityManager.find(Proyecto.class, id);
	}
	
	public void delete(Proyecto proyecto) {
		entityManager.remove(proyecto);
		entityManager.flush();
	}
	
	public Proyecto update(Proyecto proyecto) {
		Proyecto updatedProyecto =  entityManager.merge(proyecto);
		return updatedProyecto;
	}

}
