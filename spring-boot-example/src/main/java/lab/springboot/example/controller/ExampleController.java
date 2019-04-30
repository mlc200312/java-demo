package lab.springboot.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.UUID;

import javax.annotation.Resource;

import lab.springboot.example.base.BaseController;
import lab.springboot.example.service.ExampleService;
import lab.springboot.example.util.RestApiResponse;
import lab.springboot.example.util.TokenUtils;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api("ExampleController")
@RestController
@RequestMapping("/example")
public class ExampleController extends BaseController {

	@Resource
	private ExampleService exampleService;

	@ApiOperation(value = " 测试api", notes = " 测试api")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success") })
	@RequestMapping(value = "/testApi", method = RequestMethod.POST)
	@ResponseBody
	public RestApiResponse<String> testApi() {
		return success();
	}

	@ApiOperation(value = " 测试jdbc", notes = " 测试jdbc")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success") })
	@RequestMapping(value = "/testJdbc", method = RequestMethod.POST)
	@ResponseBody
	public RestApiResponse<Integer> testJdbc() {
		try {
			int num = exampleService.testJdbc();
			return success(num);
		} catch (Exception e) {
			return error(e);
		}
	}

	@ApiOperation(value = " 测试redis", notes = " 测试redis")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success") })
	@RequestMapping(value = "/testRedis", method = RequestMethod.POST)
	@ResponseBody
	public RestApiResponse<Object> testRedis() {
		try {
			String uuid = UUID.randomUUID().toString();
			TokenUtils.Builder().setToken(uuid);
			return success((Object) TokenUtils.Builder().popToken(uuid));
		} catch (Exception e) {
			return error(e);
		}
	}

	@ApiOperation(value = " 测试cach", notes = " 测试cach")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "success") })
	@RequestMapping(value = "/testCach", method = RequestMethod.POST)
	@ResponseBody
	public RestApiResponse<String> testCach() {
		return success(exampleService.uuid());
	}

}
