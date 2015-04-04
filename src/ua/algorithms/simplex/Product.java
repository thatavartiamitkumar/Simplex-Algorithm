package ua.algorithms.simplex;

import java.io.Serializable;
import java.util.List;

/**
 * @author MaheshBabu
 * 
 *         This object holds the properties of a product
 * 
 */
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private Double profit;
	private List<Ingredient> ingredientsList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

	public List<Ingredient> getIngredientsList() {
		return ingredientsList;
	}

	public void setIngredientsList(List<Ingredient> ingredientsList) {
		this.ingredientsList = ingredientsList;
	}

}
