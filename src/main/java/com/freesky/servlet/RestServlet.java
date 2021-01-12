package com.freesky.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.freesky.utils.CurrencyUtils;

public class RestServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7141484044055456497L;

	private static final String BITTREX_BTC_USDT = "https://bittrex.com/api/v1.1/public/getmarketsummary?market=USDT-BTC";

	private static final Logger logger = Logger.getLogger(RestServlet.class);

	private final SimpleDateFormat gmtFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	private final SimpleDateFormat chinaFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private String status = "";
	private String xwcLast = "";
	private String collectTime = "";
	private String okCoinBtc = "";
	// private String xwcRmb;
	private Long visitNumber = 23586L;

	private String volume = "";
	private String baseVolume = "";
	private String xwcBid = "";
	private String xwcAsk = "";
	private String openBuyOrders = "";
	private String openSellOrders = "";
	private String errMsg = "";

	private String btcRmb = "";
	private String usdRmb = "";

	private String btcBid = "";
	private String btcAsk = "";
	private String btcPrevDay = "";
	private String btcOpenBuyOrders = "";
	private String btcOpenSellOrders = "";
	private String btcVolume = "";
	private String btcBaseVolume = "";

	// hard code exchange rate of USD-RMB.
	private double exchangeRate = 7.1782D;

	private Map<String, String> usdRmbRateApiMap = new HashMap<String, String>();

	private final SimpleDateFormat HOUR_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH");

	private static DataBean bean = new DataBean();

	/**
	 * 当24小时最高价或最低价变动时，说明开始拉高或抛压，发邮件通知。
	 *
	 */
	// 上次BTC 24hour最高价
	private String last24hBtcHigh = "init";
	// 上次BTC 24hour最低价
	private String last24hBtcLow = "init";
	// 当前API获取的BTC 24hour最高价
	private String current24hBtcHigh = "";
	// 当前API获取的BTC 24hour最低价
	private String current24hBtcLow = "";
	// 上次BTC成交价
	private String lastBtcUsd = "";
	// 当前API获取的BTC成交价
	private String btcLast = "";

	// 上次XWC 24hour最高价
	private String lastXwcHigh = "init";
	// 上次XWC 24hour最低价
	private String lastXwcLow = "init";
	// 当前API获取的XWC 24hour最高价
	private String xwcHigh = "";
	// 当前API获取的XWC 24hour最低价
	private String xwcLow = "";
	// 上次XWC成交最高价
	private String preXwcHigh = "";
	// 上次XWC成交最低价
	private String preXwcLow = "";

	/**
	 * 邮箱信息
	 */
	private String host = "smtp-mail.outlook.com";
	private String port = "587";
	private String userName = "xwc2018@outlook.com";
	private String password = "Whitecoin@163.com";
	// private String to = "zhwyv@163.com";

	// 存储三次价格
	private Map<Integer, BigDecimal> priceMap = new HashMap<Integer, BigDecimal>();
	
	/**
	 * 调用REST API获取价格的任务
	 * 
	 * @author Freesky
	 *
	 */
	class RestTask extends TimerTask {
		@Override
		public void run() {
			logger.info("-----------------callRestService------------------");
			callRestService();

			// 比较24小时最高最低价是否有变动，如果是，则发邮件
			compareHighLowValue();
		}
	}

	/**
	 * 调用REST API获取美元(USD)对人民币(CNY)的汇率
	 */
	class CurrencyTask extends TimerTask {
		@Override
		public void run() {
			logger.info("-----------------call USD-CNY exchange RestService------------------");
			callExchangeService();
		}
	}
	
	/**
	 * 监控价格连续变化
	 * @author maxzhang
	 *
	 */
	 class PriceTask extends TimerTask {
		@Override
		public void run() {
			monitorPrice();
		}
	}
	
	public void monitorPrice() {
		BigDecimal first = (priceMap.get(2) == null? BigDecimal.ZERO : priceMap.get(2));
		BigDecimal second = (priceMap.get(3) == null? BigDecimal.ZERO : priceMap.get(3));
		BigDecimal third = (priceMap.get(4) == null? BigDecimal.ZERO : priceMap.get(4));
		BigDecimal fourth = (priceMap.get(5) == null? BigDecimal.ZERO : priceMap.get(5));
		BigDecimal fifth = new BigDecimal(btcLast);
		
		priceMap.put(1, first);
		priceMap.put(2, second);
		priceMap.put(3, third);
		priceMap.put(4, fourth);
		priceMap.put(5, fifth);
		
		// --- debug begin ---
//		System.out.println(new Date());
//		for (BigDecimal price : priceMap.values()) {
//			System.out.print(price.toPlainString() + "  ");
//		}
//		System.out.println();
//		System.out.println("--------------");
		// --- debug end ---
		
		if (second.compareTo(first) > 0 
				&& third.compareTo(second) > 0
				&& fourth.compareTo(third) > 0
				&& fifth.compareTo(fourth) > 0) {
			System.out.println("+++ price increase continuelly in the last 5 times.");
			sendPriceEmail("+++ price increase continuelly in the last 5 times.");
		}
		if (second.compareTo(first) < 0 
				&& third.compareTo(second) < 0
				&& fourth.compareTo(third) < 0
				&& fifth.compareTo(fourth) < 0) {
			System.out.println("--- price decrease continuelly in the last 5 times.");
			sendPriceEmail("--- price decrease continuelly in the last 5 times.");
		}
	}

	private void callExchangeService() {
		double usd2cny = CurrencyUtils.getUSD2CNY();
		if (usd2cny > 1) {
			exchangeRate = usd2cny;
		}
	}

	private void compareHighLowValue() {
		compareHighLowValueBtc();
	}

	private void compareHighLowValueBtc() {
		// last price
		BigDecimal btcUsdValue = new BigDecimal(btcLast);

		BigDecimal current24hBtcHighValue = new BigDecimal(current24hBtcHigh);
		BigDecimal current24hBtcLowValue = new BigDecimal(current24hBtcLow);

		BigDecimal last24hBtcHighValue = new BigDecimal(last24hBtcHigh);
		BigDecimal last24hBtcLowValue = new BigDecimal(last24hBtcLow);
		
		logger.info("current24hBtcHighValue: " + current24hBtcHighValue);
		logger.info("current24hBtcLowValue: " + current24hBtcLowValue);
		logger.info("last24hBtcHighValue: " + last24hBtcHighValue);
		logger.info("last24hBtcLowValue: " + last24hBtcLowValue);
		logger.info("current24hBtcHighValue.compareTo(last24hBtcHighValue): " + current24hBtcHighValue.compareTo(last24hBtcHighValue));
		logger.info("current24hBtcLowValue.compareTo(last24hBtcLowValue): " + current24hBtcLowValue.compareTo(last24hBtcLowValue));

		if (current24hBtcHighValue.compareTo(last24hBtcHighValue) > 0) {
			if (btcUsdValue.compareTo(current24hBtcHighValue) < 0) {
				sendBtcEmail("+++ BTC top increase(-)");
			} else {
				sendBtcEmail("+++ BTC top increase...");
			}
		} else if (current24hBtcHighValue.compareTo(last24hBtcHighValue) < 0) {
			sendBtcEmail("-- BTC top decrease...");
		}
		this.last24hBtcHigh = current24hBtcHigh;

		if (current24hBtcLowValue.compareTo(last24hBtcLowValue) < 0) {
			if (btcUsdValue.compareTo(current24hBtcLowValue) > 0) {
				sendBtcEmail("--- BTC bottom decrease(+)");
			} else {
				sendBtcEmail("--- BTC bottom decrease...");
			}
		} else if (current24hBtcLowValue.compareTo(last24hBtcLowValue) > 0) {
			sendBtcEmail("+++ BTC bottom increase...");
		}
		this.last24hBtcLow = current24hBtcLow;

	}

	private void sendBtcEmail(String subject) {
		try {
			sendTextMail(subject, buildBtcContent());
		} catch (Exception e) {
			logger.error("Error sending BTC email: " + e.getMessage());
		}
	}
	
	private void sendPriceEmail(String subject) {
		try {
			sendTextMail(subject, buildPriceContent());
		} catch (Exception e) {
			System.err.println("Error sending BTC price email: " + e.getMessage());
			logger.error("Error sending BTC price email: " + e.getMessage());
		}
	}

	private String buildBtcContent() {
		StringBuilder content = new StringBuilder();
		int btcHighCompare = this.current24hBtcHigh.compareTo(this.last24hBtcHigh);
		String btcHighSign = "=";
		if (btcHighCompare > 0) {
			btcHighSign = ">";
		} else if (btcHighCompare < 0) {
			btcHighSign = "<";
		}

		int btcLowCompare = this.current24hBtcLow.compareTo(this.last24hBtcLow);
		String btcLowSign = "=";
		if (btcLowCompare > 0) {
			btcLowSign = ">";
		} else if (btcLowCompare < 0) {
			btcLowSign = "<";
		}

		content.append("BTC Last price: " + this.btcLast + " USD").append("\n")
				.append("24hour high: " + this.current24hBtcHigh + "(" + btcHighSign + this.last24hBtcHigh + ")"
						+ " USD")
				.append("\n")
				.append("24hour low:  " + this.current24hBtcLow + "(" + btcLowSign + this.last24hBtcLow + ")" + " USD")
				.append("\n").append("\n")

				.append("buy (bid): " + this.btcBid + " USD").append("\n").append("sell(ask): " + this.btcAsk + " USD")
				.append("\n").append("buy / sell: " + this.btcOpenBuyOrders + " / " + this.btcOpenSellOrders)
				.append("\n").append("previous day: " + this.btcPrevDay).append("\n")
				.append("volume: " + this.btcVolume + " BTC").append("\n")
				.append("volume: " + this.btcBaseVolume + " USD").append("\n").append("\n");

		content.append(this.collectTime);

		return content.toString();
	}
	
	private String buildPriceContent() {
		StringBuilder content = new StringBuilder();
		for (BigDecimal price : priceMap.values()) {
			content.append(price.toPlainString() + "\n");
		}
		content.append("\n");
		
		content.append("BTC Last price: " + this.btcLast + " USD").append("\n")
		.append("24hour high: " + this.current24hBtcHigh + " USD")
		.append("\n")
		.append("24hour low:  " + this.current24hBtcLow + " USD")
		.append("\n").append("\n")

		.append("buy (bid): " + this.btcBid + " USD").append("\n").append("sell(ask): " + this.btcAsk + " USD")
		.append("\n").append("buy / sell: " + this.btcOpenBuyOrders + " / " + this.btcOpenSellOrders)
		.append("\n").append("previous day: " + this.btcPrevDay).append("\n")
		.append("volume: " + this.btcVolume + " BTC").append("\n")
		.append("volume: " + this.btcBaseVolume + " USD").append("\n").append("\n");

		content.append(this.collectTime);
		
		return content.toString();
	}

	private void sendTextMail(String subject, String content) {
		try {
			sendTextMail(subject, content, new Date());
		} catch (Exception e) {
			logger.error("---------Error: " + subject + "--------");
			logger.error(e.getMessage());
			
			e.printStackTrace();
		}
	}

	/**
	 * 发送文本邮件
	 * 
	 * @throws Exception
	 */
	private void sendTextMail(String subject, String content, Date sendDate) throws Exception {
		Properties pro = System.getProperties();
		pro.put("mail.smtp.host", host);
		pro.put("mail.smtp.port", port);
		pro.put("mail.smtp.auth", "true");

		// for outlook begin ---
		pro.put("mail.smtp.starttls.enable", "true");
		// for outlook end ---

		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(pro, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		});
		// 根据session创建一个邮件消息
		Message mailMessage = new MimeMessage(sendMailSession);
		// 设置邮件消息的发送者
		mailMessage.setFrom(new InternetAddress(userName));
		// 创建邮件的接收者地址，并设置到邮件消息中
		// mailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		mailMessage.setRecipients(Message.RecipientType.TO,
				new InternetAddress[] { new InternetAddress("zhwyv@163.com") });
//		mailMessage.setRecipients(Message.RecipientType.CC, new InternetAddress[] {
//				new InternetAddress("hwt69love@163.com") });
		// , new InternetAddress("517935294@qq.com")

		// 设置邮件消息的主题
		mailMessage.setSubject(subject);
		// 设置邮件消息发送的时间
		mailMessage.setSentDate(sendDate);
		// 设置邮件消息的主要内容
		mailMessage.setText(content);
		// 发送邮件
		Transport.send(mailMessage);

		logger.info("--------" + subject + " Mail is sent out.--------");
		System.out.println("---------" + subject + " Mail is Sent.--------");
	}
	
	/**
	 * 初始化三次价格，避免空指针异常。
	 */
	public void initPriceMap() {
		priceMap.put(1, BigDecimal.ZERO);
		priceMap.put(2, BigDecimal.ZERO);
		priceMap.put(3, BigDecimal.ZERO);
		priceMap.put(4, BigDecimal.ZERO);
	}

	@Override
	public void init() {
		// 需要调试的时候，放开此方法。
		// prepareTestData();
		
		initPriceMap();

		gmtFormat.setTimeZone(TimeZone.getTimeZone("Etc/GMT+0"));
		chinaFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

		logger.info("--------read /net/vister.properties to get visit number--------");
		String path = getServletContext().getRealPath("/");
		String propertiesFile = path + File.separator + "net" + File.separator + "visiter.properties";
		Properties pps = new Properties();
		try {
			pps.load(new FileInputStream(propertiesFile));
			String visitNum = pps.getProperty("XWC_VISIT_NUM");
			visitNumber = Long.parseLong(visitNum);
		} catch (FileNotFoundException e) {
			logger.error("Error visiter.properties file not found: " + e.getMessage());
		} catch (IOException e) {
			logger.error("Error: visiter.properties file not access: " + e.getMessage());
		}

//		initBtcLastPrice();

		// call USD-CNY exchange service
		callExchangeService();

		// call REST API and initialize last price.
		callRestService();
		// set last=current to avoid send email when startup Tomcat.
		initLastPrice();

		logger.info("--------init query thread--------");
		Timer timer = new Timer();
		RestTask task = new RestTask();
		/**
		 * 由于发送邮件大约需要1分钟左右，所以每隔2分钟(120秒)采集一次数据。 
		 * every 10 seconds
		 */
		timer.schedule(task, 0, 10 * 1000);

		/**
		 * 每隔2小时取一次汇率
		 */
		CurrencyTask currencyTask = new CurrencyTask();
		// delay 3 seconds to avoid calling REST service at the same time.
		timer.schedule(currencyTask, 3 * 1000, 2 * 60 * 60 * 1000);

		logger.info("--------init query thread----END----");
		
		/**
		 * 每隔2分钟监控一下价格是否连续变化
		 */
		String value = this.getServletContext().getInitParameter("monitorPriceChange");
		if (Boolean.valueOf(value)) {
			PriceTask priceTask = new PriceTask();
			// delay 6 seconds to avoid calling REST service at the same time.
			timer.schedule(priceTask, 6 * 1000, 2 * 60 * 1000);
		}
		
	}

	public void initBtcLastPrice() {
		this.last24hBtcHigh = "10396.00000000";
		this.last24hBtcLow = "10162.629";
		this.lastBtcUsd = "10318.714";
	}

	/**
	 * 准备测试数据
	 */
	private void prepareTestData() {
		// btc
		last24hBtcHigh = "6792";
		last24hBtcLow = "6376";
		// xwc
		lastXwcHigh = "0.00001498";
		lastXwcLow = "0.00001316";
	}

	private void initLastPrice() {
		// btc
		this.last24hBtcHigh = current24hBtcHigh;
		this.last24hBtcLow = current24hBtcLow;
		// xwc
		this.lastXwcHigh = xwcHigh;
		this.lastXwcLow = xwcLow;

		logger.info("Last BTC high: " + this.last24hBtcHigh + " USD");
		logger.info("Last BTC low:  " + this.last24hBtcLow + " USD");
		logger.info("Last XWC high: " + this.lastXwcHigh + " BTC");
		logger.info("Last XWC low:  " + this.lastXwcLow + " BTC");
	}

	/**
	 * 查询Bittrex 比特币对美元价格。
	 */
	private void callBittrexUSDT() {
		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpGet httpGetRequest = new HttpGet(BITTREX_BTC_USDT);
			HttpResponse httpResponse = httpClient.execute(httpGetRequest);
			HttpEntity entity = httpResponse.getEntity();

			byte[] buffer = new byte[1024];
			if (entity != null) {
				InputStream inputStream = entity.getContent();
				try {
					int bytesRead = 0;
					BufferedInputStream bis = new BufferedInputStream(inputStream);
					while ((bytesRead = bis.read(buffer)) != -1) {
						String chunk = new String(buffer, 0, bytesRead);

						JSONObject obj = new JSONObject(chunk);
						status = obj.getString("success");
						JSONArray arr = obj.getJSONArray("result");
						for (int i = 0; i < arr.length(); i++) {
							JSONObject json = arr.getJSONObject(i);

							btcLast = json.getString("Last");
							logger.info("btc-dollor:" + btcLast);

							current24hBtcHigh = json.getString("High");
							BigDecimal bHigh = new BigDecimal(current24hBtcHigh).setScale(8);
							current24hBtcHigh = bHigh.toPlainString();
							logger.info("24hour High:" + current24hBtcHigh);

							current24hBtcLow = json.getString("Low");
							BigDecimal bLow = new BigDecimal(current24hBtcLow).setScale(8);
							current24hBtcLow = bLow.toPlainString();
							logger.info("24hour Low:" + current24hBtcLow);

							btcBid = json.getString("Bid");
							btcAsk = json.getString("Ask");
							btcOpenBuyOrders = json.getString("OpenBuyOrders");
							btcOpenSellOrders = json.getString("OpenSellOrders");
							btcPrevDay = json.getString("PrevDay");
							btcVolume = json.getString("Volume");
							btcBaseVolume = json.getString("BaseVolume");

							collectTime = json.getString("TimeStamp");
							logger.info("timeStamp: " + collectTime);
							if (collectTime.contains(".")) {
								collectTime = collectTime.substring(0, collectTime.indexOf("."));
								logger.debug("---colletTime: " + collectTime);
							}
							Date date = gmtFormat.parse(collectTime);
							collectTime = chinaFormat.format(date);
							logger.debug("---colletTime: " + collectTime);
						}
					}
				} catch (Exception e) {
					logger.error("Error when parse btc-usdt REST JSON: " + e.getMessage());
				} finally {
					try {
						inputStream.close();
					} catch (Exception e1) {
						logger.error("Error when close inputstream: " + e1.getMessage());
					}
				}
			}
		} catch (Exception e) {
			if (errMsg.length() < 1) {
				errMsg = errMsg + "Bittrex服务器异常，请稍后再试！  ";
			}
			logger.error("Error when call btc-usdt REST API: " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		RestServlet app = new RestServlet();
		try {
			app.sendTextMail("test subject", "test content", new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void callRestService() {
		errMsg = "";

		Date d1 = new Date();
		logger.info("---1. Begin call service: " + d1);
//		System.out.println("---1. Begin call service: " + d1);
		long t1 = d1.getTime();

		this.callBittrexUSDT();

		String currentHour = HOUR_FORMAT.format(new Date());
		String value = usdRmbRateApiMap.get(currentHour);
		if (null == value || value.isEmpty()) {
			/**
			 * Dollar-RMB rest API is down, so we hardcode a rate and modify it regularly.
			 * private double rate = 6.60371129D;
			 * http://api.k780.com/?app=finance.rate&scur=USD&tcur=CNY&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4
			 * 
			 */
			usdRmbRateApiMap.clear();
			usdRmbRateApiMap.put(currentHour, "true");
		}

		if (Boolean.parseBoolean(status)) {

			BigDecimal btcUsdPrice = new BigDecimal(btcLast);
			BigDecimal usdRmbRate = BigDecimal.valueOf(exchangeRate);
			BigDecimal btcRmbValue = btcUsdPrice.multiply(usdRmbRate).setScale(4, RoundingMode.HALF_UP);
			this.btcRmb = btcRmbValue.toPlainString();

			synchronized (bean) {
				bean.setBittrexXwc(xwcLast);
				bean.setOkCoinBtc(okCoinBtc);
				bean.setStatus(status);
				bean.setTimeStamp(collectTime);
				bean.setVisitNumber(visitNumber);
				bean.setErrMsg(errMsg);
				bean.setXwcHigh(xwcHigh);
				bean.setXwcLow(xwcLow);
				bean.setVolume(volume);
				bean.setBaseVolume(baseVolume);
				bean.setBid(xwcBid);
				bean.setAsk(xwcAsk);
				bean.setOpenBuyOrders(openBuyOrders);
				bean.setOpenSellOrders(openSellOrders);
				bean.setBtcUsd(btcLast);
				bean.setBtcRmb(btcRmb);
				bean.setUsdRmb(usdRmb);
				bean.setBtcHigh(current24hBtcHigh);
				bean.setBtcLow(current24hBtcLow);
				// new added
				bean.setBtcBid(btcBid);
				bean.setBtcAsk(btcAsk);
				bean.setBtcPrevDay(btcPrevDay);
				bean.setBtcOpenBuyOrders(btcOpenBuyOrders);
				bean.setBtcOpenSellOrders(btcOpenSellOrders);
				bean.setBtcVolume(btcVolume);
				bean.setBtcBaseVolume(btcBaseVolume);

				bean.setUsd2cny(exchangeRate);
			}
		}

		Date d2 = new Date();
		logger.info("---2. After call service: " + d2);
//		System.out.println("---2. After call service: " + d2);
		long t2 = d2.getTime();
		logger.info("---3. call service consume: " + (t2 - t1));
//		System.out.println("---3. call service consume: " + (t2 - t1));

	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// the original output.
		// output(request, response);

		logger.info("--XWC visitNumber: " + visitNumber);

		synchronized (visitNumber) {
			visitNumber++;
			if (visitNumber >= Long.MAX_VALUE - 1) {
				logger.info(Long.MAX_VALUE);
				visitNumber = 1L;
			}
		}

		// MVC
		request.setAttribute("bean", bean);
		RequestDispatcher dispatcher = request.getRequestDispatcher("xwc.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
