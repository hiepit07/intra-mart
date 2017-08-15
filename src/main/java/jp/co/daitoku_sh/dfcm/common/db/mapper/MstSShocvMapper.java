package jp.co.daitoku_sh.dfcm.common.db.mapper;

import java.util.List;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSShocv;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSShocvExample;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSShocvKey;
import org.apache.ibatis.annotations.Param;

public interface MstSShocvMapper {

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table MST_S_SHOCV
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int countByExample(MstSShocvExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table MST_S_SHOCV
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int deleteByExample(MstSShocvExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table MST_S_SHOCV
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int deleteByPrimaryKey(MstSShocvKey key);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table MST_S_SHOCV
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int insert(MstSShocv record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table MST_S_SHOCV
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int insertSelective(MstSShocv record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table MST_S_SHOCV
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  List<MstSShocv> selectByExample(MstSShocvExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table MST_S_SHOCV
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  MstSShocv selectByPrimaryKey(MstSShocvKey key);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table MST_S_SHOCV
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int updateByExampleSelective(@Param("record") MstSShocv record,
      @Param("example") MstSShocvExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table MST_S_SHOCV
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int updateByExample(@Param("record") MstSShocv record,
      @Param("example") MstSShocvExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table MST_S_SHOCV
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int updateByPrimaryKeySelective(MstSShocv record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table MST_S_SHOCV
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int updateByPrimaryKey(MstSShocv record);
}