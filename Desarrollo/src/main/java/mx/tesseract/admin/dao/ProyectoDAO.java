package mx.tesseract.admin.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.util.Constantes;


@Repository("proyectoDao")
public class ProyectoDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<Proyecto> findByCURPColaborador(String curp) {
		List<Proyecto> proyectos = new ArrayList<Proyecto>();
		try {
			Query query = entityManager.createNamedQuery("Proyecto.findByColaboradorCurp", Proyecto.class);
			query.setParameter(Constantes.NUMERO_UNO, curp);
			proyectos = (List<Proyecto>) query.getResultList();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return proyectos;
	}
	
	@SuppressWarnings("unchecked")
	public Proyecto findByClave(String clave) {
		Proyecto proyecto = null;
		try {
			Query query = entityManager.createNamedQuery("Proyecto.findByClave", Proyecto.class);
			query.setParameter(Constantes.NUMERO_UNO, clave);
			List<Proyecto> lista = (List<Proyecto>) query.getResultList();
			if (lista.isEmpty()) {
				proyecto = null;
			} else {
				proyecto = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return proyecto;
	}
	
	@SuppressWarnings("unchecked")
	public Proyecto findByNombre(String nombre) {
		Proyecto proyecto = null;
		try {
			Query query = entityManager.createNamedQuery("Proyecto.findByNombre", Proyecto.class);
			query.setParameter(Constantes.NUMERO_UNO, nombre);
			List<Proyecto> lista = (List<Proyecto>) query.getResultList();
			if (lista.isEmpty()) {
				proyecto = null;
			} else {
				proyecto = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return proyecto;
	}

	@SuppressWarnings("unchecked")
	public Proyecto findElementosByIdProyecto(Proyecto entidad) {
		Proyecto proyecto = null;
		try {
			Query query = entityManager.createNamedQuery("Proyecto.findElementosByIdProyecto", Proyecto.class);
			query.setParameter(Constantes.NUMERO_UNO, entidad.getId());
			List<Proyecto> lista = (List<Proyecto>) query.getResultList();
			if (lista.isEmpty()) {
				proyecto = null;
			} else {
				proyecto = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			System.err.print(e.getMessage());
		}
		return proyecto;
	}
}
