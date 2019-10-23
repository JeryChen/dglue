package com.ars.dglue.admin.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话介绍功能〉<br>
 * 〈功能详细描述〉
 *
 * @author jierui on 2019-10-18.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class PagingQuery implements Serializable {

    private static final long serialVersionUID = -6024392761990989055L;
    /**
     * 默认限制数量
     * 建议根据业务表的字段总大小设置合理值
     */
    public static int DEFAULT_LIMIT = 3000;

    /**
     * 当前页,默认第1页
     */
    public int pageNo = 1;

    /**
     * 页面大小,默认每页10
     */
    public int pageSize = 10;

    /**
     * 满足条件的总数量
     */
    public int totalNum;

    /**
     * 偏移量
     */
    private int offset;

    /**
     * 限制数
     */
    private int limit;

    /**
     * 排序条件
     */
    private List<Sort> sorts;

    /**
     * 上一页数据最大Id
     * 注意这种查询必须使用 order by id asc
     */
    private Long lastMaxId;

    public PagingQuery(){
        this(DEFAULT_LIMIT);
    }

    public PagingQuery(int limit){
        this(0, limit);
    }

    public PagingQuery(int offset, int limit){
        this.offset = offset;
        this.limit = limit;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize > DEFAULT_LIMIT || pageSize < 1) {
            throw new IllegalArgumentException("每页显示条数范围为1~" + DEFAULT_LIMIT + "条");
        }
        this.pageSize = pageSize;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset < 0 ? 0: offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if (limit > DEFAULT_LIMIT || limit < 1) {
            throw new IllegalArgumentException("每页显示条数范围为1~" + DEFAULT_LIMIT + "条");
        }
        this.limit = limit;
    }

    public List<Sort> getSorts() {
        return sorts;
    }

    public Long getLastMaxId() {
        return lastMaxId;
    }

    public void setLastMaxId(Long lastMaxId) {
        this.lastMaxId = lastMaxId;
    }

    public void doPage(Integer count) {
        this.totalNum = count;
        if (pageSize <= 0) {
            this.pageSize = DEFAULT_LIMIT;
        }
        Integer maxPage = totalNum / pageSize + (totalNum % pageSize == 0 ? 0 : 1);
        if (pageNo <= 0) {
            this.pageNo = 1;
        }
        Integer page = pageNo > maxPage ? maxPage : pageNo;
        page = page < 1 ? 1 : page;
        Integer start = page > 0 ? (page - 1) * pageSize : 0;
        offset = start;
        limit = pageSize;
    }

    /**
     * 新增排序字段
     * @param column 列名
     * @param sortType 排序方式
     */
    public void addOrderBy(String column, Sort.SortType sortType) {
        if(column == null || column.trim().length() == 0){
            throw new IllegalArgumentException("列名不能为空");
        }
        if(sortType == null){
            throw new IllegalArgumentException("排序方式不能为空");
        }
        if(sorts == null){
            sorts = new ArrayList<Sort>(2);
        }
        //防止注入
        column = column.replaceAll(";", "");
        this.sorts.add(new Sort(column, sortType));
    }

    @Override
    public String toString() {
        return "PagingQuery{" +
                "offset=" + offset +
                ", limit=" + limit +
                ", sorts=" + sorts +
                ", lastMaxId=" + lastMaxId +
                '}';
    }
}
