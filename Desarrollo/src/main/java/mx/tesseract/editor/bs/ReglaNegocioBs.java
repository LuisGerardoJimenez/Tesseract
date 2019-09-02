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
import mx.tesseract.dto.ActorDTO;
import mx.tesseract.dto.ReglaNegocioDTO;
import mx.tesseract.editor.dao.ElementoDAO;
import mx.tesseract.editor.entidad.Actor;
import mx.tesseract.editor.entidad.Atributo;
import mx.tesseract.editor.entidad.Cardinalidad;
import mx.tesseract.editor.entidad.Operador;
import mx.tesseract.editor.entidad.ReglaNegocio;
import mx.tesseract.editor.entidad.TipoComparacion;
import mx.tesseract.editor.entidad.TipoReglaNegocio;
import mx.tesseract.enums.EstadoElementoEnum.Estado;
import mx.tesseract.enums.ReferenciaEnum.Clave;
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
		/*
		if (rn006.isValidRN006(reglaNegocioDTO)) {
			ReglaNegocio reglanegocio = new ReglaNegocio();
			Proyecto proyecto = genericoDAO.findById(Proyecto.class, reglaNegocioDTO.getIdProyecto());
			String numero = elementoDAO.siguienteNumero(proyecto.getId(), Clave.RN);
			reglanegocio.setClave(Clave.RN.toString());
			reglanegocio.setNumero(numero);
			reglanegocio.setNombre(reglaNegocioDTO.getNombre());
			reglanegocio.setDescripcion(reglaNegocioDTO.getDescripcion());
			reglanegocio.setEstadoElemento(elementoBs.consultarEstadoElemento(Estado.EDICION));
			reglanegocio.setProyecto(proyecto);
			reglanegocio.setRedaccion(reglaNegocioDTO.getRedaccion());
			reglanegocio.setTiporeglanegocio(genericoDAO.findById(TipoReglaNegocio.class, reglaNegocioDTO.getTipoReglaNegocioid()));
			reglanegocio.setAtributo_unicidad(genericoDAO.findById(Atributo.class, reglaNegocioDTO.getAtributoid_unicidad()));
			reglanegocio.setAtributo_fechaI(genericoDAO.findById(Atributo.class, reglaNegocioDTO.getAtributoid_fechaI()));
			reglanegocio.setAtributo_fechaT(genericoDAO.findById(Atributo.class, reglaNegocioDTO.getAtributoid_fechaT()));
			reglanegocio.setTipocomparacion(genericoDAO.findById(TipoComparacion.class, reglaNegocioDTO.getTipoComparacionid()));
			reglanegocio.setAtributo_comp1(genericoDAO.findById(Atributo.class, reglaNegocioDTO.getAtributoid_comp1()));
			reglanegocio.setAtributo_comp2(genericoDAO.findById(Atributo.class, reglaNegocioDTO.getAtributoid_comp2()));
			reglanegocio.setOperador(genericoDAO.findById(Operador.class, reglaNegocioDTO.getOperadorid()));
			reglanegocio.setAtributo_exp_reg(genericoDAO.findById(Atributo.class, reglaNegocioDTO.getAtributoid_expReg()));
			reglanegocio.setExpresionRegular(reglaNegocioDTO.getExpresionRegular());
			genericoDAO.save(reglanegocio);
		} else {
			throw new TESSERACTValidacionException("EL nombre de la Regla de Negocio ya existe.", "MSG7",
					new String[] { "La", "Regla de Negocio", reglaNegocioDTO.getNombre() }, "model.nombre");
		}*/
	}
	
	public ReglaNegocioDTO consultarRNById(Integer id) {
		ReglaNegocio reglanegocio = genericoDAO.findById(ReglaNegocio.class, id);
		ReglaNegocioDTO reglanegocioDTO = new ReglaNegocioDTO();
		if (reglanegocio != null) {
			reglanegocioDTO.setId(reglanegocio.getId());
			reglanegocioDTO.setClave(reglanegocio.getClave());
			reglanegocioDTO.setNumero(reglanegocio.getNumero());
			reglanegocioDTO.setNombre(reglanegocio.getNombre());
			reglanegocioDTO.setDescripcion(reglanegocio.getDescripcion());
			reglanegocioDTO.setRedaccion(reglanegocio.getRedaccion());
			reglanegocioDTO.setIdTipoRN(reglanegocio.getTiporeglanegocio().getId());
//			reglanegocioDTO.setAtributoComp1(reglanegocio.getAtributo_comp1());
//			reglanegocioDTO.setAtributoComp2(reglanegocio.getAtributo_comp2());
//			reglanegocioDTO.setExpresionRegular(reglanegocio.getExpresionRegular());
//			reglanegocioDTO.setAtributoid_expReg(reglanegocio.getAtributo_exp_reg());;
			reglanegocioDTO.setIdProyecto(reglanegocio.getProyecto().getId());
		} else {
			throw new TESSERACTException("No se puede consultar la Regla de Negocio.", "MSG12");
		}
		System.out.println("reglanegocioDTO: "+reglanegocioDTO);
		return reglanegocioDTO;
	}
	

	@Transactional(rollbackFor = Exception.class)
	public void modificarRN(ReglaNegocioDTO reglanegocioDTO) {
		if (rn006.isValidRN006(reglanegocioDTO)) {
			ReglaNegocio reglanegocio = genericoDAO.findById(ReglaNegocio.class, reglanegocioDTO.getId());
			reglanegocio.setNombre(reglanegocioDTO.getNombre());
			reglanegocio.setDescripcion(reglanegocioDTO.getDescripcion());
			//reglanegocio.setCardinalidad(genericoDAO.findById(Cardinalidad.class, actorDTO.getCardinalidadId()));
			//reglanegocio.setOtraCardinalidad(actorDTO.getOtraCardinalidad());
			genericoDAO.update(reglanegocio);
		} else {
			throw new TESSERACTValidacionException("EL nombre de la Reglande Negocio ya existe.", "MSG7",
					new String[] { "La", "Regla de Negocio", reglanegocioDTO.getNombre() }, "model.nombre");
		}
	}

}
