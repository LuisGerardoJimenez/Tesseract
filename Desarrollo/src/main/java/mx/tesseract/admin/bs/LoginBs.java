package mx.tesseract.admin.bs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.tesseract.admin.dao.ColaboradorDAO;
import mx.tesseract.admin.entidad.Colaborador;
import mx.tesseract.admin.entidad.ColaboradorProyecto;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.admin.entidad.Rol;
import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.util.SessionManager;

@Service("loginBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class LoginBs {
	
	private static final Logger TESSERACT_LOGGER = LogManager.getLogger();

	@Autowired
	private ColaboradorDAO colaboradorDAO;
	
	@Autowired
	private GenericoDAO genericoDAO;
	
	public Proyecto consultarProyectoActivo() {
		Proyecto proyecto = null;
		try {
			Object idProyecto = SessionManager.get("idProyecto");
			if (idProyecto != null) {
				proyecto = genericoDAO.findById(Proyecto.class, (Integer) idProyecto);
			}
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "consultarProyectoActivo", e);
		}
		return proyecto;
	}
	
	public Colaborador consultarColaboradorActivo() {
		Colaborador colaborador = null;
		try {
			Object colaboradorCURP = SessionManager.get("colaboradorCURP");
			if (colaboradorCURP != null) {
				colaborador = colaboradorDAO.findColaboradorByCURP(colaboradorCURP.toString());
			}
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "consultarColaboradorActivo", e);
		}
		return colaborador;
	}
	
	public Rol rolColaboradorActivo() {
		Rol rol = null;
		try {
			Colaborador colaborador = consultarColaboradorActivo();
			for (ColaboradorProyecto colaboradorProyecto : colaborador.getColaborador_proyectos()) {
				if (colaboradorProyecto.getColaborador().getCurp().equals(colaborador.getCurp())) {
					rol = colaboradorProyecto.getRol();
				}
			}
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "rolColaboradorActivo", e);
		}
		return rol;
	}

}
