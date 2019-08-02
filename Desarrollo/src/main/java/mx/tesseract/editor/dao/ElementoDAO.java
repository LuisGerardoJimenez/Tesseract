package mx.tesseract.editor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.bs.ReferenciaEnum;
import mx.tesseract.bs.ReferenciaEnum.TipoReferencia;
import mx.tesseract.dao.GenericoDAO;
//import mx.tesseract.editor.entidad.Actualizacion;
import mx.tesseract.editor.entidad.Elemento;
import mx.tesseract.editor.entidad.Modulo;
import mx.tesseract.util.Constantes;
import org.springframework.stereotype.Repository;

@Repository("elementoDAO")
public class ElementoDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<Elemento> consultarElementos(TipoReferencia tipoReferencia, int idProyecto) {
		System.out.println("Estoy en el DAO");
		List<Elemento> elementos = new ArrayList<Elemento>();
		try {
			Query query =  null;
			switch (tipoReferencia) {
				case TERMINOGLS:
					query = entityManager.createNamedQuery("Elemento.consultarElementosGlosario", Elemento.class);
					break;
				default:
					break;
			}
			query.setParameter(Constantes.NUMERO_UNO, idProyecto);
			query.setParameter(Constantes.NUMERO_DOS, "GLS");
			
//			System.out.println("Result List: "+query.getResultList());
			elementos = (List<Elemento>) query.getResultList();
			System.out.println("Elementos: "+elementos.size());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return elementos;
	}
	
	@SuppressWarnings("unchecked")
	public Elemento findByNombre(TipoReferencia tipoReferencia, String nombre, Integer idProyecto) {
		Elemento elemento = null;
		try {
			Query query =  null;
			switch (tipoReferencia) {
				case TERMINOGLS:
					query = entityManager.createNamedQuery("Elemento.consultarElementosGlosarioByNombre", Elemento.class);
					break;
				default:
					break;
			}
			query.setParameter(Constantes.NUMERO_UNO, nombre);
			query.setParameter(Constantes.NUMERO_DOS, idProyecto);
			List<Elemento> lista = (List<Elemento>) query.getResultList();
			if (!lista.isEmpty()) {
				elemento = (Elemento) lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			 System.err.println(e.getMessage());
		}
		return elemento;
	}
	
	@SuppressWarnings("unchecked")
	public Elemento findByNombreAndId(TipoReferencia tipoReferencia, Integer id, String nombre, Integer idProyecto) {
		Elemento elemento = null;
		try {
			Query query =  null;
			switch (tipoReferencia) {
				case TERMINOGLS:
					query = entityManager.createNamedQuery("Elemento.consultarElementosGlosarioByNombreAndId", Elemento.class);
					break;
				default:
					break;
			}
			query.setParameter(Constantes.NUMERO_UNO, nombre);
			query.setParameter(Constantes.NUMERO_DOS, idProyecto);
			query.setParameter(Constantes.NUMERO_TRES, id);
			List<Elemento> lista = (List<Elemento>) query.getResultList();
			if (!lista.isEmpty()) {
				elemento = (Elemento) lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			 System.err.println(e.getMessage());
		}
		return elemento;
	}

//	public void registrarElemento(Elemento elemento) {
//		try {
//			session.beginTransaction();
//			session.save(elemento);
//			session.getTransaction().commit();
//		} catch (HibernateException he) {
//			he.printStackTrace();
//			session.getTransaction().rollback();
//			throw he;
//		}
//	}
//
//	public void modificarElemento(Elemento elemento, String actualizacion) {
//
//		try {
//			session.beginTransaction();
//			session.update(elemento);
//			session.save(actualizacion);
//			session.getTransaction().commit();
//		} catch (HibernateException he) {
//			he.printStackTrace();
//			session.getTransaction().rollback();
//			throw he;
//		}
//	}
//
//	public Elemento consultarElemento(int id) {
//		Elemento elemento = null;
//
//		try {
//			session.beginTransaction();
//			elemento = (Elemento) session.get(Elemento.class, id);
//			session.getTransaction().commit();
//		} catch (HibernateException he) {
//			he.printStackTrace();
//			session.getTransaction().rollback();
//			throw he;
//		}
//		return elemento;
//
//	}
//
	
	@SuppressWarnings("unchecked")
	public String siguienteNumero(TipoReferencia referencia, Integer idProyecto) {
		String numero = "";
		try {
			Query query =  null;
			switch (referencia) {
				case TERMINOGLS:
					query = entityManager.createNamedQuery("Elemento.findNextNumberTerminoGlosario", Elemento.class);
					break;
				default:
					break;
			}
			query.setParameter(Constantes.NUMERO_UNO, idProyecto);
			query.setParameter(Constantes.NUMERO_DOS, "GLS");
			List<Elemento> lista = (List<Elemento>) query.getResultList();
			if (lista == null || lista.isEmpty()) {
				numero = "" +Constantes.NUMERO_UNO;
			} else {
				numero = "" + lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			 System.err.println(e.getMessage());
		}
		return numero;
	}
	
//	@SuppressWarnings("unchecked")
//	public String siguienteNumero(TipoReferencia referencia,
//			int idProyecto) {
//		List<Integer> results = null;
//		String sentencia = "";
//		switch (referencia) {
//		
//		case ACTOR:
//			sentencia = "SELECT MAX(CAST(numero AS SIGNED)) FROM Elemento INNER JOIN Actor ON Elemento.id = Actor.Elementoid WHERE Elemento.Proyectoid = "
//					+ idProyecto + ";";
//			break;
//
//		case ENTIDAD:
//			sentencia = "SELECT MAX(CAST(numero AS SIGNED)) FROM Elemento INNER JOIN Entidad ON Elemento.id = Entidad.Elementoid WHERE Elemento.Proyectoid = "
//					+ idProyecto + ";";
//
//			break;
//
//		case MENSAJE:
//			sentencia = "SELECT MAX(CAST(numero AS SIGNED)) FROM Elemento INNER JOIN Mensaje ON Elemento.id = Mensaje.Elementoid WHERE Elemento.Proyectoid = "
//					+ idProyecto + ";";
//
//			break;
//
//		case REGLANEGOCIO:
//			sentencia = "SELECT MAX(CAST(numero AS SIGNED)) FROM Elemento INNER JOIN ReglaNegocio ON Elemento.id = ReglaNegocio.Elementoid WHERE Elemento.Proyectoid = "
//					+ idProyecto + ";";
//
//			break;
//
//		case TERMINOGLS:
//			sentencia = "SELECT MAX(CAST(numero AS SIGNED)) FROM Elemento INNER JOIN TerminoGlosario ON Elemento.id = TerminoGlosario.Elementoid WHERE Elemento.Proyectoid = "
//					+ idProyecto + ";";
//
//			break;
//		default:
//			break;
//
//		}
//
//		try {
//			session.beginTransaction();
//			SQLQuery sqlQuery = session.createSQLQuery(sentencia);
//			results = sqlQuery.list();
//			session.getTransaction().commit();
//		} catch (HibernateException he) {
//			he.printStackTrace();
//			session.getTransaction().rollback();
//			throw he;
//		}
//
//		if (results == null || results.isEmpty()) {
//			return 1 + "";
//		} else
//			return results.get(0) + "";
//	}
//
//	@SuppressWarnings("unchecked")
//	public Elemento consultarElemento(String nombre, Proyecto proyecto,
//			String tabla) {
//		List<Elemento> results = null;
//
//		try {
//			session.beginTransaction();
//			Query query = session.createQuery("from " + tabla
//					+ " where nombre = :nombre AND Proyectoid = :proyecto");
//			query.setParameter("nombre", nombre);
//			query.setParameter("proyecto", proyecto.getId());
//
//			results = query.list();
//			session.getTransaction().commit();
//		} catch (HibernateException he) {
//			he.printStackTrace();
//			session.getTransaction().rollback();
//			throw he;
//		}
//		if (results.isEmpty()) {
//			return null;
//		} else
//			return results.get(0);
//
//	}

//	@SuppressWarnings("unchecked")
//	public ArrayList<Elemento> consultarElementos(Proyecto proyecto) {
//		ArrayList<Elemento> results = null;
//
//		try {
//			session.beginTransaction();
//			Query query = session
//					.createQuery("from Elemento as elem where elem.proyecto.id = :proyecto ORDER BY clave, numero ");
//			query.setParameter("proyecto", proyecto.getId());
//			results = (ArrayList<Elemento>) query.list();
//			session.getTransaction().commit();
//
//		} catch (HibernateException he) {
//			he.printStackTrace();
//			session.getTransaction().rollback();
//			throw he;
//		}
//
//		return results;
//	}
}
