package com.fym.electrichousekeeper.common;

import lombok.Data;

@Data
public class PageableResponse extends ApiResponse {

    private Integer currentPage;

    private Integer pageSize;

    private Long totalRecord;

    private Integer totalPage;

    public PageableResponse(int status){
        super(status);
    }

    public void setPageInfo(Long totalRecord,Integer totalPage){
        setTotalRecord(totalRecord);
        setTotalPage(totalPage);
    }

    public static PageableResponse OK(Long totalRecord,Integer totalPage){
        PageableResponse pageableResponse = new PageableResponse(OK);
        pageableResponse.setPageInfo(totalRecord,totalPage);
        return pageableResponse;
    }
}
