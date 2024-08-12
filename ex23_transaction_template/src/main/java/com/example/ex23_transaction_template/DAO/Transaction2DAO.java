package com.example.ex23_transaction_template.DAO;

import org.apache.ibatis.annotations.Mapper;

@Mapper // mybatis 방식으로 작성할거기 때문에 xml을 참조하기위해 Mapper 어노테이션 사용
public interface Transaction2DAO {
  public void pay(String consumerId, int amount);
}