package com.bugtracker.the_bugtracker.Repositories;

import com.bugtracker.the_bugtracker.Models.ApprovalQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalQueueRepository extends JpaRepository<ApprovalQueue, Integer> {

}
