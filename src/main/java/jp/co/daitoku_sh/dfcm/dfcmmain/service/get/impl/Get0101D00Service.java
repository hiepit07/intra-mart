/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl
 * ファイル名:Get0101D00Service.java
 * 
 *<p>作成者:HiepTruong
 * 作成日:2015/10/07
 * 
 *<p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/07 1.00 ABV)HiepTruong 新規開発
 * -------------------------------------------------------------------------
 * 
 *<p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.service.get.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.ui.Model;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.db.mapper.TblGet01JigConfirmMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.TblSei01UrkMshHeadMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.TblUri01HeadMapper;
import jp.co.daitoku_sh.dfcm.common.db.model.TblGet01JigConfirm;
import jp.co.daitoku_sh.dfcm.common.db.model.TblGet01JigConfirmExample;
import jp.co.daitoku_sh.dfcm.common.db.model.TblSei01UrkMshHead;
import jp.co.daitoku_sh.dfcm.common.db.model.TblSei01UrkMshHeadExample;
import jp.co.daitoku_sh.dfcm.common.db.model.TblSei01UrkMshHeadKey;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ObjCombobox;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.get.FormGet0101d00;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.get.GetDeterminationInfoGet0101d00;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.get.GetHeaderUirageGet0101d00;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.get.GetOfficeInformationGet0101d00;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.get.impl.Get0101D00Dao;
/**
 * サービスクラス
 * 
 * @author HiepTruong
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class Get0101D00Service extends AbsService {

  /** メッセージPropertiesファイルを読む. */
  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  @Autowired
  @Qualifier("get0101D00Dao")
  private Get0101D00Dao get0101D00Dao;

  @Autowired
  private TblGet01JigConfirmMapper tblGet01JigConfirmMapper;

  @Autowired
  private TblSei01UrkMshHeadMapper tblSei01UrkMshHeadMapper;

  @Autowired
  private TblGet01JigConfirmMapper blGet01JigConfirmMapper;

  @Autowired
  private TblUri01HeadMapper tblUri01Headmapper;

  @Autowired
  @Qualifier("commonDao")
  private CommonDao commonDao;

  @Autowired
  @Qualifier("txManager")
  private PlatformTransactionManager txManager;
  
  @Autowired
  @Qualifier("appContext")
  private ApplicationContext appContext;
  private String haitaInsuserid;
  private String haitaUpduserid;

  public String getHaitaInsuserid() {
    return haitaInsuserid;
  }

  public void setHaitaInsuserid(String haitaInsuserid) {
    this.haitaInsuserid = haitaInsuserid;
  }

  public String getHaitaInspgid() {
    return "DFCM-GET01-01D00";
  }

  public String getHaitaInsymd() {
    return DateUtil.getSysDate();
  }

  public String getHaitaInstime() {
    return DateUtil.getSysTime();
  }

  public String getHaitaUpduserid() {
    return haitaUpduserid;
  }

  public void setHaitaUpduserid(String haitaUpduserid) {
    this.haitaUpduserid = haitaUpduserid;
  }

  public String getHaitaUpdpgid() {
    return "DFCM-GET01-01D00";
  }

  public String getHaitaUpdymd() {
    return DateUtil.getSysDate();
  }

  public String getHaitaUpdtime() {
    return DateUtil.getSysTime();
  }

  /* ログ出力用 */
  private Dfcmlogger logger = new Dfcmlogger();

  public TblUri01HeadMapper getTblUri01Headmapper() {
    return tblUri01Headmapper;
  }

  public void setTblUri01Headmapper(TblUri01HeadMapper tblUri01Headmapper) {
    this.tblUri01Headmapper = tblUri01Headmapper;
  }

  public TblGet01JigConfirmMapper getBlGet01JigConfirmMapper() {
    return blGet01JigConfirmMapper;
  }

  public void setBlGet01JigConfirmMapper(
      TblGet01JigConfirmMapper blGet01JigConfirmMapper) {
    this.blGet01JigConfirmMapper = blGet01JigConfirmMapper;
  }

  public Dfcmlogger getLogger() {
    return logger;
  }

  public void setLogger(Dfcmlogger logger) {
    this.logger = logger;
  }

  public TblGet01JigConfirmMapper getTblGet01JigConfirmMapper() {
    return tblGet01JigConfirmMapper;
  }

  public void setTblGet01JigConfirmMapper(TblGet01JigConfirmMapper tblGet01JigConfirmMapper) {
    this.tblGet01JigConfirmMapper = tblGet01JigConfirmMapper;
  }

  public TblSei01UrkMshHeadMapper getTblSei01UrkMshHeadMapper() {
    return tblSei01UrkMshHeadMapper;
  }

  public void setTblSei01UrkMshHeadMapper(
      TblSei01UrkMshHeadMapper tblSei01UrkMshHeadMapper) {
    this.tblSei01UrkMshHeadMapper = tblSei01UrkMshHeadMapper;
  }

  public Get0101D00Dao getGet0101D00Dao() {
    return get0101D00Dao;
  }

  public void setGet0101D00Dao(Get0101D00Dao get0101d00Dao) {
    get0101D00Dao = get0101d00Dao;
  }

  public CommonDao getCommonDao() {
    return commonDao;
  }

  public void setCommonDao(CommonDao commonDao) {
    this.commonDao = commonDao;
  }

  public void updateUrikakeMishu(TblSei01UrkMshHead data,
      TblSei01UrkMshHeadExample exam) {
    this.tblSei01UrkMshHeadMapper.updateByExampleSelective(data, exam);
  }

  public void updateCurrentProcessDate(TblGet01JigConfirm data,
      TblGet01JigConfirmExample exam) {
    // this.tblSei01UrkMshHeadMapper.updateByExample(record, example) (data,
    // exam);
  }

  /**
   * 排他チェック.
   * 
   * @param key:担当者コード
   * @param strHaitaDate:排他日付
   * @param strHaitaTime:排他時間
   * @return boolean true: エラー false: 普通
   */
  public boolean checkHaitaTblGet01JigConfirm(String key, String strHaitaDate,
      String strHaitaTime) {

    // strHaitaDate and strHaitaTime is old time
    TblGet01JigConfirm haita = this.getBlGet01JigConfirmMapper().selectByPrimaryKey(key);

    // database is empty
    if (haita == null) {
      return true;
    }

    if (strHaitaDate == null || strHaitaTime == null) {
      return false;
    }

    // 共通項目.修正年月日 ＋ 共通項目．修正時刻 > 表示時システム日付 の場合
    try {
      int intDbDate = Integer.parseInt(haita.getUpdYmd().trim());
      int intHaitaDate = Integer.parseInt(strHaitaDate.trim());
      int intDbTime = Integer.parseInt(haita.getUpdTime().trim());
      int intHaitaTime = Integer.parseInt(strHaitaTime.trim());

      // if current time in database large than time saved before (when select)
      if ((intDbDate > intHaitaDate) || (intDbDate == intHaitaDate && intDbTime > intHaitaTime)) {
        return false;
      }
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * 排他チェック.
   * 
   * @param key:担当者コード
   * @param strHaitaDate:排他日付
   * @param strHaitaTime:排他時間
   * @return boolean true: エラー false: 普通
   */
  public boolean checkHaitaTblSei01UrkMshHeadMapper(TblSei01UrkMshHeadKey key, String strHaitaDate,
      String strHaitaTime) {

    TblSei01UrkMshHead haita = this.tblSei01UrkMshHeadMapper.selectByPrimaryKey(key);

    // database is empty
    if (haita == null) {
      return true;
    }

    // 共通項目.修正年月日 ＋ 共通項目．修正時刻 > 表示時システム日付 の場合
    try {
      int intDbDate = Integer.parseInt(haita.getUpdYmd().trim());
      int intHaitaDate = Integer.parseInt(strHaitaDate.trim());
      int intDbTime = Integer.parseInt(haita.getUpdTime().trim());
      int intHaitaTime = Integer.parseInt(strHaitaTime.trim());

      // if current time in database large than time saved before (when select)
      if ((intDbDate > intHaitaDate) || (intDbDate == intHaitaDate && intDbTime > intHaitaTime)) {
        return false;
      }
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Defaultメッセージの取得.
   * 
   * @param model:モデル
   */
  public void getDefaultMess(Model model) {
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();

    DefaultMessages defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-I");
    defaultMsg.setMessageTitle("確認メッセージ");
    paramMess = new ArrayList<String>();
    paramMess.add("月次確定");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-I", paramMess));
    ArrayList<DefaultMessages> defaultMsgLst = new ArrayList<DefaultMessages>();
    defaultMsgLst.add(defaultMsg);

    model.addAttribute("defaultMessages", defaultMsgLst);
  }
  /**
   * フォームの初期化.
   * 
   * @param model ： フォームのModel
   * @param formGet0101d00 ： FormGet0101d00
   */
  public void init(Model model,HttpServletRequest request, FormGet0101d00 formGet0101d00) {
    // store lasted haita
    Map<String, Object> map = Util.getContentsFromSession(request);
   
    String userCode = String.valueOf(map.get(CommonConst.LOGIN_USER_CODE));
    setHaitaInsuserid(userCode);
    setHaitaUpduserid(userCode);

    getDefaultMess(model);
    // システム管理者フラグ値を取得する
    String sysAdminFlag = String.valueOf(map.get(CommonConst.LOGIN_USER_SYS_ADMIN_FLG)).trim();
    formGet0101d00.setSysAdminFlag(sysAdminFlag);
    model.addAttribute("haitaDate", getHaitaInsymd());
    model.addAttribute("haitaTime", getHaitaInstime());

    CommonGetSystemCom systemCom = new CommonGetSystemCom(getCommonDao(), txManager, appContext,
        readPropertiesFileService);
    String businessdatewk = systemCom.getAplDate();
    // 1-2 共通関数を使用し、業務日付を取得する
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    String systemDateTime = sdf.format(date);
    if (null == businessdatewk || "".compareToIgnoreCase(businessdatewk) <= 0) {
      businessdatewk = systemDateTime;
    }
    model.addAttribute("businessDate", businessdatewk);
    model.addAttribute("systemDateTime", systemDateTime);
    // end of 1

    // 2 start - 事業所情報取得
    List<ObjCombobox> officeinfo = null;
    if (sysAdminFlag.compareToIgnoreCase(CommonConst.SYS_ADMIN_FLG_VALID) == 0) {
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("delFlg", "0");
      params.put("businessDate", businessdatewk);
      List<GetOfficeInformationGet0101d00> lstOfficeInfo = getGet0101D00Dao().getGet0101D00Mapper()
          .getofficeinfo(params);
      // 2-1-1 該当する事業所情報が存在しない場合、エラーとする
      if (null == lstOfficeInfo || lstOfficeInfo.size() <= 0) {
        // error messages
        showErrorMessage_1_1_5_2(model);
        model.addAttribute("isEnableBtnConfirm", false);
        return ;
      } else {
        // 2-2 取得した事業所情報を[変数]事業所情報リストへ格納する（取得件数分、2-2を繰返す）
        officeinfo = new ArrayList<ObjCombobox>();
        ObjCombobox firstObj = new ObjCombobox();
        firstObj.setCode("");
        firstObj.setName("");
        officeinfo.add(firstObj);
        ObjCombobox office;
        for (GetOfficeInformationGet0101d00 lstOfficeInfoitem : lstOfficeInfo) {
          office = new ObjCombobox();
          office.setCode(lstOfficeInfoitem.getJgycd());
          office.setName( lstOfficeInfoitem.getJgycd() + " : " + lstOfficeInfoitem.getJgymei());
          officeinfo.add(office);
        }
      }
    }
    String loginOfficeCode = String.valueOf(map.get(CommonConst.LOGIN_USER_JIGYOSHO_CODE));
    String loginOfficeName = String.valueOf(map.get(CommonConst.LOGIN_USER_JIGYOSHO_MEI));

    // 2 end
    // 3 start 画面構成
    TblGet01JigConfirm tblGet01JigConfirmData = null;
    GetDeterminationInfoGet0101d00 determinationInfo = new GetDeterminationInfoGet0101d00();

 
    // 事業所月次確定情報から月次確定情報を取得する取得
      tblGet01JigConfirmData = getCommonDao()
          .getGetJigMapper()
          .selectByPrimaryKey(loginOfficeCode);
      if (null == tblGet01JigConfirmData) {
        // 3-2-1 該当データが存在しない場合、エラーとする
        showErrorMessage_1_1_5_3(model); 
        model.addAttribute("isEnableBtnConfirm", true);
        return ;
      //(2-2)該当データが存在する場合、取得項目を変数にセットする
      } else {
        determinationInfo.setOfficeCodewk(tblGet01JigConfirmData
            .getJigyoCode());
        determinationInfo.setOfficeNamewk(tblGet01JigConfirmData.getJigyoNm());
        determinationInfo.setMonthlyFixwk(tblGet01JigConfirmData
            .getDetermMon());
        determinationInfo.setMonthlyFixFlagwk(tblGet01JigConfirmData
            .getGetsujiFlg());
        determinationInfo.setProcessStartDateTimewk(tblGet01JigConfirmData
            .getStartDatetime());
        determinationInfo.setProcessEndDateTimewk(tblGet01JigConfirmData
            .getEndDatetime());
        determinationInfo.setJimuTantoNamewk(String.valueOf(map.get("UserNm")));
     
        if (determinationInfo.getMonthlyFixFlagwk().equalsIgnoreCase("1")) {
            determinationInfo.setThisMonthFixwk(tblGet01JigConfirmData.getPresentMon()
            +  "（本社確定待ち）");
        } else {
            if (determinationInfo.getMonthlyFixFlagwk().equalsIgnoreCase("0")) {
            determinationInfo.setThisMonthFixwk(tblGet01JigConfirmData.getPresentMon());
            }
        }
       
      }
      //3-3 画面項目に変数の値をセットする
    //  (1) [変数]セッション.システム管理者フラグ = '0'
      if (sysAdminFlag.equalsIgnoreCase(CommonConst.SYS_ADMIN)) {
           String lblConfirmAccMonth =  determinationInfo
           .getMonthlyFixwk().substring(0, 4) + "/" + determinationInfo
           .getMonthlyFixwk().substring(4, determinationInfo
           .getMonthlyFixwk().length());
            formGet0101d00.setLblConfirmAccMonth(lblConfirmAccMonth);
      model.addAttribute("lblConfirmAccMonth",lblConfirmAccMonth);
      String lblProcStartTime = determinationInfo
           .getProcessStartDateTimewk().substring(0, 4) + "/" + determinationInfo
           .getProcessStartDateTimewk().substring(4, 6) + "/"
            + determinationInfo.getProcessStartDateTimewk().substring(6, 8) + " "
           + determinationInfo.getProcessStartDateTimewk().substring(8, 10) + ":"
           + determinationInfo.getProcessStartDateTimewk().substring(10, 12);
      formGet0101d00.setLblProcStartTime(lblProcStartTime);
      model.addAttribute("lblProcStartTime", lblProcStartTime);
      String lblProcEndTime = determinationInfo
           .getProcessEndDateTimewk().substring(0, 4) + "/" + determinationInfo
        .getProcessEndDateTimewk().substring(4, 6) + "/"
         + determinationInfo  .getProcessEndDateTimewk().substring(6, 8) + " "
          + determinationInfo.getProcessEndDateTimewk().substring(8, 10) + ":"
         + determinationInfo .getProcessEndDateTimewk().substring(10, 12);
      formGet0101d00.setLblProcEndTime(lblProcEndTime);
      model.addAttribute("lblProcEndTime", lblProcEndTime);
      String lblCurrAccMonth = determinationInfo
           .getThisMonthFixwk().substring(0, 4) + "/" + determinationInfo
           .getThisMonthFixwk().substring(4, determinationInfo
            .getThisMonthFixwk().length());
      formGet0101d00.setLblCurrAccMonth(lblCurrAccMonth);
      model.addAttribute("lblCurrAccMonth",lblCurrAccMonth );
      model.addAttribute("displayCbOfficeInfo", "");
      // 3-4-2 事業所情報リストの値をセットする
      model.addAttribute("UI_officeInfo", officeinfo);
      model.addAttribute("UI_officeInfoDefault", loginOfficeName);
      formGet0101d00.setDdlJigyouSho(loginOfficeCode);
      // 3-4-1 [変数]セッション.システム管理者フラグ = '0'（無効）の場合、[画面]事業所を非表示とする
      } else {
      model.addAttribute("displayCbOfficeInfo", "display_none");
      }
      // 3-5 disable [画面]確定ボタン
      model.addAttribute("isEnableBtnConfirm", false);
    // 4-1 未処理の請求締め処理が存在するか売上ヘッダ情報でチェックする
    if (determinationInfo.getMonthlyFixFlagwk().equalsIgnoreCase("0")) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("Office_Code", determinationInfo.getOfficeCodewk());
        params.put("Bill_Date", determinationInfo.getThisMonthFixwk());
        params.put("Bill_Flg_or1", "0");
        params.put("Bill_Flg_or2", "9");
        params.put("Sts_Code", "1");
        List<GetHeaderUirageGet0101d00> lstHeaderUri = getGet0101D00Dao().getGet0101D00Mapper()
            .getHeaderUirageHeader(params);
        // 4-1-1 該当データが存在していた場合、エラーとする
        if (null != lstHeaderUri && lstHeaderUri.size() > 0) {
          showErrorMessage_1_1_5_4(model);
          model.addAttribute("isEnableBtnConfirm", false);
        } else {
          // (4-1-2) 該当データが存在しない場合、[画面]確定ボタンを活性化する
          model.addAttribute("isEnableBtnConfirm", true);
        }
    } else {
          model.addAttribute("isEnableBtnConfirm", false);
    }
    //(5-1-1) [変数]セッション.システム管理者フラグ = '1'（有効）の場合、[画面]事業所へフォーカスをセットする
    if (sysAdminFlag.equalsIgnoreCase("1")) {
      model.addAttribute("initfocus", "#ddlJigyouSho");
      //(5-1-2) [変数]セッション.システム管理者フラグ = '0'（無効）の場合、[画面]確定ボタンへフォーカスをセットする
    } else {
      model.addAttribute("initfocus", "#btnConfirm");
    }
  }
  /**
   * Return error 1-5-2, sheet1 in String
   * 
   * @return String
   */
  private void showErrorMessage_1_1_5_2(Model model) {
    ErrorMessages errorMessage = new ErrorMessages();
    ArrayList<String> paramMess = new ArrayList<String>();
    paramMess.add("事業所マスタ");
    paramMess.add("事業所情報");
    errorMessage.setMessageContent(readPropertiesFileService.getMessage(
        "COM009-E", paramMess));
    addErrorMessageToModel(errorMessage, model);
  }
  
  /**
   * Return error 1-5-3, sheet1 in String
   * 
   * @return String
   */
  private void showErrorMessage_1_1_5_3(Model model) {
    ErrorMessages errorMessage = new ErrorMessages();
    ArrayList<String> paramMess = new ArrayList<String>();
    paramMess.add("事業所月次確定情報");
    paramMess.add("事業所月次情報");
    errorMessage.setMessageContent(readPropertiesFileService.getMessage(
        "COM009-E", paramMess));
    addErrorMessageToModel(errorMessage, model);
  }
  
  /**
   * Return error 1-5-4, sheet1 in String
   * 
   * @return String
   */
  private void showErrorMessage_1_1_5_4(Model model) {
    ErrorMessages errorMessage = new ErrorMessages();
    ArrayList<String> paramMess = new ArrayList<String>();
    errorMessage.setMessageContent(readPropertiesFileService.getMessage(
        "GET001-E", paramMess));
    addErrorMessageToModel(errorMessage, model);
  }
  /**
   * Put error String into model
   * 
   * @Param ErrorMessages
   * @Param Model
   */
  private void addErrorMessageToModel(ErrorMessages errorMessage, Model model) {
    List<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    lstErrMess.add(errorMessage);
    model.addAttribute("errorMessages", lstErrMess);
  }
}
