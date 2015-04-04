package ua.algorithms.simplex;

import java.io.Serializable;

/**
 * @author MaheshBabu
 * 
 *         This object holds the properties of a ingredient
 * 
 */
public class Ingredient implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private Double quantity;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

}
