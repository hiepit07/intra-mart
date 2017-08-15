/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl
 * ファイル名:Mst0104d01Controller.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/09/21
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/21 1.00 ABV)NhungMa 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.common.controller.AbsController;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.ExportCSVMst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0104d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstCourseInfoMst0104d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.SearchCondMst0104d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl.Mst0104d01Service;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;

/**
 * コントローラクラス
 * 
 * @author NhungMa
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/mst/MST01-04D00/")
public class Mst0104d01Controller extends AbsController {

  // Serviceの定義
  @Autowired
  @Qualifier("mst0104d01Service")
  private Mst0104d01Service mst0104d01Service;

  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  /** ログ出力用 */
  private Dfcmlogger logger = new Dfcmlogger();
  
  /**
   * 初期表示処理
   * @param model:モデル
   * @param formMst0104d01:フォーム
   * @param request:httpリクエスト
   * @param searchCondMst0104d01:一覧画面の情報
   * @return string
   * @throws Exception （例外が発生するクラス名を記述する）
   */
  @RequestMapping(value = "/")
  public String top(Model model, FormMst0104d01 formMst0104d01,
      HttpServletRequest request,
      @ModelAttribute("searchCondMst0104d01") SearchCondMst0104d01 searchCondMst0104d01)
          throws Exception {

    // セッション情報の取得に失敗した場合、ログイン画面（DFCM-COM01-01D01）へ遷移する
    String path = Util.checkSession(model, request,
        CommonConst.MST0104D00_SCREEN_NAME);
    if (!path.equalsIgnoreCase("")) {
      return path;
    }
    CommonGetSystemCom sysCom = new CommonGetSystemCom(mst0104d01Service
        .getCommonDao(), null, null, readPropertiesFileService);
    int searchMax = sysCom.getCodeSearchMax();
    formMst0104d01.setSearchMax(searchMax);

    // セッションのデータを取得する
    Map<String, Object> sessionData = Util.getContentsFromSession(request);

    // システム管理者フラグ値を取得する
    String sysAdminFlag = String.valueOf(sessionData.get(
        CommonConst.LOGIN_USER_SYS_ADMIN_FLG));
    formMst0104d01.setSysAdminFlag(sysAdminFlag);

    // ログイン事業所コード値を取得する
    String loginJigyouShoCode = String.valueOf(sessionData.get(
        CommonConst.LOGIN_USER_JIGYOSHO_CODE));
    formMst0104d01.setLoginJigyouShoCode(loginJigyouShoCode);

    // ビュー画面を呼ぶ
    String strScreen = "mst/mst0104d01";
    
    ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();
    ErrorMessages errMess = new ErrorMessages();

    // default メッセージを取得する。
    mst0104d01Service.getDefaultMess(model);

    // -----------------------------
    // 共通部品を使って、業務日付を取得する
    // -----------------------------
    String bussinessDate = null;

    bussinessDate = sysCom.getAplDate();
    // 戻り値チェック
    // (1)[画面_hidden]業務日付 ≠ Nullの場合、正常とする
    // (2)[画面_hidden]業務日付 ＝ Nullの場合、エラーとする

    if (bussinessDate == null) {
      // 6-3 エラー処理へ
      // 共通部品を使って、エラー時の画面制御を行う
      // メッセージコード（COM015-E）
      lstErrMess = new ArrayList<ErrorMessages>();
      paramMess = new ArrayList<String>();
      errMess = new ErrorMessages();
      paramMess.add("業務日付の取得");
      errMess.setMessageContent(readPropertiesFileService.getMessage("COM015-E",
          paramMess));
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      // 共通部品【システム共通.エラー時のコントロール制御】
      formMst0104d01.setErrorControl("errorControl");
      return strScreen;
    } else {
      // hidden設定
      formMst0104d01.setBusinessDate(Integer.parseInt(bussinessDate));
    }

    // -----------------------------
    // [セッション]システム管理者フラグ ＝ '1' （システム管理者）の場合、事業所マスタデータ取得
    // -----------------------------
    if (formMst0104d01.getSysAdminFlag().equalsIgnoreCase(
        CommonConst.SYS_ADMIN_FLG_VALID)) {
      if (!mst0104d01Service.setJigyo(formMst0104d01, model)) {
        // （COM015-E）
        lstErrMess = new ArrayList<ErrorMessages>();
        paramMess = new ArrayList<String>();
        errMess = new ErrorMessages();
        paramMess.add("事業所マスタ");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM015-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        // 共通部品【システム共通.エラー時のコントロール制御】
        formMst0104d01.setErrorControl("errorControl");
        return strScreen;
      }
    }

    // [セッション]ログイン所属事業所コード ＝" 1"
    // [入力]検索条件保持クラス ＝ NULL の場合、関数を呼び出し、ヘッダー部（検索条件入力エリア）を初期化する
    if (searchCondMst0104d01.getHaisoKb() == null) {
       mst0104d01Service.initScreenNoConditional(formMst0104d01);
    } else {
      // [入力]検索条件保持クラス ≠ NULL の場合、ヘッダー部（検索条件入力エリア）に[入力]検索条件保持クラスをセットする
      mst0104d01Service.initScreenWithConditional(searchCondMst0104d01, formMst0104d01);
    }

    return strScreen;
  }

  /**
   * 登録画面に遷移する。
   * @param rattrs:送信パラメーターの設定
   * @param formMst0104d01:フォーム
   * @return String ← 画面
   */
  @RequestMapping(value = "proc", method = RequestMethod.POST)
  public String changePage(RedirectAttributes rattrs,
      FormMst0104d01 formMst0104d01) {
    // 新規モード
    // [変数]検索条件保持クラス
    SearchCondMst0104d01 searchCondMst0104d01 = new SearchCondMst0104d01();

    // [変数]担当者コード
    String strJigyoCode = "";
    String strCourseCode = "";

    searchCondMst0104d01.setJigyoCode(formMst0104d01.getDdlJigyoCode());
    searchCondMst0104d01.setCourseCode(formMst0104d01.getTxtCourseCode());
    searchCondMst0104d01.setCourseName(formMst0104d01.getTxtCourseName());
    searchCondMst0104d01.setHaisoKb(formMst0104d01.getTxtHaisoKb());

    if (formMst0104d01.isChkCancellationData()) {
      searchCondMst0104d01.setCancelData(MstConst.CHECK_OFF);
    } else {
      searchCondMst0104d01.setCancelData(MstConst.CHECK_ON);
    }

    if (!formMst0104d01.getViewMode().equalsIgnoreCase(MstConst.SHINKI_MODE)) {
      strJigyoCode = formMst0104d01.getSelectJigyoCode();
      strCourseCode = formMst0104d01.getSelectCourseCode();
    }
    // 検索条件を登録画面に送信する。
    rattrs.addFlashAttribute("searchCondMst0104d01", searchCondMst0104d01);
    // 選択した担当者コードを登録画面に送信する。
    rattrs.addFlashAttribute("courseCode", strCourseCode);
    rattrs.addFlashAttribute("jigyoCode", strJigyoCode);
    // モード値を登録画面に送信する。
    rattrs.addFlashAttribute("viewMode", formMst0104d01.getViewMode());
    // 排他値を登録画面に送信する。
    rattrs.addFlashAttribute("haitaDate", formMst0104d01.getHaitaDate());
    rattrs.addFlashAttribute("haitaTime", formMst0104d01.getHaitaTime());
    return "redirect:/mst/MST01-04D02/";
  }
 
  /**
   * 一覧画面で最初に検索を実行する
   * @param model:モデル
   * @param formMst0104d01:フォーム
   * @return String（ＪＳＯＮ形）
   */
  @RequestMapping(value = "/getSearchResultInit", method = RequestMethod.POST,
      consumes = "application/x-www-form-urlencoded; charset=utf-8",
      produces = "application/x-www-form-urlencoded; charset=utf-8")
  @ResponseBody
  public String getSearchResultInit(Model model,
      FormMst0104d01 formMst0104d01) {
    String jsonData = "";
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();

    // 関数を呼び出し、担当者一覧データを取得する.
    List<MstCourseInfoMst0104d01> lstMstCourseInfoMst0104d01 =
        new ArrayList<MstCourseInfoMst0104d01>();
    lstMstCourseInfoMst0104d01 = mst0104d01Service.getSearchResult(
        formMst0104d01);
    int searchMax = formMst0104d01.getSearchMax() + 1;
    if (lstMstCourseInfoMst0104d01 != null) {
      if (lstMstCourseInfoMst0104d01.size() >= searchMax) {
        MstCourseInfoMst0104d01 error = new MstCourseInfoMst0104d01();
        paramMess = new ArrayList<String>();
        paramMess.add("" + formMst0104d01.getSearchMax());
        error.setType("searchMax");
        error.setMessage(readPropertiesFileService.getMessage("COM005-W",
            paramMess));
        lstMstCourseInfoMst0104d01.add(error);
      }
    }
    // 排他処理 検索時にシステム日付を取得し、画面に保持する。初期表示で検索する場合は、初期処理で実装する。
    mst0104d01Service.setHaitaDate(formMst0104d01);
    MstCourseInfoMst0104d01 addHaita = new MstCourseInfoMst0104d01();
    addHaita.setHaitaDate(formMst0104d01.getHaitaDate());
    addHaita.setHaitaTime(formMst0104d01.getHaitaTime());
    if (lstMstCourseInfoMst0104d01 != null) {
      lstMstCourseInfoMst0104d01.add(addHaita);
    }

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    try {
      jsonData = ow.writeValueAsString(lstMstCourseInfoMst0104d01);
    } catch (JsonProcessingException e) {
      logger.error(e.getMessage());
    }

    return jsonData;
  }

  /**
   * 一覧画面で検索ボタンを押すと、検索を実行する.
   * 
   * @param model:モデル
   * @param formMst0104d01:フォーム
   * @return String（ＪＳＯＮ形）
   * @throws JsonProcessingException:エラー画面
   */
  @RequestMapping(value = "/getSearchResult", method = RequestMethod.POST,
      consumes = "application/x-www-form-urlencoded; charset=utf-8",
      produces = "application/x-www-form-urlencoded; charset=utf-8")
  @ResponseBody
  public String getSearchResult(Model model,
      FormMst0104d01 formMst0104d01) throws JsonProcessingException {
    List<MstCourseInfoMst0104d01> lstMstCourseInfoMst0104d01 = null;
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();
    String jsonData = "";
    // コース一覧データを取得する
    lstMstCourseInfoMst0104d01 = mst0104d01Service.getSearchResult(
        formMst0104d01);
    // [変数]コース情報格納クラス ＝ NULL の場合、コース一覧の画面表示を行わない
    if (lstMstCourseInfoMst0104d01 == null || lstMstCourseInfoMst0104d01
        .size() <= MstConst.SIZE_ZERO) {
      lstMstCourseInfoMst0104d01 = new ArrayList<MstCourseInfoMst0104d01>();
      MstCourseInfoMst0104d01 error = new MstCourseInfoMst0104d01();
      error.setType("body");
      error.setMessage(readPropertiesFileService.getMessage("COM025-E", null));
      lstMstCourseInfoMst0104d01.add(error);
    }
    int searchMax = formMst0104d01.getSearchMax() + 1;
    if (lstMstCourseInfoMst0104d01 != null) {
      if (lstMstCourseInfoMst0104d01.size() >= searchMax) {
        MstCourseInfoMst0104d01 error = new MstCourseInfoMst0104d01();
        paramMess = new ArrayList<String>();
        paramMess.add("" + formMst0104d01.getSearchMax());
        error.setType("searchMax");
        error.setMessage(readPropertiesFileService.getMessage("COM005-W",
            paramMess));
        lstMstCourseInfoMst0104d01.add(error);
      }
    }
    // 排他処理 検索時にシステム日付を取得し、画面に保持する。初期表示で検索する場合は、初期処理で実装する。
    mst0104d01Service.setHaitaDate(formMst0104d01);
    MstCourseInfoMst0104d01 addHaita = new MstCourseInfoMst0104d01();
    addHaita.setHaitaDate(formMst0104d01.getHaitaDate());
    addHaita.setHaitaTime(formMst0104d01.getHaitaTime());
    if (lstMstCourseInfoMst0104d01 != null) {
      lstMstCourseInfoMst0104d01.add(addHaita);
    }

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    try {
      jsonData = ow.writeValueAsString(lstMstCourseInfoMst0104d01);
    } catch (JsonProcessingException e) {
      logger.error(e.getMessage());
      throw e;
    }
    return jsonData;
  }

  /**
   * CSV Export
   * @param formMst0104d01:フォーム
   * @return ページ表示（String（ＪＳＯＮ形））
   * @throws Exception:エラー画面
   */
  @RequestMapping(value = "/exportCsv", method = RequestMethod.POST,
      consumes = "application/x-www-form-urlencoded; charset=utf-8",
      produces = "application/x-www-form-urlencoded; charset=utf-8")
  @ResponseBody
  public String exportCsv(FormMst0104d01 formMst0104d01)
      throws Exception {
    String jsonData = "";
    ExportCSVMst exportCsv = new ExportCSVMst();
    String strExportCsv = "";
    try {
      strExportCsv = mst0104d01Service.exportDataCsv(formMst0104d01);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    if (strExportCsv == null) {
      exportCsv = new ExportCSVMst();
      exportCsv.setMessage(readPropertiesFileService.getMessage(
          "COM025-E", null));
    } else {
      exportCsv.setStrPathFile(strExportCsv);
    }

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    try {
      jsonData = ow.writeValueAsString(exportCsv);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }
    return jsonData;
  }
}