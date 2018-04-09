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

		String transBody = body;
		String signData = Md5Util.getMD5String(transBody.getBytes(encoding));
		body = body.replace("<signData></signData>", "<signData>" + signData + "</signData>");
		String requestBody = EncryptUtil.encode(body, EncryptUtil.getDKey());
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

			String decodeXml = returnXml;


			if (!StringKit.isEmpty(returnXml)) {

				decodeXml = EncryptUtil.decode(returnXml, EncryptUtil.getDKey());
			}
			out.close();
			in.close();
			if(logger.isDebugEnabled()){
				logger.debug(" secret body :"+returnXml);
				logger.debug(" body :"+decodeXml);
//				System.out.println(decodeXml);

			}
			return decodeXml;

		} finally {

			if (socket != null) {
				socket.close();
			}
		}
	}

}

