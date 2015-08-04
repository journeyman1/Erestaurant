/**
 * 
 */
package edu.bionic.com.PersistenceLayer.Services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.bionic.com.PersistenceLayer.Staff;
import edu.bionic.com.PersistenceLayer.DAO.StaffDAO;

/**
 * @author andrew
 *
 */
@Service
public class StaffService {	
	@Autowired
	private StaffDAO staffDAO;
	@Transactional
	public int create(Staff staff) {
		if (find(staff.getEmail()) != null) {
			return -1;
		} else return staffDAO.create(staff);
	}

	public Staff find(int id) {
		return staffDAO.find(id);		
	}
	
	public Staff find(String email) {
			return staffDAO.find(email);
	}
	@Transactional
	public void update(Staff staff) {
		staffDAO.update(staff);		
	}
	@Transactional
	public void remove(Staff staff) {
		staffDAO.remove(staff);		
	}
	
	public List<Staff> returnStaffList() {
		return staffDAO.returnStaffList();
	}

}
