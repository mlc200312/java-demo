package lab.springboot.example.service.impl;

import java.util.UUID;

import javax.annotation.Resource;

import lab.springboot.example.mapper.ExampleMapper;
import lab.springboot.example.service.ExampleService;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("exampleService")
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
public class ExampleServiceImpl implements ExampleService {

	@Resource
	private ExampleMapper exampleMapper;

	@Override
	public int testJdbc() {
		return exampleMapper.testJdbc();
	}

	@Override
	@Cacheable(value = "uuid")
	public String uuid() {
		return UUID.randomUUID().toString();
	}

}
