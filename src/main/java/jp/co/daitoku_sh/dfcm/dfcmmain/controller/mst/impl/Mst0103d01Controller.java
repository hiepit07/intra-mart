/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl
 * ファイル名:Mst0103d01Controller.java
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
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.ModelAttribute;
import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.component.CustomerData;
import jp.co.daitoku_sh.dfcm.common.component.ShopData;
import jp.co.daitoku_sh.dfcm.common.controller.AbsController;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCustomer;
import jp.co.daitoku_sh.dfcm.common.db.model.MstShop;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetData;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0103d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstStoreInfoMst0103d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl.Mst0103d01Service;

/**
 * コントローラクラス
 * 
 * @author ABV)TramChu
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/mst/MST01-03D00/")
public class Mst0103d01Controller extends AbsController {

  // Serviceの定義
  @Autowired
  @Qualifier("mst0103d01Service")
  private Mst0103d01Service mst0103d01Service;

  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;
  @Autowired
  @Qualifier("commonDao")
  private CommonDao commonDao;
  
  /**
   * 初期表示処理
   * 
   * @param request httpリクエスト
   * @param model モデル
   * @param formMst0103d01 フォーム
   * @param formMst0103d01Back form back
   * @return 画面表示
   */
  @RequestMapping(value = "/")
  public String top(HttpServletRequest request, Model model,
      FormMst0103d01 formMst0103d01,
      @ModelAttribute("formMst0103d01") FormMst0103d01 formMst0103d01Back) {

    // ビュー画面を呼ぶ
    String strScreen = "mst/mst0103d01";
    // セッション情報の取得に失敗した場合、ログイン画面（DFCM-MST01-03D00）へ遷移する
    String path = Util.checkSession(model, request,
        CommonConst.MST0103D01_SCREEN_NAME);
    if (!path.equalsIgnoreCase("")) {
      return path;
    }
    mst0103d01Service.getDefaultMess(model);
    CommonGetSystemCom commonGetSystemCom = new CommonGetSystemCom(commonDao,
        null, null, readPropertiesFileService);
    int searchMax = commonGetSystemCom.getCodeSearchMax();
    formMst0103d01.setSearchMax(searchMax);
    ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    ErrorMessages errMess = new ErrorMessages();
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();
    // セッション情報取得
    Map<String, Object> sessionData = Util.getContentsFromSession(request);
    // 業務日付取得
    String bussinessDate;
    bussinessDate = commonGetSystemCom.getAplDate();
    // 結果判定
    if (bussinessDate == null) {
      lstErrMess = new ArrayList<ErrorMessages>();
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("業務日付の取得");
      errMess.setMessageContent(readPropertiesFileService.getMessage("COM015-E",
          paramMess));
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      model.addAttribute("errControl", "1");
      return strScreen;
    } else {
      formMst0103d01.setBusinessDate(bussinessDate);
    }
    // システム管理者フラグ値を取得する
    String sysAdminFlag = String.valueOf(sessionData.get(
        CommonConst.LOGIN_USER_SYS_ADMIN_FLG));
    formMst0103d01.setSysAdminFlag(sysAdminFlag);
    if (sysAdminFlag.equalsIgnoreCase(CommonConst.SYS_ADMIN_FLG_VALID)) {
      if (!mst0103d01Service.setDataOffice(formMst0103d01, model)) {
        lstErrMess = new ArrayList<ErrorMessages>();
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("事業所マスタ");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM015-E",
            paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("errControl", "1");
        return strScreen;
      }
    } else if (sysAdminFlag.equalsIgnoreCase(CommonConst.SYS_ADMIN_FLG_INVALID)) {
      model.addAttribute("displayDdlOffice", "display_none");
    }
    // ログイン事業所コード値を取得する
    String loginJigyouShoCode = String.valueOf(sessionData.get(
        CommonConst.LOGIN_USER_JIGYOSHO_CODE));
    formMst0103d01.setLoginJigyouShoCode(loginJigyouShoCode);
    if (formMst0103d01Back.getDdlOffice() != null) {
      // [入力]検索条件保持クラス ≠ NULL の場合、ヘッダー部（検索条件入力エリア）に[入力]検索条件保持クラスをセットする
      mst0103d01Service.initScreenWithConditional(formMst0103d01Back,
          formMst0103d01);
    } else {
      formMst0103d01.setDdlOffice(loginJigyouShoCode);
      formMst0103d01.setChkCancelData(false);
    }
    return strScreen;
  }

  /**
   * 登録画面に遷移する.
   * 
   * @param rattrs 送信パラメーターの設定
   * @param formMst0103d01 form
   * @return 画面表示
   */
  @RequestMapping(value = "proc", method = RequestMethod.POST)
  public String changePage(RedirectAttributes rattrs,
      FormMst0103d01 formMst0103d01) {
    // 新規モード
    // [変数]検索条件保持クラス
    // [変数]担当者コード
    String strCustomerCode = "";
    String strStoreCode = "";
    if (!formMst0103d01.getViewMode().equalsIgnoreCase(MstConst.SHINKI_MODE)) {
      strCustomerCode = formMst0103d01.getSelectCustomerCode();
      strStoreCode = formMst0103d01.getSelectStoreCode();
    }
    // 検索条件を登録画面に送信する。
    rattrs.addFlashAttribute("formMst0103d01", formMst0103d01);
    // 選択した担当者コードを登録画面に送信する。
    rattrs.addFlashAttribute("strCustomerCode", strCustomerCode);
    rattrs.addFlashAttribute("strStoreCode", strStoreCode);
    // モード値を登録画面に送信する。
    rattrs.addFlashAttribute("viewMode", formMst0103d01.getViewMode());

    return "redirect:/mst/MST01-03D02/";
  }

  /**
   * 一覧画面で最初に検索を実行する
   * 
   * @param model モデル
   * @param formMst0103d01 form
   * @return list search
   * @throws JsonProcessingException エラー画面
   */
  
  @RequestMapping(value = "/getSearchResultInit", method = RequestMethod.POST,
      consumes = "application/x-www-form-urlencoded; charset=utf-8",
      produces = "application/x-www-form-urlencoded; charset=utf-8")
  @ResponseBody public String getSearchResultInit(Model model,
      FormMst0103d01 formMst0103d01) throws JsonProcessingException {
    String jsonData;
    jsonData = "";
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();
    // 関数を呼び出し、担当者一覧データを取得する
    ArrayList<MstStoreInfoMst0103d01> lstMstStoreInfoMst0103d01;
    lstMstStoreInfoMst0103d01 = new ArrayList<MstStoreInfoMst0103d01>();
    lstMstStoreInfoMst0103d01 = mst0103d01Service.getSearchResult(
        formMst0103d01);
    // 排他処理 検索時にシステム日付を取得し、画面に保持する。初期表示で検索する場合は、初期処理で実装する。
    mst0103d01Service.setHaitaDate(formMst0103d01);
    MstStoreInfoMst0103d01 addHaita = new MstStoreInfoMst0103d01();
    addHaita.setupdymd(formMst0103d01.getHaitaDate());
    addHaita.setType("haita");
    addHaita.setupdtime(formMst0103d01.getHaitaTime());
    if (lstMstStoreInfoMst0103d01 != null && lstMstStoreInfoMst0103d01.size() == 0 ) {
      MstStoreInfoMst0103d01 error = new MstStoreInfoMst0103d01();
      error.setType("body");
      error.setMessage("");
      lstMstStoreInfoMst0103d01.add(error);
    } else if (lstMstStoreInfoMst0103d01 != null && lstMstStoreInfoMst0103d01.size() > 0) {
      lstMstStoreInfoMst0103d01.add(addHaita);
    }
    int searchMax = formMst0103d01.getSearchMax() + 1;
    if (lstMstStoreInfoMst0103d01 != null && lstMstStoreInfoMst0103d01
        .size() >= searchMax) {
      MstStoreInfoMst0103d01 error = new MstStoreInfoMst0103d01();
      paramMess = new ArrayList<String>();
      paramMess.add(String.valueOf(formMst0103d01.getSearchMax()));
      error.setType("searchMax");
      error.setMessage(readPropertiesFileService.getMessage("COM005-W",
          paramMess));
      lstMstStoreInfoMst0103d01.add(error);
    }
    jsonData = mst0103d01Service.convertJson(lstMstStoreInfoMst0103d01);
    return jsonData;
  }

  /**
   * 一覧画面で最初に検索を実行する
   * 
   * @param model モデル
   * @param formMst0103d01 form
   * @return list search
   * @throws JsonProcessingException エラー画面
   */

  @RequestMapping(value = "/getSearchResult", method = RequestMethod.POST,
      consumes = "application/x-www-form-urlencoded; charset=utf-8", 
      produces = "application/x-www-form-urlencoded; charset=utf-8")
  @ResponseBody
  public String getSearchResult(Model model,
      FormMst0103d01 formMst0103d01) throws JsonProcessingException {
    String jsonData = "";
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();
    // 得意先マスタ検索
    String customerCodeWk = formMst0103d01.getTxtCustomerCode();
    MstCustomer customerInfo;
    boolean relationMasterErrorFlag = false;
    customerInfo = null;
    CustomerData customerData = null;
    CommonGetData commonGetData = new CommonGetData(commonDao,
        readPropertiesFileService);
    ArrayList<MstStoreInfoMst0103d01> lstMstStoreInfoMst0103d01 = null;
    lstMstStoreInfoMst0103d01 = new ArrayList<MstStoreInfoMst0103d01>();
    // 入力文字数が７文字に満たない場合、0編集を行う。
    if (!customerCodeWk.equalsIgnoreCase("")) {
      if (customerCodeWk.length() < 7) {
        customerCodeWk = Util.addLeadingZeros(customerCodeWk, 7);
      }
      // 共通部品を使って、得意先情報を取得する
      customerData = commonGetData.getCustomerData(customerCodeWk, null, Integer
          .parseInt(MstConst.CUSTOMERS));
      // 結果判定
      if (customerData.getMsgCd() == null) {
        customerInfo = customerData.getCst();
      } else {
        MstStoreInfoMst0103d01 error;
        error = new MstStoreInfoMst0103d01();
        paramMess = new ArrayList<String>();
        paramMess.add("得意先マスタ");
        paramMess.add("入力された得意先");
        error.setMessage(readPropertiesFileService.getMessage("COM009-E",
            paramMess));
        error.setIdClear("customerName");
        error.setType("msgEr");
        lstMstStoreInfoMst0103d01.add(error);
        relationMasterErrorFlag = true;
      }
    }
    // 店舗マスタ検索
    String storeCode = formMst0103d01.getTxtStoreCode();
    ShopData shopData = null;
    MstShop shopInfo = null;
    if (!customerCodeWk.equals("") && !storeCode.equals("")) {
      // 共通部品を使って、店舗情報を取得する
      shopData = commonGetData.getShopData(storeCode, customerCodeWk);
      // 結果判定
      if (shopData.getMsgCd() == null) {
        shopInfo = shopData.getShp();
      } else {
        MstStoreInfoMst0103d01 error;
        error = new MstStoreInfoMst0103d01();
        paramMess = new ArrayList<String>();
        paramMess.add("店舗マスタ");
        paramMess.add("入力された店舗");
        error.setMessage(readPropertiesFileService.getMessage("COM009-E",
            paramMess));
        error.setType("msgEr");
        error.setIdClear("storeName");
        lstMstStoreInfoMst0103d01.add(error);
        relationMasterErrorFlag = true;
      }
    }
    // 処理継続判定
    if (relationMasterErrorFlag) {
      if (customerInfo != null) {
        MstStoreInfoMst0103d01 error = new MstStoreInfoMst0103d01();
        error.setType("customerInfo");
        error.setCustNmR(customerInfo.getCustCode());
        error.setMessage(customerInfo.getCustNmR());
        lstMstStoreInfoMst0103d01.add(error);
      }
      if (shopInfo != null) {
        MstStoreInfoMst0103d01 error = new MstStoreInfoMst0103d01();
        error.setType("shopInfo");
        error.setShopCode(shopInfo.getShopCode());
        error.setShopNm(shopInfo.getShopNmR());
        lstMstStoreInfoMst0103d01.add(error);
      }
      jsonData = mst0103d01Service.convertJson(lstMstStoreInfoMst0103d01);
      return jsonData;
    }
    int searchMax = formMst0103d01.getSearchMax() + 1;
    lstMstStoreInfoMst0103d01 = mst0103d01Service.getSearchResult(
        formMst0103d01);
    if (lstMstStoreInfoMst0103d01 != null && lstMstStoreInfoMst0103d01
        .size() <= 0) {
      // データがない場合
      MstStoreInfoMst0103d01 error = new MstStoreInfoMst0103d01();
      error.setType("body");
      error.setMessage(readPropertiesFileService.getMessage("COM025-E",
          null));
      lstMstStoreInfoMst0103d01.add(error);
    } else if (lstMstStoreInfoMst0103d01 != null && lstMstStoreInfoMst0103d01
        .size() >= searchMax) {
      MstStoreInfoMst0103d01 error = new MstStoreInfoMst0103d01();
      paramMess = new ArrayList<String>();
      paramMess.add(String.valueOf(formMst0103d01.getSearchMax()));
      error.setType("searchMax");
      error.setMessage(readPropertiesFileService.getMessage("COM005-W",
          paramMess));
      lstMstStoreInfoMst0103d01.add(error);
    }
    if (customerInfo != null) {
      MstStoreInfoMst0103d01 error = new MstStoreInfoMst0103d01();
      error.setType("customerInfo");
      error.setCustNmR(customerInfo.getCustCode());
      error.setMessage(customerInfo.getCustNmR());
      lstMstStoreInfoMst0103d01.add(error);
    }
    if (shopInfo != null) {
      MstStoreInfoMst0103d01 error = new MstStoreInfoMst0103d01();
      error.setType("shopInfo");
      error.setShopCode(shopInfo.getShopCode());
      error.setShopNm(shopInfo.getShopNmR());
      lstMstStoreInfoMst0103d01.add(error);
    }
    // 排他処理 検索時にシステム日付を取得し、画面に保持する。初期表示で検索する場合は、初期処理で実装する。
    mst0103d01Service.setHaitaDate(formMst0103d01);
    MstStoreInfoMst0103d01 addHaita = new MstStoreInfoMst0103d01();
    addHaita.setupdymd(formMst0103d01.getHaitaDate());
    addHaita.setType("haita");
    addHaita.setupdtime(formMst0103d01.getHaitaTime());
    if (lstMstStoreInfoMst0103d01 != null) {
      lstMstStoreInfoMst0103d01.add(addHaita);
    }
    jsonData = mst0103d01Service.convertJson(lstMstStoreInfoMst0103d01);
    return jsonData;
  }
  
  /**
   * CSVボタンが押下された場合の処理
   * 
   * @param formMst0103d01 form
   * @param request くクライアントから値を取得する。
   * @return ページ表示（String（ＪＳＯＮ形））
   * @throws Exception エラー画面
   */

  @RequestMapping(value = "/exportCsv", method = RequestMethod.POST,
      consumes = "application/x-www-form-urlencoded; charset=utf-8", 
      produces = "application/x-www-form-urlencoded; charset=utf-8")
  @ResponseBody public  String exportCsv(FormMst0103d01 formMst0103d01,
      HttpServletRequest request) throws Exception {
    String jsonData = "";
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();
    // データ呼出条件マスタ存在チェック
    // 得意先マスタ検索
    String customerCodeWk = formMst0103d01.getTxtCustomerCode();
    MstCustomer customerInfo;
    boolean relationMasterErrorFlag = false;
    customerInfo = null;
    CustomerData customerData = null;
    CommonGetData commonGetData = new CommonGetData(commonDao,
        readPropertiesFileService);
    ArrayList<MstStoreInfoMst0103d01> lstMstStoreInfoMst0103d01 = null;
    lstMstStoreInfoMst0103d01 = new ArrayList<MstStoreInfoMst0103d01>();
    // 入力文字数が７文字に満たない場合、0編集を行う。
    if (!customerCodeWk.equalsIgnoreCase("")) {
      if (customerCodeWk.length() < 7) {
        customerCodeWk = Util.addLeadingZeros(customerCodeWk, 7);
      }
      // 共通部品を使って、得意先情報を取得する
      customerData = commonGetData.getCustomerData(customerCodeWk, null, Integer
          .parseInt(MstConst.CUSTOMERS));
      // 結果判定
      if (customerData.getMsgCd() == null) {
        customerInfo = customerData.getCst();
      } else {
        MstStoreInfoMst0103d01 error;
        error = new MstStoreInfoMst0103d01();
        paramMess = new ArrayList<String>();
        paramMess.add("得意先マスタ");
        paramMess.add("入力された得意先");
        error.setMessage(readPropertiesFileService.getMessage("COM009-E",
            paramMess));
        error.setIdClear("customerName");
        error.setType("msgEr");
        lstMstStoreInfoMst0103d01.add(error);
        relationMasterErrorFlag = true;
      }
    }
    // 店舗マスタ検索
    String storeCode = formMst0103d01.getTxtStoreCode();
    ShopData shopData = null;
    MstShop shopInfo = null;
    if (!customerCodeWk.equals("") && !storeCode.equals("")) {
      // 共通部品を使って、店舗情報を取得する
      shopData = commonGetData.getShopData(storeCode, customerCodeWk);
      // 結果判定
      if (shopData.getMsgCd() == null) {
        shopInfo = shopData.getShp();
      } else {
        MstStoreInfoMst0103d01 error;
        error = new MstStoreInfoMst0103d01();
        paramMess = new ArrayList<String>();
        paramMess.add("店舗マスタ");
        paramMess.add("入力された店舗");
        error.setMessage(readPropertiesFileService.getMessage("COM009-E",
            paramMess));
        error.setType("msgEr");
        error.setIdClear("storeName");
        lstMstStoreInfoMst0103d01.add(error);
        relationMasterErrorFlag = true;
      }
    }
    // 処理継続判定
    if (relationMasterErrorFlag) {
      if (customerInfo != null) {
        MstStoreInfoMst0103d01 error = new MstStoreInfoMst0103d01();
        error.setType("customerInfo");
        error.setCustNmR(customerInfo.getCustCode());
        error.setMessage(customerInfo.getCustNmR());
        lstMstStoreInfoMst0103d01.add(error);
      }
      if (shopInfo != null) {
        MstStoreInfoMst0103d01 error = new MstStoreInfoMst0103d01();
        error.setType("shopInfo");
        error.setShopCode(shopInfo.getShopCode());
        error.setShopNm(shopInfo.getShopNmR());
        lstMstStoreInfoMst0103d01.add(error);
      }
      jsonData = mst0103d01Service.convertJson(lstMstStoreInfoMst0103d01);
      return jsonData;
    }
    String path = mst0103d01Service.exportCsvData(formMst0103d01);
    if (path == null) {
      // データがない場合
      MstStoreInfoMst0103d01 error = new MstStoreInfoMst0103d01();
      error.setType("body");
      error.setMessage(readPropertiesFileService.getMessage("COM025-E",
          null));
      lstMstStoreInfoMst0103d01.add(error);
    } else {
      MstStoreInfoMst0103d01 error = new MstStoreInfoMst0103d01();
      error.setAdr1(path);
      lstMstStoreInfoMst0103d01.add(error);
    }
    if (customerInfo != null) {
      MstStoreInfoMst0103d01 error = new MstStoreInfoMst0103d01();
      error.setType("customerInfo");
      error.setCustNmR(customerInfo.getCustCode());
      error.setMessage(customerInfo.getCustNmR());
      lstMstStoreInfoMst0103d01.add(error);
    }
    if (shopInfo != null) {
      MstStoreInfoMst0103d01 error = new MstStoreInfoMst0103d01();
      error.setType("shopInfo");
      error.setShopCode(shopInfo.getShopCode());
      error.setShopNm(shopInfo.getShopNmR());
      lstMstStoreInfoMst0103d01.add(error);
    }
    jsonData = mst0103d01Service.convertJson(lstMstStoreInfoMst0103d01);
    return jsonData;
  }
}
