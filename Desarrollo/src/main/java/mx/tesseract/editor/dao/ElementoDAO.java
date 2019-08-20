package mx.tesseract.editor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import mx.tesseract.enums.ReferenciaEnum.Clave;
//import mx.tesseract.editor.entidad.Actualizacion;
import mx.tesseract.editor.entidad.Elemento;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.ElementoInterface;

import org.springframework.stereotype.Repository;

@Repository("elementoDAO")
public class ElementoDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public <T extends ElementoInterface> List<T> findAllByIdProyectoAndClave(Integer idProyecto, Clave clave) {
		List<T> elementos = new ArrayList<T>();
		try {
			Query query = entityManager.createNamedQuery("Elemento.consultarElementosByProyectoAndClave", Elemento.class);
			query.setParameter(Constantes.NUMERO_UNO, idProyecto);
			query.setParameter(Constantes.NUMERO_DOS, clave.toString());
			elementos = (List<T>) query.getResultList();
			System.out.println("----------------------------> "+elementos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return elementos;
	}

	@SuppressWarnings("unchecked")
	public <T extends ElementoInterface> T findAllByIdProyectoAndNombreAndClave(Integer idProyecto, String nombre, Clave clave) {
		T elemento = null;
		try {
			Query query = entityManager.createNamedQuery("Elemento.consultarElementosByProyectoAndNombreAndClave", Elemento.class);
			query.setParameter(Constantes.NUMERO_UNO, idProyecto);
			query.setParameter(Constantes.NUMERO_DOS, nombre);
			query.setParameter(Constantes.NUMERO_TRES, clave.toString());
			List<T> lista = (List<T>) query.getResultList();
			if (!lista.isEmpty()) {
				elemento = (T) lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return elemento;
	}

	@SuppressWarnings("unchecked")
	public <T extends ElementoInterface> T findAllByIdProyectoAndIdAndNombreAndClave(Integer idProyecto, Integer id, String nombre, Clave clave) {
		T elemento = null;
		try {
			Query query = entityManager.createNamedQuery("Elemento.consultarElementosByProyectoAndIdAndNombreAndClave", Elemento.class);
			query.setParameter(Constantes.NUMERO_UNO, idProyecto);
			query.setParameter(Constantes.NUMERO_DOS, id);
			query.setParameter(Constantes.NUMERO_TRES, nombre);
			query.setParameter(Constantes.NUMERO_CUATRO, clave.toString());
			List<T> lista = (List<T>) query.getResultList();
			if (!lista.isEmpty()) {
				elemento = (T) lista.get(Constantes.NUMERO_CERO);
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
