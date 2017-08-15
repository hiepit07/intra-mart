/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl
 * ファイル名:Mst0101d02Service.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/08/24
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/08/24 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;

import jp.co.daitoku_sh.dfcm.common.component.MstGeneralData;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.db.model.MstUser;
import jp.co.daitoku_sh.dfcm.common.db.model.MstUserExample;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.InputCheckCom;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ObjCombobox;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0101d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstSJigyoInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.SearchCondMst0101d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl.Mst0101d02Dao;

/**
 * サービスクラス
 * 
 * @author アクトブレーンベトナム コイー
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class Mst0101d02Service extends AbsService {

  @Autowired
  @Qualifier("mst0101d02Dao")
  private Mst0101d02Dao mst0101d02Dao;

  @Autowired
  @Qualifier("commonDao")
  private CommonDao commonDao;

  @Autowired
  private ApplicationContext appContext;

  // メッセージPropertiesファイルを読む
  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  /** ログ出力用. */
  private Dfcmlogger logger = new Dfcmlogger();

  /** トランザクション Commit / Rollback. */
  PlatformTransactionManager txManager;

  public Mst0101d02Dao getMst0101d02Dao() {
    return mst0101d02Dao;
  }

  public void setMst0101d02Dao(Mst0101d02Dao mst0101d02Dao) {
    this.mst0101d02Dao = mst0101d02Dao;
  }
  
  /**
   * Defaultメッセージの取得.
   * @param model:モデル
   */
  public void getDefaultMess(Model model) {
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();
    
    DefaultMessages defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-I01");
    defaultMsg.setMessageTitle("確認メッセージ");
    paramMess = new ArrayList<String>();
    paramMess.add("登録");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-I", paramMess));
    ArrayList<DefaultMessages> defaultMsgLst = new ArrayList<DefaultMessages>();
    defaultMsgLst.add(defaultMsg);

    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-I02");
    defaultMsg.setMessageTitle("確認メッセージ");
    paramMess = new ArrayList<String>();
    paramMess.add("画面表示時の状態に初期化");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-I", paramMess));
    defaultMsgLst.add(defaultMsg);

    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-E", null));
    defaultMsgLst.add(defaultMsg);

    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM016-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM016-E", null));
    defaultMsgLst.add(defaultMsg);

    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM006-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM006-E", null));
    defaultMsgLst.add(defaultMsg);

    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM034-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM034-E", null));
    defaultMsgLst.add(defaultMsg);

    model.addAttribute("defaultMessages", defaultMsgLst);
  }
  
  /**
   * 一覧画面から条件値を設定する。（hiddenで保存した）.
   * @param formMst0101d02:フォーム
   * @param searchCondMst0101d01:覧画面から条件値
   * @param viewMode:モード値
   */
  public void init(FormMst0101d02 formMst0101d02,
      SearchCondMst0101d01 searchCondMst0101d01, String viewMode) {
    // [入力]検索条件保持クラスを退避する
    formMst0101d02.setForm1JigyoshoCode(searchCondMst0101d01.getJigyoshoCode());
    formMst0101d02.setForm1UserCode(searchCondMst0101d01.getUserCode());
    formMst0101d02.setForm1UserNm(searchCondMst0101d01.getUserNm());
    formMst0101d02.setForm1AuthCode(searchCondMst0101d01.getAuthCode());
    formMst0101d02.setForm1CancelData(searchCondMst0101d01.getCancelData());
    formMst0101d02.setForm1UserStatus(searchCondMst0101d01.getUserStatus());
    formMst0101d02.setMode(viewMode);
  }

  /**
   * 業務日付を取得する.
   * @return 業務日付
   */
  public String getBusinessDate() {
    String strBussiDate = null;
    // 共通部品初期化
    try {
      CommonGetSystemCom systemCom = new CommonGetSystemCom(commonDao, null,
          null, readPropertiesFileService);
      // 共通部品を呼ぶ
      strBussiDate = systemCom.getAplDate();
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
    return strBussiDate;
  }
  
  /**
   * 担当者コードと担当者情報を取得する.
   * @param strUserCode:担当者コード
   * @return　MstUser
   */
  public MstUser getUserInfo(String strUserCode) {
    MstUser mstUser = null;
    try {
      mstUser = mst0101d02Dao.getMstUserMapper().selectByPrimaryKey(
          strUserCode);
      return mstUser;
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
  }
  
  /**
   * 利用権限のデータを取得して、ドロップダウンリストに表示する.
   * @param model:モデル
   * @return Boolean true: 普通 false: エラーが発生する。
   */
  public boolean setRiyoKengen_Dll(Model model) {
    ArrayList<ObjCombobox> lstMstRiyoKengenInfoReturn = new ArrayList<ObjCombobox>();
    try {
      // 共通部品初期化
      CommonGetSystemCom systemCom = new CommonGetSystemCom(commonDao, null,
          null, readPropertiesFileService);
      // 得意先種別名称を取得する
      List<MstGeneralData> generalDataAuthType = systemCom.getMstGeneralConf(
          "Auth_Code", null);
      if (generalDataAuthType != null && generalDataAuthType.size() > 0) {
        ObjCombobox firstObject = new ObjCombobox();
        firstObject.setCode("");
        firstObject.setName("");
        lstMstRiyoKengenInfoReturn.add(firstObject);
        for (MstGeneralData mstGeneralData : generalDataAuthType) {
          ObjCombobox tempObj = new ObjCombobox();
          tempObj.setCode(mstGeneralData.getGlCode());
          tempObj.setName(mstGeneralData.getGlCode() + " : " + mstGeneralData
              .getTarget1());
          lstMstRiyoKengenInfoReturn.add(tempObj);
        }
        model.addAttribute("RiyoKengenClassList", lstMstRiyoKengenInfoReturn);
        return true;
      } else {
        return false;
      }
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
  }

  /**
   * 所属事業所のデータを取得して、ドロップダウンリストに表示する.
   * @param formMst0101d02:フォーム
   * @param model:モデル
   * @return Boolean true: 普通 false: エラーが発生する。
   */
  public boolean setJigyo_Dll(FormMst0101d02 formMst0101d02, Model model) {
    try {
      ArrayList<ObjCombobox> lstMstSJigyoInfoReturn = new ArrayList<ObjCombobox>();      
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("businessDate", formMst0101d02.getBusinessDate().toString());
      params.put("strSystemAdminFlg", formMst0101d02.getSysAdminFlag());
      params.put("loginJigyoshoCode", formMst0101d02.getLoginJigyouShoCode());
      ArrayList<MstSJigyoInfo> lstMstSJigyoInfo = null;
      lstMstSJigyoInfo = mst0101d02Dao.getMst0101d02Mapper().getSJigyoInfo(
          params);
      if (lstMstSJigyoInfo != null && lstMstSJigyoInfo.size() > 0) {
        ObjCombobox firstObject = new ObjCombobox();
        firstObject.setCode("");
        firstObject.setName("");
        lstMstSJigyoInfoReturn.add(firstObject);
        for (MstSJigyoInfo mstSJigyoInfo : lstMstSJigyoInfo) {
          ObjCombobox tempObj = new ObjCombobox();
          tempObj.setCode(mstSJigyoInfo.getJgycd());
          tempObj.setName(mstSJigyoInfo.getJgycd() + " : " + mstSJigyoInfo
              .getJgymei());
          lstMstSJigyoInfoReturn.add(tempObj);
        }
        model.addAttribute("ShozokuClassList", lstMstSJigyoInfoReturn);
        return true;
      } else {
        return false;
      }
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
  }

  /**
   * 担当者コードの存在をチェックする.
   * @param strUserCode:担当者コード
   * @return true 存在 false 存在なし
   */
  public boolean checkUserInfoRegistered(String strUserCode) {
    try {
      //担当者情報の保存
      List<MstUser> lstMstUser;
      MstUserExample mstUserExample = new MstUserExample();
      mstUserExample.createCriteria().andUserCodeEqualTo(strUserCode)
          .andUserStatusEqualTo(MstConst.SHINKI_MODE);
      lstMstUser = mst0101d02Dao.getMstUserMapper().selectByExample(
          mstUserExample);
      if (lstMstUser != null && lstMstUser.size() > 0) {
        return true;
      } else {
        return false;
      }
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
  }

  /**
   * 初期パスワードを取得する.
   * @return String 初期パスワード
   */
  public String getInitPassword() {
    CommonGetSystemCom systemCom = new CommonGetSystemCom(commonDao, null,
        null, readPropertiesFileService);
    try {
      List<MstGeneralData> generalInitPassword = systemCom.getMstGeneralConf(
          "Init_Password", null);
      if (generalInitPassword != null && generalInitPassword.size() > 0) {
        String strInitPassword = "";
        for (MstGeneralData mstGeneralData : generalInitPassword) {
          strInitPassword = mstGeneralData.getTarget1();
          break;
        }
        return strInitPassword;
      } else {
        return "";
      }
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
  }  
  
  /**
   * 照会以外画面は値を設定する.
   * @param model:モデル
   * @param viewMode:モード
   * @param formMst0101d02:フォーム
   * @param mstUser:担当者の情報
   */
  public void setView(Model model, String viewMode,
      FormMst0101d02 formMst0101d02, MstUser mstUser) {

    // 値を設定
    if (!viewMode.equalsIgnoreCase(MstConst.SHINKI_MODE)) {
      formMst0101d02.setTxtUserCode(mstUser.getUserCode());
      formMst0101d02.setTxtUserName(mstUser.getUserNm());
      formMst0101d02.setTxtUserNameKata(mstUser.getUserNmKana());
      formMst0101d02.setDdlShozoku(mstUser.getJigyoshoCode());
      if (!mstUser.getJigyoshoCode().trim().equalsIgnoreCase("")) {
        formMst0101d02.setShozokuSelect(mstUser.getJigyoshoCode());
      } else {
        formMst0101d02.setShozokuSelect("");
      }
      formMst0101d02.setDdlRiyoKengen(mstUser.getAuthCode());
      if (!mstUser.getAuthCode().trim().equalsIgnoreCase("")) {
        formMst0101d02.setRiyoKengenSelect(mstUser.getAuthCode());
      } else {
        formMst0101d02.setRiyoKengenSelect("");
      }
      formMst0101d02.setTxtDivisionName(mstUser.getUserPost());
      formMst0101d02.setTxtAddress(mstUser.getMailAdr());
      String[] arrStrTelFax;
      arrStrTelFax = setFaxTel(mstUser.getTelNo());
      if (arrStrTelFax != null) {
        formMst0101d02.setTxtTel1(arrStrTelFax[0]);
        formMst0101d02.setTxtTel2(arrStrTelFax[1]);
        formMst0101d02.setTxtTel3(arrStrTelFax[2]);
      }

      arrStrTelFax = setFaxTel(mstUser.getFaxNo());
      if (arrStrTelFax != null) {
        formMst0101d02.setTxtFax1(arrStrTelFax[0]);
        formMst0101d02.setTxtFax2(arrStrTelFax[1]);
        formMst0101d02.setTxtFax3(arrStrTelFax[2]);
      }

      if (viewMode.equalsIgnoreCase("4")) {
        formMst0101d02.setTxtStatusCode("9");
      } else {
        formMst0101d02.setTxtStatusCode(mstUser.getStsCode());
      }

      if (mstUser.getUserStatus().equalsIgnoreCase("2")) {
        formMst0101d02.setChkSystemUse(true);
      } else {
        formMst0101d02.setChkSystemUse(false);
      }

      if (mstUser.getUserStatus().equalsIgnoreCase("3")) {
        formMst0101d02.setChkLogout(true);
      } else {
        formMst0101d02.setChkLogout(false);
      }
    }
  }
  
  /**
   * 照会以外画面は値を設定する.
   * @param viewMode:モード
   * @param formMst0101d02:フォーム
   * @return　boolean true 普通 false エラー
   */
  public boolean setViewClear(String viewMode, FormMst0101d02 formMst0101d02) {
    if (viewMode.equalsIgnoreCase(MstConst.SHINKI_MODE)) {
      formMst0101d02.setTxtUserCode("");
      formMst0101d02.setTxtUserName("");
      formMst0101d02.setTxtUserNameKata("");
      formMst0101d02.setDdlRiyoKengen("");
      formMst0101d02.setDdlShozoku("");
      formMst0101d02.setTxtDivisionName("");
      formMst0101d02.setTxtAddress("");
      formMst0101d02.setTxtTel1("");
      formMst0101d02.setTxtTel2("");
      formMst0101d02.setTxtTel3("");
      formMst0101d02.setTxtFax1("");
      formMst0101d02.setTxtFax2("");
      formMst0101d02.setTxtFax3("");
      formMst0101d02.setTxtStatusCode("");
      formMst0101d02.setChkSystemUse(false);
      formMst0101d02.setChkPassword(false);
      formMst0101d02.setChkLogout(false);
    } else if (viewMode.equalsIgnoreCase(MstConst.TEISEI_MODE)) {

      MstUser mstUser = new MstUser();
      // [変数]担当者情報格納クラス ＝ [関数]担当者マスタデータ取得（[入力]担当者コード）
      mstUser = this.getUserInfo(formMst0101d02.getTxtUserCode());
      if (mstUser == null) {
        return false;
      }

      formMst0101d02.setTxtUserCode(mstUser.getUserCode());
      formMst0101d02.setTxtUserName(mstUser.getUserNm());
      formMst0101d02.setTxtUserNameKata(mstUser.getUserNmKana());
      formMst0101d02.setDdlShozoku(mstUser.getJigyoshoCode());
      formMst0101d02.setDdlRiyoKengen(mstUser.getAuthCode());
      formMst0101d02.setTxtDivisionName(mstUser.getUserPost());
      formMst0101d02.setTxtAddress(mstUser.getMailAdr());
      String[] arrStrTelFax;

      arrStrTelFax = setFaxTel(mstUser.getTelNo());
      if (arrStrTelFax != null) {
        formMst0101d02.setTxtTel1(arrStrTelFax[0]);
        formMst0101d02.setTxtTel2(arrStrTelFax[1]);
        formMst0101d02.setTxtTel3(arrStrTelFax[2]);
      }

      arrStrTelFax = setFaxTel(mstUser.getFaxNo());
      if (arrStrTelFax != null) {
        formMst0101d02.setTxtFax1(arrStrTelFax[0]);
        formMst0101d02.setTxtFax2(arrStrTelFax[1]);
        formMst0101d02.setTxtFax3(arrStrTelFax[2]);
      }

      formMst0101d02.setTxtStatusCode(mstUser.getStsCode());
      
      if (mstUser.getUserStatus().equalsIgnoreCase(
          MstConst.USER_STATUS_TEISHI)) {
        formMst0101d02.setChkSystemUse(true);
      } else {
        formMst0101d02.setChkSystemUse(false);
      }

      formMst0101d02.setChkPassword(false);

      if (mstUser.getUserStatus().equalsIgnoreCase(
          MstConst.USER_STATUS_LOCKOUT)) {
        formMst0101d02.setChkLogout(true);
      } else {
        formMst0101d02.setChkLogout(false);
      }
    }
    return true;
  }

  /**
   * 電話ＮｏとFaxＮｏはSplitする.
   * @param strInput:変更する文字
   * @return String[] ３部分
   */
  public String[] setFaxTel(String strInput) {
    String[] arrStrOutput = null;
    if (strInput != null && !strInput.equalsIgnoreCase("")) {
      arrStrOutput = strInput.split("-", 3);
    }

    return arrStrOutput;
  }

  /**
   * データを組み込む.
   * @param formMst0101d02:フォーム
   * @param strPassword:パスワード
   * @param strLoginUserCode:ログインユーザコード
   * @throws Exception　エラー画面
   */
  public void insertData(FormMst0101d02 formMst0101d02, String strPassword,
      String strLoginUserCode) throws Exception {
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    txManager = (PlatformTransactionManager) appContext.getBean("txManager");
    TransactionStatus status = null;

    MstUser mstUser = new MstUser();

    mstUser.setUserCode(formMst0101d02.getTxtUserCode());
    mstUser.setUserNm(formMst0101d02.getTxtUserName());
    mstUser.setUserNmKana(formMst0101d02.getTxtUserNameKata());
    mstUser.setJigyoshoCode(formMst0101d02.getDdlShozoku());
    mstUser.setAuthCode(formMst0101d02.getDdlRiyoKengen());
    mstUser.setUserPost(formMst0101d02.getTxtDivisionName());
    mstUser.setMailAdr(formMst0101d02.getTxtAddress());
    String strTelFax = formMst0101d02.getTxtTel1() + "-" + formMst0101d02
        .getTxtTel2() + "-" + formMst0101d02.getTxtTel3();
    if (strTelFax.equalsIgnoreCase("--")) {
      mstUser.setTelNo("");
    } else {
      mstUser.setTelNo(strTelFax);
    }
    strTelFax = formMst0101d02.getTxtFax1() + "-" + formMst0101d02.getTxtFax2()
        + "-" + formMst0101d02.getTxtFax3();
    if (strTelFax.equalsIgnoreCase("--")) {
      mstUser.setFaxNo("");
    } else {
      mstUser.setFaxNo(strTelFax);
    }
    mstUser.setPassword(strPassword);
    mstUser.setUserStatus("1");
    mstUser.setLoginErrCnt((short) 0);
    mstUser.setStsCode(formMst0101d02.getTxtStatusCode());

    // 共通項目の設定
    mstUser = this.setCommonData(mstUser, strLoginUserCode, "MST01-01D02",
        true);
    try {
      status = txManager.getTransaction(def);
      mst0101d02Dao.getMstUserMapper().insert(mstUser);
      txManager.commit(status);
    } catch (Exception ex) {
      txManager.rollback(status);
      logger.error(ex.getMessage());
      throw ex;
    }

  }

  /**
   * データを更新する.
   * @param formMst0101d02:フォーム
   * @param strPassword:パスワード
   * @param strLoginUserCode:ログインユーザコード
   * @throws Exception　エラー画面
   */
  public void updateData(FormMst0101d02 formMst0101d02, String strPassword,
      String strLoginUserCode) throws Exception {

    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    txManager = (PlatformTransactionManager) appContext.getBean("txManager");
    TransactionStatus status = null;

    MstUser mstUser = new MstUser();

    mstUser.setUserCode(formMst0101d02.getTxtUserCode());
    mstUser.setUserNm(formMst0101d02.getTxtUserName());
    mstUser.setUserNmKana(formMst0101d02.getTxtUserNameKata());
    mstUser.setJigyoshoCode(formMst0101d02.getDdlShozoku());
    mstUser.setAuthCode(formMst0101d02.getDdlRiyoKengen());
    mstUser.setUserPost(formMst0101d02.getTxtDivisionName());
    mstUser.setMailAdr(formMst0101d02.getTxtAddress());

    String strTelFax = formMst0101d02.getTxtTel1() + "-" + formMst0101d02
        .getTxtTel2() + "-" + formMst0101d02.getTxtTel3();
    if (strTelFax.equalsIgnoreCase("--")) {
      mstUser.setTelNo("");
    } else {
      mstUser.setTelNo(strTelFax);
    }

    strTelFax = formMst0101d02.getTxtFax1() + "-" + formMst0101d02.getTxtFax2()
        + "-" + formMst0101d02.getTxtFax3();
    if (strTelFax.equalsIgnoreCase("--")) {
      mstUser.setFaxNo("");
    } else {
      mstUser.setFaxNo(strTelFax);
    }

    if (formMst0101d02.isChkPassword()) {
      mstUser.setPassword(strPassword);
    }

    /*
     * ①[画面]システム利用 ＝ チェック有の場合、 ユーザー状態 ＝ '2'
     * ②[画面]ロックアウト ＝ チェック有の場合、 ユーザー状態 ＝ '3'
     * ③[画面]システム利用 ＝ チェック無 AND [画面]ロックアウト ＝ チェック無の場合、 ユーザー状態 ＝ '1'
     */
    if (formMst0101d02.isChkSystemUse()) {
      mstUser.setUserStatus(MstConst.USER_STATUS_TEISHI);
    }
    if (formMst0101d02.isChkLogout()) {
      mstUser.setUserStatus(MstConst.USER_STATUS_LOCKOUT);
    }
    if (!formMst0101d02.isChkSystemUse() && !formMst0101d02.isChkLogout()) {
      mstUser.setUserStatus(MstConst.USER_STATUS_KANO);
    }

    mstUser.setStsCode(formMst0101d02.getTxtStatusCode());

    // 共通項目の設定
    mstUser = this.setCommonData(mstUser, strLoginUserCode, "MST01-01D02",
        false);

    try {
      status = txManager.getTransaction(def);
      mst0101d02Dao.getMstUserMapper().updateByPrimaryKeySelective(mstUser);
      txManager.commit(status);
    } catch (Exception ex) {
      txManager.rollback(status);
      logger.error(ex.getMessage());
      throw ex;
    }
  }

  /**
   * データを削除する.
   * @param formMst0101d02:フォーム
   * @param strLoginUserCode:ログインユーザコード
   * @throws Exception　エラー画面
   */
  public void deleteData(FormMst0101d02 formMst0101d02, String strLoginUserCode)
      throws Exception {
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    txManager = (PlatformTransactionManager) appContext.getBean("txManager");
    TransactionStatus status = null;

    MstUser mstUser = new MstUser();

    mstUser.setUserCode(formMst0101d02.getTxtUserCode());
    mstUser.setStsCode("9");
    // 共通項目の設定
    mstUser = this.setCommonData(mstUser, strLoginUserCode, "MST01-01D02",
        false);
    try {
      status = txManager.getTransaction(def);
      mst0101d02Dao.getMstUserMapper().updateByPrimaryKeySelective(mstUser);
      txManager.commit(status);
    } catch (Exception ex) {
      txManager.rollback(status);
      logger.error(ex.getMessage());
      throw ex;
    }
  }

  /**
   * 共通項目の設定.
   * @param dataUser:担当者データ
   * @param strUserId:ユーザID
   * @param strProgId:プログラムID
   * @param isNew:新規／更新
   * @return 担当者データ
   */
  public MstUser setCommonData(MstUser dataUser, String strUserId, String strProgId,
      boolean isNew) {

    String currentDate = DateUtil.getSysDate();
    String currentTime = DateUtil.getSysTime();

    if (isNew) {
      dataUser.setInsUserid(strUserId);
      dataUser.setInsPgid(strProgId);
      dataUser.setInsYmd(currentDate);
      dataUser.setInsTime(currentTime);
    }

    dataUser.setUpdUserid(strUserId);
    dataUser.setUpdPgid(strProgId);
    dataUser.setUpdYmd(currentDate);
    dataUser.setUpdTime(currentTime);

    return dataUser;
  }

  /**
   * 入力チェック処理.
   * @param val:チェックデータ
   * @param emptyFlg:必須チェックフラグ
   * @param type:型
   * @param len:桁数
   * @return null: エラー 
   */
  public String checkItem(String val, boolean emptyFlg, int type, int len) {
    String error = null;
    if (emptyFlg) {
      error = InputCheckCom.chkEmpty(val);
      if (error != null) {
        return error;
      }
    }

    error = InputCheckCom.chkType(val, type);
    if (error != null) {
      return error;
    }
    error = InputCheckCom.chkLength(val, len);
    if (error != null) {
      return error;
    }

    return error;
  }

  /**
   * 全項目入力チェック処理.
   * @param formMst0101d02:フォーム
   * @param model:モデル
   * @return エラー true 普通:false
   */
  public boolean checkInput(FormMst0101d02 formMst0101d02, Model model) {

    String strError;
    String strErrorId = "";

    ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    ErrorMessages errMess = new ErrorMessages();
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();

    // 担当者コード
    strError = checkItem(formMst0101d02.getTxtUserCode(), true,
        InputCheckCom.TYPE_NUM, 8);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("担当者コード");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtUserCode" + MstConst.DELIMITER_ERROR;
    }

    // 担当者氏名
    strError = checkItem(formMst0101d02.getTxtUserName(), true,
        InputCheckCom.TYPE_EM, 20);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("担当者氏名");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtUserName" + MstConst.DELIMITER_ERROR;
    }

    // 担当者氏名カナ
    strError = checkItem(formMst0101d02.getTxtUserNameKata(), false,
        InputCheckCom.TYPE_EM, 30);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("担当者氏名カナ");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtUserNameKata" + MstConst.DELIMITER_ERROR;
    }

    // 所属事業所
    if (formMst0101d02.getShozokuSelect().equalsIgnoreCase("")) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("所属事業所");
      errMess.setMessageContent(readPropertiesFileService.getMessage("COM006-E",
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "shozokuSelect" + MstConst.DELIMITER_ERROR;
    }

    // 利用権限
    if (formMst0101d02.getRiyoKengenSelect().equalsIgnoreCase("")) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("利用権限");
      errMess.setMessageContent(readPropertiesFileService.getMessage("COM006-E",
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "riyoKengenSelect" + MstConst.DELIMITER_ERROR;
    }

    // 部署名
    strError = checkItem(formMst0101d02.getTxtDivisionName(), false,
        InputCheckCom.TYPE_EM, 25);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("部署名");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtDivisionName" + MstConst.DELIMITER_ERROR;
    }

    // メールアドレス
    strError = checkItem(formMst0101d02.getTxtAddress(), false,
        InputCheckCom.TYPE_NUM_ALPHA_SIGN, 25);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("メールアドレス");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtAddress" + MstConst.DELIMITER_ERROR;
    }

    // 電話番号
    strError = checkItem(formMst0101d02.getTxtTelResult(), false,
        InputCheckCom.TYPE_NUM, 18);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("電話番号");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtTelResult" + MstConst.DELIMITER_ERROR;
    }

    // FAX番号
    strError = checkItem(formMst0101d02.getTxtFaxResult(), false,
        InputCheckCom.TYPE_NUM, 18);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("FAX番号");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtFaxResult" + MstConst.DELIMITER_ERROR;
    }

    // 状況コード
    String strStsCode = formMst0101d02.getTxtStatusCode();
    strError = checkItem(strStsCode, true, InputCheckCom.TYPE_NUM, 1);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("状況コード");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtStatusCode" + MstConst.DELIMITER_ERROR;
    } else {
      if (!strStsCode.equalsIgnoreCase(MstConst.TOROKU) && (!strStsCode
          .equalsIgnoreCase(MstConst.TORIKESHI))) {
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("状況コードは１または９で入力してください");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "MST013-E", paramMess));
        lstErrMess.add(errMess);
        strErrorId += "txtStatusCode" + MstConst.DELIMITER_ERROR;
      }
    }
    
    
    if (strErrorId.equalsIgnoreCase("")) {
      return false;
    } else {
      model.addAttribute("errorMessages", lstErrMess);
      model.addAttribute("lstErrorID", strErrorId);
      return true;
    }
  }

  /**
   * 排他チェック.
   * @param strUserCode:担当者コード
   * @param strHaitaDate:排他日付
   * @param strHaitaTime:排他時間
   * @return boolean true: エラー false: 普通
   */
  public boolean checkHaita(String strUserCode, String strHaitaDate,
      String strHaitaTime) {

    // User情報取得
    MstUser mstUser = new MstUser();
    mstUser = this.getUserInfo(strUserCode);

    // 共通項目.修正年月日 ＋ 共通項目．修正時刻 > 表示時システム日付 の場合
    int intDateRegDb = Integer.parseInt(mstUser.getUpdYmd());
    int intDateHidden = Integer.parseInt(strHaitaDate);

    if (intDateRegDb > intDateHidden) {
      return true;
    } else if (intDateRegDb == intDateHidden) {
      intDateRegDb = Integer.parseInt(mstUser.getUpdTime());
      intDateHidden = Integer.parseInt(strHaitaTime);
      if (intDateRegDb > intDateHidden) {
        return true;
      }
    }
    return false;
  }
}
