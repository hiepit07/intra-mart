/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.util
 * ファイル名:InputCheckJuc.java
 *
 * 作成者:都築電気株式会社
 * 作成日:2015/09/24
 *
 * 履歴
 * -------------------------------------------------------------------------
 * 日付                 指示者      担当            内容
 * -------------------------------------------------------------------------
 * 2015/09/24 1.00                  TDK)安延        新規開発
 * -------------------------------------------------------------------------
 *
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
*/

package jp.co.daitoku_sh.dfcm.common.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.component.MstGeneralData;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;

/**
 * 入力チェック（受注共通）
 *
 * @author TSUZUKI DENKI
 * @version 1.0.0
 * @since 1.0.0
 */
public class InputCheckJuc {

    /** 共通DAO */
    private CommonDao commonDao;
    /** ロガー */
    private static Logger logger = Logger.getLogger(InputCheckJuc.class);
    /** メッセージ取得 */
    private ReadPropertiesFileService readPropertiesFileService;

    /**
     * コンストラクタ
     *
     * @param in CommonDao
     * @param msg ReadPropertiesFileService
     */
    public InputCheckJuc(CommonDao in, ReadPropertiesFileService msg) {
        commonDao = in;
        readPropertiesFileService = msg;
    }

    /**
     * 先日付チェック
     *
     * @param deliDate 納品日
     * @param jucDate 受注日付
     * @return チェック結果
     */
    public String chkPostDate(String deliDate, String jucDate) {
        String result = null;
        int postDateSu = 0; //先日付範囲日数

        //納品日の日付フォーマットチェック
        result = InputCheckCom.chkDate(deliDate, CommonConst.DATE_FORMAT_YMD);
        if (result != null) {
            return result;
        }

        //汎用マスタから先日付範囲日数を取得
        CommonGetSystemCom com = new CommonGetSystemCom(commonDao,null,null,null);
        List<MstGeneralData> haniList = com.getMstGeneralConf("Date_Forward", null);

        if (haniList == null || haniList.get(0).getTarget1() == null
                || haniList.get(0).getTarget1().equals("")) {

            ArrayList<String> paramMess = new ArrayList<String>();
            paramMess.add("汎用マスタ");
            paramMess.add("先日付範囲日数");
            //ログ出力
            logger.error(readPropertiesFileService.getMessage("COM009-E", paramMess));

            postDateSu = 0;
        } else {
            postDateSu = Integer.valueOf(haniList.get(0).getTarget1()).intValue();
        }

        //先日付チェック
        java.util.Date postDate = DateUtil.getSpecDay(
                DateUtil.toDate(jucDate, CommonConst.DATE_FORMAT_YMD), postDateSu);

        if (deliDate.compareTo(DateUtil.setFormat(postDate, CommonConst.DATE_FORMAT_YMD)) > 0) {
            result = "COM003-E";
        }

        return result;
    }

}
