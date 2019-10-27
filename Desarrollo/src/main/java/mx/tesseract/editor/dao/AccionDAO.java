package mx.tesseract.editor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import mx.tesseract.editor.entidad.Accion;
import mx.tesseract.editor.entidad.Pantalla;
import mx.tesseract.util.Constantes;

@Repository("accionDAO")
public class AccionDAO {
	
	private static final Logger TESSERACT_LOGGER = LogManager.getLogger();
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	@SuppressWarnings("unchecked")
	public List<Accion> findAllByPantalla(Integer idPantalla) {
		List<Accion> lista = new ArrayList<>();
		try {
			Query query = entityManager.createNamedQuery("Accion.findByPantalla", Accion.class);
			query.setParameter(Constantes.NUMERO_UNO, idPantalla);
			lista = (List<Accion>) query.getResultList();
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "findAllByPantalla", e);
		}
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public Accion findByNombre(String nombre, Integer idPantalla) {
		Accion accion = null;
		try {
			Query query = entityManager.createNamedQuery("Accion.findByNameAndPantalla", Accion.class);
			query.setParameter(Constantes.NUMERO_UNO, nombre);
			query.setParameter(Constantes.NUMERO_DOS, idPantalla);
			List<Accion> lista = (List<Accion>) query.getResultList();
			if (!lista.isEmpty()) {
				accion = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "findByNombre", e);
		}
		return accion;
	}
	
	@SuppressWarnings("unchecked")
	public Accion findByNombreAndIdPantalla(String nombre, Integer idPantalla) {
		Accion accion = null;
		try {
			Query query = entityManager.createNamedQuery("Accion.findByNameAndPantalla", Accion.class);
			query.setParameter(Constantes.NUMERO_UNO, nombre);
			query.setParameter(Constantes.NUMERO_DOS, idPantalla);
			List<Accion> lista = (List<Accion>) query.getResultList();
			if (!lista.isEmpty()) {
				accion = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "findByNombreAndIdPantalla", e);
		}
		return accion;
	}
	
	@SuppressWarnings("unchecked")
	public Accion findByNombreAndIdAndIdPantalla(String nombre, Integer id, Integer idPantalla) {
		Accion accion = null;
		try {
			Query query = entityManager.createNamedQuery("Accion.findByNameAndIdAndPantalla", Accion.class);
			query.setParameter(Constantes.NUMERO_UNO, nombre);
			query.setParameter(Constantes.NUMERO_DOS, id);
			query.setParameter(Constantes.NUMERO_TRES, idPantalla);
			List<Accion> lista = (List<Accion>) query.getResultList();
			if (!lista.isEmpty()) {
				accion = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "findByNombreAndIdAndIdPantalla", e);
		}
		return accion;
	}
	
	@SuppressWarnings("unchecked")
	public List<Accion> consultarReferencias(Pantalla pantalla) {
		List<Accion> results = null;
		Query query = null;
		String queryCadena = null;
		queryCadena = "FROM Accion WHERE pantallaDestino.id = :idPantalla";

		try {
			query = entityManager.createQuery(queryCadena);
			query.setParameter("idPantalla", pantalla.getId());
			results = query.getResultList();

		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "consultarReferencias", e);
		}
		return results;
	}

}
