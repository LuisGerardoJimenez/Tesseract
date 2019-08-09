package mx.tesseract.action;

import java.util.Collection;
import java.util.Map;

import mx.tesseract.admin.bs.LoginBs;
import mx.tesseract.admin.entidad.Colaborador;
import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.bs.AccessBs;
import mx.tesseract.util.ActionSupportTESSERACT;
import mx.tesseract.util.ErrorManager;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;
import mx.tesseract.util.SessionManager;

import org.apache.struts2.convention.annotation.AllowedMethods;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

@Results({ @Result(name = "administrador", type = "redirectAction", params = { "actionName", "proyectos-admin" }),
		@Result(name = "colaborador", type = "redirectAction", params = { "actionName", "proyectos" }),
		@Result(name = "recover", type = "dispatcher", location = "recover.jsp") })
@AllowedMethods({ "logout" })
public class AccessAct extends ActionSupportTESSERACT {
	
	private static final long serialVersionUID = 1L;
	private static final String ADMINISTRADOR = "administrador";
	private static final String COLABORADOR = "colaborador";
	private static final String RECOVER = "recover";
	private Map<String, Object> userSession;
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
			e.printStackTrace();
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
			System.out.println("Error en el Create() TESSERACTValidacionException");
			System.err.println("Tve: " + tve);
			ErrorManager.agregaMensajeError(this, tve);
		} catch (TESSERACTException te) {
			System.out.println("Error en el Create() TESSERACTException");
			System.err.println("Te: " + te);
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			System.out.println("E: " + e);
			System.err.println("Error en el Create() Exception");
			ErrorManager.agregaMensajeError(this, e);
		}
		return resultado;
	}

	public String logout() {
		if (!SessionManager.isEmpty()) {
			SessionManager.clear();
		}
		return INDEX;
	}

	public void setSession(Map<String, Object> session) {
		this.userSession = session;
	}

	public Map<String, Object> getUserSession() {
		return userSession;
	}

	public void setUserSession(Map<String, Object> userSession) {
		this.userSession = userSession;
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
