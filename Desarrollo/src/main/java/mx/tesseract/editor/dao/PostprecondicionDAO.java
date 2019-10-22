package mx.tesseract.editor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import mx.tesseract.editor.entidad.PostPrecondicion;
import mx.tesseract.util.Constantes;

@Repository("postprecondicionDAO")
public class PostprecondicionDAO {
	
	private static final Logger TESSERACT_LOGGER = LogManager.getLogger();
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<PostPrecondicion> findAllByCasoUso(Integer idCasoUso) {
		List<PostPrecondicion> lista = new ArrayList<>();
		try {
			Query query = entityManager.createNamedQuery("PostPrecondicion.findByCasoUso", PostPrecondicion.class);
			query.setParameter(Constantes.NUMERO_UNO, idCasoUso);
			lista = (List<PostPrecondicion>) query.getResultList();
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "findAllByCasoUso", e);
		}
		return lista;
	}
	
}
