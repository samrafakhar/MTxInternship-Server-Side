package com.MTxInternship.Project.EsUtil;

import com.MTxInternship.Project.Model.Contact;
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
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController()
public class ElasticContactUtil {
    @Autowired
    private RestHighLevelClient client;

    public String saveContact(Contact contact) throws IOException {
        IndexRequest request = new IndexRequest("contacts");
        request.id(contact.getContactID().toString());
        request.source(new ObjectMapper().writeValueAsString(contact), XContentType.JSON);
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
        //System.out.println("response id: "+indexResponse.getId());
        return indexResponse.getResult().name();
    }

    public String update(Contact contact) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest("contacts", contact.getContactID().toString());
        updateRequest.doc(new ObjectMapper().writeValueAsString(contact), XContentType.JSON);
        UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
        //System.out.println(updateResponse.getGetResult());

        return updateResponse.status().name();
    }

    public String delete(@PathVariable final String id) throws IOException {
        DeleteRequest request = new DeleteRequest("contacts",id);
        DeleteResponse deleteResponse = client.delete(request,RequestOptions.DEFAULT);
        return deleteResponse.getResult().name();
    }

    public List<Contact> readAllPaginated(int n) throws IOException {
        List<Contact> contacts = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest("contacts");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        FieldSortBuilder fieldSortBuilder = SortBuilders.fieldSort("firstName"+".keyword");
        fieldSortBuilder.order(SortOrder.ASC);
        searchSourceBuilder.sort(fieldSortBuilder);

        searchSourceBuilder.from(n);
        searchSourceBuilder.size(15);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        for(SearchHit searchHit : searchResponse.getHits().getHits()){
            Contact contact = new ObjectMapper().readValue(searchHit.getSourceAsString(),Contact.class);
            contacts.add(contact);
        }
        return contacts;
    }

    public List<Contact> readAll() throws IOException {
        List<Contact> contacts = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest("contacts");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
        searchSourceBuilder.size(50);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        for(SearchHit searchHit : searchResponse.getHits().getHits()){
            Contact contact = new ObjectMapper().readValue(searchHit.getSourceAsString(),Contact.class);
            contacts.add(contact);
        }
        return contacts;
    }





    public List<Contact> containsField(String s) throws IOException {
        List<Contact> contacts = new ArrayList<>();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        String str=s.concat("*");
        System.out.println(str);
        QueryBuilder qb = QueryBuilders.wildcardQuery("firstName", str);
        boolQueryBuilder.must(qb);
        searchSourceBuilder.query(boolQueryBuilder);

        SearchRequest searchRequest = new SearchRequest("contacts");
        searchRequest.source(searchSourceBuilder);
        searchSourceBuilder.size(50);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        for(SearchHit searchHit : searchResponse.getHits().getHits()){
            Contact contact = new ObjectMapper().readValue(searchHit.getSourceAsString(),Contact.class);
            System.out.println(contact.getFirstName());
            contacts.add(contact);
        }
        return contacts;
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
