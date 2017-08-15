/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper
 * ファイル名:Mst0104d02Mapper.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/10/02
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/02 1.00 ABV)NhungMa 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper;

import java.util.List;
import java.util.Map;

import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstCusJgyo0104d02;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.MstDeliveryMst0104d02;

/**
 * 一覧画面でのマップ
 * 
 * @author NhungMa
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Mst0104d02Mapper {

  List<MstDeliveryMst0104d02> searchDeliveryList(Map<String, Object> parms);

  List<MstCusJgyo0104d02> searchCustomer(Map<String, Object> parms);
}