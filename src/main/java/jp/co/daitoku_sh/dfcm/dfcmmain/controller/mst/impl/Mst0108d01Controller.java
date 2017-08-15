/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl
 * ファイル名:Mst0108d01Controller.java
 * 
 * <p>作成者: グエン　リユオン　ギア
 * 作成日:2015/11/01
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/21 1.00 ABV) グエン　リユオン　ギア 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.ExportCSVMst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0108d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.Mst0108d01Model;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.SearchMst0108d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl.Mst0108d01Service;

/**
 * コントローラクラス
 * 
 * @author  グエン　リユオン　ギア
 * @version 1.0.0
 * @since 1.0.0
 */

@Controller
@RequestMapping(value = "/mst/MST01-08D00/")
public class Mst0108d01Controller extends AbsController {

  // Serviceの定義
  @Autowired
  @Qualifier("mst0108d01Service")
  private Mst0108d01Service mst0108d01Service;

  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  /** ログ出力用 */
  private Dfcmlogger logger = new Dfcmlogger();

  /**
   * 【オンライン得意先変換マスタ一覧画面初期表示処理】.
   * 
   * @param request　request
   * @param model　モデル
   * @param formMst0108d01 画面フォーム
   * @param searchMst0108d01　検索条件の保存
   * @return　strScreen
   */
  @RequestMapping(value = "/")
  public String top(HttpServletRequest request, Model model,
      FormMst0108d01 formMst0108d01,
      @ModelAttribute("SearchMst0108d01") SearchMst0108d01 searchMst0108d01) {

    // セッション情報の取得に失敗した場合、ログイン画面（DFCM-COM01-01D01）へ遷移する
    String path = Util.checkSession(model, request,
        CommonConst.MST0108D00_SCREEN_NAME);
    if (!path.equalsIgnoreCase("")) {
      return path;
    }
    mst0108d01Service.getDefaultMess(model);
    // 最大件数値を取得
    CommonGetSystemCom commonGetSystemCom = new CommonGetSystemCom(
        mst0108d01Service.getCommonDao(), null, null,
        null);
    int isearchMax = 0;
    isearchMax = commonGetSystemCom.getCodeSearchMax();
    formMst0108d01.setSearchMax(isearchMax);
    // セッションのデータを取得する
    Map<String, Object> sessionData = Util.getContentsFromSession(request);

    // システム管理者フラグ値を取得する
    String systemAdministratorFlag = String.valueOf(sessionData.get(
        CommonConst.LOGIN_USER_SYS_ADMIN_FLG));
    formMst0108d01.setSystemAdministratorFlag(systemAdministratorFlag);
    // 設定情報ファイル）店舗コード_未指定
    String configShopCode = readPropertiesFileService.getSetting(
        "TEN_CODE_NONE");
    formMst0108d01.setTenCodeNone(configShopCode);

    // ログイン事業所コード値を取得する
    String loginJigyouShoCode = String.valueOf(sessionData.get(
        CommonConst.LOGIN_USER_JIGYOSHO_CODE));
    formMst0108d01.setJigyouShoCode(loginJigyouShoCode);
    mst0108d01Service.initAction(model, formMst0108d01,
        systemAdministratorFlag);
    mst0108d01Service.setHaitaDate(formMst0108d01);
    if (searchMst0108d01 != null) {
      formMst0108d01.setSearchShozokuClassList(searchMst0108d01
          .getSearchShozokuClassList());;
      formMst0108d01.setCstCode(searchMst0108d01.getSearchcstCode());
      formMst0108d01.setItemCode(searchMst0108d01.getSearchitemCode());
      formMst0108d01.setShopCode(searchMst0108d01.getSearchshopCode());
      formMst0108d01.setValidFrom(searchMst0108d01.getSearchvalidFrom());
      formMst0108d01.setValidTo(searchMst0108d01.getSearchvalidTo());
      formMst0108d01.setBunruiCode(searchMst0108d01.getSearchbunruiCode());
      String salesKb = searchMst0108d01.getMasutaKubunClassList();
      formMst0108d01.setSalesKb(salesKb);
      formMst0108d01.setCheckUpdYmd(searchMst0108d01.isCheckDay());
      formMst0108d01.setCheckCancleData(searchMst0108d01.isCheckCancleData());
    }
    String strScreen = "mst/mst0108d01";

    return strScreen;
  }

  /**
   * getSearchResult 検索結果を取得します.
   * 
   * @param request 要求
   * @param model モデル
   * @param formMst0108d01　画面フォーム
   * @param custCode　顧客コード
   * @param officeCode　
   * @param checkType　チェックタイプ
   * @param checkCancleData　データのキャンセルチェック
   * @param itemCode　商品番号
   * @param shopCode　　店舗コード
   * @param ｓhozokuClassList　ｓhozokuClassList
   * @param fromDate　日から
   * @param toDate　現在まで
   * @param bunruiCode　現在まで
   * @param salesKb 　販売KB
   * @return　リスト
   * @throws JsonProcessingException　e
   */
  @RequestMapping(value = "/getSearchResult", method = RequestMethod.POST)
  @ResponseBody
  public FormMst0108d01 getSearchResult(HttpServletRequest request,
      Model model, FormMst0108d01 formMst0108d01,
      @RequestParam("custCode") String custCode,
      @RequestParam("officeCode") String officeCode,
      @RequestParam("checkType") int checkType,
      @RequestParam("checkCancelData") boolean checkCancleData,
      @RequestParam("itemCode") String itemCode,
      @RequestParam("shopCode") String shopCode,
      @RequestParam("ShozokuClassList") String jiCode,
      @RequestParam("from") String fromDate,
      @RequestParam("To") String toDate,
      @RequestParam("bunruiCode") String bunruiCode,
      @RequestParam("salesKb") String salesKb)
          throws JsonProcessingException {

    // set Data for FormMst0108d01

    formMst0108d01.setCstCode(custCode);
    formMst0108d01.setOfficeCode(officeCode);

    formMst0108d01.setCheckType(checkType);
    formMst0108d01.setCheckCancleData(checkCancleData);
    formMst0108d01.setItemCode(itemCode);
    formMst0108d01.setJigyouShoCode(jiCode);
    formMst0108d01.setValidFrom(fromDate);
    formMst0108d01.setValidTo(toDate);
    formMst0108d01.setBunruiCode(bunruiCode);
    formMst0108d01.setSalesKb(salesKb);
    // 最大件数値を取得
    CommonGetSystemCom commonGetSystemCom = new CommonGetSystemCom(
        mst0108d01Service.getCommonDao(), null, null,
        null);
    int isearchMax = 0;
    isearchMax = commonGetSystemCom.getCodeSearchMax();
    formMst0108d01.setSearchMax(isearchMax);
    mst0108d01Service.getSearchResult(request,model, formMst0108d01);
    System.out.println(formMst0108d01.getErrMessage());
    mst0108d01Service.setHaitaDate(formMst0108d01);
    return formMst0108d01;
  }

  /**
   * getSearchResultInit 検索結果を取得します.
   * 
   * @param request 要求
   * @param model モデル
   * @param formMst0108d01　画面フォーム
   * @param custCode　顧客コード
   * @param officeCode　
   * @param checkType　チェックタイプ
   * @param checkCancleData　データのキャンセルチェック
   * @param itemCode　商品番号
   * @param shopCode　　店舗コード
   * @param ｓhozokuClassList　ｓhozokuClassList
   * @param fromDate　日から
   * @param toDate　現在まで
   * @param bunruiCode　現在まで
   * @param salesKb 　販売KB
   * @return　リスト
   * @throws JsonProcessingException　e
   */
  @RequestMapping(value = "/getSearchResultInit", method = RequestMethod.POST)
  @ResponseBody
  public ArrayList<Mst0108d01Model> getSearchResultInit(HttpServletRequest request,
      Model model, FormMst0108d01 formMst0108d01,
      @RequestParam("custCode") String custCode,
      @RequestParam("officeCode") String officeCode,
      @RequestParam("checkType") int checkType,
      @RequestParam("checkCancelData") boolean checkCancleData,
      @RequestParam("itemCode") String itemCode,
      @RequestParam("shopCode") String shopCode,
      @RequestParam("ShozokuClassList") String jiCode,
      @RequestParam("from") String fromDate,
      @RequestParam("To") String toDate,
      @RequestParam("bunruiCode") String bunruiCode,
      @RequestParam("salesKb") String salesKb)
          throws JsonProcessingException {

    // set Data for FormMst0108d01

    formMst0108d01.setCstCode(custCode);
    formMst0108d01.setOfficeCode(officeCode);

    formMst0108d01.setCheckType(checkType);
    formMst0108d01.setCheckCancleData(checkCancleData);
    formMst0108d01.setItemCode(itemCode);
    formMst0108d01.setJigyouShoCode(jiCode);
    formMst0108d01.setValidFrom(fromDate);
    formMst0108d01.setValidTo(toDate);
    formMst0108d01.setBunruiCode(bunruiCode);
    formMst0108d01.setSalesKb(salesKb);
    // 最大件数値を取得
    CommonGetSystemCom commonGetSystemCom = new CommonGetSystemCom(
        mst0108d01Service.getCommonDao(), null, null,
        null);
    int isearchMax = 0;
    isearchMax = commonGetSystemCom.getCodeSearchMax();
    formMst0108d01.setSearchMax(isearchMax);
    ArrayList<Mst0108d01Model> lst = new ArrayList<Mst0108d01Model>();
    lst = (ArrayList<Mst0108d01Model>) 
        mst0108d01Service.getDataForSearchReSult(request, model, 
            formMst0108d01, custCode, shopCode);
    mst0108d01Service.setHaitaDate(formMst0108d01);
    return lst;
  }
  
  /**
   * changePage　変更ページ。
   * 
   * @param request　要求
   * @param rattrs　属性のリダイレクト
   * @param formMst0108d01　画面フォーム
   * @param searchMst0108d01　画面フォーム
   * @return　redirect Mst0108D00
   */
  @RequestMapping(value = "proc", method = RequestMethod.POST)
  public String changePage(HttpServletRequest request,
      RedirectAttributes rattrs,
      FormMst0108d01 formMst0108d01, SearchMst0108d01 searchMst0108d01) {

    // 得意先コード
    String strSelectedCustCode = "";
    // 店舗コード
    String strSelectedShopCode = "";
    // 品目コード
    String strSelectedItemCode = "";
    // 販売区分
    String strSelectedSaleType = "";
    // 適用期間（終了）
    String strSelectedEndTime = "";

    /*// セッションのデータを取得する
    Map<String, Object> sessionData = Util.getContentsFromSession(request);
    // ログイン事業所コード値を取得する
    String loginJigyouShoCode = String.valueOf(sessionData.get(
        CommonConst.LOGIN_USER_JIGYOSHO_CODE));
*/
    if (!formMst0108d01.getViewMode().equalsIgnoreCase(MstConst.SHINKI_MODE)) {
      strSelectedCustCode = formMst0108d01.getStrSelectedCustCode();
      strSelectedItemCode = formMst0108d01.getStrSelectedItemCode();
      strSelectedShopCode = formMst0108d01.getStrSelectedShopCode();
      strSelectedSaleType = formMst0108d01.getStrSelectedSaleType();
      strSelectedEndTime = formMst0108d01.getStrSelectedEndTime();
    }
    
    searchMst0108d01.setSearchcstCode(formMst0108d01.getCstCode());
    searchMst0108d01.setSearchitemCode(formMst0108d01.getItemCode());
    searchMst0108d01.setSearchshopCode(formMst0108d01.getShopCode());
    searchMst0108d01.setSearchvalidFrom(formMst0108d01.getValidFrom());
    searchMst0108d01.setSearchvalidTo(formMst0108d01.getValidTo());
    searchMst0108d01.setSearchbunruiCode(formMst0108d01.getBunruiCode());
    searchMst0108d01.setMasutaKubunClassList(formMst0108d01
        .getMasutaKubunClassList());
    searchMst0108d01.setCheckDay(formMst0108d01.isCheckUpdYmd());
    searchMst0108d01.setCheckCancleData(formMst0108d01.isCheckCancleData());
    searchMst0108d01.setSearchShozokuClassList(formMst0108d01
        .getShozokuClassList());
    
    // 検索条件を登録画面に送信する。
    rattrs.addFlashAttribute("searchMst0108d01", searchMst0108d01);
    // 検索条件を登録画面に送信する。
    rattrs.addFlashAttribute("formMst0108d01", formMst0108d01);
    // モード値を登録画面に送信する。
    rattrs.addFlashAttribute("viewMode", formMst0108d01.getViewMode());
    // 排他値を登録画面に送信する。
    rattrs.addFlashAttribute("haitaDate", formMst0108d01.getHaitaDate());
    rattrs.addFlashAttribute("haitaTime", formMst0108d01.getHaitaTime());
    // 得意先コードを登録画面に送信する。
    rattrs.addFlashAttribute("strSelectedCustCode", strSelectedCustCode);
    // 品目コードを登録画面に送信する。
    rattrs.addFlashAttribute("strSelectedItemCode", strSelectedItemCode);
    // 店舗コードを登録画面に送信する。
    rattrs.addFlashAttribute("strSelectedShopCode", strSelectedShopCode);
    // 販売区分を登録画面に送信する。
    rattrs.addFlashAttribute("strSelectedSaleType", strSelectedSaleType);
    // 適用期間（終了）を登録画面に送信する。
    rattrs.addFlashAttribute("strSelectedEndTime", strSelectedEndTime);
    return "redirect:/mst/MST01-08D02/";
  }
  
  /**
   * checkExist　存在チェック。
   * 
   * @param model　モデル
   * @param formMst0108d01 画面フォーム
   * @param custCode 顧客コード
   * @param officeCode オフィスコード
   * @param checkType チェックタイプ
   * @param checkCancelData データのキャンセルチェック
   * @param itemCode 商品番号
   * @param shopCode 店舗コード
   * @param shozokuClassList shozokuClassList
   * @param fromDate 日から
   * @param toDate 今日
   * @param bunruiCode  bunruiCode
   * @param salesKb 販売KB
   * @return　formMst0108d01
   */
  @RequestMapping(value = "/checkExist", method = RequestMethod.POST)
  @ResponseBody
  public FormMst0108d01 checkExist(Model model, FormMst0108d01 formMst0108d01,
      @RequestParam("custCode") String custCode,
      @RequestParam("officeCode") String officeCode,
      @RequestParam("checkType") int checkType,
      @RequestParam("checkCancelData") boolean checkCancelData,
      @RequestParam("itemCode") String itemCode,
      @RequestParam("shopCode") String shopCode,
      @RequestParam("ShozokuClassList") String shozokuClassList,
      @RequestParam("from") String fromDate, @RequestParam("To") String toDate,
      @RequestParam("bunruiCode") String bunruiCode,
      @RequestParam("salesKb") String salesKb) {

    formMst0108d01.setCstCode(custCode);
    formMst0108d01.setOfficeCode(officeCode);
    formMst0108d01.setCheckType(checkType);
    formMst0108d01.setItemCode(itemCode);
    formMst0108d01.setShopCode(shopCode);
    formMst0108d01.setJigyouShoCode(shozokuClassList);
    formMst0108d01.setValidFrom(fromDate);
    formMst0108d01.setValidTo(toDate);
    formMst0108d01.setBunruiCode(bunruiCode);
    formMst0108d01.setSalesKb(salesKb);
    formMst0108d01.setCheckCancleData(checkCancelData);
    //
    formMst0108d01 = mst0108d01Service.checkExist(model, formMst0108d01);
    return formMst0108d01;
  }

  /**
   * CSV Export
   * 
   * @param formMst0108d01:フォーム
   * @return ページ表示（String（ＪＳＯＮ形））
   * @throws Exception:エラー画面
   */
  @RequestMapping(value = "/exportCsv", method = RequestMethod.POST)
  @ResponseBody
  public FormMst0108d01 exportCsv(HttpServletRequest request, Model model, 
      FormMst0108d01 formMst0108d01) throws Exception {
      mst0108d01Service.checkExist(model, formMst0108d01);
      String relationMasterErrorFlag = formMst0108d01.getRelationMasterErrorFlag();
      if (relationMasterErrorFlag.equalsIgnoreCase("0")) {
        // 最大件数値を取得
        CommonGetSystemCom commonGetSystemCom = new CommonGetSystemCom(
            mst0108d01Service.getCommonDao(), null, null,
            null);
        int isearchMax = 0;
        isearchMax = commonGetSystemCom.getCodeSearchMax();
        formMst0108d01.setSearchMax(isearchMax);
        String jsonData = "";
        ExportCSVMst exportCsv = new ExportCSVMst();
        String strExportCsv = "";
        try {
          strExportCsv = mst0108d01Service.exportDataCsv(request,formMst0108d01);
        } catch (Exception e) {
          e.printStackTrace();
          throw e;
        }
        if (strExportCsv == null) {
          exportCsv = new ExportCSVMst();
          exportCsv.setMessage(readPropertiesFileService.getMessage("COM025-E",
              null));
        } else {
          exportCsv.setStrPathFile(strExportCsv);
        }

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
          jsonData = ow.writeValueAsString(exportCsv);
        } catch (JsonProcessingException e) {
          e.printStackTrace();
          logger.error(e.getMessage());
          throw e;
        }
        formMst0108d01.setJisonData(jsonData);
        
      }
    
    return formMst0108d01;
  }
}
