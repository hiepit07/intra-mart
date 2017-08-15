/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.nyu.impl
 * ファイル名:Nyu0106d01Controller.java
 * 
 * 作成者:都築電気株式会社
 * 作成日:2015/12/09
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/09 1.00 ACT)ANZAI 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.controller.nyu.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.controller.AbsController;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu.FormNyu0106d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.nyu.impl.Nyu0106d01Service;

/**
 * コントローラクラス 会計入金実績作成
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/nyu/NYU01-06D00/")
public class Nyu0106d01Controller extends AbsController {

  @Autowired
  private Nyu0106d01Service nyu0106d01Service;

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

    String strScreen = "/nyu/nyu0106d01";

    // ログインチェック
    String path = Util.checkSession(model, request,
        CommonConst.NYU0106D00_SCREEN_NAME);
    if (!path.equalsIgnoreCase("")) {
      return path;
    }

    // Formの生成
    FormNyu0106d01 form = new FormNyu0106d01();
    model.addAttribute("FormNyu0106d01", form);

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
    List<DefaultMessages> defList = nyu0106d01Service.getDefaultMess();
    model.addAttribute("defaultMessages", defList);

    // 作成履歴一覧の取得
    nyu0106d01Service.getShiwakeSakuseiRirekiList(form);

    // 作成対象件数の取得
    int kensu = nyu0106d01Service.getSakuseiKensu(form);

    if (kensu == 0) {

      ArrayList<ErrorMessages> errorList = new ArrayList<ErrorMessages>();
      ErrorMessages errorMsg = new ErrorMessages();
      ArrayList<String> params = new ArrayList<String>();

      params.add("入金仕訳");
      params.add("作成対象のデータ");
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "COM009-E", params));
      errorList.add(errorMsg);

      model.addAttribute("errorMessages", errorList);
      // エラーレベル
      form.setMsgErrorLevel(true);

    }
    
    String url = readPropertiesFileService.getSetting("URL_CSV_DOWNLOAD");

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
    // CSVリンク表示フラグ
    form.setShowCsvLink(false);
    // ダウンロードURL
    form.setDownloadUrl(url);

    return strScreen;
  }

  /**
   * 実行ボタン.クリック.
   * 
   * @param model モデル
   * @param request リクエスト
   * @param form フォーム
   * @return 遷移先
   */
  @RequestMapping(value = "/proc", method = RequestMethod.POST)
  public String proc(Model model, HttpServletRequest request,
      @ModelAttribute("FormNyu0106d01") FormNyu0106d01 form) {

    String strScreen = "/nyu/nyu0106d01";

    // ログインチェック
    String path = Util.checkSession(model, request,
        CommonConst.NYU0106D00_SCREEN_NAME);

    if (!path.equalsIgnoreCase("")) {
      return path;
    }

    ArrayList<ErrorMessages> errorList = new ArrayList<ErrorMessages>();

    // セッションの取得
    Map<String, Object> ses = Util.getContentsFromSession(request);
    String userId = String.valueOf(ses.get(CommonConst.LOGIN_USER_CODE));

    // -------------------------------------------------------------------------
    // 実行
    // -------------------------------------------------------------------------

    nyu0106d01Service.createNyukinShiwakeCsv(form, userId, errorList);

    // エラーメッセージ
    if (errorList.size() > 0) {
    
      model.addAttribute("errorMessages", errorList);
      // エラーレベル
      form.setMsgErrorLevel(true);
      form.setErrorControl("errorControl");
    
    } else {

      // DEFAULT MESSAGE の取得
      List<DefaultMessages> defList = nyu0106d01Service.getDefaultMess();
      model.addAttribute("defaultMessages", defList);
      
      // 作成履歴一覧の取得
      nyu0106d01Service.getShiwakeSakuseiRirekiList(form);

      // 作成対象件数の取得
      int kensu = nyu0106d01Service.getSakuseiKensu(form);

      if (kensu == 0) {

        ErrorMessages errorMsg = new ErrorMessages();
        ArrayList<String> params = new ArrayList<String>();

        params.add("入金仕訳");
        params.add("作成対象のデータ");
        errorMsg.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", params));
        errorList.add(errorMsg);

        model.addAttribute("errorMessages", errorList);
        // エラーレベル
        form.setMsgErrorLevel(true);

      }
      
      // CSV作成リンクの表示
      if (form.getCsvLinkList().size() > 0) {
        form.setShowCsvLink(true);
      }
      
    }
    return strScreen;
  }
  
  /**
   * CSVExport.
   * 
   * @param model モデル
   * @param request リクエスト
   * @param form フォーム
   * @return JSON JSON（message: エラーメッセージ / filePath: csvダウンロードパス）
   */
  @RequestMapping(value = "/exportCsv", method = RequestMethod.POST, 
      consumes = "application/x-www-form-urlencoded; charset=utf-8", 
      produces = "application/x-www-form-urlencoded; charset=utf-8")
  @ResponseBody
  public String exportCsv(Model model, HttpServletRequest request,
      @ModelAttribute("FormNyu0106d01") FormNyu0106d01 form) throws JsonProcessingException {
    
    Map<String, String> jsonMap = new HashMap<String, String>();
    String jsonData = "";
    
    // セッションの取得
    Map<String, Object> ses = Util.getContentsFromSession(request);
    String userId = String.valueOf(ses.get(CommonConst.LOGIN_USER_CODE));
    
    //-------------------------------------------------------------------------
    // CSV出力
    //-------------------------------------------------------------------------
    
    String fileName = nyu0106d01Service.outputCsv(form, userId);
    
    if (fileName != null && !fileName.equals("")) {
      // CSV出力 OK
      jsonMap.put("filePath", form.getDownloadUrl() + fileName);
    } else {
      // CSV出力 Error
      jsonMap.put("message", "CSV出力できませんでした。");
    }
    
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    try {
      jsonData = ow.writeValueAsString(jsonMap);
    } catch (JsonProcessingException e) {
      logger.error("JSON変換エラー[" + e.getMessage() + "]");
      throw e;
    }
    
    return jsonData;
    
  }

}
