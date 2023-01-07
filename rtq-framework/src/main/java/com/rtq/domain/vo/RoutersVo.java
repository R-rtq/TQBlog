package com.rtq.domain.vo;

import com.rtq.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author rtq
 * @Date 2023/1/5
 **/
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class RoutersVo {

    private List<Menu> menus;

}
