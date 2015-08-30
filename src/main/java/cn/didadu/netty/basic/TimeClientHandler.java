package cn.didadu.netty.basic;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.logging.Logger;

public class TimeClientHandler extends ChannelHandlerAdapter {

	private static final Logger logger = Logger
			.getLogger(TimeClientHandler.class.getName());

	private final ByteBuf firstMessage;

	/**
	 * Creates a client-side handler.
	 */
	public TimeClientHandler() {
		byte[] req = "QUERY TIME ORDER".getBytes();
		firstMessage = Unpooled.buffer(req.length);
		firstMessage.writeBytes(req);
	}

	/**
	 * 当客户端和服务端TCP链路建立成功后，Netty的NIO线程会调用channelActive方法，发送指令给服务端
	 * 调用ChannelHandlerContext的writeAndFlush方法将请求发送给服务端
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		ctx.writeAndFlush(firstMessage);
	}

	/**
	 * 当服务端返回应答消息时，channelRead方法被调用，输出服务端返回的消息
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req, "UTF-8");
		System.out.println("Now is : " + body);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// 释放资源
		logger.warning("Unexpected exception from downstream : "
				+ cause.getMessage());
		ctx.close();
	}
}
