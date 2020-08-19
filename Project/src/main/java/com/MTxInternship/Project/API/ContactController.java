package com.MTxInternship.Project.API;
import com.MTxInternship.Project.EsUtil.ElasticContactUtil;
import com.MTxInternship.Project.Model.Account;
import com.MTxInternship.Project.Model.Address;
import com.MTxInternship.Project.Model.Contact;
import com.MTxInternship.Project.Service.AccountService;
import com.MTxInternship.Project.Service.ContactService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class ContactController {

    @Autowired
    ContactService contactService;
    @Autowired
    AccountService accountService;
    @Autowired
    ElasticContactUtil elasticContactUtil;
    @Autowired
    private RestHighLevelClient client;

    @PostConstruct
    void indexAccounts() throws IOException {
        CreateIndexRequest request1 = new CreateIndexRequest("contacts");
        request1.settings(Settings.builder()
                .put("index.number_of_shards", 1)
                .put("index.number_of_replicas", 2)
        );

        List<Contact> contacts = new ArrayList<>();
        Iterable<Contact> accountsAll = contactService.getContacts();
        for (Contact contact : accountsAll) {
            System.out.println(contact.getAccount().getName());
            elasticContactUtil.saveContact(contact);
        }
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @CrossOrigin(origins = "http://d2tz8yd01soxbm.cloudfront.net")
    @RequestMapping(method= RequestMethod.GET, value = "/PaginatedContacts/{n}")
    public List<Contact> getAllContacts(@PathVariable int n) throws IOException {
        return elasticContactUtil.readAllPaginated(n);
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @CrossOrigin(origins = "http://d2tz8yd01soxbm.cloudfront.net")
    @RequestMapping(method= RequestMethod.GET, value = "/Contacts/{id}")
    public List<Contact> getAccountContacts(@PathVariable UUID id){
        return contactService.getAccountContacts(id);
    }
    //@CrossOrigin(origins = "http://localhost:4200")
    @CrossOrigin(origins = "http://d2tz8yd01soxbm.cloudfront.net")
    @RequestMapping(method= RequestMethod.GET, value = "/viewContact/{id}")
    public Contact getContactByID(@PathVariable UUID id){
        return contactService.getContactByID(id);
    }
    //@CrossOrigin(origins = "http://localhost:4200")
    @CrossOrigin(origins = "http://d2tz8yd01soxbm.cloudfront.net")
    @RequestMapping(method= RequestMethod.POST, value = "/{uid}/addContact/{aid}")
    public void addContact (@RequestBody Contact u, @PathVariable UUID aid, @PathVariable UUID uid) throws IOException {
        u.setAccount(accountService.getAccountByID(aid));
        contactService.addContact(u);
        elasticContactUtil.saveContact(u);
    }
    //@CrossOrigin(origins = "http://localhost:4200")
    @CrossOrigin(origins = "http://d2tz8yd01soxbm.cloudfront.net")
    @RequestMapping(method= RequestMethod.PUT, value ="/{uid}/{aid}/editContact/{cid}")
    public void updateContact (@RequestBody Contact u, @PathVariable UUID aid, @PathVariable UUID uid, @PathVariable UUID cid) throws IOException {
        u.setAccount(accountService.getAccountByID(aid));
        contactService.updateContact(u, cid);
        elasticContactUtil.update(u);
    }
    @Transactional
    //@CrossOrigin(origins = "http://localhost:4200")
    @CrossOrigin(origins = "http://d2tz8yd01soxbm.cloudfront.net")
    @RequestMapping(method= RequestMethod.DELETE, value = "/deleteContact/{id}")
    public void deleteContact (@PathVariable UUID id) throws IOException {
        contactService.deleteContact(id);
        elasticContactUtil.delete(id.toString());
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @CrossOrigin(origins = "http://d2tz8yd01soxbm.cloudfront.net")
    @RequestMapping(method= RequestMethod.GET, value = "/contacts/{n}/name/{field}")
    public List<Contact> searchByName(@PathVariable int n, @PathVariable String field) throws IOException {
        List<Contact> contacts = new ArrayList<>();
        QueryBuilder matchQueryBuilder = QueryBuilders.multiMatchQuery(field).field("firstName",6).field("lastName", 5).field("functionalArea", 4)
                .fuzziness(Fuzziness.AUTO)
                .prefixLength(0)
                .maxExpansions(20);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(matchQueryBuilder);
        sourceBuilder.from(0);
        sourceBuilder.size(5);
        sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        for(SearchHit searchHit : searchResponse.getHits().getHits()){
            Contact contact = new ObjectMapper().readValue(searchHit.getSourceAsString(),Contact.class);
            contacts.add(contact);
        }
        if (contacts.isEmpty()) {
            contacts = elasticContactUtil.containsField(field);

        }

        return contacts;
    }

}
