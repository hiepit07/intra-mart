/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl
 * ファイル名:Mst0199d01Controller.java
 * 
 * <p>作成者:QuanTran
 * 作成日:2015/10/01
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/01 1.00 ABV)QuanTran 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.controller.AbsController;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0105d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.SearchCondMst0105d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl.Mst0105d01Service;

/**
 * Controller layer.
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/mst/MST01-05D00/")
public class Mst0105d01Controller extends AbsController {

  // Serviceの定義
  @Autowired
  @Qualifier("mst0105d01Service")
  private Mst0105d01Service mst0105d01Service;

  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  /**
   * 画面表示初期処理。.
   * 
   * @param model A holder for model attributes
   * @param formMst0105d01 The controller object via modelAttribute.
   * @param request The request object.
   * @param searchCondMst0105d01 一覧データ呼出条件格納クラス
   * @return 画面表示
   */
  @RequestMapping(value = "/")
  public String top(Model model, FormMst0105d01 formMst0105d01, HttpServletRequest request,
      @ModelAttribute("searchCondMst0105d01") SearchCondMst0105d01 searchCondMst0105d01) {
    // 1.【初期処理】
    // セッション情報取得
    String loginPage = Util.checkSession(model, request, CommonConst.MST0105D00_SCREEN_NAME);
    // セッション情報の取得に失敗した場合、ログイン画面（DFCM-COM01-01D01）へ遷移する
    if (!loginPage.equalsIgnoreCase(CommonConst.EMPTY)) {
      return loginPage;
    }
    
    mst0105d01Service.getDefaultMess(model);
    // 設定ファイルより、オンライン取引先コード_未指定を取得する。
    String olTorihikiCodeNone = readPropertiesFileService.getSetting("OL_TORIHIKI_CODE_NONE");
    formMst0105d01.setOlTorihikiCodeNone(olTorihikiCodeNone);
    ErrorMessages errorMessage = mst0105d01Service.getBusinessDate(formMst0105d01);
    ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    if (errorMessage != null) {
      lstErrMess.add(errorMessage);
      model.addAttribute("errorMessages",lstErrMess);
      formMst0105d01.setErrorControl(true);
      return "mst/mst0105d01";
    }
    
    // 2.【オンライン得意先変換マスタ一覧画面初期表示処理】
    mst0105d01Service.initializeScreenItems(formMst0105d01, model);
    if (searchCondMst0105d01 != null) {
      formMst0105d01.setTxtOnlineCenterCode(searchCondMst0105d01.getTxtOnlineCenterCode());
      formMst0105d01.setTxtAtTorihikiCode(searchCondMst0105d01.getTxtAtTorihikiCode());
      formMst0105d01.setTxtOnlineTorihikiCode(searchCondMst0105d01.getTxtOnlineTorihikiCode());
      formMst0105d01.setChkCancellationData(searchCondMst0105d01.isChkCancellationData());
    }
    // ビュー画面を呼ぶ
    return "mst/mst0105d01";
  }

  /**
   * 【オンライン得意先変換マスタ一覧画面初期表示処理】.
   * 
   * @param formMst0105d01 The controller object via modelAttribute.
   * @return return the controller object.
   */
  @ResponseBody
  @RequestMapping(value = "/getOlCustConvMasterData", method = RequestMethod.POST)
  public FormMst0105d01 getOlCustConvMasterData(@Valid FormMst0105d01 formMst0105d01) {
    mst0105d01Service.getOlCustConvMasterData(formMst0105d01);
    return formMst0105d01;
  }
  
  /**
   * 検索ボタン.
   * 
   * @param formMst0105d01 The controller object via modelAttribute.
   * @return return the controller object.
   */
  @ResponseBody
  @RequestMapping(value = "/search", method = RequestMethod.POST)
  public FormMst0105d01 search(@Valid FormMst0105d01 formMst0105d01) {
    mst0105d01Service.search(formMst0105d01);
    return formMst0105d01;
  }
  
  /**
   * Export CSV.
   * 
   * @param formMst0105d01 A model attribute
   * @return JSON data
   * @throws Exception No comment
   */
  @RequestMapping(value = "/exportCsv", method = RequestMethod.POST)
  @ResponseBody
  public FormMst0105d01 exportCsv(FormMst0105d01 formMst0105d01) throws Exception {
    String path = mst0105d01Service.exportCvs(formMst0105d01);
    // エラー処理
    if (path == null) {
      formMst0105d01.setErrMessage(readPropertiesFileService.getMessage("COM025-E", null));
    } else {
      formMst0105d01.setFilePath(path);
    }
    return formMst0105d01;
  }
  
  /**
   * （一覧画面）新規ボタン.
   * 
   * @param rattrs Use to select attributes for a redirect scenario
   * @param formMst0105d01 A controller object
   * @return オンライン得意先変換マスタ登録画面［MST01-05D02］へ遷移する。
   */
  @RequestMapping(value = "proc", method = RequestMethod.POST, params = "btnNew")
  public String btnNew(RedirectAttributes rattrs, FormMst0105d01 formMst0105d01) {
    SearchCondMst0105d01 searchCondMst0105d01 = new SearchCondMst0105d01();
    searchCondMst0105d01.setChkCancellationData(formMst0105d01.isChkCancellationData());
    searchCondMst0105d01.setTxtAtTorihikiCode(formMst0105d01.getTxtAtTorihikiCode());
    searchCondMst0105d01.setTxtOnlineCenterCode(formMst0105d01.getTxtOnlineCenterCode());
    searchCondMst0105d01.setTxtOnlineTorihikiCode(formMst0105d01.getTxtOnlineTorihikiCode());
    rattrs.addFlashAttribute("searchCondMst0105d01", searchCondMst0105d01);
    
    rattrs.addFlashAttribute("screenMode",  MstConst.SHINKI_MODE);
    
    rattrs.addFlashAttribute("atTorihikiCode", null);
    rattrs.addFlashAttribute("olCenterCode", null);
    rattrs.addFlashAttribute("olTorihikiCode", null);
    
    rattrs.addFlashAttribute("haitaDate", formMst0105d01.getHaitaDate());
    rattrs.addFlashAttribute("haitaTime", formMst0105d01.getHaitaTime());
    return "redirect:/mst/MST01-05D02/";
  }
  
  /**
   * （一覧画面）登録画面遷移メニュー_照会ボタン.
   * 
   * @param rattrs Use to select attributes for a redirect scenario
   * @param formMst0105d01 A model attribute
   * @return オンライン得意先変換マスタ登録画面［MST01-05D02］へ遷移する。
   */
  @RequestMapping(value = "proc", method = RequestMethod.POST, params = "view")
  public String view(RedirectAttributes rattrs, FormMst0105d01 formMst0105d01) {
    SearchCondMst0105d01 searchCondMst0105d01 = new SearchCondMst0105d01();
    searchCondMst0105d01.setChkCancellationData(formMst0105d01.isChkCancellationData());
    searchCondMst0105d01.setTxtAtTorihikiCode(formMst0105d01.getTxtAtTorihikiCode());
    searchCondMst0105d01.setTxtOnlineCenterCode(formMst0105d01.getTxtOnlineCenterCode());
    searchCondMst0105d01.setTxtOnlineTorihikiCode(formMst0105d01.getTxtOnlineTorihikiCode());
    rattrs.addFlashAttribute("searchCondMst0105d01", searchCondMst0105d01);
    
    rattrs.addFlashAttribute("screenMode", MstConst.SHOUKAI_MODE);
    
    rattrs.addFlashAttribute("atTorihikiCode", formMst0105d01.getAtTorihikiCode());
    rattrs.addFlashAttribute("olCenterCode", formMst0105d01.getOlCenterCode());
    rattrs.addFlashAttribute("olTorihikiCode", formMst0105d01.getOlTorihikiCode());
    
    rattrs.addFlashAttribute("haitaDate", formMst0105d01.getHaitaDate());
    rattrs.addFlashAttribute("haitaTime", formMst0105d01.getHaitaTime());
    return "redirect:/mst/MST01-05D02/";
  }
  
  /**
   * （一覧画面）登録画面遷移メニュー_照会ボタン.
   * 
   * @param rattrs Use to select attributes for a redirect scenario
   * @param formMst0105d01 A model attribute
   * @return オンライン得意先変換マスタ登録画面［MST01-05D02］へ遷移する。
   */
  @RequestMapping(value = "proc", method = RequestMethod.POST, params = "modify")
  public String modify(RedirectAttributes rattrs, FormMst0105d01 formMst0105d01) {
    SearchCondMst0105d01 searchCondMst0105d01 = new SearchCondMst0105d01();
    searchCondMst0105d01.setChkCancellationData(formMst0105d01.isChkCancellationData());
    searchCondMst0105d01.setTxtAtTorihikiCode(formMst0105d01.getTxtAtTorihikiCode());
    searchCondMst0105d01.setTxtOnlineCenterCode(formMst0105d01.getTxtOnlineCenterCode());
    searchCondMst0105d01.setTxtOnlineTorihikiCode(formMst0105d01.getTxtOnlineTorihikiCode());
    rattrs.addFlashAttribute("searchCondMst0105d01", searchCondMst0105d01);
    
    rattrs.addFlashAttribute("screenMode", MstConst.TEISEI_MODE);
    
    rattrs.addFlashAttribute("atTorihikiCode", formMst0105d01.getAtTorihikiCode());
    rattrs.addFlashAttribute("olCenterCode", formMst0105d01.getOlCenterCode());
    rattrs.addFlashAttribute("olTorihikiCode", formMst0105d01.getOlTorihikiCode());
    
    rattrs.addFlashAttribute("haitaDate", formMst0105d01.getHaitaDate());
    rattrs.addFlashAttribute("haitaTime", formMst0105d01.getHaitaTime());
    return "redirect:/mst/MST01-05D02/";
  }
  
  /**
   * （一覧画面）登録画面遷移メニュー_照会ボタン.
   * 
   * @param rattrs Use to select attributes for a redirect scenario
   * @param formMst0105d01 A model attribute
   * @return オンライン得意先変換マスタ登録画面［MST01-05D02］へ遷移する。
   */
  @RequestMapping(value = "proc", method = RequestMethod.POST, params = "delete")
  public String delete(RedirectAttributes rattrs, FormMst0105d01 formMst0105d01) {
    SearchCondMst0105d01 searchCondMst0105d01 = new SearchCondMst0105d01();
    searchCondMst0105d01.setChkCancellationData(formMst0105d01.isChkCancellationData());
    searchCondMst0105d01.setTxtAtTorihikiCode(formMst0105d01.getTxtAtTorihikiCode());
    searchCondMst0105d01.setTxtOnlineCenterCode(formMst0105d01.getTxtOnlineCenterCode());
    searchCondMst0105d01.setTxtOnlineTorihikiCode(formMst0105d01.getTxtOnlineTorihikiCode());
    rattrs.addFlashAttribute("searchCondMst0105d01", searchCondMst0105d01);
    
    rattrs.addFlashAttribute("screenMode", MstConst.TORIKESI_MODE);
    
    rattrs.addFlashAttribute("atTorihikiCode", formMst0105d01.getAtTorihikiCode());
    rattrs.addFlashAttribute("olCenterCode", formMst0105d01.getOlCenterCode());
    rattrs.addFlashAttribute("olTorihikiCode", formMst0105d01.getOlTorihikiCode());
    
    rattrs.addFlashAttribute("haitaDate", formMst0105d01.getHaitaDate());
    rattrs.addFlashAttribute("haitaTime", formMst0105d01.getHaitaTime());
    return "redirect:/mst/MST01-05D02/";
  }
}
