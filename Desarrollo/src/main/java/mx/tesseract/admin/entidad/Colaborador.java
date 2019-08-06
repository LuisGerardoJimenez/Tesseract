package mx.tesseract.admin.entidad;

/*
 * Luis Gerardo Jiménez
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

import mx.tesseract.util.Constantes;
import mx.tesseract.util.GenericInterface;

@NamedNativeQueries({
		@NamedNativeQuery(name = "Colaborador.findAllWithoutAdmin", query = "SELECT c.* FROM colaborador c WHERE c.administrador != ?", resultClass = Colaborador.class),
		@NamedNativeQuery(name = "Colaborador.findColaboradorByCorreo", query = "SELECT c.* FROM colaborador c WHERE c.correoelectronico = ?", resultClass = Colaborador.class),
		@NamedNativeQuery(name = "Colaborador.findColaboradorByCorreoAndCurp", query = "SELECT c.* FROM colaborador c WHERE c.correoelectronico = ? AND c.curp != ?", resultClass = Colaborador.class),
		@NamedNativeQuery(name = "Colaborador.findColaboradorByCURP", query = "SELECT c.* FROM colaborador c WHERE c.curp = ?", resultClass = Colaborador.class),
		@NamedNativeQuery(name = "Colaborador.hasProyectos", query = "SELECT * FROM colaborador c INNER JOIN colaborador_proyecto cp ON cp.colaboradorcurp=c.curp WHERE curp = ?", resultClass = Colaborador.class)
		})
@Entity
@Table(name = "colaborador")
public class Colaborador implements Serializable, GenericInterface {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "CURP")
	private String curp;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "apellidoPaterno")
	private String apellidoPaterno;

	@Column(name = "apellidoMaterno")
	private String apellidoMaterno;

	@Column(name = "correoElectronico")
	private String correoElectronico;

	@Column(name = "contrasenia")
	private String contrasenia;

	@Column(name = "administrador")
	private boolean administrador;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "colaborador", orphanRemoval = true)
	private List<ColaboradorProyecto> colaborador_proyectos = new ArrayList<ColaboradorProyecto>();

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "colaborador")
	private List<Telefono> telefonos = new ArrayList<Telefono>();

	public Colaborador() {
	}

	public Colaborador(String curp, String nombre, String apellidoPaterno, String apellidoMaterno,
			String correoElectronico, String contrasenia, boolean administrador) {
		this.curp = curp;
		this.nombre = nombre;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.correoElectronico = correoElectronico;
		this.contrasenia = contrasenia;
		this.administrador = administrador;
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit = true)
	@StringLengthFieldValidator(message = "%{getText('MSG23')}", trim = true, minLength = "18", maxLength = "18", shortCircuit = true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG24')}", regex = Constantes.REGEX_CURP, shortCircuit = true)
	public String getCurp() {
		return this.curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit = true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'30', 'caracteres'})}", trim = true, maxLength = "30", shortCircuit = true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5')}", regex = Constantes.REGEX_CAMPO_ALFABETICO, shortCircuit = true)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit = true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'30', 'caracteres'})}", trim = true, maxLength = "30", shortCircuit = true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5')}", regex = Constantes.REGEX_CAMPO_ALFABETICO_SIN_ESPACIOS, shortCircuit = true)
	public String getApellidoPaterno() {
		return this.apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	@StringLengthFieldValidator(message = "%{getText('MSG6',{'30', 'caracteres'})}", trim = true, maxLength = "30", shortCircuit = true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5')}", regex = Constantes.REGEX_CAMPO_ALFABETICO_SIN_ESPACIOS, shortCircuit = true)
	public String getApellidoMaterno() {
		return this.apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit = true)
	@StringLengthFieldValidator(message = "%{getText('MSG6',{'30', 'caracteres'})}", trim = true, maxLength = "30", shortCircuit = true)
	@EmailValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5')}", shortCircuit = true)
	public String getCorreoElectronico() {
		return this.correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	@RequiredStringValidator(type = ValidatorType.FIELD, message = "%{getText('MSG4')}", shortCircuit = true)
	@StringLengthFieldValidator(message = "%{getText('MSG25',{'8', '20', 'caracteres'})}", trim = true, minLength = "8", maxLength = "20", shortCircuit = true)
	@RegexFieldValidator(type = ValidatorType.FIELD, message = "%{getText('MSG5')}", regex = Constantes.REGEX_CONTRASENIA, shortCircuit = true)
	public String getContrasenia() {
		return this.contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public List<ColaboradorProyecto> getColaborador_proyectos() {
		return colaborador_proyectos;
	}

	public void setColaborador_proyectos(List<ColaboradorProyecto> colaborador_proyectos) {
		this.colaborador_proyectos = colaborador_proyectos;
	}

	public List<Telefono> getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(List<Telefono> telefonos) {
		this.telefonos = telefonos;
	}

	public boolean isAdministrador() {
		return administrador;
	}

	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}

}
