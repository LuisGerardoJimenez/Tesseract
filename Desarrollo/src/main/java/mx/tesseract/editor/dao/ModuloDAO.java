package mx.tesseract.editor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import mx.tesseract.editor.entidad.Modulo;
import mx.tesseract.util.Constantes;

import org.springframework.stereotype.Repository;

@Repository("moduloDAO")
public class ModuloDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<Modulo> findByIdProyecto(Integer idProyecto) {
		List<Modulo> lista = new ArrayList<Modulo>();
		try {
			Query query = entityManager.createNamedQuery("Modulo.findByIdProyecto", Modulo.class);
			query.setParameter(Constantes.NUMERO_UNO, idProyecto);
			lista = (List<Modulo>) query.getResultList();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return lista;
	}

	@SuppressWarnings("unchecked")
	public Modulo findModuloByNameAndProyecto(String name, Integer idProyecto) {
		Modulo modulo = null;
		List<Modulo> lista = new ArrayList<Modulo>();
		try {
			Query query = entityManager.createNamedQuery("Modulo.findByNameAndProyecto", Modulo.class);
			query.setParameter(Constantes.NUMERO_UNO, name);
			query.setParameter(Constantes.NUMERO_DOS, idProyecto);
			lista = (List<Modulo>) query.getResultList();
			if (!lista.isEmpty()) {
				modulo = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return modulo;
	}

	@SuppressWarnings("unchecked")
	public Modulo findModuloByClaveAndProyecto(String clave, Integer idProyecto) {
		Modulo modulo = null;
		List<Modulo> lista = new ArrayList<Modulo>();
		try {
			Query query = entityManager.createNamedQuery("Modulo.findByClaveAndProyecto", Modulo.class);
			query.setParameter(Constantes.NUMERO_UNO, clave);
			query.setParameter(Constantes.NUMERO_DOS, idProyecto);
			lista = (List<Modulo>) query.getResultList();
			if (!lista.isEmpty()) {
				modulo = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return modulo;
	}

	@SuppressWarnings("unchecked")
	public Modulo findModuloByNombreAndIdAndProyecto(String nombre, Integer idModulo, Integer idProyecto) {
		Modulo modulo = null;
		List<Modulo> lista = new ArrayList<Modulo>();
		try {
			Query query = entityManager.createNamedQuery("Modulo.findByNameAndIdAndProyecto", Modulo.class);
			query.setParameter(Constantes.NUMERO_UNO, nombre);
			query.setParameter(Constantes.NUMERO_DOS, idModulo);
			query.setParameter(Constantes.NUMERO_TRES, idProyecto);
			lista = (List<Modulo>) query.getResultList();
			if (!lista.isEmpty()) {
				modulo = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return modulo;
	}

	@SuppressWarnings("unchecked")
	public Modulo hasReferenciaElementos(Integer idModulo) {
		Modulo modulo = null;
		List<Modulo> lista = new ArrayList<Modulo>();
		try {
			Query query = entityManager.createNamedQuery("Modulo.hasReferenciaElementos", Modulo.class);
			query.setParameter(Constantes.NUMERO_UNO, idModulo);
			lista = (List<Modulo>) query.getResultList();
			if (!lista.isEmpty()) {
				modulo = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return modulo;
	}
}
