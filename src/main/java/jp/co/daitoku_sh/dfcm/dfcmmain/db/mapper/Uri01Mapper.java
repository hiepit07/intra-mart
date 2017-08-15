/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper
 * ファイル名:Uri01Mapper.java
 * 
 * <p>作成者:アクトブレーン
 * 作成日:2015/11/04
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/08/24 1.00 AB)前田 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper;

import java.util.List;
import java.util.Map;

import jp.co.daitoku_sh.dfcm.common.db.model.TblSei01UrkMshHead;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.uri.SubjectData;

/**
 * 売上サブシステム共通Mapper
 * 画面固有のMapperを作成する場合は当クラスを継承する
 * 
 * @author アクトブレーン 前田
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Uri01Mapper {

  List<TblSei01UrkMshHead> getUrkMshHead(Map<String, Object> parms);

  List<SubjectData> getSubjectData(Map<String, Object> parms);
}
