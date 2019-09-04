package mx.tesseract.editor.bs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.br.RN006;
import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.dto.ReglaNegocioDTO;
import mx.tesseract.editor.dao.ElementoDAO;
import mx.tesseract.editor.entidad.Atributo;
import mx.tesseract.editor.entidad.Operador;
import mx.tesseract.editor.entidad.ReglaNegocio;
import mx.tesseract.editor.entidad.TipoReglaNegocio;
import mx.tesseract.enums.EstadoElementoEnum.Estado;
import mx.tesseract.enums.ReferenciaEnum.Clave;
import mx.tesseract.util.Constantes;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;

@Service("reglaNegocioBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class ReglaNegocioBs {
	
	@Autowired
	private ElementoDAO elementoDAO;
	
	@Autowired
	private RN006 rn006;
	
	@Autowired
	private GenericoDAO genericoDAO;

	@Autowired
	private ElementoBs elementoBs;
	

	public List<ReglaNegocio> consultarReglaNegocioProyecto(Integer idProyecto) {
		List<ReglaNegocio> listReglaNegocio = elementoDAO.findAllByIdProyectoAndClave(ReglaNegocio.class, idProyecto, Clave.RN);
		return listReglaNegocio;
	}
	
	
	@Transactional(rollbackFor = Exception.class)
	public void registrarRN(ReglaNegocioDTO reglaNegocioDTO) {
		System.out.println("ya entre al registrar");
		if (rn006.isValidRN006(reglaNegocioDTO)) {
			Proyecto proyecto = genericoDAO.findById(Proyecto.class, reglaNegocioDTO.getIdProyecto());
			ReglaNegocio reglanegocio = new ReglaNegocio();
			String numero = elementoDAO.siguienteNumero(proyecto.getId(), Clave.RN);
			reglanegocio.setClave(Clave.RN.toString());
			reglanegocio.setNumero(numero);
			reglanegocio.setNombre(reglaNegocioDTO.getNombre());
			reglanegocio.setDescripcion(reglaNegocioDTO.getDescripcion());
			reglanegocio.setEstadoElemento(elementoBs.consultarEstadoElemento(Estado.EDICION));
			reglanegocio.setProyecto(proyecto);
			reglanegocio.setRedaccion(reglaNegocioDTO.getRedaccion());
			reglanegocio.setTiporeglanegocio(genericoDAO.findById(TipoReglaNegocio.class, reglaNegocioDTO.getIdTipoRN()));
			Integer idTipoRN = reglaNegocioDTO.getIdTipoRN();

			if (idTipoRN == Constantes.TIPO_DATOS_OBLIGATORIOS || idTipoRN == Constantes.TIPO_FORMATO_ARCHIVOS || idTipoRN == Constantes.TIPO_LONGITUD_CORRECTA
					|| idTipoRN == Constantes.TIPO_DATO_CORRECTO || idTipoRN == Constantes.TIPO_TAMANIO_ARCHIVOS || idTipoRN == Constantes.TIPO_VERIFICACION_CATALOGOS
					|| idTipoRN == Constantes.TIPO_OTRO 
					) {
				reglanegocio.setAtributo_comp1(null);
				reglanegocio.setOperador(null);
				reglanegocio.setAtributo_comp2(null);
				reglanegocio.setAtributo_unicidad(null);
				reglanegocio.setAtributo_exp_reg(null);
				reglanegocio.setExpresionRegular(null);
			} else if (idTipoRN == Constantes.TIPO_COMPARACION_ATRIBUTOS) {
				reglanegocio.setAtributo_comp1(genericoDAO.findById(Atributo.class, reglaNegocioDTO.getIdAtributo1()));
				reglanegocio.setAtributo_comp2(genericoDAO.findById(Atributo.class, reglaNegocioDTO.getIdAtributo2()));
				reglanegocio.setOperador(genericoDAO.findById(Operador.class, reglaNegocioDTO.getIdOperador()));
				reglanegocio.setAtributo_unicidad(null);
				reglanegocio.setAtributo_exp_reg(null);
				reglanegocio.setExpresionRegular(null);
			} else if (idTipoRN == Constantes.TIPO_UNICIDAD_PARAMETROS) {
				reglanegocio.setAtributo_unicidad(genericoDAO.findById(Atributo.class, reglaNegocioDTO.getIdAtributoUnicidad()));
				reglanegocio.setAtributo_comp1(null);
				reglanegocio.setOperador(null);
				reglanegocio.setAtributo_comp2(null);
				reglanegocio.setAtributo_exp_reg(null);
				reglanegocio.setExpresionRegular(null);
			} else if (idTipoRN == Constantes.TIPO_FORMATO_CORRECTO) {
				reglanegocio.setAtributo_exp_reg(genericoDAO.findById(Atributo.class, reglaNegocioDTO.getIdAtributoFormato()));
				reglanegocio.setExpresionRegular(reglaNegocioDTO.getExpresionRegular());
				reglanegocio.setAtributo_unicidad(null);
				reglanegocio.setAtributo_comp1(null);
				reglanegocio.setOperador(null);
				reglanegocio.setAtributo_comp2(null);
			}
			genericoDAO.save(reglanegocio);
		} else {
			throw new TESSERACTValidacionException("EL nombre de la Regla de Negocio ya existe.", "MSG7",
					new String[] { "La", "Regla de Negocio", reglaNegocioDTO.getNombre() }, "model.nombre");}
		
	}
	
	public ReglaNegocioDTO consultarRNById(Integer id) {
		ReglaNegocio reglanegocio = genericoDAO.findById(ReglaNegocio.class, id);
		ReglaNegocioDTO reglanegocioDTO = new ReglaNegocioDTO();
		int tipo = reglanegocio.getTiporeglanegocio().getId();

		if (reglanegocio != null) {
			reglanegocioDTO.setId(reglanegocio.getId());
			reglanegocioDTO.setClave(reglanegocio.getClave());
			reglanegocioDTO.setNumero(reglanegocio.getNumero());
			reglanegocioDTO.setNombre(reglanegocio.getNombre());
			reglanegocioDTO.setDescripcion(reglanegocio.getDescripcion());
			reglanegocioDTO.setRedaccion(reglanegocio.getRedaccion());
			reglanegocioDTO.setTiporeglanegocioNombre(reglanegocio.getTiporeglanegocio().getNombre());
//			if (reglanegocio.getTiporeglanegocio() != null) {
			reglanegocioDTO.setIdTipoRN(reglanegocio.getTiporeglanegocio().getId());				
//			}
			reglanegocioDTO.setIdProyecto(reglanegocio.getProyecto().getId());

			if (tipo == 2)
			{ 
				reglanegocioDTO.setAtributo1Nombre(reglanegocio.getAtributo_comp1().getNombre());
				reglanegocioDTO.setIdAtributo1(reglanegocio.getAtributo_comp1().getId());
				reglanegocioDTO.setAtributo2Nombre(reglanegocio.getAtributo_comp2().getNombre());
				reglanegocioDTO.setIdAtributo2(reglanegocio.getAtributo_comp2().getId());
				reglanegocioDTO.setOperadorSimbolo(reglanegocio.getOperador().getSimbolo());
				reglanegocioDTO.setIdOperador(reglanegocio.getOperador().getId());
			} else if (tipo == 9) {
				reglanegocioDTO.setExpresionRegular(reglanegocio.getExpresionRegular());
				reglanegocioDTO.setAtributoExpRegNombre(reglanegocio.getAtributo_exp_reg().getNombre());
			} else if (tipo == 3) {
			reglanegocioDTO.setAtributoUnicidadNombre(reglanegocio.getAtributo_unicidad().getNombre());
			} 
		} else {
			throw new TESSERACTException("No se puede consultar la Regla de Negocio.", "MSG12");
		}
		System.out.println("reglanegocioDTO: "+reglanegocioDTO);
		return reglanegocioDTO;
		}
	
		
		
//		ReglaNegocio reglanegocio = genericoDAO.findById(ReglaNegocio.class, id);
//		ReglaNegocioDTO reglanegocioDTO = new ReglaNegocioDTO();
//		if (reglanegocio != null) {
//			reglanegocioDTO.setId(reglanegocio.getId());
//			reglanegocioDTO.setClave(reglanegocio.getClave());
//			reglanegocioDTO.setNumero(reglanegocio.getNumero());
//			reglanegocioDTO.setNombre(reglanegocio.getNombre());
//			reglanegocioDTO.setDescripcion(reglanegocio.getDescripcion());
//			reglanegocioDTO.setRedaccion(reglanegocio.getRedaccion());
//			reglanegocioDTO.setIdTipoRN(reglanegocio.getTiporeglanegocio().getId());
////			reglanegocioDTO.setAtributoComp1(reglanegocio.getAtributo_comp1());
////			reglanegocioDTO.setAtributoComp2(reglanegocio.getAtributo_comp2());
////			reglanegocioDTO.setExpresionRegular(reglanegocio.getExpresionRegular());
////			reglanegocioDTO.setAtributoid_expReg(reglanegocio.getAtributo_exp_reg());;
//			reglanegocioDTO.setIdProyecto(reglanegocio.getProyecto().getId());
//		} else {
//			throw new TESSERACTException("No se puede consultar la Regla de Negocio.", "MSG12");
//		}
//		System.out.println("reglanegocioDTO: "+reglanegocioDTO);
//		return reglanegocioDTO;
	
	

	@Transactional(rollbackFor = Exception.class)
	public void modificarRN(ReglaNegocioDTO reglaNegocioDTO) {
		if (rn006.isValidRN006(reglaNegocioDTO)) {
			ReglaNegocio reglanegocio = genericoDAO.findById(ReglaNegocio.class, reglaNegocioDTO.getId());
			reglanegocio.setNombre(reglaNegocioDTO.getNombre());
			reglanegocio.setDescripcion(reglaNegocioDTO.getDescripcion());
			reglanegocio.setRedaccion(reglaNegocioDTO.getRedaccion());
			reglanegocio.setTiporeglanegocio(genericoDAO.findById(TipoReglaNegocio.class, reglaNegocioDTO.getIdTipoRN()));

			Integer idTipoRN = reglaNegocioDTO.getIdTipoRN();

			if (idTipoRN == Constantes.TIPO_DATOS_OBLIGATORIOS || idTipoRN == Constantes.TIPO_FORMATO_ARCHIVOS || idTipoRN == Constantes.TIPO_LONGITUD_CORRECTA
					|| idTipoRN == Constantes.TIPO_DATO_CORRECTO || idTipoRN == Constantes.TIPO_TAMANIO_ARCHIVOS || idTipoRN == Constantes.TIPO_VERIFICACION_CATALOGOS
					|| idTipoRN == Constantes.TIPO_OTRO 
					) {
				reglanegocio.setAtributo_comp1(null);
				reglanegocio.setOperador(null);
				reglanegocio.setAtributo_comp2(null);
				reglanegocio.setAtributo_unicidad(null);
				reglanegocio.setAtributo_exp_reg(null);
				reglanegocio.setExpresionRegular(null);
			} else if (idTipoRN == Constantes.TIPO_COMPARACION_ATRIBUTOS) {
				reglanegocio.setAtributo_comp1(genericoDAO.findById(Atributo.class, reglaNegocioDTO.getIdAtributo1()));
				reglanegocio.setAtributo_comp2(genericoDAO.findById(Atributo.class, reglaNegocioDTO.getIdAtributo2()));
				reglanegocio.setOperador(genericoDAO.findById(Operador.class, reglaNegocioDTO.getIdOperador()));
				reglanegocio.setAtributo_unicidad(null);
				reglanegocio.setAtributo_exp_reg(null);
				reglanegocio.setExpresionRegular(null);
			} else if (idTipoRN == Constantes.TIPO_UNICIDAD_PARAMETROS) {
				reglanegocio.setAtributo_unicidad(genericoDAO.findById(Atributo.class, reglaNegocioDTO.getIdAtributoUnicidad()));
				reglanegocio.setAtributo_comp1(null);
				reglanegocio.setOperador(null);
				reglanegocio.setAtributo_comp2(null);
				reglanegocio.setAtributo_exp_reg(null);
				reglanegocio.setExpresionRegular(null);
			} else if (idTipoRN == Constantes.TIPO_FORMATO_CORRECTO) {
				reglanegocio.setAtributo_exp_reg(genericoDAO.findById(Atributo.class, reglaNegocioDTO.getIdAtributoFormato()));
				reglanegocio.setExpresionRegular(reglaNegocioDTO.getExpresionRegular());
				reglanegocio.setAtributo_unicidad(null);
				reglanegocio.setAtributo_comp1(null);
				reglanegocio.setOperador(null);
				reglanegocio.setAtributo_comp2(null);
			}
			genericoDAO.update(reglanegocio);
		} else {
			throw new TESSERACTValidacionException("EL nombre de la Reglande Negocio ya existe.", "MSG7",
					new String[] { "La", "Regla de Negocio", reglaNegocioDTO.getNombre() }, "model.nombre");
		}
	}

}
