/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl
 * ファイル名:Mst0109d02Controller.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/10/28
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/28 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.controller.AbsController;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0109d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.SearchCondMst0109d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl.Mst0109d02Service;

/**
 * コントローラクラス
 * 
 * @author アクトブレーンベトナム コイー
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/mst/MST01-09D02/")
public class Mst0109d02Controller extends AbsController {

  // Serviceの定義
  @Autowired
  @Qualifier("mst0109d02Service")
  private Mst0109d02Service mst0109d02Service;

  /** Propertiesファイルを読む. */
  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  /**
   * 初期表示処理(メニュー画面で「訂正マスタ」が選択された場合など).
   * 
   * @param model:モデル
   * @param request:httpリクエスト
   * @return 画面表示
   */
  @RequestMapping(value = "/")
  public String top(Model model, HttpServletRequest httpRequest,
      FormMst0109d02 formMst0109d02,
      @ModelAttribute("searchCondMst0109d01") SearchCondMst0109d01 searchCondMst0109d01,
      @ModelAttribute("form1CustCode") String form1CustCode,
      @ModelAttribute("form1StKb") String form1StKb,
      @ModelAttribute("viewMode") String viewMode,
      @ModelAttribute("haitaDate") String haitaDate,
      @ModelAttribute("haitaTime") String haitaTime) {

    // ビュー画面を呼ぶ
    String strScreen = "mst/mst0109d02";

    // セッション情報の取得に失敗した場合、ログイン画面（DFCM-COM01-01D01）へ遷移する
    String path = Util.checkSession(model, httpRequest,
        CommonConst.MST0109D00_SCREEN_NAME);
    if (!path.equalsIgnoreCase("")) {
      return path;
    }

    // (登録画面)画面表示
    mst0109d02Service.init(formMst0109d02, model, searchCondMst0109d01,
        viewMode, form1CustCode, form1StKb, haitaDate, haitaTime, httpRequest);
    return strScreen;

  }

  /**
   * 戻るボタンを押した処理.
   * 
   * @param rattrs:送信パラメーターの設定
   * @param formMst0109d02:フォーム
   * @return 画面表示
   */
  @RequestMapping(value = "proc", params = "Return", method = RequestMethod.POST)
  public String returnPage(RedirectAttributes rattrs,
      FormMst0109d02 formMst0109d02) {
    // [変数]検索条件保持クラス
    SearchCondMst0109d01 searchCondMst0109d01 = new SearchCondMst0109d01();
    searchCondMst0109d01.setDdlShozoku(formMst0109d02.getForm1JigyoshoCode());
    searchCondMst0109d01.setTxtCustCode(formMst0109d02.getForm1CustCode());
    searchCondMst0109d01.setTxtCustNmR(formMst0109d02.getForm1CustName());
    searchCondMst0109d01.setTxtCorrectKb(formMst0109d02.getForm1CorrectKb());
    searchCondMst0109d01.setTxtZeroDlvdatFlg(formMst0109d02
        .getForm1ZeroDlvdatFlg());
    searchCondMst0109d01.setChkStKb(formMst0109d02.getForm1StKb());
    searchCondMst0109d01.setChkStsCode(formMst0109d02.getForm1StsCode());

    rattrs.addFlashAttribute("searchCondMst0109d01", searchCondMst0109d01);

    return "redirect:/mst/MST01-09D00/";
  }

  /**
   * クリアボタンを押した処理.
   * 
   * @param formMst0109d02:フォーム
   * @param model:モデル
   * @param httpRequest：httpリクエスト
   * @return 画面表示
   */
  @RequestMapping(value = "proc", params = "Clear", method = RequestMethod.POST)
  public String clear(FormMst0109d02 formMst0109d02, Model model,
      HttpServletRequest httpRequest) {
    String strScreen = "mst/mst0109d02";    

    // セッション情報の取得に失敗した場合、ログイン画面（DFCM-COM01-01D01）へ遷移する
    String path = Util.checkSession(model, httpRequest,
        CommonConst.MST0109D00_SCREEN_NAME);
    if (!path.equalsIgnoreCase("")) {
      return path;
    }
    
    mst0109d02Service.clear(formMst0109d02, model);
    
    return strScreen;
  }
  
  /**
   * 登録ボタンを押した処理.
   * 
   * @param formMst0109d02:フォーム
   * @param model:モデル
   * @param httpRequest：httpリクエスト
   * @return　画面表示
   * @throws Exception e
   */
  @RequestMapping(value = "proc", params = "Register", method = RequestMethod.POST)
  public String register(FormMst0109d02 formMst0109d02, Model model,
      HttpServletRequest httpRequest) throws Exception {
    // 画面マッピング
    String strScreen = "mst/mst0109d02";
    // セッション情報の取得に失敗した場合、ログイン画面（DFCM-COM01-01D01）へ遷移する
    String path = Util.checkSession(model, httpRequest,
        CommonConst.MST0109D00_SCREEN_NAME);
    if (!path.equalsIgnoreCase("")) {
      return path;
    }
    //設定区分を設定する。
    //disabledは値を取得できないので、StKbSelectというhiddenを利用する。
    if (formMst0109d02.getChkStKb() == null) {
      if (formMst0109d02.getStKbSelect().equalsIgnoreCase(MstConst.IS_COMMON)) {      
        formMst0109d02.setChkStKb(MstConst.IS_COMMON);      
      } else {
        formMst0109d02.setChkStKb(MstConst.NOT_COMMON);
      }
    }
    
    // モード設定
    model.addAttribute("modeView", formMst0109d02.getMode());
    
    // default メッセージを取得する。
    mst0109d02Service.getDefaultMess(model);
    
    // 入力チェックを行う
    if (mst0109d02Service.checkInput(formMst0109d02, model)) {
      return strScreen;
    }

    // 存在チェックを行う
    if (mst0109d02Service.checkExist(formMst0109d02, model)) {
      return strScreen;
    }
    
    // 排他、既存の訂正区分マスタ情報のチェック
    if (mst0109d02Service.checkSpec(formMst0109d02, model)) {
      return strScreen;
    }
    
    mst0109d02Service.registerData(formMst0109d02, model, httpRequest);
    

    return strScreen;
  }
}
