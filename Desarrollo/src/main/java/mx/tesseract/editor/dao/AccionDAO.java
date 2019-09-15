package mx.tesseract.editor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import mx.tesseract.editor.entidad.Accion;
import mx.tesseract.util.Constantes;

@Repository("accionDAO")
public class AccionDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	@SuppressWarnings("unchecked")
	public List<Accion> findAllByPantalla(Integer idPantalla) {
		List<Accion> lista = new ArrayList<Accion>();
		try {
			Query query = entityManager.createNamedQuery("Accion.findByPantalla", Accion.class);
			query.setParameter(Constantes.NUMERO_UNO, idPantalla);
			lista = (List<Accion>) query.getResultList();
		} catch (Exception e) {
			 System.err.println(e.getMessage());
		}
		return lista;
	}

}
