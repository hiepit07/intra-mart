/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl
 * ファイル名:Mst0199d02Controller.java
 * 
 *<p>作成者:QuanTran
 * 作成日:2015/09/23
 * 
 *<p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/23 1.00 ABV)QuanTran　新規作成
 * -------------------------------------------------------------------------
 * 
 *<p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.CastData0199d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0199d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.SearchCondMst0199d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.model.Gen0199d02Data;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.model.RstMst0199d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl.Mst0199d02Service;

/**
 * コントローラクラス
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/mst/MST01-99D02/")
public class Mst0199d02Controller {

  // Serviceの定義
  @Autowired
  @Qualifier("mst0199d02Service")
  private Mst0199d02Service mst0199d02Service;

  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  
  /**
   * 初期表示処理(メニュー画面で「担当者マスタ」が選択された場合など).
   * 
   * @param model A holder for model attributes
   * @param formMst0199d02 A model attribute
   * @param request Receive data sent by Web client
   * @param searchCondMst0199d01 検索条件保持クラス
   * @param glCode 区分
   * @param viewMode 画面表示モード
   * @param haitaDate 日付文字列
   * @param haitaTime 時刻文字列
   * @return 画面表示
   */
  @RequestMapping(value = "/")
  public String top(Model model, FormMst0199d02 formMst0199d02,
      HttpServletRequest request,
      @ModelAttribute("searchCondMst0199d01") SearchCondMst0199d01 searchCondMst0199d01,
      @ModelAttribute("glCode") String glCode,
      @ModelAttribute("viewMode") String viewMode,
      @ModelAttribute("haitaDate") String haitaDate,
      @ModelAttribute("haitaTime") String haitaTime) {
    ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    ErrorMessages errMess = null;
    // セッション情報を[変数]セッションにセットする
    String path = Util.checkSession(model, request,
        CommonConst.MST0199D00_SCREEN_NAME);
    if (!path.equalsIgnoreCase("")) {
      return path;
    }
    mst0199d02Service.getDefaultMess(model);
    // ビュー画面を呼ぶ
    String strScreen = "mst/mst0199d02";
    // 【管理レコード情報取得】
    // [入力]検索条件保持クラスを退避する
    formMst0199d02.setCategory(searchCondMst0199d01.getCategory());
    formMst0199d02.setKbNm(searchCondMst0199d01.getKbNm());
    formMst0199d02.setGlCode(glCode);
    formMst0199d02.setViewMode(viewMode);
    // 排他設定
    formMst0199d02.setHaitaDate(haitaDate);
    formMst0199d02.setHaitaTime(haitaTime);
    ArrayList<String> paramMess = null;
    String errMsg = "";
    // (1) [入力]画面表示モード ＝ Null の場合、画面にエラーメッセージを表示する。
    if (viewMode == null || viewMode.equalsIgnoreCase("")) {
      paramMess = new ArrayList<String>();
      paramMess.add("画面表示モード");
      errMsg = readPropertiesFileService.getMessage("COM026-E", paramMess);
      errMess = new ErrorMessages();
      errMess.setMessageContent(errMsg);
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      // 共通部品【システム共通.エラー時のコントロール制御】
      formMst0199d02.setErrorControl("errorControl");
      model.addAttribute("viewMode", null);
      return strScreen;
    }
    // (2) [入力]区分 ＝ Null の場合、画面にエラーメッセージを表示する。
    if (glCode == null || glCode.equalsIgnoreCase("")) {
      paramMess = new ArrayList<String>();
      paramMess.add("区分");
      errMsg = readPropertiesFileService.getMessage("COM026-E", paramMess);
      errMess = new ErrorMessages();
      errMess.setMessageContent(errMsg);
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      
      // 共通部品【システム共通.エラー時のコントロール制御】
      formMst0199d02.setErrorControl("errorControl");
      model.addAttribute("viewMode", null);
      return strScreen;
    }
    
    // 【管理レコード情報取得】
    ArrayList<RstMst0199d02> recordList = mst0199d02Service.getRecordData(glCode);
    if (recordList == null || recordList.size() <= 0) {
      // ②[変数]管理レコード情報格納クラス ＝ NULL の場合、エラーメッセージを表示する
      // 共通部品【システム共通.エラー時のコントロール制御】
      formMst0199d02.setErrorControl("errorControl");
      // エラーメッセージを画面に表示する
      paramMess = new ArrayList<String>();
      paramMess.add("汎用マスタ");
      paramMess.add("汎用マスタ一覧画面で指定された管理区分");
      errMsg = readPropertiesFileService.getMessage("COM009-E", paramMess);
      errMess = new ErrorMessages();
      errMess.setMessageContent(errMsg);
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      model.addAttribute("viewMode", null);
      return strScreen;
    }
    
    // 【汎用区分設定一覧情報取得】
    List<Gen0199d02Data> generalList = mst0199d02Service.getGeneralData(glCode);
    // ②[変数]汎用区分設定一覧情報格納クラス ＝ NULL の場合、警告メッセージを表示する
    if (generalList == null || generalList.size() <= 0) {
      formMst0199d02.setErrorControl("errorControl");
      paramMess = new ArrayList<String>();
      paramMess.add("汎用マスタ");
      paramMess.add("汎用マスタ一覧画面で指定された管理区分の汎用区分設定");
      errMsg = readPropertiesFileService.getMessage("COM009-E", paramMess);
      errMess = new ErrorMessages();
      errMess.setMessageContent(errMsg);
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      model.addAttribute("viewMode", null);
      return strScreen;
    }
    RstMst0199d02 record = recordList.get(0);
    mst0199d02Service.initScreenHeader(formMst0199d02, model, record);
    mst0199d02Service.initEditArea(formMst0199d02, model, record);
    // 【画面構成】
    return strScreen;
  }

  /**
   * 検索ボタン.
   * 
   * @param formMst0199d02 A model attribute
   * @return JSON data
   */
  @RequestMapping(value = "/search", method = RequestMethod.POST)
  @ResponseBody
  public FormMst0199d02 search(@Valid FormMst0199d02 formMst0199d02) {
    mst0199d02Service.search(formMst0199d02);
    return formMst0199d02;
  }
  
  /**
   * 更新ボタンをクリックされた時の処理。編集エリアの内容を汎用区分設定一覧に追加及び更新する.
   * 
   * @param castData0199d02 A holder for screen elements
   * @param request Receive data sent by Web client
   * @return JSON data
   * @throws JsonProcessingException A non-empty description.
   */
  @RequestMapping(value = "/register", method = RequestMethod.POST, 
      consumes = MediaType.APPLICATION_JSON_VALUE, 
      produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
  @ResponseBody
  public CastData0199d02 register( @RequestBody CastData0199d02 castData0199d02,
      HttpServletRequest request) throws JsonProcessingException {
    HttpSession session = request.getSession(false);
    String userCode = String.valueOf(session.getAttribute("UserCode"));
    mst0199d02Service.register(castData0199d02, userCode);
    return castData0199d02;
  }
  
  /**
   * 戻るボタン.
   * 
   * @param formMst0199d02 A model attribute
   * @param rattrs Use to select attributes for a redirect scenario
   * @return 画面表示
   */
  @RequestMapping(value = "proc", method = RequestMethod.POST, params = "eventBack")
  public String eventBack(FormMst0199d02 formMst0199d02, RedirectAttributes rattrs) {
    SearchCondMst0199d01 searchCondMst0199d01 = new SearchCondMst0199d01();
    searchCondMst0199d01.setCategory(formMst0199d02.getCategory());
    searchCondMst0199d01.setKbNm(formMst0199d02.getKbNm());
    rattrs.addFlashAttribute("searchCondMst0199d01", searchCondMst0199d01);
    return "redirect:/mst/MST01-99D00/";
  }
  
  /**
   * (登録画面)クリアボタン_フッダー.
   * 
   * @param formMst0199d02 A model attribute
   * @param model A holder for model attributes
   * @param request Receive data sent by Web client
   * @return display screen
   */
   @RequestMapping(value = "proc", method = RequestMethod.POST, params = "clear")
   public String clear(Model model, FormMst0199d02 formMst0199d02,
       HttpServletRequest request) {
     ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
     ErrorMessages errMess = null;
     // セッション情報を[変数]セッションにセットする
     String path = Util.checkSession(model, request,
         CommonConst.MST0199D00_SCREEN_NAME);
     if (!path.equalsIgnoreCase("")) {
       return path;
     }
     String strScreen = "mst/mst0199d02";
     String viewMode = formMst0199d02.getViewMode();
     String glCode = formMst0199d02.getGlCode();
     ArrayList<String> paramMess = null;
     String errMsg = "";
     // (1) [入力]画面表示モード ＝ Null の場合、画面にエラーメッセージを表示する。
     if (viewMode == null || viewMode.equalsIgnoreCase("")) {
       paramMess = new ArrayList<String>();
       paramMess.add("画面表示モード");
       errMsg = readPropertiesFileService.getMessage("COM026-E", paramMess);
       errMess = new ErrorMessages();
       errMess.setMessageContent(errMsg);
       lstErrMess.add(errMess);
       model.addAttribute("errorMessages", lstErrMess);
       // 共通部品【システム共通.エラー時のコントロール制御】
       formMst0199d02.setErrorControl("errorControl");
       return strScreen;
     }
     // (2) [入力]区分 ＝ Null の場合、画面にエラーメッセージを表示する。
     if (glCode == null || glCode.equalsIgnoreCase("")) {
       paramMess = new ArrayList<String>();
       paramMess.add("区分");
       errMsg = readPropertiesFileService.getMessage("COM026-E", paramMess);
       errMess = new ErrorMessages();
       errMess.setMessageContent(errMsg);
       lstErrMess.add(errMess);
       model.addAttribute("errorMessages", lstErrMess);
       
       // 共通部品【システム共通.エラー時のコントロール制御】
       formMst0199d02.setErrorControl("errorControl");
       return strScreen;
     }
     // 【管理レコード情報取得】
     ArrayList<RstMst0199d02> recordList = mst0199d02Service.getRecordData(glCode);
     if (recordList == null || recordList.size() <= 0) {
       // 共通部品【システム共通.エラー時のコントロール制御】
       formMst0199d02.setErrorControl("errorControl");
       // エラーメッセージを画面に表示する
       paramMess = new ArrayList<String>();
       paramMess.add("汎用マスタ");
       paramMess.add("汎用マスタ一覧画面で指定された管理区分");
       errMsg = readPropertiesFileService.getMessage("COM009-E", paramMess);
       errMess = new ErrorMessages();
       errMess.setMessageContent(errMsg);
       lstErrMess.add(errMess);
       model.addAttribute("errorMessages", lstErrMess);
       return strScreen;
     }

     // 【汎用区分設定一覧情報取得】
     List<Gen0199d02Data> generalList = mst0199d02Service.getGeneralData(glCode);
     // ②[変数]汎用区分設定一覧情報格納クラス ＝ NULL の場合、警告メッセージを表示する
     if (generalList == null || generalList.size() <= 0) {
       paramMess = new ArrayList<String>();
       paramMess.add("汎用マスタ");
       paramMess.add("汎用マスタ一覧画面で指定された管理区分の汎用区分設定");
       errMsg = readPropertiesFileService.getMessage("COM002-W", paramMess);
       errMess = new ErrorMessages();
       errMess.setMessageContent(errMsg);
       lstErrMess.add(errMess);
       model.addAttribute("errorMessages", lstErrMess);
       return strScreen;
     }
     // 【画面構成】
     RstMst0199d02 record = recordList.get(0);
     mst0199d02Service.initScreenHeader(formMst0199d02, model, record);
     mst0199d02Service.initEditArea(formMst0199d02, model, record);
     mst0199d02Service.getDefaultMess(model);
     return "mst/mst0199d02";
   }
}
