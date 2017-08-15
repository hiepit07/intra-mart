/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.util
 * ファイル名:CommonGetData.java
 *
 * <p>作成者:株式会社アクト・ブレーン
 * 作成日:2015/09/01
 *
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/01 1.00 ACT)福田 新規開発
 * -------------------------------------------------------------------------
 *
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.common.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.MyBatisSystemException;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.component.CourseData;
import jp.co.daitoku_sh.dfcm.common.component.CustomerData;
import jp.co.daitoku_sh.dfcm.common.component.ItemBodyData;
import jp.co.daitoku_sh.dfcm.common.component.ItemDetailInfo;
import jp.co.daitoku_sh.dfcm.common.component.JigyoData;
import jp.co.daitoku_sh.dfcm.common.component.ListFormData;
import jp.co.daitoku_sh.dfcm.common.component.NohinData;
import jp.co.daitoku_sh.dfcm.common.component.ShopData;
import jp.co.daitoku_sh.dfcm.common.component.TankaData;
import jp.co.daitoku_sh.dfcm.common.component.UserData;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCourse;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCustTanka;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCustomer;
import jp.co.daitoku_sh.dfcm.common.db.model.MstListForm;
import jp.co.daitoku_sh.dfcm.common.db.model.MstListFormExample;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSJigyo;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSJigyoExample;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSNohin;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSSeihn;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSSeijgyo;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSSeijgyoExample;
import jp.co.daitoku_sh.dfcm.common.db.model.MstShop;
import jp.co.daitoku_sh.dfcm.common.db.model.MstUser;
import jp.co.daitoku_sh.dfcm.common.db.model.MstUserExample;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;

/**
 * (共通部品)データ取得クラス
 *
 * @author ACT)福田
 * @version 1.0.0
 * @since 1.0.0
 */
public class CommonGetData {

  /** 共通DAO. */
  private CommonDao commonDao;
  /** プロパティファイル読み込み. */
  private ReadPropertiesFileService readPropertiesFileService;
  /** ログ出力用. */
  private Dfcmlogger logger = new Dfcmlogger();

  /**
   * コンストラクタ.
   *
   * @param init 共通DAO
   */
  public CommonGetData(CommonDao init,
      ReadPropertiesFileService readPropertiesFileService) {
    this.commonDao = init;
    this.readPropertiesFileService = readPropertiesFileService;
  }

  /**
   * 事業所情報取得.
   *
   * @param jgycd：事業所コード
   * @return 事業所名称格納クラス
   */
  public JigyoData getJigyoData(String jgycd) {
    JigyoData out = new JigyoData(); // 事業所名称格納クラス
    // 共通部品初期化
    CommonGetSystemCom systemCom = new CommonGetSystemCom(commonDao, null, null,
        readPropertiesFileService);

    try {
      // 検索条件設定
      MstSJigyoExample example = new MstSJigyoExample();
      // 業務日付取得
      Integer bday = Integer.valueOf(systemCom.getAplDate());
      if (null != bday) {
        example.createCriteria()
            .andJgycdEqualTo(jgycd)
            .andDelflgEqualTo(CommonConst.DEL_FLG_OFF)
            .andStrymdLessThanOrEqualTo(bday)
            .andEndymdGreaterThanOrEqualTo(bday);
        // データ取得
        List<MstSJigyo> ljgy = commonDao.getJgyMapper().selectByExample(
            example);
        if (null == ljgy || ljgy.isEmpty()) {
          // データが存在しない
          out.setMsgCd("COM009-E");
        } else {
          out.setJgy(ljgy.get(0));
        }
      } else {
        out.setMsgCd("COM009-E");
      }
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }

    return out;
  }

  /**
   * 得意先マスタ情報取得.
   *
   * @param custCode：得意先コード
   * @param jigyoCode：受注事業所コード
   * @param chkType：チェック種別（0：無条件、1：得意先、2：請求先、3：取り纏め請求先）
   * @return 得意先情報格納クラス
   */
  public CustomerData getCustomerData(String custCode, String jigyoCode,
      int chkType) {
    CustomerData out = new CustomerData();
    // 共通部品初期化
    CommonGetSystemCom systemCom = new CommonGetSystemCom(commonDao, null, null,
        readPropertiesFileService);

    try {
      // 業務日付取得
      Integer bday = Integer.valueOf(systemCom.getAplDate());
      if (null != bday) {
        Map<String, Object> parms = new HashMap<String, Object>();
        parms.put("custCode", custCode);
        parms.put("jigyoCode", jigyoCode);
        parms.put("chkType", chkType);
        parms.put("businessDate", bday);
        parms.put("cstFlg", CommonConst.CST_FLG);
        parms.put("billFlg", CommonConst.BILL_FLG);
        parms.put("billSumFlg", CommonConst.BILL_SUM_FLG);
        parms.put("stsCode", CommonConst.STS_CD_ENTRY);
        // データ取得
        MstCustomer cst = commonDao.getComMapper().getCustomerData(parms);
        if (null == cst) {
          // データが存在しない
          out.setMsgCd("COM009-E");
        } else {
          out.setCst(cst);
        }
      } else {
        out.setMsgCd("COM009-E");
      }
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }

    return out;
  }

  /**
   * 店舗マスタ情報取得.
   *
   * @param shopCode：店舗コード
   * @param custCode：得意先コード
   * @return 店舗情報格納クラス
   */
  public ShopData getShopData(String shopCode, String custCode) {
    ShopData out = new ShopData();
    // 共通部品初期化
    CommonGetSystemCom systemCom = new CommonGetSystemCom(commonDao, null, null,
        readPropertiesFileService);

    try {
      // 検索条件設定
      // 業務日付取得
      Integer bday = Integer.valueOf(systemCom.getAplDate());
      if (null != bday) {
        Map<String, Object> parms = new HashMap<String, Object>();
        parms.put("custCode", custCode);
        parms.put("shopCode", shopCode);
        parms.put("businessDate", bday);
        parms.put("stsCode", CommonConst.STS_CD_ENTRY);
        // データ取得
        MstShop shp = commonDao.getComMapper().getShopData(parms);
        if (null == shp) {
          // データが存在しない
          out.setMsgCd("COM009-E");
        } else {
          out.setShp(shp);
        }
      } else {
        out.setMsgCd("COM009-E");
      }
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }

    return out;
  }

  /**
   * 担当者氏名取得.
   *
   * @param userCode：担当者コード
   * @param jigyoshoCode：事業所コード
   * @return 担当者氏名格納クラス
   */
  public UserData getUserData(String userCode, String jigyoshoCode) {
    UserData out = new UserData();

    try {
      // 検索条件設定
      MstUserExample example = new MstUserExample();
      example.createCriteria()
          .andUserCodeEqualTo(userCode)
          .andJigyoshoCodeEqualTo(jigyoshoCode);
      // データ取得
      List<MstUser> lusr = commonDao.getUsrMapper().selectByExample(example);
      if (null == lusr || lusr.isEmpty()) {
        // データが存在しない
        out.setMsgCd("COM009-E");
      } else {
        out.setUsr(lusr.get(0));
      }
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }

    return out;
  }

  /**
   * 品目明細情報取得.
   *
   * @param itemCd：品目コード
   * @param custCode：得意先コード
   * @param shopCode：店舗コード
   * @param salesKb：販売区分（ヘッダ部）
   * @param deliveryYmd 納品日
   * @param binKbn：便区分
   * @param chncd：チェーンコード
   * @param chneda：チェーン枝番
   * @param itemCdKbn：品目コード区分
   * @return 品目明細情報格納クラス
   */
  public ItemBodyData getItemBodyData(String itemCd, String custCode,
      String shopCode, String salesKb, Integer deliveryYmd, Short binKbn,
      Short chncd, Short chneda, int itemCdKbn, String orderJigyoCd) {
    // 自社品目コード
    String companyItemCd = "";
    // 得意先品目コード
    String customerItemCd = "";
    ItemBodyData out = new ItemBodyData();
    // 共通部品初期化
    CommonGetSystemCom systemCom = new CommonGetSystemCom(commonDao, null, null,
        readPropertiesFileService);

    try {
      /*********************************************
       * 商品変換マスタデータ取得.
       *********************************************/
      // 検索条件設定
      // 品目コード区分 = '0'（自社）の場合
      if (itemCdKbn == CommonConst.ITEM_CD_KBN_JISHA) {
        Map<String, Object> parms1 = new HashMap<String, Object>();
        parms1.put("itemCd", itemCd);
        parms1.put("custCode", custCode);
        parms1.put("chncd", chncd);
        parms1.put("chneda", chneda);
        parms1.put("deliveryYmd", deliveryYmd);
        parms1.put("binKbn", binKbn);
        parms1.put("delFlg", CommonConst.DEL_FLG_OFF);
        customerItemCd = commonDao.getComMapper().getItemCode(parms1);
        if (null == customerItemCd) {
          // データが存在しない
          parms1.put("stsCode", CommonConst.STS_CD_ENTRY);
          customerItemCd = commonDao.getComMapper().getCustomerItemCode(parms1);
          if (null == customerItemCd) {
            // データが存在しない
            // 業務日付取得
            Integer bday = Integer.valueOf(systemCom.getAplDate());
            if (null != bday) {
              parms1.put("businessDate", bday);
              String shipCode = commonDao.getComMapper().getShipCode(parms1);
              if (null != shipCode) {
                // 出荷伝票出力品コード = '1'（自社品目コード）の場合
                if (shipCode.equals(CommonConst.DELIVERY_OUTPUT_CD_JISHA)) {
                  companyItemCd = itemCd;
                  customerItemCd = "";
                } else if (shipCode.equals(
                    CommonConst.DELIVERY_OUTPUT_CD_AITE)) { // 出荷伝票出力品コード =
                  // '2'（相手商品コード）の場合、エラーとする
                  out.setMsgCd("COM009-E");
                  out.setOrderKb(CommonConst.GEN_MASTER_KBN_ITEM_MASTER); // 商品変換マスタ
                  return out;
                } else {
                  out.setMsgCd("COM009-E");
                  out.setOrderKb(CommonConst.GEN_MASTER_KBN_ITEM_MASTER); // 商品変換マスタ
                  return out;
                }
              }
            } else {
              out.setMsgCd("COM009-E");
              out.setOrderKb(CommonConst.GEN_MASTER_KBN_ITEM_MASTER); // 商品変換マスタ
              return out;
            }
          } else {
            companyItemCd = itemCd;
          }
        } else {
          companyItemCd = itemCd;
        }
      } else if (itemCdKbn == CommonConst.ITEM_CD_KBN_CUST) { // 品目コード区分 =
                                                              // '1'（得意先）の場合
        Map<String, Object> parms2 = new HashMap<String, Object>();
        parms2.put("itemCd", itemCd);
        parms2.put("chncd", chncd);
        parms2.put("chneda", chneda);
        parms2.put("deliveryYmd", deliveryYmd);
        parms2.put("binKbn", binKbn);
        parms2.put("delFlg", CommonConst.DEL_FLG_OFF);
        companyItemCd = commonDao.getComMapper().getPartNumber(parms2);
        if (null == companyItemCd) {
          // データが存在しない
          out.setMsgCd("COM009-E");
          out.setOrderKb(CommonConst.GEN_MASTER_KBN_ORDER_PRODUCT_HISTORY); // 受注製品履歴
          return out;
        } else {
          customerItemCd = itemCd;
        }
      }

      /*********************************************
       * 製品マスタデータ取得.
       *********************************************/
      // 検索条件設定
      Map<String, Object> parms3 = new HashMap<String, Object>();
      parms3.put("companyItemCd", companyItemCd);
      parms3.put("chncd", chncd);
      parms3.put("chneda", chneda);
      parms3.put("deliveryYmd", deliveryYmd);
      parms3.put("binKbn", binKbn);
      parms3.put("delFlg", CommonConst.DEL_FLG_OFF);
      MstSSeihn shn = commonDao.getComMapper().getProductData(parms3);
      if (null == shn) {
        // データが存在しない
        out.setMsgCd("COM009-E");
        out.setOrderKb(CommonConst.GEN_MASTER_KBN_PRODUCT_MASTER); // 製品マスタ
        return out;
      }

      /*********************************************
       * 製品事業所マスタデータ取得.
       *********************************************/
      // 検索条件設定
      MstSSeijgyoExample example = new MstSSeijgyoExample();
      example.createCriteria().andCainCodeEqualTo(chncd).andCainIdxEqualTo(
          chneda).andItemCodeEqualTo(Integer.valueOf(companyItemCd))
          .andJigyoshoCodeEqualTo(Short.valueOf(orderJigyoCd));
      List<MstSSeijgyo> sjg = commonDao.getMstSSeijgyoMapper().selectByExample(
          example);
      if (null == sjg || sjg.isEmpty()) {
        // データが存在しない
        out.setMsgCd("COM009-E");
        out.setOrderKb(CommonConst.GEN_MASTER_KBN_PRODUCT_PLANT_MASTER); // 製品事業所マスタ
        return out;
      } else {
        // 単価情報取得
        TankaData tankaData = getTankaData(custCode, shopCode, companyItemCd,
            salesKb, deliveryYmd);
        if (null != tankaData.getMsgCd()) {
          // データが存在しない
          out.setMsgCd("COM009-E");
          out.setOrderKb(CommonConst.GEN_MASTER_KBN_MATERIAL_PRICE_MASTER); // 品目価格マスタ
          return out;
        } else {
          // 該当する得意先が存在する場合、取得した値を変数にそれぞれセットする
          ItemDetailInfo itemDetailInfo = new ItemDetailInfo();
          itemDetailInfo.setCompanyItemCd(companyItemCd);
          itemDetailInfo.setCustomerItemCd(customerItemCd);
          itemDetailInfo.setHinmei(shn.getHinmei());
          itemDetailInfo.setHinkana(shn.getHinkana());
          itemDetailInfo.setHinryaku(shn.getHinryaku());
          itemDetailInfo.setIreme(sjg.get(0).getIreme());
          itemDetailInfo.setWeight1(sjg.get(0).getWeight1());
          itemDetailInfo.setCustDeliTanka(tankaData.getTcu()
              .getCustDeliTanka());
          itemDetailInfo.setCustSellTanka(tankaData.getTcu()
              .getCustSellTanka());
          itemDetailInfo.setCustBildTanka(tankaData.getTcu()
              .getCustBildTanka());
          itemDetailInfo.setBmncd(shn.getBmncd());
          itemDetailInfo.setSeikeicd(shn.getSeikeicd());
          itemDetailInfo.setOndocd(shn.getOndocd());
          itemDetailInfo.setSikirika(shn.getSikirika());
          itemDetailInfo.setBunruiCode(tankaData.getTcu().getBunruiCode());
          out.setItemDetailInfo(itemDetailInfo);
        }
      }
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }

    return out;
  }

  /**
   * コース情報取得.
   *
   * @param jigyoCode 受注事業所コード
   * @param custCode 得意先コード
   * @param shopCode 店舗コード
   * @param deliKb 納品区分
   * @param binKb 便区分
   * @param shopKbn 店舗区分
   * @return コース情報格納クラス
   * @exception MyBatisSystemException
   */
  public CourseData getCourseData(String jigyoCode, String custCode,
      String shopCode, String deliKb, String binKb, int shopKbn) {
    CourseData out = new CourseData();

    try {
      // 検索条件設定
      Map<String, Object> parms = new HashMap<String, Object>();
      parms.put("jigyoCode", jigyoCode);
      parms.put("custCode", custCode);
      if (shopKbn == CommonConst.SHOP_KBN_ARI) {
        parms.put("shopCode", shopCode);
      } else if (shopKbn == CommonConst.SHOP_KBN_NA) {
        parms.put("shopCode", CommonConst.SHOP_CD_NONE);
      }
      parms.put("deliKb", deliKb);
      parms.put("binKb", binKb);
      parms.put("stsCode", CommonConst.STS_CD_ENTRY);
      MstCourse crs = commonDao.getComMapper().getCourseData(parms);
      if (null == crs) {
        // データが存在しない
        out.setMsgCd("COM009-E");
      } else {
        out.setCrs(crs);
      }
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }

    return out;
  }

  /**
   * 単価情報取得.
   *
   * @param custCode：得意先コード
   * @param shopCode：店舗コード
   * @param itemCode：自社品目コード
   * @param salesKb：販売区分
   * @param deliveryYmd：納品日
   * @return 単価情報格納クラス
   */
  public TankaData getTankaData(String custCode, String shopCode,
      String itemCode, String salesKb, Integer deliveryYmd) {
    TankaData out = new TankaData();

    try {
      // 検索条件設定
      Map<String, Object> parms = new HashMap<String, Object>();
      parms.put("case", 1);
      parms.put("custCode", custCode);
      if (null == shopCode) {
        parms.put("shopCode", CommonConst.SHOP_CD_NONE);
      } else {
        parms.put("shopCode", shopCode);
      }
      parms.put("itemCode", itemCode);
      parms.put("salesKb", salesKb);
      parms.put("deliveryYmd", deliveryYmd);
      parms.put("stsCode", CommonConst.STS_CD_ENTRY);
      List<MstCustTanka> tcu1 = commonDao.getComMapper().getCustTankaData(
          parms);
      if (null == tcu1 || tcu1.isEmpty()) {
        // データが存在しない
        parms.put("case", 2);
        List<MstCustTanka> tcu2 = commonDao.getComMapper().getCustTankaData(
            parms);
        if (null == tcu2 || tcu2.isEmpty()) {
          // データが存在しない
          out.setMsgCd("COM009-E");
        } else {
          out.setTcu(tcu2.get(0));
        }
      } else {
        out.setTcu(tcu1.get(0));
      }
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }

    return out;
  }

  /**
   * 出荷伝票情報取得.
   *
   * @param listId：手入力出荷伝票帳票ID
   * @return 伝票情報格納クラス
   */
  public ListFormData getListFormData(String listId) {
    ListFormData out = new ListFormData();

    try {
      // 検索条件設定
      MstListFormExample example = new MstListFormExample();
      example.createCriteria()
          .andListIdEqualTo(listId);
      // データ取得
      List<MstListForm> lfrm = commonDao.getMstListFormMapper().selectByExample(
          example);
      if (null == lfrm || lfrm.isEmpty()) {
        // データが存在しない
        out.setListNm(null);
      } else {
        out.setListId(lfrm.get(0).getListId());
        out.setDenCls(lfrm.get(0).getDenCls());
        out.setListNm(lfrm.get(0).getListNm());
        out.setListNmR(lfrm.get(0).getListNmR());
        out.setLineNumber(lfrm.get(0).getLineNumber());
        // 共通部品【入力チェック.数値フォーマットチェック】
        String inputCheckResult = InputCheckCom.chkNum(String.valueOf(out
            .getLineNumber()), false);
        // [変数]処理結果 <> Nullの場合、
        if (null != inputCheckResult) {
          out.setLineNumber(CommonConst.COM_ERR_SHORT_VAL);
        }
      }
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }

    return out;
  }

  /**
   * 納品先情報取得.
   *
   * @param chncd：チェーンコード
   * @param chneda チェーン枝番
   * @param jgycd：事業所コード
   * @param deliveryYmd：納品日
   * @param binKbn：便区分
   * @param nhscd：納品先コード
   * @return 納品先情報格納クラス
   */
  public NohinData getNohinData(Short chncd, Short chneda, Short jgycd,
      Integer deliveryYmd, Short binKbn, Short nhscd) {
    NohinData out = new NohinData();
    // エントリ日付区分
    Short entryDateKubun = null;
    // 減算日数WK
    Integer genzanNissuuWk = null;
    // 生産日WK
    Integer seisanBiWk = null;

    try {
      // 検索条件設定
      Map<String, Object> parms = new HashMap<String, Object>();
      parms.put("chncd", chncd);
      parms.put("chneda", chneda);
      parms.put("nhscd", nhscd);
      parms.put("jgycd", jgycd);
      parms.put("deliveryYmd", deliveryYmd);
      parms.put("binKbn", binKbn);
      parms.put("delFlg", CommonConst.DEL_FLG_OFF);
      MstSNohin nhn = commonDao.getComMapper().getDeliveryData(parms);
      if (null == nhn) {
        // データが存在しない
        out = null;
      } else {
        if (binKbn == 1) {
          // 便区分 = '1' の場合、エントリ日付区分 = NHN.エントリ日付区分１
          entryDateKubun = nhn.getEntymdkbn1();
        } else if (binKbn == 2) {
          // 便区分 = '2' の場合、エントリ日付区分 = NHN.エントリ日付区分２
          entryDateKubun = nhn.getEntymdkbn2();
        } else if (binKbn == 3) {
          // 便区分 = '3' の場合、エントリ日付区分 = NHN.エントリ日付区分３
          entryDateKubun = nhn.getEntymdkbn3();
        } else {
          // エラー
          out = null;
        }

        if (entryDateKubun != null) {
          if (entryDateKubun == CommonConst.ENTRY_DATE_KB_CURRENT_DAY) {
            // エントリ日付区分 = '1'（当日）の場合、減算日数WK = 0
            genzanNissuuWk = 0;
          } else if (entryDateKubun == CommonConst.ENTRY_DATE_KB_PREV_DAY) {
            // エントリ日付区分 = '2'（前日）の場合、減算日数WK = 1
            genzanNissuuWk = 1;
          } else if (entryDateKubun == CommonConst.ENTRY_DATE_KB_PREV_2_DAY) {
            // エントリ日付区分 = '3'（前々日）の場合、減算日数WK = 2
            genzanNissuuWk = 2;
          } else if (entryDateKubun == CommonConst.ENTRY_DATE_KB_NOT_SET) {
            // エントリ日付区分 = '9'（便なし）の場合、減算日数WK = 0
            genzanNissuuWk = 0;
          } else {
            // エラー
            out = null;
          }
        }

        if (genzanNissuuWk != null) {
          // 生産日WK = 納品日 - 減算日数WK（日付計算）
          seisanBiWk = deliveryYmd - genzanNissuuWk;
        } else {
          // エラー
          out = null;
        }

        if (seisanBiWk != null) {
          out.setNhn(nhn);
          out.setProductionDate(seisanBiWk);
        } else {
          // エラー
          out = null;
        }
      }
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }

    return out;
  }
}
