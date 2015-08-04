/**
 * 
 */
package edu.bionic.com.PersistenceLayer.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.bionic.com.PersistenceLayer.Category;
import edu.bionic.com.PersistenceLayer.DAO.CategoryDAO;
/**
 * @author andrew
 *
 */
@Service
public class CategoryService {
	@Autowired
	private CategoryDAO categoryDAO;
	@Transactional
	public int create(Category category) {
		return categoryDAO.create(category);
	}

	public Category find(int id) {
		return categoryDAO.find(id);
	}
	
	public Category find(String title) {
		return categoryDAO.find(title);
	}
	
	@Transactional
	public void update(Category category) {
		categoryDAO.update(category);		
	}
	@Transactional
	public void remove(Category category) {
		categoryDAO.remove(category);
	}
	
	public List<Category> returnCategoryList() {
		return categoryDAO.returnCategoryList();
	}

}
