package com.onlineshoppingsystem.dao;

import com.onlineshoppingsystem.dataobject.PromoDO;

public interface PromoDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promo_info
     *
     * @mbg.generated Mon Sep 21 19:10:20 CDT 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promo_info
     *
     * @mbg.generated Mon Sep 21 19:10:20 CDT 2020
     */
    int insert(PromoDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promo_info
     *
     * @mbg.generated Mon Sep 21 19:10:20 CDT 2020
     */
    int insertSelective(PromoDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promo_info
     *
     * @mbg.generated Mon Sep 21 19:10:20 CDT 2020
     */
    PromoDO selectByPrimaryKey(Integer id);
    
    PromoDO selectByProductId(Integer productId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promo_info
     *
     * @mbg.generated Mon Sep 21 19:10:20 CDT 2020
     */
    int updateByPrimaryKeySelective(PromoDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promo_info
     *
     * @mbg.generated Mon Sep 21 19:10:20 CDT 2020
     */
    int updateByPrimaryKey(PromoDO record);
}