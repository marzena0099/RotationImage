package com.example.rotation.Rotation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RotationRepository extends JpaRepository<RotationEntity, Long> {
}
