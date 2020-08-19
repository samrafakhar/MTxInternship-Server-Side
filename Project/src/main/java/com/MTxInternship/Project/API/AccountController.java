package com.MTxInternship.Project.API;

import com.MTxInternship.Project.EsUtil.ElasticAccountUtil;
import com.MTxInternship.Project.EsUtil.ElasticContactUtil;
import com.MTxInternship.Project.Model.Account;
import com.MTxInternship.Project.Model.Address;
import com.MTxInternship.Project.Model.Contact;
import com.MTxInternship.Project.Model.User;
import com.MTxInternship.Project.Service.AccountService;
import com.MTxInternship.Project.Service.ContactService;
import com.MTxInternship.Project.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
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
public class AccountController {

    @Autowired
    private RestHighLevelClient client;
    @Autowired
    AccountService accountService;
    @Autowired
    ContactService contactService;
    @Autowired
    ElasticAccountUtil elasticUtilAccount;
    @Autowired
    ElasticContactUtil elasticUtilContact;

    @PostConstruct
    void indexAccounts() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("accounts");
        request.settings(Settings.builder()
                .put("index.number_of_shards", 1)
                .put("index.number_of_replicas", 2)
        );

        List<Account> accounts = new ArrayList<>();
        Iterable<Account> accountsAll = accountService.getAccounts();
        for (Account account : accountsAll) {
            elasticUtilAccount.saveAccount(account);
        }
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @CrossOrigin(origins = "http://d2tz8yd01soxbm.cloudfront.net")
    @RequestMapping(method= RequestMethod.GET, value = "/viewAccount/{id}")
    public Account getAccountByID(@PathVariable UUID id){
        return accountService.getAccountByID(id);
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @CrossOrigin(origins = "http://d2tz8yd01soxbm.cloudfront.net")
    @RequestMapping(method= RequestMethod.GET, value = "/viewAccount/{id}/ProteinTypes")
    public String getAccountProteinTypesByAccountID(@PathVariable UUID id){
        Account a = accountService.getAccountByID(id);
        return a.getProteinType();
    }

    @Autowired
    UserService userService;
    //@CrossOrigin(origins = "http://localhost:4200")
    @CrossOrigin(origins = "http://d2tz8yd01soxbm.cloudfront.net")
    @RequestMapping(method= RequestMethod.GET, value = "/Accounts/{n}")
    public List<Account>findAllByOwner(@PathVariable int n) throws IOException {
        //User u=userService.getUserByID(id);
        return elasticUtilAccount.readAllPaginated(n);
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @CrossOrigin(origins = "http://d2tz8yd01soxbm.cloudfront.net")
    @RequestMapping(method= RequestMethod.GET, value = "/Accounts")
    public List<Account>findAll() throws IOException {
        return elasticUtilAccount.readAll();
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @CrossOrigin(origins = "http://d2tz8yd01soxbm.cloudfront.net")
    @RequestMapping(method= RequestMethod.POST, value = "/addAccount/{id}")
    public void addAccount (@RequestBody Account u, @PathVariable String id) throws IOException {
        UUID uid = UUID.fromString(id);
        u.setOwner(new User(uid));
        accountService.addAccount(u, uid);
        elasticUtilAccount.saveAccount(u);
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @CrossOrigin(origins = "http://d2tz8yd01soxbm.cloudfront.net")
    @RequestMapping(method= RequestMethod.POST, value = "/cloneAccount/{id}")
    public void cloneAccount (@RequestBody Account u, @PathVariable UUID id) throws IOException {
        String s = u.getType();

        Account clonedAccount=new Account();

        if(s.equals("vendor") || s.equals("Vendor"))
            clonedAccount.setType("Customer");
        if(s.equals("customer") || s.equals("Customer"))
            clonedAccount.setType("Vendor");

        clonedAccount.setAccountID(new UUID(8,8));
        clonedAccount.setOwner(new User(id));
        clonedAccount.setName(u.getName());
        clonedAccount.setStatus(u.getStatus());
        clonedAccount.setPhone(u.getPhone());
        clonedAccount.setFax(u.getFax());
        clonedAccount.setWebsite(u.getWebsite());
        clonedAccount.setShippingAddress(new Address(u.getShippingAddress()));
        clonedAccount.setBillingAddress(new Address(u.getBillingAddress()));
        clonedAccount.setBusinessType(u.getBusinessType());
        clonedAccount.setProductType(u.getProductType());
        clonedAccount.setProteinType(u.getProteinType());
        accountService.addAccount(clonedAccount, id);
        elasticUtilAccount.saveAccount(clonedAccount);
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @CrossOrigin(origins = "http://d2tz8yd01soxbm.cloudfront.net")
    @RequestMapping(method= RequestMethod.PUT, value ="/{uid}/editAccount/{id}")
    public void updateAccount (@RequestBody Account account, @PathVariable UUID uid) throws IOException {
        account.setOwner(new User(uid, "","","","","",null));
        accountService.updateAccount(account, uid);
        elasticUtilAccount.update(account);
        List<Contact> contacts= contactService.getAccountContacts(account.getAccountID());
        for(Contact c : contacts)
            elasticUtilContact.update(c);
    }

    @Transactional
    //@CrossOrigin(origins = "http://localhost:4200")
    @CrossOrigin(origins = "http://d2tz8yd01soxbm.cloudfront.net")
    @RequestMapping(method= RequestMethod.DELETE, value = "/deleteAccount/{id}")
    public void deleteAccount (@PathVariable UUID id) throws IOException {
        accountService.deleteAccount(id);
        elasticUtilAccount.delete(id.toString());
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @CrossOrigin(origins = "http://d2tz8yd01soxbm.cloudfront.net")
    @RequestMapping(method= RequestMethod.GET, value = "/accounts/{n}/name/{field}")
    public List<Account> searchByName(@PathVariable int n, @PathVariable String field) throws IOException {


        List<Account> accounts = new ArrayList<>();

        QueryBuilder matchQueryBuilder = QueryBuilders.multiMatchQuery(field).field("name",4).field("businessType", 3).field("proteinType").field("productType")
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
            Account account = new ObjectMapper().readValue(searchHit.getSourceAsString(),Account.class);
            accounts.add(account);
        }
        if (accounts.isEmpty()) {
            accounts = elasticUtilAccount.containsField(field);

        }

        return accounts;
    }
}
