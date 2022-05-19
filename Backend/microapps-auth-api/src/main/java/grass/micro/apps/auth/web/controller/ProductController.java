package grass.micro.apps.auth.web.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import grass.micro.apps.annotation.Logged;
import grass.micro.apps.auth.web.dto.ProductDto;
import grass.micro.apps.auth.web.util.ControllerAction;
import grass.micro.apps.web.component.ErrorsKeyConverter;
import grass.micro.apps.web.controller.support.AppControllerSupport;
import grass.micro.apps.web.util.RequestUtils;

@RestController
public class ProductController {

	@Autowired
	private ErrorsKeyConverter errorsProcessor;

	private static String[] IMAGES = {
			"https://www.britishairways.com/assets/images/MediaHub/Media-Database/Royalty-free-RF/Food-and-drink/Club-World/cw-doco-june-2021-28_600x337.jpg",
			"https://www.britishairways.com/assets/images/MediaHub/Media-Database/Rights-managed-RM/Food-and-drink/Euro-Traveller/buy-on-board/tom-kerridge-sandwiches-rt_1200x675.jpg",
			"https://www.britishairways.com/assets/images/MediaHub/Media-Database/Rights-managed-RM/Food-and-drink/Club-Europe/Shot-048-CE-7863_480x270.jpg",
			"https://www.britishairways.com/assets/images/MediaHub/Media-Database/Rights-managed-RM/Cabins/World-Traveller/wt-1872-ret-v3-asian-chicken_1200x675.jpg" };

	private static int IMAGE_LENGTH = IMAGES.length;

	@RequestMapping(value = ControllerAction.APP_PRODUCT_ACTION, method = RequestMethod.GET)
	@Logged
	@ResponseBody
	public ResponseEntity<?> listProduct(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("segment") int segment) {
		AppControllerSupport support = new AppControllerSupport() {

			@Override
			public void process(HttpServletRequest request, HttpServletResponse response, Errors errors,
					ErrorsKeyConverter errorsProcessor) {
				List<ProductDto> dtos = new LinkedList<>();
				for (int index = 0; index < segment; index++) {
					ProductDto dto = new ProductDto();
					dto.setName("Món ăn " + (index + 1));
					dto.setPrice(100000);
					dto.setVirtualPrice(200000);
					String image = randomImage();
					dto.setImage(image);
					dtos.add(dto);
				}
				getRpcResponse().addAttribute("products", dtos);
			}

		};
		return support.doSupport(request, response, RequestUtils.getInstance().getBindingResult(), errorsProcessor);
	}
	
	@RequestMapping(value = ControllerAction.APP_PRODUCT_DETAIL_ACTION, method = RequestMethod.GET)
	@Logged
	@ResponseBody
	public ResponseEntity<?> getProductDetail(HttpServletRequest request, HttpServletResponse response) {
		AppControllerSupport support = new AppControllerSupport() {
			
			@Override
			public void process(HttpServletRequest request, HttpServletResponse response, Errors errors,
					ErrorsKeyConverter errorsProcessor) {
				ProductDto product = new ProductDto();
				product.setId(1);
				product.setName("Món ăn " + 1);
				product.setPrice(100000);
				product.setVirtualPrice(200000);
				String image = randomImage();
				product.setImage(image);
				getRpcResponse().addAttribute("product", product);
				getRpcResponse().addAttribute("resources", IMAGES);
			}
		};
		return support.doSupport(request, response, RequestUtils.getInstance().getBindingResult(), errorsProcessor);
	}

	protected String randomImage() {
		Random rand = new Random();
		int imageNo = rand.nextInt(IMAGE_LENGTH);
		return IMAGES[imageNo];
	}
}
