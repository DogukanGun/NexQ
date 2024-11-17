package com.dag.nexq_userservice.services.interfaces;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public abstract class ICrudService<T extends JpaRepository<K,ID>,K,ID> {
    private final T repository;

    public ICrudService(T repository){
        this.repository = repository;
    }

    public Optional<K> getItem(ID id){
        return repository.findById(id);
    }

    public void deleteItem(K item){
        repository.delete(item);
    }

    public K updateItem(K item){
        return repository.save(item);
    }

    public List<K> getAll(){
        return repository.findAll();
    }

}
