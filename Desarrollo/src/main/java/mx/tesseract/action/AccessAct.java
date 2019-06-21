package mx.tesseract.action;

import java.util.Collection;
import java.util.Map;

import mx.tesseract.admin.bs.LoginBs;
import mx.tesseract.admin.entidad.Colaborador;
import mx.tesseract.admin.entidad.ColaboradorProyecto;
import mx.tesseract.admin.entidad.Proyecto;/*
import mx.tesseract.editor.model.Modulo;*/
import mx.tesseract.bs.AccessBs;
import mx.tesseract.util.ActionSupportTESSERACT;
import mx.tesseract.util.ErrorManager;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;
import mx.tesseract.util.SessionManager;

import org.apache.struts2.convention.annotation.AllowedMethods;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import ch.qos.logback.core.joran.action.Action;

@Results({
		@Result(name = "administrador", type = "redirectAction", params = {
				"actionName", "proyectos-admin" }),
		@Result(name = "colaborador", type = "redirectAction", params = {
				"actionName", "proyectos" }),
		@Result(name = "recover", type = "dispatcher", location = "recover.jsp"),
		@Result(name = "welcome", type = "redirectAction", params = {"actionName", "welcome" })
		})
@AllowedMethods({"logout"})
public class AccessAct extends ActionSupportTESSERACT implements SessionAware {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Map<String, Object> userSession;
	private String userName;
	private String password;
	private static String menuString;
	
	@Autowired
	private AccessBs accessBs;
	
	@Autowired
	private LoginBs loginBs;
	
	public String index() {
		String resultado = INDEX;
		try {
			if (SessionManager.isLogged()) {
				if (loginBs.consultarColaboradorActivo().isAdministrador()) {
					resultado = "administrador";
				} else {
					resultado = "colaborador";
				}
			}
			@SuppressWarnings("unchecked")
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
	
	public String create() throws Exception {
		String resultado = INDEX;
		Colaborador colaborador;
		try {
			if (!SessionManager.isEmpty()) {
				SessionManager.clear();				
			}
			colaborador = accessBs.verificarLogin(userName, password);
			SessionManager.set(true, "login");
			SessionManager.set(colaborador.getCurp(), "colaboradorCURP");
			menuString = getMenu();
			if (colaborador.isAdministrador()) {
				resultado = "administrador";
			} else {
				resultado = "colaborador";
			}
		} catch (TESSERACTValidacionException tve) {
			System.out.println("Error en el Create() TESSERACTValidacionException");
			System.err.println("Tve: "+tve);
			ErrorManager.agregaMensajeError(this, tve);
		} catch (TESSERACTException te) {
			System.out.println("Error en el Create() TESSERACTException");
			System.err.println("Te: "+te);
			ErrorManager.agregaMensajeError(this, te);
		} catch (Exception e) {
			System.out.println("E: "+e);
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

	public String getMenu() throws Exception {
		String resultado;
		Proyecto proyecto = loginBs.consultarProyectoActivo();
		Colaborador colaborador = loginBs.consultarColaboradorActivo();
		if (colaborador != null && colaborador.isAdministrador()) {
			resultado = "administrador/menus/menuAdministrador";
		} else if (proyecto == null) {
			resultado = "editor/menus/menuAnalista";
		} else {
			resultado = "editor/menus/menuAnalistaProyecto";
		}
		return resultado;
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
	
	public String getMenuString() {
		return menuString;
	}

	public void setMenuString(String menuString) {
		this.menuString = menuString;
	}

}
