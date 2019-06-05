package mx.tesseract.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
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
		ActionContext.getContext().getSession().get("login");
		System.out.println("NameSpace: " + invocation.getProxy().getNamespace());
		System.out.println("ActionName: " + invocation.getProxy().getActionName());
		System.out.println("Method: " + invocation.getProxy().getMethod());
		Object loginObject = ActionContext.getContext().getSession().get("login");
		if (loginObject != null) {
			System.out.println("login?: "+(Boolean) loginObject);
		} else {
			System.out.println("No hay llame login");
		}
		/*if (loginObject != null) {
			Boolean login = (Boolean) loginObject;
			if (login) {
				resultado = invocation.invoke();
			}
		}*/
		System.out.println("Resultado: " + resultado);
		return invocation.invoke();
	}
}