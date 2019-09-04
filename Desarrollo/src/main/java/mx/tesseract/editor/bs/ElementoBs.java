package mx.tesseract.editor.bs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.editor.entidad.Elemento;
import mx.tesseract.editor.entidad.EstadoElemento;
import mx.tesseract.enums.AnalisisEnum.CU_Mensajes;
import mx.tesseract.enums.EstadoElementoEnum.Estado;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.TESSERACTException;

@Service("elementoBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class ElementoBs {
	
	@Autowired
	private GenericoDAO genericoDAO;
	
	public EstadoElemento consultarEstadoElemento(Estado estado) {
		Integer idEstado;
		switch(estado) {
		case EDICION:
			idEstado = Constantes.ESTADO_ELEMENTO_EDICION;
			break;
		case LIBERADO:
			idEstado = Constantes.ESTADO_ELEMENTO_LIBERADO;
			break;
		case PENDIENTECORRECCION:
			idEstado = Constantes.ESTADO_ELEMENTO_PENDIENTECORRECCION;
			break;
		case PORLIBERAR:
			idEstado = Constantes.ESTADO_ELEMENTO_PORLIBERAR;
			break;
		case REVISION:
			idEstado = Constantes.ESTADO_ELEMENTO_REVISION;
			break;
		case PRECONFIGURADO:
			idEstado = Constantes.ESTADO_ELEMENTO_PRECONFIGURADO;
			break;
		default:
			idEstado = Constantes.ESTADO_ELEMENTO_CONFIGURADO;
			break;
		}
		EstadoElemento estadoElemento = genericoDAO.findById(EstadoElemento.class, idEstado);
		if(estadoElemento == null) {
			throw new TESSERACTException("No se puede consultar el estado del elemento", "MSG12");
		}
		return estadoElemento;
	}
	
	public void verificarEstado(Elemento elemento,
			CU_Mensajes mensajeAnalisis) {
		switch(mensajeAnalisis) {
		case MODIFICARMENSAJE9_2:
			if (elemento.getEstadoElemento().getId() != Constantes.ESTADO_ELEMENTO_EDICION) {
				throw new TESSERACTException("El estado de la regla de negocio es inválido.", "MSG13");
			}
			break;
		case ELIMINARMENSAJE9_3:
			if (elemento.getEstadoElemento().getId() != Constantes.ESTADO_ELEMENTO_EDICION) {
				throw new TESSERACTException("El estado de la regla de negocio es inválido.", "MSG13");
			}
			break;
		default:
			break;
		}
		
	}
}

