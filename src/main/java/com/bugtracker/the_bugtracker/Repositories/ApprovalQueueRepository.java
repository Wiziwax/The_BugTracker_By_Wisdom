package com.bugtracker.the_bugtracker.Repositories;

import com.bugtracker.the_bugtracker.Models.ApprovalQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalQueueRepository extends JpaRepository<ApprovalQueue, Integer> {

    @Modifying
    @Query("UPDATE ApprovalQueue aq SET aq.isApproved=?2 WHERE aq.approvalQueueId=?1")
    public void updateApprovalStatus(Integer id, boolean approved);
}
