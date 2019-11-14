package mx.tesseract.util;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

public class SessionManager {
	
	/**
	 * Método proxy que regresa la sesión
	 * @return El objeto de sesión
	 */
	public static Object getSession() {
		return ActionContext.getContext().getSession();
	}
	
	/**
	 * Método proxy que regresa el objeto que se encuentra en la sesión con la
	 * llave especificada en nombre.
	 * 
	 * @param nombre
	 *            llave del objeto
	 * @return objeto que se obtuvo de la sesión
	 */
	public static Object get(String nombre) {
		return ActionContext.getContext().getSession().get(nombre);
	}

	/**
	 * Método proxy que sube un objeto a la sesión con cuya llave se especifica
	 * en nombre
	 * 
	 * @param o
	 *            objeto a subir a la sesión
	 * @param nombre
	 *            llave del objeto
	 * @return objeto que se agregó a la sesión
	 */
	public static Object set(Object o, String nombre) {
		return ActionContext.getContext().getSession().put(nombre,o);
	}

	/**
	 * Método proxy que limpia la sesión.
	 */
	public static void clear() {
		ActionContext.getContext().getSession().clear();
	}

	/**
	 * Método proxy que elimina un objeto de la sesión cuya llave se especifica
	 * en nombre
	 * 
	 * @param nombre
	 *            llave del objeto
	 * @return objeto que se eliminó de la sesión
	 */
	public static Object delete(String nombre) {
		return ActionContext.getContext().getSession().remove(nombre);
	}

	/**
	 * Método proxy que verifica si la sesión está limpia
	 * 
	 * @return false si la sesión no está limpia
	 */
	public static boolean isEmpty() {
		return ActionContext.getContext().getSession().isEmpty();
	}
	
	public static boolean isLogged() {
		HttpSession session = ServletActionContext.getRequest().getSession(false); 
		boolean logged = false;
		if (session != null) {
			if (session.getAttribute("login") != null) {
				logged = (Boolean) session.getAttribute("login");
				return logged;
			}
		} 
		return false;
	}

	public static void pushURL(HttpServletRequest request) throws Exception{
		@SuppressWarnings("unchecked")
		Deque<String> URLStack = (Deque<String>) SessionManager.get("URLStack");
		if(URLStack == null) {
			URLStack = new ArrayDeque<String>();
		}

		String url = request.getHeader("Referer");
		if(url == null) {
			url = request.getRequestURL().toString();
		}
		URLStack.push(url);
		SessionManager.set(URLStack, "URLStack");
	}

	public static String popURL(HttpServletRequest request) throws Exception{		
		@SuppressWarnings("unchecked")
		Deque<String> URLStack = (Deque<String>) SessionManager.get("URLStack");
		String urlPrev = null;
		try {
		urlPrev = URLStack.pop();
		} catch (NoSuchElementException nse) {
			System.err.println("No hay url en la pila");
			urlPrev = request.getContextPath();
		} catch (NullPointerException npe) {
			System.err.println("No existe URLStack en la pila");
			urlPrev = request.getContextPath();
		}
		SessionManager.set(URLStack, "URLStack");
		return urlPrev;
	}

}
