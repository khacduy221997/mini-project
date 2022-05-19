package grass.micro.apps.auth.web.form;

import java.io.Serializable;
import java.util.List;

import grass.micro.apps.auth.web.dto.ProductDto;

public class OrderForm implements Serializable {

	private static final long serialVersionUID = 6170956375227447768L;
	
	private List<ProductDto> products;
	
	private int customerId;

	public OrderForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<ProductDto> getProducts() {
		return products;
	}

	public void setProducts(List<ProductDto> products) {
		this.products = products;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	@Override
	public String toString() {
		return "OrderForm [products=" + products + ", customerId=" + customerId + "]";
	}
	
}
