package com.house.framework.commons.utils;

import org.apache.commons.lang3.StringUtils;
import com.house.framework.web.login.UserContext;

/**
 * 
 *功能说明:sql工具类
 *
 */
public class SqlUtil {
	
	/**删除ORDER BY正则表达式**/
//	private static final String ORDER_BY_REGEX = "ORDER[\\s]*BY[\\s]*[^\\s]*(\\s*(ASC|DESC))?";
	private static final String ORDER_BY_REGEX_UPPER = "ORDER[\\s]*BY[\\s]*[^\\)]*";
	private static final String ORDER_BY_REGEX_LOWER = "order[\\s]*by[\\s]*[^\\)]*";
	
	public static String removeOrderBy(String initSql){
		String result = initSql.replaceAll(ORDER_BY_REGEX_UPPER, "").replaceAll(ORDER_BY_REGEX_LOWER, "");
		return result;
	}
	
	/**1,2,3转'1','2','3'
	 * @param status
	 * @return
	 */
	public static String resetStatus(String status){
		StringBuffer result = new StringBuffer("");
		if (StringUtils.isNotBlank(status)){
			String[] arr = status.split(",");
			for (String str : arr){
				result.append("'").append(str).append("',");
			}
		}
		return result.toString().substring(0, result.length()>0?result.length()-1:result.length());
	}
	
	/**1,2,3转'1,2,3'
	 * @param status
	 * @return
	 */
	public static String resetIntStatus(String status){
		StringBuffer result = new StringBuffer("");
		if (StringUtils.isNotBlank(status)){
			String[] arr = status.split(",");
			for (String str : arr){
				if (StringUtils.isNotBlank(str)){
					result.append(Integer.parseInt(str)).append(",");
				}
			}
		}
		return result.toString().substring(0, result.length()>0?result.length()-1:result.length());
	}
	
	/**返回操作员对客户的查看权限的限制SQL拼接语句,
	 * type:权限限制类型，默认为0，控制到干系人中的所有人进行匹配限制，1：只从设计师业务员上进行匹配限制
	 * 
	 * @param uc
	 * @param tableAlias
	 * @param type
	 * @return
	 */
	public static String getCustRight(UserContext uc,String tableAlias, int type){
		String role = "";
		if(type==1){
			role = "00,01,20,24";
		}
		return getCustRight(uc,tableAlias,role);
	}
	
	public static String getCustRight(UserContext uc,String tableAlias,String role){
		String result = " 1<>1 ";
		try{
			if (uc!=null && StringUtils.isNotBlank(tableAlias)){
				tableAlias = tableAlias.trim();
				String emnum = uc.getEmnum().trim();
				String czybh = uc.getCzybh().trim();
				String custRight = uc.getCustRight();
				if ("1".equals(custRight)){
					if (StringUtils.isBlank(role)){
						result = " ( (exists(select 1 from tCustStakeholder pub_CS "
	                    + " where pub_CS.CustCode="+tableAlias+".Code ";
	                    result+= " and pub_CS.EmpCode='"+emnum+"')) ";
					}else{
						result = " ( (exists(select 1 from tCustStakeholder pub_CS "
	                    + " where pub_CS.Role in"+"('"+role.replaceAll(",", "','")+"')"+" and pub_CS.CustCode="+tableAlias+".Code "
	                    + " and pub_CS.EmpCode='"+emnum+"')) ";
					}
					result = result + " or (exists(select 1 from tCZYSpcBuilder where czybh='"+czybh+"' and SpcBuilder="+tableAlias+".builderCode)) )";
				}else if("2".equals(custRight)){
					if (StringUtils.isBlank(role)){
						result = " ( (exists(select 1 from tCustStakeholder pub_CS "
	                    + " where pub_CS.CustCode="+tableAlias+".Code ";
	                    result+= " and exists(select 1 from temployee pub_EP_a "
	                    + " inner join tCZYDept pub_EP_b "
	                    + " on (pub_EP_a.department1=pub_EP_b.department1 ) "
	                    + " and ((pub_EP_a.department2=pub_EP_b.department2) or (pub_EP_b.department2='') or (pub_EP_b.department2 is null)) "
	                    + " and ((pub_EP_a.department3=pub_EP_b.department3) or (pub_EP_b.department3='') or (pub_EP_b.department3 is null)) "
	                    + " and pub_EP_b.CZYBH='"+czybh+"'"
	                    + " and pub_EP_a.number = pub_CS.empcode))) ";
					}else{
						result = " ( (exists(select 1 from tCustStakeholder pub_CS "
	                    + " where pub_CS.Role in "+"('"+role.replaceAll(",", "','")+"')"+" and pub_CS.CustCode="+tableAlias+".Code "
	                    + " and exists(select 1 from temployee pub_EP_a "
	                    + " inner join tCZYDept pub_EP_b "
	                    + " on (pub_EP_a.department1=pub_EP_b.department1 ) "
	                    + " and ((pub_EP_a.department2=pub_EP_b.department2) or (pub_EP_b.department2='') or (pub_EP_b.department2 is null)) "
	                    + " and ((pub_EP_a.department3=pub_EP_b.department3) or (pub_EP_b.department3='') or (pub_EP_b.department3 is null)) "
	                    + " and pub_EP_b.CZYBH='"+czybh+"'"
	                    + " and pub_EP_a.number = pub_CS.empcode))) ";
					}
					result = result + " or (exists(select 1 from tCZYSpcBuilder where czybh='"+czybh+"' and SpcBuilder="+tableAlias+".builderCode)) )";
				}else if("3".equals(custRight)){
					result = " 1=1 ";
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	/**
	 * 根据操作员获取判断客户权限（1.查看本人 2.查看本部门(本部门+权限部门) 3.查看所有）的sql
	 * @author cjg
	 * @date 2020-1-3
	 * @param uc
	 * @param czyAlias 要控制权限的人员字段名：如a.BusinessMan
	 * @param pathAlias 要控制权限的人员的归属部门路径（关联到tDepartment）：如g.path
	 * @return
	 */
	public static String getCustRightByCzy(UserContext uc,String czyAlias,String pathAlias){
		String result="";
		if (uc!=null ){
			String czybh = uc.getCzybh().trim();
			String custRight = uc.getCustRight();
			if("1".equals(custRight)){
				result+=" and "+czyAlias+"='"+czybh+"'";
			}else if("2".equals(custRight)){
				result+=" and ("+pathAlias+" like '%'+(select Department from tEmployee where Number='"+czybh+"')+'%' " 
				+"or " 
				+"  exists (" 
				+"    select 1 from ( "
				+"      select case when d.Department is not null then d.Department when c.Department is not null then c.Department " 
				+"      else b.Department end Department "
				+"      from tCzyDept a " 
				+"      left join tDepartment1 b on a.Department1=b.Code " 
				+"      left join tDepartment2 c on a.Department2=c.Code " 
				+"      left join tDepartment3 d on a.Department3=d.Code " 
				+"      where CZYBH='"+czybh+"'"
				+"    )a where "+pathAlias+" like '%'+a.Department+'%' " 
				+"  )" 
				+") ";
			}
		}
		return result;
	}
	
	/**
	 * 在字符串(不包含以数字开头的字符串)从左边开始第一次出现数字的地方插入百分号
	 * 淮安半岛10#101 -> 淮安半岛%10#101
	 * 
	 * @param keyword
	 * @return
	 * @author 张海洋
	 */
	public static String insertPercentSignBeforeFirstDigit(String keyword) {
	    if (keyword == null) throw new NullPointerException();
	    StringBuilder result = new StringBuilder();
	    
	    // 如果以数字开头，直接返回原始字符串
	    if (Character.isDigit(keyword.charAt(0))) return keyword;
	    
	    int i;
	    for (i = 0; i < keyword.length(); i++) {
            char ch = keyword.charAt(i);
            
            if (!Character.isDigit(ch)) {
                result.append(ch);
            } else {
                result.append("%").append(keyword.substring(i));
                break;
            }
        }
	    
	    return result.toString();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
