/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.nyu.impl
 * ファイル名:Nyu0102d01Controller.java
 * 
 * 作成者:都築電気株式会社
 * 作成日:2015/12/02
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/02 1.00 ACT)ANZAI 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.controller.nyu.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.controller.AbsController;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.NyuConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ObjCombobox;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu.FormNyu0102d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu.ShoriKbnItem;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.nyu.impl.Nyu0102d01Service;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.sei.impl.SeikyuCommonService;

/**
 * コントローラクラス 入金登録
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/nyu/NYU01-02D00/")
public class Nyu0102d01Controller extends AbsController {

  @Autowired
  private SeikyuCommonService seikyuCommonService;

  @Autowired
  private Nyu0102d01Service nyu0102d01Service;

  @Autowired
  private CommonDao commonDao;

  @Autowired
  private ReadPropertiesFileService readPropertiesFileService;

  private Dfcmlogger logger = new Dfcmlogger();

  /**
   * 画面表示.
   * 
   * @param model モデル
   * @param request リクエスト
   * @return 遷移先
   */
  @RequestMapping(value = "/")
  public String top(Model model, HttpServletRequest request) {

    String strScreen = "/nyu/nyu0102d01";

    // ログインチェック
    String path = Util.checkSession(model, request,
        CommonConst.NYU0102D00_SCREEN_NAME);
    if (!path.equalsIgnoreCase("")) {
      return path;
    }

    // Formの生成
    FormNyu0102d01 form = new FormNyu0102d01();

    // 共通部品初期化
    CommonGetSystemCom commonGetSysCom = new CommonGetSystemCom(commonDao, null,
        null, readPropertiesFileService);

    // 業務日付の取得
    String businessDate = commonGetSysCom.getAplDate();

    if (businessDate == null || businessDate.equals("")) {
      // 業務日付取得エラー

      ArrayList<ErrorMessages> errorList = new ArrayList<ErrorMessages>();
      ErrorMessages errorMsg = new ErrorMessages();
      ArrayList<String> params = new ArrayList<String>();

      params.add("業務日付の取得");
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "COM015-E", params));
      errorList.add(errorMsg);

      model.addAttribute("errorMessages", errorList);
      form.setErrorControl("errorControl");
      // エラーレベル
      form.setMsgErrorLevel(true);

      model.addAttribute("FormNyu0102d01", form);

      return strScreen;
    }
    logger.debug("業務日付=[" + businessDate + "]");

    // セッションの取得
    Map<String, Object> ses = Util.getContentsFromSession(request);

    // ログイン事業所コードの取得
    String loginJigyoshoCd = String.valueOf(ses.get(
        CommonConst.LOGIN_USER_JIGYOSHO_CODE));

    // システム管理者フラグの取得
    String sysAdminFlag = String.valueOf(ses.get(
        CommonConst.LOGIN_USER_SYS_ADMIN_FLG));

    // DEFAULT MESSAGE の取得
    List<DefaultMessages> defList = nyu0102d01Service.getDefaultMess();
    model.addAttribute("defaultMessages", defList);

    // ---------------------------------------------------------------------
    // FORM設定
    // ---------------------------------------------------------------------

    // システム管理者フラグ
    form.setSysAdminFlag(sysAdminFlag.equals(CommonConst.SYS_ADMIN_FLG_VALID)
        ? true
        : false);
    // ログイン事業所コード
    form.setLoginJigyoshoCd(loginJigyoshoCd);
    // 業務日付
    form.setBusinessDate(businessDate);
    // 表示フラグ
    form.setShowFlag(false);
    // 処理区分
    form.setShoriKbn(NyuConst.SHORI_KBN_SHONIN);

    model.addAttribute("FormNyu0102d01", form);

    // 処理区分ラジオボタン
    List<ShoriKbnItem> shoriKbns = new ArrayList<ShoriKbnItem>();
    shoriKbns.add(new ShoriKbnItem("本社承認", NyuConst.SHORI_KBN_SHONIN));
    shoriKbns.add(new ShoriKbnItem("新規登録", NyuConst.SHORI_KBN_SHINKI));
    shoriKbns.add(new ShoriKbnItem("訂正", NyuConst.SHORI_KBN_TEISEI));
    shoriKbns.add(new ShoriKbnItem("取消", NyuConst.SHORI_KBN_TORIKESHI));
    shoriKbns.add(new ShoriKbnItem("照会", NyuConst.SHORI_KBN_SHOKAI));

    model.addAttribute("shoriKbns", shoriKbns);

    return strScreen;

  }

  /**
   * 画面表示（入金伝票Noパラメータあり）
   * 
   * @param model モデル
   * @param request リクエスト
   * @param shoriKbn 処理区分
   * @param nyukinDempyoNo 入金伝票No
   * @return
   */
  @RequestMapping(value = "/show", method = RequestMethod.GET)
  public String show(Model model, HttpServletRequest request,
      @RequestParam String shoriKbn, @RequestParam String nyukinDempyoNo) {

    String strScreen = "/nyu/nyu0102d01";

    // ログインチェック
    String path = Util.checkSession(model, request,
        CommonConst.NYU0102D00_SCREEN_NAME);
    if (!path.equalsIgnoreCase("")) {
      return path;
    }

    // Formの生成
    FormNyu0102d01 form = new FormNyu0102d01();

    // 共通部品初期化
    CommonGetSystemCom commonGetSysCom = new CommonGetSystemCom(commonDao, null,
        null, readPropertiesFileService);

    // 業務日付の取得
    String businessDate = commonGetSysCom.getAplDate();

    if (businessDate == null || businessDate.equals("")) {
      // 業務日付取得エラー

      ArrayList<ErrorMessages> errorList = new ArrayList<ErrorMessages>();
      ErrorMessages errorMsg = new ErrorMessages();
      ArrayList<String> params = new ArrayList<String>();

      params.add("業務日付の取得");
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "COM015-E", params));
      errorList.add(errorMsg);

      model.addAttribute("errorMessages", errorList);
      form.setErrorControl("errorControl");
      // エラーレベル
      form.setMsgErrorLevel(true);

      model.addAttribute("FormNyu0102d01", form);

      return strScreen;
    }
    logger.debug("業務日付=[" + businessDate + "]");

    // セッションの取得
    Map<String, Object> ses = Util.getContentsFromSession(request);

    // ログイン事業所コードの取得
    String loginJigyoshoCd = String.valueOf(ses.get(
        CommonConst.LOGIN_USER_JIGYOSHO_CODE));

    // システム管理者フラグの取得
    String sysAdminFlag = String.valueOf(ses.get(
        CommonConst.LOGIN_USER_SYS_ADMIN_FLG));

    // 権限区分の取得
    String authCode = String.valueOf(ses.get(CommonConst.LOGIN_USER_AUTH_CODE));
    String authKbn = nyu0102d01Service.getAuthKbn(form.isSysAdminFlag(),
        authCode);

    // DEFAULT MESSAGE の取得
    List<DefaultMessages> defList = nyu0102d01Service.getDefaultMess();
    model.addAttribute("defaultMessages", defList);

    // ---------------------------------------------------------------------
    // FORM設定
    // ---------------------------------------------------------------------

    // システム管理者フラグ
    form.setSysAdminFlag(sysAdminFlag.equals(CommonConst.SYS_ADMIN_FLG_VALID)
        ? true
        : false);
    // 権限区分
    form.setAuthKbn(authKbn);
    // ログイン事業所コード
    form.setLoginJigyoshoCd(loginJigyoshoCd);
    // 業務日付
    form.setBusinessDate(businessDate);
    // TODO:排他日付
    
    // TODO:排他時刻
    
    // 表示フラグ
    form.setShowFlag(false);
    // 処理区分
    form.setShoriKbn(shoriKbn);
    
    // 入金伝票No
    form.setNyukinDempyoNo(nyukinDempyoNo);

    model.addAttribute("FormNyu0102d01", form);

    // 処理区分ラジオボタン
    List<ShoriKbnItem> shoriKbns = new ArrayList<ShoriKbnItem>();
    shoriKbns.add(new ShoriKbnItem("本社承認", NyuConst.SHORI_KBN_SHONIN));
    shoriKbns.add(new ShoriKbnItem("新規登録", NyuConst.SHORI_KBN_SHINKI));
    shoriKbns.add(new ShoriKbnItem("訂正", NyuConst.SHORI_KBN_TEISEI));
    shoriKbns.add(new ShoriKbnItem("取消", NyuConst.SHORI_KBN_TORIKESHI));
    shoriKbns.add(new ShoriKbnItem("照会", NyuConst.SHORI_KBN_SHOKAI));

    model.addAttribute("shoriKbns", shoriKbns);

    return strScreen;

  }

  /**
   * フォーム.サブミット.
   * 
   * @param model モデル
   * @param request リクエスト
   * @param form フォーム
   * @return 遷移先
   */
  @RequestMapping(value = "/proc", method = RequestMethod.POST)
  public String proc(Model model, HttpServletRequest request,
      @ModelAttribute("FormNyu0102d01") FormNyu0102d01 form) {

    String strScreen = "/nyu/nyu0102d01";

    // ログインチェック
    String path = Util.checkSession(model, request,
        CommonConst.NYU0102D00_SCREEN_NAME);

    if (!path.equalsIgnoreCase("")) {
      return path;
    }

    Map<String, Object> ses = Util.getContentsFromSession(request);
    String userId = String.valueOf(ses.get(CommonConst.LOGIN_USER_CODE));

    ArrayList<ErrorMessages> errorList = new ArrayList<ErrorMessages>();
    String btnName = form.getBtnName();

    // 処理振り分け
    if (btnName.equals(NyuConst.BUTTON_NAME_PROC)) {
      // 登録ボタン
      execute(model, form, errorList, userId);
    } else if (btnName.equals(NyuConst.BUTTON_NAME_PRINT)) {
      // 入金内訳書ボタン
      print(model, form, errorList);
    } else if (btnName.equals(NyuConst.BUTTON_NAME_SHONIN)) {
      // 承認ボタン
      shonin(model, form, errorList, userId);
    } else if (btnName.equals(NyuConst.BUTTON_NAME_SHONIN)) {
      // 否認ボタン
      hinin(model, form, errorList, userId);
    } else {
      // クリアボタン
      clear(model, form);
    }

    // エラーメッセージ
    if (errorList.size() > 0) {
      model.addAttribute("errorMessages", errorList);
    }

    // DEFAULT MESSAGE の取得
    List<DefaultMessages> defList = nyu0102d01Service.getDefaultMess();
    model.addAttribute("defaultMessages", defList);

    // 処理区分ラジオボタン
    List<ShoriKbnItem> shoriKbns = new ArrayList<ShoriKbnItem>();
    shoriKbns.add(new ShoriKbnItem("本社承認", NyuConst.SHORI_KBN_SHONIN));
    shoriKbns.add(new ShoriKbnItem("新規登録", NyuConst.SHORI_KBN_SHINKI));
    shoriKbns.add(new ShoriKbnItem("訂正", NyuConst.SHORI_KBN_TEISEI));
    shoriKbns.add(new ShoriKbnItem("取消", NyuConst.SHORI_KBN_TORIKESHI));
    shoriKbns.add(new ShoriKbnItem("照会", NyuConst.SHORI_KBN_SHOKAI));

    model.addAttribute("shoriKbns", shoriKbns);

    return strScreen;
  }

  // ---------------------------------------------------------------------------
  // PRIVATE METHOD
  // ---------------------------------------------------------------------------

  private void execute(Model model, FormNyu0102d01 form,
      ArrayList<ErrorMessages> errorList, String userId) {

  }

  private void print(Model model, FormNyu0102d01 form,
      ArrayList<ErrorMessages> errorList) {

  }

  private void shonin(Model model, FormNyu0102d01 form,
      ArrayList<ErrorMessages> errorList, String userId) {

  }

  private void hinin(Model model, FormNyu0102d01 form,
      ArrayList<ErrorMessages> errorList, String userId) {

  }

  private void clear(Model model, FormNyu0102d01 form) {
    
    FormNyu0102d01 newForm = new FormNyu0102d01();
    
    // システム管理者フラグ
    newForm.setSysAdminFlag(form.isSysAdminFlag());
    // ログイン事業所コード
    newForm.setLoginJigyoshoCd(form.getLoginJigyoshoCd());
    // 業務日付
    newForm.setBusinessDate(form.getBusinessDate());
    // 表示フラグ
    newForm.setShowFlag(false);
    // 処理区分
    newForm.setShoriKbn(NyuConst.SHORI_KBN_SHONIN);

    model.addAttribute("FormNyu0102d01", form);

  }

}
