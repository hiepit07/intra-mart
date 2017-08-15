/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper
 * ファイル名:Mst0109d02Mapper.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/11/13
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/11/13 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper;

import java.util.List;
import java.util.Map;

import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstCustInfo0109d02;

/**
 * 登録画面でのマップ
 * 
 * @author アクトブレーンベトナム コイー
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Mst0109d02Mapper {

  List<MstCustInfo0109d02> getCustInfo(Map<String, Object> parms);
  
  String getCorrectInfo(Map<String, Object> parms);
}
