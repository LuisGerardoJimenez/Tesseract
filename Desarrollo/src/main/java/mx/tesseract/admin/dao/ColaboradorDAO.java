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
	
	@SuppressWarnings("unchecked")
	public Colaborador findColaboradorByCorreo(String correo) {
		Colaborador colaborador = null;
		try {
			System.out.println("-------------------------> Voy a buscar prro >:v");
			Query query = entityManager.createNamedQuery("Colaborador.findColaboradorByCorreo", Colaborador.class);
			query.setParameter(Constantes.NUMERO_UNO, correo);
			System.out.println("-------------------------> Query creado");
			List<Colaborador> lista = (List<Colaborador>) query.getResultList();
			System.out.println("-------------------------> Resultados encontrados");
			for (Colaborador c : lista) {
				System.out.println("Colaborador: "+c.getNombre());
				System.out.println("CURP: "+c.getCurp());
				System.out.println("Correo: "+c.getCorreoElectronico());
			}
			if (lista.isEmpty()) {
				colaborador = null;
			} else {
				colaborador = lista.get(Constantes.NUMERO_CERO);
			}
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
		} catch (Exception e) {
			System.err.print(e.getMessage());
		}
		return colaborador;
	}

}
