/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.service.impl
 * ファイル名:Com0101d01Service.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/09/16
 * 
 *  <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/16 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 *  <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.common.service.impl;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.component.FormCom0101d01;
import jp.co.daitoku_sh.dfcm.common.dao.impl.Com0101d01Dao;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.db.model.MstGeneral;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSJigyo;
import jp.co.daitoku_sh.dfcm.common.db.model.MstUser;
import jp.co.daitoku_sh.dfcm.common.db.model.MstUserExample;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;

/**
 * サービスクラス
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class Com0101d01Service extends AbsService {
  
  /** ログ出力用 */
  private Dfcmlogger logger = new Dfcmlogger();
  
  @Autowired
  @Qualifier("com0101d01Dao")
  private Com0101d01Dao com0101d01Dao;

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
   * @param formCom0101d01 A model attribute
   * @param model A holder for model attributes
   * @return error message
   */
  public String initialProcessing(FormCom0101d01 formCom0101d01, Model model) {
    String errMsg = CommonConst.EMPTY;
    String messageCode = "COM006-E";
    // メッセージのParam
    ArrayList<String> paramMess = null;

    // [画面]担当者コードが未入力の場合、エラーとする
    if (formCom0101d01.getUserid().equalsIgnoreCase(CommonConst.EMPTY)) {
      paramMess = new ArrayList<String>();
      paramMess.add("担当者コード");
      errMsg = readPropertiesFileService.getMessage(messageCode, paramMess);
      return errMsg;
    }

    // [画面]パスワードが未入力の場合、エラーとする
    if (formCom0101d01.getPassword().equalsIgnoreCase(CommonConst.EMPTY)) {
      paramMess = new ArrayList<String>();
      paramMess.add("パスワード");
      errMsg = readPropertiesFileService.getMessage(messageCode, paramMess);
      formCom0101d01.setFocusPass(true);
      return errMsg;
    }
    return errMsg;
  }

  /**
   * 【マスタ取得】.
   * 
   * @param response Provide HTTP-specific functionality in sending a response
   * @param request receive data sent by Web client
   * @param strAplDate 業務日付
   * @param maxNumErrLogin ログインエラー最大回数
   * @param formCom0101d01 A model attribute
   * @param model A holder for model attributes
   * @param txManager  トランザクション Commit / Rollback 
   * @return error message
   * @throws ParseException Signals that an error has been reached unexpectedly while parsing
   * @throws NoSuchAlgorithmException exception
   */
  public String getDataFromMasterTable(FormCom0101d01 formCom0101d01,
      Model model, HttpServletResponse response, HttpServletRequest request,
      int maxNumErrLogin, String strAplDate, PlatformTransactionManager txManager) 
          throws ParseException, NoSuchAlgorithmException {
    DefaultTransactionDefinition def = null;
    TransactionStatus status = null;
    String errMsg = CommonConst.EMPTY;
    String messageCode = CommonConst.EMPTY;
    // メッセージのParam
    ArrayList<String> paramMess = null;
    Map<String, Object> parms = new HashMap<String, Object>();
    String userCode = formCom0101d01.getUserid();
    // 存在チェック
    parms.put("userCode", userCode);
    ArrayList<MstUser> mstUsers = null;
    try {
      // 担当者マスタから担当者情報を取得する
      mstUsers = com0101d01Dao.getCom0101d01Mapper()
          .getPersonalCharge(parms);
    } catch (MyBatisSystemException e) {
      
      logger.error(e.getMessage());
      throw e;
    }
    
    // 該当する担当者情報が存在しない場合、エラーとする
    if (mstUsers == null || mstUsers.size() == 0) {
      messageCode = "COM009-E";
      paramMess = new ArrayList<String>();
      paramMess.add("担当者マスタ");
      paramMess.add("担当者");
      errMsg = readPropertiesFileService.getMessage(messageCode, paramMess);
      return errMsg;
    }

    MstUser mstUser = mstUsers.get(0);
    // 事業所マスタデータ取得
    parms.clear();
    String jigyoshoCode = mstUser.getJigyoshoCode();
    parms.put("jigyoshoCode", jigyoshoCode);
    ArrayList<MstSJigyo> mstSJigyos = null;
    try {
      mstSJigyos = com0101d01Dao.getCom0101d01Mapper()
          .getOfficeInformation(parms);
    } catch (MyBatisSystemException e) {
      
      logger.error(e.getMessage());
      throw e;
    }
    // 該当する事業所情報が存在しない場合、エラーとする
    if (mstSJigyos == null || mstSJigyos.size() == 0) {
      messageCode = "COM009-E";
      paramMess = new ArrayList<String>();
      paramMess.add("事業所マスタ");
      paramMess.add("事業所");
      errMsg = readPropertiesFileService.getMessage(messageCode, paramMess);
      return errMsg;
    }

    // フラグチェック
    // （利用停止）の場合、エラーとする
    if (mstUser.getUserStatus().equalsIgnoreCase(CommonConst.SUSPENSION_STATUS)) {
      messageCode = "COM013-E";
      paramMess = new ArrayList<String>();
      paramMess.add("担当者コード");
      paramMess.add("利用停止中");
      errMsg = readPropertiesFileService.getMessage(messageCode, paramMess);
      return errMsg;
    }
    // （ロックアウト）の場合、エラーとする
    if (mstUser.getUserStatus().equalsIgnoreCase(CommonConst.LOCKOUT_STATUS)) {
      messageCode = "COM013-E";
      paramMess = new ArrayList<String>();
      paramMess.add("担当者コード");
      paramMess.add("ロックアウト中");
      errMsg = readPropertiesFileService.getMessage(messageCode, paramMess);
      return errMsg;
    }
    // 【パスワードチェック】
    String password = CommonConst.EMPTY;
      // [画面]パスワードをMD5形式で暗号化し、[変数]暗号化パスワードへセットする
    password = Util.createDigest(formCom0101d01.getPassword());
    Date date = new Date(); // 現在日時取得
    SimpleDateFormat sdf = new SimpleDateFormat(CommonConst.DB_DATE_FORMAT_YMD); // 日付フォーマットクラス
    String curDate = sdf.format(date); // システム日付を格納
    if (strAplDate == null) {
      strAplDate = curDate.replace("-", "");
    }
    // [変数]暗号化パスワード <> USR1.ログインパスワードの場合、エラーとする
    if (!password.equalsIgnoreCase(mstUser.getPassword())) {
      int loginErrCnt = mstUser.getLoginErrCnt() == null ? 0
          : mstUser.getLoginErrCnt() + 1;
      if (maxNumErrLogin > 0 && loginErrCnt < maxNumErrLogin) {
        parms.clear();
        parms.put("loginErrCnt", new Integer(loginErrCnt));
        parms.put("userCode", userCode);
        try {
          def = new DefaultTransactionDefinition();
          status = txManager.getTransaction(def);
          com0101d01Dao.getCom0101d01Mapper().updateLoginErrCnt(parms);
          txManager.commit(status);
        } catch (MyBatisSystemException e) {
          e.printStackTrace();
          txManager.rollback(status);
          throw e;
        }
      }
      
      // [変数]ログインエラー最大回数 > 0 かつ、担当者マスタ.ログインエラー回数 + 1 =
      // [変数]ログインエラー最大回数の場合、担当者マスタを更新する
      if (maxNumErrLogin > 0 && loginErrCnt == maxNumErrLogin) {
        Date lockoutDatetime = null;
        lockoutDatetime = DateUtil.toDate(curDate, CommonConst.DB_DATE_FORMAT_YMD);
        MstUserExample exam = new MstUserExample();
        exam.createCriteria().andUserCodeEqualTo(userCode).andStsCodeEqualTo(CommonConst.REGISTRATION_STSCODE);
        MstUser user = new MstUser();
        user.setUserStatus(CommonConst.LOCKOUT_STATUS);
        user.setLockoutDatetime(lockoutDatetime);
        user.setLoginErrCnt((short) 0);
        try {
          def = new DefaultTransactionDefinition();
          status = txManager.getTransaction(def);
          com0101d01Dao.getMstUserMapper().updateByExampleSelective(user,exam);
          txManager.commit(status);
        } catch (MyBatisSystemException e) {
          
          logger.error(e.getMessage());
          txManager.rollback(status);
          throw e;
        }
      }
      Calendar cal = Calendar.getInstance();
      String now = DateUtil.setFormat(cal.getTime(), CommonConst.LOG_DATE_FOMAT_YMDHMS);
      // ログイン認証ログ出力
      // ログイン認証ログファイルに認証失敗ログを出力する（証跡情報：IPアドレス）
      logger.loginLog(readPropertiesFileService, "ログインに失敗しました（" + now
          + "　担当者コード" + formCom0101d01.getUserid()
          + "　IPアドレス：" + request.getRemoteAddr() + "）");
      // エラーメッセージを画面に表示する
      messageCode = "COM001-E";
      paramMess = new ArrayList<String>();
      paramMess.add("パスワード");
      errMsg = readPropertiesFileService.getMessage(messageCode, paramMess);
      formCom0101d01.setFocusPass(true);
      return errMsg;

      // [変数]暗号化パスワード = USR1.ログインパスワードの場合、ログイン成功とする
    } else {
      // 権限チェック
      // ユーザ種別を取得する
      String authDivision = CommonConst.SYS_ADMIN;
      String authCode = mstUser.getAuthCode();
      String userType = checkAuthorityCode(authCode, authDivision);
      String sysAdminFlg = CommonConst.EMPTY;
      // [変数]ユーザ種別 = '0'（一般ユーザ）の場合、
      if (userType.equalsIgnoreCase(CommonConst.GEN_USERS)) {
        sysAdminFlg = CommonConst.GEN_USERS; // （無効）
        // [変数]ユーザ種別 = '1'（システム管理者）の場合、
      } else if (userType.equalsIgnoreCase(CommonConst.SYS_ADMIN)) {
        sysAdminFlg = CommonConst.SYS_ADMIN; // （有効）
      }
      // 【終了処理・例外処理】
      // 担当者マスタデータ更新
      if (maxNumErrLogin > 0 && mstUser.getLoginErrCnt() > 0) {
        SimpleDateFormat formatter = new SimpleDateFormat(CommonConst.DB_DATE_FORMAT_YMD);
        Date lastLoginDatetime = null;
        try {
          lastLoginDatetime = formatter.parse(curDate);
        } catch (ParseException e) {
          
          logger.error(e.getMessage());
          throw e;
        }
        MstUserExample exam = new MstUserExample();
        exam.createCriteria().andUserCodeEqualTo(userCode).andStsCodeEqualTo(CommonConst.REGISTRATION_STSCODE);
        
        MstUser user = new MstUser();
        user.setLastLoginDatetime(lastLoginDatetime);
        user.setLoginErrCnt((short) 0);
        try {
          def = new DefaultTransactionDefinition();
          status = txManager.getTransaction(def);
          com0101d01Dao.getMstUserMapper().updateByExampleSelective(user,exam);
          txManager.commit(status);
        } catch (MyBatisSystemException e) {
          
          logger.error(e.getMessage());
          txManager.rollback(status);
          throw e;
        }
      }

      // Cookie管理
      Cookie cookie = null;
      // 担当者コード保存
      // [画面]担当者コードを保存する = Trueの場合、[画面]担当者コードをcookieに設定する
      if (formCom0101d01.isChkIdRemainder()) {
        cookie = new Cookie(CommonConst.COOKIE_KEY, formCom0101d01.getUserid());

        // 担当者コード削除
        // [画面]担当者コードを保存する = Falseの場合、cookieから[画面]担当者コードを削除する
      } else {
        cookie = new Cookie(CommonConst.COOKIE_KEY, CommonConst.EMPTY);
      }
      cookie.setMaxAge(CommonConst.COOKIE_MAX_AGE);
      response.addCookie(cookie);
      Calendar cal = Calendar.getInstance();
      SimpleDateFormat dateFormat  = new SimpleDateFormat(CommonConst.LOG_DATE_FOMAT_YMDHMS);
      String now = dateFormat.format(cal.getTime());
      logger.loginLog(readPropertiesFileService, "ログインに成功しました（" + now
          + "　担当者コード："
          + formCom0101d01.getUserid() + "　IPアドレス：" + request.getRemoteAddr()
          + "）");
      // セッション登録
      // 出力項目の内容をセッションにセットする
      setContentsToSession(request, mstUser, sysAdminFlg, strAplDate, mstSJigyos
          .get(0).getJgymei(), formCom0101d01.getUserid());
    }
    return errMsg;
  }

  /**
   * 【関数】権限コードチェック.
   * 
   * @param authCode 権限コード
   * @param authDivision 権限区分
   * @return [変数]ユーザ種別
   */
  private String checkAuthorityCode(String authCode, String authDivision) {
    // 連絡事項取得
    Map<String, Object> parms = new HashMap<String, Object>();
    parms.put("Gl_Code", authDivision);
    ArrayList<MstGeneral> mstGenerals = null;
    try {
      mstGenerals = com0101d01Dao.getCom0101d01Mapper()
          .getGeneral(parms);
    } catch (MyBatisSystemException e) {
      
      logger.error(e.getMessage());
      throw e;
    }
    // 取得データ比較
    // [入力]権限コードとGEN.権限コード1～10を比較し、合致するデータが存在した場合、'1'（該当する）で復帰する
    MstGeneral mstGeneral = mstGenerals.get(0);
    if (authCode.equalsIgnoreCase(mstGeneral.getTarget1())
        || authCode.equalsIgnoreCase(mstGeneral.getTarget2())
        || authCode.equalsIgnoreCase(mstGeneral.getTarget3())
        || authCode.equalsIgnoreCase(mstGeneral.getTarget4())
        || authCode.equalsIgnoreCase(mstGeneral.getTarget5())
        || authCode.equalsIgnoreCase(mstGeneral.getTarget6())
        || authCode.equalsIgnoreCase(mstGeneral.getTarget7())
        || authCode.equalsIgnoreCase(mstGeneral.getTarget8())
        || authCode.equalsIgnoreCase(mstGeneral.getTarget9())
        || authCode.equalsIgnoreCase(mstGeneral.getTarget10())) {
      return CommonConst.AUTH_CODE_APPLICABLE; // （該当する）
    }
    return CommonConst.AUTH_CODE_NOT_APPLICABLE; // 該当しない
  }

  /**
   * Set the contents of the output item to the session.
   * 
   * @param request receive data sent by Web client
   * @param currDate 業務日付
   * @param sysAdminFlg システム管理者フラグ
   * @param mstUser 担当者マスタ
   * @param jgymei 事業所名
   * @param userCode [画面]担当者コード
   */
  public void setContentsToSession(HttpServletRequest request, MstUser mstUser,
      String sysAdminFlg, String currDate, String jgymei, String userCode) {
    HttpSession session = request.getSession(false);
    if (session != null) {
      String maxInactiveInterval = readPropertiesFileService.getSetting("MAX_INACTIVE_INTERVAL");
      session.setMaxInactiveInterval(Integer.parseInt(maxInactiveInterval));
      session.setAttribute("UserCode", userCode);
      session.setAttribute("UserNm", mstUser.getUserNm());
      session.setAttribute("UserNmKana", mstUser.getUserNmKana());
      session.setAttribute("AuthCode", mstUser.getAuthCode());
      session.setAttribute("SysAdminFlg", sysAdminFlg);
      session.setAttribute("JigyoshoCode", mstUser.getJigyoshoCode());
      session.setAttribute("jgymei", jgymei);
      session.setAttribute("currDate", currDate);
    }
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
}
