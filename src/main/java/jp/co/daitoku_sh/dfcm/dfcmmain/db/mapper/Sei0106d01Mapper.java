/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper
 * ファイル名:Sei0106d01Mapper.java
 * 
 * <p>作成者:QuanTran
 * 作成日:2015/12/08
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/08 1.00 ABV)QuanTran 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ResultHandler;

import jp.co.daitoku_sh.dfcm.common.db.model.MstListForm;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstSJigyoInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.model.PrintSei0106d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.model.RstSei0106d01;

/**
 * 請求一覧画面用SQL Mapper
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 *
 */

public interface Sei0106d01Mapper {
  /**
   * 事業所情報検索.
   * 
   * @param parms An object that maps keys to values
   * @return 事業所情報
   */
  List<MstSJigyoInfo> getSJigyoInfo(Map<String, Object> parms);
  
  /**
   * 以下の条件で請求先情報を取得する。.
   * 
   * @param parms An object that maps keys to values
   * @return 請求先情報
   */
  ArrayList<RstSei0106d01> getBillInfo(Map<String, Object> parms);
  
  /**
   *  帳票管理マスタからデータ取得
   *  
   * @param parms An object that maps keys to values
   * @return 帳票定義マスタ
   */
  ArrayList<MstListForm> getMstListForm(Map<String, Object> parms);
  
  /**
   * 以下の条件で得意先・店舗毎明細情報を取得する
   * 
   * @param parms An object that maps keys to values
   * @return 得意先・店舗毎明細情報
   */
  ArrayList<PrintSei0106d01> getCustShop(Map<String, Object> parms);
  
  
  void getCustShop(Map<String, Object> parms, ResultHandler handler);
  
  /**
   * 以下の条件で伝票毎明細情報を取得する
   * 
   * @param parms An object that maps keys to values
   * @return 伝票毎明細情報
   */
  ArrayList<PrintSei0106d01> getDenpyo(Map<String, Object> parms);
  
  void getDenpyo(Map<String, Object> parms, ResultHandler handler);
}
