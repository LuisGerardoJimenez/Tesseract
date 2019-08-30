package mx.tesseract.editor.bs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.tesseract.editor.entidad.Atributo;
import mx.tesseract.editor.entidad.Entidad;
import mx.tesseract.editor.entidad.TipoDato;
import mx.tesseract.enums.ReferenciaEnum.Clave;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.TESSERACTValidacionException;
import mx.tesseract.br.RN006;
import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.editor.dao.AtributoDAO;
import mx.tesseract.editor.dao.ElementoDAO;

@Service("atributoBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class AtributoBs {
	
	@Autowired
	private AtributoDAO atributoDAO;
	
	@Autowired
	private RN006 rn006;
	
	@Autowired
	private GenericoDAO genericoDAO;
	
	
	public List<Atributo> consultarAtributosByEntidad(Integer idEntidad) {
		List<Atributo> atributos = atributoDAO.findByIdEntidad(idEntidad);
		return atributos;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void registrarAtributo(Atributo atributo, Integer idEntidad) {
		Entidad entidad = genericoDAO.findById(Entidad.class, idEntidad);
		atributo.setEntidad(entidad);
		if (rn006.isValidRN006(atributo)) {
			TipoDato tipoDato = genericoDAO.findById(TipoDato.class, atributo.getTipoDato().getId());
			atributo.setTipoDato(tipoDato);
			Integer idTipo = atributo.getTipoDato().getId();
			if (idTipo == Constantes.TIPO_DATO_BOOLEANO || idTipo == Constantes.TIPO_DATO_FECHA) {
				atributo.setLongitud(null);
				atributo.setFormatoArchivo(null);
				atributo.setTamanioArchivo(null);
				atributo.setUnidadTamanio(null);
				atributo.setOtroTipoDato(null);
			} else if (idTipo == Constantes.TIPO_DATO_CADENA || idTipo == Constantes.TIPO_DATO_FLOTANTE 
					|| idTipo == Constantes.TIPO_DATO_ENTERO) {
				atributo.setFormatoArchivo(null);
				atributo.setTamanioArchivo(null);
				atributo.setUnidadTamanio(null);
				atributo.setOtroTipoDato(null);
			} else if (idTipo == Constantes.TIPO_DATO_ARCHIVO) {
				atributo.setLongitud(null);
				atributo.setOtroTipoDato(null);
			} else {
				atributo.setLongitud(null);
				atributo.setFormatoArchivo(null);
				atributo.setTamanioArchivo(null);
				atributo.setUnidadTamanio(null);
			}
			genericoDAO.save(atributo);
		} else {
			throw new TESSERACTValidacionException("EL nombre del atributo ya existe.", "MSG7",
					new String[] { "El", "Atributo", atributo.getNombre() }, "model.nombre");
		}
	}
	

}
