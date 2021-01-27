package org.linlinjava.litemall.wx.dto;

import java.util.List;

public class CommentInfo {
    private Integer orderId; // 订单ID
    private Integer orderGoodsId; // 订单商品ID
    private String content; // 评价内容
    private Integer star; // 评分，1分至5分
    private Boolean hasPicture; // 是否有评价图片
    private List<String> picUrls; // 评价图片地址

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderGoodsId() {
        return orderGoodsId;
    }

    public void setOrderGoodsId(Integer orderGoodsId) {
        this.orderGoodsId = orderGoodsId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public Boolean getHasPicture() {
        return hasPicture;
    }

    public void setHasPicture(Boolean hasPicture) {
        this.hasPicture = hasPicture;
    }

    public List<String> getPicUrls() {
        return picUrls;
    }

    public void setPicUrls(List<String> picUrls) {
        this.picUrls = picUrls;
    }
}
