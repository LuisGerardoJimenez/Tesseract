package mx.tesseract.editor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import mx.tesseract.editor.entidad.Elemento;
import mx.tesseract.editor.entidad.Pantalla;
import mx.tesseract.enums.ReferenciaEnum.Clave;
import mx.tesseract.util.Constantes;

@Repository("pantallaDAO")
public class PantallaDAO {
	
	private static final Logger TESSERACT_LOGGER = LogManager.getLogger();

	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<Pantalla> findAllByIdModulo(Integer idProyecto, Clave clave, Integer idModulo) {
		List<Pantalla> pantallas = new ArrayList<Pantalla>();
		try {
			Query query = entityManager.createNamedQuery("Elemento.consultarPantallasByProyectoAndModulo", Elemento.class);
			query.setParameter("idProyecto", idProyecto);
			query.setParameter("clave", clave.toString());
			query.setParameter("idModulo", idModulo);
			pantallas = (List<Pantalla>) query.getResultList();
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "findAllByIdModulo", e);
		}
		return pantallas;
	}
	
	@SuppressWarnings("unchecked")
	public List<Pantalla> findByIdProyectoAndIdModulo(Integer idProyecto, Clave clave, Integer idModulo) {
		List<Pantalla> pantallas = new ArrayList<Pantalla>();
		try {
			Query query = entityManager.createNamedQuery("Elemento.consultarPantallasByProyectoAndModulo", Elemento.class);
			query.setParameter("idProyecto", idProyecto);
			query.setParameter("clave", clave.toString());
			query.setParameter("idModulo", idModulo);
			pantallas = (List<Pantalla>) query.getResultList();
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "findByIdProyectoAndIdModulo", e);
		}
		return pantallas;
	}
	
	@SuppressWarnings("unchecked")
	public Pantalla findByIdProyectoAndIdModuloAndNumero(Integer idProyecto, Clave clave, Integer idModulo, String numero) {
		Pantalla pantalla = null;
		try {
			Query query = entityManager.createNamedQuery("Elemento.consultarPantallasByProyectoAndModuloAndNumero", Elemento.class);
			query.setParameter("idProyecto", idProyecto);
			query.setParameter("clave", clave.toString());
			query.setParameter("idModulo", idModulo);
			query.setParameter("numero", numero);
			List<Pantalla> lista = (List<Pantalla>) query.getResultList();
			if (!lista.isEmpty()) {
				pantalla = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "findByIdProyectoAndIdModuloAndNumero", e);
		}
		return pantalla;
	}
	
	@SuppressWarnings("unchecked")
	public Pantalla findByIdProyectoAndIdModuloAndIdAndNumero(Integer idProyecto, Clave clave, Integer idModulo, Integer id, String numero) {
		Pantalla pantalla = null;
		try {
			Query query = entityManager.createNamedQuery("Elemento.consultarPantallasByProyectoAndModuloAndIdAndNumero", Elemento.class);
			query.setParameter("idProyecto", idProyecto);
			query.setParameter("clave", clave.toString());
			query.setParameter("idModulo", idModulo);
			query.setParameter("id", id);
			query.setParameter("numero", numero);
			List<Pantalla> lista = (List<Pantalla>) query.getResultList();
			if (!lista.isEmpty()) {
				pantalla = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "findByIdProyectoAndIdModuloAndIdAndNumero", e);
		}
		return pantalla;
	}
	
	@SuppressWarnings("unchecked")
	public Pantalla findByIdProyectoAndIdModuloAndNombre(Integer idProyecto, Clave clave, Integer idModulo, String nombre) {
		Pantalla pantalla = null;
		try {
			Query query = entityManager.createNamedQuery("Elemento.consultarPantallasByProyectoAndModuloAndNombre", Elemento.class);
			query.setParameter("idProyecto", idProyecto);
			query.setParameter("clave", clave.toString());
			query.setParameter("idModulo", idModulo);
			query.setParameter("nombre", nombre);
			List<Pantalla> lista = (List<Pantalla>) query.getResultList();
			if (!lista.isEmpty()) {
				pantalla = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "findByIdProyectoAndIdModuloAndNombre", e);
		}
		return pantalla;
	}
	
	@SuppressWarnings("unchecked")
	public Pantalla findByIdProyectoAndIdModuloAndIdAndNombre(Integer idProyecto, Clave clave, Integer idModulo, Integer id, String nombre) {
		Pantalla pantalla = null;
		try {
			Query query = entityManager.createNamedQuery("Elemento.consultarPantallasByProyectoAndModuloAndIdAndNombre", Elemento.class);
			query.setParameter("idProyecto", idProyecto);
			query.setParameter("clave", clave.toString());
			query.setParameter("idModulo", idModulo);
			query.setParameter("id", id);
			query.setParameter("nombre", nombre);
			List<Pantalla> lista = (List<Pantalla>) query.getResultList();
			if (!lista.isEmpty()) {
				pantalla = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "findByIdProyectoAndIdModuloAndIdAndNombre", e);
		}
		return pantalla;
	}
}
