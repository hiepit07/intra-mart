/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper
 * ファイル名:Mst0199d00Mapper.java
 * 
 * <p>作成者:QuanTran
 * 作成日:2015/09/23
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/23 1.00 ABV)QuanTran 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.session.ResultHandler;

import jp.co.daitoku_sh.dfcm.common.db.model.MstCustConv;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCustomer;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.OlCustConvMasterData;

/**
 * 一覧画面でのマップ
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */

public interface Mst0105d00Mapper {

  /**
   * オンライン得意先変換マスタより該当する得意先名称を取得する。
   * 
   * @param map The alias for the parameter
   * @return オンラインセンターコード
   */

  ArrayList<MstCustomer> getCustNmR(Map<String, Object> map);

  /**
   * オンライン得意先変換マスタより該当する得意先名称を取得する。
   * 
   * @param map The alias for the parameter
   * @return 相手取引先コード
   */
  ArrayList<MstCustomer> getPartnerCustCode(Map<String, Object> map);
  
  /**
   * ヘッダー部のオンラインセンターコード、オンライン取引先コード、相手取引先コードでマスタ存在チェックを行う。
   * 
   * @param map The alias for the parameter
   * @return 相手取引先コード
   */
  ArrayList<MstCustomer> getPartnerCustCode02(Map<String, Object> map);
  
  /**
   * オンラインセンター名称取得処理
   * 
   * @param map The alias for the parameter
   * @return オンラインセンター名称取得
   */
  ArrayList<MstCustomer> getOlCenterName(Map<String, Object> map);
  
  /**
   * 相手取引先名称取得
   * 
   * @param map The alias for the parameter
   * @return 相手取引先名称
   */
  ArrayList<MstCustomer> getOppCustName(Map<String, Object> map);
  
  /**
   * 【関数】OL得意先変換マスタ一覧データ取得
   * 
   * @param map The alias for the parameter
   * @return オンライン得意先変換マスタ一覧表示
   */
  ArrayList<OlCustConvMasterData> getOlCustConvMasterDataList(
      Map<String, Object> parms);

  /**
   * 【関数】OL得意先変換マスタ一覧データ取得
   * 
   * @param map The alias for the parameter
   * @return オンライン得意先変換マスタ一覧表示
   */
  void getOlCustConvMasterInfoListForCsv(
      Map<String, Object> parms, ResultHandler handler);

  /**
   * オンライン得意先変換マスタデータ取得
   * 
   * @param parms The alias for the parameter
   * @return オンライン得意先変換マスタデータ
   */
  ArrayList<OlCustConvMasterData> getOlCustConvDataList02(
      Map<String, Object> parms);

  /**
   * オンライン得意先変換マスタより該当する得意先名称を取得する。
   * 
   * @param map The alias for the parameter
   * @return オンラインセンターコード
   */
  ArrayList<MstCustomer> getCustNmRForCsv(Map<String, Object> map);

  /**
   * オンライン得意先変換マスタより該当する得意先名称を取得する。
   * 
   * @param map The alias for the parameter
   * @return 相手取引先コード
   */
  ArrayList<MstCustomer> getPartnerCustCodeForCsv(Map<String, Object> map);
  /**
   * オンライン得意先マスタ存在チェック
   * 
   * @param parms The alias for the parameter
   * @return オンライン得意先マスタ
   */
  ArrayList<MstCustConv> searchMstConvCustData(Map<String, Object> parms);
}
