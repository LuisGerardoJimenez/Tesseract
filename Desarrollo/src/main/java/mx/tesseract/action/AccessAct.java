package mx.tesseract.action;

import java.util.Collection;
import java.util.Map;

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
public class AccessAct extends ActionSupportTESSERACT implements SessionAware {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Map<String, Object> userSession;
	private String userName;
	private String password;
	
	@Autowired
	private AccessBs accessBs;
	
	@Autowired
	private SessionManager sessionManager;
	
	public String index() {
		System.out.println("Entramos a index");
		String resultado = INDEX;
		try {
			if (SessionManager.isLogged()) {
				if (sessionManager.consultarColaboradorActivo().isAdministrador()) {
					resultado = "administrador";
				} else {
					resultado = "colaborador";
				}
			}
			@SuppressWarnings("unchecked")
			Collection<String> msjs = (Collection<String>) SessionManager.get("mensajesAccion");
			this.setActionMessages(msjs);
			SessionManager.delete("mensajesAccion");
			System.out.println("Saliendo del index");
		} catch (TESSERACTException pe) {
			ErrorManager.agregaMensajeError(this, pe);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}
	
	public String create() throws Exception {
		System.out.println("Entramos a login");
		String resultado = INDEX;
		Colaborador colaborador;
		try {
			if (!sessionManager.isEmpty()) {
				SessionManager.clear();				
			}
			colaborador = accessBs.verificarLogin(userName, password);
			SessionManager.set(true, "login");
			SessionManager.set(colaborador.getCurp(), "colaboradorCURP");
			if (sessionManager.consultarColaboradorActivo().isAdministrador()) {
				resultado = "administrador";
			} else {
				resultado = "colaborador";
			}
		} catch (TESSERACTValidacionException pve) {
			System.out.println("Error en el Create() TESSERACTValidacionException");
			ErrorManager.agregaMensajeError(this, pve);
			System.out.println("Pve: "+pve);
		} catch (TESSERACTException pe) {
			System.out.println("Error en el Create() TESSERACTException");
			System.out.println("Pe: "+pe);
			ErrorManager.agregaMensajeError(this, pe);
		} catch (Exception e) {
			System.out.println("E: "+e);
			System.out.println("Error en el Create() Exception");
			ErrorManager.agregaMensajeError(this, e);
		}
		System.out.println("Resultado Create: "+resultado);
		return resultado;
	}
	
	public String editNew() {
		return EDITNEW;
	}

	public String logout() {
		if (!sessionManager.isEmpty()) {
			SessionManager.clear();
		}
		return index();
	}

	

	public String getMenu() throws Exception {
		String resultado;
		Proyecto proyecto = sessionManager.consultarProyectoActivo();
		Colaborador colaborador = sessionManager.consultarColaboradorActivo();
		if (colaborador != null && colaborador.isAdministrador()) {
			resultado = "administrador/menus/menuAdministrador";
		} else if (proyecto == null) {
			resultado = "editor/menus/menuAnalista";
		} else {
			resultado = "editor/menus/menuAnalistaProyecto";
		}
		System.out.println("Resultado Menu: "+resultado);
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

}
