package mx.tesseract.editor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import mx.tesseract.editor.entidad.Atributo;
import mx.tesseract.util.Constantes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository("atributoDAO")
public class AtributoDAO {
	
	private static final Logger TESSERACT_LOGGER = LogManager.getLogger();
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<Atributo> findByIdEntidad(Integer idEntidad) {
		List<Atributo> lista = new ArrayList<>();
		try {
			Query query = entityManager.createNamedQuery("Atributo.findByEntidad", Atributo.class);
			query.setParameter(Constantes.NUMERO_UNO, idEntidad);
			lista = (List<Atributo>) query.getResultList();
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "findByIdEntidad", e);
		}
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public Atributo findAtributoByNombreAndEntidad(String nombre, Integer idEntidad) {
		Atributo atributo = null;
		List<Atributo> lista = new ArrayList<>();
		try {
			Query query = entityManager.createNamedQuery("Atributo.findByNameAndEntidad", Atributo.class);
			query.setParameter(Constantes.NUMERO_UNO, nombre);
			query.setParameter(Constantes.NUMERO_DOS, idEntidad);
			lista = (List<Atributo>) query.getResultList();
			if (!lista.isEmpty()) {
				atributo = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "findAtributoByNombreAndEntidad", e);
		}
		return atributo;
	}
	
	@SuppressWarnings("unchecked")
	public Atributo findAtributoByNombreAndIdAndEntidad(String nombre, Integer idAtributo, Integer idEntidad) {
		Atributo atributo = null;
		List<Atributo> lista = new ArrayList<>();
		try {
			Query query = entityManager.createNamedQuery("Atributo.findByNameAndIdAndEntidad", Atributo.class);
			query.setParameter(Constantes.NUMERO_UNO, nombre);
			query.setParameter(Constantes.NUMERO_DOS, idAtributo);
			query.setParameter(Constantes.NUMERO_TRES, idEntidad);
			lista = (List<Atributo>) query.getResultList();
			if (!lista.isEmpty()) {
				atributo = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "findAtributoByNombreAndIdAndEntidad", e);
		}
		return atributo;
	}
}
