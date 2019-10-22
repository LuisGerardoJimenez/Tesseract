package mx.tesseract.admin.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import mx.tesseract.admin.entidad.EstadoProyecto;
import mx.tesseract.util.Constantes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository("estadoProyectoDAO")
public class EstadoProyectoDAO {
	
	private static final Logger TESSERACT_LOGGER = LogManager.getLogger();

	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<EstadoProyecto> findAllWithoutFinished() {
		List<EstadoProyecto> estadosProyecto = new ArrayList<>();
		try {
			Query query = entityManager.createNamedQuery("EstadoProyecto.findAllWithoutFinished", EstadoProyecto.class);
			query.setParameter(Constantes.NUMERO_UNO, Constantes.NUMERO_TRES);
			estadosProyecto = (List<EstadoProyecto>) query.getResultList();
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "findAllWithoutFinished", e);
		}
		return estadosProyecto;
	}
	
}
