package jp.co.daitoku_sh.dfcm.dfcmmain.controller.com.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import jp.co.daitoku_sh.dfcm.common.component.ErrorInfo;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemError;

@Controller
@RequestMapping(value = "/test")
public class TestController {

  @Autowired
  @RequestMapping(value = "/")
  public String top(WebRequest request) {

    return "TestParent";
  }

  // JAVAテスト用
  @Autowired
  CommonDao commonDao;

  @Autowired
  PlatformTransactionManager txManager;

  @Autowired
  ApplicationContext appContext;

  @Autowired
  @Qualifier("readPropertiesFileService")
  ReadPropertiesFileService readPropertiesFileService;

/*  @Autowired
  @Qualifier("juc0106b01Service")
  private Juc0106b01Service juc0106b01Service;*/

  @RequestMapping(produces = "text/plain;charset=UTF-8", value = "/testJava", method = RequestMethod.POST)
  public @ResponseBody String testJava(String test1, String test2, String test3, String test4, String test5) throws Exception {

    // 検索処理
    String jsonData = "";

    /*
    // 受注・売上共通テスト----------------------------->>
    CommonGetJucUriCom commonGetJucUriCom = new CommonGetJucUriCom(commonDao);
    Short chainCode = 4900;
    Short chainIdx = 0;
    String deliCode = "4901";
    Short jigyoCode = 13;
    Integer businessDate = 20151021;
    String deliDate = "20151021";
    String bin = "1";
    String deliKb = "1";

    String test = commonGetJucUriCom.getProductDate(chainCode, chainIdx, deliCode, jigyoCode, businessDate, deliDate, bin, deliKb);
*/


    // 受注・売上共通テスト-----------------------------<<


    // 共通部品テスト------------------------------------>>

    CommonGetSystemCom commonGetSystemCom = new CommonGetSystemCom(commonDao, txManager, appContext, readPropertiesFileService);

/*
    // 汎用マスタ設定取得======================
    String strGlKb = "TEST";
    String strGlCode = "";
    List<MstGeneralData> lstMstGeneralData = commonGetSystemCom
        .getMstGeneralConf(strGlKb, strGlCode);
    if (lstMstGeneralData == null) {
      System.out.println("汎用マスタ設定取得：件数０");
    } else {
      for (MstGeneralData mstGeneralData : lstMstGeneralData) {
        System.out.println("汎用マスタ設定取得（コード）：" + mstGeneralData.getGlCode());
        System.out.println("汎用マスタ設定取得（項目１）：" + mstGeneralData.getTarget1());
      }
    }

    // 検索上限値取得===========================
    int intSearchMax = commonGetSystemCom.getCodeSearchMax();
    System.out.println("検索上限値：" + intSearchMax);


    // 業務日付=================================
    String strAplDate = commonGetSystemCom.getAplDate();
    System.out.println("業務日付：" + strAplDate);


    // 数値丸め=================================
    double dblInValue = test1;
    int intRoundPoint = test2;
    String strRoundClass = test3;
    BigDecimal bigDecimal = commonGetSystemCom.getNumberRound(dblInValue,
        intRoundPoint, strRoundClass);
    System.out.println("数値丸め結果：" + bigDecimal);



    // 自社伝票No採番=================================
    String strDenKb = "01";
    Long lngGetCnt = Long.valueOf(1);
    List<OwnSlipNumberingData> lstOwnSlipNumberingData = commonGetSystemCom.ownSlipNumbering(strDenKb, lngGetCnt);

    for (OwnSlipNumberingData ownSlipNumberingData : lstOwnSlipNumberingData) {
      System.out.println("自社伝票No：" + ownSlipNumberingData.getOwnSlipNumber());
    }


    // 便区分取得======================================
    String strBinKb = test1;
    String result = commonGetSystemCom.getBinKb(strBinKb);
    System.out.println("便区分：" + result);


    //金額上限エラーチェック===========================
    double dblDeliPrice = test1;
    DeliPriceMaxData deliPriceMaxData = commonGetSystemCom.getDeliPriceMax(dblDeliPrice);
    System.out.println("金額上限：" + deliPriceMaxData.getMaxDeliPrice());
    System.out.println("メッセージコード：" + deliPriceMaxData.getMsgCode());


    //HULFT起動========================================
    String strHulftId = test1;
    String strHulftResult = commonGetSystemCom.startUpHulft(strHulftId);
    System.out.println("HULFT戻り値：" + strHulftResult);


    // 伝票No採番======================================
    String strCustCode = test1;
    String strShopCode = test2;
    int intSlipCnt = test3;
    String strNumberingKb = test4;
    List<SlipNumberingData> lstSlipNumberingData = new ArrayList<SlipNumberingData>();
    lstSlipNumberingData = commonGetSystemCom.slipNumbering(strCustCode, strShopCode, intSlipCnt, strNumberingKb);

    for (SlipNumberingData slipNumberingData : lstSlipNumberingData) {
      System.out.println("伝票No：" + slipNumberingData.getSlipNumber());
      System.out.println("メッセージコード：" + slipNumberingData.getMsgCd());
      System.out.println("発生マスタ区分：" + slipNumberingData.getGeneMstKb());
    }


    // 業務日付更新===================================
    commonGetSystemCom.updateAplDate();



    // 消費税率取得===================================
    String strDeliDate = test1;
    TaxRateData taxRateData = new TaxRateData();
    taxRateData = commonGetSystemCom.getTaxRate(strDeliDate);
    System.out.println("消費税率："+taxRateData.getTaxRate());
    System.out.println("処理結果："+taxRateData.getProcResult());


    //商品分類取得====================================
    String strItemCode = test1; //
    ItemClassData itemClassData = commonGetSystemCom.getItemClass(strItemCode);
    System.out.println("商品分類："+itemClassData.getItemClass());
    System.out.println("処理結果："+itemClassData.getProcResult());



    //１明細あたり金額算出====================================
    UnitTaxPriceData unitTaxPriceData = commonGetSystemCom.getUnitTaxPrice(243.52, 8, "1");
    System.out.println(unitTaxPriceData.getUnitPriceTax());
    System.out.println(unitTaxPriceData.getWithoutTax());


    // 共通部品テスト------------------------------------<<




    // 出荷伝票出力テスト-------------------------------->>

    String strShipsTyp = test1;
    String strDenNo = test2;
    String strDenIdx = test3;
    String strJuhUrhFlg = test4;

    boolean testResult = juc0106b01Service.deliOutput(strShipsTyp, strDenNo, strDenIdx, strJuhUrhFlg);

    System.out.println("出荷伝票出力結果：" + testResult);

    // 出荷伝票出力テスト--------------------------------<<

*/

    // エラーリストテスト-------------------------------->>

    List<ErrorInfo> lstError = new ArrayList<ErrorInfo>();
    ErrorInfo errorInfo = new ErrorInfo();

    errorInfo.setDateTime("20151105 101457");
    errorInfo.setDataName("オンライン受注データ ");
    errorInfo.setOfficeCode("42");
    errorInfo.setCustomerCode("0019400");
    errorInfo.setDeadline("20151009");
    errorInfo.setFlight("2");
    errorInfo.setShopCode("014770");
    errorInfo.setShopName("ｻｰｸﾙKﾀｶﾔﾏｼｮｳﾜﾏﾁﾃﾝ");
    errorInfo.setItemCode("066251");
    errorInfo.setItemName("ｻﾞﾙｿﾊﾞ&ｲﾅﾘｾｯﾄ");
    errorInfo.setLineNumber("550");
    errorInfo.setErrorMessage("得意先マスタの取得に失敗しました。 実行SEQ：550、チェーンコード：9500、チェーン枝番：00、得意先コード：0019400");
    lstError.add(errorInfo);

    errorInfo.setDateTime("20151105 101457");
    errorInfo.setDataName("オンライン受注データ ");
    errorInfo.setOfficeCode("42");
    errorInfo.setCustomerCode("0019400");
    errorInfo.setDeadline("20151009");
    errorInfo.setFlight("2");
    errorInfo.setShopCode("014770");
    errorInfo.setShopName("ｻｰｸﾙKﾀｶﾔﾏｼｮｳﾜﾏﾁﾃﾝ");
    errorInfo.setItemCode("066251");
    errorInfo.setItemName("ｻﾞﾙｿﾊﾞ&ｲﾅﾘｾｯﾄ");
    errorInfo.setLineNumber("550");
    errorInfo.setErrorMessage("得意先マスタの取得に失敗しました。 実行SEQ：550、チェーンコード：9500、チェーン枝番：00、得意先コード：0019400");
    lstError.add(errorInfo);

    CommonGetSystemError commonGetSystemError = new CommonGetSystemError(commonDao, readPropertiesFileService, lstError);
    String result = commonGetSystemError.output();

    System.out.println(result);

    // エラーリストテスト--------------------------------<<



    // 検索結果返却
    return jsonData;
  }

}
