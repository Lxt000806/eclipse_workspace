package com.house.home.service.basic;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Xtdm;

public interface XtdmService extends BaseService {

	/**系统代码分页信息
	 * @param page
	 * @param xtdm
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Xtdm xtdm);
	
	/**根据id，cbm，ibm查询系统代码
	 * @param id
	 * @param cbm
	 * @param ibm
	 * @return
	 */
	public Xtdm getByThree(String id, String cbm, Integer ibm);
	
	/**根据id和cbm查询系统代码
	 * @param id
	 * @param cbm
	 * @return
	 */
	public Xtdm getByIdAndCbm(String id, String cbm);
	
	/**根据id查询系统代码
	 * @param id
	 * @return
	 */
	public List<Xtdm> getById(String id);
	
	public Map<String,Object> getFtpData();
	
	/**根据id和note查询系统代码
	 * @param id
	 * @param cbm
	 * @return
	 */
	public Xtdm getByIdAndNote(String id, String note);

    List<Map<String, Object>> getSourcesOrChannels(int level, String code);
}
