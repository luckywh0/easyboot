package com.hu4java.web.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hu4java.base.request.PageRequest;
import com.hu4java.base.result.Result;
import com.hu4java.system.condition.RegionCondition;
import com.hu4java.system.entity.Region;
import com.hu4java.system.service.RegionService;
import com.hu4java.web.system.response.RegionSelectResponse;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 区域表
 * @author	EasyBoot
 * @date	2020-09-17 11:20:38
 */
@RestController
@RequestMapping("/region")
public class RegionController {

	@Autowired
	private RegionService regionService;
	@Autowired
	private MapperFacade mapperFacade;

	@GetMapping("/list")
	public Result<Page<Region>> list(PageRequest<Region> request, RegionCondition condition) {
		Page<Region> page = regionService.listByPage(request.toPage(), condition.queryWrapper());
		return Result.success(page);
	}

	@GetMapping("/selectList")
	public Result<List<RegionSelectResponse>> selectList() {

		List<Region> list = regionService.listByTree();
		List<RegionSelectResponse> responseList = mapperFacade.mapAsList(list, RegionSelectResponse.class);
		return Result.success(responseList);
	}
}