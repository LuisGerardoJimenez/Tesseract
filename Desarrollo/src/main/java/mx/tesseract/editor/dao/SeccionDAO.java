package mx.tesseract.editor.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import mx.tesseract.editor.entidad.Seccion;
import mx.tesseract.util.Constantes;

@Repository("seccionDAO")
public class SeccionDAO{
	

	private static final Logger TESSERACT_LOGGER = LogManager.getLogger();

	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public Seccion consultarSeccion(String nombre) {
		Seccion seccion = null;
		try {
			Query query = entityManager.createNamedQuery("Seccion.consultarParametroByNombre", Seccion.class);
			query.setParameter("nombre", nombre);
			List<Seccion> lista = (List<Seccion>) query.getResultList();
			if (!lista.isEmpty()) {
				seccion = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "consultarSeccion", e);
		}
		return seccion;
	}

	/*@SuppressWarnings("unchecked")
	public List<TipoAccion> consultarTiposAccion() {
		List<TipoAccion> results = null;

		try {
			session.beginTransaction();
			Query query = session
					.createQuery("from TipoAccion");

			results = query.list();
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			session.getTransaction().rollback();
		}
		
		if(results == null) {
			return null;
		} else 
			if (results.isEmpty()) {
				return null;
			} else
				return results;

	}*/
}
