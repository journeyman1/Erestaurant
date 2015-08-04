package main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import edu.bionic.com.PersistenceLayer.Category;
import edu.bionic.com.PersistenceLayer.Dish;
import edu.bionic.com.PersistenceLayer.Services.CategoryService;
import edu.bionic.com.PersistenceLayer.Services.DishService;

@Named
@Scope("session")
public class MenuBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject private Cart cart;
	@Inject	private DishService dishService;
	@Inject	private CategoryService categoryService;
	
	private List<Category> categoryList = new ArrayList<>();
	private List<Dish> dishes = null;
	private Dish currentDish = new Dish();
	private String newCategoryTitle;
	private String selectedCategory;
	private Map<String, Category> categoryMap = new LinkedHashMap<>();
	
	public static final String CATEGORYNAME = "category";
	public static final String KITCHENMADE = "kitchen";
	public static final String NONKITCHENMADE = "non-kitchen";
	
	public static final int UNCOOKED = 0;
	public static final int KITCHENREADY = 1;
	public static final int NONKITCHENREADY = 2;
	public static final int READYFORSHIPMENT = 3;
	public static final int DELIVERING = 4;
	public static final int DELIVERED = 5;

	
	public MenuBean(){}

	
	@PostConstruct
	public void refreshMenuList() {
		dishes = dishService.returnDishes();
	}
	
	@PostConstruct 
	public void refreshCategoryList() {
		categoryList = categoryService.returnCategoryList();	
		categoryMap = new LinkedHashMap<>();
		for (Category c : categoryList) {
			categoryMap.put(c.getTitle(), c);
		}
	}
	
    private String getViewParameter(String name) {
        FacesContext fc = FacesContext.getCurrentInstance();
        String param = (String) fc.getExternalContext().getRequestParameterMap().get(name);
        if (param != null && param.trim().length() > 0) {
            return param;
        } else {
            return null;
        }
    }
    
    public String getCategory() {
        String category = getViewParameter(CATEGORYNAME);
        if (category != null) {
            selectedCategory = category;
        }
        return selectedCategory;
    }
	
	public String removeDishFromMenu() {
		dishService.remove(currentDish);
		refreshMenuList();
		refreshCategoryList();
		return "admin";
	}
	
	public String addDishToMenu() {
		currentDish.setCategory(categoryMap.get(selectedCategory));
		dishService.create(currentDish);
		refreshCategoryList();
		return "admin";
	}
	
	public String addNewDish() {
		this.currentDish = new Dish();
		return "addish";
	}
	
	public String editDish(){
		return "editDish";
	}
	
	public String addToCart() {
		cart.addDish(currentDish);
		return "";
	}
	
	public String removeFromCart() {
		cart.removeDish(currentDish);
		return "";
	}
	
	public String updateMenuItem() {
		currentDish.setCategory(categoryMap.get(selectedCategory));
		dishService.update(currentDish);
		refreshMenuList();
		return "admin";
	}
	
	public String createCategory() {
		Category cat = new Category();
		cat.setTitle(newCategoryTitle);
		categoryService.create(cat);
		refreshCategoryList();
		return "admin";
	}
	
	
	/** Getters&Setters */
	
	
	public List<Dish> getDishes() {
		return dishes;
	}

	public void setDishes(List<Dish> dishes) {
		this.dishes = dishes;
	}
		
	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}
	
	public Dish getCurrentDish() {
		return currentDish;
	}

	public void setCurrentDish(Dish currentDish) {
		this.currentDish = currentDish;
	}
	
	public String getKitchenMade() {
		return MenuBean.KITCHENMADE;
	}
	
	public String getNonKitchenMade() {
		return MenuBean.NONKITCHENMADE;
	}

	public DishService getDishService() {
		return dishService;
	}

	public void setDishService(DishService dishService) {
		this.dishService = dishService;
	}

	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public Map<String, Category> getCategoryMap() {
		return categoryMap;
	}

	public void setCategoryMap(Map<String, Category> categoryMap) {
		this.categoryMap = categoryMap;
	}
	
	public String getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(String selectedCategory) {
		this.selectedCategory = selectedCategory;
	}


	public String getNewCategoryTitle() {
		return newCategoryTitle;
	}


	public void setNewCategoryTitle(String newCategoryTitle) {
		this.newCategoryTitle = newCategoryTitle;
	}
	
}
