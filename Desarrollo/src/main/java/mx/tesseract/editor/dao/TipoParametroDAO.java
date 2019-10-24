package mx.tesseract.editor.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import mx.tesseract.editor.entidad.TipoParametro;
import mx.tesseract.util.Constantes;

@Repository("tipoParametroDAO")
public class TipoParametroDAO {
	
	private static final Logger TESSERACT_LOGGER = LogManager.getLogger();

	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public TipoParametro consultarTipoParametroById(Integer id) {
		TipoParametro tipoParametro = null;
		try {
			Query query = entityManager.createNamedQuery("TipoParametro.consultarTipoParametroById", TipoParametro.class);
			query.setParameter("id", id);
			List<TipoParametro> lista = (List<TipoParametro>) query.getResultList();
			if (!lista.isEmpty()) {
				tipoParametro = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "consultarTipoParametroById", e);
		}
		return tipoParametro;
	}
	
	@SuppressWarnings("unchecked")
	public TipoParametro consultarTipoParametroByNombre(String nombre) {
		TipoParametro tipoParametro = null;
		try {
			Query query = entityManager.createNamedQuery("TipoParametro.consultarTipoParametroByNombre", TipoParametro.class);
			query.setParameter("nombre", nombre);
			List<TipoParametro> lista = (List<TipoParametro>) query.getResultList();
			if (!lista.isEmpty()) {
				tipoParametro = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "consultarTipoParametroByNombre", e);
		}
		return tipoParametro;
	}
}
