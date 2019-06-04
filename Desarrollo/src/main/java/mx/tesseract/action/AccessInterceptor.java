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
		HttpSession session = ServletActionContext.getRequest().getSession(
				false);
		Object loginObject = session.getAttribute("login");
		boolean login = false;
		if (loginObject != null) {
			login = (Boolean) loginObject;
			if (!login) {
				return Action.LOGIN;
			} else {
				return invocation.invoke();
			}
		} else {
			return Action.LOGIN;
		}

	}
}