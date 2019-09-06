package com.mll.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class Message implements Serializable {
    private boolean flag;
    private String mess;
    private List<?> lists;
    private List<?> lists2;
}
