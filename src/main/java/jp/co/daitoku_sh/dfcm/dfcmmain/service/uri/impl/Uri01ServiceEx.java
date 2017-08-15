/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.uri.impl
 * ファイル名:Uri01ServiceEx.java
 * 
 * <p>作成者:アクトブレーン
 * 作成日:2015/10/15
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/15 1.00 AB)前田 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.service.uri.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.component.ChainData;
import jp.co.daitoku_sh.dfcm.common.component.CourseData;
import jp.co.daitoku_sh.dfcm.common.component.CustomerData;
import jp.co.daitoku_sh.dfcm.common.component.ItemBodyData;
import jp.co.daitoku_sh.dfcm.common.component.ItemClassData;
import jp.co.daitoku_sh.dfcm.common.component.JigyoData;
import jp.co.daitoku_sh.dfcm.common.component.ListFormData;
import jp.co.daitoku_sh.dfcm.common.component.MstGeneralData;
import jp.co.daitoku_sh.dfcm.common.component.NohinData;
import jp.co.daitoku_sh.dfcm.common.component.ShopData;
import jp.co.daitoku_sh.dfcm.common.component.TaxRateData;
import jp.co.daitoku_sh.dfcm.common.component.UserData;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.db.mapper.TblGet01JigConfirmMapper;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCustomer;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSChain;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSChainKey;
import jp.co.daitoku_sh.dfcm.common.db.model.MstShop;
import jp.co.daitoku_sh.dfcm.common.db.model.TblGet01JigConfirm;
import jp.co.daitoku_sh.dfcm.common.db.model.TblSei01MshBody;
import jp.co.daitoku_sh.dfcm.common.db.model.TblSei01UrkBody;
import jp.co.daitoku_sh.dfcm.common.db.model.TblSei01UrkMshHead;
import jp.co.daitoku_sh.dfcm.common.db.model.TblUri01Body;
import jp.co.daitoku_sh.dfcm.common.db.model.TblUri01BodyExample;
import jp.co.daitoku_sh.dfcm.common.db.model.TblUri01Head;
import jp.co.daitoku_sh.dfcm.common.db.model.TblUri01HeadExample;
import jp.co.daitoku_sh.dfcm.common.db.model.TblUri01Journal;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetData;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.InputCheckJucUri;
import jp.co.daitoku_sh.dfcm.common.util.NumberUtil;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.UriConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ObjCombobox;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.uri.SubjectData;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.uri.UriageData;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.uri.UrkMshHeads;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.uri.impl.Uri01Dao;

/**
 * 売上サブシステム共通サービスクラス（Transaction不使用）
 * Transactionを使用する関数についてはUri01Serviceに定義されている（売上計上Baｔｃｈ対応）
 * 
 * @author アクトブレーン 前田
 * @version 1.0.0
 * @since 1.0.0
 */
public class Uri01ServiceEx extends AbsService {

  @Autowired
  @Qualifier("uri01Dao")
  protected Uri01Dao uri01Dao;
  
  @Autowired
  @Qualifier("commonDao")
  protected CommonDao commonDao;

  @Autowired
  @Qualifier("readPropertiesFileService")
  protected ReadPropertiesFileService readPropertiesFileService;

  /* ログ出力用 */
  protected Dfcmlogger logger = new Dfcmlogger();

  /**
   * Uri01Daoを取得（Batch用）
   * 
   * @return uri01Daoオブジェクト
   */
  public Uri01Dao getUri01Dao(){
    return this.uri01Dao;
  }
  
  /**
   * Uri01Daoをセット（Batch用）
   * 
   * @param uri01Dao uri01Daoオブジェクト
   */
  public void setUri01Dao(Uri01Dao uri01Dao) {
    this.uri01Dao = uri01Dao;
  }
  
  /**
   * CommonDaoを取得（Batch用）
   * 
   * @return commonDaoオブジェクト
   */
  public CommonDao getCommonDao() {
    return this.commonDao;
  }
  
  /**
   * CommonDaoをセット（Batch用）
   * 
   * @param commonDao commonDaoオブジェクト
   */
  public void setCommonDao(CommonDao commonDao) {
    this.commonDao = commonDao;
  }
  
  /**
   * readPropertiesFileServiceを取得（Batch用）
   * 
   * @return ReadPropertiesFileServiceオブジェクト
   */
  public ReadPropertiesFileService getReadPropertiesFileService() {
    return this.readPropertiesFileService;
  }
  
  /**
   * readPropertiesFileServiceをセット（Batch用）
   * 
   * @param readPropertiesFileService ReadPropertiesFileServiceオブジェクト
   */
  public void setReadPropertiesFileService(ReadPropertiesFileService readPropertiesFileService) {
    this.readPropertiesFileService = readPropertiesFileService;
  }

  /**
   * 売上ヘッダ・明細テーブルから売上情報を取得する.
   * 
   * @param strJigyoCode 事業所コード（システム管理者の場合、null(検索条件対象外)）
   * @param lngSlipNo 自社伝票No
   * @param strOperateMode 処理区分（0：新規登録、1：訂正、2：取消、3：照会）
   * 
   * @return UriageData 売上情報（ヘッダ＋明細リスト）
   */
  public UriageData getUriageData(String strJigyoCode, long lngSlipNo,
      String strOperateMode) {
    UriageData uriData = new UriageData();
    try {
      // 売上ヘッダ検索条件セット
      TblUri01HeadExample uriHeadExample = new TblUri01HeadExample();
      TblUri01HeadExample.Criteria uriHeadCriteria = uriHeadExample
          .createCriteria();
      uriHeadCriteria.andDenNoEqualTo(lngSlipNo);
      if (!strOperateMode.equalsIgnoreCase(CommonConst.SHORI_KB_REF)) {
        uriHeadCriteria.andStsCodeEqualTo(CommonConst.STS_CD_ENTRY);
      }
      if (strJigyoCode != null) {
        uriHeadCriteria.andOfficeCodeEqualTo(strJigyoCode);
      }
      uriHeadExample.setOrderByClause("Den_Idx DESC");
      // 売上ヘッダ検索
      List<TblUri01Head> lstUriHead = uri01Dao.getUri01HeadMapper()
          .selectByExample(uriHeadExample);
      if (lstUriHead == null || lstUriHead.isEmpty()) {
        // if (!strOperateMode.equalsIgnoreCase(CommonConst.SHORI_KB_REF)) {
        if (lstUriHead.size() > 1) {
          uriData.setMsgCd("COM009-E");
          return uriData;
        }
        // }
        uriData.setMsgCd("COM009-E");
        return uriData;
      }

      TblUri01Head tblUriHead = lstUriHead.get(0);
      uriData.setUriHead(tblUriHead);

      // 更新担当者名取得
      String strUpdUserName = "";
      UserData userData = this.getUser(tblUriHead.getOfficeCode(), tblUriHead
          .getUpdUserid());
      if (userData.getMsgCd() == null) {
        strUpdUserName = userData.getUsr().getUserNm();
      }
      uriData.setUpdUserName(strUpdUserName);

      // 売上明細検索条件セット
      TblUri01BodyExample uri01BodyExample = new TblUri01BodyExample();
      TblUri01BodyExample.Criteria uriBodyCriteria = uri01BodyExample
          .createCriteria();
      uriBodyCriteria.andDenNoEqualTo(lngSlipNo);
      uriBodyCriteria.andDenIdxEqualTo(tblUriHead.getDenIdx());
      if (!strOperateMode.equalsIgnoreCase(CommonConst.SHORI_KB_REF)) {
        uriBodyCriteria.andStsCodeEqualTo(CommonConst.STS_CD_ENTRY);
      }
      uri01BodyExample.setOrderByClause("Cust_Den_Row");
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
   * 得意先伝票Noを元に、売上ヘッダ・明細テーブルから売上情報を取得する.
   * 
   * @param strJigyoCode 事業所コード
   * @param strCustomerCode 得意先コード
   * @param strShopCode 店舗コード
   * @param strDeliYmd 納品年月日
   * @param strCustomerSlipNo 得意先伝票No
   * 
   * @return UriageData 売上情報（ヘッダ＋明細リスト）
   */
  public UriageData getUriageFromCustomer(String strJigyoCode,
      String strCustomerCode, String strShopCode, String strDeliYmd,
      String strBinKb, String strCustomerSlipNo) {
    UriageData uriData = new UriageData();
    // 売上ヘッダ取得
    TblUri01HeadExample uriHeadExample = new TblUri01HeadExample();
    TblUri01HeadExample.Criteria uriHeadCriteria = uriHeadExample
        .createCriteria();
    uriHeadCriteria.andOfficeCodeEqualTo(strJigyoCode); // 事業所コード
    uriHeadCriteria.andCustCodeEqualTo(strCustomerCode); // 得意先コード
    uriHeadCriteria.andShopCodeEqualTo(strShopCode); // 店舗コード
    uriHeadCriteria.andDeliDateEqualTo(strDeliYmd); // 納品日
    uriHeadCriteria.andBinEqualTo(strBinKb); // 便
    uriHeadCriteria.andCustDenNoEqualTo(strCustomerSlipNo); // 得意先伝票No
    uriHeadCriteria.andRecKbEqualTo(UriConst.RECORD_KB_BLACK); // レコード区分
    uriHeadCriteria.andStsCodeEqualTo(CommonConst.STS_CD_ENTRY); // 状況コード
    // 売上ヘッダ検索
    List<TblUri01Head> lstUriHead = uri01Dao.getUri01HeadMapper()
        .selectByExample(uriHeadExample);
    if (lstUriHead == null || lstUriHead.isEmpty() || lstUriHead.size() > 1) {
      uriData.setMsgCd("COM009-E");
      return uriData;
    }
    TblUri01Head tblUriHead = lstUriHead.get(0);
    uriData.setUriHead(tblUriHead);

    // 自社伝票No.、枝番取得
    Long lngDenNo = tblUriHead.getDenNo();
    short shtDenIdx = tblUriHead.getDenIdx();

    // 売上明細取得
    TblUri01BodyExample uriBodyExample = new TblUri01BodyExample();
    TblUri01BodyExample.Criteria uriBodyCriteria = uriBodyExample
        .createCriteria();
    uriBodyCriteria.andDenNoEqualTo(lngDenNo);
    uriBodyCriteria.andDenIdxEqualTo(shtDenIdx);
    uriBodyCriteria.andStsCodeEqualTo(CommonConst.STS_CD_ENTRY);
    // 売上明細検索
    List<TblUri01Body> lstUriBody = uri01Dao.getUri01BodyMapper()
        .selectByExample(uriBodyExample);
    if (lstUriBody == null || lstUriBody.isEmpty()) {
      uriData.setMsgCd("COM009-E");
      return uriData;
    }
    uriData.setLstUriBody(lstUriBody);

    return uriData;
  }

  /**
   * 共通関数を使用し、事業所マスタから事業所情報を取得する.
   * 
   * @param strJigyoCode 事業所コード
   * 
   * @return JigyoData 事業所情報
   */
  public JigyoData getJigyoName(String strJigyoCode) {
    JigyoData jigyoData = null;
    CommonGetData comGetData = new CommonGetData(commonDao,
        readPropertiesFileService);

    try {
      // 事業所情報取得
      jigyoData = comGetData.getJigyoData(strJigyoCode);
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      return jigyoData;
    }
    return jigyoData;
  }

  /**
   * 共通関数を使用し、得意先マスタから得意先情報を取得する.
   * 
   * @param strJigyoCode 事業所コード
   * @param strCustomerCode 得意先コード
   * 
   * @return CustomerData 得意先情報
   */
  public CustomerData getCustomer(String strJigyoCode, String strCustomerCode) {
    CustomerData customerData = null;
    CommonGetData comGetData = new CommonGetData(commonDao,
        readPropertiesFileService);

    try {
      // 得意先情報取得
      customerData = comGetData.getCustomerData(strCustomerCode, strJigyoCode,
          UriConst.CHK_TYPE_CUSTOMER);
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      return customerData;
    }
    return customerData;
  }

  /**
   * 共通関数を使用し、店舗マスタから店舗情報を取得する.
   * 
   * @param strCustomerCode 得意先コード
   * @param strShopCode 店舗コード
   * 
   * @return ShopData 店舗情報
   */
  public ShopData getShop(String strCustomerCode, String strShopCode) {
    ShopData shopData = null;
    CommonGetData comGetData = new CommonGetData(commonDao,
        readPropertiesFileService);

    try {
      // 店舗情報取得
      shopData = comGetData.getShopData(strShopCode, strCustomerCode);
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      return shopData;
    }
    return shopData;
  }

  /**
   * 共通関数を使用し、担当者マスタから担当者情報を取得する.
   * 
   * @param strJigyoCode 事業所コード
   * @param strUserCode 担当者コード
   * 
   * @return UserData 担当者情報
   */
  public UserData getUser(String strJigyoCode, String strUserCode) {
    UserData userData = null;
    CommonGetData comGetData = new CommonGetData(commonDao,
        readPropertiesFileService);

    try {
      // 担当者情報取得
      userData = comGetData.getUserData(strUserCode, strJigyoCode);
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      return userData;
    }
    return userData;
  }

  /**
   * 共通関数を使用し、帳票定義マスタから出荷伝票情報を取得する.
   * 
   * @param strShpTypeId 手入力出荷伝票帳票ID
   * 
   * @return ListFormData 出荷伝票情報
   */
  public ListFormData getListForm(String strShpTypeId) {
    ListFormData listFormData = null;
    CommonGetData comGetData = new CommonGetData(commonDao,
        readPropertiesFileService);

    try {
      // 帳票情報取得
      listFormData = comGetData.getListFormData(strShpTypeId);
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      return listFormData;
    }
    return listFormData;
  }

  /**
   * 共通関数を使用し、コースマスタからコース情報を取得する.
   * 
   * @param strJigyoCode 事業所コード
   * @param strCustomerCode 得意先コード
   * @param strShopCode 店舗コード
   * @param strDeliKb 納品区分
   * @param strBinKb 便区分
   * @param intShopKb 得意先マスタ.店舗区分（1：店有り、2：店無し）
   * 
   * @return CourseData コース情報
   */
  public CourseData getCourseData(String strJigyoCode, String strCustomerCode,
      String strShopCode, String strDeliKb, String strBinKb, int intShopKb) {
    CourseData courseData = null;
    CommonGetData comGetData = new CommonGetData(commonDao,
        readPropertiesFileService);

    try {
      // コース情報取得
      courseData = comGetData.getCourseData(strJigyoCode, strCustomerCode,
          strShopCode, strDeliKb, strBinKb, intShopKb);
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      return courseData;
    }

    return courseData;
  }

  /**
   * 共通関数を使用し、製品マスタなどから品目情報を取得する.
   * 
   * @param strItemCode 品目コード
   * @param strCustCode 得意先コード
   * @param strShopCode 店舗コード
   * @param strSalesKb 販売区分
   * @param intDeliYmd 納品区分
   * @param shtBinKb 便区分
   * @param shtChainCd チェーンコード
   * @param shtChainIdx チェーン枝番
   * @param intItemKb 品目コード区分
   * @param strJigyoCode 事業所コード
   * 
   * @return ItemBodyData 品目明細情報格納クラス
   */
  public ItemBodyData getItemBodyData(String strItemCode, String strCustCode,
      String strShopCode, String strSalesKb, int intDeliYmd, short shtBinKb,
      short shtChainCd, short shtChainIdx, int intItemKb, String strJigyoCode) {
    ItemBodyData itemBodyData = null;
    CommonGetData comGetData = new CommonGetData(commonDao,
        readPropertiesFileService);

    try {
      // 品目情報取得
      itemBodyData = comGetData.getItemBodyData(strItemCode, strCustCode,
          strShopCode, strSalesKb, intDeliYmd, shtBinKb, shtChainCd,
          shtChainIdx, intItemKb, strJigyoCode);
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      return itemBodyData;
    }

    return itemBodyData;
  }

  /**
   * 共通関数を使用し、納品先マスタから納品先情報を取得する.
   *
   * @param shtChainCode チェーンコード
   * @param shtChainIdx チェーン枝番
   * @param shtJigyoCode 事業所コード
   * @param intDeliYmd 納品日
   * @param shtBinKb 便区分
   * @param shtDeliCode 納品先コード
   * 
   * @return NohinData 納品先情報格納クラス
   */
  public NohinData getDeliData(short shtChainCode, short shtChainIdx,
      short shtJigyoCode, int intDeliYmd, short shtBinKb, short shtDeliCode) {
    NohinData deliData = null;
    CommonGetData comGetData = new CommonGetData(commonDao,
        readPropertiesFileService);

    try {
      // 納品先情報取得
      deliData = comGetData.getNohinData(shtChainCode, shtChainIdx,
          shtJigyoCode, intDeliYmd, shtBinKb, shtDeliCode);
      if (deliData == null) {
        deliData = new NohinData();
        deliData.setMsgCd("COM009-E");
        deliData.setNhn(null);
      }
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      return deliData;
    }

    return deliData;
  }

  /**
   * チェーンマスタからチェーン情報を取得する.
   * 
   * @param shtChainCode チェーンコード
   * @param shtChainIdx チェーン枝番
   * 
   * @return ChainData チェーン情報
   */
  public ChainData getChainData(Short shtChainCode, Short shtChainIdx) {
    ChainData chainData = new ChainData();

    try {
      MstSChainKey keyMstSchain = new MstSChainKey();
      keyMstSchain.setChncd(shtChainCode); // チェーンコード
      keyMstSchain.setChneda(shtChainIdx); // チェーン枝番
      // チェーンマスタ取得
      MstSChain mstSChain = uri01Dao.getMstSChainMapper()
          .selectByPrimaryKey(keyMstSchain);
      if (mstSChain == null) {
        // 存在しない場合
        chainData.setMsgCd("COM009-E");
      }
      chainData.setMstSchain(mstSChain);
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      return chainData;
    }

    return chainData;
  }

  /**
   * 売掛・未収ヘッダ情報を取得する.
   * 
   * @param tblUriHead 売上ヘッダ情報
   * @param strShopCode 店舗コード（NONE対応のため、別出し）
   * 
   * @return TblSei01UrkMshHead 売掛・未収ヘッダ情報
   */
  public TblSei01UrkMshHead getUrkMshHead(TblUri01Head tblUriHead,
      String strShopCode) {
    List<TblSei01UrkMshHead> lstSeiUrkMshHead = new ArrayList<TblSei01UrkMshHead>();
    try {
      // 条件をセットする
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("jigyoCode", tblUriHead.getOfficeCode());
      params.put("chainCode", tblUriHead.getChainCode());
      params.put("chainIdx", tblUriHead.getChainIdx());
      params.put("custCode", tblUriHead.getCustCode());
      params.put("shopCode", strShopCode);
      params.put("accountMonth", tblUriHead.getAdupDate().substring(0, 6));
      params.put("closeDate", tblUriHead.getAdupDate());

      lstSeiUrkMshHead = uri01Dao.getUri01Mapper()
          .getUrkMshHead(params);
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }

    if (lstSeiUrkMshHead == null || lstSeiUrkMshHead.isEmpty()) {
      return null;
    }
    return lstSeiUrkMshHead.get(0);
  }

  /**
   * 共通関数を使用し、便区分をチェックする.
   * 
   * @param strBinKb便区分
   * 
   * @return String チェック結果（正常：null、異常：null以外）
   */
  public String checkBinKb(String strBinKb) {
    String result = null;
    InputCheckJucUri inputCheck = new InputCheckJucUri(commonDao,
        readPropertiesFileService);

    try {
      result = inputCheck.chkBinKb(strBinKb);
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      return result;
    }

    return result;
  }

  /**
   * 共通関数を使用し、汎用マスタから情報を取得。 取得した情報をドロップダウンリストにセットする（画面初期表示用）.
   * 
   * @param model Modelオブジェクト
   * @param strGlKb 汎用マスタ取得用キー
   * @param strGlCode 汎用マスタ取得用コード
   * @param strJspName セットするコンボボックスのID＠JSP
   * 
   * @return List&lt;MstGeneralData&gt; 取得した汎用マスタ情報リスト
   */
  public List<MstGeneralData> setDdlFromMstGeneral(Model model, String strGlKb,
      String strGlCode, String strJspName) {
    List<MstGeneralData> lstSlip = null;
    CommonGetSystemCom sysCom = new CommonGetSystemCom(commonDao, null,
        null, readPropertiesFileService);

    try {
      // 汎用マスタ情報取得
      lstSlip = sysCom.getMstGeneralConf(strGlKb, strGlCode);
      if (lstSlip == null || lstSlip.isEmpty()) {
        return null;
      }
      // コンボボックス用Listオブジェクト
      List<ObjCombobox> lstOcb = new ArrayList<ObjCombobox>();
      ObjCombobox objCmb = new ObjCombobox();
      // 先頭に空白を詰める
      objCmb.setCode("");
      objCmb.setName("");
      lstOcb.add(objCmb);

      // 取得データ編集（コード：名称）
      for (MstGeneralData mstGeneral : lstSlip) {
        objCmb = new ObjCombobox();
        objCmb.setCode(mstGeneral.getGlCode());
        objCmb.setName(mstGeneral.getGlCode() + ":" + mstGeneral.getTarget1());
        lstOcb.add(objCmb);
      }
      model.addAttribute(strJspName, lstOcb);

      return lstSlip;
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      return null;
    }
  }

  /**
   * 事業所月次確定情報から確定対象年月に１ヶ月加算した年月を取得する.
   * 
   * @param strJigyoCode 事業所コード
   * 
   * @return String 年月（yyyyMM）
   */
  public String getDetermMonth(String strJigyoCode) {
    String determMonth = null;
    try {
      /* 事業所月次確定情報から確定対象年月 */
      TblGet01JigConfirmMapper get01JigConfirmMapper = uri01Dao
          .getGet01JigConfirmMapper();
      TblGet01JigConfirm get01JigConfirm = get01JigConfirmMapper
          .selectByPrimaryKey(strJigyoCode);
      if (get01JigConfirm == null) {
        return null;
      }
      /* 確定対象年月に１ヶ月加算 */
      String strDetermYmd = get01JigConfirm.getDetermMon().concat("01"); // 確定対象年月+'01'
      Date dtDetermBefore = DateUtil.toDate(strDetermYmd,
          CommonConst.DATE_FORMAT_YMD); // 〃 をDate型
      Date dtDetermAfter = DateUtil.getSpecMonth(dtDetermBefore, 1); // 〃 に１ヶ月加算
      String strDetermTemp = DateUtil.setFormat(dtDetermAfter,
          CommonConst.DATE_FORMAT_YMD); // 〃 をString型
      determMonth = strDetermTemp.substring(0, 6); // 〃 の１～６文字目
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      return determMonth;
    }
    return determMonth;
  }

  /**
   * 共通関数を使用し、汎用マスタからデータを取得する.
   * 
   * @param strGlKb 区分
   * @param strGlCode コード
   * 
   * @return List&lt;MstGeneralData&gt; 取得した汎用マスタ情報リスト
   */
  public List<MstGeneralData> getMstGeneralConf(String strGlKb,
      String strGlCode) {
    List<MstGeneralData> lstMstGeneral = new ArrayList<MstGeneralData>();
    CommonGetSystemCom sysCom = new CommonGetSystemCom(commonDao, null,
        null, readPropertiesFileService);

    try {
      // 汎用情報取得
      lstMstGeneral = sysCom.getMstGeneralConf(strGlKb, strGlCode);
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw e;
    }

    return lstMstGeneral;
  }

  /**
   * 共通関数を使用し、業務日付を取得する.
   * 
   * @return String 業務日付
   */
  public String getAplDate() {
    String strAplDate = null;
    CommonGetSystemCom systemCom = new CommonGetSystemCom(commonDao, null,
        null, readPropertiesFileService);

    try {
      // 業務日付取得
      strAplDate = systemCom.getAplDate();
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      return strAplDate;
    }
    return strAplDate;
  }

  /**
   * 共通関数を使用し、商品分類を取得する.
   *
   * @param strItemCode 品目コード
   * 
   * @return String 商品分類（1：商品、2：送料、3：手数料）
   */
  public String getItemClass(String strItemCode) {
    ItemClassData itemClass = null;
    CommonGetSystemCom sysCom = new CommonGetSystemCom(commonDao, null,
        null, readPropertiesFileService);

    try {
      // 商品分類取得
      itemClass = sysCom.getItemClass(strItemCode);
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      return null;
    } catch (Exception ex) {
      ex.printStackTrace();
      logger.error(ex.getMessage());
      return null;
    }

    return itemClass.getItemClass();
  }

  /**
   * 共通関数を使用し、消費税率を取得する.
   *
   * @param strDeliDate 納品日
   * 
   * @return double 消費税率
   */
  public double getTaxRate(String strDeliDate) {
    TaxRateData taxData = null;
    CommonGetSystemCom sysCom = new CommonGetSystemCom(commonDao, null,
        null, readPropertiesFileService);

    try {
      // 消費税率取得
      taxData = sysCom.getTaxRate(strDeliDate);
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      return 0;
    } catch (Exception ex) {
      ex.printStackTrace();
      logger.error(ex.getMessage());
      return 0;
    }

    return taxData.getTaxRate();
  }

  /**
   * 勘定科目情報マップを取得する.
   * 
   * @param tblUriBody 売上明細情報
   * @param tblMstCustomer 得意先マスタ
   * 
   * @return HashMap&lt;String, SubjectData&gt;
   *         本体貸方（Key：BodyCredit）、本体借方（Key：BodyDebit）、消費税貸方（Key：TaxCredit）、
   *         消費税借方（Key：TaxDebit）の４つの勘定科目情報が格納されたマップ
   */
  public HashMap<String, SubjectData> getMapSubject(TblUri01Body tblUriBody,
      MstCustomer tblMstCustomer) {
    HashMap<String, SubjectData> mapSubject = new HashMap<String, SubjectData>();
    SubjectData subjectData = null;

    String funcDtlCodeBodyCredit = ""; // 機能詳細区分_本体貸方
    String funcDtlCodeBodyDebit = ""; // 機能詳細区分_本体借方
    String funcDtlCodeTaxCredit = ""; // 機能詳細区分_消費税貸方
    String funcDtlCodeTaxDebit = ""; // 機能詳細区分_消費税借方

    String strItemCategory = tblUriBody.getItemCtgr().trim();
    if (strItemCategory.equalsIgnoreCase(UriConst.ITEM_CLASS_ITEM)) {
      // 商品分類コード = '1'（商品）の場合、
      short shtItemForm = new Short(readPropertiesFileService.getSetting(
          UriConst.ITEM_FORM_CODE));
      if (tblUriBody.getItemFrmCode() == shtItemForm) {
        // 製品形態コード = '7201'（商品）の場合、
        funcDtlCodeBodyCredit = UriConst.FUNC_DTL_KB_RECEIVEBLE; // 機能詳細区分_本体貸方（売掛金）
        funcDtlCodeBodyDebit = UriConst.FUNC_DTL_KB_ITEM; // 機能詳細区分_本体借方（商品売上）
        funcDtlCodeTaxCredit = UriConst.FUNC_DTL_KB_RECEIVEBLE; // 機能詳細区分_消費税貸方（売掛金）
        funcDtlCodeTaxDebit = UriConst.FUNC_DTL_KB_INPUT_TAX; // 機能詳細区分_消費税借方（仮受消費税）
      } else {
        // 製品形態コード = '7201'（商品）以外の場合、
        funcDtlCodeBodyCredit = UriConst.FUNC_DTL_KB_RECEIVEBLE; // 機能詳細区分_本体貸方（売掛金）
        funcDtlCodeBodyDebit = UriConst.FUNC_DTL_KB_PRODUCT; // 機能詳細区分_本体借方（製品売上）
        funcDtlCodeTaxCredit = UriConst.FUNC_DTL_KB_RECEIVEBLE; // 機能詳細区分_消費税貸方（売掛金）
        funcDtlCodeTaxDebit = UriConst.FUNC_DTL_KB_INPUT_TAX; // 機能詳細区分_消費税借方（仮受消費税）
      }
    } else if (strItemCategory.equalsIgnoreCase(UriConst.ITEM_CLASS_POSTAGE)) {
      // 商品分類コード = '2'（送料）の場合、
      funcDtlCodeBodyCredit = UriConst.FUNC_DTL_KB_SHIPMENT; // 機能詳細区分_本体貸方（配送費）
      funcDtlCodeBodyDebit = UriConst.FUNC_DTL_KB_ACCRUED; // 機能詳細区分_本体借方（未収金）
      funcDtlCodeTaxCredit = UriConst.FUNC_DTL_KB_OUTPUT_TAX; // 機能詳細区分_消費税貸方（仮払消費税）
      funcDtlCodeTaxDebit = UriConst.FUNC_DTL_KB_ACCRUED; // 機能詳細区分_消費税借方（未収金）
    } else if (strItemCategory.equalsIgnoreCase(UriConst.ITEM_CLASS_FEE)) {
      // 商品分類コード = '3'（手数料）の場合、
      funcDtlCodeBodyCredit = UriConst.FUNC_DTL_KB_COD; // 機能詳細区分_本体貸方（代引手数料）
      funcDtlCodeBodyDebit = UriConst.FUNC_DTL_KB_ACCRUED; // 機能詳細区分_本体借方（未収金）
      funcDtlCodeTaxCredit = UriConst.FUNC_DTL_KB_OUTPUT_TAX; // 機能詳細区分_消費税貸方（仮払消費税）
      funcDtlCodeTaxDebit = UriConst.FUNC_DTL_KB_ACCRUED; // 機能詳細区分_消費税借方（未収金）
    }

    // 勘定科目マスタ情報（本体貸方）取得
    subjectData = this.getSubject(UriConst.SUBJECT_KB_ACCOUNT,
        UriConst.FUNC_KB_EARNING, funcDtlCodeBodyCredit, tblMstCustomer);
    mapSubject.put(UriConst.SUBJECT_MAP_KEY_BODY_CREDIT, subjectData);

    // 勘定科目マスタ情報（本体借方）取得
    subjectData = this.getSubject(UriConst.SUBJECT_KB_ACCOUNT,
        UriConst.FUNC_KB_EARNING, funcDtlCodeBodyDebit, tblMstCustomer);
    mapSubject.put(UriConst.SUBJECT_MAP_KEY_BODY_DEBIT, subjectData);

    // 勘定科目マスタ情報（消費税貸方）取得
    subjectData = this.getSubject(UriConst.SUBJECT_KB_ACCOUNT,
        UriConst.FUNC_KB_EARNING, funcDtlCodeTaxCredit, tblMstCustomer);
    mapSubject.put(UriConst.SUBJECT_MAP_KEY_TAX_CREDIT, subjectData);

    // 勘定科目マスタ情報（消費税借方）取得
    subjectData = this.getSubject(UriConst.SUBJECT_KB_ACCOUNT,
        UriConst.FUNC_KB_EARNING, funcDtlCodeTaxDebit, tblMstCustomer);
    mapSubject.put(UriConst.SUBJECT_MAP_KEY_TAX_DEBIT, subjectData);

    return mapSubject;
  }

  /**
   * 勘定科目マスタから勘定科目情報を取得する.
   * 
   * @param strSubKb 科目区分
   * @param strFuncKb 機能区分
   * @param strFuncDetail 機能詳細区分
   * @param tblMstCustomer 得意先マスタ
   * 
   * @return 勘定科目情報
   */
  public SubjectData getSubject(String strSubKb, String strFuncKb,
      String strFuncDetail, MstCustomer tblMstCustomer) {
    List<SubjectData> lstSubjectData = new ArrayList<SubjectData>();

    try {
      // 条件をセットする
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("subKb", strSubKb);
      params.put("fncKb", strFuncKb);
      params.put("fncDtlKb", strFuncDetail);
      params.put("relComCls", tblMstCustomer.getRelComCls());

      lstSubjectData = uri01Dao.getUri01Mapper().getSubjectData(params);
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }

    if (lstSubjectData == null || lstSubjectData.isEmpty()) {
      return null;
    }
    SubjectData subjectData = lstSubjectData.get(0);
    if (subjectData.getSupSetKb().equalsIgnoreCase(UriConst.SUPSET_KB_NONE)) {
      // 補助科目設定区分 = 'なし'の場合、""
      subjectData.setSupSetVal("");
    } else if (subjectData.getSupSetKb().equalsIgnoreCase(
        UriConst.SUPSET_KB_RELATE)) {
      // 補助科目設定区分 = '関係会社コード'の場合、得意先マスタ.関係会社補助科目
      subjectData.setSupSetVal(tblMstCustomer.getRelComSub());
    }
    return subjectData;
  }

  /**
   * 売掛・未収ヘッダ情報を登録する.
   * 
   * @param lstSeiUrkBody 売掛明細情報リスト
   * @param lstSeiMshBody 未収明細情報リスト
   * @param tblUriHead 売上ヘッダ情報
   * @param tblMstCustomer 得意先マスタ
   * @param tblMstShop 店舗マスタ
   * 
   * @throws Exception DB例外
   */
  public UrkMshHeads registryUrkMshHead(List<TblSei01UrkBody> lstSeiUrkBody,
      List<TblSei01MshBody> lstSeiMshBody, TblUri01Head tblUriHead,
      MstCustomer tblMstCustomer, MstShop tblMstShop) throws Exception {
    UrkMshHeadAmount urkMshHeadAmt = new UrkMshHeadAmount();
    UrkMshHeads urkMshHeads = new UrkMshHeads();

    long lngCurUrkTtl = 0; // 現売掛金残高
    int intCurUrkTax = 0; // 現売掛金消費税残高
    int intMonUriTtl = 0; // 当月売上額
    int intMonUriTax = 0; // 当月売上額消費税
    int intMonSkrTtl = 0; // 当月総売上額
    int intMonItmTtl = 0; // 当月商品売上額
    int intMonItmDsc = 0; // 当月商品値引額
    int intMonHnpTtl = 0; // 当月返品額
    int intMonHnpTax = 0; // 当月返品額消費税
    long lngCurMshttl = 0; // 現未収金残高
    int intCurMshTax = 0; // 現未収金消費税残高
    int intMonMshTtl = 0; // 当月未収金額

    // 売掛明細情報のサイズ分、繰返す
    short shtItemForm = new Short(readPropertiesFileService.getSetting(
        UriConst.ITEM_FORM_CODE));
    for (TblSei01UrkBody tblSeiUrkBody : lstSeiUrkBody) {
      lngCurUrkTtl = (long) NumberUtil.add(lngCurUrkTtl, tblSeiUrkBody
          .getTrdPrice()); // 現売掛金残高（取引額）
      intCurUrkTax = (int) NumberUtil.add(intCurUrkTax, tblSeiUrkBody
          .getTrdTax()); // 現売掛金消費税残高（消費税）

      if (tblSeiUrkBody.getTrdKb().equalsIgnoreCase(
          UriConst.TRANSACTION_KB_URIAGE)) {
        /* 取引区分 = '売上'の場合、 */
        intMonUriTtl = (int) NumberUtil.add(intMonUriTtl, tblSeiUrkBody
            .getTrdPrice()); // 当月売上額（取引額）
        intMonUriTax = (int) NumberUtil.add(intMonUriTax, tblSeiUrkBody
            .getTrdTax()); // 当月売上額消費税（消費税）
        intMonSkrTtl = (int) NumberUtil.add(intMonSkrTtl, tblSeiUrkBody
            .getSkrAmt()); // 当月総売上額（仕切価金額）

        if (tblSeiUrkBody.getItemFrmCode() == shtItemForm) {
          /* 製品形態コード = '商品'の場合 */
          intMonItmTtl = (int) NumberUtil.add(intMonItmTtl, tblSeiUrkBody
              .getTrdPrice()); // 現売掛金残高（取引額）
          intMonItmDsc = (int) NumberUtil.minus(NumberUtil.add(intMonItmDsc,
              tblSeiUrkBody.getSkrAmt()), tblSeiUrkBody.getTrdPrice()); // 当月総売上額（仕切価金額
                                                                        // -
                                                                        // 取引額）
        }
      } else {
        /* 取引区分 = '欠品' or '返品' or '値引'の場合、 */
        intMonHnpTtl = (int) NumberUtil.add(intMonHnpTtl, tblSeiUrkBody
            .getTrdPrice()); // 当月返品額
        intMonHnpTax = (int) NumberUtil.add(intMonHnpTax, tblSeiUrkBody
            .getTrdTax()); // 当月返品額消費税
      }
    }

    // 未収明細情報のサイズ分、繰返す
    for (TblSei01MshBody tblSeiMshBody : lstSeiMshBody) {
      lngCurMshttl = (long) NumberUtil.add(lngCurMshttl, tblSeiMshBody
          .getTrdPrice()); // 現未収金残高（取引額）
      intCurMshTax = (int) NumberUtil.add(intCurMshTax, tblSeiMshBody
          .getTrdTax()); // 現未収金消費税残高（消費税）
      intMonMshTtl = (int) NumberUtil.add(intMonMshTtl, tblSeiMshBody
          .getTrdPrice()); // 当月未収金額（取引額）
      intMonUriTax = (int) NumberUtil.add(intMonUriTax, tblSeiMshBody
          .getTrdTax()); // 当月売上額消費税（消費税）
    }
    urkMshHeadAmt.setCurUrkTtl(lngCurUrkTtl); // 現売掛金残高（取引額）
    urkMshHeadAmt.setCurUrkTax(intCurUrkTax); // 現売掛金消費税残高（消費税）
    urkMshHeadAmt.setMonUriTtl(intMonUriTtl); // 当月売上額（取引額）
    urkMshHeadAmt.setMonUriTax(intMonUriTax); // 当月売上額消費税（消費税）
    urkMshHeadAmt.setMonSkrTtl(intMonSkrTtl); // 当月総売上額（仕切価金額）
    urkMshHeadAmt.setMonItmTtl(intMonItmTtl); // 現売掛金残高（取引額）
    urkMshHeadAmt.setMonItmDsc(intMonItmDsc); // 当月総売上額（仕切価金額 - 取引額）
    urkMshHeadAmt.setMonHnpTtl(intMonHnpTtl); // 当月返品額
    urkMshHeadAmt.setMonHnpTax(intMonHnpTax); // 当月返品額消費税
    urkMshHeadAmt.setCurMshttl(lngCurMshttl); // 現未収金残高（取引額）
    urkMshHeadAmt.setCurMshTax(intCurMshTax); // 現未収金消費税残高（消費税）
    urkMshHeadAmt.setMonMshTtl(intMonMshTtl); // 当月未収金額（取引額）

    TblSei01UrkMshHead tblSeiUrkMshShop = null;
    try {
      // 店舗区分 = '店有り'の場合、
      if (tblMstCustomer.getShopKb().equalsIgnoreCase(UriConst.SHOP_KB_TRUE)) {
        // 売掛・未収ヘッダ（店舗集計）情報取得
        tblSeiUrkMshShop = this.getUrkMshHead(tblUriHead, tblUriHead
            .getShopCode());
        if (tblSeiUrkMshShop == null) {
          // 売掛・未収ヘッダ（店舗集計）情報が存在しない場合、作成
          tblSeiUrkMshShop = this.createTblSeiUrkMshHead(tblUriHead,
              tblMstCustomer, tblMstShop, urkMshHeadAmt, tblUriHead
                  .getShopCode());
          urkMshHeads.setFlgInsertShop(true);
          // 売掛・未収ヘッダ（店舗集計）情報登録
          // this.insertSeiUrkMshHead(tblSeiUrkMshShop);
        } else {
          // 売掛・未収ヘッダ（店舗集計）情報が存在する場合、修正～更新
          tblSeiUrkMshShop = this.modifyTblSeiUrkMshHead(tblSeiUrkMshShop,
              urkMshHeadAmt, tblUriHead.getUserCodeReg());
          urkMshHeads.setFlgInsertShop(false);
          // 売掛・未収ヘッダ（店舗集計）情報更新
          // this.updateSeiUrkMshHead(tblSeiUrkMshShop);
        }
        urkMshHeads.setTblShopUrkMshHead(tblSeiUrkMshShop);
      } else {
        urkMshHeads.setTblShopUrkMshHead(null);
      }

      // 売掛・未収ヘッダ（得意先集計）情報取得
      TblSei01UrkMshHead tblSeiUrkMshCustomer = null;
      tblSeiUrkMshCustomer = this.getUrkMshHead(tblUriHead,
          CommonConst.SHOP_CD_NONE);
      if (tblSeiUrkMshCustomer == null) {
        // 売掛・未収ヘッダ（得意先集計）情報が存在しない場合、作成
        tblSeiUrkMshCustomer = this.createTblSeiUrkMshHead(tblUriHead,
            tblMstCustomer, tblMstShop, urkMshHeadAmt,
            CommonConst.SHOP_CD_NONE);
        urkMshHeads.setFlgInsertCustomer(true);
        // 売掛・未収ヘッダ（得意先集計）情報登録
        // this.insertSeiUrkMshHead(tblSeiUrkMshCustomer);
      } else {
        // 売掛・未収ヘッダ（得意先集計）情報が存在する場合、修正
        tblSeiUrkMshCustomer = this.modifyTblSeiUrkMshHead(tblSeiUrkMshCustomer,
            urkMshHeadAmt, tblUriHead.getUserCodeReg());
        urkMshHeads.setFlgInsertCustomer(false);
        // 売掛・未収ヘッダ（得意先集計）情報更新
        // this.updateSeiUrkMshHead(tblSeiUrkMshCustomer);
      }
      urkMshHeads.setTblCustomerUrkMshHead(tblSeiUrkMshCustomer);

      return urkMshHeads;
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * 売上仕訳テーブルリスト（本体＆消費税）を生成する.
   * 
   * @param tblUriHead 売上ヘッダ情報
   * @param tblUriBody 売上明細情報
   * @param mapSubjectData 勘定科目情報マップ
   * @param lngUriJournalNo 売上仕訳No
   * 
   * @return List&lt;TblUri01Journal&gt; 売上仕訳テーブルリスト（本体＆消費税）
   */
  public List<TblUri01Journal> createUriJournalDual(TblUri01Head tblUriHead,
      TblUri01Body tblUriBody, Map<String, SubjectData> mapSubjectData,
      long lngUriJournalNo) {
    List<TblUri01Journal> lstUriJournal = new ArrayList<TblUri01Journal>();

    // 売上仕訳テーブル（本体）生成
    TblUri01Journal uriJournalBody = this.createUriJournal(tblUriHead,
        tblUriBody, mapSubjectData, UriConst.BODY_TAX_KB_BODY,
        lngUriJournalNo);
    // 売上仕訳テーブル（消費税）生成
    TblUri01Journal uriJournalTax = this.createUriJournal(tblUriHead,
        tblUriBody, mapSubjectData, UriConst.BODY_TAX_KB_TAX,
        lngUriJournalNo);

    if (uriJournalBody == null || uriJournalTax == null) {
      return null;
    }

    // 生成データ格納
    lstUriJournal.add(uriJournalBody);
    lstUriJournal.add(uriJournalTax);

    return lstUriJournal;
  }

  /**
   * 売上仕訳テーブルを生成する.
   * 
   * @param tblUriHead 売上ヘッダ情報
   * @param tblUriBody 売上明細情報
   * @param mapSubjectData 勘定科目情報マップ
   * @param shtBodyTaxKb 本体・消費税区分（0：本体、1：消費税）
   * @param lngUriJournalNo 売上仕訳No
   * 
   * @return TblUri01Journal 売上仕訳テーブル
   */
  public TblUri01Journal createUriJournal(TblUri01Head tblUriHead,
      TblUri01Body tblUriBody, Map<String, SubjectData> mapSubjectData,
      short shtBodyTaxKb, long lngUriJournalNo) {
    TblUri01Journal tblUriJournal = new TblUri01Journal();

    // 勘定科目情報・金額取得
    SubjectData subjectDebit = null; // 借方勘定科目情報
    SubjectData subjectCredit = null; // 貸方勘定科目情報
    int inrAmount = 0; // 金額

    if (shtBodyTaxKb == UriConst.BODY_TAX_KB_BODY) {
      /* 本体の場合、 */
      subjectDebit = mapSubjectData
          .get(UriConst.SUBJECT_MAP_KEY_BODY_DEBIT); // 借方勘定科目情報
      subjectCredit = mapSubjectData
          .get(UriConst.SUBJECT_MAP_KEY_BODY_CREDIT); // 貸方勘定科目情報
      // 金額
      inrAmount = tblUriBody.getDeliAmtH();
    } else {
      /* 消費税の場合、 */
      subjectDebit = mapSubjectData
          .get(UriConst.SUBJECT_MAP_KEY_TAX_DEBIT); // 借方勘定科目情報
      subjectCredit = mapSubjectData
          .get(UriConst.SUBJECT_MAP_KEY_TAX_CREDIT); // 貸方勘定科目情報
      // 金額
      if (tblUriBody.getAccDueKb().trim()
          .equalsIgnoreCase(UriConst.ACCRUED_KB_NORMAL)) {
        /* 未収金区分 = '通常'の場合、 */
        inrAmount = tblUriBody.getDeliTax();
      } else {
        /* 未収金区分 = '未収金'の場合、 */
        inrAmount = (int) NumberUtil.multiply(tblUriBody.getDeliTax()
            .doubleValue(), (double) -1);
      }

    }

    // 売上仕訳テーブル設定
    tblUriJournal.setUriShiwakeNo(lngUriJournalNo); // 売上仕訳No
    tblUriJournal.setHonShoKb(shtBodyTaxKb); // 本体・消費税区分
    tblUriJournal.setDrJcode(tblUriHead.getOfficeCode()); // 借方事業所
    tblUriJournal.setDrScode(subjectDebit.getSecCode()); // 借方発生場所
    tblUriJournal.setDrAsub(subjectDebit.getSubNm()); // 借方勘定科目
    tblUriJournal.setDrAcode(subjectDebit.getSubCode()); // 借方勘定科目コード
    tblUriJournal.setDrAssub(subjectDebit.getSupSetVal()); // 借方補助科目
    tblUriJournal.setDrTaxkb(subjectDebit.getTaxKb()); // 借方税区分
    tblUriJournal.setCrJcode(tblUriHead.getOfficeCode()); // 貸方事業所
    tblUriJournal.setCrScode(subjectCredit.getSecCode()); // 貸方発生場所
    tblUriJournal.setCrAsub(subjectCredit.getSubNm()); // 貸方勘定科目
    tblUriJournal.setCrAcode(subjectCredit.getSubCode()); // 貸方勘定科目コード
    tblUriJournal.setCrAssub(subjectCredit.getSupSetVal()); // 貸方補助科目
    tblUriJournal.setCrTaxkb(subjectCredit.getTaxKb()); // 貸方税区分
    tblUriJournal.setDeliDate(tblUriHead.getAdupDate()); // 売上計上日
    tblUriJournal.setKingaku(inrAmount); // 金額
    tblUriJournal.setChainCode(tblUriHead.getChainCode().toString()); // チェーンコード
    tblUriJournal.setChainIdx(tblUriHead.getChainIdx().toString()); // チェーン枝番
    tblUriJournal.setCustCode(tblUriHead.getCustCode()); // 得意先コード
    tblUriJournal.setShopCode(tblUriHead.getShopCode()); // 店舗コード
    tblUriJournal.setDenNo(tblUriHead.getDenNo()); // 自社伝票No
    tblUriJournal.setDenIdx(tblUriHead.getDenIdx()); // 自社伝票枝番
    tblUriJournal.setKaikeiRenkeiId(null); // 会計連携ID

    // 共通項目の設定
    tblUriJournal = this.setCommonDataUriJournal(tblUriJournal,
        tblUriHead.getUserCodeReg(), "URI01-01D01", true);

    return tblUriJournal;
  }

  /**
   * 売掛明細情報を生成する.
   * 
   * @param tblUriHead 売上ヘッダ情報
   * @param tblUriBody 売上明細情報
   * @param tblMstCustomer 得意先マスタ
   * @param tblMstShop 店舗マスタ
   * @param lngUriJournalNo 売上仕訳No
   * 
   * @return TblSei01UrkBody 売掛明細情報
   */
  public TblSei01UrkBody createSeiUrkBody(TblUri01Head tblUriHead,
      TblUri01Body tblUriBody, MstCustomer tblMstCustomer,
      MstShop tblMstShop, long lngUrkNo, long lngUriJournalNo) {

    String strTrdKb = ""; // 取引区分
    int intTrdPrice = 0; // 取引額
    int intTrdTax = 0; // 消費税

    // 取引区分
    if (tblUriHead.getDenKb().equalsIgnoreCase(UriConst.DEN_KB_URIAGE)) {
      strTrdKb = UriConst.TRANSACTION_KB_URIAGE;
    } else if (tblUriHead.getDenKb().equalsIgnoreCase(UriConst.DEN_KB_HENPIN)) {
      strTrdKb = UriConst.TRANSACTION_KB_HENPIN;
    } else if (tblUriHead.getDenKb().equalsIgnoreCase(UriConst.DEN_KB_KEPPIN)) {
      strTrdKb = UriConst.TRANSACTION_KB_KEPPIN;
    } else if (tblUriHead.getDenKb().equalsIgnoreCase(UriConst.DEN_KB_NEBIKI)) {
      strTrdKb = UriConst.TRANSACTION_KB_NEBIKI;
    }

    // 取引額・消費税
    if (tblUriHead.getDenKb().equalsIgnoreCase(UriConst.DEN_KB_URIAGE)) {
      intTrdPrice = tblUriBody.getDeliAmt();
      intTrdTax = tblUriBody.getDeliTax();
    } else {
      intTrdPrice = (int) NumberUtil.multiply(tblUriBody.getDeliAmt()
          .doubleValue(), (double) -1);
      intTrdTax = (int) NumberUtil.multiply(tblUriBody.getDeliTax()
          .doubleValue(), (double) -1);
    }

    TblSei01UrkBody tblSeiUrkBody = new TblSei01UrkBody();
    tblSeiUrkBody.setUriNo(lngUrkNo); // 売掛No
    tblSeiUrkBody.setChainCode(tblUriHead.getChainCode()); // チェーンコード
    tblSeiUrkBody.setChainIdx(tblUriHead.getChainIdx()); // チェーン枝番
    tblSeiUrkBody.setCustCode(tblUriHead.getCustCode()); // 得意先コード
    tblSeiUrkBody.setShopCode(tblUriHead.getShopCode()); // 店舗コード
    tblSeiUrkBody.setJigyoCode(tblUriHead.getOfficeCode()); // 事業所コード
    tblSeiUrkBody.setTrdDate(tblUriHead.getAdupDate()); // 売上計上日
    tblSeiUrkBody.setTrdKb(strTrdKb); // 取引区分
    tblSeiUrkBody.setTrdPrice(intTrdPrice); // 取引額
    tblSeiUrkBody.setTrdTax(intTrdTax); // 消費税
    tblSeiUrkBody.setTrdId(lngUriJournalNo); // 取引管理ID
    tblSeiUrkBody.setBildId(new Long(0)); // 請求ID
    /* 集約店舗コード */
    if (tblMstCustomer.getShopKb()
        .equalsIgnoreCase(UriConst.SHOP_KB_TRUE)) {
      /* 店舗区分 = '店有り'の場合、 */
      if (tblMstShop.getSumShopCode() == null) {
        tblSeiUrkBody.setSumShopCode("");
      } else {
        tblSeiUrkBody.setSumShopCode(tblMstShop.getSumShopCode());
      }
    } else {
      /* 店舗区分 = '店無し'の場合、 */
      tblSeiUrkBody.setSumShopCode(CommonConst.SHOP_CD_NONE);
    }
    tblSeiUrkBody.setDenNo(tblUriHead.getDenNo()); // 自社伝票No
    tblSeiUrkBody.setDenIdx(tblUriHead.getDenIdx()); // 自社伝票枝番
    tblSeiUrkBody.setCustDenRow(tblUriBody.getCustDenRow()); // 自社伝票行No
    tblSeiUrkBody.setSkrAmt(tblUriBody.getSkrAmt()); // 仕切価金額
    tblSeiUrkBody.setCustSkrAmt(tblUriBody.getSnpSkrAmt()); // 先方仕切価金額
    tblSeiUrkBody.setRegType(UriConst.REG_TYPE_NORMAL); // 入力形態
    tblSeiUrkBody.setItemFrmCode(tblUriBody.getItemFrmCode()); // 製品形態コード
    tblSeiUrkBody.setCustCls(tblMstCustomer.getCustCls()); // 得意先種別
    tblSeiUrkBody = this.setCommonDataSeiUrkBody(tblSeiUrkBody, tblUriHead
        .getUserCodeReg(), "URI01-01D01", true);

    return tblSeiUrkBody;
  }

  /**
   * 未収明細情報を生成する.
   * 
   * @param tblUriHead 売上ヘッダ情報
   * @param tblUriBody 売上明細情報
   * @param tblMstCustomer 得意先マスタ
   * @param tblMstShop 店舗マスタ
   * @param lngUriJournalNo 売上仕訳No
   * 
   * @return TblSei01MshBody 未収明細情報
   */
  public TblSei01MshBody createSeiMshBody(TblUri01Head tblUriHead,
      TblUri01Body tblUriBody, MstCustomer tblMstCustomer,
      MstShop tblMstShop, long lngMshNo, long lngUriJournalNo) {

    String strTrdKb = ""; // 取引区分
    int intTrdPrice = 0; // 取引額
    int intTrdTax = 0; // 消費税

    // 取引区分
    if (tblUriHead.getDenKb().equalsIgnoreCase(UriConst.DEN_KB_URIAGE)) {
      strTrdKb = UriConst.TRANSACTION_KB_URIAGE;
    } else if (tblUriHead.getDenKb().equalsIgnoreCase(UriConst.DEN_KB_HENPIN)) {
      strTrdKb = UriConst.TRANSACTION_KB_HENPIN;
    } else if (tblUriHead.getDenKb().equalsIgnoreCase(UriConst.DEN_KB_KEPPIN)) {
      strTrdKb = UriConst.TRANSACTION_KB_KEPPIN;
    } else if (tblUriHead.getDenKb().equalsIgnoreCase(UriConst.DEN_KB_NEBIKI)) {
      strTrdKb = UriConst.TRANSACTION_KB_NEBIKI;
    }

    // 取引額・消費税
    if (tblUriHead.getDenKb().equalsIgnoreCase(UriConst.DEN_KB_URIAGE)) {
      intTrdPrice = tblUriBody.getDeliAmt();
      intTrdTax = tblUriBody.getDeliTax();
    } else {
      intTrdPrice = (int) NumberUtil.multiply(tblUriBody.getDeliAmt()
          .doubleValue(), (double) -1);
      intTrdTax = (int) NumberUtil.multiply(tblUriBody.getDeliTax()
          .doubleValue(), (double) -1);
    }

    TblSei01MshBody tblSeiMshBody = new TblSei01MshBody();
    tblSeiMshBody.setMishuNo(lngMshNo); // 売掛No
    tblSeiMshBody.setChainCode(tblUriHead.getChainCode()); // チェーンコード
    tblSeiMshBody.setChainIdx(tblUriHead.getChainIdx()); // チェーン枝番
    tblSeiMshBody.setCustCode(tblUriHead.getCustCode()); // 得意先コード
    tblSeiMshBody.setShopCode(tblUriHead.getShopCode()); // 店舗コード
    tblSeiMshBody.setJigyoCode(tblUriHead.getOfficeCode()); // 事業所コード
    tblSeiMshBody.setTrdDate(tblUriHead.getAdupDate()); // 売上計上日
    tblSeiMshBody.setTrdKb(strTrdKb); // 取引区分
    tblSeiMshBody.setTrdPrice(intTrdPrice); // 取引額
    tblSeiMshBody.setTrdTax(intTrdTax); // 消費税
    tblSeiMshBody.setTrdId(lngUriJournalNo); // 取引管理ID
    tblSeiMshBody.setBildId(new Long(0)); // 請求ID
    /* 集約店舗コード */
    if (tblMstCustomer.getShopKb().equalsIgnoreCase(UriConst.SHOP_KB_TRUE)) {
      /* 店舗区分 = '店有り'の場合、 */
      if (tblMstShop.getSumShopCode() == null) {
        tblSeiMshBody.setSumShopCode("");
      } else {
        tblSeiMshBody.setSumShopCode(tblMstShop.getSumShopCode());
      }
    } else {
      /* 店舗区分 = '店無し'の場合、 */
      tblSeiMshBody.setSumShopCode(CommonConst.SHOP_CD_NONE);
    }
    tblSeiMshBody.setDenNo(tblUriHead.getDenNo()); // 自社伝票No
    tblSeiMshBody.setDenIdx(tblUriHead.getDenIdx()); // 自社伝票枝番
    tblSeiMshBody.setCustDenRow(tblUriBody.getCustDenRow()); // 自社伝票行No
    tblSeiMshBody.setRegType(UriConst.REG_TYPE_NORMAL); // 入力形態
    tblSeiMshBody.setCustCls(tblMstCustomer.getCustCls()); // 得意先種別
    tblSeiMshBody = this.setCommonDataSeiMshBody(tblSeiMshBody,
        tblUriHead.getUserCodeReg(), "URI01-01D01", true);

    return tblSeiMshBody;
  }

  /**
   * 売掛・未収ヘッダ情報を作成する.
   * 
   * @param tblUriHead 売上ヘッダ情報
   * @param tblMstCustomer 得意先マスタ
   * @param tblMstShop 店舗マスタ
   * @param urkMshHeadAmt 売掛・未収ヘッダ向け金額情報クラス
   * @param ｓｔｒShopCode 店舗コード
   * 
   * @return TblSei01UrkMshHead 売掛・未収ヘッダ情報
   */
  private TblSei01UrkMshHead createTblSeiUrkMshHead(TblUri01Head tblUriHead,
      MstCustomer tblMstCustomer, MstShop tblMstShop,
      UrkMshHeadAmount urkMshHeadAmt, String strShopCode) {
    TblSei01UrkMshHead tblSeiUrkMshShop = new TblSei01UrkMshHead();

    tblSeiUrkMshShop.setJigyoCode(tblUriHead.getOfficeCode()); // 事業所コード
    tblSeiUrkMshShop.setChainCode(tblUriHead.getChainCode()); // チェーンコード
    tblSeiUrkMshShop.setChainIdx(tblUriHead.getChainIdx()); // チェーン枝番
    tblSeiUrkMshShop.setCustCode(tblUriHead.getCustCode()); // 得意先コード
    tblSeiUrkMshShop.setShopCode(strShopCode); // 店舗コード
    /* 請求先コード */
    if (tblMstCustomer.getBildFlg().equalsIgnoreCase(CommonConst.BILL_FLG)) {
      /* 請求フラグ = '対象'の場合、 */
      tblSeiUrkMshShop.setBildCode(tblUriHead.getCustCode());
    } else {
      /* 請求フラグ = '対象外'の場合、 */
      tblSeiUrkMshShop.setBildCode(tblMstCustomer.getBildCode());
    }
    tblSeiUrkMshShop.setAccountMonth(tblUriHead.getAdupDate().substring(0, 6)); // 会計年月
    /* 集約店舗情報 */
    if (tblMstCustomer.getShopKb().equalsIgnoreCase(UriConst.SHOP_KB_TRUE)) {
      /* 店舗区分 = '店有り'の場合、 */
      if (tblMstShop.getSumShopCode() != null) {
        tblSeiUrkMshShop.setSumShopCode(tblMstShop.getSumShopCode()); // 集約店舗コード
      } else {
        tblSeiUrkMshShop.setSumShopCode("");
      }
      if (tblMstShop.getSumShopKb() != null) {
        tblSeiUrkMshShop.setSumShopKb(tblMstShop.getSumShopKb()); // 集約店舗区分
      } else {
        tblSeiUrkMshShop.setSumShopKb("");
      }
      if (tblMstShop.getSumShopJkn() != null) {
        tblSeiUrkMshShop.setSumShopJkn(tblMstShop.getSumShopJkn()); // 集約店舗条件
      } else {
        tblSeiUrkMshShop.setSumShopJkn("");
      }
    } else {
      /* 店舗区分 = '店無し'の場合、 */
      tblSeiUrkMshShop.setSumShopCode(CommonConst.SHOP_CD_NONE); // 集約店舗コード
      tblSeiUrkMshShop.setSumShopKb(""); // 集約店舗区分
      tblSeiUrkMshShop.setSumShopJkn(""); // 集約店舗条件
    }
    tblSeiUrkMshShop.setCurUrikakeTtl(urkMshHeadAmt.getCurUrkTtl()); // 現売掛金残高
    tblSeiUrkMshShop.setCurUrikakeTax(urkMshHeadAmt.getCurUrkTax()); // 現売掛金残高消費税
    tblSeiUrkMshShop.setCurMishuTtl(urkMshHeadAmt.getCurMshttl()); // 現未収金残高
    tblSeiUrkMshShop.setCurMishuTax(urkMshHeadAmt.getCurMshTax()); // 現未収金残高消費税
    tblSeiUrkMshShop.setBgnUrikakeTtl(0); // 月初売掛金残高
    tblSeiUrkMshShop.setBgnUrikakeTax(0); // 月初売掛金残高消費税
    tblSeiUrkMshShop.setBgnMishuTtl(0); // 月初未収金残高
    tblSeiUrkMshShop.setBgnMishuTax(0); // 月初未収金残高消費税
    tblSeiUrkMshShop.setMonUriageTtl(urkMshHeadAmt.getMonUriTtl()); // 当月売上額
    tblSeiUrkMshShop.setMonUriageTax(urkMshHeadAmt.getMonUriTax()); // 当月売上額消費税
    tblSeiUrkMshShop.setMonHenpinTtl(urkMshHeadAmt.getMonHnpTtl()); // 当月返品額
    tblSeiUrkMshShop.setMonHenpinTax(urkMshHeadAmt.getMonHnpTax()); // 当月返品額消費税
    tblSeiUrkMshShop.setMonTaxAdj(0); // 当月消費税調整額
    tblSeiUrkMshShop.setMonShikiriTtl(urkMshHeadAmt.getMonSkrTtl()); // 当月総売上額
    tblSeiUrkMshShop.setMonUnyukinTtl(0); // 当月売掛入金額
    tblSeiUrkMshShop.setMonShohinTtl(urkMshHeadAmt.getMonItmTtl()); // 当月商品売上額
    tblSeiUrkMshShop.setMonShohinDsc(urkMshHeadAmt.getMonItmDsc()); // 当月商品値引額
    tblSeiUrkMshShop.setMonMishuTtl(urkMshHeadAmt.getMonMshTtl()); // 当月未収金額
    tblSeiUrkMshShop.setMonMnyukinTax(0); // 当月未収入金額
    tblSeiUrkMshShop.setCustCls(tblMstCustomer.getCustCls()); // 得意先種別
    tblSeiUrkMshShop.setCloseDate(""); // 使用中止日
    tblSeiUrkMshShop = this.setCommonDataSeiUrkMshHead(tblSeiUrkMshShop,
        tblUriHead.getUserCodeReg(), "URI01-01D01", true); // 共通項目
    return tblSeiUrkMshShop;
  }

  /**
   * 売掛・未収ヘッダ情報を修正する.
   * 
   * @param tblSeiUrkMsh 売掛・未収ヘッダ情報
   * @param tblUriHead 売上ヘッダ情報
   * @param tblMstCustomer 得意先マスタ
   * @param tblMstShop 店舗マスタ
   * @param urkMshHeadAmt 売掛・未収ヘッダ向け金額情報クラス
   * @param ｓｔｒShopCode 店舗コード
   * 
   * @return TblSei01UrkMshHead 売掛・未収ヘッダ情報
   */
  private TblSei01UrkMshHead modifyTblSeiUrkMshHead(
      TblSei01UrkMshHead tblSeiUrkMsh, UrkMshHeadAmount urkMshHeadAmt,
      String strUserCodeReg) {

    tblSeiUrkMsh.setCurUrikakeTtl((long) NumberUtil.add(
        tblSeiUrkMsh.getCurUrikakeTtl(), urkMshHeadAmt.getCurUrkTtl())); // 現売掛金残高
    tblSeiUrkMsh.setCurUrikakeTax((int) NumberUtil.add(
        tblSeiUrkMsh.getCurUrikakeTax(), urkMshHeadAmt.getCurUrkTax())); // 現売掛金残高消費税
    tblSeiUrkMsh.setCurMishuTtl((long) NumberUtil.add(
        tblSeiUrkMsh.getCurMishuTtl(), urkMshHeadAmt.getCurMshttl())); // 現未収金残高
    tblSeiUrkMsh.setCurMishuTax((int) NumberUtil.add(
        tblSeiUrkMsh.getCurMishuTax(), urkMshHeadAmt.getCurMshTax())); // 現未収金残高消費税
    tblSeiUrkMsh.setMonUriageTtl((int) NumberUtil.add(
        tblSeiUrkMsh.getMonUriageTtl(), urkMshHeadAmt.getMonUriTtl())); // 当月売上額
    tblSeiUrkMsh.setMonUriageTax((int) NumberUtil.add(
        tblSeiUrkMsh.getMonUriageTax(), urkMshHeadAmt.getMonUriTax())); // 当月売上額消費税
    tblSeiUrkMsh.setMonHenpinTtl((int) NumberUtil.add(
        tblSeiUrkMsh.getMonHenpinTtl(), urkMshHeadAmt.getMonHnpTtl())); // 当月返品額
    tblSeiUrkMsh.setMonHenpinTax((int) NumberUtil.add(
        tblSeiUrkMsh.getMonHenpinTax(), urkMshHeadAmt.getMonHnpTax())); // 当月返品額消費税
    tblSeiUrkMsh.setMonShikiriTtl((int) NumberUtil.add(
        tblSeiUrkMsh.getMonShikiriTtl(), urkMshHeadAmt.getMonSkrTtl())); // 当月総売上額
    tblSeiUrkMsh.setMonShohinTtl((int) NumberUtil.add(
        tblSeiUrkMsh.getMonShohinTtl(), urkMshHeadAmt.getMonItmTtl())); // 当月商品売上額
    tblSeiUrkMsh.setMonShohinDsc((int) NumberUtil.add(
        tblSeiUrkMsh.getMonShohinDsc(), urkMshHeadAmt.getMonItmDsc())); // 当月商品値引額
    tblSeiUrkMsh.setMonMishuTtl((int) NumberUtil.add(
        tblSeiUrkMsh.getMonMishuTtl(), urkMshHeadAmt.getMonMshTtl())); // 当月未収金額
    tblSeiUrkMsh = this.setCommonDataSeiUrkMshHead(tblSeiUrkMsh,
        strUserCodeReg, "URI01-01D01", false); // 共通項目

    return tblSeiUrkMsh;
  }

  /**
   * 売上ヘッダ情報.共通項目の設定.
   * 
   * @param tblUriHead 売上ヘッダ情報
   * @param strUserId ユーザID
   * @param strProgId プログラムID
   * @param isNew 新規フラグ（true：新規、false：更新）
   * 
   * @return TblUri01Head 売上ヘッダ情報
   */
  public TblUri01Head setCommonDataUriHead(TblUri01Head tblUriHead,
      String strUserId, String strProgId, boolean isNew) {

    String currentDate = DateUtil.getSysDate();
    String currentTime = DateUtil.getSysTime();

    if (isNew) {
      tblUriHead.setInsUserid(strUserId);
      tblUriHead.setInsPgid(strProgId);
      tblUriHead.setInsYmd(currentDate);
      tblUriHead.setInsTime(currentTime);
    }

    tblUriHead.setUpdUserid(strUserId);
    tblUriHead.setUpdPgid(strProgId);
    tblUriHead.setUpdYmd(currentDate);
    tblUriHead.setUpdTime(currentTime);

    return tblUriHead;
  }

  /**
   * 売上明細情報.共通項目の設定.
   * 
   * @param tblUriBody 売上明細情報
   * @param strUserId ユーザID
   * @param strProgId プログラムID
   * @param isNew 新規フラグ（true：新規、false：更新）
   * 
   * @return TblUri01Body 売上明細情報
   */
  public TblUri01Body setCommonDataUriBody(TblUri01Body tblUriBody,
      String strUserId, String strProgId, boolean isNew) {

    String currentDate = DateUtil.getSysDate();
    String currentTime = DateUtil.getSysTime();

    if (isNew) {
      tblUriBody.setInsUserid(strUserId);
      tblUriBody.setInsPgid(strProgId);
      tblUriBody.setInsYmd(currentDate);
      tblUriBody.setInsTime(currentTime);
    }

    tblUriBody.setUpdUserid(strUserId);
    tblUriBody.setUpdPgid(strProgId);
    tblUriBody.setUpdYmd(currentDate);
    tblUriBody.setUpdTime(currentTime);

    return tblUriBody;
  }

  /**
   * 売上仕訳情報.共通項目の設定.
   * 
   * @param tblUriJournal 売上仕訳情報
   * @param strUserId ユーザID
   * @param strProgId プログラムID
   * @param isNew 新規フラグ（true：新規、false：更新）
   * 
   * @return TblUri01Journal 売上仕訳情報
   */
  public TblUri01Journal setCommonDataUriJournal(TblUri01Journal tblUriJournal,
      String strUserId, String strProgId, boolean isNew) {

    String currentDate = DateUtil.getSysDate();
    String currentTime = DateUtil.getSysTime();

    if (isNew) {
      tblUriJournal.setInsUserid(strUserId);
      tblUriJournal.setInsPgid(strProgId);
      tblUriJournal.setInsYmd(currentDate);
      tblUriJournal.setInsTime(currentTime);
    }

    tblUriJournal.setUpdUserid(strUserId);
    tblUriJournal.setUpdPgid(strProgId);
    tblUriJournal.setUpdYmd(currentDate);
    tblUriJournal.setUpdTime(currentTime);

    return tblUriJournal;
  }

  /**
   * 売掛・未収ヘッダ情報.共通項目の設定.
   * 
   * @param tblSeiUrkMshHead 売掛・未収ヘッダ情報
   * @param strUserId ユーザID
   * @param strProgId プログラムID
   * @param isNew 新規フラグ（true：新規、false：更新）
   * 
   * @return TblSei01UrkBody 売掛・未収ヘッダ情報
   */
  private TblSei01UrkMshHead setCommonDataSeiUrkMshHead(
      TblSei01UrkMshHead tblSeiUrkMshHead, String strUserId, String strProgId,
      boolean isNew) {

    String currentDate = DateUtil.getSysDate();
    String currentTime = DateUtil.getSysTime();

    if (isNew) {
      tblSeiUrkMshHead.setInsUserid(strUserId);
      tblSeiUrkMshHead.setInsPgid(strProgId);
      tblSeiUrkMshHead.setInsYmd(currentDate);
      tblSeiUrkMshHead.setInsTime(currentTime);
    }

    tblSeiUrkMshHead.setUpdUserid(strUserId);
    tblSeiUrkMshHead.setUpdPgid(strProgId);
    tblSeiUrkMshHead.setUpdYmd(currentDate);
    tblSeiUrkMshHead.setUpdTime(currentTime);

    return tblSeiUrkMshHead;
  }

  /**
   * 売掛明細情報.共通項目の設定.
   * 
   * @param tblSeiUrkBody 売掛明細情報
   * @param strUserId ユーザID
   * @param strProgId プログラムID
   * @param isNew 新規フラグ（true：新規、false：更新）
   * @return TblSei01UrkBody 売掛明細情報
   */
  private TblSei01UrkBody setCommonDataSeiUrkBody(TblSei01UrkBody tblSeiUrkBody,
      String strUserId, String strProgId, boolean isNew) {

    String currentDate = DateUtil.getSysDate();
    String currentTime = DateUtil.getSysTime();

    if (isNew) {
      tblSeiUrkBody.setInsUserid(strUserId);
      tblSeiUrkBody.setInsPgid(strProgId);
      tblSeiUrkBody.setInsYmd(currentDate);
      tblSeiUrkBody.setInsTime(currentTime);
    }

    tblSeiUrkBody.setUpdUserid(strUserId);
    tblSeiUrkBody.setUpdPgid(strProgId);
    tblSeiUrkBody.setUpdYmd(currentDate);
    tblSeiUrkBody.setUpdTime(currentTime);

    return tblSeiUrkBody;
  }

  /**
   * 未収明細情報.共通項目の設定.
   * 
   * @param tblSeiMshBody 未収明細情報
   * @param strUserId ユーザID
   * @param strProgId プログラムID
   * @param isNew 新規フラグ（true：新規、false：更新）
   * 
   * @return TblSei01UrkBody 未収明細情報
   */
  private TblSei01MshBody setCommonDataSeiMshBody(TblSei01MshBody tblSeiMshBody,
      String strUserId, String strProgId, boolean isNew) {

    String currentDate = DateUtil.getSysDate();
    String currentTime = DateUtil.getSysTime();

    if (isNew) {
      tblSeiMshBody.setInsUserid(strUserId);
      tblSeiMshBody.setInsPgid(strProgId);
      tblSeiMshBody.setInsYmd(currentDate);
      tblSeiMshBody.setInsTime(currentTime);
    }

    tblSeiMshBody.setUpdUserid(strUserId);
    tblSeiMshBody.setUpdPgid(strProgId);
    tblSeiMshBody.setUpdYmd(currentDate);
    tblSeiMshBody.setUpdTime(currentTime);

    return tblSeiMshBody;
  }

  /**
   * 売掛・未収ヘッダ向け金額情報クラス.
   * 
   * @author アクトブレーン 前田
   * @version 1.0.0
   * @since 1.0.0
   */
  private class UrkMshHeadAmount {

    /* 現売掛金残高 */
    private long curUrkTtl;
    /* 現売掛金消費税残高 */
    private int curUrkTax;
    /* 当月売上額 */
    private int monUriTtl;
    /* 当月売上額消費税 */
    private int monUriTax;
    /* 当月総売上額 */
    private int monSkrTtl;
    /* 当月商品売上額 */
    private int monItmTtl;
    /* 当月商品値引額 */
    private int monItmDsc;
    /* 当月返品額 */
    private int monHnpTtl;
    /* 当月返品額消費税 */
    private int monHnpTax;
    /* 現未収金残高 */
    private long curMshttl;
    /* 現未収金消費税残高 */
    private int curMshTax;
    /* 当月未収金額 */
    private int monMshTtl;

    /**
     * curUrkTtlを取得する.
     *
     * @return long curUrkTtl
     */
    public long getCurUrkTtl() {
      return curUrkTtl;
    }

    /**
     * curUrkTtlをセットする.
     *
     * @param curUrkTtl curUrkTtl
     */
    public void setCurUrkTtl(long curUrkTtl) {
      this.curUrkTtl = curUrkTtl;
    }

    /**
     * curUrkTaxを取得する.
     *
     * @return int curUrkTax
     */
    public int getCurUrkTax() {
      return curUrkTax;
    }

    /**
     * curUrkTaxをセットする.
     *
     * @param curUrkTax curUrkTax
     */
    public void setCurUrkTax(int curUrkTax) {
      this.curUrkTax = curUrkTax;
    }

    /**
     * monUriTtlを取得する.
     *
     * @return int monUriTtl
     */
    public int getMonUriTtl() {
      return monUriTtl;
    }

    /**
     * monUriTtlをセットする.
     *
     * @param monUriTtl monUriTtl
     */
    public void setMonUriTtl(int monUriTtl) {
      this.monUriTtl = monUriTtl;
    }

    /**
     * monUriTaxを取得する.
     *
     * @return int monUriTax
     */
    public int getMonUriTax() {
      return monUriTax;
    }

    /**
     * monUriTaxをセットする.
     *
     * @param monUriTax monUriTax
     */
    public void setMonUriTax(int monUriTax) {
      this.monUriTax = monUriTax;
    }

    /**
     * monSkrTtlを取得する.
     *
     * @return int monSkrTtl
     */
    public int getMonSkrTtl() {
      return monSkrTtl;
    }

    /**
     * monSkrTtlをセットする.
     *
     * @param monSkrTtl monSkrTtl
     */
    public void setMonSkrTtl(int monSkrTtl) {
      this.monSkrTtl = monSkrTtl;
    }

    /**
     * monItmTtlを取得する.
     *
     * @return int monItmTtl
     */
    public int getMonItmTtl() {
      return monItmTtl;
    }

    /**
     * monItmTtlをセットする.
     *
     * @param monItmTtl monItmTtl
     */
    public void setMonItmTtl(int monItmTtl) {
      this.monItmTtl = monItmTtl;
    }

    /**
     * monItmDscを取得する.
     *
     * @return int monItmDsc
     */
    public int getMonItmDsc() {
      return monItmDsc;
    }

    /**
     * monItmDscをセットする.
     *
     * @param monItmDsc monItmDsc
     */
    public void setMonItmDsc(int monItmDsc) {
      this.monItmDsc = monItmDsc;
    }

    /**
     * monHnpTtlを取得する.
     *
     * @return int monHnpTtl
     */
    public int getMonHnpTtl() {
      return monHnpTtl;
    }

    /**
     * monHnpTtlをセットする.
     *
     * @param monHnpTtl monHnpTtl
     */
    public void setMonHnpTtl(int monHnpTtl) {
      this.monHnpTtl = monHnpTtl;
    }

    /**
     * monHnpTaxを取得する.
     *
     * @return int monHnpTax
     */
    public int getMonHnpTax() {
      return monHnpTax;
    }

    /**
     * monHnpTaxをセットする.
     *
     * @param monHnpTax monHnpTax
     */
    public void setMonHnpTax(int monHnpTax) {
      this.monHnpTax = monHnpTax;
    }

    /**
     * curMshttlを取得する.
     *
     * @return long curMshttl
     */
    public long getCurMshttl() {
      return curMshttl;
    }

    /**
     * curMshttlをセットする.
     *
     * @param curMshttl curMshttl
     */
    public void setCurMshttl(long curMshttl) {
      this.curMshttl = curMshttl;
    }

    /**
     * curMshTaxを取得する.
     *
     * @return int curMshTax
     */
    public int getCurMshTax() {
      return curMshTax;
    }

    /**
     * curMshTaxをセットする.
     *
     * @param curMshTax curMshTax
     */
    public void setCurMshTax(int curMshTax) {
      this.curMshTax = curMshTax;
    }

    /**
     * monMshTtlを取得する.
     *
     * @return int monMshTtl
     */
    public int getMonMshTtl() {
      return monMshTtl;
    }

    /**
     * monMshTtlをセットする.
     *
     * @param monMshTtl monMshTtl
     */
    public void setMonMshTtl(int monMshTtl) {
      this.monMshTtl = monMshTtl;
    }
  }
}
