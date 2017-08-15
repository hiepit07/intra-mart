/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl
 * ファイル名:Mst0108d01Service.java
 * 
 * <p>作成者: グエン リユオン ギア
 * 作成日:2015/11/01
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/11/01 1.00 ABV) グエン リユオン ギア 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.core.JsonProcessingException;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.component.CustomerData;
import jp.co.daitoku_sh.dfcm.common.component.MstGeneralData;
import jp.co.daitoku_sh.dfcm.common.component.ShopData;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetData;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ObjCombobox;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0108d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.Mst0108d01Model;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstCorrectKbMst0108d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstSJigyoInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl.Mst0108d01Dao;

/**
 * コントローラクラス
 * 
 * @author  グエン　リユオン　ギア
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class Mst0108d01Service extends AbsService {

  @Autowired
  @Qualifier("mst0108d01Dao")
  private Mst0108d01Dao mst0108d01Dao;

  @Autowired
  @Qualifier("commonDao")
  private CommonDao commonDao;
  // メッセージPropertiesファイルを読む
  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  /** ログ出力用. */
  private Dfcmlogger logger = new Dfcmlogger();

  public Mst0108d01Dao getMst0108d01Dao() {
    return mst0108d01Dao;
  }

  public void setMst0108d01Dao(Mst0108d01Dao mst0108d01Dao) {
    this.mst0108d01Dao = mst0108d01Dao;
  }

  public CommonDao getCommonDao() {
    return commonDao;
  }

  public void setCommonDao(CommonDao commonDao) {
    this.commonDao = commonDao;
  }

  /**
   * Defaultメッセージの取得.
   * 
   * @param model
   *          ： フォームのModel
   */
  public void getDefaultMess(Model model) {
    // メッセージのParam
    // COM015-E エラー
    ArrayList<String> paramMess = new ArrayList<String>();

    final ArrayList<DefaultMessages> defaultMsgLst = new ArrayList<DefaultMessages>();
    paramMess = new ArrayList<String>();
    paramMess.add("業務日付の取得");
    // COM015-E
    DefaultMessages defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM015-E");
    defaultMsg.setMessageTitle("確認メッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM015-E", paramMess));
    defaultMsgLst.add(defaultMsg);
    model.addAttribute("defaultMessages", defaultMsgLst);
    // COM001-I
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-I");
    defaultMsg.setMessageTitle("確認メッセージ");
    paramMess = new ArrayList<String>();
    paramMess.add("CSVファイルを出力");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-I", paramMess));
    defaultMsgLst.add(defaultMsg);
    //
    paramMess = new ArrayList<String>();
    paramMess.add("汎用マスタ");
    paramMess.add("販売区分");
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM009-E");
    defaultMsg.setMessageTitle("確認メッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM009-E", paramMess));
    defaultMsgLst.add(defaultMsg);

    // COM005-W
    
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM005-W");
    defaultMsg.setMessageTitle("確認メッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM005-W", paramMess));
    defaultMsgLst.add(defaultMsg);

    paramMess = new ArrayList<String>();
    paramMess.add("適用期間（終了）");
    paramMess.add("適用期間（開始)");
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM016-E");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM016-E", paramMess));
    defaultMsgLst.add(defaultMsg);
    // COM025-E
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM025-E");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM025-E", null));
    defaultMsgLst.add(defaultMsg);
    //COM001-E
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-E");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-E", null));
    defaultMsgLst.add(defaultMsg);
    
    
    model.addAttribute("defaultMessages", defaultMsgLst);
  }

  /**
   * getCom009EMess.
   * 
   * @param model model
   */
  public void getCom009EMess(Model model) {
    // COM015-E エラー
    ArrayList<String> paramMessCom009E = new ArrayList<String>();

    final ArrayList<DefaultMessages> defaultMsgLstCom009E = new ArrayList<DefaultMessages>();
    paramMessCom009E = new ArrayList<String>();
    paramMessCom009E.add("汎用マスタ");
    paramMessCom009E.add("販売区分");
    DefaultMessages defaultMsgCom009E = new DefaultMessages();
    defaultMsgCom009E.setMessageCode("COM009-E");
    defaultMsgCom009E.setMessageTitle("確認メッセージ");
    defaultMsgCom009E.setMessageContent(readPropertiesFileService.getMessage(
        "COM009-E", paramMessCom009E));
    defaultMsgLstCom009E.add(defaultMsgCom009E);
    model.addAttribute("defaultMessages", defaultMsgLstCom009E);
  }

  /**
   * 【オンライン得意先変換マスタ一覧画面初期表示処理】.
   * 
   * @param model model
   * @param formMst0108d01 formMst0108d01
   * @param systemAdministratorFlag systemAdministratorFlag
   */
  public void initAction(Model model, FormMst0108d01 formMst0108d01,
      String systemAdministratorFlag) {
    CommonGetSystemCom commonGetSystemCom = new CommonGetSystemCom(commonDao,
        null, null,
        readPropertiesFileService);
    String businessDate = commonGetSystemCom.getAplDate();
    // メッセージを表示し処理を終了する。 （共通仕様 4-(4)適用）
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();
    DefaultMessages defaultMsg = new DefaultMessages();
    // 業務日付 ≠ Nullの場合、画面隠し項目に業務日付をセットする。
    if (businessDate == null) {
      // [画面_Hidden]業務日付 = [変数]業務日付
      formMst0108d01.setBussinessDate(businessDate);
      String errorControll = MstConst.ERRORCONTROLL;
      formMst0108d01.setErrorControll(errorControll);
      defaultMsg = new DefaultMessages();
      defaultMsg.setMessageCode("COM015-E");
      defaultMsg.setMessageTitle("確認メッセージ");
      paramMess = new ArrayList<String>();
      paramMess.add("業務日付の取得");
      defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
          "COM015-E", paramMess));
      ArrayList<DefaultMessages> defaultMsgLst1 = new ArrayList<DefaultMessages>();
      defaultMsgLst1.add(defaultMsg);
      model.addAttribute("errorMessages", defaultMsgLst1);
    } else {
      // 業務日付取得
      formMst0108d01.setBussinessDate(businessDate);
      int flag = MstConst.ONE_FLAG;
      if (systemAdministratorFlag.equalsIgnoreCase(MstConst.SYS_ADMIN_FLG_INVALID)) {
        flag = MstConst.SIZE_ZERO;
        formMst0108d01.setFlag(flag);
      } else {
        formMst0108d01.setFlag(flag);
        Map<String, Object> params = new HashMap<String, Object>(); // Mapper
        // ログイン事業所コード値を取得する
        String loginJigyouShoCode = formMst0108d01.getJigyouShoCode();
        formMst0108d01.setJigyouShoCode(loginJigyouShoCode);
        try {
          ArrayList<ObjCombobox> lstMstSJigyoInfoReturn = new ArrayList<ObjCombobox>();
          params.put("businessDate", businessDate);
          List<MstSJigyoInfo> jigyos = mst0108d01Dao.getMst0108d01Mapper()
              .getSJigyoInfo(params);
          // 事業所マスタデータの取得に失敗、もしくは取得データ件数が０件の場合、画面にエラーメッセージを表示する。
          if (jigyos != null) {
            if (jigyos.size() == MstConst.SIZE_ZERO) {
              String errorControll = MstConst.ERRORCONTROLL;
              formMst0108d01.setErrorControll(errorControll);
              defaultMsg = new DefaultMessages();

              defaultMsg.setMessageCode("COM015-E");
              defaultMsg.setMessageTitle("確認メッセージ");
              paramMess = new ArrayList<String>();
              paramMess.add("事業所マスタの取得");
              defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
                  "COM015-E", paramMess));
              ArrayList<DefaultMessages> defaultMsgLst1 = new ArrayList<DefaultMessages>();
              defaultMsgLst1.add(defaultMsg);
              model.addAttribute("errorMessages", defaultMsgLst1);
            } else {
              ObjCombobox firstObject = new ObjCombobox();
              firstObject.setCode("");
              firstObject.setName("");
              lstMstSJigyoInfoReturn.add(firstObject);
              for (MstSJigyoInfo mstSJigyoInfo : jigyos) {
                ObjCombobox tempObj = new ObjCombobox();
                tempObj.setCode(mstSJigyoInfo.getJgycd());
                String code = mstSJigyoInfo.getJgycd();
                if (code.equals(loginJigyouShoCode)) {
                  tempObj.setName("<option value=" + mstSJigyoInfo.getJgycd()
                      + " selected='selected'>"
                      + mstSJigyoInfo.getJgycd() + " : " + mstSJigyoInfo
                          .getJgymei() + "</option>");
                } else {
                  tempObj.setName("<option value=" + mstSJigyoInfo.getJgycd()
                      + " >"
                      + mstSJigyoInfo.getJgycd() + " : " + mstSJigyoInfo
                          .getJgymei() + "</option>");
                }

                lstMstSJigyoInfoReturn.add(tempObj);
              }
              model.addAttribute("ShozokuClassList", lstMstSJigyoInfoReturn);
              boolean ddlSalesKb = setMasutaKubun_Dll(model);
              if (ddlSalesKb) {
                formMst0108d01.setErrorControll("");
              } else {
                String errorControll = MstConst.ERRORCONTROLL;
                formMst0108d01.setErrorControll(errorControll);
              }
            }

          }
        } catch (MyBatisSystemException e) {
          logger.error(e.getMessage());
          throw e;
        }
      }
    }
  }

  /**
   * setMasutaKubun_DLL.
   *  
   * @param model model
   * @return boolean
   */
  public boolean setMasutaKubun_Dll(Model model) {
    ArrayList<ObjCombobox> lstMstMasutaKubunInfoReturn = new ArrayList<ObjCombobox>();
    try {
      // 共通部品初期化
      CommonGetSystemCom systemCom = new CommonGetSystemCom(commonDao, null,
          null, readPropertiesFileService);
      // 得意先種別名称を取得する
      List<MstGeneralData> generalDataSaleType = systemCom.getMstGeneralConf(
          MstConst.Sale_Kb, null);
      if (generalDataSaleType != null && generalDataSaleType
          .size() > MstConst.SIZE_ZERO) {
        ObjCombobox firstObject = new ObjCombobox();
        firstObject.setCode("");
        firstObject.setName("");
        lstMstMasutaKubunInfoReturn.add(firstObject);
        for (MstGeneralData mstGeneralData : generalDataSaleType) {
          ObjCombobox tempObj = new ObjCombobox();
          tempObj.setCode(mstGeneralData.getGlCode());
            tempObj.setName("<option value=" + mstGeneralData.getGlCode()
                + ">" + mstGeneralData.getGlCode() + " : "
                + mstGeneralData
                    .getTarget1() + "</option>");
          lstMstMasutaKubunInfoReturn.add(tempObj);
        }
        model.addAttribute("MasutaKubunClassList", lstMstMasutaKubunInfoReturn);
        return true;
      } else {
        // COM015-E エラー
        ArrayList<String> paramMessCom009E = new ArrayList<String>();

        final ArrayList<DefaultMessages> defaultMsgLstCom009E = new ArrayList<DefaultMessages>();
        paramMessCom009E = new ArrayList<String>();
        paramMessCom009E.add("汎用マスタ");
        paramMessCom009E.add("販売区分");
        DefaultMessages defaultMsgCom009E = new DefaultMessages();
        defaultMsgCom009E.setMessageCode("COM009-E");
        defaultMsgCom009E.setMessageTitle("確認メッセージ");
        defaultMsgCom009E.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMessCom009E));
        defaultMsgLstCom009E.add(defaultMsgCom009E);
        
        model.addAttribute("errorMessages", defaultMsgLstCom009E);
        return false;
      }
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
  }

  /**
   * setForm セットフォーム.
   * 
   * @param formMst0108d01 formMst0108d01 エンティティ
   */
  public void setForm(FormMst0108d01 formMst0108d01) {

    if (formMst0108d01.getCstCode().equalsIgnoreCase("")) {
      formMst0108d01.setCstCode("");
    }
    if (formMst0108d01.getBunruiCode().equalsIgnoreCase("")) {
      formMst0108d01.setBunruiCode("");
    }
    
    if (formMst0108d01.getShopCode().equalsIgnoreCase("")) {
      formMst0108d01.setShopCode("");
    }
    
    if (formMst0108d01.getItemCode().equalsIgnoreCase("")) {
      formMst0108d01.setItemCode("");
    }
    
    if (formMst0108d01.getValidFrom().equalsIgnoreCase("")) {
      formMst0108d01.setValidFrom("");
    }
    
    if (formMst0108d01.getValidTo().equalsIgnoreCase("")) {
      formMst0108d01.setValidTo("");
    }
    
    if (formMst0108d01.getSalesKb().equalsIgnoreCase("")) {
      formMst0108d01.setSalesKb("");
    }
    
    if (formMst0108d01.getCheckType() == 0) {
      formMst0108d01.setCheckType(formMst0108d01.getCheckType());
    }
    
    if (formMst0108d01.isCheckCancleData() == true) {
      formMst0108d01.setCheckUpdYmd(false);
    }
    
  }

  /**
   * getSearchResult 検索結果を取得します.
   * 
   * @param model モデル
   * @param formMst0108d01 formMst0108d01 エンティティ
   * @return List リスト
   * @throws JsonProcessingException e
   */
  public FormMst0108d01 getSearchResult(HttpServletRequest request,
      Model model, FormMst0108d01 formMst0108d01)
          throws JsonProcessingException {
    String custCode = formMst0108d01.getCstCode();
    String shopCode = formMst0108d01.getShopCode();
    int checkType = formMst0108d01.getCheckType();
    formMst0108d01.setRelationMasterErrorFlag(MstConst.RELATIONERRORFLAG_0);
    /* String shopInfo = "";*/
    String shopNmR = "";
    Short cainCode = 0;
    Short cainIdx = 0;
    ArrayList<String> paramMess = null;
    String errMessage = "";
    CommonGetData commGetData = new CommonGetData(commonDao,
        readPropertiesFileService);
    // 共通部品を使って、得意先情報を取得する
    CustomerData data = new CustomerData();
    data = commGetData.getCustomerData(custCode, null, checkType);
    CommonGetSystemCom commonGetSystemCom = new CommonGetSystemCom(commonDao,
        null, null,
        readPropertiesFileService);
    String businessDate = commonGetSystemCom.getAplDate();

    String relationMasterErrorFlag = MstConst.RELATIONERRORFLAG_0;
    if (!custCode.equalsIgnoreCase("")) {
     
      // 結果判定
      // 変数[得意先情報格納クラス].メッセージコード ＝ Null の場合
      if (data.getMsgCd() == null || data.getMsgCd().equalsIgnoreCase("")) {
        /* custtomerInfo = data.getCst().getCustCls();*/
        cainCode = data.getCst().getCainCode();
        cainIdx = data.getCst().getCainIdx();
        formMst0108d01.setCstCode(custCode);
        formMst0108d01.setCustNmR(data.getCst().getCustNmR());
      } else {
        // 変数[得意先情報格納クラス].メッセージコード ≠ Null の場合
        // 画面にエラーメッセージを表示する。
        // COM009-E エラー
        
        paramMess = new ArrayList<String>();
        paramMess.add("得意先マスタ");
        paramMess.add("入力された得意先");
        errMessage += readPropertiesFileService.getMessage("COM009-E", paramMess)
            + "<br>";
        formMst0108d01.setErrMessage(errMessage);
        // set custNmR
        formMst0108d01.setCustNmR("");
        relationMasterErrorFlag = MstConst.RELATIONERRORFLAG_1;
        formMst0108d01.setRelationMasterErrorFlag(relationMasterErrorFlag);
      }
    }
   

    
    if ((!formMst0108d01.getCstCode().equalsIgnoreCase(""))
        && (!formMst0108d01.getItemCode().equalsIgnoreCase(""))) {
      String itemCode = formMst0108d01.getItemCode();
      try {
        Map<String, Object> parm = new HashMap<String, Object>(); // Mapper
        parm.put("businessDate", businessDate);
        parm.put("itemCode", itemCode);
        parm.put("custCode", cainCode);
        parm.put("cstNmr", cainIdx);
        List<Mst0108d01Model> lstItemName = mst0108d01Dao.getMst0108d01Mapper()
            .getItemName(parm);
        // jsonData = lstItemName.get(0).getHinRyaKu();
        if (lstItemName.size() > MstConst.SIZE_ZERO) {
          formMst0108d01.setHinRyaKu(lstItemName.get(0).getHinRyaKu());
        } else if (lstItemName.size() <= 0) {
          // COM009-E エラー
          paramMess = new ArrayList<String>();
          paramMess.add("製品マスタ");
          paramMess.add("入力された品目");
          errMessage += readPropertiesFileService.getMessage("COM009-E",
              paramMess) + "<br>";
          formMst0108d01.setErrMessage(errMessage);
          // set custNmR
          formMst0108d01.setHinRyaKu("");
          if (formMst0108d01
              .getRelationMasterErrorFlag().equalsIgnoreCase(MstConst.RELATIONERRORFLAG_0)) {
             relationMasterErrorFlag = MstConst.RELATIONERRORFLAG_2;
            formMst0108d01.setRelationMasterErrorFlag(relationMasterErrorFlag);
          }
        }
      } catch (Exception e) {
        e.getMessage();
      }
    }

    if (!formMst0108d01.getCstCode().equalsIgnoreCase("") && !formMst0108d01
        .getShopCode().equalsIgnoreCase("")) {
      ShopData shopData = commGetData.getShopData(shopCode, custCode);
      if (shopData.getMsgCd() == null || shopData.getMsgCd().equalsIgnoreCase(
          "")) {
        /*shopInfo = shopData.getShp().getSumShopCode();*/
        shopCode = shopData.getShp().getShopCode();
        shopNmR = shopData.getShp().getShopNmR();
        formMst0108d01.setShopNmR(shopNmR);
      } else {
        paramMess = new ArrayList<String>();
        paramMess.add("店舗マスタ");
        paramMess.add("入力された店舗");
        errMessage += readPropertiesFileService.getMessage("COM009-E",
            paramMess) + "<br>";
        formMst0108d01.setErrMessage(errMessage);
        // set custNmR
        formMst0108d01.setShopNmR("");
        if (formMst0108d01.getRelationMasterErrorFlag()
            .equalsIgnoreCase(MstConst.RELATIONERRORFLAG_0)) {
          relationMasterErrorFlag = MstConst.RELATIONERRORFLAG_3;
          formMst0108d01.setRelationMasterErrorFlag(relationMasterErrorFlag);
        }
      }
    }
    if (!relationMasterErrorFlag.equalsIgnoreCase(MstConst.RELATIONERRORFLAG_0)) {
      return formMst0108d01;
    } else {
      ArrayList<Mst0108d01Model> lst = (ArrayList<Mst0108d01Model>)
          getDataForSearchReSult(request, model,
          formMst0108d01, custCode, shopCode);
      formMst0108d01.setMst0108d01Models(lst);
      
      return formMst0108d01;
    }
    
  }


  /**
   * getDataForSearchReSult 検索結果のデータを取得.
   * 
   * @param request  request
   * @param model モデル
   * @param formMst0108d01　画面フォーム
   * @param custCode　　custCode
   * @param custCode　custCode
   * @return List
   * @throws JsonProcessingException　e
   */
  public List<Mst0108d01Model> getDataForSearchReSult(
      HttpServletRequest request, Model model,
      FormMst0108d01 formMst0108d01, String custCode, String shopCode)
          throws JsonProcessingException {
    // セッションのデータを取得する
    CommonGetSystemCom commonGetSystemCom = new CommonGetSystemCom(commonDao,
        null, null,
        readPropertiesFileService);
    String businessDate = commonGetSystemCom.getAplDate();
    Map<String, Object> sessionData = Util.getContentsFromSession(request);
    // ログイン事業所コード値を取得する
    String jiGyouShoCode = "";
    String loginJigyouShoCode = String.valueOf(sessionData.get(
        CommonConst.LOGIN_USER_JIGYOSHO_CODE));
    String sysaddmin = String.valueOf(sessionData.get(
        CommonConst.LOGIN_USER_SYS_ADMIN_FLG));
    if (sysaddmin.equalsIgnoreCase(MstConst.SYS_ADMIN_FLG_INVALID)) {
      jiGyouShoCode = loginJigyouShoCode;
    } else {
        if (formMst0108d01.getShozokuClassList().equalsIgnoreCase("")) {
          jiGyouShoCode = formMst0108d01.getShozokuClassList();
          
        } else {
          jiGyouShoCode = formMst0108d01.getJigyouShoCode();
        }
    }
    custCode = formMst0108d01.getCstCode();
    String itemCode = formMst0108d01.getItemCode();
    shopCode = formMst0108d01.getShopCode();
    String bunruiCode = formMst0108d01.getBunruiCode();
    String salesKb = formMst0108d01.getSalesKb();
    String validFrom = formMst0108d01.getValidFrom();
    String validTo = formMst0108d01.getValidTo();
    boolean checkCancelData = formMst0108d01.isCheckCancleData();
    int chkCancelData = 0;
    if (checkCancelData == true) {
      chkCancelData = 1;
    } else {
      chkCancelData = 0;
    }
    int checkUpdYmd = formMst0108d01.getCheckType();
    int searchMax = formMst0108d01.getSearchMax();
    int flag = 1;
    if (jiGyouShoCode.equalsIgnoreCase("")) {
      flag = 0;
    }
    
    ArrayList<Mst0108d01Model> lstTokuiSakiClassInfo = new ArrayList<Mst0108d01Model>();
    try {
      Map<String, Object> parm = new HashMap<String, Object>(); // Mapper
      //
     
      parm.put("searchMax", searchMax + 1); 
      parm.put("businessDate", businessDate);
      parm.put("loginJigyouShoCode", jiGyouShoCode);
      parm.put("custCode", custCode);
      parm.put("itemCode", itemCode);
      parm.put("shopCode", shopCode);
      parm.put("bunruiCode", bunruiCode);
      parm.put("salesKb", salesKb);
      parm.put("validFrom", validFrom);
      parm.put("validTo", validTo);
      parm.put("checkUpdYmd", checkUpdYmd);
      parm.put("checkCancelData", chkCancelData);
      parm.put("flag", flag);
      List<Mst0108d01Model> lstMst0108d01 = mst0108d01Dao.getMst0108d01Mapper()
          .getDefautData(parm);
      int itemPriceInfo = MstConst.SIZE_ZERO;
      if (lstMst0108d01.size() == MstConst.SIZE_ZERO) {
        formMst0108d01.setItemPriceInfo(itemPriceInfo);

        lstMst0108d01 = null;
      } else {
        itemPriceInfo = MstConst.ONE_FLAG;
        formMst0108d01.setItemPriceInfo(itemPriceInfo);
        for (int i = 0; i < lstMst0108d01.size(); i++) {
          Mst0108d01Model tokuiSakiInfo = new Mst0108d01Model();
          // データリストを変換します
          tokuiSakiInfo.setNumber(i + 1);
          // カストコード
          tokuiSakiInfo.setCstCode(lstMst0108d01.get(i).getCstCode());
          // カスト名
          tokuiSakiInfo.setCustNmR(Util.convertSanitize(lstMst0108d01.get(i).getCustNmR()));
          // 商品番号
          tokuiSakiInfo.setItemCode(lstMst0108d01.get(i).getItemCode());
          // 項目名
          tokuiSakiInfo.setHinRyaKu(Util.convertSanitize(lstMst0108d01.get(i).getHinRyaKu()));
          
           shopCode = lstMst0108d01.get(i).getShopCode();
           if (shopCode.equalsIgnoreCase(readPropertiesFileService.getSetting("TEN_CODE_NONE"))) {
             // 店舗コード
             tokuiSakiInfo.setShopCode("");
             // 店名
             tokuiSakiInfo.setShopNmR("");
           } else {
             // 店舗コード
             tokuiSakiInfo.setShopCode(lstMst0108d01.get(i).getShopCode());
             // 店名
             tokuiSakiInfo.setShopNmR(Util.convertSanitize(lstMst0108d01.get(i).getShopNmR()));
           }
          
          // 販売KB
          tokuiSakiInfo.setSalesKb(lstMst0108d01.get(i).getSalesKb());
          // 販売名
          tokuiSakiInfo.setSalesNm(Util.convertSanitize(lstMst0108d01.get(i).getSalesNm()));
          // から有効
          tokuiSakiInfo.setValidFrom(lstMst0108d01.get(i).getValidFrom());
          // への有効な
          tokuiSakiInfo.setValidTo(lstMst0108d01.get(i).getValidTo());
          // カストのデリの短歌.
          String custDeliTanka = lstMst0108d01.get(i).getCustDeliTanka();
          custDeliTanka = addZeroForNumeric(custDeliTanka);
          tokuiSakiInfo.setCustDeliTanka(custDeliTanka);
          // カスト売り短歌
          tokuiSakiInfo.setCustSellTanka(lstMst0108d01.get(i)
              .getCustSellTanka());
          // カストのBILDの短歌
          String custBildTanka = lstMst0108d01.get(i).getCustBildTanka();
          custBildTanka = addZeroForNumeric(custBildTanka);
          tokuiSakiInfo.setCustBildTanka(custBildTanka);
          // bunruiコード
          tokuiSakiInfo.setBunruiCode(lstMst0108d01.get(i).getBunruiCode());
          // STSコード
          tokuiSakiInfo.setStsCode(lstMst0108d01.get(i).getStsCode());
          // ユーザーID追加
          tokuiSakiInfo.setInsUserId(lstMst0108d01.get(i).getInsUserId());
          // Pg追加
          tokuiSakiInfo.setInsPgId(lstMst0108d01.get(i).getInsPgId());
          // イン年月日
          tokuiSakiInfo.setInsYmd(lstMst0108d01.get(i).getInsYmd());
          // 時間内に
          tokuiSakiInfo.setInsTime(lstMst0108d01.get(i).getInsTime());
          // ユーザIDを更新
          tokuiSakiInfo.setUpdUserId(lstMst0108d01.get(i).getUpdUserId());
          // Pg アップデート
          tokuiSakiInfo.setUpdPgId(lstMst0108d01.get(i).getUpdPgId());
          // 日付で更新
          tokuiSakiInfo.setUpdYmd(lstMst0108d01.get(i).getUpdYmd());
          // 時間の更新
          tokuiSakiInfo.setUpdTime(lstMst0108d01.get(i).getUpdTime());
          lstTokuiSakiClassInfo.add(tokuiSakiInfo);
        }
      }

      // 取得データ変換
      return lstTokuiSakiClassInfo;
    } catch (MyBatisSystemException e) {
      // 得意先品目価格マスタ情報格納クラス = Null を返却する。
      model.addAttribute("listAll", null);
      logger.error(e.getMessage());
      throw e;
    }
  }

  /**
   * checkExist 存在チェック.
   * 
   * @param model model 
   * @param formMst0108d01 formMst0108d01
   * @return formMst0108d01
   */
  public FormMst0108d01 checkExist(Model model, FormMst0108d01 formMst0108d01) {
    String custCode = "";
    String officeCode = "";
    int checkType = 0;
    String errMessage = "";
    custCode = formMst0108d01.getCstCode();
    officeCode = formMst0108d01.getOfficeCode();
    checkType = formMst0108d01.getCheckType();
    String itemCode = "";
    String shopCode = "";
    itemCode = formMst0108d01.getItemCode();
    shopCode = formMst0108d01.getShopCode();
    Short cainCode = 0;
    Short cainIdx = 0;
    // COM009-E エラー
    ArrayList<String> paramMessCom009E = new ArrayList<String>();
    
    CommonGetData commGetData = new CommonGetData(commonDao,
        readPropertiesFileService);

    CommonGetSystemCom commonGetSystemCom = new CommonGetSystemCom(commonDao,
        null, null,
        readPropertiesFileService);
    String businessDate = commonGetSystemCom.getAplDate();
    String relationMasterErrorFlag = MstConst.RELATIONERRORFLAG_0;
    formMst0108d01.setRelationMasterErrorFlag(relationMasterErrorFlag);
    if (!custCode.equalsIgnoreCase("")) {
      // 結果判定
      // 共通部品を使って、得意先情報を取得する
      CustomerData data = new CustomerData();
      data = commGetData.getCustomerData(custCode, officeCode, checkType);
      // 変数[得意先情報格納クラス].メッセージコード ＝ Null の場合

      if (data.getMsgCd() == null || data.getMsgCd().equalsIgnoreCase("")) {
        /*custtomerInfo = data.getCst().getCustCls();*/
        cainCode = data.getCst().getCainCode();
        cainIdx = data.getCst().getCainIdx();
        formMst0108d01.setCustNmR(data.getCst().getCustNmR());
      } else {
        // 変数[得意先情報格納クラス].メッセージコード ≠ Null の場合
        // 画面にエラーメッセージを表示する。
        // COM009-E
        paramMessCom009E = new ArrayList<String>();
        paramMessCom009E.add("得意先マスタ");
        paramMessCom009E.add("入力された得意先");
        errMessage += readPropertiesFileService.getMessage("COM009-E",
            paramMessCom009E) + "<br>";
        formMst0108d01.setErrorMessages(errMessage);

        // set custNmR
        formMst0108d01.setCustNmR("");
        relationMasterErrorFlag = MstConst.RELATIONERRORFLAG_1;
        formMst0108d01.setRelationMasterErrorFlag(relationMasterErrorFlag);
      }
    }
    if (!custCode.equalsIgnoreCase("") && !itemCode.equalsIgnoreCase("")) {
      try {
        Map<String, Object> parm = new HashMap<String, Object>(); // Mapper
        parm.put("businessDate", businessDate);
        parm.put("itemCode", itemCode);
        parm.put("custCode", cainCode);
        parm.put("cstNmr", cainIdx);
        List<Mst0108d01Model> lstItemName = mst0108d01Dao.getMst0108d01Mapper()
            .getItemName(parm);
        // jsonData = lstItemName.get(0).getHinRyaKu();
        if (lstItemName.size() > 0) {
          formMst0108d01.setHinRyaKu(lstItemName.get(0).getHinRyaKu());
        } else if (lstItemName.size() <= 0) {
          // COM009-E
          paramMessCom009E = new ArrayList<String>();
          paramMessCom009E.add("製品マスタ");
          paramMessCom009E.add("入力された品目");
          errMessage += readPropertiesFileService.getMessage("COM009-E",
              paramMessCom009E) + "<br>";
          formMst0108d01.setErrorMessages(errMessage);
          // set custNmR
          formMst0108d01.setHinRyaKu("");
          if (formMst0108d01.getRelationMasterErrorFlag().equalsIgnoreCase(
              MstConst.FLAG_DISABLE) ) {
            relationMasterErrorFlag = MstConst.RELATIONERRORFLAG_2;
            formMst0108d01.setRelationMasterErrorFlag(relationMasterErrorFlag);
          }
        }
      } catch (Exception e) {
        e.getMessage();
      }
    }
    if (!custCode.equalsIgnoreCase("") && !shopCode.equalsIgnoreCase("")) {
      ShopData shopData = commGetData.getShopData(shopCode, custCode);
      if (shopData.getMsgCd() == null || shopData.getMsgCd().equalsIgnoreCase(
          "")) {
        /*String shopInfo = shopData.getShp().getSumShopCode();*/
        shopCode = shopData.getShp().getShopCode();
        String shopNmR = shopData.getShp().getShopNmR();
        formMst0108d01.setShopNmR(shopNmR);
      } else {
        // COM009-E
        paramMessCom009E = new ArrayList<String>();
        paramMessCom009E.add("店舗マスタ");
        paramMessCom009E.add("入力された店舗");
        errMessage += readPropertiesFileService.getMessage("COM009-E",
            paramMessCom009E) + "<br>";
        formMst0108d01.setErrorMessages(errMessage);
        // set custNmR
        formMst0108d01.setCustNmR("");
        if (formMst0108d01.getRelationMasterErrorFlag().equalsIgnoreCase(MstConst.NONE_ERROR)) {
          relationMasterErrorFlag = MstConst.RELATIONERRORFLAG_3;
          formMst0108d01.setRelationMasterErrorFlag(relationMasterErrorFlag);
        } else {
          return null;
        }
      }

    }
    formMst0108d01.setErrorMessages(errMessage);
    return formMst0108d01;
  }

  /**
   * CSV Export 発生したCSVファイル.
   * 
   * @param formMst0104d01:フォーム
   * @return 画面表示
   * @throws Exception:エラー画面
   */
  public String exportDataCsv(HttpServletRequest request, 
      FormMst0108d01 formMst0108d01) throws Exception {
   
   
    
    formMst0108d01.setErrorMessages("");
    // セッションのデータを取得する
    Map<String, Object> sessionData = Util.getContentsFromSession(request);
    // ログイン事業所コード値を取得する
    String jiGyouShoCode = "";
    String loginJigyouShoCode = String.valueOf(sessionData.get(
        CommonConst.LOGIN_USER_JIGYOSHO_CODE));
    String sysaddmin = String.valueOf(sessionData.get(
        CommonConst.LOGIN_USER_SYS_ADMIN_FLG));
    if (sysaddmin.equalsIgnoreCase(MstConst.SYS_ADMIN_FLG_INVALID)) {
      jiGyouShoCode = loginJigyouShoCode;
    } else {
      if (formMst0108d01.getShozokuClassList().equalsIgnoreCase("")) {
        jiGyouShoCode = formMst0108d01.getShozokuClassList();
        
      } else {
        jiGyouShoCode = formMst0108d01.getJigyouShoCode();
      }
    }

    boolean chkCancelDb = formMst0108d01.isCheckCancleData();
    boolean chkUpdYmd = formMst0108d01.isCheckUpdYmd();
    int checkUpdYmd = 0;
    if (chkUpdYmd == true) {
      checkUpdYmd = 1;
    } else {
      checkUpdYmd = 0;
    }
    int checkCancelData = 0;
    if (chkCancelDb == true) {
      checkCancelData = 1;
    } else {
      checkCancelData = 0;
    }
    // CSVファイルを作成する
    CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(
        CommonConst.NEW_LINE_SEPARATOR);

   
    CommonGetSystemCom commonGetSystemCom = new CommonGetSystemCom(commonDao,
        null, null,
        readPropertiesFileService);
    String businessDate = commonGetSystemCom.getAplDate();

    // 条件をセットする
    Map<String, Object> params = new HashMap<String, Object>();
    //replace 適用期間
    String validFrom = formMst0108d01.getValidFrom().replace("/", "");
    String validTo = formMst0108d01.getValidTo().replace("/", "");
    params.put("loginJigyouShoCode", jiGyouShoCode);
    params.put("businessDate", businessDate);
    params.put("custCode", formMst0108d01.getCstCode());
    params.put("itemCode", formMst0108d01.getItemCode());
    params.put("shopCode", formMst0108d01.getShopCode());
    params.put("bunruiCode", formMst0108d01.getBunruiCode());
    params.put("salesKb", formMst0108d01.getMasutaKubunClassList());
    params.put("validFrom",validFrom );
    params.put("validTo",validTo );
    params.put("checkUpdYmd", checkUpdYmd);

    params.put("checkCancelData", checkCancelData);
    String resultCsv = "";

    String currentDate = DateUtil.getSysDate();
    String currentTime = DateUtil.getSysTime();
    // ファイル名
    String pathFile = "Mst01-08d01_" + currentDate + currentTime + ".csv";
    // ＣＳＶファイルを保存する場所
    String pathFolder = readPropertiesFileService.getSetting("PATH_CSV");
    String folderFile = pathFolder + "\\" + pathFile;
    OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(
        folderFile), "Windows-31J");
    CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
    Object[] fileHeader = {"No", "得意先コード", "得意先略称", "品目コード", "製品略称", "店舗コード",
        "店舗略称", "販売区分", "販売区分名称",
        "適用期間(開始）", "適用期間(終了）", "納品単価", "販売単価", "先方仕切単価", "分類コード", "状況コード",
        "登録ユーザID", "登録プログラムID", "登録年月日",
        "登録時刻", "修正ユーザID", "修正プログラムID", "修正年月日", "修正時刻"};
    csvFilePrinter.printRecord(fileHeader);

    SeqProc seqProc = new SeqProc(csvFilePrinter);
    try {
       mst0108d01Dao.getMst0108d01Mapper().getSearchResultCsv(params, seqProc);
    } catch (Exception e) {
      logger.error(e.getMessage());
      throw e;
    } finally {
      try {
        fileWriter.flush();
        fileWriter.close();
        csvFilePrinter.close();
      } catch (IOException e) {
        logger.error(e.getMessage());
        throw e;
      }
      if (seqProc.isEmpty) {
        File file = new File(folderFile);
        if (file.exists() && !file.isDirectory()) {
          file.delete();
        }
        
        return null;
      }
    }
    resultCsv = readPropertiesFileService.getSetting("URL_CSV_DOWNLOAD")
        + pathFile;
    return resultCsv;
  }

  /**
   * SQL実行結果を1レコードずつ処理するための内部クラス
   *
   * @author TSUZUKI DENKI
   * @version 1.00
   * @since BSS 1.0.0
   *
   */
  private class SeqProc implements ResultHandler {

    /** CSVプリンタ */
    private CSVPrinter printer;

    /** 処理結果が空 */
    private boolean isEmpty;

    /** 連番 */
    private int renban;

    /**
     * コンストラクタ
     *
     * @param nengetsudo
     *          年月
     * @param printer
     *          CSVプリンタ
     */
    public SeqProc(CSVPrinter printer) {
      this.printer = printer;
      this.setEmpty(true);
      renban = 0;
    }

    /**
     * 検索結果を1レコードずつ取得し、CSVに出力する
     *
     * @param context
     *          リザルトコンテキスト
     */
    @Override
    public void handleResult(ResultContext context) {
      renban++;
      this.setEmpty(false);

      MstCorrectKbMst0108d01 courseInfo = (MstCorrectKbMst0108d01) context
          .getResultObject();

      try {
        printer.print(renban);
        printer.print(Util.convertUnsanitize(courseInfo.getCstCode()));
        printer.print(Util.convertUnsanitize(courseInfo.getCustNmR()));
        printer.print(Util.convertUnsanitize(courseInfo.getItemCode()));
        printer.print(Util.convertUnsanitize(courseInfo.getHinRyaKu()));
        printer.print(Util.convertUnsanitize(courseInfo.getShopCode()));
        printer.print(Util.convertUnsanitize(courseInfo.getShopNmR()));
        printer.print(Util.convertUnsanitize(courseInfo.getSalesKb()));
        printer.print(Util.convertUnsanitize(courseInfo.getSalesNm()));
        printer.print(Util.convertUnsanitize(courseInfo.getValidFrom()));
        printer.print(Util.convertUnsanitize(courseInfo.getValidTo()));
        printer.print(courseInfo.getCustDeliTanka());
        printer.print(courseInfo.getCustSellTanka());
        printer.print(courseInfo.getCustBildTanka());
        printer.print(Util.convertUnsanitize(courseInfo.getBunruiCode()));
        printer.print(Util.convertUnsanitize(courseInfo.getStsCode()));
        printer.print(Util.convertUnsanitize(courseInfo.getInsUserId()));
        printer.print(Util.convertUnsanitize(courseInfo.getInsPgId()));
        printer.print(Util.convertUnsanitize(courseInfo.getInsYmd()));
        printer.print(Util.convertUnsanitize(courseInfo.getInsTime()));
        printer.print(Util.convertUnsanitize(courseInfo.getUpdUserId()));
        printer.print(Util.convertUnsanitize(courseInfo.getUpdPgId()));
        printer.print(Util.convertUnsanitize(courseInfo.getUpdYmd()));
        printer.print(Util.convertUnsanitize(courseInfo.getUpdTime()));

        // 次のレコード
        printer.println();

      } catch (IOException ioe) {
        throw new RuntimeException(ioe);
      }
    }

    public void setEmpty(boolean isEmpty) {
      this.isEmpty = isEmpty;
    }

  }

  /**
   * 排他の設定.
   * 
   * @param formMst0108d01:フォーム
   */
  public void setHaitaDate(FormMst0108d01 formMst0108d01) {
    formMst0108d01.setHaitaDate(DateUtil.getSysDate());
    formMst0108d01.setHaitaTime(DateUtil.getSysTime());
  }

  /**
   * addZeroForNumeric.
   * @param val String
   * @return String
   */
  public String addZeroForNumeric(String val) {
    NumberFormat formatter = new DecimalFormat("#0.00");     
    return formatter.format(Double.valueOf(val)); 
    
  }
  }
