package cn.didadu.netty.protocol.netty.struct;

public final class NettyMessage {

	/**
	 * 消息头
	 */
	private Header header;

	/**
	 * 消息体
	 */
	private Object body;

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NettyMessage [header=" + header + "]";
	}

}
