/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl
 * ファイル名:Mst0108d02Service.java
 * 
 * <p>作成者: グエン リユオン ギア
 * 作成日:2015/11/01
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/11/16 1.00 ABV) グエン リユオン ギア 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.component.CustomerData;
import jp.co.daitoku_sh.dfcm.common.component.MstGeneralData;
import jp.co.daitoku_sh.dfcm.common.component.ShopData;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCustTanka;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCustTankaExample;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCustTankaKey;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetData;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.InputCheckCom;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.GetConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ObjCombobox;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.CastData0108d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0108d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0108d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.Mst0108d02Model;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl.Mst0108d02Dao;

@Service
public class Mst0108d02Service extends AbsService {

  @Autowired
  @Qualifier("mst0108d02Dao")
  private Mst0108d02Dao mst0108d02Dao;

  @Autowired
  @Qualifier("commonDao")
  private CommonDao commonDao;

  // メッセージPropertiesファイルを読む
  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  @Autowired
  private ApplicationContext appContext;

  /** The operational database management object */
  @Autowired
  @Qualifier("txManager")
  private PlatformTransactionManager txManager;

  /** ログ出力用. */
  private Dfcmlogger logger = new Dfcmlogger();

  /**
   * getMst0108d02Dao.
   * 
   * @return mst0108d02Dao
   */
  public Mst0108d02Dao getMst0108d02Dao() {
    return mst0108d02Dao;
  }

  /**
   * setMst0108d02Dao.
   * 
   * @param mst0108d02Dao mst0108d02Dao
   */
  public void setMst0108d02Dao(Mst0108d02Dao mst0108d02Dao) {
    this.mst0108d02Dao = mst0108d02Dao;
  }

  /**
   * getCommonDao.
   * 
   * @return commonDao
   */
  public CommonDao getCommonDao() {
    return commonDao;
  }

  /**
   * setCommonDao.
   * 
   * @param commonDao commonDao
   */
  public void setCommonDao(CommonDao commonDao) {
    this.commonDao = commonDao;
  }

  /**
   * getReadPropertiesFileService.
   * 
   * @return readPropertiesFileService
   */
  public ReadPropertiesFileService getReadPropertiesFileService() {
    return readPropertiesFileService;
  }

  /**
   * setReadPropertiesFileService.
   * 
   * @param readPropertiesFileService readPropertiesFileService
   */
  public void setReadPropertiesFileService(
      ReadPropertiesFileService readPropertiesFileService) {
    this.readPropertiesFileService = readPropertiesFileService;
  }

  /**
   * getAppContext.
   * 
   * @return appContext
   */
  public ApplicationContext getAppContext() {
    return appContext;
  }

  /**
   * setAppContext.
   * 
   * @param appContext appContext
   */
  public void setAppContext(ApplicationContext appContext) {
    this.appContext = appContext;
  }

  /**
   * getTxManager.
   * 
   * @return txManager txManager
   */
  public PlatformTransactionManager getTxManager() {
    return txManager;
  }

  /**
   * setTxManager.
   * 
   * @param txManager txManager
   */
  public void setTxManager(PlatformTransactionManager txManager) {
    this.txManager = txManager;
  }

  /**
   * Defaultメッセージの取得.
   * 
   * @param model
   *          ： フォームのModel
   */
  public void getDefaultMess(Model model) {
    // メッセージのParam
    // COM015-E エラー
    ArrayList<String> paramMess = new ArrayList<String>();

    final ArrayList<DefaultMessages> defaultMsgLst = new ArrayList<DefaultMessages>();
    paramMess = new ArrayList<String>();
    paramMess.add("業務日付の取得");
    // COM015-E
    DefaultMessages defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM015-E");
    defaultMsg.setMessageTitle("確認メッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM015-E", paramMess));
    defaultMsgLst.add(defaultMsg);
    // COM001-I
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-I");
    defaultMsg.setMessageTitle("確認メッセージ");
    paramMess = new ArrayList<String>();
    paramMess.add("登録");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-I", paramMess));
    defaultMsgLst.add(defaultMsg);
    // COM016-E
    paramMess = new ArrayList<String>();
    paramMess.add("適用期間（終了）");
    paramMess.add("適用期間（開始)");
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM016-E");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM016-E", paramMess));
    defaultMsgLst.add(defaultMsg);
    // COM006-E
    paramMess = new ArrayList<String>();
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM006-E");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM006-E", paramMess));
    defaultMsgLst.add(defaultMsg);
    // COM001-E
    paramMess = new ArrayList<String>();
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-E");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-E", paramMess));
    defaultMsgLst.add(defaultMsg);
    // MST018-E
    paramMess = new ArrayList<String>();
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("MST018-E");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "MST018-E", paramMess));
    defaultMsgLst.add(defaultMsg);
    // COM030-E
    paramMess = new ArrayList<String>();
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM030-E");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM030-E", paramMess));
    defaultMsgLst.add(defaultMsg);
    // MST001-W メッセージコード
    paramMess = new ArrayList<String>();
    paramMess.add("メッセージコード");
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("MST001-W");
    defaultMsg.setMessageTitle("確認メッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "MST001-W", paramMess));
    defaultMsgLst.add(defaultMsg);

    // COM001-I （登録画面）クリアボタン_Click
    paramMess = new ArrayList<String>();
    paramMess.add("画面表示時の状態に初期化");
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-I1");
    defaultMsg.setMessageTitle("確認メッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-I", paramMess));
    defaultMsgLst.add(defaultMsg);
    // COM009-E
    paramMess = new ArrayList<String>();
    paramMess.add("得意先品目価格マスタ");
    paramMess.add("指定された品目価格設定");
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM009-E1");
    defaultMsg.setMessageTitle("確認メッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM009-E", paramMess));
    defaultMsgLst.add(defaultMsg);
    // COM034-E
    paramMess = new ArrayList<String>();
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM034-E");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM034-E", paramMess));
    defaultMsgLst.add(defaultMsg);
    model.addAttribute("defaultMessages", defaultMsgLst);

  }

  /**
   * 【オンライン得意先変換マスタ一覧画面初期表示処理】.
   * 
   * @param model モデル
   * @param formMst0108d02 画面フォーム登録
   * @param formMst0108d01 画面フォーム一覧
   * @param custCode 顧客コード
   * @param viewMode ビューモード
   * @param itemCode 商品番号
   * @param salesKb 販売KB
   * @param shopCode 店舗コード
   * @param endTime 終了時間
   * @param sysManagerFlag SYSマネージャー旗
   * @param loginJigyouCd JigyouCDのログイン
   */
  public void init(Model model, FormMst0108d02 formMst0108d02,
      FormMst0108d01 formMst0108d01, String custCode, String viewMode,
      String itemCode, String salesKb, String shopCode,
      String endTime, String sysManagerFlag, String loginJigyouCd) {
    try {
      // メッセージのParam
      ArrayList<String> paramMess = new ArrayList<String>();

      // エラーメッセージ変数定義
      ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
      ErrorMessages errMess = new ErrorMessages();

      // 共通部品初期化
      final CommonGetSystemCom systemCom = new CommonGetSystemCom(commonDao,
          null, null, readPropertiesFileService);
      formMst0108d02.setCstCode(formMst0108d01.getStrSelectedCustCode());
      formMst0108d02.setItemCode(formMst0108d01.getStrSelectedItemCode());
      formMst0108d02.setShopCode(formMst0108d01.getStrSelectedShopCode());
      formMst0108d02.setSalesKb(formMst0108d01.getStrSelectedSaleType());
      formMst0108d02.setEndTime(formMst0108d01.getStrSelectedEndTime());

      // システム管理者フラグ値を保存する
      formMst0108d02.setSysAdminFlag(sysManagerFlag);
      // ログイン所属事業所コードを保存する
      formMst0108d02.setLoginJigyouShoCode(loginJigyouCd);
      // (1-1) [入力]画面表示モード ＝ Null の場合、画面にエラーメッセージを表示する。
      if (viewMode.equalsIgnoreCase("")) {
        // [変数]メッセージコード（COM026-E）
        paramMess = new ArrayList<String>();
        paramMess.add("画面表示モード");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM026-E",
            paramMess));
        lstErrMess.add(errMess);
        model.addAttribute("errorMessages", lstErrMess);
        return;
      }
      model.addAttribute("viewMode", viewMode);
      formMst0108d02.setViewMode(viewMode);
      // (2) [入力]画面表示モード = '2'（照会） or '3'（訂正） or '4'（取消） or '5'（コピー）
      // の時のみ、後続の引数チェックを行う。
      if (viewMode.equalsIgnoreCase(MstConst.SHOUKAI_MODE) || viewMode
          .equalsIgnoreCase(MstConst.TEISEI_MODE) || viewMode.equalsIgnoreCase(
              MstConst.TORIKESI_MODE) || viewMode.equalsIgnoreCase(
                  MstConst.COPY_MODE)) {
        // (2-1) [入力]得意先コード ＝ Null の場合、画面にエラーメッセージを表示する。
        if (custCode.equalsIgnoreCase("")) {
          // [変数]メッセージコード（COM026-E）
          paramMess = new ArrayList<String>();
          paramMess.add("得意先コード");
          errMess.setMessageContent(readPropertiesFileService.getMessage(
              "COM026-E", paramMess));
          lstErrMess.add(errMess);
          model.addAttribute("errorMessages", lstErrMess);
          return;
        }
        // (2-2) [入力]品目コード ＝ Null の場合、画面にエラーメッセージを表示する。
        if (itemCode.equalsIgnoreCase("")) {
          // [変数]メッセージコード（COM026-E）
          paramMess = new ArrayList<String>();
          paramMess.add("品目コード");
          errMess.setMessageContent(readPropertiesFileService.getMessage(
              "COM026-E", paramMess));
          lstErrMess.add(errMess);
          model.addAttribute("errorMessages", lstErrMess);
          return;
        }
        // (2-3) [入力]販売区分 ＝ Null の場合、画面にエラーメッセージを表示する。
        if (salesKb.equalsIgnoreCase("")) {
          // [変数]メッセージコード（COM026-E）
          paramMess = new ArrayList<String>();
          paramMess.add("販売区分");
          errMess.setMessageContent(readPropertiesFileService.getMessage(
              "COM026-E", paramMess));
          lstErrMess.add(errMess);
          model.addAttribute("errorMessages", lstErrMess);
          return;
        }
        // (2-4) [入力]適用期間（終了） ＝ Null の場合、画面にエラーメッセージを表示する。
        if (endTime.equalsIgnoreCase("")) {
          // [変数]メッセージコード（COM026-E）
          paramMess = new ArrayList<String>();
          paramMess.add("適用期間（終了）");
          errMess.setMessageContent(readPropertiesFileService.getMessage(
              "COM026-E", paramMess));
          lstErrMess.add(errMess);
          model.addAttribute("errorMessages", lstErrMess);
          return;
        }
        String tenCodeNone = "";
        tenCodeNone = readPropertiesFileService.getSetting(
            "TEN_CODE_NONE");
        formMst0108d02.setTenCodeNone(tenCodeNone);
      }

      // 業務日付を取得する
      String businessDate = systemCom.getAplDate();
      if (businessDate == null) {
        // 業務日付 ＝ Nullの場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("業務日付の取得");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM015-E",
            paramMess));
        lstErrMess.add(errMess);
        String errorControll = MstConst.ERRORCONTROLL;
        formMst0108d02.setErrorControll(errorControll);
        model.addAttribute("errorMessages", lstErrMess);
        return;
      } else {
        // （2-1） [変数]業務日付 ≠ Nullの場合、画面隠し項目に業務日付をセットする。
        formMst0108d02.setBussinessDate(businessDate);
      }

      setMasutaKubun_Dll(model, salesKb);

      if (shopCode.equalsIgnoreCase("")) {
        shopCode = readPropertiesFileService.getSetting(
            "TEN_CODE_NONE");
      }
      Map<String, Object> params = new HashMap<String, Object>(); // Mapper
      params.put("businessDate", businessDate);
      params.put("custCode", custCode);
      params.put("itemCode", itemCode);
      params.put("shopCode", shopCode);
      params.put("salesKb", salesKb);
      params.put("endTime", endTime);
      ArrayList<Mst0108d02Model> lstMst0108d02 = new ArrayList<Mst0108d02Model>();
      lstMst0108d02 = (ArrayList<Mst0108d02Model>) mst0108d02Dao
          .getMst0108d02Mapper().getDefautData(params);
      if (lstMst0108d02.size() == 0 && !viewMode.equalsIgnoreCase(MstConst.SHINKI_MODE)) {
        lstMst0108d02 = null;
        // 業務日付 ＝ Nullの場合、エラーとする
        paramMess = new ArrayList<String>();
        paramMess.add("得意先品目価格マスタ");
        paramMess.add("得意先品目価格マスタ一覧画面で指定された品目");
        errMess.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E",
            paramMess));
        lstErrMess.add(errMess);
        String errorControll = MstConst.ERRORCONTROLL;
        formMst0108d02.setErrorControll(errorControll);
        model.addAttribute("errorMessages", lstErrMess);
        return;
      }
      // 画面表示モードに応じ、画面表示を制御する。
      if (viewMode.equalsIgnoreCase(MstConst.SHINKI_MODE)) {
        formMst0108d02.setCstCode("");
        formMst0108d02.setCustNmR("");
        formMst0108d02.setItemCode("");
        formMst0108d02.setHinRyaKu("");
        formMst0108d02.setShopCode("");
        formMst0108d02.setShopNmR("");
        formMst0108d02.setValidFrom("");
        formMst0108d02.setValidTo("");
        formMst0108d02.setCustDeliTanka("0");
        formMst0108d02.setCustSellTanka("0");
        formMst0108d02.setCustBildTanka("0");
        formMst0108d02.setBunruiCode("");
        formMst0108d02.setStsCode("1");
        formMst0108d02.setViewMode(MstConst.SHINKI_MODE);
      }
      // [入力]画面表示モード ＝ '2' || '3' || '4' || '5'（照会） の場合
      if (!viewMode.equalsIgnoreCase(MstConst.SHINKI_MODE)) {
        for (int i = 0; i < lstMst0108d02.size(); i++) {
          formMst0108d02.setCstCode(custCode);
          formMst0108d02.setCustNmR(Util.convertSanitize(lstMst0108d02.get(i).getCustNmR()));
          String custNmR = formMst0108d02.getCustNmR();
          model.addAttribute("custNmR", custNmR);
          formMst0108d02.setItemCode(itemCode);
          formMst0108d02.setHinRyaKu(Util.convertSanitize(lstMst0108d02.get(i).getHinRyaKu()));
          String itemNmR = formMst0108d02.getHinRyaKu();
          model.addAttribute("itemNmR", itemNmR);
          if (shopCode.equalsIgnoreCase(readPropertiesFileService.getSetting(
              "TEN_CODE_NONE"))) {
            shopCode = "";
          }
          formMst0108d02.setShopCode(shopCode);

          formMst0108d02.setShopNmR(Util.convertSanitize(lstMst0108d02.get(i).getShopNmR()));
          String shopNmR = formMst0108d02.getShopNmR();
          model.addAttribute("shopNmR", shopNmR);
          String validFrom = lstMst0108d02.get(i).getValidFrom();
          validFrom = replaceDate(validFrom);
          formMst0108d02.setValidFrom(validFrom);
          endTime = replaceDate(endTime);
          formMst0108d02.setValidTo(endTime);
          float custDeliTanka = lstMst0108d02.get(i).getCustDeliTanka();
          String deliTanka = String.valueOf(custDeliTanka);
          deliTanka = addZeroForNumeric(deliTanka);

          formMst0108d02.setCustDeliTanka(deliTanka);
          formMst0108d02.setCustSellTanka(String.valueOf(lstMst0108d02.get(i)
              .getCustSellTanka()));

          float custBildTanka = lstMst0108d02.get(i).getCustBildTanka();
          String bildTanka = String.valueOf(custBildTanka);
          bildTanka = addZeroForNumeric(bildTanka);
          formMst0108d02.setCustBildTanka(bildTanka);

          formMst0108d02.setBunruiCode(lstMst0108d02.get(i).getBunruiCode());
          formMst0108d02.setMasutaKubunClassList(salesKb);
          String stsCode;
          if (viewMode.equalsIgnoreCase(MstConst.TORIKESI_MODE)) {
            stsCode = "9";
          } else {
            stsCode = lstMst0108d02.get(i).getStsCode();
          }
          formMst0108d02.setStsCode(stsCode);
          formMst0108d02.setViewMode(viewMode);
        }

      }
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }

  }

  /**
   * replaceDate.
   * 
   * @param date date
   * @return date
   */
  public String replaceDate(String date) {
    String year = date.substring(0, 4);
    String month = date.substring(4, 6);
    String day = date.substring(6, 8);
    date = year + "/" + month + "/" + day;
    return date;
  }

  /**
   * setMasutaKubun_Dll.
   * 
   * @param model モデル
   * @return type boolean
   */
  public boolean setMasutaKubun_Dll(Model model, String salesKb) {
    ArrayList<ObjCombobox> lstMstMasutaKubunInfoReturn = new ArrayList<ObjCombobox>();
    try {
      // 共通部品初期化
      CommonGetSystemCom systemCom = new CommonGetSystemCom(commonDao, null,
          null, readPropertiesFileService);
      // 得意先種別名称を取得する
      List<MstGeneralData> generalDataSaleType = systemCom.getMstGeneralConf(
          MstConst.Sale_Kb, null);
      if (generalDataSaleType != null && generalDataSaleType.size() > 0) {
        ObjCombobox firstObject = new ObjCombobox();
        firstObject.setCode("");
        firstObject.setName("");
        lstMstMasutaKubunInfoReturn.add(firstObject);
        for (MstGeneralData mstGeneralData : generalDataSaleType) {
          ObjCombobox tempObj = new ObjCombobox();
          tempObj.setCode(mstGeneralData.getGlCode());
          String code = mstGeneralData.getGlCode();
          if (code.equals(salesKb)) {
            tempObj.setName("<option value=" + mstGeneralData.getGlCode()
                + " selected='selected'>" + mstGeneralData.getGlCode() + " : "
                + mstGeneralData
                    .getTarget1() + "</option>");
          } else {
            tempObj.setName("<option value=" + mstGeneralData.getGlCode() + ">"
                + mstGeneralData.getGlCode() + " : " + mstGeneralData
                    .getTarget1() + "</option>");
          }

          lstMstMasutaKubunInfoReturn.add(tempObj);
        }
        model.addAttribute("MasutaKubunClassList", lstMstMasutaKubunInfoReturn);
        return true;
      } else {
        // COM009-E エラー
        ArrayList<String> paramMessCom009E = new ArrayList<String>();

        final ArrayList<DefaultMessages> defaultMsgLstCom009E = new ArrayList<DefaultMessages>();
        paramMessCom009E = new ArrayList<String>();
        paramMessCom009E.add("汎用マスタ");
        paramMessCom009E.add("販売区分");
        DefaultMessages defaultMsgCom009E = new DefaultMessages();
        defaultMsgCom009E.setMessageCode("COM009-E");
        defaultMsgCom009E.setMessageTitle("確認メッセージ");
        defaultMsgCom009E.setMessageContent(readPropertiesFileService
            .getMessage(
                "COM009-E", paramMessCom009E));
        defaultMsgLstCom009E.add(defaultMsgCom009E);
        model.addAttribute("errorMessages", defaultMsgLstCom009E);

        return true;
      }
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
  }

  /**
   * 配信データを取得します.
   * 
   * @param model モデル
   * @param formMst0108d02 formMst0108d02 エンティティ
   * @param custCode 顧客コード
   * @param itemCode 商品番号
   * @param shopCode 店舗コード
   * @param jigyoushoCode jigyoushoCode
   * @return masterCheckErrorFlag マスターチェックエラーフラグ
   */
  public boolean getDeliveryData(Model model, FormMst0108d02 formMst0108d02,
      String custCode, String itemCode, String shopCode,
      String jigyoushoCode) {

    String strErrorId = "";
    boolean masterCheckErrorFlag = false;
    CommonGetData commonGetData = new CommonGetData(commonDao,
        readPropertiesFileService);
    CustomerData clsCustomerInfo = commonGetData.getCustomerData(custCode,
        null, MstConst.CHECK_TYPE);
    String cutomerNm = "";
    String shopNmR = "";
    ArrayList<String> paramMess = null;
    String errMessage = "";
    // (2-1) 変数[得意先情報格納クラス].メッセージコード ＝ Null の場合
    if (clsCustomerInfo.getMsgCd() == null) {
      cutomerNm = clsCustomerInfo.getCst().getCustNmR();
      formMst0108d02.setCustNmR(cutomerNm);
      String shopKb = clsCustomerInfo.getCst().getShopKb();
      // [変数]得意先情報.店舗区分 ＝ '2' （店無し） 且つ [画面]自社店舗コード ≠ "" の場合
      if (shopKb.equalsIgnoreCase(MstConst.NOSHOP) && !shopCode.equalsIgnoreCase("")) {
        paramMess = new ArrayList<String>();
        errMessage += readPropertiesFileService.getMessage("MST014-E",
            paramMess) + "<br>";
        formMst0108d02.setErrMessage(errMessage);
        masterCheckErrorFlag = true;
        strErrorId += "cstCode" + MstConst.DELIMITER_ERROR;
        formMst0108d02.setMasterCheckErrorFlag(masterCheckErrorFlag);
      }
    } else {
      paramMess = new ArrayList<String>();
      paramMess.add("得意先マスタ");
      paramMess.add("入力された得意先");
      errMessage += readPropertiesFileService.getMessage("COM009-E", paramMess)
          + "<br>";
      formMst0108d02.setCustNmR("");
      masterCheckErrorFlag = true;
      strErrorId += "cstCode" + MstConst.DELIMITER_ERROR;
      formMst0108d02.setMasterCheckErrorFlag(masterCheckErrorFlag);
      formMst0108d02.setErrMessage(errMessage);

    }

    String itemNmR = "";
    if (!itemCode.equalsIgnoreCase("")) {
      if (clsCustomerInfo.getCst() != null) {
        Map<String, Object> params = new HashMap<String, Object>(); // Mapper
        params.put("itemCode", itemCode);
        params.put("cainCode", clsCustomerInfo.getCst().getCainCode());
        params.put("cainIdx", clsCustomerInfo.getCst().getCainIdx());
        params.put("bussinessDate", formMst0108d02.getBussinessDate());
        List<Mst0108d02Model> lstItemName = mst0108d02Dao.getMst0108d02Mapper()
            .getItemName(params);
        if (lstItemName.size() <= 0) {
          paramMess = new ArrayList<String>();
          paramMess.add("製品マスタ");
          paramMess.add("入力された品目");
          errMessage += readPropertiesFileService.getMessage("COM009-E",
              paramMess) + "<br>";
          // ③ [変数]マスタチェックエラーフラグ ＝ True
          masterCheckErrorFlag = true;
          strErrorId += "itemCode" + MstConst.DELIMITER_ERROR;
          formMst0108d02.setErrMessage(errMessage);
          formMst0108d02.setMasterCheckErrorFlag(masterCheckErrorFlag);
          formMst0108d02.setHinRyaKu("");
        } else {
          // ① [変数]品目名称 = [製品マスタ]製品略称 （※先頭レコードの製品略称）
          itemNmR = lstItemName.get(0).getHinRyaKu();
          formMst0108d02.setHinRyaKu(itemNmR);
        }
      } else {
        paramMess = new ArrayList<String>();
        paramMess.add("製品マスタ");
        paramMess.add("入力された品目");
        errMessage += readPropertiesFileService.getMessage("COM009-E",
            paramMess) + "<br>";
        // ③ [変数]マスタチェックエラーフラグ ＝ True
        masterCheckErrorFlag = true;
        strErrorId += "itemCode" + MstConst.DELIMITER_ERROR;
        formMst0108d02.setErrMessage(errMessage);
        formMst0108d02.setMasterCheckErrorFlag(masterCheckErrorFlag);
        formMst0108d02.setHinRyaKu("");
      }

    }

    // '-4. 店舗マスタ検索
    // (1) [画面]店舗コード ≠ "" の場合、店舗マスタより店舗名称を表示する。
    if (!shopCode.equalsIgnoreCase("")) {
      ShopData clsShopInfo = commonGetData.getShopData(shopCode, custCode);
      if (clsShopInfo.getMsgCd() == null) {
        shopNmR = clsShopInfo.getShp().getShopNmR();
        formMst0108d02.setShopNmR(shopNmR);
      } else {
        paramMess = new ArrayList<String>();
        paramMess.add("店舗マスタ");
        paramMess.add("入力された店舗");
        errMessage += readPropertiesFileService.getMessage("COM009-E",
            paramMess) + "<br>";
        String error = formMst0108d02.getErrMessage();
        error = error + errMessage;
        formMst0108d02.setErrMessage(error);
        masterCheckErrorFlag = true;
        strErrorId += "shopCode" + MstConst.DELIMITER_ERROR;
        formMst0108d02.setShopNmR("");
        formMst0108d02.setMasterCheckErrorFlag(masterCheckErrorFlag);
      }
    }
    formMst0108d02.setErrMessage(errMessage);
    if (!strErrorId.equalsIgnoreCase("")) {
      model.addAttribute("lstErrorID", strErrorId);
    }
    return masterCheckErrorFlag;
  }

  /**
   * 排他チェック.
   * 
   * @param strCustCode:得意先コード
   * @param strCorrectKb:訂正区分コード
   * @param strHaitaDate:排他日付
   * @param strHaitaTime:排他時間
   * @return boolean true: エラー false: 普通
   */
  public boolean checkHaita(String custcode,
      String itemCode, String shopCode, String salesKb, String endTime,
      String strHaitaDate, String strHaitaTime) {

    // 訂正情報取得
    MstCustTanka mstCustTanka = new MstCustTanka();
    if (shopCode.equalsIgnoreCase("")) {
      shopCode = readPropertiesFileService.getSetting(
          "TEN_CODE_NONE");
    }
    mstCustTanka = this.getMstCusttankaInfo(custcode, itemCode, shopCode,
        salesKb, endTime);

    // 共通項目.修正年月日 ＋ 共通項目．修正時刻 > 表示時システム日付 の場合
    int intDateRegDb = Integer.parseInt(mstCustTanka.getUpdYmd());
    int intDateHidden = Integer.parseInt(strHaitaDate);

    if (intDateRegDb > intDateHidden) {
      return true;
    } else if (intDateRegDb == intDateHidden) {
      intDateRegDb = Integer.parseInt(mstCustTanka.getUpdTime());
      intDateHidden = Integer.parseInt(strHaitaTime);
      if (intDateRegDb > intDateHidden) {
        return true;
      }
    }
    return false;
  }

  /**
   * 訂正区分マスタデータ取得.
   * 
   * @param strCustCode:得意先コード
   * @param strCorrectKb:訂正区分コード
   * @return mstCustTanka
   */
  public MstCustTanka getMstCusttankaInfo(String custcode,
      String itemCode, String shopCode, String salesKb, String endTime) {
    MstCustTanka mstCustTanka;
    MstCustTankaKey mstCustTankaKey = new MstCustTankaKey();
    mstCustTankaKey.setCustCode(custcode);
    mstCustTankaKey.setItemCode(itemCode);
    mstCustTankaKey.setShopCode(shopCode);
    mstCustTankaKey.setSalesKb(salesKb);
    mstCustTankaKey.setValidTo(endTime);

    try {
      mstCustTanka = mst0108d02Dao.getMstCustTankaMapper().selectByPrimaryKey(
          mstCustTankaKey);

      return mstCustTanka;
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
  }

  /**
   * 排他、既存の訂正区分マスタ情報のチェック
   * 
   * @param formMst0109d02:フォーム
   * @param model:モデル
   * @return type boolean
   */
  public boolean checkSpec(FormMst0108d02 formMst0108d02, Model model) {
    String strErrorId = "";
    String errMessage = formMst0108d02.getErrMessage();
    ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    ErrorMessages errMess = new ErrorMessages();
    if (this.checkHaita(formMst0108d02.getCstCode(),
        formMst0108d02.getItemCode(), formMst0108d02.getShopCode(),
        formMst0108d02.getStrSelectedSaleType(), formMst0108d02
            .getStrSelectedEndTime(),
        formMst0108d02.getHaitaDate(), formMst0108d02.getHaitaTime())) {
      errMess = new ErrorMessages();
      errMess.setMessageContent(readPropertiesFileService.getMessage(
          "COM031-E",
          null));
      errMessage += readPropertiesFileService.getMessage("COM031-E", null)
          + "<br>";
      String error = formMst0108d02.getErrMessage();
      error = error + errMessage;
      formMst0108d02.setErrMessage(error);
      lstErrMess.add(errMess);
      model.addAttribute("errorMessages", lstErrMess);
      model.addAttribute("lstErrorID", strErrorId);
      return true;
    }
    return false;
  }

  /**
   * 登録.
   * 
   * @param model モデル
   * @param formMst0108d02 画面フォーム
   * @return マスターがレコードの編集フラグを存在します
   */
  public boolean register(Model model, FormMst0108d02 formMst0108d02,
      CastData0108d02 castData0108d02,
      HttpServletRequest httpRequest) throws Exception {
    DefaultTransactionDefinition def = null;
    // Representation of the status of a transaction.
    TransactionStatus status = null;
    ArrayList<String> paramMess = null;
    String viewMode = formMst0108d02.getViewMode();
    boolean masterExistRecordEditFlag;
    masterExistRecordEditFlag = false;
    String errMessage = "";
    // セッションのデータを取得する
    Map<String, Object> sessionData = Util.getContentsFromSession(httpRequest);
    // システム管理者フラグ値を取得する
    String strLoginUserCode = String.valueOf(sessionData.get("UserCode"));
    paramMess = new ArrayList<String>();
    if (viewMode.equalsIgnoreCase(MstConst.TEISEI_MODE)) {
      try {
        MstCustTanka mstCustTanka = new MstCustTanka();
        mstCustTanka = setMstCustTankaforUpdate(formMst0108d02,
            strLoginUserCode, castData0108d02, httpRequest);
        boolean processResult = false;
        def = new DefaultTransactionDefinition();
        status = txManager.getTransaction(def);
        if (mstCustTanka != null) {
          mst0108d02Dao.getMstCustTankaMapper()
              .updateByPrimaryKeySelective(mstCustTanka);
          txManager.commit(status);
          processResult = true;
          formMst0108d02.setSuccess(1);
          setHaitaDate(formMst0108d02);
          // COM002-I
          paramMess = new ArrayList<String>();
          paramMess.add("得意先品目価格マスタの登録");
          errMessage += readPropertiesFileService.getMessage("COM002-I",
              paramMess) + "<br>";
          formMst0108d02.setErrMessage(errMessage);
          return processResult;
        }

      } catch (MyBatisSystemException e) {
        // Perform a rollback of the given transaction.
        txManager.rollback(status);
        logger.error(e.getMessage());
        throw e;
      }
    } else if (viewMode.equalsIgnoreCase(MstConst.TORIKESI_MODE)) {
      try {
        def = new DefaultTransactionDefinition();
        status = txManager.getTransaction(def);
        MstCustTanka mstCustTanka = new MstCustTanka();
        mstCustTanka = setMstCustTankaforDelete(formMst0108d02,
            strLoginUserCode, castData0108d02, httpRequest);
        boolean processResult = false;

        if (mstCustTanka != null) {
          mst0108d02Dao.getMstCustTankaMapper()
              .updateByPrimaryKeySelective(mstCustTanka);
          txManager.commit(status);
          processResult = true;
          formMst0108d02.setSuccess(1);
          setHaitaDate(formMst0108d02);
          // COM002-I
          paramMess = new ArrayList<String>();
          paramMess.add("得意先品目価格マスタの登録");
          errMessage += readPropertiesFileService.getMessage("COM002-I",
              paramMess) + "<br>";
          formMst0108d02.setErrMessage(errMessage);
          return processResult;
        }
      } catch (MyBatisSystemException e) {
        // Perform a rollback of the given transaction.
        txManager.rollback(status);
        logger.error(e.getMessage());
        throw e;
      }
    }
    model.addAttribute("infoMessFlag", "true");
    return masterExistRecordEditFlag;
  }

  /**
   * setMstCustTankaforInsert 挿入のためにMstCustTankaを設定.
   * 
   * @param formMst0108d02 画面フォーム
   * @return mstCustTanka
   * @throws ParseException e
   */
  public MstCustTanka setMstCustTankaforInsert(FormMst0108d02 formMst0108d02,
      String strLoginUserCode, CastData0108d02 castData0108d02, HttpServletRequest httpRequest)
          throws ParseException {

    String custBildTanka = formMst0108d02.getCustBildTanka();
    if (custBildTanka.equalsIgnoreCase("")) {
      custBildTanka = "0";
    }
    formMst0108d02.setCustBildTanka(custBildTanka);

    formMst0108d02.setMasterExistRecordEditFlag(true);
    // (1) 画面入力値で、適用期間が重複している得意先品目価格マスタの検索を行う。
    this.searchInforCustTanka(formMst0108d02,
        castData0108d02, httpRequest);

    MstCustTanka mstCustTanka = new MstCustTanka();
    String custCode = formMst0108d02.getCstCode();
    String itemCode = formMst0108d02.getItemCode();
    String shopCode = formMst0108d02.getShopCode();
    mstCustTanka.setCustCode(custCode);
    mstCustTanka.setItemCode(itemCode);
    mstCustTanka.setShopCode(shopCode);
    String salesKb = formMst0108d02.getSalesKb();
    String validFrom = formMst0108d02.getValidFrom();
    String validTo = formMst0108d02.getValidTo();
    mstCustTanka.setSalesKb(salesKb);
    mstCustTanka.setValidFrom(validFrom);
    mstCustTanka.setValidTo(validTo);
    String custDeliTanka = formMst0108d02.getCustDeliTanka();
    Double cstDeliTanka = Double.valueOf(custDeliTanka);
    mstCustTanka.setCustDeliTanka(BigDecimal.valueOf(cstDeliTanka));
    String custSellTanka = formMst0108d02.getCustSellTanka();
    int cstSellTanka = Integer.parseInt(custSellTanka);
    mstCustTanka.setCustSellTanka(cstSellTanka);
    Double cstBildTanka = Double.valueOf(custBildTanka);
    String bunruiCode = formMst0108d02.getBunruiCode();
    String stsCode = formMst0108d02.getStsCode();
    mstCustTanka.setCustBildTanka(BigDecimal.valueOf(cstBildTanka));
    mstCustTanka.setBunruiCode(bunruiCode);
    mstCustTanka.setStsCode(stsCode);

    mstCustTanka = this.setCommonData(mstCustTanka, strLoginUserCode,
        "MST01-08D02", true);

    return mstCustTanka;
  }

  /**
   * setMstCustTankaforUpdate 更新のためにMstCustTankaを設定.
   * 
   * @param formMst0108d02 画面フォーム
   * @param strLoginUserCode STRログインユーザコード
   * @return MstCustTanka
   * @throws ParseException e
   */
  public MstCustTanka setMstCustTankaforUpdate(FormMst0108d02 formMst0108d02,
      String strLoginUserCode, CastData0108d02 castData0108d02, HttpServletRequest httpRequest)
          throws ParseException {

    String shopCode = formMst0108d02.getShopCode();
    if (shopCode.equalsIgnoreCase("")) {
      shopCode = formMst0108d02.getTenCodeNone();
    }

    String custBildTanka = formMst0108d02.getCustBildTanka();
    if (custBildTanka.equalsIgnoreCase("")) {
      custBildTanka = "0";
    }
    formMst0108d02.setCustBildTanka(custBildTanka);

    MstCustTanka mstCustTanka = new MstCustTanka();
    String custCode = formMst0108d02.getCstCode();
    mstCustTanka.setCustCode(custCode);
    String itemCode = formMst0108d02.getItemCode();
    mstCustTanka.setItemCode(itemCode);
    if (shopCode.equalsIgnoreCase("")) {
      shopCode = readPropertiesFileService.getSetting(
          "TEN_CODE_NONE");
    }
    mstCustTanka.setShopCode(shopCode);
    String salesKb = formMst0108d02.getSalesKb();
    String validFrom = formMst0108d02.getValidFrom();
    String validTo = formMst0108d02.getValidTo();
    mstCustTanka.setSalesKb(salesKb);
    mstCustTanka.setValidFrom(validFrom);
    mstCustTanka.setValidTo(validTo);
    String custDeliTanka = formMst0108d02.getCustDeliTanka();

    String custSellTanka = formMst0108d02.getCustSellTanka();
    Double cstDeliTanka = Double.valueOf(custDeliTanka);
    mstCustTanka.setCustDeliTanka(BigDecimal.valueOf(cstDeliTanka));
    int cstSellTanka = Integer.parseInt(custSellTanka);
    mstCustTanka.setCustSellTanka(cstSellTanka);
    Double cstBildTanka = Double.valueOf(custBildTanka);
    mstCustTanka.setCustBildTanka(BigDecimal.valueOf(cstBildTanka));
    String bunruiCode = formMst0108d02.getBunruiCode();
    mstCustTanka.setBunruiCode(bunruiCode);
    String stsCode = formMst0108d02.getStsCode();
    String haitaDate = formMst0108d02.getHaitaDate();
    String haitaTime = formMst0108d02.getHaitaTime();
    mstCustTanka.setStsCode(stsCode);
    mstCustTanka.setUpdYmd(haitaDate);
    mstCustTanka.setUpdTime(haitaTime);
    mstCustTanka = this.setCommonData(mstCustTanka, strLoginUserCode,
        "MST01-08D02", true);
    return mstCustTanka;
  }

  /**
   * setMstCustTankaforDelete 削除のためにMstCustTankaを設定.
   * 
   * @param formMst0108d02 画面フォーム
   * @param strLoginUserCode STRログインユーザコード
   * @return MstCustTanka
   * @throws ParseException e
   */
  public MstCustTanka setMstCustTankaforDelete(FormMst0108d02 formMst0108d02,
      String strLoginUserCode, CastData0108d02 castData0108d02, HttpServletRequest httpRequest)
          throws ParseException {

    String custCode = formMst0108d02.getCstCode();
    String itemCode = formMst0108d02.getItemCode();
    String shopCode = formMst0108d02.getShopCode();

    MstCustTanka mstCustTanka = new MstCustTanka();
    mstCustTanka.setCustCode(custCode);
    mstCustTanka.setItemCode(itemCode);
    if (shopCode.equalsIgnoreCase("")) {
      shopCode = readPropertiesFileService.getSetting(
          "TEN_CODE_NONE");
    }
    mstCustTanka.setShopCode(shopCode);
    String salesKb = formMst0108d02.getSalesKb();
    String validFrom = formMst0108d02.getValidFrom();
    String validTo = formMst0108d02.getValidTo();
    mstCustTanka.setSalesKb(salesKb);
    mstCustTanka.setValidFrom(validFrom);
    mstCustTanka.setValidTo(validTo);
    String custDeliTanka = formMst0108d02.getCustDeliTanka();
    Double cstDeliTanka = Double.valueOf(custDeliTanka);
    String custSellTanka = formMst0108d02.getCustSellTanka();
    int cstSellTanka = Integer.parseInt(custSellTanka);
    String custBildTanka = formMst0108d02.getCustBildTanka();
    mstCustTanka.setCustDeliTanka(BigDecimal.valueOf(cstDeliTanka));
    mstCustTanka.setCustSellTanka(cstSellTanka);
    Double cstBildTanka = Double.valueOf(custBildTanka);
    String bunruiCode = formMst0108d02.getBunruiCode();
    mstCustTanka.setCustBildTanka(BigDecimal.valueOf(cstBildTanka));
    mstCustTanka.setBunruiCode(bunruiCode);
    String stsCode = formMst0108d02.getStsCode();
    String haitaDate = formMst0108d02.getHaitaDate();
    String haitaTime = formMst0108d02.getHaitaTime();
    mstCustTanka.setStsCode(stsCode);
    mstCustTanka.setUpdYmd(haitaDate);
    mstCustTanka.setUpdTime(haitaTime);
    mstCustTanka = this.setCommonData(mstCustTanka, strLoginUserCode,
        "MST01-08D02", true);
    return mstCustTanka;
  }

  /**
   * searchInforCustTanka インフォアCustTankaを検索.
   * 
   * @param formMst0108d02 エンチｔ
   * @return lstInfoCustTanka
   * @throws ParseException e
   */
  public ArrayList<MstCustTanka> searchInforCustTanka(
      FormMst0108d02 formMst0108d02,
      CastData0108d02 castData0108d02, HttpServletRequest httpRequest)
          throws ParseException {
    String custCode = formMst0108d02.getCstCode();
    String itemCode = formMst0108d02.getItemCode();
    String shopCode = formMst0108d02.getShopCode();
    String salesKb = formMst0108d02.getSalesKb();
    String validFrom = formMst0108d02.getValidFrom();
    String validTo = formMst0108d02.getValidTo();

    Map<String, Object> params = new HashMap<String, Object>(); // Mapper
    params.put("custCode", custCode);
    params.put("itemCode", itemCode);
    params.put("shopCode", shopCode);
    params.put("salesKb", salesKb);
    params.put("validFrom", validFrom);
    params.put("validTo", validTo);
    ArrayList<MstCustTanka> lstInfoCustTanka = mst0108d02Dao
        .getMst0108d02Mapper().searchInforCustTanka(params);
    return lstInfoCustTanka;
  }

  /**
   * searchInforCustTankaforInsert インフォアCustTankaを検索.
   * 
   * @param formMst0108d02 エンチｔ
   * @param httpRequest httpRequest
   * @return lstInfoCustTanka
   * @throws ParseException e
   */
  public ArrayList<MstCustTanka> searchInforCustTankaforInsert(
      FormMst0108d02 formMst0108d02,
      CastData0108d02 castData0108d02, HttpServletRequest httpRequest)
          throws ParseException {
    String custCode = formMst0108d02.getCstCode();
    String itemCode = formMst0108d02.getItemCode();
    String shopCode = formMst0108d02.getShopCode();
    String salesKb = formMst0108d02.getSalesKb();
    String validFrom = formMst0108d02.getValidFrom();
    String validTo = formMst0108d02.getValidTo();

    Map<String, Object> params = new HashMap<String, Object>(); // Mapper
    params.put("custCode", custCode);
    params.put("itemCode", itemCode);
    params.put("shopCode", shopCode);
    params.put("salesKb", salesKb);
    params.put("validFrom", validFrom);
    params.put("validTo", validTo);
    ArrayList<MstCustTanka> lstInfoCustTanka = mst0108d02Dao
        .getMst0108d02Mapper().searchInforCustTanka(params);
    return lstInfoCustTanka;
  }

  /**
   * checkValidDay 有効日チェック.
   * 
   * @param formMst0108d02 画面フォーム
   * @param castData0108d02 Backデータ定義
   * @param lstInfoCustTanka lstInfoCustTanka
   * @throws ParseException e
   */
  public void checkValidDay(FormMst0108d02 formMst0108d02,
      CastData0108d02 castData0108d02,
      ArrayList<MstCustTanka> lstInfoCustTanka, HttpServletRequest httpRequest)
          throws ParseException {

    // セッションのデータを取得する
    Map<String, Object> sessionData = Util.getContentsFromSession(httpRequest);
    // システム管理者フラグ値を取得する
    String strLoginUserCode = String.valueOf(sessionData.get("UserCode"));
    DefaultTransactionDefinition def = null;
    // Representation of the status of a transaction.
    TransactionStatus status = null;

    ArrayList<String> paramMess = null;
    def = new DefaultTransactionDefinition();
    status = txManager.getTransaction(def);
    if (lstInfoCustTanka.size() > 0) {
      formMst0108d02.setMasterExistRecordEditFlag(true);

      // ※当処理を、得意先品目価格マスタ取得レコード全件を対象に実施する。処理(1)～処理(7)
      for (int i = 0; i < lstInfoCustTanka.size(); i++) {

        int validToWk = Integer.parseInt(
            lstInfoCustTanka.get(i).getValidTo());
        int validToSaveWk = validToWk;

        String validFromUpdate = "";
        String validToUpdate = "";
        // (1-1) 共通マスタ項目を変数に格納する。
        MstCustTanka mstCustTanka = new MstCustTanka();
        mstCustTanka.setCustCode(lstInfoCustTanka.get(i).getCustCode());
        mstCustTanka.setItemCode(lstInfoCustTanka.get(i).getItemCode());
        mstCustTanka.setShopCode(lstInfoCustTanka.get(i).getShopCode());
        mstCustTanka.setSalesKb(lstInfoCustTanka.get(i).getSalesKb());
        mstCustTanka.setValidFrom(lstInfoCustTanka.get(i).getValidFrom());
        mstCustTanka.setValidTo(lstInfoCustTanka.get(i).getValidTo());
        mstCustTanka.setCustDeliTanka(lstInfoCustTanka.get(i)
            .getCustDeliTanka());
        mstCustTanka.setCustSellTanka(lstInfoCustTanka.get(i)
            .getCustSellTanka());
        mstCustTanka.setCustBildTanka(lstInfoCustTanka.get(i)
            .getCustBildTanka());
        mstCustTanka.setBunruiCode(lstInfoCustTanka.get(i).getBunruiCode());
        mstCustTanka.setStsCode(lstInfoCustTanka.get(i).getStsCode());
        mstCustTanka = this.setCommonData(mstCustTanka, strLoginUserCode,
            "MST01-08D02", true);

        // (2) [画面] 適用期間（開始） ＞ [変数] 適用期間（開始）WK 且つ
        // [画面] 適用期間（終了） ＜ [変数]適用期間（終了）WK の場合、既存レコードの編集を行う。
        int validFromScreen = Integer.parseInt(formMst0108d02.getValidFrom());
        int validToScreen = Integer.parseInt(formMst0108d02.getValidTo());
        int validFromWk = Integer.parseInt(
            lstInfoCustTanka.get(i).getValidFrom());
        if (validFromScreen > validFromWk && validToScreen < validToWk) {
          validToWk = Integer.parseInt(addPlusDay(String.valueOf(
              validFromScreen), -1));
          validFromUpdate = String.valueOf(validFromWk);
          validToUpdate = String.valueOf(validToWk);
          mstCustTanka.setValidFrom(validFromUpdate);
          mstCustTanka.setValidTo(validToUpdate);
          mst0108d02Dao.getMstCustTankaMapper().insert(mstCustTanka);
          // [変数]適用期間(開始)_更新 ＝ [画面]適用期間（終了） + 1
          validFromScreen = Integer.parseInt(addPlusDay(String.valueOf(
              validToScreen), 1));
          validFromUpdate = String.valueOf(validFromScreen);

          mstCustTanka.setValidFrom(validFromUpdate);
          mstCustTanka.setValidTo(String.valueOf(validToSaveWk));
          // (2-4) 既存レコードを更新する。
          mst0108d02Dao.getMstCustTankaMapper()
              .updateByPrimaryKeySelective(mstCustTanka);
        } else if (validFromScreen < validFromWk && validToScreen < validToWk) {
          // (3) [画面] 適用期間（開始） ＜ [変数] 適用期間（開始）WK 且つ
          // [画面] 適用期間（終了） ＜ [変数]適用期間（終了）WKの場合、既存レコードの編集を行う。
          validFromUpdate = addPlusDay(String.valueOf(validToScreen), 1);

          mstCustTanka.setValidFrom(validFromUpdate);
          mst0108d02Dao.getMstCustTankaMapper()
              .updateByPrimaryKeySelective(mstCustTanka);
        } else if (validFromScreen > validFromWk && validToScreen > validToWk) {
          // (4) [画面] 適用期間（開始） ＞ [変数] 適用期間（開始）WK 且つ
          // [画面] 適用期間（終了） ＞ [変数]適用期間（終了）WKの場合、既存レコードの編集を行う。
          validToUpdate = addPlusDay(String.valueOf(validFromScreen), -1);

          mstCustTanka.setValidTo(validToUpdate);

          /*
           * mst0108d02Dao.getMstCustTankaMapper()
           * .updateByPrimaryKeySelective(mstCustTanka);
           */
          MstCustTankaExample mstCustTankaExample = new MstCustTankaExample();
          mstCustTankaExample.createCriteria().andCustCodeEqualTo(
              lstInfoCustTanka.get(i).getCustCode())
              .andItemCodeEqualTo(lstInfoCustTanka.get(i).getItemCode())
              .andSalesKbEqualTo(lstInfoCustTanka.get(i).getSalesKb())
              .andShopCodeEqualTo(lstInfoCustTanka.get(i).getShopCode())
              .andValidToEqualTo(lstInfoCustTanka.get(i).getValidTo());

          mst0108d02Dao.getMstCustTankaMapper().updateByExample(mstCustTanka,
              mstCustTankaExample);

        } else if (validFromScreen < validFromWk && validToScreen > validToWk) {
          // (5) [画面] 適用期間（開始） ＜ [変数] 適用期間（開始）WK 且つ
          // [画面] 適用期間（終了） ＞ [変数]適用期間（終了）WKの場合、既存レコードの削除を行う。
          mst0108d02Dao.getMstCustTankaMapper()
              .deleteByPrimaryKey(mstCustTanka);
        } else if (validFromScreen == validFromWk
            && validToScreen < validToWk) {
          // (6) [画面] 適用期間（開始） ＝ [変数] 適用期間（開始）WK 且つ
          // [画面] 適用期間（終了） ＜ [変数]適用期間（終了）WKの場合、既存レコードの編集を行う。
          validFromUpdate = addPlusDay(String.valueOf(validToScreen), 1);

          mstCustTanka.setValidFrom(validFromUpdate);
          mst0108d02Dao.getMstCustTankaMapper()
              .updateByPrimaryKeySelective(mstCustTanka);
        } else if (validFromScreen > validFromWk
            && validToScreen == validToWk) {
          // (7) [画面] 適用期間（開始） ＞ [変数] 適用期間（開始）WK 且つ
          // [画面] 適用期間（終了） ＝ [変数]適用期間（終了）WKの場合、既存レコードの編集を行う。
          validToUpdate = addPlusDay(String.valueOf(validFromScreen), -1);

          mstCustTanka.setValidTo(validToUpdate);
          MstCustTankaExample mstCustTankaExample = new MstCustTankaExample();
          mstCustTankaExample.createCriteria().andCustCodeEqualTo(
              lstInfoCustTanka.get(i).getCustCode())
              .andItemCodeEqualTo(lstInfoCustTanka.get(i).getItemCode())
              .andSalesKbEqualTo(lstInfoCustTanka.get(i).getSalesKb())
              .andShopCodeEqualTo(lstInfoCustTanka.get(i).getShopCode())
              .andValidToEqualTo(lstInfoCustTanka.get(i).getValidTo());
          mst0108d02Dao.getMstCustTankaMapper().updateByExample(mstCustTanka,
              mstCustTankaExample);
        }
      }

    }
    try {
      MstCustTanka mstCustTanka = new MstCustTanka();
      mstCustTanka = setMstCustTankaforInsert(formMst0108d02,
          strLoginUserCode, castData0108d02, httpRequest);
      if (mstCustTanka != null) {
        String shopCode = formMst0108d02.getShopCode();
        if (!shopCode.equalsIgnoreCase("")) {
          mstCustTanka.setShopCode(shopCode);
        } else if (shopCode.equalsIgnoreCase("")) {
          shopCode = readPropertiesFileService.getSetting(
              "TEN_CODE_NONE");
          mstCustTanka.setShopCode(shopCode);
        }
        mst0108d02Dao.getMstCustTankaMapper().insert(
            mstCustTanka);
        formMst0108d02.setSuccess(1);
        // トランザクション単位は、一覧全件とする。 （補足）一覧行単位に、Commitを行わない。
        txManager.commit(status);
        String errMessage = "";
        paramMess = new ArrayList<String>();
        paramMess.add("得意先品目価格マスタの登録");
        errMessage += readPropertiesFileService.getMessage("COM002-I",
            paramMess) + "<br>";
        String error = formMst0108d02.getErrMessage();
        error = error + errMessage;
        formMst0108d02.setErrMessage(errMessage);
      }
    } catch (Exception e) {
      // Perform a rollback of the given transaction.
      String errMessage1 = "";
      errMessage1 += readPropertiesFileService.getMessage("MST015-E", null)
          + "<br>";
      formMst0108d02.setErrMessage(errMessage1);
      formMst0108d02.setFlag(3);
      txManager.rollback(status);
      logger.error(e.getMessage());
    }
  }

  /**
   * 共通項目の設定.
   * 
   * @param data:訂正区分データ
   * @param strUserId:ユーザID
   * @param strProgId:プログラムID
   * @param isNew:新規／更新
   * @return 訂正区分データ
   */
  public MstCustTanka setCommonData(MstCustTanka data, String strUserId,
      String strProgId, boolean isNew) {

    String currentDate = DateUtil.getSysDate();
    String currentTime = DateUtil.getSysTime();

    if (isNew) {
      data.setInsUserid(strUserId);
      data.setInsPgid(strProgId);
      data.setInsYmd(currentDate);
      data.setInsTime(currentTime);
    }

    data.setUpdUserid(strUserId);
    data.setUpdPgid(strProgId);
    data.setUpdYmd(currentDate);
    data.setUpdTime(currentTime);

    return data;
  }

  /**
   * データCusttomerを取得.
   * 
   * @param model モデル
   * @param formMst0108d02 画面フォーム
   * @return lstInfoCustTanka
   */
  public ArrayList<MstCustTanka> getDataCusttomer(Model model,
      FormMst0108d02 formMst0108d02) {
    String custCode = formMst0108d02.getCstCode();
    String itemCode = formMst0108d02.getItemCode();
    String shopCode = formMst0108d02.getShopCode();
    String salesKb = formMst0108d02.getSalesKb();
    String validFrom = formMst0108d02.getValidFrom();
    String validTo = formMst0108d02.getValidTo();
    Map<String, Object> params = new HashMap<String, Object>(); // Mapper
    params.put("custCode", custCode);
    params.put("itemCode", itemCode);
    params.put("shopCode", shopCode);
    params.put("salesKb", salesKb);
    params.put("validFrom", validFrom);
    params.put("validTo", validTo);
    ArrayList<MstCustTanka> lstInfoCustTanka = mst0108d02Dao
        .getMst0108d02Mapper().searchInforCustTanka(params);
    if (lstInfoCustTanka.size() <= 0) {
      lstInfoCustTanka = null;
    }
    return lstInfoCustTanka;
  }

  /**
   * 全項目入力チェック処理.
   * 
   * @param formMst0101d02:フォーム
   * @param model:モデル
   * @return エラー true 普通:false
   */
  public boolean checkInput(FormMst0108d02 formMst0108d02, Model model) {

    String strError;
    String strErrorId = "";
    String hadError = "";
    ArrayList<ErrorMessages> lstErrMess = new ArrayList<ErrorMessages>();
    ErrorMessages errMess = new ErrorMessages();
    // メッセージのParam
    ArrayList<String> paramMess = new ArrayList<String>();

    // 得意先コード
    strError = checkItem(formMst0108d02.getCstCode(), true,
        InputCheckCom.TYPE_NUM, 7);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("得意先コード");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "cstCode" + MstConst.DELIMITER_ERROR;
    }

    // 品目コード
    strError = checkItem(formMst0108d02.getItemCode(), true,
        InputCheckCom.TYPE_NUM, 6);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("品目コード");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "itemCode" + MstConst.DELIMITER_ERROR;
    }

    // 販売区分
    String viewMode = formMst0108d02.getViewMode();
    if (viewMode.equalsIgnoreCase(MstConst.TEISEI_MODE)
        || viewMode.equalsIgnoreCase(MstConst.TORIKESI_MODE)) {
      if (formMst0108d02.getMasutaKubunClassList() != null) {
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("販売区分");
        errMess.setMessageContent(readPropertiesFileService.getMessage("COM006-E",
            paramMess));
        lstErrMess.add(errMess);
        strErrorId += "MasutaKubunClassList" + MstConst.DELIMITER_ERROR;
      }
    } else if (formMst0108d02.getMasutaKubunClassList().equalsIgnoreCase("")) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("販売区分");
      errMess.setMessageContent(readPropertiesFileService.getMessage("COM006-E",
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "MasutaKubunClassList" + MstConst.DELIMITER_ERROR;
    }

    // 店舗コード
    strError = checkItem(formMst0108d02.getShopCode(), false,
        InputCheckCom.TYPE_NUM, 8);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("店舗コード");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "shopCode" + MstConst.DELIMITER_ERROR;
    }
    // 適用期間 （開始)
    boolean fromDate = false;
    strError = checkItem(formMst0108d02.getValidFrom(), true,
        InputCheckCom.TYPE_NUM, 8);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("適用期間 （開始)");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "validFrom" + MstConst.DELIMITER_ERROR;
      fromDate = true;
    } else {
      strError = InputCheckCom.chkDate(formMst0108d02.getValidFrom(),
          CommonConst.DATE_FORMAT_YMD);
      if (strError != null) {
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("適用期間 （開始)");
        errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
            paramMess));
        lstErrMess.add(errMess);
        strErrorId += "validFrom" + MstConst.DELIMITER_ERROR;
        fromDate = true;
      }
    }
    // 適用期間 （終了)
    strError = checkItem(formMst0108d02.getValidTo(), true,
        InputCheckCom.TYPE_NUM, 8);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("適用期間 （終了）");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "validTo" + MstConst.DELIMITER_ERROR;
      fromDate = true;
    } else {
      strError = InputCheckCom.chkDate(formMst0108d02.getValidTo(),
          CommonConst.DATE_FORMAT_YMD);
      if (strError != null) {
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("適用期間 （終了）");
        errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
            paramMess));
        lstErrMess.add(errMess);
        strErrorId += "validTo" + MstConst.DELIMITER_ERROR;
        fromDate = true;
      }
    }

    if (!fromDate) {
      Integer validFrom = Integer.parseInt(formMst0108d02.getValidFrom());
      Integer validTo = Integer.parseInt(formMst0108d02.getValidTo());
      if (validFrom > validTo) {
        paramMess = new ArrayList<String>();
        paramMess.add("適用期間（終了）");
        paramMess.add("適用期間（開始)");
        errMess = new ErrorMessages();
        errMess.setMessageContent(readPropertiesFileService.getMessage("COM016-E",
            paramMess));
        lstErrMess.add(errMess);
        strErrorId += "validFrom" + MstConst.DELIMITER_ERROR;
      }
    }
    // 納品単価

    String custDeliTanka = String.valueOf(formMst0108d02.getCustDeliTanka());
    strError = InputCheckCom.chkEmpty(custDeliTanka);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("納品単価");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "custDeliTanka" + MstConst.DELIMITER_ERROR;
      hadError = "1";
    } else {
      strError = InputCheckCom.chkNum(custDeliTanka, false);

      if (strError != null) {
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("納品単価");
        errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
            paramMess));
        lstErrMess.add(errMess);
        strErrorId += "custDeliTanka" + MstConst.DELIMITER_ERROR;
        hadError = "1";
      }
    }

    if (hadError.equalsIgnoreCase("")) {
      boolean str = checkFloat(custDeliTanka, 9, 2);
      if (!str) {
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("納品単価");
        errMess.setMessageContent(readPropertiesFileService.getMessage("COM001-E",
            paramMess));
        lstErrMess.add(errMess);
        strErrorId += "custDeliTanka" + MstConst.DELIMITER_ERROR;
      }
    }

    // 販売単価
    String custSellTanka = String.valueOf(formMst0108d02.getCustSellTanka());
    strError = InputCheckCom.chkEmpty(custSellTanka);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("販売単価");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "custSellTanka" + MstConst.DELIMITER_ERROR;
    } else {
      strError = checkNum(custSellTanka, true,
          InputCheckCom.TYPE_NUM, 6);
      if (strError != null) {
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("販売単価");
        errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
            paramMess));
        lstErrMess.add(errMess);
        strErrorId += "custSellTanka" + MstConst.DELIMITER_ERROR;
      }
    }
    // 先方仕切単価
    String custBildTanka = String.valueOf(formMst0108d02.getCustBildTanka());
    if (!custBildTanka.equalsIgnoreCase("")) {
      strError = InputCheckCom.chkNum(custBildTanka, false);
      if (strError != null) {
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("先方仕切単価");
        errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
            paramMess));
        lstErrMess.add(errMess);
        strErrorId += "custBildTanka" + MstConst.DELIMITER_ERROR;
      } else {
        boolean str = checkFloat(custBildTanka, 9, 2);
        if (!str) {
          errMess = new ErrorMessages();
          paramMess = new ArrayList<String>();
          paramMess.add("先方仕切単価");
          errMess.setMessageContent(readPropertiesFileService.getMessage("COM001-E",
              paramMess));
          lstErrMess.add(errMess);
          strErrorId += "custBildTanka" + MstConst.DELIMITER_ERROR;
        }
      }

    }
    // 販売単価
    String bunruiCode = String.valueOf(formMst0108d02.getBunruiCode());
    strError = checkNum(bunruiCode, false,
        InputCheckCom.TYPE_NUM, 6);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("分類コード");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "bunruiCode" + MstConst.DELIMITER_ERROR;
    }

    // 状況コード
    String stsCode = String.valueOf(formMst0108d02.getStsCode());
    strError = checkNum(stsCode, true,
        InputCheckCom.TYPE_NUM, 1);
    if (strError != null) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("状況コード");
      errMess.setMessageContent(readPropertiesFileService.getMessage(strError,
          paramMess));
      lstErrMess.add(errMess);
      strErrorId += "stsCode" + MstConst.DELIMITER_ERROR;
    } else {
      String strStsCode = formMst0108d02.getStsCode();
      if (!strStsCode.equalsIgnoreCase("") && (!strStsCode.equalsIgnoreCase(MstConst.TOROKU)
          && (!strStsCode.equalsIgnoreCase(MstConst.TORIKESHI)))) {
        errMess = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("状況コードは１または９で入力してください");
        errMess.setMessageContent(readPropertiesFileService.getMessage("MST013-E",
            paramMess));
        lstErrMess.add(errMess);
        strErrorId += "stsCode" + MstConst.DELIMITER_ERROR;
      }
    }
    // (1) 画面表示モード = '1' （新規登録） の時のみ、過去日付チェックを行う。

    if (formMst0108d02.getViewMode().equalsIgnoreCase(MstConst.SHINKI_MODE)) {
      if (!formMst0108d02.getValidFrom().equalsIgnoreCase("")) {
        int validFrom1 = Integer.parseInt(formMst0108d02.getValidFrom());
        int bussinessDate = Integer.parseInt(formMst0108d02.getBussinessDate());
        if (validFrom1 < bussinessDate) {
          errMess = new ErrorMessages();
          paramMess = new ArrayList<String>();
          errMess.setMessageContent(readPropertiesFileService.getMessage(
              "MST018-E", paramMess));
          lstErrMess.add(errMess);
          strErrorId += "validFrom" + MstConst.DELIMITER_ERROR;
        }
      }

    }
    // (1) [画面]店舗コード ＝ [変数]設定情報）店舗コード_未指定の場合エラーとし、次の通り処理を行う。
    if (formMst0108d02.getShopCode().equalsIgnoreCase(readPropertiesFileService.getSetting(
        "TEN_CODE_NONE"))) {
      errMess = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("店舗コード");
      errMess.setMessageContent(readPropertiesFileService.getMessage(
          "COM030-E", paramMess));
      lstErrMess.add(errMess);
      strErrorId += "shopCode" + MstConst.DELIMITER_ERROR;
    }
    if (strErrorId.equalsIgnoreCase("")) {
      return true;
    } else {
      model.addAttribute("errorMessages", lstErrMess);
      model.addAttribute("lstErrorID", strErrorId);
      return false;
    }
  }

  /**
   * 入力チェック処理.
   * 
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
   * checkNum 民をチェック.
   * 
   * @param val ストリング
   * @param emptyFlg 空フラグ
   * @param type タイプ
   * @param len 長さ
   * @return error
   */
  public String checkNum(String val, boolean emptyFlg, int type, int len) {
    String error = null;
    if (emptyFlg) {
      error = InputCheckCom.chkEmpty(val);
      if (error != null) {
        return error;
      }
    }

    error = InputCheckCom.chkNum(val, emptyFlg);
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
   * checkFloat フロートをチェック.
   * 
   * @param val ストリング
   * @param len 文字列の長さ
   * @return error = true --> Ok , false --> not float.
   */
  public boolean checkFloat(String val, int len, int smallNumber) {
    boolean error = false;
    String strError = "";
    if (!val.equalsIgnoreCase("")) {
      try {
        Double.parseDouble(val);
        error = true;
      } catch (Exception e) {
        error = false;
      }
      int index = val.indexOf(".");
      int lastIndex = val.lastIndexOf("");
      if (index != -1) {
        String firstStr = val.substring(0, index);
        String lastStr = val.substring(index + 1, lastIndex);

        if (val.length() > len) {
          error = false;
          return error;
        }
        if (firstStr.length() > 0 && firstStr.length() <= (len
            - smallNumber - 1)) {
          strError = InputCheckCom.chkNum(firstStr, false);
          if (strError == null) {
            error = true;
          } else {
            error = false;
            return error;
          }
        } else {
          error = false;
          return error;
        }
        if (lastStr.length() > 0 && lastStr.length() <= smallNumber) {
          int intLastStr = Integer.parseInt(lastStr);
          strError = InputCheckCom.chkNum(String.valueOf(intLastStr), false);
          if (strError == null) {
            error = true;
          } else {
            error = false;
            return error;
          }
        } else {
          error = false;
        }
      } else {
        error = true;
      }
    } else {
      error = false;
    }
    return error;
  }

  /**
   * 日次
   * 
   * @param datetime String
   * @param countDay int
   * @return String
   */
  public String addPlusDay(String datetime, int countDay) {
    StringBuilder result = new StringBuilder();
    String yearStart = datetime.substring(0, 4);
    String monthStart = datetime.substring(4, 6);
    String dateStart = datetime.substring(6, 8);
    Calendar calStart = Calendar.getInstance();
    calStart.set(Integer.parseInt(yearStart), Integer.parseInt(monthStart)
        - GetConst.ADJAST_CALENDAR_MONTH, Integer.parseInt(dateStart), 0, 0, 0);
    calStart.add(Calendar.DAY_OF_YEAR, countDay);
    result.append(calStart.get(Calendar.YEAR));
    int month = calStart.get(Calendar.MONTH) + GetConst.ADJAST_CALENDAR_MONTH;
    if (month < 10) {
      result.append("0");
      result.append(String.valueOf((calStart.get(Calendar.MONTH)
          + GetConst.ADJAST_CALENDAR_MONTH)));
    } else {
      result.append(String.valueOf((calStart.get(Calendar.MONTH)
          + GetConst.ADJAST_CALENDAR_MONTH)));
    }

    String date = "";
    date = String.valueOf(calStart.get(Calendar.DATE));
    if (calStart.get(Calendar.DATE) < 10) {
      date = "0" + calStart.get(Calendar.DATE);
    }
    result.append(date);
    return result.toString();
  }

  /**
   * 排他の設定.
   * 
   * @param formMst0108d01:フォーム
   */
  public void setHaitaDate(FormMst0108d02 formMst0108d02) {
    formMst0108d02.setHaitaDate(DateUtil.getSysDate());
    formMst0108d02.setHaitaTime(DateUtil.getSysTime());
  }

  /**
   * addZeroForNumeric.
   * 
   * @param val String
   * @return String
   */
  public String addZeroForNumeric(String val) {
    NumberFormat formatter = new DecimalFormat("#0.00");
    return formatter.format(Double.valueOf(val));

  }
}
