/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.controller.impl
 * ファイル名:Com0101d03Controller.java
 * 
 * <p>作成者:QuanTran
 * 作成日:2015/09/21
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/21 1.00 ABV)QuanTran 新規作成
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.common.controller.impl;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.component.FormCom0101d03;
import jp.co.daitoku_sh.dfcm.common.component.MstGeneralData;
import jp.co.daitoku_sh.dfcm.common.controller.AbsController;
import jp.co.daitoku_sh.dfcm.common.db.model.MstUser;
import jp.co.daitoku_sh.dfcm.common.service.impl.Com0101d03Service;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;

/**
 * このアノテーションをつけて、component-scanさせるとControllerとして扱われます。
 * The Com0101d02Controller is an controller layer that
 * simple change password.
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/com/COM01-01D03/")
public class Com0101d03Controller extends AbsController{
  
  /** ログ出力用 */
  private Dfcmlogger logger = new Dfcmlogger();
  
  @Autowired
  @Qualifier("com0101d03Service")
  private Com0101d03Service com0101d03Service;
  
  
  // メッセージPropertiesファイルを読む
  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;
  
  @Autowired
  @Qualifier("txManager")
  private PlatformTransactionManager txManager;

  @Autowired
  @Qualifier("appContext")
  private ApplicationContext appContext;
  /**
   * 初期表示処理
   * 
   * @return 画面表示
   */
  @RequestMapping(value = "/")
  public String top(HttpServletRequest request, FormCom0101d03 formCom0101d03,
      Model model) {
    // 【セッション取得】
    // セッション情報を[変数]セッションにセットする
    // セッション情報の取得に失敗した場合、ログイン画面（DFCM-COM01-01D01）へ遷移する
    String path = Util.checkSession(model, request,
        CommonConst.COM0101D03_SCREEN_NAME);
    if (!path.equalsIgnoreCase(CommonConst.EMPTY)) {
      return path;
    }
    String strErrorId = CommonConst.EMPTY;
    com0101d03Service.setHaitaDate(formCom0101d03);
    com0101d03Service.getDefaultMess(model);
    model.addAttribute("lstErrorID", strErrorId);
    // 画面マッピング
    return "common/com0101d03";
  }

  /**
   * ログインパスワード変更処理.
   * 
   * @param formCom0101d03 A model attribute
   * @param model A holder for model attributes
   * @param request Receive data sent by Web client
   * @return screen mapping
   * @throws NoSuchAlgorithmException a non-empty description.
   */
  @RequestMapping(value = "proc", method = RequestMethod.POST, params = "changePassword")
  public String changePassword(FormCom0101d03 formCom0101d03, Model model,
      HttpServletRequest request) throws NoSuchAlgorithmException {
    ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    ErrorMessages errMess = null;
    String path = Util.checkSession(model, request,
        CommonConst.COM0101D03_SCREEN_NAME);
    if (!path.equalsIgnoreCase(CommonConst.EMPTY)) {
      return path;
    }
    com0101d03Service.getDefaultMess(model);
    model.addAttribute("password", formCom0101d03.getPassword());
    model.addAttribute("newPass", formCom0101d03.getNewPass());
    model.addAttribute("reEnterNewPass", formCom0101d03.getReEnterNewPass());
    String screenMapping = "common/com0101d03";
    ArrayList<ErrorMessages> arrLstErrMsg = com0101d03Service.requiredCheck(formCom0101d03,model);
    // 【必須チェック】
    if (arrLstErrMsg.size() > 0 ) {
      model.addAttribute("errorMessages", arrLstErrMsg);
      return screenMapping;
    }

    // 【入力チェック】
    String errMsg = com0101d03Service.inputValidation(formCom0101d03, model);
    if (!errMsg.equalsIgnoreCase(CommonConst.EMPTY)) {
      errMess = new ErrorMessages();
      errMess.setMessageContent(errMsg);
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      return screenMapping;
    }

    CommonGetSystemCom com = new CommonGetSystemCom(com0101d03Service
        .getCommonDao(), txManager, appContext, readPropertiesFileService);
    // 【データ取得】
    // 初期パスワード取得
    String initPassword = null;
    List<MstGeneralData> mstGeneralDatas = null;
    try {
      mstGeneralDatas = com.getMstGeneralConf("Init_Password", null);
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
    if (mstGeneralDatas != null && mstGeneralDatas.size() > 0
        && mstGeneralDatas.get(0) != null) {
      initPassword = mstGeneralDatas.get(0).getTarget1();
    } else {
      initPassword = null;
    }
    // 【担当者情報取得】
    Map<String, Object> map = Util.getContentsFromSession(request);
    String userCode = String.valueOf(map.get("UserCode"));
    ArrayList<MstUser> users = com0101d03Service.getData(formCom0101d03, userCode);
    ArrayList<String> paramMess = null;
    // 【パスワードチェック】
    // [画面]現在のパスワードをMD5で暗号化し、[変数]変換済パスワードにセットする
    String transPass = Util.createDigest(formCom0101d03.getPassword());
    String curPass = users.get(0).getPassword();
    String strErrorId = CommonConst.EMPTY;
    // [変数]変換済パスワード <> USR1.ログインパスワードの場合、エラーとする
    if (!transPass.equalsIgnoreCase(curPass)) {
      strErrorId += "password" + MstConst.DELIMITER_ERROR;
      paramMess = new ArrayList<String>();
      paramMess.add("パスワード");
      errMsg = readPropertiesFileService.getMessage("COM001-E", paramMess);
      errMess = new ErrorMessages();
      errMess.setMessageContent(errMsg);
      lstErrMess.add(errMess);
      model.addAttribute("lstErrorID", strErrorId);
      model.addAttribute("errorMessages", lstErrMess);
      return screenMapping;
    }

    // [変数]変換済パスワード = [変数]初期パスワード の場合、エラーとする
    if (formCom0101d03.getNewPass().equalsIgnoreCase(initPassword)) {
      paramMess = new ArrayList<String>();
      paramMess.add("新しいパスワード");
      paramMess.add("初期パスワード");
      errMsg = readPropertiesFileService.getMessage("COM007-E", paramMess);
      errMess = new ErrorMessages();
      errMess.setMessageContent(errMsg);
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      strErrorId += "newPass" + MstConst.DELIMITER_ERROR;
      model.addAttribute("lstErrorID", strErrorId);
      return screenMapping;
    }
    // 【終了処理・例外処理】
    errMess = com0101d03Service.endProcessing(formCom0101d03, userCode, txManager);
    if (errMess != null) {
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      strErrorId += "password" + MstConst.DELIMITER_ERROR;
      model.addAttribute("lstErrorID", strErrorId);
      return screenMapping;
    } else {
      com0101d03Service.setHaitaDate(formCom0101d03);
      lstErrMess = new ArrayList<ErrorMessages>();
      paramMess = new ArrayList<String>();
      paramMess.add("パスワードの更新");
      errMsg = readPropertiesFileService.getMessage("COM002-I", paramMess);
      errMess = new ErrorMessages();
      errMess.setMessageContent(errMsg);
      lstErrMess.add(errMess);
      model.addAttribute("infoMessages", lstErrMess);
    }
    return screenMapping;
  }
}
