package mx.tesseract.editor.bs;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.tesseract.br.RN006;
import mx.tesseract.br.RN018;
import mx.tesseract.dao.GenericoDAO;
import mx.tesseract.dto.ExtensionDTO;
import mx.tesseract.dto.TrayectoriaDTO;
import mx.tesseract.editor.entidad.CasoUso;
import mx.tesseract.editor.entidad.Extension;
import mx.tesseract.editor.entidad.Trayectoria;
import mx.tesseract.enums.AnalisisEnum.CU_CasosUso;
import mx.tesseract.enums.ReferenciaEnum.TipoSeccion;
import mx.tesseract.util.TESSERACTException;
import mx.tesseract.util.TESSERACTValidacionException;

@Service("extensionBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class ExtensionBs {
	
	@Autowired
	private GenericoDAO genericoDAO;
	
	@Autowired
	private CasoUsoBs casoUsoBs;
	
	@Autowired
	private TokenBs tokenBs;
	
	@Autowired
	private ElementoBs elementoBs;
	
	@Autowired
	private RN006 rn006;
	
	@Autowired
	private RN018 rn018;
	
	@Transactional(rollbackFor = Exception.class)
	public void registrarExtension(ExtensionDTO extensionDTO) throws Exception {
		if (rn006.isValidRN006(extensionDTO, extensionDTO.getCasoUsoOrigen().getId())) {
			Extension extension = new Extension();
			extension.setCasoUsoDestino(extensionDTO.getCasoUsoDestino());
			extension.setCasoUsoOrigen(extensionDTO.getCasoUsoOrigen());
			extension.setCausa(extensionDTO.getCausa());
			extension.setRegion(extensionDTO.getRegion());
			genericoDAO.save(extension);
		} else {
			throw new TESSERACTException(
					"La Extension ya existe.", "MSG7",
					new String[] { "El", "Punto de extensión", "que intenta registrar" });
		}
	}

	/*public static Verbo consultaVerbo(String nombre) {
		
		Verbo verbo = null;
		try {
			verbo = new VerboDAO().consultarVerbo(nombre);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if(verbo == null) {
			throw new PRISMAException("No se puede consultar el verbo por nombre.", "MSG16", new String[] { "El",
					"verbo"});
		}
		return verbo;
	}*/

	@SuppressWarnings("unused")
	public ExtensionDTO consultarExtension(int idSel) {
		Extension extension = genericoDAO.findById(Extension.class, idSel);
		ExtensionDTO extensionDTO = new ExtensionDTO();
		extensionDTO.setCasoUsoDestino(extension.getCasoUsoDestino());
		extensionDTO.setCasoUsoOrigen(extension.getCasoUsoOrigen());
		extensionDTO.setCausa(extension.getCausa());
		extensionDTO.setId(extension.getId());
		extensionDTO.setRegion(extension.getRegion());
		if (extension == null) {
			throw new TESSERACTException(
					"No se puede consultar el punto de extensión por el id.", "MSG16",
					new String[] { "El", "Punto de extensión" });
		}
		return extensionDTO;
	}

	public List<ExtensionDTO> consultarExtensionesByIdCasoUso(Integer idCasoUso) {
		List<ExtensionDTO> lista = new ArrayList<ExtensionDTO>();
		CasoUso casoUso = casoUsoBs.consultarCasoUso(idCasoUso);
		List<Extension> listPtosExtension = new ArrayList<Extension>();
		String regionDecodificada = "";
		for (Extension extension : casoUso.getExtiende()) {
			regionDecodificada = tokenBs.decodificarCadenasToken(extension.getRegion());
			extension.setRegion(regionDecodificada);
			listPtosExtension.add(extension);
		}
		
		/*for (Revision rev : casoUso.getRevisiones()) {
			if (!rev.isRevisado()
					&& rev.getSeccion()
							.getNombre()
							.equals(TipoSeccionEnum
									.getNombre(TipoSeccionENUM.PUNTOSEXTENSION))) {
				this.observaciones = rev.getObservaciones();
			}
		}*/
		for (Extension extension : casoUso.getExtiende()) {
			ExtensionDTO extensionDTO = new ExtensionDTO();
			extensionDTO.setCasoUsoDestino(extension.getCasoUsoDestino());
			extensionDTO.setCasoUsoOrigen(extension.getCasoUsoOrigen());
			extensionDTO.setCausa(extension.getCausa());
			extensionDTO.setId(extension.getId());
			extensionDTO.setRegion(extension.getRegion());
			lista.add(extensionDTO);
		}
		return lista;
	}
	
	public void preAlmacenarObjetosToken(ExtensionDTO extensionDTO) {
		tokenBs.almacenarObjetosToken(tokenBs.convertirToken_Objeto(
				extensionDTO.getRegion(), extensionDTO.getCasoUsoOrigen().getProyecto() , extensionDTO.getCasoUsoOrigen().getModulo().getId()), extensionDTO.getCasoUsoOrigen(),
				TipoSeccion.EXTENSIONES);
		extensionDTO.setRegion(tokenBs.codificarCadenaToken(extensionDTO.getRegion(), extensionDTO.getCasoUsoOrigen().getProyecto(), extensionDTO.getCasoUsoOrigen().getModulo().getId()));
		
	}

	@Transactional(rollbackFor = Exception.class)
	public void eliminarExtension(ExtensionDTO model) {
		if (rn018.isValidRN018(model)) {
			Extension entidad = genericoDAO.findById(Extension.class, model.getId());
			genericoDAO.delete(entidad);
		} else {
			throw new TESSERACTException("Este elemento no se puede eliminar debido a que esta siendo referenciado.",
					"MSG13");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void modificarExtension(ExtensionDTO model) {
		if (rn018.isValidRN018(model)) {
			Extension entidad = genericoDAO.findById(Extension.class, model.getId());
			entidad.setCasoUsoDestino(model.getCasoUsoDestino());
			entidad.setCasoUsoOrigen(model.getCasoUsoOrigen());
			entidad.setCausa(model.getCausa());
			entidad.setRegion(model.getRegion());
			genericoDAO.update(entidad);
		} else {
			throw new TESSERACTException("Este elemento no se puede eliminar debido a que esta siendo referenciado.",
					"MSG13");
		}
	}
	
}
