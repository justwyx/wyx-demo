package com.wyx.springbootmongodb.entity;

import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * @author : Just wyx
 * @Description : TODO 2020/7/9
 * @Date : 2020/7/9
 */
public class MerchantSku {
	private Integer id;

	/**
	 * 订货号
	 */
	private String itemNumber;

	/**
	 * 型号
	 */
	private String model;

	private String name;

	/**
	 * 品牌
	 */
	private Integer brandId;
	private String brandName;

	/**
	 * 系列
	 */
	private Integer seriesId;
	private String series;

	/**
	 * 分类
	 */
	private Integer classificationId;
	private String classificationName;

	/**
	 * 单位
	 */
	private String unit;

	/**
	 * 最小包装量
	 */
	private String minPackQuantity;

	/**
	 * 最小起订量
	 */
	private String minOrder;

	/**
	 * 图片
	 */
	private String imageUrl;


	/**
	 * 面价
	 */
	private Long price;
	/**
	 * 最低价格
	 */
	private Long lowestPrice;
	/**
	 * 通用价格
	 */
	private Long defaultPrice;

	/**
	 * 总库存数量
	 */
	private Integer totalStock;


	/**
	 * 云仓是否启用状态:未启用-false, 启用-true
	 */
	private Boolean cloudEnableStatus;

	/**
	 * 是否已添加到我的型号库:未添加-false, 已添加-true
	 */
	private Boolean addStatus;

	/**
	 * 现货状态:下架-false, 上架-true
	 */
	private Boolean stockStatus;

	/**
	 * 期货状态:下架-false, 上架-true
	 */
	private Boolean futuresStatus;

	/**
	 * 云库产品状态:下架-false, 上架-true
	 */
	private Boolean cloudStatus;

	/**
	 * 云供应状态:下架-false, 上架-true
	 */
	private Boolean cloudSupplyStatus;

	/**
	 * 商户编辑状态，false:未编辑
	 */
	private Boolean merchantEdit;

	private List<String> mainImageList;

	private List<String> detailImageList;

	private List<String> pdfImageList;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Integer getSeriesId() {
		return seriesId;
	}

	public void setSeriesId(Integer seriesId) {
		this.seriesId = seriesId;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public Integer getClassificationId() {
		return classificationId;
	}

	public void setClassificationId(Integer classificationId) {
		this.classificationId = classificationId;
	}

	public String getClassificationName() {
		return classificationName;
	}

	public void setClassificationName(String classificationName) {
		this.classificationName = classificationName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getMinPackQuantity() {
		return minPackQuantity;
	}

	public void setMinPackQuantity(String minPackQuantity) {
		this.minPackQuantity = minPackQuantity;
	}

	public String getMinOrder() {
		return minOrder;
	}

	public void setMinOrder(String minOrder) {
		this.minOrder = minOrder;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(Long lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	public Long getDefaultPrice() {
		return defaultPrice;
	}

	public void setDefaultPrice(Long defaultPrice) {
		this.defaultPrice = defaultPrice;
	}

	public Integer getTotalStock() {
		return totalStock;
	}

	public void setTotalStock(Integer totalStock) {
		this.totalStock = totalStock;
	}

	public Boolean getCloudEnableStatus() {
		return cloudEnableStatus;
	}

	public void setCloudEnableStatus(Boolean cloudEnableStatus) {
		this.cloudEnableStatus = cloudEnableStatus;
	}

	public Boolean getAddStatus() {
		return addStatus;
	}

	public void setAddStatus(Boolean addStatus) {
		this.addStatus = addStatus;
	}

	public Boolean getStockStatus() {
		return stockStatus;
	}

	public void setStockStatus(Boolean stockStatus) {
		this.stockStatus = stockStatus;
	}

	public Boolean getFuturesStatus() {
		return futuresStatus;
	}

	public void setFuturesStatus(Boolean futuresStatus) {
		this.futuresStatus = futuresStatus;
	}

	public Boolean getCloudStatus() {
		return cloudStatus;
	}

	public void setCloudStatus(Boolean cloudStatus) {
		this.cloudStatus = cloudStatus;
	}

	public Boolean getCloudSupplyStatus() {
		return cloudSupplyStatus;
	}

	public void setCloudSupplyStatus(Boolean cloudSupplyStatus) {
		this.cloudSupplyStatus = cloudSupplyStatus;
	}

	public Boolean getMerchantEdit() {
		return merchantEdit;
	}

	public void setMerchantEdit(Boolean merchantEdit) {
		this.merchantEdit = merchantEdit;
	}

	public List<String> getMainImageList() {
		return mainImageList;
	}

	public void setMainImageList(List<String> mainImageList) {
		this.mainImageList = mainImageList;
	}

	public List<String> getDetailImageList() {
		return detailImageList;
	}

	public void setDetailImageList(List<String> detailImageList) {
		this.detailImageList = detailImageList;
	}

	public List<String> getPdfImageList() {
		return pdfImageList;
	}

	public void setPdfImageList(List<String> pdfImageList) {
		this.pdfImageList = pdfImageList;
	}

	public Update convertToUpdate() {
		Update update = new Update();
		if (this.brandId != null) {
			update.set("brandId", this.brandId);
		}
		if (this.brandName != null) {
			update.set("brandName", this.brandName);
		}
		if (this.name != null) {
			update.set("name", this.name);
		}

		return update;
	}

	@Override
	public String toString() {
		return "MerchantSku{" +
				"id=" + id +
				", itemNumber='" + itemNumber + '\'' +
				", model='" + model + '\'' +
				", name='" + name + '\'' +
				", brandId=" + brandId +
				", brandName='" + brandName + '\'' +
				", seriesId=" + seriesId +
				", series='" + series + '\'' +
				", classificationId=" + classificationId +
				", classificationName='" + classificationName + '\'' +
				", unit='" + unit + '\'' +
				", minPackQuantity='" + minPackQuantity + '\'' +
				", minOrder='" + minOrder + '\'' +
				", imageUrl='" + imageUrl + '\'' +
				", price=" + price +
				", lowestPrice=" + lowestPrice +
				", defaultPrice=" + defaultPrice +
				", totalStock=" + totalStock +
				", cloudEnableStatus=" + cloudEnableStatus +
				", addStatus=" + addStatus +
				", stockStatus=" + stockStatus +
				", futuresStatus=" + futuresStatus +
				", cloudStatus=" + cloudStatus +
				", cloudSupplyStatus=" + cloudSupplyStatus +
				", merchantEdit=" + merchantEdit +
				", mainImageList=" + mainImageList +
				", detailImageList=" + detailImageList +
				", pdfImageList=" + pdfImageList +
				'}';
	}
}
