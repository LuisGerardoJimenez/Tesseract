package mx.tesseract.editor.bs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.tesseract.editor.entidad.Atributo;
import mx.tesseract.editor.entidad.Entidad;
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
	
	public void registrarAtributo(Atributo atributo) {
		if (rn006.isValidRN006(atributo)) {
			Entidad entidad = genericoDAO.findById(Entidad.class, atributo.getEntidad().getId());
			atributo.setEntidad(entidad);
			if (atributo.getTipoDato().getId() == Constantes.TIPO_DATO_BOOLEANO || atributo.getTipoDato().getId() == Constantes.TIPO_DATO_FECHA) {
				atributo.setLongitud(null);
				atributo.setFormatoArchivo(null);
				atributo.setTamanioArchivo(null);
				atributo.setUnidadTamanio(null);
				atributo.setOtroTipoDato(null);
			}
			System.out.println("---------------------->");
			System.out.println("Nombre: "+atributo.getNombre());
			System.out.println("Descripcion: "+atributo.getDescripcion());
			System.out.println("Obligatorio: "+atributo.isObligatorio());
			System.out.println("Longitud: "+atributo.getLongitud());
			System.out.println("FormatoArchivo: "+atributo.getFormatoArchivo());
			System.out.println("TamañoArchivo: "+atributo.getTamanioArchivo());
			System.out.println("UnidadTamanio: "+atributo.getUnidadTamanio().getId());
			System.out.println("TipoDato: "+atributo.getTipoDato().getId());
			System.out.println("OtroTipoDato: "+atributo.getOtroTipoDato());
			System.out.println("---------------------->");			
		} else {
			throw new TESSERACTValidacionException("EL nombre del atributo ya existe.", "MSG7",
					new String[] { "El", "Atributo", atributo.getNombre() }, "model.nombre");
		}
	}
	

}
