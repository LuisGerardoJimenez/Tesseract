package mx.tesseract.editor.bs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.tesseract.editor.entidad.Atributo;
import mx.tesseract.editor.entidad.Entidad;
import mx.tesseract.editor.entidad.TipoDato;
import mx.tesseract.editor.entidad.UnidadTamanio;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;
import mx.tesseract.br.RN006;
import mx.tesseract.br.RN018;
import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.dto.AtributoDTO;
import mx.tesseract.editor.dao.AtributoDAO;

@Service("atributoBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class AtributoBs {
	
	@Autowired
	private AtributoDAO atributoDAO;
	
	@Autowired
	private RN006 rn006;
	
	@Autowired
	private GenericoDAO genericoDAO;
	
	@Autowired
	private RN018 rn018;
	
	public List<Atributo> consultarAtributosByEntidad(Integer idEntidad) {
		return atributoDAO.findByIdEntidad(idEntidad);
	}
	
	public AtributoDTO consultarAtributoById(Integer id) {
		AtributoDTO atributoDTO = new AtributoDTO();
		Atributo atributo = genericoDAO.findById(Atributo.class, id);
		if (atributo != null) {
			atributoDTO.setId(atributo.getId());
			atributoDTO.setNombre(atributo.getNombre());
			atributoDTO.setDescripcion(atributo.getDescripcion());
			atributoDTO.setObligatorio(atributo.isObligatorio());
			atributoDTO.setLongitud(atributo.getLongitud());
			atributoDTO.setFormatoArchivo(atributo.getFormatoArchivo());
			atributoDTO.setTamanioArchivo(atributo.getTamanioArchivo());
			if (atributo.getUnidadTamanio() != null) {
				atributoDTO.setIdUnidadTamanio(atributo.getUnidadTamanio().getId());				
			}
			if (atributo.getTipoDato() != null) {
				atributoDTO.setIdTipoDato(atributo.getTipoDato().getId());				
			}
			atributoDTO.setOtroTipoDato(atributo.getOtroTipoDato());
			atributoDTO.setIdEntidad(atributo.getEntidad().getId());
		}
		return atributoDTO;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void registrarAtributo(AtributoDTO atributoDTO) {
		if (rn006.isValidRN006(atributoDTO)) {
			Atributo atributo = new Atributo();
			atributo.setNombre(atributoDTO.getNombre());
			atributo.setDescripcion(atributoDTO.getDescripcion());
			atributo.setObligatorio(atributoDTO.isObligatorio());
			Entidad entidad = genericoDAO.findById(Entidad.class, atributoDTO.getIdEntidad());
			atributo.setEntidad(entidad);
			TipoDato tipoDato = genericoDAO.findById(TipoDato.class, atributoDTO.getIdTipoDato());
			atributo.setTipoDato(tipoDato);
			Integer idTipo = atributoDTO.getIdTipoDato();
			if (idTipo == Constantes.TIPO_DATO_BOOLEANO || idTipo == Constantes.TIPO_DATO_FECHA) {
				atributo.setLongitud(null);
				atributo.setFormatoArchivo(null);
				atributo.setTamanioArchivo(null);
				atributo.setUnidadTamanio(null);
				atributo.setOtroTipoDato(null);
			} else if (idTipo == Constantes.TIPO_DATO_CADENA || idTipo == Constantes.TIPO_DATO_FLOTANTE 
					|| idTipo == Constantes.TIPO_DATO_ENTERO) {
				atributo.setLongitud(atributoDTO.getLongitud());
				atributo.setFormatoArchivo(null);
				atributo.setTamanioArchivo(null);
				atributo.setUnidadTamanio(null);
				atributo.setOtroTipoDato(null);
			} else if (idTipo == Constantes.TIPO_DATO_ARCHIVO) {
				atributo.setFormatoArchivo(atributoDTO.getFormatoArchivo());
				atributo.setTamanioArchivo(atributoDTO.getTamanioArchivo());
				UnidadTamanio ut = genericoDAO.findById(UnidadTamanio.class, atributoDTO.getIdUnidadTamanio());
				atributo.setUnidadTamanio(ut);
				atributo.setLongitud(null);
				atributo.setOtroTipoDato(null);
			} else {
				atributo.setOtroTipoDato(atributoDTO.getOtroTipoDato());
				atributo.setLongitud(null);
				atributo.setFormatoArchivo(null);
				atributo.setTamanioArchivo(null);
				atributo.setUnidadTamanio(null);
			}
			genericoDAO.save(atributo);
		} else {
			throw new TESSERACTValidacionException("EL nombre del atributo ya existe.", "MSG7",
					new String[] { "El", "Atributo", atributoDTO.getNombre() }, "model.nombre");
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void modificarAtributo(AtributoDTO atributoDTO) {
		if (rn006.isValidRN006(atributoDTO)) {
			Atributo atributo = genericoDAO.findById(Atributo.class, atributoDTO.getId());
			atributo.setNombre(atributoDTO.getNombre());
			atributo.setDescripcion(atributoDTO.getDescripcion());
			atributo.setObligatorio(atributoDTO.isObligatorio());
			TipoDato tipoDato = genericoDAO.findById(TipoDato.class, atributoDTO.getIdTipoDato());
			atributo.setTipoDato(tipoDato);
			Integer idTipo = atributoDTO.getIdTipoDato();
			if (idTipo == Constantes.TIPO_DATO_BOOLEANO || idTipo == Constantes.TIPO_DATO_FECHA) {
				atributo.setLongitud(null);
				atributo.setFormatoArchivo(null);
				atributo.setTamanioArchivo(null);
				atributo.setUnidadTamanio(null);
				atributo.setOtroTipoDato(null);
			} else if (idTipo == Constantes.TIPO_DATO_CADENA || idTipo == Constantes.TIPO_DATO_FLOTANTE 
					|| idTipo == Constantes.TIPO_DATO_ENTERO) {
				atributo.setLongitud(atributoDTO.getLongitud());
				atributo.setFormatoArchivo(null);
				atributo.setTamanioArchivo(null);
				atributo.setUnidadTamanio(null);
				atributo.setOtroTipoDato(null);
			} else if (idTipo == Constantes.TIPO_DATO_ARCHIVO) {
				atributo.setFormatoArchivo(atributoDTO.getFormatoArchivo());
				atributo.setTamanioArchivo(atributoDTO.getTamanioArchivo());
				UnidadTamanio ut = genericoDAO.findById(UnidadTamanio.class, atributoDTO.getIdUnidadTamanio());
				atributo.setUnidadTamanio(ut);
				atributo.setLongitud(null);
				atributo.setOtroTipoDato(null);
			} else {
				atributo.setOtroTipoDato(atributoDTO.getOtroTipoDato());
				atributo.setLongitud(null);
				atributo.setFormatoArchivo(null);
				atributo.setTamanioArchivo(null);
				atributo.setUnidadTamanio(null);
			}
			genericoDAO.update(atributo);
		} else {
			throw new TESSERACTValidacionException("EL nombre del atributo ya existe.", "MSG7",
					new String[] { "El", "Atributo", atributoDTO.getNombre() }, "model.nombre");
		}
	}
	
	public List<AtributoDTO> consultarAtributosToRN(Integer idEntidad) {
		List<Atributo> atributos = atributoDAO.findByIdEntidad(idEntidad);
		List<AtributoDTO> atributosDTO = new ArrayList<>(); 
		for(Atributo atributoItem : atributos) {
			AtributoDTO atributoDTO = new AtributoDTO();
			atributoDTO.setId(atributoItem.getId());
			atributoDTO.setNombre(atributoItem.getNombre());
			atributoDTO.setObligatorio(atributoItem.isObligatorio());
			atributosDTO.add(atributoDTO);
		}
		return atributosDTO;
	}

	@Transactional(rollbackFor = Exception.class)
	public void eliminarAtributo(AtributoDTO model) {
		if (rn018.isValidRN018(model)) {
			Atributo atributo = genericoDAO.findById(Atributo.class, model.getId());
			genericoDAO.delete(atributo);
		} else {
			throw new TESSERACTException("Este elemento no se puede eliminar debido a que esta siendo referenciado.",
					"MSG13");
		}
	}
	

}
