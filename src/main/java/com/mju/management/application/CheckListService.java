package com.mju.management.application;

import com.mju.management.domain.model.CheckList;
import com.mju.management.presentation.dto.CheckListRegisterDto;

import java.util.List;

public interface CheckListService {
    public void registerCheckList(CheckListRegisterDto checkListRegisterDto);

    public List<CheckList> getCheckList();

    public void deleteCheckList(Long checkListIndex);

    public CheckList showCheckListOne(Long checkListIndex);

    public void updateCheckList(Long checkListIndex, CheckListRegisterDto checkListRegisterDto);

    public void finishCheckList(Long checkListIndex);
}
