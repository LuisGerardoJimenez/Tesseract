package mx.tesseract.entidad;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {
	@Id
	@SequenceGenerator(name = "person_id_person_seq", sequenceName = "person_id_person_seq", allocationSize = 1)
	@GeneratedValue(generator = "person_id_person_seq", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_person")
	protected Integer id;
	@Column(name = "tx_first_name")
	protected String name;
	@Column(name = "tx_last_name_a")
	protected String lastName;
	@Column(name = "tx_last_name_b")
	protected String secondLastName;
	@Column(name = "tx_curp")
	protected String curp;
	@Column(name = "fh_birth")
	protected Date birthDay;
	@Transient
	protected String fullName;

	@OneToMany
	@JoinColumn(name = "id_person", referencedColumnName = "id_person", insertable = false, updatable = false)
	private List<PersonContact> personContacts;

	@OneToMany
	@JoinColumn(name = "id_person", referencedColumnName = "id_person", insertable = false, updatable = false)
	private List<Contact> contacts;

	@ManyToMany
	@JoinTable(name = "contact", joinColumns = {
			@JoinColumn(name = "id_person", referencedColumnName = "id_person", insertable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "id_contact", referencedColumnName = "id_person", insertable = false, updatable = false) })
	private List<Person> persons;

	@OneToOne(optional = true)
	@JoinColumn(name = "id_person", referencedColumnName = "id_person", insertable = false, updatable = false)
	private Address address;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Validations(requiredStrings = {@RequiredStringValidator(fieldName = "model.name", type = ValidatorType.FIELD, key = "MSG2")}, stringLengthFields = {@StringLengthFieldValidator(fieldName = "model.name", type = ValidatorType.FIELD, message = "The length of name field is wrong", minLength = "5", maxLength = "10")})
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSecondLastName() {
		return secondLastName;
	}

	public void setSecondLastName(String secondLastName) {
		this.secondLastName = secondLastName;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public List<PersonContact> getPersonContacts() {
		return personContacts;
	}

	public void setPersonContacts(List<PersonContact> personContacts) {
		this.personContacts = personContacts;
	}

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
