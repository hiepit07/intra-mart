/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl
 * ファイル名:Sei0106d01Controller.java
 * 
 * <p>作成者:QuanTran
 * 作成日:2015/12/08
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/08 1.00 ABV)QuanTran 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.controller.sei.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.controller.AbsController;
import jp.co.daitoku_sh.dfcm.common.db.model.MstListForm;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.SeiConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.sei.FormSei0106d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.model.RstSei0106d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.sei.impl.Sei0106d01Service;

/**
 * Controller layer.
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */

@Controller
@RequestMapping(value = "/sei/SEI01-06D00/")
public class Sei0106d01Controller extends AbsController {
  
  @Autowired
  @Qualifier("sei0106d01Service")
  private Sei0106d01Service sei0106d01Service;
  
  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;
  /**
   * 画面表示初期処理。.
   * 
   * @param model A holder for model attributes
   * @param formSei0106d01 The controller object via modelAttribute.
   * @param request The request object.
   * @return 画面表示
   */
  @RequestMapping(value = "/")
  public String top(Model model, FormSei0106d01 formSei0106d01, HttpServletRequest request) {
    // 【初期処理】
    // セッション情報取得
    String loginPage = Util.checkSession(model, request, CommonConst.SEI0106D00_SCREEN_NAME);
    // セッション情報の取得に失敗した場合、ログイン画面（DFCM-COM01-01D01）へ遷移する
    if (!loginPage.equalsIgnoreCase(CommonConst.EMPTY)) {
      return loginPage;
    }
    ArrayList<String> paramMess = new ArrayList<String>();
    String strErrMsg = "";
    ErrorMessages errorMessages = null;
    ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    // 共通部品を使用して、業務日付を取得する
    String businessDate = sei0106d01Service.getBusinessDate();
    if (null == businessDate) {
      paramMess = new ArrayList<String>();
      paramMess.add("システムコントロール");
      paramMess.add("業務日付");
      strErrMsg = readPropertiesFileService.getMessage("COM009-E", paramMess);
      errorMessages = new ErrorMessages();
      errorMessages.setMessageContent(strErrMsg);
      lstErrMess.add(errorMessages);
      model.addAttribute("errorMessages", lstErrMess);
      // 業務続行が不可能なエラーが発生した場合
      formSei0106d01.setErrorControl(true);
      return "sei/sei0106d01";
    } else {
      formSei0106d01.setBusinessDate(businessDate);
    }
    // セッション情報を[変数]セッションにセットする
    Map<String, Object> sessionData = Util.getContentsFromSession(request);
    // システム管理者フラグ値を取得する
    String sysAdminFlag = String.valueOf(sessionData.get(CommonConst.LOGIN_USER_SYS_ADMIN_FLG));
    if (sysAdminFlag.equalsIgnoreCase(CommonConst.SYS_ADMIN_FLG_VALID)) {
      errorMessages = sei0106d01Service.setSJigyoInfo(model, businessDate);
      // Error processing
      if (errorMessages != null) {
        lstErrMess.add(errorMessages);
        model.addAttribute("errorMessages", lstErrMess);
        // 業務続行が不可能なエラーが発生した場合
        formSei0106d01.setErrorControl(true);
        return "sei/sei0106d01";
      }
    }
    model.addAttribute("sysAdminFlag", sysAdminFlag);
    formSei0106d01.setSysAdminFlag(sysAdminFlag);
    Date date = new Date();
    String sysDate = DateUtil.setFormat(date, CommonConst.DATE_FORMAT_Y_M);
    // 画面表示
    formSei0106d01.setTxtBildDate(sysDate);
    
    sei0106d01Service.setCloseDateLisCbx(model);
    if (sysAdminFlag.equalsIgnoreCase(CommonConst.SYS_ADMIN_FLG_VALID)) {
      formSei0106d01.setDdlJigyoCode(String.valueOf(sessionData.get(
          CommonConst.LOGIN_USER_JIGYOSHO_CODE)));
      formSei0106d01.setLoginJigyouShoCode(String.valueOf(sessionData.get(
          CommonConst.LOGIN_USER_JIGYOSHO_CODE)));
      formSei0106d01.setTxtJgycd(String.valueOf(sessionData.get(
          CommonConst.LOGIN_USER_CODE)));
    }
    
    model.addAttribute("lblJgycdNm", String.valueOf(sessionData.get(CommonConst.LOGIN_USER_NAME)));
    
    int searchMax = sei0106d01Service.getSearchMax();
    formSei0106d01.setSearchMax(searchMax);
    
    formSei0106d01.setRdOutputFormat(SeiConst.OUTPUT_FORMAT_BILLCUSTFLAG);
    sei0106d01Service.getDefaultMess(model);
    // ビュー画面を呼ぶ
    return "sei/sei0106d01";
  }
  
  /**
   * 表示ボタン.
   * 
   * @param formSei0106d01 The controller object
   * @return JSON Data
   */
  @RequestMapping(value = "/display", method = RequestMethod.POST)
  @ResponseBody
  public FormSei0106d01 display(@Valid FormSei0106d01 formSei0106d01) {
    // 入力チェックの内容
    if (!sei0106d01Service.checkInputContent(formSei0106d01)) {
      return formSei0106d01;
    }

    // 汎用マスタ情報取得
    sei0106d01Service.getBillInfo(formSei0106d01);
    return formSei0106d01;
  }
  
  /**
   * 印刷ボタンをクリックされた場合の処理。請求一覧を印刷する.
   * 
   * @param formSei0106d01 The controller object
   * @param request The request object.
   * @return JSON data
   * @throws Exception contains information about the error
   */
  @RequestMapping(value = "/print", method = RequestMethod.POST, 
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
      + ";charset=utf-8")
  @ResponseBody
  public FormSei0106d01 print(@RequestBody FormSei0106d01 formSei0106d01,
      HttpServletRequest request) throws Exception {
    ArrayList<RstSei0106d01> billInfoList = formSei0106d01.getBillInfoList();
    // [画面]選択が１件もチェックされていない（全件未チェック）場合、エラーとする
    if (billInfoList == null || billInfoList.size() == 0) {
      ArrayList<String> params = new ArrayList<String>();
      params.add("請求先");
      formSei0106d01.setErrMessage(readPropertiesFileService.getMessage(
          "COM008-E", params));
      return formSei0106d01;
    }
    // 入力チェックの内容
    if (!sei0106d01Service.checkInputContent(formSei0106d01)) {
      return formSei0106d01;
    }

    // 入力情報格納
    // [変数]出力形式_請求先フラグ
    boolean outFormatBillCustFlg = false;
    // [変数]出力形式_得意先店舗フラグ
    boolean outFormatCustShopFlg = false;
    // [変数]出力形式_伝票フラグ
    boolean outFormatDenpyoFlg = false;

    // 出力形式判定
    String outFormat = formSei0106d01.getRdOutputFormat();
    if (outFormat.equalsIgnoreCase(SeiConst.OUTPUT_FORMAT_BILLCUSTFLAG)) {
      outFormatBillCustFlg = true;
    } else if (outFormat.equalsIgnoreCase(
        SeiConst.OUTPUT_FORMAT_CUSTSHOPFLAG)) {
      outFormatCustShopFlg = true;
    } else if (outFormat.equalsIgnoreCase(SeiConst.OUTPUT_FORMAT_DENPYOFLAG)) {
      outFormatDenpyoFlg = true;
    }
    // サーバ名WK
    String svrNmWk = null;
    // 格納ディレクトリWK
    String dirWk = null;
    // 帳票名WK
    String nmWk = null;
    // 帳票管理マスタ取得
    ArrayList<MstListForm> listForms = sei0106d01Service.getListForm(
        outFormatBillCustFlg, outFormatCustShopFlg, outFormatDenpyoFlg);
    if (listForms != null && listForms.size() > 0) {
      svrNmWk = listForms.get(0).getListSvrNm();
      dirWk = listForms.get(0).getListDir();
      nmWk = listForms.get(0).getListNm();
    } else {
      ArrayList<String> params = new ArrayList<String>();
      params.add("帳票定義マスタ");
      params.add("帳票定義");
      // エラーメッセージを画面に表示する
      formSei0106d01.setErrMessage(readPropertiesFileService.getMessage(
          "COM009-E", params));
      return formSei0106d01;
    }
    
    // 出力メイン
    sei0106d01Service.outputMain(formSei0106d01, billInfoList, outFormatBillCustFlg,
        outFormatCustShopFlg, outFormatDenpyoFlg, dirWk, nmWk, formSei0106d01
            .getDdlJigyoCode());
    return formSei0106d01;
  }

  /**
   * 印刷ボタンをクリックされた場合の処理。請求一覧を印刷する.
   * 
   * @param formSei0106d01 The controller object
   * @param request The request object.
   * @return JSON data
   * @throws Exception contains information about the error
   */
  @RequestMapping(value = "/exportCsv", method = RequestMethod.POST, 
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
      + ";charset=utf-8")
  @ResponseBody
  public FormSei0106d01 exportCsv(@RequestBody FormSei0106d01 formSei0106d01,
      HttpServletRequest request) throws Exception {
    ArrayList<RstSei0106d01> billInfoList = formSei0106d01.getBillInfoList();
    // [画面]選択が１件もチェックされていない（全件未チェック）場合、エラーとする
    if (billInfoList == null || billInfoList.size() == 0) {
      ArrayList<String> params = new ArrayList<String>();
      params.add("請求先");
      formSei0106d01.setErrMessage(readPropertiesFileService.getMessage(
          "COM008-E", params));
      return formSei0106d01;
    }

    // 入力チェックの内容
    if (!sei0106d01Service.checkInputContent(formSei0106d01)) {
      return formSei0106d01;
    }

    // 入力情報格納
    // [変数]出力形式_請求先フラグ
    boolean outFormatBillCustFlg = false;
    // [変数]出力形式_得意先店舗フラグ
    boolean outFormatCustShopFlg = false;
    // [変数]出力形式_伝票フラグ
    boolean outFormatDenpyoFlg = false;

    // 出力形式判定
    String outFormat = formSei0106d01.getRdOutputFormat();
    if (outFormat.equalsIgnoreCase(SeiConst.OUTPUT_FORMAT_BILLCUSTFLAG)) {
      outFormatBillCustFlg = true;
    } else if (outFormat.equalsIgnoreCase(
        SeiConst.OUTPUT_FORMAT_CUSTSHOPFLAG)) {
      outFormatCustShopFlg = true;
    } else if (outFormat.equalsIgnoreCase(SeiConst.OUTPUT_FORMAT_DENPYOFLAG)) {
      outFormatDenpyoFlg = true;
    }

    // CSV出力データ作成
    sei0106d01Service.exportCsv(formSei0106d01, outFormatBillCustFlg,
        outFormatCustShopFlg, outFormatDenpyoFlg);
    // エラー処理
    if (formSei0106d01.getFilePath() == null) {
      formSei0106d01.setErrMessage(readPropertiesFileService.getMessage(
          "COM025-E", null));
    }
    return formSei0106d01;
  }

}
