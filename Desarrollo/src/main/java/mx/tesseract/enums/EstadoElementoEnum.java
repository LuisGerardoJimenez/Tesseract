package mx.tesseract.enums;

import mx.tesseract.util.Constantes;

public class EstadoElementoEnum {
	
	public enum Estado {
	    EDICION, REVISION, PENDIENTECORRECCION, PORLIBERAR, LIBERADO, PRECONFIGURADO, CONFIGURADO
	}
	
	public static Integer getIdEstado(Estado estado) {
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
		return idEstado;
	}

}
