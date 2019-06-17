package mx.tesseract.admin.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import mx.tesseract.admin.entidad.Colaborador;

@Repository("colaboradorDAO")
public class ColaboradorDAO {
	
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
	public List<Colaborador> findAllWithoutAdmin() {
		List<Colaborador> lista = new ArrayList<Colaborador>();
		try {
			Query query = entityManager.createNamedQuery("findAllWithoutAdmin",Colaborador.class);
			query.setParameter("value", Boolean.TRUE);
			lista = (List<Colaborador>) query.getResultList();
		} catch (Exception e) {
			System.err.print(e.getMessage());
		}
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public Colaborador findColaboradorByCorreo(String correo) {
		Colaborador colaborador = null;
		try {
			Query query = entityManager.createNamedQuery("findColaboradorByCorreo", Colaborador.class);
			query.setParameter("correoElectronico", correo);
			colaborador = (Colaborador) query.getSingleResult();
		} catch (Exception e) {
			System.err.print(e.getMessage());
		}
		return colaborador;
	}
	
	@SuppressWarnings("unchecked")
	public Colaborador findColaboradorByCURP(String curp) {
		Colaborador colaborador = null;
		try {
			Query query = entityManager.createNamedQuery("findColaboradorByCURP", Colaborador.class);
			query.setParameter("curp", curp);
			colaborador = (Colaborador) query.getSingleResult();
		} catch (Exception e) {
			System.err.print(e.getMessage());
		}
		return colaborador;
	}

}
