/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.controller.impl
 * ファイル名:Com0101d01Controller.java
 * 
 * <p>作成者:QuanTran
 * 作成日:2015/09/15
 *  
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/15 1.00 ABV)QuanTran 新規開発
 * -------------------------------------------------------------------------
 *  
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.common.controller.impl;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.component.FormCom0101d01;
import jp.co.daitoku_sh.dfcm.common.component.MstGeneralData;
import jp.co.daitoku_sh.dfcm.common.controller.AbsController;
import jp.co.daitoku_sh.dfcm.common.service.impl.Com0101d01Service;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.InputCheckCom;

/**
 * このアノテーションをつけて、component-scanさせるとControllerとして扱われます。
 * The Com0101d01Controller is an controller layer that
 * simple login system.
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/")
public class Com0101d01Controller extends AbsController{

  // Serviceの定義
  @Autowired
  @Qualifier("com0101d01Service")
  private Com0101d01Service com0101d01Service;

  @Autowired
  @Qualifier("txManager")
  private PlatformTransactionManager txManager;

  @Autowired
  @Qualifier("appContext")
  private ApplicationContext appContext;

  // メッセージPropertiesファイルを読む
  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;


  /**
   * 初期表示処理.
   * 
   * @param formCom0101d01 A model attribute
   * @param userCode Cookie取得
   * @param response Provide HTTP-specific functionality in sending a response
   * @return 画面表示
   */
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String index(FormCom0101d01 formCom0101d01,
      @CookieValue(value = CommonConst.COOKIE_KEY, 
      defaultValue = CommonConst.EMPTY) String userCode,
      Model model,
      HttpServletResponse response) {
    // 画面マッピング
    final String screenMapping = "common/com0101d01";
    // Create cookie and set it in response
    Cookie cookie = new Cookie(CommonConst.COOKIE_KEY, userCode);
    cookie.setMaxAge(CommonConst.COOKIE_MAX_AGE);
    response.addCookie(cookie);
    formCom0101d01.setUserid(userCode);
    // 担当者コードがCookieに保存されている場合、以下の初期値をセットする
    if (userCode.equalsIgnoreCase(CommonConst.EMPTY)) {
      formCom0101d01.setChkIdRemainder(false);
      // 担当者コードがCookieに保存されていない場合、以下の初期値をセットする
    } else {
      formCom0101d01.setChkIdRemainder(true);
    }
    
    com0101d01Service.getDefaultMess(model);
    return screenMapping;
  }

  /**
   * ログイン.
   * 
   * @param formCom0101d01 A model attribute
   * @param response Provide HTTP-specific functionality in sending a response
   * @param model A holder for model attributes
   * @return 画面表示
   * @throws ParseException exception
   * @throws NoSuchAlgorithmException exception
   */
  @RequestMapping(value = "proc", method = RequestMethod.POST, params = "login")
  public String login(FormCom0101d01 formCom0101d01,
      HttpServletResponse response, Model model, HttpServletRequest request)
          throws ParseException, NoSuchAlgorithmException {
    com0101d01Service.getDefaultMess(model);
    String errMsg = CommonConst.EMPTY;

    // 【必須チェック】
    errMsg = com0101d01Service.initialProcessing(formCom0101d01, model);
    // エラーとする場合、
    if (!errMsg.equalsIgnoreCase(CommonConst.EMPTY)) {
      model.addAttribute("errMsg", errMsg);
      return "common/com0101d01";
    }
    // [変数]ログインエラー最大回数
    int maxNumErrLogin = 0;
    CommonGetSystemCom com = new CommonGetSystemCom(com0101d01Service
        .getCommonDao(), txManager, appContext, readPropertiesFileService);
    // データ取得
    List<MstGeneralData> mstGeneralDatas = com.getMstGeneralConf(
        "login_error_max", null);
    if (mstGeneralDatas != null && mstGeneralDatas.size() > 0
        && mstGeneralDatas.get(0) != null) {
      maxNumErrLogin = Integer.parseInt(mstGeneralDatas.get(0).getTarget1());
    } else {
      maxNumErrLogin = CommonConst.MAX_NUM_ERR_LOGIN;
    }

    String result = InputCheckCom.chkNum(String.valueOf(maxNumErrLogin), false);

    if (result != null) {
      maxNumErrLogin = CommonConst.MAX_NUM_ERR_LOGIN;
    }

    // 初期パスワード取得
    String initPassword = CommonConst.EMPTY;
    mstGeneralDatas = com.getMstGeneralConf("Init_Password", null);
    if (mstGeneralDatas != null && mstGeneralDatas.size() > 0
        && mstGeneralDatas.get(0) != null) {
      initPassword = mstGeneralDatas.get(0).getTarget1();
    } else {
      initPassword = null;
    }

    // 業務日付取得
    String strAplDate = com.getAplDate();
    if (strAplDate == null) {
      Date date = new Date(); // 現在日時取得
      SimpleDateFormat sdf = new SimpleDateFormat(CommonConst.DATE_FORMAT_YMD); // 日付フォーマットクラス
      strAplDate = sdf.format(date); // システム日付を格納
    }
    // 【マスタ取得】
    errMsg = com0101d01Service.getDataFromMasterTable(formCom0101d01, model,
        response, request, maxNumErrLogin, strAplDate, txManager);
    // エラーとする場合、
    if (!errMsg.equalsIgnoreCase(CommonConst.EMPTY)) {
      model.addAttribute("errMsg", errMsg);
      
      return "common/com0101d01";
    }
    String password = CommonConst.EMPTY;
    password = formCom0101d01.getPassword();
    // 画面遷移
    if (initPassword.equalsIgnoreCase(password)) {
      return "redirect:/com/COM01-01D03/";
    }
    
    // それ以外の場合、メニュー（DFCM-COM01-01D02）へ遷移する
    return "redirect:/com/COM01-01D02/";
  }
}
