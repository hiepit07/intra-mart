/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.util
 * ファイル名:PrintUtil.java
 *
 * 作成者:都築電気株式会社
 * 作成日:2015/09/27
 *
 * 履歴
 * -------------------------------------------------------------------------
 * 日付                 指示者      担当            内容
 * -------------------------------------------------------------------------
 * 2015/09/27 1.00                  TDK)安延        新規開発
 * -------------------------------------------------------------------------
 *
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
*/

package jp.co.daitoku_sh.dfcm.common.util;

import java.io.File;

import org.apache.log4j.Logger;


import com.fujitsu.systemwalker.outputassist.connector.ConnectorException;
import com.fujitsu.systemwalker.outputassist.connector.FormBase;
import com.fujitsu.systemwalker.outputassist.connector.FormsFile;
import com.fujitsu.systemwalker.outputassist.connector.PrintForm;
import com.fujitsu.systemwalker.outputassist.connector.PrintProperties;


import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.db.model.MstListDist;
import jp.co.daitoku_sh.dfcm.common.db.model.MstListDistKey;
import jp.co.daitoku_sh.dfcm.common.db.model.MstListForm;

/**
 * 印刷処理
 *
 * @author TSUZUKI DENKI
 * @version 1.0.0
 * @since 1.0.0
 */
public class PrintUtil {

    /** 共通DAO */
    private CommonDao commonDao;
    /** ロガー */
    private static Logger logger = Logger.getLogger(PrintUtil.class);

    /** マルチフォームフラグ（マルチフォーム） */
    private static final String MULTI_FORM_FLG_MULTI = "1";
    /** マルチフォームID区切り文字 */
    private static final String MULTI_FORM_DELIMIT = ";";

    /** 電子保存実行フラグ（電子保存する） */
    private static final String ELE_LIST_FLG_TRUE = "1";

    /**
     * 印刷処理
     *
     * @param commonDao CommonDao
     * @param listId 帳票ID
     * @param denPrint 伝票発行
     * @param jigyoCode 事業所コード
     * @param printStr 帳票文字列
     * @param dataFile データファイル
     */
    public PrintUtil(CommonDao commonDao) {

        this.commonDao = commonDao;
    }

    /**
     * 印刷処理を実行する。
     *
     * @param listId 帳票ID
     * @param denPrint 伝票発行
     * @param jigyoCode 事業所コード
     * @param printStr 帳票文字列
     * @param dataFile データファイル
     * @return 実行結果
     * @throws ConnectorException ListCreatorコネクターエラー
     */
    public String exePrint(String listId, String denPrint, String jigyoCode,
            String printStr, File dataFile) throws ConnectorException {
        String result = null;

        //帳票定義マスタ取得
        MstListForm mstListForm = commonDao.getMstListFormMapper().selectByPrimaryKey(listId);

        if (mstListForm == null) {
            //帳票定義マスタに設定が存在しません。
            return "COM032-E";
        }

        //帳票定義クラス初期化
        FormsFile formsFile = new FormsFile(mstListForm.getListDir());

        try {
            //データファイルセット
            formsFile.setDataFile(dataFile.getAbsolutePath(), FormBase.CODE_UTF8);
            //文字コードセット
            formsFile.setFileType(FormBase.CODE_UTF8);

            //マルチフォームの場合
            if (mstListForm.getMultiKb().equals(MULTI_FORM_FLG_MULTI)) {
                //マルチフォーム出力
                formsFile.setGrpOut(FormsFile.GRPOUT_GRP);
                //帳票名データ区切り文字種別を設定
                formsFile.setGrpDelimitMode(FormsFile.DELIMIT_TAB);
                //帳票定義体名を設定
                formsFile.setLcForm(getMultiFormId(mstListForm));
            } else {
                //マルチフォームでない場合
                formsFile.setScriptFile(listId);
            }

            //帳票配信マスタ取得
            MstListDist mstListDist = getMstListDist(listId, jigyoCode, denPrint,
                    mstListForm.getDenCls());
            if (mstListDist == null) {
                //帳票配信マスタに設定が存在しません。
                return "COM033-E";
            }

            //帳票設定情報セット
            PrintProperties printProperties = new PrintProperties();
            //通信対象コンピュータ
            printProperties.setProperty(PrintProperties.ID_HOST, mstListForm.getListSvrNm());
            //帳票の転送指定
            printProperties.setProperty(PrintProperties.ID_SENDRESOURCE, "NOSEND");
            //タイトル
            printProperties.setProperty(PrintProperties.ID_TITLE, printStr);
            //出力方法
            printProperties.setProperty(PrintProperties.ID_OUTPUTMODE, mstListForm.getOutPtn());
            //印刷部数（電子保存）
            printProperties.setProperty(PrintProperties.ID_LW_COPYNUMBERCOLLATE,
                    mstListForm.getPrintNum());
                    //配信フォルダ
                    //TODO LW jarファイル最新化が必要
                    //printProperties.setProperty(PrintProperties.ID_LW_DSTFOLDER,
                    //mstListDist.getDistFolderId());
                    //帳票印刷配信指定
                    //TODO LW jarファイル最新化が必要
                    //printProperties.setProperty(PrintProperties.ID_LW_DSTMETHOD, "MAT");

            //印刷処理実行
            PrintForm printForm = new PrintForm();
            try {
                printForm.PrintOut(formsFile, printProperties);
            } catch (ConnectorException ex) {
                ex.printStackTrace();
                logger.error(ex.getMessage());
                logger.error(ex.getDetailMessage());
                return "COM034-E";
            }

            //電子保存する場合は、再度印刷処理を実行する
            if (mstListForm.getEleListFlg() != null && mstListForm.equals(ELE_LIST_FLG_TRUE)) {

                //帳票配信マスタ取得
                MstListDist mstListDistEle = getMstListDist(mstListForm.getEleListId(),
                        jigyoCode, denPrint, mstListForm.getDenCls());
                if (mstListDistEle == null) {
                    //帳票配信マスタに設定が存在しません。
                    return "COM033-E";
                }

                //帳票設定情報セット
                PrintProperties printPropertiesEle = new PrintProperties();
                //通信対象コンピュータ
                printPropertiesEle.setProperty(PrintProperties.ID_HOST, mstListForm.getListSvrNm());
                //帳票の転送指定
                printPropertiesEle.setProperty(PrintProperties.ID_SENDRESOURCE, "NOSEND");
                //タイトル
                printPropertiesEle.setProperty(PrintProperties.ID_TITLE, printStr);
                //出力方法
                printPropertiesEle.setProperty(PrintProperties.ID_OUTPUTMODE,
                        mstListForm.getOutPtn());
                //印刷部数（電子保存）
                printPropertiesEle.setProperty(PrintProperties.ID_LW_COPYNUMBERCOLLATE,
                        mstListForm.getPrintNum());
                //電子保存装置名
                printPropertiesEle.setProperty(PrintProperties.ID_LW_DRVNAME,
                        mstListDist.getEleDeviceNm());
                //電子保存先パス
                printPropertiesEle.setProperty(PrintProperties.ID_LW_KEEPDIR,
                        mstListDist.getElePath());

                //印刷処理実行
                PrintForm printFormEle = new PrintForm();
                try {
                    printFormEle.PrintOut(formsFile, printPropertiesEle);
                } catch (ConnectorException ex) {
                    ex.printStackTrace();
                    logger.error(ex.getMessage());
                    logger.error(ex.getDetailMessage());
                    return "COM034-E";
                }
            }
        } finally {
            if (formsFile != null) {
                formsFile.cleanup();
            }
        }

        return result;
    }

    /**
     * 帳票定義マスタからマルチフォームIDを取得する。
     *
     * @param mstListForm 帳票定義マスタ
     * @return マルチフォームID
     */
    private String getMultiFormId(MstListForm mstListForm) {
        StringBuffer sb = new StringBuffer();
        sb.append(mstListForm.getListId());
        //マルチ帳票ID_1 ～ 3 が存在する場合は、「;」区切りの文字列として結合する。
        if (mstListForm.getListId1() != null && !mstListForm.getListId1().equals("")) {
            sb.append(MULTI_FORM_DELIMIT);
            sb.append(mstListForm.getListId1());
        }
        if (mstListForm.getListId2() != null && !mstListForm.getListId2().equals("")) {
            sb.append(MULTI_FORM_DELIMIT);
            sb.append(mstListForm.getListId2());
        }
        if (mstListForm.getListId3() != null && !mstListForm.getListId3().equals("")) {
            sb.append(MULTI_FORM_DELIMIT);
            sb.append(mstListForm.getListId3());
        }
        return sb.toString();
    }

    /**
     * 帳票配信マスタを主キーで検索します。
     *
     * @param listId 帳票ID
     * @param jigyoCode 事業所コード
     * @param denPrint 伝票発行
     * @param decCls 伝票区分
     * @return 帳票配信マスタ情報
     */
    private MstListDist getMstListDist(String listId, String jigyoCode,
            String denPrint, String decCls) {
        MstListDistKey key = new MstListDistKey();
        key.setListId(listId);
        key.setJigyoCode(jigyoCode);
        key.setDenPrint(denPrint);
        key.setDenCls(decCls);
        MstListDist mstListDist = commonDao.getMstListDistMapper().selectByPrimaryKey(key);
        return mstListDist;
    }
}
