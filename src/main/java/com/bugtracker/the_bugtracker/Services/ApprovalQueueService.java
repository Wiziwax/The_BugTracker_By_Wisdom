package com.bugtracker.the_bugtracker.Services;

import com.bugtracker.the_bugtracker.Configs.UserNotFoundException;
import com.bugtracker.the_bugtracker.Models.ApprovalQueue;
import com.bugtracker.the_bugtracker.Repositories.ApprovalQueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ApprovalQueueService {

    @Autowired
    ApprovalQueueRepository approvalQueueRepository;

    public List<ApprovalQueue> getAllPendingApproval(){
        return approvalQueueRepository.findAll();
    }

    public void updateApprovalStatus(Integer id, boolean isApproved){
        approvalQueueRepository.updateApprovalStatus(id, isApproved);
    }

    public ApprovalQueue get(Integer id) throws UserNotFoundException{

        try{
            return approvalQueueRepository.findById(id).get();
        }catch (NoSuchElementException e){
            throw new UserNotFoundException("Could not find any request with ID" + id);
        }
    }

}
