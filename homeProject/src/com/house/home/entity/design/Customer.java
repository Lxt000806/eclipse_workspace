package com.house.home.entity.design;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.crypto.Data;

import com.house.framework.commons.orm.BaseEntity;
import com.house.framework.commons.utils.XmlConverUtil;

/**
 * 客户信息表
 */
@Entity
@Table(name = "tCustomer")
public class Customer extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "Code",nullable = false)
	private String code;
	@Column(name = "Source")
	private String source;
	@Column(name = "Descr")
	private String descr;
	@Column(name = "Gender")
	private String gender;
	@Column(name = "Address")
	private String address;
	@Column(name = "Layout")
	private String layout;
	@Column(name = "Area")
	private Integer area;
	
	@Column(name = "WallArea6")
	private double wallArea6;
	@Column(name = "WallArea12")
	private double wallArea12;
	@Column(name = "WallArea18")
	private double wallArea18;
	@Column(name = "WallArea24")
	private double wallArea24;
	
	@Column(name = "Mobile1")
	private String mobile1;
	@Column(name = "Mobile2")
	private String mobile2;
	@Column(name = "QQ")
	private String qq;
	@Column(name = "Email2")
	private String email2;
	@Column(name = "Remarks")
	private String remarks;
	@Column(name = "CrtDate")
	private Date crtDate;
	@Column(name = "Status")
	private String status;
	@Column(name = "DesignMan")
	private String designMan;
	@Column(name = "BusinessMan")
	private String businessMan;
	@Column(name = "PlanSet")
	private Date planSet;
	@Column(name = "SetDate")
	private Date setDate;
	@Column(name = "PlanMeasure")
	private Date planMeasure;
	@Column(name = "MeasureDate")
	private Date measureDate;
	@Column(name = "PlanPlane")
	private Date planPlane;
	@Column(name = "PlaneDate")
	private Date planeDate;
	@Column(name = "PlanFacade")
	private Date planFacade;
	@Column(name = "FacadeDate")
	private Date facadeDate;
	@Column(name = "PlanPrice")
	private Date planPrice;
	@Column(name = "PriceDate")
	private Date priceDate;
	@Column(name = "PlanDraw")
	private Date planDraw;
	@Column(name = "DrawDate")
	private Date drawDate;
	@Column(name = "PlanPrev")
	private Date planPrev;
	@Column(name = "PrevDate")
	private Date prevDate;
	@Column(name = "PlanSign")
	private Date planSign;
	@Column(name = "SignDate")
	private Date signDate;
	@Column(name = "DesignFee")
	private Double designFee;
	@Column(name = "MeasureFee")
	private Double measureFee;
	@Column(name = "DrawFee")
	private Double drawFee;
	@Column(name = "ColorDrawFee")
	private Double colorDrawFee;
	@Column(name = "ManageFee")
	private Double manageFee;
	@Column(name = "BaseFee")
	private Double baseFee;
	@Column(name = "BaseDisc")
	private Double baseDisc;
	@Column(name = "ChgBaseFee")
	private Double chgBaseFee;
	@Column(name = "MainFee")
	private Double mainFee;
	@Column(name = "MainDisc")
	private Double mainDisc;
	@Column(name = "ChgMainFee")
	private Double chgMainFee;
	@Column(name = "SoftFee")
	private Double softFee;
	@Column(name = "SoftDisc")
	private Double softDisc;
	@Column(name = "ChgSoftFee")
	private Double chgSoftFee;
	@Column(name = "IntegrateFee")
	private Double integrateFee;
	@Column(name = "IntegrateDisc")
	private Double integrateDisc;
	@Column(name = "ChgIntFee")
	private Double chgIntFee;
	@Column(name = "CupboardFee")
	private Double cupBoardFee;
	@Column(name = "CupBoardDisc")
	private Double cupBoardDisc;
	@Column(name = "ChgCupFee")
	private Double chgCupFee;
	@Column(name = "ContractFee")
	private Double contractFee;
	@Column(name = "MaterialFee")
	private Double materialFee;
	@Column(name = "EndDate")
	private Date endDate;
	@Column(name = "EndCode")
	private String endCode;
	@Column(name = "EndRemark")
	private String endRemark;
	@Column(name = "LastUpdate")
	private Date lastUpdate;
	@Column(name = "LastUpdatedBy")
	private String lastUpdatedBy;
	@Column(name = "Expired")
	private String expired;
	@Column(name = "ActionLog")
	private String actionLog;
	@Column(name = "MainFee_Per")
	private Double mainFeePer;
	@Column(name = "ChgMainFee_Per")
	private Double chgMainFeePer;
	@Column(name = "IntegrateCost")
	private Double integrateCost;
	@Column(name = "CupBoardCost")
	private Double cupBoardCost;
	@Column(name = "IsIntgDesign")
	private String isIntgDesign;
	@Column(name = "IsFirstCal")
	private String isFirstCal;
	@Column(name = "FirstCalNo")
	private String firstCalNo;
	@Column(name = "IsSecCal")
	private String isSecCal;
	@Column(name = "SecCalNo")
	private String secCalNo;
	@Column(name = "IsSoftCal")
	private String isSoftCal;
	@Column(name = "SoftCalNo")
	private String softCalNo;
	@Column(name = "BaseFee_Dirct")
	private Double baseFeeDirct;
	@Column(name = "BaseFee_Comp")
	private Double baseFeeComp;
	@Column(name = "FirstPay")
	private Double firstPay;
	@Column(name = "SecondPay")
	private Double secondPay;
	@Column(name = "ThirdPay")
	private Double thirdPay;
	@Column(name = "FourPay")
	private Double fourPay;
	@Column(name = "IsDesignCal")
	private String isDesignCal;
	@Column(name = "DesignCalNo")
	private String designCalNo;
	@Column(name = "DesignStyle")
	private String designStyle;
	@Column(name = "BeginDate")
	private Date beginDate;
	@Column(name = "ConstructType")
	private String constructType;
	@Column(name = "PlanAmount")
	private Double planAmount;
	@Column(name = "Gift")
	private Double gift;
	@Column(name = "SoftOther")
	private Double softOther;
	@Column(name = "ContainBase")
	private Integer containBase;
	@Column(name = "ContainMain")
	private Integer containMain;
	@Column(name = "ContainSoft")
	private Integer containSoft;
	@Column(name = "ContainInt")
	private Integer containInt;
	@Column(name = "PayRemark")
	private String payRemark;
	@Column(name = "DiscRemark")
	private String discRemark;
	@Column(name = "AchievDed")
	private Double achievDed;
	@Column(name = "ContainCup")
	private Integer containCup;
	@Column(name = "ContainMainServ")
	private Integer containMainServ;
	@Column(name = "MainServFee")
	private Double mainServFee;
	@Column(name = "ChgMainServFee")
	private Double chgMainServFee;
	@Column(name = "ConfirmBegin")
	private Date confirmBegin;
	@Column(name = "ProjectMan")
	private String projectMan;
	@Column(name = "CheckMan")
	private String checkMan;
	@Column(name = "BeginComDate")
	private Date beginComDate;
	@Column(name = "ConstructDay")
	private Integer constructDay;
	@Column(name = "ConstructStatus")
	private String constructStatus;
	@Column(name = "ConsEndDate")
	private Date consEndDate;
	@Column(name = "PlanCheckOut")
	private Date planCheckOut;
	@Column(name = "CheckOutDate")
	private Date checkOutDate;
	@Column(name = "ConsRcvDate")
	private Date consRcvDate;
	@Column(name = "IsComplain")
	private String isComplain;
	@Column(name = "SendJobDate")
	private Date sendJobDate;
	@Column(name = "ConsArea")
	private String consArea;
	@Column(name = "SpecialDisc")
	private Double specialDisc;
	@Column(name = "DocumentNo")
	private String documentNo;
	@Column(name = "RepairCardDate")
	private Date repairCardDate;
	@Column(name = "RepairCardMan")
	private String repairCardMan;
	@Column(name = "Cddate")
	private Date cddate;
	@Column(name = "Cdman")
	private String cdman;
	@Column(name = "RepairRemark")
	private String repairRemark;
	@Column(name = "CustType")
	private String custType;
	@Column(name = "SoftBaseFee")
	private Double softBaseFee;
	@Column(name = "SoftFee_WallPaper")
	private Double softFeeWallPaper;
	@Column(name = "SoftFee_Curtain")
	private Double softFeeCurtain;
	@Column(name = "SoftFee_Light")
	private Double softFeeLight;
	@Column(name = "SoftFee_Furniture")
	private Double softFeeFurniture;
	@Column(name = "SoftFee_Adornment")
	private Double softFeeAdornment;
	@Column(name = "BaseCtrlAmount")
	private Double baseCtrlAmount;
	@Column(name = "BaseCtrlPer")
	private Double baseCtrlPer;
	@Column(name = "CheckStatus")
	private String checkStatus;
	@Column(name = "IntMsrDate")
	private Date intMsrDate;
	@Column(name = "IntDlyDay")
	private Integer intDlyDay;
	@Column(name = "CustCheckDate")
	private Date custCheckDate;
	@Column(name = "CheckDocumentNo")
	private String checkDocumentNo;
	@Column(name = "AgainMan")
	private String againMan;
	@Column(name = "BuilderCode")
	private String builderCode;
	@Column(name = "HaveReturn")
	private String haveReturn;
	@Column(name = "HaveCheck")
	private String haveCheck;
	@Column(name = "HavePhoto")
	private String havePhoto;
	@Column(name = "ProjectCtrlAdj")
	private Double projectCtrlAdj;
	@Column(name = "CtrlAdjRemark")
	private String ctrlAdjRemark;
	@Column(name = "MainSetFee")
	private Double mainSetFee;
	@Column(name = "SetMinus")
	private Double setMinus;
	@Column(name = "SetAdd")
	private Double setAdd;
	@Column(name = "LongFee")
	private Double longFee;
	@Column(name = "ManageFee_Base")
	private Double manageFeeBase;
	@Column(name = "ManageFee_Main")
	private Double manageFeeMain;
	@Column(name = "ManageFee_Int")
	private Double manageFeeInt;
	@Column(name = "ManageFee_Serv")
	private Double manageFeeServ;
	@Column(name = "ManageFee_Soft")
	private Double manageFeeSoft;
	@Column(name = "ManageFee_Cup")
	private Double manageFeeCup;
	@Column(name = "CustLevel")
	private String custLevel;//客户级别
	@Column(name = "ConstructRemark")
	private String constructRemark;//施工承诺字段
	@Column(name = "PrjProgTempNo")
	private String prjProgTempNo;//工程提醒模板编号
	@Column(name = "IsUpPosiPic")
	private String isUpPosiPic;
	@Column(name = "SaleType")
	private String saleType;
	@Column(name = "OldCode")
	private String oldCode;
	@Column(name = "NetChanel")
	private String netChanel;
	@Column(name = "IsInternal")
	private String isInternal; 
	@Column(name = "ComeTimes")
	private Integer comeTimes;
	@Column(name = "BuildPass")
	private String buildPass;
	@Column(name = "RewardRemark")
	private String rewardRemark;
	@Column(name="PrgRemark")
	private String prgRemark;
	@Column(name="RealAddress")
	private String realAddress;
	@Column(name="CanCancel")
	private String canCancel;
	@Column(name="RelCust")
	private String relCust;
	@Column(name="BuildSta")
	private String buildSta;
	@Column(name = "PayType")
	private String payType;
	@Column(name="BuilderNum")
	private String builderNum;
	@Column(name="IsItemCheck")
	private String isItemCheck;
	@Column(name="RealDesignFee")
	private Double realDesignFee;
	@Column(name="PrjDeptLeader")
	private String prjDeptLeader;
	@Column(name="SoftPlanRemark")
	private String softPlanRemark;
	@Column(name="ContractDay")
	private Integer contractDay;
	@Column(name="WallArea")
	private Double wallArea;
	@Column(name="PerfPercCode")
	private String perfPercCode;
	@Column(name="StdDesignFee")
	private Double stdDesignFee;
	@Column(name="ReturnDesignFee")
	private Double returnDesignFee;
	@Column(name="SoftTokenAmount")
	private Double softTokenAmount;
	@Column(name="SoftTokenNo")
	private String softTokenNo;
	@Column(name="MarketFund")
	private Double marketFund;
	@Column(name="Tax")
	private Double tax;
	@Column(name="Position")
	private String position;
	@Column (name="ConStaDate")
	private Date conStaDate;
	@Column (name="TileStatus")
	private String tileStatus;
	@Column (name="BathStatus")
	private String bathStatus;
	@Column (name = "PayeeCode")
	private String payeeCode;
	@Column (name="BaseTempNo")
	private String baseTempNo;
	@Column (name="MainTempNo")
	private String mainTempNo;
	@Column (name="ToConstructDate")
	private Date toConstructDate;
	
	@Column (name="ConPhone")
	private String conPhone;
	@Column (name="ConId")
	private String conId;
	@Column(name = "InnerArea")
	private Double innerArea;
	@Column(name = "CustAccountPk")
	private Integer custAccountPk;
	@Column(name="IsWaterItemCtrl")
	private String isWaterItemCtrl; //是否水电材料发包
	@Column(name="AppSoftRemark")
	private String appSoftRemark; //软装未下单说明
	@Column(name="PerfMarkup")
	private Double perfMarkup; //业绩
	@Column(name="InstallElev")
	private String installElev; //安装电梯
	@Column(name="SpecifyBaseCtrl")
	private String specifyBaseCtrl;
	@Column(name="CarryFloor")
	private Double carryFloor; //搬楼层数
	@Column(name="IsWaterCtrl")
	private String isWaterCtrl; //防水发包
	@Column(name="PrjDepartment1")
	private String prjDepartment1; //工程事业部
	@Column(name="PrjDepartment2")
	private String prjDepartment2; //工程部
	@Column(name="MtCustInfoPK")
	private Integer mtCustInfoPK; //麦田客户pk
	@Column(name="IsInitSign")
	private String isInitSign;
	@Column(name="VisitDate")
	private Date visitDate;
	@Column(name="PlanSendDate_Soft")
	private Date planSendDateSoft;
	@Column(name="MainItemOk")
	private String mainItemOk;
	@Column(name="DesignRiskFund")
	private Double designRiskFund; //设计师风控基金
	@Column(name="PrjManRiskFund")
	private Double prjManRiskFund; //项目经理风控基金
	@Column(name="PrgRmkDate")
	private Date PrgRmkDate;
	@Column(name="ToiletQty")
	private Double toiletQty;//app填写马桶数量
	@Column(name="CabinetQty")
	private Double cabinetQty;//app填写浴室柜数量
	@Column(name="IsHoliConstruct")
	private String isHoliConstruct;//是否周末施工
	@Column(name="GroupId")
	private Long groupId;
	@Column(name="DesignerMaxDiscAmount")
	private Double designerMaxDiscAmount; //设计师最高优惠额度
	@Column(name="DirectorMaxDiscAmount")
	private Double directorMaxDiscAmount; //总监最高优惠额度
	@Column(name="FrontEndDiscAmount")
	private Double frontEndDiscAmount; //前端承担金额
	@Column(name="CmpDiscAmount")
	private Double cmpDiscAmount; //公司承担额外赠送金额
	@Column(name="CarryRemark")
	private String carryRemark; //搬运说明
	@Column(name = "RepClause")
	private String repClause;
	@Column(name = "OldRepClause")
	private String oldRepClause;
	@Column(name = "ExpectMoveIntoDate")
	private Date expectMoveIntoDate;
	@Column(name = "AgainType")
	private String againType;
	@Column(name = "PerfPK")
	private Integer perfPK;
	
	@Transient
	private String projectManDescr;
	@Transient
	private String builderCodeDescr;
	@Transient
	private String designManDescr;
	@Transient
	private String businessManDescr;
	@Transient
	private String againManDescr;
	@Transient
	private String groupCode;
	@Transient
	private String role;
	@Transient
	private String empCode;
	@Transient
	private String department1;
	@Transient
	private String department2;
	@Transient
	private String department3;
	@Transient
	private Date setDateFrom;
	@Transient
	private Date setDateTo;
	@Transient
	private Date signDateFrom;
	@Transient
	private Date signDateTo;
	@Transient
	private Date crtDateFrom;
	@Transient
	private Date crtDateTo;
	@Transient
	private Date endDateFrom;
	@Transient
	private Date endDateTo;
	@Transient
	private String stakeholder;//干系人
	@Transient
	private String haveGd;//包含归档客户（默认不包含）
	@Transient
	private Date ConfirmBeginFrom;
	@Transient
	private Date ConfirmBeginTo;
	@Transient
	private String statistcsMethod; //统计方式
	@Transient
	private String statistcsDateType; //统计时间：1.按下定时间统计 2.按合同签订时间统计
	@Transient
	private String team;
	@Transient
	private String orderBy;
	@Transient
	private String checkStatusDescr;
	@Transient
	private String oldCodeDescr;
	@Transient
	private String disabledEle;//禁用元素
	@Transient
	private String itemType1;
	@Transient
	private Double baseFeeDirctPer;
	@Transient
	private Double baseFeeCompPer;
	@Transient
	private Double manageFeeBasePer;
	@Transient
	private Double manageFeeCupPer;
	@Transient
	private Double cupboardFeePer;
	@Transient
	private Double cupBoardDiscPer;
	@Transient
	private Integer areaPer;
	@Transient
	private String detailJson;
	@Transient
	private Integer softPK;
	@Transient
	private String longFeeCode;
	@Transient
	private Double mainSetFeePer;
	@Transient
	private Double setMinusPer;
	@Transient
	private Double setAddPer;
	@Transient
	private Double longFeePer;

	@Transient
	private Date custCheckDateFrom;
	@Transient
	private Date custCheckDateTo;
	@Transient
	private String isOutSet;
	@Transient
	private String softFee_WallPaper;
	@Transient
	private String softFee_Curtain;
	@Transient
	private String softFee_Light;
	@Transient
	private String softFee_Furniture;
	@Transient
	private String softFee_Adornment;
	@Transient
	private String confItem;
	@Transient
	private Date informDateFrom;
	@Transient
	private Date informDateTo;
	@Transient
	private Date ConfirmDateFrom;
	@Transient
	private Date ConfirmDateTo;
	@Transient
	private String confirmFinish;//1 确认完成 
	@Transient
	private String isOrder;//1 表示待预约
	
	@Transient
	private String prjProgTempNoDescr;
	@Transient
	private String isPrjProgTemp;
	@Transient
	private Date confirmEnd;
	@Transient
	private String mobileHidden;
	@Transient
	private String doExcelBefore;
	@Transient 
	private String custWorkApp;//工程部申请
	@Transient
	private String itemType2;
	@Transient
	private String softEmpCode;
	@Transient
	private String chengeCheckMan;
	@Transient 
	private String planPrjDescr;//计划进度
	@Transient 
	private String nowPrjDescr;//实际进度
	@Transient
	private String businessFlowDescr;//业务跟单人员
	@Transient
	private String delayDay;
	@Transient
	private String prjManCost;//项目经理结算
	@Transient
	private String consManCost;//工程经理结算
	@Transient
	private String custCountCost;//客户结算
	@Transient 
	private String zjxhj;//增减项合计
	@Transient 
	private String jczj;//基础增减
	@Transient 
	private String softCost;//软装合同额
	@Transient 
	private String inteCost;//集成合同额
	@Transient 
	private String baseCost;//基础合同额
	@Transient 
	private String mainCost;//主材合同额
	@Transient
	private String preloftsMan;//预放样员
	@Transient
	private String havePay;
	@Transient
	private String checkManDescr;
	@Transient 
	private Date beginDateFrom;
	@Transient
	private Date beginDateTo;
	@Transient
	private Date checkOutDateFrom;
	@Transient 
	private Date checkOutDateTo;
	@Transient
	private String preloftsManDescr;
	@Transient 
	private String oldPreloftsMan;
	@Transient 
	private String purchStatus;//采购到货明细查询是控制
	

	@Transient 
	private Double distance;
	@Transient 
    private Double lontitude;
	@Transient 
    private Double latitude;
	@Transient
	private String prjItem;
	@Transient
	private String inteEmpCode;
	@Transient
	private Integer intePk;
	@Transient
	private String prjProgTempType;
	@Transient
	private String CGDesignCode;
	@Transient
	private String CGDesignerDescr;
	@Transient
	private Integer cgPk;
	@Transient
	private String region;
	@Transient
	private String region2;
	
	@Transient
	private String saleIndependence;//是否独立销售,APP
	@Transient
	private String normConstructDay;
	@Transient 
	private String spcBuilder;
	@Transient
	private Double mainDiscPer;
	@Transient
	private Double manageFeeMainPer;
	@Transient
	private Double mainServFeePer;
	@Transient
	private Double manageFeeServPer;
	@Transient
	private String isContainOutPlan;//包含无预算客户，1是，0否
	@Transient
	private String mainPlanMan;//主材设计师
	@Transient
	private String mainBusinessMan;//主材管家
	@Transient
	private String declareMan;//主材报单员
	@Transient
	private String houseType;
	@Transient
	private String buildStatus;
	@Transient
	private String mainPlanManName;//主材设计师
	@Transient
	private String mainBusinessManName;//主材管家
	@Transient
	private String declareManName;//主材报单员
	@Transient
	private String monthNum;//月数
	@Transient
	private String tgyy;//月数
	@Transient
	private String custRight;//操作员的客户权限
	@Transient
	private String mapper;//绘图员
	@Transient
	private String sketcher;//效果图员
	@Transient
	private String mapperName;//绘图员
	@Transient
	private String sketcherName;//效果图员
	@Transient
	private String jcMan;//集成干系人
	@Transient
	private String rzMan;//软装干系人
	@Transient
	private String jcManName;//集成干系人
	@Transient
	private String rzManName;//软装干系人

	@Transient
	private String stakeholderRoll;

	@Transient 
	private String custSceneDesi;//现场设计师 
	@Transient
	private String custSceneDesiDescr;
	@Transient
	private String arrangeStatus;
	@Transient
	private String isSchemeDesigner;

	@Column(name="IsItemUp")
	private String isItemUp;// 材料升级标志
	@Transient 
	private String xtcs;//获取系统参数的
	@Transient 
	private int signMonth ;//月份
	@Transient
	private String department2Descr;
	@Transient
	private String softSteward;
	@Transient
	private String softStewardDescr;
	@Transient
	private String softStewardStatus;
	@Transient
	private Integer softStewardPk;
	@Transient
	private String isServiceItem;
	@Transient
	private String itemRight;
	@Transient
	private String itemType12;
	@Transient
	private Double manageFeeSoftPer;
	@Transient
	private Double manageFeeIntPer;
	
	@Transient
	private Date confirmStartDateFrom; //confirmStartDateFrom 确认开始时间-从
	@Transient
	private Date confirmStartDateTo; //confirmStartDateTo 确认开始时间-至
	@Transient
	private String statusDescr;
	@Transient
	private String isSave;
	@Transient
	private String fromStatus;
	@Transient
	private String toStatus;
	@Transient
	private String ignoreCustRight;
	@Transient
	private String viewAll;
	@Transient
	private String codes;
	@Transient
	private String supplierCode;
	@Transient
	private String JCZYDesignCode;//集成专员
	@Transient
	private String JCZYDesignerDescr;
	@Transient 
	private String laborFeeCustStatus;
	@Transient
	private Double softFeePer;
	@Transient
	private String custWorkerCustStatus;
	@Transient
	private Double  softDiscPer;
	@Transient
	private Double integrateFeePer;
	@Transient
	private Double integrateDiscPer;

	@Transient
	private String cantInstall;

	
	@Transient
	private String visitType;/*回访类型*/
	
	@Transient
	private String showType;
	
	@Transient
	private String IsCupboard;
	@Transient
	private String prjRegionCode;
	@Transient
	private String prjRegionCodeDescr;

	@Transient
	private String dept1Descr;
	@Transient
	private String dept2Descr;
	@Transient
	private String projectManPhone;
	@Transient
	private String isAddAllInfo;
	
	@Transient
	private String designPhone;
	@Transient
	private Double jzyjjs;//基础业绩基数
	@Transient
	private String isContainDraftsMan;//是否包含绘图员
	@Transient
	private String searchType;
	@Transient
	private String oldDesignMan;
	@Transient
	private String oldBusinessMan;
	@Transient
	private String oldAgainman;
	@Transient
	private String existsPrePlan;
	@Transient
	private Date sendDateFrom;
	@Transient
	private Date sendDateTo;
	@Transient
	private String designDemo;
	@Transient
	private String includEnd;
	@Transient
	private Data planEnd;
	@Transient
	private String isGetDetail; //是否获取材料成本明细(工地结算利润分析用) add by zb
	@Transient
	private String jsonString;
	@Transient
	private Integer isRight;//操作员是否超级管理员
	@Transient
	private Integer gxrPK;
	@Transient
	private String itemChgNo;
	@Transient
	private String baseItemCode;
	
	@Transient
	private String phoneOrConId; //用手机号/身份证号/客户编号查询
	@Transient
	private String no;
	@Transient
	private String workType1;
	@Transient
	private String workType2;
	@Transient
	private String itemPlanCustCode;
	@Transient
	private String designChgDoc;
	
	@Transient
	private String itemType12ZC;
	@Transient
	private String itemType12RZ;
	@Transient
	private Integer areaFrom;
	@Transient
	private Integer areaTo;
	@Transient 
	private String module;
	@Transient
	private String workerClassify;
	@Transient
	private String JcSpecMan;//集成解单员
	@Transient
	private String CgSpecMan;//橱柜解单员
	@Transient
	private String JcSpecManDescr;//集成解单员
	@Transient
	private String CgSpecManDescr;//橱柜解单员
	@Transient
	private String workType12Dept;
	@Transient
	private String authorityCtrl;
	@Transient
	private Integer daysFrom;
	@Transient 
	private Integer daysTo;
	@Transient
	private String newStakeholder;
	@Transient
	private String workerCode;
	@Transient 
	private String containOilPaint;
	@Transient
	private String czybh;
	@Transient
	private String confItemType;
	@Transient
	private String sendType;
	@Transient
	private String isNotPrint; //包含油漆未做 （工地结算利润分析）
	@Transient
	private Date toConstructDateFrom;
	@Transient
	private Date toConstructDateTo;
	@Transient
	private Date prgRmkDateTo;
	@Transient
	private int stopDays;
	@Transient
	private int timeOutDays;
	@Transient
	private String workType12;
	@Transient
	private String isSalaryConfirm;
	@Transient 
	private Date  deliverDateFrom; //交付时间
	@Transient 
	private Date  deliverDateTo;
	@Transient
	private String accountRoomMan;
	@Transient
	private String resrCustCode;
	@Transient
	private String deepDesignMan;
	@Transient
	private String deepDesignManDescr;
	@Transient
	private String measureMan;
	@Transient
	private String measureManDescr;
	@Transient
	private String documentNoFrom;
	@Transient
	private String documentNoTo;
	@Transient
	private String period;
	@Transient
	private String taxCallType;//税金计算类型
	@Transient
	private String department;
	@Transient
	private String department_emp;
	@Transient
	private String positionType;
	@Transient
	private String depType;
	@Transient
	private String showAll;	//component_customerOA 是否显示全部信息 add by zb on 20200317
	@Transient
	private String needAgainMan;	
	@Transient
	private String custNetCnl;
	@Transient
	private Date visitDateFrom;
	@Transient
	private Date visitDateTo;
	@Transient
	private Date measureDateFrom;
	@Transient
	private Date measureDateTo;
	@Transient
	private Date priceDateFrom;
    @Transient
	private Date priceDateTo;
    @Transient
	private String isFullColorDraw;//全景效果图
	@Transient
	private String drawNoChg;//图纸无变更
	@Transient
	private Date drawNoChgDate;//图纸无变更日期
	@Transient
	private String custKind;//客户类别
	@Transient
	private String isPartDecorate;//装修类型
	@Transient
	private String progStage;//施工阶段
	@Transient
	private String whCode;
	@Transient
	private String mustImportTemp;
	
	// 砌墙验收日期
	@Transient
	private Date confirmWallDateFrom;
	
	// 砌墙验收日期
	@Transient
	private Date confirmWallDateTo;
	
	// 设计出图日期
    @Transient
    private Date designDrawingDateFrom;
    
    // 设计出图日期
    @Transient
    private Date designDrawingDateTo;
    
    @Transient
    private String designStatus;
    
    @Transient
    private String contactRole;
	
	@Transient
	private String returnCount;//客户记录数
	
	@Transient
	private String canUseComItem;//可报公用材料
	@Transient
	private String discTokenNo;
	@Transient
	private double discTokenAmount;
	
	// 已验收项目
	@Transient
	private String acceptedPrjItem;
	
	// 未验收项目
	@Transient
	private String notAcceptedPrjItem;
	
	@Transient
	private String supplier;
	
	@Transient
	private Integer delayDays;
	
	// 集成安装状态
	@Transient
	private String installationStatus;
	
	@Transient
	private String company;
	
	@Transient
	private Date confirmWaterDateFrom;
	@Transient
	private Date confirmWaterDateTo;
	@Transient
	private Date changeDateFrom;
	@Transient
	private Date changeDateTo;
	@Transient
	private String isComplete;
	
	@Transient
	private String signDateFirstFrom;
	
	@Transient
	private String signDateFirstTo;
	
	@Transient
	private Date firstSignDateFrom;
	
	@Transient
	private Date firstSignDateTo;
	
	// 不包含完工 项目经理App领料新增查询楼盘时使用
	@Transient
	private String excludeComplete;
	@Transient
	private String type;
	@Transient
	private String crtCzy;
	@Transient
	private String paidAmount;
	@Transient
	private String dayRange;
	@Transient 
	private Double discAmount; //优惠金额
	@Transient
	private String contractStatus;
	@Transient
	private String giftInDisc; //礼品计入优惠
	@Transient
	private String jobType;
	@Transient
	private String moneyInFull;
	@Transient
	private Date intDesignDate;
	@Transient
	private Date cupDesignDate;
	
	@Transient
	private Date sendJobDateFrom;

    @Transient
	private Date sendJobDateTo;
    
    @Transient
    private String whetherNotifySend;
    
    @Transient
    private Date notifySendDateFrom;
    
    @Transient
    private Date notifySendDateTo;
    
    @Transient
    private String designCompleted;
	
	public Integer getPerfPK() {
		return perfPK;
	}

	public void setPerfPK(Integer perfPK) {
		this.perfPK = perfPK;
	}

	public Date getIntDesignDate() {
		return intDesignDate;
	}

	public void setIntDesignDate(Date intDesignDate) {
		this.intDesignDate = intDesignDate;
	}

	public Date getCupDesignDate() {
		return cupDesignDate;
	}

	public void setCupDesignDate(Date cupDesignDate) {
		this.cupDesignDate = cupDesignDate;
	}

	public String getRepClause() {
		return repClause;
	}

	public void setRepClause(String repClause) {
		this.repClause = repClause;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getChangeDateFrom() {
		return changeDateFrom;
	}

	public void setChangeDateFrom(Date changeDateFrom) {
		this.changeDateFrom = changeDateFrom;
	}

	public Date getChangeDateTo() {
		return changeDateTo;
	}

	public void setChangeDateTo(Date changeDateTo) {
		this.changeDateTo = changeDateTo;
	}

	public Date getConfirmWaterDateFrom() {
		return confirmWaterDateFrom;
	}

	public void setConfirmWaterDateFrom(Date confirmWaterDateFrom) {
		this.confirmWaterDateFrom = confirmWaterDateFrom;
	}

	public Date getConfirmWaterDateTo() {
		return confirmWaterDateTo;
	}

	public void setConfirmWaterDateTo(Date confirmWaterDateTo) {
		this.confirmWaterDateTo = confirmWaterDateTo;
	}

	public Date getConfirmWallDateFrom() {
        return confirmWallDateFrom;
    }

    public void setConfirmWallDateFrom(Date confirmWallDateFrom) {
        this.confirmWallDateFrom = confirmWallDateFrom;
    }

    public Date getConfirmWallDateTo() {
        return confirmWallDateTo;
    }

    public void setConfirmWallDateTo(Date confirmWallDateTo) {
        this.confirmWallDateTo = confirmWallDateTo;
    }

    public String getWhCode() {
		return whCode;
	}

	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}

	public Date getVisitDateFrom() {
		return visitDateFrom;
	}

	public void setVisitDateFrom(Date visitDateFrom) {
		this.visitDateFrom = visitDateFrom;
	}

	public Date getVisitDateTo() {
		return visitDateTo;
	}

	public void setVisitDateTo(Date visitDateTo) {
		this.visitDateTo = visitDateTo;
	}

	public String getCustNetCnl() {
		return custNetCnl;
	}

	public void setCustNetCnl(String custNetCnl) {
		this.custNetCnl = custNetCnl;
	}

	public String getResrCustCode() {
		return resrCustCode;
	}

	public void setResrCustCode(String resrCustCode) {
		this.resrCustCode = resrCustCode;
	}

	public String getPayeeCode() {
		return payeeCode;
	}

	public void setPayeeCode(String payeeCode) {
		this.payeeCode = payeeCode;
	}

	public String getAccountRoomMan() {
		return accountRoomMan;
	}

	public void setAccountRoomMan(String accountRoomMan) {
		this.accountRoomMan = accountRoomMan;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
 
	public int getTimeOutDays() {
		return timeOutDays;
	}

	public void setTimeOutDays(int timeOutDays) {
		this.timeOutDays = timeOutDays;
	}

	public String getWorkType12() {
		return workType12;
	}

	public void setWorkType12(String workType12) {
		this.workType12 = workType12;
	}
 
	public String getNewStakeholder() {
		return newStakeholder;
	}

	public void setNewStakeholder(String newStakeholder) {
		this.newStakeholder = newStakeholder;
	}

	public Integer getDaysFrom() {
		return daysFrom;
	}

	public void setDaysFrom(Integer daysFrom) {
		this.daysFrom = daysFrom;
	}

	public Integer getDaysTo() {
		return daysTo;
	}

	public void setDaysTo(Integer daysTo) {
		this.daysTo = daysTo;
	}

	public String getAuthorityCtrl() {
		return authorityCtrl;
	}

	public void setAuthorityCtrl(String authorityCtrl) {
		this.authorityCtrl = authorityCtrl;
	}

	public String getWorkType12Dept() {
		return workType12Dept;
	}

	public void setWorkType12Dept(String workType12Dept) {
		this.workType12Dept = workType12Dept;
	}
	public String getWorkerClassify() {
		return workerClassify;
	}

	public void setWorkerClassify(String workerClassify) {
		this.workerClassify = workerClassify;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getDesignChgDoc() {
		return designChgDoc;
	}

	public void setDesignChgDoc(String designChgDoc) {
		this.designChgDoc = designChgDoc;
	}

	public Date getToConstructDate() {
		return toConstructDate;
	}

	public void setToConstructDate(Date toConstructDate) {
		this.toConstructDate = toConstructDate;
	}

	public String getItemPlanCustCode() {
		return itemPlanCustCode;
	}

	public void setItemPlanCustCode(String itemPlanCustCode) {
		this.itemPlanCustCode = itemPlanCustCode;
	}

	public String getWorkType2() {
		return workType2;
	}

	public void setWorkType2(String workType2) {
		this.workType2 = workType2;
	}

	public String getWorkType1() {
		return workType1;
	}

	public void setWorkType1(String workType1) {
		this.workType1 = workType1;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getBaseItemCode() {
		return baseItemCode;
	}

	public void setBaseItemCode(String baseItemCode) {
		this.baseItemCode = baseItemCode;
	}

	public Double getBaseCtrlAmount() {
		return baseCtrlAmount;
	}

	public void setBaseCtrlAmount(Double baseCtrlAmount) {
		this.baseCtrlAmount = baseCtrlAmount;
	}

	public String getSpecifyBaseCtrl() {
		return specifyBaseCtrl;
	}

	public void setSpecifyBaseCtrl(String specifyBaseCtrl) {
		this.specifyBaseCtrl = specifyBaseCtrl;
	}

	public String getItemChgNo() {
		return itemChgNo;
	}

	public void setItemChgNo(String itemChgNo) {
		this.itemChgNo = itemChgNo;
	}

	public Integer getGxrPK() {
		return gxrPK;
	}

	public void setGxrPK(Integer gxrPK) {
		this.gxrPK = gxrPK;
	}

	public Integer getIsRight() {
		return isRight;
	}

	public void setIsRight(Integer isRight) {
		this.isRight = isRight;
	}

	public Data getPlanEnd() {
		return planEnd;
	}

	public void setPlanEnd(Data planEnd) {
		this.planEnd = planEnd;
	}

	public String getIncludEnd() {
		return includEnd;
	}

	public void setIncludEnd(String includEnd) {
		this.includEnd = includEnd;
	}

	public String getDesignDemo() {
		return designDemo;
	}

	public void setDesignDemo(String designDemo) {
		this.designDemo = designDemo;
	}

	public double getWallArea18() {
		return wallArea18;
	}

	public void setWallArea18(double wallArea18) {
		this.wallArea18 = wallArea18;
	}

	public String getExistsPrePlan() {
		return existsPrePlan;
	}

	public void setExistsPrePlan(String existsPrePlan) {
		this.existsPrePlan = existsPrePlan;
	}

	public String getConId() {
		return conId;
	}

	public void setConId(String conId) {
		this.conId = conId;
	}

	public String getConPhone() {
		return conPhone;
	}

	public void setConPhone(String conPhone) {
		this.conPhone = conPhone;
	}

	public String getTileStatus() {
		return tileStatus;
	}

	public void setTileStatus(String tileStatus) {
		this.tileStatus = tileStatus;
	}

	public String getBathStatus() {
		return bathStatus;
	}

	public void setBathStatus(String bathStatus) {
		this.bathStatus = bathStatus;
	}

	public Date getConStaDate() {
		return conStaDate;
	}

	public void setConStaDate(Date conStaDate) {
		this.conStaDate = conStaDate;
	}

	public String getCantInstall() {
		return cantInstall;
	}

	public void setCantInstall(String cantInstall) {
		this.cantInstall = cantInstall;
	}

	public String getCustWorkerCustStatus() {
		return custWorkerCustStatus;
	}

	public void setCustWorkerCustStatus(String custWorkerCustStatus) {
		this.custWorkerCustStatus = custWorkerCustStatus;
	}

	public String getLaborFeeCustStatus() {
		return laborFeeCustStatus;
	}

	public void setLaborFeeCustStatus(String laborFeeCustStatus) {
		this.laborFeeCustStatus = laborFeeCustStatus;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getViewAll() {
		return viewAll;
	}

	public void setViewAll(String viewAll) {
		this.viewAll = viewAll;
	}

	public String getItemRight() {
		return itemRight;
	}

	public void setItemRight(String itemRight) {
		this.itemRight = itemRight;
	}

	public String getIsServiceItem() {
		return isServiceItem;
	}

	public void setIsServiceItem(String isServiceItem) {
		this.isServiceItem = isServiceItem;
	}

	public Integer getSoftStewardPk() {
		return softStewardPk;
	}

	public void setSoftStewardPk(Integer softStewardPk) {
		this.softStewardPk = softStewardPk;
	}

	public String getSoftStewardStatus() {
		return softStewardStatus;
	}

	public void setSoftStewardStatus(String softStewardStatus) {
		this.softStewardStatus = softStewardStatus;
	}

	public String getSoftStewardDescr() {
		return softStewardDescr;
	}

	public void setSoftStewardDescr(String softStewardDescr) {
		this.softStewardDescr = softStewardDescr;
	}

	public String getSoftSteward() {
		return softSteward;
	}

	public void setSoftSteward(String softSteward) {
		this.softSteward = softSteward;
	}

	public String getIsSchemeDesigner() {
		return isSchemeDesigner;
	}

	public void setIsSchemeDesigner(String isSchemeDesigner) {
		this.isSchemeDesigner = isSchemeDesigner;
	}

	public String getArrangeStatus() {
		return arrangeStatus;
	}

	public void setArrangeStatus(String arrangeStatus) {
		this.arrangeStatus = arrangeStatus;
	}

	public String getBuilderNum() {
		return builderNum;
	}

	public void setBuilderNum(String builderNum) {
		this.builderNum = builderNum;
	}

	public String getCustSceneDesiDescr() {
		return custSceneDesiDescr;
	}

	public void setCustSceneDesiDescr(String custSceneDesiDescr) {
		this.custSceneDesiDescr = custSceneDesiDescr;
	}

	public String getCustSceneDesi() {
		return custSceneDesi;
	}

	public void setCustSceneDesi(String custSceneDesi) {
		this.custSceneDesi = custSceneDesi;
	}

	public String getBuildStatus() {
		return buildStatus;
	}

	public void setBuildStatus(String buildStatus) {
		this.buildStatus = buildStatus;
	}

	public String getHouseType() {
		return houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}

	public String getRelCust() {
		return relCust;
	}

	public void setRelCust(String relCust) {
		this.relCust = relCust;
	}

	public String getBuildSta() {
		return buildSta;
	}

	public void setBuildSta(String buildSta) {
		this.buildSta = buildSta;
	}

	public String getCanCancel() {
		return canCancel;
	}

	public void setCanCancel(String canCancel) {
		this.canCancel = canCancel;
	}

	public String getSpcBuilder() {
		return spcBuilder;
	}

	public void setSpcBuilder(String spcBuilder) {
		this.spcBuilder = spcBuilder;
	}

	public String getNormConstructDay() {
		return normConstructDay;
	}

	public void setNormConstructDay(String normConstructDay) {
		this.normConstructDay = normConstructDay;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRegion2() {
		return region2;
	}

	public void setRegion2(String region2) {
		this.region2 = region2;
	}

	public Integer getCgPk() {
		return cgPk;
	}

	public void setCgPk(Integer cgPk) {
		this.cgPk = cgPk;
	}

	public String getCGDesignerDescr() {
		return CGDesignerDescr;
	}

	public void setCGDesignerDescr(String cGDesignerDescr) {
		CGDesignerDescr = cGDesignerDescr;
	}

	public String getCGDesignCode() {
		return CGDesignCode;
	}

	public void setCGDesignCode(String cGDesignCode) {
		CGDesignCode = cGDesignCode;
	}

	public String getPrjProgTempType() {
		return prjProgTempType;
	}

	public void setPrjProgTempType(String prjProgTempType) {
		this.prjProgTempType = prjProgTempType;
	}

	public Integer getIntePk() {
		return intePk;
	}

	public void setIntePk(Integer intePk) {
		this.intePk = intePk;
	}

	public String getInteEmpCode() {
		return inteEmpCode;
	}

	public void setInteEmpCode(String inteEmpCode) {
		this.inteEmpCode = inteEmpCode;
	}

	public String getPurchStatus() {
		return purchStatus;
	}

	public void setPurchStatus(String purchStatus) {
		this.purchStatus = purchStatus;
	}

	public String getRealAddress() {
		return realAddress;
	}

	public void setRealAddress(String realAddress) {
		this.realAddress = realAddress;
	}

	public String getHavePay() {
		return havePay;
	}

	public void setHavePay(String havePay) {
		this.havePay = havePay;
	}

	public String getOldPreloftsMan() {
		return oldPreloftsMan;
	}

	public void setOldPreloftsMan(String oldPreloftsMan) {
		this.oldPreloftsMan = oldPreloftsMan;
	}

	public String getPreloftsManDescr() {
		return preloftsManDescr;
	}

	public void setPreloftsManDescr(String preloftsManDescr) {
		this.preloftsManDescr = preloftsManDescr;
	}

	public Date getBeginDateFrom() {
		return beginDateFrom;
	}

	public void setBeginDateFrom(Date beginDateFrom) {
		this.beginDateFrom = beginDateFrom;
	}

	public Date getBeginDateTo() {
		return beginDateTo;
	}

	public void setBeginDateTo(Date beginDateTo) {
		this.beginDateTo = beginDateTo;
	}

	public Date getCheckOutDateFrom() {
		return checkOutDateFrom;
	}

	public void setCheckOutDateFrom(Date checkOutDateFrom) {
		this.checkOutDateFrom = checkOutDateFrom;
	}

	public Date getCheckOutDateTo() {
		return checkOutDateTo;
	}

	public void setCheckOutDateTo(Date checkOutDateTo) {
		this.checkOutDateTo = checkOutDateTo;
	}

	public String getCheckManDescr() {
		return checkManDescr;
	}

	public void setCheckManDescr(String checkManDescr) {
		this.checkManDescr = checkManDescr;
	}

	public String getPreloftsMan() {
		return preloftsMan;
	}

	public void setPreloftsMan(String preloftsMan) {
		this.preloftsMan = preloftsMan;
	}

	public String getSoftCost() {
		return softCost;
	}

	public void setSoftCost(String softCost) {
		this.softCost = softCost;
	}

	public String getInteCost() {
		return inteCost;
	}

	public void setInteCost(String inteCost) {
		this.inteCost = inteCost;
	}

	public String getBaseCost() {
		return baseCost;
	}

	public void setBaseCost(String baseCost) {
		this.baseCost = baseCost;
	}


	public String getMainCost() {
		return mainCost;
	}

	public void setMainCost(String mainCost) {
		this.mainCost = mainCost;
	}

	public String getJczj() {
		return jczj;
	}

	public void setJczj(String jczj) {
		this.jczj = jczj;
	}

	public String getZjxhj() {
		return zjxhj;
	}

	public void setZjxhj(String zjxhj) {
		this.zjxhj = zjxhj;
	}

	public String getCustCountCost() {
		return custCountCost;
	}

	public void setCustCountCost(String custCountCost) {
		this.custCountCost = custCountCost;
	}

	public String getPrjManCost() {
		return prjManCost;
	}

	public void setPrjManCost(String prjManCost) {
		this.prjManCost = prjManCost;
	}

	public String getConsManCost() {
		return consManCost;
	}

	public void setConsManCost(String consManCost) {
		this.consManCost = consManCost;
	}

	public String getDelayDay() {
		return delayDay;
	}

	public void setDelayDay(String delayDay) {
		this.delayDay = delayDay;
	}

	public String getBusinessFlowDescr() {
		return businessFlowDescr;
	}

	public void setBusinessFlowDescr(String businessFlowDescr) {
		this.businessFlowDescr = businessFlowDescr;
	}

	public String getPlanPrjDescr() {
		return planPrjDescr;
	}

	public void setPlanPrjDescr(String planPrjDescr) {
		this.planPrjDescr = planPrjDescr;
	}

	public String getNowPrjDescr() {
		return nowPrjDescr;
	}

	public void setNowPrjDescr(String nowPrjDescr) {
		this.nowPrjDescr = nowPrjDescr;
	}

	public String getChengeCheckMan() {
		return chengeCheckMan;
	}

	public void setChengeCheckMan(String chengeCheckMan) {
		this.chengeCheckMan = chengeCheckMan;
	}

	public Integer getSoftPK() {
		return softPK;
	}

	public void setSoftPK(Integer softPK) {
		this.softPK = softPK;
	}

	public String getSoftEmpCode() {
		return softEmpCode;
	}

	public void setSoftEmpCode(String softEmpCode) {
		this.softEmpCode = softEmpCode;
	}

	public String getItemType2() {
		return itemType2;
	}

	public void setItemType2(String itemType2) {
		this.itemType2 = itemType2;
	}

	public String getCustWorkApp() {
		return custWorkApp;
	}

	public void setCustWorkApp(String custWorkApp) {
		this.custWorkApp = custWorkApp;
	}

	public String getMobileHidden() {
		return mobileHidden;
	}

	public void setMobileHidden(String mobileHidden) {
		this.mobileHidden = mobileHidden;
	}

	public Date getConfirmEnd() {
		return confirmEnd;
	}

	public void setConfirmEnd(Date confirmEnd) {
		this.confirmEnd = confirmEnd;
	}


	public String getPrgRemark() {
		return prgRemark;
	}

	public void setPrgRemark(String prgRemark) {
		this.prgRemark = prgRemark;
	}

	public String getPrjProgTempNoDescr() {
		return prjProgTempNoDescr;
	}

	public void setPrjProgTempNoDescr(String prjProgTempNoDescr) {
		this.prjProgTempNoDescr = prjProgTempNoDescr;
	}

	public String getIsPrjProgTemp() {
		return isPrjProgTemp;
	}

	public void setIsPrjProgTemp(String isPrjProgTemp) {
		this.isPrjProgTemp = isPrjProgTemp;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}

	public String getMobile1() {
		return mobile1;
	}

	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}

	public String getMobile2() {
		return mobile2;
	}

	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getCrtDate() {
		return crtDate;
	}

	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDesignMan() {
		return designMan;
	}

	public void setDesignMan(String designMan) {
		this.designMan = designMan;
	}

	public String getBusinessMan() {
		return businessMan;
	}

	public void setBusinessMan(String businessMan) {
		this.businessMan = businessMan;
	}

	public Date getPlanSet() {
		return planSet;
	}

	public void setPlanSet(Date planSet) {
		this.planSet = planSet;
	}

	public Date getSetDate() {
		return setDate;
	}

	public void setSetDate(Date setDate) {
		this.setDate = setDate;
	}

	public Date getPlanMeasure() {
		return planMeasure;
	}

	public void setPlanMeasure(Date planMeasure) {
		this.planMeasure = planMeasure;
	}

	public Date getMeasureDate() {
		return measureDate;
	}

	public void setMeasureDate(Date measureDate) {
		this.measureDate = measureDate;
	}

	public Date getPlanPlane() {
		return planPlane;
	}

	public void setPlanPlane(Date planPlane) {
		this.planPlane = planPlane;
	}

	public Date getPlaneDate() {
		return planeDate;
	}

	public void setPlaneDate(Date planeDate) {
		this.planeDate = planeDate;
	}

	public Date getPlanFacade() {
		return planFacade;
	}

	public void setPlanFacade(Date planFacade) {
		this.planFacade = planFacade;
	}

	public Date getFacadeDate() {
		return facadeDate;
	}

	public void setFacadeDate(Date facadeDate) {
		this.facadeDate = facadeDate;
	}

	public Date getPlanPrice() {
		return planPrice;
	}

	public void setPlanPrice(Date planPrice) {
		this.planPrice = planPrice;
	}

	public Date getPriceDate() {
		return priceDate;
	}

	public void setPriceDate(Date priceDate) {
		this.priceDate = priceDate;
	}

	public Date getPlanDraw() {
		return planDraw;
	}

	public void setPlanDraw(Date planDraw) {
		this.planDraw = planDraw;
	}

	public Date getDrawDate() {
		return drawDate;
	}

	public void setDrawDate(Date drawDate) {
		this.drawDate = drawDate;
	}

	public Date getPlanPrev() {
		return planPrev;
	}

	public void setPlanPrev(Date planPrev) {
		this.planPrev = planPrev;
	}

	public Date getPrevDate() {
		return prevDate;
	}

	public void setPrevDate(Date prevDate) {
		this.prevDate = prevDate;
	}

	public Date getPlanSign() {
		return planSign;
	}

	public void setPlanSign(Date planSign) {
		this.planSign = planSign;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public Double getDesignFee() {
		return designFee;
	}

	public void setDesignFee(Double designFee) {
		this.designFee = designFee;
	}

	public Double getMeasureFee() {
		return measureFee;
	}

	public void setMeasureFee(Double measureFee) {
		this.measureFee = measureFee;
	}

	public Double getDrawFee() {
		return drawFee;
	}

	public void setDrawFee(Double drawFee) {
		this.drawFee = drawFee;
	}

	public Double getColorDrawFee() {
		return colorDrawFee;
	}

	public void setColorDrawFee(Double colorDrawFee) {
		this.colorDrawFee = colorDrawFee;
	}

	public Double getManageFee() {
		return manageFee;
	}

	public void setManageFee(Double manageFee) {
		this.manageFee = manageFee;
	}

	public Double getBaseFee() {
		return baseFee;
	}

	public void setBaseFee(Double baseFee) {
		this.baseFee = baseFee;
	}

	public Double getBaseDisc() {
		return baseDisc;
	}

	public void setBaseDisc(Double baseDisc) {
		this.baseDisc = baseDisc;
	}

	public Double getChgBaseFee() {
		return chgBaseFee;
	}

	public void setChgBaseFee(Double chgBaseFee) {
		this.chgBaseFee = chgBaseFee;
	}

	public Double getMainFee() {
		return mainFee;
	}

	public void setMainFee(Double mainFee) {
		this.mainFee = mainFee;
	}

	public Double getMainDisc() {
		return mainDisc;
	}

	public void setMainDisc(Double mainDisc) {
		this.mainDisc = mainDisc;
	}

	public Double getChgMainFee() {
		return chgMainFee;
	}

	public void setChgMainFee(Double chgMainFee) {
		this.chgMainFee = chgMainFee;
	}

	public Double getSoftFee() {
		return softFee;
	}

	public void setSoftFee(Double softFee) {
		this.softFee = softFee;
	}

	public Double getSoftDisc() {
		return softDisc;
	}

	public void setSoftDisc(Double softDisc) {
		this.softDisc = softDisc;
	}

	public Double getChgSoftFee() {
		return chgSoftFee;
	}

	public void setChgSoftFee(Double chgSoftFee) {
		this.chgSoftFee = chgSoftFee;
	}

	public Double getIntegrateFee() {
		return integrateFee;
	}

	public void setIntegrateFee(Double integrateFee) {
		this.integrateFee = integrateFee;
	}

	public Double getIntegrateDisc() {
		return integrateDisc;
	}

	public void setIntegrateDisc(Double integrateDisc) {
		this.integrateDisc = integrateDisc;
	}

	public Double getChgIntFee() {
		return chgIntFee;
	}

	public void setChgIntFee(Double chgIntFee) {
		this.chgIntFee = chgIntFee;
	}

	public Double getCupBoardFee() {
		return cupBoardFee;
	}

	public void setCupBoardFee(Double cupBoardFee) {
		this.cupBoardFee = cupBoardFee;
	}

	public Double getCupBoardDisc() {
		return cupBoardDisc;
	}

	public void setCupBoardDisc(Double cupBoardDisc) {
		this.cupBoardDisc = cupBoardDisc;
	}

	public Double getChgCupFee() {
		return chgCupFee;
	}

	public void setChgCupFee(Double chgCupFee) {
		this.chgCupFee = chgCupFee;
	}

	public Double getContractFee() {
		return contractFee;
	}

	public void setContractFee(Double contractFee) {
		this.contractFee = contractFee;
	}

	public Double getMaterialFee() {
		return materialFee;
	}

	public void setMaterialFee(Double materialFee) {
		this.materialFee = materialFee;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getEndCode() {
		return endCode;
	}

	public void setEndCode(String endCode) {
		this.endCode = endCode;
	}

	public String getEndRemark() {
		return endRemark;
	}

	public void setEndRemark(String endRemark) {
		this.endRemark = endRemark;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getExpired() {
		return expired;
	}

	public void setExpired(String expired) {
		this.expired = expired;
	}

	public String getActionLog() {
		return actionLog;
	}

	public void setActionLog(String actionLog) {
		this.actionLog = actionLog;
	}

	public Double getMainFeePer() {
		return mainFeePer;
	}

	public void setMainFeePer(Double mainFeePer) {
		this.mainFeePer = mainFeePer;
	}

	public Double getChgMainFeePer() {
		return chgMainFeePer;
	}

	public void setChgMainFeePer(Double chgMainFeePer) {
		this.chgMainFeePer = chgMainFeePer;
	}

	public Double getIntegrateCost() {
		return integrateCost;
	}

	public void setIntegrateCost(Double integrateCost) {
		this.integrateCost = integrateCost;
	}

	public Double getCupBoardCost() {
		return cupBoardCost;
	}

	public void setCupBoardCost(Double cupBoardCost) {
		this.cupBoardCost = cupBoardCost;
	}

	public String getIsIntgDesign() {
		return isIntgDesign;
	}

	public void setIsIntgDesign(String isIntgDesign) {
		this.isIntgDesign = isIntgDesign;
	}

	public String getIsFirstCal() {
		return isFirstCal;
	}

	public void setIsFirstCal(String isFirstCal) {
		this.isFirstCal = isFirstCal;
	}

	public String getFirstCalNo() {
		return firstCalNo;
	}

	public void setFirstCalNo(String firstCalNo) {
		this.firstCalNo = firstCalNo;
	}

	public String getIsSecCal() {
		return isSecCal;
	}

	public void setIsSecCal(String isSecCal) {
		this.isSecCal = isSecCal;
	}

	public String getSecCalNo() {
		return secCalNo;
	}

	public void setSecCalNo(String secCalNo) {
		this.secCalNo = secCalNo;
	}

	public String getIsSoftCal() {
		return isSoftCal;
	}

	public void setIsSoftCal(String isSoftCal) {
		this.isSoftCal = isSoftCal;
	}

	public String getSoftCalNo() {
		return softCalNo;
	}

	public void setSoftCalNo(String softCalNo) {
		this.softCalNo = softCalNo;
	}

	public Double getBaseFeeDirct() {
		return baseFeeDirct;
	}

	public void setBaseFeeDirct(Double baseFeeDirct) {
		this.baseFeeDirct = baseFeeDirct;
	}

	public Double getBaseFeeComp() {
		return baseFeeComp;
	}

	public void setBaseFeeComp(Double baseFeeComp) {
		this.baseFeeComp = baseFeeComp;
	}

	public Double getFirstPay() {
		return firstPay;
	}

	public void setFirstPay(Double firstPay) {
		this.firstPay = firstPay;
	}

	public Double getSecondPay() {
		return secondPay;
	}

	public void setSecondPay(Double secondPay) {
		this.secondPay = secondPay;
	}

	public Double getThirdPay() {
		return thirdPay;
	}

	public void setThirdPay(Double thirdPay) {
		this.thirdPay = thirdPay;
	}

	public Double getFourPay() {
		return fourPay;
	}

	public void setFourPay(Double fourPay) {
		this.fourPay = fourPay;
	}

	public String getIsDesignCal() {
		return isDesignCal;
	}

	public void setIsDesignCal(String isDesignCal) {
		this.isDesignCal = isDesignCal;
	}

	public String getDesignCalNo() {
		return designCalNo;
	}

	public void setDesignCalNo(String designCalNo) {
		this.designCalNo = designCalNo;
	}

	public String getDesignStyle() {
		return designStyle;
	}

	public void setDesignStyle(String designStyle) {
		this.designStyle = designStyle;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public String getConstructType() {
		return constructType;
	}

	public void setConstructType(String constructType) {
		this.constructType = constructType;
	}

	public Double getPlanAmount() {
		return planAmount;
	}

	public void setPlanAmount(Double planAmount) {
		this.planAmount = planAmount;
	}

	public Double getGift() {
		return gift;
	}

	public void setGift(Double gift) {
		this.gift = gift;
	}

	public Double getSoftOther() {
		return softOther;
	}

	public void setSoftOther(Double softOther) {
		this.softOther = softOther;
	}

	public Integer getContainBase() {
		return containBase;
	}

	public void setContainBase(Integer containBase) {
		this.containBase = containBase;
	}

	public Integer getContainMain() {
		return containMain;
	}

	public void setContainMain(Integer containMain) {
		this.containMain = containMain;
	}

	public Integer getContainSoft() {
		return containSoft;
	}

	public void setContainSoft(Integer containSoft) {
		this.containSoft = containSoft;
	}

	public Integer getContainInt() {
		return containInt;
	}

	public void setContainInt(Integer containInt) {
		this.containInt = containInt;
	}

	public String getPayRemark() {
		return payRemark;
	}

	public void setPayRemark(String payRemark) {
		this.payRemark = payRemark;
	}

	public String getDiscRemark() {
		return discRemark;
	}

	public void setDiscRemark(String discRemark) {
		this.discRemark = discRemark;
	}

	public Double getAchievDed() {
		return achievDed;
	}

	public void setAchievDed(Double achievDed) {
		this.achievDed = achievDed;
	}

	public Integer getContainCup() {
		return containCup;
	}

	public void setContainCup(Integer containCup) {
		this.containCup = containCup;
	}

	public Integer getContainMainServ() {
		return containMainServ;
	}

	public void setContainMainServ(Integer containMainServ) {
		this.containMainServ = containMainServ;
	}

	public Double getMainServFee() {
		return mainServFee;
	}

	public void setMainServFee(Double mainServFee) {
		this.mainServFee = mainServFee;
	}

	public Double getChgMainServFee() {
		return chgMainServFee;
	}

	public void setChgMainServFee(Double chgMainServFee) {
		this.chgMainServFee = chgMainServFee;
	}

	public Date getConfirmBegin() {
		return confirmBegin;
	}

	public void setConfirmBegin(Date confirmBegin) {
		this.confirmBegin = confirmBegin;
	}

	public String getProjectMan() {
		return projectMan;
	}

	public void setProjectMan(String projectMan) {
		this.projectMan = projectMan;
	}

	public String getCheckMan() {
		return checkMan;
	}

	public void setCheckMan(String checkMan) {
		this.checkMan = checkMan;
	}

	public Date getBeginComDate() {
		return beginComDate;
	}

	public void setBeginComDate(Date beginComDate) {
		this.beginComDate = beginComDate;
	}

	public Integer getConstructDay() {
		return constructDay;
	}

	public void setConstructDay(Integer constructDay) {
		this.constructDay = constructDay;
	}

	public String getConstructStatus() {
		return constructStatus;
	}

	public void setConstructStatus(String constructStatus) {
		this.constructStatus = constructStatus;
	}

	public Date getConsEndDate() {
		return consEndDate;
	}

	public void setConsEndDate(Date consEndDate) {
		this.consEndDate = consEndDate;
	}

	public Date getPlanCheckOut() {
		return planCheckOut;
	}

	public void setPlanCheckOut(Date planCheckOut) {
		this.planCheckOut = planCheckOut;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public Date getConsRcvDate() {
		return consRcvDate;
	}

	public void setConsRcvDate(Date consRcvDate) {
		this.consRcvDate = consRcvDate;
	}

	public String getIsComplain() {
		return isComplain;
	}

	public void setIsComplain(String isComplain) {
		this.isComplain = isComplain;
	}

	public Date getSendJobDate() {
		return sendJobDate;
	}

	public void setSendJobDate(Date sendJobDate) {
		this.sendJobDate = sendJobDate;
	}

	public String getConsArea() {
		return consArea;
	}

	public void setConsArea(String consArea) {
		this.consArea = consArea;
	}

	public Double getSpecialDisc() {
		return specialDisc;
	}

	public void setSpecialDisc(Double specialDisc) {
		this.specialDisc = specialDisc;
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public Date getRepairCardDate() {
		return repairCardDate;
	}

	public void setRepairCardDate(Date repairCardDate) {
		this.repairCardDate = repairCardDate;
	}

	public String getRepairCardMan() {
		return repairCardMan;
	}

	public void setRepairCardMan(String repairCardMan) {
		this.repairCardMan = repairCardMan;
	}

	public Date getCddate() {
		return cddate;
	}

	public void setCddate(Date cddate) {
		this.cddate = cddate;
	}

	public String getCdman() {
		return cdman;
	}

	public void setCdman(String cdman) {
		this.cdman = cdman;
	}

	public String getRepairRemark() {
		return repairRemark;
	}

	public void setRepairRemark(String repairRemark) {
		this.repairRemark = repairRemark;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public Double getSoftBaseFee() {
		return softBaseFee;
	}

	public void setSoftBaseFee(Double softBaseFee) {
		this.softBaseFee = softBaseFee;
	}

	public Double getSoftFeeWallPaper() {
		return softFeeWallPaper;
	}

	public void setSoftFeeWallPaper(Double softFeeWallPaper) {
		this.softFeeWallPaper = softFeeWallPaper;
	}

	public Double getSoftFeeCurtain() {
		return softFeeCurtain;
	}

	public void setSoftFeeCurtain(Double softFeeCurtain) {
		this.softFeeCurtain = softFeeCurtain;
	}

	public Double getSoftFeeLight() {
		return softFeeLight;
	}

	public void setSoftFeeLight(Double softFeeLight) {
		this.softFeeLight = softFeeLight;
	}

	public Double getSoftFeeFurniture() {
		return softFeeFurniture;
	}

	public void setSoftFeeFurniture(Double softFeeFurniture) {
		this.softFeeFurniture = softFeeFurniture;
	}

	public Double getSoftFeeAdornment() {
		return softFeeAdornment;
	}

	public void setSoftFeeAdornment(Double softFeeAdornment) {
		this.softFeeAdornment = softFeeAdornment;
	}

	public Double getBaseCtrlPer() {
		return baseCtrlPer;
	}

	public void setBaseCtrlPer(Double baseCtrlPer) {
		this.baseCtrlPer = baseCtrlPer;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public Date getIntMsrDate() {
		return intMsrDate;
	}

	public void setIntMsrDate(Date intMsrDate) {
		this.intMsrDate = intMsrDate;
	}

	public Integer getIntDlyDay() {
		return intDlyDay;
	}

	public void setIntDlyDay(Integer intDlyDay) {
		this.intDlyDay = intDlyDay;
	}

	public Date getCustCheckDate() {
		return custCheckDate;
	}

	public void setCustCheckDate(Date custCheckDate) {
		this.custCheckDate = custCheckDate;
	}

	public String getCheckDocumentNo() {
		return checkDocumentNo;
	}

	public void setCheckDocumentNo(String checkDocumentNo) {
		this.checkDocumentNo = checkDocumentNo;
	}

	public String getAgainMan() {
		return againMan;
	}

	public void setAgainMan(String againMan) {
		this.againMan = againMan;
	}

	public String getBuilderCode() {
		return builderCode;
	}

	public void setBuilderCode(String builderCode) {
		this.builderCode = builderCode;
	}

	public String getHaveReturn() {
		return haveReturn;
	}

	public void setHaveReturn(String haveReturn) {
		this.haveReturn = haveReturn;
	}

	public String getHaveCheck() {
		return haveCheck;
	}

	public void setHaveCheck(String haveCheck) {
		this.haveCheck = haveCheck;
	}

	public String getHavePhoto() {
		return havePhoto;
	}

	public void setHavePhoto(String havePhoto) {
		this.havePhoto = havePhoto;
	}

	public Double getProjectCtrlAdj() {
		return projectCtrlAdj;
	}

	public void setProjectCtrlAdj(Double projectCtrlAdj) {
		this.projectCtrlAdj = projectCtrlAdj;
	}

	public String getCtrlAdjRemark() {
		return ctrlAdjRemark;
	}

	public void setCtrlAdjRemark(String ctrlAdjRemark) {
		this.ctrlAdjRemark = ctrlAdjRemark;
	}

	public Double getMainSetFee() {
		return mainSetFee;
	}

	public void setMainSetFee(Double mainSetFee) {
		this.mainSetFee = mainSetFee;
	}

	public Double getSetMinus() {
		return setMinus;
	}

	public void setSetMinus(Double setMinus) {
		this.setMinus = setMinus;
	}

	public Double getSetAdd() {
		return setAdd;
	}

	public void setSetAdd(Double setAdd) {
		this.setAdd = setAdd;
	}

	public Double getLongFee() {
		return longFee;
	}

	public void setLongFee(Double longFee) {
		this.longFee = longFee;
	}

	public Double getManageFeeBase() {
		return manageFeeBase;
	}

	public void setManageFeeBase(Double manageFeeBase) {
		this.manageFeeBase = manageFeeBase;
	}

	public Double getManageFeeMain() {
		return manageFeeMain;
	}

	public void setManageFeeMain(Double manageFeeMain) {
		this.manageFeeMain = manageFeeMain;
	}

	public Double getManageFeeInt() {
		return manageFeeInt;
	}

	public void setManageFeeInt(Double manageFeeInt) {
		this.manageFeeInt = manageFeeInt;
	}

	public Double getManageFeeServ() {
		return manageFeeServ;
	}

	public void setManageFeeServ(Double manageFeeServ) {
		this.manageFeeServ = manageFeeServ;
	}

	public Double getManageFeeSoft() {
		return manageFeeSoft;
	}

	public void setManageFeeSoft(Double manageFeeSoft) {
		this.manageFeeSoft = manageFeeSoft;
	}

	public Double getManageFeeCup() {
		return manageFeeCup;
	}

	public void setManageFeeCup(Double manageFeeCup) {
		this.manageFeeCup = manageFeeCup;
	}

	public String getBuilderCodeDescr() {
		return builderCodeDescr;
	}

	public void setBuilderCodeDescr(String builderCodeDescr) {
		this.builderCodeDescr = builderCodeDescr;
	}

	public String getDesignManDescr() {
		return designManDescr;
	}

	public void setDesignManDescr(String designManDescr) {
		this.designManDescr = designManDescr;
	}

	public String getBusinessManDescr() {
		return businessManDescr;
	}

	public void setBusinessManDescr(String businessManDescr) {
		this.businessManDescr = businessManDescr;
	}

	public String getAgainManDescr() {
		return againManDescr;
	}

	public void setAgainManDescr(String againManDescr) {
		this.againManDescr = againManDescr;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getDepartment1() {
		return department1;
	}

	public void setDepartment1(String department1) {
		this.department1 = department1;
	}

	public String getDepartment2() {
		return department2;
	}

	public void setDepartment2(String department2) {
		this.department2 = department2;
	}

	public String getDepartment3() {
		return department3;
	}

	public void setDepartment3(String department3) {
		this.department3 = department3;
	}

	public Date getSetDateFrom() {
		return setDateFrom;
	}

	public void setSetDateFrom(Date setDateFrom) {
		this.setDateFrom = setDateFrom;
	}

	public Date getSetDateTo() {
		return setDateTo;
	}

	public void setSetDateTo(Date setDateTo) {
		this.setDateTo = setDateTo;
	}

	public Date getSignDateFrom() {
		return signDateFrom;
	}

	public void setSignDateFrom(Date signDateFrom) {
		this.signDateFrom = signDateFrom;
	}

	public Date getSignDateTo() {
		return signDateTo;
	}

	public void setSignDateTo(Date signDateTo) {
		this.signDateTo = signDateTo;
	}

	public Date getCrtDateFrom() {
		return crtDateFrom;
	}

	public void setCrtDateFrom(Date crtDateFrom) {
		this.crtDateFrom = crtDateFrom;
	}

	public Date getCrtDateTo() {
		return crtDateTo;
	}

	public void setCrtDateTo(Date crtDateTo) {
		this.crtDateTo = crtDateTo;
	}

	public Date getEndDateFrom() {
		return endDateFrom;
	}

	public void setEndDateFrom(Date endDateFrom) {
		this.endDateFrom = endDateFrom;
	}

	public Date getEndDateTo() {
		return endDateTo;
	}

	public void setEndDateTo(Date endDateTo) {
		this.endDateTo = endDateTo;
	}

	public String getCustLevel() {
		return custLevel;
	}

	public void setCustLevel(String custLevel) {
		this.custLevel = custLevel;
	}

	public String getConstructRemark() {
		return constructRemark;
	}

	public void setConstructRemark(String constructRemark) {
		this.constructRemark = constructRemark;
	}

	public String getPrjProgTempNo() {
		return prjProgTempNo;
	}

	public void setPrjProgTempNo(String prjProgTempNo) {
		this.prjProgTempNo = prjProgTempNo;
	}

	public String getStakeholder() {
		return stakeholder;
	}

	public void setStakeholder(String stakeholder) {
		this.stakeholder = stakeholder;
	}

	public String getHaveGd() {
		return haveGd;
	}

	public void setHaveGd(String haveGd) {
		this.haveGd = haveGd;
	}

	public String getIsUpPosiPic() {
		return isUpPosiPic;
	}

	public void setIsUpPosiPic(String isUpPosiPic) {
		this.isUpPosiPic = isUpPosiPic;
	}

	public Date getConfirmBeginFrom() {
		return ConfirmBeginFrom;
	}

	public void setConfirmBeginFrom(Date confirmBeginFrom) {
		ConfirmBeginFrom = confirmBeginFrom;
	}

	public Date getConfirmBeginTo() {
		return ConfirmBeginTo;
	}

	public void setConfirmBeginTo(Date confirmBeginTo) {
		ConfirmBeginTo = confirmBeginTo;
	}
	
	public String getStatistcsMethod() {
		return statistcsMethod;
	}

	public void setStatistcsMethod(String statistcsMethod) {
		this.statistcsMethod = statistcsMethod;
	}

	public String getStatistcsDateType() {
		return statistcsDateType;
	}

	public void setStatistcsDateType(String statistcsDateType) {
		this.statistcsDateType = statistcsDateType;
	}
	
	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getProjectManDescr() {
		return projectManDescr;
	}

	public void setProjectManDescr(String projectManDescr) {
		this.projectManDescr = projectManDescr;
	}

	public String getCheckStatusDescr() {
		return checkStatusDescr;
	}

	public void setCheckStatusDescr(String checkStatusDescr) {
		this.checkStatusDescr = checkStatusDescr;
	}

	public String getSaleType() {
		return saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	public String getOldCode() {
		return oldCode;
	}

	public void setOldCode(String oldCode) {
		this.oldCode = oldCode;
	}

	public String getNetChanel() {
		return netChanel;
	}

	public void setNetChanel(String netChanel) {
		this.netChanel = netChanel;
	}

	public String getOldCodeDescr() {
		return oldCodeDescr;
	}

	public void setOldCodeDescr(String oldCodeDescr) {
		this.oldCodeDescr = oldCodeDescr;
	}

	public String getIsInternal() {
		return isInternal;
	}

	public void setIsInternal(String isInternal) {
		this.isInternal = isInternal;
	}

	public String getDisabledEle() {
		return disabledEle;
	}

	public void setDisabledEle(String disabledEle) {
		this.disabledEle = disabledEle;
	}

	public Integer getComeTimes() {
		return comeTimes;
	}

	public void setComeTimes(Integer comeTimes) {
		this.comeTimes = comeTimes;
	}

	public String getItemType1() {
		return itemType1;
	}

	public void setItemType1(String itemType1) {
		this.itemType1 = itemType1;
	}

	public Double getBaseFeeDirctPer() {
		return baseFeeDirctPer;
	}

	public void setBaseFeeDirctPer(Double baseFeeDirctPer) {
		this.baseFeeDirctPer = baseFeeDirctPer;
	}

	public Double getBaseFeeCompPer() {
		return baseFeeCompPer;
	}

	public void setBaseFeeCompPer(Double baseFeeCompPer) {
		this.baseFeeCompPer = baseFeeCompPer;
	}

	public Double getManageFeeBasePer() {
		return manageFeeBasePer;
	}

	public void setManageFeeBasePer(Double manageFeeBasePer) {
		this.manageFeeBasePer = manageFeeBasePer;
	}

	public Integer getAreaPer() {
		return areaPer;
	}

	public void setAreaPer(Integer areaPer) {
		this.areaPer = areaPer;
	}

	public String getDetailJson() {
		return detailJson;
	}
	public void setDetailJson(String detailJson) {
		this.detailJson = detailJson;
	}
	public String getDetailXml(){
		String xml = XmlConverUtil.jsonToXmlNoHead(detailJson);
		return xml;
	}


	public String getLongFeeCode() {
		return longFeeCode;
	}

	public void setLongFeeCode(String longFeeCode) {
		this.longFeeCode = longFeeCode;
	}

	public Double getMainSetFeePer() {
		return mainSetFeePer;
	}

	public void setMainSetFeePer(Double mainSetFeePer) {
		this.mainSetFeePer = mainSetFeePer;
	}

	public Double getSetMinusPer() {
		return setMinusPer;
	}

	public void setSetMinusPer(Double setMinusPer) {
		this.setMinusPer = setMinusPer;
	}

	public Double getSetAddPer() {
		return setAddPer;
	}

	public void setSetAddPer(Double setAddPer) {
		this.setAddPer = setAddPer;
	}

	public Double getLongFeePer() {
		return longFeePer;
	}

	public void setLongFeePer(Double longFeePer) {
		this.longFeePer = longFeePer;
	}

	public Date getCustCheckDateFrom() {
		return custCheckDateFrom;
	}

	public void setCustCheckDateFrom(Date custCheckDateFrom) {
		this.custCheckDateFrom = custCheckDateFrom;
	}

	public Date getCustCheckDateTo() {
		return custCheckDateTo;
	}

	public void setCustCheckDateTo(Date custCheckDateTo) {
		this.custCheckDateTo = custCheckDateTo;
	}

	public String getIsOutSet() {
		return isOutSet;
	}

	public void setIsOutSet(String isOutSet) {
		this.isOutSet = isOutSet;
	}

	public String getSoftFee_WallPaper() {
		return softFee_WallPaper;
	}

	public void setSoftFee_WallPaper(String softFee_WallPaper) {
		this.softFee_WallPaper = softFee_WallPaper;
	}

	public String getSoftFee_Curtain() {
		return softFee_Curtain;
	}

	public void setSoftFee_Curtain(String softFee_Curtain) {
		this.softFee_Curtain = softFee_Curtain;
	}

	public String getSoftFee_Light() {
		return softFee_Light;
	}

	public void setSoftFee_Light(String softFee_Light) {
		this.softFee_Light = softFee_Light;
	}

	public String getSoftFee_Furniture() {
		return softFee_Furniture;
	}

	public void setSoftFee_Furniture(String softFee_Furniture) {
		this.softFee_Furniture = softFee_Furniture;
	}

	public String getSoftFee_Adornment() {
		return softFee_Adornment;
	}

	public void setSoftFee_Adornment(String softFee_Adornment) {
		this.softFee_Adornment = softFee_Adornment;
	}

	public String getBuildPass() {
		return buildPass;
	}

	public void setBuildPass(String buildPass) {
		this.buildPass = buildPass;
	}
		
	public String getRewardRemark() {
		return rewardRemark;
	}

	public void setRewardRemark(String rewardRemark) {
		this.rewardRemark = rewardRemark;
	}

	public String getConfItem() {
		return confItem;
	}

	public void setConfItem(String confItem) {
		this.confItem = confItem;
	}

	public Date getInformDateFrom() {
		return informDateFrom;
	}

	public void setInformDateFrom(Date informDateFrom) {
		this.informDateFrom = informDateFrom;
	}

	public Date getInformDateTo() {
		return informDateTo;
	}

	public void setInformDateTo(Date informDateTo) {
		this.informDateTo = informDateTo;
	}

	public Date getConfirmDateFrom() {
		return ConfirmDateFrom;
	}

	public void setConfirmDateFrom(Date confirmDateFrom) {
		ConfirmDateFrom = confirmDateFrom;
	}

	public Date getConfirmDateTo() {
		return ConfirmDateTo;
	}

	public void setConfirmDateTo(Date confirmDateTo) {
		ConfirmDateTo = confirmDateTo;
	}

	public String getConfirmFinish() {
		return confirmFinish;
	}

	public void setConfirmFinish(String confirmFinish) {
		this.confirmFinish = confirmFinish;
	}

	public String getIsOrder() {
		return isOrder;
	}

	public void setIsOrder(String isOrder) {
		this.isOrder = isOrder;
	}

	public String getDoExcelBefore() {
		return doExcelBefore;
	}

	public void setDoExcelBefore(String doExcelBefore) {
		this.doExcelBefore = doExcelBefore;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Double getLontitude() {
		return lontitude;
	}

	public void setLontitude(Double lontitude) {
		this.lontitude = lontitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getPrjItem() {
		return prjItem;
	}

	public void setPrjItem(String prjItem) {
		this.prjItem = prjItem;
	}

	public String getSaleIndependence() {
		return saleIndependence;
	}

	public void setSaleIndependence(String saleIndependence) {
		this.saleIndependence = saleIndependence;
	}

	public Double getMainDiscPer() {
		return mainDiscPer;
	}

	public void setMainDiscPer(Double mainDiscPer) {
		this.mainDiscPer = mainDiscPer;
	}

	public Double getManageFeeMainPer() {
		return manageFeeMainPer;
	}

	public void setManageFeeMainPer(Double manageFeeMainPer) {
		this.manageFeeMainPer = manageFeeMainPer;
	}

	public Double getMainServFeePer() {
		return mainServFeePer;
	}

	public void setMainServFeePer(Double mainServFeePer) {
		this.mainServFeePer = mainServFeePer;
	}

	public Double getManageFeeServPer() {
		return manageFeeServPer;
	}

	public void setManageFeeServPer(Double manageFeeServPer) {
		this.manageFeeServPer = manageFeeServPer;
	}

	public Double getManageFeeCupPer() {
		return manageFeeCupPer;
	}

	public void setManageFeeCupPer(Double manageFeeCupPer) {
		this.manageFeeCupPer = manageFeeCupPer;
	}

	public Double getCupboardFeePer() {
		return cupboardFeePer;
	}

	public void setCupboardFeePer(Double cupboardFeePer) {
		this.cupboardFeePer = cupboardFeePer;
	}

	public Double getCupBoardDiscPer() {
		return cupBoardDiscPer;
	}

	public void setCupBoardDiscPer(Double cupBoardDiscPer) {
		this.cupBoardDiscPer = cupBoardDiscPer;
	}

	public String getIsContainOutPlan() {
		return isContainOutPlan;
	}

	public void setIsContainOutPlan(String isContainOutPlan) {
		this.isContainOutPlan = isContainOutPlan;
	}

	public String getMainPlanMan() {
		return mainPlanMan;
	}

	public void setMainPlanMan(String mainPlanMan) {
		this.mainPlanMan = mainPlanMan;
	}

	public String getMainBusinessMan() {
		return mainBusinessMan;
	}

	public void setMainBusinessMan(String mainBusinessMan) {
		this.mainBusinessMan = mainBusinessMan;
	}

	public String getMainPlanManName() {
		return mainPlanManName;
	}

	public void setMainPlanManName(String mainPlanManName) {
		this.mainPlanManName = mainPlanManName;
	}

	public String getMainBusinessManName() {
		return mainBusinessManName;
	}

	public void setMainBusinessManName(String mainBusinessManName) {
		this.mainBusinessManName = mainBusinessManName;
	}

	public String getMonthNum() {
		return monthNum;
	}

	public void setMonthNum(String monthNum) {
		this.monthNum = monthNum;
	}

	public String getTgyy() {
		return tgyy;
	}

	public void setTgyy(String tgyy) {
		this.tgyy = tgyy;
	}

	public String getCustRight() {
		return custRight;
	}

	public void setCustRight(String custRight) {
		this.custRight = custRight;
	}

	public String getMapper() {
		return mapper;
	}

	public void setMapper(String mapper) {
		this.mapper = mapper;
	}

	public String getSketcher() {
		return sketcher;
	}

	public void setSketcher(String sketcher) {
		this.sketcher = sketcher;
	}

	public String getMapperName() {
		return mapperName;
	}

	public void setMapperName(String mapperName) {
		this.mapperName = mapperName;
	}

	public String getSketcherName() {
		return sketcherName;
	}

	public void setSketcherName(String sketcherName) {
		this.sketcherName = sketcherName;
	}

	public String getJcMan() {
		return jcMan;
	}

	public void setJcMan(String jcMan) {
		this.jcMan = jcMan;
	}

	public String getRzMan() {
		return rzMan;
	}

	public void setRzMan(String rzMan) {
		this.rzMan = rzMan;
	}

	public String getJcManName() {
		return jcManName;
	}

	public void setJcManName(String jcManName) {
		this.jcManName = jcManName;
	}

	public String getRzManName() {
		return rzManName;
	}

	public void setRzManName(String rzManName) {
		this.rzManName = rzManName;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getStakeholderRoll() {
		return stakeholderRoll;
	}

	public void setStakeholderRoll(String stakeholderRoll) {
		this.stakeholderRoll = stakeholderRoll;
	}

	public String getIsItemUp() {
		return isItemUp;
	}

	public void setIsItemUp(String isItemUp) {
		this.isItemUp = isItemUp;
	}

	public String getXtcs() {
		return xtcs;
	}

	public void setXtcs(String xtcs) {
		this.xtcs = xtcs;
	}

	public int getSignMonth() {
		return signMonth;
	}

	public void setSignMonth(int signMonth) {
		this.signMonth = signMonth;
	}

	public String getDepartment2Descr() {
		return department2Descr;
	}

	public void setDepartment2Descr(String department2Descr) {
		this.department2Descr = department2Descr;
	}

	public String getItemType12() {
		return itemType12;
	}

	public void setItemType12(String itemType12) {
		this.itemType12 = itemType12;
	}

	public Date getConfirmStartDateFrom() {
		return confirmStartDateFrom;
	}

	public void setConfirmStartDateFrom(Date confirmStartDateFrom) {
		this.confirmStartDateFrom = confirmStartDateFrom;
	}

	public Date getConfirmStartDateTo() {
		return confirmStartDateTo;
	}

	public void setConfirmStartDateTo(Date confirmStartDateTo) {
		this.confirmStartDateTo = confirmStartDateTo;
	}

	public String getIsItemCheck() {
		return isItemCheck;
	}

	public void setIsItemCheck(String isItemCheck) {
		this.isItemCheck = isItemCheck;
	}

	public String getStatusDescr() {
		return statusDescr;
	}

	public void setStatusDescr(String statusDescr) {
		this.statusDescr = statusDescr;
	}

	public String getIsSave() {
		return isSave;
	}

	public void setIsSave(String isSave) {
		this.isSave = isSave;
	}

	public Double getRealDesignFee() {
		return realDesignFee;
	}

	public void setRealDesignFee(Double realDesignFee) {
		this.realDesignFee = realDesignFee;
	}

	public String getFromStatus() {
		return fromStatus;
	}

	public void setFromStatus(String fromStatus) {
		this.fromStatus = fromStatus;
	}

	public String getToStatus() {
		return toStatus;
	}

	public void setToStatus(String toStatus) {
		this.toStatus = toStatus;
	}

	public String getIgnoreCustRight() {
		return ignoreCustRight;
	}

	public void setIgnoreCustRight(String ignoreCustRight) {
		this.ignoreCustRight = ignoreCustRight;
	}

	public String getPrjDeptLeader() {
		return prjDeptLeader;
	}

	public void setPrjDeptLeader(String prjDeptLeader) {
		this.prjDeptLeader = prjDeptLeader;
	}

	public String getCodes() {
		return codes;
	}

	public void setCodes(String codes) {
		this.codes = codes;
	}

	public String getJCZYDesignCode() {
		return JCZYDesignCode;
	}

	public void setJCZYDesignCode(String jCZYDesignCode) {
		JCZYDesignCode = jCZYDesignCode;
	}

	public String getJCZYDesignerDescr() {
		return JCZYDesignerDescr;
	}

	public void setJCZYDesignerDescr(String jCZYDesignerDescr) {
		JCZYDesignerDescr = jCZYDesignerDescr;
	}

	public String getSoftPlanRemark() {
		return softPlanRemark;
	}

	public void setSoftPlanRemark(String softPlanRemark) {
		this.softPlanRemark = softPlanRemark;
	}

	public Double getSoftFeePer() {
		return softFeePer;
	}

	public void setSoftFeePer(Double softFeePer) {
		this.softFeePer = softFeePer;
	}

	public Double getManageFeeSoftPer() {
		return manageFeeSoftPer;
	}

	public void setManageFeeSoftPer(Double manageFeeSoftPer) {
		this.manageFeeSoftPer = manageFeeSoftPer;
	}

	public Double getSoftDiscPer() {
		return softDiscPer;
	}

	public void setSoftDiscPer(Double softDiscPer) {
		this.softDiscPer = softDiscPer;
	}

	public Double getManageFeeIntPer() {
		return manageFeeIntPer;
	}

	public void setManageFeeIntPer(Double manageFeeIntPer) {
		this.manageFeeIntPer = manageFeeIntPer;
	}

	public Double getIntegrateFeePer() {
		return integrateFeePer;
	}

	public void setIntegrateFeePer(Double integrateFeePer) {
		this.integrateFeePer = integrateFeePer;
	}

	public Double getIntegrateDiscPer() {
		return integrateDiscPer;
	}

	public void setIntegrateDiscPer(Double integrateDiscPer) {
		this.integrateDiscPer = integrateDiscPer;
	}

	public String getVisitType() {
		return visitType;
	}

	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	public String getIsCupboard() {
		return IsCupboard;
	}

	public void setIsCupboard(String isCupboard) {
		IsCupboard = isCupboard;
	}

	public String getPrjRegionCode() {
		return prjRegionCode;
	}

	public void setPrjRegionCode(String prjRegionCode) {
		this.prjRegionCode = prjRegionCode;
	}

	public String getPrjRegionCodeDescr() {
		return prjRegionCodeDescr;
	}

	public void setPrjRegionCodeDescr(String prjRegionCodeDescr) {
		this.prjRegionCodeDescr = prjRegionCodeDescr;
	}

	public String getDept1Descr() {
		return dept1Descr;
	}

	public void setDept1Descr(String dept1Descr) {
		this.dept1Descr = dept1Descr;
	}

	public String getDept2Descr() {
		return dept2Descr;
	}

	public void setDept2Descr(String dept2Descr) {
		this.dept2Descr = dept2Descr;
	}

	public String getProjectManPhone() {
		return projectManPhone;
	}

	public void setProjectManPhone(String projectManPhone) {
		this.projectManPhone = projectManPhone;
	}

	public String getIsAddAllInfo() {
		return isAddAllInfo;
	}

	public void setIsAddAllInfo(String isAddAllInfo) {
		this.isAddAllInfo = isAddAllInfo;
	}
	
	public String getDesignPhone() {
		return designPhone;
	}

	public void setDesignPhone(String designPhone) {
		this.designPhone = designPhone;
	}

	public Integer getContractDay() {
		return contractDay;
	}

	public void setContractDay(Integer contractDay) {
		this.contractDay = contractDay;
	}

	public Double getWallArea() {
		return wallArea;
	}

	public void setWallArea(Double wallArea) {
		this.wallArea = wallArea;
	}

	public String getPerfPercCode() {
		return perfPercCode;
	}

	public void setPerfPercCode(String perfPercCode) {
		this.perfPercCode = perfPercCode;
	}

	public Double getStdDesignFee() {
		return stdDesignFee;
	}

	public void setStdDesignFee(Double stdDesignFee) {
		this.stdDesignFee = stdDesignFee;
	}

	public Double getReturnDesignFee() {
		return returnDesignFee;
	}

	public void setReturnDesignFee(Double returnDesignFee) {
		this.returnDesignFee = returnDesignFee;
	}

	public Double getSoftTokenAmount() {
		return softTokenAmount;
	}

	public void setSoftTokenAmount(Double softTokenAmount) {
		this.softTokenAmount = softTokenAmount;
	}

	public String getSoftTokenNo() {
		return softTokenNo;
	}

	public void setSoftTokenNo(String softTokenNo) {
		this.softTokenNo = softTokenNo;
	}

	public Double getMarketFund() {
		return marketFund;
	}

	public void setMarketFund(Double marketFund) {
		this.marketFund = marketFund;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Double getJzyjjs() {
		return jzyjjs;
	}

	public void setJzyjjs(Double jzyjjs) {
		this.jzyjjs = jzyjjs;
	}

	public double getWallArea6() {
		return wallArea6;
	}

	public void setWallArea6(double wallArea6) {
		this.wallArea6 = wallArea6;
	}

	public double getWallArea12() {
		return wallArea12;
	}

	public void setWallArea12(double wallArea12) {
		this.wallArea12 = wallArea12;
	}

	public double getWallArea24() {
		return wallArea24;
	}

	public void setWallArea24(double wallArea24) {
		this.wallArea24 = wallArea24;
	}

	public String getIsContainDraftsMan() {
		return isContainDraftsMan;
	}

	public void setIsContainDraftsMan(String isContainDraftsMan) {
		this.isContainDraftsMan = isContainDraftsMan;
	}

	public String getBaseTempNo() {
		return baseTempNo;
	}

	public void setBaseTempNo(String baseTempNo) {
		this.baseTempNo = baseTempNo;
	}

	public String getMainTempNo() {
		return mainTempNo;
	}

	public void setMainTempNo(String mainTempNo) {
		this.mainTempNo = mainTempNo;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getOldDesignMan() {
		return oldDesignMan;
	}

	public void setOldDesignMan(String oldDesignMan) {
		this.oldDesignMan = oldDesignMan;
	}

	public String getOldBusinessMan() {
		return oldBusinessMan;
	}

	public void setOldBusinessMan(String oldBusinessMan) {
		this.oldBusinessMan = oldBusinessMan;
	}

	public String getOldAgainman() {
		return oldAgainman;
	}

	public void setOldAgainman(String oldAgainman) {
		this.oldAgainman = oldAgainman;
	}

	public Double getInnerArea() {
		return innerArea;
	}

	public void setInnerArea(Double innerArea) {
		this.innerArea = innerArea;
	}

	public Date getSendDateFrom() {
		return sendDateFrom;
	}

	public void setSendDateFrom(Date sendDateFrom) {
		this.sendDateFrom = sendDateFrom;
	}

	public Date getSendDateTo() {
		return sendDateTo;
	}

	public void setSendDateTo(Date sendDateTo) {
		this.sendDateTo = sendDateTo;
	}

	public Integer getCustAccountPk() {
		return custAccountPk;
	}

	public void setCustAccountPk(Integer custAccountPk) {
		this.custAccountPk = custAccountPk;
	}

	public String getIsWaterItemCtrl() {
		return isWaterItemCtrl;
	}

	public void setIsWaterItemCtrl(String isWaterItemCtrl) {
		this.isWaterItemCtrl = isWaterItemCtrl;
	}

	public String getAppSoftRemark() {
		return appSoftRemark;
	}

	public void setAppSoftRemark(String appSoftRemark) {
		this.appSoftRemark = appSoftRemark;
	}

	public String getIsGetDetail() {
		return isGetDetail;
	}

	public void setIsGetDetail(String isGetDetail) {
		this.isGetDetail = isGetDetail;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public Double getPerfMarkup() {
		return perfMarkup;
	}

	public void setPerfMarkup(Double perfMarkup) {
		this.perfMarkup = perfMarkup;
	}

	public String getInstallElev() {
		return installElev;
	}

	public void setInstallElev(String installElev) {
		this.installElev = installElev;
	}

	public Double getCarryFloor() {
		return carryFloor;
	}

	public void setCarryFloor(Double carryFloor) {
		this.carryFloor = carryFloor;
	}
	
	public String getPhoneOrConId() {
		return phoneOrConId;
	}

	public void setPhoneOrConId(String phoneOrConId) {
		this.phoneOrConId = phoneOrConId;
	}

	public String getItemType12ZC() {
		return itemType12ZC;
	}

	public void setItemType12ZC(String itemType12ZC) {
		this.itemType12ZC = itemType12ZC;
	}

	public String getItemType12RZ() {
		return itemType12RZ;
	}

	public void setItemType12RZ(String itemType12RZ) {
		this.itemType12RZ = itemType12RZ;
	}

	public String getIsWaterCtrl() {
		return isWaterCtrl;
	}

	public void setIsWaterCtrl(String isWaterCtrl) {
		this.isWaterCtrl = isWaterCtrl;
	}

	public String getPrjDepartment1() {
		return prjDepartment1;
	}

	public void setPrjDepartment1(String prjDepartment1) {
		this.prjDepartment1 = prjDepartment1;
	}

	public String getPrjDepartment2() {
		return prjDepartment2;
	}

	public void setPrjDepartment2(String prjDepartment2) {
		this.prjDepartment2 = prjDepartment2;
	}

	public Integer getMtCustInfoPK() {
		return mtCustInfoPK;
	}

	public void setMtCustInfoPK(Integer mtCustInfoPK) {
		this.mtCustInfoPK = mtCustInfoPK;
	}

	public Integer getAreaFrom() {
		return areaFrom;
	}

	public void setAreaFrom(Integer areaFrom) {
		this.areaFrom = areaFrom;
	}

	public Integer getAreaTo() {
		return areaTo;
	}

	public void setAreaTo(Integer areaTo) {
		this.areaTo = areaTo;
	}

	public String getIsInitSign() {
		return isInitSign;
	}

	public void setIsInitSign(String isInitSign) {
		this.isInitSign = isInitSign;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public Date getPlanSendDateSoft() {
		return planSendDateSoft;
	}

	public void setPlanSendDateSoft(Date planSendDateSoft) {
		this.planSendDateSoft = planSendDateSoft;
	}

	public String getJcSpecMan() {
		return JcSpecMan;
	}

	public void setJcSpecMan(String jcSpecMan) {
		JcSpecMan = jcSpecMan;
	}

	public String getCgSpecMan() {
		return CgSpecMan;
	}

	public void setCgSpecMan(String cgSpecMan) {
		CgSpecMan = cgSpecMan;
	}

	public String getJcSpecManDescr() {
		return JcSpecManDescr;
	}

	public void setJcSpecManDescr(String jcSpecManDescr) {
		JcSpecManDescr = jcSpecManDescr;
	}

	public String getCgSpecManDescr() {
		return CgSpecManDescr;
	}

	public void setCgSpecManDescr(String cgSpecManDescr) {
		CgSpecManDescr = cgSpecManDescr;
	}

	public String getWorkerCode() {
		return workerCode;
	}

	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}

	public String getContainOilPaint() {
		return containOilPaint;
	}

	public void setContainOilPaint(String containOilPaint) {
		this.containOilPaint = containOilPaint;
	}

	public String getCzybh() {
		return czybh;
	}

	public void setCzybh(String czybh) {
		this.czybh = czybh;
	}

	public String getConfItemType() {
		return confItemType;
	}

	public void setConfItemType(String confItemType) {
		this.confItemType = confItemType;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public String getIsNotPrint() {
		return isNotPrint;
	}

	public void setIsNotPrint(String isNotPrint) {
		this.isNotPrint = isNotPrint;
	}

	public String getMainItemOk() {
		return mainItemOk;
	}

	public void setMainItemOk(String mainItemOk) {
		this.mainItemOk = mainItemOk;
	}

	public Double getDesignRiskFund() {
		return designRiskFund;
	}

	public void setDesignRiskFund(Double designRiskFund) {
		this.designRiskFund = designRiskFund;
	}

	public Double getPrjManRiskFund() {
		return prjManRiskFund;
	}

	public void setPrjManRiskFund(Double prjManRiskFund) {
		this.prjManRiskFund = prjManRiskFund;
	}

	public Date getToConstructDateFrom() {
		return toConstructDateFrom;
	}

	public void setToConstructDateFrom(Date toConstructDateFrom) {
		this.toConstructDateFrom = toConstructDateFrom;
	}

	public Date getToConstructDateTo() {
		return toConstructDateTo;
	}

	public void setToConstructDateTo(Date toConstructDateTo) {
		this.toConstructDateTo = toConstructDateTo;
	}

	public Date getPrgRmkDate() {
		return PrgRmkDate;
	}

	public void setPrgRmkDate(Date prgRmkDate) {
		PrgRmkDate = prgRmkDate;
	}

	public Double getToiletQty() {
		return toiletQty;
	}

	public void setToiletQty(Double toiletQty) {
		this.toiletQty = toiletQty;
	}

	public Double getCabinetQty() {
		return cabinetQty;
	}

	public void setCabinetQty(Double cabinetQty) {
		this.cabinetQty = cabinetQty;
	}

	public Date getPrgRmkDateTo() {
		return prgRmkDateTo;
	}

	public void setPrgRmkDateTo(Date prgRmkDateTo) {
		this.prgRmkDateTo = prgRmkDateTo;
	}

	public int getStopDays() {
		return stopDays;
	}

	public void setStopDays(int stopDays) {
		this.stopDays = stopDays;
	}

	public String getIsSalaryConfirm() {
		return isSalaryConfirm;
	}

	public void setIsSalaryConfirm(String isSalaryConfirm) {
		this.isSalaryConfirm = isSalaryConfirm;
	}

	public Date getDeliverDateFrom() {
		return deliverDateFrom;
	}

	public void setDeliverDateFrom(Date deliverDateFrom) {
		this.deliverDateFrom = deliverDateFrom;
	}

	public Date getDeliverDateTo() {
		return deliverDateTo;
	}

	public void setDeliverDateTo(Date deliverDateTo) {
		this.deliverDateTo = deliverDateTo;
	}

	public String getDeepDesignMan() {
		return deepDesignMan;
	}

	public void setDeepDesignMan(String deepDesignMan) {
		this.deepDesignMan = deepDesignMan;
	}

	public String getDeepDesignManDescr() {
		return deepDesignManDescr;
	}

	public void setDeepDesignManDescr(String deepDesignManDescr) {
		this.deepDesignManDescr = deepDesignManDescr;
	}

	public String getMeasureMan() {
		return measureMan;
	}

	public void setMeasureMan(String measureMan) {
		this.measureMan = measureMan;
	}

	public String getMeasureManDescr() {
		return measureManDescr;
	}

	public void setMeasureManDescr(String measureManDescr) {
		this.measureManDescr = measureManDescr;
	}

	public String getDocumentNoFrom() {
		return documentNoFrom;
	}

	public void setDocumentNoFrom(String documentNoFrom) {
		this.documentNoFrom = documentNoFrom;
	}

	public String getDocumentNoTo() {
		return documentNoTo;
	}

	public void setDocumentNoTo(String documentNoTo) {
		this.documentNoTo = documentNoTo;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getIsHoliConstruct() {
		return isHoliConstruct;
	}

	public void setIsHoliConstruct(String isHoliConstruct) {
		this.isHoliConstruct = isHoliConstruct;
	}
	public String getTaxCallType() {
		return taxCallType;
	}

	public void setTaxCallType(String taxCallType) {
		this.taxCallType = taxCallType;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDepartment_emp() {
		return department_emp;
	}

	public void setDepartment_emp(String department_emp) {
		this.department_emp = department_emp;
	}

	public String getPositionType() {
		return positionType;
	}

	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}

	public String getDepType() {
		return depType;
	}

	public void setDepType(String depType) {
		this.depType = depType;
	}

	public String getShowAll() {
		return showAll;
	}

	public void setShowAll(String showAll) {
		this.showAll = showAll;
	}

	public String getNeedAgainMan() {
		return needAgainMan;
	}

	public void setNeedAgainMan(String needAgainMan) {
		this.needAgainMan = needAgainMan;
	}

	public String getIsFullColorDraw() {
		return isFullColorDraw;
	}

	public void setIsFullColorDraw(String isFullColorDraw) {
		this.isFullColorDraw = isFullColorDraw;
	}

	public String getDrawNoChg() {
		return drawNoChg;
	}

	public void setDrawNoChg(String drawNoChg) {
		this.drawNoChg = drawNoChg;
	}

	public Date getDrawNoChgDate() {
		return drawNoChgDate;
	}

	public void setDrawNoChgDate(Date drawNoChgDate) {
		this.drawNoChgDate = drawNoChgDate;
	}

	public Double getDesignerMaxDiscAmount() {
		return designerMaxDiscAmount;
	}

	public void setDesignerMaxDiscAmount(Double designerMaxDiscAmount) {
		this.designerMaxDiscAmount = designerMaxDiscAmount;
	}

	public Double getDirectorMaxDiscAmount() {
		return directorMaxDiscAmount;
	}

	public void setDirectorMaxDiscAmount(Double directorMaxDiscAmount) {
		this.directorMaxDiscAmount = directorMaxDiscAmount;
	}

	public Double getFrontEndDiscAmount() {
		return frontEndDiscAmount;
	}

	public void setFrontEndDiscAmount(Double frontEndDiscAmount) {
		this.frontEndDiscAmount = frontEndDiscAmount;
	}

	public Double getCmpDiscAmount() {
		return cmpDiscAmount;
	}

	public void setCmpDiscAmount(Double cmpDiscAmount) {
		this.cmpDiscAmount = cmpDiscAmount;
	}

	public String getCustKind() {
		return custKind;
	}

	public void setCustKind(String custKind) {
		this.custKind = custKind;
	}

	public String getIsPartDecorate() {
		return isPartDecorate;
	}

	public void setIsPartDecorate(String isPartDecorate) {
		this.isPartDecorate = isPartDecorate;
	}

	public String getProgStage() {
		return progStage;
	}

	public void setProgStage(String progStage) {
		this.progStage = progStage;
	}

	public String getReturnCount() {
		return returnCount;
	}

	public void setReturnCount(String returnCount) {
		this.returnCount = returnCount;
	}

	public String getMustImportTemp() {
		return mustImportTemp;
	}

	public void setMustImportTemp(String mustImportTemp) {
		this.mustImportTemp = mustImportTemp;
	}

	public String getCanUseComItem() {
		return canUseComItem;
	}

	public void setCanUseComItem(String canUseComItem) {
		this.canUseComItem = canUseComItem;
	}
	
    public String getDiscTokenNo() {
        return discTokenNo;
    }

    public void setDiscTokenNo(String discTokenNo) {
        this.discTokenNo = discTokenNo;
    }

    public double getDiscTokenAmount() {
        return discTokenAmount;
    }

    public void setDiscTokenAmount(double discTokenAmount) {
        this.discTokenAmount = discTokenAmount;
    }

    public String getAcceptedPrjItem() {
        return acceptedPrjItem;
    }

    public void setAcceptedPrjItem(String acceptedPrjItem) {
        this.acceptedPrjItem = acceptedPrjItem;
    }

    public String getNotAcceptedPrjItem() {
        return notAcceptedPrjItem;
    }

    public void setNotAcceptedPrjItem(String notAcceptedPrjItem) {
        this.notAcceptedPrjItem = notAcceptedPrjItem;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getInstallationStatus() {
        return installationStatus;
    }

    public void setInstallationStatus(String installationStatus) {
        this.installationStatus = installationStatus;
    }

	public Integer getDelayDays() {
		return delayDays;
	}

	public void setDelayDays(Integer delayDays) {
		this.delayDays = delayDays;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCarryRemark() {
		return carryRemark;
	}

	public void setCarryRemark(String carryRemark) {
		this.carryRemark = carryRemark;
	}

	public String getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(String isComplete) {
		this.isComplete = isComplete;
	}

	public String getExcludeComplete() {
		return excludeComplete;
	}

	public void setExcludeComplete(String excludeComplete) {
		this.excludeComplete = excludeComplete;
	}

	public String getSignDateFirstFrom() {
		return signDateFirstFrom;
	}

	public void setSignDateFirstFrom(String signDateFirstFrom) {
		this.signDateFirstFrom = signDateFirstFrom;
	}

	public String getSignDateFirstTo() {
		return signDateFirstTo;
	}

	public void setSignDateFirstTo(String signDateFirstTo) {
		this.signDateFirstTo = signDateFirstTo;
	}

    public Date getMeasureDateFrom() {
        return measureDateFrom;
    }

    public void setMeasureDateFrom(Date measureDateFrom) {
        this.measureDateFrom = measureDateFrom;
    }

    public Date getMeasureDateTo() {
        return measureDateTo;
    }

    public void setMeasureDateTo(Date measureDateTo) {
        this.measureDateTo = measureDateTo;
    }
    
    public Date getPriceDateFrom() {
        return priceDateFrom;
    }

    public void setPriceDateFrom(Date priceDateFrom) {
        this.priceDateFrom = priceDateFrom;
    }

    public Date getPriceDateTo() {
        return priceDateTo;
    }

    public void setPriceDateTo(Date priceDateTo) {
        this.priceDateTo = priceDateTo;
    }

    public Date getDesignDrawingDateFrom() {
        return designDrawingDateFrom;
    }

    public void setDesignDrawingDateFrom(Date designDrawingDateFrom) {
        this.designDrawingDateFrom = designDrawingDateFrom;
    }

    public Date getDesignDrawingDateTo() {
        return designDrawingDateTo;
    }

    public void setDesignDrawingDateTo(Date designDrawingDateTo) {
        this.designDrawingDateTo = designDrawingDateTo;
    }

    public String getDesignStatus() {
        return designStatus;
    }

    public void setDesignStatus(String designStatus) {
        this.designStatus = designStatus;
    }

    public String getContactRole() {
        return contactRole;
    }

    public void setContactRole(String contactRole) {
        this.contactRole = contactRole;
    }
    
    public String getDeclareMan() {
		return declareMan;
	}

	public void setDeclareMan(String declareMan) {
		this.declareMan = declareMan;
	}

	public String getDeclareManName() {
		return declareManName;
	}

	public void setDeclareManName(String declareManName) {
		this.declareManName = declareManName;
	}

	public String getCrtCzy() {
		return crtCzy;
	}

	public void setCrtCzy(String crtCzy) {
		this.crtCzy = crtCzy;
	}

	public String getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(String paidAmount) {
		this.paidAmount = paidAmount;
	}

	public String getDayRange() {
		return dayRange;
	}

	public void setDayRange(String dayRange) {
		this.dayRange = dayRange;
	}

	public String getOldRepClause() {
		return oldRepClause;
	}

	public void setOldRepClause(String oldRepClause) {
		this.oldRepClause = oldRepClause;
	}

	public Double getDiscAmount() {
		return discAmount;
	}

	public void setDiscAmount(Double discAmount) {
		this.discAmount = discAmount;
	}

	public Date getExpectMoveIntoDate() {
		return expectMoveIntoDate;
	}

	public void setExpectMoveIntoDate(Date expectMoveIntoDate) {
		this.expectMoveIntoDate = expectMoveIntoDate;
	}

	public String getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}

	public String getGiftInDisc() {
		return giftInDisc;
	}

	public void setGiftInDisc(String giftInDisc) {
		this.giftInDisc = giftInDisc;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getMoneyInFull() {
		return moneyInFull;
	}

	public void setMoneyInFull(String moneyInFull) {
		this.moneyInFull = moneyInFull;
	}

	public String getAgainType() {
		return againType;
	}

	public void setAgainType(String againType) {
		this.againType = againType;
	}
	
    public Date getSendJobDateFrom() {
        return sendJobDateFrom;
    }

    public void setSendJobDateFrom(Date sendJobDateFrom) {
        this.sendJobDateFrom = sendJobDateFrom;
    }

    public Date getSendJobDateTo() {
        return sendJobDateTo;
    }

    public void setSendJobDateTo(Date sendJobDateTo) {
        this.sendJobDateTo = sendJobDateTo;
    }

	public Date getFirstSignDateFrom() {
		return firstSignDateFrom;
	}

	public void setFirstSignDateFrom(Date firstSignDateFrom) {
		this.firstSignDateFrom = firstSignDateFrom;
	}

	public Date getFirstSignDateTo() {
		return firstSignDateTo;
	}

	public void setFirstSignDateTo(Date firstSignDateTo) {
		this.firstSignDateTo = firstSignDateTo;
	}

    public String getWhetherNotifySend() {
        return whetherNotifySend;
    }

    public void setWhetherNotifySend(String whetherNotifySend) {
        this.whetherNotifySend = whetherNotifySend;
    }

    public Date getNotifySendDateFrom() {
        return notifySendDateFrom;
    }

    public void setNotifySendDateFrom(Date notifySendDateFrom) {
        this.notifySendDateFrom = notifySendDateFrom;
    }

    public Date getNotifySendDateTo() {
        return notifySendDateTo;
    }

    public void setNotifySendDateTo(Date notifySendDateTo) {
        this.notifySendDateTo = notifySendDateTo;
    }

    public String getDesignCompleted() {
        return designCompleted;
    }

    public void setDesignCompleted(String designCompleted) {
        this.designCompleted = designCompleted;
    }
    
}
