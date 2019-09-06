package com.mll.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MLL_Receiving implements Serializable {
    private Integer mr_id;
    private Integer mu_user_id;
    private String mr_name;
    private String mr_mobile;
    private String mr_address;
    private String mr_detail_address;
    private String mr_postal_code;
    private Integer mr_default;
}
