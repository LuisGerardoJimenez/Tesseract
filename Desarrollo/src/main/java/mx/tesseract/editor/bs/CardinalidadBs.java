package mx.tesseract.editor.bs;

import mx.tesseract.br.RN006;
import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.editor.dao.ElementoDAO;
import mx.tesseract.editor.entidad.Cardinalidad;
import mx.tesseract.util.TESSERACTException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("cardinalidadBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class CardinalidadBs {

	@Autowired
	private RN006 rn006;

	@Autowired
	private ElementoDAO elementoDAO;

	@Autowired
	private GenericoDAO genericoDAO;

	@Autowired
	private ElementoBs elementoBs;

	public List<Cardinalidad> consultarCardinalidad() {
		List<Cardinalidad> listCardinalidad = genericoDAO.findAll(Cardinalidad.class);
		if (listCardinalidad.isEmpty()) {
			throw new TESSERACTException("No se pueden consultar las cardinalidades.", "MSG29");
		}
		return listCardinalidad;
	}

}
