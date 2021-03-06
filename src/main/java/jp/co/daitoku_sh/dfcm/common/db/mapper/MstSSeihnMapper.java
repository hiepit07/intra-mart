package jp.co.daitoku_sh.dfcm.common.db.mapper;

import java.util.List;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSSeihn;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSSeihnExample;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSSeihnKey;
import org.apache.ibatis.annotations.Param;

public interface MstSSeihnMapper {

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table MST_S_SEIHN
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int countByExample(MstSSeihnExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table MST_S_SEIHN
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int deleteByExample(MstSSeihnExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table MST_S_SEIHN
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int deleteByPrimaryKey(MstSSeihnKey key);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table MST_S_SEIHN
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int insert(MstSSeihn record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table MST_S_SEIHN
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int insertSelective(MstSSeihn record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table MST_S_SEIHN
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  List<MstSSeihn> selectByExample(MstSSeihnExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table MST_S_SEIHN
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  MstSSeihn selectByPrimaryKey(MstSSeihnKey key);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table MST_S_SEIHN
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int updateByExampleSelective(@Param("record") MstSSeihn record,
      @Param("example") MstSSeihnExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table MST_S_SEIHN
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int updateByExample(@Param("record") MstSSeihn record,
      @Param("example") MstSSeihnExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table MST_S_SEIHN
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int updateByPrimaryKeySelective(MstSSeihn record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table MST_S_SEIHN
   * @mbggenerated  Sat Oct 24 16:19:36 ICT 2015
   */
  int updateByPrimaryKey(MstSSeihn record);
}