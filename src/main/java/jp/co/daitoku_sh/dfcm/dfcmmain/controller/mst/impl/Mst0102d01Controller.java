/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl
 * ファイル名:Mst0102d01Controller.java
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
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0102d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.Mst0102d01CustomerList;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.Mst0102d01ExportCsv;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl.Mst0102d01Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * コントローラクラス
 * 
 * @author アクトブレーンベトナム コアー
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/mst/MST01-02D00/")
public class Mst0102d01Controller extends AbsController {

  // Serviceの定義
  @Autowired
  @Qualifier("mst0102d01Service")
  private Mst0102d01Service mst0102d01Service;

  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  /**
   * 初期表示処理(メニュー画面で「得意先マスタ」が選択された場合など).
   * 
   * @param model ： フォームのModel
   * @param formMst0102d01 ： 画面Mst0102d01のフォーム
   * @return String ： 画面のパス
   */
  @RequestMapping(value = "/")
  public String top(HttpServletRequest request, Model model,
      FormMst0102d01 formMst0102d01) {
    // セッション情報の取得に失敗した場合、ログイン画面（DFCM-COM01-01D01）へ遷移する
    String path = Util.checkSession(model, request,
        CommonConst.MST0102D01_SCREEN_NAME);
    if (!path.equalsIgnoreCase("")) {
      return path;
    }

    // セッションのデータを取得する
    Map<String, Object> sessionData = Util.getContentsFromSession(request);

    // システム管理者フラグ値を取得する
    String sysAdminFlag = String.valueOf(sessionData.get(
        CommonConst.LOGIN_USER_SYS_ADMIN_FLG));
    formMst0102d01.setSysAdminFlag(sysAdminFlag);

    // ログイン事業所コード値を取得する
    String loginJigyouShoCode = String.valueOf(sessionData.get(
        CommonConst.LOGIN_USER_JIGYOSHO_CODE));
    formMst0102d01.setLoginJigyouShoCode(loginJigyouShoCode);

    // ビュー画面を呼ぶ
    String strScreen = "mst/mst0102d01";

    // デフォルトメッセージを取得する
    mst0102d01Service.getDefaultMess(model);

    // 画面のデータを初期化
    mst0102d01Service.init(model, formMst0102d01, sysAdminFlag);

    return strScreen;
  }

  /**
   * 得意先一覧情報を取得する.
   * 
   * @param searchCondition　：　検索条件オブジェクト
   * @return Mst0102d01CustomerListオブジェクト
   */
  @RequestMapping(value = "/getCustomerList", method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public List<Mst0102d01CustomerList> getCustomerList(
      @RequestBody FormMst0102d01 searchCondition) {
    return mst0102d01Service.getCustomerList(searchCondition);
  }

  /**
   * 得意先一覧情報を取得する.
   * 
   * @param searchCondition　：　検索条件オブジェクト
   * @return Mst0102d01CustomerListオブジェクト
   */
  @RequestMapping(value = "/searchCustomerData", method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public List<Mst0102d01CustomerList> searchCustomerData(
      @RequestBody FormMst0102d01 searchCondition) {
    return mst0102d01Service.searchCustomerData(searchCondition);
  }

  /**
   * CSV出力データを取得して、取得した得意先情報格納クラスを元にCSVファイルを出力する.
   * 
   * @param searchCondition　：　検索条件オブジェクト
   * @return Mst0102d01CustomerListオブジェクト
   */
  @RequestMapping(value = "/exportCsv", method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Mst0102d01ExportCsv exportCsv(
      @RequestBody FormMst0102d01 searchCondition) throws Exception {
    Mst0102d01ExportCsv exportCsv = null;
    exportCsv = mst0102d01Service.exportCsv(searchCondition);
    return exportCsv;
  }

  /**
   * 登録画面に遷移する.
   * 
   * @param rattrs ： 送信パラメーターの設定
   * @return String ： 画面のパス
   */
  @RequestMapping(value = "proc", method = RequestMethod.POST)
  public String changePage(RedirectAttributes rattrs,
      FormMst0102d01 formMst0102d01) {
    // [変数]担当者コード
    String strUserCode = "";
    // [変数]チェーンコード
    String strChainCode = "";
    // [変数]チェーン枝番
    String strChainEda = "";

    if (!formMst0102d01.getViewMode().equalsIgnoreCase(MstConst.SHINKI_MODE)) {
      strUserCode = formMst0102d01.getSelectUserCode();
      strChainCode = formMst0102d01.getSelectChainCode();
      strChainEda = formMst0102d01.getSelectChainEda();
    }
    // 検索条件を登録画面に送信する。
    rattrs.addFlashAttribute("formMst0102d01", formMst0102d01);
    // 選択した担当者コードを登録画面に送信する。
    rattrs.addFlashAttribute("userCode", strUserCode);
    // モード値を登録画面に送信する。
    rattrs.addFlashAttribute("viewMode", formMst0102d01.getViewMode());
    // モード値を登録画面に送信する。
    rattrs.addFlashAttribute("chainCode", strChainCode);
    // モード値を登録画面に送信する。
    rattrs.addFlashAttribute("chainEda", strChainEda);
    // 排他値を登録画面に送信する。
    rattrs.addFlashAttribute("haitaDate", formMst0102d01.getHaitaDate());
    rattrs.addFlashAttribute("haitaTime", formMst0102d01.getHaitaTime());

    return "redirect:/mst/MST01-02D02/";
  }
}
