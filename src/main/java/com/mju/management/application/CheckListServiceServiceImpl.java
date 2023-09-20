package com.mju.management.application;

import com.mju.management.domain.model.Exception.ExceptionList;
import com.mju.management.domain.model.Exception.NonExistentException;
import com.mju.management.domain.model.CheckList;
import com.mju.management.domain.repository.CheckListRepository;
import com.mju.management.presentation.dto.CheckListRegisterDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckListServiceServiceImpl implements CheckListService {
    private final CheckListRepository checkListRepository;

    @Override
    @Transactional
    public void registerCheckList(CheckListRegisterDto checkListRegisterDto) {
        CheckList checkList = CheckList.builder()
                .checkListContent(checkListRegisterDto.getCheckListContent())
                .build();
        checkListRepository.save(checkList);
    }

    @Override
    @Transactional
    public List<CheckList> getCheckList() {
        Sort sort = Sort.by(Sort.Order.asc("isChecked"));
        List<CheckList> checkListList =  checkListRepository.findAll(sort);
        if (!checkListList.isEmpty()) {
            return checkListList;
        }else {
            throw new NonExistentException(ExceptionList.NON_EXISTENT_CHECKLIST);
        }
    }

    @Override
    @Transactional
    public void deleteCheckList(Long checkListIndex) {
        Optional<CheckList> optionalCheckList = checkListRepository.findById(checkListIndex);
        if (optionalCheckList.isPresent()) { // 해당 index의 CheckList가 존재하는 경우
            checkListRepository.deleteById(checkListIndex);
        } else {
            throw new NonExistentException(ExceptionList.NON_EXISTENT_CHECKLIST);
        }
    }

    @Override
    @Transactional
    public CheckList showCheckListOne(Long checkListIndex) {
        Optional<CheckList> optionalCheckList = checkListRepository.findById(checkListIndex);
        if (optionalCheckList.isPresent()) {
            CheckList checkList = optionalCheckList.get();
            return checkList;
        } else {
            throw new NonExistentException(ExceptionList.NON_EXISTENT_CHECKLIST);
        }
    }

    @Override
    @Transactional
    public void updateCheckList(Long checkListIndex, CheckListRegisterDto checkListRegisterDto) {
        Optional<CheckList> optionalCheckList = checkListRepository.findById(checkListIndex);
        if (optionalCheckList.isPresent()) {
            CheckList checkList = optionalCheckList.get();
            checkList.update(checkListRegisterDto.getCheckListContent());
            checkListRepository.save(checkList);
        } else {
            throw new NonExistentException(ExceptionList.NON_EXISTENT_CHECKLIST);
        }
    }

    @Override
    @Transactional
    public void finishCheckList(Long checkListIndex) {
        Optional<CheckList> optionalCheckList = checkListRepository.findById(checkListIndex);
        if (optionalCheckList.isPresent()) {
            CheckList checkList = optionalCheckList.get();
            checkList.finish();
            checkListRepository.save(checkList);
        } else {
            throw new NonExistentException(ExceptionList.NON_EXISTENT_CHECKLIST);
        }
    }

}
