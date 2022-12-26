package com.rtq.utils;

import com.rtq.domain.entity.Article;
import com.rtq.domain.vo.HotArticleVo;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rtq
 * @Date 2022/12/24
 **/
public class BeanCopyUtils {
    private BeanCopyUtils(){

    }
    public static <V> V copyBean(Object source,Class<V> clazz){
        V result=null;
        try {
            //创建目标对象
             result = clazz.newInstance();
            //实现目标拷贝
            BeanUtils.copyProperties(source,result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回结果
        return result;
    }
    public static <O,V> List<V> copyBeanList(List<O> list, Class<V> clazz){
        return list.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }


    public static void main(String[] args) {
        Article article = new Article();
        article.setContent("你好");
        article.setId(1L);
        article.setTitle("999");
        HotArticleVo hotArticleVo = copyBean(article, HotArticleVo.class);
        System.out.println(hotArticleVo);
    }

}
