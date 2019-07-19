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
	
//	public Modulo consultarModulo(String clave, Proyecto proyecto) {
//		Modulo modulo = null;
//		try {
//			session.beginTransaction();
//			Query query = session.createSQLQuery("select * from Modulo where clave = :clave AND Proyectoid = :proyecto").addEntity(Modulo.class) ;
//			query.setParameter("clave", clave);
//			query.setParameter("proyecto", proyecto.getId());
//
//			@SuppressWarnings("unchecked")
//			List<Modulo> list  = query.list();
//			if(list.isEmpty()){
//				modulo = null;
//			} else {
//				modulo = list.get(0);
//			}
//			session.getTransaction().commit();
//		} catch (HibernateException he) {
//			he.printStackTrace();
//			session.getTransaction().rollback();
//		}
//
//		return modulo;
//
//	}
	
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
	
}
