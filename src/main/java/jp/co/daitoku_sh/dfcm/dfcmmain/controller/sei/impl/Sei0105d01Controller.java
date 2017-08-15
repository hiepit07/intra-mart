/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.sei.impl
 * ファイル名:Sei0105d01Controller.java
 * 
 * 作成者:都築電気株式会社
 * 作成日:2015/11/27
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/11/27 1.00 ACT)ANZAI 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.controller.sei.impl;

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
import jp.co.daitoku_sh.dfcm.dfcmmain.component.sei.FormSei0105d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.sei.SeikyusakiInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.sei.TorimatomeSeikyusakiInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.sei.impl.Sei0105d01Service;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.sei.impl.SeikyuCommonService;

/**
 * コントローラクラス 取り纏め請求
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/sei/SEI01-05D00/")
public class Sei0105d01Controller extends AbsController {

  @Autowired
  private SeikyuCommonService seikyuCommonService;

  @Autowired
  private Sei0105d01Service sei0105d01Service;

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

    String strScreen = "/sei/sei0105d01";

    // ログインチェック
    String path = Util.checkSession(model, request,
        CommonConst.SEI0105D00_SCREEN_NAME);
    if (!path.equalsIgnoreCase("")) {
      return path;
    }

    // Formの生成
    FormSei0105d01 form = new FormSei0105d01();

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

      model.addAttribute("FormSei0105d01", form);

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

      model.addAttribute("FormSei0105d01", form);

      return strScreen;
    }
    logger.debug("会計月=[" + accountMonth + "]");

    // システム管理者フラグの取得
    String sysAdminFlag = String.valueOf(ses.get(
        CommonConst.LOGIN_USER_SYS_ADMIN_FLG));

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

        model.addAttribute("FormSei0105d01", form);

        return strScreen;
      } else {
        model.addAttribute("JigyoshoList", jigyoshoList);
      }
    }

    // DEFAULT MESSAGE の取得
    List<DefaultMessages> defList = sei0105d01Service.getDefaultMess();
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
    // 会計月
    form.setAccountMonth(accountMonth);
    // 検索結果0件フラグ
    form.setNotFoundFlag(true);

    // 事業所セレクトボックス
    if (sysAdminFlag.equals(CommonConst.SYS_ADMIN_FLG_VALID)) {
      form.setSelectedJigyoshoCd(loginJigyoshoCd);
    }
    // 請求締め日
    form.setSeikyuShimebi(DateUtil.getSysDate());

    model.addAttribute("FormSei0105d01", form);

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
      @ModelAttribute("FormSei0105d01") FormSei0105d01 form) {

    String strScreen = "/sei/sei0105d01";

    // ログインチェック
    String path = Util.checkSession(model, request,
        CommonConst.SEI0105D00_SCREEN_NAME);

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

    } else if (btnName.equals(SeiConst.BUTTON_NAME_TORIKESHI)) {
      // 取消ボタン
      boolean isOk = torikeshi(model, form, errorList, userId);
      // 検索条件戻し
      setCondToForm(form);
      if (isOk) {
        // 再検索
        search(model, form, errorList);
      }

    } else if (btnName.equals(SeiConst.BUTTON_NAME_RE_PRINT_SEIKYUSHO)) {
      // 再印刷ボタン
      rePrintSeikyusho(model, form, errorList, userId);
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
    List<DefaultMessages> defList = sei0105d01Service.getDefaultMess();
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
  private void search(Model model, FormSei0105d01 form,
      ArrayList<ErrorMessages> errorList) {

    // 入力チェック
    boolean isInputOk = sei0105d01Service.checkInputParamsForSearch(model,
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

    // ------------------------------------------------------------------------
    // 検索
    // ------------------------------------------------------------------------

    List<SeikyusakiInfo> list = sei0105d01Service.getSeikyusakiInfoList(form);

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

    // ------------------------------------------------------------------------
    // 取り纏め請求情報設定
    // ------------------------------------------------------------------------

    TorimatomeSeikyusakiInfo matomesaki = new TorimatomeSeikyusakiInfo();
    matomesaki.setShimeSumiFlag(true);

    boolean existTorimatomeJigyosho = false;

    for (SeikyusakiInfo info : list) {

      String seikyuId = info.getKonkaiSeikyuId();
      int uriage = info.getKonkaiUriageUrikake() + info.getKonkaiUriageMishu();
      int nyukin = info.getKonkaiNyukinUrikake() + info.getKonkaiNyukinMishu();
      int sosai = info.getKonkaiSosaiUrikake() + info.getKonkaiSosaiMishu();
      int chosei = info.getKonkaiChoseiUrikake() + info.getKonkaiChoseiMishu();
      int kurikoshi = info.getKurikoshiGakuUrikake() + info
          .getKonkaiUriageMishu();

      // 今回売上フラグ
      if (uriage > 0) {
        matomesaki.setKonakiUriageFlag(true);
      }
      // 今回入金フラグ
      if (nyukin > 0 || sosai > 0 || chosei > 0) {
        matomesaki.setKonkaiNyukinFlag(true);
      }
      // 繰越残高フラグ
      if (kurikoshi > 0) {
        matomesaki.setSeikyuZanFlag(true);
      }
      // 締め処理済みフラグ
      if ((uriage > 0 || nyukin > 0 || sosai > 0 || chosei > 0 || kurikoshi > 0)
          && (seikyuId == null || seikyuId.equals(""))) {
        matomesaki.setShimeSumiFlag(false);
      }
      // 入金済みフラグ
      if (info.getNyukinKakuninFlag() != null && info.getNyukinKakuninFlag()
          .equals(
              SeiConst.NYUKIN_KAKUNIN_FLAG_NYUKIN_ZUMI)) {
        matomesaki.setNyukinSumiFlag(true);
      }

      // 取り纏め事業所
      if (info.getJigyoshoCd().equals(info.getTorimatomeJigyoshoCd())) {

        existTorimatomeJigyosho = true;

        // 請求ID
        matomesaki.setSeikyuId(info.getKonkaiSeikyuId());
        // チェーンコード
        matomesaki.setChainCode(info.getChainCode());
        // チェーン枝番
        matomesaki.setChainIdx(info.getChainIdx());
        // 請求先コード
        matomesaki.setSeikyusakiCd(info.getSeikyusakiCd());
        // 請求先名
        matomesaki.setSeikyusakiNameR(info.getSeikyusakiNameR());
        // 事業所コード
        matomesaki.setJigyoshoCd(info.getJigyoshoCd());
        // 事業所名
        matomesaki.setJigyoshoName(info.getJigyoshoName());
        // 担当者コード
        matomesaki.setTantoshaCd(info.getTantoshaCd());
        // 担当者名
        matomesaki.setTantoshaName(info.getTantoshaName());
        // 消費税計算単位
        matomesaki.setTaxKeisanTani(info.getTaxKeisanTani());
        // 消費税端数処理
        matomesaki.setTaxHasuShori(info.getTaxHasuShori());
        // 請求書種類
        matomesaki.setSeikyushoCls(info.getSeikyushoCls());
        // 請求データ区分
        matomesaki.setSeikyuDataKbn(info.getSeikyuDataKbn());

      }
    }

    if (!existTorimatomeJigyosho) {
      logger.debug("SEI017-E=取り纏め請求先がありません");
      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "SEI017-E", null));
      errorList.add(errorMsg);
      model.addAttribute("errorMessages", errorList);
      // エラーレベル
      form.setMsgErrorLevel(true);
      return;
    }

    // ------------------------------------------------------------------------
    // 取り纏め請求ヘッダー取得
    // ------------------------------------------------------------------------

    Map<String, Object> torimatomeHeaderMap = sei0105d01Service
        .getTorimatomeSeikyuHeader(matomesaki.getSeikyusakiCd(), matomesaki
            .getJigyoshoCd(), form.getCondSeikyuShimebi());

    if (torimatomeHeaderMap != null && torimatomeHeaderMap.size() > 0) {

      logger.debug("取り纏め請求ヘッダあり");

      matomesaki.setSeikyuId(String.valueOf(torimatomeHeaderMap.get(
          "SEIKYU_ID")));
      matomesaki.setSeikyuShimebi(String.valueOf(torimatomeHeaderMap.get(
          "SEIKYU_SHIMEBI")));
      matomesaki.setSeikyuShimeNichiji(String.valueOf(torimatomeHeaderMap.get(
          "SEIKYU_SHIME_NICHIJI")));
    } else {
      logger.debug("取り纏め請求ヘッダなし");
      matomesaki.setSeikyuId("");
    }

    // ------------------------------------------------------------------------
    // 排他件数の取得
    // ------------------------------------------------------------------------

    if (matomesaki.isShimeSumiFlag()
        && !matomesaki.isNyukinSumiFlag()
        && (matomesaki.isKonakiUriageFlag()
            || matomesaki.isKonkaiNyukinFlag()
            || matomesaki.isSeikyuZanFlag())) {

      int recCount = sei0105d01Service.getHaitaRecordCount(matomesaki
          .getSeikyusakiCd(), form.getCondSeikyuShimebi());
      form.setHaitaCount(recCount);

      logger.debug("排他件数 = [" + recCount + "]");
    } else {
      logger.debug("排他件数 取得しない");
    }

    // formにセット
    form.setSeikyusakiInfoList(null);
    form.setSeikyusakiInfoList(list);
    form.setTorimatomeInfo(null);
    form.setTorimatomeInfo(matomesaki);

    // 排他日時
    String haitaDate = DateUtil.getSysDate();
    String haitaTime = DateUtil.getSysTime();

    form.setHaitaDate(haitaDate);
    form.setHaitaTime(haitaTime);

  }

  /**
   * 実行
   * 
   * @param model モデル
   * @param form フォーム
   * @param errorList エラーリスト
   * @param userId ユーザーID
   * @return 処理結果
   */
  private boolean execute(Model model, FormSei0105d01 form,
      ArrayList<ErrorMessages> errorList, String userId) {

    // 入力チェック
    boolean isInputOk = sei0105d01Service.checkInputParamsForExecute(model,
        form);
    if (!isInputOk) {
      // エラーレベル
      form.setMsgErrorLevel(true);
      return false;
    }

    logger.debug("▽▽▽▽▽▽▽▽▽▽ 取り纏め請求 実行 ▽▽▽▽▽▽▽▽▽▽");

    boolean isProcOk = sei0105d01Service.execute(form, errorList, userId);

    logger.debug("△△△△△△△△△△ 取り纏め請求 実行 △△△△△△△△△△");

    TorimatomeSeikyusakiInfo torimatome = form.getTorimatomeInfo();
    boolean isPrintOk = false;

    if (isProcOk && torimatome.getSeikyuDataKbn().equals(
        SeiConst.SEIKYU_DATA_KBN_SEIKYUSHO)) {

      logger.debug("▽▽▽▽▽▽▽▽▽▽ 請求書印刷 ▽▽▽▽▽▽▽▽▽▽");

      isPrintOk = seikyuCommonService.printSeikyusho(
          Long.valueOf(torimatome.getSeikyuId()),
          torimatome.getSeikyusakiCd(),
          form.getBusinessDate(),
          errorList,
          userId,
          SeiConst.PG_ID_TORIMATOME_SEIKYU);

      logger.debug("△△△△△△△△△△ 請求書印刷 △△△△△△△△△△");

    } else {
      isPrintOk = true;
    }

    if (isProcOk) {

      // 完了メッセージ
      ErrorMessages infoMsg = new ErrorMessages();
      ArrayList<String> params = new ArrayList<String>();
      params.add("取り纏め請求");
      infoMsg.setMessageContent(readPropertiesFileService.getMessage("COM002-I",
          params));
      errorList.add(0, infoMsg);
    }

    if (isProcOk && isPrintOk) {
      // 情報レベル
      form.setMsgInfoLevel(true);
    } else {
      // エラーレベル
      form.setMsgErrorLevel(true);
    }

    return true;
  }

  /**
   * 取消
   * 
   * @param model モデル
   * @param form フォーム
   * @param errorList エラーリスト
   * @param userId ユーザーID
   * @return 処理結果
   */
  private boolean torikeshi(Model model, FormSei0105d01 form,
      ArrayList<ErrorMessages> errorList, String userId) {

    // 入力チェック
    boolean isInputOk = sei0105d01Service.checkInputParamsForTorikeshi(model,
        form);
    if (!isInputOk) {
      // エラーレベル
      form.setMsgErrorLevel(true);
      return false;
    }

    logger.debug("▽▽▽▽▽▽▽▽▽▽ 取り纏め請求 取消 ▽▽▽▽▽▽▽▽▽▽");

    boolean isProcOk = sei0105d01Service.torikeshi(form, errorList, userId);

    logger.debug("△△△△△△△△△△ 取り纏め請求 取消 △△△△△△△△△△");

    if (isProcOk) {
      // 完了メッセージ
      ErrorMessages infoMsg = new ErrorMessages();
      ArrayList<String> params = new ArrayList<String>();
      params.add("取消");
      infoMsg.setMessageContent(readPropertiesFileService.getMessage("COM002-I",
          params));
      errorList.add(0, infoMsg);

      // 情報レベル
      form.setMsgInfoLevel(true);
    } else {
      // エラーレベル
      form.setMsgErrorLevel(true);
    }

    return true;
  }

  /**
   * 再印刷
   * 
   * @param model モデル
   * @param form フォーム
   * @param errorList エラーリスト
   * @param userId ユーザーID
   * @return 処理結果
   */
  private boolean rePrintSeikyusho(Model model, FormSei0105d01 form,
      ArrayList<ErrorMessages> errorList, String userId) {

    // 入力チェック
    boolean isInputOk = sei0105d01Service.checkInputParamsForRePrint(model,
        form);
    if (!isInputOk) {
      // エラーレベル
      form.setMsgErrorLevel(true);
      return false;
    }

    TorimatomeSeikyusakiInfo torimatome = form.getTorimatomeInfo();

    logger.debug("▽▽▽▽▽▽▽▽▽▽ 請求書印刷 ▽▽▽▽▽▽▽▽▽▽");

    boolean isPrintOk = seikyuCommonService.printSeikyusho(
        Long.valueOf(torimatome.getSeikyuId()),
        torimatome.getSeikyusakiCd(),
        form.getBusinessDate(),
        errorList,
        userId,
        SeiConst.PG_ID_TORIMATOME_SEIKYU);

    logger.debug("△△△△△△△△△△ 請求書印刷 △△△△△△△△△△");

    if (isPrintOk) {
      // 完了メッセージ
      ErrorMessages infoMsg = new ErrorMessages();
      ArrayList<String> params = new ArrayList<String>();
      params.add("請求書の再印刷");
      infoMsg.setMessageContent(readPropertiesFileService.getMessage("COM002-I",
          params));
      errorList.add(0, infoMsg);

      // 情報レベル
      form.setMsgInfoLevel(true);
    } else {
      // エラーレベル
      form.setMsgErrorLevel(true);
    }

    return true;
  }

  /**
   * クリア.
   * 
   * @param model モデル
   * @param request リクエスト
   * @param form フォーム
   */
  private void clear(Model model, FormSei0105d01 form,
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

    // 退避用検索条件
    setFormToCond(form);

  }

  /**
   * 検索条件退避.
   * 
   * @param form フォーム
   */
  private void setFormToCond(FormSei0105d01 form) {
    // ---------------------------------------------------------------------
    // 検索条件退避
    // ---------------------------------------------------------------------

    // 事業所セレクトボックス選択値
    form.setCondSelectedJigyoshoCd(form.getSelectedJigyoshoCd());
    // 請求締め日
    form.setCondSeikyuShimebi(form.getSeikyuShimebi());
    // 請求先コード
    form.setCondSeikyusakiCd(form.getSeikyusakiCd());
  }

  /**
   * 検索条件戻し.
   * 
   * @param form フォーム
   */
  private void setCondToForm(FormSei0105d01 form) {
    // ---------------------------------------------------------------------
    // 検索条件戻し
    // ---------------------------------------------------------------------

    // 事業所セレクトボックス選択値
    form.setSelectedJigyoshoCd(form.getCondSelectedJigyoshoCd());
    // 請求締め日
    form.setSeikyuShimebi(form.getCondSeikyuShimebi());
    // 請求先コード
    form.setSeikyusakiCd(form.getCondSeikyusakiCd());
  }

}
