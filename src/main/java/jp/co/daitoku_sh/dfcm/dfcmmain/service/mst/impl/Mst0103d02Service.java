/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl;
 * ファイル名:Mst0103d02Service.java
 * 
 * <p>作成者:ABV)TramChu
 * 作成日:2015/08/24
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/08/24 1.00 ABV)TramChu  新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.component.CustomerData;
import jp.co.daitoku_sh.dfcm.common.component.MstGeneralData;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCustomer;
import jp.co.daitoku_sh.dfcm.common.db.model.MstDatIdx;
import jp.co.daitoku_sh.dfcm.common.db.model.MstListForm;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSNohin;
import jp.co.daitoku_sh.dfcm.common.db.model.MstShop;
import jp.co.daitoku_sh.dfcm.common.db.model.MstShopKey;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetData;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.InputCheckCom;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ObjCombobox;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0103d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstCourseInfoMst0103d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0103d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstSJigyoInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstSpecificationsConfirmationMst0103d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstStoreInfoMst0103d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstUserInfoMst0103d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl.Mst0103d02Dao;

/**
 * サービスクラス
 * 
 * @author ABV)TramChu
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class Mst0103d02Service extends AbsService {

  @Autowired
  @Qualifier("mst0103d02Dao")
  private Mst0103d02Dao mst0103d02Dao;

  public Mst0103d02Dao getMst0103d02Dao() {
    return mst0103d02Dao;
  }

  public void setMst0103d02Dao(Mst0103d02Dao mst0103d02Dao) {
    this.mst0103d02Dao = mst0103d02Dao;
  }

  @Autowired
  @Qualifier("commonDao")
  private CommonDao commonDao;

  public CommonDao getCommonDao() {
    return commonDao;
  }

  public void setCommonDao(CommonDao commonDao) {
    this.commonDao = commonDao;
  }

  @Autowired
  private ApplicationContext appContext;

  // メッセージPropertiesファイルを読む
  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  /** トランザクション Commit / Rollback */
  PlatformTransactionManager txManager;
  /** ログ出力用 */
  private Dfcmlogger logger = new Dfcmlogger();

  /**
   * Defaultメッセージの取得
   * 
   * @param model model
   */
  public void getDefaultMess(Model model) {
    // メッセージのParam    
    ArrayList<DefaultMessages> defaultMsgLst;
    defaultMsgLst = new ArrayList<DefaultMessages>();
    DefaultMessages defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-I01");
    defaultMsg.setMessageTitle("確認メッセージ");
    ArrayList<String> paramMess;
    paramMess = new ArrayList<String>();
    paramMess.add("登録");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-I", paramMess));
    defaultMsgLst.add(defaultMsg);
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-I02");
    defaultMsg.setMessageTitle("確認メッセージ");
    paramMess = new ArrayList<String>();
    paramMess.add("画面表示時の状態に初期化");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-I", paramMess));
    defaultMsgLst.add(defaultMsg);
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-E", null));
    defaultMsgLst.add(defaultMsg);

    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM016-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM016-E", null));
    defaultMsgLst.add(defaultMsg);

    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM006-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM006-E", null));
    defaultMsgLst.add(defaultMsg);

    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM034-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM034-E", null));
    defaultMsgLst.add(defaultMsg);
    model.addAttribute("defaultMessages", defaultMsgLst);
  }

  /**
   * 画面構成
   * 
   * @param formMst0103d02 form display
   * @param formMst0103d01 back form
   * @param viewMode mode
   */
  public void init(FormMst0103d02 formMst0103d02, FormMst0103d01 formMst0103d01,
      String viewMode) {
    // [入力]検索条件保持クラスを退避する
    formMst0103d02.setFrm1Office(formMst0103d01.getDdlOffice());
    formMst0103d02.setFrm1CustomerCode(formMst0103d01.getTxtCustomerCode());
    formMst0103d02.setFrm1CustomerName(formMst0103d01.getTxtCustomerName());
    formMst0103d02.setFrm1StoreCode(formMst0103d01.getTxtStoreCode());
    formMst0103d02.setFrm1StoreName(formMst0103d01.getTxtStoreName());
    formMst0103d02.setFrm1chkCancelData(String.valueOf(formMst0103d01
        .getChkCancelData()));
    formMst0103d02.setMode(viewMode);
    formMst0103d02.setHaitaDate(formMst0103d01.getHaitaDate());
    formMst0103d02.setHaitaTime(formMst0103d01.getHaitaTime());
  }

  /**
   * 事業所の入力項目
   * 
   * @param formMst0103d02 form display
   * @param model A holder for model attributes
   * @return Boolean true: 普通 false: エラーが発生する。
   */
  public boolean setDataOffice(FormMst0103d02 formMst0103d02, Model model) {
    ArrayList<ObjCombobox> lstMstSJigyo = new ArrayList<ObjCombobox>();
    ArrayList<MstSJigyoInfo> lstMstSJigyoInfo = new ArrayList<MstSJigyoInfo>();
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("businessDate", formMst0103d02.getBusinessDate().toString());
    lstMstSJigyoInfo = mst0103d02Dao.getMst0103d02Mapper().getSJigyoInfo(
        params);
    if (lstMstSJigyoInfo != null && lstMstSJigyoInfo.size() > 0) {
      ObjCombobox objCombobox = new ObjCombobox();
      objCombobox.setCode("");
      objCombobox.setName("");
      lstMstSJigyo.add(objCombobox);
      for (MstSJigyoInfo mstSJigyoInfo : lstMstSJigyoInfo) {
        objCombobox = new ObjCombobox();
        objCombobox.setCode(mstSJigyoInfo.getJgycd());
        objCombobox.setName(mstSJigyoInfo.getJgycd() + " : " + mstSJigyoInfo
            .getJgymei());
        lstMstSJigyo.add(objCombobox);
      }
      model.addAttribute("lstMstSJigyo", lstMstSJigyo);
      return true;
    }
    return false;
  }

  /**
   * set data サンクス区分
   * 
   * @param formMst0103d02 form
   * @param model model
   * @param mstGeneralDatalst list GeneralData
   */
  public void setDataClassification(FormMst0103d02 formMst0103d02, Model model,
      List<MstGeneralData> mstGeneralDatalst) {
    ArrayList<ObjCombobox> lstClassification = new ArrayList<ObjCombobox>();
    ObjCombobox objCombobox = new ObjCombobox();
    objCombobox.setCode("");
    objCombobox.setName("");
    lstClassification.add(objCombobox);
    for (MstGeneralData mstGeneralData : mstGeneralDatalst) {
      objCombobox = new ObjCombobox();
      objCombobox.setCode(mstGeneralData.getGlCode());
      objCombobox.setName(mstGeneralData.getGlCode() + " : " + mstGeneralData
          .getTarget1());
      lstClassification.add(objCombobox);
    }
    model.addAttribute("lstClassification", lstClassification);
  }

  /**
   * set data 店舗集約条件
   * 
   * @param formMst0103d02 form
   * @param model model
   * @param mstGeneralDataLst list GeneralData
   */
  public void setDataCondition(FormMst0103d02 formMst0103d02, Model model,
      List<MstGeneralData> mstGeneralDataLst) {
    ArrayList<ObjCombobox> lstCondition = new ArrayList<ObjCombobox>();
    ObjCombobox objCombobox = new ObjCombobox();
    objCombobox.setCode("");
    objCombobox.setName("");
    lstCondition.add(objCombobox);
    for (MstGeneralData mstGeneralData : mstGeneralDataLst) {
      objCombobox = new ObjCombobox();
      objCombobox.setCode(mstGeneralData.getGlCode());
      objCombobox.setName(mstGeneralData.getGlCode() + " : " + mstGeneralData
          .getTarget1());
      lstCondition.add(objCombobox);
    }
    model.addAttribute("lstCondition", lstCondition);
  }

  /**
   * get StoreMaster
   * 
   * @param strBusinessDate 業務日付
   * @param customerCode 得意先コード
   * @param storeCode 店舗コード
   * @return StoreMaster
   */
  public MstUserInfoMst0103d02 getDataStoreMaster(
      String strBusinessDate, String customerCode,
      String storeCode) {
    MstUserInfoMst0103d02 lstStoreMaster = null;
    try {
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("businessDate", strBusinessDate);
      params.put("customerCode", customerCode);
      params.put("storeCode", storeCode);
      lstStoreMaster = mst0103d02Dao.getMst0103d02Mapper().getStoreMaster(
          params);
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }

    return lstStoreMaster;
  }


  /**
   * 納品センターリストセット
   * 
   * @param model model
   * @param strBusinessDate 業務日付
   * @param officeCodeWk 事業所
   * @param customerCode 得意先コード
   * @return Boolean true: 普通 false: エラーが発生する。
   */
  public boolean checkDataAccess(Model model, String strBusinessDate,
      String officeCodeWk, String customerCode) {
    ArrayList<MstSNohin> lstStoreMaster = null;
    ArrayList<ObjCombobox> lstDeliveryCenter = new ArrayList<ObjCombobox>();
    lstStoreMaster = getDeliveryCenter(strBusinessDate, officeCodeWk,
        customerCode);
    if (lstStoreMaster != null && lstStoreMaster.size() > 0) {
      if (lstStoreMaster.get(0).getNhscd().equals("null") || lstStoreMaster.get(
          0).getNhscd().equals("noData")) {
        model.addAttribute("ddlDeliveryCenterDisable", "true");
      } else {
        ObjCombobox objCombobox = new ObjCombobox();
        objCombobox.setCode("");
        objCombobox.setName("");
        lstDeliveryCenter.add(objCombobox);
        for (MstSNohin mstSNohin : lstStoreMaster) {
          objCombobox = new ObjCombobox();
          objCombobox.setCode(mstSNohin.getNhscd().toString());
          objCombobox.setName(mstSNohin.getNhscd().toString() + " : "
              + mstSNohin.getNhsmei());
          lstDeliveryCenter.add(objCombobox);
        }
        model.addAttribute("ddlDeliveryCenterDisable", "false");
        model.addAttribute("lstDeliveryCenter", lstDeliveryCenter);
        return true;
      }
    }
    return false;
  }
 
  /**
   * get data 納品センター
   * 
   * @param strBusinessDate 業務日付
   * @param officeCodeWk 事業所
   * @param customerCode 得意先コード
   * @return Boolean true: 普通 false: エラーが発生する。
   */
  public ArrayList<MstSNohin> getDeliveryCenter(String strBusinessDate,
      String officeCodeWk, String customerCode) {
    ArrayList<MstSNohin> lstStoreMaster = null;
    if (strBusinessDate.equals("")) {
      lstStoreMaster = new ArrayList<MstSNohin>();
      MstSNohin mstSNohin = new MstSNohin();
      mstSNohin.setNhscd("null");
      lstStoreMaster.add(mstSNohin);
      return lstStoreMaster;
    }
    if (officeCodeWk.equals("")) {
      lstStoreMaster = new ArrayList<MstSNohin>();
      MstSNohin mstSNohin = new MstSNohin();
      mstSNohin.setNhscd("null");
      lstStoreMaster.add(mstSNohin);
      return lstStoreMaster;
    }
    if (customerCode.equals("")) {
      lstStoreMaster = new ArrayList<MstSNohin>();
      MstSNohin mstSNohin = new MstSNohin();
      mstSNohin.setNhscd("null");
      lstStoreMaster.add(mstSNohin);
      return lstStoreMaster;
    }
    CommonGetData commonGetData = new CommonGetData(commonDao,
        readPropertiesFileService);
    CustomerData customerData = null;
    customerData = commonGetData.getCustomerData(customerCode, null, Integer
        .parseInt(MstConst.CUSTOMERS));
    if (customerData.getMsgCd() != null) {
      lstStoreMaster = new ArrayList<MstSNohin>();
      MstSNohin mstSNohin = new MstSNohin();
      mstSNohin.setNhscd("noData");
      lstStoreMaster.add(mstSNohin);
      return lstStoreMaster;
    }
    try {
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("businessDate", strBusinessDate);
      params.put("officeCodeWK", officeCodeWk);
      params.put("chainCode", customerData.getCst().getCainCode());
      params.put("chainBranch", customerData.getCst().getCainIdx());
      lstStoreMaster = mst0103d02Dao.getMst0103d02Mapper().getDataAccess(
          params);
      if (lstStoreMaster != null && lstStoreMaster.size() > 0) {
        return lstStoreMaster;
      } else {
        lstStoreMaster = new ArrayList<MstSNohin>();
        MstSNohin mstSNohin = new MstSNohin();
        mstSNohin.setNhscd("noData");
        lstStoreMaster.add(mstSNohin);
      }
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }
    return lstStoreMaster;
  }

  /**
   * 店舗マスタ登録画面のコース情報一覧を表示する。
   * 
   * @param formMst0103d02 form
   * @param model model
   */
  public void getCourseInformation(FormMst0103d02 formMst0103d02, Model model) {
    String customerNumberWk = "";
    customerNumberWk = formMst0103d02.getTxtCustomerCode();
    String storeCodeWk = "";
    storeCodeWk = formMst0103d02.getTxtStoreCode();
    String plantCodeWk = "";
    String systemAdminFlg = formMst0103d02.getSysAdminFlag();
    if (systemAdminFlg.equalsIgnoreCase(CommonConst. SYS_ADMIN_FLG_VALID)) {
      plantCodeWk = formMst0103d02.getLoginJigyouShoCode();
    } else if (systemAdminFlg.equalsIgnoreCase(
        CommonConst.SYS_ADMIN_FLG_INVALID)) {
      if (formMst0103d02.getMode().equals(MstConst.SHOUKAI_MODE)) {
        plantCodeWk = formMst0103d02.getLoginJigyouShoCode();
      } else {
        plantCodeWk = formMst0103d02.getDdlOffice();
      }
    }
    ArrayList<MstCourseInfoMst0103d02> lstCourseInformation;
    lstCourseInformation = new ArrayList<MstCourseInfoMst0103d02>();
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("customerNumberWK", customerNumberWk);
    params.put("storeCodeWK", storeCodeWk);
    params.put("plantCodeWK", plantCodeWk);
    lstCourseInformation = mst0103d02Dao.getMst0103d02Mapper()
        .getCourseInformationLst(params);
    model.addAttribute("lstCourseInformation", lstCourseInformation);
  }

  /**
   * 店舗マスタ登録画面のコース情報一覧を表示する。
   * 
   * @param formMst0103d02 form
   * @param model model
   */
  public void getCommonSpecificationsConfirmation(FormMst0103d02 formMst0103d02,
      Model model) {
    String customerNumberWk = formMst0103d02.getTxtCustomerCode();
    String storeCodeWk = formMst0103d02.getTxtStoreCode();
    ArrayList<MstSpecificationsConfirmationMst0103d02> lstCommonSpecificationsConfirmation;
    lstCommonSpecificationsConfirmation = new ArrayList<MstSpecificationsConfirmationMst0103d02>();
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("customerNumberWK", customerNumberWk);
    params.put("storeCodeWK", storeCodeWk);
    lstCommonSpecificationsConfirmation = mst0103d02Dao.getMst0103d02Mapper()
        .getCommonSpecificationsConfirmation(params);
    model.addAttribute("lstCommonSpecificationsConfirmation",
        lstCommonSpecificationsConfirmation);
  }

  /**
   * 画面構成
   * 
   * @param model mode
   * @param viewMode viewMode
   * @param formMst0103d02 form
   * @param storeMaster storeMaster
   */
  public void setView(Model model, String viewMode,
      FormMst0103d02 formMst0103d02,
      MstUserInfoMst0103d02 storeMaster) {
    model.addAttribute("modeView", viewMode);
    formMst0103d02.setOfficeSelect("");
    formMst0103d02.setDeliveryCenterSelect("");
    String systemAdminFlg = formMst0103d02.getSysAdminFlag();
    if (viewMode.equalsIgnoreCase(MstConst.SHINKI_MODE)) {
      if (systemAdminFlg.equalsIgnoreCase(CommonConst.SYS_ADMIN_FLG_INVALID)) {
        model.addAttribute("ddlOfficeName", formMst0103d02
            .getLoginJigyouShoCode() + ":" + formMst0103d02
                .getLoginJigyouShoName());
      }
      formMst0103d02.setTxtStatusCode(MstConst.REGISTRATION);
      model.addAttribute("ddlDeliveryCenterDisable", "true");
    } else {
      if (viewMode.equalsIgnoreCase(MstConst.SHOUKAI_MODE)) {
        if (systemAdminFlg.equalsIgnoreCase(
            CommonConst.SYS_ADMIN_FLG_INVALID)) {
          model.addAttribute("ddlOfficeName", formMst0103d02
              .getLoginJigyouShoCode() + ":" + formMst0103d02
                  .getLoginJigyouShoName());
        } else {
          formMst0103d02.setDdlOffice(storeMaster.getJigyoCode());
          formMst0103d02.setOfficeSelect(storeMaster.getJigyoCode());
        }
      } else if (viewMode.equalsIgnoreCase(MstConst.TEISEI_MODE)) {
        if (systemAdminFlg.equalsIgnoreCase(CommonConst.SYS_ADMIN_FLG_VALID)) {
          formMst0103d02.setDdlOffice(storeMaster.getJigyoCode());
          formMst0103d02.setOfficeSelect(storeMaster.getJigyoCode());
        }
      } else if (viewMode.equalsIgnoreCase(MstConst.TORIKESI_MODE)) {
        if (systemAdminFlg.equalsIgnoreCase(CommonConst.SYS_ADMIN_FLG_VALID)) {
          formMst0103d02.setDdlOffice(storeMaster.getJigyoCode());
          formMst0103d02.setOfficeSelect(storeMaster.getJigyoCode());
        }
      }
      formMst0103d02.setTxtCustomerCode(storeMaster.getCustCode());
      model.addAttribute("customerName", storeMaster.getCustNmR());
      formMst0103d02.setTxtStoreCode(storeMaster.getShopCode());
      formMst0103d02.setTxtStoreName(storeMaster.getShopNm());
      formMst0103d02.setTxtStoreNameKana(storeMaster.getShopNmKana());
      formMst0103d02.setTxtStoreAbbreviation(storeMaster
          .getShopNmR());
      String[] arrZipCode = null;
      String strZipCode = storeMaster.getZipCode();      
      if (strZipCode != null && !strZipCode.equalsIgnoreCase("")) {
        arrZipCode = strZipCode.split("-", 2);
        formMst0103d02.setTxtPostalCodeResult(strZipCode.replaceAll("-", ""));
      }
      if (arrZipCode != null) {
        formMst0103d02.setTxtPostalCode1(arrZipCode[0]);
        formMst0103d02.setTxtPostalCode2(arrZipCode[1]);
      }
      formMst0103d02.setTxtAddress1(storeMaster.getAdr1());
      formMst0103d02.setTxtAddress2(storeMaster.getAdr2());
      String[] arrPhoneNumber = null;
      String strPhoneNumber = storeMaster.getTelNo();      
      if (strPhoneNumber != null && !strPhoneNumber.equalsIgnoreCase("")) {
        arrPhoneNumber = strPhoneNumber.split("-", 3);
        formMst0103d02.setTxtPhoneNumberResult(strPhoneNumber.replaceAll("-",
            ""));
      }
      if (arrPhoneNumber != null) {
        formMst0103d02.setTxtPhoneNumber1(arrPhoneNumber[0]);
        formMst0103d02.setTxtPhoneNumber2(arrPhoneNumber[1]);
        formMst0103d02.setTxtPhoneNumber3(arrPhoneNumber[2]);
      }
      String[] arrFaxNumber = null;
      String strFaxNumber = storeMaster.getFaxNo();      
      if (strFaxNumber != null && !strFaxNumber.equalsIgnoreCase("")) {
        arrFaxNumber = strFaxNumber.split("-", 3);
        formMst0103d02.setTxtFaxNumberResult(strFaxNumber.replaceAll("-", ""));
      }
      if (arrFaxNumber != null) {
        formMst0103d02.setTxtFaxNumber1(arrFaxNumber[0]);
        formMst0103d02.setTxtFaxNumber2(arrFaxNumber[1]);
        formMst0103d02.setTxtFaxNumber3(arrFaxNumber[2]);
      }
      formMst0103d02.setDdlDeliveryCenter(storeMaster
          .getDeliCenterCode());
      formMst0103d02.setDeliveryCenterSelect(storeMaster
          .getDeliCenterCode());
      formMst0103d02.setDdlThanksClassification(storeMaster
          .getSunksKb());
      formMst0103d02.setThanksClassificationSelect(storeMaster
          .getSunksKb());
      formMst0103d02.setTxtFixMisejika(storeMaster.getstCodeSts());
      formMst0103d02.setTxtFixCenter(storeMaster.getstCodeStc());
      formMst0103d02.setTxtSaleMisejika(storeMaster.getstCodeSps());
      formMst0103d02.setTxtSaleCenter(storeMaster.getstCodeSpc());
      if (storeMaster.getSumShopKb().equals(
          MstConst.COLLECTION_DESTINATION_STORE)) {
        formMst0103d02.setChkDestinationStore(true);
        formMst0103d02.setChkDestinationStoreDis("true");
      } else if (storeMaster.getSumShopKb().equals(MstConst.NORMAL_STORE)) {
        formMst0103d02.setChkDestinationStore(false);
        formMst0103d02.setChkDestinationStoreDis("false");
      }
      formMst0103d02.setDdlCondition(storeMaster.getSumShopJkn());
      formMst0103d02.setConditionSelect(storeMaster.getSumShopJkn());
      formMst0103d02.setTxtCollectionDestination(storeMaster.getSumShopCode());
      model.addAttribute("collectionDestination", storeMaster.getShopNmSus());
      String closeDate = storeMaster.getCloseDate().trim();
      if ( closeDate != null && !closeDate.equals("")) {
        closeDate = closeDate.substring(0, 4) + "/" + closeDate.substring(4, 6)
            + "/" + closeDate.substring(6);
        formMst0103d02.setTxtDate(closeDate);
      }
      if (viewMode.equalsIgnoreCase(MstConst.TORIKESI_MODE)) {
        formMst0103d02.setTxtStatusCode(MstConst.CANCELLATION);
      } else {
        formMst0103d02.setTxtStatusCode(storeMaster.getstsCode());
      }
      getCourseInformation(formMst0103d02, model);
      getCommonSpecificationsConfirmation(formMst0103d02, model);
    }
  }
  
  /**
   * convert data to Json
   * 
   * @param mstStoreInfoMst0103d01Lst list convert
   * @return Json
   * @throws JsonProcessingException エラー画面
   */
  public String convertJson(
      ArrayList<MstStoreInfoMst0103d01> mstStoreInfoMst0103d01Lst)
          throws JsonProcessingException {
    String jsonData = "";
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    jsonData = ow.writeValueAsString(mstStoreInfoMst0103d01Lst);
    return jsonData;
  }
 
  /**
   * 共通チェック.
   * 
   * @param formMst0101d02:フォーム
   * @param model:モデル
   * @return エラー true 普通:false
   */
  public boolean checkInput(FormMst0103d02 formMst0103d02, Model model) {
    String strError;
    String strErrorId = "";
    ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    ErrorMessages errMess = new ErrorMessages();
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();
    // 必須チェックは画面項目定義に則り実施する。入力不備を検知した場合は、次の通り処理を行う。
    // 得意先コード
    strError = checkItem(formMst0103d02.getTxtCustomerCode(), true,
        InputCheckCom.TYPE_NUM, 7);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("得意先コード");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtCustomerCode" + MstConst.DELIMITER_ERROR;
    }
    // 店舗コード
    strError = checkItem(formMst0103d02.getTxtStoreCode(), true,
        InputCheckCom.TYPE_NUM_ALPHA, 8);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("店舗コード");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtStoreCode" + MstConst.DELIMITER_ERROR;
    }
    // 店舗名称
    strError = checkItem(formMst0103d02.getTxtStoreName(), true,
        InputCheckCom.TYPE_EM, 20);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("店舗名称");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtStoreName" + MstConst.DELIMITER_ERROR;
    }
    // 店舗名称カナ
    strError = checkItem(formMst0103d02.getTxtStoreNameKana(), false,
        InputCheckCom.TYPE_EM, 20);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("店舗名称カナ");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtStoreNameKana" + MstConst.DELIMITER_ERROR;
    }
    // 店舗略称
    strError = checkItem(formMst0103d02.getTxtStoreAbbreviation(), true,
        InputCheckCom.TYPE_EM, 10);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("店舗略称");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtStoreAbbreviation" + MstConst.DELIMITER_ERROR;
    }
    // 郵便番号
    strError = checkItem(formMst0103d02.getTxtPostalCodeResult(), true,
        InputCheckCom.TYPE_NUM, 9);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("郵便番号");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtPostalCode1" + MstConst.DELIMITER_ERROR
          + "txtPostalCode2" + MstConst.DELIMITER_ERROR;
    }
    // 住所１
    strError = checkItem(formMst0103d02.getTxtAddress1(), true,
        InputCheckCom.TYPE_EM, 20);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("住所１");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtAddress1" + MstConst.DELIMITER_ERROR;
    }
    // 住所2
    strError = checkItem(formMst0103d02.getTxtAddress2(), false,
        InputCheckCom.TYPE_EM, 20);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("住所2");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtAddress2" + MstConst.DELIMITER_ERROR;
    }
    // 電話番号
    strError = checkItem(formMst0103d02.getTxtPhoneNumberResult(), false,
        InputCheckCom.TYPE_NUM, 18);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("電話番号");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtPhoneNumber1" + MstConst.DELIMITER_ERROR
          + "txtPhoneNumber2" + MstConst.DELIMITER_ERROR + "txtPhoneNumber3"
          + MstConst.DELIMITER_ERROR;
    }
    // FAX番号
    strError = checkItem(formMst0103d02.getTxtFaxNumberResult(), false,
        InputCheckCom.TYPE_NUM, 18);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("FAX番号");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtFaxNumber1" + MstConst.DELIMITER_ERROR
          + "txtFaxNumber2" + MstConst.DELIMITER_ERROR + "txtFaxNumber3"
          + MstConst.DELIMITER_ERROR;
    }
    // 事業所
    if (formMst0103d02.getOfficeSelect().equalsIgnoreCase("")) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("事業所");
      errMess.setMessageContent(readPropertiesFileService.getMessage("COM006-E",
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlOffice" + MstConst.DELIMITER_ERROR;
    }
    // 納品センター
    if (formMst0103d02.getDeliveryCenterSelect().equalsIgnoreCase("")) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("納品センター");
      errMess.setMessageContent(readPropertiesFileService.getMessage("COM006-E",
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlDeliveryCenter" + MstConst.DELIMITER_ERROR;
    }
    // 社店コード
    strError = checkItem(formMst0103d02.getTxtFixMisejika(), false,
        InputCheckCom.TYPE_NUM, 6);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("社店コード");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtFixMisejika" + MstConst.DELIMITER_ERROR;
    }
    // 社店コード
    strError = checkItem(formMst0103d02.getTxtFixCenter(), false,
        InputCheckCom.TYPE_NUM, 6);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("社店コード");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtFixCenter" + MstConst.DELIMITER_ERROR;
    }
    // 社店コード
    strError = checkItem(formMst0103d02.getTxtSaleMisejika(), false,
        InputCheckCom.TYPE_NUM, 6);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("社店コード");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtSaleMisejika" + MstConst.DELIMITER_ERROR;
    }
    // 社店コード
    strError = checkItem(formMst0103d02.getTxtSaleCenter(), false,
        InputCheckCom.TYPE_NUM, 6);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("社店コード");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtSaleCenter" + MstConst.DELIMITER_ERROR;
    }
    // 店舗集約条件
    if (formMst0103d02.getChkDestinationStore()) {
      if (formMst0103d02.getConditionSelect().equalsIgnoreCase("")) {
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("店舗集約条件");
        errMess.setMessageContent(readPropertiesFileService.getMessage("COM006-E",
            paramMess));
        lstErrMess.add(errMess);
        strErrorId += "ddlCondition" + MstConst.DELIMITER_ERROR;
      }
    }
    // 集約先店舗コード
    strError = checkItem(formMst0103d02.getTxtCollectionDestination(), false,
        InputCheckCom.TYPE_NUM_ALPHA, 8);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("集約先店舗コード");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtCollectionDestination" + MstConst.DELIMITER_ERROR;
    }
    // 取引中止日
    strError = InputCheckCom.chkDate(formMst0103d02.getTxtDate()
        .replaceAll("/", ""), CommonConst.DATE_FORMAT_YMD);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("取引中止日");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtDate" + MstConst.DELIMITER_ERROR;
    }
    // 状況コード
    strError = checkItem(formMst0103d02.getTxtStatusCode(), true,
        InputCheckCom.TYPE_NUM, 1);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("状況コード");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtStatusCode" + MstConst.DELIMITER_ERROR;
    }
    if (formMst0103d02.getTxtStoreCode().equals(formMst0103d02.getTenCodeNone())) {      
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("店舗コード");
      errMess.setMessageContent(readPropertiesFileService.getMessage(
          "COM030-E", paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtStoreCode";
    }
    String statusCode = formMst0103d02.getTxtStatusCode();
    if (!statusCode.equals("") && !statusCode.equals(CommonConst.STS_CD_ENTRY)
        && !statusCode.equals(CommonConst.STS_CD_INVALID)) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("状況コードは１または９で入力してください");
      errMess.setMessageContent(readPropertiesFileService.getMessage("MST013-E",
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtStatusCode" + MstConst.DELIMITER_ERROR;
    }
    if (strErrorId.equalsIgnoreCase("")) {
      return false;
    } else {
      model.addAttribute("errorMessages", lstErrMess);
      model.addAttribute("lstErrorID", strErrorId);
      return true;
    }
  }
  
  /**
   * 請求書伝票種別取得
   * 
   * @param customerInfo 得意先情報
   * @return 伝票種別
   */
  public String getInvoiceDocumentsType(MstCustomer customerInfo) {
    String chohyoId;
    String denpyoType;
    denpyoType = null;
    chohyoId = "";
    CustomerData customerData = null;
    MstCustomer billingInfoWk;
    // 得意先が属する請求先の請求書種類帳票IDを取得する。
    if (customerInfo.getBildFlg().equals(MstConst.BILDFLG)) {
      chohyoId = customerInfo.getBildTypId();
    } else {
      CommonGetData commonGetData = new CommonGetData(commonDao,
          readPropertiesFileService);
      customerData = commonGetData.getCustomerData(customerInfo.getBildCode(),
          null, Integer.parseInt(MstConst.BILLING));
      if (customerData.getMsgCd() == null) {
        billingInfoWk = customerData.getCst();
        chohyoId = billingInfoWk.getBildTypId();
      } else {
        return denpyoType;
      }
    }
    // 帳票IDから請求書伝票種別を取得する。
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("chohyoID", chohyoId);
    MstListForm mstListForm = mst0103d02Dao.getMst0103d02Mapper()
        .getMstListForm(params);
    if (mstListForm != null) {
      denpyoType = mstListForm.getDenCls();
    } else {
      denpyoType = null;
    }
    return denpyoType;
  }
 
  /**
   * 店舗マスタ存在チェック
   * 
   * @param custCode [画面]得意先コード
   * @param shopCode [画面]店舗コード
   * @return エラー true 普通:false
   */
  public boolean checkShopExist(String custCode, String shopCode) {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("custCode", custCode);
    params.put("shopCode", shopCode);
    MstShop mstShop = mst0103d02Dao.getMst0103d02Mapper().getMstShop(params);
    if (mstShop == null) {
      return false;
    }
    return true;
  }
  
  /**
   * 伝票採番マスタ存在チェック
   * 
   * @param custCode [画面]得意先コード
   * @param shopCode [画面]店舗コード
   * @return エラー true 普通:false
   */
  public boolean checkDatIdxExist(String custCode, String shopCode) {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("custCode", custCode);
    params.put("shopCode", shopCode);
    MstDatIdx mstDatIdx = mst0103d02Dao.getMst0103d02Mapper().getMstDatIdx(
        params);
    if (mstDatIdx == null) {
      return false;
    }
    return true;
  }
  
  /**
   * 伝票採番マスタ新規登録
   * 
   * @param formMst0103d02 form
   * @param loginUserId ユーザID
   * @throws Exception エラー画面
   */
  public void insertMstDatIdx(FormMst0103d02 formMst0103d02, String loginUserId)
      throws Exception {
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    txManager = (PlatformTransactionManager) appContext.getBean("txManager");
    TransactionStatus status = null;
    MstDatIdx mstDatIdx = new MstDatIdx();
    mstDatIdx.setCustCode(formMst0103d02.getTxtCustomerCode());
    mstDatIdx.setShopCode(formMst0103d02.getTxtStoreCode());
    mstDatIdx.setMaxIdx(MstConst.MAX_IDX);
    mstDatIdx.setMinIdx(MstConst.MIN_IDX);
    mstDatIdx.setValidDigit(MstConst.VALID_DIGIT);
    mstDatIdx.setZeroSuppress(MstConst.TARGET);
    mstDatIdx.setDatIdx(MstConst.DAT_IDX);
    mstDatIdx.setStsCode(MstConst.REGISTRATION);
    mstDatIdx = this.setCommonMstDatIdx(mstDatIdx, loginUserId, "MST01-03D02",
        true);
    try {
      status = txManager.getTransaction(def);
      mst0103d02Dao.getMstDatIdxMapper().insert(mstDatIdx);
      txManager.commit(status);
    } catch (Exception ex) {
      txManager.rollback(status);
      logger.error(ex.getMessage());
      throw ex;
    }
  }
  
  /**
   * 伝票採番マスタ新規登録
   * 
   * @param formMst0103d02 form
   * @param loginUserId ユーザID
   * @throws Exception エラー画面
   */
  public void updateMstDatIdx(FormMst0103d02 formMst0103d02, String loginUserId)
      throws Exception {
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    txManager = (PlatformTransactionManager) appContext.getBean("txManager");
    TransactionStatus status = null;
    MstDatIdx mstDatIdx = new MstDatIdx();
    mstDatIdx.setCustCode(formMst0103d02.getTxtCustomerCode());
    mstDatIdx.setShopCode(formMst0103d02.getTxtStoreCode());
    mstDatIdx.setMaxIdx(MstConst.MAX_IDX);
    mstDatIdx.setMinIdx(MstConst.MIN_IDX);
    mstDatIdx.setValidDigit(MstConst.VALID_DIGIT);
    mstDatIdx.setZeroSuppress(MstConst.TARGET);
    mstDatIdx.setDatIdx(MstConst.DAT_IDX);
    mstDatIdx.setStsCode(MstConst.REGISTRATION);
    mstDatIdx = this.setCommonMstDatIdx(mstDatIdx, loginUserId, "MST01-03D02",
        false);
    try {
      status = txManager.getTransaction(def);
      mst0103d02Dao.getMstDatIdxMapper().updateByPrimaryKeySelective(mstDatIdx);
      txManager.commit(status);
    } catch (Exception ex) {
      txManager.rollback(status);
      logger.error(ex.getMessage());
      throw ex;
    }
  }
  
  /**
   * 店舗マスタ新規登録
   * 
   * @param formMst0103d02 フォーム
   * @param loginUserId ログインユーザコード
   * @throws Exception エラー画面
   */
  public void insertMstShop(FormMst0103d02 formMst0103d02, String loginUserId)
      throws Exception {
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    txManager = (PlatformTransactionManager) appContext.getBean("txManager");
    TransactionStatus status = null;
    MstShop mstShop = new MstShop();
    mstShop.setCustCode(formMst0103d02.getTxtCustomerCode());
    mstShop.setShopCode(formMst0103d02.getTxtStoreCode());
    mstShop.setShopNm(formMst0103d02.getTxtStoreName());
    mstShop.setShopNmKana(formMst0103d02.getTxtStoreNameKana());
    mstShop.setShopNmR(formMst0103d02.getTxtStoreAbbreviation());
    String txtPostalCode = formMst0103d02.getTxtPostalCode1() + "-"
        + formMst0103d02.getTxtPostalCode2();
    mstShop.setZipCode(txtPostalCode);
    mstShop.setAdr1(formMst0103d02.getTxtAddress1());
    mstShop.setAdr2(formMst0103d02.getTxtAddress2());
    
    String txtTelNo = formMst0103d02.getTxtPhoneNumber1() + "-" + formMst0103d02
        .getTxtPhoneNumber2() + "-" + formMst0103d02.getTxtPhoneNumber3();
    if (txtTelNo.equals("--")) {
      txtTelNo = "";
    }
    mstShop.setTelNo(txtTelNo);
    String txtFaxNo = formMst0103d02.getTxtFaxNumber1() + "-" + formMst0103d02
        .getTxtFaxNumber2() + "-" + formMst0103d02.getTxtFaxNumber3();
    if (txtFaxNo.equals("--")) {
      txtFaxNo = "";
    }
    mstShop.setFaxNo(txtFaxNo);
    mstShop.setJigyoCode(formMst0103d02.getOfficeSelect());
    boolean chkDestinationStore = formMst0103d02.getChkDestinationStore();
    if (chkDestinationStore) {
      mstShop.setSumShopKb(MstConst.CHECK_ON);
    } else {
      mstShop.setSumShopKb(MstConst.CHECK_OFF);
    }
    mstShop.setSumShopJkn(formMst0103d02.getConditionSelect());
    mstShop.setDeliCenterCode(formMst0103d02.getDeliveryCenterSelect());
    mstShop.setSunksKb(formMst0103d02.getThanksClassificationSelect());
    mstShop.setSumShopCode(formMst0103d02.getTxtCollectionDestination());
    mstShop.setStCodeStS(formMst0103d02.getTxtFixMisejika());
    mstShop.setStCodeStC(formMst0103d02.getTxtFixCenter());
    mstShop.setStCodeSpS(formMst0103d02.getTxtSaleMisejika());
    mstShop.setStCodeSpC(formMst0103d02.getTxtSaleCenter());
    mstShop.setCloseDate(formMst0103d02.getTxtDate().replaceAll("/", ""));
    mstShop.setStsCode(formMst0103d02.getTxtStatusCode());
    mstShop.setSndFlg(MstConst.UNSENT);
    // 共通項目の設定
    mstShop = this.setCommonMstShop(mstShop, loginUserId, "MST01-03D02", true);
    try {
      status = txManager.getTransaction(def);
      mst0103d02Dao.getMstShopMapper().insert(mstShop);
      txManager.commit(status);
    } catch (Exception ex) {
      txManager.rollback(status);
      logger.error(ex.getMessage());
      throw ex;
    }
  }
  
  /**
   * 店舗マスタ更新
   * 
   * @param formMst0103d02 フォーム
   * @param loginUserId ログインユーザコード
   * @throws Exception エラー画面
   */
  public void updateMstShop(FormMst0103d02 formMst0103d02, String loginUserId)
      throws Exception {
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    txManager = (PlatformTransactionManager) appContext.getBean("txManager");
    TransactionStatus status = null;
    MstShop mstShop = new MstShop();
    mstShop.setCustCode(formMst0103d02.getTxtCustomerCode());
    mstShop.setShopCode(formMst0103d02.getTxtStoreCode());
    mstShop.setShopNm(formMst0103d02.getTxtStoreName());
    mstShop.setShopNmKana(formMst0103d02.getTxtStoreNameKana());
    mstShop.setShopNmR(formMst0103d02.getTxtStoreAbbreviation());
    String txtPostalCode = formMst0103d02.getTxtPostalCode1() + "-"
        + formMst0103d02.getTxtPostalCode2();
    mstShop.setZipCode(txtPostalCode);
    mstShop.setAdr1(formMst0103d02.getTxtAddress1());
    mstShop.setAdr2(formMst0103d02.getTxtAddress2());
    String txtTelNo = formMst0103d02.getTxtPhoneNumber1() + "-" + formMst0103d02
        .getTxtPhoneNumber2() + "-" + formMst0103d02.getTxtPhoneNumber3();
    if (txtTelNo.equals("--")) {
      txtTelNo = "";
    }
    mstShop.setTelNo(txtTelNo);
    String txtFaxNo = formMst0103d02.getTxtFaxNumber1() + "-" + formMst0103d02
        .getTxtFaxNumber2() + "-" + formMst0103d02.getTxtFaxNumber3();
    if (txtFaxNo.equals("--")) {
      txtFaxNo = "";
    }
    mstShop.setFaxNo(txtFaxNo);
    mstShop.setJigyoCode(formMst0103d02.getOfficeSelect());
    boolean chkDestinationStore = formMst0103d02.getChkDestinationStore();
    if (chkDestinationStore) {
      mstShop.setSumShopKb(MstConst.CHECK_ON);
    } else {
      mstShop.setSumShopKb(MstConst.CHECK_OFF);
    }
    mstShop.setSumShopJkn(formMst0103d02.getConditionSelect());
    mstShop.setDeliCenterCode(formMst0103d02.getDeliveryCenterSelect());
    mstShop.setSunksKb(formMst0103d02.getThanksClassificationSelect());
    mstShop.setSumShopCode(formMst0103d02.getTxtCollectionDestination());
    mstShop.setStCodeStS(formMst0103d02.getTxtFixMisejika());
    mstShop.setStCodeStC(formMst0103d02.getTxtFixCenter());
    mstShop.setStCodeSpS(formMst0103d02.getTxtSaleMisejika());
    mstShop.setStCodeSpC(formMst0103d02.getTxtSaleCenter());
    mstShop.setCloseDate(formMst0103d02.getTxtDate().replaceAll("/", ""));
    mstShop.setStsCode(formMst0103d02.getTxtStatusCode());
    mstShop.setSndFlg(MstConst.UNSENT);
    // 共通項目の設定
    mstShop = this.setCommonMstShop(mstShop, loginUserId, "MST01-03D02", false);
    try {
      status = txManager.getTransaction(def);
      mst0103d02Dao.getMstShopMapper().updateByPrimaryKeySelective(
          mstShop);
      txManager.commit(status);
    } catch (Exception ex) {
      txManager.rollback(status);
      logger.error(ex.getMessage());
      throw ex;
    }
  }
  
  /**
   * 店舗マスタ取消
   * 
   * @param formMst0103d02 フォーム
   * @param loginUserId ログインユーザコード
   * @throws Exception エラー画面
   */
  public void deleteMstShop(FormMst0103d02 formMst0103d02, String loginUserId)
      throws Exception {
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    txManager = (PlatformTransactionManager) appContext.getBean("txManager");
    TransactionStatus status = null;
    MstShop mstShop = new MstShop();
    mstShop.setCustCode(formMst0103d02.getTxtCustomerCode());
    mstShop.setShopCode(formMst0103d02.getTxtStoreCode());
    mstShop.setStsCode(formMst0103d02.getTxtStatusCode());
    mstShop.setSndFlg(MstConst.UNSENT);
    // 共通項目の設定
    mstShop = this.setCommonMstShop(mstShop, loginUserId, "MST01-03D02", false);
    try {
      status = txManager.getTransaction(def);
      mst0103d02Dao.getMstShopMapper().updateByPrimaryKeySelective(mstShop);
      txManager.commit(status);
    } catch (Exception ex) {
      txManager.rollback(status);
      logger.error(ex.getMessage());
      throw ex;
    }
  }
 
  /**
   * 共通項目の設定.
   * 
   * @param mstShop:担当者データ
   * @param strUserId:ユーザID
   * @param strProgId:プログラムID
   * @param isNew:新規／更新
   * @return 担当者データ
   */
  public MstDatIdx setCommonMstDatIdx(MstDatIdx mstDatIdx, String strUserId,
      String strProgId,
      boolean isNew) {
    String currentDate = DateUtil.getSysDate();
    String currentTime = DateUtil.getSysTime();
    if (isNew) {
      mstDatIdx.setInsUserid(strUserId);
      mstDatIdx.setInsPgid(strProgId);
      mstDatIdx.setInsYmd(currentDate);
      mstDatIdx.setInsTime(currentTime);
    }
    mstDatIdx.setUpdUserid(strUserId);
    mstDatIdx.setUpdPgid(strProgId);
    mstDatIdx.setUpdYmd(currentDate);
    mstDatIdx.setUpdTime(currentTime);
    return mstDatIdx;
  }
  
  /**
   * 共通項目の設定.
   * 
   * @param mstShop:担当者データ
   * @param strUserId:ユーザID
   * @param strProgId:プログラムID
   * @param isNew:新規／更新
   * @return 担当者データ
   */
  public MstShop setCommonMstShop(MstShop mstShop, String strUserId,
      String strProgId,
      boolean isNew) {
    String currentDate = DateUtil.getSysDate();
    String currentTime = DateUtil.getSysTime();
    if (isNew) {
      mstShop.setInsUserid(strUserId);
      mstShop.setInsPgid(strProgId);
      mstShop.setInsYmd(currentDate);
      mstShop.setInsTime(currentTime);
    }
    mstShop.setUpdUserid(strUserId);
    mstShop.setUpdPgid(strProgId);
    mstShop.setUpdYmd(currentDate);
    mstShop.setUpdTime(currentTime);

    return mstShop;
  }
 
  /**
   * 入力チェック処理.
   * 
   * @param val:チェックデータ
   * @param emptyFlg:必須チェックフラグ
   * @param type:型
   * @param len:桁数
   * @return null: エラー
   */
  public String checkItem(String val, boolean emptyFlg, int type, int len) {
    String error = null;
    if (emptyFlg) {
      error = InputCheckCom.chkEmpty(val);
      if (error != null) {
        return error;
      }
    }

    error = InputCheckCom.chkType(val, type);
    if (error != null) {
      return error;
    }
    error = InputCheckCom.chkLength(val, len);
    if (error != null) {
      return error;
    }

    return error;
  }
  
  /**
   * 排他チェック.
   * 
   * @param strUserCode:担当者コード
   * @param strHaitaDate:排他日付
   * @param strHaitaTime:排他時間
   * @return boolean true: エラー false: 普通
   */
  public boolean checkHaita(String custcode, String shopCode,
      String strHaitaDate,
      String strHaitaTime) {

    // User情報取得
    MstShop mstShop = new MstShop();
    mstShop = this.getMstShopInfo(custcode, shopCode);
    // 共通項目.修正年月日 ＋ 共通項目．修正時刻 > 表示時システム日付 の場合
    int intDateRegDb = Integer.parseInt(mstShop.getUpdYmd());
    int intDateHidden = Integer.parseInt(strHaitaDate);

    if (intDateRegDb > intDateHidden) {
      return true;
    } else if (intDateRegDb == intDateHidden) {
      intDateRegDb = Integer.parseInt(mstShop.getUpdTime());
      intDateHidden = Integer.parseInt(strHaitaTime);
      if (intDateRegDb > intDateHidden) {
        return true;
      }
    }
    return false;
  }
 
  /**
   * 担当者コードと担当者情報を取得する.
   * 
   * @param strUserCode:担当者コード
   * @return MstUser
   */
  public MstShop getMstShopInfo(String custCode, String shopCode) {
    MstShop mstShop = null;
    try {
      MstShopKey mstShopKey = new MstShopKey();
      mstShopKey.setCustCode(custCode);
      mstShopKey.setShopCode(shopCode);
      mstShop = mst0103d02Dao.getMstShopMapper().selectByPrimaryKey(mstShopKey);
      return mstShop;
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
  }
}
