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
	
	/*public String index() {
		System.out.println("Entramos a index");
		try {
			
		}
	}*/

	public String index() {
		System.out.println("Entramos a index");
		String resultado = INDEX;
		try {
			if (SessionManager.isLogged()) {
				/*if (SessionManager.consultarColaboradorActivo()
						.isAdministrador()) {
					resultado = "administrador";
				} else {
					resultado = "colaborador";
				}*/
			}
			@SuppressWarnings("unchecked")
			Collection<String> msjs = (Collection<String>) SessionManager
					.get("mensajesAccion");
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
		String resultado = null;
		Colaborador colaborador;
		Map<String, Object> session;
		try {
			if (userSession != null) {
				userSession.clear();
			}
			//colaborador = accessBs.verificarLogin(userName, password);
			session = ActionContext.getContext().getSession();
			//session.put("login", true);
			//session.put("colaboradorCURP", colaborador.getCurp());
			setSession(session);
			/*if (SessionManager.consultarColaboradorActivo().isAdministrador()) {
				resultado = "administrador";
			} else {
				resultado = "colaborador";
			}*/
		} catch (TESSERACTValidacionException pve) {
			System.out.println("Uno");
			ErrorManager.agregaMensajeError(this, pve);
			System.out.println("Pve: "+pve);
			return index();
		} catch (TESSERACTException pe) {
			System.out.println("dos");
			System.out.println("Pe: "+pe);
			ErrorManager.agregaMensajeError(this, pe);
		} catch (Exception e) {
			System.out.println("E: "+e);
			System.out.println("tres");
			ErrorManager.agregaMensajeError(this, e);
		}
		return "welcome";
	}

	public String logout() {
		if (userSession != null) {
			userSession.clear();
		}
		return index();
	}

	

	public static String getMenu() throws Exception {
		String resultado;
		/*Proyecto proyecto = SessionManager.consultarProyectoActivo();
		Colaborador colaborador = SessionManager.consultarColaboradorActivo();*/
		Proyecto proyecto = null;
		Colaborador colaborador = null;
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

}
