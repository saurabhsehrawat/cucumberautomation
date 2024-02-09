package com.bdd.stepdefinations;

import com.bdd.pages.PlanitShop_page;

import io.cucumber.java.en.Then;

public class PlanitShop_stepDef extends PlanitShop_page{
	
	//add to cart
	@Then("user buy {int} {string} and add to shopping cart and get the product price")
	public void user_buy_and_add_to_shopping_cart_and_get_the_product_price(Integer product_quantity, String product_name) throws Exception {
		fetchProductPriceQtyNameFromAddToCart(product_quantity, product_name);
		workflowActionToAddProductsToCart(product_quantity, product_name);
	}
	//go to cart
	@Then("user go to shopping {string} page")
	public void user_go_to_shopping_page(String cart_login) throws Exception {
	    workflowActionToGoToShoppingCart(cart_login);
	}
	@Then("user verify subtotal and price for each product in the cart")
	public void user_verify_subtotal_and_price_for_each_product_in_the_cart() throws InterruptedException {
		validatePriceSubtotalOfCartItems();
	}
	@Then("user verify sum total value of all the products in the cart")
	public void user_verify_sum_total_value_of_all_the_products_in_the_cart() throws Exception {
	    validateSumtotalOfCartProducts();
	}
}
