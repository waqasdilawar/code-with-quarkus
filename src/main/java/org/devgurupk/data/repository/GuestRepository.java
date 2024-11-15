package org.devgurupk.data.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import org.devgurupk.data.entity.GuestEntity;

@ApplicationScoped
public class GuestRepository implements PanacheRepository<GuestEntity> {}
