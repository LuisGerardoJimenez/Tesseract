package mx.tesseract.editor.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import mx.tesseract.editor.entidad.Verbo;
import mx.tesseract.util.Constantes;

@Repository("verboDAO")
public class VerboDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public Verbo findByNombre(String nombre) {
		Verbo verbo = null;
		try {
			Query query = entityManager.createNamedQuery("Verbo.findByName", Verbo.class);
			query.setParameter("nombre", nombre);
			List<Verbo> lista = (List<Verbo>) query.getResultList();
			if (!lista.isEmpty()) {
				verbo = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return verbo;
	}

}
