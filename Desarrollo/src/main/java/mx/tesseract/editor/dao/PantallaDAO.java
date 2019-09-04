package mx.tesseract.editor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import mx.tesseract.editor.entidad.Elemento;
import mx.tesseract.editor.entidad.Pantalla;
import mx.tesseract.enums.ReferenciaEnum.Clave;

@Repository("pantallaDAO")
public class PantallaDAO {

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
			e.printStackTrace();
		}
		return pantallas;
	}
}
