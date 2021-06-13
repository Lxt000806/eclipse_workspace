package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.SignIn;
import com.house.home.entity.design.DesignDemo;
import com.house.home.entity.project.WorkerArrange;
import com.house.home.entity.query.SignInPic;


@SuppressWarnings("serial")
@Repository
public class SignInDao extends BaseDao {
	/**
	 * SignIn分页信息
	 * 
	 * @param page
	 * @param signIn
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SignIn signIn) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select  case when a.custScore is null or custScore = '' then ''" +
				"	 else cast (a.custScore as char(1)) + '星' end custScore,a.custRemarks ,a.PK, a.CustCode, a.SignCZY, a.CrtDate, a. Longitude, a.Latitude, a.Address, a.ErrPosi, b.address custAddress, c.NOTE errposidescr, " 
				+ " em.NameChi signczydescr, dm1.desc1 department1, dm2.desc1 department2,tst2.Descr signInType2Descr,a.No,a.PrjItem,d.Descr,a.Remarks, "
				+ " (case when a.IsPass='1' then '是' else '否' end )IsPass, g.descr worktype12descr from tSignIn a "
				+ " left join tCustomer b on a.custCode=b.code " 
				+ " left join tXTDM c on a.ErrPosi=c.CBM and c.ID='YESNO' "
				+ " left join tEmployee em on a.signCZY=em.Number " 
				+ " left join tDepartment1 dm1 on em.Department1=dm1.Code " 
			    + " left join tDepartment2 dm2 on em.Department2=dm2.Code "
				+ " left join tSignInType2 tst2 on tst2.Code = a.SignInType2 "
			    + " left join tPrjItem1 d on a.PrjItem=d.Code "
				+ " left join ( "
				+ "		select in_a.No, max(TechCode) TechCode "
				+ "		from tSignInPic in_a "
				+ "		where in_a.TechCode is not null and in_a.TechCode <> '' "
				+ " 	group by in_a.No "
				+ "	) e on e.No = a.no "
				+ " left join tTechnology f on e.TechCode = f.Code "
				+ " left join tWorkType12 g on g.Code = f.WorkType12 "	
				+ " where 1=1 ";

    	if(signIn.getDateFrom()!=null){
    		sql+="and a.CrtDate>=? ";
			list.add(signIn.getDateFrom());
		}
    	if(signIn.getDateTo()!=null){
    		sql+="and a.CrtDate-1<=? ";
    		list.add(signIn.getDateTo());
    	}
    	if(StringUtils.isNotBlank(signIn.getCustScoreSignIn())){
    		sql+=" and a.custScore in ('"+signIn.getCustScoreSignIn().replaceAll(",", "','")+"')";
    	}
    	if (StringUtils.isNotBlank(signIn.getErrPosi())) {
			sql += " and a.errPosi=? ";
			list.add(signIn.getErrPosi());
		}
		if (StringUtils.isNotBlank(signIn.getDepartment1())) {
			sql += " and em.Department1=? ";
			list.add(signIn.getDepartment1());
		}
    	if (StringUtils.isNotBlank(signIn.getDepartment2())) {
			sql += " and em.Department2=? ";
			list.add(signIn.getDepartment2());
		}
    	if (signIn.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(signIn.getPk());
		}
    	if(StringUtils.isNotBlank(signIn.getAddress())){
			sql += " and b.Address like ?";
			list.add("%"+signIn.getAddress()+ "%");
		}
    	if (StringUtils.isNotBlank(signIn.getIsPass())) {
			sql += " and a.IsPass=? ";
			list.add(signIn.getIsPass());
		}
    	if (StringUtils.isNotBlank(signIn.getSignCzy())) {
    		if(this.isNumer(signIn.getSignCzy().trim())){
    			sql += " and a.SignCZY=? ";
    			list.add(signIn.getSignCzy());
    		}else{
    			sql += " and em.NameChi like ? ";
    			list.add("%"+signIn.getSignCzy()+ "%");
    		}
		}
    	if (signIn.getCrtDate() != null){
			sql += " and CONVERT(VARCHAR(10),a.crtDate,112)=CONVERT(VARCHAR(10),?,112) ";
			list.add(signIn.getCrtDate());
		}
    	if(StringUtils.isNotBlank(signIn.getSignInType2())){
    		if("null".equals(signIn.getSignInType2())){
    			sql += " and a.SignInType2 is null ";
    		}else{
    			sql += " and a.SignInType2=? ";
    			list.add(signIn.getSignInType2());
    		}
    	}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.CrtDate desc";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}

	
	public Page<Map<String,Object>> findPageBySql_CZY(Page<Map<String,Object>> page, SignIn signIn) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select signczy, signczydescr, department1, department2, count(signczydescr) totalsignindescr, " 
				+ "       count(distinct a.custcode) num, count(distinct convert(varchar(100), a.crtDate, 112)) days, "
		        + "       (select count(in_a.Code) from tCustomer in_a where in_a.Status = '4' and in_a.ProjectMan = signczy) constructioncount "
				+ " from ("
				+ "     select case when a.custScore is null or a.custScore = '' then ''"
				+ "	        else cast (a.custScore as char(1)) + '星' end custScore,"
				+ "         a.custRemarks,a.PK, a.CustCode, a.SignCZY, a.CrtDate, a. Longitude, "
				+ "         a.Latitude, a.Address, a.ErrPosi, "
				+ "         b.address custAddress, c.NOTE errposidescr, " 
				+ "         em.NameChi signczydescr, dm1.desc1 department1, dm2.desc1 department2 "
				+ "     from tSignIn a "
				+ "     left join tCustomer b on a.custCode=b.code " 
				+ "     left join tXTDM c on a.ErrPosi=c.CBM and c.ID='YESNO' "
				+ "     left join tEmployee em on a.signCZY=em.Number " 
				+ "     left join tDepartment1 dm1 on em.Department1=dm1.Code " 
			    + "     left join tDepartment2 dm2 on em.Department2=dm2.Code "
				+ "     where 1=1 ";

		if (StringUtils.isNotBlank(signIn.getDepartment1())) {
			sql += " and em.Department1=? ";
			list.add(signIn.getDepartment1());
		}
    	if (StringUtils.isNotBlank(signIn.getDepartment2())) {
			sql += " and em.Department2=? ";
			list.add(signIn.getDepartment2());
		}
    	if(StringUtils.isNotBlank(signIn.getCustScoreSignIn())){
    		sql+=" and a.custScore in ('"+signIn.getCustScoreSignIn().replaceAll(",", "','")+"')";
    	}
    	if (StringUtils.isNotBlank(signIn.getSignCzy())) {
    		if(this.isNumer(signIn.getSignCzy().trim())){
    			sql += " and a.SignCZY=? ";
    			list.add(signIn.getSignCzy());
    		}else{
    			sql += " and em.NameChi like ? ";
    			list.add("%"+signIn.getSignCzy()+ "%");
    		}
		}
    	if (StringUtils.isNotBlank(signIn.getErrPosi())) {
			sql += " and a.errPosi=? ";
			list.add(signIn.getErrPosi());
		}
    	if(signIn.getDateFrom()!=null){
    		sql+="and a.CrtDate>=? ";
			list.add(signIn.getDateFrom());
		}
    	if(signIn.getDateTo()!=null){
    		sql+="and a.CrtDate-1<=? ";
    		list.add(signIn.getDateTo());
    	}
    	if(StringUtils.isNotBlank(signIn.getSignInType2())){
    		sql += " and a.SignInType2=? ";
    		list.add(signIn.getSignInType2());
    	}
    	if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a group by signczy, signczydescr, department1, department2 order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a group by signczy, signczydescr, department1, department2 order by signczydescr ";
		}
    	List<Map<String, Object>>resultList=this.findListBySql(sql, list.toArray());
    	page.setResult(resultList);
    	page.setTotalCount(resultList.size());
		return page;
	}
	/**获取当天签到次数
	 * @param signIn
	 * @return
	 */
	public long getSignCountNow(SignIn signIn) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select pk from tSignIn a where CONVERT(VARCHAR(10),GETDATE(),112)=CONVERT(VARCHAR(10),a.crtDate,112) ";

    	if (StringUtils.isNotBlank(signIn.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(signIn.getCustCode());
		}
    	if (StringUtils.isNotBlank(signIn.getSignCzy())) {
			sql += " and a.SignCZY=? ";
			list.add(signIn.getSignCzy());
		}
		return this.countSqlResult(sql, "", list.toArray());
	}
	public static boolean isNumer(String str){ 
	    Pattern pattern = Pattern.compile("[0-9]*"); 
	    return pattern.matcher(str).matches();    
	 } 
	
	public List<Map<String,Object>> getSignInType1List(){
		String sql = " select Code,Descr from tSignInType1 where Expired='F' ";
		return this.findBySql(sql, new Object[]{});
	}
	
	public List<Map<String,Object>> getSignInType2List(String signInType1){
		String sql = " select Code,Descr,IsNeedPic,IsNeedPrjItem from tSignInType2 where SignItemType1=? and Expired='F' ";
		return this.findBySql(sql, new Object[]{signInType1});
	}
	
	public List<Map<String,Object>> getPrjItemList(String custCode, String prjItem){
		List<Object> params = new ArrayList<Object>();
		String sql = " select a.PK,b.Code PrjItem,b.Descr PrjItemDescr,a.BeginDate,b.prjphotoNum, "
				+" case when (b.IsConfirm='1' and c.Status is null) then '1'  else '0' end isConfirm,a.EndDate " +
				" from tPrjItem1 b"
				+" left join tPrjProg a on a.PrjItem=b.Code  and a.CustCode = ? "
				+" left  join tPrjConfirmApp c on a.CustCode=c.CustCode and a.PrjItem=c.PrjItem "
				+" where ((b.IsConfirm = '1' or a.CustCode is not null ) " +
				"	and (a.EndDate is null or datediff(day, a.EndDate, getdate()) <= 3  )  )";
		params.add(custCode);
		if(StringUtils.isNotBlank(prjItem)) {
			sql += " and b.Code = ? ";
			params.add(prjItem);
		}
		sql += " order  by b.Seq ";
		return this.findBySql(sql, params.toArray());
	}

	public SignInPic getPicByName(String photoName) {
		String hql = "from SignInPic where photoName=?";
		List<SignInPic> list = this.find(hql, new Object[]{photoName});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public Page<Map<String,Object>> goJqGrid_SignInType2(Page<Map<String,Object>> page, SignIn signIn){
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from ( "
				   + " 		select case when tsit2.Descr is null then '历史记录' else tsit2.Descr end signInType2Descr,count(*) signInCount,tsit2.Code signInType2 "
				   + " 		from tSignIn tsi "
				   + " 		left join tSignInType2 tsit2 on tsit2.Code = tsi.SignInType2 "
				   + " 		left join tEmployee te on tsi.signCZY=te.Number " 
				   + " 		left join tDepartment1 td1 on te.Department1=td1.Code " 
				   + " 		left join tDepartment2 td2 on te.Department2=td2.Code "
				   + "      where 1=1 ";

		if (StringUtils.isNotBlank(signIn.getDepartment1())) {
			sql += " and te.Department1=? ";
			list.add(signIn.getDepartment1());
		}
    	if (StringUtils.isNotBlank(signIn.getDepartment2())) {
			sql += " and te.Department2=? ";
			list.add(signIn.getDepartment2());
		}
    	if (StringUtils.isNotBlank(signIn.getSignCzy())) {
    		if(this.isNumer(signIn.getSignCzy().trim())){
    			sql += " and tsi.SignCZY=? ";
    			list.add(signIn.getSignCzy());
    		}else{
    			sql += " and te.NameChi like ? ";
    			list.add("%"+signIn.getSignCzy()+ "%");
    		}
		}
    	if (StringUtils.isNotBlank(signIn.getErrPosi())) {
			sql += " and tsi.errPosi=? ";
			list.add(signIn.getErrPosi());
		}
    	if(signIn.getDateFrom()!=null){
    		sql+="and tsi.CrtDate>=? ";
			list.add(signIn.getDateFrom());
		}
    	if(signIn.getDateTo()!=null){
    		sql+="and tsi.CrtDate-1<=? ";
    		list.add(signIn.getDateTo());
    	}
		sql += " group by tsit2.Descr,tsit2.Code ) a where 1=1 ";
		if(StringUtils.isNotBlank(signIn.getSignInType2())){
			sql += " and a.signInType2=? ";
			list.add(signIn.getSignInType2());
		}
    	if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += "order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> goJqGrid_DetailSignInType2(Page<Map<String,Object>> page, SignIn signIn){
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from ( "
				   + " 		select case when tsit2.Descr is null then '历史记录' else tsit2.Descr end signInType2Descr,count(*) signInCount,tsit2.Code signInType2 "
				   + " 		from tSignIn tsi "
				   + " 		left join tSignInType2 tsit2 on tsit2.Code = tsi.SignInType2 "
				   + " 		left join tEmployee te on tsi.signCZY=te.Number " 
				   + " 		left join tDepartment1 td1 on te.Department1=td1.Code " 
				   + " 		left join tDepartment2 td2 on te.Department2=td2.Code "
				   + "      where 1=1 ";

		if (StringUtils.isNotBlank(signIn.getDepartment1())) {
			sql += " and te.Department1=? ";
			list.add(signIn.getDepartment1());
		}
    	if (StringUtils.isNotBlank(signIn.getDepartment2())) {
			sql += " and te.Department2=? ";
			list.add(signIn.getDepartment2());
		}
    	if (StringUtils.isNotBlank(signIn.getSignCzy())) {
    		if(this.isNumer(signIn.getSignCzy().trim())){
    			sql += " and tsi.SignCZY=? ";
    			list.add(signIn.getSignCzy());
    		}else{
    			sql += " and te.NameChi like ? ";
    			list.add("%"+signIn.getSignCzy()+ "%");
    		}
		}
    	if (StringUtils.isNotBlank(signIn.getErrPosi())) {
			sql += " and tsi.errPosi=? ";
			list.add(signIn.getErrPosi());
		}
    	if(signIn.getDateFrom()!=null){
    		sql+="and tsi.CrtDate>=? ";
			list.add(signIn.getDateFrom());
		}
    	if(signIn.getDateTo()!=null){
    		sql+="and tsi.CrtDate-1<=? ";
    		list.add(signIn.getDateTo());
    	}
		sql += " group by tsit2.Descr,tsit2.Code ";
    	if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public List<Map<String,Object>> goJqGridSignInPicList(Page<Map<String,Object>> page, String no){
		String sql = " select * from ( "
				   + " 		select tsip.photoname,tsip.lastUpdate "
				   + " 		from tSignInPic tsip "
				   + " 		where tsip.No=? "; 
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.lastUpdate ";
		}
		return this.findBySql(sql, new Object[]{no});
	}
	

	public List<Map<String, Object>> findNoSendYunPic(){
		String sql = "select top 100 PhotoName,PK from tSignInPic where LastUpdate >= dateadd(day,-30,getdate()) and (IsSendYun = '0' or IsSendYun is null or IsSendYun = '') order by PK desc ";
		return this.findBySql(sql, new Object[]{});
	}
	
	public List<Map<String, Object>> findNoSendYunWorkSignPic(){
		String sql = " select top 100 twsp.PhotoName,tws.CustCode,twsp.PK "
				   + " from tWorkSignPic twsp "
				   + " left join tWorkSign tws on twsp.No = tws.No " 
				   + " where twsp.LastUpdate >= dateadd(day,-30,getdate()) and (twsp.IsSendYun = '0' or twsp.IsSendYun is null or twsp.IsSendYun = '') order by twsp.PK desc ";
		return this.findBySql(sql, new Object[]{});
	}
	
	public boolean existsFirstPass(String custCode, String prjItem){
		String sql = " select 1 from tSignIn where CustCode=? and PrjItem=? and IsPass='1' and SignInType2='05' ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{custCode, prjItem});
		if(list != null && list.size() > 0){
			return true;
		}
		return false;
	}
	/**
	 * 工地信息——工地签到(APP)
	 * @author	created by zb
	 * @date	2019-6-21--上午10:06:45
	 * @param page
	 * @param signIn
	 * @return
	 */
	public Page<Map<String, Object>> getPrjSignList(
			Page<Map<String, Object>> page, SignIn signIn) {
		String sql = " select a.Address,a.SignInType2,b.Descr SignInType2Descr,a.CrtDate,d.Descr prjItemDescr" +
				" ,c.NameChi signCzyDescr" +
				" from tSignIn a " +
				" left join tSignInType2 b on b.Code=a.SignInType2  " +
				" left join tEmployee c on c.Number = a.SignCZY" +
				" left join tPrjItem1 d on d.Code = a.PrjItem  " +
				" where a.SignCZY=?  " +
				" order by CrtDate desc ";
		return this.findPageBySql(page, sql, new Object[]{signIn.getSignCzy()});
	}
	
	public Long updateEndDate(String custCode, String prjItem1) {
	    String sql = " update tCustWorker "
	    		+" set EndDate=? "
	    		+" from tCustWorker a "
	    		+" inner join dbo.tWorkType12 b on a.WorkType12=b.Code "
	    		+" where a.CustCode=? and b.PrjItem=? and (a.Enddate is null or a.EndDate='') ";
	    
	    return executeUpdateBySql(sql, new Date(), custCode, prjItem1);
	}
	
	public Long saveFirstAddConfirmApp(String custCode, String prjItem1, String isPass) {
	    String sql = " insert into tPrjConfirmApp (CustCode, Status, PrjItem, AppDate, Remarks, RefConfirmNo, LastUpdatedBy, Expired," +
	    		"								ActionLog, LastUpdate, WorkerCode, workSignPK)" +
	    		" select ?, '1', ?, getdate(),case when ? = '1' then '初检通过触发' else '初检不通过触发' end ,null,'1','F','ADD',getdate(),null,null " +
	    		" from tPrjItem1 a " +
	    		" where Code = ? and (a.FirstAddConfirm = '3' or (? = '1' and a.FirstAddConfirm = '1' ) or (? = '0' and a.FirstAddConfirm = '2' ))";
	    
	    return executeUpdateBySql(sql, custCode,prjItem1, isPass, prjItem1, isPass, isPass);
	}
	
	public Long updatePrjProgEndDate(String custCode, String prjItem1, String signCzy) {
	    String sql = "update tPrjProg set ConfirmCZY = ?, ConfirmDate = getdate() ,LastUpdate = getdate() " +
	    		" where CustCode = ? and PrjItem = ? ";
	    
	    return executeUpdateBySql(sql, signCzy, custCode, prjItem1);
	}
	
	public List<Map<String,Object>> getPrjProgList(String custCode){
		String sql = " select a.PrjItem, b.Descr PrjItemDescr from tPrjProg a "
				+" left join tPrjItem1 b on a.PrjItem = b.Code "
				+" where a.Expired = 'F' and a.CustCode = ? and (a.EndDate is null or datediff(day,a.EndDate,getdate()) <= 3) "
				+" order by b.Seq asc";
		return this.findBySql(sql, new Object[]{custCode});
	}
	
	public List<Map<String, Object>> getSignInPic(String no) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.PK,a.No,a.photoName,a.isSendYun from tSignInPic a " 
				+" where 1=1 ";
		
		if(StringUtils.isNotBlank(no)){
			sql+=" and a.no = ? ";
			list.add(no);
		}

		return this.findBySql(sql, list.toArray());
	}
	
	public Boolean existsDesignPicPrgChange(String custCode) {
		String sql = " select 1 from tDesignPicPrg where DrawNoChg = '0' and CustCode = ? ";

		List<Map<String, Object>> list =  this.findBySql(sql, new Object[]{custCode});
		if(list.size()>0) {
			return true;
		}
		return false;
	}
}
