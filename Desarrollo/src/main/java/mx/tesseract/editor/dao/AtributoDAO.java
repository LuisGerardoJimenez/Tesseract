package mx.tesseract.editor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import mx.tesseract.editor.entidad.Atributo;
import mx.tesseract.util.Constantes;

import org.springframework.stereotype.Repository;

@Repository("atributoDAO")
public class AtributoDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<Atributo> findByIdEntidad(Integer idEntidad) {
		List<Atributo> lista = new ArrayList<Atributo>();
		try {
			Query query = entityManager.createNamedQuery("Atributo.findByEntidad", Atributo.class);
			query.setParameter(Constantes.NUMERO_UNO, idEntidad);
			lista = (List<Atributo>) query.getResultList();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public Atributo findAtributoByNombre(String nombre) {
		Atributo modulo = null;
		List<Atributo> lista = new ArrayList<Atributo>();
		try {
			Query query = entityManager.createNamedQuery("Atributo.findByName", Atributo.class);
			query.setParameter(Constantes.NUMERO_UNO, nombre);
			lista = (List<Atributo>) query.getResultList();
			if (!lista.isEmpty()) {
				modulo = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return modulo;
	}
	
	@SuppressWarnings("unchecked")
	public Atributo findAtributoByNombreAndId(String nombre, Integer idAtributo) {
		Atributo modulo = null;
		List<Atributo> lista = new ArrayList<Atributo>();
		try {
			Query query = entityManager.createNamedQuery("Atributo.findByNameAndId", Atributo.class);
			query.setParameter(Constantes.NUMERO_UNO, nombre);
			query.setParameter(Constantes.NUMERO_DOS, idAtributo);
			lista = (List<Atributo>) query.getResultList();
			if (!lista.isEmpty()) {
				modulo = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return modulo;
	}
}
