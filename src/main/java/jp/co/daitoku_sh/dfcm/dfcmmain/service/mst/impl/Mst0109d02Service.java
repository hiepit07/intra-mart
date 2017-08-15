/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl
 * ファイル名:Mst0109d02Service.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/10/29
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/29 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCorrectKb;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCorrectKbKey;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.InputCheckCom;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0109d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstCustInfo0109d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.SearchCondMst0109d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl.Mst0109d02Dao;

/**
 * サービスクラス
 * 
 * @author アクトブレーンベトナム コイー
 * @version 1.0.0
 * @since 1.0.0
 */

@Service
public class Mst0109d02Service extends AbsService {

  @Autowired
  @Qualifier("commonDao")
  private CommonDao commonDao;

  @Autowired
  @Qualifier("mst0109d02Dao")
  private Mst0109d02Dao mst0109d02Dao;

  @Autowired
  private ApplicationContext appContext;
  
  /** ログ出力用. */
  private Dfcmlogger logger = new Dfcmlogger();
  
  /** トランザクション Commit / Rollback. */
  PlatformTransactionManager txManager;

  // メッセージPropertiesファイルを読む
  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

 

  /**
   * (登録画面)画面表示.
   * 
   * @param formMst0109d02:フォーム
   * @param model:モデル
   * @param searchCondMst0109d01:一覧画面の条件検索
   * @param viewMode:モード表示
   * @return　true:エラー　false：正常
   */
  public boolean init(FormMst0109d02 formMst0109d02, Model model,
      SearchCondMst0109d01 searchCondMst0109d01, String viewMode,
      String form1CustCode, String form1StKb, String haitaDate,
      String haitaTime, HttpServletRequest httpRequest) {
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();

    ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    ErrorMessages errMess = new ErrorMessages();

    // [入力]検索条件保持クラスを退避する
    formMst0109d02.setForm1JigyoshoCode(searchCondMst0109d01.getDdlShozoku());
    formMst0109d02.setForm1CustCode(searchCondMst0109d01.getTxtCustCode());
    formMst0109d02.setForm1CustName(searchCondMst0109d01.getTxtCustNmR());
    formMst0109d02.setForm1CorrectKb(searchCondMst0109d01.getTxtCorrectKb());
    formMst0109d02.setForm1ZeroDlvdatFlg(searchCondMst0109d01
        .getTxtZeroDlvdatFlg());
    formMst0109d02.setForm1StKb(searchCondMst0109d01.getChkStKb());
    formMst0109d02.setForm1StsCode(searchCondMst0109d01.getChkStsCode());
    formMst0109d02.setHaitaDate(haitaDate);
    formMst0109d02.setHaitaTime(haitaTime);
    formMst0109d02.setMode(viewMode);

    // 引数チェック
    // (1) 画面表示モードのチェック
    // (1-1) [入力]画面表示モード ＝ Null の場合、画面にエラーメッセージを表示する。
    if (viewMode == null ||  viewMode.equalsIgnoreCase("")) {
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
      formMst0109d02.setErrorControl("errorControl");
      return true;
    }

    // モード設定
    model.addAttribute("modeView", formMst0109d02.getMode());

    // default メッセージを取得する。
    this.getDefaultMess(model);

    // (2) [入力]画面表示モード = '2'（照会） or '3'（訂正） or '4'（取消） の時のみ、後続の引数チェックを行う。
    // (2-1) [入力]得意先コード ＝ Null の場合、画面にエラーメッセージを表示する。
    if (viewMode.equalsIgnoreCase(MstConst.SHOUKAI_MODE) || viewMode
        .equalsIgnoreCase(MstConst.TEISEI_MODE) || viewMode.equalsIgnoreCase(
            MstConst.TORIKESI_MODE)) {
      if (form1CustCode.equalsIgnoreCase("")) {
        // [変数]メッセージコード（COM026-E）
        lstErrMess = new ArrayList<ErrorMessages>();
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("得意先コード");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM026-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        formMst0109d02.setErrorControl("errorControl");
        return true;
      }
      if (form1StKb.equalsIgnoreCase("")) {
        // [変数]メッセージコード（COM026-E）
        lstErrMess = new ArrayList<ErrorMessages>();
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("訂正区分");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM026-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        formMst0109d02.setErrorControl("errorControl");
        return true;
      }
    }
    
    // 設定ファイル情報を取得する
    // (1) [画面_hidden]設定情報）得意先コード_未指定 ＝ 設定情報ファイル）得意先共通得意先コード
    formMst0109d02.setCustCodeNone(readPropertiesFileService.getSetting(
        "CUST_CODE_NONE"));

    // -----------------------------
    // 共通部品を使って、業務日付を取得する
    // -----------------------------
    String strBussinessDate;

    strBussinessDate = this.getBusinessDate();

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
      formMst0109d02.setErrorControl("errorControl");
      return true;
    }
    // hidden設定
    formMst0109d02.setBusinessDate(Integer.parseInt(strBussinessDate));

    // [入力]得意先コード、[入力]訂正区分にて訂正区分マスタ情報を取得する
    // （2）画面表示モード ≠ 1 の場合、訂正区分マスタ情報を取得する
    MstCorrectKb mstCorrectKb = null;
    if (!viewMode.equalsIgnoreCase(MstConst.SHINKI_MODE)) {
      mstCorrectKb = getCorrectKbInfo(form1CustCode, form1StKb);
      if (mstCorrectKb == null) {
        // [変数]メッセージコード（COM009-E）
        lstErrMess = new ArrayList<ErrorMessages>();
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("訂正区分マスタ");
        paramMess.add("訂正区分マスタ一覧画面で指定された訂正区分");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        formMst0109d02.setErrorControl("errorControl");
        return true;
      }
    }

    // [入力]画面表示モードにより、画面表示を制御する
    this.setView(model, viewMode, formMst0109d02, mstCorrectKb, form1CustCode,
        form1StKb);

    // セッションのデータを取得する
    Map<String, Object> sessionData = Util.getContentsFromSession(httpRequest);
    // システム管理者フラグ値を取得する
    formMst0109d02.setSysAdminFlag(String.valueOf(sessionData.get(
        CommonConst.LOGIN_USER_SYS_ADMIN_FLG)));

    // ログイン事業所コード値を取得する
    formMst0109d02.setLoginJigyouShoCode(String.valueOf(sessionData.get(
        CommonConst.LOGIN_USER_JIGYOSHO_CODE)));

    return false;
  }

  /**
   * (登録画面)クリアボタン処理
   * 
   * @param formMst0109d02:フォーム
   * @param model：モデル
   */
  public void clear(FormMst0109d02 formMst0109d02, Model model) {
    model.addAttribute("modeView", formMst0109d02.getMode());
    // default メッセージを取得する。
    this.getDefaultMess(model);
    if (formMst0109d02.getMode().equalsIgnoreCase(MstConst.SHINKI_MODE)) {
      // （2）-1 画面表示モード ＝ 1 の場合
      // [画面]設定区分 ＝ '得意先個別指定' 選択状態
      formMst0109d02.setChkStKb(MstConst.NOT_COMMON);

      // [画面]得意先コード ＝ NULL
      formMst0109d02.setTxtCustCode("");

      // [画面]得意先名称 ＝ NULL
      formMst0109d02.setLblCustNmRHidden("");

      // [画面]訂正区分 ＝ NULL
      formMst0109d02.setTxtCorrectKb("");

      // [画面]訂正理由 ＝ NULL
      formMst0109d02.setTxtCorrectCause("");

      // [画面]数量ゼロ納品データ連携 ＝ NULL
      formMst0109d02.setTxtZeroDlvdatFlg("");

      // [画面]備考 ＝ NULL
      formMst0109d02.setTxtBikou("");

      // [画面]状況コード ＝ NULL
      formMst0109d02.setTxtStsCode("");
    } else if (formMst0109d02.getMode().equalsIgnoreCase(
        MstConst.TEISEI_MODE)) {
      // メッセージのParam
      ArrayList<String> paramMess = new ArrayList<String>();

      ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
      ErrorMessages errMess = new ErrorMessages();
      MstCorrectKb mstCorrectKb = null;
      mstCorrectKb = getCorrectKbInfo(formMst0109d02.getTxtCustCode(),
          formMst0109d02.getTxtCorrectKb());
      if (mstCorrectKb == null) {
        // [変数]メッセージコード（COM009-E）
        lstErrMess = new ArrayList<ErrorMessages>();
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("訂正区分マスタ");
        paramMess.add("指定された訂正区分");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
      } else {
        if (mstCorrectKb.getStKb().equalsIgnoreCase("1")) {
          // [変数]訂正区分マスタ情報格納クラス.設定区分 ＝ '1'（得意先共通） の場合
          // [画面]設定区分 ＝ '共通設定' 選択状態
          formMst0109d02.setChkStKb(MstConst.IS_COMMON);
          formMst0109d02.setStKbSelect(MstConst.IS_COMMON);
        } else {
          // [変数]訂正区分マスタ情報格納クラス.設定区分 ＝ '2'（得意先個別） の場合
          // [画面]設定区分 ＝ '得意先個別設定' 選択状態
          formMst0109d02.setChkStKb(MstConst.NOT_COMMON);
          formMst0109d02.setStKbSelect(MstConst.NOT_COMMON);
        }
        // [画面]訂正理由 ＝ [変数]訂正区分マスタ情報格納クラス.訂正理由
        formMst0109d02.setTxtCorrectCause(mstCorrectKb.getCorrectCause());

        // [画面]数量ゼロ納品データ連携 ＝ [変数]訂正区分マスタ情報格納クラス.数量ゼロ納品データ連携
        formMst0109d02.setTxtZeroDlvdatFlg(mstCorrectKb.getZeroDlvdatFlg());

        // [画面]備考 ＝ [変数]訂正区分マスタ情報格納クラス.備考
        formMst0109d02.setTxtBikou(mstCorrectKb.getBikou());

        // [画面]状況コード ＝ [変数]訂正区分マスタ情報格納クラス.状況コード
        formMst0109d02.setTxtStsCode(mstCorrectKb.getStsCode());
      }
    }
  }

  /**
   * Defaultメッセージの取得.
   * 
   * @param model:モデル
   */
  public void getDefaultMess(Model model) {
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();

    DefaultMessages defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-I01");
    defaultMsg.setMessageTitle("確認メッセージ");
    paramMess = new ArrayList<String>();
    paramMess.add("登録");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-I", paramMess));
    ArrayList<DefaultMessages> defaultMsgLst = new ArrayList<DefaultMessages>();
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
   * 業務日付を取得する.
   * 
   * @return 業務日付
   */
  public String getBusinessDate() {
    String strBussiDate = null;
    // 共通部品初期化
    try {
      CommonGetSystemCom systemCom = new CommonGetSystemCom(commonDao, null,
          null, readPropertiesFileService);
      // 共通部品を呼ぶ
      strBussiDate = systemCom.getAplDate();
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
    return strBussiDate;
  }

  /**
   * 訂正区分マスタデータ取得.
   * 
   * @param strCustCode:得意先コード
   * @param strCorrectKb:訂正区分コード
   * @return　MstCorrectKb:訂正区分マスタ情報
   */
  public MstCorrectKb getCorrectKbInfo(String strCustCode,
      String strCorrectKb) {
    MstCorrectKb mstCorrectKb;
    MstCorrectKbKey mstCorrectKbKey = new MstCorrectKbKey();
    mstCorrectKbKey.setCorrectKb(strCorrectKb);
    mstCorrectKbKey.setCustCode(strCustCode);

    try {
      mstCorrectKb = mst0109d02Dao.getMstCorrectKbMapper().selectByPrimaryKey(
          mstCorrectKbKey);

      return mstCorrectKb;
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
  }

  /**
   * 画面は値を設定する.
   * @param model：モデル
   * @param viewMode:モード表示
   * @param formMst0109d02:フォーム
   * @param mstCorrectKb：訂正区分
   * @param form1CustCode:得意先コード
   * @param form1StKb:状況コード
   */
  public void setView(Model model, String viewMode,
      FormMst0109d02 formMst0109d02, MstCorrectKb mstCorrectKb,
      String form1CustCode, String form1StKb) {

    // （1）[入力]画面表示モード ＝ 1 の場合
    if (viewMode.equalsIgnoreCase(MstConst.SHINKI_MODE)) {
      // 新規モード
      // [画面]設定区分 ＝ '得意先個別指定' 選択状態
      formMst0109d02.setChkStKb(MstConst.NOT_COMMON);
      // [画面]状況コード ＝ '1'（登録）
      formMst0109d02.setTxtStsCode(MstConst.SHINKI_MODE);
    } else {
      // [画面]設定区分設定
      if (mstCorrectKb.getStKb().equalsIgnoreCase(MstConst.IS_COMMON_VAL)) {
        formMst0109d02.setChkStKb(MstConst.IS_COMMON);
        formMst0109d02.setStKbSelect(MstConst.IS_COMMON);
      } else {
        formMst0109d02.setChkStKb(MstConst.NOT_COMMON);
        formMst0109d02.setStKbSelect(MstConst.NOT_COMMON);
      }
      // [画面]得意先コード設定
      if (form1CustCode.equalsIgnoreCase(formMst0109d02.getCustCodeNone())) {
        formMst0109d02.setTxtCustCode("");
      } else {
        formMst0109d02.setTxtCustCode(form1CustCode);
      }

      // [画面]訂正区分 ＝ [入力]訂正区分
      formMst0109d02.setTxtCorrectKb(form1StKb);

      // [画面]訂正理由 ＝ [変数訂正区分マスタ情報格納クラス.訂正理由
      formMst0109d02.setTxtCorrectCause(mstCorrectKb.getCorrectCause());

      // [画面]数量ゼロ納品データ連携 ＝ [変数訂正区分マスタ情報格納クラス.数量ゼロ納品データ連携
      formMst0109d02.setTxtZeroDlvdatFlg(mstCorrectKb.getZeroDlvdatFlg());

      // [画面]備考 ＝ [変数訂正区分マスタ情報格納クラス.備考
      formMst0109d02.setTxtBikou(mstCorrectKb.getBikou());

      if (viewMode.equalsIgnoreCase(MstConst.TORIKESI_MODE)) {
        // [画面]状況コード ＝ '9'（取消）
        formMst0109d02.setTxtStsCode(MstConst.TORIKESHI);
      } else {
        // [画面]状況コード ＝ [変数訂正区分マスタ情報格納クラス.状況コード
        formMst0109d02.setTxtStsCode(mstCorrectKb.getStsCode());
      }
    }
  }

  /**
   * 全項目入力チェック処理.
   * 
   * @param formMst0109d02:フォーム
   * @param model:モデル
   * @return エラー true 普通:false
   */
  public boolean checkInput(FormMst0109d02 formMst0109d02, Model model) {
    String strError;
    String strErrorId = "";

    ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    ErrorMessages errMess = new ErrorMessages();
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();

    // 得意先コード
    if (formMst0109d02.getChkStKb().equalsIgnoreCase(MstConst.NOT_COMMON)) {
      strError = checkItem(formMst0109d02.getTxtCustCode(), false,
          InputCheckCom.TYPE_NUM, 7);
      if (strError != null) {
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("得意先コード");
        errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
            paramMess));
        lstErrMess.add(errMess);
        strErrorId += "txtCustCode" + MstConst.DELIMITER_ERROR;
      }
    }

    // 訂正区分
    strError = checkItem(formMst0109d02.getTxtCorrectKb(), true,
        InputCheckCom.TYPE_NUM, 4);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("訂正区分");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtCorrectKb" + MstConst.DELIMITER_ERROR;
    }

    // 訂正理由
    strError = checkItem(formMst0109d02.getTxtCorrectCause(), false,
        InputCheckCom.TYPE_EM, 20);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("訂正理由");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtCorrectCause" + MstConst.DELIMITER_ERROR;
    }

    // 数量ゼロ納品データ連携
    strError = checkItem(formMst0109d02.getTxtZeroDlvdatFlg(), false,
        InputCheckCom.TYPE_NUM, 1);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("数量ゼロ納品データ連携");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtZeroDlvdatFlg" + MstConst.DELIMITER_ERROR;
    }

    // 備考
    strError = checkItem(formMst0109d02.getTxtBikou(), false,
        InputCheckCom.TYPE_EM, 25);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("備考");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtBikou" + MstConst.DELIMITER_ERROR;
    }

    // 状況コード
    strError = checkItem(formMst0109d02.getTxtStsCode(), true,
        InputCheckCom.TYPE_NUM, 1);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("状況コード");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtStsCode" + MstConst.DELIMITER_ERROR;
    }

    // (4) [画面]得意先コード ＝ [画面_hidden]設定情報）得意先コード_未指定 の場合エラーとし、次の通り処理を行う。
    if (formMst0109d02.getTxtCustCode().equalsIgnoreCase(formMst0109d02
        .getCustCodeNone())) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("得意先コード");
      errMess.setMessageContent(readPropertiesFileService.getMessage("COM030-E",
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtCustCode" + MstConst.DELIMITER_ERROR;
    }

    // (5) [画面]数量ゼロ納品データ連携 ≠ NULL and ([画面]数量ゼロ納品データ連携 ≠ '1' or [画面]数量ゼロ納品データ連携
    // ≠ '2') の場合エラーとし、次の通り処理を行う。
    String strZeroDlvdatFlg = formMst0109d02.getTxtZeroDlvdatFlg();

    if (!strZeroDlvdatFlg.equalsIgnoreCase("") && (!strZeroDlvdatFlg.equalsIgnoreCase(
        MstConst.ZERODLVDATFLG_ON) && (!strZeroDlvdatFlg.equalsIgnoreCase(
            MstConst.ZERODLVDATFLG_OFF)))) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("数量ゼロ納品データ連携は１または２で入力してください");
      errMess.setMessageContent(readPropertiesFileService.getMessage("MST013-E",
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtZeroDlvdatFlg" + MstConst.DELIMITER_ERROR;
    }
    // (6) [画面]状況コード ≠ NULL and ([画面]状況コード ≠ '1' or [画面]状況コード ≠ '9')
    // の場合エラーとし、次の通り処理を行う。
    String strStsCode = formMst0109d02.getTxtStsCode();
    if (!strStsCode.equalsIgnoreCase("") && (!strStsCode.equalsIgnoreCase(MstConst.TOROKU)
        && (!strStsCode.equalsIgnoreCase(MstConst.TORIKESHI)))) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("状況コードは１または９で入力してください");
      errMess.setMessageContent(readPropertiesFileService.getMessage("MST013-E",
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtStsCode" + MstConst.DELIMITER_ERROR;
    }

    // (7) [画面]設定区分 ＝ 得意先個別設定 and [画面]得意先コード ＝ NULL の場合エラーとし、次の通り処理を行う。
    String strChkStKbSelect;
    if (formMst0109d02.getChkStKb() == null) {
      strChkStKbSelect = formMst0109d02.getStKbSelect();      
    } else {
      strChkStKbSelect = formMst0109d02.getChkStKb();
    }
    if (strChkStKbSelect.equalsIgnoreCase(MstConst.NOT_COMMON)
        && (formMst0109d02.getTxtCustCode() == null || formMst0109d02
            .getTxtCustCode().equalsIgnoreCase(""))) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("設定区分");
      paramMess.add("得意先個別指定");
      paramMess.add("得意先コード");
      errMess.setMessageContent(readPropertiesFileService.getMessage("MST010-E",
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtCustCode" + MstConst.DELIMITER_ERROR;
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
   * 入力チェック処理.
   * 
   * @param val:チェックデータ
   * @param emptyFlg:必須チェックフラグ
   * @param type:型
   * @param len:桁数
   * @return null: エラー
   */
  public String checkItem(String val, boolean emptyFlg, Integer type, int len) {
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
   * 全項目存在チェック処理.
   * 
   * @param formMst0109d02:フォーム
   * @param model:モデル
   * @return エラー true 普通:false
   */
  public boolean checkExist(FormMst0109d02 formMst0109d02, Model model) {
    String strErrorId = "";

    ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    ErrorMessages errMess = new ErrorMessages();
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();
    // マスタ参照項目の存在チェックを行う
    // （1）得意先コード
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("CustCode", formMst0109d02.getTxtCustCode());
    params.put("SysAdminFlag", formMst0109d02.getSysAdminFlag());
    params.put("JigyoshoCode", formMst0109d02.getLoginJigyouShoCode());
    List<MstCustInfo0109d02> lstMstCustInfo0109d02 = null;
    lstMstCustInfo0109d02 = mst0109d02Dao.getMst0109d02Mapper().getCustInfo(params);
    //①該当レコードが存在する場合、画面表示を行う
    if (lstMstCustInfo0109d02.size() > 0) {
      //[画面]得意先名称                   ＝     [変数]得意先名称格納クラス.得意先略称                          
      formMst0109d02.setLblCustNmRHidden(lstMstCustInfo0109d02.get(0).getCustNameR());      
    } else {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("得意先マスタ");
      paramMess.add("入力された得意先");
      errMess.setMessageContent(readPropertiesFileService.getMessage("COM009-E",
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtCustCode" + MstConst.DELIMITER_ERROR;
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
   * 排他、既存の訂正区分マスタ情報のチェック
   * 
   * @param formMst0109d02:フォーム
   * @param model:モデル
   * @return エラー true 普通:false
   */
  public boolean checkSpec(FormMst0109d02 formMst0109d02, Model model) {
    String strErrorId = "";

    ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    ErrorMessages errMess = new ErrorMessages();
    
    // （1） 画面表示モード ＝ '3' （訂正） or '4' （取消）の場合、排他チェックを行う。 共通仕様 9-(4) 適用
    String strMode = formMst0109d02.getMode();
    if (strMode.equalsIgnoreCase(MstConst.TEISEI_MODE) || strMode
        .equalsIgnoreCase(MstConst.TORIKESI_MODE)) {
      if (this.checkHaita(formMst0109d02.getTxtCustCode(), formMst0109d02
          .getTxtCorrectKb(), formMst0109d02.getHaitaDate(), formMst0109d02
              .getHaitaTime())) {
        errMess = new ErrorMessages();
        errMess.setMessageContent(readPropertiesFileService.getMessage("COM031-E",
            null));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("lstErrorID", strErrorId);
        return true;
      }
    }
    
    if (strMode.equalsIgnoreCase(MstConst.SHINKI_MODE)) {
      //（2）画面表示モード ＝ 1 の場合、訂正区分マスタ情報を新規登録する
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("CustCode", formMst0109d02.getTxtCustCode());
      params.put("CorrectKb", formMst0109d02.getTxtCorrectKb());
      String strCustCodeTop1 = null;
      strCustCodeTop1 = mst0109d02Dao.getMst0109d02Mapper().getCorrectInfo(params);
      if (strCustCodeTop1 != null) {
        // メッセージのParam
        ArrayList<String> paramMess = new ArrayList<String>();
        paramMess = new ArrayList<String>();
        paramMess.add("訂正区分");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM027-E", paramMess));
        strErrorId += "txtCustCode" + MstConst.DELIMITER_ERROR + "txtCorrectKb";
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("lstErrorID", strErrorId);      
        return true;
      }
    }
    
    return false;
  }
  
  /**
   * 登録処理.
   * 
   * @param formMst0109d02:フォーム
   * @param model:モデル
   * @param httpRequest：httpリクエスト
   * @throws Exception e
   */

  public void registerData(FormMst0109d02 formMst0109d02, Model model,
      HttpServletRequest httpRequest) throws Exception {
    String strMode = formMst0109d02.getMode();
    ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    ErrorMessages errMess = new ErrorMessages();    

    // セッションのデータを取得する
    Map<String, Object> sessionData = Util.getContentsFromSession(httpRequest);
    // システム管理者フラグ値を取得する
    String strLoginUserCode = String.valueOf(sessionData.get(CommonConst.LOGIN_USER_CODE));

    if (strMode.equalsIgnoreCase(MstConst.SHINKI_MODE)) {
      // （2）画面表示モード ＝ 1 の場合、訂正区分マスタ情報を新規登録する
      String strCustCode = null;
      String strStKb = null;
      // （2）-3 得意先コード判定
      // ①[画面]設定区分 ＝ '共通設定' の場合
      if (formMst0109d02.getChkStKb().equalsIgnoreCase(MstConst.IS_COMMON)) {
        // [変数]得意先コード ＝ [画面_hidden]設定情報）得意先コード_未指定
        strCustCode = formMst0109d02.getCustCodeNone();
        strStKb = MstConst.IS_COMMON_VAL;
      } else {
        // ②[画面]設定区分 ＝ '得意先個別設定' の場合
        if (formMst0109d02.getChkStKb().equalsIgnoreCase(MstConst.NOT_COMMON)) {
          // [変数]得意先コード ＝ [画面]得意先コード
          strCustCode = formMst0109d02.getTxtCustCode();
          strStKb = MstConst.NOT_COMMON_VAL;
        }
      }
      // Insert データ
      this.insertData(strCustCode, strStKb, strLoginUserCode, formMst0109d02);      
    } else if (strMode.equalsIgnoreCase(MstConst.TEISEI_MODE)) {
      // （3）画面表示モード ＝ 3 の場合、訂正区分マスタ情報を訂正更新する
      this.updateData(strLoginUserCode, formMst0109d02);
    } else if (strMode.equalsIgnoreCase(MstConst.TORIKESI_MODE)) {
      // （4）画面表示モード ＝ 4 の場合、訂正区分マスタ情報を取消更新する
      this.deleteData(strLoginUserCode, formMst0109d02);
    }
    ArrayList<String> paramMess = new ArrayList<String>();
    lstErrMess = new ArrayList<ErrorMessages>();
    errMess = new ErrorMessages();
    paramMess = new ArrayList<String>();
    paramMess.add("訂正区分マスタの登録");
    errMess.setMessageContent(readPropertiesFileService.getMessage(
        "COM002-I", paramMess));
    lstErrMess.add(errMess);
    
    //排他値設定
    String currentDate = DateUtil.getSysDate();
    String currentTime = DateUtil.getSysTime();
    formMst0109d02.setHaitaDate(currentDate);
    formMst0109d02.setHaitaTime(currentTime);
    
    model.addAttribute("errorMessages", lstErrMess);
    model.addAttribute("infoMessFlag", "true");
  }
  
  /**
   * 排他チェック.
   * 
   * @param strCustCode:得意先コード
   * @param strCorrectKb:訂正区分コード
   * @param strHaitaDate:排他日付
   * @param strHaitaTime:排他時間
   * @return boolean true: エラー false: 普通
   */
  public boolean checkHaita(String strCustCode, String strCorrectKb,
      String strHaitaDate, String strHaitaTime) {

    // 訂正情報取得
    MstCorrectKb mstCorrectKb = new MstCorrectKb();

    mstCorrectKb = this.getCorrectKbInfo(strCustCode, strCorrectKb);

    // 共通項目.修正年月日 ＋ 共通項目．修正時刻 > 表示時システム日付 の場合
    int intDateRegDb = Integer.parseInt(mstCorrectKb.getUpdYmd());
    int intDateHidden = Integer.parseInt(strHaitaDate);

    if (intDateRegDb > intDateHidden) {
      return true;
    } else if (intDateRegDb == intDateHidden) {
      intDateRegDb = Integer.parseInt(mstCorrectKb.getUpdTime());
      intDateHidden = Integer.parseInt(strHaitaTime);
      if (intDateRegDb > intDateHidden) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * データを組み込む.
   * @param strCustCode:得意先コード
   * @param strStKb：訂正区分コード
   * @param strLoginUserCode:ログインユーザコード
   * @param formMst0109d02：フォーム
   * @throws Exception e
   */
  public void insertData(String strCustCode, String strStKb,
      String strLoginUserCode, FormMst0109d02 formMst0109d02) throws Exception {
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    txManager = (PlatformTransactionManager) appContext.getBean("txManager");
    TransactionStatus status = null;

    MstCorrectKb mstCorrectKb = new MstCorrectKb();
    // [画面]設定区分
    mstCorrectKb.setStKb(strStKb);
    // [変数]得意先コード
    mstCorrectKb.setCustCode(strCustCode);
    // [画面]訂正区分
    mstCorrectKb.setCorrectKb(formMst0109d02.getTxtCorrectKb());
    // [画面]訂正理由
    mstCorrectKb.setCorrectCause(formMst0109d02.getTxtCorrectCause());
    // [画面]数量ゼロ納品データ連携
    mstCorrectKb.setZeroDlvdatFlg(formMst0109d02.getTxtZeroDlvdatFlg());
    // [画面]備考
    mstCorrectKb.setBikou(formMst0109d02.getTxtBikou());
    // [画面]状況コード
    mstCorrectKb.setStsCode(formMst0109d02.getTxtStsCode());

    mstCorrectKb = this.setCommonData(mstCorrectKb, strLoginUserCode,
        "MST01-09D02", true);
    try {
      status = txManager.getTransaction(def);
      mst0109d02Dao.getMstCorrectKbMapper().insert(mstCorrectKb);
      txManager.commit(status);
    } catch (Exception ex) {
      txManager.rollback(status);
      logger.error(ex.getMessage());
      throw ex;
    }
  }
  
  /**
   * データを更新する.
   * 
   * @param strLoginUserCode:ログインユーザコード
   * @param formMst0109d02：フォーム
   * @throws Exception e
   */
  public void updateData(String strLoginUserCode, FormMst0109d02 formMst0109d02)
      throws Exception {
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    txManager = (PlatformTransactionManager) appContext.getBean("txManager");
    TransactionStatus status = null;

    MstCorrectKb mstCorrectKb = new MstCorrectKb();
    // PrimaryKey
    mstCorrectKb.setCustCode(formMst0109d02.getTxtCustCode());
    mstCorrectKb.setCorrectKb(formMst0109d02.getTxtCorrectKb());

    // [画面]訂正理由
    mstCorrectKb.setCorrectCause(formMst0109d02.getTxtCorrectCause());
    // [画面]数量ゼロ納品データ連携
    mstCorrectKb.setZeroDlvdatFlg(formMst0109d02.getTxtZeroDlvdatFlg());
    // [画面]備考
    mstCorrectKb.setBikou(formMst0109d02.getTxtBikou());
    // [画面]状況コード
    mstCorrectKb.setStsCode(formMst0109d02.getTxtStsCode());
    mstCorrectKb = this.setCommonData(mstCorrectKb, strLoginUserCode,
        "MST01-09D02", false);
    try {
      status = txManager.getTransaction(def);
      mst0109d02Dao.getMstCorrectKbMapper().updateByPrimaryKeySelective(
          mstCorrectKb);
      txManager.commit(status);
    } catch (Exception ex) {
      txManager.rollback(status);
      logger.error(ex.getMessage());
      throw ex;
    }
  }
  
  /**
   * データを削除する.
   * 
   * @param strLoginUserCode:ログインユーザコード
   * @param formMst0109d02：フォーム
   * @throws Exception e
   */
  public void deleteData(String strLoginUserCode,
      FormMst0109d02 formMst0109d02) throws Exception {
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    txManager = (PlatformTransactionManager) appContext.getBean("txManager");
    TransactionStatus status = null;
    
    MstCorrectKb mstCorrectKb = new MstCorrectKb();
    // PrimaryKey
    mstCorrectKb.setCustCode(formMst0109d02.getTxtCustCode());
    mstCorrectKb.setCorrectKb(formMst0109d02.getTxtCorrectKb());
    
    // [画面]状況コード
    mstCorrectKb.setStsCode(MstConst.TORIKESHI);
    
    mstCorrectKb = this.setCommonData(mstCorrectKb, strLoginUserCode,
        "MST01-09D02", false);
    
    try {
      status = txManager.getTransaction(def);
      mst0109d02Dao.getMstCorrectKbMapper().updateByPrimaryKeySelective(
          mstCorrectKb);
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
   * @param data:訂正区分データ
   * @param strUserId:ユーザID
   * @param strProgId:プログラムID
   * @param isNew:新規／更新
   * @return 訂正区分データ
   */
  public MstCorrectKb setCommonData(MstCorrectKb data, String strUserId,
      String strProgId, boolean isNew) {

    String currentDate = DateUtil.getSysDate();
    String currentTime = DateUtil.getSysTime();

    if (isNew) {
      data.setInsUserid(strUserId);
      data.setInsPgid(strProgId);
      data.setInsYmd(currentDate);
      data.setInsTime(currentTime);
    }

    data.setUpdUserid(strUserId);
    data.setUpdPgid(strProgId);
    data.setUpdYmd(currentDate);
    data.setUpdTime(currentTime);

    return data;
  }      
}
