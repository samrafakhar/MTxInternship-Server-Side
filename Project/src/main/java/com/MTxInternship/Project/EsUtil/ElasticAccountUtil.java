package com.MTxInternship.Project.EsUtil;

import com.MTxInternship.Project.Model.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController()
public class ElasticAccountUtil {
    @Autowired
    private RestHighLevelClient client;

    public String saveAccount(Account account) throws IOException {
        IndexRequest request = new IndexRequest("accounts");
        request.id(account.getAccountID().toString());
        request.source(new ObjectMapper().writeValueAsString(account), XContentType.JSON);
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
        //System.out.println("response id: "+indexResponse.getId());
        return indexResponse.getResult().name();
    }

    public String update(Account account) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest("accounts", account.getAccountID().toString());
        updateRequest.doc(new ObjectMapper().writeValueAsString(account), XContentType.JSON);
        UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
        //System.out.println(updateResponse.getGetResult());

        return updateResponse.status().name();
    }

    public String delete(@PathVariable final String id) throws IOException {
        DeleteRequest request = new DeleteRequest("accounts",id);
        DeleteResponse deleteResponse = client.delete(request,RequestOptions.DEFAULT);
        return deleteResponse.getResult().name();
    }

    public List<Account> readAllPaginated(int n) throws IOException {
        List<Account> accounts = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest("accounts");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        FieldSortBuilder fieldSortBuilder = SortBuilders.fieldSort("name"+".keyword");
        fieldSortBuilder.order(SortOrder.ASC);
        searchSourceBuilder.sort(fieldSortBuilder);

        searchSourceBuilder.from(n);
        searchSourceBuilder.size(14);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        for(SearchHit searchHit : searchResponse.getHits().getHits()){
            Account account = new ObjectMapper().readValue(searchHit.getSourceAsString(),Account.class);
            accounts.add(account);
        }
        return accounts;
    }

    public List<Account> readAll() throws IOException {
        List<Account> accounts = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest("accounts");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        //searchSourceBuilder.sort(new FieldSortBuilder("name").order(SortOrder.ASC));
        searchSourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        searchRequest.source(searchSourceBuilder);
        searchSourceBuilder.size(50);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        for(SearchHit searchHit : searchResponse.getHits().getHits()){
            Account account = new ObjectMapper().readValue(searchHit.getSourceAsString(),Account.class);
            accounts.add(account);
        }
        return accounts;
    }

    public List<Account> containsField(String s) throws IOException {
        List<Account> accounts = new ArrayList<>();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        String str=s.concat("*");
        System.out.println(str);
        QueryBuilder qb = QueryBuilders.wildcardQuery("name", str);
        boolQueryBuilder.must(qb);
        searchSourceBuilder.query(boolQueryBuilder);

        SearchRequest searchRequest = new SearchRequest("accounts");
        searchRequest.source(searchSourceBuilder);
        searchSourceBuilder.size(50);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        for(SearchHit searchHit : searchResponse.getHits().getHits()){
            Account account = new ObjectMapper().readValue(searchHit.getSourceAsString(),Account.class);
            accounts.add(account);
        }
        return accounts;
    }
    
    ActionListener listener = new ActionListener<IndexResponse>() {
        @Override
        public void onResponse(IndexResponse indexResponse) {
            System.out.println(" Document updated successfully !!!");
        }

        @Override
        public void onFailure(Exception e) {
            System.out.print(" Document creation failed !!!"+ e.getMessage());
        }
    };
}
