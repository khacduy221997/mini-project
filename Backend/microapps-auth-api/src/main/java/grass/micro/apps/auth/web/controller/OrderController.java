package grass.micro.apps.auth.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import grass.micro.apps.annotation.Logged;
import grass.micro.apps.auth.web.form.OrderForm;
import grass.micro.apps.auth.web.util.ControllerAction;
import grass.micro.apps.web.component.ErrorsKeyConverter;
import grass.micro.apps.web.controller.support.AppControllerSupport;
import grass.micro.apps.web.util.RequestUtils;

@RestController
public class OrderController {

	@Autowired
	private ErrorsKeyConverter errorsProcessor;

	@RequestMapping(value = ControllerAction.APP_ORDER_ACTION, method = RequestMethod.POST)
	@Logged
	@ResponseBody
	public ResponseEntity<?> getProductDetail(HttpServletRequest request, HttpServletResponse response,
			@RequestBody OrderForm orderForm) {
		AppControllerSupport support = new AppControllerSupport() {
			
			@Override
			public void process(HttpServletRequest request, HttpServletResponse response, Errors errors,
					ErrorsKeyConverter errorsProcessor) {
				
			}
		};
		return support.doSupport(request, response, RequestUtils.getInstance().getBindingResult(), errorsProcessor);
	}
}
