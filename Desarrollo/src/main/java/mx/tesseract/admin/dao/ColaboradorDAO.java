package mx.tesseract.admin.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import mx.tesseract.admin.entidad.Colaborador;
import mx.tesseract.util.Constantes;

@Repository("colaboradorDAO")
public class ColaboradorDAO {
	
	private static final Logger TESSERACT_LOGGER = LogManager.getLogger();

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<Colaborador> findAllWithoutAdmin() {
		List<Colaborador> lista = new ArrayList<>();
		try {
			Query query = entityManager.createNamedQuery("Colaborador.findAllWithoutAdmin", Colaborador.class);
			query.setParameter(Constantes.NUMERO_UNO, Boolean.TRUE);
			lista = (List<Colaborador>) query.getResultList();
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "findAllWithoutAdmin", e);
		}
		return lista;
	}

	@SuppressWarnings("unchecked")
	public Colaborador findColaboradorByCorreo(String correo) {
		Colaborador colaborador = null;
		try {
			Query query = entityManager.createNamedQuery("Colaborador.findColaboradorByCorreo", Colaborador.class);
			query.setParameter(Constantes.NUMERO_UNO, correo);
			List<Colaborador> lista = (List<Colaborador>) query.getResultList();
			if (!lista.isEmpty()) {
				colaborador = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "findColaboradorByCorreo", e);
		}
		return colaborador;
	}

	@SuppressWarnings("unchecked")
	public Colaborador findColaboradorByCURP(String curp) {
		Colaborador colaborador = null;
		try {
			Query query = entityManager.createNamedQuery("Colaborador.findColaboradorByCURP", Colaborador.class);
			query.setParameter(Constantes.NUMERO_UNO, curp);
			List<Colaborador> lista = (List<Colaborador>) query.getResultList();
			if (!lista.isEmpty()) {
				colaborador = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "findColaboradorByCURP", e);
		}
		return colaborador;
	}
	
	@SuppressWarnings("unchecked")
	public Colaborador findColaboradorByCorreoAndCurp(String curp, String correo) {
		Colaborador colaborador = null;
		try {
			Query query = entityManager.createNamedQuery("Colaborador.findColaboradorByCorreoAndCurp", Colaborador.class);
			query.setParameter(Constantes.NUMERO_UNO, correo);
			query.setParameter(Constantes.NUMERO_DOS, curp);
			List<Colaborador> lista = (List<Colaborador>) query.getResultList();
			if (!lista.isEmpty()) {
				colaborador = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "findColaboradorByCorreoAndCurp", e);
		}
		return colaborador;
	}

	@SuppressWarnings("unchecked")
	public Colaborador hasProyectos(Colaborador entidad) {
		Colaborador colaborador = null;
		try {
			Query query = entityManager.createNamedQuery("Colaborador.hasProyectos", Colaborador.class);
			query.setParameter(Constantes.NUMERO_UNO, entidad.getCurp());
			List<Colaborador> lista = (List<Colaborador>) query.getResultList();
			if (!lista.isEmpty()) {
				colaborador = lista.get(Constantes.NUMERO_CERO);
			}
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "hasProyectos", e);
		}
		return colaborador;
	}

}
