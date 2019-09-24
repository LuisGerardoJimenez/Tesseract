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
import mx.tesseract.editor.entidad.Pantalla;
import mx.tesseract.util.Constantes;

import org.springframework.stereotype.Repository;

@Repository("casoUsoDAO")
public class CasoUsoDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<CasoUso> findAllByProyectoAndModulo(Integer idProyecto, Integer idModulo, Clave clave) {
		List<CasoUso> casosUso = new ArrayList<CasoUso>();
		try {
			Query query = entityManager.createNamedQuery("CasoUso.consultarCasosUsoByProyectoAndModulo", Elemento.class);
			query.setParameter("idProyecto", idProyecto);
			query.setParameter("clave", clave.toString());
			query.setParameter("idModulo", idModulo);
			casosUso = (List<CasoUso>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
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
			System.err.println(e.getMessage());
		}
		return casoUso;
	}

}
