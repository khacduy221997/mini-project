package grass.micro.apps.auth.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import grass.micro.apps.annotation.GetBody;
import grass.micro.apps.annotation.Logged;
import grass.micro.apps.auth.web.dto.CityDto;
import grass.micro.apps.auth.web.dto.CompanyDto;
import grass.micro.apps.auth.web.util.ControllerAction;
import grass.micro.apps.auth.web.util.DtoFetchingUtils;
import grass.micro.apps.model.auth.City;
import grass.micro.apps.model.auth.Company;
import grass.micro.apps.model.auth.Department;
import grass.micro.apps.model.auth.District;
import grass.micro.apps.service.auth.CityService;
import grass.micro.apps.service.auth.CompanyService;
import grass.micro.apps.service.auth.DepartmentService;
import grass.micro.apps.service.auth.DistrictService;
import grass.micro.apps.web.component.ErrorsKeyConverter;
import grass.micro.apps.web.controller.GeneralController;
import grass.micro.apps.web.controller.support.AppControllerListingSupport;
import grass.micro.apps.web.controller.support.AppControllerSupport;
import grass.micro.apps.web.dto.RpcResponse;
import grass.micro.apps.web.form.validator.LimittedForm;
import grass.micro.apps.web.util.RequestUtils;

@RestController
public class CommonController extends GeneralController {

	private Logger logger = Logger.getLogger(CommonController.class);

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CityService cityService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private DistrictService districtService;

	@Autowired
	private ErrorsKeyConverter errorsProcessor;

	@GetMapping(value = ControllerAction.APP_COMPANY_ACTION)
	@Logged
	public ResponseEntity<?> listCompany(HttpServletRequest request, HttpServletResponse response,
			@GetBody LimittedForm form, BindingResult errors) {
		RpcResponse rs = new RpcResponse(ControllerAction.APP_COMPANY_ACTION);
		HttpStatus status = HttpStatus.OK;
		List<Company> companies = null;
		try {
			companies = this.companyService.getAllActivated();
		} catch (Exception ex) {
			logger.warn(ex.getMessage(), ex);
		}

		if (companies == null) {
			companies = new ArrayList<>();
		}

		List<CompanyDto> dtos = DtoFetchingUtils.fetchCompanies(companies);
		rs.addAttribute("companies", dtos);

		return new ResponseEntity<>(rs, status);
	}

	@GetMapping(value = ControllerAction.APP_COMPANY_DETAIL_DEPARTMENT_ACTION)
	@Logged
	public ResponseEntity<?> listDepartmentsOfCompany(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("id") Integer id, @GetBody LimittedForm form) {
		AppControllerSupport support = new AppControllerListingSupport() {
			@Override
			public String getAttributeName() {
				return "departments";
			}

			@Override
			public List<? extends Serializable> getEntityList(HttpServletRequest request, HttpServletRequest response,
					Errors errors, ErrorsKeyConverter errorsProcessor) {
				return CommonController.this.departmentService.getByCompanyId(id);
			}

			@SuppressWarnings("unchecked")
			@Override
			public List<?> fetchEntitiesToDtos(List<? extends Serializable> entities) {
				return DtoFetchingUtils.fetchDepartments((List<Department>) entities);
			}
		};

		return support.doSupport(request, null, RequestUtils.getInstance().getBindingResult(), errorsProcessor);
	}

	@GetMapping(value = ControllerAction.APP_CITY_ACTION)
	@Logged
	public ResponseEntity<?> listCity(HttpServletRequest request, HttpServletResponse response,
			@GetBody LimittedForm form, BindingResult errors) {
		RpcResponse rs = new RpcResponse(ControllerAction.APP_CITY_ACTION);
		HttpStatus status = HttpStatus.OK;
		List<City> cities = null;
		try {
			cities = this.cityService.getAll();
		} catch (Exception ex) {
			logger.warn(ex.getMessage(), ex);
		}

		if (cities == null) {
			cities = new ArrayList<>();
		}

		List<CityDto> dtos = DtoFetchingUtils.fetchCities(cities);
		rs.addAttribute("cities", dtos);

		return new ResponseEntity<>(rs, status);
	}

	@GetMapping(value = ControllerAction.APP_CITY_DETAIL_ACTION)
	@Logged
	public ResponseEntity<?> cityDetail(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("id") String code, @GetBody LimittedForm form) {
		AppControllerSupport support = new AppControllerSupport() {

			@Override
			public void process(HttpServletRequest request, HttpServletResponse response, Errors errors,
					ErrorsKeyConverter errorsProcessor) {
				List<District> districts = null;
				try {
					districts = districtService.getByCityCode(code);
				} catch (Exception ex) {
					logger.warn(ex.getMessage(), ex);
				}

				if (districts == null) {
					districts = new ArrayList<>();
				}
				getRpcResponse().addAttribute("districts", DtoFetchingUtils.fetchDistricts(districts));
			}
			
		};

		return support.doSupport(request, null, RequestUtils.getInstance().getBindingResult(), errorsProcessor);
	}
}
