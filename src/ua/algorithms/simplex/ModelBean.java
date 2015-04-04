package ua.algorithms.simplex;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class ModelBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private int ingredientsCount;
	private List<Ingredient> ingredientsList;

	private int productsCount;
	private List<Product> productsList;
	private String conclusion;
	private boolean showConclusion;

	/**
	 * Change Listener for ingredient count
	 */
	public void ingredientCountChangeListener() {
		System.out.println("ingredientCountChangeListener");

		ingredientsList = new ArrayList<Ingredient>(ingredientsCount);
		for (int i = 0; i < ingredientsCount; i++) {
			Ingredient ingredient = new Ingredient();
			ingredientsList.add(ingredient);
		}

		// Updating the products table accordingly
		if (null != productsList)
			productCountChangeListener();
	}

	/**
	 * Change listener for Product count
	 */
	public void productCountChangeListener() {
		System.out.println("productCountChangeListener");
		if (null != ingredientsList) {

			productsList = new ArrayList<Product>(productsCount);
			for (int i = 0; i < productsCount; i++) {
				Product product = new Product();
				List<Ingredient> ingredientsList = new ArrayList<Ingredient>(
						ingredientsCount);
				for (int j = 0; j < ingredientsCount; j++) {
					Ingredient ingredient = new Ingredient();
					ingredient.setName(this.ingredientsList.get(j).getName());
					ingredientsList.add(ingredient);
				}
				product.setIngredientsList(ingredientsList);
				productsList.add(product);
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(
					"NoIngredients",
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Please define ingredients",
							"No ingredients defined!!"));
		}
	}

	/**
	 * Method to perform the Simplex Algorithm
	 * 
	 * @return
	 */
	public String calculateMaxProfit() {
		// Array to store names of all the ingredients
		String ingrdtNameArray[] = new String[ingredientsCount];

		// Array to store the quantity of each ingredient
		double quantityArray[] = new double[ingredientsCount];

		for (int j = 0; j < ingredientsCount; j++) {
			ingrdtNameArray[j] = ingredientsList.get(j).getName();
			quantityArray[j] = ingredientsList.get(j).getQuantity();
		}

		// Array to store the product names
		String prodNameArray[] = new String[productsCount];

		// Array to store the profits earned per unit
		double profitArray[] = new double[productsCount];

		for (int i = 0; i < productsCount; i++) {
			prodNameArray[i] = productsList.get(i).getName();
			profitArray[i] = productsList.get(i).getProfit();
		}

		// Array to store the ingredient composition of products
		double allQuantities[][] = new double[ingredientsCount][productsCount];

		for (int i = 0; i < productsCount; i++) {
			Product product = productsList.get(i);
			for (int j = 0; j < ingredientsCount; j++) {
				allQuantities[j][i] = product.getIngredientsList().get(j)
						.getQuantity();
			}
		}

		SimplexAlgorithm simplex = new SimplexAlgorithm();
		conclusion = simplex.performSimplex(ingrdtNameArray, quantityArray,
				prodNameArray, profitArray, allQuantities);

		showConclusion = true;

		return "";
	}

	/**
	 * Method to reset the form
	 * 
	 * @return
	 */
	public String reset() {
		showConclusion = false;

		ingredientsCount = 0;
		ingredientsList = null;
		productsCount = 0;
		productsList = null;

		return "";
	}

	public int getIngredientsCount() {
		return ingredientsCount;
	}

	public void setIngredientsCount(int ingredientsCount) {
		this.ingredientsCount = ingredientsCount;
	}

	public List<Ingredient> getIngredientsList() {
		return ingredientsList;
	}

	public void setIngredientsList(List<Ingredient> ingredientsList) {
		this.ingredientsList = ingredientsList;
	}

	public int getProductsCount() {
		return productsCount;
	}

	public void setProductsCount(int productsCount) {
		this.productsCount = productsCount;
	}

	public List<Product> getProductsList() {
		return productsList;
	}

	public void setProductsList(List<Product> productsList) {
		this.productsList = productsList;
	}

	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	public boolean isShowConclusion() {
		return showConclusion;
	}

	public void setShowConclusion(boolean showConclusion) {
		this.showConclusion = showConclusion;
	}

}
