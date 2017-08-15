/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl
 * ファイル名:Mst0199d01Controller.java
 * 
 * <p>作成者:QuanTran
 * 作成日:2015/09/23
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/23 1.00 ABV)QuanTran　新規作成
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl;

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
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0199d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.SearchCondMst0199d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl.Mst0199d01Service;

/**
 * コントローラクラス
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/mst/MST01-99D00/")
public class Mst0199d01Controller {

  // Serviceの定義
  @Autowired
  @Qualifier("mst0199d01Service")
  private Mst0199d01Service mst0199d01Service;

  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  /**
   * 初期表示処理(メニュー画面で「担当者マスタ」が選択された場合など).
   * 
   * @param model A holder for model attributes
   * @param formMst0199d01 A model attribute
   * @param request Receive data sent by Web client
   * @param searchCondMst0199d01 検索条件保持クラス
   * @return 画面表示
   */
  @RequestMapping(value = "/")
  public String top(Model model, FormMst0199d01 formMst0199d01,
      HttpServletRequest request,
      @ModelAttribute("searchCondMst0199d01") SearchCondMst0199d01 searchCondMst0199d01) {
    // セッション情報の取得に失敗した場合、ログイン画面（DFCM-COM01-01D01）へ遷移する
    String path = Util.checkSession(model, request, CommonConst.MST0199D00_SCREEN_NAME);
    if (!path.equalsIgnoreCase("")) {
      return path;
    }
    // ビュー画面を呼ぶ
    String strScreen = "mst/mst0199d01";
    // 画面表示
    mst0199d01Service.init(formMst0199d01, model,
        searchCondMst0199d01);
    mst0199d01Service.getDefaultMess(model);
    return strScreen;
  }

  /**
   * 照会ボタン .
   * 
   * @param rattrs Use to select attributes for a redirect scenario
   * @param formMst0199d01 A model attribute
   * @return 汎用マスタ登録画面遷移メニューを
   */
  @RequestMapping(value = "proc", method = RequestMethod.POST, params = "view")
  public String viewSubMenu(RedirectAttributes rattrs, FormMst0199d01 formMst0199d01) {
    SearchCondMst0199d01 searchCondMst0199d01 = new SearchCondMst0199d01();
    searchCondMst0199d01.setCategory(formMst0199d01.getCategory());
    searchCondMst0199d01.setKbNm(formMst0199d01.getKbNm());
    // 検索条件保持クラス作成
    rattrs.addFlashAttribute("searchCondMst0199d01", searchCondMst0199d01);
    // 選択した担当者コードを登録画面に送信する。
    rattrs.addFlashAttribute("glCode", formMst0199d01.getSelectGlCode());
    // モード値を登録画面に送信する。
    rattrs.addFlashAttribute("viewMode", formMst0199d01.getViewMode());
    // 排他値を登録画面に送信する。
    rattrs.addFlashAttribute("haitaDate", formMst0199d01.getHaitaDate());
    rattrs.addFlashAttribute("haitaTime", formMst0199d01.getHaitaTime());
    return "redirect:/mst/MST01-99D02/";
  }

  /**
   * 訂正ボタン.
   * 
   * @param rattrs Use to select attributes for a redirect scenario
   * @param formMst0199d01 A model attribute
   * @return 汎用マスタ登録画面遷移メニューを
   */
  @RequestMapping(value = "proc", method = RequestMethod.POST, params = "modify")
  public String modifySubMenu(RedirectAttributes rattrs, FormMst0199d01 formMst0199d01) {
    SearchCondMst0199d01 searchCondMst0199d01 = new SearchCondMst0199d01();
    searchCondMst0199d01.setCategory(formMst0199d01.getCategory());
    searchCondMst0199d01.setKbNm(formMst0199d01.getKbNm());
    // 検索条件を登録画面に送信する。
    rattrs.addFlashAttribute("searchCondMst0199d01", searchCondMst0199d01);
    // 選択した担当者コードを登録画面に送信する。
    rattrs.addFlashAttribute("glCode", formMst0199d01.getSelectGlCode());
    // モード値を登録画面に送信する。
    rattrs.addFlashAttribute("viewMode", formMst0199d01.getViewMode());
    // 排他値を登録画面に送信する。
    rattrs.addFlashAttribute("haitaDate", formMst0199d01.getHaitaDate());
    rattrs.addFlashAttribute("haitaTime", formMst0199d01.getHaitaTime());
    return "redirect:/mst/MST01-99D02/";
  }

  /**
   * 検索ボタン.
   * 
   * @param formMst0199d01 A model attribute
   * @return JSON data
   */
  @RequestMapping(value = "/search", method = RequestMethod.POST)
  @ResponseBody
  public FormMst0199d01 search(@Valid FormMst0199d01 formMst0199d01) {
    mst0199d01Service.search(formMst0199d01);
    return formMst0199d01;
  }

  /**
   * Export CSV
   * 
   * @param formMst0199d01 A model attribute
   * @return JSON data
   * @throws Exception No comment
   */
  @RequestMapping(value = "/exportCsv", method = RequestMethod.POST)
  @ResponseBody
  public FormMst0199d01 exportCsv(FormMst0199d01 formMst0199d01) throws Exception {
    String path = mst0199d01Service.exportCvs(formMst0199d01);
    // エラー処理
    if (path == null) {
      formMst0199d01.setErrMessage(readPropertiesFileService.getMessage("COM025-E", null));
    } else {
      formMst0199d01.setFilePath(path);
    }
    return formMst0199d01;
  }
}
