package com.mola.molablogv2.repository;

import com.mola.molablogv2.pojo.BlogComment;
import com.mola.molablogv2.pojo.example.BlogCommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogCommentMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blog_comment
     *
     * @mbg.generated
     */
    long countByExample(BlogCommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blog_comment
     *
     * @mbg.generated
     */
    int deleteByExample(BlogCommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blog_comment
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blog_comment
     *
     * @mbg.generated
     */
    int insert(BlogComment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blog_comment
     *
     * @mbg.generated
     */
    int insertSelective(BlogComment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blog_comment
     *
     * @mbg.generated
     */
    List<BlogComment> selectByExample(BlogCommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blog_comment
     *
     * @mbg.generated
     */
    BlogComment selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blog_comment
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") BlogComment record, @Param("example") BlogCommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blog_comment
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") BlogComment record, @Param("example") BlogCommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blog_comment
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(BlogComment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blog_comment
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(BlogComment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blog_comment
     *
     * @mbg.generated
     */
    List<BlogComment> selectAll();
}