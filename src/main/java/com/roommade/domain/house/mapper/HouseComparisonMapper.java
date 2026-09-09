package com.roommade.domain.house.mapper;

import com.roommade.domain.house.dto.request.HouseRegisterRequest;
import com.roommade.domain.house.dto.response.HouseComparisonCurrentResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HouseComparisonMapper {

    HouseComparisonCurrentResponse findCurrentByUserId(@Param("userId") Long userId);

    void insertComparison(@Param("userId") Long userId);

    void insertHouse(
            @Param("comparisonId") Long comparisonId,
            @Param("houseType") String houseType,
            @Param("request") HouseRegisterRequest request);

    /** 사용자의 집 비교 최초 등록 완료 시각을 한 번만 기록한다(자립 준비도 집 비교 점수). */
    void markHouseComparisonCompleted(@Param("userId") Long userId);
}
