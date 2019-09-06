package com.mll.pojo;
import java.io.Serializable;
import java.util.*;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MLL_PRODUCT_CATEGORY implements Serializable {


    private Integer mpc_id;//分类编号

    private String mpc_name;//分类名字

    private int parent_id;//父id

    private List<MLL_PRODUCT_CATEGORY> childList;


}
