package mx.tesseract.editor.bs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.editor.entidad.TipoReglaNegocio;
import mx.tesseract.util.TESSERACTException;

@Service("tipoReglaNegocioBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)

public class TipoReglaNegocioBs {

	@Autowired
	private GenericoDAO genericoDAO;

	public List<TipoReglaNegocio> consultarTipoReglaNegocio() {
		List<TipoReglaNegocio> listTipoRN = genericoDAO.findAll(TipoReglaNegocio.class);
		if (listTipoRN.isEmpty()) {
			throw new TESSERACTException("No se pueden consultar los Tipos de Regla de Negocio.", "MSG29");
		}
		return listTipoRN;
	}

}
