package mx.tesseract.bs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.tesseract.admin.dao.ColaboradorDAO;
import mx.tesseract.admin.entidad.Colaborador;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.TESSERACTValidacionException;

@Service("accessBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class AccessBs {
	
	private static final Logger TESSERACT_LOGGER = LogManager.getLogger();

	@Autowired
	private ColaboradorDAO colaboradorDAO;

	public Colaborador verificarLogin(String userName, String password) {
		Colaborador colaborador = null;
		if (userName == null || userName.equals("")) {
			throw new TESSERACTValidacionException("El usuario no ingresó el correo electrónico", "MSG4", null,
					"userName");
		}
		if (password == null || password.equals("")) {
			throw new TESSERACTValidacionException("El usuario no ingresó la contraseña.", "MSG4", null, "password");
		}
		if (userName.length() > Constantes.NUMERO_TREINTA) {
			throw new TESSERACTValidacionException("El usuario no ingresó el correo electrónico", "MSG6",
					new String[] { Constantes.NUMERO_TREINTA.toString(), "caracteres" }, "userName");
		}
		if (password.length() > Constantes.NUMERO_VEINTE) {
			throw new TESSERACTValidacionException("El usuario no ingresó la contraseña.", "MSG6",
					new String[] { Constantes.NUMERO_VEINTE.toString(), "caracteres" }, "password");
		}
		try {
			colaborador = colaboradorDAO.findColaboradorByCorreo(userName);
		} catch (Exception e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "verificarLogin", e);
		}
		if (colaborador == null || !colaborador.getContrasenia().equals(password)) {
			throw new TESSERACTValidacionException("Colaborador no encontrado o contraseña incorrecta", "MSG19");
		}
		return colaborador;
	}

}
