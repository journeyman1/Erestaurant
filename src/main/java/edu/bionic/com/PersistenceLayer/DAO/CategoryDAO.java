package edu.bionic.com.PersistenceLayer.DAO;

import java.util.List;

import javax.persistence.*;

import org.springframework.stereotype.Repository;

import edu.bionic.com.PersistenceLayer.Category;

@Repository
public class CategoryDAO implements CRUD<Category>{
	@PersistenceContext
	private EntityManager em;
		
	public CategoryDAO(){}

	public int create(Category category) {
		em.persist(category);
		return category.getId();
	}

	public Category find(int id) {
		return em.find(Category.class, id);
	}
	
	public Category find(String title) {
		String q = "SELECT c FROM Category c WHERE c.title='"+title.trim() + "'";
		TypedQuery<Category> query = em.createQuery(q, Category.class);
		try {
		return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public void update(Category category) {
		em.merge(category);
	}

	public void remove(Category category) {
		em.remove(category);
	}
	
	public List<Category> returnCategoryList() {
		String query = "SELECT c FROM Category c";
		TypedQuery<Category> q = em.createQuery(query, Category.class);
		return q.getResultList();
	}
	
	
}
