package grima.kuhaejwo.domain.mateoffer.dao;

import grima.kuhaejwo.domain.mateoffer.domain.MateOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateOfferRepository extends JpaRepository<MateOffer,Long> {
}
