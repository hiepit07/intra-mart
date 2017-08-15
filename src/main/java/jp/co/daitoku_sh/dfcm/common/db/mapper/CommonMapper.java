/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.db.mapper
 * ファイル名:CommonMapper.java
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

package jp.co.daitoku_sh.dfcm.common.db.mapper;

import jp.co.daitoku_sh.dfcm.common.db.model.MstCourse;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCustTanka;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCustomer;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSNohin;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSSeihn;
import jp.co.daitoku_sh.dfcm.common.db.model.MstShop;

import java.util.List;
import java.util.Map;

/**
 * 共通Mapperクラス
 * 
 * @author ACT)福田
 * @version 1.0.0
 * @since 1.0.0
 */
public interface CommonMapper {

  /**
   * 得意先マスタ情報取得.
   *
   * @param parms データ取得条件
   * @return 得意先情報
   */
  MstCustomer getCustomerData(Map<String, Object> parms);

  /**
   * 店舗情報取得.
   *
   * @param parms データ取得条件
   * @return 店舗情報
   */
  MstShop getShopData(Map<String, Object> parms);

  /**
   * 商品コードデータ取得.
   *
   * @param parms データ取得条件
   * @return 商品コード
   */
  String getItemCode(Map<String, Object> parms);

  /**
   * 得意先商品コードデータ取得.
   *
   * @param parms データ取得条件
   * @return 得意先商品コード
   */
  String getCustomerItemCode(Map<String, Object> parms);

  /**
   * 出荷伝票出力品コードデータ取得.
   *
   * @param parms データ取得条件
   * @return 出荷伝票出力品コード
   */
  String getShipCode(Map<String, Object> parms);

  /**
   * 品番データ取得.
   *
   * @param parms データ取得条件
   * @return 品番
   */
  String getPartNumber(Map<String, Object> parms);

  /**
   * 製品マスタデータ取得.
   *
   * @param parms データ取得条件
   * @return 製品マスタ情報
   */
  MstSSeihn getProductData(Map<String, Object> parms);

  /**
   * 製品事業所マスタデータ取得.
   *
   * @param parms データ取得条件
   * @return 製品事業所マスタ情報
   */
  MstSSeihn getProductPlantData(Map<String, Object> parms);

  /**
   * コース情報取得.
   *
   * @param parms データ取得条件
   * @return コース情報
   */
  MstCourse getCourseData(Map<String, Object> parms);

  /**
   * 単価情報取得.
   *
   * @param parms データ取得条件
   * @return コース情報
   */
  List<MstCustTanka> getCustTankaData(Map<String, Object> parms);

  /**
   * 納品先情報取得.
   *
   * @param parms データ取得条件
   * @return 納品先情報
   */
  MstSNohin getDeliveryData(Map<String, Object> parms);
  
  /**
   * 得意先名称取得.
   *
   * @param parms データ取得条件
   * @return 得意先名称
   */
  List<MstCustomer> getCustomerName(Map<String, Object> parms);
  
}
