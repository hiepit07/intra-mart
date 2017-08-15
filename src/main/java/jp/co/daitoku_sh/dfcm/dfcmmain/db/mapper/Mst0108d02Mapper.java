/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper
 * ファイル名:Mst0108d02Mapper.java
 * 
 * <p>作成者:NghiaNguyen
 * 作成日:2015/11/16
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/11/16 1.00 ABV)NghiaNguyen 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.daitoku_sh.dfcm.common.db.model.MstCustTanka;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.Mst0108d02Model;



public interface Mst0108d02Mapper {

  /**
   * 
   * @param parm
   * @return
   */
  List<Mst0108d02Model> getDefautData(Map<String, Object> parm);
  
  List<Mst0108d02Model> getItemName(Map<String, Object> parm);
  
  ArrayList<MstCustTanka> searchInforCustTanka(Map<String, Object> parm);
}
