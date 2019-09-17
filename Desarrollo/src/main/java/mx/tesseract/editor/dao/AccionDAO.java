package mx.tesseract.editor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import mx.tesseract.editor.entidad.Accion;
import mx.tesseract.editor.entidad.Elemento;
import mx.tesseract.enums.ReferenciaEnum.Clave;
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
	
	@SuppressWarnings("unchecked")
	public Accion findByNombreAndIdPantalla(String nombre, Integer idPantalla) {
		Accion accion = null;
		try {
			Query query = entityManager.createNamedQuery("Accion.findByNameAndPantalla", Accion.class);
			query.setParameter(Constantes.NUMERO_UNO, nombre);
			query.setParameter(Constantes.NUMERO_DOS, idPantalla);
			List<Accion> lista = (List<Accion>) query.getResultList();
			if (!lista.isEmpty()) {
				accion = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accion;
	}
	
	@SuppressWarnings("unchecked")
	public Accion findByNombreAndIdAndIdPantalla(String nombre, Integer id, Integer idPantalla) {
		Accion accion = null;
		try {
			System.out.println("Nombre: "+nombre);
			System.out.println("Id: "+id);
			System.out.println("IdPantalla: "+idPantalla);
			Query query = entityManager.createNamedQuery("Accion.findByNameAndIdAndPantalla", Accion.class);
			query.setParameter(Constantes.NUMERO_UNO, nombre);
			query.setParameter(Constantes.NUMERO_DOS, id);
			query.setParameter(Constantes.NUMERO_TRES, idPantalla);
			List<Accion> lista = (List<Accion>) query.getResultList();
			if (!lista.isEmpty()) {
				accion = lista.get(Constantes.NUMERO_CERO);
				System.out.println("AccionId : "+accion.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accion;
	}

}
