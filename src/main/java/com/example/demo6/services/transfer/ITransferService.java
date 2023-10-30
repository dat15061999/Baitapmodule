package com.example.demo6.services.transfer;

import com.example.demo6.models.Transfer;
import com.example.demo6.services.IGeneralService;

import java.util.List;

public interface ITransferService extends IGeneralService<Transfer,Long> {
     List<Transfer> findAll(boolean deleted);

}
