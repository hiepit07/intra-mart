/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl
 * ファイル名:Mst0101d01Controller.java
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.component.UserData;
import jp.co.daitoku_sh.dfcm.common.controller.AbsController;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.ExportCSVMst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0101d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstUserInfoMst0101d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.SearchCondMst0101d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl.Mst0101d01Service;

/**
 * コントローラクラス
 * 
 * @author アクトブレーンベトナム コイー
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/mst/MST01-01D00/")
public class Mst0101d01Controller extends AbsController {

  // Serviceの定義
  @Autowired
  @Qualifier("mst0101d01Service")
  private Mst0101d01Service mst0101d01Service;

  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  /** ログ出力用. */
  private Dfcmlogger logger = new Dfcmlogger();

  /**
   * 初期表示処理(メニュー画面で「担当者マスタ」が選択された場合など).
   * 
   * @param model:モデル
   * @param formMst0101d01:フォーム
   * @param request：httpリクエスト
   * @param searchCondMst0101d01:一覧画面の情報
   * @return ページ表示（String（ＪＳＯＮ形））
   */
  @RequestMapping(value = "/")
  public String top(Model model, FormMst0101d01 formMst0101d01,
      HttpServletRequest request,
      @ModelAttribute("searchCondMst0101d01") SearchCondMst0101d01 searchCondMst0101d01) {

    // セッション情報の取得に失敗した場合、ログイン画面（DFCM-COM01-01D01）へ遷移する
    String path = Util.checkSession(model, request,
        CommonConst.MST0101D00_SCREEN_NAME);
    if (!path.equalsIgnoreCase("")) {
      return path;
    }

    // ビュー画面を呼ぶ
    String strScreen = "mst/mst0101d01";

    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();

    ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    ErrorMessages errMess = new ErrorMessages();

    // 最大件数値を取得
    CommonGetSystemCom commonGetSystemCom = new CommonGetSystemCom(
        mst0101d01Service.getCommonDao(), null, null, null);
    int intSearchMax = commonGetSystemCom.getCodeSearchMax();
    formMst0101d01.setSearchMax(intSearchMax);

    // default メッセージを取得する。
    mst0101d01Service.getDefaultMess(model);

    // -----------------------------
    // 共通部品を使って、業務日付を取得する
    // -----------------------------
    String strBussinessDate = null;
    strBussinessDate = mst0101d01Service.getBusinessDate();

    // 戻り値チェック
    // (1)[画面_hidden]業務日付 ≠ Nullの場合、正常とする
    // (2)[画面_hidden]業務日付 ＝ Nullの場合、エラーとする

    if (strBussinessDate == null || strBussinessDate.equalsIgnoreCase("")) {
      // 6-3 エラー処理へ
      // メッセージコード（COM015-E）
      lstErrMess = new ArrayList<ErrorMessages>();
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("業務日付の取得");
      errMess.setMessageContent(readPropertiesFileService.getMessage("COM015-E",
          paramMess));
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      // 共通部品【システム共通.エラー時のコントロール制御】
      formMst0101d01.setErrorControl("errorControl");
      return strScreen;
    } else {
      // hidden設定
      formMst0101d01.setBusinessDate(Integer.parseInt(strBussinessDate));
    }

    // セッションのデータを取得する
    Map<String, Object> sessionData = Util.getContentsFromSession(request);
    // システム管理者フラグ値を取得する
    formMst0101d01.setSysAdminFlag(String.valueOf(sessionData.get(
        "SysAdminFlg")));

    // ログイン事業所コード値を取得する
    formMst0101d01.setLoginJigyouShoCode(String.valueOf(sessionData.get(
        "JigyoshoCode")));

    // -----------------------------
    // [セッション]システム管理者フラグ ＝ '1' （システム管理者）の場合、事業所マスタデータ取得
    // -----------------------------
    if (formMst0101d01.getSysAdminFlag().equalsIgnoreCase(
        MstConst.SYS_ADMIN_FLG_VALID)) {
      if (!mst0101d01Service.setJigyo_Dll(formMst0101d01, model)) {
        // エラー（COM015-E)
        lstErrMess = new ArrayList<ErrorMessages>();
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("事業所マスタの取得");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM015-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        // 共通部品【システム共通.エラー時のコントロール制御】
        formMst0101d01.setErrorControl("errorControl");
        return strScreen;
      }
    }

    // -----------------------------
    // 共通部品を使って、利用権限名称を取得する
    // -----------------------------
    if (!mst0101d01Service.setRiyoKengen_Dll(model)) {
      // エラー COM009-E
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
      formMst0101d01.setErrorControl("errorControl");
      return strScreen;
    }

    // [入力]検索条件保持クラス ＝ NULL の場合、関数を呼び出し、ヘッダー部（検索条件入力エリア）を初期化する
    if (searchCondMst0101d01.getAuthCode() == null) {
      mst0101d01Service.initScreenNoConditional(formMst0101d01);
    } else {
      // [入力]検索条件保持クラス ≠ NULL の場合、ヘッダー部（検索条件入力エリア）に[入力]検索条件保持クラスをセットする
      mst0101d01Service.initScreenWithConditional(searchCondMst0101d01, formMst0101d01);
    }

    return strScreen;
  }

  /**
   * 一覧画面で最初に検索を実行する.
   * 
   * @param model:モデル
   * @param formMst0101d01:フォーム
   * @return ページ表示（String（ＪＳＯＮ形））
   * @throws JsonProcessingException:エラー画面
   */

  @RequestMapping(value = "/getSearchResultInit", method = RequestMethod.POST, 
      consumes = "application/x-www-form-urlencoded; charset=utf-8", 
      produces = "application/x-www-form-urlencoded; charset=utf-8")
  @ResponseBody
  public String getSearchResultInit(final Model model,
      final FormMst0101d01 formMst0101d01) throws JsonProcessingException {
    String jsonData = "";
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();

    // 関数を呼び出し、担当者一覧データを取得する
    List<MstUserInfoMst0101d02> lstMstUserInfoMst0101d2 = new ArrayList<MstUserInfoMst0101d02>();
    lstMstUserInfoMst0101d2 = mst0101d01Service.getSearchResult(formMst0101d01);
    int searchMax = formMst0101d01.getSearchMax() + 1;
    if (lstMstUserInfoMst0101d2 != null) {
      if (lstMstUserInfoMst0101d2.size() >= searchMax) {
        MstUserInfoMst0101d02 error = new MstUserInfoMst0101d02();
        paramMess = new ArrayList<String>();
        paramMess.add(String.valueOf(formMst0101d01.getSearchMax()));
        error.setType("searchMax");
        error.setMessage(readPropertiesFileService.getMessage("COM005-W",
            paramMess));
        lstMstUserInfoMst0101d2.add(error);
      }
      // 排他処理 検索時にシステム日付を取得し、画面に保持する。初期表示で検索する場合は、初期処理で実装する。
      mst0101d01Service.setHaitaDate(formMst0101d01);
      MstUserInfoMst0101d02 addHaita = new MstUserInfoMst0101d02();
      addHaita.setHaitaDate(formMst0101d01.getHaitaDate());
      addHaita.setHaitaTime(formMst0101d01.getHaitaTime());
      lstMstUserInfoMst0101d2.add(addHaita);
    }

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    try {
      jsonData = ow.writeValueAsString(lstMstUserInfoMst0101d2);
    } catch (JsonProcessingException e) {
      logger.error(e.getMessage());
      throw e;
    }

    return jsonData;
  }

  /**
   * 一覧画面で検索ボタンを押すと、検索を実行する.
   * 
   * @param model:モデル
   * @param request：httpリクエスト
   * @param formMst0101d01:フォーム
   * @return ページ表示（String（ＪＳＯＮ形））
   * @throws JsonProcessingException:エラー画面
   */
  @RequestMapping(value = "/getSearchResult", method = RequestMethod.POST,
      consumes = "application/x-www-form-urlencoded; charset=utf-8", 
      produces = "application/x-www-form-urlencoded; charset=utf-8")
  @ResponseBody
  public String getSearchResult(Model model,
      HttpServletRequest request, FormMst0101d01 formMst0101d01)
          throws JsonProcessingException {

    List<MstUserInfoMst0101d02> lstMstUserInfoMst0101d2 = new ArrayList<MstUserInfoMst0101d02>();
    String jsonData = "";
    Boolean checkInput = false;
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();

    // （1）事業所判定
    // [セッション]システム管理者フラグ ＝ '1'（システム管理者）の場合
    // [変数]事業所コード ＝ [画面]所属事業所.事業所コード
    // [セッション]システム管理者フラグ ＝ '0'（一般ユーザ）の場合
    // [変数]事業所コード ＝ [セッション]ログイン所属事業所コード
    if (!formMst0101d01.getSysAdminFlag().equalsIgnoreCase(
        MstConst.SYS_ADMIN_FLG_VALID)) {
      formMst0101d01.setDdlShozoku(formMst0101d01.getLoginJigyouShoCode());
    }

    // [画面]担当者コード ≠ NULL の場合
    String strUserCode = formMst0101d01.getTxtUserCode();
    UserData userData = null;
    if ((strUserCode != null) && (!strUserCode.equalsIgnoreCase(""))) {
      // （2）-1 [画面]担当者コードをゼロ編集する
      if (strUserCode.length() < 8) {
        formMst0101d01.setTxtUserCode(Util.addLeadingZeros(strUserCode, 8));
      }

      // 共通部品にて担当者情報を取得 【データ取得.担当者情報取得】
      userData = mst0101d01Service.getUserName(formMst0101d01.getTxtUserCode(),
          formMst0101d01.getDdlShozoku());

      if (userData.getUsr() == null) {
        // 該当レコードが存在しない場合、[変数]入力チェックフラグにTrueをセット
        checkInput = true;
      }
    }

    // [変数]入力チェックエラーフラグ ＝ True の場合、エラーとする
    if (checkInput) {
      lstMstUserInfoMst0101d2 = new ArrayList<MstUserInfoMst0101d02>();
      paramMess = new ArrayList<String>();
      paramMess.add("担当者マスタ");
      paramMess.add("入力された担当者");
      MstUserInfoMst0101d02 error = new MstUserInfoMst0101d02();
      error.setMessage(readPropertiesFileService.getMessage("COM009-E",
          paramMess));
      lstMstUserInfoMst0101d2.add(error);
    } else {
      // 関数を呼び出し、担当者一覧データを取得する
      lstMstUserInfoMst0101d2 = mst0101d01Service.getSearchResult(
          formMst0101d01);

      // 検索は結果がありません。
      if (lstMstUserInfoMst0101d2.size() <= 0) {
        // return "COM002-W";
        lstMstUserInfoMst0101d2 = new ArrayList<MstUserInfoMst0101d02>();
        MstUserInfoMst0101d02 error = new MstUserInfoMst0101d02();
        error.setType("body");
        error.setMessage(readPropertiesFileService.getMessage("COM025-E",
            null));
        lstMstUserInfoMst0101d2.add(error);
      } else {
        // クライアントに担当者氏名を送信する。
        if (userData != null) {
          if (userData.getUsr() != null) {
            MstUserInfoMst0101d02 addUserName = new MstUserInfoMst0101d02();
            addUserName.setUserNm(userData.getUsr().getUserNm());
            lstMstUserInfoMst0101d2.add(addUserName);
          }
        }
      }
    }
    int searchMax = formMst0101d01.getSearchMax() + 1;
    if (lstMstUserInfoMst0101d2 != null && lstMstUserInfoMst0101d2
        .size() >= searchMax) {
      MstUserInfoMst0101d02 error = new MstUserInfoMst0101d02();
      paramMess = new ArrayList<String>();
      paramMess.add(String.valueOf(formMst0101d01.getSearchMax()));
      error.setType("searchMax");
      error.setMessage(readPropertiesFileService.getMessage("COM005-W",
          paramMess));
      lstMstUserInfoMst0101d2.add(error);
    }

    // 排他処理 検索時にシステム日付を取得し、画面に保持する。初期表示で検索する場合は、初期処理で実装する。
    mst0101d01Service.setHaitaDate(formMst0101d01);
    MstUserInfoMst0101d02 addHaita = new MstUserInfoMst0101d02();
    addHaita.setHaitaDate(formMst0101d01.getHaitaDate());
    addHaita.setHaitaTime(formMst0101d01.getHaitaTime());
    lstMstUserInfoMst0101d2.add(addHaita);

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    try {
      jsonData = ow.writeValueAsString(lstMstUserInfoMst0101d2);
    } catch (JsonProcessingException e) {
      logger.error(e.getMessage());
      throw e;
    }
    return jsonData;
  }

  /**
   * CSVExport.
   * 
   * @param formMst0101d01:フォーム
   * @param request：httpリクエスト
   * @return ページ表示（String（ＪＳＯＮ形））
   * @throws Exception:エラー画面
   */
  @RequestMapping(value = "/exportCsv", method = RequestMethod.POST, 
      consumes = "application/x-www-form-urlencoded; charset=utf-8", 
      produces = "application/x-www-form-urlencoded; charset=utf-8")
  @ResponseBody
  public String exportCsv(FormMst0101d01 formMst0101d01,
      HttpServletRequest request) throws Exception {
    String jsonData = "";
    Boolean checkUserExist = true;
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();

    ExportCSVMst exportCsvMst0101d01 = new ExportCSVMst();

    // [画面]担当者コード ≠ NULL の場合
    String strUserCode = formMst0101d01.getTxtUserCode();
    UserData userData = null;
    if ((strUserCode != null) && (!strUserCode.equalsIgnoreCase(""))) {
      if (strUserCode.length() < 8) {
        formMst0101d01.setTxtUserCode(Util.addLeadingZeros(strUserCode, 8));
      }
      // 共通部品にて担当者情報を取得 【データ取得.担当者情報取得】
      userData = mst0101d01Service.getUserName(formMst0101d01.getTxtUserCode(),
          formMst0101d01.getDdlShozoku());

      if (userData.getUsr() == null) {
        // 該当レコードが存在しない場合、[変数]担当者存在チェックフラグにFalseをセット
        checkUserExist = false;
      }
    }

    // [変数]担当者存在チェックフラグ ＝ False の場合、エラーとする
    if (!checkUserExist) {
      exportCsvMst0101d01 = new ExportCSVMst();
      paramMess = new ArrayList<String>();
      paramMess.add("担当者マスタ");
      paramMess.add("入力された担当者");
      exportCsvMst0101d01.setMessage(readPropertiesFileService.getMessage(
          "COM009-E", paramMess));
      exportCsvMst0101d01.setType("COM009-E");
    } else {

      // 関数を呼び出し、担当者一覧データを取得する
      if (userData != null) {
        if (userData.getUsr() != null) {
          exportCsvMst0101d01.setStrName(userData.getUsr().getUserCode());
        }
      }
      String strExportCsv;
      try {
        strExportCsv = mst0101d01Service.exportCsvData(formMst0101d01);
      } catch (Exception e) {
        logger.error(e.getMessage());
        throw e;
      }
      if (strExportCsv == null) {
        exportCsvMst0101d01 = new ExportCSVMst();
        exportCsvMst0101d01.setMessage(readPropertiesFileService.getMessage(
            "COM025-E", null));
        exportCsvMst0101d01.setType("COM025-E");
      } else {
        exportCsvMst0101d01.setStrPathFile(strExportCsv);
      }
    }

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    try {
      jsonData = ow.writeValueAsString(exportCsvMst0101d01);
    } catch (JsonProcessingException e) {
      logger.error(e.getMessage());
      throw e;
    }

    return jsonData;
  }

  /**
   * 登録画面に遷移する.
   * 
   * @param rattrs:送信パラメーターの設定
   * @param formMst0101d01:フォーム
   * @return 画面表示
   */
  @RequestMapping(value = "proc", method = RequestMethod.POST)
  public String changePage(RedirectAttributes rattrs,
      FormMst0101d01 formMst0101d01) {
    // 新規モード
    // [変数]検索条件保持クラス
    SearchCondMst0101d01 searchCondMst0101d01 = new SearchCondMst0101d01();

    // [変数]担当者コード
    String strUserCode = "";

    // ヘッダ部の検索条件を[変数]検索条件保持クラスに格納する
    searchCondMst0101d01.setJigyoshoCode(formMst0101d01.getDdlShozoku());
    searchCondMst0101d01.setUserCode(formMst0101d01.getTxtUserCode());
    searchCondMst0101d01.setUserNm(formMst0101d01.getTxtUserName());
    searchCondMst0101d01.setAuthCode(formMst0101d01.getDdlRiyoKengen());
    if (formMst0101d01.isChkTorikeshiData()) {
      searchCondMst0101d01.setCancelData(MstConst.CHECK_OFF);
    } else {
      searchCondMst0101d01.setCancelData(MstConst.CHECK_ON);
    }
    if (formMst0101d01.isChkLock()) {
      searchCondMst0101d01.setUserStatus(MstConst.CHECK_OFF);
    } else {
      searchCondMst0101d01.setUserStatus(MstConst.CHECK_ON);
    }

    if (!formMst0101d01.getViewMode().equalsIgnoreCase(MstConst.SHINKI_MODE)) {
      strUserCode = formMst0101d01.getSelectUserCode();
    }
    // 検索条件を登録画面に送信する。
    rattrs.addFlashAttribute("searchCondMst0101d01", searchCondMst0101d01);
    // 選択した担当者コードを登録画面に送信する。
    rattrs.addFlashAttribute("userCode", strUserCode);
    // モード値を登録画面に送信する。
    rattrs.addFlashAttribute("viewMode", formMst0101d01.getViewMode());

    // 排他値を登録画面に送信する。
    rattrs.addFlashAttribute("haitaDate", formMst0101d01.getHaitaDate());
    rattrs.addFlashAttribute("haitaTime", formMst0101d01.getHaitaTime());

    return "redirect:/mst/MST01-01D02/";
  }
}
