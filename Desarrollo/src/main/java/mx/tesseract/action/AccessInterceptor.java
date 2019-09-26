package mx.tesseract.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import mx.tesseract.util.SessionManager;

public class AccessInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String resultado = Action.LOGIN;
		System.out.println("Inicia interceptor");
		System.out.println("NameSpace: " + invocation.getProxy().getNamespace());
		System.out.println("ActionName: " + invocation.getProxy().getActionName());
		System.out.println("Method: " + invocation.getProxy().getMethod());
		Object loginObject = SessionManager.get("login");
		if (loginObject != null && (Boolean) loginObject) {
			resultado = invocation.invoke();
		} else if (invocation.getProxy().getActionName().isEmpty() || invocation.getProxy().getActionName().equals("access")) {
			resultado = invocation.invoke();
		}
		System.out.println("Resultado: " + resultado);
		return resultado;
	}
}