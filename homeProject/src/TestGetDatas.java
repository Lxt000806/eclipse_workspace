import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import com.house.framework.commons.utils.DbUtil;

public class TestGetDatas {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String sql = "declare @ConfirmBegin datetime=cast(? as datetime) "
		+"declare @IsCheckDept varchar(10)=? "
		+"select * from (	select   100000.000 as distance,a.Code, a.Address,a.descr custdescr, " 
		+"a.BuilderCode, "
		+"datediff(dd,isnull(i.date,a.ConfirmBegin),getdate()) noCheckDate , " 
		+"i.Date lastPrjProgCheckDate , " 
		+"j.ModifyCount  from tcustomer a "   
		+"left join (  select  m.CustCode, max(m.no) no from tPrjProgCheck m where m.IsCheckDept= @IsCheckDept " 
		+"group by  m.CustCode  ) i1 on a.code = i1.CustCode "   
		+"left join tPrjProgCheck  i on i.no=i1.no "
		+"LEFT JOIN (  select max(Pk) pk,Custcode from tPrjProg a "   
		+"where a.Begindate=(select max(Begindate) from tPrjProg  where custcode=a.CustCode )  group by a.CustCode) pp " 
		+"ON pp.Custcode=a.Code "    
		+"left join tPrjprog sj on sj.pk=pp.pk and sj.prjitem <> '15' "
		+"left join ( select  CustCode, sum(case when IsModify = '1' then 1 else 0 end) ModifyCount "  
		+"from  tPrjProgCheck group by CustCode   ) j on j.CustCode = a.code " 
		+"left join (  	select CustCode, max(Date) Date from tPrjProgCheck where IsCheckDept= 0 	group by CustCode  ) k " 
		+"on a.code=k.CustCode "  
		+"where a.status='4'   and a.ConfirmBegin < @ConfirmBegin and a.constructStatus  in ('1','3') "   
		+"and not exists( select 1 from tPrjProgCheck  where CustCode=a.code and IsCheckDept= @IsCheckDept and date>=dateadd(d,-1,@ConfirmBegin) ) " 
		+"and sj.prjitem <> '15' "  
		+"and not exists( select 1 from tProgCheckPlanDetail mx  left join tProgCheckPlan pc on pc.no=mx.PlanNo "  
		+"where CustCode=a.code and  pc.IsCheckDept= @IsCheckDept   and CrtDate>=dateadd(dd,-1,cast(getdate() as date)) and pc.Expired='F'  ) "
		+" )a " 
		+"order by a.distance,a.noCheckDate desc ";
//		String sql = "select top 5 code,source,descr codeDescr,BeginDate,Area,BaseFee from tCustomer";
		String sql2 = "exec pJcszcx 'CT000208' ";
		Object o1 = "2018-04-18",o2 = "0";
		List<Object> params = Arrays.asList(o1, o2);
//		List<Map<String,Object>> list = DbUtil.getListBySql(sql, params);
		List<Map<String,Object>> list = DbUtil.getListBySql(sql2, null);
		if (list!=null && list.size()>0){
			for (Map<String,Object> map : list){
				for (Entry<String, Object> m : map.entrySet()){
					System.out.println(m.getKey());
					System.out.println(m.getValue());
				}
			}
			System.out.println(list.size());
		}
	}
	
	public static Object getObjectByRs(ResultSet rs,String colType,int j){
		Object object = null;
		try{
			if ("datetime".equals(colType)){
				object = rs.getDate(j);
			}else if("int".equals(colType)){
				object = rs.getInt(j);
			}else if("money".equals(colType)){
				object = rs.getDouble(j);
			}else{
				object = rs.getString(j);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return object;
	}
	
}
