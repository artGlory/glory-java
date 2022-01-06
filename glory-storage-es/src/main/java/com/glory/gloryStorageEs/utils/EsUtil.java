package com.glory.gloryStorageEs.utils;

import co.elastic.clients.elasticsearch.core.search.Hit;
import com.glory.gloryStorageEs.enums.*;

import java.util.List;
import java.util.Map;

public interface EsUtil {


    /**
     * @param indexPatternList 匹配的索引模式
     * @return java.lang.Boolean
     * @Description 创建索引模板
     * @Param [templateName, indexPatternList, fieldTypeMap]
     * @Author hyy
     * @Date 2022-01-06 15:10
     **/
    Boolean createIndexTemplate(String templateName, List<String> indexPatternList, Map<String, EsFieldType> fieldTypeMap);

    /**
     * @return java.util.List<java.lang.String>
     * @Description 找出符合名称的模板名称
     * @Param [templateSumStr] 可以使用通配符    包含 *templateSumStr* ； 开头 templateSumStr*； 结尾 *templateSumStr
     * @Author hyy
     * @Date 2022-01-06 15:07
     **/
    List<String> listIndexTemplateName(String templateRegularName);

    /**
     * @return java.lang.Boolean
     * @Description 创建索引
     * @Param [indexName, fieldTypeMap]
     * @Author hyy
     * @Date 2021-12-31 17:10
     **/
    Boolean createIndex(String _index, Map<String, EsFieldType> fieldTypeMap);

    /**
     * @return java.lang.Boolean
     * @Description 删除
     * @Param [_index]
     * @Author hyy
     * @Date 2022-01-06 9:46
     **/
    Boolean deleteIndex(String _index);

    /**
     * @return java.util.List<java.lang.String>
     * @Description 匹配所有索引名称；支持通配符*
     * @Param [regularStr]  * 查询所有；
     * @Author hyy
     * @Date 2022-01-06 10:06
     **/
    List<String> listIndexName(String regularStr);

    /**
     * @return java.lang.Boolean
     * @Description 添加文档到索引
     * @Param [indexName, data]
     * @Author hyy
     * @Date 2021-12-31 17:31
     **/
    <T> Boolean addDoc(String _index, String _id, T data);

    /**
     * @return java.lang.Boolean
     * @Description 删除后，再新增；；全面更新
     * @Param [_index, _id, data]
     * @Author hyy
     * @Date 2022-01-06 17:22
     **/
    <T> Boolean updateDoc(String _index, String _id, T data);

    /**
     * @return java.lang.Boolean
     * @Description 部分更新，有赋值的地方更新
     * @Param [_index, _id, data]
     * @Author hyy
     * @Date 2022-01-06 17:23
     **/
    <T> Boolean updatePartialDoc(String _index, String _id, T data);

    /**
     * @return java.lang.Boolean
     * @Description 根据索引和主键删除
     * @Param [_index, _id]
     * @Author hyy
     * @Date 2022-01-06 15:49
     **/
    Boolean delDoc(String _index, String _id);

    /**
     * @return java.util.List<T>
     * @Description 条件查询
     * @Param [_index, clazz, esSearchFieldList, esSearchFieldRangeList, esSearchPage, esSearchOrderList]
     * @Author hyy
     * @Date 2022-01-06 9:29
     **/
    <T> List<T> queryByCondition(String _index, Class<T> clazz
            , List<EsSearchField> esSearchFieldList, List<EsSearchFieldRange> esSearchFieldRangeList
            , EsSearchPage esSearchPage, List<EsSearchOrder> esSearchOrderList);

    /**
     * @return java.lang.Long
     * @Description count 条件查询
     * @Param [_index, esSearchFieldList, esSearchFieldRangeList]
     * @Author hyy
     * @Date 2022-01-06 9:38
     **/
    Long countByCondition(String _index, List<EsSearchField> esSearchFieldList, List<EsSearchFieldRange> esSearchFieldRangeList);

    /**
     * @return java.util.List<co.elastic.clients.elasticsearch.core.search.Hit < T>>
     * @Description 条件查询  返回结果中有 _id
     * @Param [_index, clazz, esSearchFieldList, esSearchFieldRangeList]
     * @Author hyy
     * @Date 2022-01-06 15:40
     **/
    <T> List<Hit<T>> queryHitByCondition(String _index, Class<T> clazz
            , List<EsSearchField> esSearchFieldList, List<EsSearchFieldRange> esSearchFieldRangeList);

}
