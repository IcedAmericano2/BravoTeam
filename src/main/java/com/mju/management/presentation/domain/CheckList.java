package com.mju.management.presentation.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "checkList")
public class CheckList {
    @Builder
    public CheckList(String checkListContent){
        this.checkListContent = checkListContent;
        this.isChecked = false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "checkList_index")
    private Long checkListIndex;

    @Column(name = "checkList_content")
    private String checkListContent;

    @Column(name = "isChecked")
    private boolean isChecked;

    public void update(String checkListContent) {
        this.checkListContent = checkListContent;
        this.isChecked = false;
    }

    public void finish() {
        this.isChecked = true;
    }
}
