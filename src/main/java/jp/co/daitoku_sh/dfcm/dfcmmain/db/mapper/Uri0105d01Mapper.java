/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper
 * ファイル名:Uri0105d01Mapper.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/12/09
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/09 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper;

import java.util.ArrayList;
import java.util.Map;

import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstSJigyoInfo;

/**
 * 一覧画面でのマップ
 * 
 * @author アクトブレーンベトナム コイー
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Uri0105d01Mapper {

  ArrayList<MstSJigyoInfo> getSJigyoInfo(Map<String, Object> parms);
  
}
