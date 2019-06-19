package mx.tesseract.admin.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import mx.tesseract.admin.entidad.Colaborador;

@Repository("colaboradorDAO")
public class ColaboradorDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
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
