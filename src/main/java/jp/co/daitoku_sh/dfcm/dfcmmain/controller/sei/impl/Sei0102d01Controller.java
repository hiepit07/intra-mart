/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.sei.impl
 * ファイル名:Sei0102d01Controller.java
 * 
 * 作成者:都築電気株式会社
 * 作成日:2015/10/28
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/28 1.00 ACT)ANZAI 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.controller.sei.impl;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.controller.AbsController;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.SeiConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ObjCombobox;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.sei.FormSei0102d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.sei.SeikyusakiInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.sei.impl.Sei0102d01Service;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.sei.impl.SeikyuCommonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * コントローラクラス 請求締め処理
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/sei/SEI01-02D00/")
public class Sei0102d01Controller extends AbsController {

  @Autowired
  private SeikyuCommonService seikyuCommonService;

  @Autowired
  private Sei0102d01Service sei0102d01Service;

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

    String strScreen = "/sei/sei0102d01";

    // ログインチェック
    String path = Util.checkSession(model, request,
        CommonConst.SEI0102D00_SCREEN_NAME);
    if (!path.equalsIgnoreCase("")) {
      return path;
    }

    // Formの生成
    FormSei0102d01 form = new FormSei0102d01();

    // 共通部品初期化
    CommonGetSystemCom commonGetSysCom = new CommonGetSystemCom(commonDao, null,
        null, readPropertiesFileService);

    // 最大件数の取得
    int searchMax = commonGetSysCom.getCodeSearchMax();
    logger.debug("検索上限値=[" + searchMax + "]");

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
      
      model.addAttribute("FormSei0102d01", form);

      return strScreen;
    }
    logger.debug("業務日付=[" + businessDate + "]");

    // セッションの取得
    Map<String, Object> ses = Util.getContentsFromSession(request);

    // ログイン事業所コードの取得
    String loginJigyoshoCd = String.valueOf(ses.get(
        CommonConst.LOGIN_USER_JIGYOSHO_CODE));

    // 会計月の取得
    String accountMonth = seikyuCommonService.getAccountMonth(loginJigyoshoCd);

    if (accountMonth == null || accountMonth.equals("")) {
      // 会計月取得エラー

      ArrayList<ErrorMessages> errorList = new ArrayList<ErrorMessages>();
      ErrorMessages errorMsg = new ErrorMessages();
      ArrayList<String> params = new ArrayList<String>();

      params.add("当月売掛月度の取得");
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "COM015-E", params));
      errorList.add(errorMsg);

      model.addAttribute("errorMessages", errorList);
      form.setErrorControl("errorControl");
      // エラーレベル
      form.setMsgErrorLevel(true);

      model.addAttribute("FormSei0102d01", form);
      
      return strScreen;
    }
    logger.debug("会計月=[" + accountMonth + "]");

    // システム管理者フラグの取得
    String sysAdminFlag = String.valueOf(ses.get(CommonConst.LOGIN_USER_SYS_ADMIN_FLG));

    // 事業所リストの取得
    if (sysAdminFlag.equals(CommonConst.SYS_ADMIN_FLG_VALID)) {
      // システム管理者

      List<ObjCombobox> jigyoshoList = seikyuCommonService.getJigyoshoList(
          businessDate);

      if (jigyoshoList == null || jigyoshoList.size() == 0) {
        // 事業所リスト取得エラー

        ArrayList<ErrorMessages> errorList = new ArrayList<ErrorMessages>();
        ErrorMessages errorMsg = new ErrorMessages();
        ArrayList<String> params = new ArrayList<String>();

        params.add("事業所マスタの取得");
        errorMsg.setMessageContent(readPropertiesFileService.getMessage(
            "COM015-E", params));
        errorList.add(errorMsg);

        model.addAttribute("errorMessages", errorList);
        form.setErrorControl("errorControl");
        // エラーレベル
        form.setMsgErrorLevel(true);
        
        model.addAttribute("FormSei0102d01", form);

        return strScreen;
      } else {
        model.addAttribute("JigyoshoList", jigyoshoList);
      }
    }

    // DEFAULT MESSAGE の取得
    List<DefaultMessages> defList = sei0102d01Service.getDefaultMess();
    model.addAttribute("defaultMessages", defList);

    // ---------------------------------------------------------------------
    // FORM設定
    // ---------------------------------------------------------------------

    // システム管理者フラグ
    form.setSysAdminFlag(sysAdminFlag.equals(CommonConst.SYS_ADMIN_FLG_VALID) ? true
        : false);
    // ログイン事業所コード
    form.setLoginJigyoshoCd(loginJigyoshoCd);
    // 業務日付
    form.setBusinessDate(businessDate);
    // 会計月
    form.setAccountMonth(accountMonth);
    // 検索上限値
    form.setSearchMax(searchMax);
    // 検索結果0件フラグ
    form.setNotFoundFlag(true);

    // 事業所セレクトボックス
    if (sysAdminFlag.equals(CommonConst.SYS_ADMIN_FLG_VALID)) {
      form.setSelectedJigyoshoCd(loginJigyoshoCd);
    }
    // 請求締め日
    form.setSeikyuShimebi(DateUtil.getSysDate());
    // 事務担当者コード
    form.setJimuTantoshaCd(String.valueOf(ses.get(
        CommonConst.LOGIN_USER_CODE)));
    // 事務担当者名
    form.setJimuTantoshaName(String.valueOf(ses.get(
        CommonConst.LOGIN_USER_NAME)));

    model.addAttribute("FormSei0102d01", form);

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
      @ModelAttribute("FormSei0102d01") FormSei0102d01 form) {

    String strScreen = "/sei/sei0102d01";

    // ログインチェック
    String path = Util.checkSession(model, request,
        CommonConst.SEI0102D00_SCREEN_NAME);

    if (!path.equalsIgnoreCase("")) {
      return path;
    }

    Map<String, Object> ses = Util.getContentsFromSession(request);
    String userId = String.valueOf(ses.get(CommonConst.LOGIN_USER_CODE));

    ArrayList<ErrorMessages> errorList = new ArrayList<ErrorMessages>();
    String btnName = form.getBtnName();

    // 処理振り分け
    if (btnName.equals(SeiConst.BUTTON_NAME_SEARCH)) {
      // 表示ボタン
      search(model, form, errorList);

    } else if (btnName.equals(SeiConst.BUTTON_NAME_PROC)) {
      // 実行ボタン
      boolean isOk = execute(model, form, errorList, userId);
      // 検索条件戻し
      setCondToForm(form);
      if (isOk) {
        // 再検索
        search(model, form, errorList);
      }

    } else if (btnName.equals(SeiConst.BUTTON_NAME_PRINT_NOHIN_M)) {
      // 納品明細書印刷ボタン
      printNohinMeisai(model, form, errorList);
      // 検索条件戻し
      setCondToForm(form);

    } else if (btnName.equals(SeiConst.BUTTON_NAME_RE_PRINT_SEIKYUSHO)) {
      // 再印刷ボタン
      rePrintSeikyusho(model, form, errorList, userId);
      // 検索条件戻し
      setCondToForm(form);

    } else if (btnName.equals(SeiConst.BUTTON_NAME_SEND_SEIKYU_DATA)) {
      // 請求送信
      sendSeikyuData(model, form, errorList, userId);
      // 検索条件戻し
      setCondToForm(form);

    } else {
      // クリアボタン
      clear(model, form, ses);

    }

    // 事業所リストの取得
    if (form.isSysAdminFlag()) {

      List<ObjCombobox> jigyoshoList = seikyuCommonService.getJigyoshoList(form
          .getBusinessDate());

      if (jigyoshoList == null || jigyoshoList.size() == 0) {
        // 事業所リスト取得エラー

        ErrorMessages errorMsg = new ErrorMessages();
        ArrayList<String> params = new ArrayList<String>();

        params.add("事業所マスタの取得");
        errorMsg.setMessageContent(readPropertiesFileService.getMessage(
            "COM015-E", params));
        errorList.add(errorMsg);

        form.setErrorControl("errorControl");
        // エラーレベル
        form.setMsgErrorLevel(true);

        return strScreen;
      } else {
        model.addAttribute("JigyoshoList", jigyoshoList);
      }
    }

    // エラーメッセージ
    if (errorList.size() > 0) {
      model.addAttribute("errorMessages", errorList);
    }

    // DEFAULT MESSAGE の取得
    List<DefaultMessages> defList = sei0102d01Service.getDefaultMess();
    model.addAttribute("defaultMessages", defList);

    return strScreen;
  }

  /**
   * 検索.
   * 
   * @param model モデル
   * @param request リクエスト
   * @param form フォーム
   * @param errorList エラーリスト
   */
  private void search(Model model, FormSei0102d01 form,
      ArrayList<ErrorMessages> errorList) {

    // 入力チェック
    boolean isInputOk = sei0102d01Service.checkInputParamsForSearch(model,
        form);
    if (!isInputOk) {
      // エラーレベル
      form.setMsgErrorLevel(true);
      return;
    }

    // 請求締日編集
    form.setSeikyuShimebi(form.getSeikyuShimebi().replace("/", ""));

    // ---------------------------------------------------------------------
    // 検索条件退避
    // ---------------------------------------------------------------------

    setFormToCond(form);

    // ---------------------------------------------------------------------
    // 検索
    // ---------------------------------------------------------------------

    List<SeikyusakiInfo> list = sei0102d01Service.getSeikyusakiInfoList(form);

    // 明細部表示
    form.setShowListFlag(true);

    if (list == null || list.size() == 0) {
      // 検索結果0件
      form.setNotFoundFlag(true);
      String msg = readPropertiesFileService.getMessage("COM025-E", null);
      model.addAttribute("notFoundMessage", msg);

      return;
    } else {
      form.setNotFoundFlag(false);
    }

    // formにセット
    form.setSeikyusakiInfoList(null);
    form.setSeikyusakiInfoList(list);

    // 排他日時
    String haitaDate = DateUtil.getSysDate();
    String haitaTime = DateUtil.getSysTime();

    form.setHaitaDate(haitaDate);
    form.setHaitaTime(haitaTime);

    if (list.size() > form.getSearchMax()) {
      // 検索上限値オーバー

      ErrorMessages errorMsg = new ErrorMessages();
      ArrayList<String> params = new ArrayList<String>();

      params.add(String.valueOf(form.getSearchMax()));
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "COM005-W", params));

      errorList.add(errorMsg);
      // 警告レベル
      form.setMsgWarningLevel(true);

      // 上限を超えたものを削除
      list.remove(form.getSearchMax());
    }

    int intSeikyuShimebi = Integer.valueOf(form.getSeikyuShimebi());
    int intBusinessDate = Integer.valueOf(form.getBusinessDate());

    if (intSeikyuShimebi > intBusinessDate) {
      // 請求締日が未来日付

      ErrorMessages errorMsg = new ErrorMessages();

      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI001-W", null));
      // 警告レベル
      form.setMsgWarningLevel(true);

      errorList.add(errorMsg);
    }
  }

  /**
   * 実行.
   * 
   * @param model モデル
   * @param request リクエスト
   * @param form フォーム
   * @param errorList エラーリスト
   * @return 処理結果
   */
  private boolean execute(Model model, FormSei0102d01 form,
      ArrayList<ErrorMessages> errorList,
      String userId) {

    // 入力チェック
    boolean isInputOk = sei0102d01Service.checkInputParamsForExecute(model,
        form);
    if (!isInputOk) {
      // エラーレベル
      form.setMsgErrorLevel(true);
      return false;
    }

    List<SeikyusakiInfo> list = form.getSeikyusakiInfoList();

    logger.debug("▼▼▼▼▼▼▼▼▼▼ 請求先ループ [実行] ▼▼▼▼▼▼▼▼▼▼");

    boolean isPrintOk = false;

    int execOkCount = 0;
    int execErrorCount = 0;
    int printErrorCount = 0;

    for (SeikyusakiInfo info : list) {

      if (!info.isCheckBoxFlag()) {
        // チェックなし
        continue;
      }

      String seikyuId = info.getKonkaiSeikyuId();
      if (seikyuId != null && !seikyuId.equals("")) {
        // 締め処理済み
        continue;
      }

      if (info.getKonkaiUriageUrikake() == 0 && info.getKonkaiUriageMishu() == 0
          && info.getKonkaiNyukinUrikake() == 0 && info
              .getKonkaiNyukinMishu() == 0
          && info.getKonkaiSosaiUrikake() == 0 && info
              .getKonkaiSosaiMishu() == 0
          && info.getKonkaiChoseiUrikake() == 0 && info
              .getKonkaiChoseiMishu() == 0
          && info.getKurikoshiGakuUrikake() == 0 && info
              .getKurikoshiGakuMishu() == 0) {
        
        // 対象外
        
        continue;
      }

      // -----------------------------------------------------------------
      // 請求データ作成
      // -----------------------------------------------------------------

      logger.debug("▽▽▽▽▽▽▽▽▽▽ 請求データ作成 ▽▽▽▽▽▽▽▽▽▽");
      Long newSeikyuId = seikyuCommonService.createSeikyuData(info, form
          .getCondSeikyuShimebi(),
          SeiConst.SEIKYU_SHIME_KBN_TSUJO, null, form.getBusinessDate(), form
              .getHaitaDate(),
          form.getHaitaTime(), errorList, userId,
          SeiConst.PG_ID_SEIKYU_SHIME_SHORI);
      logger.debug("△△△△△△△△△△ 請求データ作成 △△△△△△△△△△");

      if (newSeikyuId != null && newSeikyuId > 0) {

        execOkCount++;

        if (info.getSeikyuDataKbn().equals(
            SeiConst.SEIKYU_DATA_KBN_SEIKYUSHO)) {

          // -------------------------------------------------------------
          // 請求書印刷
          // -------------------------------------------------------------

          logger.debug("▽▽▽▽▽▽▽▽▽▽ 請求書印刷 ▽▽▽▽▽▽▽▽▽▽");
          isPrintOk = seikyuCommonService.printSeikyusho(newSeikyuId, info
              .getSeikyusakiCd(),
              form.getBusinessDate(),
              errorList, userId, SeiConst.PG_ID_SEIKYU_SHIME_SHORI);
          logger.debug("△△△△△△△△△△ 請求書印刷 △△△△△△△△△△");

          if (!isPrintOk) {
            printErrorCount++;
          }

        }
      } else {
        
        execErrorCount++;
        
      }
    }

    logger.debug("▲▲▲▲▲▲▲▲▲▲ 請求先ループ [実行] ▲▲▲▲▲▲▲▲▲▲");

    if (execOkCount > 0) {
      // 完了メッセージ
      ErrorMessages infoMsg = new ErrorMessages();
      ArrayList<String> params = new ArrayList<String>();
      params.add("請求締め処理");
      infoMsg.setMessageContent(readPropertiesFileService.getMessage("COM002-I",
          params));
      errorList.add(0, infoMsg);
    }
    
    if (execErrorCount > 0 || printErrorCount > 0) {
      // エラーレベル
      form.setMsgErrorLevel(true);
    } else {
      // 情報レベル
      form.setMsgInfoLevel(true);
    }
    

    return true;
  }

  /**
   * 納品明細書印刷.
   * 
   * @param model モデル
   * @param request リクエスト
   * @param form フォーム
   * @aram errorList エラーリスト
   */
  private void printNohinMeisai(Model model, FormSei0102d01 form,
      ArrayList<ErrorMessages> errorList) {

    // 入力チェック
    boolean isInputOk = sei0102d01Service.checkInputParamsForPrintNohinMeisai(
        model, form);
    if (!isInputOk) {
      // エラーレベル
      form.setMsgErrorLevel(true);
      return;
    }

    List<SeikyusakiInfo> list = form.getSeikyusakiInfoList();

    logger.debug("▼▼▼▼▼▼▼▼▼▼ 請求先ループ [納品明細書印刷] ▼▼▼▼▼▼▼▼▼▼");

    boolean isOk = false;
    int okCount = 0;
    int errorCount = 0;

    for (SeikyusakiInfo info : list) {

      if (!info.isCheckBoxFlag()) {
        // チェックなし
        continue;
      }
      String seikyuId = info.getKonkaiSeikyuId();
      if (seikyuId == null || seikyuId.equals("")) {
        // 締め処理未
        continue;
      }
      if (info.getKonkaiUriageUrikake() == 0 && info
          .getKonkaiUriageMishu() == 0) {
        // 売上ゼロ
        continue;
      }

      // -----------------------------------------------------------------
      // 納品明細印刷
      // -----------------------------------------------------------------

      logger.debug("▽▽▽▽▽▽▽▽▽▽ 納品明細書印刷 ▽▽▽▽▽▽▽▽▽▽");
      isOk = sei0102d01Service.printNohinMeisai(info, form
          .getCondSeikyuShimebi(),
          errorList);
      logger.debug("△△△△△△△△△△ 納品明細書印刷 △△△△△△△△△△");

      if (isOk) {
        okCount++;
      } else {
        errorCount++;
      }

    }

    logger.debug("▲▲▲▲▲▲▲▲▲▲ 請求先ループ [納品明細書印刷] ▲▲▲▲▲▲▲▲▲▲");

    if (okCount > 0) {
      // 完了メッセージ
      ErrorMessages infoMsg = new ErrorMessages();
      ArrayList<String> params = new ArrayList<String>();
      params.add("納品明細書の印刷");
      infoMsg.setMessageContent(readPropertiesFileService.getMessage("COM002-I",
          params));
      errorList.add(0, infoMsg);
    }
    
    if (errorCount > 0) {
      // エラーレベル
      form.setMsgErrorLevel(true);
    } else {
      // 情報レベル
      form.setMsgInfoLevel(true);
    }
  }

  /**
   * 再印刷.
   * 
   * @param model モデル
   * @param request リクエスト
   * @param form フォーム
   * @param errorList エラーリスト
   */
  private void rePrintSeikyusho(Model model, FormSei0102d01 form,
      ArrayList<ErrorMessages> errorList,
      String userId) {

    // 入力チェック
    boolean isInputOk = sei0102d01Service.checkInputParamsForRePrintSeikyusho(
        model, form);
    if (!isInputOk) {
      // エラーレベル
      form.setMsgErrorLevel(true);
      return;
    }

    List<SeikyusakiInfo> list = form.getSeikyusakiInfoList();

    logger.debug("▼▼▼▼▼▼▼▼▼▼ 請求先ループ [再印刷] ▼▼▼▼▼▼▼▼▼▼");

    int okCount = 0;
    int errorCount = 0;
    boolean isOk = false;

    for (SeikyusakiInfo info : list) {

      if (!info.isCheckBoxFlag()) {
        // チェックなし
        continue;
      }
      String seikyuId = info.getKonkaiSeikyuId();
      if (seikyuId == null || seikyuId.equals("")) {
        // 締め処理未
        continue;
      }

      // -----------------------------------------------------------------
      // 再印刷
      // -----------------------------------------------------------------

      logger.debug("▽▽▽▽▽▽▽▽▽▽ 請求書印刷 ▽▽▽▽▽▽▽▽▽▽");
      isOk = seikyuCommonService.printSeikyusho(Long.valueOf(seikyuId), info
          .getSeikyusakiCd(), form.getBusinessDate(),
          errorList, userId, SeiConst.PG_ID_SEIKYU_SHIME_SHORI);
      logger.debug("△△△△△△△△△△ 請求書印刷 △△△△△△△△△△");

      if (isOk) {
        okCount++;
      } else {
        errorCount++;
      }

    }

    logger.debug("▲▲▲▲▲▲▲▲▲▲ 請求先ループ [再印刷] ▲▲▲▲▲▲▲▲▲▲");

    if (okCount > 0) {
      // 完了メッセージ
      ErrorMessages infoMsg = new ErrorMessages();
      ArrayList<String> params = new ArrayList<String>();
      params.add("請求書の再印刷");
      infoMsg.setMessageContent(readPropertiesFileService.getMessage("COM002-I",
          params));
      errorList.add(0, infoMsg);
    }
    
    if (errorCount > 0) {
      // エラーレベル
      form.setMsgErrorLevel(true);
    } else {
      // 情報レベル
      form.setMsgInfoLevel(true);
    }
  }

  /**
   * 請求データ送信.
   * 
   * @param model モデル
   * @param form フォーム
   * @param errorList エラーリスト
   */
  private void sendSeikyuData(Model model, FormSei0102d01 form,
      ArrayList<ErrorMessages> errorList,
      String userId) {

    // ---------------------------------------------------------------------
    // 請求データ送信
    // ---------------------------------------------------------------------

    logger.debug("▽▽▽▽▽▽▽▽▽▽ 請求データ送信 ▽▽▽▽▽▽▽▽▽▽");
    boolean isOk = seikyuCommonService.sendSeikyuData(form
        .getDataSendSeikyuId(), form.getDataSendSoshinId(),
        errorList, userId, SeiConst.PG_ID_SEIKYU_SHIME_SHORI);
    logger.debug("△△△△△△△△△△ 請求データ送信 △△△△△△△△△△");

    if (isOk) {
      // 完了メッセージ
      ErrorMessages infoMsg = new ErrorMessages();
      ArrayList<String> params = new ArrayList<String>();
      params.add("請求データ送信");
      infoMsg.setMessageContent(readPropertiesFileService.getMessage("COM002-I",
          params));
      errorList.add(0, infoMsg);
      
      // 情報レベル
      form.setMsgInfoLevel(true);
    } else {
      // エラーレベル
      form.setMsgErrorLevel(true);
    }
  }

  /**
   * クリア.
   * 
   * @param model モデル
   * @param request リクエスト
   * @param form フォーム
   */
  private void clear(Model model, FormSei0102d01 form,
      Map<String, Object> ses) {

    // ボタン名
    form.setBtnName("");
    // 排他日付
    form.setHaitaDate("");
    // 排他時刻
    form.setHaitaTime("");
    // エラーコントロール
    form.setErrorControl("");
    // 明細部非表示
    form.setShowListFlag(false);
    // 検索結果0件
    form.setNotFoundFlag(true);

    // ---------------------------------------------------------------------
    // 検索条件
    // ---------------------------------------------------------------------

    // 事業所コード
    if (form.isSysAdminFlag()) {
      form.setSelectedJigyoshoCd(form.getLoginJigyoshoCd());
    }
    // 請求締め日
    form.setSeikyuShimebi(DateUtil.getSysDate());
    // 請求先コード
    form.setSeikyusakiCd("");
    // 請求先名
    form.setSeikyusakiName("");
    // 事務担当者コード
    form.setJimuTantoshaCd(String.valueOf(ses.get(
        CommonConst.LOGIN_USER_CODE)));
    // 事務担当者名
    form.setJimuTantoshaName(String.valueOf(ses.get(
        CommonConst.LOGIN_USER_NAME)));
    // 処理対象のみ表示
    form.setShoriTaishoFlag(false);
    // 他社顧客
    form.setTashaKokyakuFlag(false);
    // 請求データ未送信のみ表示
    form.setSeikyuMisoshinFlag(false);
    // 退避用検索条件
    setFormToCond(form);

  }

  /**
   * 検索条件退避.
   * 
   * @param form フォーム
   */
  private void setFormToCond(FormSei0102d01 form) {
    // ---------------------------------------------------------------------
    // 検索条件退避
    // ---------------------------------------------------------------------

    // 事業所セレクトボックス選択値
    form.setCondSelectedJigyoshoCd(form.getSelectedJigyoshoCd());
    // 請求締め日
    form.setCondSeikyuShimebi(form.getSeikyuShimebi());
    // 請求先コード
    form.setCondSeikyusakiCd(form.getSeikyusakiCd());
    // 事務担当者コード
    form.setCondJimuTantoshaCd(form.getJimuTantoshaCd());
    // 処理対象のみ表示
    form.setCondShoriTaishoFlag(form.isShoriTaishoFlag());
    // 他社顧客
    form.setCondTashaKokyakuFlag(form.isTashaKokyakuFlag());
    // 請求データ未送信のみ表示
    form.setCondSeikyuMisoshinFlag(form.isSeikyuMisoshinFlag());
  }

  /**
   * 検索条件戻し.
   * 
   * @param form フォーム
   */
  private void setCondToForm(FormSei0102d01 form) {
    // ---------------------------------------------------------------------
    // 検索条件戻し
    // ---------------------------------------------------------------------

    // 事業所セレクトボックス選択値
    form.setSelectedJigyoshoCd(form.getCondSelectedJigyoshoCd());
    // 請求締め日
    form.setSeikyuShimebi(form.getCondSeikyuShimebi());
    // 請求先コード
    form.setSeikyusakiCd(form.getCondSeikyusakiCd());
    // 事務担当者コード
    form.setJimuTantoshaCd(form.getCondJimuTantoshaCd());
    // 処理対象のみ表示
    form.setShoriTaishoFlag(form.isCondShoriTaishoFlag());
    // 他社顧客
    form.setTashaKokyakuFlag(form.isCondTashaKokyakuFlag());
    // 請求データ未送信のみ表示
    form.setSeikyuMisoshinFlag(form.isCondSeikyuMisoshinFlag());
  }

}
