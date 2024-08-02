package com.example.ex14_jdbc_board.DTO;

import lombok.Data;

@Data
public class SimpleBoardDTO {
  private int id;
  private String writer;
  private String title;
  private String content;
}
