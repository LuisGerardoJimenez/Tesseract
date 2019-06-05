package mx.tesseract.action;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ResultPath;

import mx.tesseract.util.ActionSupportTESSERACT;

@ResultPath("/pages/welcomeM/")
public class WelcomeAct extends ActionSupportTESSERACT {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String index() {
		System.out.println("Ingrese Al Welcome");
		return INDEX;
	}
}
