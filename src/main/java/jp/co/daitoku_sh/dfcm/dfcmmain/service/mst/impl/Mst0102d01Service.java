/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl
 * ファイル名:Mst0102d01Service.java
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
import jp.co.daitoku_sh.dfcm.common.component.MstGeneralData;
import jp.co.daitoku_sh.dfcm.common.component.UserData;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSJigyo;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSJigyoExample;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetData;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ObjCombobox;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0102d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.Mst0102d01CustomerList;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.Mst0102d01ExportCsv;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.Mst0102d01ExportCsvDataObject;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl.Mst0102d01Dao;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * サービスクラス
 * 
 * @author アクトブレーンベトナム コアー
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class Mst0102d01Service extends AbsService {

  @Autowired
  @Qualifier("mst0102d01Dao")
  private Mst0102d01Dao mst0102d01Dao;

  @Autowired
  @Qualifier("commonDao")
  private CommonDao commonDao;

  // メッセージPropertiesファイルを読む
  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  /** ログ出力用. */
  private Dfcmlogger logger = new Dfcmlogger();

  // DAOの定義
  public Mst0102d01Dao getMst0102d01Dao() {
    return mst0102d01Dao;
  }

  public void setMst0102d01Dao(Mst0102d01Dao mst0102d01Dao) {
    this.mst0102d01Dao = mst0102d01Dao;
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
    paramMess = new ArrayList<String>();
    paramMess.add("CSVファイルを出力");
    DefaultMessages defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-I");
    defaultMsg.setMessageTitle("確認メッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-I", paramMess));
    defaultMsgLst.add(defaultMsg);
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
    defaultMsg.setMessageCode("COM006-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM006-E", null));
    defaultMsgLst.add(defaultMsg);
    // 入力チェックエラー
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM008-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM008-E", null));
    defaultMsgLst.add(defaultMsg);
    model.addAttribute("defaultMessages", defaultMsgLst);
  }

  /**
   * フォームの初期化.
   * 
   * @param model ： フォームのModel
   * @param formMst0102d01 ： 画面のフォーム
   * @param sysAdminFlag ： システム管理者フラグ値
   */
  public void init(Model model, FormMst0102d01 formMst0102d01,
      String sysAdminFlag) {
    try {
      // メッセージのParam
      ArrayList<String> paramMess = new ArrayList<String>();

      // エラーメッセージ変数定義
      ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
      ErrorMessages errMess = new ErrorMessages();

      // 共通部品初期化
      CommonGetSystemCom systemCom = new CommonGetSystemCom(commonDao, null,
          null, readPropertiesFileService);

      // 最大検索件数取得
      int searchMax = systemCom.getCodeSearchMax();
      formMst0102d01.setSearchMax(searchMax);

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
        return;
      }
      formMst0102d01.setBusinessDate(Integer.parseInt(businessDate));

      // システム管理者フラグ ＝ '1' （システム管理者）の場合、事業所コンボの作成を行う
      if (sysAdminFlag.equals(CommonConst.SYS_ADMIN_FLG_VALID)) {
        // 事業所マスタデータ取得
        MstSJigyoExample jigyouExample = new MstSJigyoExample();
        MstSJigyoExample.Criteria jigyouCriteria = jigyouExample
            .createCriteria();
        jigyouCriteria.andDelflgEqualTo(CommonConst.DEL_FLG_OFF);
        jigyouCriteria.andStrymdLessThanOrEqualTo(formMst0102d01.getBusinessDate());
        jigyouCriteria.andEndymdGreaterThanOrEqualTo(formMst0102d01.getBusinessDate());
        jigyouExample.setOrderByClause("JGYCD");
        List<MstSJigyo> jigyouData = mst0102d01Dao.getMstSJigyoMapper()
            .selectByExample(jigyouExample);
        if (jigyouData == null || jigyouData.isEmpty()) {
          // 事業所情報格納クラス ＝ NULL の場合、エラーとする
          paramMess = new ArrayList<String>();
          paramMess.add("事業所マスタの取得");
          errMess.setMessageContent(readPropertiesFileService.getMessage(
              "COM015-E", paramMess));
          lstErrMess.add(errMess);
          model.addAttribute("errorMessages", lstErrMess);
          return;
        }
        ArrayList<ObjCombobox> jigyouResultList = new ArrayList<ObjCombobox>();
        // 最初のコンボボックスデータを追加する
        ObjCombobox firstObj = new ObjCombobox();
        firstObj.setCode("");
        firstObj.setName("");
        jigyouResultList.add(firstObj);
        // 残るコンボボックスデータを追加する
        for (int i = 0; i < jigyouData.size(); i++) {
          ObjCombobox tempObj = new ObjCombobox();
          tempObj.setCode(jigyouData.get(i).getJgycd());
          tempObj.setName(jigyouData.get(i).getJgycd() + "："
                  + jigyouData.get(i).getJgymei());
          jigyouResultList.add(tempObj);
        }
        // 取得した[変数]事業所名称格納クラスを、事業所へセットする
        model.addAttribute("arrListJigyouSho", jigyouResultList);
        if (formMst0102d01.getDdlJigyouSho() != null) {
          formMst0102d01.setDdlJigyouSho(formMst0102d01.getDdlJigyouSho());
        } else {
          formMst0102d01.setDdlJigyouSho(formMst0102d01.getLoginJigyouShoCode());
        }
      }

      // 得意先種別名称を取得する
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
        return;
      }
      ArrayList<ObjCombobox> customerTypeResultList = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      ObjCombobox firstObj = new ObjCombobox();
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

      // 内税顧客区分名称を取得する
      List<MstGeneralData> generalDataUchizeiKubun = systemCom
          .getMstGeneralConf("Tax_Inc_Kb", null);
      if (generalDataUchizeiKubun == null || generalDataUchizeiKubun
          .isEmpty()) {
        // 汎用マスタ格納クラス ＝ NULL の場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("汎用マスタ");
        paramMess.add("内税顧客区分");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E", paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        return;
      }
      final ArrayList<ObjCombobox> uchizeiKubunResultList = new ArrayList<ObjCombobox>();
      // 最初のコンボボックスデータを追加する
      firstObj = new ObjCombobox();
      firstObj.setCode("");
      firstObj.setName("");
      uchizeiKubunResultList.add(firstObj);
      // 残るコンボボックスデータを追加する
      for (int i = 0; i < generalDataUchizeiKubun.size(); i++) {
        ObjCombobox tempObj = new ObjCombobox();
        tempObj.setCode(generalDataUchizeiKubun.get(i).getGlCode());
        tempObj.setName(generalDataUchizeiKubun.get(i).getGlCode() + "："
                + generalDataUchizeiKubun.get(i).getTarget1());
        uchizeiKubunResultList.add(tempObj);
      }
      // 取得した[変数]内税顧客区分名称格納クラスを、内税顧客区分へセットする
      model.addAttribute("arrListUchiZeiKoKyakuKubun", uchizeiKubunResultList);

      // チェックボックスの初期化
      if (formMst0102d01.getDdlCustomerType() == null) {
        formMst0102d01.setChkCustomer(true);
        formMst0102d01.setChkBilling(true);
        formMst0102d01.setChkCustomerBilling(true);
      }
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }
  }

  /**
   * 得意先一覧情報を取得する.
   * 
   * @param searchCondition ： 検索条件オブジェクト
   * @return Mst0102d01CustomerListオブジェクト
   */
  public List<Mst0102d01CustomerList> searchCustomerData(
      FormMst0102d01 searchCondition) {
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();

    // 共通データ取得を初期化
    CommonGetData commonGetData = new CommonGetData(commonDao,
        readPropertiesFileService);

    // 変数定義
    String strErrorId = "";
    boolean inputCheckFlag = false;
    String jigyouShoCode = null;
    List<Mst0102d01CustomerList> customerList = null;

    try {
      // マスタ参照項目の存在チェックを行う
      // （1）事業所判定
      // システム管理者フラグ ＝ '1'（システム管理者）の場合
      if (searchCondition.getSysAdminFlag().equals(
          CommonConst.SYS_ADMIN_FLG_VALID)) {
        jigyouShoCode = searchCondition.getDdlJigyouSho().trim();
      } else if (searchCondition.getSysAdminFlag().equals(
          // システム管理者フラグ ＝ '0'（一般ユーザ）の場合
          CommonConst.SYS_ADMIN_FLG_INVALID)) {
        jigyouShoCode = searchCondition.getLoginJigyouShoCode().trim();
      }

      // チェーンコード ≠ NULL and チェーン枝番 ≠ NULL の場合、
      String chainCode = searchCondition.getTxtChainCode().trim();
      String chainEda = searchCondition.getTxtChainEda().trim();
      if (!chainCode.equals("") && !chainEda.equals("")) {
        // チェーンコード、チェーン枝番をゼロ編集する
        chainCode = Util.addLeadingZeros(chainCode, 4);
        chainEda = Util.addLeadingZeros(chainEda, 2);
        // チェーンマスタからチェーン名称を取得する
        Map<String, Object> parms = new HashMap<String, Object>();
        parms.put("chainCode", chainCode);
        parms.put("chainEda", chainEda);
        parms.put("delFlg", CommonConst.DEL_FLG_OFF);
        parms.put("businessDate", searchCondition.getBusinessDate());
        String chainName = mst0102d01Dao.getMst0102d01Mapper().getChainName(
            parms);
        // 該当レコードが存在する場合、画面表示を行う
        if (chainName != null) {
          searchCondition.setTxtChainName(chainName);
        } else { // 該当レコードが存在しない場合、入力チェックフラグにTrueをセット
          inputCheckFlag = true;
          strErrorId += "txtChainCode" + MstConst.DELIMITER_ERROR
              + "txtChainEda" + MstConst.DELIMITER_ERROR;
        }
      }

      // 営業担当者コード ≠ NULL の場合
      String eigyouTantoushaCode = searchCondition.getTxtEigyouTantoushaCode()
          .trim();
      if (!eigyouTantoushaCode.equals("")) {
        // 営業担当者コードをゼロ編集する
        eigyouTantoushaCode = Util.addLeadingZeros(eigyouTantoushaCode, 8);

        // 担当者マスタから担当者名称を取得する
        UserData eigyouUserData = commonGetData.getUserData(eigyouTantoushaCode,
            jigyouShoCode);
        if (eigyouUserData.getMsgCd() != null) {
          // 該当レコードが存在しない場合、入力チェックフラグにTrueをセット
          inputCheckFlag = true;
          strErrorId += "txtEigyouTantoushaCode" + MstConst.DELIMITER_ERROR;
        } else {
          // 該当レコードが存在する場合、画面表示を行う
          searchCondition.setTxtEigyouTantoushaName(eigyouUserData.getUsr().getUserNm()
              .trim());
        }
      }

      // 事務担当者コード ≠NULL の場合
      String jimuTantoushaCode = searchCondition.getTxtJimuTantoushaCode()
          .trim();
      if (!jimuTantoushaCode.equals("")) {
        // 事務担当者コードをゼロ編集する
        jimuTantoushaCode = Util.addLeadingZeros(jimuTantoushaCode, 8);

        // 担当者マスタから担当者名称を取得する
        UserData jimuUserData = commonGetData.getUserData(jimuTantoushaCode,
            jigyouShoCode);
        if (jimuUserData.getMsgCd() != null) {
          // 該当レコードが存在しない場合、入力チェックフラグにTrueをセット
          inputCheckFlag = true;
          strErrorId += "txtJimuTantoushaCode" + MstConst.DELIMITER_ERROR;
        } else {
          // 該当レコードが存在する場合、画面表示を行う
          searchCondition.setTxtJimuTantoushaName(jimuUserData.getUsr().getUserNm()
              .trim());
        }
      }

      // 存在チェックフラグ確認
      // 入力チェックフラグ ＝ True の場合、エラーとする
      if (inputCheckFlag) {
        Mst0102d01CustomerList tempObj = new Mst0102d01CustomerList();
        tempObj.setChainCode(chainCode);
        tempObj.setChainEda(chainEda);
        tempObj.setChainName(searchCondition.getTxtChainName() == null ? ""
            : searchCondition.getTxtChainName().trim());
        tempObj.setEigyouTantoushaCode(eigyouTantoushaCode);
        tempObj.setEigyouTantoushaName(searchCondition
            .getTxtEigyouTantoushaName() == null ? ""
                : searchCondition.getTxtEigyouTantoushaName().trim());
        tempObj.setJimuTantoushaCode(jimuTantoushaCode);
        tempObj.setJimuTantoushaName(searchCondition
            .getTxtJimuTantoushaName() == null ? ""
                : searchCondition.getTxtJimuTantoushaName().trim());
        tempObj.setErrorIdString(strErrorId);
        tempObj.setSearchResult("input_error");
        customerList = new ArrayList<Mst0102d01CustomerList>();
        customerList.add(tempObj);
      } else {
        // データ取得開始
        Map<String, Object> parms = new HashMap<String, Object>();
        parms.put("searchMax", "TOP " + String.valueOf(searchCondition
            .getSearchMax() + 1));
        parms.put("jigyouShoCode", jigyouShoCode);
        parms.put("chainCode", chainCode);
        parms.put("chainEda", chainEda);
        parms.put("customerCode", searchCondition.getTxtCustomerCode().trim());
        if (!searchCondition.getTxtCustomerName().trim().equals("")) {
          parms.put("customerName", "%" + searchCondition.getTxtCustomerName()
              .trim() + "%");
        }
        parms.put("eigyouTantoushaCode", eigyouTantoushaCode);
        parms.put("jimuTantoushaCode", jimuTantoushaCode);
        parms.put("customerType", searchCondition.getDdlCustomerType().trim());
        parms.put("uchizeiKokyakuKubun", searchCondition
            .getDdlUchiZeiKoKyakuKubun().trim());
        parms.put("chkCancelData", searchCondition.isChkCancelData() ? 1 : 0);
        parms.put("chkCustomer", searchCondition.isChkCustomer() ? 1 : 0);
        parms.put("chkBilling", searchCondition.isChkBilling() ? 1 : 0);
        parms.put("chkCustomerBilling", searchCondition.isChkCustomerBilling()
            ? 1 : 0);
        parms.put("businessDate", searchCondition.getBusinessDate());
        parms.put("delFlg", CommonConst.DEL_FLG_OFF);
        parms.put("stsCode", CommonConst.STS_CD_ENTRY);
        customerList = mst0102d01Dao.getMst0102d01Mapper().getCustomerList(
            parms);
        // データの存在をチェックする
        if (customerList == null || customerList.isEmpty()) {
          // エラーメッセージを表示する
          Mst0102d01CustomerList tempObj = new Mst0102d01CustomerList();
          tempObj.setChainCode(chainCode);
          tempObj.setChainEda(chainEda);
          tempObj.setChainName(searchCondition.getTxtChainName() == null ? ""
              : searchCondition.getTxtChainName().trim());
          tempObj.setEigyouTantoushaCode(eigyouTantoushaCode);
          tempObj.setEigyouTantoushaName(searchCondition
              .getTxtEigyouTantoushaName() == null ? ""
                  : searchCondition.getTxtEigyouTantoushaName().trim());
          tempObj.setJimuTantoushaCode(jimuTantoushaCode);
          tempObj.setJimuTantoushaName(searchCondition
              .getTxtJimuTantoushaName() == null ? ""
                  : searchCondition.getTxtJimuTantoushaName().trim());
          tempObj.setSearchResult("error");
          tempObj.setErrorMessage(readPropertiesFileService.getMessage(
              "COM025-E", null));
          customerList = new ArrayList<Mst0102d01CustomerList>();
          customerList.add(tempObj);
        } else {
          Mst0102d01CustomerList tempObj = new Mst0102d01CustomerList();
          // 最大検索件数チェック
          if (customerList.size() >= (searchCondition.getSearchMax() + 1)) {
            // エラーメッセージを表示する
            paramMess = new ArrayList<String>();
            paramMess.add(String.valueOf(searchCondition.getSearchMax()));
            tempObj.setErrorMessage(readPropertiesFileService.getMessage(
                "COM005-W", paramMess));
          }
          tempObj.setChainCode(chainCode);
          tempObj.setChainEda(chainEda);
          tempObj.setChainName(searchCondition.getTxtChainName() == null ? ""
              : searchCondition.getTxtChainName());
          tempObj.setEigyouTantoushaCode(eigyouTantoushaCode);
          tempObj.setEigyouTantoushaName(searchCondition
              .getTxtEigyouTantoushaName() == null ? ""
                  : searchCondition.getTxtEigyouTantoushaName());
          tempObj.setJimuTantoushaCode(jimuTantoushaCode);
          tempObj.setJimuTantoushaName(searchCondition
              .getTxtJimuTantoushaName() == null ? ""
                  : searchCondition.getTxtJimuTantoushaName());
          tempObj.setHaitaDate(DateUtil.getSysDate());
          tempObj.setHaitaTime(DateUtil.getSysTime());
          customerList.add(tempObj);
        }
      }
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }

    return customerList;
  }

  /**
   * 得意先一覧情報を取得する.
   * 
   * @param searchCondition ： 検索条件オブジェクト
   * @return Mst0102d01CustomerListオブジェクト
   */
  public List<Mst0102d01CustomerList> getCustomerList(
      FormMst0102d01 searchCondition) {
    // メッセージのParaml
    ArrayList<String> paramMess = new ArrayList<String>();
    // 変数定義
    String jigyouShoCode = "";
    List<Mst0102d01CustomerList> customerList = null;

    // （1）事業所判定
    if (searchCondition.getSysAdminFlag().equals(
        CommonConst.SYS_ADMIN_FLG_VALID)) {
      // システム管理者フラグ ＝ '1'（システム管理者）の場合
      jigyouShoCode = searchCondition.getDdlJigyouSho().trim();
    } else if (searchCondition.getSysAdminFlag().equals(
        CommonConst.SYS_ADMIN_FLG_INVALID)) {
      // システム管理者フラグ ＝ '0'（一般ユーザ）の場合
      jigyouShoCode = searchCondition.getLoginJigyouShoCode().trim();
    }

    try {
      // データ取得開始
      Map<String, Object> parms = new HashMap<String, Object>();
      parms.put("searchMax", "TOP " + String.valueOf(searchCondition
          .getSearchMax() + 1));
      parms.put("jigyouShoCode", jigyouShoCode);
      parms.put("chainCode", searchCondition.getTxtChainCode().trim());
      parms.put("chainEda", searchCondition.getTxtChainEda().trim());
      parms.put("customerCode", searchCondition.getTxtCustomerCode().trim());
      if (!searchCondition.getTxtCustomerName().trim().equals("")) {
        parms.put("customerName", "%" + searchCondition.getTxtCustomerName()
            .trim() + "%");
      }
      parms.put("eigyouTantoushaCode", searchCondition
          .getTxtEigyouTantoushaCode().trim());
      parms.put("jimuTantoushaCode", searchCondition.getTxtJimuTantoushaCode()
          .trim());
      parms.put("customerType", searchCondition.getDdlCustomerType().trim());
      parms.put("uchizeiKokyakuKubun", searchCondition
          .getDdlUchiZeiKoKyakuKubun().trim());
      parms.put("chkCancelData", searchCondition.isChkCancelData() ? 1 : 0);
      parms.put("chkCustomer", searchCondition.isChkCustomer() ? 1 : 0);
      parms.put("chkBilling", searchCondition.isChkBilling() ? 1 : 0);
      parms.put("chkCustomerBilling", searchCondition.isChkCustomerBilling()
          ? 1 : 0);
      parms.put("businessDate", searchCondition.getBusinessDate());
      parms.put("delFlg", CommonConst.DEL_FLG_OFF);
      parms.put("stsCode", CommonConst.STS_CD_ENTRY);
      customerList = mst0102d01Dao.getMst0102d01Mapper().getCustomerList(parms);
      // データの存在をチェックする
      if (customerList == null || customerList.isEmpty()) {
        // エラーメッセージを表示する
        Mst0102d01CustomerList tempObj = new Mst0102d01CustomerList();
        tempObj.setSearchResult("error");
        customerList = new ArrayList<Mst0102d01CustomerList>();
        customerList.add(tempObj);
      } else {
        Mst0102d01CustomerList tempObj = new Mst0102d01CustomerList();
        // 最大検索件数チェック
        if (customerList.size() >= (searchCondition.getSearchMax() + 1)) {
          // エラーメッセージを表示する
          paramMess = new ArrayList<String>();
          paramMess.add(String.valueOf(searchCondition.getSearchMax()));
          tempObj.setErrorMessage(readPropertiesFileService.getMessage(
              "COM005-W", paramMess));
        }
        tempObj.setHaitaDate(DateUtil.getSysDate());
        tempObj.setHaitaTime(DateUtil.getSysTime());
        customerList.add(tempObj);
      }
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }

    return customerList;
  }

  /**
   * 得意先一覧情報をCSVファイルに出力する.
   * 
   * @param searchCondition ： 検索条件オブジェクト
   * @return Mst0102d01ExportCSVオブジェクト
   */
  public Mst0102d01ExportCsv exportCsv(FormMst0102d01 searchCondition)
      throws Exception {
    // 共通データ取得を初期化
    CommonGetData commonGetData = new CommonGetData(commonDao,
        readPropertiesFileService);

    // 変数定義
    String strErrorId = "";
    boolean inputCheckFlag = false;
    String jigyouShoCode = "";

    Mst0102d01ExportCsv exportCsvObj = new Mst0102d01ExportCsv();
    String currentDate = DateUtil.getSysDate();
    String currentTime = DateUtil.getSysTime();
    // ファイル名
    String pathFile = "Mst01-02d011_" + currentDate + currentTime + ".csv";
    // ＣＳＶファイルを保存する場所
    String pathFolder = readPropertiesFileService.getSetting("PATH_CSV");
    String folderFile = pathFolder + "\\" + pathFile;
    // 既存のファイルをチェックする。
    File folderFileCheck = new File(pathFolder);
    if (!folderFileCheck.exists()) {
      folderFileCheck.mkdirs();
    }
    // マスタ参照項目の存在チェックを行う
    // （1）事業所判定
    if (searchCondition.getSysAdminFlag().equals(
        CommonConst.SYS_ADMIN_FLG_VALID)) {
      // システム管理者フラグ ＝ '1'（システム管理者）の場合
      jigyouShoCode = searchCondition.getDdlJigyouSho();
    } else if (searchCondition.getSysAdminFlag().equals(
        CommonConst.SYS_ADMIN_FLG_INVALID)) {
      // システム管理者フラグ ＝ '0'（一般ユーザ）の場合
      jigyouShoCode = searchCondition.getLoginJigyouShoCode();
    }

    // チェーンコード ≠ NULL and チェーン枝番 ≠ NULL の場合、
    String chainCode = searchCondition.getTxtChainCode().trim();
    String chainEda = searchCondition.getTxtChainEda().trim();
    if (!chainCode.equals("") && !chainEda.equals("")) {
      // チェーンコード、チェーン枝番をゼロ編集する
      chainCode = Util.addLeadingZeros(chainCode, 4);
      chainEda = Util.addLeadingZeros(chainEda, 2);
      exportCsvObj.setChainCode(chainCode);
      exportCsvObj.setChainEda(chainEda);
      // チェーンマスタからチェーン名称を取得する
      Map<String, Object> parms = new HashMap<String, Object>();
      parms.put("chainCode", chainCode);
      parms.put("chainEda", chainEda);
      parms.put("delFlg", CommonConst.DEL_FLG_OFF);
      parms.put("businessDate", searchCondition.getBusinessDate());
      String chainName = mst0102d01Dao.getMst0102d01Mapper().getChainName(
          parms);
      // 該当レコードが存在する場合、画面表示を行う
      if (chainName != null) {
        exportCsvObj.setChainName(chainName);
      } else { // 該当レコードが存在しない場合、入力チェックフラグにTrueをセット
        inputCheckFlag = true;
        strErrorId += "txtChainCode" + MstConst.DELIMITER_ERROR
            + "txtChainEda" + MstConst.DELIMITER_ERROR;
      }
    }

    // 営業担当者コード ≠ NULL の場合
    String eigyouTantoushaCode = searchCondition.getTxtEigyouTantoushaCode()
        .trim();
    if (!eigyouTantoushaCode.equals("")) {
      // 営業担当者コードをゼロ編集する
      eigyouTantoushaCode = Util.addLeadingZeros(eigyouTantoushaCode, 8);
      exportCsvObj.setEigyouTantoushaCode(eigyouTantoushaCode);

      // 担当者マスタから担当者名称を取得する
      UserData eigyouUserData = commonGetData.getUserData(eigyouTantoushaCode,
          jigyouShoCode);
      if (eigyouUserData.getMsgCd() != null) {
        // 該当レコードが存在しない場合、入力チェックフラグにTrueをセット
        inputCheckFlag = true;
        strErrorId += "txtEigyouTantoushaCode" + MstConst.DELIMITER_ERROR;
      } else {
        // 該当レコードが存在する場合、画面表示を行う
        exportCsvObj.setEigyouTantoushaName(eigyouUserData.getUsr().getUserNm()
            .trim());
      }
    }

    // 事務担当者コード ≠NULL の場合
    String jimuTantoushaCode = searchCondition.getTxtJimuTantoushaCode()
        .trim();
    if (!jimuTantoushaCode.equals("")) {
      // 事務担当者コードをゼロ編集する
      jimuTantoushaCode = Util.addLeadingZeros(jimuTantoushaCode, 8);
      exportCsvObj.setJimuTantoushaCode(jimuTantoushaCode);

      // 担当者マスタから担当者名称を取得する
      UserData jimuUserData = commonGetData.getUserData(jimuTantoushaCode,
          jigyouShoCode);
      if (jimuUserData.getMsgCd() != null) {
        // 該当レコードが存在しない場合、入力チェックフラグにTrueをセット
        inputCheckFlag = true;
        strErrorId += "txtJimuTantoushaCode" + MstConst.DELIMITER_ERROR;
      } else {
        // 該当レコードが存在する場合、画面表示を行う
        exportCsvObj.setJimuTantoushaName(jimuUserData.getUsr().getUserNm()
            .trim());
      }
    }

    // 存在チェックフラグ確認
    // 入力チェックフラグ ＝ True の場合、エラーとする
    if (inputCheckFlag) {
      exportCsvObj.setErrorIdString(strErrorId);
      exportCsvObj.setSearchResult("input_error");
    } else {
      // CSVファイルを作成する
      OutputStreamWriter fileWriter = new OutputStreamWriter(
          new FileOutputStream(
              folderFile), "Windows-31J");

      CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(
          CommonConst.NEW_LINE_SEPARATOR);
      CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
      Object[] fileHeader = {"No", "得意先コード", "チェーンコード", "チェーン枝番", "得意先フラグ",
          "請求先フラグ", "請求先コード", "得意先名称", "得意先名称カナ", "得意先略称", "郵便番号", "住所１",
          "住所２", "電話番号", "FAX番号", "得意先担当者", "得意先担当者メールアドレス", "幹事事業所コード",
          "得意先種別", "業態区分", "納品センターコード", "関係会社種別", "関係会社補助科目", "採番区分", "店舗区分",
          "YG取引区分", "内税顧客区分", "内税消費税端数処理", "集金有無", "現金集金マーク印字", "集計出力FLG",
          "手入力伝票発行", "手入力出荷伝票帳票ID", "手入力出荷伝票種別", "伝票行計算金額まるめ", "出荷伝票出力品コード",
          "請求先名称", "請求先名称カナ", "請求先略称", "請求先郵便番号", "請求先住所１", "請求先住所２",
          "請求先電話番号", "請求先FAX番号", "請求単位", "請求書単価", "請求書種類帳票ID", "請求書種別",
          "取纏め請求", "取り纏め請求事業所", "請求書パターン", "請求書住所印字", "請求集計表区分", "消費税計算単位",
          "消費税端数処理", "請求チェックリスト　出力対象", "請求チェックリスト　出力順", "締日１", "回収月区分１",
          "回収日１", "締日２", "回収月区分２", "回収日２", "入金種別", "入金口座", "手形サイト",
          "受領データ突合区分", "請求データ区分", "修正データ突合区分", "修正データ種別_返品", "修正データ種別_欠品",
          "修正データ種別_修正", "請求先支払データ区分", "取引コード", "分類コード（定番、店直）", "伝票区分（定番、店直）",
          "分類コード（定番、センター）", "伝票区分（定番、センター）", "分類コード（特売、店直）", "伝票区分（特売、店直）",
          "分類コード（特売、センター）", "伝票区分（特売、センター）", "請求データ配信ＩＤ", "集計コード１", "集計コード２",
          "使用中止日", "状況コード", "送信済みフラグ", "得意先コード", "得意先事業所コード", "営業担当者コード",
          "事務担当者コード", "チェーン名", "事業所名", "営業担当者氏名", "事務担当者氏名", "得意先種別名称",
          "業態区分名称", "関係会社種別名称", "採番区分名称", "店舗区分名称", "YG取引区分名称", "内税顧客区分名称",
          "内税消費税端数処理名称", "集金有無名称", "現金集金マーク印字名称", "集計出力FLG名称", "手入力伝票発行名称",
          "伝票行計算金額まるめ", "出荷伝票出力品コード", "請求単位", "請求書単価", "請求書種類", "取締め請求",
          "請求書パターン", "請求書住所印字", "消費税計算単位", "消費税端数処理", "請求チェックリスト_出力対象",
          "請求チェックリスト_出力順", "回収月区分１", "回収月区分２", "入金種別", "入金口座", "受領データ突合区分",
          "請求データ区分", "修正データ突合区分", "請求先支払データ区分"};
      csvFilePrinter.printRecord(fileHeader);
      SeqProc seqProc = new SeqProc(csvFilePrinter);
      try {
        // 条件をセットする
        Map<String, Object> parms = new HashMap<String, Object>();
        parms.put("jigyouShoCode", jigyouShoCode);
        parms.put("chainCode", chainCode);
        parms.put("chainEda", chainEda);
        parms.put("customerCode", searchCondition.getTxtCustomerCode().trim());
        if (!searchCondition.getTxtCustomerName().trim().equals("")) {
          parms.put("customerName", "%" + searchCondition.getTxtCustomerName()
              .trim() + "%");
        }
        parms.put("eigyouTantoushaCode", eigyouTantoushaCode);
        parms.put("jimuTantoushaCode", jimuTantoushaCode);
        parms.put("customerType", searchCondition.getDdlCustomerType().trim());
        parms.put("uchizeiKokyakuKubun", searchCondition
            .getDdlUchiZeiKoKyakuKubun().trim());
        parms.put("chkCancelData", searchCondition.isChkCancelData() ? 1 : 0);
        parms.put("chkCustomer", searchCondition.isChkCustomer() ? 1 : 0);
        parms.put("chkBilling", searchCondition.isChkBilling() ? 1 : 0);
        parms.put("chkCustomerBilling", searchCondition.isChkCustomerBilling()
            ? 1 : 0);
        parms.put("businessDate", searchCondition.getBusinessDate());
        parms.put("delFlg", CommonConst.DEL_FLG_OFF);
        parms.put("stsCode", CommonConst.STS_CD_ENTRY);

        mst0102d01Dao.getMst0102d01Mapper().getCsvOutputData(parms, seqProc);
        if (seqProc.isEmpty) {
          fileWriter.flush();
          fileWriter.close();
          csvFilePrinter.close();
          File fileTemp = new File(folderFile);
          if (fileTemp.exists() && !fileTemp.isDirectory()) {
            fileTemp.delete();
          }
          // エラーメッセージを表示する
          exportCsvObj.setSearchResult("error");
          exportCsvObj.setErrorMessage(readPropertiesFileService.getMessage(
              "COM025-E", new ArrayList<String>()));
        }
      } catch (Exception e) {
        logger.error(e.getMessage());
        throw e;
      } finally {
        try {
          if (!seqProc.isEmpty) {
            fileWriter.flush();
            fileWriter.close();
            csvFilePrinter.close();
          }
        } catch (IOException e) {
          logger.error(e.getMessage());
          throw e;
        }
      }
      exportCsvObj.setCsvPath(readPropertiesFileService.getSetting(
          "URL_CSV_DOWNLOAD") + pathFile);
    }

    return exportCsvObj;
  }

  /**
   * SQL実行結果を1レコードずつ処理するための内部クラス
   *
   * @author TSUZUKI DENKI
   * @version 1.00
   * @since BSS 1.0.0
   *
   */
  private class SeqProc implements ResultHandler {

    /** CSVプリンタ. */
    private CSVPrinter printer;

    /** 処理結果が空. */
    private boolean isEmpty;

    /** 連番. */
    private int intRenban;

    /**
     * コンストラクタ.
     * 
     * @param printer:CSVプリンタ
     */
    public SeqProc(CSVPrinter printer) {
      this.printer = printer;
      this.setEmpty(true);
      intRenban = 0;
    }

    /**
     * 検索結果を1レコードずつ取得し、CSVに出力する.
     *
     * @param context リザルトコンテキスト
     */
    @Override
    public void handleResult(ResultContext context) {
      intRenban++;
      this.setEmpty(false);

      Mst0102d01ExportCsvDataObject data = (Mst0102d01ExportCsvDataObject) context
          .getResultObject();

      try {
        printer.print(intRenban);
        printer.print(Util.convertUnsanitize(data.getCustCode()));
        printer.print(Util.convertUnsanitize(data.getCainCode()));
        printer.print(Util.convertUnsanitize(data.getCainIdx()));
        printer.print(Util.convertUnsanitize(data.getCustFlg()));
        printer.print(Util.convertUnsanitize(data.getBildFlg()));
        printer.print(Util.convertUnsanitize(data.getBildCode()));
        printer.print(Util.convertUnsanitize(data.getCustNm()));
        printer.print(Util.convertUnsanitize(data.getCustNmKana()));
        printer.print(Util.convertUnsanitize(data.getCustNmR()));
        printer.print(Util.convertUnsanitize(data.getZipCode()));
        printer.print(Util.convertUnsanitize(data.getAdr1()));
        printer.print(Util.convertUnsanitize(data.getAdr2()));
        printer.print(Util.convertUnsanitize(data.getTelNo()));
        printer.print(Util.convertUnsanitize(data.getFaxNo()));
        printer.print(Util.convertUnsanitize(data.getCustTan()));
        printer.print(Util.convertUnsanitize(data.getCustTanMail()));
        printer.print(Util.convertUnsanitize(data.getManagerJigyoCode()));
        printer.print(Util.convertUnsanitize(data.getCustCls()));
        printer.print(Util.convertUnsanitize(data.getGyotaiKb()));
        printer.print(Util.convertUnsanitize(data.getDeliCenterCode()));
        printer.print(Util.convertUnsanitize(data.getRelComCls()));
        printer.print(Util.convertUnsanitize(data.getRelComSub()));
        printer.print(Util.convertUnsanitize(data.getDatIdxKb()));
        printer.print(Util.convertUnsanitize(data.getShopKb()));
        printer.print(Util.convertUnsanitize(data.getYgKb()));
        printer.print(Util.convertUnsanitize(data.getTaxIncKb()));
        printer.print(Util.convertUnsanitize(data.getTaxIncFrcKb()));
        printer.print(Util.convertUnsanitize(data.getColMonKb()));
        printer.print(Util.convertUnsanitize(data.getColMmrkKb()));
        printer.print(Util.convertUnsanitize(data.getSumsFlg()));
        printer.print(Util.convertUnsanitize(data.getShipsKb()));
        printer.print(Util.convertUnsanitize(data.getShipsTypId()));
        printer.print(Util.convertUnsanitize(data.getShipsTypCls()));
        printer.print(Util.convertUnsanitize(data.getShipsRudKb()));
        printer.print(Util.convertUnsanitize(data.getShipsCodeKb()));
        printer.print(Util.convertUnsanitize(data.getBildNm()));
        printer.print(Util.convertUnsanitize(data.getBildNmKana()));
        printer.print(Util.convertUnsanitize(data.getBildNmR()));
        printer.print(Util.convertUnsanitize(data.getBildZipCode()));
        printer.print(Util.convertUnsanitize(data.getBildAdr1()));
        printer.print(Util.convertUnsanitize(data.getBildAdr2()));
        printer.print(Util.convertUnsanitize(data.getBildTelNo()));
        printer.print(Util.convertUnsanitize(data.getBildFaxNo()));
        printer.print(Util.convertUnsanitize(data.getBildUntKb()));
        printer.print(Util.convertUnsanitize(data.getBildTanka()));
        printer.print(Util.convertUnsanitize(data.getBildTypId()));
        printer.print(Util.convertUnsanitize(data.getBildTypCls()));
        printer.print(Util.convertUnsanitize(data.getSumBildKb()));
        printer.print(Util.convertUnsanitize(data.getSumBildJgyo()));
        printer.print(Util.convertUnsanitize(data.getBildPtn()));
        printer.print(Util.convertUnsanitize(data.getBildAdrOutKb()));
        printer.print(Util.convertUnsanitize(data.getBildSumKb()));
        printer.print(Util.convertUnsanitize(data.getTaxUntKb()));
        printer.print(Util.convertUnsanitize(data.getTaxFrcKb()));
        printer.print(Util.convertUnsanitize(data.getBildChkKb()));
        printer.print(Util.convertUnsanitize(data.getBildChkSrt()));
        printer.print(Util.convertUnsanitize(data.getTotalDate1()));
        printer.print(Util.convertUnsanitize(data.getColTermKb1()));
        printer.print(Util.convertUnsanitize(data.getColDate1()));
        printer.print(Util.convertUnsanitize(data.getTotalDate2()));
        printer.print(Util.convertUnsanitize(data.getColTermKb2()));
        printer.print(Util.convertUnsanitize(data.getColDate2()));
        printer.print(Util.convertUnsanitize(data.getRcvmCls()));
        printer.print(Util.convertUnsanitize(data.getRcvmAcc()));
        printer.print(Util.convertUnsanitize(data.getReceNoteSite()));
        printer.print(Util.convertUnsanitize(data.getRcvDatKb()));
        printer.print(Util.convertUnsanitize(data.getBildDatKb()));
        printer.print(Util.convertUnsanitize(data.getModDatKb()));
        printer.print(Util.convertUnsanitize(data.getModKbHpn()));
        printer.print(Util.convertUnsanitize(data.getModKbKpn()));
        printer.print(Util.convertUnsanitize(data.getModKbSsi()));
        printer.print(Util.convertUnsanitize(data.getPayDatKb()));
        printer.print(Util.convertUnsanitize(data.getTrCode()));
        printer.print(Util.convertUnsanitize(data.getBnCodeStS()));
        printer.print(Util.convertUnsanitize(data.getDnKbStS()));
        printer.print(Util.convertUnsanitize(data.getBnCodeStC()));
        printer.print(Util.convertUnsanitize(data.getDnKbStC()));
        printer.print(Util.convertUnsanitize(data.getBnCodeSpS()));
        printer.print(Util.convertUnsanitize(data.getDnKbSpS()));
        printer.print(Util.convertUnsanitize(data.getBnCodeSpC()));
        printer.print(Util.convertUnsanitize(data.getDnKbSpC()));
        printer.print(Util.convertUnsanitize(data.getBildDatId()));
        printer.print(Util.convertUnsanitize(data.getSumCode1()));
        printer.print(Util.convertUnsanitize(data.getSumCode2()));
        printer.print(Util.convertUnsanitize(data.getCloseDate()));
        printer.print(Util.convertUnsanitize(data.getStsCode()));
        printer.print(Util.convertUnsanitize(data.getSndFlg()));
        printer.print(Util.convertUnsanitize(data.getCustJigyoCustCode()));
        printer.print(Util.convertUnsanitize(data.getCustJigyoJigyoCode()));
        printer.print(Util.convertUnsanitize(data.getCustJigyoEgtCode()));
        printer.print(Util.convertUnsanitize(data.getCustJigyoJmtTanCode()));
        printer.print(Util.convertUnsanitize(data.getSchainChnMei()));
        printer.print(Util.convertUnsanitize(data.getSjigyoJgyMei()));
        printer.print(Util.convertUnsanitize(data.getUser1UserNm()));
        printer.print(Util.convertUnsanitize(data.getUser2UserNm()));
        printer.print(Util.convertUnsanitize(data.getGenCustCls()));
        printer.print(Util.convertUnsanitize(data.getGenGyotaiKb()));
        printer.print(Util.convertUnsanitize(data.getGenRelComCls()));
        printer.print(Util.convertUnsanitize(data.getGenDatIdxKb()));
        printer.print(Util.convertUnsanitize(data.getGenShopKb()));
        printer.print(Util.convertUnsanitize(data.getGenYgKb()));
        printer.print(Util.convertUnsanitize(data.getGenTaxIncKb()));
        printer.print(Util.convertUnsanitize(data.getGenTaxIncFrcKb()));
        printer.print(Util.convertUnsanitize(data.getGenColMonKb()));
        printer.print(Util.convertUnsanitize(data.getGenColMmrkKb()));
        printer.print(Util.convertUnsanitize(data.getGenSumsFlg()));
        printer.print(Util.convertUnsanitize(data.getGenShipsKb()));
        printer.print(Util.convertUnsanitize(data.getGenShipsRudKb()));
        printer.print(Util.convertUnsanitize(data.getGenShipsCodeKb()));
        printer.print(Util.convertUnsanitize(data.getGenBildUntKb()));
        printer.print(Util.convertUnsanitize(data.getGenBildTanka()));
        printer.print(Util.convertUnsanitize(data.getGenBildTyp()));
        printer.print(Util.convertUnsanitize(data.getGenSumBildKb()));
        printer.print(Util.convertUnsanitize(data.getGenBildPtn()));
        printer.print(Util.convertUnsanitize(data.getGenBildAdrOutKb()));
        printer.print(Util.convertUnsanitize(data.getGenTaxUntKb()));
        printer.print(Util.convertUnsanitize(data.getGenTaxFrcKb()));
        printer.print(Util.convertUnsanitize(data.getGenBildChkKb()));
        printer.print(Util.convertUnsanitize(data.getGenBildChkSrt()));
        printer.print(Util.convertUnsanitize(data.getGenColTermKb1()));
        printer.print(Util.convertUnsanitize(data.getGenColTermKb2()));
        printer.print(Util.convertUnsanitize(data.getGenRcvmCls()));
        printer.print(Util.convertUnsanitize(data.getGenRcvmAcc()));
        printer.print(Util.convertUnsanitize(data.getGenRcvDatKb()));
        printer.print(Util.convertUnsanitize(data.getGenBildDatKb()));
        printer.print(Util.convertUnsanitize(data.getGenModDatKb()));
        printer.print(Util.convertUnsanitize(data.getGenPayDatKb()));

        // 次のレコード
        printer.println();

      } catch (IOException ioe) {
        throw new RuntimeException(ioe);
      }
    }

    public void setEmpty(boolean isEmpty) {
      this.isEmpty = isEmpty;
    }
  }
}