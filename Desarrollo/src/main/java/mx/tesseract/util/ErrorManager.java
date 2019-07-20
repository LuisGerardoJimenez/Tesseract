package mx.tesseract.util;

public class ErrorManager {
	public static void agregaMensajeError(ActionSupportTESSERACT ap, Exception ex) {
		System.out.println("1");
		if(ex instanceof TESSERACTException) {
		TESSERACTException pe = (TESSERACTException) ex;
			if(pe instanceof TESSERACTValidacionException) {
				TESSERACTValidacionException pve = (TESSERACTValidacionException) pe;
				if(pve.getCampo() != null) {
					if(pe.getParametros() != null){
						ap.addFieldError(pve.getCampo(), ap.getText(pve.getIdMensaje(), pe.getParametros()));
						System.out.println("2");
					} else {
						ap.addFieldError(pve.getCampo(), ap.getText(pve.getIdMensaje()));
						System.out.println("3");
					}
				} else {
					if(pe.getParametros() != null){
						ap.addActionError(ap.getText(pe.getIdMensaje(), pe.getParametros()));
						System.out.println("4");
					} else {
						ap.addActionError("SI esto sale esta bien");
						System.out.println("5");
					}
				}
			} else { 
				if(pe.getParametros() != null){
					ap.addActionError(ap.getText(pe.getIdMensaje(), pe.getParametros()));
					System.out.println("6");
				} else {
					ap.addActionError(ap.getText(pe.getIdMensaje()));
					System.out.println("7");
				}
			}
		} else {
			ap.addActionError(ap.getText("MSG13"));
		}
		
		System.err.println(ex.getMessage());
		ex.printStackTrace();
	}
}

