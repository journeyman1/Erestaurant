package edu.bionic.com.PersistenceLayer.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import edu.bionic.com.PersistenceLayer.OrderContent;
import edu.bionic.com.PersistenceLayer.DAO.OrderContentDAO;

/**
 * @author andrew
 *
 */
@Service
public class OrderContentService {
	@Autowired
	private OrderContentDAO orderContentDAO;
	@Transactional
	public int create(OrderContent orderContent) {
		return orderContentDAO.create(orderContent);
	}

	public OrderContent find(int id) {
		return orderContentDAO.find(id);		
	}
	@Transactional
	public void update(OrderContent orderContent) {
		orderContentDAO.update(orderContent);		
	}
	@Transactional
	public void remove(OrderContent orderContent) {
		orderContentDAO.remove(orderContent);		
	}
	
	public List<OrderContent> returnKitchenList() {
		return orderContentDAO.returnKitchenList();
	}
		
}
