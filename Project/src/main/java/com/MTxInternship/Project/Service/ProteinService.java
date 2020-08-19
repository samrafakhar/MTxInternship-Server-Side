package com.MTxInternship.Project.Service;

import com.MTxInternship.Project.Model.Account;
import com.MTxInternship.Project.Model.Product;
import com.MTxInternship.Project.Model.Protein;
import com.MTxInternship.Project.Repository.ProteinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProteinService {
    @Autowired
    private ProteinRepository proteinRepository;
    //public List<Protein> getProteinsList(int id){
    //  return pr.findByProduct_ProductID(id);
    //}
    public List<Protein> getProteins(){
        return proteinRepository.findAll();
    }
    public Protein getProteinByID(UUID id) {
        return proteinRepository.findByProteinId(id);
    }
    public void addProtein (Protein u) {
        proteinRepository.save(u);
    }
    public void updateProtein (Protein u, UUID id) {
        proteinRepository.save(u);
    }
    public void deleteProtein (UUID id) {
        proteinRepository.deleteByProteinId(id);
    }
    //public List<Protein> findProteinsByProduct(UUID id){return proteinRepository.findProteinsByProduct_ProductID(id);}

    public List<String> findAllByProduct(Product product){
        //System.out.println("findAllByProduct");
        Iterable<Protein> accountsAll = proteinRepository.findAllByProduct(product);
        List<String> proteinList=new ArrayList<>();
        for (Protein account : accountsAll) {
            //System.out.println(account.getProteinName());
            proteinList.add(account.getProteinName());
        }
        return proteinList;
    }
}