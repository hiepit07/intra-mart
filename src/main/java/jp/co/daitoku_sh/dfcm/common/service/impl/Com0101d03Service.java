/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.service.impl
 * ファイル名:Com0101d03Service.java
 * 
 * <p>作成者:QuanTran
 * 作成日:2015/09/21
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/21 1.00 ABV)QuanTran　新規作成
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.common.service.impl;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.component.FormCom0101d03;
import jp.co.daitoku_sh.dfcm.common.dao.impl.Com0101d03Dao;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.db.model.MstUser;
import jp.co.daitoku_sh.dfcm.common.db.model.MstUserExample;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * サービスクラス.
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class Com0101d03Service extends AbsService {

  /** ログ出力用 */
  private Dfcmlogger logger = new Dfcmlogger();
  
  @Autowired
  @Qualifier("com0101d03Dao")
  private Com0101d03Dao com0101d03Dao;

  @Autowired
  @Qualifier("commonDao")
  private CommonDao commonDao;
  // メッセージPropertiesファイルを読む
  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  public CommonDao getCommonDao() {
    return commonDao;
  }

  public void setCommonDao(CommonDao commonDao) {
    this.commonDao = commonDao;
  }

  /**
   * 【必須チェック】.
   * 
   * @param formCom0101d03 A model attribute
   * @param model A holder for model attributes
   * @return Listエラーメッセージ
   */
  public ArrayList<ErrorMessages> requiredCheck(FormCom0101d03 formCom0101d03, Model model) {    
    ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    ErrorMessages errMess = new ErrorMessages();
    ArrayList<String> paramMess = null;
    String strErrorId = CommonConst.EMPTY;
    // [画面]現在のパスワードが未入力の場合、エラーとする
    if (formCom0101d03.getPassword().equalsIgnoreCase(CommonConst.EMPTY)) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("現在のパスワード");
      errMess.setMessageContent(readPropertiesFileService.getMessage("COM006-E", paramMess));
      lstErrMess.add(errMess);
      strErrorId += "password" + MstConst.DELIMITER_ERROR;      
    }

    // [画面]新しいパスワードが未入力の場合、エラーとする
    if (formCom0101d03.getNewPass().equalsIgnoreCase(CommonConst.EMPTY)) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("新しいパスワード");
      errMess.setMessageContent(readPropertiesFileService.getMessage("COM006-E", paramMess));
      lstErrMess.add(errMess);
      strErrorId += "newPass" + MstConst.DELIMITER_ERROR;
    }

    // [画面]新しいパスワード（再入力）が未入力の場合、エラーとする
    if (formCom0101d03.getReEnterNewPass().equalsIgnoreCase(CommonConst.EMPTY)) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("新しいパスワード（再入力）");
      errMess.setMessageContent(readPropertiesFileService.getMessage("COM006-E", paramMess));
      lstErrMess.add(errMess);
      strErrorId += "reEnterNewPass" + MstConst.DELIMITER_ERROR;      
    }
    model.addAttribute("lstErrorID", strErrorId);    
    return lstErrMess;
  }

  /**
   * 【入力チェック】.
   * 
   * @param formCom0101d03 A model attribute
   * @param model A holder for model attributes
   * @return エラーメッセージ
   */
  public String inputValidation(FormCom0101d03 formCom0101d03, Model model) {
    ArrayList<String> paramMess = null;
    String strErrorId = CommonConst.EMPTY;
    String errMsg = CommonConst.EMPTY;
    // [画面]現在のパスワードが未入力の場合、エラーとする
    String curPass = formCom0101d03.getPassword();
    String newPass = formCom0101d03.getNewPass();
    String reEnterPass = formCom0101d03.getReEnterNewPass();
    // [画面]新しいパスワード <> [画面]新しいパスワード（再入力）の場合、エラーとする
    if (!newPass.equalsIgnoreCase(reEnterPass)) {
      paramMess = new ArrayList<String>();
      paramMess.add("新しいパスワード（再入力）");
      errMsg = readPropertiesFileService.getMessage("COM001-E", paramMess);
      strErrorId += "reEnterNewPass" + MstConst.DELIMITER_ERROR;
      model.addAttribute("lstErrorID", strErrorId);
    }

    // [画面]現在のパスワード == [画面]新しいパスワード == [画面]新しいパスワード（再入力）の場合、エラーとする
    if (curPass.equalsIgnoreCase(newPass) && newPass.equalsIgnoreCase(reEnterPass)) {
      paramMess = new ArrayList<String>();
      paramMess.add("新しいパスワード");
      paramMess.add("現在のパスワード");
      errMsg = readPropertiesFileService.getMessage("COM007-E", paramMess);
      strErrorId += "newPass" + MstConst.DELIMITER_ERROR;
      model.addAttribute("lstErrorID", strErrorId);
    }
    return errMsg;
  }

  /**
   * 担当者マスタデータ取得.
   * 
   * @param formCom0101d03 A model attribute
   * @param userCode 担当者コード
   * @return 担当者マスタから担当者情報を取得する
   */
  public ArrayList<MstUser> getData(FormCom0101d03 formCom0101d03,
      String userCode) {
    Map<String, Object> parms = new HashMap<String, Object>();
    parms.put("User_Code", userCode);
    ArrayList<MstUser> users = null;
    try {
      users = com0101d03Dao.getCom0101d03Mapper().getPersonalCharge(parms);
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
    return users;
  }

  /**
   * 【終了処理・例外処理】.
   * 
   * @param formCom0101d03 A model attribute
   * @param userCode 担当者コード
   * @param txManager:txManager
   * @throws NoSuchAlgorithmException No comment
   */
  public ErrorMessages endProcessing(FormCom0101d03 formCom0101d03,
      String userCode, 
      PlatformTransactionManager txManager) throws NoSuchAlgorithmException {
    DefaultTransactionDefinition def = null;
    TransactionStatus status = null;
    ErrorMessages errMsg = null;
    if (checkHaita(userCode, formCom0101d03.getHaitaDate(), formCom0101d03
        .getHaitaTime())) {
      errMsg = new ErrorMessages();
      errMsg.setMessageContent(readPropertiesFileService.getMessage(
          "COM031-E", null));
      return errMsg;
    }
    
    try {
      String newPass = Util.createDigest(formCom0101d03.getNewPass());
      MstUser user = new MstUser();
      user.setPassword(newPass);
      setUserData(user, userCode, "COM01-01D03");
      MstUserExample example = new MstUserExample();
      example.createCriteria().andUserCodeEqualTo(userCode).andStsCodeEqualTo("1");
      def = new DefaultTransactionDefinition();
      status = txManager.getTransaction(def);
      com0101d03Dao.getMstUserMapper().updateByExampleSelective(user, example);
      txManager.commit(status);
    } catch (MyBatisSystemException e) {
      txManager.rollback(status);
      logger.error(e.getMessage());
      throw e;
    }
    return errMsg;
  }
  
  /**
   * Defaultメッセージの取得.
   * 
   * @param model  A holder for model attributes
   */
  public void getDefaultMess(Model model) {
    // メッセージのParam
    ArrayList<String> paramMess = null;
    ArrayList<DefaultMessages> defaultMsgLst = null;
    DefaultMessages defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-I");
    defaultMsgLst = new ArrayList<DefaultMessages>();
    paramMess = new ArrayList<String>();
    defaultMsg.setMessageTitle("確認メッセージ");
    paramMess = new ArrayList<String>();
    paramMess.add("CSVファイルを出力");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-I", paramMess));
   
    defaultMsgLst.add(defaultMsg);
    
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage("COM001-E", null));
    defaultMsgLst.add(defaultMsg);
    
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM016-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage("COM016-E", null));
    defaultMsgLst.add(defaultMsg);
    
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM006-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage("COM006-E", null));
    defaultMsgLst.add(defaultMsg);
    
    model.addAttribute("defaultMessages", defaultMsgLst);
  }
  
  /**
   * 共通項目の設定.
   * 
   * @param data MstUser
   * @param strUserID ユーザID
   * @param strProgID プログラムID
   * @return Object
   */
  private MstUser setUserData(MstUser data, String updUserid, String updPgid) {
    String currentDate = DateUtil.getSysDate();
    String currentTime = DateUtil.getSysTime();
    data.setUpdUserid(updUserid);
    data.setUpdPgid(updPgid);
    data.setUpdYmd(currentDate);
    data.setUpdTime(currentTime);
    return data;
  }
  
  /**
   * 排他チェック
   * 
   * @param strUserCode No comment
   * @param strHaitaDate No comment
   * @param strHaitaTime No comment
   * @return boolean true: エラー false: 普通
   */
  public boolean checkHaita(String strUserCode, String strHaitaDate,
      String strHaitaTime) {

    // User情報取得
    MstUser mstUser = new MstUser();
    mstUser = getUserInfo(strUserCode);
    // 共通項目.修正年月日 ＋ 共通項目．修正時刻 > 表示時システム日付 の場合
    int dateRegDb = Integer.parseInt(mstUser.getUpdYmd());
    int dateHidden = Integer.parseInt(strHaitaDate);

    if (dateRegDb > dateHidden) {
      return true;
    } else if (dateRegDb == dateHidden) {
      dateRegDb = Integer.parseInt(mstUser.getUpdTime());
      dateHidden = Integer.parseInt(strHaitaTime);
      if (dateRegDb > dateHidden) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * 担当者コードと担当者情報を取得する。.
   * @param String strUserCode
   * @return MstUser
   * @exception
   */
  private MstUser getUserInfo(String strUserCode) {
    MstUser mstUser = null;
    try {
      mstUser = com0101d03Dao.getMstUserMapper().selectByPrimaryKey(
          strUserCode);
      return mstUser;
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
  }
  
  /**
   * 排他の設定.
   * 
   * @param formCom0101d03 フォーム(jspの modelAttribute="FormCom0101d03" とリンク）
   */
  public void setHaitaDate(FormCom0101d03 formCom0101d03) {
    String currentDate = DateUtil.getSysDate();
    String currentTime = DateUtil.getSysTime();
    formCom0101d03.setHaitaDate(currentDate);
    formCom0101d03.setHaitaTime(currentTime);
  }
}
