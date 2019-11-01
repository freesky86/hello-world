package com.freesky.servlet;

public class DataBean {
	private String status = "";	
	private String bittrexXwc = "";
	private String timeStamp = "";
	private String okCoinBtc = "";
	private String xwcRmb = "";
	private Long visitNumber = 0L;
	
	/**
	 * XWC
	 */
	private String xwcHigh = "";
	private String xwcLow = "";
	private String volume = "";
	private String baseVolume = "";
	private String bid = "";
	private String ask = "";
	private String openBuyOrders = "";
	private String openSellOrders = "";
	
	private String errMsg = "";
	
	/**
	 * BTC
	 */
	private String btcUsd = "";
	private String btcRmb = "";
	private String usdRmb = "";
	private String btcHigh = "";
	private String btcLow = "";
	
	private String btcBid = "";
	private String btcAsk = "";
	private String btcPrevDay = "";
	private String btcOpenBuyOrders = "";
	private String btcOpenSellOrders = "";
	private String btcVolume = "";
	private String btcBaseVolume = "";
	
	/*
	 * USD-CNY exchange
	 */
	private double usd2cny = 0;
	
	public double getUsd2cny() {
		return usd2cny;
	}
	public void setUsd2cny(double usd2cny) {
		this.usd2cny = usd2cny;
	}
	public String getBtcBid() {
		return btcBid;
	}
	public void setBtcBid(String btcBid) {
		this.btcBid = btcBid;
	}
	public String getBtcAsk() {
		return btcAsk;
	}
	public void setBtcAsk(String btcAsk) {
		this.btcAsk = btcAsk;
	}
	public String getBtcPrevDay() {
		return btcPrevDay;
	}
	public void setBtcPrevDay(String btcPrevDay) {
		this.btcPrevDay = btcPrevDay;
	}
	public String getBtcOpenBuyOrders() {
		return btcOpenBuyOrders;
	}
	public void setBtcOpenBuyOrders(String btcOpenBuyOrders) {
		this.btcOpenBuyOrders = btcOpenBuyOrders;
	}
	public String getBtcOpenSellOrders() {
		return btcOpenSellOrders;
	}
	public void setBtcOpenSellOrders(String btcOpenSellOrders) {
		this.btcOpenSellOrders = btcOpenSellOrders;
	}
	public String getBtcVolume() {
		return btcVolume;
	}
	public void setBtcVolume(String btcVolume) {
		this.btcVolume = btcVolume;
	}
	public String getBtcBaseVolume() {
		return btcBaseVolume;
	}
	public void setBtcBaseVolume(String btcBaseVolume) {
		this.btcBaseVolume = btcBaseVolume;
	}
	public String getBtcRmb() {
		return btcRmb;
	}
	public void setBtcRmb(String btcRmb) {
		this.btcRmb = btcRmb;
	}
	
	public String getBtcHigh() {
		return btcHigh;
	}
	public void setBtcHigh(String btcHigh) {
		this.btcHigh = btcHigh;
	}
	public String getBtcLow() {
		return btcLow;
	}
	public void setBtcLow(String btcLow) {
		this.btcLow = btcLow;
	}
	public String getBtcUsd() {
		return btcUsd;
	}
	public void setBtcUsd(String btcUsd) {
		this.btcUsd = btcUsd;
	}
	public String getUsdRmb() {
		return usdRmb;
	}
	public void setUsdRmb(String usdRmb) {
		this.usdRmb = usdRmb;
	}
	public String getOpenBuyOrders() {
		return openBuyOrders;
	}
	public void setOpenBuyOrders(String openBuyOrders) {
		this.openBuyOrders = openBuyOrders;
	}
	public String getOpenSellOrders() {
		return openSellOrders;
	}
	public void setOpenSellOrders(String openSellOrders) {
		this.openSellOrders = openSellOrders;
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getAsk() {
		return ask;
	}
	public void setAsk(String ask) {
		this.ask = ask;
	}
	public String getXwcHigh() {
		return xwcHigh;
	}
	public void setXwcHigh(String xwcHigh) {
		this.xwcHigh = xwcHigh;
	}
	public String getXwcLow() {
		return xwcLow;
	}
	public void setXwcLow(String xwcLow) {
		this.xwcLow = xwcLow;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getBaseVolume() {
		return baseVolume;
	}
	public void setBaseVolume(String baseVolume) {
		this.baseVolume = baseVolume;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public Long getVisitNumber() {
		return visitNumber;
	}
	public void setVisitNumber(Long visitNumber) {
		this.visitNumber = visitNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBittrexXwc() {
		return bittrexXwc;
	}
	public void setBittrexXwc(String bittrexXwc) {
		this.bittrexXwc = bittrexXwc;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getOkCoinBtc() {
		return okCoinBtc;
	}
	public void setOkCoinBtc(String okCoinBtc) {
		this.okCoinBtc = okCoinBtc;
	}
	public String getXwcRmb() {
		return xwcRmb;
	}
	public void setXwcRmb(String xwcRmb) {
		this.xwcRmb = xwcRmb;
	}
}
