package com.inschos.proposal.logic;

import com.inschos.proposal.kit.EncryptUtil;
import com.inschos.proposal.kit.Md5Util;
import com.inschos.proposal.kit.StringKit;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;



public class TcpClientNocar {

	private  Logger logger = LoggerFactory.getLogger(TcpClientNocar.class);

	public static final int PAD_RIGHT = 0;
	public static final int PAD_LEFT = 1;

	protected String encoding = "GBK";// 字符编码

	protected String headBeforeLength = "";// 长度头的内容前填充的串
	protected String headAfterLength = "";// 长度头的内容后面填充的串
	protected int lengthHeadSize = 0; // 长度报文头大小
	protected int lengthHeadPad = PAD_RIGHT;//
	protected String lengthHeadPadChar = " ";

	private final int SOCKET_CONNECT_TIMEOUT = 10*1000;

	private final int SOCKET_TIMEOUT = 600*1000;

	public String getHeadBeforeLength() {
		return headBeforeLength;
	}

	public void setHeadBeforeLength(String headBeforeLength) {
		this.headBeforeLength = headBeforeLength;
	}

	public String getHeadAfterLength() {
		return headAfterLength;
	}

	public void setHeadAfterLength(String headAfterLength) {
		this.headAfterLength = headAfterLength;
	}

	public int getLengthHeadSize() {
		return lengthHeadSize;
	}

	public void setLengthHeadSize(int lengthHeadSize) {
		this.lengthHeadSize = lengthHeadSize;
	}

	public int getLengthHeadPad() {
		return lengthHeadPad;
	}

	public void setLengthHeadPad(int lengthHeadPad) {
		this.lengthHeadPad = lengthHeadPad;
	}

	public String getLengthHeadPadChar() {
		return lengthHeadPadChar;
	}

	public void setLengthHeadPadChar(String lengthHeadPadChar) {
		this.lengthHeadPadChar = lengthHeadPadChar;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String call(String host, int port, String body) throws Exception {

//		String signData = Md5Util.getMD5String(body);
		String bodyToGbk = new String(body.getBytes("UTF-8"), encoding);
		String signData = Md5Util.getMD5String(bodyToGbk);
		body = body.replace("<signData></signData>", "<signData>" + signData + "</signData>");
//		String requestBody = EncryptUtil.encode(new String(body.getBytes("UTF-8"), encoding), EncryptUtil.getDKey());

		String requestBody = "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";


		Socket socket = null;// 建立一个socket
		try {

			socket = new Socket();// 开启socket
			socket.connect(new InetSocketAddress(host, port),SOCKET_CONNECT_TIMEOUT);
			socket.setSoTimeout(SOCKET_TIMEOUT);
			socket.setTcpNoDelay(true);
			socket.setKeepAlive(true);


			// send
			int length = requestBody.getBytes(encoding).length;// 获取xml报文体的长度
			String lengthHead = "" + length;// 将长度变成字符串形式
			if (PAD_RIGHT == lengthHeadPad) {
				lengthHead = StringUtils.rightPad(lengthHead, lengthHeadSize, lengthHeadPadChar);
			} else if (PAD_LEFT == lengthHeadPad) {
				lengthHead = StringUtils.leftPad(lengthHead, lengthHeadSize, lengthHeadPadChar);
			}
//
			logger.debug("---------报文头部分：------\n" + "开始" + (headBeforeLength + lengthHead + headAfterLength) + "结束");
			logger.debug("---------报文体部分：------\n" + requestBody);


			OutputStream out = socket.getOutputStream();// 获取输出流
			out.write((headBeforeLength + lengthHead + headAfterLength).getBytes(encoding));
			out.write(requestBody.getBytes(encoding));
			out.flush();

//			// receive
			InputStream in = socket.getInputStream();
			byte[] buf = new byte[160240];
			int headLength = headBeforeLength.length() + lengthHeadSize + headAfterLength.length();
			StringBuilder headBuilder = new StringBuilder();

			int unreadLength = headLength;
			while (unreadLength > 0) {
				int readLength = in.read(buf, 0, unreadLength);
				if(readLength==-1){
					break;
				}
				headBuilder.append(new String(buf, 0, readLength, encoding));
				unreadLength -= readLength;
			}

//			int len = -1;
//			while ((len = in.read(buf)) != -1) {
//				headBuilder.append(new String(buf, 0, len, encoding));
//				System.out.println(headBuilder.toString());
//			}
			System.out.println("header");
			System.out.println(headBuilder.toString());
			if(logger.isDebugEnabled()){
				logger.debug(" head :"+headBuilder.toString());
			}
			lengthHead = headBuilder.substring(headBeforeLength.length(), headBeforeLength.length() + lengthHeadSize).trim();
			if(!StringKit.isEmpty(lengthHead)){
				length = Integer.parseInt(lengthHead);
			}
			StringBuilder bodyBuilder = new StringBuilder();
			unreadLength = length;

			while (unreadLength > 0) {
				int readLength = in.read(buf, 0, unreadLength);
				if(readLength==-1){
					break;
				}
				bodyBuilder.append(new String(buf, 0, unreadLength, encoding));
				unreadLength -= readLength;
			}
			String returnXml = bodyBuilder.toString();

			if(logger.isDebugEnabled()){
				logger.debug(" body :"+returnXml);
				System.out.println("body");
				System.out.println(returnXml);
			}

			if (!StringKit.isEmpty(returnXml)) {
				returnXml = EncryptUtil.decode(returnXml, EncryptUtil.getDKey());
			}
			out.close();
			in.close();
			return returnXml;

		} finally {

			if (socket != null) {
				socket.close();
			}
		}
	}

}

