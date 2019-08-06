package mx.tesseract.util;

public class ErrorManager {
	public static void agregaMensajeError(ActionSupportTESSERACT at, Exception ex) {
		if (ex instanceof TESSERACTException) {
			TESSERACTException te = (TESSERACTException) ex;
			if (te instanceof TESSERACTValidacionException) {
				TESSERACTValidacionException pve = (TESSERACTValidacionException) te;
				if (pve.getCampo() != null) {
					if (te.getParametros() != null) {
						at.addFieldError(pve.getCampo(), at.getText(pve.getIdMensaje(), te.getParametros()));
					} else {
						at.addFieldError(pve.getCampo(), at.getText(pve.getIdMensaje()));
					}
				} else {
					if (te.getParametros() != null) {
						at.addActionError(at.getText(te.getIdMensaje(), te.getParametros()));
					} else {
						at.addActionError(at.getText(te.getIdMensaje()));
					}
				}
			} else {
				if (te.getParametros() != null) {
					at.addActionError(at.getText(te.getIdMensaje(), te.getParametros()));
				} else {
					at.addActionError(at.getText(te.getIdMensaje()));
				}
			}
		} else {
			at.addActionError(at.getText("MSG12"));
		}
		System.err.println(ex.getMessage());
		ex.printStackTrace();
	}
}
