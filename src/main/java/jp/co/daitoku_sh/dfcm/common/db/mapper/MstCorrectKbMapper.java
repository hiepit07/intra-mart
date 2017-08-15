package jp.co.daitoku_sh.dfcm.common.db.mapper;

import java.util.List;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCorrectKb;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCorrectKbExample;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCorrectKbKey;
import org.apache.ibatis.annotations.Param;

public interface MstCorrectKbMapper {

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table MST_CORRECT_KB
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int countByExample(MstCorrectKbExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table MST_CORRECT_KB
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int deleteByExample(MstCorrectKbExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table MST_CORRECT_KB
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int deleteByPrimaryKey(MstCorrectKbKey key);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table MST_CORRECT_KB
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int insert(MstCorrectKb record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table MST_CORRECT_KB
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int insertSelective(MstCorrectKb record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table MST_CORRECT_KB
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  List<MstCorrectKb> selectByExample(MstCorrectKbExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table MST_CORRECT_KB
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  MstCorrectKb selectByPrimaryKey(MstCorrectKbKey key);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table MST_CORRECT_KB
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int updateByExampleSelective(@Param("record") MstCorrectKb record,
      @Param("example") MstCorrectKbExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table MST_CORRECT_KB
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int updateByExample(@Param("record") MstCorrectKb record,
      @Param("example") MstCorrectKbExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table MST_CORRECT_KB
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int updateByPrimaryKeySelective(MstCorrectKb record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table MST_CORRECT_KB
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int updateByPrimaryKey(MstCorrectKb record);
}