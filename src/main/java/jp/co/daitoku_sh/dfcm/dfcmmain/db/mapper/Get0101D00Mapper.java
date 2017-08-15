/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper
 * ファイル名:Get0101D00Mapper.java
 * 
 *<p>作成者:HiepTruong
 * 作成日:2015/10/07
 * 
 *<p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/07 1.00 ABV)HiepTruong 新規開発
 * -------------------------------------------------------------------------
 * 
 *<p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper;

import java.util.ArrayList;
import java.util.Map;

import jp.co.daitoku_sh.dfcm.dfcmmain.component.get.GetHeaderUirageGet0101d00;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.get.GetNextMonthAccountGet0101d00;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.get.GetOfficeInformationGet0101d00;

/**
 * Get0101D00 Mapperクラス
 * 
 * @author HiepTruong
 * @version 1.0.0
 * @since 1.0.0
 */

public interface Get0101D00Mapper {

  /**
   * 事業所情報検索
   *
   *  @param parms データ取得条件
   * @return 事業所情報
   */
  ArrayList<GetOfficeInformationGet0101d00> getofficeinfo(
      Map<String, Object> params);

  /**
   * 請求締め処理チェック
   *
   * @param parms データ取得条件
   * @return 請求締め処理チェック
   */
  ArrayList<GetHeaderUirageGet0101d00> getHeaderUirageHeader(
      Map<String, Object> params);

  /**
   * 事業所月次確定情報から月次確定情報を取得する取得
   *
   * @param parms データ取得条件
   * @return 事業所月次確定情報
   */
  GetNextMonthAccountGet0101d00 getNextMonthAccountGet0101d00(
      Map<String, Object> params);

  /**
   * 未処理の請求締め処理が存在するか売上ヘッダ情報でチェックする
   *
   * @param parms データ取得条件
   * @return 未処理の請求締め処理
   */
  GetNextMonthAccountGet0101d00 getNextMonthAccountReceivableGet0101d00(
      Map<String, Object> params);
}
