/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl
 * ファイル名:Mst0101d02Controller.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/08/24
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/08/24 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.controller.AbsController;
import jp.co.daitoku_sh.dfcm.common.db.model.MstUser;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0101d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.SearchCondMst0101d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl.Mst0101d02Service;

@Controller
@RequestMapping(value = "/mst/MST01-01D02/")
public class Mst0101d02Controller extends AbsController {

  @Autowired
  @Qualifier("mst0101d02Service")
  private Mst0101d02Service mst0101d02Service;

  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  /** ログ出力用. */
  private Dfcmlogger logger = new Dfcmlogger();

  
  /**
   * 初期表示処理.
   * @param httpRequest:httpリクエスト
   * @param model:モデル
   * @param formMst0101d02:フォーム
   * @param searchCondMst0101d01：一覧画面の情報
   * @param userCode:担当者コード
   * @param viewMode:モード
   * @param haitaDate:排他日付
   * @param haitaTime:排他時間
   * @return　ページ表示
   */
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String top(HttpServletRequest httpRequest, Model model, FormMst0101d02 formMst0101d02,
      @ModelAttribute("searchCondMst0101d01") SearchCondMst0101d01 searchCondMst0101d01,
      @ModelAttribute("userCode") String userCode,
      @ModelAttribute("viewMode") String viewMode,
      @ModelAttribute("haitaDate") String haitaDate,
      @ModelAttribute("haitaTime") String haitaTime) {

    // セッション情報の取得に失敗した場合、ログイン画面（DFCM-COM01-01D01）へ遷移する
    String path = Util.checkSession(model, httpRequest,
        CommonConst.MST0101D00_SCREEN_NAME);
    if (!path.equalsIgnoreCase("")) {
      return path;
    }

    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();

    ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    ErrorMessages errMess = new ErrorMessages();

    // 画面マッピング
    String strScreen = "mst/mst0101d02";

    // default メッセージを取得する。
    mst0101d02Service.getDefaultMess(model);

    // 排他設定
    formMst0101d02.setHaitaDate(haitaDate);
    formMst0101d02.setHaitaTime(haitaTime);

    // 初期処理 一覧画面から値を取得する。
    mst0101d02Service.init(formMst0101d02, searchCondMst0101d01, viewMode);

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
      // 共通部品【システム共通.エラー時のコントロール制御】
      formMst0101d02.setErrorControl("errorControl");
      return strScreen;
    }
    // モード設定
    model.addAttribute("modeView", formMst0101d02.getMode());

    // [入力]画面表示モード = '2'（照会） or '3'（訂正） or '4'（取消） の時のみ、後続の引数チェックを行う。
    if (viewMode.equalsIgnoreCase(MstConst.SHOUKAI_MODE) || viewMode
        .equalsIgnoreCase(MstConst.TEISEI_MODE) || viewMode.equalsIgnoreCase(
            MstConst.TORIKESI_MODE)) {
      if (userCode.equalsIgnoreCase("")) {
        // [変数]メッセージコード（COM026-E）
        lstErrMess = new ArrayList<ErrorMessages>();
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("担当者コード");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM026-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        formMst0101d02.setErrorControl("errorControl");
        return strScreen;
      }
    }

    // -----------------------------
    // 共通部品を使って、業務日付を取得する
    // -----------------------------
    String strBussinessDate;

    strBussinessDate = mst0101d02Service.getBusinessDate();

    if (strBussinessDate == null || strBussinessDate.equalsIgnoreCase("")) {
      // 6-3 エラー処理へ
      // 共通部品を使って、エラー時の画面制御を行う
      // [変数]メッセージコード（COM015-E）
      lstErrMess = new ArrayList<ErrorMessages>();
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("業務日付の取得");
      errMess.setMessageContent(readPropertiesFileService.getMessage("COM015-E",
          paramMess));
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      // 共通部品【システム共通.エラー時のコントロール制御】
      formMst0101d02.setErrorControl("errorControl");
      return strScreen;
    } else {
      // hidden設定
      formMst0101d02.setBusinessDate(Integer.parseInt(strBussinessDate));
    }

    // 関数を呼び出し、[入力]担当者コードにて担当者情報を取得する
    // （1）画面表示モード ＝ 1 の場合、担当者情報の取得は行わない
    // （2）画面表示モード ≠ 1 の場合、担当者情報を取得する
    MstUser mstUserForm1 = new MstUser();
    if (!viewMode.equalsIgnoreCase(MstConst.SHINKI_MODE)) {
      // [変数]担当者情報格納クラス ＝ [関数]担当者マスタデータ取得（[入力]担当者コード）

      mstUserForm1 = mst0101d02Service.getUserInfo(userCode);
      if (mstUserForm1 == null) {
        // [変数]メッセージコード（COM009-E）
        lstErrMess = new ArrayList<ErrorMessages>();
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("担当者マスタ");
        paramMess.add("担当者マスタ一覧画面で指定された担当者");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        // 共通部品【システム共通.エラー時のコントロール制御】
        formMst0101d02.setErrorControl("errorControl");
        return strScreen;
      }
    }

    // 共通部品を使って、利用権限名称を取得する
    if (!mst0101d02Service.setRiyoKengen_Dll(model)) {
      // [変数]メッセージコード（COM009-E）
      lstErrMess = new ArrayList<ErrorMessages>();
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("汎用マスタ");
      paramMess.add("利用権限");
      errMess.setMessageContent(readPropertiesFileService.getMessage("COM009-E",
          paramMess));
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      // 共通部品【システム共通.エラー時のコントロール制御】
      formMst0101d02.setErrorControl("errorControl");
      return strScreen;
    }

    // セッションのデータを取得する
    Map<String, Object> sessionData = Util.getContentsFromSession(httpRequest);
    // システム管理者フラグ値を取得する
    formMst0101d02.setSysAdminFlag(String.valueOf(sessionData.get(
        "SysAdminFlg")));

    // ログイン事業所コード値を取得する
    formMst0101d02.setLoginJigyouShoCode(String.valueOf(sessionData.get(
        "JigyoshoCode")));

    // 共通部品 事業所マスタから事業所情報を取得する
    if (!mst0101d02Service.setJigyo_Dll(formMst0101d02, model)) {
      // [変数]メッセージコード（COM009-E）
      lstErrMess = new ArrayList<ErrorMessages>();
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("事業所マスタの取得");
      errMess.setMessageContent(readPropertiesFileService.getMessage("COM015-E",
          paramMess));
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      // 共通部品【システム共通.エラー時のコントロール制御】
      formMst0101d02.setErrorControl("errorControl");
      return strScreen;
    }

    // [入力]画面表示モードにより、画面表示を制御する
    mst0101d02Service.setView(model, viewMode, formMst0101d02, mstUserForm1);

    return strScreen;
  }

  /**
   * 登録ボタンを押した処理.
   * @param formMst0101d02:フォーム
   * @param model:モデル
   * @param httpRequest：httpリクエスト
   * @return 画面表示
   * @throws Exception　エラー画面
   */
  @RequestMapping(value = "proc", params = "Register", method = RequestMethod.POST)
  public String register(FormMst0101d02 formMst0101d02, Model model,
      HttpServletRequest httpRequest) throws Exception {
    // 画面マッピング
    String strScreen = "mst/mst0101d02";

    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();

    ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    ErrorMessages errMess = new ErrorMessages();

    // セッション情報の取得に失敗した場合、ログイン画面（DFCM-COM01-01D01）へ遷移する
    String path = Util.checkSession(model, httpRequest,
        CommonConst.MST0101D00_SCREEN_NAME);
    if (!path.equalsIgnoreCase("")) {
      return path;
    }

    // セッションのデータを取得する
    Map<String, Object> sessionData = Util.getContentsFromSession(httpRequest);
    // システム管理者フラグ値を取得する
    String strLoginUserCode = String.valueOf(sessionData.get("UserCode"));

    // モード設定
    model.addAttribute("modeView", formMst0101d02.getMode());

    // 共通部品を使って、利用権限名称を取得する
    if (!mst0101d02Service.setRiyoKengen_Dll(model)) {
      // [変数]メッセージコード（COM009-E）
      lstErrMess = new ArrayList<ErrorMessages>();
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("汎用マスタ");
      paramMess.add("利用権限");
      errMess.setMessageContent(readPropertiesFileService.getMessage("COM009-E",
          paramMess));
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      // 共通部品【システム共通.エラー時のコントロール制御】
      formMst0101d02.setErrorControl("errorControl");
      return strScreen;
    }

    // 共通部品 事業所マスタから事業所情報を取得する
    if (!mst0101d02Service.setJigyo_Dll(formMst0101d02, model)) {
      // [変数]メッセージコード（COM009-E）
      lstErrMess = new ArrayList<ErrorMessages>();
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("事業所マスタの取得");
      errMess.setMessageContent(readPropertiesFileService.getMessage("COM015-E",
          paramMess));
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      // 共通部品【システム共通.エラー時のコントロール制御】
      formMst0101d02.setErrorControl("errorControl");
      return strScreen;
    }

    // default メッセージを取得する。
    mst0101d02Service.getDefaultMess(model);

    // 入力チェックを行う
    if (mst0101d02Service.checkInput(formMst0101d02, model)) {
      return strScreen;
    }

    String mode = formMst0101d02.getMode();
    if ((mode.equalsIgnoreCase(MstConst.TEISEI_MODE) == true)
        || (mode.equalsIgnoreCase(MstConst.TORIKESI_MODE) == true)) {
      // 排他チェックを行う。 共通仕様 9-(4) 適用
      if (mst0101d02Service.checkHaita(formMst0101d02.getTxtUserCode(),
          formMst0101d02.getHaitaDate(), formMst0101d02.getHaitaTime())) {
        lstErrMess = new ArrayList<ErrorMessages>();
        errMess = new ErrorMessages();
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM031-E", null));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        return strScreen;
      }
    }

    // 共通部品を使って、初期パスワードを取得する
    String strPassword = mst0101d02Service.getInitPassword();
    String strPasswordAfter = "";
    if (strPassword.equalsIgnoreCase("")) {
      // [変数]初期パスワード_変換前 ＝ Nullの場合、エラーとする(COM009-E)
      lstErrMess = new ArrayList<ErrorMessages>();
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("汎用マスタ");
      paramMess.add("初期パスワード");
      errMess.setMessageContent(readPropertiesFileService.getMessage(
          "COM009-E", paramMess));
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      return strScreen;
    }

    // 取得した初期パスワードをMD5変換する
    try {
      strPasswordAfter = Util.createDigest(strPassword);
    } catch (NoSuchAlgorithmException e) {
      logger.error(e.getMessage());
      throw e;
    }

    if (mode.equalsIgnoreCase(MstConst.SHINKI_MODE)) {
      // 担当者マスタデータ検索 キー項目存在チェック用
      if (mst0101d02Service.checkUserInfoRegistered(formMst0101d02
          .getTxtUserCode())) {
        // エラー COM027-E
        lstErrMess = new ArrayList<ErrorMessages>();
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("担当者");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM027-E", paramMess));
        errMess.setItemId("txtUserCode");
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        return strScreen;
      }
      // データ更新
      mst0101d02Service.insertData(formMst0101d02, strPasswordAfter,
          strLoginUserCode);
      // [変数]メッセージコード（COM002-I）
      lstErrMess = new ArrayList<ErrorMessages>();
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("担当者マスタの登録");
      errMess.setMessageContent(readPropertiesFileService.getMessage(
          "COM002-I", paramMess));
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      model.addAttribute("infoMessFlag", "true");
      return strScreen;

    } else if (mode.equalsIgnoreCase(MstConst.TEISEI_MODE)) {
      // データ更新 (修正モード)

      // 排他チェックする。
      if (mst0101d02Service.checkHaita(formMst0101d02.getTxtUserCode(),
          formMst0101d02.getHaitaDate(), formMst0101d02.getHaitaTime())) {
        lstErrMess = new ArrayList<ErrorMessages>();
        errMess = new ErrorMessages();
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM031-E", null));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        return strScreen;
      }

      mst0101d02Service.updateData(formMst0101d02, strPasswordAfter,
          strLoginUserCode);
      lstErrMess = new ArrayList<ErrorMessages>();
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("担当者マスタの登録");
      errMess.setMessageContent(readPropertiesFileService.getMessage(
          "COM002-I", paramMess));
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      model.addAttribute("infoMessFlag", "true");

      // 排他値設定
      String currentDate = DateUtil.getSysDate();
      String currentTime = DateUtil.getSysTime();
      formMst0101d02.setHaitaDate(currentDate);
      formMst0101d02.setHaitaTime(currentTime);

      model.addAttribute("errorMessages", lstErrMess);
      return strScreen;

    } else if (mode.equalsIgnoreCase(MstConst.TORIKESI_MODE)) {
      // 排他チェックする。
      if (mst0101d02Service.checkHaita(formMst0101d02.getTxtUserCode(),
          formMst0101d02.getHaitaDate(), formMst0101d02.getHaitaTime())) {
        lstErrMess = new ArrayList<ErrorMessages>();
        errMess = new ErrorMessages();
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM031-E", null));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        return strScreen;
      }
      // データ更新（削除モード）
      mst0101d02Service.deleteData(formMst0101d02, strLoginUserCode);
      formMst0101d02.setDdlShozoku(formMst0101d02.getShozokuSelect());
      formMst0101d02.setDdlRiyoKengen(formMst0101d02.getRiyoKengenSelect());
      lstErrMess = new ArrayList<ErrorMessages>();
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("担当者マスタの登録");
      errMess.setMessageContent(readPropertiesFileService.getMessage(
          "COM002-I", paramMess));
      lstErrMess.add(errMess);
      // 排他値設定
      String currentDate = DateUtil.getSysDate();
      String currentTime = DateUtil.getSysTime();
      formMst0101d02.setHaitaDate(currentDate);
      formMst0101d02.setHaitaTime(currentTime);
      model.addAttribute("errorMessages", lstErrMess);
      model.addAttribute("infoMessFlag", "true");
      return strScreen;
    }
    return strScreen;
  }
  
  /**
   * 戻るボタンを押した処理.
   * @param rattrs:送信パラメーターの設定
   * @param formMst0101d02:フォーム
   * @return 画面表示
   */
  @RequestMapping(value = "proc", params = "Return", method = RequestMethod.POST)
  public String returnPage(RedirectAttributes rattrs,
      FormMst0101d02 formMst0101d02) {
    // [変数]検索条件保持クラス
    SearchCondMst0101d01 searchCondMst0101d01 = new SearchCondMst0101d01();
    searchCondMst0101d01.setJigyoshoCode(formMst0101d02.getForm1JigyoshoCode());
    searchCondMst0101d01.setUserCode(formMst0101d02.getForm1UserCode());
    searchCondMst0101d01.setUserNm(formMst0101d02.getForm1UserNm());
    searchCondMst0101d01.setAuthCode(formMst0101d02.getForm1AuthCode());
    searchCondMst0101d01.setCancelData(formMst0101d02.getForm1CancelData());
    searchCondMst0101d01.setUserStatus(formMst0101d02.getForm1UserStatus());

    rattrs.addFlashAttribute("searchCondMst0101d01", searchCondMst0101d01);

    return "redirect:/mst/MST01-01D00/";
  }

  /**
   * クリアボタンを押した処理.
   * @param formMst0101d02:フォーム
   * @param model:モデル
   * @param httpRequest：httpリクエスト
   * @return 画面表示
   */
  @RequestMapping(value = "proc", params = "Clear", method = RequestMethod.POST)
  public String clear(FormMst0101d02 formMst0101d02, Model model,
      HttpServletRequest httpRequest) {
    String strScreen = "mst/mst0101d02";
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();

    ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    ErrorMessages errMess = new ErrorMessages();
    // 入力チェック
    model.addAttribute("modeView", formMst0101d02.getMode());
    // default メッセージを取得する。
    mst0101d02Service.getDefaultMess(model);

    // セッション情報の取得に失敗した場合、ログイン画面（DFCM-COM01-01D01）へ遷移する
    String path = Util.checkSession(model, httpRequest,
        CommonConst.MST0101D00_SCREEN_NAME);
    if (!path.equalsIgnoreCase("")) {
      return path;
    }

    // 共通部品を使って、利用権限名称を取得する
    if (!mst0101d02Service.setRiyoKengen_Dll(model)) {
      // [変数]メッセージコード（COM009-E）
      lstErrMess = new ArrayList<ErrorMessages>();
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("汎用マスタ");
      paramMess.add("利用権限");
      errMess.setMessageContent(readPropertiesFileService.getMessage("COM009-E",
          paramMess));
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      // 共通部品【システム共通.エラー時のコントロール制御】
      formMst0101d02.setErrorControl("errorControl");
      return strScreen;
    }

    // 共通部品 事業所マスタから事業所情報を取得する
    if (!mst0101d02Service.setJigyo_Dll(formMst0101d02, model)) {
      // [変数]メッセージコード（COM009-E）
      lstErrMess = new ArrayList<ErrorMessages>();
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("事業所マスタの取得");
      errMess.setMessageContent(readPropertiesFileService.getMessage("COM015-E",
          paramMess));
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      // 共通部品【システム共通.エラー時のコントロール制御】
      formMst0101d02.setErrorControl("errorControl");
      return strScreen;
    }

    formMst0101d02.setDdlRiyoKengen(formMst0101d02.getRiyoKengenSelect());
    formMst0101d02.setDdlShozoku(formMst0101d02.getShozokuSelect());
    String mode = formMst0101d02.getMode();
    if (!mst0101d02Service.setViewClear(mode, formMst0101d02)) {
      lstErrMess = new ArrayList<ErrorMessages>();
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("担当者マスタ");
      paramMess.add("指定された担当者");
      errMess.setMessageContent(readPropertiesFileService.getMessage("COM009-E",
          paramMess));
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
    }
    return strScreen;
  }
}
