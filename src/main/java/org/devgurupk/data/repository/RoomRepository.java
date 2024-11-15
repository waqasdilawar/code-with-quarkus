package org.devgurupk.data.repository;

import jakarta.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.devgurupk.data.entity.RoomEntity;

@ApplicationScoped
public class RoomRepository implements PanacheRepository<RoomEntity> {}
