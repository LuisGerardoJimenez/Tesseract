package mx.tesseract.action;

import java.util.Collection;

import mx.tesseract.admin.bs.LoginBs;
import mx.tesseract.admin.entidad.Colaborador;
import mx.tesseract.bs.AccessBs;
import mx.tesseract.util.ActionSupportTESSERACT;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.ErrorManager;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;
import mx.tesseract.util.SessionManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.AllowedMethods;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

@Results({ @Result(name = "administrador", type = "redirectAction", params = { "actionName", Constantes.ACTION_NAME_PROYECTOS_ADMIN }),
		@Result(name = "colaborador", type = "redirectAction", params = { "actionName", Constantes.ACTION_NAME_PROYECTOS }),
		@Result(name = "recover", type = "dispatcher", location = "recover.jsp") })
@AllowedMethods({ "logout" })
public class AccessAct extends ActionSupportTESSERACT {
	
	private static final long serialVersionUID = 1L;
	private static final Logger TESSERACT_LOGGER = LogManager.getLogger();
	private static final String ADMINISTRADOR = "administrador";
	private static final String COLABORADOR = "colaborador";
	private String userName;
	private String password;

	@Autowired
	private AccessBs accessBs;

	@Autowired
	private LoginBs loginBs;

	@SuppressWarnings("unchecked")
	public String index() {
		String resultado = INDEX;
		try {
			if (SessionManager.isLogged()) {
				if (loginBs.consultarColaboradorActivo().isAdministrador()) {
					resultado = ADMINISTRADOR;
				} else {
					resultado = COLABORADOR;
				}
			}
			Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
			this.setActionMessages(msjs);
			SessionManager.delete("mensajesAccion");
		} catch (TESSERACTException pe) {
			ErrorManager.agregaMensajeError(this, pe);
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ":" + "Mostrar Login", e);
		}
		return resultado;
	}

	public String create() {
		String resultado = INDEX;
		Colaborador colaborador;
		try {
			if (!SessionManager.isEmpty()) {
				SessionManager.clear();
			}
			colaborador = accessBs.verificarLogin(userName, password);
			SessionManager.set(true, "login");
			SessionManager.set(colaborador.getCurp(), "colaboradorCURP");
			if (colaborador.isAdministrador()) {
				SessionManager.set(true, "admin");
				resultado = ADMINISTRADOR;
			} else {
				resultado = COLABORADOR;
			}
		} catch (TESSERACTValidacionException tve) {
			TESSERACT_LOGGER.debug(this.getClass().getName()+ " --> " + tve.getMessage());
			ErrorManager.agregaMensajeError(this, tve);
		} catch (TESSERACTException te) {
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ":" + "Iniciar Sesión", e);
		}
		return resultado;
	}

	public String logout() {
		if (!SessionManager.isEmpty()) {
			SessionManager.clear();
		}
		return INDEX;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
