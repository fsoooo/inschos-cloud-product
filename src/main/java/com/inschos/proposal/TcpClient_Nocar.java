package com.inschos.proposal;

import com.inschos.proposal.kit.EncryptUtil;
import com.inschos.proposal.kit.Md5Util;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;



public class TcpClient_Nocar {
	public static final int PAD_RIGHT = 0;
	public static final int PAD_LEFT = 1;

	protected String encoding = "GBK";// 字符编码

	protected String headBeforeLength = "";// 长度头的内容前填充的串
	protected String headAfterLength = "";// 长度头的内容后面填充的串
	protected int lengthHeadSize = 0; // 长度报文头大小
	protected int lengthHeadPad = PAD_RIGHT;//
	protected String lengthHeadPadChar = " ";

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

	public String call(String host, int port, String requestBody) throws Exception {

		String signData = Md5Util.getMD5String(requestBody);
//		String signData = Md5Util.getMD5String(requestBody.getBytes("GBK"));
		requestBody = requestBody.replace("<signData></signData>", "<signData>" + signData + "</signData>");
		requestBody = EncryptUtil.encode(requestBody, EncryptUtil.getDKey());

		Socket socket = null;// 建立一个socket
		try {
			socket = new Socket(host, port);// 开启socket
			// send
			int length = requestBody.getBytes(encoding).length;// 获取xml报文体的长度
			String lengthHead = "" + length;// 将长度变成字符串形式
			if (PAD_RIGHT == lengthHeadPad) {
				lengthHead = StringUtils.rightPad(lengthHead, lengthHeadSize, lengthHeadPadChar);
			} else if (PAD_LEFT == lengthHeadPad) {
				lengthHead = StringUtils.leftPad(lengthHead, lengthHeadSize, lengthHeadPadChar);
			}

			System.out.println("---------报文头部分：------\n" + "开始" + (headBeforeLength + lengthHead + headAfterLength) + "结束");
			System.out.println("---------报文体部分：------\n" + requestBody);

			OutputStream out = socket.getOutputStream();// 获取输出流
			out.write((headBeforeLength + lengthHead + headAfterLength).getBytes(encoding));
			out.write(requestBody.getBytes(encoding));
			out.flush();

			// receive
			InputStream in = socket.getInputStream();
			byte[] buf = new byte[160240];
			int headLength = headBeforeLength.length() + lengthHeadSize + headAfterLength.length();
			StringBuilder headBuilder = new StringBuilder();


			int len = -1;
			while ((len = in.read(buf)) != -1) {
				int readLength = in.read(buf, 0, len);
				headBuilder.append(new String(buf, 0, readLength, encoding));
			}
			System.out.println(headBuilder.toString());


			int unreadLength = headLength;
			while (unreadLength > 0) {
				int readLength = in.read(buf, 0, unreadLength);
				headBuilder.append(new String(buf, 0, readLength, encoding));
				unreadLength -= readLength;
			}
			lengthHead = headBuilder.substring(headBeforeLength.length(), headBeforeLength.length() + lengthHeadSize).trim();
			length = Integer.parseInt(lengthHead);
			StringBuilder bodyBuilder = new StringBuilder();
			unreadLength = length;
			while (unreadLength > 0) {
				int readLength = in.read(buf, 0, unreadLength);
				bodyBuilder.append(new String(buf, 0, readLength, encoding));
				unreadLength -= readLength;
			}
			String returnXml = bodyBuilder.toString();

			System.out.println(returnXml);

			if (!"".equals(returnXml) && returnXml != null) {
				returnXml = EncryptUtil.decode(returnXml, EncryptUtil.getDKey());
			}
			return returnXml;
		} finally {
			if (socket != null) {
				socket.close();
			}
		}
	}

}

