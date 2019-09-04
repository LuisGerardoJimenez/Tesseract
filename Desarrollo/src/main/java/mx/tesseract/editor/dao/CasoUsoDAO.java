package mx.tesseract.editor.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import mx.tesseract.enums.ReferenciaEnum.Clave;
import mx.tesseract.editor.entidad.CasoUso;
//import mx.tesseract.editor.entidad.Actualizacion;
import mx.tesseract.editor.entidad.Elemento;
import mx.tesseract.util.Constantes;

import org.springframework.stereotype.Repository;

@Repository("casoUsoDAO")
public class CasoUsoDAO {

	@PersistenceContext
	private EntityManager entityManager;

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