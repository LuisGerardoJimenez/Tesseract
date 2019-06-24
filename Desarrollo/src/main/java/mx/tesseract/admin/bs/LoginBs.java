package mx.tesseract.admin.bs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.tesseract.admin.dao.ColaboradorDAO;
import mx.tesseract.admin.entidad.Colaborador;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.util.SessionManager;

@Service("loginBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class LoginBs {

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
			System.err.println(e.getMessage());
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
			System.err.println(e.getMessage());
		}
		return colaborador;
	}

}
