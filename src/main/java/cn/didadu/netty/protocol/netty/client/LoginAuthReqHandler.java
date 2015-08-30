package cn.didadu.netty.protocol.netty.client;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import cn.didadu.netty.protocol.netty.MessageType;
import cn.didadu.netty.protocol.netty.struct.Header;
import cn.didadu.netty.protocol.netty.struct.NettyMessage;

public class LoginAuthReqHandler extends ChannelHandlerAdapter {

	 /**
     * Calls {@link ChannelHandlerContext#fireChannelActive()} to forward to the
     * next {@link ChannelHandler} in the {@link ChannelPipeline}.
     * 
     * Sub-classes may override this method to change behavior.
     * 
     * 客户端与服务端Tcp链路建立成功后激活channelActive方法
     * channelActive方法中构造握手认证消息并发送到服务端
     */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(buildLoginReq());
	}

	private NettyMessage buildLoginReq() {
		NettyMessage message = new NettyMessage();
		Header header = new Header();
		header.setType(MessageType.LOGIN_REQ.value());
		message.setHeader(header);
		return message;
	}

	/**
     * Calls {@link ChannelHandlerContext#fireChannelRead(Object)} to forward to
     * the next {@link ChannelHandler} in the {@link ChannelPipeline}.
     * 
     * Sub-classes may override this method to change behavior.
     * 
     * 当服务端有消息返回时调用channelRead方法
     * channelRead方法中判断是否是握手消息
     * 如果是握手消息，判断认证状态
     * 如果不是握手消息，直接传递给后面的ChannelHandler进行处理
     */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		NettyMessage message = (NettyMessage) msg;

		// 如果是握手应答消息，需要判断是否认证成功
		if (message.getHeader() != null
				&& message.getHeader().getType() == MessageType.LOGIN_RESP
						.value()) {
			byte loginResult = (byte) message.getBody();
			if (loginResult != (byte) 0) {
				// 握手失败，关闭连接
				ctx.close();
			} else {
				System.out.println("Login is ok : " + message);
				ctx.fireChannelRead(msg);
			}
		} else {
			ctx.fireChannelRead(msg);
		}
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.fireExceptionCaught(cause);
	}
}
