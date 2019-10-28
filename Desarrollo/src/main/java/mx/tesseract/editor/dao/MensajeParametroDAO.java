package mx.tesseract.editor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import mx.tesseract.editor.entidad.MensajeParametro;
import mx.tesseract.util.Constantes;

@Repository("mensajeParametroDAO")
public class MensajeParametroDAO {
	
	private static final Logger TESSERACT_LOGGER = LogManager.getLogger();
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public MensajeParametro consultarMensajeParametro(int idMensaje, int idParametro) {
		MensajeParametro parametro = null;
		List<MensajeParametro> lista = new ArrayList<>();
		try {
			Query query = entityManager.createNamedQuery("MensajeParametro.findByIdParametroIdMensaje", MensajeParametro.class);
			query.setParameter(Constantes.NUMERO_UNO, idMensaje);
			query.setParameter(Constantes.NUMERO_DOS, idParametro);
			lista = (List<MensajeParametro>) query.getResultList();
			if (!lista.isEmpty()) {
				parametro = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "consultarMensajeParametro", e);
		}
		return parametro;
	}
	
	@SuppressWarnings("unchecked")
	public MensajeParametro consultarMensajeParametro(int idParametro) {
		MensajeParametro parametro = null;
		List<MensajeParametro> lista = new ArrayList<>();
		try {
			Query query = entityManager.createNamedQuery("MensajeParametro.findByIdParametro", MensajeParametro.class);
			query.setParameter(Constantes.NUMERO_UNO, idParametro);
			lista = (List<MensajeParametro>) query.getResultList();
			if (!lista.isEmpty()) {
				parametro = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "consultarMensajeParametro", e);
		}
		return parametro;
	}

}
