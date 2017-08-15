/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl
 * ファイル名:Mst0103d02Controller.java
 * 
 * <p>作成者:ABV)TramChu
 * 作成日:2015/08/24
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/08/24 1.00 ABV)TramChu 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.component.CustomerData;
import jp.co.daitoku_sh.dfcm.common.component.MstGeneralData;
import jp.co.daitoku_sh.dfcm.common.component.ShopData;
import jp.co.daitoku_sh.dfcm.common.controller.AbsController;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCustomer;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSNohin;
import jp.co.daitoku_sh.dfcm.common.db.model.MstShop;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetData;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0103d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0103d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstUserInfoMst0103d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl.Mst0103d02Service;

/**
 * コントローラクラス
 * 
 * @author ABV)TramChu
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/mst/MST01-03D02/")
public class Mst0103d02Controller extends AbsController {

  @Autowired
  @Qualifier("mst0103d02Service")
  private Mst0103d02Service mst0103d02Service;
  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;
  @Autowired
  @Qualifier("commonDao")
  private CommonDao commonDao;

  /**
   * 初期表示処理
   * @param request httpリクエスト
   * @param model モデル
   * @param formMst0103d02 フォーム
   * @param formMst0103d01 一覧画面の情報
   * @param customerCode 得意先コード
   * @param storeCode 店舗コード
   * @param viewMode モード
   * @return ページ表示
   */
  @RequestMapping(value = "/")
  public String top(HttpServletRequest request, Model model,
      FormMst0103d02 formMst0103d02,
      @ModelAttribute("formMst0103d01") FormMst0103d01 formMst0103d01,
      @ModelAttribute("strCustomerCode") String customerCode,
      @ModelAttribute("strStoreCode") String storeCode,
      @ModelAttribute("viewMode") String viewMode) {
    // 画面マッピング
    String strScreen = "mst/mst0103d02";
    // セッション情報の取得に失敗した場合、ログイン画面（DFCM-MST01-03D00）へ遷移する
    String path = Util.checkSession(model, request,
        CommonConst.MST0103D01_SCREEN_NAME);
    if (!path.equalsIgnoreCase("")) {
      return path;
    }
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();
    ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    ErrorMessages errMess = new ErrorMessages();
    // default メッセージを取得する。
    mst0103d02Service.getDefaultMess(model);
    // (1-1) [入力]画面表示モード ＝ Null の場合、画面にエラーメッセージを表示する。
    if (viewMode.equalsIgnoreCase("")) {
      // [変数]メッセージコード（COM026-E）
      lstErrMess = new ArrayList<ErrorMessages>();
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("画面表示モード");
      errMess.setMessageContent(readPropertiesFileService.getMessage("COM026-E",
          paramMess));
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      model.addAttribute("errControl", "1");
      return strScreen;
    }

    // [入力]画面表示モード = '2'（照会） or '3'（訂正） or '4'（取消） の時のみ、後続の引数チェックを行う。
    if (viewMode.equalsIgnoreCase(MstConst.SHOUKAI_MODE) || viewMode
        .equalsIgnoreCase(MstConst.TEISEI_MODE) || viewMode.equalsIgnoreCase(
            MstConst.TORIKESI_MODE)) {
      if (customerCode.equalsIgnoreCase("")) {
        // [変数]メッセージコード（COM026-E）
        lstErrMess = new ArrayList<ErrorMessages>();
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("得意先コード");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM026-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("errControl", "1");
        return strScreen;
      }
      if (storeCode.equalsIgnoreCase("")) {
        // [変数]メッセージコード（COM026-E）
        lstErrMess = new ArrayList<ErrorMessages>();
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("店舗コード");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM026-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("errControl", "1");
        return strScreen;
      }
    }
    // 設定ファイルより、得意先店無し店舗コードを取得する。
    formMst0103d02.setTenCodeNone(readPropertiesFileService.getSetting(
        "TEN_CODE_NONE"));
    // 一覧画面から値を取得する。
    mst0103d02Service.init(formMst0103d02, formMst0103d01, viewMode);
    // 業務日付を取得する。
    CommonGetSystemCom commonGetSystemCom = new CommonGetSystemCom(
        mst0103d02Service.getCommonDao(),
        null, null, readPropertiesFileService);
    String bussinessDate;
    bussinessDate = commonGetSystemCom.getAplDate();
    if (bussinessDate == null) {
      // 画面にエラーメッセージを表示する。
      lstErrMess = new ArrayList<ErrorMessages>();
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("業務日付の取得");
      errMess.setMessageContent(readPropertiesFileService.getMessage(
          "COM015-E", paramMess));
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      model.addAttribute("errControl", "1");
      return strScreen;
    } else {
      // 画面隠し項目に業務日付をセットする。
      formMst0103d02.setBusinessDate(Integer.parseInt(bussinessDate));
    }
    // セッション情報取得
    Map<String, Object> sessionData = null;
    sessionData = Util.getContentsFromSession(request);
    // システム管理者フラグ値を取得する
    String sysAdminFlag = String.valueOf(sessionData.get(
        CommonConst.LOGIN_USER_SYS_ADMIN_FLG));
    formMst0103d02.setSysAdminFlag(sysAdminFlag);
    // ログイン事業所コード値を取得する
    String loginJigyouShoCode = String.valueOf(sessionData.get(
        CommonConst.LOGIN_USER_JIGYOSHO_CODE));
    String loginJigyouShoName = String.valueOf(sessionData.get(
        CommonConst.LOGIN_USER_JIGYOSHO_MEI));
    formMst0103d02.setLoginJigyouShoCode(loginJigyouShoCode);
    formMst0103d02.setLoginJigyouShoName(loginJigyouShoName);
    if (sysAdminFlag.equalsIgnoreCase(CommonConst.SYS_ADMIN_FLG_INVALID)) {
      model.addAttribute("displayDdlOffice", "display_none");
    } else if (sysAdminFlag.equalsIgnoreCase(
        CommonConst.SYS_ADMIN_FLG_VALID)) {
      model.addAttribute("displayDdlOffice", "");
      // 事業所
      if (!mst0103d02Service.setDataOffice(formMst0103d02, model)) {
        lstErrMess = new ArrayList<ErrorMessages>();
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("事業所マスタの取得");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM015-E",
            paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("errControl", "1");
        return strScreen;
      }
    }
    // サンクス区分
    List<MstGeneralData> mstGeneralData = null;
    mstGeneralData = commonGetSystemCom.getMstGeneralConf("Sunks_Kb", null);
    if (mstGeneralData != null && mstGeneralData.size() > 0) {
      mst0103d02Service.setDataClassification(formMst0103d02, model,
          mstGeneralData);
    } else {
      lstErrMess = new ArrayList<ErrorMessages>();
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("汎用マスタ");
      paramMess.add("サンクス区分");
      errMess.setMessageContent(readPropertiesFileService.getMessage(
          "COM009-E", paramMess));
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      model.addAttribute("errControl", "1");
      return strScreen;
    }
    // 店舗集約条件選択肢セット
    List<MstGeneralData> mstGeneralDataLst = null;
    mstGeneralDataLst = commonGetSystemCom.getMstGeneralConf("Sum_Shop_Jkn",
        null);
    if (mstGeneralDataLst != null && mstGeneralDataLst.size() > 0) {
      mst0103d02Service.setDataCondition(formMst0103d02, model,
          mstGeneralDataLst);
    } else {
      lstErrMess = new ArrayList<ErrorMessages>();
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("汎用マスタ");
      paramMess.add("店舗集約条件");
      errMess.setMessageContent(readPropertiesFileService.getMessage(
          "COM009-E", paramMess));
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      model.addAttribute("errControl", "1");
      return strScreen;
    }
    // 店舗マスタデータ取得
    MstUserInfoMst0103d02 mstUserInfoMst0103d02 = null;
    if (!viewMode.equalsIgnoreCase(MstConst.SHINKI_MODE)) {
      mstUserInfoMst0103d02 = mst0103d02Service.getDataStoreMaster(
          bussinessDate, customerCode, storeCode);
      if (mstUserInfoMst0103d02 == null) {
        lstErrMess = new ArrayList<ErrorMessages>();
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("店舗マスタ");
        paramMess.add("店舗マスタ一覧画面で指定された店舗");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("errControl", "1");
        return strScreen;
      }
    }
    // 納品センターの入力項目設定
    if (!viewMode.equalsIgnoreCase(MstConst.SHINKI_MODE)) {
      String officeCodeWk = "";
      String custCode = "";
      if (mstUserInfoMst0103d02 != null) {
        officeCodeWk = mstUserInfoMst0103d02.getJigyoCode();
        custCode = mstUserInfoMst0103d02.getCustCode();
      }
      if (!mst0103d02Service.checkDataAccess(model,
          formMst0103d02.getBusinessDate().toString(), officeCodeWk,
          custCode)) {
        lstErrMess = new ArrayList<ErrorMessages>();
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("納品先マスタの取得");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM015-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("errControl", "1");
        return strScreen;
      }
      if (!viewMode.equalsIgnoreCase(MstConst.TEISEI_MODE)) {
        model.addAttribute("ddlDeliveryCenterDisable", "true");
      }
    }
    // [入力]画面表示モードにより、画面表示を制御する
    mst0103d02Service.setView(model, viewMode, formMst0103d02,
        mstUserInfoMst0103d02);
    return strScreen;
  }

  /**
   * 戻るボタンを押した処理.
   * 
   * @param rattrs 送信パラメーターの設定
   * @param formMst0103d02 フォーム
   * @return 画面表示
   */
  @RequestMapping(value = "proc", params = "Return", method = RequestMethod.POST)
  public String returnPage(RedirectAttributes rattrs,
      FormMst0103d02 formMst0103d02) {
    // [変数]検索条件保持クラス
    FormMst0103d01 formMst0103d01 = new FormMst0103d01();
    formMst0103d01.setDdlOffice(formMst0103d02.getFrm1Office());
    formMst0103d01.setTxtCustomerCode(formMst0103d02.getFrm1CustomerCode());
    formMst0103d01.setTxtCustomerName(formMst0103d02.getFrm1CustomerName());
    formMst0103d01.setTxtStoreCode(formMst0103d02.getFrm1StoreCode());
    formMst0103d01.setTxtStoreName(formMst0103d02.getFrm1StoreName());
    formMst0103d01.setChkCancelData(Boolean.parseBoolean(formMst0103d02
        .getFrm1chkCancelData()));
    formMst0103d01.setViewMode(formMst0103d02.getMode());
    rattrs.addFlashAttribute("formMst0103d01", formMst0103d01);
    return "redirect:/mst/MST01-03D00/";
  }

  /**
   * 登録ボタンを押した処理.
   * 
   * @param formMst0103d02 フォーム
   * @param model モデル
   * @param httpRequest httpリクエスト
   * @return 画面表示
   * @throws Exception Exception　エラー画面
   */
  @RequestMapping(value = "proc", params = "Register", method = RequestMethod.POST)
  public String register(FormMst0103d02 formMst0103d02, Model model,
      HttpServletRequest httpRequest) throws Exception {
    // 画面マッピング
    String strScreen = "mst/mst0103d02";
    // セッション情報の取得に失敗した場合、ログイン画面（DFCM-MST01-03D00）へ遷移する
    String path = Util.checkSession(model, httpRequest,
        CommonConst.MST0103D01_SCREEN_NAME);
    if (!path.equalsIgnoreCase("")) {
      return path;
    }
    // モード設定
    String viewMode = formMst0103d02.getMode();
    model.addAttribute("modeView", viewMode);
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();
    ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    ErrorMessages errMess = new ErrorMessages();
    // セッションのデータを取得する
    Map<String, Object> sessionData = null;
    sessionData = Util.getContentsFromSession(httpRequest);
    // default メッセージを取得する。
    mst0103d02Service.getDefaultMess(model);
    String sysAdminFlag = formMst0103d02.getSysAdminFlag();
    if (sysAdminFlag.equalsIgnoreCase(CommonConst.SYS_ADMIN_FLG_INVALID)) {
      model.addAttribute("displayDdlOffice", "display_none");
    } else if (sysAdminFlag.equalsIgnoreCase(
        CommonConst.SYS_ADMIN_FLG_VALID)) {
      model.addAttribute("displayDdlOffice", "");
      // 事業所
      if (!mst0103d02Service.setDataOffice(formMst0103d02, model)) {
        lstErrMess = new ArrayList<ErrorMessages>();
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("事業所マスタの取得");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM015-E",
            paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("errControl", "1");
        return strScreen;
      }
    }
    // サンクス区分
    CommonGetSystemCom commonGetSystemCom = new CommonGetSystemCom(
        mst0103d02Service.getCommonDao(),
        null, null, readPropertiesFileService);
    List<MstGeneralData> mstGeneralData = null;
    mstGeneralData = commonGetSystemCom.getMstGeneralConf("Sunks_Kb", null);
    if (mstGeneralData != null && mstGeneralData.size() > 0) {
      mst0103d02Service.setDataClassification(formMst0103d02, model,
          mstGeneralData);
    } else {
      lstErrMess = new ArrayList<ErrorMessages>();
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("汎用マスタ");
      paramMess.add("サンクス区分");
      errMess.setMessageContent(readPropertiesFileService.getMessage(
          "COM009-E", paramMess));
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      model.addAttribute("errControl", "1");
      return strScreen;
    }
    // 店舗集約条件選択肢セット
    List<MstGeneralData> mstGeneralDataLst = null;
    mstGeneralDataLst = commonGetSystemCom.getMstGeneralConf("Sum_Shop_Jkn",
        null);
    if (mstGeneralDataLst != null && mstGeneralDataLst.size() > 0) {
      mst0103d02Service.setDataCondition(formMst0103d02, model,
          mstGeneralDataLst);
    } else {
      lstErrMess = new ArrayList<ErrorMessages>();
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("汎用マスタ");
      paramMess.add("店舗集約条件");
      errMess.setMessageContent(readPropertiesFileService.getMessage(
          "COM009-E", paramMess));
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      model.addAttribute("errControl", "1");
      return strScreen;
    }
    // 店舗マスタデータ取得
    MstUserInfoMst0103d02 mstUserInfoMst0103d02 = null;
    if (!viewMode.equalsIgnoreCase(MstConst.SHINKI_MODE)) {
      mstUserInfoMst0103d02 = mst0103d02Service.getDataStoreMaster(
          formMst0103d02.getBusinessDate().toString(), formMst0103d02
              .getTxtCustomerCode(), formMst0103d02.getTxtStoreCode());
      if (mstUserInfoMst0103d02 == null) {
        lstErrMess = new ArrayList<ErrorMessages>();
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("店舗マスタ");
        paramMess.add("店舗マスタ一覧画面で指定された店舗");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("errControl", "1");
        return strScreen;
      }
    }
    // 納品センターの入力項目設定
    if (!viewMode.equalsIgnoreCase(MstConst.SHINKI_MODE)) {
      String officeCodeWk = "";
      String custCode = "";
      if (mstUserInfoMst0103d02 != null) {
        officeCodeWk = mstUserInfoMst0103d02.getJigyoCode();
        custCode = mstUserInfoMst0103d02.getCustCode();
      }
      if (!mst0103d02Service.checkDataAccess(model,
          formMst0103d02.getBusinessDate().toString(), officeCodeWk,
          custCode)) {
        lstErrMess = new ArrayList<ErrorMessages>();
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("納品先マスタの取得");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM015-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("errControl", "1");
        return strScreen;
      }
    }
    String systemAdminFlg = formMst0103d02.getSysAdminFlag();
    if (viewMode.equalsIgnoreCase(MstConst.SHINKI_MODE)) {
      if (systemAdminFlg.equalsIgnoreCase(CommonConst.SYS_ADMIN_FLG_INVALID)) {
        model.addAttribute("ddlOfficeName", formMst0103d02
            .getLoginJigyouShoCode() + ":" + formMst0103d02
                .getLoginJigyouShoName());
      }
      if ((!formMst0103d02.getTxtCustomerCode().equals("")) && (!formMst0103d02
          .getOfficeSelect().equals(""))) {
        mst0103d02Service.checkDataAccess(model,
            formMst0103d02.getBusinessDate().toString(), formMst0103d02
                .getOfficeSelect(),
            formMst0103d02.getTxtCustomerCode());
      }
    }
    if (mstUserInfoMst0103d02 != null) {
      model.addAttribute("collectionDestination", mstUserInfoMst0103d02
          .getShopNmSus());
      model.addAttribute("customerName", mstUserInfoMst0103d02.getCustNmR());
    }
    mst0103d02Service.getCourseInformation(formMst0103d02, model);
    mst0103d02Service.getCommonSpecificationsConfirmation(formMst0103d02,
        model);
    // 項目編集
    String customerCodeWk = formMst0103d02.getTxtCustomerCode();
    if (!customerCodeWk.equals("")) {
      if (customerCodeWk.length() < 7) {
        customerCodeWk = Util.addLeadingZeros(customerCodeWk, 7);
        formMst0103d02.setTxtCustomerCode(customerCodeWk);
      }
    }
    // 共通チェック
    if (!viewMode.equalsIgnoreCase(MstConst.TORIKESI_MODE)) {
      if (mst0103d02Service.checkInput(formMst0103d02, model)) {
        if (formMst0103d02.getDdlDeliveryCenter() == null) {
          model.addAttribute("ddlDeliveryCenterDisable", "true");
        }
        return strScreen;
      }
    }
    if (viewMode.equalsIgnoreCase(MstConst.TORIKESI_MODE)) {
      model.addAttribute("ddlDeliveryCenterDisable", "true");
    }

    lstErrMess = new ArrayList<ErrorMessages>();
    // 得意先マスタ検索
    // 共通部品を使って、得意先情報を取得する
    CustomerData customerData = null;
    MstCustomer customerInfo;
    customerInfo = null;
    String relationMasterErrorFlag;
    relationMasterErrorFlag = MstConst.NONE_ERROR;
    CommonGetData commonGetData = new CommonGetData(commonDao,
        readPropertiesFileService);
    customerData = commonGetData.getCustomerData(customerCodeWk, formMst0103d02
        .getOfficeSelect(), Integer.parseInt(MstConst.CUSTOMERS));
    // 処理結果に応じ、後続の処理を行う。
    String strErrorId;
    strErrorId = "";   
    if (customerData.getMsgCd() == null) {
      customerInfo = customerData.getCst();
      model.addAttribute("customerName", customerInfo.getCustNmR());
      if (customerInfo.getShopKb().equals(MstConst.NOSHOP)) {
        errMess = new ErrorMessages();
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "MST011-E", null));
        lstErrMess.add(errMess);
        relationMasterErrorFlag = MstConst.CUSTOMER_MASTER_ERROR;
        strErrorId += "txtCustomerCode" + MstConst.DELIMITER_ERROR;
      }
    } else {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("得意先マスタ");
      paramMess.add("入力された得意先");
      errMess.setMessageContent(readPropertiesFileService.getMessage("COM009-E",
          paramMess));
      lstErrMess.add(errMess);
      model.addAttribute("customerName", "");
      strErrorId += "txtCustomerCode" + MstConst.DELIMITER_ERROR;
      relationMasterErrorFlag = MstConst.CUSTOMER_MASTER_ERROR;
    }
    ShopData shopData = null;
    MstShop shopInfo = null;
    if (!formMst0103d02.getTxtCollectionDestination().equals("")) {
      // 共通部品を使って、店舗情報を取得する
      shopData = commonGetData.getShopData(formMst0103d02
          .getTxtCollectionDestination(), customerCodeWk);
      // 結果に応じ、後続の処理を行う。
      if (shopData.getMsgCd() == null) {
        shopInfo = shopData.getShp();
        model.addAttribute("collectionDestination", shopInfo.getShopNmR());
        if (!shopInfo.getDeliCenterCode().equals(formMst0103d02
            .getDeliveryCenterSelect())) {
          errMess = new ErrorMessages();
          errMess.setMessageContent(readPropertiesFileService.getMessage(
              "MST002-E",
              null));
          lstErrMess.add(errMess);
          strErrorId += "txtCollectionDestination" + MstConst.DELIMITER_ERROR;
          if (relationMasterErrorFlag.equals(MstConst.NONE_ERROR)) {
            relationMasterErrorFlag = MstConst.COLLECTION_DESTINATION_STORE_ERROR;
          }
        }
      } else {
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("店舗マスタ");
        paramMess.add("入力された集約先店舗");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E",
            paramMess));
        strErrorId += "txtCollectionDestination" + MstConst.DELIMITER_ERROR;
        lstErrMess.add(errMess);
        model.addAttribute("collectionDestination", "");
        if (relationMasterErrorFlag.equals(MstConst.NONE_ERROR)) {
          relationMasterErrorFlag = MstConst.COLLECTION_DESTINATION_STORE_ERROR;
        }
      }
    }
    // 店舗集約条件（請求先伝票種別）
    String denpyoType;
    if (formMst0103d02.getConditionSelect().equals(MstConst.STORE)) {
      // 請求書伝票種別を取得する。
      denpyoType = mst0103d02Service.getInvoiceDocumentsType(customerInfo);
      if (!denpyoType.equals(MstConst.UNIVERSITY_CO_OP_BILL)) {
        errMess = new ErrorMessages();
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "MST006-E",
            null));
        strErrorId += "ddlCondition" + MstConst.DELIMITER_ERROR;
        lstErrMess.add(errMess);
        if (relationMasterErrorFlag.equals(MstConst.NONE_ERROR)) {
          relationMasterErrorFlag = MstConst.STORE_AGGREGATION_CONDITION_ERROR;
        }
      }
    }
    model.addAttribute("errorMessages", lstErrMess);
    // 店舗マスタデータ登録
    if (!relationMasterErrorFlag.equals(MstConst.NONE_ERROR)) {
      model.addAttribute("lstErrorID", strErrorId);
      return strScreen;
    }
    String mode = formMst0103d02.getMode();
    String loginUserId = String.valueOf(sessionData.get("UserCode"));
    // 画面表示モード = 1 （新規登録）の場合
    String customerCode = formMst0103d02.getTxtCustomerCode();
    String storeCode = formMst0103d02.getTxtStoreCode();
    String haitaDate = formMst0103d02.getHaitaDate();
    String haitaTime = formMst0103d02.getHaitaTime();
    if (mode.equalsIgnoreCase(MstConst.SHINKI_MODE)) {
      // 店舗マスタ存在チェック
      if (mst0103d02Service.checkShopExist(formMst0103d02.getTxtCustomerCode(),
          formMst0103d02.getTxtStoreCode())) {
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("店舗");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM027-E",
            paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        strErrorId += "txtCustomerCode" + MstConst.DELIMITER_ERROR;
        strErrorId += "txtStoreCode" + MstConst.DELIMITER_ERROR;
        model.addAttribute("lstErrorID", strErrorId);
        return strScreen;
      }
      // 画面入力内容を店舗マスタに登録する。
      mst0103d02Service.insertMstShop(formMst0103d02, loginUserId);
      // 伝票採番マスタ存在チェック
      if (mst0103d02Service.checkDatIdxExist(formMst0103d02
          .getTxtCustomerCode(), formMst0103d02.getTxtStoreCode())) {
        // 該当レコードが存在する場合、伝票採番マスタ該当レコードの値を初期化する。
        mst0103d02Service.updateMstDatIdx(formMst0103d02, loginUserId);
      } else {
        // 該当レコードが存在しない場合、伝票採番マスタレコードを新規登録する。
        mst0103d02Service.insertMstDatIdx(formMst0103d02, loginUserId);
      }
      // 画面に終了メッセージを表示する。
      lstErrMess = new ArrayList<ErrorMessages>();
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("店舗マスタの登録処理");
      errMess.setMessageContent(readPropertiesFileService.getMessage(
          "COM002-I", paramMess));
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      model.addAttribute("infoMessFlag", "true");
      // return strScreen;
      // 画面表示モード = 3 （訂正）の場合
    } else if (mode.equalsIgnoreCase(MstConst.TEISEI_MODE)) {
      if (mst0103d02Service.checkHaita(customerCode, storeCode, haitaDate,
          haitaTime)) {
        lstErrMess = new ArrayList<ErrorMessages>();
        errMess = new ErrorMessages();
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM031-E", null));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("lstErrorID", strErrorId);
        return strScreen;
      }
      mst0103d02Service.updateMstShop(formMst0103d02, loginUserId);
      // 画面に終了メッセージを表示する。
      lstErrMess = new ArrayList<ErrorMessages>();
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("店舗マスタの登録処理");
      errMess.setMessageContent(readPropertiesFileService.getMessage(
          "COM002-I", paramMess));
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      model.addAttribute("infoMessFlag", "true");
      // 画面表示モード = 4 （取消）の場合
    } else if (mode.equalsIgnoreCase(MstConst.TORIKESI_MODE)) {
      if (mst0103d02Service.checkHaita(customerCode, storeCode, haitaDate,
          haitaTime)) {
        lstErrMess = new ArrayList<ErrorMessages>();
        errMess = new ErrorMessages();
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM031-E", null));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("lstErrorID", strErrorId);
        return strScreen;
      }
      mst0103d02Service.deleteMstShop(formMst0103d02, loginUserId);
      // 画面に終了メッセージを表示する。
      lstErrMess = new ArrayList<ErrorMessages>();
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("店舗マスタの登録処理");
      errMess.setMessageContent(readPropertiesFileService.getMessage(
          "COM002-I", paramMess));
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      model.addAttribute("infoMessFlag", "true");
    }
    String currentDate = DateUtil.getSysDate();
    String currentTime = DateUtil.getSysTime();
    formMst0103d02.setHaitaDate(currentDate);
    formMst0103d02.setHaitaTime(currentTime);
    model.addAttribute("lstErrorID", strErrorId);
    return strScreen;
  }

  /**
   * getDeliveryCenter
   * 
   * @param businessDate [画面_Hiden]業務日付
   * @param officeCode [画面]事業所コード
   * @param customerCode [画面]得意先コード
   * @return 納品先マスタデータ
   * @throws JsonProcessingException Exception　エラー画面
   */
  @RequestMapping(value = "/getDeliveryCenter", method = RequestMethod.POST,
      consumes = "application/x-www-form-urlencoded; charset=utf-8", 
      produces = "application/x-www-form-urlencoded; charset=utf-8")
  @ResponseBody public String getDeliveryCenter(
      @RequestParam(value = "businessDate") String businessDate,
      @RequestParam(value = "officeCode") String officeCode,
      @RequestParam(value = "customerCode") String customerCode)
          throws JsonProcessingException {
    ArrayList<MstSNohin> lstStoreMaster = null;
    String jsonData = "";
    lstStoreMaster = mst0103d02Service.getDeliveryCenter(businessDate,
        officeCode, customerCode);
    if (lstStoreMaster != null && lstStoreMaster.size() > 0) {
      if (lstStoreMaster.get(0).getNhscd().equals("null") || lstStoreMaster.get(
          0).getNhscd().equals("noData")) {
        lstStoreMaster = new ArrayList<MstSNohin>();
        MstSNohin mstSNohin = new MstSNohin();
        ArrayList<String> paramMess = new ArrayList<String>();
        paramMess = new ArrayList<String>();
        paramMess.add("納品先マスタの取得");
        mstSNohin.setAddr1(readPropertiesFileService.getMessage(
            "COM015-E", paramMess));
        lstStoreMaster.add(mstSNohin);
      }
    }
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    jsonData = ow.writeValueAsString(lstStoreMaster);
    return jsonData;
  }
  
  /**
   * getStoreData
   * @param businessDate [画面_Hiden]業務日付
   * @param customerCode [画面]得意先コード
   * @param storeCode [画面]店舗コード
   * @return 納品先マスタデータ
   * @throws JsonProcessingException Exception　エラー画面
   */
  @RequestMapping(value = "/getStoreData", method = RequestMethod.POST, 
      consumes = "application/x-www-form-urlencoded; charset=utf-8", 
      produces = "application/x-www-form-urlencoded; charset=utf-8")
  @ResponseBody public String getStoreData(
      @RequestParam(value = "businessDate") String businessDate,
      @RequestParam(value = "customerCode") String customerCode,
      @RequestParam(value = "storeCode") String storeCode)
          throws JsonProcessingException {
    String jsonData = "";
    // 店舗マスタデータ取得
    MstUserInfoMst0103d02 mstUserInfoMst0103d02 = null;
    mstUserInfoMst0103d02 = mst0103d02Service.getDataStoreMaster(
        businessDate, customerCode, storeCode);
    if (mstUserInfoMst0103d02 != null) {
      // nothing
    } else {
      mstUserInfoMst0103d02 = new MstUserInfoMst0103d02();
      ArrayList<String> paramMess = new ArrayList<String>();
      paramMess = new ArrayList<String>();
      paramMess.add("店舗マスタ");
      paramMess.add("指定された店舗");
      mstUserInfoMst0103d02.setAdr1(readPropertiesFileService.getMessage(
          "COM009-E", paramMess));
    }
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    jsonData = ow.writeValueAsString(mstUserInfoMst0103d02);
    return jsonData;
  }
}
