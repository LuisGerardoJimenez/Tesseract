package mx.tesseract.editor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.editor.entidad.MensajeParametro;
import mx.tesseract.editor.entidad.Parametro;
import mx.tesseract.util.Constantes;

@Repository("mensajeParametroDAO")
public class MensajeParametroDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public MensajeParametro consultarMensajeParametro(int idMensaje, int idParametro) {
		MensajeParametro parametro = null;
		List<MensajeParametro> lista = new ArrayList<MensajeParametro>();
		try {
			Query query = entityManager.createNamedQuery("MensajeParametro.findByIdParametroIdMensaje", MensajeParametro.class);
			query.setParameter(Constantes.NUMERO_UNO, idMensaje);
			query.setParameter(Constantes.NUMERO_DOS, idParametro);
			lista = (List<MensajeParametro>) query.getResultList();
			if (!lista.isEmpty()) {
				parametro = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return parametro;
	}
	
	@SuppressWarnings("unchecked")
	public MensajeParametro consultarMensajeParametro(int idParametro) {
		MensajeParametro parametro = null;
		List<MensajeParametro> lista = new ArrayList<MensajeParametro>();
		try {
			Query query = entityManager.createNamedQuery("MensajeParametro.findByIdParametro", MensajeParametro.class);
			query.setParameter(Constantes.NUMERO_UNO, idParametro);
			lista = (List<MensajeParametro>) query.getResultList();
			if (!lista.isEmpty()) {
				parametro = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return parametro;
	}

}
