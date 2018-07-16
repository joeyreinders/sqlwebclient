package net.sqlwebclient.util;

public final class Pair<L, R> {
	private final L left;
	private final R right;

	private Pair(final L left, final R right) {
		this.left = left;
		this.right = right;
	}

	public L getLeft() {
		return left;
	}

	public R getRight() {
		return right;
	}

	public static <L, R> Pair<L, R> newInstance(final L left, final R right) {
		return new Pair<L, R>(left, right);
	}
}
