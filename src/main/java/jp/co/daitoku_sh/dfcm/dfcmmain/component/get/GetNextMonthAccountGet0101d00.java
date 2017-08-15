/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:GetNextMonthAccountGet0101d00.java
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

package jp.co.daitoku_sh.dfcm.dfcmmain.component.get;

/**
 * 次月取得のクラス
 * 
 * @author HiepTruong
 * @version 1.0.0
 * @since 1.0.0
 */
public class GetNextMonthAccountGet0101d00 {

  // 売掛・未収ヘッダー情報.事業所コード
  private String jigyocode;
  // 売掛・未収ヘッダー情報.チェーンコード
  private String chaincode;
  // 売掛・未収ヘッダー情報.チェーン枝番
  private String chainidx;
  // 売掛・未収ヘッダー情報.得意先コード
  private String custcode;
  // 売掛・未収ヘッダー情報.店舗コード
  private String shopcode;
  // 売掛・未収ヘッダー情報.現売掛金残高
  private String cururikakettl;
  // 売掛・未収ヘッダー情報.現売掛金消費税残高
  private String cururikaketax;
  // 売掛・未収ヘッダー情報.現未収金残高
  private String curmishuttl;
  // 売掛・未収ヘッダー情報.現未収金消費税残高
  private String curmishutax;

  public String getJigyocode() {
    return jigyocode;
  }

  public void setJigyocode(String jigyocode) {
    this.jigyocode = jigyocode;
  }

  public String getChaincode() {
    return chaincode;
  }

  public void setChaincode(String chaincode) {
    this.chaincode = chaincode;
  }

  public String getChainidx() {
    return chainidx;
  }

  public void setChainidx(String chainidx) {
    this.chainidx = chainidx;
  }

  public String getCustcode() {
    return custcode;
  }

  public void setCustcode(String custcode) {
    this.custcode = custcode;
  }

  public String getShopcode() {
    return shopcode;
  }

  public void setShopcode(String shopcode) {
    this.shopcode = shopcode;
  }

  public String getCururikakettl() {
    return cururikakettl;
  }

  public void setCururikakettl(String cururikakettl) {
    this.cururikakettl = cururikakettl;
  }

  public String getCururikaketax() {
    return cururikaketax;
  }

  public void setCururikaketax(String cururikaketax) {
    this.cururikaketax = cururikaketax;
  }

  public String getCurmishuttl() {
    return curmishuttl;
  }

  public void setCurmishuttl(String curmishuttl) {
    this.curmishuttl = curmishuttl;
  }

  public String getCurmishutax() {
    return curmishutax;
  }

  public void setCurmishutax(String curmishutax) {
    this.curmishutax = curmishutax;
  }

}