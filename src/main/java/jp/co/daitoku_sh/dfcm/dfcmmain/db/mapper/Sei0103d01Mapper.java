/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper
 * ファイル名:Sei0103d01Mapper.java
 * 
 * 作成者:都築電気
 * 作成日:2015/10/29
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/29 1.00 ACT)ANZAI 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper;

import jp.co.daitoku_sh.dfcm.dfcmmain.component.sei.SeikyusakiInfo;

import java.util.List;
import java.util.Map;

/**
 * 臨時締め処理画面用SQL Mapper.
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public interface Sei0103d01Mapper {

  /**
   * 請求先情報リストの取得.
   * 
   * @param params パラメータ
   * @return 検索結果
   */
  List<SeikyusakiInfo> getSeikyusakiInfoList(Map<String, Object> params);

}
