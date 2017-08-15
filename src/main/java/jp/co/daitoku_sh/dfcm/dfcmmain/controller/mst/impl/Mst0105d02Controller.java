/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl
 * ファイル名:Mst0105d02Controller.java
 * 
 * <p>作成者:QuanTran
 * 作成日:2015/10/01
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/01 1.00 ABV)QuanTran 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.component.MstGeneralData;
import jp.co.daitoku_sh.dfcm.common.controller.AbsController;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.CastData0105d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ObjCombobox;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0105d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.SearchCondMst0105d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl.Mst0105d02Service;

/**
 * The controller layer use services to load objects
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/mst/MST01-05D02/")
public class Mst0105d02Controller extends AbsController {

  // Serviceの定義
  @Autowired
  @Qualifier("mst0105d02Service")
  private Mst0105d02Service mst0105d02Service;

  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  /**
   * 初期表示処理.
   * 
   * @param model A holder for model attributes
   * @param formMst0105d02 The controller object
   * @param request Provide request information for HTTP servlets
   * @param searchCondMst0105d01 一覧データ呼出条件格納クラス
   * @param screenMode 画面表示モード
   * @param atTorihikiCode 相手取引先コード
   * @param olCenterCode オンラインセンターコード
   * @param olTorihikiCode オンライン取引先コード
   * @param haitaDate The date that retrieve data from database of the previous screen
   * @param haitaTime The time that retrieve data from database of the previous screen
   * @return オンライン得意先変換マスタ登録画面
   */
  @RequestMapping(value = "/")
  public String top(Model model, FormMst0105d02 formMst0105d02,
      HttpServletRequest request,
      @ModelAttribute("searchCondMst0105d01") SearchCondMst0105d01 searchCondMst0105d01,
      @ModelAttribute("screenMode") String screenMode,
      @ModelAttribute("atTorihikiCode") String atTorihikiCode,
      @ModelAttribute("olCenterCode") String olCenterCode,
      @ModelAttribute("olTorihikiCode") String olTorihikiCode,
      @ModelAttribute("haitaDate") String haitaDate,
      @ModelAttribute("haitaTime") String haitaTime) {
    // セッション情報の取得に失敗した場合、ログイン画面（DFCM-COM01-01D01）へ遷移する
    String path = Util.checkSession(model, request,
        CommonConst.MST0105D00_SCREEN_NAME);
    if (!path.equalsIgnoreCase("")) {
      return path;
    }
    Map<String, Object> sessionData = Util.getContentsFromSession(request);
    String sysAdminFlag = String.valueOf(sessionData.get(CommonConst.LOGIN_USER_SYS_ADMIN_FLG));
    formMst0105d02.setSysAdminFlag(sysAdminFlag);
    String jigyoshoCode = String.valueOf(sessionData.get(CommonConst.LOGIN_USER_JIGYOSHO_CODE));
    formMst0105d02.setPlantCode(jigyoshoCode);
    mst0105d02Service.getDefaultMess(model);
    ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    ErrorMessages errMess = null;
    ArrayList<String> paramMess = null;
    String errMsg = "";
    String screenUrl = "mst/mst0105d02";
    // 引数チェック
    if (null == screenMode || screenMode.equalsIgnoreCase(CommonConst.EMPTY)) {
      paramMess = new ArrayList<String>();
      paramMess.add("画面表示モード");
      errMsg = readPropertiesFileService.getMessage("COM026-E", paramMess);
      errMess = new ErrorMessages();
      errMess.setMessageContent(errMsg);
      lstErrMess.add(errMess);
    } else if (screenMode.equalsIgnoreCase(MstConst.SHOUKAI_MODE)
        || screenMode.equalsIgnoreCase(MstConst.TEISEI_MODE)
        || screenMode.equalsIgnoreCase(MstConst.TORIKESI_MODE)) {
      if (olCenterCode == null || olCenterCode.equalsIgnoreCase(
          CommonConst.EMPTY)) {
        paramMess = new ArrayList<String>();
        paramMess.add("オンラインセンターコード");
        errMsg = readPropertiesFileService.getMessage("COM026-E", paramMess);
        errMess = new ErrorMessages();
        errMess.setMessageContent(errMsg);
        lstErrMess.add(errMess);
      }

      if (atTorihikiCode == null || atTorihikiCode.equalsIgnoreCase(
          CommonConst.EMPTY)) {
        paramMess = new ArrayList<String>();
        paramMess.add("相手取引先コード");
        errMsg = readPropertiesFileService.getMessage("COM026-E", paramMess);
        errMess = new ErrorMessages();
        errMess.setMessageContent(errMsg);
        lstErrMess.add(errMess);
      }
    }
    // 処理継続判定
    if (lstErrMess.size() > 0) {
      model.addAttribute("screenMode", null);
      formMst0105d02.setErrorControl(true);
      model.addAttribute("errorMessages", lstErrMess);
      return screenUrl;
    }
    // 設定ファイル情報を取得する
    String tenCodeNone = readPropertiesFileService.getSetting("TEN_CODE_NONE");
    String olTorihikiCodeNone = readPropertiesFileService.getSetting(
        "OL_TORIHIKI_CODE_NONE");
    formMst0105d02.setTenCodeNone(tenCodeNone);
    formMst0105d02.setOlTorihikiCodeNone(olTorihikiCodeNone);

    // 業務日付取得
    String businessDate = mst0105d02Service.getBusinessDate();
    // 結果判定
    if (null != businessDate) {
      formMst0105d02.setBusinessDate(businessDate);
    } else {
      paramMess = new ArrayList<String>();
      paramMess.add("業務日付の取得");
      errMsg = readPropertiesFileService.getMessage("COM015-E", paramMess);
      errMess = new ErrorMessages();
      errMess.setMessageContent(errMsg);
      lstErrMess.add(errMess);
      model.addAttribute("screenMode", null);
      formMst0105d02.setErrorControl(true);
      model.addAttribute("errorMessages", lstErrMess);
      return screenUrl;
    }
    
    // 納品区分選択肢セット
    CommonGetSystemCom commonGetSystemCom = new CommonGetSystemCom(
        mst0105d02Service.getCommonDao(),
        null, null, readPropertiesFileService);
    List<MstGeneralData> generalDatas = commonGetSystemCom.getMstGeneralConf("Deli_Kb", null);
    
    // 結果判定
    if (generalDatas == null) {
      paramMess = new ArrayList<String>();
      paramMess.add("汎用マスタ");
      paramMess.add("納品区分");
      errMsg = readPropertiesFileService.getMessage("COM009-E", paramMess);
      errMess = new ErrorMessages();
      errMess.setMessageContent(errMsg);
      lstErrMess.add(errMess);
      model.addAttribute("screenMode", null);
      // （共通仕様 3-6適用）
      formMst0105d02.setErrorControl(true);
      
      model.addAttribute("errorMessages", lstErrMess);
      // 処理を終了する。
      return screenUrl;
    } else {
      ArrayList<ObjCombobox> deliKbList = mst0105d02Service.getDeliKbList(generalDatas);
      model.addAttribute("deliKbList", deliKbList);
    }
    
    formMst0105d02.setScreenMode(screenMode);
    formMst0105d02.setHaitaDate(haitaDate);
    formMst0105d02.setHaitaTime(haitaTime);

    formMst0105d02.setPreAtTorihikiCode(searchCondMst0105d01.getTxtAtTorihikiCode());
    formMst0105d02.setPreOnlineCenterCode(searchCondMst0105d01.getTxtOnlineCenterCode());
    formMst0105d02.setPreOnlineTorihikiCode(searchCondMst0105d01.getTxtOnlineTorihikiCode());
    formMst0105d02.setChkCancellationData(searchCondMst0105d01.isChkCancellationData());
    
    // オンライン得意先変換マスタデータ取得
    String errorMsg = mst0105d02Service.getOlCustConvData(formMst0105d02,
        screenMode, atTorihikiCode, olCenterCode, olTorihikiCode, businessDate,
        olTorihikiCodeNone);
    
    // 結果判定
    if (errorMsg != null) {
      model.addAttribute("screenMode", null);
      formMst0105d02.setErrorControl(true);
      formMst0105d02.setErrMessage(errorMsg);
      return screenUrl;
    }
    model.addAttribute("screenMode", screenMode);
    if (screenMode.equalsIgnoreCase(MstConst.SHOUKAI_MODE)) {
      // ヘッダ部
      String lblCustomerNameR01 = mst0105d02Service.getOlCenterName(
          olCenterCode,businessDate,null);
      formMst0105d02.setTxtOnlineCenterCode(olCenterCode);
      if (lblCustomerNameR01 != null) {
        formMst0105d02.setLblCustomerNameR01(lblCustomerNameR01);
      }
      formMst0105d02.setTxtOnlineTorihikiCode(olTorihikiCode);
      formMst0105d02.setTxtAtTorihikiCode(atTorihikiCode);
      String lblCustomerNameR02 = mst0105d02Service.getOppCustName(
          atTorihikiCode,
          olCenterCode, olTorihikiCodeNone, businessDate, false);
      formMst0105d02.setLblCustomerNameR02(lblCustomerNameR02);
      // 編集エリア
    } else if (screenMode.equalsIgnoreCase(MstConst.TEISEI_MODE)) {
      // ヘッダ部
      String lblCustomerNameR01 = mst0105d02Service.getOlCenterName(
          olCenterCode,businessDate,null);
      formMst0105d02.setTxtOnlineCenterCode(olCenterCode);
      if (lblCustomerNameR01 != null) {
        formMst0105d02.setLblCustomerNameR01(lblCustomerNameR01);
      }
      formMst0105d02.setTxtOnlineTorihikiCode(olTorihikiCode);
      formMst0105d02.setTxtAtTorihikiCode(atTorihikiCode);
      String lblCustomerNameR02 = mst0105d02Service.getOppCustName(
          atTorihikiCode,
          olCenterCode, olTorihikiCodeNone, businessDate, false);
      formMst0105d02.setLblCustomerNameR02(lblCustomerNameR02);

      // 編集エリア
      formMst0105d02.setTxtAtTorihikiCode02(atTorihikiCode);
      formMst0105d02.setTxtStsCode(MstConst.TOROKU);
      model.addAttribute("editMode", MstConst.ADD_MODE);
      formMst0105d02.setEditMode(MstConst.ADD_MODE);
    } else if (screenMode.equalsIgnoreCase(MstConst.TORIKESI_MODE)) {
      // ヘッダ部
      String lblCustomerNameR01 = mst0105d02Service.getOlCenterName(
          olCenterCode,businessDate,null);
      formMst0105d02.setTxtOnlineCenterCode(olCenterCode);
      if (lblCustomerNameR01 != null) {
        formMst0105d02.setLblCustomerNameR01(lblCustomerNameR01);
      }
      formMst0105d02.setTxtOnlineTorihikiCode(olTorihikiCode);
      formMst0105d02.setTxtAtTorihikiCode(atTorihikiCode);
      String lblCustomerNameR02 = mst0105d02Service.getOppCustName(
          atTorihikiCode,olCenterCode, olTorihikiCodeNone, businessDate, false);
      formMst0105d02.setLblCustomerNameR02(lblCustomerNameR02);
      formMst0105d02.setTxtStsCode(MstConst.TORIKESHI);
    }

    // ビュー画面を呼ぶ
    return screenUrl;
  }
  
   
  /**
   * 戻るボタン.
   * 
   * @param formMst0105d02 The controller object
   * @param rattrs Use to select attributes for a redirect scenario
   * @return 画面表示
   */
  @RequestMapping(value = "proc", method = RequestMethod.POST, params = "eventBack")
  public String eventBack(FormMst0105d02 formMst0105d02, RedirectAttributes rattrs) {
    SearchCondMst0105d01 searchCondMst0105d01 = new SearchCondMst0105d01();
    searchCondMst0105d01.setChkCancellationData(formMst0105d02.isChkCancellationData());
    searchCondMst0105d01.setTxtAtTorihikiCode(formMst0105d02.getPreAtTorihikiCode());
    searchCondMst0105d01.setTxtOnlineCenterCode(formMst0105d02.getPreOnlineCenterCode());
    searchCondMst0105d01.setTxtOnlineTorihikiCode(formMst0105d02.getPreOnlineTorihikiCode());
    rattrs.addFlashAttribute("searchCondMst0105d01", searchCondMst0105d01);
    return "redirect:/mst/MST01-05D00/";
  }
  
  /**
   * 検索ボタン.
   * 
   * @param formMst0105d02 The controller object
   * @return JSON data
   */
  @RequestMapping(value = "/search", method = RequestMethod.POST)
  @ResponseBody
  public FormMst0105d02 search(@Valid FormMst0105d02 formMst0105d02) {
    String screenMode = formMst0105d02.getScreenMode();
    String atTorihikiCode = formMst0105d02.getTxtAtTorihikiCode();
    String olCenterCode = formMst0105d02.getTxtOnlineCenterCode();
    String olTorihikiCode = formMst0105d02.getTxtOnlineTorihikiCode();
    String businessDate = formMst0105d02.getBusinessDate();
    String olTorihikiCodeNone = formMst0105d02.getOlTorihikiCodeNone();
    String errorMsg =  mst0105d02Service.getOlCustConvData(formMst0105d02,
        screenMode, atTorihikiCode, olCenterCode, olTorihikiCode, businessDate,
        olTorihikiCodeNone);
    // 結果判定
    if (errorMsg != null) {
      formMst0105d02.setErrMessage(errorMsg);
      formMst0105d02.setErrorControl(true);
    }
    
    return formMst0105d02;
  }
  
  /**
   * オンライン得意先変換マスタデータ取得.
   * 
   * @param formMst0105d02 The controller object
   * @param screenMode 画面表示モード
   * @param atTorihikiCode 相手取引先コード
   * @param olCenterCode オンラインセンターコード
   * @param olTorihikiCode オンライン取引先コード
   * @param businessDate 業務日付
   * @param olTorihikiCodeNone オンライン取引先コード_未指定
   * @return JSON data
   */
  @RequestMapping(value = "/getOlCustConvData", method = RequestMethod.POST)
  @ResponseBody
  public FormMst0105d02 getOlCustConvData(@Valid FormMst0105d02 formMst0105d02,
      @RequestParam(value = "screenMode") String screenMode,
      @RequestParam(value = "atTorihikiCode") String atTorihikiCode,
      @RequestParam(value = "olCenterCode") String olCenterCode,
      @RequestParam(value = "olTorihikiCode") String olTorihikiCode,
      @RequestParam(value = "businessDate") String businessDate,
      @RequestParam(value = "olTorihikiCodeNone") String olTorihikiCodeNone) {
    String errorMsg =  mst0105d02Service.getOlCustConvData(formMst0105d02,
        screenMode, atTorihikiCode, olCenterCode, olTorihikiCode, businessDate,
        olTorihikiCodeNone);
    // 結果判定
    if (errorMsg != null) {
      formMst0105d02.setErrMessage(errorMsg);
      formMst0105d02.setErrorControl(true);
    }
    
    return formMst0105d02;
  }
  /**
   * 設定ボタン.
   * 
   * @param formMst0105d02 The controller object
   * @return JSON data
   */
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  @ResponseBody
  public FormMst0105d02 create(@Valid FormMst0105d02 formMst0105d02) {
    mst0105d02Service.create(formMst0105d02);
    return formMst0105d02;
  }
  
  /**
   * 得意先マスタ検索.
   * 
   * @param formMst0105d02 The controller object.
   * @param custCode 得意先コード
   * @param jigyoCode 受注事業所コード
   * @param shopCode 店舗コード
   * @return formMst0105d02 The controller object.
   */
  @RequestMapping(value = "/getCustomerData", method = RequestMethod.POST)
  @ResponseBody
  public FormMst0105d02 getCustomerData(@Valid FormMst0105d02 formMst0105d02,
      @RequestParam(value = "custCode") String custCode,
      @RequestParam(value = "jigyoCode") String jigyoCode,
      @RequestParam(value = "shopCode") String shopCode) {
    mst0105d02Service.searchMstCust(formMst0105d02, custCode, jigyoCode, shopCode);
    return formMst0105d02;
  }
  
  /**
   * （登録画面）登録ボタン_Click.
   * .
   * @param castData0105d02 An object is used to receive data in same ajax request
   * @param request Provide request information for HTTP servlets
   * @return An object is used to send data to the client
   * @throws Exception contains information about the error
   */
  @RequestMapping(value = "/register", method = RequestMethod.POST, 
      consumes = MediaType.APPLICATION_JSON_VALUE, 
      produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
  @ResponseBody
  public CastData0105d02 register( @RequestBody CastData0105d02 castData0105d02,
      HttpServletRequest request) throws Exception {
    HttpSession session = request.getSession(false);
    String userCode = String.valueOf(session.getAttribute(CommonConst.LOGIN_USER_CODE));
    mst0105d02Service.register(castData0105d02, userCode);
    return castData0105d02;
  }
}
