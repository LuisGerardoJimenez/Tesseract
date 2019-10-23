package mx.tesseract.admin.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.util.Constantes;


@Repository("proyectoDao")
public class ProyectoDAO {
	
	private static final Logger TESSERACT_LOGGER = LogManager.getLogger();
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<Proyecto> findByCURPColaborador(String curp) {
		List<Proyecto> proyectos = new ArrayList<>();
		try {
			Query query = entityManager.createNamedQuery("Proyecto.findByColaboradorCurp", Proyecto.class);
			query.setParameter(Constantes.NUMERO_UNO, curp);
			proyectos = (List<Proyecto>) query.getResultList();
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "findByCURPColaborador", e);
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
			if (!lista.isEmpty()) {
				proyecto = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "findByClave", e);
		}
		return proyecto;
	}
	
	@SuppressWarnings("unchecked")
	public Proyecto findByClaveAndId(String clave, Integer id) {
		Proyecto proyecto = null;
		try {
			Query query = entityManager.createNamedQuery("Proyecto.findByClaveAndId", Proyecto.class);
			query.setParameter(Constantes.NUMERO_UNO, clave);
			query.setParameter(Constantes.NUMERO_DOS, id);
			List<Proyecto> lista = (List<Proyecto>) query.getResultList();
			if (!lista.isEmpty()) {
				proyecto = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "findByClaveAndId", e);
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
			if (!lista.isEmpty()) {
				proyecto = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "findByNombre", e);
		}
		return proyecto;
	}
	
	@SuppressWarnings("unchecked")
	public Proyecto findByNombreAndId(String nombre, Integer id) {
		Proyecto proyecto = null;
		try {
			Query query = entityManager.createNamedQuery("Proyecto.findByNombreAndId", Proyecto.class);
			query.setParameter(Constantes.NUMERO_UNO, nombre);
			query.setParameter(Constantes.NUMERO_DOS, id);
			List<Proyecto> lista = (List<Proyecto>) query.getResultList();
			if (!lista.isEmpty()) {
				proyecto = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "findByNombreAndId", e);
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
			if (!lista.isEmpty()) {
				proyecto = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "findElementosByIdProyecto", e);
		}
		return proyecto;
	}
}
