package com.house.framework.commons.orm;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@SuppressWarnings("serial")
@Service
public class BaseServiceImpl implements BaseService{

	@Autowired
	private BaseDao baseDao;

	public <T> T load(Class<T> clazz, Serializable id) {
		return baseDao.load(clazz, id);
	}

	
	public <T> T get(Class<T> clazz, Serializable id) {
		return baseDao.get(clazz, id);
	}

	
	public <T> List<T> loadAll(Class<T> clazz) {
		return baseDao.loadAll(clazz);
	}

	public <T> Serializable save(T entity) {
		return baseDao.save(entity);
	}

	
	public <T> void delete(T entity) {
		baseDao.delete(entity);
	}

	
	public <T> void update(T entity) {
		baseDao.update(entity);
	}

	
	public <T> void saveOrUpdate(T entity) {
		baseDao.saveOrUpdate(entity);
	}

	
	public <T> void merge(T entity) {
		baseDao.merge(entity);
	}

	
	@SuppressWarnings("rawtypes")
	public List find(String hql, Object... params) {
		return baseDao.find(hql, params);
	}

	
	public <X> List<X> find(String hql, int count, Object... params) {
		return baseDao.find(hql, count, params);
	}

	
	public List<Map<String, Object>> findBySql(String sql, Object... params) {
		return baseDao.findBySql(sql, params);
	}

	
	public <X> X findUnique(String hql, Object... params) {
		return baseDao.findUnique(hql, params);
	}

	
	public void initialize(Object entity) {
		baseDao.initialize(entity);
	}

	
	public void flush() {
		baseDao.flush();
	}

	
	public <X> Page<X> findPage(Page<X> page, String hql, Object... params) {
		return baseDao.findPage(page, hql, params);
	}

	
	public <X> Page<X> findPage(Page<X> page, String hql, String countHql,
			Object... params) {
		return baseDao.findPage(page, hql, countHql, params);
	}

	
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, String sql, Object... params) {
		return baseDao.findPageBySql(page, sql, params);
	}

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, String sql, String countSql,
			Object... params) {
		return baseDao.findPageBySql(page, sql, countSql, params);
	}

	
	public <T> List<T> findByNativeSql(String sql, RowMapper<T> rowMapper,
			Object... params) {
		return baseDao.findByNativeSql(sql, rowMapper, params);
	}

	
	public <T> Page<T> findPageByNativeSql(Page<T> page, String sql,
			RowMapper<T> rowMapper, Object... params) {
		return baseDao.findPageByNativeSql(page, sql, rowMapper, params);
	}

	
	public <T> Page<T> findPageByNativeSql(Page<T> page, String sql,
			String countSql, RowMapper<T> rowMapper, Object... params) {
		return baseDao.findPageByNativeSql(page, sql, countSql, rowMapper, params);
	}

	
	public List<Map<String, Object>> findListsByNativeSQL(String nativeSQL,
			Object... params) {
		return baseDao.findListsByNativeSQL(nativeSQL, params);
	}

	
	public Map<Long, Map<String, Object>> findMapsByNativeSQL(String nativeSQL,
			String primaryKey, Object... params) {
		return baseDao.findMapsByNativeSQL(nativeSQL, primaryKey, params);
	}

	
	public Long executeUpdate(String hql, Object... params) {
		return baseDao.executeUpdate(hql, params);
	}

	
	public Long executeUpdateBySql(String sql, Object... params) {
		return baseDao.executeUpdateBySql(sql, params);
	}
	
	public String getSeqNo(String tableName){
		return baseDao.getSeqNo(tableName);
	}
	
}
