package main;

import java.io.Serializable;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import edu.bionic.com.PersistenceLayer.Customer;
import edu.bionic.com.PersistenceLayer.Staff;
import edu.bionic.com.PersistenceLayer.Services.CustomerService;
import edu.bionic.com.PersistenceLayer.Services.Encoder;
import edu.bionic.com.PersistenceLayer.Services.StaffService;

@Named
@Scope("session")
public class UserBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject	private CustomerService customerService;
	@Inject	private StaffService staffService;
	@Inject	private Cart cart;
	
	private String login;
	private String password;
	private String password2;
	private Map<String, String> permissions = new LinkedHashMap<>();
	private List<Staff> staff = null;
	private String[] permissionSelector = {};
	private java.util.Date birthday;
	private Staff currentStaff = new Staff();
	private Staff loggedStaff = null;
	private Customer loggedCustomer = null;
	private Customer currentCustomer = new Customer();
		
	private boolean showPasswordChangeForm = false;
	private boolean showLoginForm = true;
	private boolean showCustomer = false;
	private boolean showStaffCabinet = false;
	private boolean showAdmin = false;
	private boolean showSuper = false;
	private boolean showKitchen = false;
	private boolean showDelivery = false;
	private boolean showAnalyst = false;
	
	
	public UserBean(){}
	
	
	@PostConstruct
	private void init() {
		permissions.put("S", "Super-user");
		permissions.put("K", "Kitchen Staff");
		permissions.put("D", "Delivery Staff");
		permissions.put("A", "Menu Administrator");
		permissions.put("X", "Blocked User");
		permissions.put("B", "Business Analyst");
	}
	
	@PostConstruct
	public void updateStaffList() {
		staff = staffService.returnStaffList();
		if (staff.size()==0) {
			Staff def = new Staff();
			def.setEmail("default@mail.com");
			def.setAddress("Home");
			def.setName("Default Superuser");
			def.setPermissions("S");
			def.setPassword(Encoder.md5("password"));
			staffService.create(def);
			staff = staffService.returnStaffList();
		}
	}
	
	public String createUser() {
		currentStaff = new Staff();
		permissionSelector = null;
		birthday = null;
		return "";
	}
	
	public String editUser() {
		this.setPermissionSelector(currentStaff.getPermissions().split(""));
		birthday = new java.util.Date(currentStaff.getBirthday().getTime());
		return "editUser";
	}
	
	public String blockUser() {
		currentStaff.setPermissions("X");
		staffService.update(currentStaff);
		updateStaffList();
		return "superuser";
	}

	public void processPermissions() {
		if (loggedStaff != null) {
			String p = loggedStaff.getPermissions();
			if (p.contains("S")) showSuper = true;
			if (p.contains("K")) showKitchen = true;
			if (p.contains("D")) showDelivery = true;
			if (p.contains("A")) showAdmin = true;
			if (p.contains("B")) showAnalyst = true;
		} else {
			showSuper = false;
			showKitchen = false;
			showDelivery = false;
			showAdmin = false;
			showAnalyst = false;
		}
	}
	
	public String returnPermissionsList(String perm) {
		String out = "";
		for (String c : perm.split("")) {
			out = out + permissions.get(c) + ", ";
		}		
		return out.substring(0, out.length()-2);
	}
	
	public boolean validateUserLogin(FacesContext context, UIComponent uiComponent, Object value) {
		login = String.valueOf(value);
		return customerService.find(login) != null || staffService.find(login) != null;
	}
	
	public boolean validateUser(FacesContext context, UIComponent uiComponent, Object value) {
		String s= String.valueOf(value);
		return customerService.find(s) == null && staffService.find(s) == null;
	}
	
	public boolean validatePassword(FacesContext context, UIComponent uiComponent, Object value) {
		Customer cus = customerService.find(login);
		Staff staff = staffService.find(login);
		if (cus != null && cus.getPassword().equals(Encoder.md5(String.valueOf(value)))) return true;
		if (staff != null && staff.getPassword().equals(Encoder.md5(String.valueOf(value)))) return true;
		return false;
	}	
	
	public String logIn() {
		Customer cus = customerService.find(login);
		Staff staff = staffService.find(login);
		if (cus != null ) {
			loggedCustomer = cus;
			showCustomer = true;
			showLoginForm = false;
			password = null;
			return "menu";
		} else if (staff != null){
				loggedStaff = staff;
				processPermissions();
				showStaffCabinet = true;
				showLoginForm = false;
				password = null;
				return goToWorkingPage();
		}		
		return "menu";
	}
	
	public String logOut() {
		if ( loggedCustomer != null ) {
			loggedCustomer = null;
			showCustomer = false;
		} else {
			loggedStaff = null;
			showStaffCabinet = false;
			processPermissions();
		}
		showLoginForm = true;
		return "menu";
	}
	
	public String goToWorkingPage() {
		if (showSuper) return "superuser";
		if (showAdmin) return "admin";
		if (showAnalyst) return "report";
		if (showKitchen) return "kitchen";
		if (showDelivery) return "delivery";
		return "menu";
	}
	
	public String submitOrder() {
		if (loggedCustomer != null) {
			return cart.submitOrder(loggedCustomer.getId());
		} else {
			currentCustomer.setBirthday(new Date(birthday.getTime()));
			customerService.create(currentCustomer);
			return cart.submitOrder(currentCustomer.getId());
		}
	}
	
	public String saveCustomer() {
		loggedCustomer.setBirthday(new Date(birthday.getTime()));
		customerService.update(loggedCustomer);
		return "menu";
	}
	
	public String register() {
		currentCustomer.setPassword(Encoder.md5(currentCustomer.getPassword()));
		currentCustomer.setBirthday(new Date(birthday.getTime()));
		customerService.create(currentCustomer);
		showCustomer = true;
		showLoginForm = false;
		loggedCustomer = currentCustomer;
		return "menu";
	}
	
	public void switchPasswordChangeForm() {
		if (showPasswordChangeForm) {
			showPasswordChangeForm = false;
		} else {
			showPasswordChangeForm = true;
		}
	}
	
	public String changePassword() {
		if (loggedCustomer != null) {
			loggedCustomer.setPassword(Encoder.md5(password));
			customerService.update(loggedCustomer);
			switchPasswordChangeForm();
			return "editCustomer";
		} else if (loggedStaff.getPermissions().contains("S")) {
			currentStaff.setPassword(Encoder.md5(password));
			staffService.update(currentStaff);
			switchPasswordChangeForm();
			return "editUser";
		}
		return "menu";
	}
	
	public void registerUser() {
		String perm = "";
		for (String s : permissionSelector) {
			perm += s;
		}
		currentStaff.setPassword(Encoder.md5(currentStaff.getPassword()));
		currentStaff.setPermissions(perm);
		currentStaff.setBirthday(new Date(birthday.getTime()));
		staffService.create(currentStaff);
		currentStaff = new Staff();
		updateStaffList();
	}
	
	public String saveUserChanges() {
		String perm = "";
		for (String s : permissionSelector) {
			perm += s;
		}
		currentStaff.setPermissions(perm);
		currentStaff.setBirthday(new Date(birthday.getTime()));
		staffService.update(currentStaff);
		return "superuser";
	}	
	
	public boolean showBlockButton(Staff staff) {
		if (staff.getPermissions().contains("X") ||  staff.getEmail().equals(loggedStaff.getEmail())) return false;
		return true;
	}
	
	/** Getters&Setters */	
	
	
	public boolean isShowCustomer() {
		return showCustomer;
	}

	public void setShowCustomer(boolean logged) {
		this.showCustomer = logged;
	}
	
	public java.util.Date getBirthday() {
		return birthday;
	}

	public void setBirthday(java.util.Date birthday) {
		this.birthday = birthday;
	}

	public Customer getCurrentCustomer() {
		return currentCustomer;
	}

	public void setCurrentCustomer(Customer currentCustomer) {
		this.currentCustomer = currentCustomer;
	}

	public Customer getLoggedCustomer() {
		return loggedCustomer;
	}

	public void setLoggedCustomer(Customer loggedCustomer) {
		this.loggedCustomer = loggedCustomer;
	}

	public Staff getLoggedStaff() {
		return loggedStaff;
	}

	public void setLoggedStaff(Staff loggedStaff) {
		this.loggedStaff = loggedStaff;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public StaffService getStaffService() {
		return staffService;
	}

	public void setStaffService(StaffService staffService) {
		this.staffService = staffService;
	}

	public Cart getCart() {
		return cart;
	}
	
	public void setCart(Cart cart) {
		this.cart = cart;
	}

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

	public Map<String, String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Map<String, String> permissions) {
		this.permissions = permissions;
	}

	public boolean isShowAdmin() {
		return showAdmin;
	}

	public void setShowAdmin(boolean showAdmin) {
		this.showAdmin = showAdmin;
	}

	public boolean isShowSuper() {
		return showSuper;
	}

	public void setShowSuper(boolean showSuper) {
		this.showSuper = showSuper;
	}

	public boolean isShowKitchen() {
		return showKitchen;
	}

	public void setShowKitchen(boolean showKitchen) {
		this.showKitchen = showKitchen;
	}

	public boolean isShowDelivery() {
		return showDelivery;
	}

	public void setShowDelivery(boolean showDelivery) {
		this.showDelivery = showDelivery;
	}

	public boolean isShowAnalyst() {
		return showAnalyst;
	}

	public void setShowAnalyst(boolean showAnalyst) {
		this.showAnalyst = showAnalyst;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public boolean isShowPasswordChangeForm() {
		return showPasswordChangeForm;
	}

	public void setShowPasswordChangeForm(boolean showPasswordChangeForm) {
		this.showPasswordChangeForm = showPasswordChangeForm;
	}

	public List<Staff> getStaff() {
		return staff;
	}

	public void setStaff(List<Staff> staff) {
		this.staff = staff;
	}

	public Staff getCurrentStaff() {
		return currentStaff;
	}

	public void setCurrentStaff(Staff currentStaff) {
		this.currentStaff = currentStaff;
	}

	public String[] getPermissionSelector() {
		return permissionSelector;
	}

	public void setPermissionSelector(String[] permissionSelector) {
		this.permissionSelector = permissionSelector;
	}

	public boolean isShowStaffCabinet() {
		return showStaffCabinet;
	}

	public void setShowStaffCabinet(boolean showStaffCabinet) {
		this.showStaffCabinet = showStaffCabinet;
	}


	public boolean isShowLoginForm() {
		return showLoginForm;
	}


	public void setShowLoginForm(boolean showLoginForm) {
		this.showLoginForm = showLoginForm;
	}

}
