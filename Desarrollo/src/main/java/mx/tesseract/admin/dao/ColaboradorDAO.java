package mx.tesseract.admin.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import mx.tesseract.admin.entidad.Colaborador;
import mx.tesseract.util.Constantes;

@Repository("colaboradorDAO")
public class ColaboradorDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<Colaborador> findAllWithoutAdmin() {
		List<Colaborador> lista = new ArrayList<Colaborador>();
		try {
			Query query = entityManager.createNamedQuery("Colaborador.findAllWithoutAdmin",Colaborador.class);
			query.setParameter(Constantes.NUMERO_UNO, Boolean.TRUE);
			lista = (List<Colaborador>) query.getResultList();
		} catch (Exception e) {
			System.err.print(e.getMessage());
		}
		return lista;
	}
	
	public Colaborador findColaboradorByCorreo(String correo) {
		Colaborador colaborador = null;
		try {
			Query query = entityManager.createNamedQuery("Colaborador.findColaboradorByCorreo", Colaborador.class);
			query.setParameter(Constantes.NUMERO_UNO, correo);
			for (Object o : query.getResultList()) {
				System.out.println("Colaborador: "+((Colaborador)o).getNombre());
			}
			System.out.println("Query: "+query.toString());
			colaborador = (Colaborador) query.getSingleResult();
		} catch (Exception e) {
			System.err.print(e.getMessage());
		}
		return colaborador;
	}
	
	public Colaborador findColaboradorByCURP(String curp) {
		Colaborador colaborador = null;
		try {
			Query query = entityManager.createNamedQuery("Colaborador.findColaboradorByCURP", Colaborador.class);
			query.setParameter(Constantes.NUMERO_UNO, curp);
			colaborador = (Colaborador) query.getSingleResult();
			entityManager.detach(colaborador);
		} catch (Exception e) {
			System.err.print(e.getMessage());
		}
		return colaborador;
	}

}
