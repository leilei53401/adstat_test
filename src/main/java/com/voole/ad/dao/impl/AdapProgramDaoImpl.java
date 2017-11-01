package com.voole.ad.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.voole.ad.dao.AdapProgramDao;
import com.voole.ad.utils.datasource.DataSourceType;


@Repository
public class AdapProgramDaoImpl  extends SqlSessionDaoSupport implements AdapProgramDao {

	@Override
	@DataSourceType(type="adguide")
	public List<String> getValidAmidList() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("adapProgram.queryValidAmid");
	}

}
