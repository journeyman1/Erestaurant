package edu.bionic.com.PersistenceLayer.DAO;

import java.util.List;

import javax.persistence.*;

import org.springframework.stereotype.Repository;

import edu.bionic.com.PersistenceLayer.Staff;
@Repository
public class StaffDAO implements CRUD<Staff> {
	@PersistenceContext
	private EntityManager em;
  	
  	public StaffDAO(){}
  	
  	public int create(Staff staff) {
  		em.persist(staff);
  		return staff.getId();  		
  	}
  	
  	public Staff find(String email) {
  		if (email == null) return null;
		String q = "SELECT st FROM Staff st WHERE st.email='"+email.trim()+"'";
		TypedQuery<Staff> query = em.createQuery(q, Staff.class);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
  	}
  	
  	public Staff find(int id) {
		return em.find(Staff.class, id);
  	}
  	
  	public void update(Staff staff) {
  		em.merge(staff);
  	}
  	
  	public void remove(Staff staff) {
  		em.remove(staff);
  	}
  	
  	public List<Staff> returnStaffList() {
  		String q = "SELECT st FROM Staff st";
  		TypedQuery<Staff> query = em.createQuery(q, Staff.class);
  		return query.getResultList();
  	}
  	
}
