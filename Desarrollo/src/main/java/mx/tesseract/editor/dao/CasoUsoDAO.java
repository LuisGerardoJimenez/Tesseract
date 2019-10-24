package mx.tesseract.editor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import mx.tesseract.enums.ReferenciaEnum.Clave;
import mx.tesseract.editor.entidad.CasoUso;
import mx.tesseract.editor.entidad.CasoUsoActor;
//import mx.tesseract.editor.entidad.Actualizacion;
import mx.tesseract.editor.entidad.Elemento;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.GenericInterface;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository("casoUsoDAO")
public class CasoUsoDAO {
	
	private static final Logger TESSERACT_LOGGER = LogManager.getLogger();

	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<CasoUso> findAllByProyectoAndModulo(Integer idProyecto, Integer idModulo, Clave clave) {
		List<CasoUso> casosUso = new ArrayList<CasoUso>();
		try {
			Query query = entityManager.createNamedQuery("Elemento.consultarElementosByProyectoAndClave", Elemento.class);
			query.setParameter("idProyecto", idProyecto);
			query.setParameter("clave", Clave.CU.toString());
			casosUso = (List<CasoUso>) query.getResultList();
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "findAllByProyectoAndModulo", e);
		}
		return casosUso;
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
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "findElementoHasCasoUsoAsociado", e);
		}
		return casoUso;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends GenericInterface> T findElementoAsociado(Integer idCasoUso,Integer idElemento, Class<T> clase) {
		T entidad = null;
		try {
			Query query;
			if(clase.equals(CasoUsoActor.class))
				query = entityManager.createNamedQuery("Elemento.findElementoAsociadoCasoUsoActor");
			else
				query = entityManager.createNamedQuery("Elemento.findElementoAsociadoCasoUsoReglaNegocio");
			query.setParameter("idCasoUso", idCasoUso);
			query.setParameter("idElemento", idElemento);
			List<T> lista = (List<T>) query.getResultList();
			if (!lista.isEmpty()) {
				entidad = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "findElementoAsociado", e);
		}
		return entidad;
	}

}
