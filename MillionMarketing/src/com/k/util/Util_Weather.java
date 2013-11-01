package com.k.util;

import java.io.UnsupportedEncodingException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class Util_Weather {
	String weather, temperature;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String city = "上海";
	 Util_Weather util=new	Util_Weather();
	 String[] results=util.getWeather(city);
		System.out.println("天气："+results[0]+"温度："+results[1]);

	}

	private static final String NAMESPACE = "http://WebXml.com.cn/";

	// WebService��ַ
	private static String URL = "http://www.webxml.com.cn/webservices/weatherwebservice.asmx";

	private static final String METHOD_NAME = "getWeatherbyCityName";

	private static String SOAP_ACTION = "http://WebXml.com.cn/getWeatherbyCityName";

	private String weatherToday;

	private SoapObject detail,rpc;

	public String[] getWeather(String cityName) {
		try {
			System.out.println("rpc------");
			 rpc = new SoapObject(NAMESPACE, METHOD_NAME);
			System.out.println("rpc:" + rpc);
			System.out.println("cityName is: " + cityName);
			rpc.addProperty("theCityName", cityName);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.bodyOut = rpc;
			envelope.dotNet = true;
			envelope.setOutputSoapObject(rpc);

			HttpTransportSE ht = new HttpTransportSE(URL);

			// AndroidHttpTransport ht = new AndroidHttpTransport(URL);
			ht.debug = true;

			ht.call(SOAP_ACTION, envelope);
			// ht.call(null, envelope);

			// SoapObject result = (SoapObject)envelope.bodyIn;
			// detail = (SoapObject)
			// result.getProperty("getWeatherbyCityNameResult");

			detail = (SoapObject) envelope.getResponse();

			// System.out.println("result" + result);
			System.out.println("detail:" + detail);
			// Toast.makeText(this, detail.toString(),
			// Toast.LENGTH_LONG).show();
			parseWeather(detail);

		} catch (Exception e) {
			e.printStackTrace();
		}
			String temp= weather+","+temperature;
			  String[] results= temp.split(",");
			  return results;
	}

	private void parseWeather(SoapObject detail)
			throws UnsupportedEncodingException {
		String date = detail.getProperty(6).toString();
		weatherToday = " " + date.split(" ")[0];
		weatherToday = weatherToday + "\n " + date.split(" ")[1];
		weatherToday = weatherToday + "\n "
				+ detail.getProperty(5).toString();
		weatherToday = weatherToday + "\n "
				+ detail.getProperty(7).toString() + "\n";
		System.out.println("weatherToday is " + weatherToday);
		// Toast.makeText(this, weatherToday, Toast.LENGTH_LONG).show();
		weather = date.split(" ")[1];
		temperature = detail.getProperty(5).toString();
	}

}
