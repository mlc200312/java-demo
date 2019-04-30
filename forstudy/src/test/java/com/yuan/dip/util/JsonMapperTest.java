package com.yuan.dip.util;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;

import com.yuan.forstudy.entity.Model;
import com.yuan.forstudy.util.JsonMapper;

public class JsonMapperTest {

	@Test
	public void obj2Json() {
		Model model = new Model(UUID.randomUUID().toString(), "小闵");
		String json = JsonMapper.obj2Json(model);
		System.out.println(json);
	}

	@Test
	public void obj2Map() {
		Model model = new Model(UUID.randomUUID().toString(), "小闵");
		String json = JsonMapper.obj2Json(model);
		Map<String, Object> map = JsonMapper.json2Map(json, String.class, Object.class);
		System.out.println(map.toString());
	}

	@Test
	public void json2Obj() {
		String json = "{\"id\":\"98b21d1b-5a5a-4330-993a-6a66b619a523\",\"name\":\"小闵\",\"cretaeDate\":1504949297449}";
		Model model = JsonMapper.json2Obj(json, Model.class);
		System.out.println(model.toString());
	}

	@Test
	public void json2List() {
		String json = "[{\"id\":\"98b21d1b-5a5a-4330-993a-6a66b619a523\",\"name\":\"小闵\",\"cretaeDate\":1504949297449},{\"id\":\"98b21d1b-5a5a-4330-993a-6a66b619a523\",\"name\":\"小闵\",\"cretaeDate\":1504949297449}]";
		List<Model> list = JsonMapper.json2List(json, Model.class);
		System.out.println(list.toString());
	}

	@Test
	public void json2Map() {
		String json = "{\"id\":\"98b21d1b-5a5a-4330-993a-6a66b619a523\",\"name\":\"小闵\",\"cretaeDate\":1504949297449}";
		Map<String, Object> map = JsonMapper.json2Map(json, String.class, Object.class);
		System.out.println(map.toString());
	}

	@Test
	public void map2Json() {
		String json = "{\"id\":\"98b21d1b-5a5a-4330-993a-6a66b619a523\",\"name\":\"小闵\",\"cretaeDate\":1504949297449}";
		Map<String, Object> map = JsonMapper.json2Map(json, String.class, Object.class);
		json = JsonMapper.obj2Json(map);
		System.out.println(json);
	}

	@Test
	public void map2Model() {
		String json = "{\"id\":\"98b21d1b-5a5a-4330-993a-6a66b619a523\",\"name\":\"小闵\",\"cretaeDate\":1504949297449}";
		Map<String, Object> map = JsonMapper.json2Map(json, String.class, Object.class);
		json = JsonMapper.obj2Json(map);
		Model model = JsonMapper.json2Obj(json, Model.class);
		System.out.println(model.toString());
	}

	@Test
	public void list2Json() {
		String json = "[{\"id\":\"98b21d1b-5a5a-4330-993a-6a66b619a523\",\"name\":\"小闵\",\"cretaeDate\":1504949297449},{\"id\":\"98b21d1b-5a5a-4330-993a-6a66b619a523\",\"name\":\"小闵\",\"cretaeDate\":1504949297449}]";
		List<Model> list = JsonMapper.json2List(json, Model.class);
		json = JsonMapper.obj2Json(list);
		System.out.println(json);
	}

}
