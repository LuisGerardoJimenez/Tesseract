package mx.tesseract.editor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import mx.tesseract.enums.ReferenciaEnum.Clave;
import mx.tesseract.editor.entidad.CasoUso;
//import mx.tesseract.editor.entidad.Actualizacion;
import mx.tesseract.editor.entidad.Elemento;
import mx.tesseract.editor.entidad.EstadoElemento;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.ElementoInterface;

import org.springframework.stereotype.Repository;

@Repository("elementoDAO")
public class ElementoDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public <T extends ElementoInterface> List<T> findAllByIdProyectoAndClave(Class<T> clase, Integer idProyecto, Clave clave) {
		List<T> elementos = new ArrayList<T>();
		try {
			Query query = entityManager.createNamedQuery("Elemento.consultarElementosByProyectoAndClave", Elemento.class);
			query.setParameter("idProyecto", idProyecto);
			query.setParameter("clave", clave.toString());
			elementos = (List<T>) query.getResultList();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return elementos;
	}

	@SuppressWarnings("unchecked")
	public <T extends ElementoInterface> T findAllByIdProyectoAndNombreAndClave(Class<T> clase, Integer idProyecto, String nombre, Clave clave) {
		T elemento = null;
		try {
			Query query = entityManager.createNamedQuery("Elemento.consultarElementosByProyectoAndNombreAndClave", Elemento.class);
			query.setParameter("idProyecto", idProyecto);
			query.setParameter("nombre", nombre);
			query.setParameter("clave", clave.toString());
			List<T> lista = (List<T>) query.getResultList();
			if (!lista.isEmpty()) {
				elemento = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return elemento;
	}

	@SuppressWarnings("unchecked")
	public <T extends ElementoInterface> T findAllByIdProyectoAndIdAndNombreAndClave(Class<T> clase, Integer idProyecto, Integer id, String nombre, Clave clave) {
		T elemento = null;
		try {
			Query query = entityManager.createNamedQuery("Elemento.consultarElementosByProyectoAndIdAndNombreAndClave", Elemento.class);
			query.setParameter("idProyecto", idProyecto);
			query.setParameter("id", id);
			query.setParameter("nombre", nombre);
			query.setParameter("clave", clave.toString());
			List<T> lista = (List<T>) query.getResultList();
			if (!lista.isEmpty()) {
				elemento = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return elemento;
	}
	
	@SuppressWarnings("unchecked")
	public String siguienteNumero(Integer idProyecto, Clave clave) {
		String numero = "";
		try {
			Query query = entityManager.createNamedQuery("Elemento.findNextNumber");
			query.setParameter(Constantes.NUMERO_UNO, idProyecto);
			query.setParameter(Constantes.NUMERO_DOS, clave.toString());
			List<Integer> lista = query.getResultList();
			numero = "" + lista.get(Constantes.NUMERO_CERO);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return numero;
	}

	@SuppressWarnings("unchecked")
	public CasoUso findElementoHasCasoUsoAsociado(String claveBusqueda,Clave clave) {
		CasoUso casoUso = null;
		try {
			Query query = entityManager.createNamedQuery("Elemento.findElementoHasCasoUsoAsociado", Elemento.class); 
			query.setParameter("redaccionActores", "%" + claveBusqueda + "%");
			query.setParameter("redaccionEntradas", "%" + claveBusqueda + "%");
			query.setParameter("redaccionSalidas", "%" + claveBusqueda + "%");
			query.setParameter("redaccionReglasNegocio", "%" + claveBusqueda + "%");
			query.setParameter("estado", Constantes.ESTADO_ELEMENTO_LIBERADO);
			List<CasoUso> lista = (List<CasoUso>) query.getResultList();
			if (!lista.isEmpty()) {
				casoUso = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return casoUso;
	}

}
