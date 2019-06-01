package mx.tesseract.bs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.tesseract.dao.AccessDao;
import mx.tesseract.dao.AccountDao;
import mx.tesseract.dao.AddressDao;
import mx.tesseract.dao.ContactDao;
import mx.tesseract.dao.PersonContactDao;
import mx.tesseract.dao.UsersDao;
import mx.tesseract.entidad.Account;
import mx.tesseract.entidad.Contact;
import mx.tesseract.entidad.PersonContact;
import mx.tesseract.entidad.User;

@Service("usersBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class UsersBs {

	@Autowired
	private AccessDao accessDao;

	@Autowired
	private AccountDao accountDao;

	@Autowired
	private AddressDao addressDao;

	@Autowired
	private ContactDao contactDao;

	@Autowired
	private PersonContactDao personContactDao;

	@Autowired
	private UsersDao usersDao;

	public List<User> findAllUsers() {
		return usersDao.findAllUsers();
	}

	public User findById(Integer id) {
		return usersDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public User update(User user) throws LoginDuplicatedException {
		if(true) {
			throw new LoginDuplicatedException();
		}
		return usersDao.update(user);
	}

	@Transactional(rollbackFor = Exception.class)
	public void deleteUser(Integer id) {
		User user = findById(id);
		accessDao.delete(user.getAccess());
		for (Account acc : user.getAccounts()) {
			accountDao.delete(acc);
		}
		for (PersonContact pc : user.getPersonContacts()) {
			personContactDao.delete(pc);
		}
		for (Contact c : user.getContacts()) {
			contactDao.delete(c);
		}
		if (user.getAddress() != null) {
			addressDao.delete(user.getAddress());
		}
		usersDao.delete(user);
	}
}
