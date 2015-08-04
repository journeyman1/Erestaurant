/**
 * 
 */
package edu.bionic.com.PersistenceLayer.Services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import edu.bionic.com.PersistenceLayer.Orders;
import edu.bionic.com.PersistenceLayer.DAO.OrdersDAO;

/**
 * @author andrew
 *
 */
@Service
public class OrderService {
	@Autowired
	private OrdersDAO ordersDAO;
	@Transactional
	public int create(Orders orders) {
  		orders.setStatus(0);
		return ordersDAO.create(orders);
	}

	public Orders find(int id) {
		return ordersDAO.find(id);		
	}
	@Transactional
	public void update(Orders orders) {
  		orders.setStatusTime(Timestamp.from(Instant.now()));
		ordersDAO.update(orders);		
	}
	@Transactional
	public void remove(Orders orders) {
		ordersDAO.remove(orders);		
	}
	
	public List<Orders> returnOrderList() {
		return ordersDAO.returnOrderList();
	}
	
	public List<Orders> returnOrderList4Report(java.util.Date startDate, java.util.Date endDate) {
		java.sql.Timestamp begin;
		java.sql.Timestamp end;
		if (startDate == null) {
			begin = new Timestamp(0);
				} else {
				begin = new Timestamp(startDate.getTime());
		}
		if (endDate == null) {
			end = Timestamp.from(Instant.now());
				} else {
				end = new Timestamp(endDate.getTime());
		}
		return ordersDAO.returnOrderList4Report(begin, end);
	}

}
