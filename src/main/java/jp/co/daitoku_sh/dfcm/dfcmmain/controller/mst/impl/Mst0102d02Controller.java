/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl
 * ファイル名:Mst0102d02Controller.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/09/21
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/21 1.00 ABV)コアー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.controller.AbsController;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCustomer;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ObjCombobox;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0102d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0102d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl.Mst0102d02Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * コントローラクラス
 * 
 * @author アクトブレーンベトナム コアー
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/mst/MST01-02D02/")
public class Mst0102d02Controller extends AbsController {

  // Serviceの定義
  @Autowired
  @Qualifier("mst0102d02Service")
  private Mst0102d02Service mst0102d02Service;

  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  /**
   * 初期表示処理(メニュー画面で「担当者マスタ」が選択された場合など).
   * 
   * @param model ： フォームのModel
   * @param formMst0102d02 ： MST0102d02画面のフォーム
   * @param formMst0102d01 ： MST0102d01画面のフォーム
   * @param userCode ： MST0102d01画面からの得意先コード
   * @param viewMode ： MST0102d01画面からの表示モード
   * @param chainCode ： MST0102d01画面からのチェーンコード
   * @param chainEda ： MST0102d01画面からのチェーン枝番
   * @param haitaDate ： 排他日付
   * @param haitaTime ： 排他時間
   * @return String ： 画面のパス
   */
  @RequestMapping(value = "/")
  public String top(HttpServletRequest request, Model model,
      FormMst0102d02 formMst0102d02,
      @ModelAttribute("formMst0102d01") FormMst0102d01 formMst0102d01,
      @ModelAttribute("userCode") String userCode,
      @ModelAttribute("viewMode") String viewMode,
      @ModelAttribute("chainCode") String chainCode,
      @ModelAttribute("chainEda") String chainEda,
      @ModelAttribute("haitaDate") String haitaDate,
      @ModelAttribute("haitaTime") String haitaTime) {
    // セッション情報の取得に失敗した場合、ログイン画面（DFCM-COM01-01D01）へ遷移する
    String path = Util.checkSession(model, request,
        CommonConst.MST0102D01_SCREEN_NAME);
    if (!path.equalsIgnoreCase("")) {
      return path;
    }

    // セッションのデータを取得する
    Map<String, Object> sessionData = Util.getContentsFromSession(request);

    // システム管理者フラグ値を取得する
    final String sysAdminFlag = String.valueOf(sessionData.get(
        CommonConst.LOGIN_USER_SYS_ADMIN_FLG)).trim();
    // ログイン所属事業所コードを取得する
    final String loginJigyouShoCode = String.valueOf(sessionData.get(
        CommonConst.LOGIN_USER_JIGYOSHO_CODE)).trim();

    // ビュー画面を呼ぶ
    final String strScreen = "mst/mst0102d02";

    // デフォルトメッセージを取得する
    mst0102d02Service.getDefaultMess(model);

    // 排他設定
    formMst0102d02.setHaitaDate(haitaDate);
    formMst0102d02.setHaitaTime(haitaTime);

    // 一覧画面から値を取得する
    mst0102d02Service.init(model, formMst0102d02, formMst0102d01, userCode,
        viewMode, chainCode, chainEda, sysAdminFlag, loginJigyouShoCode);

    return strScreen;
  }

  /**
   * 請求先情報を取得する.
   * 
   * @param billingCode ： 請求先コード
   * @return MstCustomerオブジェクト
   */
  @RequestMapping(value = "/getBillingData", method = RequestMethod.POST)
  @ResponseBody
  public MstCustomer getBillingData(
      @RequestParam(value = "billingCode") String billingCode) {
    return mst0102d02Service.getBillingData(billingCode);
  }

  /**
   * 納品先マスタ情報を取得する.
   * 
   * @param businessDate ： 業務日付
   * @param jigyoushoCode ： 事業所コード
   * @param chainCode ： チェーンコード
   * @param chainEda ： チェーン枝番
   * @return ArrayList ： コンボボックスオブジェクト
   */
  @RequestMapping(value = "/getDeliveryData", method = RequestMethod.POST)
  @ResponseBody
  public ArrayList<ObjCombobox> getDeliveryData(
      @RequestParam(value = "businessDate") String businessDate,
      @RequestParam(value = "jigyoushoCode") String jigyoushoCode,
      @RequestParam(value = "chainCode") String chainCode,
      @RequestParam(value = "chainEda") String chainEda) {
    return mst0102d02Service.getDeliveryData(businessDate, jigyoushoCode,
        chainCode, chainEda);
  }

  /**
   * 戻るボタンを押した処理.
   * 
   * @param rattrs ： データ送信
   * @param formMst0102d02 ： 画面Mst0102d02のフォーム
   * @return String ： 画面表示
   */
  @RequestMapping(value = "proc", params = "Return", method = RequestMethod.POST)
  public String returnPage(RedirectAttributes rattrs,
      FormMst0102d02 formMst0102d02) {
    // [変数]検索条件保持クラス
    FormMst0102d01 formMst0102d01 = new FormMst0102d01();
    formMst0102d01.setDdlJigyouSho(formMst0102d02.getForm1DdlJigyouSho());
    formMst0102d01.setTxtCustomerCode(formMst0102d02.getForm1TxtCustomerCode());
    formMst0102d01.setTxtCustomerName(formMst0102d02.getForm1TxtCustomerName());
    formMst0102d01.setTxtChainCode(formMst0102d02.getForm1TxtChainCode());
    formMst0102d01.setTxtChainEda(formMst0102d02.getForm1TxtChainEda());
    formMst0102d01.setTxtEigyouTantoushaCode(formMst0102d02
        .getForm1TxtEigyouTantoushaCode());
    formMst0102d01.setTxtJimuTantoushaCode(formMst0102d02
        .getForm1TxtJimuTantoushaCode());
    formMst0102d01.setDdlCustomerType(formMst0102d02.getForm1DdlCustomerType());
    formMst0102d01.setDdlUchiZeiKoKyakuKubun(formMst0102d02
        .getForm1DdlUchiZeiKoKyakuKubun());
    formMst0102d01.setChkCancelData(formMst0102d02.isForm1ChkCancelData());
    formMst0102d01.setChkCustomer(formMst0102d02.isForm1ChkCustomer());
    formMst0102d01.setChkBilling(formMst0102d02.isForm1ChkBilling());
    formMst0102d01.setChkCustomerBilling(formMst0102d02
        .isForm1ChkCustomerBilling());

    rattrs.addFlashAttribute("formMst0102d01", formMst0102d01);

    return "redirect:/mst/MST01-02D00/";
  }

  /**
   * 登録ボタンを押した処理.
   * 
   * @param formMst0102d02 ： 画面Mst0102d02のフォーム
   * @param model ： フォームのModel
   * @return String ： 画面のパス
   */
  @RequestMapping(value = "proc", params = "Register", method = RequestMethod.POST)
  public String register(HttpServletRequest request,
      @Valid FormMst0102d02 formMst0102d02, Model model) throws Exception {
    // セッション情報の取得に失敗した場合、ログイン画面（DFCM-COM01-01D01）へ遷移する
    String path = Util.checkSession(model, request,
        CommonConst.MST0102D01_SCREEN_NAME);
    if (!path.equalsIgnoreCase("")) {
      return path;
    }

    // 画面マッピング
    String strScreen = "mst/mst0102d02";
    // 入力チェックエラーフラグ定義
    boolean inputCheckError = false;

    // デフォルトメッセージを取得する
    mst0102d02Service.getDefaultMess(model);

    // セッションのデータを取得する
    Map<String, Object> sessionData = Util.getContentsFromSession(request);

    // ユーザーＩＤ値を取得する
    String loginUserCode = String.valueOf(sessionData.get(
        CommonConst.LOGIN_USER_CODE)).trim();

    // 画面の項目の現在値を作成する
    if (!mst0102d02Service.returnFormState(formMst0102d02, model)) {
      return strScreen;
    }

    // 新規モードと訂正モードのみ入力チェックする
    if (!formMst0102d02.getViewMode().trim().equals(MstConst.TORIKESI_MODE)) {
      // 必須チェック、型/桁チェックは画面項目定義に則り実施する
      inputCheckError = mst0102d02Service.checkInput(formMst0102d02, model);
      if (inputCheckError) {
        return strScreen;
      }

      // 固有入力チェック
      inputCheckError = mst0102d02Service.checkSpecificInput(formMst0102d02, model);
      if (inputCheckError) {
        return strScreen;
      }

      // 存在チェック
      inputCheckError = mst0102d02Service.checkExistence(formMst0102d02, model);
      if (inputCheckError) {
        return strScreen;
      }
    }

    // 得意先情報登録・更新
    mst0102d02Service.saveData(formMst0102d02, model, loginUserCode);

    return strScreen;
  }

  /**
   * クリアボタンが押下された場合の処理.
   * 
   * @param formMst0102d02 ： 画面Mst0102d02のフォーム
   * @param model ： フォームのModel
   * @return String ： 画面のパス
   */
  @RequestMapping(value = "proc", params = "Clear", method = RequestMethod.POST)
  public String clear(HttpServletRequest request,
      @Valid FormMst0102d02 formMst0102d02, Model model) {
    // セッション情報の取得に失敗した場合、ログイン画面（DFCM-COM01-01D01）へ遷移する
    String path = Util.checkSession(model, request,
        CommonConst.MST0102D01_SCREEN_NAME);
    if (!path.equalsIgnoreCase("")) {
      return path;
    }

    // 画面マッピング
    String strScreen = "mst/mst0102d02";

    // デフォルトメッセージを取得する
    mst0102d02Service.getDefaultMess(model);

    // 画面表示モードにより画面項目を制御する
    mst0102d02Service.clear(formMst0102d02, model);

    return strScreen;
  }
}
