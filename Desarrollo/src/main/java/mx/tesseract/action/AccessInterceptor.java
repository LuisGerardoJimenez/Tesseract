package mx.tesseract.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import mx.tesseract.admin.dao.ProyectoDAO;
import mx.tesseract.admin.entidad.Proyecto;

public class AccessInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String resultado = Action.LOGIN;
		System.out.println("Inicia interceptor");
		HttpSession session = ServletActionContext.getRequest().getSession(false);
		System.out.println("Session: "+session);
		if (session != null) {
			Object loginObject = session.getAttribute("login");
			if (loginObject != null) {
				Boolean login = (Boolean) loginObject;
				if (login) {
					resultado = invocation.invoke();
				}
			}
		}
		System.out.println("Resultado: "+resultado);
		return resultado;
	}
}