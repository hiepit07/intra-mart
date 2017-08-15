/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl
 * ファイル名:Mst0102d02Service.java
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

package jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.component.CustomerData;
import jp.co.daitoku_sh.dfcm.common.component.MstGeneralData;
import jp.co.daitoku_sh.dfcm.common.component.UserData;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCustjgyo;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCustjgyoKey;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCustomer;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCustomerExample;
import jp.co.daitoku_sh.dfcm.common.db.model.MstDatIdx;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSJigyo;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSNohin;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSubjectCd;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSubjectCdExample;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetData;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.InputCheckCom;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ObjCombobox;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0102d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0102d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.Mst0102d02CustomerData;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.Mst0102d02CustomerJigyo;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.Mst0102d02ListForm;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstCourseInformation;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstToriatsukaiJigyouSho;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl.Mst0102d02Dao;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * サービスクラス
 * 
 * @author アクトブレーンベトナム コアー
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class Mst0102d02Service extends AbsService {

  @Autowired
  @Qualifier("mst0102d02Dao")
  private Mst0102d02Dao mst0102d02Dao;

  @Autowired
  @Qualifier("commonDao")
  private CommonDao commonDao;

  // メッセージPropertiesファイルを読む
  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  @Autowired
  private ApplicationContext appContext;

  /** トランザクション Commit / Rollback. */
  PlatformTransactionManager txManager;

  /** ログ出力用. */
  private Dfcmlogger logger = new Dfcmlogger();

  // DAOの定義
  public Mst0102d02Dao getMst0102d02Dao() {
    return mst0102d02Dao;
  }

  public void setMst0102d02Dao(Mst0102d02Dao mst0102d02Dao) {
    this.mst0102d02Dao = mst0102d02Dao;
  }

  public CommonDao getCommonDao() {
    return commonDao;
  }

  public void setCommonDao(CommonDao commonDao) {
    this.commonDao = commonDao;
  }

  /**
   * Defaultメッセージの取得.
   * 
   * @param model ： フォームのModel
   */
  public void getDefaultMess(Model model) {
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();
    final ArrayList<DefaultMessages> defaultMsgLst = new ArrayList<DefaultMessages>();
    // 登録の時に確認メッセージを作成する
    DefaultMessages defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-I_register");
    defaultMsg.setMessageTitle("確認メッセージ");
    paramMess = new ArrayList<String>();
    paramMess.add("登録");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-I", paramMess));
    defaultMsgLst.add(defaultMsg);
    // 画面クリアの時に確認メッセージを作成する
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-I_clear");
    defaultMsg.setMessageTitle("確認メッセージ");
    paramMess = new ArrayList<String>();
    paramMess.add("画面表示時の状態に初期化");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-I", paramMess));
    defaultMsgLst.add(defaultMsg);
    // 画面のデータが変更されていない場合のメッセージを作成する
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM034-E");
    defaultMsg.setMessageTitle("確認メッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM034-E", null));
    // 入力チェックエラー
    defaultMsgLst.add(defaultMsg);
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-E", null));
    defaultMsgLst.add(defaultMsg);
    // 入力チェックエラー
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM016-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM016-E", null));
    defaultMsgLst.add(defaultMsg);
    // 入力チェックエラー
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM006-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM006-E", null));
    defaultMsgLst.add(defaultMsg);
    model.addAttribute("defaultMessages", defaultMsgLst);
  }

  /**
   * 画面の表示初期化処理.
   * 
   * @param model ： フォームのModel
   * @param formMst0102d02 ： MST0102d02画面のフォーム
   * @param formMst0102d01 ： MST0102d01画面のフォーム
   * @param userCode ： MST0102d01画面からの得意先コード
   * @param viewMode ： MST0102d01画面からの表示モード
   * @param chainCode ： MST0102d01画面からのチェーンコード
   * @param chainEda ： MST0102d01画面からのチェーン枝番
   * @param sysManagerFlag ： システム管理者フラグ
   * @param loginJigyouCd ： ログイン所属事業所コード
   */
  public void init(Model model, FormMst0102d02 formMst0102d02,
      FormMst0102d01 formMst0102d01,
      String userCode, String viewMode, String chainCode, String chainEda,
      String sysManagerFlag, String loginJigyouCd) {
    try {
      // メッセージのParam
      ArrayList<String> paramMess = new ArrayList<String>();

      // エラーメッセージ変数定義
      ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
      ErrorMessages errMess = new ErrorMessages();

      // 共通部品初期化
      final CommonGetSystemCom systemCom = new CommonGetSystemCom(commonDao,
          null, null, readPropertiesFileService);

      // 画面Mst0102d01のフォームを保存する
      formMst0102d02.setForm1DdlJigyouSho(formMst0102d01
          .getDdlJigyouSho() != null ? formMst0102d01.getDdlJigyouSho() : "");
      formMst0102d02.setForm1TxtCustomerCode(formMst0102d01
          .getTxtCustomerCode());
      formMst0102d02.setForm1TxtCustomerName(formMst0102d01
          .getTxtCustomerName());
      formMst0102d02.setForm1TxtChainCode(formMst0102d01.getTxtChainCode());
      formMst0102d02.setForm1TxtChainEda(formMst0102d01.getTxtChainEda());
      formMst0102d02.setForm1TxtEigyouTantoushaCode(formMst0102d01
          .getTxtEigyouTantoushaCode());
      formMst0102d02.setForm1TxtJimuTantoushaCode(formMst0102d01
          .getTxtJimuTantoushaCode());
      formMst0102d02.setForm1DdlCustomerType(formMst0102d01
          .getDdlCustomerType());
      formMst0102d02.setForm1DdlUchiZeiKoKyakuKubun(formMst0102d01
          .getDdlUchiZeiKoKyakuKubun());
      formMst0102d02.setForm1ChkCancelData(formMst0102d01.isChkCancelData());
      formMst0102d02.setForm1ChkCustomer(formMst0102d01.isChkCustomer());
      formMst0102d02.setForm1ChkBilling(formMst0102d01.isChkBilling());
      formMst0102d02.setForm1ChkCustomerBilling(formMst0102d01
          .isChkCustomerBilling());

      // システム管理者フラグ値を保存する
      formMst0102d02.setSysAdminFlag(sysManagerFlag);
      // ログイン所属事業所コードを保存する
      formMst0102d02.setLoginJigyouShoCode(loginJigyouCd);

      // (1-1) [入力]画面表示モード ＝ Null の場合、画面にエラーメッセージを表示する。
      if (Util.removeWhitespaces(viewMode).equalsIgnoreCase("")) {
        // [変数]メッセージコード（COM026-E）
        paramMess = new ArrayList<String>();
        paramMess.add("画面表示モード");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM026-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return;
      }
      model.addAttribute("viewMode", viewMode);
      formMst0102d02.setViewMode(viewMode);

      // [入力]画面表示モード = '2'（照会） or '3'（訂正） or '4'（取消） の時のみ、後続の引数チェックを行う。
      if (Util.removeWhitespaces(viewMode).equalsIgnoreCase(
          MstConst.SHOUKAI_MODE)
          || Util.removeWhitespaces(viewMode).equalsIgnoreCase(
              MstConst.TEISEI_MODE)
          || Util.removeWhitespaces(viewMode).equalsIgnoreCase(
              MstConst.TORIKESI_MODE)) {
        // (2-1) [入力]得意先コード ＝ Null の場合、画面にエラーメッセージを表示する。
        if (Util.removeWhitespaces(userCode).equalsIgnoreCase("")) {
          // [変数]メッセージコード（COM026-E）
          paramMess = new ArrayList<String>();
          paramMess.add("得意先コード");
          errMess.setMessageContent(readPropertiesFileService.getMessage(
              "COM026-E", paramMess));
          lstErrMess.add(errMess);
          model.addAttribute("errorMessages", lstErrMess);
          model.addAttribute("isDisableControl", "true");
          return;
        }
        // (2-2) [入力]チェーンコード ＝ Null の場合、画面にエラーメッセージを表示する。
        if (Util.removeWhitespaces(chainCode).equalsIgnoreCase("")) {
          // [変数]メッセージコード（COM026-E）
          paramMess = new ArrayList<String>();
          paramMess.add("チェーンコード");
          errMess.setMessageContent(readPropertiesFileService.getMessage(
              "COM026-E", paramMess));
          lstErrMess.add(errMess);
          model.addAttribute("errorMessages", lstErrMess);
          model.addAttribute("isDisableControl", "true");
          return;
        }
        // (2-3) [入力]チェーン枝番 ＝ Null の場合、画面にエラーメッセージを表示する。
        if (Util.removeWhitespaces(chainEda).equalsIgnoreCase("")) {
          // [変数]メッセージコード（COM026-E）
          paramMess = new ArrayList<String>();
          paramMess.add("チェーン枝番");
          errMess.setMessageContent(readPropertiesFileService.getMessage(
              "COM026-E", paramMess));
          lstErrMess.add(errMess);
          model.addAttribute("errorMessages", lstErrMess);
          model.addAttribute("isDisableControl", "true");
          return;
        }
      }

      // 業務日付を取得する
      String businessDate = systemCom.getAplDate();
      if (businessDate == null) {
        // 業務日付 ＝ Nullの場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("業務日付の取得");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM015-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return;
      }
      formMst0102d02.setBusinessDate(Integer.parseInt(businessDate));

      // 得意先マスタ情報を取得する
      Map<String, Object> parms = new HashMap<String, Object>();
      Mst0102d02CustomerData customerData = null;
      // 画面表示モード ≠ 1 の場合、得意先情報を取得する
      if (!Util.removeWhitespaces(viewMode).equals(MstConst.SHINKI_MODE)) {
        parms.put("chainCode", Util.removeWhitespaces(chainCode));
        parms.put("chainEda", Util.removeWhitespaces(chainEda));
        parms.put("userCode", Util.removeWhitespaces(userCode));
        customerData = mst0102d02Dao.getMst0102d02Mapper()
            .getCustomerData(parms);
        if (customerData == null) {
          // 得意先情報格納クラス ＝ NULL の場合、エラーメッセージを表示する
          paramMess = new ArrayList<String>();
          paramMess.add("得意先マスタ");
          paramMess.add("得意先マスタ一覧画面で指定された得意先");
          errMess.setMessageContent(readPropertiesFileService.getMessage(
              "COM009-E", paramMess));
          lstErrMess.add(errMess);
          model.addAttribute("errorMessages", lstErrMess);
          model.addAttribute("isDisableControl", "true");
          return;
        }
      }

      // 得意先事業所マスタ情報を取得する
      Map<String, Object> parms1 = new HashMap<String, Object>();
      List<Mst0102d02CustomerJigyo> custJigyoData = null;
      // 画面表示モード ≠ 1 の場合、得意先事業所情報を取得する
      if (!viewMode.equals(MstConst.SHINKI_MODE)) {
        parms1.put("userCode", Util.removeWhitespaces(userCode));
        parms1.put("businessDate", formMst0102d02.getBusinessDate());
        parms1.put("delFlg", CommonConst.DEL_FLG_OFF);
        parms1.put("stsCode", CommonConst.STS_CD_ENTRY);
        custJigyoData = mst0102d02Dao.getMst0102d02Mapper()
            .getCustomerJigyouData(parms1);
        if (custJigyoData == null || custJigyoData.isEmpty()) {
          // 得意先事業所情報格納クラス ＝ NULL の場合、エラーメッセージを表示する
          paramMess = new ArrayList<String>();
          paramMess.add("得意先事業所マスタ");
          paramMess.add("得意先マスタ一覧画面で指定された得意先");
          errMess.setMessageContent(readPropertiesFileService.getMessage(
              "COM009-E", paramMess));
          lstErrMess.add(errMess);
          model.addAttribute("errorMessages", lstErrMess);
          model.addAttribute("isDisableControl", "true");
          return;
        }
      }

      // 画面のコンボボックスデータを取得する
      if (!getDataForCombobox(lstErrMess, model, formMst0102d02, userCode,
          viewMode, chainCode, chainEda, sysManagerFlag,
          loginJigyouCd, customerData)) {
        return;
      }

      // コースマスタデータ取得
      // 表示モードが新規モードではない場合、コース情報を表示する
      if (!Util.removeWhitespaces(viewMode).equals(MstConst.SHINKI_MODE)) {
        Map<String, Object> parms6 = new HashMap<String, Object>();
        parms6.put("stsCode", CommonConst.STS_CD_ENTRY);
        parms6.put("delFlg", CommonConst.DEL_FLG_OFF);
        parms6.put("businessDate", businessDate);
        parms6.put("glKb", CommonConst.GEN_KB_DELI_TYPE);
        parms6.put("customerCode", Util.removeWhitespaces(customerData
            .getCustCode()));
        List<MstCourseInformation> courseInformationData = mst0102d02Dao
            .getMst0102d02Mapper().getCourseData(parms6);
        if (courseInformationData != null && !courseInformationData.isEmpty()) {
          for (int j = 0; j < courseInformationData.size(); j++) {
            String deliveryKubun = courseInformationData.get(j)
                .getTxtDeliveryKubun();
            String deliveryKubunName = courseInformationData.get(j)
                .getTxtDeliveryKubunName();
            courseInformationData.get(j).setTxtDeliveryKubunName(deliveryKubun
                + "：" + deliveryKubunName);
          }
          model.addAttribute("arrListCourseInformation", courseInformationData);
        }
      }

      // 表示モードによって、フォームの値を初期化
      if (Util.removeWhitespaces(viewMode).equals(MstConst.SHINKI_MODE)) {
        // 画面表示モード
        model.addAttribute("viewModeText", "新規登録");
        // 取扱事業所テーブルを作成する
        final ArrayList<MstToriatsukaiJigyouSho> arrListToriatsukaiJigyouSho
            = new ArrayList<MstToriatsukaiJigyouSho>();
        MstToriatsukaiJigyouSho toriatsukaiJigyouShoObj = new MstToriatsukaiJigyouSho();
        toriatsukaiJigyouShoObj.setDdlToriatsukaiJigyouSho(loginJigyouCd);
        toriatsukaiJigyouShoObj.setTxtEigyouTantoushaCode("");
        toriatsukaiJigyouShoObj.setTxtEigyouTantoushaName("");
        toriatsukaiJigyouShoObj.setTxtJimuTantoushaCode("");
        toriatsukaiJigyouShoObj.setTxtJimuTantoushaName("");
        arrListToriatsukaiJigyouSho.add(toriatsukaiJigyouShoObj);
        // 取得した[変数]得意先事業所情報格納クラスを、取扱事業所テーブルへセットする
        formMst0102d02.setArrListToriatsukaiJigyouSho(
            arrListToriatsukaiJigyouSho);
        model.addAttribute("arrListToriatsukaiJigyouSho",
            arrListToriatsukaiJigyouSho);
        // 幹事事業所
        formMst0102d02.setDdlKanjiJigyouSho(loginJigyouCd);
        // 取纏め請求事業所
        formMst0102d02.setDdlBillingToriMatomeJigyouSho(loginJigyouCd);
        // 状況コード
        formMst0102d02.setTxtStatusCode(CommonConst.STS_CD_ENTRY); // 登録
      } else if (Util.removeWhitespaces(viewMode).equals(MstConst.SHOUKAI_MODE)
          || Util.removeWhitespaces(viewMode).equals(MstConst.TEISEI_MODE)
          || Util.removeWhitespaces(viewMode).equals(MstConst.TORIKESI_MODE)) {
        // 画面表示モード
        if (Util.removeWhitespaces(viewMode).equals(MstConst.SHOUKAI_MODE)) {
          model.addAttribute("viewModeText", "照会");
        } else if (Util.removeWhitespaces(viewMode).equals(
            MstConst.TEISEI_MODE)) {
          model.addAttribute("viewModeText", "訂正");
        } else if (Util.removeWhitespaces(viewMode).equals(
            MstConst.TORIKESI_MODE)) {
          model.addAttribute("viewModeText", "取消");
        }
        // 得意先コード
        formMst0102d02.setTxtCustomerCode(customerData.getCustCode());
        // 得意先フラグ
        if (customerData.getCustFlg().equals(MstConst.CHECK_OFF)) {
          // 対象外
          formMst0102d02.setChkCustomer(false);
        } else if (customerData.getCustFlg().equals(MstConst.CHECK_ON)) {
          // 対象
          formMst0102d02.setChkCustomer(true);
        }
        // 請求先フラグ
        if (customerData.getBildFlg().equals(MstConst.CHECK_OFF)) {
          // 対象外
          formMst0102d02.setChkBilling(false);
        } else if (customerData.getBildFlg().equals(MstConst.CHECK_ON)) {
          // 対象
          formMst0102d02.setChkBilling(true);
        }
        // 請求先コード
        formMst0102d02.setTxtBillingCode(customerData.getBildCode());
        // 請求先名称
        formMst0102d02.setTxtBillingSearchNameHidden(customerData.getBildSearchNm());
        // チェーンコード
        formMst0102d02.setTxtChainCode(String.valueOf(customerData
            .getCainCode()));
        // チェーン枝番
        formMst0102d02.setTxtChainEda(String.valueOf(customerData
            .getCainIdx()));
        // 得意先名称
        formMst0102d02.setTxtCustomerName(customerData.getCustNm());
        // 得意先名称カナ
        formMst0102d02.setTxtCustomerNameKana(customerData.getCustNmKana());
        // 得意先略称
        formMst0102d02.setTxtCustomerNameR(customerData.getCustNmR());
        // 郵便番号
        if (Util.removeWhitespaces(customerData.getZipCode()).equals("")) {
          // 郵便番号が無い場合
          formMst0102d02.setTxtPostalCode1("");
          formMst0102d02.setTxtPostalCode2("");
        } else {
          // 郵便番号がある場合
          String[] postalCodeArr = customerData.getZipCode()
              .split(MstConst.HYPHEN_MARK, -1);
          formMst0102d02.setTxtPostalCode1(postalCodeArr[0]);
          formMst0102d02.setTxtPostalCode2(postalCodeArr[1]);
        }
        // 住所１
        formMst0102d02.setTxtAddress1(customerData.getAdr1());
        // 住所２
        formMst0102d02.setTxtAddress2(customerData.getAdr2());
        // 電話番号
        if (Util.removeWhitespaces(customerData.getTelNo()).equals("")) {
          // 電話番号が無い場合
          formMst0102d02.setTxtTel1("");
          formMst0102d02.setTxtTel2("");
          formMst0102d02.setTxtTel3("");
        } else {
          // 電話番号がある場合
          String[] telNoArr = customerData.getTelNo()
              .split(MstConst.HYPHEN_MARK, -1);
          if (telNoArr.length == 2) {
            formMst0102d02.setTxtTel1(telNoArr[0]);
            formMst0102d02.setTxtTel2(telNoArr[1]);
          } else {
            formMst0102d02.setTxtTel1(telNoArr[0]);
            formMst0102d02.setTxtTel2(telNoArr[1]);
            formMst0102d02.setTxtTel3(telNoArr[2]);
          }
        }
        // FAX番号
        if (Util.removeWhitespaces(customerData.getFaxNo()).equals("")) {
          // FAX番号が無い場合
          formMst0102d02.setTxtFax1("");
          formMst0102d02.setTxtFax2("");
          formMst0102d02.setTxtFax3("");
        } else {
          // FAX番号がある場合
          String[] faxNoArr = customerData.getFaxNo()
              .split(MstConst.HYPHEN_MARK, -1);
          if (faxNoArr.length == 2) {
            formMst0102d02.setTxtFax1(faxNoArr[0]);
            formMst0102d02.setTxtFax2(faxNoArr[1]);
          } else {
            formMst0102d02.setTxtFax1(faxNoArr[0]);
            formMst0102d02.setTxtFax2(faxNoArr[1]);
            formMst0102d02.setTxtFax3(faxNoArr[2]);
          }
        }
        // 得意先担当者
        formMst0102d02.setTxtCustomerTantousha(customerData.getCustTan());
        // メールアドレス
        formMst0102d02.setTxtMailAddress(customerData.getCustTanMail());
        // 取扱事業所テーブルを作成する
        String toriatsukaiJigyouShoId = "";
        ArrayList<MstToriatsukaiJigyouSho> arrListToriatsukaiJigyouSho
            = new ArrayList<MstToriatsukaiJigyouSho>();
        for (int i = 0; i < custJigyoData.size(); i++) {
          MstToriatsukaiJigyouSho toriatsukaiJigyouShoObj = new MstToriatsukaiJigyouSho();
          toriatsukaiJigyouShoObj.setDdlToriatsukaiJigyouSho(custJigyoData.get(i)
              .getJigyouShoCode());
          toriatsukaiJigyouShoObj.setTxtEigyouTantoushaCode(custJigyoData.get(i)
              .getEigyouTantoushaCode());
          toriatsukaiJigyouShoObj.setTxtEigyouTantoushaName(custJigyoData.get(i)
              .getEigyouTantoushaName());
          toriatsukaiJigyouShoObj.setTxtJimuTantoushaCode(custJigyoData.get(i)
              .getJimuTantoushaCode());
          toriatsukaiJigyouShoObj.setTxtJimuTantoushaName(custJigyoData.get(i)
              .getJimuTantoushaName());
          arrListToriatsukaiJigyouSho.add(toriatsukaiJigyouShoObj);
          toriatsukaiJigyouShoId += custJigyoData.get(i).getJigyouShoCode()
              + ",";
        }
        // 取得した[変数]得意先事業所情報格納クラスを、取扱事業所テーブルへセットする
        formMst0102d02.setToriatsukaiJigyouShoId(toriatsukaiJigyouShoId);
        formMst0102d02.setArrListToriatsukaiJigyouSho(
            arrListToriatsukaiJigyouSho);
        model.addAttribute("arrListToriatsukaiJigyouSho",
            arrListToriatsukaiJigyouSho);
        // 幹事事業所
        formMst0102d02.setDdlKanjiJigyouSho(customerData.getManagerJigyoCode());
        // 得意先種別
        formMst0102d02.setDdlCustomerType(customerData.getCustCls());
        // 業態区分
        formMst0102d02.setDdlGyoutaiKubun(customerData.getGyotaiKb());
        // 納品センター
        formMst0102d02.setDdlDeliveryCenter(String.valueOf(customerData
            .getDeliCenterCode()));
        // 関係会社種別
        formMst0102d02.setDdlKankeiKaishaType(customerData.getRelComCls());
        // 補助科目
        formMst0102d02.setDdlHojoKamoku(customerData.getRelComSub());
        // 採番区分
        formMst0102d02.setDdlSaibanKubun(customerData.getDatidxKb());
        // 店舗区分
        formMst0102d02.setDdlTenpoKubun(customerData.getShopKb());
        // YG取引区分
        formMst0102d02.setDdlYgTorihikiKubun(customerData.getYgKb());
        // 内税顧客区分
        formMst0102d02.setDdlUchiZeiKoKyakuKubun(customerData.getTaxIncKb());
        // 内税消費税端数処理
        formMst0102d02.setDdlUchiZeiShori(customerData.getTaxIncFrcKb());
        // 集金有無
        formMst0102d02.setDdlShuukinUmu(customerData.getColMonKb());
        // 現金集金マーク印字
        formMst0102d02.setDdlGenkinShuukinMark(customerData.getColMmrkKb());
        // 集計出力FLG
        formMst0102d02.setDdlShuukinOutputFlag(customerData.getSumsFlg());
        // 手入力伝票発行
        formMst0102d02.setDdlManualInputBilling(customerData.getShipsKb());
        // 手入力出荷伝票
        formMst0102d02.setDdlManualInputDelivery(customerData.getShipsTypCls()
            + MstConst.DELIMITER_ERROR + customerData.getShipsTypId());
        // 伝票行計算金額まるめ
        formMst0102d02.setDdlSlipLineCalculationAmountRounding(customerData
            .getShipsRudKb());
        // 出荷伝票出力品コード
        formMst0102d02.setDdlDeliveryOutputProductCode(customerData
            .getShipsCodeKb());
        // 請求先名称
        formMst0102d02.setTxtBillingName(customerData.getBildNm());
        // 請求先名称カナ
        formMst0102d02.setTxtBillingNameKana(customerData.getBildNmKana());
        // 請求先略称
        formMst0102d02.setTxtBillingNameR(customerData.getBildNmR());
        // 請求先郵便番号
        if (Util.removeWhitespaces(customerData.getBildZipCode()).equals("")) {
          // 請求先郵便番号が無い場合
          formMst0102d02.setTxtBillingZipCode1("");
          formMst0102d02.setTxtBillingZipCode2("");
        } else {
          // 請求先郵便番号がある場合
          String[] zipCodeArr = customerData.getBildZipCode()
              .split(MstConst.HYPHEN_MARK, -1);
          formMst0102d02.setTxtBillingZipCode1(zipCodeArr[0]);
          formMst0102d02.setTxtBillingZipCode2(zipCodeArr[1]);
        }
        // 住所１
        formMst0102d02.setTxtBillingAddress1(customerData.getBildAdr1());
        // 住所２
        formMst0102d02.setTxtBillingAddress2(customerData.getBildAdr2());
        // 電話番号
        if (Util.removeWhitespaces(customerData.getBildTelNo()).equals("")) {
          // 電話番号が無い場合
          formMst0102d02.setTxtBillingTel1("");
          formMst0102d02.setTxtBillingTel2("");
          formMst0102d02.setTxtBillingTel3("");
        } else {
          // 電話番号がある場合
          String[] telNoArr = customerData.getBildTelNo()
              .split(MstConst.HYPHEN_MARK, -1);
          if (telNoArr.length == 2) {
            formMst0102d02.setTxtBillingTel1(telNoArr[0]);
            formMst0102d02.setTxtBillingTel2(telNoArr[1]);
          } else {
            formMst0102d02.setTxtBillingTel1(telNoArr[0]);
            formMst0102d02.setTxtBillingTel2(telNoArr[1]);
            formMst0102d02.setTxtBillingTel3(telNoArr[2]);
          }
        }
        // FAX番号
        if (Util.removeWhitespaces(customerData.getBildFaxNo()).equals("")) {
          // FAX番号が無い場合
          formMst0102d02.setTxtBillingFax1("");
          formMst0102d02.setTxtBillingFax2("");
          formMst0102d02.setTxtBillingFax3("");
        } else {
          // FAX番号がある場合
          String[] faxNoArr = customerData.getBildFaxNo()
              .split(MstConst.HYPHEN_MARK, -1);
          if (faxNoArr.length == 2) {
            formMst0102d02.setTxtBillingFax1(faxNoArr[0]);
            formMst0102d02.setTxtBillingFax2(faxNoArr[1]);
          } else {
            formMst0102d02.setTxtBillingFax1(faxNoArr[0]);
            formMst0102d02.setTxtBillingFax2(faxNoArr[1]);
            formMst0102d02.setTxtBillingFax3(faxNoArr[2]);
          }
        }
        // 請求単位
        formMst0102d02.setDdlBillingUnit(customerData.getBildUntKb());
        // 請求単価
        formMst0102d02.setDdlBillingUnitPrice(customerData.getBildTanka());
        // 請求書種類
        formMst0102d02.setDdlBillingType(customerData.getBildTypCls()
            + MstConst.DELIMITER_ERROR + customerData.getBildTypId());
        // 請求書パターン
        formMst0102d02.setDdlBillingPattern(customerData.getBildPtn());
        // 請求書住所印字
        formMst0102d02.setDdlBillingAddressPrint(customerData
            .getBildAdrOutKb());
        // 請求集計表区分
        formMst0102d02.setDdlBillingShuukeiHyouKubun(customerData
            .getBildSumKb());
        // 消費税計算単位
        formMst0102d02.setDdlBillingShouhizeiCalculationUnit(customerData
            .getTaxUntKb());
        // 請求チェックリスト 出力対象
        formMst0102d02.setDdlBillingCheckListOutputTarget(customerData
            .getBildChkKb());
        // 消費税端数処理
        formMst0102d02.setDdlBillingShouhizeiShori(customerData.getTaxFrcKb());
        // 請求チェックリスト 出力順
        formMst0102d02.setDdlBillingCheckListOutputOrder(customerData
            .getBildChkSrt());
        // 取纏め請求
        formMst0102d02.setDdlBillingToriMatomeUMu(customerData.getSumBildKb());
        // 取纏め請求事業所
        formMst0102d02.setDdlBillingToriMatomeJigyouSho(customerData
            .getSumBildJgyo());
        // 締日１
        formMst0102d02.setTxtCloseDay1(customerData.getTotalDate1() == null
            ? "" : String.valueOf(customerData.getTotalDate1()));
        // 回収月区分１
        formMst0102d02.setDdlRecoveryMonthKubun1(customerData.getColTermKb1());
        // 回収日１
        formMst0102d02.setTxtRecoveryDay1(customerData.getColDate1() == null
            ? "" : String.valueOf(customerData.getColDate1()));
        // 締日２
        formMst0102d02.setTxtCloseDay2(customerData.getTotalDate2() == null
            ? "" : String.valueOf(customerData.getTotalDate2()));
        // 回収月区分２
        formMst0102d02.setDdlRecoveryMonthKubun2(customerData.getColTermKb2());
        // 回収日２
        formMst0102d02.setTxtRecoveryDay2(customerData.getColDate2() == null
            ? "" : String.valueOf(customerData.getColDate2()));
        // 入金種別
        formMst0102d02.setDdlPaymentType(customerData.getRcvmCls());
        // 入金口座
        formMst0102d02.setDdlPaymentAccount(customerData.getRcvmAcc());
        // 手形サイト
        formMst0102d02.setTxtNoteSite(customerData.getReceNoteSite() == null
            ? "" : String.valueOf(customerData.getReceNoteSite()));
        // 取引コード
        formMst0102d02.setTxtTorihikiCode(customerData.getTrCode());
        // 分類コード（定番、店直）
        formMst0102d02.setTxtBunruiCodeTeibanShop(customerData.getBnCodeStS());
        // 伝票区分（定番、店直）
        formMst0102d02.setTxtDenpyouKubunTeibanShop(customerData.getDnKbStS());
        // 分類コード（定番、センター）
        formMst0102d02.setTxtBunruiCodeTeibanCenter(customerData
            .getBnCodeStC());
        // 伝票区分（定番、センター）
        formMst0102d02.setTxtDenpyouKubunTeibanCenter(customerData
            .getDnKbStC());
        // 分類コード（特売、店直）
        formMst0102d02.setTxtBunruiCodeTokubaiShop(customerData.getBnCodeSpS());
        // 伝票区分（特売、店直）
        formMst0102d02.setTxtDenpyouKubunTokubaiShop(customerData.getDnKbSpS());
        // 分類コード（特売、センター）
        formMst0102d02.setTxtBunruiCodeTokubaiCenter(customerData
            .getBnCodeSpC());
        // 伝票区分（特売、センター）
        formMst0102d02.setTxtDenpyouKubunTokubaiCenter(customerData
            .getDnKbSpC());
        // 受領データ突合区分
        formMst0102d02.setDdlJuryouDataKubun(customerData.getRcvDatKb());
        // 請求データ区分
        formMst0102d02.setDdlBillingDataKubun(customerData.getBildDatKb());
        // 修正データ突合区分
        formMst0102d02.setDdlModifyDataKubun(customerData.getModDatKb());
        // 請求先支払い案内データ区分
        formMst0102d02.setDdlBillingPaymentDataKubun(customerData
            .getPayDatKb());
        // （修正種別）返品データ
        if (customerData.getModKbHpn().equals(MstConst.CHECK_OFF)) { // 対象外
          formMst0102d02.setChkModifyTypeHenpinData(false);
        } else if (customerData.getModKbHpn().equals(MstConst.CHECK_ON)) { // 対象
          formMst0102d02.setChkModifyTypeHenpinData(true);
        }
        // （修正種別）欠品データ
        if (customerData.getModKbKpn().equals(MstConst.CHECK_OFF)) { // 対象外
          formMst0102d02.setChkModifyTypeKetsuhinData(false);
        } else if (customerData.getModKbKpn().equals(MstConst.CHECK_ON)) { // 対象
          formMst0102d02.setChkModifyTypeKetsuhinData(true);
        }
        // （修正種別）修正データ
        if (customerData.getModKbSsi().equals(MstConst.CHECK_OFF)) { // 対象外
          formMst0102d02.setChkModifyTypeShuuseiData(false);
        } else if (customerData.getModKbSsi().equals(MstConst.CHECK_ON)) { // 対象
          formMst0102d02.setChkModifyTypeShuuseiData(true);
        }
        // 請求データ配信ＩＤ
        formMst0102d02.setTxtBillingDataDeliveryId(customerData.getBildDatId().trim());
        // 集計コード１
        formMst0102d02.setTxtShuukeiCode1(customerData.getSumCode1());
        // 集計コード２
        formMst0102d02.setTxtShuukeiCode2(customerData.getSumCode2());
        // 使用中止日
        String closeDate = customerData.getCloseDate();
        if (closeDate != null) {
          if (!Util.removeWhitespaces(closeDate).equals("")) {
            closeDate = closeDate.substring(0, 4) + "/" + closeDate.substring(4, 6)
                          + "/" + closeDate.substring(6);
            formMst0102d02.setTxtShiyouChuushiDay(closeDate);
          }
        }
        // 状況コード
        formMst0102d02.setTxtStatusCode(customerData.getStsCode());
        if (Util.removeWhitespaces(viewMode).equals(MstConst.TORIKESI_MODE)) {
          formMst0102d02.setTxtStatusCode(CommonConst.STS_CD_INVALID); // 取消
        }
      }
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }
  }

  /**
   * クリアボタンが押下された場合の処理.
   * 
   * @param formMst0102d02 ： 画面Mst0102d02のフォーム
   * @param model ： フォームのModel
   */
  public void clear(FormMst0102d02 formMst0102d02, Model model) {
    try {
      // メッセージのParam
      ArrayList<String> paramMess = new ArrayList<String>();

      // エラーメッセージ変数定義
      ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
      ErrorMessages errMess = new ErrorMessages();

      // 表示モードを取得する
      final String viewMode = formMst0102d02.getViewMode();
      // 得意先コードを取得する
      String userCode = formMst0102d02.getTxtCustomerCode();
      // チェーンコードを取得する
      String chainCode = formMst0102d02.getTxtChainCode();
      // チェーン枝番を取得する
      String chainEda = formMst0102d02.getTxtChainEda();
      // システム管理者フラグ値を取得する
      String sysAdminFlag = formMst0102d02.getSysAdminFlag();
      // ログイン所属事業所コードを取得する
      String loginJigyouShoCode = formMst0102d02.getLoginJigyouShoCode();

      // 排他を保存する
      formMst0102d02.setHaitaDate(formMst0102d02.getHaitaDate());
      formMst0102d02.setHaitaTime(formMst0102d02.getHaitaTime());

      // 画面Mst0102d01のフォームを保存する
      formMst0102d02.setForm1DdlJigyouSho(formMst0102d02
          .getForm1DdlJigyouSho());
      formMst0102d02.setForm1TxtCustomerCode(formMst0102d02
          .getForm1TxtCustomerCode());
      formMst0102d02.setForm1TxtCustomerName(formMst0102d02
          .getForm1TxtCustomerName());
      formMst0102d02.setForm1TxtChainCode(formMst0102d02
          .getForm1TxtChainCode());
      formMst0102d02.setForm1TxtChainEda(formMst0102d02.getForm1TxtChainEda());
      formMst0102d02.setForm1TxtEigyouTantoushaCode(formMst0102d02
          .getForm1TxtEigyouTantoushaCode());
      formMst0102d02.setForm1TxtJimuTantoushaCode(formMst0102d02
          .getForm1TxtJimuTantoushaCode());
      formMst0102d02.setForm1DdlCustomerType(formMst0102d02
          .getForm1DdlCustomerType());
      formMst0102d02.setForm1DdlUchiZeiKoKyakuKubun(formMst0102d02
          .getForm1DdlUchiZeiKoKyakuKubun());
      formMst0102d02.setForm1ChkCancelData(formMst0102d02
          .isForm1ChkCancelData());
      formMst0102d02.setForm1ChkCustomer(formMst0102d02.isForm1ChkCustomer());
      formMst0102d02.setForm1ChkBilling(formMst0102d02.isForm1ChkBilling());
      formMst0102d02.setForm1ChkCustomerBilling(formMst0102d02
          .isForm1ChkCustomerBilling());

      // システム管理者フラグ値を保存する
      formMst0102d02.setSysAdminFlag(sysAdminFlag);
      // ログイン所属事業所コードを保存する
      formMst0102d02.setLoginJigyouShoCode(loginJigyouShoCode);

      // 表示モードを保存する
      model.addAttribute("viewMode", viewMode);
      formMst0102d02.setViewMode(viewMode);

      // 業務日付を取得する
      String businessDate = formMst0102d02.getBusinessDate().toString();
      // 業務日付を保存する
      formMst0102d02.setBusinessDate(formMst0102d02.getBusinessDate());

      // 得意先マスタ情報を取得する
      Map<String, Object> parms = new HashMap<String, Object>();
      Mst0102d02CustomerData customerData = null;
      // 画面表示モード ≠ 1 の場合、得意先情報を取得する
      if (!Util.removeWhitespaces(viewMode).equals(MstConst.SHINKI_MODE)) {
        parms.put("chainCode", Util.removeWhitespaces(chainCode));
        parms.put("chainEda", Util.removeWhitespaces(chainEda));
        parms.put("userCode", Util.removeWhitespaces(userCode));
        customerData = mst0102d02Dao.getMst0102d02Mapper()
            .getCustomerData(parms);
        if (customerData == null) {
          // 得意先情報格納クラス ＝ NULL の場合、エラーメッセージを表示する
          paramMess = new ArrayList<String>();
          paramMess.add("得意先マスタ");
          paramMess.add("指定された得意先");
          errMess.setMessageContent(readPropertiesFileService.getMessage(
              "COM009-E", paramMess));
          lstErrMess.add(errMess);
          model.addAttribute("errorMessages", lstErrMess);
          return;
        }
      }

      // 得意先事業所マスタ情報を取得する
      Map<String, Object> parms1 = new HashMap<String, Object>();
      List<Mst0102d02CustomerJigyo> custJigyoData = null;
      // 画面表示モード ≠ 1 の場合、得意先事業所情報を取得する
      if (!Util.removeWhitespaces(viewMode).equals(MstConst.SHINKI_MODE)) {
        parms1.put("userCode", Util.removeWhitespaces(userCode));
        parms1.put("businessDate", formMst0102d02.getBusinessDate());
        parms1.put("delFlg", CommonConst.DEL_FLG_OFF);
        parms1.put("stsCode", CommonConst.STS_CD_ENTRY);
        custJigyoData = mst0102d02Dao.getMst0102d02Mapper()
            .getCustomerJigyouData(parms1);
        if (custJigyoData == null || custJigyoData.isEmpty()) {
          // 得意先事業所情報格納クラス ＝ NULL の場合、エラーメッセージを表示する
          paramMess = new ArrayList<String>();
          paramMess.add("得意先事業所マスタ");
          paramMess.add("指定された得意先事業所");
          errMess.setMessageContent(readPropertiesFileService.getMessage(
              "COM009-E", paramMess));
          lstErrMess.add(errMess);
          model.addAttribute("errorMessages", lstErrMess);
          return;
        }
      }

      // 画面のコンボボックスデータを取得する
      if (!getDataForCombobox(lstErrMess, model, formMst0102d02, userCode,
          viewMode, chainCode, chainEda, sysAdminFlag,
          loginJigyouShoCode, customerData)) {
        return;
      }

      // 表示モードによって、フォームの値を初期化
      if (Util.removeWhitespaces(viewMode).equals(MstConst.SHINKI_MODE)) {
        // 画面表示モード
        model.addAttribute("viewModeText", "新規登録");
        // 得意先コード
        formMst0102d02.setTxtCustomerCode("");
        // 得意先フラグ
        formMst0102d02.setChkCustomer(false);
        // 請求先フラグ
        formMst0102d02.setChkBilling(false);
        // 請求先コード
        formMst0102d02.setTxtBillingCode("");
        // 請求先名称
        formMst0102d02.setTxtBillingSearchNameHidden("");
        // チェーンコード
        formMst0102d02.setTxtChainCode("");
        // チェーン枝番
        formMst0102d02.setTxtChainEda("");
        // チェーン名称
        formMst0102d02.setTxtChainNameHidden("");
        // 得意先名称
        formMst0102d02.setTxtCustomerName("");
        // 得意先名称カナ
        formMst0102d02.setTxtCustomerNameKana("");
        // 得意先略称
        formMst0102d02.setTxtCustomerNameR("");
        // 郵便番号
        formMst0102d02.setTxtPostalCode1("");
        formMst0102d02.setTxtPostalCode2("");
        // 住所１
        formMst0102d02.setTxtAddress1("");
        // 住所２
        formMst0102d02.setTxtAddress2("");
        // 電話番号
        formMst0102d02.setTxtTel1("");
        formMst0102d02.setTxtTel2("");
        formMst0102d02.setTxtTel3("");
        // FAX番号
        formMst0102d02.setTxtFax1("");
        formMst0102d02.setTxtFax2("");
        formMst0102d02.setTxtFax3("");
        // 得意先担当者
        formMst0102d02.setTxtCustomerTantousha("");
        // メールアドレス
        formMst0102d02.setTxtMailAddress("");
        // 取扱事業所テーブルを作成する
        final ArrayList<MstToriatsukaiJigyouSho> arrListToriatsukaiJigyouSho
            = new ArrayList<MstToriatsukaiJigyouSho>();
        MstToriatsukaiJigyouSho toriatsukaiJigyouShoObj = new MstToriatsukaiJigyouSho();
        toriatsukaiJigyouShoObj.setDdlToriatsukaiJigyouSho(loginJigyouShoCode);
        toriatsukaiJigyouShoObj.setTxtEigyouTantoushaCode("");
        toriatsukaiJigyouShoObj.setTxtEigyouTantoushaName("");
        toriatsukaiJigyouShoObj.setTxtJimuTantoushaCode("");
        toriatsukaiJigyouShoObj.setTxtJimuTantoushaName("");
        arrListToriatsukaiJigyouSho.add(toriatsukaiJigyouShoObj);
        // 取得した[変数]得意先事業所情報格納クラスを、取扱事業所テーブルへセットする
        formMst0102d02.setArrListToriatsukaiJigyouSho(
            arrListToriatsukaiJigyouSho);
        model.addAttribute("arrListToriatsukaiJigyouSho",
            arrListToriatsukaiJigyouSho);
        // 幹事事業所
        formMst0102d02.setDdlKanjiJigyouSho(loginJigyouShoCode);
        /////// 得意先種別
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtCustomerTypeHidden()).equals("")) {
          // 得意先種別コンボボックスが活性化された場合
          if (formMst0102d02.getDdlCustomerType() != null) {
            formMst0102d02.setDdlCustomerType(formMst0102d02
                .getDdlCustomerType());
          }
        } else {
          // 得意先種別コンボボックスが非活性化された場合
          formMst0102d02.setDdlCustomerType(formMst0102d02
              .getTxtCustomerTypeHidden());
          formMst0102d02.setTxtCustomerTypeHidden(formMst0102d02
              .getTxtCustomerTypeHidden());
        }
        /////// 業態区分
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtGyoutaiKubunHidden()).equals("")) {
          // 業態区分コンボボックスが活性化された場合
          if (formMst0102d02.getDdlGyoutaiKubun() != null) {
            formMst0102d02.setDdlGyoutaiKubun(formMst0102d02
                .getDdlGyoutaiKubun());
          }
        } else {
          // 業態区分コンボボックスが非活性化された場合
          formMst0102d02.setDdlGyoutaiKubun(formMst0102d02
              .getTxtGyoutaiKubunHidden());
          formMst0102d02.setTxtGyoutaiKubunHidden(formMst0102d02
              .getTxtGyoutaiKubunHidden());
        }
        /////// 納品センター
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtDeliveryCenterHidden()).equals("")) {
          // 納品センターコンボボックスが活性化された場合
          if (formMst0102d02.getDdlDeliveryCenter() != null) {
            formMst0102d02.setDdlDeliveryCenter(formMst0102d02
                .getDdlDeliveryCenter());
          }
        } else {
          // 納品センターコンボボックスが非活性化された場合
          formMst0102d02.setDdlDeliveryCenter(formMst0102d02
              .getTxtDeliveryCenterHidden());
          formMst0102d02.setTxtDeliveryCenterHidden(formMst0102d02
              .getTxtDeliveryCenterHidden());
        }
        /////// 関係会社種別
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtKankeiKaishaTypeHidden()).equals("")) {
          // 関係会社種別コンボボックスが活性化された場合
          if (formMst0102d02.getDdlKankeiKaishaType() != null) {
            formMst0102d02.setDdlKankeiKaishaType(formMst0102d02
                .getDdlKankeiKaishaType());
          }
        } else {
          // 関係会社種別コンボボックスが非活性化された場合
          formMst0102d02.setDdlKankeiKaishaType(formMst0102d02
              .getTxtKankeiKaishaTypeHidden());
          formMst0102d02.setTxtKankeiKaishaTypeHidden(formMst0102d02
              .getTxtKankeiKaishaTypeHidden());
        }
        /////// 補助科目
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtHojoKamokuHidden()).equals("")) {
          // 補助科目コンボボックスが活性化された場合
          if (formMst0102d02.getDdlHojoKamoku() != null) {
            formMst0102d02.setDdlHojoKamoku(formMst0102d02.getDdlHojoKamoku());
          }
        } else {
          // 補助科目コンボボックスが非活性化された場合
          formMst0102d02.setDdlHojoKamoku(formMst0102d02
              .getTxtHojoKamokuHidden());
          formMst0102d02.setTxtHojoKamokuHidden(formMst0102d02
              .getTxtHojoKamokuHidden());
        }
        /////// 採番区分
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtSaibanKubunHidden()).equals("")) {
          // 補助科目コンボボックスが活性化された場合
          if (formMst0102d02.getDdlSaibanKubun() != null) {
            formMst0102d02.setDdlSaibanKubun(formMst0102d02
                .getDdlSaibanKubun());
          }
        } else {
          // 補助科目コンボボックスが非活性化された場合
          formMst0102d02.setDdlSaibanKubun(formMst0102d02
              .getTxtSaibanKubunHidden());
          formMst0102d02.setTxtSaibanKubunHidden(formMst0102d02
              .getTxtSaibanKubunHidden());
        }
        /////// 店舗区分
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtTenpoKubunHidden()).equals("")) {
          // 店舗区分コンボボックスが活性化された場合
          if (formMst0102d02.getDdlTenpoKubun() != null) {
            formMst0102d02.setDdlTenpoKubun(formMst0102d02.getDdlTenpoKubun());
          }
        } else {
          // 店舗区分コンボボックスが非活性化された場合
          formMst0102d02.setDdlTenpoKubun(formMst0102d02
              .getTxtTenpoKubunHidden());
          formMst0102d02.setTxtTenpoKubunHidden(formMst0102d02
              .getTxtTenpoKubunHidden());
        }
        /////// YG取引区分
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtYgTorihikiKubunHidden()).equals("")) {
          // YG取引区分コンボボックスが活性化された場合
          if (formMst0102d02.getDdlYgTorihikiKubun() != null) {
            formMst0102d02.setDdlYgTorihikiKubun(formMst0102d02
                .getDdlYgTorihikiKubun());
          }
        } else {
          // YG取引区分コンボボックスが非活性化された場合
          formMst0102d02.setDdlYgTorihikiKubun(formMst0102d02
              .getTxtYgTorihikiKubunHidden());
          formMst0102d02.setTxtYgTorihikiKubunHidden(formMst0102d02
              .getTxtYgTorihikiKubunHidden());
        }
        /////// 内税顧客区分
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtUchiZeiKoKyakuKubunHidden()).equals("")) {
          // 内税顧客区分コンボボックスが活性化された場合
          if (formMst0102d02.getDdlUchiZeiKoKyakuKubun() != null) {
            formMst0102d02.setDdlUchiZeiKoKyakuKubun(formMst0102d02
                .getDdlUchiZeiKoKyakuKubun());
          }
        } else {
          // 内税顧客区分コンボボックスが非活性化された場合
          formMst0102d02.setDdlUchiZeiKoKyakuKubun(formMst0102d02
              .getTxtUchiZeiKoKyakuKubunHidden());
          formMst0102d02.setTxtUchiZeiKoKyakuKubunHidden(formMst0102d02
              .getTxtUchiZeiKoKyakuKubunHidden());
        }
        /////// 内税消費税端数処理
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtUchiZeiShoriHidden()).equals("")) {
          // 内税消費税端数処理コンボボックスが活性化された場合
          if (formMst0102d02.getDdlUchiZeiShori() != null) {
            formMst0102d02.setDdlUchiZeiShori(formMst0102d02
                .getDdlUchiZeiShori());
          }
        } else {
          // 内税消費税端数処理コンボボックスが非活性化された場合
          formMst0102d02.setDdlUchiZeiShori(formMst0102d02
              .getTxtUchiZeiShoriHidden());
          formMst0102d02.setTxtUchiZeiShoriHidden(formMst0102d02
              .getTxtUchiZeiShoriHidden());
        }
        /////// 集金有無
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtShuukinUmuHidden()).equals("")) {
          // 集金有無コンボボックスが活性化された場合
          if (formMst0102d02.getDdlShuukinUmu() != null) {
            formMst0102d02.setDdlShuukinUmu(formMst0102d02.getDdlShuukinUmu());
          }
        } else {
          // 集金有無コンボボックスが非活性化された場合
          formMst0102d02.setDdlShuukinUmu(formMst0102d02
              .getTxtShuukinUmuHidden());
          formMst0102d02.setTxtShuukinUmuHidden(formMst0102d02
              .getTxtShuukinUmuHidden());
        }
        /////// 現金集金マーク印字
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtGenkinShuukinMarkHidden()).equals("")) {
          // 現金集金マーク印字コンボボックスが活性化された場合
          if (formMst0102d02.getDdlGenkinShuukinMark() != null) {
            formMst0102d02.setDdlGenkinShuukinMark(formMst0102d02
                .getDdlGenkinShuukinMark());
          }
        } else {
          // 現金集金マーク印字コンボボックスが非活性化された場合
          formMst0102d02.setDdlGenkinShuukinMark(formMst0102d02
              .getTxtGenkinShuukinMarkHidden());
          formMst0102d02.setTxtGenkinShuukinMarkHidden(formMst0102d02
              .getTxtGenkinShuukinMarkHidden());
        }
        /////// 集計出力FLG
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtShuukinOutputFlagHidden()).equals("")) {
          // 集計出力FLGコンボボックスが活性化された場合
          if (formMst0102d02.getDdlShuukinOutputFlag() != null) {
            formMst0102d02.setDdlShuukinOutputFlag(formMst0102d02
                .getDdlShuukinOutputFlag());
          }
        } else {
          // 集計出力FLGコンボボックスが非活性化された場合
          formMst0102d02.setDdlShuukinOutputFlag(formMst0102d02
              .getTxtShuukinOutputFlagHidden());
          formMst0102d02.setTxtShuukinOutputFlagHidden(formMst0102d02
              .getTxtShuukinOutputFlagHidden());
        }
        /////// 手入力伝票発行
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtManualInputBillingHidden()).equals("")) {
          // 手入力伝票発行コンボボックスが活性化された場合
          if (formMst0102d02.getDdlManualInputBilling() != null) {
            formMst0102d02.setDdlManualInputBilling(formMst0102d02
                .getDdlManualInputBilling());
          }
        } else {
          // 手入力伝票発行コンボボックスが非活性化された場合
          formMst0102d02.setDdlManualInputBilling(formMst0102d02
              .getTxtManualInputBillingHidden());
          formMst0102d02.setTxtManualInputBillingHidden(formMst0102d02
              .getTxtManualInputBillingHidden());
        }
        /////// 手入力出荷伝票
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtManualInputDeliveryHidden()).equals("")) {
          // 手入力出荷伝票コンボボックスが活性化された場合
          if (formMst0102d02.getDdlManualInputDelivery() != null) {
            formMst0102d02.setDdlManualInputDelivery(formMst0102d02
                .getDdlManualInputDelivery());
          }
        } else {
          // 手入力出荷伝票コンボボックスが非活性化された場合
          formMst0102d02.setDdlManualInputDelivery(formMst0102d02
              .getTxtManualInputDeliveryHidden());
          formMst0102d02.setTxtManualInputDeliveryHidden(formMst0102d02
              .getTxtManualInputDeliveryHidden());
        }
        /////// 伝票行計算金額まるめ
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtSlipLineCalculationAmountRoundingHidden()).equals("")) {
          // 伝票行計算金額まるめコンボボックスが活性化された場合
          if (formMst0102d02
              .getDdlSlipLineCalculationAmountRounding() != null) {
            formMst0102d02.setDdlSlipLineCalculationAmountRounding(
                formMst0102d02
                    .getDdlSlipLineCalculationAmountRounding());
          }
        } else {
          // 伝票行計算金額まるめコンボボックスが非活性化された場合
          formMst0102d02.setDdlSlipLineCalculationAmountRounding(formMst0102d02
              .getTxtSlipLineCalculationAmountRoundingHidden());
          formMst0102d02.setTxtSlipLineCalculationAmountRoundingHidden(
              formMst0102d02
                  .getTxtSlipLineCalculationAmountRoundingHidden());
        }
        /////// 出荷伝票出力品コード
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtDeliveryOutputProductCodeHidden()).equals("")) {
          // 出荷伝票出力品コードコンボボックスが活性化された場合
          if (formMst0102d02.getDdlDeliveryOutputProductCode() != null) {
            formMst0102d02.setDdlDeliveryOutputProductCode(formMst0102d02
                .getDdlDeliveryOutputProductCode());
          }
        } else {
          // 出荷伝票出力品コードコンボボックスが非活性化された場合
          formMst0102d02.setDdlDeliveryOutputProductCode(formMst0102d02
              .getTxtDeliveryOutputProductCodeHidden());
          formMst0102d02.setTxtDeliveryOutputProductCodeHidden(formMst0102d02
              .getTxtDeliveryOutputProductCodeHidden());
        }
        // 請求先名称
        formMst0102d02.setTxtBillingName("");
        // 請求先名称カナ
        formMst0102d02.setTxtBillingNameKana("");
        // 請求先略称
        formMst0102d02.setTxtBillingNameR("");
        // 請求先郵便番号
        formMst0102d02.setTxtBillingZipCode1("");
        formMst0102d02.setTxtBillingZipCode2("");
        // 住所１
        formMst0102d02.setTxtBillingAddress1("");
        // 住所２
        formMst0102d02.setTxtBillingAddress2("");
        // 電話番号
        formMst0102d02.setTxtBillingTel1("");
        formMst0102d02.setTxtBillingTel2("");
        formMst0102d02.setTxtBillingTel3("");
        // FAX番号
        formMst0102d02.setTxtBillingFax1("");
        formMst0102d02.setTxtBillingFax2("");
        formMst0102d02.setTxtBillingFax3("");
        // 請求単位
        formMst0102d02.setDdlBillingUnit(formMst0102d02.getDdlBillingUnit());
        // 請求単価
        formMst0102d02.setDdlBillingUnitPrice(formMst0102d02
            .getDdlBillingUnitPrice());
        // 請求書種類
        formMst0102d02.setDdlBillingType(formMst0102d02.getDdlBillingType());
        // 請求書パターン
        if (formMst0102d02.getDdlBillingPattern() != null) {
          formMst0102d02.setDdlBillingPattern(formMst0102d02
              .getDdlBillingPattern());
        }
        // 請求書住所印字
        formMst0102d02.setDdlBillingAddressPrint(formMst0102d02
            .getDdlBillingAddressPrint());
        // 請求集計表区分
        formMst0102d02.setDdlBillingShuukeiHyouKubun(formMst0102d02
            .getDdlBillingShuukeiHyouKubun());
        // 消費税計算単位
        formMst0102d02.setDdlBillingShouhizeiCalculationUnit(formMst0102d02
            .getDdlBillingShouhizeiCalculationUnit());
        // 請求チェックリスト 出力対象
        formMst0102d02.setDdlBillingCheckListOutputTarget(formMst0102d02
            .getDdlBillingCheckListOutputTarget());
        // 消費税端数処理
        formMst0102d02.setDdlBillingShouhizeiShori(formMst0102d02
            .getDdlBillingShouhizeiShori());
        // 請求チェックリスト 出力順
        if (formMst0102d02.getDdlBillingCheckListOutputOrder() != null) {
          formMst0102d02.setDdlBillingCheckListOutputOrder(formMst0102d02
              .getDdlBillingCheckListOutputOrder());
        }
        // 取纏め請求
        formMst0102d02.setDdlBillingToriMatomeUMu(formMst0102d02
            .getDdlBillingToriMatomeUMu());
        // 取纏め請求事業所
        if (formMst0102d02.getDdlBillingToriMatomeJigyouSho() != null) {
          formMst0102d02.setDdlBillingToriMatomeJigyouSho(loginJigyouShoCode);
        }
        // 締日１
        formMst0102d02.setTxtCloseDay1("");
        // 回収月区分１
        formMst0102d02.setDdlRecoveryMonthKubun1(formMst0102d02
            .getDdlRecoveryMonthKubun1());
        // 回収日１
        formMst0102d02.setTxtRecoveryDay1("");
        // 締日２
        formMst0102d02.setTxtCloseDay2("");
        // 回収月区分２
        formMst0102d02.setDdlRecoveryMonthKubun2(formMst0102d02
            .getDdlRecoveryMonthKubun2());
        // 回収日２
        formMst0102d02.setTxtRecoveryDay2("");
        // 入金種別
        formMst0102d02.setDdlPaymentType(formMst0102d02.getDdlPaymentType());
        // 入金口座
        if (formMst0102d02.getDdlPaymentAccount() != null) {
          formMst0102d02.setDdlPaymentAccount(formMst0102d02
              .getDdlPaymentAccount());
        }
        // 手形サイト
        formMst0102d02.setTxtNoteSite("");
        // 取引コード
        formMst0102d02.setTxtTorihikiCode("");
        // 分類コード（定番、店直）
        formMst0102d02.setTxtBunruiCodeTeibanShop("");
        // 伝票区分（定番、店直）
        formMst0102d02.setTxtDenpyouKubunTeibanShop("");
        // 分類コード（定番、センター）
        formMst0102d02.setTxtBunruiCodeTeibanCenter("");
        // 伝票区分（定番、センター）
        formMst0102d02.setTxtDenpyouKubunTeibanCenter("");
        // 分類コード（特売、店直）
        formMst0102d02.setTxtBunruiCodeTokubaiShop("");
        // 伝票区分（特売、店直）
        formMst0102d02.setTxtDenpyouKubunTokubaiShop("");
        // 分類コード（特売、センター）
        formMst0102d02.setTxtBunruiCodeTokubaiCenter("");
        // 伝票区分（特売、センター）
        formMst0102d02.setTxtDenpyouKubunTokubaiCenter("");
        // 受領データ突合区分
        formMst0102d02.setDdlJuryouDataKubun(formMst0102d02
            .getDdlJuryouDataKubun());
        // 請求データ区分
        formMst0102d02.setDdlBillingDataKubun(formMst0102d02
            .getDdlBillingDataKubun());
        // 修正データ突合区分
        formMst0102d02.setDdlModifyDataKubun(formMst0102d02
            .getDdlModifyDataKubun());
        // 請求先支払い案内データ区分
        formMst0102d02.setDdlBillingPaymentDataKubun(formMst0102d02
            .getDdlBillingPaymentDataKubun());
        // （修正種別）返品データ
        formMst0102d02.setChkModifyTypeHenpinData(false);
        // （修正種別）欠品データ
        formMst0102d02.setChkModifyTypeKetsuhinData(false);
        // （修正種別）修正データ
        formMst0102d02.setChkModifyTypeShuuseiData(false);
        // 請求データ配信ＩＤ
        formMst0102d02.setTxtBillingDataDeliveryId("");
        // 集計コード１
        formMst0102d02.setTxtShuukeiCode1("");
        // 集計コード２
        formMst0102d02.setTxtShuukeiCode2("");
        // 使用中止日
        formMst0102d02.setTxtShiyouChuushiDay("");
        // 状況コード
        formMst0102d02.setTxtStatusCode(CommonConst.STS_CD_ENTRY); // 登録
      } else if (Util.removeWhitespaces(viewMode).equals(
          MstConst.TEISEI_MODE)) {
        // 画面表示モード
        model.addAttribute("viewModeText", "訂正");
        // 得意先コード
        formMst0102d02.setTxtCustomerCode(customerData.getCustCode());
        // 得意先フラグ
        if (customerData.getCustFlg().equals(MstConst.CHECK_OFF)) { // 対象外
          formMst0102d02.setChkCustomer(false);
        } else if (customerData.getCustFlg().equals(MstConst.CHECK_ON)) { // 対象
          formMst0102d02.setChkCustomer(true);
        }
        // 請求先フラグ
        if (customerData.getBildFlg().equals(MstConst.CHECK_OFF)) { // 対象外
          formMst0102d02.setChkBilling(false);
        } else if (customerData.getBildFlg().equals(MstConst.CHECK_ON)) { // 対象
          formMst0102d02.setChkBilling(true);
        }
        // 請求先コード
        formMst0102d02.setTxtBillingCode(customerData.getBildCode());
        // 請求先名称
        formMst0102d02.setTxtBillingSearchNameHidden(customerData.getBildSearchNm());
        // チェーンコード
        formMst0102d02.setTxtChainCode(String.valueOf(customerData
            .getCainCode()));
        // チェーン枝番
        formMst0102d02.setTxtChainEda(String.valueOf(customerData
            .getCainIdx()));
        // 得意先名称
        formMst0102d02.setTxtCustomerName(customerData.getCustNm());
        // 得意先名称カナ
        formMst0102d02.setTxtCustomerNameKana(customerData.getCustNmKana());
        // 得意先略称
        formMst0102d02.setTxtCustomerNameR(customerData.getCustNmR());
        // 郵便番号
        if (Util.removeWhitespaces(customerData.getZipCode()).equals("")) {
          // 郵便番号が無い場合
          formMst0102d02.setTxtPostalCode1("");
          formMst0102d02.setTxtPostalCode2("");
        } else {
          // 郵便番号がある場合
          String[] postalCodeArr = customerData.getZipCode()
              .split(MstConst.HYPHEN_MARK, -1);
          formMst0102d02.setTxtPostalCode1(postalCodeArr[0]);
          formMst0102d02.setTxtPostalCode2(postalCodeArr[1]);
        }
        // 住所１
        formMst0102d02.setTxtAddress1(customerData.getAdr1());
        // 住所２
        formMst0102d02.setTxtAddress2(customerData.getAdr2());
        // 電話番号
        if (Util.removeWhitespaces(customerData.getTelNo()).equals("")) {
          // 電話番号が無い場合
          formMst0102d02.setTxtTel1("");
          formMst0102d02.setTxtTel2("");
          formMst0102d02.setTxtTel3("");
        } else {
          // 電話番号がある場合
          String[] telNoArr = customerData.getTelNo()
              .split(MstConst.HYPHEN_MARK, -1);
          if (telNoArr.length == 2) {
            formMst0102d02.setTxtTel1(telNoArr[0]);
            formMst0102d02.setTxtTel2(telNoArr[1]);
          } else {
            formMst0102d02.setTxtTel1(telNoArr[0]);
            formMst0102d02.setTxtTel2(telNoArr[1]);
            formMst0102d02.setTxtTel3(telNoArr[2]);
          }
        }
        // FAX番号
        if (Util.removeWhitespaces(customerData.getFaxNo()).equals("")) {
          // FAX番号が無い場合
          formMst0102d02.setTxtFax1("");
          formMst0102d02.setTxtFax2("");
          formMst0102d02.setTxtFax3("");
        } else {
          // FAX番号がある場合
          String[] faxNoArr = customerData.getFaxNo()
              .split(MstConst.HYPHEN_MARK, -1);
          if (faxNoArr.length == 2) {
            formMst0102d02.setTxtFax1(faxNoArr[0]);
            formMst0102d02.setTxtFax2(faxNoArr[1]);
          } else {
            formMst0102d02.setTxtFax1(faxNoArr[0]);
            formMst0102d02.setTxtFax2(faxNoArr[1]);
            formMst0102d02.setTxtFax3(faxNoArr[2]);
          }
        }
        // 得意先担当者
        formMst0102d02.setTxtCustomerTantousha(customerData.getCustTan());
        // メールアドレス
        formMst0102d02.setTxtMailAddress(customerData.getCustTanMail());
        // 取扱事業所テーブルを作成する
        ArrayList<MstToriatsukaiJigyouSho> arrListToriatsukaiJigyouSho
            = new ArrayList<MstToriatsukaiJigyouSho>();
        for (int i = 0; i < custJigyoData.size(); i++) {
          MstToriatsukaiJigyouSho toriatsukaiJigyouShoObj = new MstToriatsukaiJigyouSho();
          toriatsukaiJigyouShoObj.setDdlToriatsukaiJigyouSho(custJigyoData
              .get(i).getJigyouShoCode());
          toriatsukaiJigyouShoObj.setTxtEigyouTantoushaCode(custJigyoData.get(i)
              .getEigyouTantoushaCode());
          toriatsukaiJigyouShoObj.setTxtEigyouTantoushaName(custJigyoData.get(i)
              .getEigyouTantoushaName());
          toriatsukaiJigyouShoObj.setTxtJimuTantoushaCode(custJigyoData.get(i)
              .getJimuTantoushaCode());
          toriatsukaiJigyouShoObj.setTxtJimuTantoushaName(custJigyoData.get(i)
              .getJimuTantoushaName());
          arrListToriatsukaiJigyouSho.add(toriatsukaiJigyouShoObj);
        }
        // 取得した[変数]得意先事業所情報格納クラスを、取扱事業所テーブルへセットする
        formMst0102d02.setArrListToriatsukaiJigyouSho(
            arrListToriatsukaiJigyouSho);
        model.addAttribute("arrListToriatsukaiJigyouSho",
            arrListToriatsukaiJigyouSho);
        // 幹事事業所
        formMst0102d02.setDdlKanjiJigyouSho(customerData.getManagerJigyoCode());
        // 得意先種別
        formMst0102d02.setDdlCustomerType(customerData.getCustCls());
        // 業態区分
        formMst0102d02.setDdlGyoutaiKubun(customerData.getGyotaiKb());
        // 納品センター
        formMst0102d02.setDdlDeliveryCenter(String.valueOf(customerData
            .getDeliCenterCode()));
        // 関係会社種別
        formMst0102d02.setDdlKankeiKaishaType(customerData.getRelComCls());
        // 補助科目
        formMst0102d02.setDdlHojoKamoku(customerData.getRelComSub());
        // 採番区分
        formMst0102d02.setDdlSaibanKubun(customerData.getDatidxKb());
        // 店舗区分
        formMst0102d02.setDdlTenpoKubun(customerData.getShopKb());
        // YG取引区分
        formMst0102d02.setDdlYgTorihikiKubun(customerData.getYgKb());
        // 内税顧客区分
        formMst0102d02.setDdlUchiZeiKoKyakuKubun(customerData.getTaxIncKb());
        // 内税消費税端数処理
        formMst0102d02.setDdlUchiZeiShori(customerData.getTaxIncFrcKb());
        // 集金有無
        formMst0102d02.setDdlShuukinUmu(customerData.getColMonKb());
        // 現金集金マーク印字
        formMst0102d02.setDdlGenkinShuukinMark(customerData.getColMmrkKb());
        // 集計出力FLG
        formMst0102d02.setDdlShuukinOutputFlag(customerData.getSumsFlg());
        // 手入力伝票発行
        formMst0102d02.setDdlManualInputBilling(customerData.getShipsKb());
        // 手入力出荷伝票
        formMst0102d02.setDdlManualInputDelivery(customerData.getShipsTypCls()
            + MstConst.DELIMITER_ERROR + customerData.getShipsTypId());
        // 伝票行計算金額まるめ
        formMst0102d02.setDdlSlipLineCalculationAmountRounding(customerData
            .getShipsRudKb());
        // 出荷伝票出力品コード
        formMst0102d02.setDdlDeliveryOutputProductCode(customerData
            .getShipsCodeKb());
        // 請求先名称
        formMst0102d02.setTxtBillingName(customerData.getBildNm());
        // 請求先名称カナ
        formMst0102d02.setTxtBillingNameKana(customerData.getBildNmKana());
        // 請求先略称
        formMst0102d02.setTxtBillingNameR(customerData.getBildNmR());
        // 請求先郵便番号
        if (Util.removeWhitespaces(customerData.getBildZipCode()).equals("")) {
          // 請求先郵便番号が無い場合
          formMst0102d02.setTxtBillingZipCode1("");
          formMst0102d02.setTxtBillingZipCode2("");
        } else {
          // 請求先郵便番号がある場合
          String[] zipCodeArr = customerData.getBildZipCode()
              .split(MstConst.HYPHEN_MARK, -1);
          formMst0102d02.setTxtBillingZipCode1(zipCodeArr[0]);
          formMst0102d02.setTxtBillingZipCode2(zipCodeArr[1]);
        }
        // 住所１
        formMst0102d02.setTxtBillingAddress1(customerData.getBildAdr1());
        // 住所２
        formMst0102d02.setTxtBillingAddress2(customerData.getBildAdr2());
        // 電話番号
        if (Util.removeWhitespaces(customerData.getBildTelNo()).equals("")) {
          // 電話番号が無い場合
          formMst0102d02.setTxtBillingTel1("");
          formMst0102d02.setTxtBillingTel2("");
          formMst0102d02.setTxtBillingTel3("");
        } else {
          // 電話番号がある場合
          String[] telNoArr = customerData.getBildTelNo()
              .split(MstConst.HYPHEN_MARK, -1);
          if (telNoArr.length == 2) {
            formMst0102d02.setTxtBillingTel1(telNoArr[0]);
            formMst0102d02.setTxtBillingTel2(telNoArr[1]);
          } else {
            formMst0102d02.setTxtBillingTel1(telNoArr[0]);
            formMst0102d02.setTxtBillingTel2(telNoArr[1]);
            formMst0102d02.setTxtBillingTel3(telNoArr[2]);
          }
        }
        // FAX番号
        if (Util.removeWhitespaces(customerData.getBildFaxNo()).equals("")) {
          // FAX番号が無い場合
          formMst0102d02.setTxtBillingFax1("");
          formMst0102d02.setTxtBillingFax2("");
          formMst0102d02.setTxtBillingFax3("");
        } else {
          // FAX番号がある場合
          String[] faxNoArr = customerData.getBildFaxNo()
              .split(MstConst.HYPHEN_MARK, -1);
          if (faxNoArr.length == 2) {
            formMst0102d02.setTxtBillingFax1(faxNoArr[0]);
            formMst0102d02.setTxtBillingFax2(faxNoArr[1]);
          } else {
            formMst0102d02.setTxtBillingFax1(faxNoArr[0]);
            formMst0102d02.setTxtBillingFax2(faxNoArr[1]);
            formMst0102d02.setTxtBillingFax3(faxNoArr[2]);
          }
        }
        // 請求単位
        formMst0102d02.setDdlBillingUnit(customerData.getBildUntKb());
        // 請求単価
        formMst0102d02.setDdlBillingUnitPrice(customerData.getBildTanka());
        // 請求書種類
        formMst0102d02.setDdlBillingType(customerData.getBildTypCls()
            + MstConst.DELIMITER_ERROR + customerData.getBildTypId());
        // 請求書パターン
        formMst0102d02.setDdlBillingPattern(customerData.getBildPtn());
        // 請求書住所印字
        formMst0102d02.setDdlBillingAddressPrint(customerData
            .getBildAdrOutKb());
        // 請求集計表区分
        formMst0102d02.setDdlBillingShuukeiHyouKubun(customerData
            .getBildSumKb());
        // 消費税計算単位
        formMst0102d02.setDdlBillingShouhizeiCalculationUnit(customerData
            .getTaxUntKb());
        // 請求チェックリスト 出力対象
        formMst0102d02.setDdlBillingCheckListOutputTarget(customerData
            .getBildChkKb());
        // 消費税端数処理
        formMst0102d02.setDdlBillingShouhizeiShori(customerData.getTaxFrcKb());
        // 請求チェックリスト 出力順
        formMst0102d02.setDdlBillingCheckListOutputOrder(customerData
            .getBildChkSrt());
        // 取纏め請求
        formMst0102d02.setDdlBillingToriMatomeUMu(customerData.getSumBildKb());
        // 取纏め請求事業所
        formMst0102d02.setDdlBillingToriMatomeJigyouSho(customerData
            .getSumBildJgyo());
        // 締日１
        formMst0102d02.setTxtCloseDay1(customerData.getTotalDate1() == null
            ? "" : String.valueOf(customerData.getTotalDate1()));
        // 回収月区分１
        formMst0102d02.setDdlRecoveryMonthKubun1(customerData.getColTermKb1());
        // 回収日１
        formMst0102d02.setTxtRecoveryDay1(customerData.getColDate1() == null
            ? "" : String.valueOf(customerData.getColDate1()));
        // 締日２
        formMst0102d02.setTxtCloseDay2(customerData.getTotalDate2() == null
            ? "" : String.valueOf(customerData.getTotalDate2()));
        // 回収月区分２
        formMst0102d02.setDdlRecoveryMonthKubun2(customerData.getColTermKb2());
        // 回収日２
        formMst0102d02.setTxtRecoveryDay2(customerData.getColDate2() == null
            ? "" : String.valueOf(customerData.getColDate2()));
        // 入金種別
        formMst0102d02.setDdlPaymentType(customerData.getRcvmCls());
        // 入金口座
        formMst0102d02.setDdlPaymentAccount(customerData.getRcvmAcc());
        // 手形サイト
        formMst0102d02.setTxtNoteSite(customerData.getReceNoteSite() == null
            ? "" : String.valueOf(customerData.getReceNoteSite()));
        // 取引コード
        formMst0102d02.setTxtTorihikiCode(customerData.getTrCode());
        // 分類コード（定番、店直）
        formMst0102d02.setTxtBunruiCodeTeibanShop(customerData.getBnCodeStS());
        // 伝票区分（定番、店直）
        formMst0102d02.setTxtDenpyouKubunTeibanShop(customerData.getDnKbStS());
        // 分類コード（定番、センター）
        formMst0102d02.setTxtBunruiCodeTeibanCenter(customerData
            .getBnCodeStC());
        // 伝票区分（定番、センター）
        formMst0102d02.setTxtDenpyouKubunTeibanCenter(customerData
            .getDnKbStC());
        // 分類コード（特売、店直）
        formMst0102d02.setTxtBunruiCodeTokubaiShop(customerData.getBnCodeSpS());
        // 伝票区分（特売、店直）
        formMst0102d02.setTxtDenpyouKubunTokubaiShop(customerData.getDnKbSpS());
        // 分類コード（特売、センター）
        formMst0102d02.setTxtBunruiCodeTokubaiCenter(customerData
            .getBnCodeSpC());
        // 伝票区分（特売、センター）
        formMst0102d02.setTxtDenpyouKubunTokubaiCenter(customerData
            .getDnKbSpC());
        // 受領データ突合区分
        formMst0102d02.setDdlJuryouDataKubun(customerData.getRcvDatKb());
        // 請求データ区分
        formMst0102d02.setDdlBillingDataKubun(customerData.getBildDatKb());
        // 修正データ突合区分
        formMst0102d02.setDdlModifyDataKubun(customerData.getModDatKb());
        // 請求先支払い案内データ区分
        formMst0102d02.setDdlBillingPaymentDataKubun(customerData
            .getPayDatKb());
        // （修正種別）返品データ
        if (customerData.getModKbHpn().equals(MstConst.CHECK_OFF)) { // 対象外
          formMst0102d02.setChkModifyTypeHenpinData(false);
        } else if (customerData.getModKbHpn().equals(MstConst.CHECK_ON)) { // 対象
          formMst0102d02.setChkModifyTypeHenpinData(true);
        }
        // （修正種別）欠品データ
        if (customerData.getModKbKpn().equals(MstConst.CHECK_OFF)) { // 対象外
          formMst0102d02.setChkModifyTypeKetsuhinData(false);
        } else if (customerData.getModKbKpn().equals(MstConst.CHECK_ON)) { // 対象
          formMst0102d02.setChkModifyTypeKetsuhinData(true);
        }
        // （修正種別）修正データ
        if (customerData.getModKbSsi().equals(MstConst.CHECK_OFF)) { // 対象外
          formMst0102d02.setChkModifyTypeShuuseiData(false);
        } else if (customerData.getModKbSsi().equals(MstConst.CHECK_ON)) { // 対象
          formMst0102d02.setChkModifyTypeShuuseiData(true);
        }
        // 請求データ配信ＩＤ
        formMst0102d02.setTxtBillingDataDeliveryId(customerData.getBildDatId().trim());
        // 集計コード１
        formMst0102d02.setTxtShuukeiCode1(customerData.getSumCode1());
        // 集計コード２
        formMst0102d02.setTxtShuukeiCode2(customerData.getSumCode2());
        // 使用中止日
        formMst0102d02.setTxtShiyouChuushiDay(customerData.getCloseDate());
        // 状況コード
        formMst0102d02.setTxtStatusCode(customerData.getStsCode());
        // コースマスタデータ取得
        Map<String, Object> parms6 = new HashMap<String, Object>();
        parms6.put("stsCode", CommonConst.STS_CD_ENTRY);
        parms6.put("delFlg", CommonConst.DEL_FLG_OFF);
        parms6.put("businessDate", businessDate);
        parms6.put("glKb", CommonConst.GEN_KB_DELI_TYPE);
        parms6.put("customerCode", customerData.getCustCode());
        List<MstCourseInformation> courseInformationData = mst0102d02Dao
            .getMst0102d02Mapper().getCourseData(parms6);
        if (courseInformationData != null && !courseInformationData.isEmpty()) {
          for (int j = 0; j < courseInformationData.size(); j++) {
            String deliveryKubun = courseInformationData.get(j)
                .getTxtDeliveryKubun();
            String deliveryKubunName = courseInformationData.get(j)
                .getTxtDeliveryKubunName();
            courseInformationData.get(j).setTxtDeliveryKubunName(deliveryKubun
                + "：" + deliveryKubunName);
          }
          model.addAttribute("arrListCourseInformation", courseInformationData);
        }
      }
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }
  }

  /**
   * フォームの現在の状態を初期する.
   * 
   * @param formMst0102d02 ： 画面Mst0102d02のフォーム
   * @param model ： フォームのModel
   */
  public boolean returnFormState(FormMst0102d02 formMst0102d02, Model model) {
    try {
      // メッセージのParam
      ArrayList<String> paramMess = new ArrayList<String>();

      // エラーメッセージ変数定義
      ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
      ErrorMessages errMess = new ErrorMessages();

      // 表示モードを取得する
      final String viewMode = formMst0102d02.getViewMode();
      // 得意先コードを取得する
      String userCode = formMst0102d02.getTxtCustomerCode();
      // チェーンコードを取得する
      String chainCode = formMst0102d02.getTxtChainCode();
      // チェーン枝番を取得する
      String chainEda = formMst0102d02.getTxtChainEda();
      // システム管理者フラグ値を取得する
      String sysAdminFlag = formMst0102d02.getSysAdminFlag();
      // ログイン所属事業所コードを取得する
      String loginJigyouShoCode = formMst0102d02.getLoginJigyouShoCode();

      // 排他を保存する
      formMst0102d02.setHaitaDate(formMst0102d02.getHaitaDate());
      formMst0102d02.setHaitaTime(formMst0102d02.getHaitaTime());

      // 画面Mst0102d01のフォームを保存する
      formMst0102d02.setForm1DdlJigyouSho(formMst0102d02
          .getForm1DdlJigyouSho());
      formMst0102d02.setForm1TxtCustomerCode(formMst0102d02
          .getForm1TxtCustomerCode());
      formMst0102d02.setForm1TxtCustomerName(formMst0102d02
          .getForm1TxtCustomerName());
      formMst0102d02.setForm1TxtChainCode(formMst0102d02
          .getForm1TxtChainCode());
      formMst0102d02.setForm1TxtChainEda(formMst0102d02.getForm1TxtChainEda());
      formMst0102d02.setForm1TxtEigyouTantoushaCode(formMst0102d02
          .getForm1TxtEigyouTantoushaCode());
      formMst0102d02.setForm1TxtJimuTantoushaCode(formMst0102d02
          .getForm1TxtJimuTantoushaCode());
      formMst0102d02.setForm1DdlCustomerType(formMst0102d02
          .getForm1DdlCustomerType());
      formMst0102d02.setForm1DdlUchiZeiKoKyakuKubun(formMst0102d02
          .getForm1DdlUchiZeiKoKyakuKubun());
      formMst0102d02.setForm1ChkCancelData(formMst0102d02
          .isForm1ChkCancelData());
      formMst0102d02.setForm1ChkCustomer(formMst0102d02.isForm1ChkCustomer());
      formMst0102d02.setForm1ChkBilling(formMst0102d02.isForm1ChkBilling());
      formMst0102d02.setForm1ChkCustomerBilling(formMst0102d02
          .isForm1ChkCustomerBilling());

      // システム管理者フラグ値を保存する
      formMst0102d02.setSysAdminFlag(sysAdminFlag);
      // ログイン所属事業所コードを保存する
      formMst0102d02.setLoginJigyouShoCode(loginJigyouShoCode);

      // 表示モードを保存する
      model.addAttribute("viewMode", viewMode);
      formMst0102d02.setViewMode(viewMode);

      // 業務日付を取得する
      String businessDate = formMst0102d02.getBusinessDate().toString();
      // 業務日付を保存する
      formMst0102d02.setBusinessDate(formMst0102d02.getBusinessDate());

      // 得意先マスタ情報を取得する
      Map<String, Object> parms = new HashMap<String, Object>();
      Mst0102d02CustomerData customerData = null;
      // 画面表示モード ≠ 1 の場合、得意先情報を取得する
      if (!Util.removeWhitespaces(viewMode).equals(MstConst.SHINKI_MODE)) {
        parms.put("chainCode", Util.removeWhitespaces(chainCode));
        parms.put("chainEda", Util.removeWhitespaces(chainEda));
        parms.put("userCode", Util.removeWhitespaces(userCode));
        customerData = mst0102d02Dao.getMst0102d02Mapper()
            .getCustomerData(parms);
        if (customerData == null) {
          // 得意先情報格納クラス ＝ NULL の場合、エラーメッセージを表示する
          paramMess = new ArrayList<String>();
          paramMess.add("得意先マスタ");
          paramMess.add("得意先マスタ一覧画面で指定された得意先");
          errMess.setMessageContent(readPropertiesFileService.getMessage(
              "COM009-E", paramMess));
          lstErrMess.add(errMess);
          model.addAttribute("errorMessages", lstErrMess);
          return false;
        }
      }

      // 得意先事業所マスタ情報を取得する
      Map<String, Object> parms1 = new HashMap<String, Object>();
      List<Mst0102d02CustomerJigyo> custJigyoData = null;
      // 画面表示モード ≠ 1 の場合、得意先事業所情報を取得する
      if (!Util.removeWhitespaces(viewMode).equals(MstConst.SHINKI_MODE)) {
        parms1.put("userCode", userCode);
        parms1.put("businessDate", formMst0102d02.getBusinessDate());
        parms1.put("delFlg", CommonConst.DEL_FLG_OFF);
        parms1.put("stsCode", CommonConst.STS_CD_ENTRY);
        custJigyoData = mst0102d02Dao.getMst0102d02Mapper()
            .getCustomerJigyouData(parms1);
        if (custJigyoData == null || custJigyoData.isEmpty()) {
          // 得意先事業所情報格納クラス ＝ NULL の場合、エラーメッセージを表示する
          paramMess = new ArrayList<String>();
          paramMess.add("得意先事業所マスタ");
          paramMess.add("得意先マスタ一覧画面で指定された得意先");
          errMess.setMessageContent(readPropertiesFileService.getMessage(
              "COM009-E", paramMess));
          lstErrMess.add(errMess);
          model.addAttribute("errorMessages", lstErrMess);
          return false;
        }
      }

      // 画面のコンボボックスデータを取得する
      if (!getDataForCombobox(lstErrMess, model, formMst0102d02, userCode,
          viewMode, chainCode, chainEda, sysAdminFlag,
          loginJigyouShoCode, customerData)) {
        return false;
      }

      // 画面表示モード
      if (Util.removeWhitespaces(viewMode).equals(MstConst.SHINKI_MODE)) {
        model.addAttribute("viewModeText", "新規登録");
      } else if (Util.removeWhitespaces(viewMode)
          .equals(MstConst.TEISEI_MODE)) {
        model.addAttribute("viewModeText", "訂正");
      } else if (Util.removeWhitespaces(viewMode)
          .equals(MstConst.TORIKESI_MODE)) {
        model.addAttribute("viewModeText", "取消");
      }
      // 得意先コード
      formMst0102d02.setTxtCustomerCode(formMst0102d02.getTxtCustomerCode());
      // 得意先フラグ
      formMst0102d02.setChkCustomer(formMst0102d02.isChkCustomer());
      // 請求先フラグ
      formMst0102d02.setChkBilling(formMst0102d02.isChkBilling());
      // 請求先コード
      formMst0102d02.setTxtBillingCode(formMst0102d02.getTxtBillingCode());
      // 請求先名称
      formMst0102d02.setTxtBillingSearchNameHidden(formMst0102d02
          .getTxtBillingSearchNameHidden());
      // チェーンコード
      formMst0102d02.setTxtChainCode(formMst0102d02.getTxtChainCode());
      // チェーン枝番
      formMst0102d02.setTxtChainEda(formMst0102d02.getTxtChainEda());
      // 得意先名称
      formMst0102d02.setTxtCustomerName(formMst0102d02.getTxtCustomerName());
      // 得意先名称カナ
      formMst0102d02.setTxtCustomerNameKana(formMst0102d02
          .getTxtCustomerNameKana());
      // 得意先略称
      formMst0102d02.setTxtCustomerNameR(formMst0102d02.getTxtCustomerNameR());
      // 郵便番号
      formMst0102d02.setTxtPostalCode1(formMst0102d02.getTxtPostalCode1());
      formMst0102d02.setTxtPostalCode2(formMst0102d02.getTxtPostalCode2());
      // 住所１
      formMst0102d02.setTxtAddress1(formMst0102d02.getTxtAddress1());
      // 住所２
      formMst0102d02.setTxtAddress2(formMst0102d02.getTxtAddress2());
      // 電話番号
      formMst0102d02.setTxtTel1(formMst0102d02.getTxtTel1());
      formMst0102d02.setTxtTel2(formMst0102d02.getTxtTel2());
      formMst0102d02.setTxtTel3(formMst0102d02.getTxtTel3());
      // FAX番号
      formMst0102d02.setTxtFax1(formMst0102d02.getTxtFax1());
      formMst0102d02.setTxtFax2(formMst0102d02.getTxtFax2());
      formMst0102d02.setTxtFax3(formMst0102d02.getTxtFax3());
      // 得意先担当者
      formMst0102d02.setTxtCustomerTantousha(formMst0102d02
          .getTxtCustomerTantousha());
      // メールアドレス
      formMst0102d02.setTxtMailAddress(formMst0102d02.getTxtMailAddress());
      // 取扱事業所テーブルを作成する
      ArrayList<MstToriatsukaiJigyouSho> arrListToriatsukaiJigyouSho = formMst0102d02
          .getArrListToriatsukaiJigyouSho();
      for (int i = 0; i < arrListToriatsukaiJigyouSho.size(); i++) {
        MstToriatsukaiJigyouSho currentItem = arrListToriatsukaiJigyouSho.get(i);
        /////// 取扱事業所
        if (!Util.removeWhitespaces(currentItem
            .getTxtToriatsukaiJigyouShoHidden()).equals("")) {
          // 取扱事業所コンボボックスが非活性化された場合
          currentItem.setDdlToriatsukaiJigyouSho(currentItem
              .getTxtToriatsukaiJigyouShoHidden());
          currentItem.setTxtToriatsukaiJigyouShoHidden(currentItem
              .getTxtToriatsukaiJigyouShoHidden());
          model.addAttribute("isDisableCombobox", "true");
        }
      }
      // 取得した[変数]得意先事業所情報格納クラスを、取扱事業所テーブルへセットする
      formMst0102d02.setToriatsukaiJigyouShoId(formMst0102d02
          .getToriatsukaiJigyouShoId());
      formMst0102d02.setArrListToriatsukaiJigyouSho(
          arrListToriatsukaiJigyouSho);
      model.addAttribute("arrListToriatsukaiJigyouSho",
          arrListToriatsukaiJigyouSho);
      /////// 幹事事業所
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtKanjiJigyouShoHidden()).equals("")) {
        // 幹事事業所コンボボックスが活性化された場合
        if (formMst0102d02.getDdlKanjiJigyouSho() != null) {
          formMst0102d02.setDdlKanjiJigyouSho(formMst0102d02
              .getDdlKanjiJigyouSho());
        }
      } else {
        // 幹事事業所コンボボックスが非活性化された場合
        formMst0102d02.setDdlKanjiJigyouSho(formMst0102d02
            .getTxtKanjiJigyouShoHidden());
        formMst0102d02.setTxtKanjiJigyouShoHidden(formMst0102d02
            .getTxtKanjiJigyouShoHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      /////// 得意先種別
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtCustomerTypeHidden()).equals("")) {
        // 得意先種別コンボボックスが活性化された場合
        if (formMst0102d02.getDdlCustomerType() != null) {
          formMst0102d02.setDdlCustomerType(formMst0102d02
              .getDdlCustomerType());
        }
      } else {
        // 得意先種別コンボボックスが非活性化された場合
        formMst0102d02.setDdlCustomerType(formMst0102d02
            .getTxtCustomerTypeHidden());
        formMst0102d02.setTxtCustomerTypeHidden(formMst0102d02
            .getTxtCustomerTypeHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      /////// 業態区分
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtGyoutaiKubunHidden()).equals("")) {
        // 業態区分コンボボックスが活性化された場合
        if (formMst0102d02.getDdlGyoutaiKubun() != null) {
          formMst0102d02.setDdlGyoutaiKubun(formMst0102d02
              .getDdlGyoutaiKubun());
        }
      } else {
        // 業態区分コンボボックスが非活性化された場合
        formMst0102d02.setDdlGyoutaiKubun(formMst0102d02
            .getTxtGyoutaiKubunHidden());
        formMst0102d02.setTxtGyoutaiKubunHidden(formMst0102d02
            .getTxtGyoutaiKubunHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      /////// 納品センター
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtDeliveryCenterHidden()).equals("")) {
        // 納品センターコンボボックスが活性化された場合
        if (formMst0102d02.getDdlDeliveryCenter() != null) {
          // 納品センターコンボボックスのデータを取得する
          if (formMst0102d02.getTxtDeliveryCenterDataChanged()
              .equals("true")) {
            ArrayList<ObjCombobox> deliveryCenterResultList = getDeliveryData(
                businessDate,
                formMst0102d02.getDdlKanjiJigyouSho(),
                formMst0102d02.getTxtChainCode(),
                formMst0102d02.getTxtChainEda());
            // 取得した[変数]納品先情報格納クラスを、納品センターへセットする
            model.addAttribute("arrListDeliveryCenter",
                deliveryCenterResultList);
          }
          formMst0102d02.setDdlDeliveryCenter(formMst0102d02
              .getDdlDeliveryCenter());
          model.addAttribute("enableDeliveryCenter", "1");
        }
      } else {
        // 納品センターコンボボックスが非活性化された場合
        formMst0102d02.setDdlDeliveryCenter(formMst0102d02
            .getTxtDeliveryCenterHidden());
        formMst0102d02.setTxtDeliveryCenterHidden(formMst0102d02
            .getTxtDeliveryCenterHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      /////// 関係会社種別
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtKankeiKaishaTypeHidden()).equals("")) {
        // 関係会社種別コンボボックスが活性化された場合
        if (formMst0102d02.getDdlKankeiKaishaType() != null) {
          formMst0102d02.setDdlKankeiKaishaType(formMst0102d02
              .getDdlKankeiKaishaType());
        }
      } else {
        // 関係会社種別コンボボックスが非活性化された場合
        formMst0102d02.setDdlKankeiKaishaType(formMst0102d02
            .getTxtKankeiKaishaTypeHidden());
        formMst0102d02.setTxtKankeiKaishaTypeHidden(formMst0102d02
            .getTxtKankeiKaishaTypeHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      /////// 補助科目
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtHojoKamokuHidden()).equals("")) {
        // 補助科目コンボボックスが活性化された場合
        if (formMst0102d02.getDdlHojoKamoku() != null) {
          formMst0102d02.setDdlHojoKamoku(formMst0102d02.getDdlHojoKamoku());
        }
      } else {
        // 補助科目コンボボックスが非活性化された場合
        formMst0102d02.setDdlHojoKamoku(formMst0102d02
            .getTxtHojoKamokuHidden());
        formMst0102d02.setTxtHojoKamokuHidden(formMst0102d02
            .getTxtHojoKamokuHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      /////// 採番区分
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtSaibanKubunHidden()).equals("")) {
        // 補助科目コンボボックスが活性化された場合
        if (formMst0102d02.getDdlSaibanKubun() != null) {
          formMst0102d02.setDdlSaibanKubun(formMst0102d02.getDdlSaibanKubun());
        }
      } else {
        // 補助科目コンボボックスが非活性化された場合
        formMst0102d02.setDdlSaibanKubun(formMst0102d02
            .getTxtSaibanKubunHidden());
        formMst0102d02.setTxtSaibanKubunHidden(formMst0102d02
            .getTxtSaibanKubunHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      /////// 店舗区分
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtTenpoKubunHidden()).equals("")) {
        // 店舗区分コンボボックスが活性化された場合
        if (formMst0102d02.getDdlTenpoKubun() != null) {
          formMst0102d02.setDdlTenpoKubun(formMst0102d02.getDdlTenpoKubun());
        }
      } else {
        // 店舗区分コンボボックスが非活性化された場合
        formMst0102d02.setDdlTenpoKubun(formMst0102d02
            .getTxtTenpoKubunHidden());
        formMst0102d02.setTxtTenpoKubunHidden(formMst0102d02
            .getTxtTenpoKubunHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      /////// YG取引区分
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtYgTorihikiKubunHidden()).equals("")) {
        // YG取引区分コンボボックスが活性化された場合
        if (formMst0102d02.getDdlYgTorihikiKubun() != null) {
          formMst0102d02.setDdlYgTorihikiKubun(formMst0102d02
              .getDdlYgTorihikiKubun());
        }
      } else {
        // YG取引区分コンボボックスが非活性化された場合
        formMst0102d02.setDdlYgTorihikiKubun(formMst0102d02
            .getTxtYgTorihikiKubunHidden());
        formMst0102d02.setTxtYgTorihikiKubunHidden(formMst0102d02
            .getTxtYgTorihikiKubunHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      /////// 内税顧客区分
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtUchiZeiKoKyakuKubunHidden()).equals("")) {
        // 内税顧客区分コンボボックスが活性化された場合
        if (formMst0102d02.getDdlUchiZeiKoKyakuKubun() != null) {
          formMst0102d02.setDdlUchiZeiKoKyakuKubun(formMst0102d02
              .getDdlUchiZeiKoKyakuKubun());
        }
      } else {
        // 内税顧客区分コンボボックスが非活性化された場合
        formMst0102d02.setDdlUchiZeiKoKyakuKubun(formMst0102d02
            .getTxtUchiZeiKoKyakuKubunHidden());
        formMst0102d02.setTxtUchiZeiKoKyakuKubunHidden(formMst0102d02
            .getTxtUchiZeiKoKyakuKubunHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      /////// 内税消費税端数処理
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtUchiZeiShoriHidden()).equals("")) {
        // 内税消費税端数処理コンボボックスが活性化された場合
        if (formMst0102d02.getDdlUchiZeiShori() != null) {
          formMst0102d02.setDdlUchiZeiShori(formMst0102d02
              .getDdlUchiZeiShori());
        }
      } else {
        // 内税消費税端数処理コンボボックスが非活性化された場合
        formMst0102d02.setDdlUchiZeiShori(formMst0102d02
            .getTxtUchiZeiShoriHidden());
        formMst0102d02.setTxtUchiZeiShoriHidden(formMst0102d02
            .getTxtUchiZeiShoriHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      /////// 集金有無
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtShuukinUmuHidden()).equals("")) {
        // 集金有無コンボボックスが活性化された場合
        if (formMst0102d02.getDdlShuukinUmu() != null) {
          formMst0102d02.setDdlShuukinUmu(formMst0102d02.getDdlShuukinUmu());
        }
      } else {
        // 集金有無コンボボックスが非活性化された場合
        formMst0102d02.setDdlShuukinUmu(formMst0102d02
            .getTxtShuukinUmuHidden());
        formMst0102d02.setTxtShuukinUmuHidden(formMst0102d02
            .getTxtShuukinUmuHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      /////// 現金集金マーク印字
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtGenkinShuukinMarkHidden()).equals("")) {
        // 現金集金マーク印字コンボボックスが活性化された場合
        if (formMst0102d02.getDdlGenkinShuukinMark() != null) {
          formMst0102d02.setDdlGenkinShuukinMark(formMst0102d02
              .getDdlGenkinShuukinMark());
        }
      } else {
        // 現金集金マーク印字コンボボックスが非活性化された場合
        formMst0102d02.setDdlGenkinShuukinMark(formMst0102d02
            .getTxtGenkinShuukinMarkHidden());
        formMst0102d02.setTxtGenkinShuukinMarkHidden(formMst0102d02
            .getTxtGenkinShuukinMarkHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      /////// 集計出力FLG
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtShuukinOutputFlagHidden()).equals("")) {
        // 集計出力FLGコンボボックスが活性化された場合
        if (formMst0102d02.getDdlShuukinOutputFlag() != null) {
          formMst0102d02.setDdlShuukinOutputFlag(formMst0102d02
              .getDdlShuukinOutputFlag());
        }
      } else {
        // 集計出力FLGコンボボックスが非活性化された場合
        formMst0102d02.setDdlShuukinOutputFlag(formMst0102d02
            .getTxtShuukinOutputFlagHidden());
        formMst0102d02.setTxtShuukinOutputFlagHidden(formMst0102d02
            .getTxtShuukinOutputFlagHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      /////// 手入力伝票発行
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtManualInputBillingHidden()).equals("")) {
        // 手入力伝票発行コンボボックスが活性化された場合
        if (formMst0102d02.getDdlManualInputBilling() != null) {
          formMst0102d02.setDdlManualInputBilling(formMst0102d02
              .getDdlManualInputBilling());
        }
      } else {
        // 手入力伝票発行コンボボックスが非活性化された場合
        formMst0102d02.setDdlManualInputBilling(formMst0102d02
            .getTxtManualInputBillingHidden());
        formMst0102d02.setTxtManualInputBillingHidden(formMst0102d02
            .getTxtManualInputBillingHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      /////// 手入力出荷伝票
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtManualInputDeliveryHidden()).equals("")) {
        // 手入力出荷伝票コンボボックスが活性化された場合
        if (formMst0102d02.getDdlManualInputDelivery() != null) {
          formMst0102d02.setDdlManualInputDelivery(formMst0102d02
              .getDdlManualInputDelivery());
        }
      } else {
        // 手入力出荷伝票コンボボックスが非活性化された場合
        formMst0102d02.setDdlManualInputDelivery(formMst0102d02
            .getTxtManualInputDeliveryHidden());
        formMst0102d02.setTxtManualInputDeliveryHidden(formMst0102d02
            .getTxtManualInputDeliveryHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      /////// 伝票行計算金額まるめ
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtSlipLineCalculationAmountRoundingHidden()).equals("")) {
        // 伝票行計算金額まるめコンボボックスが活性化された場合
        if (formMst0102d02
            .getDdlSlipLineCalculationAmountRounding() != null) {
          formMst0102d02.setDdlSlipLineCalculationAmountRounding(
              formMst0102d02
                  .getDdlSlipLineCalculationAmountRounding());
        }
      } else {
        // 伝票行計算金額まるめコンボボックスが非活性化された場合
        formMst0102d02.setDdlSlipLineCalculationAmountRounding(formMst0102d02
            .getTxtSlipLineCalculationAmountRoundingHidden());
        formMst0102d02.setTxtSlipLineCalculationAmountRoundingHidden(
            formMst0102d02
                .getTxtSlipLineCalculationAmountRoundingHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      /////// 出荷伝票出力品コード
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtDeliveryOutputProductCodeHidden()).equals("")) {
        // 出荷伝票出力品コードコンボボックスが活性化された場合
        if (formMst0102d02.getDdlDeliveryOutputProductCode() != null) {
          formMst0102d02.setDdlDeliveryOutputProductCode(formMst0102d02
              .getDdlDeliveryOutputProductCode());
        }
      } else {
        // 出荷伝票出力品コードコンボボックスが非活性化された場合
        formMst0102d02.setDdlDeliveryOutputProductCode(formMst0102d02
            .getTxtDeliveryOutputProductCodeHidden());
        formMst0102d02.setTxtDeliveryOutputProductCodeHidden(formMst0102d02
            .getTxtDeliveryOutputProductCodeHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      // 請求先名称
      formMst0102d02.setTxtBillingName(formMst0102d02.getTxtBillingName());
      // 請求先名称カナ
      formMst0102d02.setTxtBillingNameKana(formMst0102d02
          .getTxtBillingNameKana());
      // 請求先略称
      formMst0102d02.setTxtBillingNameR(formMst0102d02.getTxtBillingNameR());
      // 請求先郵便番号
      formMst0102d02.setTxtBillingZipCode1(formMst0102d02
          .getTxtBillingZipCode1());
      formMst0102d02.setTxtBillingZipCode2(formMst0102d02
          .getTxtBillingZipCode2());
      // 住所１
      formMst0102d02.setTxtBillingAddress1(formMst0102d02
          .getTxtBillingAddress1());
      // 住所２
      formMst0102d02.setTxtBillingAddress2(formMst0102d02
          .getTxtBillingAddress2());
      // 電話番号
      formMst0102d02.setTxtBillingTel1(formMst0102d02.getTxtBillingTel1());
      formMst0102d02.setTxtBillingTel2(formMst0102d02.getTxtBillingTel2());
      formMst0102d02.setTxtBillingTel3(formMst0102d02.getTxtBillingTel3());
      // FAX番号
      formMst0102d02.setTxtBillingFax1(formMst0102d02.getTxtBillingFax1());
      formMst0102d02.setTxtBillingFax2(formMst0102d02.getTxtBillingFax2());
      formMst0102d02.setTxtBillingFax3(formMst0102d02.getTxtBillingFax3());
      /////// 請求単位
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtBillingUnitHidden()).equals("")) {
        // 請求単位コンボボックスが活性化された場合
        if (formMst0102d02.getDdlBillingUnit() != null) {
          formMst0102d02.setDdlBillingUnit(formMst0102d02.getDdlBillingUnit());
        }
      } else {
        // 請求単位コンボボックスが非活性化された場合
        formMst0102d02.setDdlBillingUnit(formMst0102d02
            .getTxtBillingUnitHidden());
        formMst0102d02.setTxtBillingUnitHidden(formMst0102d02
            .getTxtBillingUnitHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      /////// 請求単価
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtBillingUnitPriceHidden()).equals("")) {
        // 請求単価コンボボックスが活性化された場合
        if (formMst0102d02.getDdlBillingUnitPrice() != null) {
          formMst0102d02.setDdlBillingUnitPrice(formMst0102d02
              .getDdlBillingUnitPrice());
        }
      } else {
        // 請求単価コンボボックスが非活性化された場合
        formMst0102d02.setDdlBillingUnitPrice(formMst0102d02
            .getTxtBillingUnitPriceHidden());
        formMst0102d02.setTxtBillingUnitPriceHidden(formMst0102d02
            .getTxtBillingUnitPriceHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      /////// 請求書種類
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtBillingTypeHidden()).equals("")) {
        // 請求書種類コンボボックスが活性化された場合
        if (formMst0102d02.getDdlBillingType() != null) {
          formMst0102d02.setDdlBillingType(formMst0102d02.getDdlBillingType());
        }
      } else {
        // 請求書種類コンボボックスが非活性化された場合
        formMst0102d02.setDdlBillingType(formMst0102d02
            .getTxtBillingTypeHidden());
        formMst0102d02.setTxtBillingTypeHidden(formMst0102d02
            .getTxtBillingTypeHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      /////// 請求書パターン
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtBillingPatternHidden()).equals("")) {
        // 請求書パターンコンボボックスが活性化された場合
        if (formMst0102d02.getDdlBillingPattern() != null) {
          formMst0102d02.setDdlBillingPattern(formMst0102d02
              .getDdlBillingPattern());
        }
      } else {
        // 請求書パターンコンボボックスが非活性化された場合
        formMst0102d02.setDdlBillingPattern(formMst0102d02
            .getTxtBillingPatternHidden());
        formMst0102d02.setTxtBillingPatternHidden(formMst0102d02
            .getTxtBillingPatternHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      /////// 請求書住所印字
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtBillingAddressPrintHidden()).equals("")) {
        // 請求書住所印字コンボボックスが活性化された場合
        if (formMst0102d02.getDdlBillingAddressPrint() != null) {
          formMst0102d02.setDdlBillingAddressPrint(formMst0102d02
              .getDdlBillingAddressPrint());
        }
      } else {
        // 請求書住所印字コンボボックスが非活性化された場合
        formMst0102d02.setDdlBillingAddressPrint(formMst0102d02
            .getTxtBillingAddressPrintHidden());
        formMst0102d02.setTxtBillingAddressPrintHidden(formMst0102d02
            .getTxtBillingAddressPrintHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      /////// 請求集計表区分
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtBillingShuukeiHyouKubunHidden()).equals("")) {
        // 請求集計表区分コンボボックスが活性化された場合
        if (formMst0102d02.getDdlBillingShuukeiHyouKubun() != null) {
          formMst0102d02.setDdlBillingShuukeiHyouKubun(formMst0102d02
              .getDdlBillingShuukeiHyouKubun());
        }
      } else {
        // 請求集計表区分コンボボックスが非活性化された場合
        formMst0102d02.setDdlBillingShuukeiHyouKubun(formMst0102d02
            .getTxtBillingShuukeiHyouKubunHidden());
        formMst0102d02.setTxtBillingShuukeiHyouKubunHidden(formMst0102d02
            .getTxtBillingShuukeiHyouKubunHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      /////// 消費税計算単位
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtBillingShouhizeiCalculationUnitHidden()).equals("")) {
        // 消費税計算単位コンボボックスが活性化された場合
        if (formMst0102d02.getDdlBillingShouhizeiCalculationUnit() != null) {
          formMst0102d02.setDdlBillingShouhizeiCalculationUnit(formMst0102d02
              .getDdlBillingShouhizeiCalculationUnit());
        }
      } else {
        // 消費税計算単位コンボボックスが非活性化された場合
        formMst0102d02.setDdlBillingShouhizeiCalculationUnit(formMst0102d02
            .getTxtBillingShouhizeiCalculationUnitHidden());
        formMst0102d02.setTxtBillingShouhizeiCalculationUnitHidden(
            formMst0102d02
                .getTxtBillingShouhizeiCalculationUnitHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      /////// 請求チェックリスト 出力対象
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtBillingCheckListOutputTargetHidden()).equals("")) {
        // 請求チェックリスト 出力対象コンボボックスが活性化された場合
        if (formMst0102d02.getDdlBillingCheckListOutputTarget() != null) {
          formMst0102d02.setDdlBillingCheckListOutputTarget(formMst0102d02
              .getDdlBillingCheckListOutputTarget());
        }
      } else {
        // 請求チェックリスト 出力対象コンボボックスが非活性化された場合
        formMst0102d02.setDdlBillingCheckListOutputTarget(formMst0102d02
            .getTxtBillingCheckListOutputTargetHidden());
        formMst0102d02.setTxtBillingCheckListOutputTargetHidden(formMst0102d02
            .getTxtBillingCheckListOutputTargetHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      /////// 消費税端数処理
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtBillingShouhizeiShoriHidden()).equals("")) {
        // 消費税端数処理コンボボックスが活性化された場合
        if (formMst0102d02.getDdlBillingShouhizeiShori() != null) {
          formMst0102d02.setDdlBillingShouhizeiShori(formMst0102d02
              .getDdlBillingShouhizeiShori());
        }
      } else {
        // 消費税端数処理コンボボックスが非活性化された場合
        formMst0102d02.setDdlBillingShouhizeiShori(formMst0102d02
            .getTxtBillingShouhizeiShoriHidden());
        formMst0102d02.setTxtBillingShouhizeiShoriHidden(formMst0102d02
            .getTxtBillingShouhizeiShoriHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      /////// 請求チェックリスト 出力順
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtBillingCheckListOutputOrderHidden()).equals("")) {
        // 請求チェックリスト 出力順コンボボックスが活性化された場合
        if (formMst0102d02.getDdlBillingCheckListOutputOrder() != null) {
          formMst0102d02.setDdlBillingCheckListOutputOrder(formMst0102d02
              .getDdlBillingCheckListOutputOrder());
        }
      } else {
        // 請求チェックリスト 出力順コンボボックスが非活性化された場合
        formMst0102d02.setDdlBillingCheckListOutputOrder(formMst0102d02
            .getTxtBillingCheckListOutputOrderHidden());
        formMst0102d02.setTxtBillingCheckListOutputOrderHidden(formMst0102d02
            .getTxtBillingCheckListOutputOrderHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      /////// 取纏め請求
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtBillingToriMatomeUMuHidden()).equals("")) {
        // 取纏め請求コンボボックスが活性化された場合
        if (formMst0102d02.getDdlBillingToriMatomeUMu() != null) {
          formMst0102d02.setDdlBillingToriMatomeUMu(formMst0102d02
              .getDdlBillingToriMatomeUMu());
        }
      } else {
        // 取纏め請求コンボボックスが非活性化された場合
        formMst0102d02.setDdlBillingToriMatomeUMu(formMst0102d02
            .getTxtBillingToriMatomeUMuHidden());
        formMst0102d02.setTxtBillingToriMatomeUMuHidden(formMst0102d02
            .getTxtBillingToriMatomeUMuHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      /////// 取纏め請求事業所
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtBillingToriMatomeJigyouShoHidden()).equals("")) {
        // 取纏め請求事業所コンボボックスが活性化された場合
        if (formMst0102d02.getDdlBillingToriMatomeJigyouSho() != null) {
          formMst0102d02.setDdlBillingToriMatomeJigyouSho(formMst0102d02
              .getDdlBillingToriMatomeJigyouSho());
        }
      } else {
        // 取纏め請求事業所コンボボックスが非活性化された場合
        formMst0102d02.setDdlBillingToriMatomeJigyouSho(formMst0102d02
            .getTxtBillingToriMatomeJigyouShoHidden());
        formMst0102d02.setTxtBillingToriMatomeJigyouShoHidden(formMst0102d02
            .getTxtBillingToriMatomeJigyouShoHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      // 締日１
      formMst0102d02.setTxtCloseDay1(formMst0102d02.getTxtCloseDay1());
      /////// 回収月区分１
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtRecoveryMonthKubun1Hidden()).equals("")) {
        // 回収月区分１コンボボックスが活性化された場合
        if (formMst0102d02.getDdlRecoveryMonthKubun1() != null) {
          formMst0102d02.setDdlRecoveryMonthKubun1(formMst0102d02
              .getDdlRecoveryMonthKubun1());
        }
      } else {
        // 回収月区分１コンボボックスが非活性化された場合
        formMst0102d02.setDdlRecoveryMonthKubun1(formMst0102d02
            .getTxtRecoveryMonthKubun1Hidden());
        formMst0102d02.setTxtRecoveryMonthKubun1Hidden(formMst0102d02
            .getTxtRecoveryMonthKubun1Hidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      // 回収日１
      formMst0102d02.setTxtRecoveryDay1(formMst0102d02.getTxtRecoveryDay1());
      // 締日２
      formMst0102d02.setTxtCloseDay2(formMst0102d02.getTxtCloseDay2());
      /////// 回収月区分２
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtRecoveryMonthKubun2Hidden()).equals("")) {
        // 回収月区分２コンボボックスが活性化された場合
        if (formMst0102d02.getDdlRecoveryMonthKubun2() != null) {
          formMst0102d02.setDdlRecoveryMonthKubun2(formMst0102d02
              .getDdlRecoveryMonthKubun2());
        }
      } else {
        // 回収月区分２コンボボックスが非活性化された場合
        formMst0102d02.setDdlRecoveryMonthKubun2(formMst0102d02
            .getTxtRecoveryMonthKubun2Hidden());
        formMst0102d02.setTxtRecoveryMonthKubun2Hidden(formMst0102d02
            .getTxtRecoveryMonthKubun2Hidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      // 回収日２
      formMst0102d02.setTxtRecoveryDay2(formMst0102d02.getTxtRecoveryDay2());
      /////// 入金種別
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtPaymentTypeHidden()).equals("")) {
        // 入金種別コンボボックスが活性化された場合
        if (formMst0102d02.getDdlPaymentType() != null) {
          formMst0102d02.setDdlPaymentType(formMst0102d02.getDdlPaymentType());
        }
      } else {
        // 入金種別コンボボックスが非活性化された場合
        formMst0102d02.setDdlPaymentType(formMst0102d02
            .getTxtPaymentTypeHidden());
        formMst0102d02.setTxtPaymentTypeHidden(formMst0102d02
            .getTxtPaymentTypeHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      /////// 入金口座
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtPaymentAccountHidden()).equals("")) {
        // 入金口座コンボボックスが活性化された場合
        if (formMst0102d02.getDdlPaymentAccount() != null) {
          formMst0102d02.setDdlPaymentAccount(formMst0102d02
              .getDdlPaymentAccount());
        }
      } else {
        // 入金口座コンボボックスが非活性化された場合
        formMst0102d02.setDdlPaymentAccount(formMst0102d02
            .getTxtPaymentAccountHidden());
        formMst0102d02.setTxtPaymentAccountHidden(formMst0102d02
            .getTxtPaymentAccountHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      // 手形サイト
      formMst0102d02.setTxtNoteSite(formMst0102d02.getTxtNoteSite());
      // 取引コード
      formMst0102d02.setTxtTorihikiCode(formMst0102d02.getTxtTorihikiCode());
      // 分類コード（定番、店直）
      formMst0102d02.setTxtBunruiCodeTeibanShop(formMst0102d02
          .getTxtBunruiCodeTeibanShop());
      // 伝票区分（定番、店直）
      formMst0102d02.setTxtDenpyouKubunTeibanShop(formMst0102d02
          .getTxtDenpyouKubunTeibanShop());
      // 分類コード（定番、センター）
      formMst0102d02.setTxtBunruiCodeTeibanCenter(formMst0102d02
          .getTxtBunruiCodeTeibanCenter());
      // 伝票区分（定番、センター）
      formMst0102d02.setTxtDenpyouKubunTeibanCenter(formMst0102d02
          .getTxtDenpyouKubunTeibanCenter());
      // 分類コード（特売、店直）
      formMst0102d02.setTxtBunruiCodeTokubaiShop(formMst0102d02
          .getTxtBunruiCodeTokubaiShop());
      // 伝票区分（特売、店直）
      formMst0102d02.setTxtDenpyouKubunTokubaiShop(formMst0102d02
          .getTxtDenpyouKubunTokubaiShop());
      // 分類コード（特売、センター）
      formMst0102d02.setTxtBunruiCodeTokubaiCenter(formMst0102d02
          .getTxtBunruiCodeTokubaiCenter());
      // 伝票区分（特売、センター）
      formMst0102d02.setTxtDenpyouKubunTokubaiCenter(formMst0102d02
          .getTxtDenpyouKubunTokubaiCenter());
      /////// 受領データ突合区分
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtJuryouDataKubunHidden()).equals("")) {
        // 受領データ突合区分コンボボックスが活性化された場合
        if (formMst0102d02.getDdlJuryouDataKubun() != null) {
          formMst0102d02.setDdlJuryouDataKubun(formMst0102d02
              .getDdlJuryouDataKubun());
        }
      } else {
        // 受領データ突合区分コンボボックスが非活性化された場合
        formMst0102d02.setDdlJuryouDataKubun(formMst0102d02
            .getTxtJuryouDataKubunHidden());
        formMst0102d02.setTxtJuryouDataKubunHidden(formMst0102d02
            .getTxtJuryouDataKubunHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      /////// 請求データ区分
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtBillingDataKubunHidden()).equals("")) {
        // 請求データ区分コンボボックスが活性化された場合
        if (formMst0102d02.getDdlBillingDataKubun() != null) {
          formMst0102d02.setDdlBillingDataKubun(formMst0102d02
              .getDdlBillingDataKubun());
        }
      } else {
        // 請求データ区分コンボボックスが非活性化された場合
        formMst0102d02.setDdlBillingDataKubun(formMst0102d02
            .getTxtBillingDataKubunHidden());
        formMst0102d02.setTxtBillingDataKubunHidden(formMst0102d02
            .getTxtBillingDataKubunHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      /////// 修正データ突合区分
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtModifyDataKubunHidden()).equals("")) {
        // 修正データ突合区分コンボボックスが活性化された場合
        if (formMst0102d02.getDdlModifyDataKubun() != null) {
          formMst0102d02.setDdlModifyDataKubun(formMst0102d02
              .getDdlModifyDataKubun());
        }
      } else {
        // 修正データ突合区分コンボボックスが非活性化された場合
        formMst0102d02.setDdlModifyDataKubun(formMst0102d02
            .getTxtModifyDataKubunHidden());
        formMst0102d02.setTxtModifyDataKubunHidden(formMst0102d02
            .getTxtModifyDataKubunHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      /////// 請求先支払い案内データ区分
      if (Util.removeWhitespaces(formMst0102d02
          .getTxtBillingPaymentDataKubunHidden()).equals("")) {
        // 請求先支払い案内データ区分コンボボックスが活性化された場合
        if (formMst0102d02.getDdlBillingPaymentDataKubun() != null) {
          formMst0102d02.setDdlBillingPaymentDataKubun(formMst0102d02
              .getDdlBillingPaymentDataKubun());
        }
      } else {
        // 請求先支払い案内データ区分コンボボックスが非活性化された場合
        formMst0102d02.setDdlBillingPaymentDataKubun(formMst0102d02
            .getTxtBillingPaymentDataKubunHidden());
        formMst0102d02.setTxtBillingPaymentDataKubunHidden(formMst0102d02
            .getTxtBillingPaymentDataKubunHidden());
        model.addAttribute("isDisableCombobox", "true");
      }
      // （修正種別）返品データ
      formMst0102d02.setChkModifyTypeHenpinData(formMst0102d02
          .isChkModifyTypeHenpinData());
      // （修正種別）欠品データ
      formMst0102d02.setChkModifyTypeKetsuhinData(formMst0102d02
          .isChkModifyTypeKetsuhinData());
      // （修正種別）修正データ
      formMst0102d02.setChkModifyTypeShuuseiData(formMst0102d02
          .isChkModifyTypeShuuseiData());
      // 請求データ配信ＩＤ
      formMst0102d02.setTxtBillingDataDeliveryId(formMst0102d02
          .getTxtBillingDataDeliveryId());
      // 集計コード１
      formMst0102d02.setTxtShuukeiCode1(formMst0102d02.getTxtShuukeiCode1());
      // 集計コード２
      formMst0102d02.setTxtShuukeiCode2(formMst0102d02.getTxtShuukeiCode2());
      // 使用中止日
      formMst0102d02.setTxtShiyouChuushiDay(formMst0102d02
          .getTxtShiyouChuushiDay());
      // 状況コード
      formMst0102d02.setTxtStatusCode(formMst0102d02.getTxtStatusCode());
      // コースマスタデータ取得
      if (viewMode.equals(MstConst.TEISEI_MODE)
          || viewMode.equals(MstConst.TORIKESI_MODE)) {
        Map<String, Object> parms2 = new HashMap<String, Object>();
        parms2.put("stsCode", CommonConst.STS_CD_ENTRY);
        parms2.put("delFlg", CommonConst.DEL_FLG_OFF);
        parms2.put("businessDate", businessDate);
        parms2.put("glKb", CommonConst.GEN_KB_DELI_TYPE);
        parms2.put("customerCode", customerData.getCustCode());
        List<MstCourseInformation> courseInformationData = mst0102d02Dao
            .getMst0102d02Mapper().getCourseData(parms2);
        if (courseInformationData != null && !courseInformationData.isEmpty()) {
          for (int j = 0; j < courseInformationData.size(); j++) {
            String deliveryKubun = courseInformationData.get(j)
                .getTxtDeliveryKubun();
            String deliveryKubunName = courseInformationData.get(j)
                .getTxtDeliveryKubunName();
            courseInformationData.get(j).setTxtDeliveryKubunName(deliveryKubun
                + "：" + deliveryKubunName);
          }
          model.addAttribute("arrListCourseInformation", courseInformationData);
        }
      }
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }

    return true;
  }

  /**
   * 請求先情報を取得する.
   * 
   * @param billingCode ： 請求先コード
   * @return returnResult ： MstCustomerオブジェクト
   */
  public MstCustomer getBillingData(String billingCode) {
    // 変数定義
    MstCustomer returnResult = null;
    try {
      // メッセージのParam
      ArrayList<String> paramMess = new ArrayList<String>();

      // データ取得開始
      MstCustomerExample customerExample = new MstCustomerExample();
      MstCustomerExample.Criteria customerCriteria = customerExample
          .createCriteria();
      customerCriteria.andCustCodeEqualTo(Util.removeWhitespaces(billingCode));
      customerCriteria.andStsCodeEqualTo(CommonConst.STS_CD_ENTRY);
      List<MstCustomer> customerDataList = mst0102d02Dao.getMstCustomerMapper()
          .selectByExample(customerExample);
      // データの存在をチェックする
      if (customerDataList != null && !customerDataList.isEmpty()) {
        returnResult = customerDataList.get(0);
      } else {
        paramMess = new ArrayList<String>();
        paramMess.add("得意先マスタ");
        paramMess.add(billingCode);
        returnResult = new MstCustomer();
        returnResult.setBildCode("error");
        returnResult.setCustCode(readPropertiesFileService.getMessage(
            "COM002-W", paramMess));
      }
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }

    return returnResult;
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
  public ArrayList<ObjCombobox> getDeliveryData(String businessDate,
      String jigyoushoCode, String chainCode, String chainEda) {
    // 変数定義
    Map<String, Object> parms = new HashMap<String, Object>();
    List<MstSNohin> nohinData = null;
    ArrayList<ObjCombobox> deliveryCenterResultList = null;
    try {
      // エラーチェック
      if (Util.removeWhitespaces(businessDate).equals("")
          || Util.removeWhitespaces(jigyoushoCode).equals("")
          || Util.removeWhitespaces(chainCode).equals("")
          || Util.removeWhitespaces(chainEda).equals("")) {
        return deliveryCenterResultList;
      }
      // データ取得開始
      parms.put("delFlg", CommonConst.DEL_FLG_OFF);
      parms.put("businessDate", businessDate);
      parms.put("jigyouCd", Short.valueOf(Util
          .removeWhitespaces(jigyoushoCode)));
      parms.put("chainCode", Short.valueOf(Util
          .removeWhitespaces(chainCode)));
      parms.put("chainEda", Short.valueOf(Util
          .removeWhitespaces(chainEda)));
      nohinData = mst0102d02Dao.getMst0102d02Mapper().getDeliveryData(parms);
      // データの存在をチェックする
      if (nohinData == null || nohinData.isEmpty()) {
        return deliveryCenterResultList;
      } else {
        deliveryCenterResultList = new ArrayList<ObjCombobox>();
        // 最初のコンボボックスデータを追加する
        ObjCombobox firstObj = new ObjCombobox();
        firstObj.setCode("");
        firstObj.setName("");
        deliveryCenterResultList.add(firstObj);
        // 残るコンボボックスデータを追加する
        for (int i = 0; i < nohinData.size(); i++) {
          ObjCombobox tempObj = new ObjCombobox();
          tempObj.setCode(String.valueOf(nohinData.get(i).getNhscd()));
          tempObj.setName(nohinData.get(i).getNhscd() + "："
              + nohinData.get(i).getNhsmei());
          deliveryCenterResultList.add(tempObj);
        }
      }
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }

    return deliveryCenterResultList;
  }

  /**
   * 画面のコンボボックスデータを取得する.
   * 
   * @param model ： フォームのModel
   * @param formMst0102d02 ： MST0102d02画面のフォーム
   * @param userCode ： MST0102d01画面からの得意先コード
   * @param viewMode ： MST0102d01画面からの表示モード
   * @param chainCode ： MST0102d01画面からのチェーンコード
   * @param chainEda ： MST0102d01画面からのチェーン枝番
   * @param sysManagerFlag ： システム管理者フラグ
   * @param loginJigyouCd ： ログイン所属事業所コード
   * @param customerData ： 得意先情報格納クラス
   * @return boolean ： 作業が成功（true）／失敗（false）
   */
  private boolean getDataForCombobox(ArrayList<ErrorMessages> lstErrMess,
      Model model, FormMst0102d02 formMst0102d02,
      String userCode, String viewMode, String chainCode, String chainEda,
      String sysManagerFlag, String loginJigyouCd, Mst0102d02CustomerData customerData) {
    try {
      // メッセージのParam
      ArrayList<String> paramMess = new ArrayList<String>();

      // エラーメッセージ変数定義
      ErrorMessages errMess = new ErrorMessages();

      // 共通部品初期化
      final CommonGetSystemCom systemCom = new CommonGetSystemCom(commonDao,
          null, null, readPropertiesFileService);

      //////////// 事業所マスタデータ取得 START ////////////
      Map<String, Object> parms2 = new HashMap<String, Object>();
      parms2.put("businessDate", formMst0102d02.getBusinessDate());
      parms2.put("delFlg", CommonConst.DEL_FLG_OFF);
      // システム管理者フラグ ＝ '1'（システム管理者） の場合、検索条件から除外する
      if (!Util.removeWhitespaces(sysManagerFlag).equals(
          CommonConst.SYS_ADMIN_FLG_VALID)) {
        parms2.put("jigyouCdIncluded", true);
        parms2.put("jigyouCd", Util.removeWhitespaces(loginJigyouCd));
      } else {
        parms2.put("jigyouCdIncluded", false);
      }
      List<MstSJigyo> jigyouData = mst0102d02Dao.getMst0102d02Mapper()
          .getJigyouData(parms2);
      if (jigyouData == null || jigyouData.isEmpty()) {
        // 事業所情報格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("事業所マスタの取得");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM015-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      //ArrayList<ObjCombobox> jigyouShoResultList = new ArrayList<ObjCombobox>();
      final ArrayList<ObjCombobox> jigyouShoResultListWithCode = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      ObjCombobox firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      //jigyouShoResultList.add(firstObj);
      jigyouShoResultListWithCode.add(firstObj);
      // 残るコンボボックスデータを追加する
      /*for (int i = 0; i < jigyouData.size(); i++) {
        // フォーマット：「名称」
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(jigyouData.get(i).getJgycd());
        tempObj.setName(jigyouData.get(i).getJgymei());
        jigyouShoResultList.add(tempObj);
      }*/
      for (int i = 0; i < jigyouData.size(); i++) {
        // フォーマット：「コード：名称」
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(jigyouData.get(i).getJgycd());
        tempObj.setName(jigyouData.get(i).getJgycd() + "："
            + jigyouData.get(i).getJgymei());
        jigyouShoResultListWithCode.add(tempObj);
      }
      // 取得した[変数]事業所情報格納クラスを、取扱事業所へセットする
      model.addAttribute("arrListSubToriatsukaiJigyouSho", jigyouShoResultListWithCode);
      // 取得した[変数]事業所情報格納クラスを、幹事事業所へセットする
      model.addAttribute("arrListKanjiJigyouSho", jigyouShoResultListWithCode);
      // 取得した[変数]事業所情報格納クラスを、取纏め請求事業所へセットする
      model.addAttribute("arrListBillingToriMatomeJigyouSho",
          jigyouShoResultListWithCode);
          //////////// 事業所マスタデータ取得 END ////////////

      //////////// 納品先マスタ情報取得 START ////////////
      Map<String, Object> parms3 = new HashMap<String, Object>();
      List<MstSNohin> nohinData = null;
      // 画面表示モード ≠ 1 の場合、納品先マスタ情報を取得する
      if (!Util.removeWhitespaces(viewMode).equals(MstConst.SHINKI_MODE)) {
        // 事業所コード = "" or チェーンコード = "" or チェーン枝番 = ""の場合、エラーとする
        if (Util.removeWhitespaces(customerData.getManagerJigyoCode())
            .equals("")
            || String.valueOf(customerData.getCainCode()).equals("")
            || String.valueOf(customerData.getCainIdx()).equals("")) {
          // 納品先情報格納クラス ＝ NULL の場合、エラーとする
          paramMess = new ArrayList<String>();
          paramMess.add("納品先マスタの取得");
          errMess.setMessageContent(readPropertiesFileService.getMessage(
              "COM015-E", paramMess));
          lstErrMess.add(errMess);
          model.addAttribute("errorMessages", lstErrMess);
          return false;
        } else {
          parms3.put("delFlg", CommonConst.DEL_FLG_OFF);
          parms3.put("businessDate", formMst0102d02.getBusinessDate());
          parms3.put("jigyouCd", Util.removeWhitespaces(customerData
              .getManagerJigyoCode()));
          parms3.put("chainCode", customerData.getCainCode());
          parms3.put("chainEda", customerData.getCainIdx());
          nohinData = mst0102d02Dao.getMst0102d02Mapper().getDeliveryData(
              parms3);
          if (nohinData == null || nohinData.isEmpty()) {
            // 納品先情報格納クラス ＝ NULL の場合、エラーとする
            paramMess = new ArrayList<String>();
            paramMess.add("納品先マスタの取得");
            errMess.setMessageContent(readPropertiesFileService.getMessage(
                "COM015-E", paramMess));
            lstErrMess.add(errMess);
            model.addAttribute("errorMessages", lstErrMess);
            model.addAttribute("isDisableControl", "true");
            return false;
          }
          final ArrayList<ObjCombobox> deliveryCenterResultList = new ArrayList<ObjCombobox>();
          // 最初のコンボボックスデータを追加する
          firstObj = new ObjCombobox();
          firstObj.setCode("");
          firstObj.setName("");
          deliveryCenterResultList.add(firstObj);
          // 残るコンボボックスデータを追加する
          for (int i = 0; i < nohinData.size(); i++) {
            ObjCombobox tempObj = new ObjCombobox();
            tempObj.setCode(String.valueOf(nohinData.get(i).getNhscd()));
            tempObj.setName(nohinData.get(i).getNhscd() + "："
                + nohinData.get(i).getNhsmei());
            deliveryCenterResultList.add(tempObj);
          }
          // 取得した[変数]納品先情報格納クラスを、納品センターへセットする
          model.addAttribute("arrListDeliveryCenter", deliveryCenterResultList);
        }
      }
      //////////// 納品先マスタ情報取得 END ////////////

      //////////// 勘定科目マスタ取得 START ////////////
      MstSubjectCdExample mstSubjectCdExample = new MstSubjectCdExample();
      MstSubjectCdExample.Criteria mstSubjectCdCriteria = mstSubjectCdExample
          .createCriteria();
      mstSubjectCdCriteria.andSubKbEqualTo(CommonConst.SUBJECT_KB_REL_COM);
      // 画面表示モード ＝ '1' （新規） or 画面表示モード ＝ '3' （訂正）の場合、検索条件から除外する
      if (!Util.removeWhitespaces(viewMode).equals(MstConst.SHINKI_MODE)
          && !Util.removeWhitespaces(viewMode).equals(MstConst.TEISEI_MODE)) {
        String relComSub = Util.removeWhitespaces(customerData.getRelComSub());
        if (!relComSub.equals("")) {
          mstSubjectCdCriteria.andSubCodeEqualTo(relComSub);
        }
      }
      mstSubjectCdExample.setOrderByClause("Sub_Code");
      List<MstSubjectCd> subjectCdData = mst0102d02Dao.getMstSubjectCdMapper()
          .selectByExample(mstSubjectCdExample);
      if (subjectCdData == null || subjectCdData.isEmpty()) {
        // 勘定科目情報格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("勘定科目マスタの取得");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM015-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      final ArrayList<ObjCombobox> hojoKamokuResultList = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      hojoKamokuResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < subjectCdData.size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(subjectCdData.get(i).getSubCode().trim());
        tempObj.setName(subjectCdData.get(i).getSubCode().trim() + "："
            + subjectCdData.get(i).getSubNm());
        hojoKamokuResultList.add(tempObj);
      }
      // 取得した[変数]勘定科目情報格納クラスを、補助科目へセットする
      model.addAttribute("arrListHojoKamoku", hojoKamokuResultList);
      //////////// 勘定科目マスタ取得 END ////////////

      //////////// 手入力出荷伝票情報取得 START ////////////
      Map<String, Object> parms4 = new HashMap<String, Object>();
      parms4.put("glKb", CommonConst.GEN_KB_SLIP_TYPE);
      parms4.put("denKb", CommonConst.LIST_FORM_SLIP_KB_DELIVERY);
      // 画面表示モード ＝ '1' （新規） or 画面表示モード ＝ '3' （訂正）の場合、検索条件から除外する
      if (!Util.removeWhitespaces(viewMode).equals(MstConst.SHINKI_MODE)
          && !Util.removeWhitespaces(viewMode).equals(MstConst.TEISEI_MODE)) {
        parms4.put("listIdIncluded", true);
        parms4.put("listId", Util.removeWhitespaces(customerData
            .getShipsTypId()));
      } else {
        parms4.put("listIdIncluded", false);
      }
      List<Mst0102d02ListForm> listFormShipsTypData = mst0102d02Dao
          .getMst0102d02Mapper().getListFormData(parms4);
      if (listFormShipsTypData == null || listFormShipsTypData.isEmpty()) {
        // 手入力出荷伝票情報格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("帳票定義マスタの取得");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM015-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      final ArrayList<ObjCombobox> manualInputDeliveryResultList = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      manualInputDeliveryResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < listFormShipsTypData.size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(listFormShipsTypData.get(i).getDenCls()
            + MstConst.DELIMITER_ERROR
            + listFormShipsTypData.get(i).getListId());
        tempObj.setName(listFormShipsTypData.get(i).getTargetName() + "（"
            + listFormShipsTypData.get(i).getDenCls() + "）" + "："
            + listFormShipsTypData.get(i).getListName() + "（"
            + listFormShipsTypData.get(i).getListId() + "）");
        manualInputDeliveryResultList.add(tempObj);
      }
      // 取得した[変数]手入力出荷伝票情報格納クラスを、手入力出荷伝票へセットする
      model.addAttribute("arrListManualInputDelivery",
          manualInputDeliveryResultList);
          //////////// 手入力出荷伝票情報取得 END ////////////

      //////////// 請求書種類情報取得 START ////////////
      Map<String, Object> parms5 = new HashMap<String, Object>();
      parms5.put("glKb", CommonConst.GEN_KB_BILD_TYPE);
      parms5.put("denKb", CommonConst.LIST_FORM_SLIP_KB_BILL);
      // 画面表示モード ＝ '1' （新規） or 画面表示モード ＝ '3' （訂正）の場合、検索条件から除外する
      if (!Util.removeWhitespaces(viewMode).equals(MstConst.SHINKI_MODE)
          && !Util.removeWhitespaces(viewMode).equals(MstConst.TEISEI_MODE)) {
        parms5.put("listIdIncluded", true);
        parms5.put("listId", Util.removeWhitespaces(customerData
            .getBildTypId()));
      } else {
        parms5.put("listIdIncluded", false);
      }
      List<Mst0102d02ListForm> listFormBildTypData = mst0102d02Dao
          .getMst0102d02Mapper().getListFormData(parms5);
      if (listFormBildTypData == null || listFormBildTypData.isEmpty()) {
        // 請求書種類情報格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("帳票定義マスタの取得");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM015-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      final ArrayList<ObjCombobox> billingTypeResultList = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      billingTypeResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < listFormBildTypData.size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(listFormBildTypData.get(i).getDenCls()
            + MstConst.DELIMITER_ERROR
            + listFormBildTypData.get(i).getListId());
        tempObj.setName(listFormBildTypData.get(i).getTargetName() + "（"
            + listFormBildTypData.get(i).getDenCls() + "）" + "："
            + listFormBildTypData.get(i).getListName() + "（"
            + listFormBildTypData.get(i).getListId() + "）");
        billingTypeResultList.add(tempObj);
      }
      // 取得した[変数]請求書種類情報格納クラスを、請求書種類へセットする
      model.addAttribute("arrListBillingType", billingTypeResultList);
      //////////// 請求書種類情報取得 END ////////////

      //////////// 共通部品を使って、得意先種別名称を取得する START ////////////
      List<MstGeneralData> generalDataCustomerType = systemCom
          .getMstGeneralConf("Cust_Cls", null);
      if (generalDataCustomerType == null || generalDataCustomerType
          .isEmpty()) {
        // 汎用マスタ格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("汎用マスタ");
        paramMess.add("得意先種別");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      final ArrayList<ObjCombobox> customerTypeResultList = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      customerTypeResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < generalDataCustomerType.size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(generalDataCustomerType.get(i).getGlCode());
        tempObj.setName(generalDataCustomerType.get(i).getGlCode() + "："
            + generalDataCustomerType.get(i).getTarget1());
        customerTypeResultList.add(tempObj);
      }
      // 取得した[変数]得意先種別名称格納クラスを、得意先種別へセットする
      model.addAttribute("arrListCustomerType", customerTypeResultList);
      //////////// 共通部品を使って、得意先種別名称を取得する END ////////////

      //////////// 共通部品を使って、業態区分名称を取得する START ////////////
      List<MstGeneralData> generalDataGyoutaiKubun = systemCom
          .getMstGeneralConf("Gyotai_Kb", null);
      if (generalDataGyoutaiKubun == null || generalDataGyoutaiKubun
          .isEmpty()) {
        // 汎用マスタ格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("汎用マスタ");
        paramMess.add("業態区分");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      final ArrayList<ObjCombobox> gyoutaiKubunResultList = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      gyoutaiKubunResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < generalDataGyoutaiKubun.size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(generalDataGyoutaiKubun.get(i).getGlCode());
        tempObj.setName(generalDataGyoutaiKubun.get(i).getGlCode() + "："
            + generalDataGyoutaiKubun.get(i).getTarget1());
        gyoutaiKubunResultList.add(tempObj);
      }
      // 取得した[変数]業態区分名称格納クラスを、業態区分へセットする
      model.addAttribute("arrListGyoutaiKubun", gyoutaiKubunResultList);
      //////////// 共通部品を使って、業態区分名称を取得する END ////////////

      //////////// 共通部品を使って、関係会社種別名称を取得する START ////////////
      List<MstGeneralData> generalDataKankeiKaishaType = systemCom
          .getMstGeneralConf("Rel_Com_Cls", null);
      if (generalDataKankeiKaishaType == null || generalDataKankeiKaishaType
          .isEmpty()) {
        // 汎用マスタ格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("汎用マスタ");
        paramMess.add("関係会社種別");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      final ArrayList<ObjCombobox> kankeiKaishaTypeResultList = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      kankeiKaishaTypeResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < generalDataKankeiKaishaType.size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(generalDataKankeiKaishaType.get(i).getGlCode());
        tempObj.setName(generalDataKankeiKaishaType.get(i).getGlCode() + "："
            + generalDataKankeiKaishaType.get(i).getTarget1());
        kankeiKaishaTypeResultList.add(tempObj);
      }
      // 取得した[変数]関係会社種別名称格納クラスを、関係会社種別へセットする
      model.addAttribute("arrListKankeiKaishaType", kankeiKaishaTypeResultList);
      //////////// 共通部品を使って、関係会社種別名称を取得する END ////////////

      //////////// 共通部品を使って、採番区分名称を取得する START ////////////
      List<MstGeneralData> generalDataSaibanKubun = systemCom
          .getMstGeneralConf("DatIdx_Kb", null);
      if (generalDataSaibanKubun == null || generalDataSaibanKubun.isEmpty()) {
        // 汎用マスタ格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("汎用マスタ");
        paramMess.add("採番区分");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      final ArrayList<ObjCombobox> saibanKubunResultList = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      saibanKubunResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < generalDataSaibanKubun.size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(generalDataSaibanKubun.get(i).getGlCode());
        tempObj.setName(generalDataSaibanKubun.get(i).getGlCode() + "："
            + generalDataSaibanKubun.get(i).getTarget1());
        saibanKubunResultList.add(tempObj);
      }
      // 取得した[変数]採番区分名称格納クラスを、採番区分へセットする
      model.addAttribute("arrListSaibanKubun", saibanKubunResultList);
      //////////// 共通部品を使って、採番区分名称を取得する END ////////////

      //////////// 共通部品を使って、店舗区分名称を取得する START ////////////
      List<MstGeneralData> generalDataTenpoKubun = systemCom
          .getMstGeneralConf("Shop_Kb", null);
      if (generalDataTenpoKubun == null || generalDataTenpoKubun.isEmpty()) {
        // 汎用マスタ格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("汎用マスタ");
        paramMess.add("店舗区分");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      final ArrayList<ObjCombobox> tenpoKubunResultList = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      tenpoKubunResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < generalDataTenpoKubun.size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(generalDataTenpoKubun.get(i).getGlCode());
        tempObj.setName(generalDataTenpoKubun.get(i).getGlCode() + "："
            + generalDataTenpoKubun.get(i).getTarget1());
        tenpoKubunResultList.add(tempObj);
      }
      // 取得した[変数]店舗区分別名称格納クラスを、店舗区分へセットする
      model.addAttribute("arrListTenpoKubun", tenpoKubunResultList);
      //////////// 共通部品を使って、店舗区分名称を取得する END ////////////

      //////////// 共通部品を使って、YG取引区分名称を取得する START ////////////
      List<MstGeneralData> generalDataYgTorihikiKubun = systemCom
          .getMstGeneralConf("YG_Kb", null);
      if (generalDataYgTorihikiKubun == null || generalDataYgTorihikiKubun
          .isEmpty()) {
        // 汎用マスタ格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("汎用マスタ");
        paramMess.add("ＹＧ取引区分");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      final ArrayList<ObjCombobox> ygTorihikiKubunResultList = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      ygTorihikiKubunResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < generalDataYgTorihikiKubun.size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(generalDataYgTorihikiKubun.get(i).getGlCode());
        tempObj.setName(generalDataYgTorihikiKubun.get(i).getGlCode() + "："
            + generalDataYgTorihikiKubun.get(i).getTarget1());
        ygTorihikiKubunResultList.add(tempObj);
      }
      // 取得した[変数]YG取引区分名称格納クラスを、YG取引区分へセットする
      model.addAttribute("arrListYGTorihikiKubun", ygTorihikiKubunResultList);
      //////////// 共通部品を使って、YG取引区分名称を取得する END ////////////

      //////////// 共通部品を使って、内税顧客区分名称を取得する START ////////////
      List<MstGeneralData> generalDataUchiZeiKoKyakuKubun = systemCom
          .getMstGeneralConf("Tax_Inc_Kb", null);
      if (generalDataUchiZeiKoKyakuKubun == null
          || generalDataUchiZeiKoKyakuKubun.isEmpty()) {
        // 汎用マスタ格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("汎用マスタ");
        paramMess.add("内税顧客区分");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      final ArrayList<ObjCombobox> uchiZeiKoKyakuKubunResultList = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      uchiZeiKoKyakuKubunResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < generalDataUchiZeiKoKyakuKubun.size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(generalDataUchiZeiKoKyakuKubun.get(i).getGlCode());
        tempObj.setName(generalDataUchiZeiKoKyakuKubun.get(i).getGlCode() + "："
            + generalDataUchiZeiKoKyakuKubun.get(i).getTarget1());
        uchiZeiKoKyakuKubunResultList.add(tempObj);
      }
      // 取得した[変数]内税顧客区分名称格納クラスを、内税顧客区分へセットする
      model.addAttribute("arrListUchiZeiKoKyakuKubun",
          uchiZeiKoKyakuKubunResultList);
          //////////// 共通部品を使って、内税顧客区分名称を取得する END ////////////

      //////////// 共通部品を使って、内税消費税端数処理名称を取得する START ////////////
      List<MstGeneralData> generalDataUchiZeiShori = systemCom
          .getMstGeneralConf("Tax_Inc_Frc_Kb", null);
      if (generalDataUchiZeiShori == null || generalDataUchiZeiShori
          .isEmpty()) {
        // 汎用マスタ格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("汎用マスタ");
        paramMess.add("内税消費税端数処理");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      final ArrayList<ObjCombobox> uchiZeiShoriResultList = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      uchiZeiShoriResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < generalDataUchiZeiShori.size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(generalDataUchiZeiShori.get(i).getGlCode());
        tempObj.setName(generalDataUchiZeiShori.get(i).getGlCode() + "："
            + generalDataUchiZeiShori.get(i).getTarget1());
        uchiZeiShoriResultList.add(tempObj);
      }
      // 取得した[変数]内税消費税端数処理名称格納クラスを、内税消費税端数処理へセットする
      model.addAttribute("arrListUchiZeiShori", uchiZeiShoriResultList);
      //////////// 共通部品を使って、内税消費税端数処理名称を取得する END ////////////

      //////////// 共通部品を使って、集金有無名称を取得する START ////////////
      List<MstGeneralData> generalDataShuukinUmu = systemCom
          .getMstGeneralConf("Col_Mon_Kb", null);
      if (generalDataShuukinUmu == null || generalDataShuukinUmu
          .isEmpty()) {
        // 汎用マスタ格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("汎用マスタ");
        paramMess.add("集金有無");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      final ArrayList<ObjCombobox> shuukinUmuResultList = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      shuukinUmuResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < generalDataShuukinUmu.size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(generalDataShuukinUmu.get(i).getGlCode());
        tempObj.setName(generalDataShuukinUmu.get(i).getGlCode() + "："
            + generalDataShuukinUmu.get(i).getTarget1());
        shuukinUmuResultList.add(tempObj);
      }
      // 取得した[変数]集金有無名称格納クラスを、集金有無へセットする
      model.addAttribute("arrListShuukinUmu", shuukinUmuResultList);
      //////////// 共通部品を使って、集金有無名称を取得する END ////////////

      //////////// 共通部品を使って、現金集金マーク印字名称を取得する START ////////////
      List<MstGeneralData> generalDataGenkinShuukinMark = systemCom
          .getMstGeneralConf("Col_Mmrk_Kb", null);
      if (generalDataGenkinShuukinMark == null || generalDataGenkinShuukinMark
          .isEmpty()) {
        // 汎用マスタ格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("汎用マスタ");
        paramMess.add("現金集金マーク印字");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      final ArrayList<ObjCombobox> genkinShuukinMarkResultList = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      genkinShuukinMarkResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < generalDataGenkinShuukinMark.size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(generalDataGenkinShuukinMark.get(i).getGlCode());
        tempObj.setName(generalDataGenkinShuukinMark.get(i).getGlCode() + "："
            + generalDataGenkinShuukinMark.get(i).getTarget1());
        genkinShuukinMarkResultList.add(tempObj);
      }
      // 取得した[変数]現金集金マーク印字名称格納クラスを、現金集金マーク印字へセットする
      model.addAttribute("arrListGenkinShuukinMark",
          genkinShuukinMarkResultList);
          //////////// 共通部品を使って、現金集金マーク印字名称を取得する END ////////////

      //////////// 共通部品を使って、集計出力FLG名称を取得する START ////////////
      List<MstGeneralData> generalDataShuukinOutputFlag = systemCom
          .getMstGeneralConf("Sums_Flg", null);
      if (generalDataShuukinOutputFlag == null || generalDataShuukinOutputFlag
          .isEmpty()) {
        // 汎用マスタ格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("汎用マスタ");
        paramMess.add("集計出力FLG");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      final ArrayList<ObjCombobox> shuukinOutputFlagResultList = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      shuukinOutputFlagResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < generalDataShuukinOutputFlag.size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(generalDataShuukinOutputFlag.get(i).getGlCode());
        tempObj.setName(generalDataShuukinOutputFlag.get(i).getGlCode() + "："
            + generalDataShuukinOutputFlag.get(i).getTarget1());
        shuukinOutputFlagResultList.add(tempObj);
      }
      // 取得した[変数]集金出力FLG名称格納クラスを、集計出力FLGへセットする
      model.addAttribute("arrListShuukinOutputFlag",
          shuukinOutputFlagResultList);
          //////////// 共通部品を使って、集計出力FLG名称を取得する END ////////////

      //////////// 共通部品を使って、手入力伝票発行名称を取得する START ////////////
      List<MstGeneralData> generalDataManualInputBilling = systemCom
          .getMstGeneralConf("Ships_Kb", null);
      if (generalDataManualInputBilling == null || generalDataManualInputBilling
          .isEmpty()) {
        // 汎用マスタ格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("汎用マスタ");
        paramMess.add("手入力伝票発行");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      final ArrayList<ObjCombobox> manualInputBillingResultList = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      manualInputBillingResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < generalDataManualInputBilling.size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(generalDataManualInputBilling.get(i).getGlCode());
        tempObj.setName(generalDataManualInputBilling.get(i).getGlCode() + "："
            + generalDataManualInputBilling.get(i).getTarget1());
        manualInputBillingResultList.add(tempObj);
      }
      // 取得した[変数]手入力伝票発行名称格納クラスを、手入力伝票発行へセットする
      model.addAttribute("arrListManualInputBilling",
          manualInputBillingResultList);
          //////////// 共通部品を使って、手入力伝票発行名称を取得する END ////////////

      //////////// 共通部品を使って、伝票行計算金額まるめ名称を取得する START ////////////
      List<MstGeneralData> generalDataSlipLineCalculationAmountRounding = systemCom
          .getMstGeneralConf("Ships_rud_Kb", null);
      if (generalDataSlipLineCalculationAmountRounding == null
          || generalDataSlipLineCalculationAmountRounding.isEmpty()) {
        // 汎用マスタ格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("汎用マスタ");
        paramMess.add("伝票行計算金額まるめ");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      final ArrayList<ObjCombobox> slipLineCalculationAmountRoundingResultList
          = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      slipLineCalculationAmountRoundingResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < generalDataSlipLineCalculationAmountRounding
          .size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(generalDataSlipLineCalculationAmountRounding.get(i)
            .getGlCode());
        tempObj.setName(generalDataSlipLineCalculationAmountRounding.get(i)
            .getGlCode() + "："
            + generalDataSlipLineCalculationAmountRounding.get(i)
                .getTarget1());
        slipLineCalculationAmountRoundingResultList.add(tempObj);
      }
      // 取得した[変数]伝票行計算金額まるめ名称格納クラスを、伝票行計算金額まるめへセットする
      model.addAttribute("arrListSlipLineCalculationAmountRounding",
          slipLineCalculationAmountRoundingResultList);
          //////////// 共通部品を使って、伝票行計算金額まるめ名称を取得する END ////////////

      //////////// 共通部品を使って、出荷伝票出力品コード名称を取得する START ////////////
      List<MstGeneralData> generalDataDeliveryOutputProductCode = systemCom
          .getMstGeneralConf("Ships_Code_Kb", null);
      if (generalDataDeliveryOutputProductCode == null
          || generalDataDeliveryOutputProductCode.isEmpty()) {
        // 汎用マスタ格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("汎用マスタ");
        paramMess.add("出荷伝票出力品コード");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      final ArrayList<ObjCombobox> deliveryOutputProductCodeResultList
          = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      deliveryOutputProductCodeResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < generalDataDeliveryOutputProductCode.size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(generalDataDeliveryOutputProductCode.get(i)
            .getGlCode());
        tempObj.setName(generalDataDeliveryOutputProductCode.get(i)
            .getGlCode() + "："
            + generalDataDeliveryOutputProductCode.get(i).getTarget1());
        deliveryOutputProductCodeResultList.add(tempObj);
      }
      // 取得した[変数]出力伝票出力品コード名称格納クラスを、出荷伝票出力品コードへセットする
      model.addAttribute("arrListDeliveryOutputProductCode",
          deliveryOutputProductCodeResultList);
          //////////// 共通部品を使って、出荷伝票出力品コード名称を取得する END ////////////

      //////////// 共通部品を使って、請求単位名称を取得する START ////////////
      List<MstGeneralData> generalDataBillingUnit = systemCom
          .getMstGeneralConf("Bild_Unt_Kb", null);
      if (generalDataBillingUnit == null || generalDataBillingUnit
          .isEmpty()) {
        // 汎用マスタ格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("汎用マスタ");
        paramMess.add("請求単位");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      final ArrayList<ObjCombobox> billingUnitResultList = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      billingUnitResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < generalDataBillingUnit.size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(generalDataBillingUnit.get(i).getGlCode());
        tempObj.setName(generalDataBillingUnit.get(i).getGlCode() + "："
            + generalDataBillingUnit.get(i).getTarget1());
        billingUnitResultList.add(tempObj);
      }
      // 取得した[変数]請求単位名称格納クラスを、請求単位へセットする
      model.addAttribute("arrListBillingUnit", billingUnitResultList);
      //////////// 共通部品を使って、請求単位名称を取得する END ////////////

      //////////// 共通部品を使って、請求書単価名称を取得する START ////////////
      List<MstGeneralData> generalDataBillingUnitPrice = systemCom
          .getMstGeneralConf("Bild_Tanka", null);
      if (generalDataBillingUnitPrice == null || generalDataBillingUnitPrice
          .isEmpty()) {
        // 汎用マスタ格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("汎用マスタ");
        paramMess.add("請求書単価");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      final ArrayList<ObjCombobox> billingUnitPriceResultList = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      billingUnitPriceResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < generalDataBillingUnitPrice.size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(generalDataBillingUnitPrice.get(i).getGlCode());
        tempObj.setName(generalDataBillingUnitPrice.get(i).getGlCode() + "："
            + generalDataBillingUnitPrice.get(i).getTarget1());
        billingUnitPriceResultList.add(tempObj);
      }
      // 取得した[変数]請求書単価名称格納クラスを、請求書単価へセットする
      model.addAttribute("arrListBillingUnitPrice", billingUnitPriceResultList);
      //////////// 共通部品を使って、請求書単価名称を取得する END ////////////

      //////////// 共通部品を使って、取締め請求名称を取得する START ////////////
      List<MstGeneralData> generalDataBillingToriMatomeUMu = systemCom
          .getMstGeneralConf("Sum_Bild_Kb", null);
      if (generalDataBillingToriMatomeUMu == null
          || generalDataBillingToriMatomeUMu.isEmpty()) {
        // 汎用マスタ格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("汎用マスタ");
        paramMess.add("取締め請求");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      final ArrayList<ObjCombobox> billingToriMatomeUMuResultList = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      billingToriMatomeUMuResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < generalDataBillingToriMatomeUMu.size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(generalDataBillingToriMatomeUMu.get(i).getGlCode());
        tempObj.setName(generalDataBillingToriMatomeUMu.get(i).getGlCode() + "："
            + generalDataBillingToriMatomeUMu.get(i).getTarget1());
        billingToriMatomeUMuResultList.add(tempObj);
      }
      // 取得した[変数]取纏め請求名称格納クラスを、取纏め請求へセットする
      model.addAttribute("arrListBillingToriMatomeUMu",
          billingToriMatomeUMuResultList);
          //////////// 共通部品を使って、取締め請求名称を取得する END ////////////

      //////////// 共通部品を使って、請求書パターン名称を取得する START ////////////
      List<MstGeneralData> generalDataBillingPattern = systemCom
          .getMstGeneralConf("Bild_Ptn", null);
      if (generalDataBillingPattern == null || generalDataBillingPattern
          .isEmpty()) {
        // 汎用マスタ格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("汎用マスタ");
        paramMess.add("請求書パターン");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      final ArrayList<ObjCombobox> billingPatternResultList = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      billingPatternResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < generalDataBillingPattern.size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(generalDataBillingPattern.get(i).getGlCode());
        tempObj.setName(generalDataBillingPattern.get(i).getGlCode() + "："
            + generalDataBillingPattern.get(i).getTarget1());
        billingPatternResultList.add(tempObj);
      }
      // 取得した[変数]請求書パターン名称格納クラスを、請求書パターンへセットする
      model.addAttribute("arrListBillingPattern", billingPatternResultList);
      //////////// 共通部品を使って、請求書パターン名称を取得する END ////////////

      //////////// 共通部品を使って、請求書住所印字名称を取得する START ////////////
      List<MstGeneralData> generalDataBillingAddressPrint = systemCom
          .getMstGeneralConf("Bild_Adr_Out_Kb", null);
      if (generalDataBillingAddressPrint == null
          || generalDataBillingAddressPrint.isEmpty()) {
        // 汎用マスタ格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("汎用マスタ");
        paramMess.add("請求書住所印字");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      final ArrayList<ObjCombobox> billingAddressPrintResultList = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      billingAddressPrintResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < generalDataBillingAddressPrint.size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(generalDataBillingAddressPrint.get(i).getGlCode());
        tempObj.setName(generalDataBillingAddressPrint.get(i).getGlCode() + "："
            + generalDataBillingAddressPrint.get(i).getTarget1());
        billingAddressPrintResultList.add(tempObj);
      }
      // 取得した[変数]請求書住所印字名称格納クラスを、請求書住所印字へセットする
      model.addAttribute("arrListBillingAddressPrint",
          billingAddressPrintResultList);
          //////////// 共通部品を使って、請求書住所印字名称を取得する END ////////////

      //////////// 共通部品を使って、請求集計表区分名称を取得する START ////////////
      List<MstGeneralData> generalDataBillingShuukeiHyouKubun = systemCom
          .getMstGeneralConf("Bild_Sum_Kb", null);
      if (generalDataBillingShuukeiHyouKubun == null
          || generalDataBillingShuukeiHyouKubun.isEmpty()) {
        // 汎用マスタ格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("汎用マスタ");
        paramMess.add("請求書住所印字");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      final ArrayList<ObjCombobox> billingShuukeiHyouKubunResultList = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      billingShuukeiHyouKubunResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < generalDataBillingShuukeiHyouKubun.size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(generalDataBillingShuukeiHyouKubun.get(i).getGlCode());
        tempObj.setName(generalDataBillingShuukeiHyouKubun.get(i).getGlCode()
            + "：" + generalDataBillingShuukeiHyouKubun.get(i).getTarget1());
        billingShuukeiHyouKubunResultList.add(tempObj);
      }
      // 取得した[変数]請求集計表区分名称格納クラスを、請求集計表区分へセットする
      model.addAttribute("arrListBillingShuukeiHyouKubun",
          billingShuukeiHyouKubunResultList);
          //////////// 共通部品を使って、請求集計表区分名称を取得する END ////////////

      //////////// 共通部品を使って、消費税計算単位名称を取得する START ////////////
      List<MstGeneralData> generalDataBillingShouhizeiCalculationUnit = systemCom
          .getMstGeneralConf("Tax_Unt_Kb", null);
      if (generalDataBillingShouhizeiCalculationUnit == null
          || generalDataBillingShouhizeiCalculationUnit.isEmpty()) {
        // 汎用マスタ格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("汎用マスタ");
        paramMess.add("消費税計算単位");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      final ArrayList<ObjCombobox> billingShouhizeiCalculationUnitResultList
          = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      billingShouhizeiCalculationUnitResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < generalDataBillingShouhizeiCalculationUnit
          .size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(generalDataBillingShouhizeiCalculationUnit.get(i)
            .getGlCode());
        tempObj.setName(generalDataBillingShouhizeiCalculationUnit.get(i)
            .getGlCode() + "："
            + generalDataBillingShouhizeiCalculationUnit.get(i)
                .getTarget1());
        billingShouhizeiCalculationUnitResultList.add(tempObj);
      }
      // 取得した[変数]消費税計算単位名称格納クラスを、消費税計算単位へセットする
      model.addAttribute("arrListBillingShouhizeiCalculationUnit",
          billingShouhizeiCalculationUnitResultList);
          //////////// 共通部品を使って、消費税計算単位名称を取得する END ////////////

      //////////// 共通部品を使って、消費税端数処理名称を取得する START ////////////
      List<MstGeneralData> generalDataBillingShouhizeiShori = systemCom
          .getMstGeneralConf("Tax_Frc_Kb", null);
      if (generalDataBillingShouhizeiShori == null
          || generalDataBillingShouhizeiShori.isEmpty()) {
        // 汎用マスタ格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("汎用マスタ");
        paramMess.add("消費税端数処理");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      final ArrayList<ObjCombobox> billingShouhizeiShoriResultList = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      billingShouhizeiShoriResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < generalDataBillingShouhizeiShori.size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(generalDataBillingShouhizeiShori.get(i).getGlCode());
        tempObj.setName(generalDataBillingShouhizeiShori.get(i).getGlCode()
            + "：" + generalDataBillingShouhizeiShori.get(i).getTarget1());
        billingShouhizeiShoriResultList.add(tempObj);
      }
      // 取得した[変数]消費税端数処理名称格納クラスを、消費税端数処理へセットする
      model.addAttribute("arrListBillingShouhizeiShori",
          billingShouhizeiShoriResultList);
          //////////// 共通部品を使って、消費税端数処理名称を取得する END ////////////

      //////////// 共通部品を使って、請求チェックリスト_出力対象名称を取得する START ////////////
      List<MstGeneralData> generalDataBillingCheckListOutputTarget = systemCom
          .getMstGeneralConf("Bild_Chk_Kb", null);
      if (generalDataBillingCheckListOutputTarget == null
          || generalDataBillingCheckListOutputTarget.isEmpty()) {
        // 汎用マスタ格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("汎用マスタ");
        paramMess.add("請求チェックリスト_出力対象");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      final ArrayList<ObjCombobox> billingCheckListOutputTargetResultList
          = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      billingCheckListOutputTargetResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < generalDataBillingCheckListOutputTarget.size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(generalDataBillingCheckListOutputTarget.get(i)
            .getGlCode());
        tempObj.setName(generalDataBillingCheckListOutputTarget.get(i)
            .getGlCode() + "：" + generalDataBillingCheckListOutputTarget
                .get(i).getTarget1());
        billingCheckListOutputTargetResultList.add(tempObj);
      }
      // 取得した[変数]請求チェックリスト_出力対象名称格納クラスを、請求チェックリスト 出力対象へセットする
      model.addAttribute("arrListBillingCheckListOutputTarget",
          billingCheckListOutputTargetResultList);
          //////////// 共通部品を使って、請求チェックリスト_出力対象名称を取得する END ////////////

      //////////// 共通部品を使って、請求チェックリスト_出力順名称を取得する START ////////////
      List<MstGeneralData> generalDataBillingCheckListOutputOrder = systemCom
          .getMstGeneralConf("Bild_Chk_Srt", null);
      if (generalDataBillingCheckListOutputOrder == null
          || generalDataBillingCheckListOutputOrder.isEmpty()) {
        // 汎用マスタ格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("汎用マスタ");
        paramMess.add("請求チェックリスト_出力順");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      final ArrayList<ObjCombobox> billingCheckListOutputOrderResultList
          = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      billingCheckListOutputOrderResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < generalDataBillingCheckListOutputOrder.size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(generalDataBillingCheckListOutputOrder.get(i)
            .getGlCode());
        tempObj.setName(generalDataBillingCheckListOutputOrder.get(i)
            .getGlCode() + "：" + generalDataBillingCheckListOutputOrder
                .get(i).getTarget1());
        billingCheckListOutputOrderResultList.add(tempObj);
      }
      // 取得した[変数]請求チェックリスト_出力順名称格納クラスを、請求チェックリスト 出力順へセットする
      model.addAttribute("arrListBillingCheckListOutputOrder",
          billingCheckListOutputOrderResultList);
          //////////// 共通部品を使って、請求チェックリスト_出力順名称を取得する END ////////////

      //////////// 共通部品を使って、回収月区分１名称を取得する START ////////////
      List<MstGeneralData> generalDataRecoveryMonthKubun1 = systemCom
          .getMstGeneralConf("Col_Term_Kb_1", null);
      if (generalDataRecoveryMonthKubun1 == null
          || generalDataRecoveryMonthKubun1.isEmpty()) {
        // 汎用マスタ格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("汎用マスタ");
        paramMess.add("回収月区分１");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      final ArrayList<ObjCombobox> recoveryMonthKubun1ResultList = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      recoveryMonthKubun1ResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < generalDataRecoveryMonthKubun1.size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(generalDataRecoveryMonthKubun1.get(i).getGlCode());
        tempObj.setName(generalDataRecoveryMonthKubun1.get(i).getGlCode() + "："
            + generalDataRecoveryMonthKubun1.get(i).getTarget1());
        recoveryMonthKubun1ResultList.add(tempObj);
      }
      // 取得した[変数]回収月区分１名称格納クラスを、回収月区分１へセットする
      model.addAttribute("arrListRecoveryMonthKubun1",
          recoveryMonthKubun1ResultList);
          //////////// 共通部品を使って、回収月区分１名称を取得する END ////////////

      //////////// 共通部品を使って、回収月区分２名称を取得する START ////////////
      List<MstGeneralData> generalDataRecoveryMonthKubun2 = systemCom
          .getMstGeneralConf("Col_Term_Kb_2", null);
      if (generalDataRecoveryMonthKubun2 == null
          || generalDataRecoveryMonthKubun2.isEmpty()) {
        // 汎用マスタ格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("汎用マスタ");
        paramMess.add("回収月区分２");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      final ArrayList<ObjCombobox> recoveryMonthKubun2ResultList = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      recoveryMonthKubun2ResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < generalDataRecoveryMonthKubun2.size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(generalDataRecoveryMonthKubun2.get(i).getGlCode());
        tempObj.setName(generalDataRecoveryMonthKubun2.get(i).getGlCode() + "："
            + generalDataRecoveryMonthKubun2.get(i).getTarget1());
        recoveryMonthKubun2ResultList.add(tempObj);
      }
      // 取得した[変数]回収月区分２名称格納クラスを、回収月区分２へセットする
      model.addAttribute("arrListRecoveryMonthKubun2",
          recoveryMonthKubun2ResultList);
          //////////// 共通部品を使って、回収月区分２名称を取得する END ////////////

      //////////// 共通部品を使って、入金種別名称を取得する START ////////////
      List<MstGeneralData> generalDataPaymentType = systemCom
          .getMstGeneralConf("Rcvm_Cls", null);
      if (generalDataPaymentType == null || generalDataPaymentType
          .isEmpty()) {
        // 汎用マスタ格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("汎用マスタ");
        paramMess.add("入金種別");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      final ArrayList<ObjCombobox> paymentTypeResultList = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      paymentTypeResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < generalDataPaymentType.size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(generalDataPaymentType.get(i).getGlCode());
        tempObj.setName(generalDataPaymentType.get(i).getGlCode() + "："
            + generalDataPaymentType.get(i).getTarget1());
        paymentTypeResultList.add(tempObj);
      }
      // 取得した[変数]入金種別名称格納クラスを、入金種別へセットする
      model.addAttribute("arrListPaymentType", paymentTypeResultList);
      //////////// 共通部品を使って、入金種別名称を取得する END ////////////

      //////////// 共通部品を使って、入金口座名称を取得する START ////////////
      List<MstGeneralData> generalDataPaymentAccount = systemCom
          .getMstGeneralConf("Rcvm_Acc", null);
      if (generalDataPaymentAccount == null || generalDataPaymentAccount
          .isEmpty()) {
        // 汎用マスタ格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("汎用マスタ");
        paramMess.add("入金口座");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      final ArrayList<ObjCombobox> paymentAccountResultList = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      paymentAccountResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < generalDataPaymentAccount.size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(generalDataPaymentAccount.get(i).getGlCode());
        tempObj.setName(generalDataPaymentAccount.get(i).getGlCode() + "："
            + generalDataPaymentAccount.get(i).getTarget1());
        paymentAccountResultList.add(tempObj);
      }
      // 取得した[変数]入金口座名称格納クラスを、入金口座へセットする
      model.addAttribute("arrListPaymentAccount", paymentAccountResultList);
      //////////// 共通部品を使って、入金口座名称を取得する END ////////////

      //////////// 共通部品を使って、受領データ突合区分名称を取得する START ////////////
      List<MstGeneralData> generalDataJuryouDataKubun = systemCom
          .getMstGeneralConf("Rcv_Dat_Kb", null);
      if (generalDataJuryouDataKubun == null || generalDataJuryouDataKubun
          .isEmpty()) {
        // 汎用マスタ格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("汎用マスタ");
        paramMess.add("受領データ突合区分");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      final ArrayList<ObjCombobox> juryouDataKubunResultList = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      juryouDataKubunResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < generalDataJuryouDataKubun.size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(generalDataJuryouDataKubun.get(i).getGlCode());
        tempObj.setName(generalDataJuryouDataKubun.get(i).getGlCode() + "："
            + generalDataJuryouDataKubun.get(i).getTarget1());
        juryouDataKubunResultList.add(tempObj);
      }
      // 取得した[変数]受領データ突合区別名称格納クラスを、受領データ突合区分へセットする
      model.addAttribute("arrListJuryouDataKubun", juryouDataKubunResultList);
      //////////// 共通部品を使って、受領データ突合区分名称を取得する END ////////////

      //////////// 共通部品を使って、請求データ区分名称を取得する START ////////////
      List<MstGeneralData> generalDataBillingDataKubun = systemCom
          .getMstGeneralConf("Bild_Dat_Kb", null);
      if (generalDataBillingDataKubun == null || generalDataBillingDataKubun
          .isEmpty()) {
        // 汎用マスタ格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("汎用マスタ");
        paramMess.add("請求データ区分");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      final ArrayList<ObjCombobox> billingDataKubunResultList = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      billingDataKubunResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < generalDataBillingDataKubun.size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(generalDataBillingDataKubun.get(i).getGlCode());
        tempObj.setName(generalDataBillingDataKubun.get(i).getGlCode() + "："
            + generalDataBillingDataKubun.get(i).getTarget1());
        billingDataKubunResultList.add(tempObj);
      }
      // 取得した[変数]請求データ区分名称格納クラスを、請求データ区分へセットする
      model.addAttribute("arrListBillingDataKubun", billingDataKubunResultList);
      //////////// 共通部品を使って、請求データ区分名称を取得する END ////////////

      //////////// 共通部品を使って、修正データ突合区分名称を取得する START ////////////
      List<MstGeneralData> generalDataModifyDataKubun = systemCom
          .getMstGeneralConf("Mod_Dat_Kb", null);
      if (generalDataModifyDataKubun == null || generalDataModifyDataKubun
          .isEmpty()) {
        // 汎用マスタ格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("汎用マスタ");
        paramMess.add("修正データ突合区分");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      final ArrayList<ObjCombobox> modifyDataKubunResultList = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      modifyDataKubunResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < generalDataModifyDataKubun.size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(generalDataModifyDataKubun.get(i).getGlCode());
        tempObj.setName(generalDataModifyDataKubun.get(i).getGlCode() + "："
            + generalDataModifyDataKubun.get(i).getTarget1());
        modifyDataKubunResultList.add(tempObj);
      }
      // 取得した[変数]修正データ突合区別名称格納クラスを、修正データ突合区分へセットする
      model.addAttribute("arrListModifyDataKubun", modifyDataKubunResultList);
      //////////// 共通部品を使って、修正データ突合区分名称を取得する END ////////////

      //////////// 共通部品を使って、請求先支払データ区分名称を取得する START ////////////
      List<MstGeneralData> generalDataBillingPaymentDataKubun = systemCom
          .getMstGeneralConf("Pay_Dat_Kb", null);
      if (generalDataBillingPaymentDataKubun == null
          || generalDataBillingPaymentDataKubun.isEmpty()) {
        // 汎用マスタ格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("汎用マスタ");
        paramMess.add("請求先支払データ区分");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        model.addAttribute("isDisableControl", "true");
        return false;
      }
      final ArrayList<ObjCombobox> billingPaymentDataKubunResultList = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      billingPaymentDataKubunResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < generalDataBillingPaymentDataKubun.size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(generalDataBillingPaymentDataKubun.get(i).getGlCode());
        tempObj.setName(generalDataBillingPaymentDataKubun.get(i).getGlCode()
            + "：" + generalDataBillingPaymentDataKubun.get(i).getTarget1());
        billingPaymentDataKubunResultList.add(tempObj);
      }
      // 取得した[変数]請求先支払い案内名称格納クラスを、請求先支払い案内データ区分へセットする
      model.addAttribute("arrListBillingPaymentDataKubun",
          billingPaymentDataKubunResultList);
      //////////// 共通部品を使って、請求先支払データ区分名称を取得する END ////////////
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }

    return true;
  }

  /**
   * 全項目入力チェック処理.
   * 
   * @param formMst0102d02 ： 画面Mst0102d02のフォーム
   * @param model ： フォームのModel
   * @return boolean ： true(エラー)／false(普通)
   */
  public boolean checkInput(FormMst0102d02 formMst0102d02, Model model) {
    String strError;
    String strErrorId = "";

    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();

    // エラーメッセージ変数定義
    ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    ErrorMessages errMess = new ErrorMessages();

    // 得意先コード
    strError = checkItem(Util.removeWhitespaces(formMst0102d02
        .getTxtCustomerCode()), true, InputCheckCom.TYPE_NUM, 7);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("得意先コード");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtCustomerCode" + MstConst.DELIMITER_ERROR;
    }
    // チェーンコード
    strError = checkItem(Util.removeWhitespaces(formMst0102d02
        .getTxtChainCode()), true, InputCheckCom.TYPE_NUM, 4);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("チェーンコード");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtChainCode" + MstConst.DELIMITER_ERROR;
    }
    // チェーン枝番
    strError = checkItem(Util.removeWhitespaces(formMst0102d02
        .getTxtChainEda()), true, InputCheckCom.TYPE_NUM, 2);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("チェーン枝番");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtChainEda" + MstConst.DELIMITER_ERROR;
    }
    // 得意先名称
    strError = checkItem(Util.removeWhitespaces(formMst0102d02
        .getTxtCustomerName()), true, InputCheckCom.TYPE_EM, 20);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("得意先名称");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtCustomerName" + MstConst.DELIMITER_ERROR;
    }
    // 得意先名称カナ
    strError = checkItem(Util.removeWhitespaces(formMst0102d02
        .getTxtCustomerNameKana()), false, InputCheckCom.TYPE_EM, 30);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("得意先名称カナ");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtCustomerNameKana" + MstConst.DELIMITER_ERROR;
    }
    // 得意先略称
    strError = checkItem(Util.removeWhitespaces(formMst0102d02
        .getTxtCustomerNameR()), true, InputCheckCom.TYPE_EM, 10);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("得意先略称");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtCustomerNameR" + MstConst.DELIMITER_ERROR;
    }
    // 郵便番号
    strError = checkItem(Util.removeWhitespaces(formMst0102d02
        .getTxtPostalCodeResult()), true, InputCheckCom.TYPE_NUM, 9);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("郵便番号");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtPostalCode1" + MstConst.DELIMITER_ERROR
          + "txtPostalCode2" + MstConst.DELIMITER_ERROR;
    }
    // 住所１
    strError = checkItem(Util.removeWhitespaces(formMst0102d02
        .getTxtAddress1()), true, InputCheckCom.TYPE_EM, 20);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("住所１");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtAddress1" + MstConst.DELIMITER_ERROR;
    }
    // 住所２
    strError = checkItem(Util.removeWhitespaces(formMst0102d02
        .getTxtAddress2()), false, InputCheckCom.TYPE_EM, 20);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("住所２");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtAddress2" + MstConst.DELIMITER_ERROR;
    }
    // 電話番号
    strError = checkItem(Util.removeWhitespaces(formMst0102d02
        .getTxtTelResult()), false, InputCheckCom.TYPE_NUM, 18);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("電話番号");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtTel1" + MstConst.DELIMITER_ERROR + "txtTel2"
          + MstConst.DELIMITER_ERROR + "txtTel3" + MstConst.DELIMITER_ERROR;
    }
    // FAX番号
    strError = checkItem(Util.removeWhitespaces(formMst0102d02
        .getTxtFaxResult()), false, InputCheckCom.TYPE_NUM, 18);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("FAX番号");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtFax1" + MstConst.DELIMITER_ERROR + "txtFax2"
          + MstConst.DELIMITER_ERROR + "txtFax3" + MstConst.DELIMITER_ERROR;
    }
    // 得意先担当者
    strError = checkItem(Util.removeWhitespaces(formMst0102d02
        .getTxtCustomerTantousha()), false, InputCheckCom.TYPE_EM, 20);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("得意先担当者");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtCustomerTantousha" + MstConst.DELIMITER_ERROR;
    }
    // メールアドレス
    strError = checkItem(Util.removeWhitespaces(formMst0102d02
        .getTxtMailAddress()), false, InputCheckCom.TYPE_NUM_ALPHA_SIGN, 50);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("メールアドレス");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtMailAddress" + MstConst.DELIMITER_ERROR;
    }
    // 取扱事業所
    ArrayList<MstToriatsukaiJigyouSho> toriatsukaiData = formMst0102d02
        .getArrListToriatsukaiJigyouSho();
    int toriatsukaiIndex = 0;
    for (MstToriatsukaiJigyouSho item : toriatsukaiData) {
      toriatsukaiIndex++;
      // 取扱事業所
      strError = checkItem(item.getDdlToriatsukaiJigyouSho(), true,
          null, null);
      if (strError != null) {
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("取扱事業所");
        errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
            paramMess));
        lstErrMess.add(errMess);
        strErrorId += "arrListToriatsukaiJigyouSho" + (toriatsukaiIndex)
            + MstConst.DELIMITER_ERROR;
      }
      // 営業担当者コード
      strError = checkItem(Util.removeWhitespaces(item
          .getTxtEigyouTantoushaCode()), true, InputCheckCom.TYPE_NUM, 8);
      if (strError != null) {
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("営業担当者コード");
        errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
            paramMess));
        lstErrMess.add(errMess);
        strErrorId += "arrListToriatsukaiEigyou" + (toriatsukaiIndex)
            + MstConst.DELIMITER_ERROR;
      }
      // 事務担当者コード
      strError = checkItem(Util.removeWhitespaces(item
          .getTxtJimuTantoushaCode()), true, InputCheckCom.TYPE_NUM, 8);
      if (strError != null) {
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("事務担当者コード");
        errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
            paramMess));
        lstErrMess.add(errMess);
        strErrorId += "arrListToriatsukaiJimu" + (toriatsukaiIndex)
            + MstConst.DELIMITER_ERROR;
      }
    }
    // 幹事事業所
    strError = checkItem(formMst0102d02.getDdlKanjiJigyouSho(), true,
        null, null);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("幹事事業所");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlKanjiJigyouSho" + MstConst.DELIMITER_ERROR;
    }
    // 得意先種別
    strError = checkItem(formMst0102d02.getDdlCustomerType(), true,
        null, null);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("得意先種別");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlCustomerType" + MstConst.DELIMITER_ERROR;
    }
    // 店舗区分
    strError = checkItem(formMst0102d02.getDdlTenpoKubun(), true,
        null, null);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("店舗区分");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlTenpoKubun" + MstConst.DELIMITER_ERROR;
    }
    // 業態区分
    strError = checkItem(formMst0102d02.getDdlGyoutaiKubun(), true,
        null, null);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("業態区分");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlGyoutaiKubun" + MstConst.DELIMITER_ERROR;
    }
    // 関係会社種別
    strError = checkItem(formMst0102d02.getDdlKankeiKaishaType(), true,
        null, null);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("関係会社種別");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlKankeiKaishaType" + MstConst.DELIMITER_ERROR;
    }
    // 採番区分
    strError = checkItem(formMst0102d02.getDdlSaibanKubun(), true,
        null, null);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("採番区分");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlSaibanKubun" + MstConst.DELIMITER_ERROR;
    }
    // YG取引区分
    strError = checkItem(formMst0102d02.getDdlYgTorihikiKubun(), true,
        null, null);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("YG取引区分");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlYgTorihikiKubun" + MstConst.DELIMITER_ERROR;
    }
    // 内税顧客区分
    strError = checkItem(formMst0102d02.getDdlUchiZeiKoKyakuKubun(), true,
        null, null);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("内税顧客区分");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlUchiZeiKoKyakuKubun" + MstConst.DELIMITER_ERROR;
    }
    // 集金有無
    strError = checkItem(formMst0102d02.getDdlShuukinUmu(), true,
        null, null);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("集金有無");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlShuukinUmu" + MstConst.DELIMITER_ERROR;
    }
    // 現金集金マーク印字
    strError = checkItem(formMst0102d02.getDdlGenkinShuukinMark(), true,
        null, null);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("現金集金マーク印字");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlGenkinShuukinMark" + MstConst.DELIMITER_ERROR;
    }
    // 集計出力FLG
    strError = checkItem(formMst0102d02.getDdlShuukinOutputFlag(), true,
        null, null);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("集計出力FLG");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlShuukinOutputFlag" + MstConst.DELIMITER_ERROR;
    }
    // 手入力伝票発行
    strError = checkItem(formMst0102d02.getDdlManualInputBilling(), true,
        null, null);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("手入力伝票発行");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlManualInputBilling" + MstConst.DELIMITER_ERROR;
    }
    // 手入力出荷伝票
    strError = checkItem(formMst0102d02.getDdlManualInputDelivery(), true,
        null, null);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("手入力出荷伝票");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlManualInputDelivery" + MstConst.DELIMITER_ERROR;
    }
    // 伝票行計算金額まるめ
    strError = checkItem(formMst0102d02
        .getDdlSlipLineCalculationAmountRounding(), true,
        null, null);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("伝票行計算金額まるめ");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlSlipLineCalculationAmountRounding"
          + MstConst.DELIMITER_ERROR;
    }
    // 出荷伝票出力品コード
    strError = checkItem(formMst0102d02.getDdlDeliveryOutputProductCode(), true,
        null, null);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("出荷伝票出力品コード");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlDeliveryOutputProductCode" + MstConst.DELIMITER_ERROR;
    }
    // 請求先情報
    boolean customerFlg = formMst0102d02.isChkCustomer();
    boolean billingFlg = formMst0102d02.isChkBilling();
    if ((customerFlg && billingFlg) || (!customerFlg && billingFlg)) {
      // 請求先名称
      strError = checkItem(Util.removeWhitespaces(formMst0102d02
          .getTxtBillingName()), true, InputCheckCom.TYPE_EM, 20);
      if (strError != null) {
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("請求先名称");
        errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
            paramMess));
        lstErrMess.add(errMess);
        strErrorId += "txtBillingName" + MstConst.DELIMITER_ERROR;
      }
      // 請求先名称カナ
      strError = checkItem(Util.removeWhitespaces(formMst0102d02
          .getTxtBillingName()), false, InputCheckCom.TYPE_EM, 30);
      if (strError != null) {
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("請求先名称カナ");
        errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
            paramMess));
        lstErrMess.add(errMess);
        strErrorId += "txtBillingNameKana" + MstConst.DELIMITER_ERROR;
      }
      // 請求先略称
      strError = checkItem(Util.removeWhitespaces(formMst0102d02
          .getTxtBillingNameR()), true, InputCheckCom.TYPE_EM, 10);
      if (strError != null) {
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("請求先略称");
        errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
            paramMess));
        lstErrMess.add(errMess);
        strErrorId += "txtBillingNameR" + MstConst.DELIMITER_ERROR;
      }
      // 請求先郵便番号
      strError = checkItem(Util.removeWhitespaces(formMst0102d02
          .getTxtBillingZipCodeResult()), true, InputCheckCom.TYPE_NUM, 9);
      if (strError != null) {
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("請求先郵便番号");
        errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
            paramMess));
        lstErrMess.add(errMess);
        strErrorId += "txtBillingZipCode1" + MstConst.DELIMITER_ERROR
            + "txtBillingZipCode2" + MstConst.DELIMITER_ERROR;
      }
      // 住所１
      strError = checkItem(Util.removeWhitespaces(formMst0102d02
          .getTxtBillingAddress1()), true, InputCheckCom.TYPE_EM, 20);
      if (strError != null) {
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("住所１");
        errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
            paramMess));
        lstErrMess.add(errMess);
        strErrorId += "txtBillingAddress1" + MstConst.DELIMITER_ERROR;
      }
      // 住所２
      strError = checkItem(Util.removeWhitespaces(formMst0102d02
          .getTxtBillingAddress2()), false, InputCheckCom.TYPE_EM, 20);
      if (strError != null) {
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("住所２");
        errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
            paramMess));
        lstErrMess.add(errMess);
        strErrorId += "txtBillingAddress2" + MstConst.DELIMITER_ERROR;
      }
      // 請求先電話番号
      strError = checkItem(Util.removeWhitespaces(formMst0102d02
          .getTxtBillingTelResult()), false, InputCheckCom.TYPE_NUM, 18);
      if (strError != null) {
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("請求先電話番号");
        errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
            paramMess));
        lstErrMess.add(errMess);
        strErrorId += "txtBillingTel1" + MstConst.DELIMITER_ERROR
            + "txtBillingTel2"
            + MstConst.DELIMITER_ERROR + "txtBillingTel3"
            + MstConst.DELIMITER_ERROR;
      }
      // 請求先FAX番号
      strError = checkItem(Util.removeWhitespaces(formMst0102d02
          .getTxtBillingFaxResult()), false, InputCheckCom.TYPE_NUM, 18);
      if (strError != null) {
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("請求先FAX番号");
        errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
            paramMess));
        lstErrMess.add(errMess);
        strErrorId += "txtBillingFax1" + MstConst.DELIMITER_ERROR
            + "txtBillingFax2"
            + MstConst.DELIMITER_ERROR + "txtBillingFax3"
            + MstConst.DELIMITER_ERROR;
      }
    }
    // 請求単位
    strError = checkItem(formMst0102d02.getDdlBillingUnit(), true,
        null, null);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("請求単位");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlBillingUnit" + MstConst.DELIMITER_ERROR;
    }
    // 請求書単価
    strError = checkItem(formMst0102d02.getDdlBillingUnitPrice(), true,
        null, null);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("請求書単価");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlBillingUnitPrice" + MstConst.DELIMITER_ERROR;
    }
    // 請求書種類
    strError = checkItem(formMst0102d02.getDdlBillingType(), true,
        null, null);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("請求書種類");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlBillingType" + MstConst.DELIMITER_ERROR;
    }
    // 取纏め請求有無
    strError = checkItem(formMst0102d02.getDdlBillingToriMatomeUMu(), true,
        null, null);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("取纏め請求有無");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlBillingToriMatomeUMu" + MstConst.DELIMITER_ERROR;
    }
    // 請求書住所印字
    strError = checkItem(formMst0102d02.getDdlBillingAddressPrint(), true,
        null, null);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("請求書住所印字");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlBillingAddressPrint" + MstConst.DELIMITER_ERROR;
    }
    // 請求集計表区分
    strError = checkItem(formMst0102d02.getDdlBillingShuukeiHyouKubun(), true,
        null, null);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("請求集計表区分");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlBillingShuukeiHyouKubun" + MstConst.DELIMITER_ERROR;
    }
    // 消費税計算単位
    strError = checkItem(formMst0102d02.getDdlBillingShouhizeiCalculationUnit(),
        true, null, null);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("消費税計算単位");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlBillingShouhizeiCalculationUnit"
          + MstConst.DELIMITER_ERROR;
    }
    // 請求チェックリスト 出力対象
    strError = checkItem(formMst0102d02.getDdlBillingCheckListOutputTarget(),
        true, null, null);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("請求チェックリスト 出力対象");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlBillingCheckListOutputTarget"
          + MstConst.DELIMITER_ERROR;
    }
    // 消費税端数処理
    strError = checkItem(formMst0102d02.getDdlBillingShouhizeiShori(), true,
        null, null);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("消費税端数処理");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlBillingShouhizeiShori" + MstConst.DELIMITER_ERROR;
    }
    // 締日１
    strError = checkItem(Util.removeWhitespaces(formMst0102d02
        .getTxtCloseDay1()), false, InputCheckCom.TYPE_NUM, 2);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("締日１");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtCloseDay1" + MstConst.DELIMITER_ERROR;
    }
    // 回収月区分１
    strError = checkItem(formMst0102d02.getDdlRecoveryMonthKubun1(), true,
        null, null);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("回収月区分１");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlRecoveryMonthKubun1" + MstConst.DELIMITER_ERROR;
    }
    // 回収日１
    strError = checkItem(Util.removeWhitespaces(formMst0102d02
        .getTxtRecoveryDay1()), true, InputCheckCom.TYPE_NUM, 2);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("回収日１");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtRecoveryDay1" + MstConst.DELIMITER_ERROR;
    }
    // 締日２
    strError = checkItem(Util.removeWhitespaces(formMst0102d02
        .getTxtCloseDay2()), false, InputCheckCom.TYPE_NUM, 2);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("締日２");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtCloseDay2" + MstConst.DELIMITER_ERROR;
    }
    // 回収月区分２
    strError = checkItem(formMst0102d02.getDdlRecoveryMonthKubun2(), false,
        null, null);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("回収月区分２");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlRecoveryMonthKubun2" + MstConst.DELIMITER_ERROR;
    }
    // 回収日２
    strError = checkItem(Util.removeWhitespaces(formMst0102d02
        .getTxtRecoveryDay2()), false, InputCheckCom.TYPE_NUM, 2);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("回収日２");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtRecoveryDay2" + MstConst.DELIMITER_ERROR;
    }
    // 入金種別
    strError = checkItem(formMst0102d02.getDdlPaymentType(), true,
        null, null);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("入金種別");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlPaymentType" + MstConst.DELIMITER_ERROR;
    }
    // 手形サイト
    strError = checkItem(Util.removeWhitespaces(formMst0102d02
        .getTxtNoteSite()), false, InputCheckCom.TYPE_NUM, 3);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("手形サイト");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtNoteSite" + MstConst.DELIMITER_ERROR;
    }
    // 取引コード
    strError = checkItem(Util.removeWhitespaces(formMst0102d02
        .getTxtTorihikiCode()), false, InputCheckCom.TYPE_NUM, 6);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("取引コード");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtTorihikiCode" + MstConst.DELIMITER_ERROR;
    }
    // 分類コード（定番、店直）
    strError = checkItem(Util.removeWhitespaces(formMst0102d02
        .getTxtBunruiCodeTeibanShop()), false, InputCheckCom.TYPE_NUM, 6);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("分類コード（定番、店直）");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtBunruiCodeTeibanShop" + MstConst.DELIMITER_ERROR;
    }
    // 伝票区分（定番、店直）
    strError = checkItem(Util.removeWhitespaces(formMst0102d02
        .getTxtDenpyouKubunTeibanShop()), false, InputCheckCom.TYPE_NUM, 6);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("伝票区分（定番、店直）");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtDenpyouKubunTeibanShop" + MstConst.DELIMITER_ERROR;
    }
    // 分類コード（定番、センター）
    strError = checkItem(Util.removeWhitespaces(formMst0102d02
        .getTxtBunruiCodeTeibanCenter()), false, InputCheckCom.TYPE_NUM, 6);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("分類コード（定番、センター）");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtBunruiCodeTeibanCenter" + MstConst.DELIMITER_ERROR;
    }
    // 伝票区分（定番、センター）
    strError = checkItem(Util.removeWhitespaces(formMst0102d02
        .getTxtDenpyouKubunTeibanCenter()), false, InputCheckCom.TYPE_NUM, 6);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("伝票区分（定番、センター）");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtDenpyouKubunTeibanCenter" + MstConst.DELIMITER_ERROR;
    }
    // 分類コード（特売、店直）
    strError = checkItem(Util.removeWhitespaces(formMst0102d02
        .getTxtBunruiCodeTokubaiShop()), false, InputCheckCom.TYPE_NUM, 6);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("分類コード（特売、店直）");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtBunruiCodeTokubaiShop" + MstConst.DELIMITER_ERROR;
    }
    // 伝票区分（特売、店直）
    strError = checkItem(Util.removeWhitespaces(formMst0102d02
        .getTxtDenpyouKubunTokubaiShop()), false, InputCheckCom.TYPE_NUM, 6);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("伝票区分（特売、店直）");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtDenpyouKubunTokubaiShop" + MstConst.DELIMITER_ERROR;
    }
    // 分類コード（特売、センター）
    strError = checkItem(Util.removeWhitespaces(formMst0102d02
        .getTxtBunruiCodeTokubaiCenter()), false, InputCheckCom.TYPE_NUM, 6);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("分類コード（特売、センター）");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtBunruiCodeTokubaiCenter" + MstConst.DELIMITER_ERROR;
    }
    // 伝票区分（特売、センター）
    strError = checkItem(Util.removeWhitespaces(formMst0102d02
        .getTxtDenpyouKubunTokubaiCenter()), false, InputCheckCom.TYPE_NUM, 6);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("伝票区分（特売、センター）");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtDenpyouKubunTokubaiCenter" + MstConst.DELIMITER_ERROR;
    }
    // 受領データ突合区分
    strError = checkItem(formMst0102d02.getDdlJuryouDataKubun(), true,
        null, null);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("受領データ突合区分");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlJuryouDataKubun" + MstConst.DELIMITER_ERROR;
    }
    // 請求データ区分
    strError = checkItem(formMst0102d02.getDdlBillingDataKubun(), true,
        null, null);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("請求データ区分");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlBillingDataKubun" + MstConst.DELIMITER_ERROR;
    }
    // 修正データ突合区分
    strError = checkItem(formMst0102d02.getDdlModifyDataKubun(), true,
        null, null);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("修正データ突合区分");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlModifyDataKubun" + MstConst.DELIMITER_ERROR;
    }
    // 請求先支払いデータ区分
    strError = checkItem(formMst0102d02.getDdlBillingPaymentDataKubun(), true,
        null, null);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("請求先支払いデータ区分");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlBillingPaymentDataKubun" + MstConst.DELIMITER_ERROR;
    }
    // 集計コード１
    strError = checkItem(Util.removeWhitespaces(formMst0102d02
        .getTxtShuukeiCode1()), false, InputCheckCom.TYPE_NUM_ALPHA, 6);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("集計コード１");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtShuukeiCode1" + MstConst.DELIMITER_ERROR;
    }
    // 集計コード２
    strError = checkItem(Util.removeWhitespaces(formMst0102d02
        .getTxtShuukeiCode2()), false, InputCheckCom.TYPE_NUM_ALPHA, 6);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("集計コード２");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtShuukeiCode2" + MstConst.DELIMITER_ERROR;
    }
    // 使用中止日
    strError = InputCheckCom.chkDate(Util.removeWhitespaces(formMst0102d02
        .getTxtShiyouChuushiDay()).replace("/", ""),
        CommonConst.DATE_FORMAT_YMD);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("使用中止日");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtShiyouChuushiDay" + MstConst.DELIMITER_ERROR;
    }
    // 状況コード
    strError = checkItem(Util.removeWhitespaces(formMst0102d02
        .getTxtStatusCode()), true, InputCheckCom.TYPE_NUM, 1);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("状況コード");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtStatusCode" + MstConst.DELIMITER_ERROR;
    }

    // 結果を戻す
    if (lstErrMess.isEmpty()) {
      return false;
    } else {
      model.addAttribute("errorMessages", lstErrMess);
      model.addAttribute("lstErrorID", strErrorId);
      return true;
    }
  }

  /**
   * 固有入力チェック.
   * 
   * @param formMst0102d02 ： 画面Mst0102d02のフォーム
   * @param model ： フォームのModel
   * @return boolean ： true(エラー)／false(普通)
   */
  public boolean checkSpecificInput(FormMst0102d02 formMst0102d02,
      Model model) {
    String strErrorId = "";

    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();

    // エラーメッセージ変数定義
    ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    ErrorMessages errMess = new ErrorMessages();

    /** 利用情報. */
    boolean customerFlg = formMst0102d02.isChkCustomer();
    boolean billingFlg = formMst0102d02.isChkBilling();
    String billingCode = Util.removeWhitespaces(formMst0102d02
        .getTxtBillingCode());
    // 得意先フラグ ＝ チェックあり and 請求先フラグ ＝ チェックなし and 請求先コード ＝ NULL の場合、エラーとする
    if (customerFlg && !billingFlg) {
      if (billingCode.equals("")) {
        errMess = new ErrorMessages();
        errMess.setMessageContent(readPropertiesFileService.getMessage("MST003-E",
            null));
        lstErrMess.add(errMess);
        strErrorId += "txtBillingCode" + MstConst.DELIMITER_ERROR;
      } else {
        String strError = checkItem(Util.removeWhitespaces(formMst0102d02
            .getTxtBillingCode()), false, InputCheckCom.TYPE_NUM, 7);
        if (strError != null) {
          errMess = new ErrorMessages();
          paramMess = new ArrayList<String>();
          paramMess.add("請求先コード");
          errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
              paramMess));
          lstErrMess.add(errMess);
          strErrorId += "txtBillingCode" + MstConst.DELIMITER_ERROR;
        }
      }
    }
    // 得意先フラグ ＝ チェックなし and 請求先フラグ ＝ チェックなし and 請求先コード ≠ NULL の場合、エラーとする
    if (!customerFlg && !billingFlg) {
      if (!billingCode.equals("")) {
        errMess = new ErrorMessages();
        errMess.setMessageContent(readPropertiesFileService.getMessage("MST004-E",
            null));
        lstErrMess.add(errMess);
      } else {
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("利用情報");
        errMess.setMessageContent(readPropertiesFileService.getMessage("COM006-E",
            paramMess));
        lstErrMess.add(errMess);
      }
    }

    /** 取扱事業所. */
    ArrayList<MstToriatsukaiJigyouSho> toriatsukaiArray = formMst0102d02
        .getArrListToriatsukaiJigyouSho();
    // 取扱事業所一覧.行数 ＝ 0 の場合、エラーとする
    if (toriatsukaiArray.isEmpty()) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("取扱事業所");
      errMess.setMessageContent(readPropertiesFileService.getMessage("COM006-E",
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += MstConst.DELIMITER_ERROR;
    }
    // 取扱事業所一覧.取扱事業所が複数行定義されている場合、エラーとする
    Set<Integer> duplicateIndexArray = new HashSet<Integer>();
    ArrayList<String> jigyoushoArray = new ArrayList<String>();
    for (int i = 0; i < toriatsukaiArray.size(); i++) {
      jigyoushoArray.add(toriatsukaiArray.get(i).getDdlToriatsukaiJigyouSho());
    }
    for (int j = 0; j < jigyoushoArray.size(); j++) {
      String currentItem = Util.removeWhitespaces(jigyoushoArray.get(j));
      for (int k = j + 1; k < jigyoushoArray.size(); k++) {
        String currentInnerItem = Util.removeWhitespaces(jigyoushoArray.get(k));
        if (currentItem.equals(currentInnerItem)) {
          duplicateIndexArray.add(j);
          duplicateIndexArray.add(k);
        }
      }
    }
    if (!duplicateIndexArray.isEmpty()) {
      errMess = new ErrorMessages();
      errMess.setMessageContent(readPropertiesFileService.getMessage("MST007-E",
          null));
      lstErrMess.add(errMess);
      for (Integer index : duplicateIndexArray) {
        strErrorId += "arrListToriatsukaiJigyouSho" + (index + 1)
            + MstConst.DELIMITER_ERROR;
      }
    }

    /** 幹事事業所. */
    String kanjiJigyousho = Util.removeWhitespaces(formMst0102d02
        .getDdlKanjiJigyouSho());
    // 幹事事業所が取扱事業所一覧.取扱事業所に存在しない場合、エラーとする
    if (!kanjiJigyousho.equals("")
        && !jigyoushoArray.contains(kanjiJigyousho)) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("幹事事業所");
      errMess.setMessageContent(readPropertiesFileService.getMessage("MST009-E",
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlKanjiJigyouSho" + MstConst.DELIMITER_ERROR;
    }

    /** 会社関係種別. */
    // 会社関係種別 ＝ '関係会社' and 補助科目 ＝ NULL の場合、エラーとする
    String kankeiKaishaType = Util.removeWhitespaces(formMst0102d02
        .getDdlKankeiKaishaType());
    String hojoKamoku = "";
    if (formMst0102d02.getDdlHojoKamoku() != null) {
      hojoKamoku = Util.removeWhitespaces(formMst0102d02
          .getDdlHojoKamoku());
    }
    if (kankeiKaishaType.equals(MstConst.COMPANY_TYPE_RELATED)
        && hojoKamoku.equals("")) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("会社関係種別");
      paramMess.add("'関係会社'");
      paramMess.add("補助科目");
      errMess.setMessageContent(readPropertiesFileService.getMessage("MST010-E",
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlHojoKamoku" + MstConst.DELIMITER_ERROR;
    }

    /** 納品センター. */
    // 店舗区分 ＝ '店無し' and 納品センター ＝ NULL の場合、エラーとする
    String tenpoKubun = Util.removeWhitespaces(formMst0102d02
        .getDdlTenpoKubun());
    String deliveryCenter = "";
    if (formMst0102d02.getDdlDeliveryCenter() != null) {
      deliveryCenter = Util.removeWhitespaces(formMst0102d02
          .getDdlDeliveryCenter());
    }
    if (tenpoKubun.equals(MstConst.SHOP_KUBUN_NO_SHOP)
        && deliveryCenter.equals("")) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("店舗区分");
      paramMess.add("'店無し'");
      paramMess.add("納品センター");
      errMess.setMessageContent(readPropertiesFileService.getMessage("MST010-E",
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlDeliveryCenter" + MstConst.DELIMITER_ERROR;
    }

    /** 内税顧客区分. */
    // 内税顧客区分 ＝ '内税' and 内税消費税端数処理 ＝ NULL の場合、エラーとする
    String uchizeiKokyakuKubun = Util.removeWhitespaces(formMst0102d02
        .getDdlUchiZeiKoKyakuKubun());
    String uchizeiShori = "";
    if (formMst0102d02.getDdlUchiZeiShori() != null) {
      uchizeiShori = Util.removeWhitespaces(formMst0102d02
          .getDdlUchiZeiShori());
    }
    if (uchizeiKokyakuKubun.equals(MstConst.TAX_CUSTOMER_KUBUN_INCLUDED)
        && uchizeiShori.equals("")) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("内税顧客区分");
      paramMess.add("'内税'");
      paramMess.add("内税消費税端数処理");
      errMess.setMessageContent(readPropertiesFileService.getMessage("MST010-E",
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlUchiZeiShori" + MstConst.DELIMITER_ERROR;
    }

    /** 取纏め請求有無. */
    // 取纏め請求有無 ＝ 'する' and 取纏め請求事業所 ＝ NULL の場合、エラーとする
    String billingToriMatomeUMu = Util.removeWhitespaces(formMst0102d02
        .getDdlBillingToriMatomeUMu());
    String billingToriMatomeJigyousho = "";
    if (formMst0102d02.getDdlBillingToriMatomeJigyouSho() != null) {
      billingToriMatomeJigyousho = Util.removeWhitespaces(formMst0102d02
          .getDdlBillingToriMatomeJigyouSho());
    }
    if (billingToriMatomeUMu.equals(MstConst.SUM_BILL_KUBUN_ON)
        && billingToriMatomeJigyousho.equals("")) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("取纏め請求有無");
      paramMess.add("'する'");
      paramMess.add("取纏め請求事業所");
      errMess.setMessageContent(readPropertiesFileService.getMessage("MST010-E",
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlBillingToriMatomeJigyouSho" + MstConst.DELIMITER_ERROR;
    }
    // 取纏め請求事業所が取扱事業所一覧.取扱事業所に存在しない場合、エラーとする
    if (!billingToriMatomeJigyousho.equals("")
        && !jigyoushoArray.contains(billingToriMatomeJigyousho)) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("取纏め請求事業所");
      errMess.setMessageContent(readPropertiesFileService.getMessage("MST009-E",
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlBillingToriMatomeJigyouSho" + MstConst.DELIMITER_ERROR;
    }

    /** 請求書種類. */
    // 請求書種類 ＝ '自社請求書' and 請求書パターン ＝ NULL の場合、エラーとする
    String billingType = Util.removeWhitespaces(formMst0102d02
        .getDdlBillingType().split(MstConst.DELIMITER_ERROR)[0]);
    String billingPattern = "";
    if (formMst0102d02.getDdlBillingPattern() != null) {
      billingPattern = Util.removeWhitespaces(formMst0102d02
          .getDdlBillingPattern());
    }
    if (billingType.equals(MstConst.BILLING_TYPE_JISHA)
        && billingPattern.equals("")) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("請求書種類");
      paramMess.add("'自社請求書'");
      paramMess.add("請求書パターン");
      errMess.setMessageContent(readPropertiesFileService.getMessage("MST010-E",
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlBillingPattern" + MstConst.DELIMITER_ERROR;
    }

    /** 請求チェックリスト_出力対象. */
    // 請求チェックリスト_出力対象 ＝ '対象' and 請求チェックリスト_出力順 ＝ NULL の場合、エラーとする
    String billingCheckListOutputTarget = Util.removeWhitespaces(formMst0102d02
        .getDdlBillingCheckListOutputTarget());
    String billingCheckListOutputOrder = "";
    if (formMst0102d02.getDdlBillingCheckListOutputOrder() != null) {
      billingCheckListOutputOrder = Util.removeWhitespaces(formMst0102d02
          .getDdlBillingCheckListOutputOrder());
    }
    if (billingCheckListOutputTarget.equals(MstConst.BILLING_CHECKLIST_TARGET)
        && billingCheckListOutputOrder.equals("")) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("請求チェックリスト_出力対象");
      paramMess.add("'対象'");
      paramMess.add("請求チェックリスト_出力順");
      errMess.setMessageContent(readPropertiesFileService.getMessage("MST010-E",
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlBillingCheckListOutputOrder" + MstConst.DELIMITER_ERROR;
    }

    /** 締日. */
    // 請求単位 ＝ '締め単位' and 締日１ ＝ NULL and 締日２ ＝ NULL の場合、エラーとする
    String billingUnit = Util.removeWhitespaces(formMst0102d02
        .getDdlBillingUnit());
    String closeDay1 = Util.removeWhitespaces(formMst0102d02.getTxtCloseDay1());
    String closeDay2 = Util.removeWhitespaces(formMst0102d02.getTxtCloseDay2());
    if (billingUnit.equals(MstConst.BILLING_UNIT_DEADLINE_UNIT)
        && closeDay1.equals("") && closeDay2.equals("")) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("請求単位");
      paramMess.add("'締め単位'");
      paramMess.add("締日");
      errMess.setMessageContent(readPropertiesFileService.getMessage("MST010-E",
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtCloseDay1" + MstConst.DELIMITER_ERROR + "txtCloseDay2"
          + MstConst.DELIMITER_ERROR;
    }
    // （締日１ ≠ NULL and （締日１ ＜ １ or 締日１ ＞ ３１） or （締日２ ≠ NULL and （締日２ ＜ １ or 締日２
    // ＞ ３１） 場合、エラーとする
    boolean closeDayInvalid = false;
    if (!closeDay1.equals("") && (Integer.valueOf(
        closeDay1) < MstConst.CLOSE_DAY_MIN || Integer.valueOf(
            closeDay1) > MstConst.CLOSE_DAY_MAX)) {
      closeDayInvalid = true;
      strErrorId += "txtCloseDay1" + MstConst.DELIMITER_ERROR;
    }
    if (!closeDay2.equals("") && (Integer.valueOf(
        closeDay2) < MstConst.CLOSE_DAY_MIN || Integer.valueOf(
            closeDay2) > MstConst.CLOSE_DAY_MAX)) {
      closeDayInvalid = true;
      strErrorId += "txtCloseDay2" + MstConst.DELIMITER_ERROR;
    }
    if (closeDayInvalid) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("締日は１～３１の値を入力してください。");
      errMess.setMessageContent(readPropertiesFileService.getMessage("MST013-E",
          paramMess));
      lstErrMess.add(errMess);
    }

    /** 回収日. */
    // （回収日１ ≠ NULL and （回収日１ ＜ １ or 回収日１ ＞ ３１） or （回収日２ ≠ NULL and （回収日２ ＜ １ or
    // 回収日２ ＞ ３１） 場合、エラーとする
    boolean recoveryDayInvalid = false;
    String recoveryDay1 = Util.removeWhitespaces(formMst0102d02
        .getTxtRecoveryDay1());
    String recoveryDay2 = Util.removeWhitespaces(formMst0102d02
        .getTxtRecoveryDay2());
    if (!recoveryDay1.equals("") && (Integer.valueOf(
        recoveryDay1) < MstConst.CLOSE_DAY_MIN || Integer.valueOf(
            recoveryDay1) > MstConst.CLOSE_DAY_MAX)) {
      recoveryDayInvalid = true;
      strErrorId += "txtRecoveryDay1" + MstConst.DELIMITER_ERROR;
    }
    if (!recoveryDay2.equals("") && (Integer.valueOf(
        recoveryDay2) < MstConst.CLOSE_DAY_MIN || Integer.valueOf(
            recoveryDay2) > MstConst.CLOSE_DAY_MAX)) {
      recoveryDayInvalid = true;
      strErrorId += "txtRecoveryDay2" + MstConst.DELIMITER_ERROR;
    }
    if (recoveryDayInvalid) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("回収日は１～３１の値を入力してください。");
      errMess.setMessageContent(readPropertiesFileService.getMessage("MST013-E",
          paramMess));
      lstErrMess.add(errMess);
    }

    /** 入金種別. */
    // 入金種別 ＝ （'口座振替' or '普通振込'） and 入金口座 ＝ NULL の場合、エラーとする
    String paymentType = Util.removeWhitespaces(formMst0102d02
        .getDdlPaymentType());
    String paymentAccount = "";
    if (formMst0102d02.getDdlPaymentAccount() != null) {
      paymentAccount = Util.removeWhitespaces(formMst0102d02
          .getDdlPaymentAccount());
    }
    if ((paymentType.equals(MstConst.PAYMENT_TYPE_ACCOUNT_TRANSFER)
        || paymentType.equals(MstConst.PAYMENT_TYPE_NORMAL_TRANSFER))
        && paymentAccount.equals("")) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("入金種別");
      paramMess.add("'口座振替'　または　'普通振込'");
      paramMess.add("入金口座");
      errMess.setMessageContent(readPropertiesFileService.getMessage("MST010-E",
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "ddlPaymentAccount" + MstConst.DELIMITER_ERROR;
    }

    /** 修正種別. */
    // 修正データ突合区分 ＝ '対象' and (返品データ ＝ チェックなし and 欠品データ ＝ チェックなし and 修正データ ＝
    // チェックなし) の場合、エラーとする
    String modifyDataKubun = Util.removeWhitespaces(formMst0102d02
        .getDdlModifyDataKubun());
    boolean modifyTypeHenpinData = formMst0102d02.isChkModifyTypeHenpinData();
    boolean modifyTypeKetsuhinData = formMst0102d02
        .isChkModifyTypeKetsuhinData();
    boolean modifyTypeShuuseiData = formMst0102d02.isChkModifyTypeShuuseiData();
    if (modifyDataKubun.equals(MstConst.MODIFY_DATA_KUBUN_TARGET)
        && !modifyTypeHenpinData && !modifyTypeKetsuhinData
        && !modifyTypeShuuseiData) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("修正データ突合区分");
      paramMess.add("'対象'");
      paramMess.add("修正区分");
      errMess.setMessageContent(readPropertiesFileService.getMessage("MST010-E",
          paramMess));
      lstErrMess.add(errMess);
    }

    /** 請求データ区分. */
    // 請求データ区分 ＝ 'オンライン' and 請求データ配信ＩＤ ＝ NULL の場合、エラーとする
    String billingDataKubun = Util.removeWhitespaces(formMst0102d02
        .getDdlBillingDataKubun());
    String billingDataDeliveryId = Util.removeWhitespaces(formMst0102d02
        .getTxtBillingDataDeliveryId());
    if (billingDataKubun.equals(MstConst.BILLING_DATA_KUBUN_ONLINE)
        && billingDataDeliveryId.equals("")) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("請求データ区分");
      paramMess.add("'オンライン'");
      paramMess.add("請求データ配信ＩＤ");
      errMess.setMessageContent(readPropertiesFileService.getMessage("MST010-E",
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtBillingDataDeliveryId" + MstConst.DELIMITER_ERROR;
    }

    /** 状況コード. */
    // 状況コード ≠ NULL and 状況コード ≠ '1' and 状況コード ≠ '9' の場合エラーとし、次の通り処理を行う。
    String statusCode = Util.removeWhitespaces(formMst0102d02
        .getTxtStatusCode());
    if (!statusCode.equals("") && !statusCode.equals(CommonConst.STS_CD_ENTRY)
        && !statusCode.equals(CommonConst.STS_CD_INVALID)) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("状況コードは１または９で入力してください");
      errMess.setMessageContent(readPropertiesFileService.getMessage("MST013-E",
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "txtStatusCode" + MstConst.DELIMITER_ERROR;
    }

    // 結果を戻す
    if (lstErrMess.isEmpty()) {
      return false;
    } else {
      model.addAttribute("errorMessages", lstErrMess);
      model.addAttribute("lstErrorID", strErrorId);
      return true;
    }
  }

  /**
   * マスタ参照項目の存在チェックを行う.
   * 
   * @param formMst0102d02 ： 画面Mst0102d02のフォーム
   * @param model ： フォームのModel
   * @return boolean ： true(エラー)／false(普通)
   */
  public boolean checkExistence(FormMst0102d02 formMst0102d02,
      Model model) {
    String strErrorId = "";

    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();

    // エラーメッセージ変数定義
    ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    ErrorMessages errMess = new ErrorMessages();

    try {
      // 共通データ取得を初期化
      CommonGetData commonGetData = new CommonGetData(commonDao,
          readPropertiesFileService);

      /** 得意先情報取得. */
      String billingCode = Util.removeWhitespaces(formMst0102d02
          .getTxtBillingCode());
      if (!billingCode.equals("")) {
        // 請求先コードをゼロ編集する
        billingCode = Util.addLeadingZeros(billingCode, 7);
        formMst0102d02.setTxtBillingCode(billingCode);

        // 共通部品を使って、得意先マスタから請求先名称を取得する
        CustomerData customerData = commonGetData.getCustomerData(billingCode,
            null, CommonConst.GET_CUSTOMER_DATA_BILLING_TARGET);
        if (customerData.getMsgCd() != null) {
          // 該当レコードが存在しない場合
          errMess = new ErrorMessages();
          paramMess = new ArrayList<String>();
          paramMess.add("得意先マスタ");
          paramMess.add("入力された請求先");
          errMess.setMessageContent(readPropertiesFileService.getMessage(
              "COM009-E", paramMess));
          lstErrMess.add(errMess);
          strErrorId += "txtBillingCode" + MstConst.DELIMITER_ERROR;
          formMst0102d02.setTxtBillingSearchNameHidden("");
        } else {
          // 該当レコードが存在する場合、画面表示を行う
          formMst0102d02.setTxtBillingSearchNameHidden(customerData.getCst()
              .getCustNmR());
        }
      }

      /** チェーン情報取得. */
      String chainCode = Util.removeWhitespaces(formMst0102d02
          .getTxtChainCode());
      String chainEda = Util.removeWhitespaces(formMst0102d02
          .getTxtChainEda());
      if (!chainCode.equals("") && !chainEda.equals("")) {
        // チェーンコード、チェーン枝番をゼロ編集する
        chainCode = Util.addLeadingZeros(chainCode, 4);
        chainEda = Util.addLeadingZeros(chainEda, 2);
        formMst0102d02.setTxtChainCode(chainCode);
        formMst0102d02.setTxtChainEda(chainEda);

        // チェーンマスタからチェーン名称を取得する
        Map<String, Object> parms = new HashMap<String, Object>();
        parms.put("chainCode", chainCode);
        parms.put("chainEda", chainEda);
        parms.put("delFlg", CommonConst.DEL_FLG_OFF);
        parms.put("businessDate", formMst0102d02.getBusinessDate());
        String chainName = mst0102d02Dao.getMst0102d02Mapper().getChainName(
            parms);
        if (chainName == null) {
          // 該当レコードが存在しない場合
          errMess = new ErrorMessages();
          paramMess = new ArrayList<String>();
          paramMess.add("チェーンマスタ");
          paramMess.add("入力されたチェーン");
          errMess.setMessageContent(readPropertiesFileService.getMessage(
              "COM009-E", paramMess));
          lstErrMess.add(errMess);
          strErrorId += "txtChainCode" + MstConst.DELIMITER_ERROR
              + "txtChainEda" + MstConst.DELIMITER_ERROR;
          formMst0102d02.setTxtChainNameHidden("");
        } else {
          // 該当レコードが存在する場合、画面表示を行う
          formMst0102d02.setTxtChainNameHidden(chainName);
        }
      }

      /** 担当者情報取得. */
      ArrayList<MstToriatsukaiJigyouSho> toriatsukaiArray = formMst0102d02
          .getArrListToriatsukaiJigyouSho();
      for (int i = 0; i < toriatsukaiArray.size(); i++) {
        String jigyoushoCode = Util.removeWhitespaces(toriatsukaiArray.get(i)
            .getDdlToriatsukaiJigyouSho());
        String eigyouTantoushaCode = toriatsukaiArray.get(i)
            .getTxtEigyouTantoushaCode();
        String jimuTantoushaCode = toriatsukaiArray.get(i)
            .getTxtJimuTantoushaCode();
        if (!Util.removeWhitespaces(eigyouTantoushaCode).equals("")) {
          // 営業担当者コードをゼロ編集する
          eigyouTantoushaCode = Util.addLeadingZeros(eigyouTantoushaCode, 8);
          formMst0102d02.getArrListToriatsukaiJigyouSho().get(i)
              .setTxtEigyouTantoushaCode(eigyouTantoushaCode);

          // 共通部品を使って、担当者マスタから担当者名称を取得する
          UserData userData = commonGetData.getUserData(eigyouTantoushaCode,
              jigyoushoCode);
          if (userData.getMsgCd() != null) {
            // 該当レコードが存在しない場合、画面にエラーメッセージを表示する
            errMess = new ErrorMessages();
            errMess.setMessageContent(readPropertiesFileService.getMessage(
                "MST008-E", null));
            lstErrMess.add(errMess);
            strErrorId += "arrListToriatsukaiEigyou" + (i + 1)
                + MstConst.DELIMITER_ERROR;
            formMst0102d02.getArrListToriatsukaiJigyouSho().get(i)
                .setTxtEigyouTantoushaName("");
          } else {
            // 該当レコードが存在する場合、画面表示を行う
            formMst0102d02.getArrListToriatsukaiJigyouSho().get(i)
                .setTxtEigyouTantoushaName(userData.getUsr().getUserNm());
          }
        }
        if (!Util.removeWhitespaces(jimuTantoushaCode).equals("")) {
          // 事務担当者コードをゼロ編集する
          jimuTantoushaCode = Util.addLeadingZeros(jimuTantoushaCode, 8);
          formMst0102d02.getArrListToriatsukaiJigyouSho().get(i)
              .setTxtJimuTantoushaCode(jimuTantoushaCode);

          // 共通部品を使って、担当者マスタから担当者名称を取得する
          UserData userData = commonGetData.getUserData(jimuTantoushaCode,
              jigyoushoCode);
          if (userData.getMsgCd() != null) {
            // 該当レコードが存在しない場合、画面にエラーメッセージを表示する
            errMess = new ErrorMessages();
            errMess.setMessageContent(readPropertiesFileService.getMessage(
                "MST008-E", null));
            lstErrMess.add(errMess);
            strErrorId += "arrListToriatsukaiJimu" + (i + 1)
                + MstConst.DELIMITER_ERROR;
            formMst0102d02.getArrListToriatsukaiJigyouSho().get(i)
                .setTxtJimuTantoushaName("");
          } else {
            // 該当レコードが存在する場合、画面表示を行う
            formMst0102d02.getArrListToriatsukaiJigyouSho().get(i)
                .setTxtJimuTantoushaName(userData.getUsr().getUserNm());
          }
        }
      }
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }

    // 結果を戻す
    if (lstErrMess.isEmpty()) {
      return false;
    } else {
      model.addAttribute("errorMessages", lstErrMess);
      model.addAttribute("lstErrorID", strErrorId);
      return true;
    }
  }

  /**
   * 得意先情報登録・更新.
   * 
   * @param formMst0102d02 ： 画面Mst0102d02のフォーム
   * @param model ： フォームのModel
   * @param loginUserCode ： ユーザーＩＤ値
   */
  public void saveData(FormMst0102d02 formMst0102d02,
      Model model, String loginUserCode) throws Exception {
    String strErrorId = "";

    // トランザクション定義
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    txManager = (PlatformTransactionManager) appContext.getBean("txManager");
    TransactionStatus status = null;

    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();

    // エラーメッセージ変数定義
    ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    ErrorMessages errMess = new ErrorMessages();

    // 表示モードを取得する
    String viewMode = formMst0102d02.getViewMode();

    // 表示モードによって登録／更新を行う
    if (Util.removeWhitespaces(viewMode).equals(MstConst.SHINKI_MODE)) {
      // 画面表示モード ＝ 1 の場合、得意先情報を新規登録する
      // 得意先マスタデータ検索(存在チェック)
      MstCustomer mstCustomer = getCustomerInfo(Util
          .removeWhitespaces(formMst0102d02.getTxtCustomerCode()));
      if (mstCustomer != null) {
        // 該当レコードが存在する場合、画面にエラーメッセージを表示する
        // エラー COM027-E
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("得意先");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM027-E", paramMess));
        lstErrMess.add(errMess);
        strErrorId += "txtCustomerCode" + MstConst.DELIMITER_ERROR;
      } else {
        // 該当レコードが存在しない場合、登録を開始する
        /** 得意先マスタ登録. */
        mstCustomer = new MstCustomer();
        // 得意先コード
        mstCustomer.setCustCode(Util.removeWhitespaces(formMst0102d02
            .getTxtCustomerCode()));
        // チェーンコード
        mstCustomer.setCainCode(Short.valueOf(formMst0102d02
            .getTxtChainCode()));
        // チェーン枝番
        mstCustomer.setCainIdx(Short.valueOf(formMst0102d02.getTxtChainEda()));
        // 得意先フラグ
        mstCustomer.setCustFlg(formMst0102d02.isChkCustomer()
            ? MstConst.CHECK_ON : MstConst.CHECK_OFF);
        // 請求先フラグ
        mstCustomer.setBildFlg(formMst0102d02.isChkBilling() ? MstConst.CHECK_ON
            : MstConst.CHECK_OFF);
        // 請求先コード
        if (!formMst0102d02.getTxtBillingCode().trim().equals("")) {
          mstCustomer.setBildCode(formMst0102d02.getTxtBillingCode());
        } else {
          mstCustomer.setBildCode("");
        }
        // 得意先名称
        mstCustomer.setCustNm(formMst0102d02.getTxtCustomerName());
        // 得意先名称カナ
        mstCustomer.setCustNmKana(formMst0102d02.getTxtCustomerNameKana());
        // 得意先略称
        mstCustomer.setCustNmR(formMst0102d02.getTxtCustomerNameR());
        // 郵便番号
        mstCustomer.setZipCode(formMst0102d02.getTxtPostalCode1()
            + MstConst.HYPHEN_MARK + formMst0102d02.getTxtPostalCode2());
        // 住所１
        mstCustomer.setAdr1(formMst0102d02.getTxtAddress1());
        // 住所２
        mstCustomer.setAdr2(formMst0102d02.getTxtAddress2());
        // 電話番号
        String telNo = formMst0102d02.getTxtTel1()
            + MstConst.HYPHEN_MARK + formMst0102d02.getTxtTel2()
            + MstConst.HYPHEN_MARK + formMst0102d02.getTxtTel3();
        if (telNo.equals(MstConst.HYPHEN_MARK + MstConst.HYPHEN_MARK)) {
          telNo = "";
        }
        mstCustomer.setTelNo(telNo);
        // FAX番号
        String faxNo = formMst0102d02.getTxtFax1()
            + MstConst.HYPHEN_MARK + formMst0102d02.getTxtFax2()
            + MstConst.HYPHEN_MARK + formMst0102d02.getTxtFax3();
        if (faxNo.equals(MstConst.HYPHEN_MARK + MstConst.HYPHEN_MARK)) {
          faxNo = "";
        }
        mstCustomer.setFaxNo(faxNo);
        // 得意先担当者
        mstCustomer.setCustTan(formMst0102d02.getTxtCustomerTantousha());
        // 得意先担当者メールアドレス
        mstCustomer.setCustTanMail(formMst0102d02.getTxtMailAddress());
        /////// 幹事事業所コード
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtKanjiJigyouShoHidden()).equals("")) {
          // 幹事事業所コードコンボボックスが活性化された場合
          mstCustomer.setManagerJigyoCode(formMst0102d02
              .getDdlKanjiJigyouSho());
        } else {
          // 幹事事業所コードコンボボックスが非活性化された場合
          mstCustomer.setManagerJigyoCode(formMst0102d02
              .getTxtKanjiJigyouShoHidden());
        }
        /////// 得意先種別
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtCustomerTypeHidden()).equals("")) {
          // 得意先種別コンボボックスが活性化された場合
          mstCustomer.setCustCls(formMst0102d02.getDdlCustomerType());
        } else {
          // 得意先種別コンボボックスが非活性化された場合
          mstCustomer.setCustCls(formMst0102d02.getTxtCustomerTypeHidden());
        }
        /////// 業態区分
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtGyoutaiKubunHidden()).equals("")) {
          // 業態区分コンボボックスが活性化された場合
          mstCustomer.setGyotaiKb(formMst0102d02.getDdlGyoutaiKubun());
        } else {
          // 業態区分コンボボックスが非活性化された場合
          mstCustomer.setGyotaiKb(formMst0102d02.getTxtGyoutaiKubunHidden());
        }
        /////// 納品センターコード
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtDeliveryCenterHidden()).equals("")) {
          // 納品センターコードコンボボックスが活性化された場合
          if (formMst0102d02.getDdlDeliveryCenter() != null) {
            mstCustomer.setDeliCenterCode(formMst0102d02.getDdlDeliveryCenter());
          } else {
            mstCustomer.setDeliCenterCode("");
          }
        } else {
          // 納品センターコードコンボボックスが非活性化された場合
          mstCustomer.setDeliCenterCode(formMst0102d02
              .getTxtDeliveryCenterHidden());
        }
        /////// 関係会社種別
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtKankeiKaishaTypeHidden()).equals("")) {
          // 関係会社種別コンボボックスが活性化された場合
          mstCustomer.setRelComCls(formMst0102d02.getDdlKankeiKaishaType());
        } else {
          // 関係会社種別コンボボックスが非活性化された場合
          mstCustomer.setRelComCls(formMst0102d02
              .getTxtKankeiKaishaTypeHidden());
        }
        /////// 関係会社補助科目
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtHojoKamokuHidden()).equals("")) {
          // 補助科目コンボボックスが活性化された場合
          if (formMst0102d02.getDdlHojoKamoku() != null) {
            mstCustomer.setRelComSub(formMst0102d02.getDdlHojoKamoku());
          } else {
            mstCustomer.setRelComSub("");
          }
        } else {
          // 補助科目コンボボックスが非活性化された場合
          mstCustomer.setRelComSub(formMst0102d02.getTxtHojoKamokuHidden());
        }
        /////// 採番区分
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtSaibanKubunHidden()).equals("")) {
          // 補助科目コンボボックスが活性化された場合
          mstCustomer.setDatidxKb(formMst0102d02.getDdlSaibanKubun());
        } else {
          // 補助科目コンボボックスが非活性化された場合
          mstCustomer.setDatidxKb(formMst0102d02.getTxtSaibanKubunHidden());
        }
        /////// 店舗区分
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtTenpoKubunHidden()).equals("")) {
          // 店舗区分コンボボックスが活性化された場合
          mstCustomer.setShopKb(formMst0102d02.getDdlTenpoKubun());
        } else {
          // 店舗区分コンボボックスが非活性化された場合
          mstCustomer.setShopKb(formMst0102d02.getTxtTenpoKubunHidden());
        }
        /////// YG取引区分
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtYgTorihikiKubunHidden()).equals("")) {
          // YG取引区分コンボボックスが活性化された場合
          mstCustomer.setYgKb(formMst0102d02.getDdlYgTorihikiKubun());
        } else {
          // YG取引区分コンボボックスが非活性化された場合
          mstCustomer.setYgKb(formMst0102d02.getTxtYgTorihikiKubunHidden());
        }
        /////// 内税顧客区分
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtUchiZeiKoKyakuKubunHidden()).equals("")) {
          // 内税顧客区分コンボボックスが活性化された場合
          mstCustomer.setTaxIncKb(formMst0102d02.getDdlUchiZeiKoKyakuKubun());
        } else {
          // 内税顧客区分コンボボックスが非活性化された場合
          mstCustomer.setTaxIncKb(formMst0102d02
              .getTxtUchiZeiKoKyakuKubunHidden());
        }
        /////// 内税消費税端数処理
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtUchiZeiShoriHidden()).equals("")) {
          // 内税消費税端数処理コンボボックスが活性化された場合
          if (formMst0102d02.getDdlUchiZeiShori() != null) {
            mstCustomer.setTaxIncFrcKb(formMst0102d02.getDdlUchiZeiShori());
          } else {
            mstCustomer.setTaxIncFrcKb("");
          }
        } else {
          // 内税消費税端数処理コンボボックスが非活性化された場合
          mstCustomer.setTaxIncFrcKb(formMst0102d02.getTxtUchiZeiShoriHidden());
        }
        /////// 集金有無
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtShuukinUmuHidden()).equals("")) {
          // 集金有無コンボボックスが活性化された場合
          mstCustomer.setColMonKb(formMst0102d02.getDdlShuukinUmu());
        } else {
          // 集金有無コンボボックスが非活性化された場合
          mstCustomer.setColMonKb(formMst0102d02.getTxtShuukinUmuHidden());
        }
        /////// 現金集金マーク印字
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtGenkinShuukinMarkHidden()).equals("")) {
          // 現金集金マーク印字コンボボックスが活性化された場合
          mstCustomer.setColMmrkKb(formMst0102d02.getDdlGenkinShuukinMark());
        } else {
          // 現金集金マーク印字コンボボックスが非活性化された場合
          mstCustomer.setColMmrkKb(formMst0102d02
              .getTxtGenkinShuukinMarkHidden());
        }
        /////// 集計出力FLG
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtShuukinOutputFlagHidden()).equals("")) {
          // 集計出力FLGコンボボックスが活性化された場合
          mstCustomer.setSumsFlg(formMst0102d02.getDdlShuukinOutputFlag());
        } else {
          // 集計出力FLGコンボボックスが非活性化された場合
          mstCustomer.setSumsFlg(formMst0102d02
              .getTxtShuukinOutputFlagHidden());
        }
        /////// 手入力伝票発行
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtManualInputBillingHidden()).equals("")) {
          // 手入力伝票発行コンボボックスが活性化された場合
          mstCustomer.setShipsKb(formMst0102d02.getDdlManualInputBilling());
        } else {
          // 手入力伝票発行コンボボックスが非活性化された場合
          mstCustomer.setShipsKb(formMst0102d02
              .getTxtManualInputBillingHidden());
        }
        /////// 手入力出荷伝票帳票ID
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtManualInputDeliveryHidden()).equals("")) {
          // 手入力出荷伝票コンボボックスが活性化された場合
          mstCustomer.setShipsTypId(formMst0102d02.getDdlManualInputDelivery()
              .split(MstConst.DELIMITER_ERROR)[1]);
        } else {
          // 手入力出荷伝票コンボボックスが非活性化された場合
          mstCustomer.setShipsTypId(formMst0102d02
              .getTxtManualInputDeliveryHidden()
              .split(MstConst.DELIMITER_ERROR)[1]);
        }
        /////// 手入力出荷伝票種別
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtManualInputDeliveryHidden()).equals("")) {
          // 手入力出荷伝票コンボボックスが活性化された場合
          mstCustomer.setShipsTypCls(formMst0102d02.getDdlManualInputDelivery()
              .split(MstConst.DELIMITER_ERROR)[0]);
        } else {
          // 手入力出荷伝票コンボボックスが非活性化された場合
          mstCustomer.setShipsTypCls(formMst0102d02
              .getTxtManualInputDeliveryHidden()
              .split(MstConst.DELIMITER_ERROR)[0]);
        }
        /////// 伝票行計算金額まるめ
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtSlipLineCalculationAmountRoundingHidden()).equals("")) {
          // 伝票行計算金額まるめコンボボックスが活性化された場合
          mstCustomer.setShipsRudKb(formMst0102d02
              .getDdlSlipLineCalculationAmountRounding());
        } else {
          // 伝票行計算金額まるめコンボボックスが非活性化された場合
          mstCustomer.setShipsRudKb(formMst0102d02
              .getTxtSlipLineCalculationAmountRoundingHidden());
        }
        /////// 出荷伝票出力品コード
        if (Util.removeWhitespaces(formMst0102d02
            .getTxtDeliveryOutputProductCodeHidden()).equals("")) {
          // 出荷伝票出力品コードコンボボックスが活性化された場合
          mstCustomer.setShipsCodeKb(formMst0102d02
              .getDdlDeliveryOutputProductCode());
        } else {
          // 出荷伝票出力品コードコンボボックスが非活性化された場合
          mstCustomer.setShipsCodeKb(formMst0102d02
              .getTxtDeliveryOutputProductCodeHidden());
        }
        // 請求先名称
        mstCustomer.setBildNm(formMst0102d02.getTxtBillingName());
        // 請求先名称カナ
        mstCustomer.setBildNmKana(formMst0102d02.getTxtBillingNameKana());
        // 請求先略称
        mstCustomer.setBildNmR(formMst0102d02.getTxtBillingNameR());
        // 請求先郵便番号
        mstCustomer.setBildZipCode(formMst0102d02.getTxtBillingZipCode1()
            + MstConst.HYPHEN_MARK + formMst0102d02.getTxtBillingZipCode2());
        // 請求先住所１
        mstCustomer.setBildAdr1(formMst0102d02.getTxtBillingAddress1());
        // 請求先住所２
        mstCustomer.setBildAdr2(formMst0102d02.getTxtBillingAddress2());
        // 請求先電話番号
        String bildTelNo = formMst0102d02.getTxtBillingTel1()
            + MstConst.HYPHEN_MARK + formMst0102d02.getTxtBillingTel2()
            + MstConst.HYPHEN_MARK + formMst0102d02.getTxtBillingTel3();
        if (bildTelNo.equals(MstConst.HYPHEN_MARK + MstConst.HYPHEN_MARK)) {
          bildTelNo = "";
        }
        mstCustomer.setBildTelNo(bildTelNo);
        // 請求先FAX番号
        String bildFaxNo = formMst0102d02.getTxtBillingFax1()
            + MstConst.HYPHEN_MARK + formMst0102d02.getTxtBillingFax2()
            + MstConst.HYPHEN_MARK + formMst0102d02.getTxtBillingFax3();
        if (bildFaxNo.equals(MstConst.HYPHEN_MARK + MstConst.HYPHEN_MARK)) {
          bildFaxNo = "";
        }
        mstCustomer.setBildFaxNo(bildFaxNo);
        // 請求単位
        mstCustomer.setBildUntKb(formMst0102d02.getDdlBillingUnit());
        // 請求書単価
        mstCustomer.setBildTanka(formMst0102d02.getDdlBillingUnitPrice());
        // 請求書種類帳票ID
        mstCustomer.setBildTypId(formMst0102d02.getDdlBillingType()
            .split(MstConst.DELIMITER_ERROR)[1]);
        // 請求書種別
        mstCustomer.setBildTypCls(formMst0102d02.getDdlBillingType()
            .split(MstConst.DELIMITER_ERROR)[0]);
        // 取纏め請求
        mstCustomer.setSumBildKb(formMst0102d02.getDdlBillingToriMatomeUMu());
        // 取り纏め請求事業所
        if (formMst0102d02.getDdlBillingToriMatomeJigyouSho() != null) {
          mstCustomer.setSumBildJgyo(formMst0102d02
              .getDdlBillingToriMatomeJigyouSho());
        } else {
          mstCustomer.setSumBildJgyo("");
        }
        // 請求書パターン
        if (formMst0102d02.getDdlBillingPattern() != null) {
          mstCustomer.setBildPtn(formMst0102d02.getDdlBillingPattern());
        } else {
          mstCustomer.setBildPtn("");
        }
        // 請求書住所印字
        mstCustomer.setBildAdrOutKb(formMst0102d02.getDdlBillingAddressPrint());
        // 請求集計表区分
        mstCustomer.setBildSumKb(formMst0102d02
            .getDdlBillingShuukeiHyouKubun());
        // 消費税計算単位
        mstCustomer.setTaxUntKb(formMst0102d02
            .getDdlBillingShouhizeiCalculationUnit());
        // 消費税端数処理
        mstCustomer.setTaxFrcKb(formMst0102d02.getDdlBillingShouhizeiShori());
        // 請求チェックリスト 出力対象
        mstCustomer.setBildChkKb(formMst0102d02
            .getDdlBillingCheckListOutputTarget());
        // 請求チェックリスト 出力順
        if (formMst0102d02.getDdlBillingCheckListOutputOrder() != null) {
          mstCustomer.setBildChkSrt(formMst0102d02
              .getDdlBillingCheckListOutputOrder());
        } else {
          mstCustomer.setBildChkSrt("");
        }
        // 締日１
        if (!Util.removeWhitespaces(formMst0102d02
            .getTxtCloseDay1()).equals("")) {
          mstCustomer.setTotalDate1(Short.valueOf(formMst0102d02
              .getTxtCloseDay1()));
        }
        // 回収月区分１
        mstCustomer.setColTermKb1(formMst0102d02.getDdlRecoveryMonthKubun1());
        // 回収日１
        if (!Util.removeWhitespaces(formMst0102d02
            .getTxtRecoveryDay1()).equals("")) {
          mstCustomer.setColDate1(Short.valueOf(formMst0102d02
              .getTxtRecoveryDay1()));
        }
        // 締日２
        if (!Util.removeWhitespaces(formMst0102d02
            .getTxtCloseDay2()).equals("")) {
          mstCustomer.setTotalDate2(Short.valueOf(formMst0102d02
              .getTxtCloseDay2()));
        }
        // 回収月区分２
        if (formMst0102d02.getDdlRecoveryMonthKubun2() != null) {
          mstCustomer.setColTermKb2(formMst0102d02.getDdlRecoveryMonthKubun2());
        }
        // 回収日２
        if (!Util.removeWhitespaces(formMst0102d02
            .getTxtRecoveryDay2()).equals("")) {
          mstCustomer.setColDate2(Short.valueOf(formMst0102d02
              .getTxtRecoveryDay2()));
        }
        // 入金種別
        mstCustomer.setRcvmCls(formMst0102d02.getDdlPaymentType());
        // 入金口座
        if (formMst0102d02.getDdlPaymentAccount() != null) {
          mstCustomer.setRcvmAcc(formMst0102d02.getDdlPaymentAccount());
        } else {
          mstCustomer.setRcvmAcc("");
        }
        // 手形サイト
        if (!Util.removeWhitespaces(formMst0102d02
            .getTxtNoteSite()).equals("")) {
          mstCustomer.setReceNoteSite(Short.valueOf(formMst0102d02
              .getTxtNoteSite()));
        }
        // 受領データ突合区分
        mstCustomer.setRcvDatKb(formMst0102d02.getDdlJuryouDataKubun());
        // 請求データ区分
        mstCustomer.setBildDatKb(formMst0102d02.getDdlBillingDataKubun());
        // 修正データ突合区分
        mstCustomer.setModDatKb(formMst0102d02.getDdlModifyDataKubun());
        // 修正データ種別_返品
        mstCustomer.setModKbHpn(formMst0102d02.isChkModifyTypeHenpinData()
            ? MstConst.CHECK_ON : MstConst.CHECK_OFF);
        // 修正データ種別_欠品
        mstCustomer.setModKbKpn(formMst0102d02.isChkModifyTypeKetsuhinData()
            ? MstConst.CHECK_ON : MstConst.CHECK_OFF);
        // 修正データ種別_修正
        mstCustomer.setModKbSsi(formMst0102d02.isChkModifyTypeShuuseiData()
            ? MstConst.CHECK_ON : MstConst.CHECK_OFF);
        // 請求先支払データ区分
        mstCustomer.setPayDatKb(formMst0102d02.getDdlBillingPaymentDataKubun());
        // 取引コード
        mstCustomer.setTrCode(formMst0102d02.getTxtTorihikiCode());
        // 分類コード（定番、店直）
        mstCustomer.setBnCodeStS(formMst0102d02.getTxtBunruiCodeTeibanShop());
        // 伝票区分（定番、店直）
        mstCustomer.setDnKbStS(formMst0102d02.getTxtDenpyouKubunTeibanShop());
        // 分類コード（定番、センター）
        mstCustomer.setBnCodeStC(formMst0102d02.getTxtBunruiCodeTeibanCenter());
        // 伝票区分（定番、センター）
        mstCustomer.setDnKbStC(formMst0102d02.getTxtDenpyouKubunTeibanCenter());
        // 分類コード（特売、店直）
        mstCustomer.setBnCodeSpS(formMst0102d02.getTxtBunruiCodeTokubaiShop());
        // 伝票区分（特売、店直）
        mstCustomer.setDnKbSpS(formMst0102d02.getTxtDenpyouKubunTokubaiShop());
        // 分類コード（特売、センター）
        mstCustomer.setBnCodeSpC(formMst0102d02
            .getTxtBunruiCodeTokubaiCenter());
        // 伝票区分（特売、センター）
        mstCustomer.setDnKbSpC(formMst0102d02
            .getTxtDenpyouKubunTokubaiCenter());
        // 請求データ配信ＩＤ
        mstCustomer.setBildDatId(formMst0102d02.getTxtBillingDataDeliveryId());
        // 集計コード１
        mstCustomer.setSumCode1(formMst0102d02.getTxtShuukeiCode1());
        // 集計コード２
        mstCustomer.setSumCode2(formMst0102d02.getTxtShuukeiCode2());
        // 使用中止日
        mstCustomer.setCloseDate(formMst0102d02.getTxtShiyouChuushiDay()
            .replace("/", ""));
        // 状況コード
        mstCustomer.setStsCode(formMst0102d02.getTxtStatusCode());
        // 送信済みフラグ
        mstCustomer.setSndFlg(CommonConst.SEND_FLAG_UNSEND);
        // 共通項目の設定
        mstCustomer = setCustCommonData(mstCustomer, loginUserCode,
            "MST01-02D02", true);
        // 登録開始
        try {
          status = txManager.getTransaction(def);
          mst0102d02Dao.getMstCustomerMapper().insertSelective(mstCustomer);
          txManager.commit(status);
        } catch (Exception ex) {
          txManager.rollback(status);
          logger.error(ex.getMessage());
          throw ex;
        }

        /** 得意先事業所マスタ登録. */
        ArrayList<MstToriatsukaiJigyouSho> toriatsukaiArray = formMst0102d02
            .getArrListToriatsukaiJigyouSho();
        // 取扱事業所一覧の行数分登録処理をループする
        for (int i = 0; i < toriatsukaiArray.size(); i++) {
          MstCustjgyo mstCustjgyo = new MstCustjgyo();
          // 得意先コード
          mstCustjgyo.setCustCode(Util.removeWhitespaces(formMst0102d02
              .getTxtCustomerCode()));
          // 事業所コード
          mstCustjgyo.setJigyoCode(toriatsukaiArray.get(i)
              .getDdlToriatsukaiJigyouSho());
          // 営業担当者コード
          mstCustjgyo.setEgTanCode(toriatsukaiArray.get(i)
              .getTxtEigyouTantoushaCode());
          // 事務担当者コード
          mstCustjgyo.setJmTanCode(toriatsukaiArray.get(i)
              .getTxtJimuTantoushaCode());
          // 送信済みフラグ
          mstCustjgyo.setSndFlg(CommonConst.SEND_FLAG_UNSEND);
          // 共通項目の設定
          mstCustjgyo = setCustjgyoCommonData(mstCustjgyo, loginUserCode,
              "MST01-02D02", true);
          // 登録開始
          try {
            status = txManager.getTransaction(def);
            mst0102d02Dao.getMstCustjgyoMapper().insertSelective(mstCustjgyo);
            txManager.commit(status);
          } catch (Exception ex) {
            txManager.rollback(status);
            logger.error(ex.getMessage());
            throw ex;
          }
        }

        /** 伝票採番マスタ登録. */
        // 採番区分 ＝ '得意先毎' or 採番区分 ＝ '請求先毎' の場合、伝票採番マスタを登録する
        if (Util.removeWhitespaces(formMst0102d02.getDdlSaibanKubun())
            .equals(MstConst.SAIBAN_KUBUN_CUSTOMER)
            || Util.removeWhitespaces(formMst0102d02.getDdlSaibanKubun())
                .equals(MstConst.SAIBAN_KUBUN_BILL)) {
          // 伝票採番マスタ存在チェック
          Map<String, Object> parms = new HashMap<String, Object>();
          parms.put("customerCode", Util
              .removeWhitespaces(formMst0102d02.getTxtCustomerCode()));
          String customerCode = mst0102d02Dao.getMst0102d02Mapper()
              .checkDenpyouSaibanData(parms);
          if (customerCode == null || customerCode.equals("")) {
            // 該当レコードが存在しない場合のみ、伝票採番マスタの登録を行う
            MstDatIdx mstDatIdx = new MstDatIdx();
            // 得意先コード
            mstDatIdx.setCustCode(Util.removeWhitespaces(formMst0102d02
                .getTxtCustomerCode()));
            // 店舗コード
            mstDatIdx.setShopCode(CommonConst.SHOP_CD_NONE);
            // 上限値
            mstDatIdx.setMaxIdx(MstConst.SAIBAN_MAX_IDX);
            // 下限値
            mstDatIdx.setMinIdx(MstConst.SAIBAN_MIN_IDX);
            // 有効桁数
            mstDatIdx.setValidDigit(MstConst.SAIBAN_VALID_DIGIT);
            // 0埋め
            mstDatIdx.setZeroSuppress(MstConst.SAIBAN_ZERO_SUPPRESS);
            // 採番番号
            mstDatIdx.setDatIdx(MstConst.SAIBAN_DAT_IDX);
            // 状況コード
            mstDatIdx.setStsCode(CommonConst.STS_CD_ENTRY);
            // 共通項目の設定
            mstDatIdx = setDatIdxCommonData(mstDatIdx, loginUserCode,
                "MST01-02D02", true);
            // 登録開始
            try {
              status = txManager.getTransaction(def);
              mst0102d02Dao.getMstDatIdxMapper().insertSelective(mstDatIdx);
              txManager.commit(status);
            } catch (Exception ex) {
              txManager.rollback(status);
              logger.error(ex.getMessage());
              throw ex;
            }
          }
        }
        // 登録が完了のメッセージを表示する
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("得意先マスタの登録");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM002-I", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("infoMessFlag", "true");
      }
    } else if (Util.removeWhitespaces(viewMode).equals(MstConst.TEISEI_MODE)) {
      // 画面表示モード ＝ '3' （訂正）の場合、排他チェックを行う
      String customerCode = Util.removeWhitespaces(formMst0102d02
          .getTxtCustomerCode());
      ArrayList<MstToriatsukaiJigyouSho> toriatsukaiArray = formMst0102d02
          .getArrListToriatsukaiJigyouSho();
      String haitaDate = formMst0102d02.getHaitaDate();
      String haitaTime = formMst0102d02.getHaitaTime();
      // 排他チェックを行う。 共通仕様 9-(4) 適用
      boolean customerInvalid = checkHaitaCustomer(customerCode, haitaDate,
          haitaTime);
      // 排他チェック開始
      if (customerInvalid) {
        // エラー
        errMess = new ErrorMessages();
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM031-E", null));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
      } else {
        /** 得意先マスタ訂正更新. */
        MstCustomer mstCustomer = getCustomerInfo(Util
            .removeWhitespaces(formMst0102d02.getTxtCustomerCode()));
        if (mstCustomer != null) {
          // 得意先フラグ
          mstCustomer.setCustFlg(formMst0102d02.isChkCustomer()
              ? MstConst.CHECK_ON : MstConst.CHECK_OFF);
          // 請求先フラグ
          mstCustomer.setBildFlg(formMst0102d02.isChkBilling()
              ? MstConst.CHECK_ON : MstConst.CHECK_OFF);
          // 請求先コード
          mstCustomer.setBildCode(formMst0102d02.getTxtBillingCode());
          // 得意先名称
          mstCustomer.setCustNm(formMst0102d02.getTxtCustomerName());
          // 得意先名称カナ
          mstCustomer.setCustNmKana(formMst0102d02.getTxtCustomerNameKana());
          // 得意先略称
          mstCustomer.setCustNmR(formMst0102d02.getTxtCustomerNameR());
          // 郵便番号
          mstCustomer.setZipCode(formMst0102d02.getTxtPostalCode1()
              + MstConst.HYPHEN_MARK + formMst0102d02.getTxtPostalCode2());
          // 住所１
          mstCustomer.setAdr1(formMst0102d02.getTxtAddress1());
          // 住所２
          mstCustomer.setAdr2(formMst0102d02.getTxtAddress2());
          // 電話番号
          String telNo = formMst0102d02.getTxtTel1()
              + MstConst.HYPHEN_MARK + formMst0102d02.getTxtTel2()
              + MstConst.HYPHEN_MARK + formMst0102d02.getTxtTel3();
          if (telNo.equals(MstConst.HYPHEN_MARK + MstConst.HYPHEN_MARK)) {
            telNo = "";
          }
          mstCustomer.setTelNo(telNo);
          // FAX番号
          String faxNo = formMst0102d02.getTxtFax1()
              + MstConst.HYPHEN_MARK + formMst0102d02.getTxtFax2()
              + MstConst.HYPHEN_MARK + formMst0102d02.getTxtFax3();
          if (faxNo.equals(MstConst.HYPHEN_MARK + MstConst.HYPHEN_MARK)) {
            faxNo = "";
          }
          mstCustomer.setFaxNo(faxNo);
          // 得意先担当者
          mstCustomer.setCustTan(formMst0102d02.getTxtCustomerTantousha());
          // 得意先担当者メールアドレス
          mstCustomer.setCustTanMail(formMst0102d02.getTxtMailAddress());
          /////// 幹事事業所コード
          if (Util.removeWhitespaces(formMst0102d02
              .getTxtKanjiJigyouShoHidden()).equals("")) {
            // 幹事事業所コードコンボボックスが活性化された場合
            mstCustomer.setManagerJigyoCode(formMst0102d02
                .getDdlKanjiJigyouSho());
          } else {
            // 幹事事業所コードコンボボックスが非活性化された場合
            mstCustomer.setManagerJigyoCode(formMst0102d02
                .getTxtKanjiJigyouShoHidden());
          }
          /////// 得意先種別
          if (Util.removeWhitespaces(formMst0102d02
              .getTxtCustomerTypeHidden()).equals("")) {
            // 得意先種別コンボボックスが活性化された場合
            mstCustomer.setCustCls(formMst0102d02.getDdlCustomerType());
          } else {
            // 得意先種別コンボボックスが非活性化された場合
            mstCustomer.setCustCls(formMst0102d02.getTxtCustomerTypeHidden());
          }
          /////// 業態区分
          if (Util.removeWhitespaces(formMst0102d02
              .getTxtGyoutaiKubunHidden()).equals("")) {
            // 業態区分コンボボックスが活性化された場合
            mstCustomer.setGyotaiKb(formMst0102d02.getDdlGyoutaiKubun());
          } else {
            // 業態区分コンボボックスが非活性化された場合
            mstCustomer.setGyotaiKb(formMst0102d02.getTxtGyoutaiKubunHidden());
          }
          /////// 納品センターコード
          if (Util.removeWhitespaces(formMst0102d02
              .getTxtDeliveryCenterHidden()).equals("")) {
            // 納品センターコードコンボボックスが活性化された場合
            if (formMst0102d02.getDdlDeliveryCenter() != null) {
              mstCustomer.setDeliCenterCode(formMst0102d02
                  .getDdlDeliveryCenter());
            } else {
              mstCustomer.setDeliCenterCode("");
            }
          } else {
            // 納品センターコードコンボボックスが非活性化された場合
            mstCustomer.setDeliCenterCode(formMst0102d02
                .getTxtDeliveryCenterHidden());
          }
          /////// 関係会社種別
          if (Util.removeWhitespaces(formMst0102d02
              .getTxtKankeiKaishaTypeHidden()).equals("")) {
            // 関係会社種別コンボボックスが活性化された場合
            mstCustomer.setRelComCls(formMst0102d02.getDdlKankeiKaishaType());
          } else {
            // 関係会社種別コンボボックスが非活性化された場合
            mstCustomer.setRelComCls(formMst0102d02
                .getTxtKankeiKaishaTypeHidden());
          }
          /////// 関係会社補助科目
          if (Util.removeWhitespaces(formMst0102d02
              .getTxtHojoKamokuHidden()).equals("")) {
            // 補助科目コンボボックスが活性化された場合
            if (formMst0102d02.getDdlHojoKamoku() != null) {
              mstCustomer.setRelComSub(formMst0102d02.getDdlHojoKamoku());
            } else {
              mstCustomer.setRelComSub("");
            }
          } else {
            // 補助科目コンボボックスが非活性化された場合
            mstCustomer.setRelComSub(formMst0102d02.getTxtHojoKamokuHidden());
          }
          /////// 採番区分
          if (Util.removeWhitespaces(formMst0102d02
              .getTxtSaibanKubunHidden()).equals("")) {
            // 補助科目コンボボックスが活性化された場合
            mstCustomer.setDatidxKb(formMst0102d02.getDdlSaibanKubun());
          } else {
            // 補助科目コンボボックスが非活性化された場合
            mstCustomer.setDatidxKb(formMst0102d02.getTxtSaibanKubunHidden());
          }
          /////// 店舗区分
          if (Util.removeWhitespaces(formMst0102d02
              .getTxtTenpoKubunHidden()).equals("")) {
            // 店舗区分コンボボックスが活性化された場合
            mstCustomer.setShopKb(formMst0102d02.getDdlTenpoKubun());
          } else {
            // 店舗区分コンボボックスが非活性化された場合
            mstCustomer.setShopKb(formMst0102d02.getTxtTenpoKubunHidden());
          }
          /////// YG取引区分
          if (Util.removeWhitespaces(formMst0102d02
              .getTxtYgTorihikiKubunHidden()).equals("")) {
            // YG取引区分コンボボックスが活性化された場合
            mstCustomer.setYgKb(formMst0102d02.getDdlYgTorihikiKubun());
          } else {
            // YG取引区分コンボボックスが非活性化された場合
            mstCustomer.setYgKb(formMst0102d02.getTxtYgTorihikiKubunHidden());
          }
          /////// 内税顧客区分
          if (Util.removeWhitespaces(formMst0102d02
              .getTxtUchiZeiKoKyakuKubunHidden()).equals("")) {
            // 内税顧客区分コンボボックスが活性化された場合
            mstCustomer.setTaxIncKb(formMst0102d02.getDdlUchiZeiKoKyakuKubun());
          } else {
            // 内税顧客区分コンボボックスが非活性化された場合
            mstCustomer.setTaxIncKb(formMst0102d02
                .getTxtUchiZeiKoKyakuKubunHidden());
          }
          /////// 内税消費税端数処理
          if (Util.removeWhitespaces(formMst0102d02
              .getTxtUchiZeiShoriHidden()).equals("")) {
            // 内税消費税端数処理コンボボックスが活性化された場合
            if (formMst0102d02.getDdlUchiZeiShori() != null) {
              mstCustomer.setTaxIncFrcKb(formMst0102d02.getDdlUchiZeiShori());
            } else {
              mstCustomer.setTaxIncFrcKb("");
            }
          } else {
            // 内税消費税端数処理コンボボックスが非活性化された場合
            mstCustomer.setTaxIncFrcKb(formMst0102d02
                .getTxtUchiZeiShoriHidden());
          }
          /////// 集金有無
          if (Util.removeWhitespaces(formMst0102d02
              .getTxtShuukinUmuHidden()).equals("")) {
            // 集金有無コンボボックスが活性化された場合
            mstCustomer.setColMonKb(formMst0102d02.getDdlShuukinUmu());
          } else {
            // 集金有無コンボボックスが非活性化された場合
            mstCustomer.setColMonKb(formMst0102d02.getTxtShuukinUmuHidden());
          }
          /////// 現金集金マーク印字
          if (Util.removeWhitespaces(formMst0102d02
              .getTxtGenkinShuukinMarkHidden()).equals("")) {
            // 現金集金マーク印字コンボボックスが活性化された場合
            mstCustomer.setColMmrkKb(formMst0102d02.getDdlGenkinShuukinMark());
          } else {
            // 現金集金マーク印字コンボボックスが非活性化された場合
            mstCustomer.setColMmrkKb(formMst0102d02
                .getTxtGenkinShuukinMarkHidden());
          }
          /////// 集計出力FLG
          if (Util.removeWhitespaces(formMst0102d02
              .getTxtShuukinOutputFlagHidden()).equals("")) {
            // 集計出力FLGコンボボックスが活性化された場合
            mstCustomer.setSumsFlg(formMst0102d02.getDdlShuukinOutputFlag());
          } else {
            // 集計出力FLGコンボボックスが非活性化された場合
            mstCustomer.setSumsFlg(formMst0102d02
                .getTxtShuukinOutputFlagHidden());
          }
          /////// 手入力伝票発行
          if (Util.removeWhitespaces(formMst0102d02
              .getTxtManualInputBillingHidden()).equals("")) {
            // 手入力伝票発行コンボボックスが活性化された場合
            mstCustomer.setShipsKb(formMst0102d02.getDdlManualInputBilling());
          } else {
            // 手入力伝票発行コンボボックスが非活性化された場合
            mstCustomer.setShipsKb(formMst0102d02
                .getTxtManualInputBillingHidden());
          }
          /////// 手入力出荷伝票帳票ID
          if (Util.removeWhitespaces(formMst0102d02
              .getTxtManualInputDeliveryHidden()).equals("")) {
            // 手入力出荷伝票コンボボックスが活性化された場合
            mstCustomer.setShipsTypId(formMst0102d02.getDdlManualInputDelivery()
                .split(MstConst.DELIMITER_ERROR)[1]);
          } else {
            // 手入力出荷伝票コンボボックスが非活性化された場合
            mstCustomer.setShipsTypId(formMst0102d02
                .getTxtManualInputDeliveryHidden()
                .split(MstConst.DELIMITER_ERROR)[1]);
          }
          /////// 手入力出荷伝票種別
          if (Util.removeWhitespaces(formMst0102d02
              .getTxtManualInputDeliveryHidden()).equals("")) {
            // 手入力出荷伝票コンボボックスが活性化された場合
            mstCustomer.setShipsTypCls(formMst0102d02
                .getDdlManualInputDelivery()
                .split(MstConst.DELIMITER_ERROR)[0]);
          } else {
            // 手入力出荷伝票コンボボックスが非活性化された場合
            mstCustomer.setShipsTypCls(formMst0102d02
                .getTxtManualInputDeliveryHidden()
                .split(MstConst.DELIMITER_ERROR)[0]);
          }
          /////// 伝票行計算金額まるめ
          if (Util.removeWhitespaces(formMst0102d02
              .getTxtSlipLineCalculationAmountRoundingHidden()).equals("")) {
            // 伝票行計算金額まるめコンボボックスが活性化された場合
            mstCustomer.setShipsRudKb(formMst0102d02
                .getDdlSlipLineCalculationAmountRounding());
          } else {
            // 伝票行計算金額まるめコンボボックスが非活性化された場合
            mstCustomer.setShipsRudKb(formMst0102d02
                .getTxtSlipLineCalculationAmountRoundingHidden());
          }
          /////// 出荷伝票出力品コード
          if (Util.removeWhitespaces(formMst0102d02
              .getTxtDeliveryOutputProductCodeHidden()).equals("")) {
            // 出荷伝票出力品コードコンボボックスが活性化された場合
            mstCustomer.setShipsCodeKb(formMst0102d02
                .getDdlDeliveryOutputProductCode());
          } else {
            // 出荷伝票出力品コードコンボボックスが非活性化された場合
            mstCustomer.setShipsCodeKb(formMst0102d02
                .getTxtDeliveryOutputProductCodeHidden());
          }
          // 請求先名称
          mstCustomer.setBildNm(formMst0102d02.getTxtBillingName());
          // 請求先名称カナ
          mstCustomer.setBildNmKana(formMst0102d02.getTxtBillingNameKana());
          // 請求先略称
          mstCustomer.setBildNmR(formMst0102d02.getTxtBillingNameR());
          // 請求先郵便番号
          mstCustomer.setBildZipCode(formMst0102d02.getTxtBillingZipCode1()
              + MstConst.HYPHEN_MARK
              + formMst0102d02.getTxtBillingZipCode2());
          // 請求先住所１
          mstCustomer.setBildAdr1(formMst0102d02.getTxtBillingAddress1());
          // 請求先住所２
          mstCustomer.setBildAdr2(formMst0102d02.getTxtBillingAddress2());
          // 請求先電話番号
          String bildTelNo = formMst0102d02.getTxtBillingTel1()
              + MstConst.HYPHEN_MARK + formMst0102d02.getTxtBillingTel2()
              + MstConst.HYPHEN_MARK + formMst0102d02.getTxtBillingTel3();
          if (bildTelNo.equals(MstConst.HYPHEN_MARK + MstConst.HYPHEN_MARK)) {
            bildTelNo = "";
          }
          mstCustomer.setBildTelNo(bildTelNo);
          // 請求先FAX番号
          String bildFaxNo = formMst0102d02.getTxtBillingFax1()
              + MstConst.HYPHEN_MARK + formMst0102d02.getTxtBillingFax2()
              + MstConst.HYPHEN_MARK + formMst0102d02.getTxtBillingFax3();
          if (bildFaxNo.equals(MstConst.HYPHEN_MARK + MstConst.HYPHEN_MARK)) {
            bildFaxNo = "";
          }
          mstCustomer.setBildFaxNo(bildFaxNo);
          // 請求単位
          mstCustomer.setBildUntKb(formMst0102d02.getDdlBillingUnit());
          // 請求書単価
          mstCustomer.setBildTanka(formMst0102d02.getDdlBillingUnitPrice());
          // 請求書種類帳票ID
          mstCustomer.setBildTypId(formMst0102d02.getDdlBillingType()
              .split(MstConst.DELIMITER_ERROR)[1]);
          // 請求書種別
          mstCustomer.setBildTypCls(formMst0102d02.getDdlBillingType()
              .split(MstConst.DELIMITER_ERROR)[0]);
          // 取纏め請求
          mstCustomer.setSumBildKb(formMst0102d02.getDdlBillingToriMatomeUMu());
          // 取り纏め請求事業所
          if (formMst0102d02.getDdlBillingToriMatomeJigyouSho() != null) {
            mstCustomer.setSumBildJgyo(formMst0102d02
                .getDdlBillingToriMatomeJigyouSho());
          } else {
            mstCustomer.setSumBildJgyo("");
          }
          // 請求書パターン
          if (formMst0102d02.getDdlBillingPattern() != null) {
            mstCustomer.setBildPtn(formMst0102d02.getDdlBillingPattern());
          } else {
            mstCustomer.setBildPtn("");
          }
          // 請求書住所印字
          mstCustomer.setBildAdrOutKb(formMst0102d02
              .getDdlBillingAddressPrint());
          // 請求集計表区分
          mstCustomer.setBildSumKb(formMst0102d02
              .getDdlBillingShuukeiHyouKubun());
          // 消費税計算単位
          mstCustomer.setTaxUntKb(formMst0102d02
              .getDdlBillingShouhizeiCalculationUnit());
          // 消費税端数処理
          mstCustomer.setTaxFrcKb(formMst0102d02.getDdlBillingShouhizeiShori());
          // 請求チェックリスト 出力対象
          mstCustomer.setBildChkKb(formMst0102d02
              .getDdlBillingCheckListOutputTarget());
          // 請求チェックリスト 出力順
          if (formMst0102d02.getDdlBillingCheckListOutputOrder() != null) {
            mstCustomer.setBildChkSrt(formMst0102d02
                .getDdlBillingCheckListOutputOrder());
          } else {
            mstCustomer.setBildChkSrt("");
          }
          // 締日１
          if (!Util.removeWhitespaces(formMst0102d02
              .getTxtCloseDay1()).equals("")) {
            mstCustomer.setTotalDate1(Short.valueOf(formMst0102d02
                .getTxtCloseDay1()));
          } else {
            mstCustomer.setTotalDate1(null);
          }
          // 回収月区分１
          mstCustomer.setColTermKb1(formMst0102d02.getDdlRecoveryMonthKubun1());
          // 回収日１
          if (!Util.removeWhitespaces(formMst0102d02
              .getTxtRecoveryDay1()).equals("")) {
            mstCustomer.setColDate1(Short.valueOf(formMst0102d02
                .getTxtRecoveryDay1()));
          } else {
            mstCustomer.setColDate1(null);
          }
          // 締日２
          if (!Util.removeWhitespaces(formMst0102d02
              .getTxtCloseDay2()).equals("")) {
            mstCustomer.setTotalDate2(Short.valueOf(formMst0102d02
                .getTxtCloseDay2()));
          } else {
            mstCustomer.setTotalDate2(null);
          }
          // 回収月区分２
          mstCustomer.setColTermKb2(formMst0102d02.getDdlRecoveryMonthKubun2());
          // 回収日２
          if (!Util.removeWhitespaces(formMst0102d02
              .getTxtRecoveryDay2()).equals("")) {
            mstCustomer.setColDate2(Short.valueOf(formMst0102d02
                .getTxtRecoveryDay2()));
          } else {
            mstCustomer.setColDate2(null);
          }
          // 入金種別
          mstCustomer.setRcvmCls(formMst0102d02.getDdlPaymentType());
          // 入金口座
          if (formMst0102d02.getDdlPaymentAccount() != null) {
            mstCustomer.setRcvmAcc(formMst0102d02.getDdlPaymentAccount());
          } else {
            mstCustomer.setRcvmAcc("");
          }
          // 手形サイト
          if (!Util.removeWhitespaces(formMst0102d02
              .getTxtNoteSite()).equals("")) {
            mstCustomer.setReceNoteSite(Short.valueOf(formMst0102d02
                .getTxtNoteSite()));
          } else {
            mstCustomer.setReceNoteSite(null);
          }
          // 受領データ突合区分
          mstCustomer.setRcvDatKb(formMst0102d02.getDdlJuryouDataKubun());
          // 請求データ区分
          mstCustomer.setBildDatKb(formMst0102d02.getDdlBillingDataKubun());
          // 修正データ突合区分
          mstCustomer.setModDatKb(formMst0102d02.getDdlModifyDataKubun());
          // 修正データ種別_返品
          mstCustomer.setModKbHpn(formMst0102d02.isChkModifyTypeHenpinData()
              ? MstConst.CHECK_ON : MstConst.CHECK_OFF);
          // 修正データ種別_欠品
          mstCustomer.setModKbKpn(formMst0102d02.isChkModifyTypeKetsuhinData()
              ? MstConst.CHECK_ON : MstConst.CHECK_OFF);
          // 修正データ種別_修正
          mstCustomer.setModKbSsi(formMst0102d02.isChkModifyTypeShuuseiData()
              ? MstConst.CHECK_ON : MstConst.CHECK_OFF);
          // 請求先支払データ区分
          mstCustomer.setPayDatKb(formMst0102d02
              .getDdlBillingPaymentDataKubun());
          // 取引コード
          mstCustomer.setTrCode(formMst0102d02.getTxtTorihikiCode());
          // 分類コード（定番、店直）
          mstCustomer.setBnCodeStS(formMst0102d02.getTxtBunruiCodeTeibanShop());
          // 伝票区分（定番、店直）
          mstCustomer.setDnKbStS(formMst0102d02.getTxtDenpyouKubunTeibanShop());
          // 分類コード（定番、センター）
          mstCustomer.setBnCodeStC(formMst0102d02
              .getTxtBunruiCodeTeibanCenter());
          // 伝票区分（定番、センター）
          mstCustomer.setDnKbStC(formMst0102d02
              .getTxtDenpyouKubunTeibanCenter());
          // 分類コード（特売、店直）
          mstCustomer.setBnCodeSpS(formMst0102d02
              .getTxtBunruiCodeTokubaiShop());
          // 伝票区分（特売、店直）
          mstCustomer.setDnKbSpS(formMst0102d02
              .getTxtDenpyouKubunTokubaiShop());
          // 分類コード（特売、センター）
          mstCustomer.setBnCodeSpC(formMst0102d02
              .getTxtBunruiCodeTokubaiCenter());
          // 伝票区分（特売、センター）
          mstCustomer.setDnKbSpC(formMst0102d02
              .getTxtDenpyouKubunTokubaiCenter());
          // 請求データ配信ＩＤ
          mstCustomer.setBildDatId(formMst0102d02
              .getTxtBillingDataDeliveryId());
          // 集計コード１
          mstCustomer.setSumCode1(formMst0102d02.getTxtShuukeiCode1());
          // 集計コード２
          mstCustomer.setSumCode2(formMst0102d02.getTxtShuukeiCode2());
          // 使用中止日
          mstCustomer.setCloseDate(formMst0102d02.getTxtShiyouChuushiDay()
              .replace("/", ""));
          // 状況コード
          mstCustomer.setStsCode(formMst0102d02.getTxtStatusCode());
          // 送信済みフラグ
          mstCustomer.setSndFlg(CommonConst.SEND_FLAG_UNSEND);
          // 共通項目の設定
          mstCustomer = setCustCommonData(mstCustomer, loginUserCode,
              "MST01-02D02", false);
          // 更新開始
          try {
            status = txManager.getTransaction(def);
            mst0102d02Dao.getMstCustomerMapper().updateByPrimaryKey(
                mstCustomer);
            txManager.commit(status);
          } catch (Exception ex) {
            txManager.rollback(status);
            logger.error(ex.getMessage());
            throw ex;
          }

          /** 得意先事業所マスタ訂正更新. */
          // ※得意先単位にDelete→Insertにて登録する
          String[] toriatsukaiJigyouShoCode = formMst0102d02
              .getToriatsukaiJigyouShoId().split(",", -1);
          for (String code : toriatsukaiJigyouShoCode) {
            if (!code.equals("")) {
              MstCustjgyoKey custJgyoKey = new MstCustjgyoKey();
              custJgyoKey.setCustCode(Util
                  .removeWhitespaces(formMst0102d02.getTxtCustomerCode()));
              custJgyoKey.setJigyoCode(Util.removeWhitespaces(code));
              try {
                status = txManager.getTransaction(def);
                mst0102d02Dao.getMstCustjgyoMapper().deleteByPrimaryKey(
                    custJgyoKey);
                txManager.commit(status);
              } catch (Exception ex) {
                txManager.rollback(status);
                logger.error(ex.getMessage());
                throw ex;
              }
            }
          }
          // 取扱事業所一覧の行数分訂正処理をループする
          for (int j = 0; j < toriatsukaiArray.size(); j++) {
            MstCustjgyo mstCustjgyo = new MstCustjgyo();
            // 得意先コード
            mstCustjgyo.setCustCode(Util
                .removeWhitespaces(formMst0102d02.getTxtCustomerCode()));
            // 事業所コード
            mstCustjgyo.setJigyoCode(toriatsukaiArray.get(j)
                .getDdlToriatsukaiJigyouSho());
            // 営業担当者コード
            mstCustjgyo.setEgTanCode(toriatsukaiArray.get(j)
                .getTxtEigyouTantoushaCode());
            // 事務担当者コード
            mstCustjgyo.setJmTanCode(toriatsukaiArray.get(j)
                .getTxtJimuTantoushaCode());
            // 送信済みフラグ
            mstCustjgyo.setSndFlg(CommonConst.SEND_FLAG_UNSEND);
            // 共通項目の設定
            mstCustjgyo = setCustjgyoCommonData(mstCustjgyo, loginUserCode,
                "MST01-02D02", true);
            // 登録開始
            try {
              status = txManager.getTransaction(def);
              mst0102d02Dao.getMstCustjgyoMapper().insert(mstCustjgyo);
              txManager.commit(status);
            } catch (Exception ex) {
              txManager.rollback(status);
              logger.error(ex.getMessage());
              throw ex;
            }
          }

          /** 個別請求先情報更新. */
          // 得意先フラグ ＝ チェックなし and 請求先フラグ ＝ チェックあり の場合のみ更新処理を行う
          boolean customerFlg = formMst0102d02.isChkCustomer();
          boolean billingFlg = formMst0102d02.isChkBilling();
          if (!customerFlg && billingFlg) {
            /** 得意先マスタ登録. */
            mstCustomer = new MstCustomer();
            MstCustomerExample customerExample = new MstCustomerExample();
            MstCustomerExample.Criteria customerCriteria = customerExample
                .createCriteria();
            customerCriteria.andBildCodeEqualTo(Util
                .removeWhitespaces(formMst0102d02.getTxtCustomerCode()));
            /////// 幹事事業所コード
            if (Util.removeWhitespaces(formMst0102d02
                .getTxtKanjiJigyouShoHidden()).equals("")) {
              // 幹事事業所コードコンボボックスが活性化された場合
              mstCustomer.setManagerJigyoCode(formMst0102d02
                  .getDdlKanjiJigyouSho());
            } else {
              // 幹事事業所コードコンボボックスが非活性化された場合
              mstCustomer.setManagerJigyoCode(formMst0102d02
                  .getTxtKanjiJigyouShoHidden());
            }
            /////// 得意先種別
            if (Util.removeWhitespaces(formMst0102d02
                .getTxtCustomerTypeHidden()).equals("")) {
              // 得意先種別コンボボックスが活性化された場合
              mstCustomer.setCustCls(formMst0102d02.getDdlCustomerType());
            } else {
              // 得意先種別コンボボックスが非活性化された場合
              mstCustomer.setCustCls(formMst0102d02.getTxtCustomerTypeHidden());
            }
            /////// 業態区分
            if (Util.removeWhitespaces(formMst0102d02
                .getTxtGyoutaiKubunHidden()).equals("")) {
              // 業態区分コンボボックスが活性化された場合
              mstCustomer.setGyotaiKb(formMst0102d02.getDdlGyoutaiKubun());
            } else {
              // 業態区分コンボボックスが非活性化された場合
              mstCustomer.setGyotaiKb(formMst0102d02
                  .getTxtGyoutaiKubunHidden());
            }
            // 納品センターコード
            if (Util.removeWhitespaces(formMst0102d02
                .getTxtDeliveryCenterHidden()).equals("")) {
              // 納品センターコードコンボボックスが活性化された場合
              if (formMst0102d02.getDdlDeliveryCenter() != null) {
                mstCustomer.setDeliCenterCode(formMst0102d02
                    .getDdlDeliveryCenter());
              } else {
                mstCustomer.setDeliCenterCode("");
              }
            } else {
              // 納品センターコードコンボボックスが非活性化された場合
              mstCustomer.setDeliCenterCode(formMst0102d02
                  .getTxtDeliveryCenterHidden());
            }
            /////// 関係会社種別
            if (Util.removeWhitespaces(formMst0102d02
                .getTxtKankeiKaishaTypeHidden()).equals("")) {
              // 関係会社種別コンボボックスが活性化された場合
              mstCustomer.setRelComCls(formMst0102d02.getDdlKankeiKaishaType());
            } else {
              // 関係会社種別コンボボックスが非活性化された場合
              mstCustomer.setRelComCls(formMst0102d02
                  .getTxtKankeiKaishaTypeHidden());
            }
            /////// 関係会社補助科目
            if (Util.removeWhitespaces(formMst0102d02
                .getTxtHojoKamokuHidden()).equals("")) {
              // 補助科目コンボボックスが活性化された場合
              if (formMst0102d02.getDdlHojoKamoku() != null) {
                mstCustomer.setRelComSub(formMst0102d02.getDdlHojoKamoku());
              } else {
                mstCustomer.setRelComSub("");
              }
            } else {
              // 補助科目コンボボックスが非活性化された場合
              mstCustomer.setRelComSub(formMst0102d02.getTxtHojoKamokuHidden());
            }
            /////// 採番区分
            if (Util.removeWhitespaces(formMst0102d02
                .getTxtSaibanKubunHidden()).equals("")) {
              // 補助科目コンボボックスが活性化された場合
              mstCustomer.setDatidxKb(formMst0102d02.getDdlSaibanKubun());
            } else {
              // 補助科目コンボボックスが非活性化された場合
              mstCustomer.setDatidxKb(formMst0102d02.getTxtSaibanKubunHidden());
            }
            // 店舗区分
            if (Util.removeWhitespaces(formMst0102d02
                .getTxtTenpoKubunHidden()).equals("")) {
              // 店舗区分コンボボックスが活性化された場合
              mstCustomer.setShopKb(formMst0102d02.getDdlTenpoKubun());
            } else {
              // 店舗区分コンボボックスが非活性化された場合
              mstCustomer.setShopKb(formMst0102d02.getTxtTenpoKubunHidden());
            }
            /////// YG取引区分
            if (Util.removeWhitespaces(formMst0102d02
                .getTxtYgTorihikiKubunHidden()).equals("")) {
              // YG取引区分コンボボックスが活性化された場合
              mstCustomer.setYgKb(formMst0102d02.getDdlYgTorihikiKubun());
            } else {
              // YG取引区分コンボボックスが非活性化された場合
              mstCustomer.setYgKb(formMst0102d02.getTxtYgTorihikiKubunHidden());
            }
            /////// 内税顧客区分
            if (Util.removeWhitespaces(formMst0102d02
                .getTxtUchiZeiKoKyakuKubunHidden()).equals("")) {
              // 内税顧客区分コンボボックスが活性化された場合
              mstCustomer.setTaxIncKb(formMst0102d02
                  .getDdlUchiZeiKoKyakuKubun());
            } else {
              // 内税顧客区分コンボボックスが非活性化された場合
              mstCustomer.setTaxIncKb(formMst0102d02
                  .getTxtUchiZeiKoKyakuKubunHidden());
            }
            /////// 内税消費税端数処理
            if (Util.removeWhitespaces(formMst0102d02
                .getTxtUchiZeiShoriHidden()).equals("")) {
              // 内税消費税端数処理コンボボックスが活性化された場合
              if (formMst0102d02.getDdlUchiZeiShori() != null) {
                mstCustomer.setTaxIncFrcKb(formMst0102d02.getDdlUchiZeiShori());
              } else {
                mstCustomer.setTaxIncFrcKb("");
              }
            } else {
              // 内税消費税端数処理コンボボックスが非活性化された場合
              mstCustomer.setTaxIncFrcKb(formMst0102d02
                  .getTxtUchiZeiShoriHidden());
            }
            /////// 集金有無
            if (Util.removeWhitespaces(formMst0102d02
                .getTxtShuukinUmuHidden()).equals("")) {
              // 集金有無コンボボックスが活性化された場合
              mstCustomer.setColMonKb(formMst0102d02.getDdlShuukinUmu());
            } else {
              // 集金有無コンボボックスが非活性化された場合
              mstCustomer.setColMonKb(formMst0102d02.getTxtShuukinUmuHidden());
            }
            /////// 現金集金マーク印字
            if (Util.removeWhitespaces(formMst0102d02
                .getTxtGenkinShuukinMarkHidden()).equals("")) {
              // 現金集金マーク印字コンボボックスが活性化された場合
              mstCustomer.setColMmrkKb(formMst0102d02
                  .getDdlGenkinShuukinMark());
            } else {
              // 現金集金マーク印字コンボボックスが非活性化された場合
              mstCustomer.setColMmrkKb(formMst0102d02
                  .getTxtGenkinShuukinMarkHidden());
            }
            /////// 集計出力FLG
            if (Util.removeWhitespaces(formMst0102d02
                .getTxtShuukinOutputFlagHidden()).equals("")) {
              // 集計出力FLGコンボボックスが活性化された場合
              mstCustomer.setSumsFlg(formMst0102d02.getDdlShuukinOutputFlag());
            } else {
              // 集計出力FLGコンボボックスが非活性化された場合
              mstCustomer.setSumsFlg(formMst0102d02
                  .getTxtShuukinOutputFlagHidden());
            }
            /////// 手入力伝票発行
            if (Util.removeWhitespaces(formMst0102d02
                .getTxtManualInputBillingHidden()).equals("")) {
              // 手入力伝票発行コンボボックスが活性化された場合
              mstCustomer.setShipsKb(formMst0102d02.getDdlManualInputBilling());
            } else {
              // 手入力伝票発行コンボボックスが非活性化された場合
              mstCustomer.setShipsKb(formMst0102d02
                  .getTxtManualInputBillingHidden());
            }
            /////// 手入力出荷伝票帳票ID
            if (Util.removeWhitespaces(formMst0102d02
                .getTxtManualInputDeliveryHidden()).equals("")) {
              // 手入力出荷伝票コンボボックスが活性化された場合
              mstCustomer.setShipsTypId(formMst0102d02
                  .getDdlManualInputDelivery()
                  .split(MstConst.DELIMITER_ERROR)[1]);
            } else {
              // 手入力出荷伝票コンボボックスが非活性化された場合
              mstCustomer.setShipsTypId(formMst0102d02
                  .getTxtManualInputDeliveryHidden()
                  .split(MstConst.DELIMITER_ERROR)[1]);
            }
            /////// 手入力出荷伝票種別
            if (Util.removeWhitespaces(formMst0102d02
                .getTxtManualInputDeliveryHidden()).equals("")) {
              // 手入力出荷伝票コンボボックスが活性化された場合
              mstCustomer.setShipsTypCls(formMst0102d02
                  .getDdlManualInputDelivery()
                  .split(MstConst.DELIMITER_ERROR)[0]);
            } else {
              // 手入力出荷伝票コンボボックスが非活性化された場合
              mstCustomer.setShipsTypCls(formMst0102d02
                  .getTxtManualInputDeliveryHidden()
                  .split(MstConst.DELIMITER_ERROR)[0]);
            }
            /////// 伝票行計算金額まるめ
            if (Util.removeWhitespaces(formMst0102d02
                .getTxtSlipLineCalculationAmountRoundingHidden())
                .equals("")) {
              // 伝票行計算金額まるめコンボボックスが活性化された場合
              mstCustomer.setShipsRudKb(formMst0102d02
                  .getDdlSlipLineCalculationAmountRounding());
            } else {
              // 伝票行計算金額まるめコンボボックスが非活性化された場合
              mstCustomer.setShipsRudKb(formMst0102d02
                  .getTxtSlipLineCalculationAmountRoundingHidden());
            }
            /////// 出荷伝票出力品コード
            if (Util.removeWhitespaces(formMst0102d02
                .getTxtDeliveryOutputProductCodeHidden()).equals("")) {
              // 出荷伝票出力品コードコンボボックスが活性化された場合
              mstCustomer.setShipsCodeKb(formMst0102d02
                  .getDdlDeliveryOutputProductCode());
            } else {
              // 出荷伝票出力品コードコンボボックスが非活性化された場合
              mstCustomer.setShipsCodeKb(formMst0102d02
                  .getTxtDeliveryOutputProductCodeHidden());
            }
            // 共通項目の設定
            mstCustomer = setCustCommonData(mstCustomer, loginUserCode,
                "MST01-02D02", false);
            // 更新開始
            try {
              status = txManager.getTransaction(def);
              mst0102d02Dao.getMstCustomerMapper().updateByExampleSelective(
                  mstCustomer, customerExample);
              txManager.commit(status);
            } catch (Exception ex) {
              txManager.rollback(status);
              logger.error(ex.getMessage());
              throw ex;
            }
          }
          // 排他の値を更新する
          formMst0102d02.setHaitaDate(DateUtil.getSysDate());
          formMst0102d02.setHaitaTime(DateUtil.getSysTime());
          // 登録が完了のメッセージを表示する
          errMess = new ErrorMessages();
          paramMess = new ArrayList<String>();
          paramMess.add("得意先マスタの登録");
          errMess.setMessageContent(readPropertiesFileService.getMessage(
              "COM002-I", paramMess));
          lstErrMess.add(errMess);
          model.addAttribute("infoMessFlag", "true");
        }
      }
    } else if (Util.removeWhitespaces(viewMode).equals(
        MstConst.TORIKESI_MODE)) {
      // 画面表示モード ＝ '4' （取消）の場合、排他チェックを行う
      String customerCode = Util.removeWhitespaces(formMst0102d02
          .getTxtCustomerCode());
      String haitaDate = formMst0102d02.getHaitaDate();
      String haitaTime = formMst0102d02.getHaitaTime();
      // 排他チェックを行う。 共通仕様 9-(4) 適用
      boolean customerValid = checkHaitaCustomer(customerCode, haitaDate,
          haitaTime);
      if (customerValid) {
        errMess = new ErrorMessages();
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM031-E", null));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
      } else {
        // 得意先情報を取消更新する
        MstCustomer mstCustomer = new MstCustomer();
        if (mstCustomer != null) {
          mstCustomer.setCustCode(Util.removeWhitespaces(formMst0102d02
              .getTxtCustomerCode()));
          mstCustomer.setStsCode(CommonConst.STS_CD_INVALID);
          mstCustomer.setSndFlg(CommonConst.SEND_FLAG_UNSEND);
          // 共通項目の設定
          mstCustomer = setCustCommonData(mstCustomer, loginUserCode,
              "MST01-02D02", false);
          // 更新開始
          try {
            status = txManager.getTransaction(def);
            mst0102d02Dao.getMstCustomerMapper().updateByPrimaryKeySelective(
                mstCustomer);
            txManager.commit(status);
          } catch (Exception ex) {
            txManager.rollback(status);
            logger.error(ex.getMessage());
            throw ex;
          }
          // 排他の値を更新する
          formMst0102d02.setHaitaDate(DateUtil.getSysDate());
          formMst0102d02.setHaitaTime(DateUtil.getSysTime());
          // 登録が完了のメッセージを表示する
          errMess = new ErrorMessages();
          paramMess = new ArrayList<String>();
          paramMess.add("得意先マスタの登録");
          errMess.setMessageContent(readPropertiesFileService.getMessage(
              "COM002-I", paramMess));
          lstErrMess.add(errMess);
          model.addAttribute("infoMessFlag", "true");
        }
      }
    }
    // フォームのデータをClientに戻す
    model.addAttribute("errorMessages", lstErrMess);
    model.addAttribute("lstErrorID", strErrorId);
  }

  /**
   * 排他チェック.
   * 
   * @param customerCode : 得意先コード
   * @param haitaDate : 排他日付
   * @param haitaTime : 排他時間
   * @return boolean ： true(エラー)／false(普通)
   */
  public boolean checkHaitaCustomer(String customerCode, String haitaDate,
      String haitaTime) {
    // 得意先情報取得
    MstCustomer mstCustomer = new MstCustomer();
    mstCustomer = getCustomerInfo(customerCode);

    // 共通項目.修正年月日 ＋ 共通項目．修正時刻 > 表示時システム日付 の場合
    int intDateRegDb = Integer.parseInt(mstCustomer.getUpdYmd());
    int intDateHidden = Integer.parseInt(haitaDate);

    if (intDateRegDb > intDateHidden) {
      return true;
    } else if (intDateRegDb == intDateHidden) {
      intDateRegDb = Integer.parseInt(mstCustomer.getUpdTime());
      intDateHidden = Integer.parseInt(haitaTime);
      if (intDateRegDb > intDateHidden) {
        return true;
      }
    }
    return false;
  }

  /**
   * 得意先情報を取得する.
   * 
   * @param customerCode : 得意先コード
   * @return MstCustomer
   */
  private MstCustomer getCustomerInfo(String customerCode) {
    MstCustomer mstCustomer = null;
    try {
      mstCustomer = mst0102d02Dao.getMstCustomerMapper().selectByPrimaryKey(
          customerCode);
      return mstCustomer;
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
  }

  /**
   * 入力チェック処理.
   * 
   * @param val ： チェックしたい値
   * @param emptyFlg ： 必須チェックフラグ(true:必須チェックする、false:必須チェックしない）
   * @param type ： 型（※形式は定数参照）
   * @param len ： 桁数
   * @return String ： エラーメッセージコード
   */
  private String checkItem(String val, boolean emptyFlg, Integer type,
      Integer len) {
    String error = null;
    if (emptyFlg) {
      error = InputCheckCom.chkEmpty(val);
      if (error != null) {
        return error;
      }
    }

    if (type != null) {
      error = InputCheckCom.chkType(val, type);
      if (error != null) {
        return error;
      }
    }

    if (len != null) {
      error = InputCheckCom.chkLength(val, len);
      if (error != null) {
        return error;
      }
    }

    return error;
  }

  /**
   * 得意先共通項目の設定.
   * 
   * @param dataUser ： 得意先データ
   * @param strUserId ： ユーザID
   * @param strProgId ： プログラムID
   * @param isNew ： 新規／更新
   * @return 得意先データ
   */
  private MstCustomer setCustCommonData(MstCustomer dataUser,
      String strUserId, String strProgId, boolean isNew) {
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
   * 得意先事業所共通項目の設定.
   * 
   * @param dataUser ： 得意先事業所データ
   * @param strUserId ： ユーザID
   * @param strProgId ： プログラムID
   * @param isNew ： 新規／更新
   * @return 得意先事業所データ
   */
  private MstCustjgyo setCustjgyoCommonData(MstCustjgyo dataUser,
      String strUserId, String strProgId, boolean isNew) {
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
   * 伝票採番共通項目の設定.
   * 
   * @param dataUser ： 伝票採番データ
   * @param strUserId ： ユーザID
   * @param strProgId ： プログラムID
   * @param isNew ： 新規／更新
   * @return 伝票採番データ
   */
  private MstDatIdx setDatIdxCommonData(MstDatIdx dataUser,
      String strUserId, String strProgId, boolean isNew) {
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
}
