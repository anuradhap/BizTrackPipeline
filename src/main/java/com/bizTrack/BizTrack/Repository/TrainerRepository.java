package com.bizTrack.BizTrack.Repository;

import com.bizTrack.BizTrack.Model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {
}
