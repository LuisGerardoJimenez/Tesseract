package mx.tesseract.br;

import java.io.File;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.tesseract.util.Constantes;

@Service("rN040")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class RN040 {
	
	public Boolean isValidRN040(File archivo) {
		Boolean valido = true;
		if (archivo.length() > Constantes.TAMANIO_ARCHIVO) {
			valido = false;
		}
		return valido;
	}

}
