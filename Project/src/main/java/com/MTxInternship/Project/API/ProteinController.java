package com.MTxInternship.Project.API;

import com.MTxInternship.Project.Model.Account;
import com.MTxInternship.Project.Model.Product;
import com.MTxInternship.Project.Model.Protein;
import com.MTxInternship.Project.Model.User;
import com.MTxInternship.Project.Service.ProductService;
import com.MTxInternship.Project.Service.ProteinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ProteinController {
    @Autowired
    private ProteinService proteinService;
    @Autowired
    private ProductService productService;

    //@RequestMapping(method= RequestMethod.GET, value = "/Products/{id}/Proteins")
    //public List<Protein> getAllProteinsOfProduct(@PathVariable UUID id){
    //   return proteinService.getProteinsList(id);
    //}

    //@CrossOrigin(origins = "http://localhost:4200")
    @CrossOrigin(origins = "http://d2tz8yd01soxbm.cloudfront.net")
    @RequestMapping(method= RequestMethod.GET, value = "/Proteins")
    public List<Protein> getProteins(){
        return proteinService.getProteins();
    }

    /*//@CrossOrigin(origins = "http://localhost:4200")
    @CrossOrigin(origins = "http://d2tz8yd01soxbm.cloudfront.net")
    @RequestMapping(method= RequestMethod.GET, value = "/Proteins")
    public List<Protein> getProteins0fProduct(){
        return proteinService.getProteins();
    }*/

    /*//@CrossOrigin(origins = "http://localhost:4200")
    @CrossOrigin(origins = "http://d2tz8yd01soxbm.cloudfront.net")
    @RequestMapping(method= RequestMethod.GET, value = "/getProteinsOfProduct/{id}")
    public List<Protein> getProteinsByProductID(@PathVariable UUID id){
        List<Protein> p=proteinService.findProteinsByProduct(id);
        for(int i=0; i<proteinService.findProteinsByProduct(id).size();i++)
            System.out.println(p.get(i).getProteinName());
        return proteinService.findProteinsByProduct(id);
    }*/

    @RequestMapping(method= RequestMethod.GET, value = "/Products/{pid}/Proteins/{id}")
    public Protein getProteinByID(@PathVariable UUID id){
        return proteinService.getProteinByID(id);
    }

    @RequestMapping(method= RequestMethod.POST, value = "/Products/{id}/Proteins")
    public void addProtein (@RequestBody Protein u, @PathVariable UUID id) {
        u.setProductType(new Product(id, ""));
        proteinService.addProtein(u);
    }

    @RequestMapping(method= RequestMethod.PUT, value ="/Products/{pid}/Proteins/{id}")
    public void updateProtein (@RequestBody Protein u, @PathVariable UUID id) {
        u.setProductType(new Product(id, ""));
        proteinService.updateProtein(u, id);
    }

    @RequestMapping(method= RequestMethod.DELETE, value = "/Proteins/{id}")
    public void deleteProtein (@PathVariable UUID id) {
        proteinService.deleteProtein(id);
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @CrossOrigin(origins = "http://d2tz8yd01soxbm.cloudfront.net")
    @RequestMapping(method= RequestMethod.GET, value = "/ProteinsOfAProduct/{name}")
    public List<String>loadAllProteinsByProduct(@PathVariable String name){
        Product u=productService.getProductByName(name);
        return proteinService.findAllByProduct(u);
    }
}