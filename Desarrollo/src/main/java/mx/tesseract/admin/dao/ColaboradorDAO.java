package mx.tesseract.admin.dao;

import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

import mx.tesseract.admin.entidad.Colaborador;

@Repository("colaboradorDAO")
public class ColaboradorDAO {
	
	private static final String FIND_COLABORADOR_BY_CORREO = "SELECT c FROM colaborador c WHERE c.correoElectronico = :correoElectronico";
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<Colaborador> findAll() {
		List<Colaborador> lista = new ArrayList<Colaborador>();
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Colaborador> criteriaQuery = criteriaBuilder.createQuery(Colaborador.class);
			Root<Colaborador> root = criteriaQuery.from(Colaborador.class);
			criteriaQuery.select(root);
			Query query = entityManager.createQuery(criteriaQuery);
			lista = query.getResultList();
		} catch (Exception e) {
			System.err.print(e.getMessage());
		}
		return lista;
	}
	
	public Colaborador findById(Integer id) {
		return entityManager.find(Colaborador.class, id);
	}
	
	public void delete(Colaborador colaborador) {
		entityManager.remove(colaborador);
		entityManager.flush();
	}
	
	public Colaborador update(Colaborador colaborador) {
		Colaborador updatedColaborador =  entityManager.merge(colaborador);
		return updatedColaborador;
	}
	
	@SuppressWarnings("unchecked")
	public Colaborador findColaboradorByCorreo(String correo) {
		Colaborador colaborador = null;
		try {
			Query query = entityManager.createNativeQuery(FIND_COLABORADOR_BY_CORREO, Colaborador.class);
			query.setParameter("correoElectronico", correo);
			colaborador = (Colaborador) query.getSingleResult();
		} catch (Exception e) {
			System.err.print(e.getMessage());
		}
		return colaborador;
	}

}
