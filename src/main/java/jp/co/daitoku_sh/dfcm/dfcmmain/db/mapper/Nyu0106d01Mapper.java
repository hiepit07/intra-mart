/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper
 * ファイル名:Nyu0106d01Mapper.java
 * 
 * 作成者:都築電気
 * 作成日:2015/12/09
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/09 1.00 ACT)ANZAI 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ResultHandler;

import jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu.Nyu0106d01ShiwakeSakuseiRirekiInfo;

/**
 * 会計入金実績作成用SQL Mapper.
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public interface Nyu0106d01Mapper {

  /**
   * 仕訳作成履歴リストの取得
   * @return 作成履歴リスト
   */
  List<Nyu0106d01ShiwakeSakuseiRirekiInfo> getShiwakeSakuseiRirekiList();

  /**
   * 今回入金仕訳作成件数の取得
   * @return 対象件数
   */
  Map<String, Object> getKonkaiNyukinShiwakeSakuseiCount();

  /**
   * 入金仕訳リストの取得（逐次処理）
   * @param renkeiId 連携ID
   * @param handler 逐次処理
   * 
   */
  void getNyukinShiwakeList(String renkeiId, ResultHandler handler);

  /**
   * 売上仕訳リストの取得
   * @param renkeiId 連携ID
   * @param handler 逐次処理
   * 
   */
  void getUriageShiwakeList(String renkeiId, ResultHandler handler);

}
