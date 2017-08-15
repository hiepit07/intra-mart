/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.get.impl
 * ファイル名:Get0105D00Controller.java
 *
 *<p>作成者:HiepTruong
 * 作成日:2015/10/21
 * 
 *<p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/21 1.00 HiepTruong 新規開発
 * -------------------------------------------------------------------------
 *
 *<p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.controller.get.impl;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.component.CustomerData;
import jp.co.daitoku_sh.dfcm.common.component.UserData;
import jp.co.daitoku_sh.dfcm.common.controller.AbsController;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetData;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.GetConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.get.ExportCsvGet;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.get.FormGet0105d00;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.get.GetCorrectKbGet0105d00;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.get.impl.Get0105D00Service;

/**
 * コントローラクラス
 * 
 * @author HiepTruong
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/get/GET01-05D00/")
public class Get0105D00Controller extends AbsController {

  // Serviceの定義
  @Autowired
  @Qualifier("get0105D00Service")
  private Get0105D00Service get0105D00Service;

  @Autowired
  @Qualifier("appContext")
  private ApplicationContext appContext;

  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  /* ログ出力用 */
  private Dfcmlogger logger = new Dfcmlogger();

  @Autowired
  @Qualifier("txManager")
  private PlatformTransactionManager txManager;

  @Autowired
  @Qualifier("commonDao")
  private CommonDao commonDao;

  /**
   * メニュー画面で「事業所月次確定」が選択された場合の初期表示処理
   * 
   * @param model Model
   * 
   * @param request HttpServletRequest
   * 
   * @param formGet0105d00 FormGet0105d00
   * 
   * @return String
   */
  @RequestMapping(value = "/")
  public String top(Model model, HttpServletRequest request,
      @ModelAttribute("formGet0105d00") FormGet0105d00 formGet0105d00) {
    // 1-1 セッション取得
    // (1) セッション情報の取得に失敗した場合、ログイン画面（DFCM-COM01-01D01
    String path = Util.checkSession(model, request,
        CommonConst.GET0105D00_SCREEN_NAME);
    if (!path.equalsIgnoreCase("")) {
      return path;
    }
    get0105D00Service.getDefaultMess(model);
    get0105D00Service.init(model, formGet0105d00, request, txManager,
        appContext);
    // ビュー画面を呼ぶ
    String strScreen = "get/get0105d00";
    return strScreen;
  }

  /**
   * 事業所が変更された場合の処理。ログインユーザ権限がシステム管理者権限の場合のみ発生
   * 
   * @param request HttpServletRequest.
   * @param reqOfficeCodewk String
   * @param haitaDate String.
   * @param haitaTime String
   * @return String
   */
  @RequestMapping(produces = "text/plain;charset=UTF-8", 
      value = "/get0105D00changeOfficeCode", method = RequestMethod.POST)
  @ResponseBody
  public String get0105D00changeOfficeCode(
      HttpServletRequest request,
      @RequestParam("officeCode") String reqOfficeCodewk,
      @RequestParam("haitaDate") String haitaDate,
      @RequestParam("haitaTime") String haitaTime)
          throws JsonProcessingException {

    // store lasted haita
    // Json data store
    Map<String, String> returnMap = new HashMap<String, String>();
    returnMap.put("haitaDate", get0105D00Service.getHaitaInsymd());
    returnMap.put("haitaTime", get0105D00Service.getHaitaInstime());

    String jsonData = "";
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    // (1) [画面]事業所でブランクが選択された場合、
    if (null == reqOfficeCodewk || reqOfficeCodewk.trim().compareToIgnoreCase(
        "") == 0) {

      // (2) [画面]事業所でブランク以外が選択された場合
    } else {
      returnMap.put("isEnableBtnConfirm", "true");
      returnMap.put("setFocus", "#urikakeMonth");
      jsonData = ow.writeValueAsString(returnMap);
    }
    return jsonData;
  }

  /**
   * CSV出力データを取得して、取得した得意先情報格納クラスを元にCSVファイルを出力する.
   * 
   * @throws Exception
   *           CSVボタンが押下された場合の処理
   * @param request HttpServletRequest
   * @param formGet0105d00 FormGet0105d00
   * @return ページ表示（String（ＪＳＯＮ形））
   * @throws Exception:エラー画面
   */
  @RequestMapping(value = "/exportCsv", method = RequestMethod.POST, 
      consumes = "application/x-www-form-urlencoded; charset=utf-8", 
      produces = "application/x-www-form-urlencoded; charset=utf-8")
  @ResponseBody
  public String exportCsv(FormGet0105d00 formGet0105d00,
      HttpServletRequest request) throws Exception {
    String jsonData = "";
    // メッセージのParam
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    ExportCsvGet exportCsvGet0105D00S = new ExportCsvGet();
    // 入力チェック
    GetCorrectKbGet0105d00 getCorrectKbGet0105d00 = null;
    getCorrectKbGet0105d00 = get0105D00Service.checkInput(formGet0105d00);
    // [変数]売掛月度WK = '' の場合、エラーとする

    if (getCorrectKbGet0105d00 != null) {
      // 入力チェックエラー
      exportCsvGet0105D00S.setMessage(getCorrectKbGet0105d00.getMessage());
      // 項目ID
      exportCsvGet0105D00S.setType(getCorrectKbGet0105d00.getLstId());
      // フォーカスセット
      // [画面]売掛月度へフォーカスをセットする
      exportCsvGet0105D00S.setSetFocus("#urikakeMonth");
      jsonData = ow.writeValueAsString(exportCsvGet0105D00S);
      return jsonData;
    }

    CustomerData customerData = null;
    CustomerData customerInfo = null;
    CommonGetData commonGetData = new CommonGetData(commonDao,
        readPropertiesFileService);
    String customerCode = formGet0105d00.getTxtCustCd();
    String offiCode = formGet0105d00.getDdlJigyouSho();
    String tantoshaName = formGet0105d00.getTantoshaName();
    String tantoshaCode = formGet0105d00.getTxtSaleUserCd();
    // 請求先情報取得
    // [画面]請求先コード <> '' and [画面]請求先名称 = '' の場合
    if (!customerCode.equalsIgnoreCase("")
        && formGet0105d00.getSeikyu().equalsIgnoreCase("")) {
      // 共通部品を使って、得意先情報を取得する
      customerData = commonGetData.getCustomerData(customerCode, offiCode, 2);
      // (1) [変数]請求先情報格納クラス.メッセージコード = Nullの場合、
      if (customerData.getMsgCd() == null) {
        customerInfo = customerData;
        formGet0105d00.setSeikyu(customerInfo.getCst().getCustNmR());
      } else {
        customerInfo = null;
        formGet0105d00.setSeikyu("");
      }
      // (2) [変数]請求先情報格納クラス.メッセージコード <> Nullの場合、
    }

    UserData tantoSha = null;

    // 担当者情報取得
    // [画面]担当者コード <> '' and [画面]店舗名称 = '' の場合
    if (!tantoshaCode.equalsIgnoreCase("") && tantoshaName.equalsIgnoreCase("")) {
      // 共通部品【データ取得.担当者情報取得】
      tantoSha = commonGetData.getUserData(customerCode, offiCode);
      // (1) [変数]担当者氏名格納クラス.メッセージコード = Nullの場合
      if (tantoSha.getMsgCd() == null) {
        formGet0105d00.setTantoshaName(tantoSha.getUsr().getUserNm());
      } else {
        // (2) [変数]担当者氏名格納クラス.メッセージコード <> Nullの場合
        formGet0105d00.setSeikyu("");
      }
    }

    String strExportCsv;
    try {
      strExportCsv = get0105D00Service.exportCsvData(formGet0105d00);
    } catch (Exception e) {
      logger.error(e.getMessage());
      throw e;
    }
    // 6-2-1 エラー処理②
    // エラーメッセージを画面に表示する
    if (strExportCsv.equalsIgnoreCase(GetConst.NO_RECORD)) {
      exportCsvGet0105D00S.setMessage(readPropertiesFileService.getMessage(
          "COM025-E", null));
      // フォーカスセット
      // (1) [変数]セッション.システム管理者フラグ = '0'（無効）の場合、[画面]請求先コードへフォーカスをセットする
      if (formGet0105d00.getSysAdminFlag().equalsIgnoreCase(
          CommonConst.SYS_ADMIN_FLG_VALID)) {
        exportCsvGet0105D00S.setSetFocus("#ddlJigyouSho");
      } else {
       // (2) [変数]セッション.システム管理者フラグ = '1'（有効）の場合、[画面]事業所へフォーカスをセットする
        exportCsvGet0105D00S.setSetFocus("#txtCustCd");
      }
    } else {
      exportCsvGet0105D00S.setStrPathFile(strExportCsv);
      exportCsvGet0105D00S.setStrName("");
    }
    exportCsvGet0105D00S.setSeikyuName(formGet0105d00.getSeikyu());
    exportCsvGet0105D00S.setTantoshaName(formGet0105d00.getTantoshaName());
    try {
      jsonData = ow.writeValueAsString(exportCsvGet0105D00S);
    } catch (JsonProcessingException e) {
      logger.error(e.getMessage());
      throw e;
    }
    return jsonData;
  }
}
