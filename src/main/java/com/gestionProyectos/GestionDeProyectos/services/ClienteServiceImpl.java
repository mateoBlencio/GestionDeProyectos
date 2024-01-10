package com.gestionProyectos.GestionDeProyectos.services;

import com.gestionProyectos.GestionDeProyectos.model.Cliente;
import com.gestionProyectos.GestionDeProyectos.repositories.ClienteRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ClienteServiceImpl {
    final ClienteRepository clienteRepository;

    public Page<Cliente> findAll(int page, int size){
        PageRequest pageable = PageRequest.of(page, size);
        return clienteRepository.findAll(pageable);}

    public Optional<Cliente> findOne(Integer id){return clienteRepository.findById(id);}

    @Transactional
    public Cliente create(String nombreCliente,
                          Integer telefono,
                          String mail){

        Cliente cliente = new Cliente(nombreCliente, telefono, mail);

        return clienteRepository.save(cliente);
    }

    @Transactional
    public void update(Integer nroCliente,
                       Integer telefonoP,
                       String mailP){
        val cliente = clienteRepository.findById(nroCliente)
                .orElseThrow(() -> new IllegalArgumentException("Cliente not found"));

        Integer telefono = (telefonoP != null) ? telefonoP : cliente.getTelefono();
        String mail = (mailP != null) ? mailP : cliente.getMail();

        cliente.update(telefono, mail);
        clienteRepository.save(cliente);
    }

    @Transactional
    public void delete(Integer nroCliente){
        try{
            clienteRepository.deleteById(nroCliente);
        } catch (Exception e){
            throw new IllegalArgumentException();
        }
    }
}
