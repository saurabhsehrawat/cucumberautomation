package com.bdd.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.bdd.helper.DriverHelper;

public class PlanitShop_page extends DriverHelper{
	
	//unique elements from page
	By buy_element=By.xpath("//*[contains(text(),'')]/parent::div/p/a");
	By price_element=By.xpath("//*[contains(text(),'')]/parent::div/p/span");
	By cart_login_element=By.xpath("//a[contains(@href,'')]");
	By shopping_cart_items=By.xpath("//tr[contains(@class,'cart-item')]");
	By sum_total=By.xpath("//strong[contains(text(),'Total')]");
	
	//variables for validation purpose
	int product_1_qty,product_2_qty,product_3_qty,total_qty_of_products; //product quantity
	double product_1_price,product_2_price,product_3_price,sum_total_cart; //product price
	double subtotal_product_1,subtotal_product_2,subtotal_product_3; //product subtotal
	String product_name_1,product_name_2,product_name_3;//product names
	
	//get product price and quantities from Add To Cart page
		public void fetchProductPriceQtyNameFromAddToCart(int product_quantity, String product_name) throws Exception
		{
			price_element=By.xpath("//*[contains(text(),'"+product_name+"')]/parent::div/p/span");
			if(product_name.equals("Stuffed Frog"))
			{
				this.product_1_qty=product_quantity;
				this.product_name_1=product_name;
				//parse String to Double removing $ sign
				String temp_price_1=base_util.utobj().getText(price_element).replaceAll("[$,]", "");
				this.product_1_price=Double.parseDouble(temp_price_1);
				this.subtotal_product_1=product_1_price*product_1_qty;
			}
			else if(product_name.equals("Fluffy Bunny"))
			{
				this.product_2_qty=product_quantity;
				this.product_name_2=product_name;
				//parse String to Double removing $ sign
				String temp_price_2=base_util.utobj().getText(price_element).replaceAll("[$,]", "");
				this.product_2_price=Double.parseDouble(temp_price_2);
				this.subtotal_product_2=product_1_price*product_2_qty;
			}
			else if(product_name.equals("Valentine Bear"))
			{
				this.product_3_qty=product_quantity;
				this.product_name_3=product_name;
				//parse String to Double removing $ sign
				String temp_price_3=base_util.utobj().getText(price_element).replaceAll("[$,]", "");
				this.product_3_price=Double.parseDouble(temp_price_3);
				this.subtotal_product_3=product_1_price*product_3_qty;
			}
		}
	
	//add products to cart
	public void workflowActionToAddProductsToCart(int product_quantity, String product_name) throws Exception
	{
		buy_element=By.xpath("//*[contains(text(),'"+product_name+"')]/parent::div/p/a");
		//adding products
		for(int i=0;i<product_quantity;i++)
		{
			base_util.eWait().WaitForElementToBeClickable(buy_element, 10);
			base_util.utobj().clickElement(buy_element);
			Thread.sleep(750);
		}
	}
	
	//go to shopping cart page
	public void workflowActionToGoToShoppingCart(String cart_login) throws Exception
	{
		cart_login_element=By.xpath("//a[contains(@href,'"+cart_login+"')]");
		base_util.eWait().WaitForElementToBeClickable(cart_login_element, 10);
		base_util.utobj().clickElement(cart_login_element);
		//total products in the cart
		By total_qty_element=By.xpath("//a[contains(@href,'"+cart_login+"')]/span");
		total_qty_of_products=Integer.valueOf(driver.findElement(total_qty_element).getText());
		ExtentCucumberAdapter.addTestStepLog("Total products added in the cart are <b>"+total_qty_of_products+"</b>");
	}
	
	//validate subtotal, price and sum total for added items in the shopping cart
	public void validatePriceSubtotalOfCartItems() throws InterruptedException
	{
		List<WebElement> shopping_cart_products=driver.findElements(shopping_cart_items);
		double total=0.0;
		double[] subtotal_of_items=new double[3];
		double[] product_price=new double[3];
		for(int i=1;i<=shopping_cart_products.size();i++)
		{
			//name of product
			String name=driver.findElement(By.xpath("(//tr[contains(@class,'cart-item')])["+i+"]/td[1]")).getText().trim();
			//find the price for each product
			WebElement price_of_cart_items=driver.findElement(By.xpath("(//tr[contains(@class,'cart-item')]/td[2])["+i+"]"));
			String price_of_item=price_of_cart_items.getText().replaceAll("[$,]", "");
			product_price[i-1]=Double.parseDouble(price_of_item);
			
			//find the quantity of each product
			WebElement qty_of_cart_items=driver.findElement(By.xpath("(//tr[contains(@class,'cart-item')]/td[3]/input)["+i+"]"));
			String product_qty=qty_of_cart_items.getAttribute("value");
			double product_quantity=Double.parseDouble(product_qty);
			
			//calculate subtotal for each row
			subtotal_of_items[i-1]=(product_price[i-1]*product_quantity);
			ExtentCucumberAdapter.addTestStepLog("<b><i>"+name+" has price "+product_price[i-1]+" with quantities "+(int)product_quantity+" and subtotal "+subtotal_of_items[i-1]+"</b></i>");
			
			//total of all the products purchased
			total+=subtotal_of_items[i-1];
			continue;
			
		}
		//validate subtotal and price for each products in the cart 
		if(product_name_1.equals("Stuffed Frog"))
		{
			
			Assert.assertEquals(subtotal_product_1, subtotal_of_items[0], "Subtotal for Stuffed Frog does not match..");//validate subtotal
			Assert.assertEquals(product_1_price, product_price[0], "Pricing for Stuffed Frog does not match..");//validate price
		}
		else if(product_name_2.equals("Fluffy Bunny"))
		{
			Assert.assertEquals(subtotal_product_2, subtotal_of_items[1], "Subtotal for Fluffy Bunny does not match..");//validate subtotal
			Assert.assertEquals(product_2_price, product_price[1], "Pricing for Fluffy Bunny does not match..");//validate price
		}
		else if(product_name_3.equals("Valentine Bear"))
		{
			Assert.assertEquals(subtotal_product_3, subtotal_of_items[2], "Subtotal for Valentine Bear does not match..");//validate subtotal
			Assert.assertEquals(product_3_price, product_price[2], "Pricing for Valentine Bear does not match..");//validate price
		}
		this.sum_total_cart=total;
		ExtentCucumberAdapter.addTestStepLog("<font color='red'><b><i>Subtotal of all the products becomes "+total+"</b></i></font>");
	}
	//validate sum total of all the products
	public void validateSumtotalOfCartProducts() throws Exception
	{
		sum_total=By.xpath("//strong[contains(text(),'Total')]");
		//regex for removing non numerical characters
		String fetch_sum=base_util.utobj().getText(sum_total).replaceAll("[^\\d.]", "");
		Assert.assertEquals(sum_total_cart, Double.parseDouble(fetch_sum), "Sum total of all the products does not match");
	}
}
