package com.influence.webservermanagement.service.implementation;

import com.influence.webservermanagement.exception.ResourceNotFoundException;
import com.influence.webservermanagement.model.Server;
import com.influence.webservermanagement.repository.ServerRepository;
import com.influence.webservermanagement.service.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.influence.webservermanagement.model.Status.SERVER_DOWN;
import static com.influence.webservermanagement.model.Status.SERVER_UP;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {

    private final ServerRepository serverRepository;

    public String generateRandomImageURL(){
        String[] images = {"server1.png","server2.png","server3.png","server4.png"};
        return images[new Random().nextInt(4)];
    }

    @Override
    public Server create(Server server) {
        log.info("ServerServiceImpl::create is creating new Server : {}", server.getName());
        server.setImageUrl(generateRandomImageURL());
        Server savedServer = serverRepository.save(server);
        log.info("ServerServiceImpl::create New server created successfully : {}", server.getName());
        return savedServer;
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("ServerServiceImpl::ping is attempting to ping the IP Address : {}", ipAddress);
        Optional<Server> serverOptional = serverRepository.findByIpAddress(ipAddress);
        if(serverOptional.isPresent()){
            Server server = serverOptional.get();
            InetAddress address = InetAddress.getByName(ipAddress);
            server.setStatus(address.isReachable(5000) ? SERVER_UP : SERVER_DOWN );
            log.info("ServerServiceImpl::ping completed action for IP Address : {}", server.getName());
            return serverRepository.save(server);
        }
        else {
            log.error("ServerServiceImpl::ping No Servers were found for Ip Address : {}", ipAddress);
            throw new ResourceNotFoundException("Server with IpAddress wasn't found " + ipAddress);
        }
    }

    @Override
    public List<Server> getServers(Integer limit) {
        log.info("ServerServiceImpl::getServers is executing");
        List<Server> servers = serverRepository.findAll(PageRequest.of(0, limit)).toList();
        log.info("ServerServiceImpl::getServers executed successfully");
        return servers;
    }

    @Override
    public Optional<Server> getServer(Long id) {
        log.info("ServerServiceImpl::getServer is executing. Attempting to get : {}", id);
        Optional<Server> serverByIdOptional = serverRepository.findById(id);
        if(serverByIdOptional.isPresent()){
            log.info("ServerServiceImpl::getServer executed successfully. Attempting to get : {}", id);
            return serverByIdOptional;
        }else{
            log.error("ServerServiceImpl::getServer Error Triggered. Unable to find Server with ID : {}",id);
            throw new RuntimeException("No Server found with ID : "+id);
        }
    }

    @Override
    public Server update(Server server) {
        log.info("ServerServiceImpl::update is executing. Attempting to update server : {}", server.getName());
        Server savedServer = serverRepository.save(server);
        log.info("ServerServiceImpl::update executed successfully.");
        return savedServer;
    }

    @Override
    public Boolean delete(Long id) {
        log.info("ServerServiceImpl::delete is executing. Attempting to delete server with id : {}", id);
        serverRepository.deleteById(id);
        log.info("ServerServiceImpl::delete executed successfully.");
        return true;
    }
}