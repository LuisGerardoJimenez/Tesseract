package mx.tesseract.br;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.tesseract.dto.AccionDTO;
import mx.tesseract.dto.ActorDTO;
import mx.tesseract.dto.AtributoDTO;
import mx.tesseract.dto.CasoUsoDTO;
import mx.tesseract.dto.EntidadDTO;
import mx.tesseract.dto.MensajeDTO;
import mx.tesseract.dto.PantallaDTO;
import mx.tesseract.dto.ReglaNegocioDTO;
import mx.tesseract.dto.TerminoGlosarioDTO;
import mx.tesseract.editor.dao.ElementoDAO;
import mx.tesseract.editor.entidad.CasoUso;
import mx.tesseract.editor.entidad.ReglaNegocio;
import mx.tesseract.enums.ReferenciaEnum.Clave;
import mx.tesseract.util.Constantes;

@Service("rN018")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class RN018 {	
	
	@Autowired
	private ElementoDAO elementoDAO;
	
	public Boolean isValidRN018(MensajeDTO entidad) {
		Boolean valido = true;
		CasoUso casoUso = null;
		if (entidad.getId() == null) {
			casoUso = elementoDAO.findElementoHasCasoUsoAsociado(Clave.MSG+"·"+entidad.getId());
		}
		if (casoUso != null) {
			valido = false;
		}
		return valido;
	}
	
	public Boolean isValidRN018(TerminoGlosarioDTO entidad) {
		Boolean valido = true;
		CasoUso casoUso = null;
		if (entidad.getId() == null) {
			casoUso = elementoDAO.findElementoHasCasoUsoAsociado(Clave.GLS+"·"+entidad.getId());
		}
		if (casoUso != null) {
			valido = false;
		}
		return valido;
	}
	
	public Boolean isValidRN018(AtributoDTO entidad) {
		Boolean valido = true;
		ReglaNegocio reglaNegocio = null;
		if (entidad.getId() == null) {
			reglaNegocio = elementoDAO.findElementoHasAtributo(entidad.getId(),entidad.getProyectoId());
		}
		if (reglaNegocio != null) {
			valido = false;
		}
		return valido;
	}
	
	public Boolean isValidRN018(EntidadDTO entidadDTO) {
		Boolean valido = true;
		CasoUso casoUso = null;
		if (entidadDTO.getId() == null) {
			casoUso = elementoDAO.findElementoHasCasoUsoAsociado(Clave.ENT.toString()+"·"+entidadDTO.getId());
		}
		if (casoUso != null) {
			valido = false;
		}
		return valido;
	}

	public boolean isValidRN018(ReglaNegocioDTO modelDTO) {
		Boolean valido = true;
		CasoUso casoUso = null;
		if (modelDTO.getId() == null) {
			casoUso = elementoDAO.findElementoHasCasoUsoAsociado(Clave.RN+"·"+modelDTO.getId());
		}
		if (casoUso != null) {
			valido = false;
		}
		return valido;
	}

	public boolean isValidRN018(ActorDTO actorDTO) {
		Boolean valido = true;
		CasoUso casoUso = null;
		if (actorDTO.getId() == null) {
			casoUso = elementoDAO.findElementoHasCasoUsoAsociado(Clave.ACT.toString()+"·"+actorDTO.getId());
		}
		if (casoUso != null) {
			valido = false;
		}
		return valido;
	}

	public boolean isValidRN018(PantallaDTO model) {
		Boolean valido = true;
		CasoUso casoUso = null;
		if (model.getId() == null) {
			casoUso = elementoDAO.findElementoHasCasoUsoAsociado(Clave.IU.toString()+"·"+model.getId());
		}
		if (casoUso != null) {
			valido = false;
		}
		return valido;
	}
	
	public boolean isValidRN018(AccionDTO model) {
		Boolean valido = true;
		CasoUso casoUso = null;
		if (model.getId() == null) {
			casoUso = elementoDAO.findElementoHasCasoUsoAsociado(Constantes.TIPO_REFERENCIA_ACCION.toString()+"·"+model.getId());
		}
		if (casoUso != null) {
			valido = false;
		}
		return valido;
	}
	
	public boolean isValidRN018(CasoUsoDTO model) {
		Boolean valido = true;
		CasoUso casoUso = null;
		if (model.getId() == null) {
			casoUso = elementoDAO.findElementoHasCasoUsoAsociado(Clave.CU.toString()+"·"+model.getId());
		}
		if (casoUso != null) {
			valido = false;
		}
		return valido;
	}

}
