/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.mst.impl
 * ファイル名:Mst0105d00Service.java
 * 
 * <p>作成者:QuanTran
 * 作成日:2015/10/01
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/01 1.00 ABV)QuanTran 新規開発
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
import jp.co.daitoku_sh.dfcm.common.db.model.MstCustConv;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCustomer;
import jp.co.daitoku_sh.dfcm.common.db.model.MstShop;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetData;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.InputCheckCom;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.MstConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.CastData0105d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ObjCombobox;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.FormMst0105d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.OlCustConvMasterData;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl.Mst0105d00Dao;

/**
 * The service layer can use daos to load multiple objects 
 * and do some kind of business logic 
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class Mst0105d02Service extends AbsService {
  /** ログ出力用 */
  private Dfcmlogger logger = new Dfcmlogger();
  
  @Autowired
  @Qualifier("mst0105d00Dao")
  private Mst0105d00Dao mst0105d00Dao;
  
  @Autowired
  @Qualifier("commonDao")
  private CommonDao commonDao;
  
  /** メッセージPropertiesファイルを読む */
  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;
  
  /** The operational database management object*/
  @Autowired
  @Qualifier("txManager")
  private PlatformTransactionManager txManager;

  public CommonDao getCommonDao() {
    return commonDao;
  }

  public void setCommonDao(CommonDao commonDao) {
    this.commonDao = commonDao;
  }

  /**
   * 業務日付取得.
   * 
   * @return 業務日付
   */
  public String getBusinessDate() {
    CommonGetSystemCom sysCom = new CommonGetSystemCom(commonDao, null, null,
        readPropertiesFileService);
    return sysCom.getAplDate();
  }
  
  /**
   * オンライン得意先変換マスタデータ取得.
   * 
   * @param formMst0105d02 The controller object
   * @param screenMode 画面表示モード
   * @param atTorihikiCode 相手取引先コード
   * @param olCenterCode オンラインセンターコード
   * @param olTorihikiCode オンライン取引先コード
   * @param businessDate 業務日付
   * @param olTorihikiCodeNone オンライン取引先コード_未指定
   * @return The error messages
   */
  public String getOlCustConvData(FormMst0105d02 formMst0105d02,
      String screenMode, String atTorihikiCode, String olCenterCode,
      String olTorihikiCode, String businessDate, String olTorihikiCodeNone) {
    ArrayList<OlCustConvMasterData> olCustConvList = null;
    String errorMsg = null;
    if (screenMode.equalsIgnoreCase(MstConst.SHOUKAI_MODE)
        || screenMode.equalsIgnoreCase(MstConst.TEISEI_MODE)
        || screenMode.equalsIgnoreCase(MstConst.TORIKESI_MODE)) {
      String txtOnlineTorihikiCodeWk = null;
      // 項目編集
      if (olTorihikiCode != null && !olTorihikiCode.equalsIgnoreCase("")) {
        txtOnlineTorihikiCodeWk = olTorihikiCode;
      } else {
        txtOnlineTorihikiCodeWk = olTorihikiCodeNone;
      }
      // オンライン得意先変換マスタデータ取得
      Map<String, Object> parms = new HashMap<String, Object>();
      parms.put("Close_Date", businessDate);
      parms.put("OL_Center_Code", olCenterCode);
      parms.put("OL_Torihiki_Code", txtOnlineTorihikiCodeWk);
      parms.put("AT_Torihiki_Code", atTorihikiCode);
      try {
        olCustConvList = mst0105d00Dao.getMst0105d00Mapper()
            .getOlCustConvDataList02(parms);
      } catch (MyBatisSystemException e) {
        logger.error(e.getMessage());
        throw e;
      }

      if (olCustConvList == null || olCustConvList.size() == 0) {
        ArrayList<String> paramMess = new ArrayList<String>();
        paramMess.add("オンライン得意先変換マスタ");
        paramMess.add("オンライン得意先変換マスタ一覧で指定された変換定義");
        return readPropertiesFileService.getMessage("COM009-E", paramMess);
      } else {
        formMst0105d02.setOlCustConvList(olCustConvList);
      }
    }
    return errorMsg;
  }

  /**
   * オンラインセンター名称取得.
   * 
   * @param olCenterCode オンラインセンターコード
   * @param businessDate 業務日付
   * @return オンラインセンター名称
   */
  public String getOlCenterName(String olCenterCode, String businessDate,
      String olTorihikiCode) {
    String olCenterName = null;
    // 自社得意先名称取得
    Map<String, Object> parms = new HashMap<String, Object>();
    parms.put("Close_Date", businessDate);
    parms.put("OL_Center_Code", olCenterCode);
    parms.put("OL_Torihiki_Code", olTorihikiCode);
    ArrayList<MstCustomer> custNmRs = null;
    try {
      custNmRs = mst0105d00Dao.getMst0105d00Mapper().getOlCenterName(parms);
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }

    MstCustomer customer = null;
    // 取得結果に応じて後続の処理を行う。
    if (custNmRs != null && custNmRs.size() == 1) {
      customer = custNmRs.get(0);
      if (customer.getCustNmR() == null) {
        olCenterName = customer.getCustCode();
      } else {
        olCenterName = customer.getCustNmR();
      }
    } else if (custNmRs != null && custNmRs.size() > 1) {
      customer = custNmRs.get(0);
      if (customer.getCustNmR() == null) {
        olCenterName = customer.getCustCode() + " " + "他";
      } else {
        olCenterName = customer.getCustNmR() + " " + "他";
      }
    }
    return olCenterName;
  }

  /**
   * オンラインセンター名称取得.
   * 
   * @param atTorihikiCode 相手取引先コード
   * @param olCenterCode オンラインセンターコード
   * @param olTorihikiCode オンライン取引先コード
   * @param businessDate 業務日付
   * @param isNew true if the 設定ボタン event is called.
   * @return オンラインセンター名称
   */
  public String getOppCustName(String atTorihikiCode, String olCenterCode,
      String olTorihikiCode, String businessDate, boolean isNew) {
    String oppCustName = null;
    // 自社得意先名称取得
    Map<String, Object> parms = new HashMap<String, Object>();
    parms.put("Close_Date", businessDate);
    parms.put("OL_Center_Code", olCenterCode);
    parms.put("OL_Torihiki_Code", olTorihikiCode);
    parms.put("AT_Torihiki_Code", atTorihikiCode);
    ArrayList<MstCustomer> custNmRs = null;
    // オンライン得意先変換マスタより該当する得意先名称を取得する。
    try {
      if (isNew) {
        custNmRs = mst0105d00Dao.getMst0105d00Mapper().getPartnerCustCode02(parms);
      } else {
        custNmRs = mst0105d00Dao.getMst0105d00Mapper().getOppCustName(parms);
      }
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }

    MstCustomer customer = null;
    // 取得結果に応じて後続の処理を行う。
    if (custNmRs != null && custNmRs.size() >= 1) {
      customer = custNmRs.get(0);
      if (customer.getCustNmR() == null) {
        oppCustName = customer.getCustCode();
      } else {
        oppCustName = customer.getCustNmR();
      }
    }
    return oppCustName;
  }

  /**
   * 共通部品を使って、得意先情報を取得する
   * 
   * @param formMst0105d02 The controller object
   * @param custCode 得意先コード
   * @param jigyoCode 受注事業所コード
   * @param shopCode [画面]自社店舗コード
   */
  public void searchMstCust(FormMst0105d02 formMst0105d02, String custCode,
      String jigyoCode, String shopCode) {
    CommonGetData commonGetData = new CommonGetData(commonDao,
        readPropertiesFileService);

    if (jigyoCode.equalsIgnoreCase("")) {
      jigyoCode = null;
    }

    CustomerData custData = commonGetData.getCustomerData(custCode, jigyoCode,
        Integer.parseInt(MstConst.CUSTOMERS));
    ArrayList<String> paramMess = null;
    String errItemIds = CommonConst.EMPTY;
    String errMessage = "";
    // 処理結果に応じ、後続の処理を行う。
    if (custData.getMsgCd() == null) {
      MstCustomer cusInfo = custData.getCst();
      formMst0105d02.setLblCustomerNameR(cusInfo.getCustNmR());
      if (cusInfo.getShopKb().equalsIgnoreCase(MstConst.SHOPKB_HAS_SHOP) && shopCode
          .equalsIgnoreCase("")) {
        paramMess = new ArrayList<String>();
        paramMess.add("自社店舗コード");
        errMessage += readPropertiesFileService.getMessage("COM006-E",
            paramMess) + "<br>";
        errItemIds += "txtShopCode" + MstConst.DELIMITER_ERROR;
      }

      if (cusInfo.getShopKb().equalsIgnoreCase(MstConst.SHOPKB_HASNOT_SHOP) && !shopCode
          .equalsIgnoreCase("")) {
        paramMess = new ArrayList<String>();
        paramMess.add("自社店舗コード");
        errMessage += readPropertiesFileService.getMessage("MST014-E",
            paramMess) + "<br>";
        errItemIds += "txtShopCode" + MstConst.DELIMITER_ERROR;
      }
    } else if (custData.getMsgCd() != null) {
      paramMess = new ArrayList<String>();
      paramMess.add("得意先マスタ");
      paramMess.add("入力された得意先");
      errMessage += readPropertiesFileService.getMessage("COM009-E", paramMess)
          + "<br>";
      errItemIds += "txtCustomerCode" + MstConst.DELIMITER_ERROR;
      formMst0105d02.setLblCustomerNameR("");
    }

    // 店舗マスタ検索
    if (!shopCode.equalsIgnoreCase("")) {
      ShopData shopData = commonGetData.getShopData(shopCode, custCode);
      if (shopData != null && shopData.getMsgCd() == null) {
        if (shopData.getShp() != null) {
          MstShop shop = shopData.getShp();
          formMst0105d02.setLblShopNameR(shop.getShopNmR());
        }
      } else {
        paramMess = new ArrayList<String>();
        paramMess.add("店舗マスタ");
        paramMess.add("入力された店舗");
        errMessage += readPropertiesFileService.getMessage("COM009-E",
            paramMess) + "<br>";
        errItemIds += "txtShopCode" + MstConst.DELIMITER_ERROR;
        formMst0105d02.setLblShopNameR("");
      }
    }
    formMst0105d02.setErrItemIds(errItemIds);
    formMst0105d02.setErrMessage(errMessage);
  }

  /**
   * 設定ボタン.
   * 
   * @param formMst0105d02 A controller object.
   */
  public void create(FormMst0105d02 formMst0105d02) {
    String olCenterCode = formMst0105d02.getTxtOnlineCenterCode();
    String olTorihikiCode = formMst0105d02.getTxtOnlineTorihikiCode();
    String atTorihikiCode = formMst0105d02.getTxtAtTorihikiCode();
    String errItemIds = CommonConst.EMPTY;
    String errorMsg = "";
    // ヘッダーエリアオンライン得意先マスタ存在チェック
    String olCenterName = getOlCenterName(olCenterCode, formMst0105d02
        .getBusinessDate(), olTorihikiCode);
    if (olCenterName != null) {
      formMst0105d02.setLblCustomerNameR01(olCenterName);
      // エラー判定
      if (atTorihikiCode.equalsIgnoreCase("")) {
        errorMsg += readPropertiesFileService.getMessage("MST012-E", null);
        errItemIds += "txtOnlineCenterCode" + MstConst.DELIMITER_ERROR;
        formMst0105d02.setErrItemIds(errItemIds);
        formMst0105d02.setErrMessage(errorMsg);
        return;
      }
    }
    
    formMst0105d02.setLblCustomerNameR01("");
    if (!olCenterCode.equalsIgnoreCase("") && !atTorihikiCode.equalsIgnoreCase("")) {
      String oppCustName = getOppCustName(atTorihikiCode, olCenterCode,
          olTorihikiCode, formMst0105d02.getBusinessDate(), true);
      if (oppCustName != null) {
        formMst0105d02.setLblCustomerNameR02(oppCustName);
        // 重複エラー処理
        errorMsg += readPropertiesFileService.getMessage("MST012-E", null);
        errItemIds += "txtAtTorihikiCode" + MstConst.DELIMITER_ERROR;
        formMst0105d02.setErrItemIds(errItemIds);
        formMst0105d02.setErrMessage(errorMsg);
      } else {
        formMst0105d02.setLblCustomerNameR02("");
      }
    }

  }

  /**
   * 取得した納品区分情報を、[画面]納品区分にセットする。（当処理を取得レコード件数分繰り返し行う）
   * 
   * @param deliKbList 汎用マスタ設定情報格納クラス
   * @return 納品区分
   */
  public ArrayList<ObjCombobox> getDeliKbList(List<MstGeneralData> deliKbList) {
    ObjCombobox element = new ObjCombobox();
    element.setCode("");
    element.setName("");
    ArrayList<ObjCombobox> comboboxs = new ArrayList<ObjCombobox>();
    comboboxs.add(element);
    for (MstGeneralData gen : deliKbList) {
      element = new ObjCombobox();
      element.setCode(gen.getGlCode());
      element.setName(gen.getGlCode() + " : " + gen.getTarget1());
      comboboxs.add(element);
    }
    return comboboxs;
  }

  

  /**
   * 登録ボタン.
   * 
   * @param castData0105d02 An object is used to send and receive in same ajax  request
   * @param userCode 担当者コード
   */
  public void register(CastData0105d02 castData0105d02, String userCode)
      throws Exception {
    DefaultTransactionDefinition def = null;
    // Representation of the status of a transaction.
    TransactionStatus status = null;
    ArrayList<OlCustConvMasterData> list = castData0105d02.getOlCustConvList();
    String screenMode = castData0105d02.getScreenMode();
    int haitaDate = Integer.parseInt(castData0105d02.getHaitaDate());
    int haitaTime = Integer.parseInt(castData0105d02.getHaitaTime());
    ArrayList<MstCustConv> custConvs = null;
    MstCustConv record = null;

    int numRecord = 0;
    OlCustConvMasterData olCustConv = null;
    if (list != null && list.size() > 0) {

      if (checkInput(list, castData0105d02)) {
        return;
      }
      /** SHINKI_MODE */
      if (screenMode.equalsIgnoreCase(MstConst.SHINKI_MODE)) {
        try {
          def = new DefaultTransactionDefinition();
          status = txManager.getTransaction(def);
          // 変換定義一覧全件を対象に、オンライン得意先変換マスタに登録する。次の処理を一覧全件に対して繰返し処理を行う。
          for (int i = 0; i < list.size(); i++) {
            olCustConv = list.get(i);
            // オンライン得意先変換マスタデータ検索
            custConvs = searchMstConvCustData(olCustConv, castData0105d02);
            // 該当レコードの有無に応じ、後続処理を行う。
            if (custConvs != null && custConvs.size() > 0) {
              castData0105d02.setMessage(readPropertiesFileService.getMessage(
                  "MST016-E", null).replace("{1}", String.valueOf(i + 1)));
              castData0105d02.setOlCustConvList(null);
              // Perform a roll-back of the given transaction.
              txManager.rollback(status);
              return;
            } else {
              record = newRecord(olCustConv, userCode, "MST01-05D02",
                  castData0105d02.getOlTorihikiCodeNone());
              mst0105d00Dao.getMstCustConvMapper().insert(record);
              numRecord++;
            }
          }
          // トランザクション単位は、一覧全件とする。 （補足）一覧行単位に、Commitを行わない。
          txManager.commit(status);
        } catch (MyBatisSystemException e) {
          // Perform a roll-back of the given transaction.
          txManager.rollback(status);
          logger.error(e.getMessage());
          throw e;
        }
        /** TEISEI_MODE */
      } else if (screenMode.equalsIgnoreCase(MstConst.TEISEI_MODE)) {
        try {
          def = new DefaultTransactionDefinition();
          status = txManager.getTransaction(def);
          for (int i = 0; i < list.size(); i++) {
            olCustConv = list.get(i);
            // オンライン得意先変換マスタデータ検索
            custConvs = searchMstConvCustData(olCustConv, castData0105d02);

            // 該当レコードが存在しない場合、オンライン得意先変換マスタに登録を行う。
            if (custConvs != null && custConvs.size() > 0) {
              if (olCustConv.getUpdateFlg().equalsIgnoreCase(
                  MstConst.FLAG_ENABLE)) {
                record = custConvs.get(0);
                // 排他チェックを行う。 （共通仕様 9-(4) 適用）
                if (haitaDate < Integer.parseInt(record.getUpdYmd())) {
                  castData0105d02.setMessage(readPropertiesFileService
                      .getMessage("COM031-E", null));
                  castData0105d02.setOlCustConvList(null);
                  // Perform a roll-back of the given transaction.
                  txManager.rollback(status);
                  return;
                } else if (haitaDate == Integer.parseInt(record.getUpdYmd())) {
                  if (haitaTime < Integer.parseInt(record.getUpdTime())) {
                    castData0105d02.setMessage(readPropertiesFileService
                        .getMessage("COM031-E", null));
                    castData0105d02.setOlCustConvList(null);
                    // Perform a roll-back of the given transaction.
                    txManager.rollback(status);
                    return;
                  }
                }
                updateRecord(record, olCustConv, userCode, "MST01-05D02");
                mst0105d00Dao.getMstCustConvMapper()
                    .updateByPrimaryKeySelective(record);
                numRecord++;
                // 該当レコードが存在する場合、オンライン得意先マスタレコードを更新する。
              }
            } else {
              record = newRecord(olCustConv, userCode, "MST01-05D02",
                  castData0105d02.getOlTorihikiCodeNone());
              mst0105d00Dao.getMstCustConvMapper().insert(record);
              numRecord++;
            }
          }
          // Commit the given transaction, with regard to its status.
          txManager.commit(status);
        } catch (MyBatisSystemException e) {
          // Perform a roll-back of the given transaction.
          txManager.rollback(status);
          logger.error(e.getMessage());
          throw e;
        }
        /** TORIKESI_MODE */
      } else if (screenMode.equalsIgnoreCase(MstConst.TORIKESI_MODE)) {
        try {
          def = new DefaultTransactionDefinition();
          status = txManager.getTransaction(def);
          for (int i = 0; i < list.size(); i++) {
            olCustConv = list.get(i);
            // オンライン得意先変換マスタデータ検索
            custConvs = searchMstConvCustData(olCustConv, castData0105d02);
            // 該当レコードが存在しない場合、オンライン得意先変換マスタに登録を行う。
            if (custConvs != null && custConvs.size() > 0) {
              record = custConvs.get(0);
              // 排他チェックを行う。 （共通仕様 9-(4) 適用）
              if (haitaDate < Integer.parseInt(record.getUpdYmd())) {
                castData0105d02.setMessage(readPropertiesFileService.getMessage(
                    "COM031-E", null));
                castData0105d02.setOlCustConvList(null);
                // Perform a roll-back of the given transaction.
                txManager.rollback(status);
                return;
              } else if (haitaDate == Integer.parseInt(record.getUpdYmd())) {
                if (haitaTime < Integer.parseInt(record.getUpdTime())) {
                  castData0105d02.setMessage(readPropertiesFileService
                      .getMessage("COM031-E", null));
                  castData0105d02.setOlCustConvList(null);
                  // Perform a roll-back of the given transaction.
                  txManager.rollback(status);
                  return;
                }
              }
              updateRecord(record, olCustConv, userCode, "MST01-05D02");
              mst0105d00Dao.getMstCustConvMapper().updateByPrimaryKeySelective(
                  record);
              numRecord++;
            }
          }
          // // Commit the given transaction, with regard to its status.
          txManager.commit(status);
        } catch (MyBatisSystemException e) {
          //// Perform a roll-back of the given transaction.
          txManager.rollback(status);
          logger.error(e.getMessage());
          throw e;
        }
      }
    }

    if (numRecord > 0) {
      // 正常処理
      ArrayList<String> paramMess = new ArrayList<String>();
      paramMess.add("オンライン得意先変換マスタの登録");
      castData0105d02.setMessage(readPropertiesFileService.getMessage(
          "COM002-I", paramMess));
      setHaitaDate(castData0105d02);
    } else {
      castData0105d02.setMessage(readPropertiesFileService.getMessage(
          "COM034-E", null));
      castData0105d02.setOlCustConvList(null);
    }

  }

  /**
   * Check input for the item when register data.
   * 
   * @param list オンライン得意先変換マスタ一覧
   * @return  true if the error occurs; false otherwise
   */
  private boolean checkInput(ArrayList<OlCustConvMasterData> list,
      CastData0105d02 castData0105d02) {
    String errorCode = "";
    String errorMsg = "";
    ArrayList<String> paramMsg = null;
    OlCustConvMasterData olCustConv = null;
    String line = "";
    for (int i = 0; i < list.size(); i++) {
      line = String.valueOf(i + 1);
      olCustConv = list.get(i);
      // O/Lセンター
      errorCode = Util.checkItem(olCustConv.getOlCenterCode(), true,
          InputCheckCom.TYPE_NUM_ALPHA, 13);
      if (errorCode != null) {
        errorCode = returnMessageCode(errorCode);
        paramMsg = new ArrayList<String>();
        paramMsg.add("O/Lセンター");
        paramMsg.add(line);
        errorMsg += readPropertiesFileService.getMessage(errorCode, paramMsg)
            + "<br>";
      }

      // O/L取引先
      errorCode = Util.checkItem(olCustConv.getOlTorihikiCode(), false,
          InputCheckCom.TYPE_NUM_ALPHA, 12);
      if (errorCode != null) {
        errorCode = returnMessageCode(errorCode);
        paramMsg = new ArrayList<String>();
        paramMsg.add("O/L取引先");
        paramMsg.add(line);
        errorMsg += readPropertiesFileService.getMessage(errorCode, paramMsg)
            + "<br>";
      }

      // 相手取引先
      errorCode = Util.checkItem(olCustConv.getAtTorihikiCode(), false,
          InputCheckCom.TYPE_NUM, 12);
      if (errorCode != null) {
        errorCode = returnMessageCode(errorCode);
        paramMsg = new ArrayList<String>();
        paramMsg.add("相手取引先");
        paramMsg.add(line);
        errorMsg += readPropertiesFileService.getMessage(errorCode, paramMsg)
            + "<br>";
      }

      // 相手店コード
      errorCode = Util.checkItem(olCustConv.getAtTenCode(), true,
          InputCheckCom.TYPE_NUM_ALPHA, 12);
      if (errorCode != null) {
        errorCode = returnMessageCode(errorCode);
        paramMsg = new ArrayList<String>();
        paramMsg.add("相手店コード");
        paramMsg.add(line);
        errorMsg += readPropertiesFileService.getMessage(errorCode, paramMsg)
            + "<br>";
      }

      // 自社得意先コード
      errorCode = Util.checkItem(olCustConv.getCustCode(), true,
          InputCheckCom.TYPE_NUM, 7);
      if (errorCode != null) {
        errorCode = returnMessageCode(errorCode);
        paramMsg = new ArrayList<String>();
        paramMsg.add("自社得意先コード");
        paramMsg.add(line);
        errorMsg += readPropertiesFileService.getMessage(errorCode, paramMsg)
            + "<br>";
      }

      // 自社店舗コード
      errorCode = Util.checkItem(olCustConv.getShopCode(), true,
          InputCheckCom.TYPE_NUM_ALPHA, 8);
      if (errorCode != null) {
        errorCode = returnMessageCode(errorCode);
        paramMsg = new ArrayList<String>();
        paramMsg.add("自社店舗コード");
        paramMsg.add(line);
        errorMsg += readPropertiesFileService.getMessage(errorCode, paramMsg)
            + "<br>";
      }

      // 納品区分
      errorCode = Util.checkItem(olCustConv.getTarget1(), true,
          InputCheckCom.TYPE_EM, 5);
      if (errorCode != null) {
        errorCode = returnMessageCode(errorCode);
        paramMsg = new ArrayList<String>();
        paramMsg.add("納品区分名");
        paramMsg.add(line);
        errorMsg += readPropertiesFileService.getMessage(errorCode, paramMsg)
            + "<br>";
      }

      // 得意先名称
      errorCode = Util.checkItem(olCustConv.getCustNmR(), false,
          InputCheckCom.TYPE_EM, 10);
      if (errorCode != null) {
        errorCode = returnMessageCode(errorCode);
        paramMsg = new ArrayList<String>();
        paramMsg.add("得意先名称");
        paramMsg.add(line);
        errorMsg += readPropertiesFileService.getMessage(errorCode, paramMsg)
            + "<br>";
      }

      // 店舗名称
      errorCode = Util.checkItem(olCustConv.getShopNmR(), false,
          InputCheckCom.TYPE_EM, 10);
      if (errorCode != null) {
        errorCode = returnMessageCode(errorCode);
        paramMsg = new ArrayList<String>();
        paramMsg.add("店舗名称");
        paramMsg.add(line);
        errorMsg += readPropertiesFileService.getMessage(errorCode, paramMsg)
            + "<br>";
      }

      // 納品区分
      errorCode = Util.checkItem(olCustConv.getDeliKb(), true,
          InputCheckCom.TYPE_NUM, 1);
      if (errorCode != null) {
        errorCode = returnMessageCode(errorCode);
        paramMsg = new ArrayList<String>();
        paramMsg.add("納品区分");
        paramMsg.add(line);
        errorMsg += readPropertiesFileService.getMessage(errorCode, paramMsg)
            + "<br>";
      }

      // 状況コード
      errorCode = Util.checkItem(olCustConv.getStsCode(), true,
          InputCheckCom.TYPE_NUM, 1);
      if (errorCode != null) {
        errorCode = returnMessageCode(errorCode);
        paramMsg = new ArrayList<String>();
        paramMsg.add("状況コード");
        paramMsg.add(line);
        errorMsg += readPropertiesFileService.getMessage(errorCode, paramMsg)
            + "<br>";
      }
      String stsCode = olCustConv.getStsCode();
      if (!stsCode.equalsIgnoreCase("")) {
        if (!stsCode.equalsIgnoreCase(MstConst.TORIKESHI)
            && !stsCode.equalsIgnoreCase(MstConst.TOROKU)) {
          paramMsg = new ArrayList<String>();
          paramMsg.add("状況コードは１または９で入力してください");
          errorMsg += readPropertiesFileService.getMessage("MST013-E", paramMsg)
              + "<br>";
        }
      }
    }

    if (errorMsg != null && !errorMsg.equalsIgnoreCase("")) {
      castData0105d02.setMessage(errorMsg);
      castData0105d02.setOlCustConvList(null);
      return true;
    }
    return false;
  }
  
  /**
   * Replaces the error code.
   * 
   * @param errorCode エラーコード
   * @return The new error code
   */
  private String returnMessageCode(String errorCode) {
    if (errorCode.equalsIgnoreCase("COM001-E")) {
      errorCode = "COM036-E";
    }
    
    if (errorCode.equalsIgnoreCase("COM006-E")) {
      errorCode = "COM035-E";
    }
    return errorCode;
  }
  /**
   * オンライン得意先マスタ存在チェック.
   * 
   * @param olCustConv オンライン得意先変換マスタ
   * @param castData0105d02 An object is used to send and receive data
   * @return オンライン得意先マスタ
   */
  private ArrayList<MstCustConv> searchMstConvCustData(
      OlCustConvMasterData olCustConv, CastData0105d02 castData0105d02) {
    String olTorihikiCodeWk = null;
    String atTenCodeWk = null;
    // 項目編集
    // オンライン取引先コード
    String olTorihikiCode = olCustConv.getOlTorihikiCode();
    if (olTorihikiCode.equalsIgnoreCase("")) {
      olTorihikiCodeWk = castData0105d02.getOlTorihikiCodeNone();
    } else {
      olTorihikiCodeWk = olTorihikiCode;
    }
    // 相手店コード
    String atTenCode = olCustConv.getAtTenCode();
    if (atTenCode.equalsIgnoreCase("")) {
      atTenCodeWk = castData0105d02.getTenCodeNone();
    } else {
      atTenCodeWk = atTenCode;
    }

    // オンライン得意先マスタ存在チェック
    Map<String, Object> parms = new HashMap<String, Object>();
    parms.put("OL_Center_Code", olCustConv.getOlCenterCode());
    parms.put("OL_Torihiki_Code", olTorihikiCodeWk);
    parms.put("AT_Torihiki_Code", olCustConv.getAtTorihikiCode());
    parms.put("AT_Ten_Code", atTenCodeWk);
    ArrayList<MstCustConv> custConvs = mst0105d00Dao.getMst0105d00Mapper().searchMstConvCustData(
        parms);
    return custConvs;
  }

  /**
   * Update the existing record.
   * 
   * @param record OL得意先変換マスタ
   * @param olCustConv オンライン得意先変換マスタ
   * @param userId 担当者コード
   * @param pgId プログラムID
   */
  private void updateRecord(MstCustConv record, OlCustConvMasterData olCustConv,
      String userId, String pgId) {
    String currentDate = DateUtil.getSysDate();
    String currentTime = DateUtil.getSysTime();

    record.setCustCode(olCustConv.getCustCode());
    record.setShopCode(olCustConv.getShopCode());
    record.setDeliKb(olCustConv.getDeliKb());
    record.setStsCode(olCustConv.getStsCode());

    record.setUpdUserid(userId);
    record.setUpdPgid(pgId);
    record.setUpdYmd(currentDate);
    record.setUpdTime(currentTime);
  }

  /**
   * OL得意先変換マスタ新規.
   * 
   * @param olCustConv オンライン得意先変換マスタ
   * @param userId 担当者コード
   * @param pgId プログラムID
   * @param olTorihikiCodeNone オンライン取引先コード_未指定 
   * @return OL得意先変換マスタ
   */
  public MstCustConv newRecord(OlCustConvMasterData olCustConv, String userId,
      String pgId, String olTorihikiCodeNone) {
    MstCustConv record = new MstCustConv();

    record.setOlCenterCode(olCustConv.getOlCenterCode());
    if (olCustConv.getOlTorihikiCode().equalsIgnoreCase("")) {
      record.setOlTorihikiCode(olTorihikiCodeNone);
    } else {
      record.setOlTorihikiCode(olCustConv.getOlTorihikiCode());
    }
    
    record.setAtTorihikiCode(olCustConv.getAtTorihikiCode());
    record.setAtTenCode(olCustConv.getAtTenCode());
    record.setCustCode(olCustConv.getCustCode());
    record.setShopCode(olCustConv.getShopCode());
    record.setDeliKb(olCustConv.getDeliKb());
    record.setStsCode(olCustConv.getStsCode());
    String currentDate = DateUtil.getSysDate();
    String currentTime = DateUtil.getSysTime();
    record.setInsUserid(userId);
    record.setInsPgid(pgId);
    record.setInsYmd(currentDate);
    record.setInsTime(currentTime);

    record.setUpdUserid(userId);
    record.setUpdPgid(pgId);
    record.setUpdYmd(currentDate);
    record.setUpdTime(currentTime);

    return record;
  }

  /**
   * 排他の設定.
   * 
   * @param cast:フォーム
   */
  public void setHaitaDate(CastData0105d02 castData0105d02) {
    castData0105d02.setHaitaDate(DateUtil.getSysDate());
    castData0105d02.setHaitaTime(DateUtil.getSysTime());
  }
  
  /**
   * Defaultメッセージの取得.
   * 
   * @param model A holder for model attributes
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
    defaultMsg.setMessageCode("COM001-I1");
    defaultMsg.setMessageTitle("確認メッセージ");
    paramMess = new ArrayList<String>();
    paramMess.add("画面表示時の状態に初期化");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-I", paramMess));
    defaultMsgLst.add(defaultMsg);

    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-I2");
    defaultMsg.setMessageTitle("確認メッセージ");
    paramMess = new ArrayList<String>();
    paramMess.add("登録");
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
    defaultMsg.setMessageCode("COM027-E");
    paramMess = new ArrayList<String>();
    paramMess.add("変換定義");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM027-E", paramMess));
    defaultMsgLst.add(defaultMsg);

    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("MST001-I");
    paramMess = new ArrayList<String>();
    paramMess.add("変換定義一覧");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "MST001-I", paramMess));
    defaultMsgLst.add(defaultMsg);

    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("MST002-I");
    paramMess = new ArrayList<String>();
    paramMess.add("変換定義一覧");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "MST002-I", paramMess));
    defaultMsgLst.add(defaultMsg);

    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM030-E");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM030-E", null));
    defaultMsgLst.add(defaultMsg);

    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM029-E");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM029-E", null));
    defaultMsgLst.add(defaultMsg);

    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("MST013-E");
    paramMess = new ArrayList<String>();
    paramMess.add("状況コードは１または９で入力してください");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "MST013-E", paramMess));
    defaultMsgLst.add(defaultMsg);

    model.addAttribute("defaultMessages", defaultMsgLst);
  }
}
