/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl
 * ファイル名:Mst0109d01Controller.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/10/20
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/20 1.00 ABV)コイー 新規開発
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
import jp.co.daitoku_sh.dfcm.common.controller.AbsController;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.ExportCSVMst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0109d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstCorrectKbMst0109d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.SearchCondMst0109d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl.Mst0109d01Service;

/**
 * コントローラクラス
 * 
 * @author アクトブレーンベトナム コイー
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/mst/MST01-09D00/")
public class Mst0109d01Controller extends AbsController {

  // Serviceの定義
  @Autowired
  @Qualifier("mst0109d01Service")
  private Mst0109d01Service mst0109d01Service;

  /** Propertiesファイルを読む. */
  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  /** ログ出力用. */
  private Dfcmlogger logger = new Dfcmlogger();

  /**
   * 初期表示処理(メニュー画面で「訂正マスタ」が選択された場合など).
   * 
   * @param model:モデル
   * @param request:httpリクエスト
   * @return 画面表示
   */
  @RequestMapping(value = "/")
  public String top(Model model, HttpServletRequest request,
      FormMst0109d01 formMst0109d01,
      @ModelAttribute("searchCondMst0109d01") SearchCondMst0109d01 searchCondMst0109d01) {

    // ビュー画面を呼ぶ
    String strScreen = "mst/mst0109d01";

    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();

    ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    ErrorMessages errMess = new ErrorMessages();

    // セッション情報の取得に失敗した場合、ログイン画面（DFCM-COM01-01D01）へ遷移する
    String path = Util.checkSession(model, request,
        CommonConst.MST0109D00_SCREEN_NAME);
    if (!path.equalsIgnoreCase("")) {
      return path;
    }
    // default メッセージを取得する。
    mst0109d01Service.getDefaultMess(model);

    // 最大件数値を取得
    CommonGetSystemCom commonGetSystemCom = new CommonGetSystemCom(
        mst0109d01Service.getCommonDao(), null, null, null);
    int intSearchMax = commonGetSystemCom.getCodeSearchMax();
    formMst0109d01.setSearchMax(intSearchMax);

    // 設定ファイル情報を取得する
    formMst0109d01.setCustCodeNone(readPropertiesFileService.getSetting(
        "CUST_CODE_NONE"));

    // 共通部品を使って、業務日付を取得する
    String strBussinessDate = null;
    strBussinessDate = mst0109d01Service.getBusinessDate();

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
      formMst0109d01.setErrorControl("errorControl");
      return strScreen;
    } else {
      // hidden設定
      formMst0109d01.setBusinessDate(Integer.parseInt(strBussinessDate));
    }

    // セッションのデータを取得する
    Map<String, Object> sessionData = Util.getContentsFromSession(request);
    // システム管理者フラグ値を取得する
    formMst0109d01.setSysAdminFlag(String.valueOf(sessionData.get(
        CommonConst.LOGIN_USER_SYS_ADMIN_FLG)));
    // ログイン事業所コード値を取得する
    formMst0109d01.setLoginJigyouShoCode(String.valueOf(sessionData.get(
        CommonConst.LOGIN_USER_JIGYOSHO_CODE)));

    // [セッション]システム管理者フラグ ＝ '1' （システム管理者）の場合、事業所コンボの作成を行う
    if (formMst0109d01.getSysAdminFlag().equalsIgnoreCase(
        MstConst.SYS_ADMIN_FLG_VALID)) {
      if (!mst0109d01Service.setJigyo_Dll(formMst0109d01, model)) {
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
        formMst0109d01.setErrorControl("errorControl");
        return strScreen;
      }
    }
    // 入力]検索条件保持クラス ＝ NULL の場合、関数を呼び出し、ヘッダー部（検索条件入力エリア）を初期化する
    if (searchCondMst0109d01.getTxtCustCode() == null) {
      mst0109d01Service.initScreenNoConditional(formMst0109d01);
    } else {
      mst0109d01Service.initScreenWithConditional(searchCondMst0109d01,
          formMst0109d01);
    }
    return strScreen;
  }

  /**
   * 一覧画面で最初に検索を実行する.
   * 
   * @param model:モデル
   * @param formMst0109d01:フォーム
   * @return ページ表示（String（ＪＳＯＮ形））
   * @throws JsonProcessingException:エラー画面
   */

  @RequestMapping(value = "/getSearchResultInit", method = RequestMethod.POST, 
      consumes = "application/x-www-form-urlencoded; charset=utf-8", 
      produces = "application/x-www-form-urlencoded; charset=utf-8")
  @ResponseBody
  public String getSearchResultInit(final Model model,
      final FormMst0109d01 formMst0109d01) throws JsonProcessingException {
    String jsonData = "";
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();
    List<MstCorrectKbMst0109d01> lstMstCorrectKbMst0109d01 = 
        new ArrayList<MstCorrectKbMst0109d01>();

    lstMstCorrectKbMst0109d01 = mst0109d01Service.getSearchResult(formMst0109d01);
    int searchMax = formMst0109d01.getSearchMax() + 1;
    if (lstMstCorrectKbMst0109d01 != null) {
      if (lstMstCorrectKbMst0109d01.size() >= searchMax) {
        MstCorrectKbMst0109d01 error = new MstCorrectKbMst0109d01();
        paramMess = new ArrayList<String>();
        paramMess.add(String.valueOf(formMst0109d01.getSearchMax()));
        error.setType("searchMax");
        error.setMessage(readPropertiesFileService.getMessage("COM005-W", paramMess));
        lstMstCorrectKbMst0109d01.add(error);
      }
    }
    // 排他処理 検索時にシステム日付を取得し、画面に保持する。初期表示で検索する場合は、初期処理で実装する。
    mst0109d01Service.setHaitaDate(formMst0109d01);
    MstCorrectKbMst0109d01 addHaita = new MstCorrectKbMst0109d01();
    addHaita.setHaitaDate(formMst0109d01.getHaitaDate());
    addHaita.setHaitaTime(formMst0109d01.getHaitaTime());
    if (lstMstCorrectKbMst0109d01 == null) {
      lstMstCorrectKbMst0109d01 = new ArrayList<MstCorrectKbMst0109d01>();
    }
    lstMstCorrectKbMst0109d01.add(addHaita);

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    try {
      jsonData = ow.writeValueAsString(lstMstCorrectKbMst0109d01);
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
   * @param formMst0109d01:フォーム
   * @return ページ表示（String（ＪＳＯＮ形））
   * @throws JsonProcessingException:エラー画面
   */
  @RequestMapping(value = "/getSearchResult", method = RequestMethod.POST, 
      consumes = "application/x-www-form-urlencoded; charset=utf-8", 
      produces = "application/x-www-form-urlencoded; charset=utf-8")
  @ResponseBody
  public String getSearchResult(Model model, HttpServletRequest request,
      FormMst0109d01 formMst0109d01) throws JsonProcessingException {

    String jsonData = "";
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();
    List<MstCorrectKbMst0109d01> lstMstCorrectKbMst0109d01 = 
        new ArrayList<MstCorrectKbMst0109d01>();

    // セッションのデータを取得する
    Map<String, Object> sessionData = Util.getContentsFromSession(request);
    // ログイン事業所コード値を取得する
    formMst0109d01.setLoginJigyouShoCode(String.valueOf(sessionData.get(
        CommonConst.LOGIN_USER_JIGYOSHO_CODE)));
    // 入力チェック
    MstCorrectKbMst0109d01 mstCorrectKbMst0109d01 = null;

    mstCorrectKbMst0109d01 = mst0109d01Service.checkInput(formMst0109d01);
    if (mstCorrectKbMst0109d01 == null) {
      // 存在チェック
      mstCorrectKbMst0109d01 = mst0109d01Service.checkExist(formMst0109d01);
      if (mstCorrectKbMst0109d01 == null) {
        // 関数を呼び出し、訂正区分マスタ一覧データを取得する
        lstMstCorrectKbMst0109d01 = mst0109d01Service.getSearchResult(formMst0109d01);
        // 検索は結果がありません。
        if (lstMstCorrectKbMst0109d01.size() <= 0) {
          // return "COM002-W";
          mstCorrectKbMst0109d01 = new MstCorrectKbMst0109d01();
          mstCorrectKbMst0109d01.setType("body");
          mstCorrectKbMst0109d01.setMessage(readPropertiesFileService.getMessage("COM025-E", null));
          lstMstCorrectKbMst0109d01.add(mstCorrectKbMst0109d01);
        } else {
          if (formMst0109d01.getTxtCustNmR() != null) {
            mstCorrectKbMst0109d01 = new MstCorrectKbMst0109d01();
            mstCorrectKbMst0109d01.setCustNmR(formMst0109d01.getLblCustNmRHidden());
            lstMstCorrectKbMst0109d01.add(mstCorrectKbMst0109d01);
          }
          int searchMax = formMst0109d01.getSearchMax() + 1;
          if (lstMstCorrectKbMst0109d01.size() >= searchMax) {
            mstCorrectKbMst0109d01 = new MstCorrectKbMst0109d01();
            paramMess = new ArrayList<String>();
            paramMess.add(String.valueOf(formMst0109d01.getSearchMax()));
            mstCorrectKbMst0109d01.setType("searchMax");
            mstCorrectKbMst0109d01.setMessage(readPropertiesFileService.getMessage("COM005-W",
                paramMess));
            lstMstCorrectKbMst0109d01.add(mstCorrectKbMst0109d01);
          }

          // 排他処理 検索時にシステム日付を取得し、画面に保持する。初期表示で検索する場合は、初期処理で実装する。
          mst0109d01Service.setHaitaDate(formMst0109d01);
          mstCorrectKbMst0109d01 = new MstCorrectKbMst0109d01();
          mstCorrectKbMst0109d01.setHaitaDate(formMst0109d01.getHaitaDate());
          mstCorrectKbMst0109d01.setHaitaTime(formMst0109d01.getHaitaTime());
          lstMstCorrectKbMst0109d01.add(mstCorrectKbMst0109d01);
        }
      } else {
        // 存在チェックエラー
        lstMstCorrectKbMst0109d01.add(mstCorrectKbMst0109d01);
      }
    } else {
      // 入力チェックエラー
      lstMstCorrectKbMst0109d01.add(mstCorrectKbMst0109d01);
    }

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    try {
      jsonData = ow.writeValueAsString(lstMstCorrectKbMst0109d01);
    } catch (JsonProcessingException e) {
      logger.error(e.getMessage());
      throw e;
    }

    return jsonData;
  }

  /**
   * CSVExport.
   * 
   * @param formMst0109d01:フォーム
   * @param request：httpリクエスト
   * @return ページ表示（String（ＪＳＯＮ形））
   * @throws Exception:エラー画面
   */
  @RequestMapping(value = "/exportCsv", method = RequestMethod.POST, 
      consumes = "application/x-www-form-urlencoded; charset=utf-8", 
      produces = "application/x-www-form-urlencoded; charset=utf-8")
  @ResponseBody
  public String exportCsv(FormMst0109d01 formMst0109d01, HttpServletRequest request)
      throws Exception {

    String jsonData = "";

    ExportCSVMst exportCsvMst0109d01 = new ExportCSVMst();

    // セッションのデータを取得する
    Map<String, Object> sessionData = Util.getContentsFromSession(request);
    // ログイン事業所コード値を取得する
    formMst0109d01.setLoginJigyouShoCode(String.valueOf(sessionData.get(
        CommonConst.LOGIN_USER_JIGYOSHO_CODE)));
    // 入力チェック
    MstCorrectKbMst0109d01 mstCorrectKbMst0109d01 = null;

    mstCorrectKbMst0109d01 = mst0109d01Service.checkInput(formMst0109d01);

    if (mstCorrectKbMst0109d01 == null) {
      // 存在チェック
      mstCorrectKbMst0109d01 = mst0109d01Service.checkExist(formMst0109d01);
      if (mstCorrectKbMst0109d01 == null) {
        // 関数を呼び出し、訂正区分マスタ一覧データを取得する
        String strExportCsv;
        try {
          strExportCsv = mst0109d01Service.exportCsvData(formMst0109d01);
        } catch (Exception e) {
          logger.error(e.getMessage());
          throw e;
        }

        if (strExportCsv == null) {
          exportCsvMst0109d01.setMessage(readPropertiesFileService.getMessage(
              "COM025-E", null));
        } else {
          exportCsvMst0109d01.setStrPathFile(strExportCsv);
          exportCsvMst0109d01.setStrName(formMst0109d01.getLblCustNmRHidden());
        }

      } else {
        // 存在チェックエラー
        exportCsvMst0109d01.setMessage(mstCorrectKbMst0109d01.getMessage());
        // 項目ID
        exportCsvMst0109d01.setType(mstCorrectKbMst0109d01.getLstId());
      }
    } else {
      // 入力チェックエラー
      exportCsvMst0109d01.setMessage(mstCorrectKbMst0109d01.getMessage());
      // 項目ID
      exportCsvMst0109d01.setType(mstCorrectKbMst0109d01.getLstId());
    }

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    try {
      jsonData = ow.writeValueAsString(exportCsvMst0109d01);
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
   * @param formMst0109d01:フォーム
   * @param searchCondMst0109d01:検索条件
   * @return 画面表示
   */
  @RequestMapping(value = "proc", method = RequestMethod.POST)
  public String changePage(RedirectAttributes rattrs,
      FormMst0109d01 formMst0109d01) {
    SearchCondMst0109d01 searchCondMst0109d01 = new SearchCondMst0109d01();

    searchCondMst0109d01.setDdlShozoku(formMst0109d01.getDdlOyaShozoku());
    searchCondMst0109d01.setTxtCustCode(formMst0109d01.getTxtCustCode());
    searchCondMst0109d01.setTxtCustNmR(formMst0109d01.getTxtCustNmR());
    searchCondMst0109d01.setTxtCorrectKb(formMst0109d01.getTxtCorrectKb());
    searchCondMst0109d01.setTxtZeroDlvdatFlg(formMst0109d01
        .getTxtZeroDlvdatFlg());
    if (formMst0109d01.isChkStKb()) {
      searchCondMst0109d01.setChkStKb(MstConst.CHECK_OFF);
    } else {
      searchCondMst0109d01.setChkStKb(MstConst.CHECK_ON);
    }

    if (formMst0109d01.isChkStsCode()) {
      searchCondMst0109d01.setChkStsCode(MstConst.CHECK_OFF);
    } else {
      searchCondMst0109d01.setChkStsCode(MstConst.CHECK_ON);
    }

    String strStKb = "";
    String strCustCd = "";
    // （2）[入力]画面表示モード ≠ '1'（新規）の場合
    if (!formMst0109d01.getViewMode().equalsIgnoreCase(MstConst.SHINKI_MODE)) {
      // [画面]訂正区分マスタ一覧[i]得意先コード ＝ '-------' の場合
      if (formMst0109d01.getSelectCustCode().trim().equalsIgnoreCase(
          MstConst.CUST_HYPHEN_MARK)) {
        strCustCd = formMst0109d01.getCustCodeNone();
      } else {
        strCustCd = formMst0109d01.getSelectCustCode();
      }
      strStKb = formMst0109d01.getSelectCorrectKb();
    }

    // 検索条件を登録画面に送信する。
    //検索条件              ＝   [変数]検索条件保持クラス
    rattrs.addFlashAttribute("searchCondMst0109d01", searchCondMst0109d01);
    
    //得意先コード              ＝   [変数]得意先コード
    rattrs.addFlashAttribute("form1CustCode", strCustCd);
    
    //訂正区分              ＝   [変数]訂正区分
    rattrs.addFlashAttribute("form1StKb", strStKb);

    //画面表示モード             ＝   [入力]画面表示モード                   ※1（新規）、2（照会）、3（訂正）、4（取消）
    rattrs.addFlashAttribute("viewMode", formMst0109d01.getViewMode());

    // 排他値を登録画面に送信する。
    rattrs.addFlashAttribute("haitaDate", formMst0109d01.getHaitaDate());
    rattrs.addFlashAttribute("haitaTime", formMst0109d01.getHaitaTime());

    return "redirect:/mst/MST01-09D02/";
  }
}
