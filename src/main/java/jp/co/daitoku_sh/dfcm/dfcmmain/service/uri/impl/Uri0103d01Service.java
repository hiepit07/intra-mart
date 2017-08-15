/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.uri.impl
 * ファイル名:Uri0103d01Service.java
 * 
 * <p>作成者:アクトブレーン
 * 作成日:2015/12/10
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/10 1.00 AB)前田 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.service.uri.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.ui.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.component.OneSpecPriceData;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.db.model.TblUri01Body;
import jp.co.daitoku_sh.dfcm.common.db.model.TblUri01BodyExample;
import jp.co.daitoku_sh.dfcm.common.db.model.TblUri01Head;
import jp.co.daitoku_sh.dfcm.common.db.model.TblUri01HeadExample;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.NumberUtil;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.UriConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.uri.BillSourceInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.uri.FormUri0103d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.uri.Uri0103d01Screen;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.uri.UriageData;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.uri.UriageItem;

/**
 * 再請求売上登録サービスクラス
 * 
 * @author アクトブレーン 前田
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class Uri0103d01Service extends Uri01Service {

  @Autowired
  @Qualifier("commonDao")
  private CommonDao commonDao;

  @Autowired
  @Qualifier("readPropertiesFileService")
  private ReadPropertiesFileService readPropertiesFileService;

  @Autowired
  @Qualifier("txManager")
  private PlatformTransactionManager txManager;

  public CommonDao getCommonDao() {
    return this.commonDao;
  }

  public void setCommonDao(CommonDao commonDao) {
    this.commonDao = commonDao;
  }

  /**
   * JavaScriptで表示するメッセージを取得し、再請求売上登録modelへセットする.
   * 
   * @param model 再請求売上登録model
   */
  public void getDefaultMess(Model model) {
    DefaultMessages defaultMsg = null;

    // {1}します。よろしいですか？
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-I");
    defaultMsg.setMessageTitle(UriConst.MESSAGE_TITLE_CONFIRM);
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-I", null));
    ArrayList<DefaultMessages> defaultMsgLst = new ArrayList<DefaultMessages>();
    defaultMsgLst.add(defaultMsg);

    // {1}が完了しました
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM002-I");
    defaultMsg.setMessageTitle(UriConst.MESSAGE_TITLE_CONFIRM);
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM002-I", null));
    defaultMsgLst.add(defaultMsg);

    // 行No.{1}が未入力です。このまま登録しますか？
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM013-I");
    defaultMsg.setMessageTitle(UriConst.MESSAGE_TITLE_CONFIRM);
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM013-I", null));
    defaultMsgLst.add(defaultMsg);

    // 自社伝票No {1}が登録されました。
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM014-I");
    defaultMsg.setMessageTitle(UriConst.MESSAGE_TITLE_CONFIRM);
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM014-I", null));
    defaultMsgLst.add(defaultMsg);

    // {1}の値が正しくありません。
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-E");
    defaultMsg.setMessageTitle(UriConst.MESSAGE_TITLE_ERROR);
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-E", null));
    defaultMsgLst.add(defaultMsg);

    // 納品金額上限（{1}円）を超過しました。
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM004-E");
    defaultMsg.setMessageTitle(UriConst.MESSAGE_TITLE_ERROR);
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM004-E", null));
    defaultMsgLst.add(defaultMsg);

    // 元の{1}を超える{1}は指定できません。
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM005-E");
    defaultMsg.setMessageTitle(UriConst.MESSAGE_TITLE_ERROR);
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM005-E", null));
    defaultMsgLst.add(defaultMsg);

    // {1}は必須項目です。
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM006-E");
    defaultMsg.setMessageTitle(UriConst.MESSAGE_TITLE_ERROR);
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM006-E", null));
    defaultMsgLst.add(defaultMsg);

    // // {1}に{2}は指定できません。
    // defaultMsg = new DefaultMessages();
    // defaultMsg.setMessageCode("COM007-E");
    // defaultMsg.setMessageTitle(UriConst.MESSAGE_TITLE_ERROR);
    // defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
    // "COM007-E", null));
    // defaultMsgLst.add(defaultMsg);
    //
    // {1}に{2}が存在しません。
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM009-E");
    defaultMsg.setMessageTitle(UriConst.MESSAGE_TITLE_ERROR);
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM009-E", null));
    defaultMsgLst.add(defaultMsg);

    // {1}の{2}に失敗しました。
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM023-E");
    defaultMsg.setMessageTitle(UriConst.MESSAGE_TITLE_ERROR);
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM023-E", null));
    defaultMsgLst.add(defaultMsg);

    // 編集が行われていません。
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM034-E");
    defaultMsg.setMessageTitle(UriConst.MESSAGE_TITLE_ERROR);
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM034-E", null));
    defaultMsgLst.add(defaultMsg);

    // {1}元伝票に存在しない{2}を追加する事はできません。
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("URI015-E");
    defaultMsg.setMessageTitle(UriConst.MESSAGE_TITLE_ERROR);
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "URI015-E", null));
    defaultMsgLst.add(defaultMsg);

    // {1}元伝票より{2}を増やす事はできません。
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("URI016-E");
    defaultMsg.setMessageTitle(UriConst.MESSAGE_TITLE_ERROR);
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "URI016-E", null));
    defaultMsgLst.add(defaultMsg);

    // 該当の売上情報は{1}画面から{2}してください
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("URI017-E");
    defaultMsg.setMessageTitle(UriConst.MESSAGE_TITLE_ERROR);
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "URI017-E", null));
    defaultMsgLst.add(defaultMsg);

    // 該当の売上情報は締め処理未実施のため、再請求できません
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("URI018-E");
    defaultMsg.setMessageTitle(UriConst.MESSAGE_TITLE_ERROR);
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "URI018-E", null));
    defaultMsgLst.add(defaultMsg);

    // 該当の売上情報は再請求実施済となっています
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("URI019-E");
    defaultMsg.setMessageTitle(UriConst.MESSAGE_TITLE_ERROR);
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "URI019-E", null));
    defaultMsgLst.add(defaultMsg);

    // {1}に{2}が指定されています。
    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-W");
    defaultMsg.setMessageTitle(UriConst.MESSAGE_TITLE_WARNING);
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-W", null));
    defaultMsgLst.add(defaultMsg);

    model.addAttribute("defaultMessages", defaultMsgLst);
  }

  /**
   * 請求元売上情報リスト情報を取得する.
   * 
   * @param strJigyoCode 事業所コード（システム管理者の場合、null(検索条件対象外)）
   * @param strBillSrcNo 請求元伝票No
   * 
   * @return List&lt;BillSourceInfo&gt; 請求元売上選択ダイアログ情報リスト
   */
  public List<BillSourceInfo> getBillSourceList(String strJigyoCode,
      String strBillSrcNo) {
    List<TblUri01Head> lstUriHead = new ArrayList<TblUri01Head>();
    List<BillSourceInfo> lstBillSrcInfo = new ArrayList<BillSourceInfo>();

    try {
      // 売上ヘッダ検索条件セット
      TblUri01HeadExample uriHeadExample = new TblUri01HeadExample();
      TblUri01HeadExample.Criteria uriHeadCriteria = uriHeadExample
          .createCriteria();
      if (strJigyoCode != null && !strJigyoCode.equalsIgnoreCase("")) {
        uriHeadCriteria.andOfficeCodeEqualTo(strJigyoCode); // 事業所コード
      }
      uriHeadCriteria.andCustDenNoEqualTo(strBillSrcNo); // 請求元伝票No
      uriHeadCriteria.andStsCodeEqualTo(CommonConst.STS_CD_ENTRY); // 状況コード
      uriHeadExample.setOrderByClause(
          "Deli_Date, Bin, Cust_Code, Shop_Code, UPD_YMD DESC, UPD_TIME DESC");

      // 売上ヘッダ検索
      lstUriHead = uri01Dao.getUri01HeadMapper().selectByExample(
          uriHeadExample);
      if (lstUriHead == null || lstUriHead.isEmpty()) {
        return null;
      }

      // 請求元伝票情報
      BillSourceInfo billSrcInfo = null;

      // 同一の得意先伝票Noのレコードを納品日・便区分・得意先コード・店舗コードでサマリー
      for (TblUri01Head tblUriHead : lstUriHead) {
        // 請求元伝票情報 != Null（２件目以降）、納品日・便区分・得意先コード・店舗コードが一致、
        if (billSrcInfo != null
            && billSrcInfo.getDeliDate().equalsIgnoreCase(tblUriHead
                .getDeliDate())
            && billSrcInfo.getBinKb().equalsIgnoreCase(tblUriHead.getBin())
            && billSrcInfo.getCustCode().equalsIgnoreCase(tblUriHead
                .getCustCode())
            && billSrcInfo.getShopCode().equalsIgnoreCase(tblUriHead
                .getShopCode())) {
          // カウントをインクリメント
          billSrcInfo.setCount(billSrcInfo.getCount() + 1);
        } else {
          // 上記以外（１件目 or 新しい請求元情報）
          if (billSrcInfo != null) {
            lstBillSrcInfo.add(billSrcInfo);
          }
          // 売上ヘッダ情報を退避
          billSrcInfo = new BillSourceInfo();
          billSrcInfo.setJigyoCode(tblUriHead.getOfficeCode()); // 事業所コード
          billSrcInfo.setDeliDate(tblUriHead.getDeliDate()); // 納品日
          billSrcInfo.setBinKb(tblUriHead.getBin()); // 便区分
          billSrcInfo.setCustCode(tblUriHead.getCustCode()); // 得意先コード
          billSrcInfo.setCustName(tblUriHead.getCustNmR()); // 得意先名称
          billSrcInfo.setShopCode(tblUriHead.getShopCode()); // 店舗コード
          billSrcInfo.setShopName(tblUriHead.getShopNmR()); // 店舗名称
          billSrcInfo.setCount(1); // 重複件数
          billSrcInfo.setDenNo(tblUriHead.getDenNo()); // 自社伝票No（更新日時最新）
          billSrcInfo.setBillFlg(tblUriHead.getBillFlg()); // 締め処理フラグ（更新日時最新）
        }
      }
      // 最後の１件を追加
      lstBillSrcInfo.add(billSrcInfo);
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }
    return lstBillSrcInfo;
  }

  /**
   * 請求元売上情報取得
   * 
   * @param strBillSrcNo 請求元伝票No
   * @param strCustCode 得意先コード
   * @param strShopCode 店舗コード
   * @param strBinKb 便区分
   * @param strDeliDate 納品日
   * @return UriageData 請求元売上情報
   */
  public UriageData getBillSrcUriageData(String strBillSrcNo,
      String strCustCode, String strShopCode, String strBinKb,
      String strDeliDate) {
    UriageData uriData = new UriageData();
    List<TblUri01Head> lstUriHead = new ArrayList<TblUri01Head>();

    try {
      // 売上ヘッダ検索条件セット
      TblUri01HeadExample uriHeadExample = new TblUri01HeadExample();
      TblUri01HeadExample.Criteria uriHeadCriteria = uriHeadExample
          .createCriteria();
      uriHeadCriteria.andCustDenNoEqualTo(strBillSrcNo); // 得意先伝票No
      uriHeadCriteria.andCustCodeEqualTo(strCustCode); // 得意先コード
      uriHeadCriteria.andShopCodeEqualTo(strShopCode); // 店舗コード
      uriHeadCriteria.andBinEqualTo(strBinKb); // 便区分
      uriHeadCriteria.andDeliDateEqualTo(strDeliDate); // 納品日
      uriHeadCriteria.andStsCodeEqualTo(CommonConst.STS_CD_ENTRY); // 状況コード
      uriHeadExample.setOrderByClause("UPD_YMD DESC, UPD_TIME DESC");

      // 売上ヘッダ検索
      lstUriHead = uri01Dao.getUri01HeadMapper()
          .selectByExample(uriHeadExample);
      if (lstUriHead == null || lstUriHead.isEmpty()) {
        return null;
      }

      TblUri01Head tblUriHead = lstUriHead.get(1);
      uriData.setUriHead(tblUriHead);

      // 売上明細検索条件セット
      TblUri01BodyExample uri01BodyExample = new TblUri01BodyExample();
      TblUri01BodyExample.Criteria uriBodyCriteria = uri01BodyExample
          .createCriteria();
      uriBodyCriteria.andDenNoEqualTo(tblUriHead.getDenNo()); // 自社伝票No
      uriBodyCriteria.andDenIdxEqualTo(tblUriHead.getDenIdx()); // 自社伝票枝番
      uriBodyCriteria.andStsCodeEqualTo(CommonConst.STS_CD_ENTRY); // 状況コード
      uri01BodyExample.setOrderByClause("Cust_Den_Row"); // 伝票行No
      // 売上明細検索
      List<TblUri01Body> lstUriBody = uri01Dao.getUri01BodyMapper()
          .selectByExample(uri01BodyExample);
      if (lstUriBody == null || lstUriBody.isEmpty()) {
        uriData.setMsgCd("COM009-E");
        return uriData;
      }
      uriData.setLstUriBody(lstUriBody);
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }
    return uriData;
  }

  /**
   * 売上情報（黒伝票）を生成する.
   * 
   * @param model 再請求売上登録model
   * @param formUri0103d01 再請求売上登録form
   * 
   * @return UriageData 売上情報
   */
  public UriageData createUriageData(Model model,
      FormUri0103d01 formUri0103d01) {
    UriageData uriData = new UriageData();

    // JSON型をUriItem型に変換
    UriageItem[] uriItem = this.convJson2UriItem(formUri0103d01
        .getHdnItemJson());
    if (uriItem == null) {
      uriData.setMsgCd("COM023-E");
      return uriData;
    }

    // JSON型をUri0103d01Screen型に変換
    Uri0103d01Screen screen = this.convJson2Screen(formUri0103d01
        .getHdnJsonBillSrc());
    if (screen == null) {
      uriData.setMsgCd("COM023-E");
      return uriData;
    }

    TblUri01Head tblUriHeadBillSrc = screen.getUriData().getUriHead(); // 請求元売上ヘッダ

    // 売上明細情報
    List<TblUri01Body> lstUriBody = new ArrayList<TblUri01Body>();
    for (int idx = 0; idx < uriItem.length; idx++) {
      if (uriItem[idx].getItemCode() != null
          && !uriItem[idx].getItemCode().trim().equalsIgnoreCase("")) {
        TblUri01Body tblUriBodyBillSrc = screen.getUriData().getLstUriBody()
            .get(idx); // 請求元売上明細
        short shtRowNo = (short) (idx + 1);

        // 売上明細情報生成
        TblUri01Body tblUriBody = this.createUriBody(formUri0103d01,
            tblUriBodyBillSrc, uriItem[idx], shtRowNo);
        try {
          tblUriBody = this.setCalculationDataUriBody(tblUriBody,
              tblUriHeadBillSrc);
        } catch (Exception ex) {
          return null;
        }
        lstUriBody.add(tblUriBody);
      }
    }
    uriData.setLstUriBody(lstUriBody);

    // 売上ヘッダ情報生成
    TblUri01Head tblUriHead = this.createUriHead(formUri0103d01,
        tblUriHeadBillSrc);
    tblUriHead = this.setCalculationDataUriHead(lstUriBody, tblUriHead,
        tblUriHeadBillSrc, formUri0103d01.getStrTaxFrcKb());

    uriData.setUriHead(tblUriHead);

    return uriData;
  }

  /**
   * 売上情報（赤伝票）を生成する.
   * 
   * @param model 再請求売上登録model
   * @param formUri0103d01 再請求売上登録form
   * 
   * @return UriageData 売上情報
   */
  public UriageData createUriageDataRed(Model model,
      FormUri0103d01 formUri0103d01) {
    UriageData uriDataRed = new UriageData();
    UriageData uriDataOrg = new UriageData();

    // 売上元情報取得
    try {
      String strJigyoCode = formUri0103d01.getHdnJigyoCode();
      long lngSlipNo = new Long(formUri0103d01.getHdnSlipNo()).longValue();
      uriDataOrg = this.getUriageData(strJigyoCode, lngSlipNo, formUri0103d01
          .getHdnOperateMode());
      if (uriDataOrg.getMsgCd() != null) {
        uriDataRed.setMsgCd(uriDataOrg.getMsgCd());
        return uriDataRed;
      }

      // 排他チェック
      TblUri01Head uriHeadOrg = uriDataOrg.getUriHead();
      String strUpdateDateTimeOrg = uriHeadOrg.getUpdYmd().concat(uriHeadOrg
          .getUpdTime());
      String strUpdateDateTime = formUri0103d01.getHdnUpdateDateTime();
      if (!strUpdateDateTime.equalsIgnoreCase(strUpdateDateTimeOrg)) {
        uriDataRed.setMsgCd("COM014-E");
        return uriDataRed;
      }
    } catch (MyBatisSystemException e) {
      uriDataRed.setMsgCd("COM023-E");
      return uriDataRed;
    }

    TblUri01Head tblUriHeadRed = this.createUriHeadRed(uriDataOrg.getUriHead(),
        formUri0103d01.getHdnRegistUserCode());
    uriDataRed.setUriHead(tblUriHeadRed);

    List<TblUri01Body> lstUriBodyRed = this.createUriBodyRed(uriDataOrg
        .getLstUriBody(), formUri0103d01.getHdnRegistUserCode());
    uriDataRed.setLstUriBody(lstUriBodyRed);

    return uriDataRed;
  }

  /**
   * JavaScriptから渡されたScreen情報（JSON型）をUri0103d01Screenクラスに変換する.
   * 
   * @param strJsonData JavaScriptから渡されたScreen情報（JSON型）
   * 
   * @return Uri0103d01Screen Screen情報リスト
   */
  private Uri0103d01Screen convJson2Screen(String strJsonData) {
    Uri0103d01Screen[] lstScreen = null;

    try {
      // JSON型の品目情報をクラス配列に格納
      ObjectMapper om = new ObjectMapper();
      lstScreen = om.readValue(strJsonData, Uri0103d01Screen[].class);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      return null;
    }
    return lstScreen[0];
  }

  /**
   * 売上ヘッダ情報（黒伝票）を生成する.
   * 
   * @param formUri0103d01 再請求売上登録form
   * @param screen 再請求売上登録screen
   * 
   * @return TblUri01Head 売上ヘッダ情報
   */
  private TblUri01Head createUriHead(FormUri0103d01 formUri0103d01,
      TblUri01Head tblUriHeadBillSrc) {
    TblUri01Head tblUriHead = new TblUri01Head();
    // TODO
    String strOperateMode = formUri0103d01.getHdnOperateMode();

    tblUriHead.setDenNo(new Long(formUri0103d01.getHdnSlipNo()).longValue()); // 自社伝票No
    /* 自社伝票枝番 */
    short shtSlipIdx = formUri0103d01.getHdnSlipIdx();
    // 処理区分 = '修正'の場合、
    if (strOperateMode.equalsIgnoreCase(CommonConst.SHORI_KB_MNT)) {
      shtSlipIdx = (short) (shtSlipIdx + 2);
    }
    tblUriHead.setDenIdx(shtSlipIdx);
    tblUriHead.setDatKb(UriConst.DATA_KB_RECLAIM); // データ区分
    tblUriHead.setDenKb(UriConst.DEN_KB_URIAGE); // 伝票区分
    tblUriHead.setCorrectType(UriConst.MODIFY_TYPE_NONE); // 修正データ種別
    tblUriHead.setDenNoOrigin(tblUriHeadBillSrc.getDenNoOrigin()); // 返品／欠品元伝票No
    tblUriHead.setOfficeCode(tblUriHeadBillSrc.getOfficeCode()); // 事業所コード
    tblUriHead.setJigyoNm(tblUriHeadBillSrc.getJigyoNm()); // 事業所名
    tblUriHead.setChainCode(tblUriHeadBillSrc.getChainCode()); // チェーンコード
    tblUriHead.setChainIdx(tblUriHeadBillSrc.getChainIdx()); // チェーン枝番
    tblUriHead.setChainNm(tblUriHeadBillSrc.getChainNm()); // チェーン名
    tblUriHead.setCustCode(tblUriHeadBillSrc.getCustCode()); // 得意先コード
    tblUriHead.setCustNm(tblUriHeadBillSrc.getCustNm()); // 得意先名称
    tblUriHead.setCustNmKana(tblUriHeadBillSrc.getCustNmKana()); // 得意先名称カナ
    tblUriHead.setCustNmR(tblUriHeadBillSrc.getCustNmR()); // 得意先略称
    tblUriHead.setCustDenNo(tblUriHeadBillSrc.getCustDenNo()); // 得意先伝票No
    tblUriHead.setShopCode(tblUriHeadBillSrc.getShopCode()); // 店舗コード
    tblUriHead.setShopNm(tblUriHeadBillSrc.getShopNm()); // 店舗名称
    tblUriHead.setShopNmKana(tblUriHeadBillSrc.getShopNmKana()); // 店舗名称カナ
    tblUriHead.setShopNmR(tblUriHeadBillSrc.getShopNmR()); // 店舗略称
    tblUriHead.setDenPrint(tblUriHeadBillSrc.getDenPrint()); // 伝票発行
    tblUriHead.setDeliDate(tblUriHeadBillSrc.getDeliDate()); // 納品日
    tblUriHead.setOrdrDate(tblUriHeadBillSrc.getOrdrDate()); // 受注日
    tblUriHead.setAdupDate(tblUriHeadBillSrc.getAdupDate()); // 売上計上日
    tblUriHead.setBin(tblUriHeadBillSrc.getBin()); // 便
    tblUriHead.setDeliCode(tblUriHeadBillSrc.getDeliCode()); // 納品先コード
    tblUriHead.setDeliNm(tblUriHeadBillSrc.getDeliNm()); // 納品先名
    tblUriHead.setDeliNmKana(tblUriHeadBillSrc.getDeliNmKana()); // 納品先カナ
    tblUriHead.setDeliKb(tblUriHeadBillSrc.getDeliKb()); // 納品区分
    tblUriHead.setSaleKb(tblUriHeadBillSrc.getSaleKb()); // 販売区分
    tblUriHead.setCourseCode(tblUriHeadBillSrc.getCourseCode()); // コースコード
    tblUriHead.setCosNm(tblUriHeadBillSrc.getCosNm()); // コース名称
    tblUriHead.setCosNmR(tblUriHeadBillSrc.getCosNmR()); // コース略称
    tblUriHead.setSnpDenNo(tblUriHeadBillSrc.getSnpDenNo()); // 先方伝票No
    tblUriHead.setShipsKb(tblUriHeadBillSrc.getShipsKb()); // 手入力伝票発行
    tblUriHead.setShipsTypId(tblUriHeadBillSrc.getShipsTypId()); // 手入力出荷伝票帳票ID
    tblUriHead.setShipsNm(tblUriHeadBillSrc.getShipsNm()); // 出荷伝票名称
    tblUriHead.setShipsLine(tblUriHeadBillSrc.getShipsLine()); // 出荷伝票行数
    tblUriHead.setCollectKb(tblUriHeadBillSrc.getCollectKb()); // 修正区分
    tblUriHead.setUserCodeReg(tblUriHeadBillSrc.getUserCodeReg()); // 入力担当者コード
    tblUriHead.setUserNmReg(tblUriHeadBillSrc.getUserNmReg()); // 入力担当者氏名
    tblUriHead.setUserNmKanaReg(tblUriHeadBillSrc.getUserNmKanaReg()); // 入力担当者氏名カナ
    /* 合計金額系・消費税率は別途セットする */
    tblUriHead.setTaxIncKb(tblUriHeadBillSrc.getTaxIncKb()); // 内税顧客区分
    tblUriHead.setTaxIncFrcKb(tblUriHeadBillSrc.getTaxIncFrcKb()); // 内税消費税端数処理
    tblUriHead.setShipsRudKb(tblUriHeadBillSrc.getShipsRudKb()); // 伝票業計算金額まるめ
    tblUriHead.setAmtRndKb(tblUriHeadBillSrc.getAmtRndKb()); // 金額丸め区分
    tblUriHead.setSkrRndKb(tblUriHeadBillSrc.getSkrRndKb()); // 仕切丸め区分
    tblUriHead.setSkrRndKeta(tblUriHeadBillSrc.getSkrRndKeta()); // 仕切丸め少数桁
    tblUriHead.setcSaleKb(tblUriHeadBillSrc.getcSaleKb()); // チェーン販売区分
    tblUriHead.setDeliDatFlg(UriConst.PROCESSING_COMPLETE); // 納品データ連携フラグ
    tblUriHead.setListPrintFlg(UriConst.PROCESSING_COMPLETE); // 請求チェックリスト印刷フラグ
    tblUriHead.setBillPrintFlg(UriConst.PROCESSING_NOTYET); // 請求書印刷フラグ
    tblUriHead.setBillFlg(UriConst.PROCESSING_NOTYET); // 締め処理フラグ
    tblUriHead.setBillDate(tblUriHeadBillSrc.getBillDate()); // 入金調整請求締日
    tblUriHead.setBillDenNo(formUri0103d01.getHdnBillSrcNo()); // 請求元伝票No
    tblUriHead.setRecKb(UriConst.RECORD_KB_BLACK); // レコード区分
    tblUriHead.setFwdDeliDate(tblUriHeadBillSrc.getFwdDeliDate()); // 先日付データフラグ
    tblUriHead.setCustCls(tblUriHeadBillSrc.getCustCls()); // 得意先種別
    tblUriHead.setBunruiCode(tblUriHeadBillSrc.getBunruiCode()); // 分類コード
    tblUriHead.setHinKakakuBunruiCode(tblUriHeadBillSrc
        .getHinKakakuBunruiCode()); // 分類コード_品目価格情報
    tblUriHead.setProductDate(tblUriHeadBillSrc.getProductDate()); // 生産日
    tblUriHead.setStsCode(CommonConst.STS_CD_ENTRY); // 状況コード
    tblUriHead.setAplYmd(""); // 業務処理日
    tblUriHead.setEntryTyp(""); // 入力形態
    tblUriHead.setRunJobid(""); // 実行ジョブID
    // 共通項目の設定
    tblUriHead = this.setCommonDataUriHead(tblUriHead, formUri0103d01
        .getHdnRegistUserCode(), "URI01-01D01", true);

    return tblUriHead;
  }

  /**
   * 売上ヘッダ情報（合計値）を生成する.
   * 
   * @param lstUriBody 売上明細情報リスト
   * @param tblUriHead 売上ヘッダ情報
   * @param tblMstCustomer 得意先マスタ
   * @param dblTaxRate 消費税率
   * 
   * @return TblUri01Head 売上ヘッダ情報
   */
  private TblUri01Head setCalculationDataUriHead(List<TblUri01Body> lstUriBody,
      TblUri01Head tblUriHead, TblUri01Head tblUriHeadBillSrc,
      String strTaxFrcKb) {
    int intTotalDeliAmt = 0; // 合計納品金額
    int intTotalDeliTax = 0; // 合計納品金額消費税
    int intTotalCustomerBillAmt = 0; // 合計先方仕切金額
    int intTotalCustomerBillTax = 0; // 合計先方仕切金額消費税
    int intTotalSalesAmt = 0; // 合計販売金額
    int intTotalBillAmt = 0; // 合計仕切金額

    CommonGetSystemCom sysCom = new CommonGetSystemCom(commonDao, null, null,
        readPropertiesFileService);

    for (int idx = 0; idx < lstUriBody.size(); idx++) {
      TblUri01Body tblUriBody = lstUriBody.get(idx);
      if (tblUriBody.getItemCode() != null && !tblUriBody.getItemCode()
          .equalsIgnoreCase("")) {
        intTotalDeliAmt = intTotalDeliAmt + tblUriBody.getDeliAmt(); // 合計納品金額
        intTotalCustomerBillAmt = intTotalCustomerBillAmt + tblUriBody
            .getSnpSkrAmt(); // 合計先方仕切金額
        intTotalSalesAmt = intTotalSalesAmt + tblUriBody.getSaleAmt(); // 合計販売金額
        intTotalBillAmt = intTotalBillAmt + tblUriBody.getSkrAmt(); // 合計仕切金額
      }
    }

    double dblTaxRate = tblUriHeadBillSrc.getTaxRate().doubleValue(); // 消費税率
    String strTaxIncKb = tblUriHeadBillSrc.getTaxIncKb(); // 内税顧客区分
    String strTaxIncFrcKb = tblUriHeadBillSrc.getTaxIncFrcKb(); // 内税消費税端数処理

    if (strTaxIncKb.equalsIgnoreCase(CommonConst.TAX_INC_KB_OUT)) {
      /* 外税顧客の場合、 */
      // 合計納品金額消費税
      double dblTotalDeliAmtTmp = NumberUtil.multiply((double) intTotalDeliAmt,
          NumberUtil.divide(dblTaxRate, 100));
      BigDecimal bdTotalDeliTax = sysCom.getNumberRound(dblTotalDeliAmtTmp, 1,
          strTaxFrcKb);
      intTotalDeliTax = bdTotalDeliTax.intValue();

      // 合計先方仕切金額消費税
      double dblTotalCustomerBillTaxTmp = NumberUtil.multiply(
          (double) intTotalCustomerBillAmt, NumberUtil.divide(dblTaxRate, 100));
      BigDecimal bdTotalCustomerBillTax = sysCom.getNumberRound(
          dblTotalCustomerBillTaxTmp, 1, strTaxFrcKb);
      intTotalCustomerBillTax = bdTotalCustomerBillTax.intValue();
    } else {
      /* 内税顧客の場合、 */
      int intRoundPoint = getRoundPointForTaxInc(strTaxIncFrcKb); // 丸め小数桁
      String strRoundClass = getRoundClassForTaxInc(strTaxIncFrcKb); // 丸め種別（1：四捨五入、2：切り捨て、3：切り上げ）

      // 合計納品金額消費税
      double dblTotalDeliAmtTmp = NumberUtil.divide(NumberUtil.multiply(
          (double) intTotalDeliAmt, dblTaxRate), NumberUtil.add(100,
              dblTaxRate));
      BigDecimal bdTotalDeliAmtTax = sysCom.getNumberRound(dblTotalDeliAmtTmp,
          intRoundPoint, strRoundClass);
      intTotalDeliTax = bdTotalDeliAmtTax.intValue();

      // 合計納品金額消費税
      double dblTotalCustomerBillTaxTmp = NumberUtil.divide(NumberUtil.multiply(
          (double) intTotalCustomerBillAmt, dblTaxRate), NumberUtil.add(100,
              dblTaxRate));
      BigDecimal bdTotalCustomerBillTax = sysCom.getNumberRound(
          dblTotalCustomerBillTaxTmp, intRoundPoint, strRoundClass);
      intTotalCustomerBillTax = bdTotalCustomerBillTax.intValue();
    }
    // 項目セット
    /* 消費税率 */
    BigDecimal bdTaxRate = new BigDecimal(dblTaxRate);
    bdTaxRate = bdTaxRate.setScale(1, BigDecimal.ROUND_DOWN);
    tblUriHead.setTaxRate(bdTaxRate);
    tblUriHead.setSumDeliAmt(intTotalDeliAmt); // 合計納品金額
    tblUriHead.setSumDeliTax(intTotalDeliTax); // 合計納品金額消費税
    tblUriHead.setSumSnpSkriAmt(intTotalCustomerBillAmt); // 合計先方仕切金額
    tblUriHead.setSumSnpSkriTax(intTotalCustomerBillTax); // 合計先方仕切金額消費税
    tblUriHead.setSumSakeAmt(intTotalSalesAmt); // 合計販売金額
    tblUriHead.setSumSkriAmt(intTotalBillAmt); // 合計仕切金額

    return tblUriHead;
  }

  /**
   * 売上ヘッダ情報（赤伝票）を生成する.
   * 
   * @param tblUriHeadOrg 売上ヘッダ情報（元伝票）
   * @param strUserCodeReg 登録ユーザID
   * 
   * @return TblUri01Head 売上ヘッダ情報
   */
  private TblUri01Head createUriHeadRed(TblUri01Head tblUriHeadOrg,
      String strUserCodeReg) {
    TblUri01Head tblUriHeadRed = new TblUri01Head();

    tblUriHeadRed.setDenNo(tblUriHeadOrg.getDenNo()); // 自社伝票No
    tblUriHeadRed.setDenIdx((short) (tblUriHeadOrg.getDenIdx() + 1)); // 自社伝票枝番
    tblUriHeadRed.setDatKb(tblUriHeadOrg.getDatKb()); // データ区分
    tblUriHeadRed.setDenKb(tblUriHeadOrg.getDenKb()); // 伝票区分
    tblUriHeadRed.setCorrectType(tblUriHeadOrg.getCorrectType()); // 修正データ種別
    tblUriHeadRed.setDenNoOrigin(tblUriHeadOrg.getDenNoOrigin()); // 返品／欠品元伝票No
    tblUriHeadRed.setOfficeCode(tblUriHeadOrg.getOfficeCode()); // 事業所コード
    tblUriHeadRed.setJigyoNm(tblUriHeadOrg.getJigyoNm()); // 事業所名
    tblUriHeadRed.setChainCode(tblUriHeadOrg.getChainCode()); // チェーンコード
    tblUriHeadRed.setChainIdx(tblUriHeadOrg.getChainIdx()); // チェーン枝番
    tblUriHeadRed.setChainNm(tblUriHeadOrg.getChainNm()); // チェーン名
    tblUriHeadRed.setCustCode(tblUriHeadOrg.getCustCode()); // 得意先コード
    tblUriHeadRed.setCustNm(tblUriHeadOrg.getCustNm()); // 得意先名称
    tblUriHeadRed.setCustNmKana(tblUriHeadOrg.getCustNmKana()); // 得意先名称カナ
    tblUriHeadRed.setCustNmR(tblUriHeadOrg.getCustNmR()); // 得意先略称
    tblUriHeadRed.setCustDenNo(tblUriHeadOrg.getCustDenNo()); // 得意先伝票No
    tblUriHeadRed.setShopCode(tblUriHeadOrg.getShopCode()); // 店舗コード
    tblUriHeadRed.setShopNm(tblUriHeadOrg.getShopNm()); // 店舗名称
    tblUriHeadRed.setShopNmKana(tblUriHeadOrg.getShopNmKana()); // 店舗名称カナ
    tblUriHeadRed.setShopNmR(tblUriHeadOrg.getShopNmR()); // 店舗略称
    tblUriHeadRed.setDenPrint(tblUriHeadOrg.getDenPrint()); // 伝票発行
    tblUriHeadRed.setDeliDate(tblUriHeadOrg.getDeliDate()); // 納品日
    tblUriHeadRed.setOrdrDate(tblUriHeadOrg.getOrdrDate()); // 受注日
    tblUriHeadRed.setAdupDate(tblUriHeadOrg.getAdupDate()); // 売上計上日
    tblUriHeadRed.setBin(tblUriHeadOrg.getBin()); // 便
    tblUriHeadRed.setDeliCode(tblUriHeadOrg.getDeliCode()); // 納品先コード
    tblUriHeadRed.setDeliNm(tblUriHeadOrg.getDeliNm()); // 納品先名
    tblUriHeadRed.setDeliNmKana(tblUriHeadOrg.getDeliNmKana()); // 納品先カナ
    tblUriHeadRed.setDeliKb(tblUriHeadOrg.getDeliKb()); // 納品区分
    tblUriHeadRed.setSaleKb(tblUriHeadOrg.getSaleKb()); // 販売区分
    tblUriHeadRed.setCourseCode(tblUriHeadOrg.getCourseCode()); // コースコード
    tblUriHeadRed.setCosNm(tblUriHeadOrg.getCosNm()); // コース名称
    tblUriHeadRed.setCosNmR(tblUriHeadOrg.getCosNmR()); // コース略称
    tblUriHeadRed.setSnpDenNo(tblUriHeadOrg.getSnpDenNo()); // 先方伝票No
    tblUriHeadRed.setShipsKb(tblUriHeadOrg.getShipsKb()); // 手入力伝票発行
    tblUriHeadRed.setShipsTypId(tblUriHeadOrg.getShipsTypId()); // 手入力出荷伝票帳票ID
    tblUriHeadRed.setShipsNm(tblUriHeadOrg.getShipsNm()); // 出荷伝票名称
    tblUriHeadRed.setShipsLine(tblUriHeadOrg.getShipsLine()); // 出荷伝票行数
    tblUriHeadRed.setCollectKb(null); // 修正区分
    tblUriHeadRed.setUserCodeReg(tblUriHeadOrg.getUserCodeReg()); // 入力担当者コード
    tblUriHeadRed.setUserNmReg(tblUriHeadOrg.getUserNmReg()); // 入力担当者氏名
    tblUriHeadRed.setUserNmKanaReg(tblUriHeadOrg.getUserNmKanaReg()); // 入力担当者氏名カナ
    /* 合計納品金額 */
    int intTotalDeliAmt = (int) NumberUtil.multiply(tblUriHeadOrg
        .getSumDeliAmt(), -1);
    tblUriHeadRed.setSumDeliAmt(intTotalDeliAmt);
    /* 合計納品金額消費税 */
    int intTotalDeliTax = (int) NumberUtil.multiply(tblUriHeadOrg
        .getSumDeliTax(), -1);
    tblUriHeadRed.setSumDeliTax(intTotalDeliTax);
    /* 合計先方仕切金額 */
    int intTotalCustomerBillAmt = (int) NumberUtil.multiply(tblUriHeadOrg
        .getSumSnpSkriAmt(), -1);
    tblUriHeadRed.setSumSnpSkriAmt(intTotalCustomerBillAmt);
    /* 合計先方仕切金額消費税 */
    int intTotalCustomerBillTax = (int) NumberUtil.multiply(tblUriHeadOrg
        .getSumSnpSkriTax(), -1);
    tblUriHeadRed.setSumSnpSkriTax(intTotalCustomerBillTax);
    /* 合計販売金額 */
    int intTotalSalesAmt = (int) NumberUtil.multiply(tblUriHeadOrg
        .getSumSakeAmt(), -1);
    tblUriHeadRed.setSumSakeAmt(intTotalSalesAmt);
    /* 合計仕切金額 */
    int intTotalBillAmt = (int) NumberUtil.multiply(tblUriHeadOrg
        .getSumSkriAmt(), -1);
    tblUriHeadRed.setSumSkriAmt(intTotalBillAmt);
    tblUriHeadRed.setTaxIncKb(tblUriHeadOrg.getTaxIncKb()); // 内税顧客区分
    tblUriHeadRed.setTaxIncFrcKb(tblUriHeadOrg.getTaxIncFrcKb()); // 内税消費税端数処理
    tblUriHeadRed.setShipsRudKb(tblUriHeadOrg.getShipsRudKb()); // 伝票業計算金額まるめ
    tblUriHeadRed.setAmtRndKb(tblUriHeadOrg.getAmtRndKb()); // 金額丸め区分
    tblUriHeadRed.setSkrRndKb(tblUriHeadOrg.getSkrRndKb()); // 仕切丸め区分
    tblUriHeadRed.setSkrRndKeta(tblUriHeadOrg.getSkrRndKeta()); // 仕切丸め少数桁
    tblUriHeadRed.setcSaleKb(tblUriHeadOrg.getSaleKb()); // チェーン販売区分
    tblUriHeadRed.setDeliDatFlg(tblUriHeadOrg.getDeliDatFlg()); // 納品データ連携フラグ
    tblUriHeadRed.setListPrintFlg(tblUriHeadOrg.getListPrintFlg()); // 請求チェックリスト印刷フラグ
    tblUriHeadRed.setBillPrintFlg(tblUriHeadOrg.getBillPrintFlg()); // 請求書印刷フラグ
    tblUriHeadRed.setBillFlg(tblUriHeadOrg.getBillFlg()); // 締め処理フラグ
    tblUriHeadRed.setBillDate(tblUriHeadOrg.getBillDate()); // 入金調整請求締日
    tblUriHeadRed.setBillDenNo(tblUriHeadOrg.getBillDenNo()); // 請求元伝票No
    tblUriHeadRed.setRecKb(UriConst.RECORD_KB_RED); // レコード区分
    tblUriHeadRed.setFwdDeliDate(tblUriHeadOrg.getFwdDeliDate()); // 先日付データフラグ
    tblUriHeadRed.setTaxRate(tblUriHeadOrg.getTaxRate()); // 消費税率
    tblUriHeadRed.setCustCls(tblUriHeadOrg.getCustCls()); // 得意先種別
    tblUriHeadRed.setBunruiCode(tblUriHeadOrg.getBunruiCode()); // 分類コード
    tblUriHeadRed.setHinKakakuBunruiCode(tblUriHeadOrg
        .getHinKakakuBunruiCode()); // 分類コード_品目価格情報
    tblUriHeadRed.setProductDate(tblUriHeadOrg.getProductDate()); // 生産日
    tblUriHeadRed.setStsCode(CommonConst.STS_CD_INVALID); // 状況コード
    tblUriHeadRed.setAplYmd(tblUriHeadOrg.getAplYmd()); // 業務処理日
    tblUriHeadRed.setEntryTyp(tblUriHeadOrg.getEntryTyp()); // 入力形態
    tblUriHeadRed.setRunJobid(tblUriHeadOrg.getRunJobid()); // 実行ジョブID

    // 共通項目の設定
    tblUriHeadRed = this.setCommonDataUriHead(tblUriHeadRed, strUserCodeReg,
        "URI01-01D01", true);

    return tblUriHeadRed;
  }

  /**
   * 売上明細情報（黒伝票）を生成する.
   * 
   * @param formUri0103d01 再請求売上登録form
   * @param screen 再請求売上登録screen
   * @param uriItem 売上品目情報
   * @param shtRowNo 行No
   * 
   * @return TblUri01Body 売上明細情報
   */
  private TblUri01Body createUriBody(FormUri0103d01 formUri0103d01,
      TblUri01Body tblUriBodyBillSrc, UriageItem uriItem, short shtRowNo) {
    TblUri01Body tblUriBody = new TblUri01Body();
    String strOperateMode = formUri0103d01.getHdnOperateMode();

    tblUriBody.setDenNo(new Long(formUri0103d01.getHdnSlipNo()).longValue()); // 自社伝票No
    /* 自社伝票枝番 */
    short slipIdx = formUri0103d01.getHdnSlipIdx();
    // 処理区分 = '修正'の場合、
    if (strOperateMode.equalsIgnoreCase(CommonConst.SHORI_KB_MNT)) {
      slipIdx = (short) (slipIdx + 2);
    }
    tblUriBody.setDenIdx(slipIdx);
    tblUriBody.setCustDenRow(shtRowNo); // 伝票行No
    tblUriBody.setItemCode(uriItem.getItemCode()); // 品目コード
    tblUriBody.setItemCodeCust(uriItem.getCustomerItemCode()); // 得意先商品コード
    tblUriBody.setItemNm(tblUriBodyBillSrc.getItemNm()); // 品目名(☆)
    tblUriBody.setItemNmKana(tblUriBodyBillSrc.getItemNmKana()); // 品目カナ
    tblUriBody.setItemNmR(uriItem.getItemNameR()); // 品目略称
    tblUriBody.setRyomoku(tblUriBodyBillSrc.getRyomoku()); // 量目
    tblUriBody.setIreme(uriItem.getIrisu()); // 入数
    tblUriBody.setCaseSu(uriItem.getJucCase()); // 受注数量（ケース）
    tblUriBody.setBaraSu(uriItem.getJucSeparate()); // 受注数量（バラ）
    tblUriBody.setDeliPrice(uriItem.getDeliPrice()); // 納品単価
    tblUriBody.setDeliAmt(uriItem.getDeliAmt()); // 納品金額
    tblUriBody.setSnpSkriPrice(tblUriBodyBillSrc.getSnpSkriPrice()); // 先方仕切価
    tblUriBody.setSalePrice(uriItem.getSalesPrice()); // 販売単価
    tblUriBody.setSkriPrice(tblUriBodyBillSrc.getSkriPrice()); // 仕切価
    /* 計算が必要な項目は別途セットする */
    tblUriBody.setDepartCode(tblUriBodyBillSrc.getDepartCode()); // 部門コード
    tblUriBody.setItemFrmCode(tblUriBodyBillSrc.getItemFrmCode()); // 製品形態コード
    tblUriBody.setTempCode(tblUriBodyBillSrc.getTempCode()); // 温度帯コード
    tblUriBody.setDeliDatFlg(UriConst.PROCESSING_COMPLETE); // 納品データ連携フラグ
    tblUriBody.setListPrintFlg(UriConst.PROCESSING_COMPLETE); // 請求チェックリスト印刷フラグ
    tblUriBody.setBillPrintFlg(UriConst.PROCESSING_NOTYET); // 請求書印刷フラグ
    tblUriBody.setBillFlg(UriConst.PROCESSING_NOTYET); // 締め処理フラグ
    tblUriBody.setCollectKb(""); // 修正区分
    tblUriBody.setRecKb(UriConst.RECORD_KB_BLACK); // レコード区分

    // 商品分類、未収金区分取得
    String accruedKb = UriConst.ACCRUED_KB_ACCRUED;
    String itemCategory = this.getItemClass(tblUriBodyBillSrc.getItemCtgr());
    if (itemCategory.equalsIgnoreCase(UriConst.ITEM_CLASS_ITEM)) {
      accruedKb = UriConst.ACCRUED_KB_NORMAL;
    }
    tblUriBody.setItemCtgr(itemCategory); // 商品分類
    tblUriBody.setAccDueKb(accruedKb); // 未収金区分
    tblUriBody.setCancelKb(UriConst.CANCEL_KB_NORMAL); // 取消区分
    tblUriBody.setStsCode(CommonConst.STS_CD_ENTRY); // 状況コード
    tblUriBody.setAplYmd(""); // 業務処理日
    tblUriBody.setEntryTyp(""); // 入力形態
    tblUriBody.setRunJobid(""); // 実行ジョブID
    tblUriBody.setRunSeq(0); // 実行SEQ
    // 共通項目の設定
    tblUriBody = this.setCommonDataUriBody(tblUriBody, formUri0103d01
        .getHdnRegistUserCode(), "URI01-01D01", true);

    return tblUriBody;
  }

  /**
   * 売上明細情報（算出が必要な項目）を生成する.
   * 
   * @param tblUriBody 売上明細情報
   * @param tblMstCustomer 得意先マスタ
   * @param uriItem 売上品目情報
   * @param dblTaxRate 消費税率
   * 
   * @return TblUri01Body 売上明細情報
   * @throws Exception １明細当たり金額算出例外
   */
  private TblUri01Body setCalculationDataUriBody(TblUri01Body tblUriBody,
      TblUri01Head tblUriHeadBillSrc) throws Exception {
    CommonGetSystemCom sysCom = new CommonGetSystemCom(commonDao, null, null,
        readPropertiesFileService);

    int intCustBillAmt = 0; // 先方仕切価金額
    int intSalesAmt = 0; // 販売金額
    int intBillAmt = 0; // 仕切価金額
    double dblDeliPriceH = 0; // 納品単価（本体）
    double dblDeliAmtH = 0; // 納品金額（本体）
    double dblDeliTax = 0; // 納品金額消費税額
    double dblSnpPriceH = 0; // 先方仕切価（本体）
    double dblSnpAmtH = 0; // 先方仕切価金額（本体）
    double dblSnpTax = 0; // 先方仕切価金額消費税額

    // 先方仕切価金額（先方仕切価 × 受注数量（バラ）
    intCustBillAmt = (int) NumberUtil.multiply(tblUriBody.getSnpSkriPrice()
        .doubleValue(), tblUriBody.getBaraSu());

    // 販売金額（販売単価 × 受注数量（バラ）
    intSalesAmt = (int) NumberUtil.multiply(tblUriBody.getSalePrice(),
        tblUriBody.getBaraSu());

    // 仕切価金額（仕切価 × 受注数量（バラ）
    intBillAmt = (int) NumberUtil.multiply(tblUriBody.getSkriPrice()
        .doubleValue(), tblUriBody.getBaraSu());

    try {
      double dblTaxRate = tblUriHeadBillSrc.getTaxRate().doubleValue(); // 消費税率
      String strTaxIncKb = tblUriHeadBillSrc.getTaxIncKb(); // 内税顧客区分
      String strTaxIncFrcKb = tblUriHeadBillSrc.getTaxIncFrcKb(); // 内税消費税端数処理
      String strShipsRudKb = tblUriHeadBillSrc.getShipsRudKb(); // 伝票行計算金額まるめ

      // 納品 単価（本体）、金額（本体）、消費税
      OneSpecPriceData ospdDeli = sysCom.getOneSpecPrice(tblUriBody
          .getDeliPrice().doubleValue(), tblUriBody.getBaraSu(), dblTaxRate,
          strTaxIncKb, strTaxIncFrcKb, strShipsRudKb);

      dblDeliPriceH = ospdDeli.getBodyUnitPrice(); // 納品単価（本体）
      dblDeliAmtH = ospdDeli.getBodyPrice(); // 納品金額（本体）
      dblDeliTax = ospdDeli.getTaxPrice(); // 納品金額消費税額

      // 先方仕切価 単価（本体）、金額（本体）、消費税
      OneSpecPriceData ospdSnp = sysCom.getOneSpecPrice(tblUriBody
          .getSnpSkriPrice().doubleValue(), tblUriBody.getBaraSu(), dblTaxRate,
          strTaxIncKb, strTaxIncFrcKb, strShipsRudKb);

      dblSnpPriceH = ospdSnp.getBodyUnitPrice(); // 先方仕切価（本体）
      dblSnpAmtH = ospdSnp.getBodyPrice(); // 先方仕切価金額（本体）
      dblSnpTax = ospdSnp.getTaxPrice(); // 先方仕切価金額消費税額
    } catch (Exception ex) {
      throw ex;
    }

    BigDecimal bdDeliPriceH = new BigDecimal(dblDeliPriceH);
    bdDeliPriceH = bdDeliPriceH.setScale(2, BigDecimal.ROUND_DOWN);
    tblUriBody.setDeliPriceH(bdDeliPriceH); // 納品単価（本体）
    tblUriBody.setDeliAmtH((int) dblDeliAmtH); // 納品金額（本体）
    tblUriBody.setDeliTax((int) dblDeliTax); // 納品金額消費税額
    BigDecimal bdSnpPriceH = new BigDecimal(dblSnpPriceH);
    bdSnpPriceH = bdSnpPriceH.setScale(2, BigDecimal.ROUND_DOWN);
    tblUriBody.setSnpSkriPriceH(bdSnpPriceH); // 先方仕切価（本体）
    tblUriBody.setSnpSkrAmtH((int) dblSnpAmtH); // 先方仕切価金額（本体）
    tblUriBody.setSnpSkrTax((int) dblSnpTax); // 先方仕切価金額消費税額

    tblUriBody.setSnpSkrAmt(intCustBillAmt); // 先方仕切価金額
    tblUriBody.setSaleAmt(intSalesAmt); // 販売金額
    tblUriBody.setSkrAmt(intBillAmt); // 仕切価金額

    return tblUriBody;
  }

  /**
   * 売上明細情報（赤伝票）を生成する.
   * 
   * @param lstUriBodyOrg 売上明細情報リスト（元伝票）
   * @param strUserCodeReg 登録ユーザID
   * 
   * @return List&lt;TblUri01Body&gt; 売上明細情報リスト
   */
  private List<TblUri01Body> createUriBodyRed(List<TblUri01Body> lstUriBodyOrg,
      String strUserCodeReg) {
    List<TblUri01Body> lstUriBodyRed = new ArrayList<TblUri01Body>();

    for (TblUri01Body tblUriBodyOrg : lstUriBodyOrg) {
      TblUri01Body tblUriBodyRed = new TblUri01Body();

      tblUriBodyRed.setDenNo(tblUriBodyOrg.getDenNo()); // 自社伝票No
      tblUriBodyRed.setDenIdx((short) (tblUriBodyOrg.getDenIdx() + 1)); // 自社伝票枝番
      tblUriBodyRed.setCustDenRow(tblUriBodyOrg.getCustDenRow()); // 伝票行No
      tblUriBodyRed.setItemCode(tblUriBodyOrg.getItemCode()); // 品目コード
      tblUriBodyRed.setItemCodeCust(tblUriBodyOrg.getItemCodeCust()); // 得意先商品コード
      tblUriBodyRed.setItemNm(tblUriBodyOrg.getItemNm()); // 品目名
      tblUriBodyRed.setItemNmKana(tblUriBodyOrg.getItemNmKana()); // 品目カナ
      tblUriBodyRed.setItemNmR(tblUriBodyOrg.getItemNmR()); // 品目略称
      tblUriBodyRed.setRyomoku(tblUriBodyOrg.getRyomoku()); // 量目
      tblUriBodyRed.setIreme(tblUriBodyOrg.getIreme()); // 入数
      /* 受注数量（ケース） */
      int intJucCase = (int) NumberUtil.multiply(tblUriBodyOrg.getCaseSu(), -1);
      tblUriBodyRed.setCaseSu(intJucCase);
      /* 受注数量（バラ） */
      int intJucSeparate = (int) NumberUtil.multiply(tblUriBodyOrg.getBaraSu(),
          -1);
      tblUriBodyRed.setBaraSu(intJucSeparate);
      tblUriBodyRed.setDeliPrice(tblUriBodyOrg.getDeliPrice()); // 納品単価
      tblUriBodyRed.setDeliPriceH(tblUriBodyOrg.getDeliPriceH()); // 納品単価（本体価格）
      /* 納品金額 */
      int intDeliAmt = (int) NumberUtil.multiply(tblUriBodyOrg.getDeliAmt(),
          -1);
      tblUriBodyRed.setDeliAmt(intDeliAmt);
      /* 納品金額（本体価格） */
      int intDeliAmtH = (int) NumberUtil.multiply(tblUriBodyOrg.getDeliAmtH(),
          -1);
      tblUriBodyRed.setDeliAmtH(intDeliAmtH);
      /* 納品金額消費税 */
      int intDeliTax = (int) NumberUtil.multiply(tblUriBodyOrg.getDeliTax(),
          -1);
      tblUriBodyRed.setDeliTax(intDeliTax);
      tblUriBodyRed.setSnpSkriPrice(tblUriBodyOrg.getSnpSkriPrice()); // 先方仕切価
      tblUriBodyRed.setSnpSkriPriceH(tblUriBodyOrg.getSnpSkriPriceH()); // 先方仕切価（本体価格）
      /* 先方仕切価金額 */
      int intCustBillAmt = (int) NumberUtil.multiply(tblUriBodyOrg
          .getSnpSkrAmt(), -1);
      tblUriBodyRed.setSnpSkrAmt(intCustBillAmt);
      /* 先方仕切価金額（本体価格） */
      int intSnpSkrAmtH = (int) NumberUtil.multiply(tblUriBodyOrg
          .getSnpSkrAmtH(), -1);
      tblUriBodyRed.setSnpSkrAmtH(intSnpSkrAmtH);
      /* 先方仕切価金額消費税 */
      int intSnpSkrTax = (int) NumberUtil.multiply(tblUriBodyOrg.getSnpSkrTax(),
          -1);
      tblUriBodyRed.setSnpSkrTax(intSnpSkrTax);
      tblUriBodyRed.setSalePrice(tblUriBodyOrg.getSalePrice()); // 販売単価
      /* 販売金額 */
      int intSaleAmt = (int) NumberUtil.multiply(tblUriBodyOrg.getSaleAmt(),
          -1);
      tblUriBodyRed.setSaleAmt(intSaleAmt);
      tblUriBodyRed.setSkriPrice(tblUriBodyOrg.getSkriPrice()); // 仕切価
      /* 仕切価金額 */
      int intBillAmt = (int) NumberUtil.multiply(tblUriBodyOrg.getSkrAmt(), -1);
      tblUriBodyRed.setSkrAmt(intBillAmt);
      tblUriBodyRed.setDepartCode(tblUriBodyOrg.getDepartCode()); // 部門コード
      tblUriBodyRed.setItemFrmCode(tblUriBodyOrg.getItemFrmCode()); // 製品形態コード
      tblUriBodyRed.setTempCode(tblUriBodyOrg.getTempCode()); // 温度帯コード
      tblUriBodyRed.setDeliDatFlg(tblUriBodyOrg.getDeliDatFlg()); // 納品データ連携フラグ
      tblUriBodyRed.setListPrintFlg(tblUriBodyOrg.getListPrintFlg()); // 請求チェックリスト印刷フラグ
      tblUriBodyRed.setBillPrintFlg(tblUriBodyOrg.getBillPrintFlg()); // 請求書印刷フラグ
      tblUriBodyRed.setBillFlg(tblUriBodyOrg.getBillFlg()); // 締め処理フラグ
      tblUriBodyRed.setCollectKb(tblUriBodyOrg.getCollectKb()); // 修正区分
      tblUriBodyRed.setRecKb(UriConst.RECORD_KB_RED); // レコード区分
      tblUriBodyRed.setItemCtgr(tblUriBodyOrg.getItemCtgr().trim()); // 商品分類
      tblUriBodyRed.setAccDueKb(tblUriBodyOrg.getAccDueKb().trim()); // 未収金区分
      tblUriBodyRed.setCancelKb(tblUriBodyOrg.getCancelKb()); // 取消区分
      tblUriBodyRed.setStsCode(CommonConst.STS_CD_INVALID); // 状況コード
      tblUriBodyRed.setAplYmd(tblUriBodyOrg.getAplYmd()); // 業務処理日
      tblUriBodyRed.setEntryTyp(tblUriBodyOrg.getEntryTyp()); // 入力形態
      tblUriBodyRed.setRunJobid(tblUriBodyOrg.getRunJobid()); // 実行ジョブID
      tblUriBodyRed.setRunSeq(tblUriBodyOrg.getRunSeq()); // 実行SEQ
      // 共通項目の設定
      tblUriBodyRed = this.setCommonDataUriBody(tblUriBodyRed, strUserCodeReg,
          "URI01-01D01", true);

      lstUriBodyRed.add(tblUriBodyRed);
    }

    return lstUriBodyRed;
  }

  /**
   * 得意先マスタ.内税消費税端数処理から丸め少数桁を求める.
   * 
   * @param strTaxIncFrcKb 内税消費税端数処理
   * 
   * @return int 丸め少数桁
   */
  private int getRoundPointForTaxInc(String strTaxIncFrcKb) {
    int intTaxKb = new Integer(strTaxIncFrcKb).intValue();

    switch (intTaxKb) {
    case UriConst.TIFK_1ST_ROUND_HALF_UP:
    case UriConst.TIFK_1ST_ROUND_UP:
    case UriConst.TIFK_1ST_ROUND_DOWN:
      return 1;
    case UriConst.TIFK_2ND_ROUND_HALF_UP:
    case UriConst.TIFK_2ND_ROUND_UP:
    case UriConst.TIFK_2ND_ROUND_DOWN:
      return 2;
    case UriConst.TIFK_3RD_ROUND_HALF_UP:
    case UriConst.TIFK_3RD_ROUND_UP:
    case UriConst.TIFK_3RD_ROUND_DOWN:
      return 3;
    default:
      break;
    }
    return 1;
  }

  /**
   * 得意先マスタ.内税消費税端数処理から丸め種別を求める。
   * 
   * @param strTaxIncFrcKb
   *          内税消費税端数処理
   * 
   * @return String 丸め種別（1：四捨五入、2：切り捨て、3：切り上げ）
   */
  private String getRoundClassForTaxInc(String strTaxIncFrcKb) {
    int intTaxKb = new Integer(strTaxIncFrcKb).intValue();

    switch (intTaxKb) {
    case UriConst.TIFK_1ST_ROUND_HALF_UP:
    case UriConst.TIFK_2ND_ROUND_HALF_UP:
    case UriConst.TIFK_3RD_ROUND_HALF_UP:
      return CommonConst.ROUND_HALF_UP;
    case UriConst.TIFK_1ST_ROUND_DOWN:
    case UriConst.TIFK_2ND_ROUND_DOWN:
    case UriConst.TIFK_3RD_ROUND_DOWN:
      return CommonConst.ROUND_DOWN;
    case UriConst.TIFK_1ST_ROUND_UP:
    case UriConst.TIFK_2ND_ROUND_UP:
    case UriConst.TIFK_3RD_ROUND_UP:
      return CommonConst.ROUND_UP;
    default:
      break;
    }
    return CommonConst.ROUND_HALF_UP;
  }
}
