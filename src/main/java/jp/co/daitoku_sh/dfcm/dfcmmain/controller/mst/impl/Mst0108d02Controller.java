/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl
 * ファイル名:Mst0108d02Controller.java
 * 
 * <p>作成者: グエン　リユオン　ギア
 * 作成日:2015/11/11
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

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.controller.AbsController;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCustTanka;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.CastData0108d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0108d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0108d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.SearchMst0108d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl.Mst0108d02Service;

@Controller
@RequestMapping(value = "/mst/MST01-08D02/")
public class Mst0108d02Controller extends AbsController {

  // Serviceの定義
  @Autowired
  @Qualifier("mst0108d02Service")
  private Mst0108d02Service mst0108d02Service;

  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;


  /**
   * 【オンライン得意先変換マスタ一覧画面初期表示処理】.
   * 
   * @param request 要求
   * @param model モデル
   * @param formMst0108d02　画面フォーム
   * @param formMst0108d01　画面フォーム
   * @param custCode　顧客コード
   * @param itemCode　商品番号
   * @param shopCode　店舗コード
   * @param salesKb　販売KB
   * @param EndTime　終了時間
   * @param viewMode　ビューモード
   * @param haitaDate　haita日
   * @param haitaTime　haita時間
   * @return　strScreen
   */
  @RequestMapping(value = "/")
  public String top(HttpServletRequest request, Model model,
      FormMst0108d02 formMst0108d02,FormMst0108d01 formMst0108d01,
      SearchMst0108d01 searchMst0108d01,
      @ModelAttribute("strSelectedCustCode") String custCode,
      @ModelAttribute("strSelectedItemCode") String itemCode,
      @ModelAttribute("strSelectedShopCode") String shopCode,
      @ModelAttribute("strSelectedSaleType") String salesKb,
      @ModelAttribute("strSelectedEndTime") String endTime,
      @ModelAttribute("viewMode") String viewMode,
      @ModelAttribute("haitaDate") String haitaDate,
      @ModelAttribute("haitaTime") String haitaTime) {
    // セッション情報の取得に失敗した場合、ログイン画面（DFCM-COM01-01D01）へ遷移する
    String path = Util.checkSession(model, request,
        CommonConst.MST0108D00_SCREEN_NAME);
    if (!path.equalsIgnoreCase("")) {
      return path;
    }
    
    mst0108d02Service.getDefaultMess(model);
    model.addAttribute("modeView", viewMode);
    formMst0108d02.setStrSelectedCustCode(custCode);
    formMst0108d02.setStrSelectedItemCode(itemCode);
    formMst0108d02.setStrSelectedShopCode(shopCode);
    formMst0108d02.setStrSelectedSaleType(salesKb);
    formMst0108d02.setStrSelectedEndTime(endTime);
    formMst0108d02.setSalesKb(salesKb);
    formMst0108d02.setSearchShozokuClassList(searchMst0108d01.getSearchShozokuClassList());
    formMst0108d02.setSearchCstCode(searchMst0108d01.getSearchcstCode());
    formMst0108d02.setSearchItemCode(searchMst0108d01.getSearchitemCode());
    formMst0108d02.setSearchShopCode(searchMst0108d01.getSearchshopCode());
    formMst0108d02.setSearchMasutaKubunClassList(searchMst0108d01.getMasutaKubunClassList());
    formMst0108d02.setSearchBunruiCode(searchMst0108d01.getSearchbunruiCode());
    formMst0108d02.setSearchValidFrom(searchMst0108d01.getSearchvalidFrom());
    formMst0108d02.setSearchValidTo(searchMst0108d01.getSearchvalidTo());
    formMst0108d02.setSearchcheckDay(searchMst0108d01.isCheckDay());
    formMst0108d02.setSearchcheckCancleData(searchMst0108d01.isCheckCancleData());
    
    // セッションのデータを取得する
    Map<String, Object> sessionData = Util.getContentsFromSession(request);

    // システム管理者フラグ値を取得する
    String sysAdminFlag = String.valueOf(sessionData.get("SysAdminFlg")).trim();
    formMst0108d02.setSysAdminFlag(sysAdminFlag);
    // ログイン所属事業所コードを取得する
    String loginJigyouShoCode = String.valueOf(sessionData.get("JigyoshoCode")).trim();
    formMst0108d02.setLoginJigyouShoCode(loginJigyouShoCode);
    // 排他設定
    formMst0108d02.setHaitaDate(haitaDate);
    formMst0108d02.setHaitaTime(haitaTime);
    // ビュー画面を呼ぶ
    String strScreen = "mst/mst0108d02";
    // デフォルトメッセージを取得する
    mst0108d02Service.init(model, formMst0108d02, 
        formMst0108d01, custCode, viewMode, itemCode, salesKb, 
        shopCode, endTime, sysAdminFlag, loginJigyouShoCode);
    return strScreen;
  }
  
  /**
   * clear 明確な.
   * 
   * @param model モデル
   * @param formMst0108d02　画面フォーム
   * @param custCode　顧客コード
   * @param itemCode　商品番号
   * @param shopCode　店舗コード
   * @param salesKb　販売KB
   * @param validTo　への有効な
   * @param validFrom　から有効
   * @return　list
   */
  @RequestMapping(value = "/clear", method = RequestMethod.POST)
  @ResponseBody
  public ArrayList<MstCustTanka> clear(Model model, FormMst0108d02 formMst0108d02,
      @RequestParam("custCode") String custCode,
      @RequestParam("itemCode") String itemCode,
      @RequestParam("shopCode") String shopCode,
      @RequestParam("salesKb") String salesKb,
      @RequestParam("validTo") String validTo,
      @RequestParam("validFrom") String validFrom) {
    
    formMst0108d02.setCstCode(custCode);
    formMst0108d02.setItemCode(itemCode);
    if (shopCode.equalsIgnoreCase("")) {
      shopCode = readPropertiesFileService.getSetting(
          "TEN_CODE_NONE");
    }
    formMst0108d02.setShopCode(shopCode);
    formMst0108d02.setSalesKb(salesKb);
    formMst0108d02.setValidFrom(validFrom);
    formMst0108d02.setValidTo(validTo);
    ArrayList<MstCustTanka> lst = mst0108d02Service.getDataCusttomer(model, formMst0108d02);
    
    return lst ;
  }
  
  /**
   * 戻るボタンを押した処理.
   * 
   * @param rattrs:送信パラメーターの設定
   * @param formMst0109d02:フォーム
   * @return 画面表示
   */
  @RequestMapping(value = "", params = "Return", method = RequestMethod.POST)
  public String returnPage(RedirectAttributes rattrs,
      FormMst0108d02 formMst0108d02) {
    // [変数]検索条件保持クラス
    SearchMst0108d01 searchMst0108d01 = new SearchMst0108d01();
    
    searchMst0108d01.setSearchShozokuClassList(formMst0108d02.getSearchShozokuClassList());
    searchMst0108d01.setSearchcstCode(formMst0108d02.getSearchCstCode());
    searchMst0108d01.setSearchitemCode(formMst0108d02.getSearchItemCode());
    searchMst0108d01.setSearchshopCode(formMst0108d02.getSearchShopCode());
    searchMst0108d01.setMasutaKubunClassList(formMst0108d02.getSearchMasutaKubunClassList());
    searchMst0108d01.setSearchvalidTo(formMst0108d02.getSearchValidTo());
    searchMst0108d01.setSearchvalidFrom(formMst0108d02.getSearchValidFrom());
    searchMst0108d01.setSearchbunruiCode(formMst0108d02.getSearchBunruiCode());
    searchMst0108d01.setCheckDay(formMst0108d02.isSearchcheckDay());
    searchMst0108d01.setCheckCancleData(formMst0108d02.isSearchcheckCancleData());
    
    rattrs.addFlashAttribute("SearchMst0108d01", searchMst0108d01);

    return "redirect:/mst/MST01-08D00/";
  }
  
  /**
   * 登録ボタンを押した処理.
   * 
   * @param formMst0108d02:フォーム
   * @param model:モデル
   * @param httpRequest：httpリクエスト
   * @return 画面表示
   * @throws Exception　エラー画面
   */
  
  @RequestMapping(value = "", params = "Register", method = RequestMethod.POST)
  public String register(HttpServletRequest request,
      FormMst0108d02 formMst0108d02, Model model,
      CastData0108d02 castData0108d02) throws Exception {

    String path = Util.checkSession(model, request,
        CommonConst.MST0108D00_SCREEN_NAME);
    if (!path.equalsIgnoreCase("")) {
      return path;
    }
    model.addAttribute("modeView", formMst0108d02.getViewMode());
    String jigyoushoCode = formMst0108d02.getLoginJigyouShoCode();
    String custCode = formMst0108d02.getCstCode();
    if (custCode != null && !custCode.equalsIgnoreCase("")) {
      if (custCode.length() < 7) {
        custCode = Util.addLeadingZeros(custCode, 7);
        formMst0108d02.setCstCode(custCode);
      }
    }
    
    String itemCode = formMst0108d02.getItemCode();
    
    if (itemCode != null && !itemCode.equalsIgnoreCase("")) {
      if (itemCode.length() < 6) {
        itemCode = Util.addLeadingZeros(itemCode, 6);
        formMst0108d02.setItemCode(itemCode);
      }
    }
    //適用期間（開始）
    String validFrom = formMst0108d02.getValidFrom();
    //適用期間（終了）
    String validTo = formMst0108d02.getValidTo();
    //販売区分
    String ddlSalesKb = formMst0108d02.getMasutaKubunClassList();
    //replace 適用期間
    validFrom = validFrom.replace("/", "");
    validTo = validTo.replace("/", "");
    // 画面上の期間適用保存
    String validFromNotReplace = DateUtil.addSlash(validFrom);
    String validToNotReplace = DateUtil.addSlash(validTo);
    formMst0108d02.setValidFrom(validFrom);
    formMst0108d02.setValidTo(validTo);
    mst0108d02Service.getDefaultMess(model);
    String shopCode = formMst0108d02.getShopCode();
    //ビューモード
    String viewMode = formMst0108d02.getViewMode();
    //もしビューモード別の「編集」または「削除」
    if (viewMode.equalsIgnoreCase(MstConst.TORIKESI_MODE)
        || viewMode.equalsIgnoreCase(MstConst.TEISEI_MODE)) {
      //販売KBを取得
      String salesKb = formMst0108d02.getSalesKb();
      formMst0108d02.setSalesKb(salesKb);
    } else {
      //販売区分取得
      formMst0108d02.setSalesKb(ddlSalesKb);
    }
    //セット販売区分画面に
    mst0108d02Service.setMasutaKubun_Dll(model, formMst0108d02.getSalesKb());
    //入力を確認してください
    boolean error = mst0108d02Service.checkInput(formMst0108d02, model);
    //if error != true
    if (error) {
      //得意先マスタ検索
      boolean checkData = mst0108d02Service.getDeliveryData(model, formMst0108d02,
          custCode, itemCode, shopCode, jigyoushoCode);
      if (!checkData) {
          if (viewMode.equalsIgnoreCase(MstConst.SHINKI_MODE) 
              || viewMode.equalsIgnoreCase(MstConst.COPY_MODE)) {
            //(1) 画面入力値で、適用期間が重複している得意先品目価格マスタの検索を行う。
            ArrayList<MstCustTanka> lstInfoCustTanka = mst0108d02Service
                .searchInforCustTanka(formMst0108d02,
                    castData0108d02, request);
            //(2-1) 取得件数　＞　0件　の場合
            if (lstInfoCustTanka.size() > 0) {
              //① 確認メッセージダイアログを表示する。
              formMst0108d02.setFlag(1);
            } else {
              mst0108d02Service.checkValidDay(formMst0108d02, 
                  castData0108d02, lstInfoCustTanka, request);
            }
            
          } else {
            if (viewMode.equalsIgnoreCase(MstConst.TEISEI_MODE) || viewMode
                .equalsIgnoreCase(MstConst.TORIKESI_MODE)) {
                //排他、既存の訂正区分マスタ情報のチェック
                boolean checkhaita =  mst0108d02Service.checkSpec(formMst0108d02, model);
                if (!checkhaita) {
                  boolean flagRegister = mst0108d02Service.register(model, 
                      formMst0108d02, castData0108d02, request);
                    if (flagRegister) {
                      formMst0108d02.setFlag(0);
                      // 排他設定
                      formMst0108d02.setHaitaDate(DateUtil.getSysDate());
                      formMst0108d02.setHaitaTime(DateUtil.getSysTime());
                    }
                } else {
                  formMst0108d02.setFlag(3);
                }
            } 
          }
      } else {
        formMst0108d02.setFlag(3);
        
      }
      formMst0108d02.setValidFrom(validFromNotReplace);
      formMst0108d02.setValidTo(validToNotReplace);
      return "mst/mst0108d02";
    } else {
      formMst0108d02.setFlag(0);
      formMst0108d02.setPass(0);
      formMst0108d02.setValidFrom(validFromNotReplace);
      formMst0108d02.setValidTo(validToNotReplace);
      return "mst/mst0108d02";
    }

  }
  /**
   * 登録ボタンを押した処理.
   * @param formMst0108d02:フォーム
   * @param model:モデル
   * @param httpRequest：httpリクエスト
   * @return 画面表示
   * @throws Exception　エラー画面
   */
  @RequestMapping(value = "", params = "Register1", method = RequestMethod.POST)
  public String register1(HttpServletRequest request,FormMst0108d02 formMst0108d02, Model model,
      CastData0108d02 castData0108d02) throws Exception {

    // ② OKボタン押下
    // セッション情報の取得に失敗した場合、ログイン画面（DFCM-COM01-01D01）へ遷移する
    String path = Util.checkSession(model, request,
        CommonConst.MST0108D00_SCREEN_NAME);
    if (!path.equalsIgnoreCase("")) {
      return path;
    }
    model.addAttribute("modeView", formMst0108d02.getViewMode());

    String validFrom = formMst0108d02.getValidFrom();
    validFrom = validFrom.replace("/", "");

    String validTo = formMst0108d02.getValidTo();
    validTo = validTo.replace("/", "");

    formMst0108d02.setValidFrom(validFrom);
    formMst0108d02.setValidTo(validTo);
    int flag = formMst0108d02.getFlag();
    if (flag != 1) {
      return "mst/mst0108d02";
    } else {
      formMst0108d02.setFlag(2);
    }
    String viewMode = formMst0108d02.getViewMode();
    mst0108d02Service.setMasutaKubun_Dll(model, formMst0108d02.getSalesKb());
    mst0108d02Service.getDefaultMess(model);
    boolean masterCheckErrorFlag = formMst0108d02.isMasterCheckErrorFlag();
    // ド編集FLG = True の場合、当処理を行う。（既存得意先品目価格マスタレコード編集処理）
    if (masterCheckErrorFlag != true) {
      // (1) 画面入力値で、適用期間が重複している得意先品目価格マスタの検索を行う。
      ArrayList<MstCustTanka> lstInfoCustTanka = mst0108d02Service
          .searchInforCustTanka(formMst0108d02,
              castData0108d02, request);
      if (viewMode.equalsIgnoreCase(MstConst.SHINKI_MODE)
          || viewMode.equalsIgnoreCase(MstConst.COPY_MODE)) {

        mst0108d02Service.checkValidDay(formMst0108d02, castData0108d02, lstInfoCustTanka, request);
      }
    } else {
      formMst0108d02.setFlag(3);
    }

    String validFromNotReplace = DateUtil.addSlash(validFrom);
    String validToNotReplace = DateUtil.addSlash(validTo);

    formMst0108d02.setValidFrom(validFromNotReplace);
    formMst0108d02.setValidTo(validToNotReplace);
    return "mst/mst0108d02";
  }
}
