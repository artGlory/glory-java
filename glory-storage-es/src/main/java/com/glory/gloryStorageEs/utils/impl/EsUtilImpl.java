package com.glory.gloryStorageEs.utils.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.*;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.indices.*;
import co.elastic.clients.elasticsearch.indices.put_index_template.IndexTemplateMapping;
import co.elastic.clients.json.JsonData;
import com.glory.gloryStorageEs.enums.*;
import com.glory.gloryStorageEs.utils.EsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("esUtil")
@Slf4j
public class EsUtilImpl implements EsUtil {

    @Value("${elasticsearch.index.number_of_shards}")
    private String number_of_shards;
    @Value("${elasticsearch.index.number_of_replicas}")
    private String number_of_replicas;

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Override
    public Boolean createIndexTemplate(String templateName, List<String> indexPatternList, Map<String, EsFieldType> fieldTypeMap) {
        try {
            Map<String, Property> map = new HashMap<>();
            for (String key : fieldTypeMap.keySet()) {
                map.put(key, new Property(fieldTypeMap.get(key).getType()));
            }
            TypeMapping typeMapping = new TypeMapping.Builder()
                    .properties(map)
                    .build();
            PutIndexTemplateRequest putIndexTemplateRequest = new PutIndexTemplateRequest.Builder()
                    .template(new IndexTemplateMapping.Builder()
                            .settings(indexSettings())
                            .mappings(typeMapping)
                            .build())
                    .name(templateName)
                    .indexPatterns(indexPatternList)
                    .build();
            PutIndexTemplateResponse putIndexTemplateResponse = elasticsearchClient.indices().putIndexTemplate(putIndexTemplateRequest);
            boolean result = putIndexTemplateResponse.acknowledged();
            return result;

        } catch (ElasticsearchException e) {
            if (e.getMessage().contains("resource_already_exists_exception")) {
                return true;
            } else {
                log.warn("创建索引模板[{}]异常{}", templateName, fieldTypeMap, e);
            }
        } catch (Exception e) {
            log.warn("创建索引模板[{}]异常{}", templateName, fieldTypeMap, e);
        }
        return false;
    }

    @Override
    public List<String> listIndexTemplateName(String templateRegularName) {
        List<String> templateNameList = new ArrayList<>();
        try {
            GetIndexTemplateResponse getIndexTemplateResponse = elasticsearchClient.indices().getIndexTemplate(new GetIndexTemplateRequest.Builder()
                    .name(templateRegularName)
                    .build());
            getIndexTemplateResponse.indexTemplates().forEach(indexTemplateItem -> {
                templateNameList.add(indexTemplateItem.name());
            });
        } catch (Exception e) {

            log.warn("查询索引模板名称失败，{}", templateRegularName, e);
        }
        return templateNameList;
    }

    public Boolean createIndex(String _index, Map<String, EsFieldType> fieldTypeMap) {
        try {
            Map<String, Property> map = new HashMap<>();
            for (String key : fieldTypeMap.keySet()) {
                map.put(key, new Property(fieldTypeMap.get(key).getType()));
            }
            TypeMapping typeMapping = new TypeMapping.Builder()
                    .properties(map)
                    .build();
            CreateIndexRequest createIndexRequest = new CreateIndexRequest.Builder()
                    .index(_index)
                    .settings(indexSettings())
                    .mappings(typeMapping)
                    .build();
            CreateIndexResponse createIndexResponse = elasticsearchClient.indices().create(createIndexRequest);
            boolean result = createIndexResponse.acknowledged();
            return result;

        } catch (ElasticsearchException e) {
            if (e.getMessage().contains("resource_already_exists_exception")) {
                return true;
            } else {
                log.warn("创建索引[{}]异常{}", _index, fieldTypeMap, e);
            }
        } catch (Exception e) {
            log.warn("创建索引[{}]异常{}", _index, fieldTypeMap, e);
        }
        return false;
    }

    @Override
    public Boolean deleteIndex(String _index) {
        boolean result = false;
        try {
            DeleteIndexResponse deleteIndexResponse = elasticsearchClient.indices()
                    .delete(new DeleteIndexRequest.Builder()
                            .index(_index)
                            .build());
            result = deleteIndexResponse.acknowledged();
        } catch (Exception e) {
            log.warn("删除索引[{}]异常", _index, e);
        }
        return result;
    }

    @Override
    public List<String> listIndexName(String regularStr) {
        List<String> indexNameList = new ArrayList<>();
        try {
            GetIndexResponse getIndexResponse = elasticsearchClient.indices().get(f -> {
                return f.index(regularStr);
            });
            getIndexResponse.result().forEach((s, indexState) -> {
                indexNameList.add(s);
            });
        } catch (Exception e) {
            log.info("查询失败，根据名称获取索引。索引名称{}", regularStr);
        }
        return indexNameList;
    }

    public <T> Boolean addDoc(String _index, String _id, T data) {
        Boolean result = false;
        String unid;
        if (_id != null && !"".equals(_id)) {
            unid = _id;
        } else {
            unid = UUID.randomUUID().toString();
        }
        try {
            CreateRequest createRequest = new CreateRequest.Builder<T>()
                    .index(_index)
                    .id(unid)
                    .document(data)
                    .build();
            CreateResponse createResponse = elasticsearchClient.create(createRequest);
            return createResponse.result().equals(Result.Created);
        } catch (Exception e) {
            log.error("添加文档到索引[{}]主键[{}]异常{}", _index, _id, data, e);
            result = false;
        }
        return result;
    }

    @Override
    public <T> Boolean updateDoc(String _index, String _id, T data) {
        Boolean result = false;
        try {
            result = delDoc(_index, _id) && addDoc(_index, _id, data);
        } catch (Exception e) {
            log.error("更新文档到索引[{}]主键[{}]文档[{}]异常{}", _index, _id, data, e);
            result = false;
        }
        return result;
    }

    @Override
    public <T> Boolean updatePartialDoc(String _index, String _id, T data) {
        boolean result = false;
        try {
            UpdateResponse<Object> updateResponse = elasticsearchClient.update(new UpdateRequest.Builder<Object, T>()
                            .index(_index)
                            .id(_id)
                            .doc(data)
                            .build(),
                    Object.class);
            result = updateResponse.result().equals(Result.Updated);
        } catch (Exception e) {
            log.error("更新文档到索引[{}]主键[{}]文档[{}]异常{}", _index, _id, data, e);
            result = false;
        }
        return result;
    }

    @Override
    public Boolean delDoc(String _index, String _id) {
        boolean result = false;
        try {
            DeleteResponse deleteResponse = elasticsearchClient.delete(new DeleteRequest.Builder()
                    .index(_index)
                    .id(_id)
                    .build());
            result = deleteResponse.result().equals(Result.Deleted);
        } catch (Exception e) {
            if (e.getMessage().contains("index_not_found_exception"))
                return true;
            log.error("删除文档失败，索引[{}]主键[{}]异常{}", _index, _id, e);
        }
        return result;
    }

    @Override
    public <T> List<T> queryByCondition(String _index, Class<T> clazz
            , List<EsSearchField> esSearchFieldList, List<EsSearchFieldRange> esSearchFieldRangeList
            , EsSearchPage esSearchPage, List<EsSearchOrder> esSearchOrderList) {
        List<T> resultList = new ArrayList<>();
        try {
            List<Query> queryList = new ArrayList<>();
//精确查询
            if (esSearchFieldList != null) {
                esSearchFieldList.forEach(esSearchField -> {
                    switch (esSearchField.getSearchType()) {
                        case 1:
                            queryList.add(new Query.Builder().term(generateTermQuery(esSearchField)).build());
                            break;
                        case 2:
                            queryList.add(new Query.Builder().match(generateMatchQuery(esSearchField)).build());
                            break;
                        case 3:
                            queryList.add(new Query.Builder().matchPhrase(generateMatchPhraseQuery(esSearchField)).build());
                            break;
                    }
                });
            }
//范围查询
            if (esSearchFieldRangeList != null) {
                esSearchFieldRangeList.forEach(esSearchFieldRange -> {
                    if (esSearchFieldRange.getMaxValue() == null) {
                        esSearchFieldRange.setMaxValue(Long.MAX_VALUE);
                    }
                    if (esSearchFieldRange.getMinValue() == null) {
                        esSearchFieldRange.setMinValue(Long.MIN_VALUE);
                    }
                    queryList.add(new Query.Builder()
                            .range(new RangeQuery.Builder()
                                    .field(esSearchFieldRange.getField())
                                    .gte(JsonData.of(esSearchFieldRange.getMinValue()))
                                    .lt(JsonData.of(esSearchFieldRange.getMaxValue()))
                                    .build())
                            .build());
                });
            }
            //排序
            List<SortOptions> list = new ArrayList<>();
            if (esSearchOrderList != null) {
                esSearchOrderList.forEach(esSearchOrder -> {
                    list.add(new SortOptions.Builder()
                            .field(new FieldSort.Builder()
                                    .field(esSearchOrder.getField())
                                    .order(EsSearchOrder.order_by_type__desc.equals(esSearchOrder.getField()) ? SortOrder.Desc : SortOrder.Asc)
                                    .build())
                            .build());
                });
            }

            BoolQuery boolQuery = new BoolQuery.Builder()
                    .must(queryList)
                    .build();
            Query query = new Query.Builder()
                    .bool(boolQuery)
                    .build();
            SearchRequest searchRequest = new SearchRequest.Builder()
                    .index(_index)
                    .query(query)
                    .from(esSearchPage == null ? null : esSearchPage.getEsPageFrom())
                    .size(esSearchPage == null ? null : esSearchPage.getEsPageSize())
                    .sort(list)
                    .build();
            SearchResponse<T> searchResponse = elasticsearchClient.search(searchRequest, clazz);
            searchResponse.hits().hits().forEach(tHit -> {
                resultList.add(tHit.source());
            });
        } catch (Exception e) {
            log.error("查询失败，索引{}，精度查询条件{}，范围查询条件{},分页{}，异常{}", _index, esSearchFieldList, esSearchFieldRangeList, esSearchPage, e);
        }
        return resultList;
    }

    @Override
    public Long countByCondition(String _index, List<EsSearchField> esSearchFieldList, List<EsSearchFieldRange> esSearchFieldRangeList) {
        Long result = 0L;
        try {
            List<Query> queryList = new ArrayList<>();
//精确查询
            if (esSearchFieldList != null) {
                esSearchFieldList.forEach(esSearchField -> {
                    switch (esSearchField.getSearchType()) {
                        case 1:
                            queryList.add(new Query.Builder().term(generateTermQuery(esSearchField)).build());
                            break;
                        case 2:
                            queryList.add(new Query.Builder().match(generateMatchQuery(esSearchField)).build());
                            break;
                        case 3:
                            queryList.add(new Query.Builder().matchPhrase(generateMatchPhraseQuery(esSearchField)).build());
                            break;
                    }
                });
            }
//范围查询
            if (esSearchFieldRangeList != null) {
                esSearchFieldRangeList.forEach(esSearchFieldRange -> {
                    if (esSearchFieldRange.getMaxValue() == null) {
                        esSearchFieldRange.setMaxValue(Long.MAX_VALUE);
                    }
                    if (esSearchFieldRange.getMinValue() == null) {
                        esSearchFieldRange.setMinValue(Long.MIN_VALUE);
                    }
                    queryList.add(new Query.Builder()
                            .range(new RangeQuery.Builder()
                                    .field(esSearchFieldRange.getField())
                                    .gte(JsonData.of(esSearchFieldRange.getMinValue()))
                                    .lt(JsonData.of(esSearchFieldRange.getMaxValue()))
                                    .build())
                            .build());
                });
            }

            BoolQuery boolQuery = new BoolQuery.Builder()
                    .must(queryList)
                    .build();
            Query query = new Query.Builder()
                    .bool(boolQuery)
                    .build();
            CountRequest countRequest = new CountRequest.Builder()
                    .index(_index)
                    .query(query)
                    .build();
            CountResponse countResponse = elasticsearchClient.count(countRequest);
            result = countResponse.count();
        } catch (Exception e) {
            log.error("查询失败，索引{}，精度查询条件{}，范围查询条件{}，异常{}", _index, esSearchFieldList, esSearchFieldRangeList, e);
        }
        return result;
    }

    @Override
    public <T> List<Hit<T>> queryHitByCondition(String _index, Class<T> clazz, List<EsSearchField> esSearchFieldList, List<EsSearchFieldRange> esSearchFieldRangeList) {
        try {
            List<Query> queryList = new ArrayList<>();
//精确查询
            if (esSearchFieldList != null) {
                esSearchFieldList.forEach(esSearchField -> {
                    switch (esSearchField.getSearchType()) {
                        case 1:
                            queryList.add(new Query.Builder().term(generateTermQuery(esSearchField)).build());
                            break;
                        case 2:
                            queryList.add(new Query.Builder().match(generateMatchQuery(esSearchField)).build());
                            break;
                        case 3:
                            queryList.add(new Query.Builder().matchPhrase(generateMatchPhraseQuery(esSearchField)).build());
                            break;
                    }
                });
            }
//范围查询
            if (esSearchFieldRangeList != null) {
                esSearchFieldRangeList.forEach(esSearchFieldRange -> {
                    if (esSearchFieldRange.getMaxValue() == null) {
                        esSearchFieldRange.setMaxValue(Long.MAX_VALUE);
                    }
                    if (esSearchFieldRange.getMinValue() == null) {
                        esSearchFieldRange.setMinValue(Long.MIN_VALUE);
                    }
                    queryList.add(new Query.Builder()
                            .range(new RangeQuery.Builder()
                                    .field(esSearchFieldRange.getField())
                                    .gte(JsonData.of(esSearchFieldRange.getMinValue()))
                                    .lt(JsonData.of(esSearchFieldRange.getMaxValue()))
                                    .build())
                            .build());
                });
            }

            BoolQuery boolQuery = new BoolQuery.Builder()
                    .must(queryList)
                    .build();
            Query query = new Query.Builder()
                    .bool(boolQuery)
                    .build();
            SearchRequest searchRequest = new SearchRequest.Builder()
                    .index(_index)
                    .query(query)
                    .build();
            SearchResponse<T> searchResponse = elasticsearchClient.search(searchRequest, clazz);
            return searchResponse.hits().hits();
        } catch (Exception e) {
            log.error("查询失败，索引{}，精度查询条件{}，范围查询条件{},分页{}，异常{}", _index, esSearchFieldList, esSearchFieldRangeList, e);
        }
        return null;
    }

    /**
     * @return co.elastic.clients.elasticsearch.indices.IndexSettings
     * @Description 默认索引设置
     * @Param []
     * @Author hyy
     * @Date 2021-12-31 14:59
     **/
    private IndexSettings indexSettings() {
        IndexSettings indexSettings = new IndexSettings.Builder()
                .numberOfShards(number_of_shards)//索引应具有的主分片数
                .numberOfReplicas(number_of_replicas)//每个主分片具有的副本数
                .build();
        return indexSettings;
    }


    /**
     * 如果为text; 不分词，不分析
     *
     * @param esSearchField
     * @return
     */
    private TermQuery generateTermQuery(EsSearchField esSearchField) {
        TermQuery termQuery = new TermQuery.Builder()
                .field(esSearchField.getField())
                .value(generateQueryFieldValue(esSearchField))
                .build();
        return termQuery;
    }

    /**
     * 如果为text;分词，分析
     *
     * @param esSearchField
     * @return
     */
    private MatchQuery generateMatchQuery(EsSearchField esSearchField) {
        MatchQuery matchQuery = new MatchQuery.Builder()
                .field(esSearchField.getField())
                .query(generateQueryFieldValue(esSearchField))
                .build();
        return matchQuery;
    }

    /**
     * 如果为text;不分词，包含查询字符串（被查询字符串 是 查询结果 的 子字符串）
     *
     * @param esSearchField
     * @return
     */
    private MatchPhraseQuery generateMatchPhraseQuery(EsSearchField esSearchField) {
        MatchPhraseQuery matchPhraseQuery = new MatchPhraseQuery.Builder()
                .field(esSearchField.getField())
                .query((String) esSearchField.getValue())
                .build();
        return matchPhraseQuery;
    }

    /**
     * 查询条件的值
     *
     * @param esSearchField
     * @return
     */
    private FieldValue generateQueryFieldValue(EsSearchField esSearchField) {
        FieldValue fieldValue;
        switch (esSearchField.getEsFieldType()) {
            case BOOLEAN:
                fieldValue = new FieldValue.Builder().booleanValue((Boolean) esSearchField.getValue()).build();
                break;
            case KEYWORD:
            case TEXT:
                fieldValue = new FieldValue.Builder().stringValue((String) esSearchField.getValue()).build();
                break;
            case INTEGER:
            case LONG:
                fieldValue = new FieldValue.Builder().longValue((Long) esSearchField.getValue()).build();
                break;
            case FLOAT:
            case DOUBLE:
            case DATE:
                fieldValue = new FieldValue.Builder().doubleValue((Double) esSearchField.getValue()).build();
                break;
            case BINARY:
            case OBJECT:
            default:
                fieldValue = new FieldValue.Builder().nullValue().build();
                break;
        }
        return fieldValue;
    }
}
