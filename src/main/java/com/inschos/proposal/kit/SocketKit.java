package com.inschos.proposal.kit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by IceAnt on 2018/3/24.
 */
public class SocketKit {

    private static Logger logger = LoggerFactory.getLogger(SocketKit.class);
    /**
     * 发送socket请求
     * @param serverIp
     * @param serverPort
     * @param msg
     * @return
     */
    public static synchronized String tcpPost(String serverIp,int serverPort,String msg){
        String rs = "";

        if(serverIp==null||"".equals(serverIp)||serverPort<=0){
            logger.error("Ip或端口不存在...");
            return null;
        }

        logger.info("serverIp："+serverIp+" serverPort："+serverPort);

        Socket s = null;
        OutputStream out = null;
        InputStream in = null;
        try {
            s = new Socket();
            SocketAddress socketAddress = new InetSocketAddress(serverIp, serverPort);
            //连接10秒超时
            s.connect(socketAddress,10*1000);
            s.setSendBufferSize(4096);
            s.setTcpNoDelay(true);
            //读取数据超时
            s.setSoTimeout(10*1000);
            s.setKeepAlive(true);
            out = s.getOutputStream();
            in = s.getInputStream();

            //准备报文msg
            logger.info("准备发送报文："+msg);

            out.write(msg.getBytes("GBK"));
            out.flush();

            byte[] rsByte = readStream(in);

            if(rsByte!=null){
                rs = new String(rsByte, "GBK");
            }


        } catch (Exception e) {
            logger.error("tcpPost发送请求异常："+e.getMessage());
        }finally{
            logger.info("tcpPost(rs)："+rs);
            try {
                if(out!=null){
                    out.close();
                    out = null;
                }
                if(in!=null){
                    in.close();
                    in = null;
                }
                if(s!=null){
                    s.close();
                    s = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return rs;

    }

    /**
     * 读取输入流
     * @param in
     * @return
     */
    private static byte[] readStream(InputStream in){
        if(in==null){
            return null;
        }

        byte[] b = null;
        ByteArrayOutputStream outSteam = null;
        try {
            byte[] buffer = new byte[1024];
            outSteam = new ByteArrayOutputStream();

            int len = -1;
            while ((len = in.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }

            b = outSteam.toByteArray();
        } catch (IOException e) {
            logger.error("读取流信息异常"+e);
            e.printStackTrace();
        } finally{
            try {
                if(outSteam!=null){
                    outSteam.close();
                    outSteam = null;
                }
                if(in!=null){
                    in.close();
                    in = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return b;
    }
}
