package mx.tesseract.admin.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.util.Constantes;


@Repository("proyectoDao")
public class ProyectoDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<Proyecto> findByCURPColaborador(String curp) {
		//TODO: Agregar el Named query al mapeo
		List<Proyecto> proyectos = new ArrayList<Proyecto>();
		try {
			Query query = entityManager.createNamedQuery("Query", Proyecto.class);
			query.setParameter(Constantes.NUMERO_UNO, curp);
			proyectos = (List<Proyecto>) query.getResultList();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return proyectos;
	}

}
