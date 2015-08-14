package cn.didadu.io.pio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import cn.didadu.io.bio.TimeServerHandler;

public class TimeServer {

	public static void main(String[] args) {
		int port = 8080;
		if (args != null && args.length > 0) {

			try {
				port = Integer.valueOf(args[0]);
			} catch (NumberFormatException e) {
				// 采用默认值
			}

		}
		ServerSocket server = null;

		try {
			server = new ServerSocket(port);
			System.out.println("The time server is start in port : " + port);
			Socket socket = null;
			TimeServerHandlerExecutePool singleExecutor = new TimeServerHandlerExecutePool(
					50, 10000);// 创建IO任务线程池
			while (true) {
				socket = server.accept();
				singleExecutor.execute(new TimeServerHandler(socket));
			}
		} catch (Exception e) {

		} finally {
			if (server != null) {
				System.out.println("The time server close");
				try {
					server.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				server = null;
			}
		}
	}
}
