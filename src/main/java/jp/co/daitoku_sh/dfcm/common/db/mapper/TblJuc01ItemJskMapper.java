package jp.co.daitoku_sh.dfcm.common.db.mapper;

import java.util.List;
import jp.co.daitoku_sh.dfcm.common.db.model.TblJuc01ItemJsk;
import jp.co.daitoku_sh.dfcm.common.db.model.TblJuc01ItemJskExample;
import jp.co.daitoku_sh.dfcm.common.db.model.TblJuc01ItemJskKey;
import org.apache.ibatis.annotations.Param;

public interface TblJuc01ItemJskMapper {

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table TBL_JUC01_ITEM_JSK
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int countByExample(TblJuc01ItemJskExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table TBL_JUC01_ITEM_JSK
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int deleteByExample(TblJuc01ItemJskExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table TBL_JUC01_ITEM_JSK
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int deleteByPrimaryKey(TblJuc01ItemJskKey key);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table TBL_JUC01_ITEM_JSK
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int insert(TblJuc01ItemJsk record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table TBL_JUC01_ITEM_JSK
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int insertSelective(TblJuc01ItemJsk record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table TBL_JUC01_ITEM_JSK
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  List<TblJuc01ItemJsk> selectByExample(TblJuc01ItemJskExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table TBL_JUC01_ITEM_JSK
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  TblJuc01ItemJsk selectByPrimaryKey(TblJuc01ItemJskKey key);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table TBL_JUC01_ITEM_JSK
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int updateByExampleSelective(@Param("record") TblJuc01ItemJsk record,
      @Param("example") TblJuc01ItemJskExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table TBL_JUC01_ITEM_JSK
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int updateByExample(@Param("record") TblJuc01ItemJsk record,
      @Param("example") TblJuc01ItemJskExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table TBL_JUC01_ITEM_JSK
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int updateByPrimaryKeySelective(TblJuc01ItemJsk record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table TBL_JUC01_ITEM_JSK
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int updateByPrimaryKey(TblJuc01ItemJsk record);
}