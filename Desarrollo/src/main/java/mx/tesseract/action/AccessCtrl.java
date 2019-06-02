package mx.tesseract.action;

import java.util.Collection;
import java.util.Map;

import mx.tesseract.admin.entidad.Colaborador;
import mx.tesseract.admin.entidad.ColaboradorProyecto;
import mx.tesseract.admin.entidad.Proyecto;/*
import mx.tesseract.bs.AccessBs;
import mx.tesseract.editor.model.Modulo;*/
import mx.tesseract.util.ActionSupportTESSERACT;
import mx.tesseract.util.ErrorManager;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;
import mx.tesseract.util.SessionManager;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;

@InterceptorRef(value = "defaultStack")
@Results({
		@Result(name = "administrador", type = "redirectAction", params = {
				"actionName", "proyectos-admin" }),
		@Result(name = "colaborador", type = "redirectAction", params = {
				"actionName", "proyectos" }),
		@Result(name = "recover", type = "dispatcher", location = "recover.jsp") })
public class AccessCtrl extends ActionSupportTESSERACT implements SessionAware {
	/** 
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Map<String, Object> userSession;
	private String userName;
	private String password;

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

		} catch (TESSERACTException pe) {
			ErrorManager.agregaMensajeError(this, pe);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}

	public String login() throws Exception {
		System.out.println("Entramos a login");
		String resultado = null;
		Colaborador colaborador;
		Map<String, Object> session;
		try {
			if (userSession != null) {
				userSession.clear();
			}
			//colaborador = AccessBs.verificarLogin(userName, password);
			session = ActionContext.getContext().getSession();
			session.put("login", true);
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
		return resultado;
	}

	public String logout() {
		if (userSession != null) {
			userSession.clear();
		}
		return index();
	}

	public String recover() {
		String resultado = null;
		try {
			resultado = "recover";
		} catch (TESSERACTValidacionException pve) {
			ErrorManager.agregaMensajeError(this, pve);
			resultado = recover();
		} catch (TESSERACTException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}
		return resultado;
	}

	public String sendPassword() {
		String resultado = null;
		try {
			//AccessBs.recuperarContrasenia(userName);
			resultado = INDEX;
			addActionMessage(getText("MSG32"));

			SessionManager.set(this.getActionMessages(), "mensajesAccion");

		} catch (TESSERACTValidacionException pve) {
			ErrorManager.agregaMensajeError(this, pve);
			resultado = recover();
		} catch (TESSERACTException pe) {
			ErrorManager.agregaMensajeError(this, pe);
			resultado = index();
		} catch (Exception e) {
			ErrorManager.agregaMensajeError(this, e);
			resultado = index();
		}
		return resultado;
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
	
	public static String getRol() throws Exception {
		/*Proyecto proyecto = SessionManager.consultarProyectoActivo();
		Colaborador colaborador = SessionManager.consultarColaboradorActivo();
		
		for (ColaboradorProyecto colaboradorProyecto : proyecto.getProyecto_colaboradores()) {
			if (colaboradorProyecto.getColaborador().getCurp().equals(colaborador.getCurp())) {
				return colaboradorProyecto.getRol().getId() + "";
			}
		}*/
		
		return "";
	}

	/*public static Proyecto getInfoProyecto() throws Exception {
		Proyecto proyecto = null;
		proyecto = SessionManager.consultarProyectoActivo();
		return proyecto;
	}

	public static Modulo getInfoModulo() throws Exception {
		Modulo modulo = null;
		modulo = SessionManager.consultarModuloActivo();
		return modulo;
	}*/
	
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
