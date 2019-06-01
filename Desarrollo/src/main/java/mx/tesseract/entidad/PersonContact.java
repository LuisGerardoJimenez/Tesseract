package mx.tesseract.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "person_contact")
public class PersonContact {
	@Id
	@SequenceGenerator(name = "person_contact_id_person_contact_seq", sequenceName = "person_contact_id_person_contact_seq", allocationSize = 1)
	@GeneratedValue(generator = "person_contact_id_person_contact_seq", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_person_contact")
	private Integer id;
	@Column(name = "id_person")
	private Integer idPerson;
	@Column(name = "id_type")
	private Integer idType;
	@Column(name = "tx_contact")
	private String contact;

	@ManyToOne
	@JoinColumn(name = "id_type", referencedColumnName = "id_type", insertable = false, updatable = false)
	private TypeContact type;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdPerson() {
		return idPerson;
	}

	public void setIdPerson(Integer idPerson) {
		this.idPerson = idPerson;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
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
		PersonContact other = (PersonContact) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
