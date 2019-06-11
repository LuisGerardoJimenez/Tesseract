package mx.tesseract.admin.bs;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.tesseract.admin.dao.ColaboradorDAO;
import mx.tesseract.admin.dao.ProyectoDAO;
import mx.tesseract.admin.entidad.Colaborador;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.util.SessionManager;

@Service("loginBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class LoginBs {

	@Autowired
	private ColaboradorDAO colaboradorDAO;
	
	@Autowired
	private ProyectoDAO proyectoDAO;
	
	public Proyecto consultarProyectoActivo() throws Exception{
		Proyecto proyecto = null;
		Object idProyecto = SessionManager.get("idProyecto");
		if (idProyecto != null) {
			proyecto = proyectoDAO.findById((Integer) idProyecto);
		}
		return proyecto;
	}
	
	public Colaborador consultarColaboradorActivo() throws Exception{
		Colaborador colaborador = null;
		Object colaboradorCURP = SessionManager.get("colaboradorCURP");
		if (colaboradorCURP != null) {
			colaborador = colaboradorDAO.findColaboradorByCURP(colaboradorCURP.toString());
		}
		return colaborador;
	}
	
}
