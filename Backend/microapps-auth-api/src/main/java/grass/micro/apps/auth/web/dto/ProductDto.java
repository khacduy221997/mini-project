package grass.micro.apps.auth.web.dto;

import java.io.Serializable;

import grass.micro.apps.web.dto.StatusTimestampDto;

public class ProductDto extends StatusTimestampDto implements Serializable {

	private static final long serialVersionUID = -1169494583912084516L;
	
	private String name;
	
	private int price;
	
	private int virtualPrice;
	
	private String image;

	public ProductDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getVirtualPrice() {
		return virtualPrice;
	}

	public void setVirtualPrice(int virtualPrice) {
		this.virtualPrice = virtualPrice;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "ProductDto [name=" + name + ", price=" + price + ", virtualPrice=" + virtualPrice + ", image=" + image
				+ "]";
	}
	
}
