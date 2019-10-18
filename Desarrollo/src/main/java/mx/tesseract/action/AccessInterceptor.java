package mx.tesseract.action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import mx.tesseract.util.SessionManager;

public class AccessInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	private static final Logger TESSERACT_LOGGER = LogManager.getLogger();

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String resultado = Action.LOGIN;
		TESSERACT_LOGGER.debug(this.getClass().getName() + ": Inicia interceptor");
		TESSERACT_LOGGER.debug(this.getClass().getName() + ": NameSpace: " + invocation.getProxy().getNamespace());
		TESSERACT_LOGGER.debug(this.getClass().getName() + ": ActionName: " + invocation.getProxy().getActionName());
		TESSERACT_LOGGER.debug(this.getClass().getName() + ": Method: " + invocation.getProxy().getMethod());
		Object loginObject = SessionManager.get("login");
		if (loginObject != null && (Boolean) loginObject) {
			resultado = invocation.invoke();
		} else if (invocation.getProxy().getActionName().isEmpty() || invocation.getProxy().getActionName().equals("access")) {
			resultado = invocation.invoke();
		}
		TESSERACT_LOGGER.debug(this.getClass().getName() + ": Resultado: " + resultado);
		return resultado;
	}
}