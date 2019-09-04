package mx.tesseract.editor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.editor.entidad.Parametro;
import mx.tesseract.util.Constantes;

@Repository("parametroDAO")
public class ParametroDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private GenericoDAO genericoDAO;

	public Parametro consultarParametro(int identificador) {
		Parametro parametro = null;
		try {
				parametro = genericoDAO.findById(Parametro.class, identificador);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return parametro;

	}
	
	@SuppressWarnings("unchecked")
	public Parametro consultarParametro(String nombre, int idProyecto) {
		Parametro parametro = null;
		List<Parametro> lista = new ArrayList<Parametro>();
		try {
			Query query = entityManager.createNamedQuery("Parametro.findByNombreAndProyectoId", Parametro.class);
			query.setParameter(Constantes.NUMERO_UNO, nombre);
			query.setParameter(Constantes.NUMERO_DOS, idProyecto);
			lista = (List<Parametro>) query.getResultList();
			if (!lista.isEmpty()) {
				parametro = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return parametro;
	}

	@SuppressWarnings("unchecked")
	public List<Parametro> consultarParametros(int idProyecto) {
		List<Parametro> lista = new ArrayList<Parametro>();
		try {
			Query query = entityManager.createNamedQuery("Parametro.findByIdProyecto", Parametro.class);
			query.setParameter(Constantes.NUMERO_UNO, idProyecto);
			lista = (List<Parametro>) query.getResultList();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return lista;

	}
}
