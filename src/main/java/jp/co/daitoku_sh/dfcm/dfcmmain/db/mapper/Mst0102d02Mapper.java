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

package jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper;

import jp.co.daitoku_sh.dfcm.common.db.model.MstSJigyo;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSNohin;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.Mst0102d02CustomerData;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.Mst0102d02CustomerJigyo;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.Mst0102d02ListForm;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstCourseInformation;

import java.util.List;
import java.util.Map;

/**
 * 共通Mapperクラス
 * 
 * @author ACT)福田
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Mst0102d02Mapper {

  /**
   * 得意先マスタ情報取得.
   *
   * @param parms データ取得条件
   * @return 得意先情報
   */
  Mst0102d02CustomerData getCustomerData(Map<String, Object> parms);

  /**
   * 得意先事業所マスタ取得.
   *
   * @param parms データ取得条件
   * @return 得意先事業所情報
   */
  List<Mst0102d02CustomerJigyo> getCustomerJigyouData(Map<String, Object> parms);

  /**
   * 事業所マスタデータ取得.
   *
   * @param parms データ取得条件
   * @return 事業所情報
   */
  List<MstSJigyo> getJigyouData(Map<String, Object> parms);

  /**
   * 納品先マスタ情報取得.
   *
   * @param parms データ取得条件
   * @return 納品先情報
   */
  List<MstSNohin> getDeliveryData(Map<String, Object> parms);
  
  /**
   * 帳票定義マスタ取得.
   *
   * @param parms データ取得条件
   * @return 帳票定義情報
   */
  List<Mst0102d02ListForm> getListFormData(Map<String, Object> parms);

  /**
   * チェーンマスタからチェーン名称取得.
   *
   * @param parms データ取得条件
   * @return チェーン名称
   */
  String getChainName(Map<String, Object> parms);

  /**
   * コースマスタ取得.
   *
   * @param parms データ取得条件
   * @return コース情報
   */
  List<MstCourseInformation> getCourseData(Map<String, Object> parms);

  /**
   * 伝票採番マスタ存在チェック.
   *
   * @param parms データチェック条件
   * @return 得意先コード
   */
  String checkDenpyouSaibanData(Map<String, Object> parms);
}
