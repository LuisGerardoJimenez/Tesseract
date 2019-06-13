package mx.tesseract.bs;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.tesseract.admin.dao.ColaboradorDAO;
import mx.tesseract.admin.entidad.Colaborador;
/*
import mx.tesseract.admin.dao.ColaboradorDAO;
import mx.tesseract.admin.model.Colaborador;
import mx.tesseract.admin.model.ColaboradorProyecto;
import mx.tesseract.admin.model.Proyecto;*/
import mx.tesseract.util.Constantes;
import mx.tesseract.util.Correo;
import mx.tesseract.util.TESSERACTValidacionException;/*
import mx.tesseract.util.Validador;*/
import mx.tesseract.util.Validador;

@Service("accessBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class AccessBs {
	
	@Autowired
	private ColaboradorDAO colaboradorDAO;

	public Colaborador verificarLogin(String userName, String password) {
		System.out.println("Entre a Buscar al Colaborador");
		Colaborador colaborador = null;
		if (Validador.esNuloOVacio(userName)) {
			throw new TESSERACTValidacionException(
					"El usuario no ingres� el correo electr�nico", "MSG4", null,
					"userName");
		}
		if (Validador.esNuloOVacio(password)) {
			throw new TESSERACTValidacionException(
					"El usuario no ingres� la contrase�a.", "MSG4", null,
					"password");
		}
		if (Validador.validaLongitudMaxima(userName, Constantes.NUMERO_TREINTA)) {
			throw new TESSERACTValidacionException(
					"El usuario no ingres� el correo electr�nico", "MSG6", 
					new String[] { Constantes.NUMERO_TREINTA.toString(), "caracteres"},
					"userName");
		}
		if (Validador.validaLongitudMaxima(password, Constantes.NUMERO_VEINTE)) {
			throw new TESSERACTValidacionException(
					"El usuario no ingres� la contrase�a.", "MSG6", 
					new String[] { Constantes.NUMERO_VEINTE.toString(), "caracteres"},
					"password");
		}
		/*if (Validador.esInvalidaREGEX(password, Constantes.REGEX_CONTRASENIA)) {
			throw new TESSERACTValidacionException(
					"El usuario no ingres� la contrase�a.", "MSG6", 
					new String[] { Constantes.NUMERO_VEINTE.toString(), "caracteres"},
					"password");
		}*/
		
		try {
			System.out.println("Buscando al colaborador");
			colaborador = colaboradorDAO.findColaboradorByCorreo(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (colaborador == null || !colaborador.getContrasenia().equals(password)) {
			throw new TESSERACTValidacionException("Colaborador no encontrado o contrase�a incorrecta", "MSG31");
		}
		return colaborador;
	}
/*
	public static void recuperarContrasenia(String userName) throws AddressException, MessagingException {
		Colaborador colaborador = null;
		if (Validador.esNuloOVacio(userName)) {
			throw new TESSERACTValidacionException(
					"El usuario no ingres� el correo electr�nico", "MSG4", null,
					"userName");
		}
		if (!Validador.esCorreo(userName)) {
			throw new TESSERACTValidacionException("Colaborador no encontrado", "MSG33");

		}
		try {
			colaborador = new ColaboradorDAO().consultarColaboradorCorreo(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (colaborador == null) {
			throw new TESSERACTValidacionException("Colaborador no encontrado", "MSG33");
		}
		Correo.enviarCorreo(colaborador, 1);
		
	}
	
	public static boolean verificarPermisos(Proyecto proyecto, Colaborador colaborador) {
		boolean acceso = false;
		for (ColaboradorProyecto colaboradorProyecto : colaborador.getColaborador_proyectos()) {
			if (colaboradorProyecto.getProyecto().getId() == proyecto.getId()) {
				acceso = true;
				break;
			}
		}
		return acceso;
	}*/
}
