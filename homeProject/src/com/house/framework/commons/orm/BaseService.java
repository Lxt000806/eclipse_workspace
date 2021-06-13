package com.house.framework.commons.orm;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;

public interface BaseService extends Serializable {

	/**
	 * 根据ID加载PO实例, 如果二级缓存中存在, 则读取二级缓存的数据
	 * @param <T>
	 * 
	 * @param id
	 * @return 返回相应的持久化PO实例
	 */
	public <T> T load(Class<T> clazz, Serializable id);

	/**
	 * 根据ID获取PO实例, 如果一级缓存不存在, 则从数据库中读取
	 * @param <T>
	 * 
	 * @param id
	 * @return 返回相应的持久化PO实例
	 */
	public <T> T get(Class<T> clazz, Serializable id);

	/**
	 * 获取PO的所有对象
	 * @param <T>
	 * 
	 * @return
	 */
	public <T> List<T> loadAll(Class<T> clazz);
	
	/**
	 * 保存PO
	 * @param <T>
	 * 
	 * @param entity
	 */
	public <T> Serializable save(T entity);

	/**
	 * 删除PO
	 * @param <T>
	 * 
	 * @param entity
	 */
	public <T> void delete(T entity);

	/**
	 * 更新PO
	 * @param <T>
	 * 
	 * @param entity
	 */
	public <T> void update(T entity);
	
	/**
	 * 保存或更新PO
	 * @param <T>
	 * 
	 * @param entity
	 */
	public <T> void saveOrUpdate(T entity);
	
	/**
	 * 
	 * @param <T>
	 * @param entity
	 */
	public <T> void merge(T entity);

	/**
	 * 执行带参的HQL查询
	 * 
	 * @param sql
	 * @param params
	 * @return 查询结果
	 */
	@SuppressWarnings("rawtypes")
	public List find(String hql, Object... params);
	/**
	 * 执行带参的HQL查询，返回count条数据
	 * @param <X>
	 * 
	 * @param sql
	 * @param params
	 * @return 查询结果
	 */
	public <X> List<X> find(final String hql, final int count, final Object... params);
	
	/**
	 * 按SQL查询结果, 
	 * 注与HQL混合使用时事项, 原生SQL查询不到在同一事务下session级save/delete等操作影响数据表
	 *  
	 * @param hql
	 * @param params
	 * @return List<Map<String, Object>>, key:大写表字段名
	 */
	public List<Map<String, Object>> findBySql(final String sql, final Object... params);
	
	
	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param params 数量可变的参数,按顺序绑定.
	 */
	public <X> X findUnique(final String hql, final Object... params);
	
    
	/**
	 * 对延迟加载的实体PO执行初始化
	 * 
	 * @param entity
	 */
	public void initialize(Object entity);
	
	/**
	 * 刷新session缓存, 将缓存同步数据库
	 */
	public void flush();
	
	/**
	 * 按HQL分页查询.
	 * 
	 * @param page 分页参数.不支持其中的orderBy参数.
	 * @param hql hql语句.
	 * @param params 数量可变的查询参数,按顺序绑定.
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询时的参数.
	 */
	public <X> Page<X>  findPage(final Page<X> page, final String hql, final Object... params);
	
	/**
	 * 按HQL分页查询.
	 * 
	 * @param page 分页参数.不支持其中的orderBy参数.
	 * @param hql hql语句.
	 * @param countHql 自定义查询总数, 用户未定义, 请赋值null
	 * @param params 数量可变的查询参数,按顺序绑定.
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询时的参数.
	 */
	public <X> Page<X> findPage(final Page<X> page, final String hql, final String countHql, final Object... params);
	
	/**
	 * 按SQL查询分页
	 * @param page 分页参数.不支持其中的orderBy参数.
	 * @param sql
	 * @param params 数量可变的查询参数,按顺序绑定.
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(final Page<Map<String, Object>> page, final String sql, final Object... params);
	/**
	 * 按SQL查询分页
	 * 注: 返回page封装数据result结构:List<Map<String, Object>>, key:如果查询 SQL 未写as, 则key取大写表字段名, 否则取as 命名
	 * @param page 分页参数.不支持其中的orderBy参数.
	 * @param sql
	 * @param countSql 自定义查询总数
	 * @param params 数量可变的查询参数,按顺序绑定.
	 * @return Page,
	 * 
	 */
	public Page<Map<String, Object>> findPageBySql(final Page<Map<String, Object>> page, final String sql, final String countSql, final Object... params);
	
	/**
	 * SQL查询记录, 返回对象列表(需实现rowMapper接口) 不支持分页
	 * @param <T>
	 * @param sql
	 * @param rowMapper
	 * @param params
	 * @return
	 */
	public <T> List<T> findByNativeSql(final String sql, final RowMapper<T> rowMapper, final Object... params);
	
	public <T> Page<T> findPageByNativeSql(final Page<T> page,final String sql, final RowMapper<T> rowMapper, final Object... params);
	
	public <T> Page<T> findPageByNativeSql(final Page<T> page,final String sql,final String countSql ,final RowMapper<T> rowMapper, final Object... params);
	
	/**
	 * SQL查询记录, 返回按顺序查询字段 不支持分页
	 * @param nativeSQL
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> findListsByNativeSQL(String nativeSQL, final Object... params);
	
	/**
	 * SQL查询记录, 不支持分页, 外层Map key:primaryKey在库表中的值, 内层Map: 表示一条数据记录
	 * @param nativeSQL
	 * @param primaryKey 主键在数据库中的标识
	 * @param params
	 * @return
	 */
	public Map<Long, Map<String, Object>> findMapsByNativeSQL(String nativeSQL, final String primaryKey, final Object... params);
	
	/**
	 * 批量执行hql语句, 包括删除和更新
	 * 注: 可执行批量更新操作, 立即执行相关HQL语句操作, 与session级缓存同步(在删除或更新前自动检查session缓存脏数据)
	 * eg: hql: delete from User u where u.userName = ?
	 * 
	 * @param hql
	 * @param params
	 * @return 返回影响行数
	 */
	public Long executeUpdate(final String hql, final Object... params);
	
	/**
	 * 批量执行sql语句, 包括删除和更新
	 * 注: 可执行批量更新操作, 立即执行相关SQL语句操作, 因此需要注意:要处理hibernate session缓存中的Java对象(需要手动删除session脏缓存数据)
	 * @param hql
	 * @param params
	 * @return 返回影响行数
	 */
	public Long executeUpdateBySql(final String sql, final Object... params);
	
	/**根据表明获取流水号
	 * @param tableName
	 * @return
	 */
	public String getSeqNo(String tableName);
	
}
