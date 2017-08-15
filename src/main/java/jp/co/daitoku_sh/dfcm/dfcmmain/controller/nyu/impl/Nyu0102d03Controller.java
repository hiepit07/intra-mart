/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.nyu.impl
 * ファイル名:Nyu0102d03Controller.java
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

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.controller.AbsController;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.NyuConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ObjCombobox;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu.FormNyu0102d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu.FormNyu0102d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu.FormNyu0102d03;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu.NyuSeikyusakiInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu.NyukinGamenMeisaiInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu.ShoriKbnItem;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.nyu.impl.Nyu0102d01Service;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.nyu.impl.Nyu0102d03Service;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.sei.impl.SeikyuCommonService;

/**
 * コントローラクラス 都度請求未回収設定
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/nyu/NYU01-02D03/")
public class Nyu0102d03Controller extends AbsController {

  @Autowired
  private SeikyuCommonService seikyuCommonService;

  @Autowired
  private Nyu0102d01Service nyu0102d01Service;
  
  @Autowired
  private Nyu0102d03Service nyu0102d03Service;

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

    String strScreen = "/nyu/nyu0102d03";

    // ログインチェック
    String path = Util.checkSession(model, request,
        CommonConst.NYU0102D00_SCREEN_NAME);
    if (!path.equalsIgnoreCase("")) {
      return path;
    }

    // Formの生成
    FormNyu0102d03 form = new FormNyu0102d03();

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
      
      model.addAttribute("FormNyu0102d03", form);

      return strScreen;
    }
    logger.debug("業務日付=[" + businessDate + "]");

    // セッションの取得
    Map<String, Object> ses = Util.getContentsFromSession(request);

    // ログイン事業所コードの取得
    String loginJigyoshoCd = String.valueOf(ses.get(
        CommonConst.LOGIN_USER_JIGYOSHO_CODE));

    // システム管理者フラグの取得
    String sysAdminFlag = String.valueOf(ses.get(CommonConst.LOGIN_USER_SYS_ADMIN_FLG));

    // 権限区分の取得
    String authCode = String.valueOf(ses.get(CommonConst.LOGIN_USER_AUTH_CODE));
    String authKbn = nyu0102d01Service.getAuthKbn(form.isSysAdminFlag(), authCode);
    
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
        
        model.addAttribute("FormNyu0102d03", form);

        return strScreen;
      } else {
        model.addAttribute("JigyoshoList", jigyoshoList);
      }
    }

    // DEFAULT MESSAGE の取得
    List<DefaultMessages> defList = nyu0102d03Service.getDefaultMess();
    model.addAttribute("defaultMessages", defList);

    // ---------------------------------------------------------------------
    // FORM設定
    // ---------------------------------------------------------------------

    // システム管理者フラグ
    form.setSysAdminFlag(sysAdminFlag.equals(CommonConst.SYS_ADMIN_FLG_VALID) ? true
        : false);
    // 権限区分
    form.setAuthKbn(authKbn);
    // ログイン事業所コード
    form.setLoginJigyoshoCd(loginJigyoshoCd);
    // 業務日付
    form.setBusinessDate(businessDate);
    // 検索上限値
    form.setSearchMax(searchMax);
    // 検索結果0件フラグ
    form.setNotFoundFlag(true);

    // 事業所セレクトボックス
    if (sysAdminFlag.equals(CommonConst.SYS_ADMIN_FLG_VALID)) {
      form.setSelectedJigyoshoCd(loginJigyoshoCd);
    }

    model.addAttribute("FormNyu0102d03", form);

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
      @ModelAttribute("FormNyu0102d03") FormNyu0102d03 form) {

    String strScreen = "/nyu/nyu0102d03";

    // ログインチェック
    String path = Util.checkSession(model, request,
        CommonConst.NYU0102D00_SCREEN_NAME);

    if (!path.equalsIgnoreCase("")) {
      return path;
    }

    ArrayList<ErrorMessages> errorList = new ArrayList<ErrorMessages>();
    String btnName = form.getBtnName();

    // 処理振り分け
    if (btnName.equals(NyuConst.BUTTON_NAME_SEARCH)) {
      // 表示ボタン
      search(model, form, errorList);

    } else if (btnName.equals(NyuConst.BUTTON_NAME_PROC)) {
      // 実行ボタン
      boolean isOk = execute(model, form, errorList);
      
      if (isOk) {
        logger.debug("入金登録画面へ遷移");
        return "/nyu/nyu0102d01";
      }
      
    } else {
      // クリアボタン
      clear(form);

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
    List<DefaultMessages> defList = nyu0102d03Service.getDefaultMess();
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
  private void search(Model model, FormNyu0102d03 form,
      ArrayList<ErrorMessages> errorList) {

    // 入力チェック
    boolean isInputOk = nyu0102d03Service.checkInputParamsForSearch(model,
        form);
    if (!isInputOk) {
      // エラーレベル
      form.setMsgErrorLevel(true);
      return;
    }

    // ---------------------------------------------------------------------
    // 検索条件退避
    // ---------------------------------------------------------------------

    setFormToCond(form);

    // ---------------------------------------------------------------------
    // 検索
    // ---------------------------------------------------------------------

    List<NyuSeikyusakiInfo> list = nyu0102d03Service.getTsudoSeikyusakiInfoList(form);

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
    form.setNyuSeikyusakiList(null);
    form.setNyuSeikyusakiList(list);

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
  }

  /**
   * 実行
   * @param model モデル
   * @param form フォーム
   * @param errorList エラーリスト
   */
  private boolean execute(Model model, FormNyu0102d03 form, ArrayList<ErrorMessages> errorList) {
    
    //-------------------------------------------------------------------------
    // 入力チェック
    //-------------------------------------------------------------------------
    
    List<NyuSeikyusakiInfo> seikyuList = form.getNyuSeikyusakiList();
    
    int idx = 0;  // ループIDX
    int checkIdx = 0; // チェックありIDX
    int checkCount = 0;  // チェック数
    
    boolean isChecked = false; 
    boolean hasSeikyuId = false;
    
    for (NyuSeikyusakiInfo info : seikyuList) {
      
      if (info.isCheckBoxFlag()) {
        checkIdx = idx;
        isChecked = true;
        checkCount++;
        // 請求IDチェック
        if (info.getSeikyuId() != null && !info.getSeikyuId().equals("")) {
          hasSeikyuId = true;
        }
      }
      idx++;
    }
    
    if (!isChecked) {
      
      // チェックなし
      ErrorMessages errorMsg = new ErrorMessages();
      ArrayList<String> msgParams = new ArrayList<String>();
      msgParams.add("請求先");
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "COM008-E", msgParams));
      errorList.add(errorMsg);
      // エラーレベル
      form.setMsgErrorLevel(true);
      
      return false;
    }
    
    if (checkCount > 1 && hasSeikyuId) {
      
      // 複数選択内に未回収残がある伝票が含まれている
      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "NYU010-E", null));
      errorList.add(errorMsg);
      // エラーレベル
      form.setMsgErrorLevel(true);
      
      return false;
    }
    
    //-------------------------------------------------------------------------
    // 画面表示項目設定
    //-------------------------------------------------------------------------
    
    // 処理区分ラジオボタン
    List<ShoriKbnItem> shoriKbns = new ArrayList<ShoriKbnItem>();
    shoriKbns.add(new ShoriKbnItem("本社承認", NyuConst.SHORI_KBN_SHONIN));
    shoriKbns.add(new ShoriKbnItem("新規登録", NyuConst.SHORI_KBN_SHINKI));
    shoriKbns.add(new ShoriKbnItem("訂正", NyuConst.SHORI_KBN_TEISEI));
    shoriKbns.add(new ShoriKbnItem("取消", NyuConst.SHORI_KBN_TORIKESHI));
    shoriKbns.add(new ShoriKbnItem("照会", NyuConst.SHORI_KBN_SHOKAI));

    model.addAttribute("shoriKbns", shoriKbns);
    
    NyuSeikyusakiInfo info = seikyuList.get(checkIdx);
    
    // 入金登録画面フォーム
    FormNyu0102d01 formNyukin = new FormNyu0102d01();
    model.addAttribute("FormNyu0102d01", formNyukin);
    
    // システム管理者フラグ
    formNyukin.setSysAdminFlag(form.isSysAdminFlag());
    // 権限区分
    formNyukin.setAuthKbn(form.getAuthKbn());
    // ログイン事業所コード
    formNyukin.setLoginJigyoshoCd(form.getLoginJigyoshoCd());
    // 業務日付
    formNyukin.setBusinessDate(form.getBusinessDate());
    // 排他日付
    formNyukin.setHaitaDate(form.getHaitaDate());
    // 排他時刻
    formNyukin.setHaitaTime(form.getHaitaTime());
    // 表示フラグ
    formNyukin.setShowFlag(true);
    // 処理区分
    formNyukin.setShoriKbn(NyuConst.SHORI_KBN_SHINKI);
    
    // チェーンコード
    formNyukin.setChainCd(info.getChainCd());
    // チェーン枝番
    formNyukin.setChainIdx(info.getChainIdx());
    // 請求先コード
    formNyukin.setSeikyusakiCd(info.getSeikyusakiCd());
    // 請求先名
    formNyukin.setSeikyusakiName(info.getSeikyusakiName());
    // 請求単位
    formNyukin.setSeikyuTani(info.getSeikyuTani());
    // 得意先種別
    formNyukin.setTokuisakiCls(info.getTokuisakiCls());
    // 関係会社種別
    formNyukin.setKankeiKaishaCls(info.getKankeiKaishaCls());
    // 関係会社補助科目
    formNyukin.setKankeiKaishaHojoKamoku(info.getKankeiKaishaHojoKamoku());
    // 事業所コード
    formNyukin.setJigyoshoCd(info.getJigyoshoCd());
    // 事業所名
    formNyukin.setJigyoshoName(info.getJigyoshoName());
    // 請求ID
    formNyukin.setSeikyuId(info.getSeikyuId());
    // 請求締日
    formNyukin.setSeikyuShimebi(info.getSeikyuShimebi());
    // 入金予定日
    formNyukin.setNyukinYoteibi(info.getNyukinYoteibi());
    // 未回収残
    formNyukin.setMikaishuZandaka(info.getMikaishuGaku());

    // 共通部品初期化
    CommonGetSystemCom commonGetSysCom = new CommonGetSystemCom(commonDao, null,
        null, readPropertiesFileService);
    
    boolean isOk = false;
    
    // 入金遅れリスト
    isOk = nyu0102d01Service.getOkureRiyuList(commonGetSysCom, model, errorList);
    if (!isOk) {
      model.addAttribute("errorMessages", errorList);
      formNyukin.setErrorControl("errorControl");
      // エラーレベル
      formNyukin.setMsgErrorLevel(true);
      return true;
    }
    
    // 初期表示科目リスト
    isOk = nyu0102d01Service.getDefaultKamokuList(formNyukin, errorList);
    if (!isOk) {
      model.addAttribute("errorMessages", errorList);
      formNyukin.setErrorControl("errorControl");
      // エラーレベル
      formNyukin.setMsgErrorLevel(true);
      return true;
    }
    
    // 事業所リスト
    List<ObjCombobox> jigyoshoList = seikyuCommonService.getJigyoshoList(
        form.getBusinessDate());
    if (jigyoshoList == null || jigyoshoList.size() == 0) {
      // 事業所リスト取得エラー

      ErrorMessages errorMsg = new ErrorMessages();
      ArrayList<String> msgParams = new ArrayList<String>();

      msgParams.add("事業所マスタの取得");
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "COM015-E", msgParams));
      errorList.add(errorMsg);

      model.addAttribute("errorMessages", errorList);
      formNyukin.setErrorControl("errorControl");
      // エラーレベル
      formNyukin.setMsgErrorLevel(true);

      return true;
    } else {
      model.addAttribute("jigyoshoList", jigyoshoList);
    }
    
    // 発生場所リスト
    isOk = nyu0102d01Service.getHasseiBashoList(model, errorList);
    if (!isOk) {
      model.addAttribute("errorMessages", errorList);
      formNyukin.setErrorControl("errorControl");
      // エラーレベル
      formNyukin.setMsgErrorLevel(true);
      return true;
    }
    
    // 入金調整額
    formNyukin.setChoseiGaku(0);
    
    // 追加科目リスト
    isOk = nyu0102d01Service.getAddKamokuList(model, errorList);
    if (!isOk) {
      model.addAttribute("errorMessages", errorList);
      formNyukin.setErrorControl("errorControl");
      // エラーレベル
      formNyukin.setMsgErrorLevel(true);
      return true;
    }
    
    // 残高理由リスト
    isOk = nyu0102d01Service.getZandakaRiyuList(commonGetSysCom, model, errorList);
    if (!isOk) {
      model.addAttribute("errorMessages", errorList);
      formNyukin.setErrorControl("errorControl");
      // エラーレベル
      formNyukin.setMsgErrorLevel(true);
      return true;
    }
    
    // 選択科目区分リスト
    isOk = nyu0102d01Service.getSelectKamokuKbnList(commonGetSysCom, model, errorList);
    if (!isOk) {
      model.addAttribute("errorMessages", errorList);
      formNyukin.setErrorControl("errorControl");
      // エラーレベル
      formNyukin.setMsgErrorLevel(true);
      return true;
    }
    
    return true;
  }
  
  /**
   * クリア.
   * 
   * @param form フォーム
   */
  private void clear(FormNyu0102d03 form) {

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
    // 請求先コード
    form.setSeikyusakiCd("");
    // 退避用検索条件
    setFormToCond(form);

  }

  /**
   * 検索条件退避.
   * 
   * @param form フォーム
   */
  private void setFormToCond(FormNyu0102d03 form) {
    // ---------------------------------------------------------------------
    // 検索条件退避
    // ---------------------------------------------------------------------

    // 事業所セレクトボックス選択値
    form.setCondSelectedJigyoshoCd(form.getSelectedJigyoshoCd());
    // 請求先コード
    form.setCondSeikyusakiCd(form.getSeikyusakiCd());
  }
  
}
