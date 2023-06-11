package com.influence.webservermanagement.service;

import com.influence.webservermanagement.model.Server;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ServerService {
    Server create(Server server);
    Server ping(String ipAddress) throws IOException;
    List<Server> getServers(Integer limit);
    Optional<Server> getServer(Long id);
    Server update(Server server);
    Boolean delete(Long id);
}