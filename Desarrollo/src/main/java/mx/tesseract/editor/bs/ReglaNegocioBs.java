package mx.tesseract.editor.bs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.tesseract.admin.entidad.Proyecto;
import mx.tesseract.br.RN006;
import mx.tesseract.br.RN018;
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
	
	@Autowired
	private RN018 rn018;
	
	public List<ReglaNegocio> consultarReglaNegocioProyecto(Integer idProyecto) {
		return elementoDAO.findAllByIdProyectoAndClave(ReglaNegocio.class, idProyecto, Clave.RN);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void registrarRN(ReglaNegocioDTO reglaNegocioDTO) {
		if (rn006.isValidRN006(reglaNegocioDTO)) {
			Proyecto proyecto = genericoDAO.findById(Proyecto.class, reglaNegocioDTO.getIdProyecto());
			ReglaNegocio reglanegocio = new ReglaNegocio();
			reglanegocio.setClave(Clave.RN.toString());
			reglanegocio.setNumero(reglaNegocioDTO.getNumero());
			reglanegocio.setNombre(reglaNegocioDTO.getNombre());
			reglanegocio.setDescripcion(reglaNegocioDTO.getDescripcion().trim());
			reglanegocio.setEstadoElemento(elementoBs.consultarEstadoElemento(Estado.EDICION));
			reglanegocio.setProyecto(proyecto);
			reglanegocio.setRedaccion(reglaNegocioDTO.getRedaccion().trim());
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
		int idTipoRN = reglanegocio.getTiporeglanegocio().getId();

		reglanegocioDTO.setId(reglanegocio.getId());
		reglanegocioDTO.setClave(reglanegocio.getClave());
		reglanegocioDTO.setNumero(reglanegocio.getNumero());
		reglanegocioDTO.setNombre(reglanegocio.getNombre());
		reglanegocioDTO.setDescripcion(reglanegocio.getDescripcion());
		reglanegocioDTO.setRedaccion(reglanegocio.getRedaccion());
		reglanegocioDTO.setTiporeglanegocioNombre(reglanegocio.getTiporeglanegocio().getNombre());
		reglanegocioDTO.setIdTipoRN(reglanegocio.getTiporeglanegocio().getId());				
		reglanegocioDTO.setIdProyecto(reglanegocio.getProyecto().getId());

		if (idTipoRN == Constantes.TIPO_COMPARACION_ATRIBUTOS)
		{ 
			reglanegocioDTO.setAtributo1Nombre(reglanegocio.getAtributo_comp1().getNombre());
			reglanegocioDTO.setIdAtributo1(reglanegocio.getAtributo_comp1().getId());
			reglanegocioDTO.setIdEntidad1(reglanegocio.getAtributo_comp1().getEntidad().getId());
			reglanegocioDTO.setAtributo2Nombre(reglanegocio.getAtributo_comp2().getNombre());
			reglanegocioDTO.setIdAtributo2(reglanegocio.getAtributo_comp2().getId());
			reglanegocioDTO.setIdEntidad2(reglanegocio.getAtributo_comp2().getEntidad().getId());
			reglanegocioDTO.setOperadorSimbolo(reglanegocio.getOperador().getSimbolo());
			reglanegocioDTO.setIdOperador(reglanegocio.getOperador().getId());
		} else if (idTipoRN == Constantes.TIPO_FORMATO_CORRECTO) {
			reglanegocioDTO.setExpresionRegular(reglanegocio.getExpresionRegular());
			reglanegocioDTO.setAtributoExpRegNombre(reglanegocio.getAtributo_exp_reg().getNombre());
		} else if (idTipoRN == Constantes.TIPO_UNICIDAD_PARAMETROS) {
		reglanegocioDTO.setAtributoUnicidadNombre(reglanegocio.getAtributo_unicidad().getNombre());
		}
		return reglanegocioDTO;
	}
	@Transactional(rollbackFor = Exception.class)
	public void modificarRN(ReglaNegocioDTO reglaNegocioDTO) {
		if (rn006.isValidRN006(reglaNegocioDTO)) {
			ReglaNegocio reglanegocio = genericoDAO.findById(ReglaNegocio.class, reglaNegocioDTO.getId());
			reglanegocio.setNumero(reglaNegocioDTO.getNumero());
			reglanegocio.setNombre(reglaNegocioDTO.getNombre());
			reglanegocio.setDescripcion(reglaNegocioDTO.getDescripcion().trim());
			reglanegocio.setRedaccion(reglaNegocioDTO.getRedaccion().trim());
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

	@Transactional(rollbackFor = Exception.class)
	public void eliminarRN(ReglaNegocioDTO modelDTO) throws TESSERACTException{
		if (rn018.isValidRN018(modelDTO)) {
			ReglaNegocio reglaNegocio = genericoDAO.findById(ReglaNegocio.class, modelDTO.getId());
			genericoDAO.delete(reglaNegocio);
		} else {
			throw new TESSERACTException("Este elemento no se puede eliminar debido a que esta siendo referenciado.",
					"MSG13");
		}
	}

}
