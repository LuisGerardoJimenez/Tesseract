package mx.tesseract.entidad;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "id_user", referencedColumnName = "id_person")
public class User extends Person implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "tx_login")
	private String login;
	@Column(name = "tx_password")
	private String password;

	@OneToOne
	@JoinColumn(name = "id_user", referencedColumnName = "id_access", insertable = false, updatable = false)
	private Access access;

	@OneToMany
	@JoinColumn(name = "id_user", referencedColumnName = "id_user", insertable = false, updatable = false)
	private List<Account> accounts;

	@OneToMany
	@Where(clause = "")
	@JoinColumn(name = "id_user", referencedColumnName = "id_user", insertable = false, updatable = false)
	private List<Account> activeAccounts;

	@EmailValidator(fieldName = "model.login", type = ValidatorType.FIELD, message = "The email field is wrong")
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Access getAccess() {
		return access;
	}

	public void setAccess(Access access) {
		this.access = access;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public List<Account> getActiveAccounts() {
		return activeAccounts;
	}

	public void setActiveAccounts(List<Account> activeAccounts) {
		this.activeAccounts = activeAccounts;
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
